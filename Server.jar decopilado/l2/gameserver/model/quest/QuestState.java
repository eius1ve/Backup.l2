/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.quest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.GameServer;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.OneDayRewardHolder;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.actor.OnKillListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Party;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Spawner;
import l2.gameserver.model.Summon;
import l2.gameserver.model.base.Element;
import l2.gameserver.model.entity.oneDayReward.requirement.CompleteQuestRequirement;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestTimer;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowQuestMark;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.network.l2.s2c.QuestList;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.TutorialEnableClientEvent;
import l2.gameserver.network.l2.s2c.TutorialShowHtml;
import l2.gameserver.network.l2.s2c.TutorialShowQuestionMark;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.spawn.PeriodOfDay;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class QuestState {
    private static final Logger cw = LoggerFactory.getLogger(QuestState.class);
    public static final int RESTART_HOUR = 6;
    public static final int RESTART_MINUTES = 30;
    public static final String VAR_COND = "cond";
    public static final QuestState[] EMPTY_ARRAY = new QuestState[0];
    private final Player n;
    private Quest a;
    private int _state;
    private Integer c = null;
    private Map<String, String> bm = new ConcurrentHashMap<String, String>();
    private Map<String, QuestTimer> bn = new ConcurrentHashMap<String, QuestTimer>();
    private OnKillListener a = null;

    public QuestState(Quest quest, Player player, int n) {
        this.a = quest;
        this.n = player;
        player.setQuestState(this);
        this._state = n;
        quest.notifyCreate(this);
    }

    public void addExpAndSp(long l, long l2) {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        l = (long)((double)l * this.getRateQuestsRewardExp());
        l2 = (long)((double)l2 * this.getRateQuestsRewardSp());
        if (l > 0L && l2 > 0L) {
            player.addExpAndSp(l, l2);
        } else {
            if (l > 0L) {
                player.addExpAndSp(l, 0L);
            }
            if (l2 > 0L) {
                player.addExpAndSp(0L, l2);
            }
        }
    }

    public void addNotifyOfDeath(Player player, boolean bl) {
        Summon summon;
        OnDeathListenerImpl onDeathListenerImpl = new OnDeathListenerImpl();
        player.addListener(onDeathListenerImpl);
        if (bl && (summon = player.getPet()) != null) {
            summon.addListener(onDeathListenerImpl);
        }
    }

    public void addPlayerOnKillListener() {
        if (this.a != null) {
            throw new IllegalArgumentException("Cant add twice kill listener to player");
        }
        this.a = new PlayerOnKillListenerImpl();
        this.n.addListener(this.a);
    }

    public void removePlayerOnKillListener() {
        if (this.a != null) {
            this.n.removeListener(this.a);
        }
    }

    public void addRadar(int n, int n2, int n3) {
        Player player = this.getPlayer();
        if (player != null) {
            player.addRadar(n, n2, n3);
        }
    }

    public void addRadarWithMap(int n, int n2, int n3) {
        Player player = this.getPlayer();
        if (player != null) {
            player.addRadarWithMap(n, n2, n3);
        }
    }

    public void exitCurrentQuest(Quest quest) {
        Player player = this.getPlayer();
        this.exitCurrentQuest(true);
        quest.newQuestState(player, 4);
        QuestState questState = player.getQuestState(quest.getClass());
        questState.setRestartTime();
    }

    public QuestState exitCurrentQuest(boolean bl) {
        return this.exitCurrentQuest(bl, false);
    }

    public QuestState exitCurrentQuest(boolean bl, boolean bl2) {
        Player player = this.getPlayer();
        if (player == null) {
            return this;
        }
        this.removePlayerOnKillListener();
        for (int n : this.a.getItems()) {
            ItemInstance itemInstance = player.getInventory().getItemByItemId(n);
            if (itemInstance == null || n == 57) continue;
            long l = itemInstance.getCount();
            player.getInventory().destroyItemByItemId(n, l);
            player.getWarehouse().destroyItemByItemId(n, l);
        }
        if (bl) {
            player.removeQuestState(this.a.getName());
            Quest.deleteQuestInDb(this);
            this.bm.clear();
        } else {
            Object object = this.bm.keySet().iterator();
            while (object.hasNext()) {
                String string = (String)object.next();
                if (string == null) continue;
                this.unset(string);
            }
            this.setState(3);
            Quest.updateQuestInDb(this);
        }
        GameServer.getInstance().getListeners().fireEvent("onQuestFinish", player, this.getQuest(), bl2);
        if (!bl2 && !bl) {
            OneDayRewardHolder.getInstance().fireRequirements(player, null, CompleteQuestRequirement.class);
        }
        player.sendPacket((IStaticPacket)new QuestList(player));
        return this;
    }

    public void abortQuest() {
        this.a.onAbort(this);
        this.exitCurrentQuest(true, true);
    }

    public String get(String string) {
        return this.bm.get(string);
    }

    public Map<String, String> getVars() {
        return this.bm;
    }

    public int getInt(String string) {
        int n = 0;
        try {
            String string2 = this.get(string);
            if (string2 == null) {
                return 0;
            }
            n = Integer.parseInt(string2);
        }
        catch (Exception exception) {
            cw.error(this.getPlayer().getName() + ": variable " + string + " isn't an integer: " + n, (Throwable)exception);
        }
        return n;
    }

    public int getItemEquipped(int n) {
        return this.getPlayer().getInventory().getPaperdollItemId(n);
    }

    public Player getPlayer() {
        return this.n;
    }

    public Quest getQuest() {
        return this.a;
    }

    public boolean checkQuestItemsCount(int ... nArray) {
        Player player = this.getPlayer();
        if (player == null) {
            return false;
        }
        for (int n : nArray) {
            if (player.getInventory().getCountOf(n) > 0L) continue;
            return false;
        }
        return true;
    }

    public long getSumQuestItemsCount(int ... nArray) {
        Player player = this.getPlayer();
        if (player == null) {
            return 0L;
        }
        long l = 0L;
        for (int n : nArray) {
            l += player.getInventory().getCountOf(n);
        }
        return l;
    }

    public long getQuestItemsCount(int n) {
        Player player = this.getPlayer();
        return player == null ? 0L : player.getInventory().getCountOf(n);
    }

    public long getQuestItemsCount(int ... nArray) {
        long l = 0L;
        for (int n : nArray) {
            l += this.getQuestItemsCount(n);
        }
        return l;
    }

    public boolean haveQuestItem(int n, int n2) {
        return this.getQuestItemsCount(n) >= (long)n2;
    }

    public boolean haveQuestItem(int n) {
        return this.haveQuestItem(n, 1);
    }

    public int getState() {
        return this._state == 4 ? 1 : this._state;
    }

    public String getStateName() {
        return Quest.getStateName(this._state);
    }

    public void giveItems(int n, long l) {
        if (n == 57) {
            this.giveItems(n, l, true);
        } else {
            this.giveItems(n, l, false);
        }
    }

    public void giveItems(int n, long l, boolean bl) {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        if (l <= 0L) {
            l = 1L;
        }
        if (bl) {
            l = (long)((double)l * (n != 57 ? this.getRateQuestsReward() : this.getRateQuestsAdenaReward()));
        }
        ItemFunctions.addItem((Playable)player, n, l, true);
        Log.LogItem(this.getPlayer(), Log.ItemLog.QuestCreate, n, l, 0L, this.getQuest().getQuestIntId());
        player.sendChanges();
    }

    public void giveItems(int n, long l, Element element, int n2) {
        ItemTemplate itemTemplate;
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        if (l <= 0L) {
            l = 1L;
        }
        if ((itemTemplate = ItemHolder.getInstance().getTemplate(n)) == null) {
            return;
        }
        int n3 = 0;
        while ((long)n3 < l) {
            ItemInstance itemInstance = ItemFunctions.createItem(n);
            if (element != Element.NONE) {
                itemInstance.setAttributeElement(element, n2);
            }
            player.getInventory().addItem(itemInstance);
            ++n3;
        }
        player.sendPacket((IStaticPacket)SystemMessage.obtainItems(itemTemplate.getItemId(), l, 0));
        player.sendChanges();
    }

    public void dropItem(NpcInstance npcInstance, int n, long l) {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        ItemInstance itemInstance = ItemFunctions.createItem(n, l);
        itemInstance.dropToTheGround(player, npcInstance);
    }

    public int rollDrop(int n, double d) {
        if (d <= 0.0 || n <= 0) {
            return 0;
        }
        return this.rollDrop(n, n, d);
    }

    public int rollDrop(int n, int n2, double d) {
        Party party;
        if (d <= 0.0 || n <= 0 || n2 <= 0) {
            return 0;
        }
        int n3 = 1;
        d *= this.getRateQuestsDrop();
        if (Config.ALT_PARTY_BONUS_FOR_QUESTS && this.getQuest().getParty() != 0 && (party = this.getPlayer().getParty()) != null) {
            d *= Config.ALT_PARTY_BONUS[party.getMemberCountInRange(this.getPlayer(), Config.ALT_PARTY_DISTRIBUTION_RANGE) - 1];
        }
        if (d > 100.0) {
            double d2 = d - 100.0;
            d = 100.0;
            n3 = 1 + Math.max(0, (int)(d2 / 100.0));
            if (Rnd.chance(d2 - (double)(n3 - 1) * 100.0)) {
                ++n3;
            }
        }
        return Rnd.chance(d) ? Rnd.get(n * n3, n2 * n3) : 0;
    }

    public double getRateQuestsDrop() {
        Player player = this.getPlayer();
        double d = player == null ? 1.0 : player.getQuestDropBonusMul();
        return Config.RATE_QUESTS_DROP * d * this.getQuest().getRates().getDropRate();
    }

    public double getRateQuestsReward() {
        Player player = this.getPlayer();
        double d = player == null ? 1.0 : (double)player.getBonus().getQuestRewardRate();
        return Config.RATE_QUESTS_REWARD * d * this.getQuest().getRates().getRewardRate();
    }

    public double getRateQuestsAdenaReward() {
        Player player = this.getPlayer();
        double d = player == null ? 1.0 : (double)player.getBonus().getQuestRewardAdenaRate();
        return Config.RATE_QUESTS_ADENA_REWARD * d * this.getQuest().getRates().getRewardAdenaRate();
    }

    public double getRateQuestsRewardExp() {
        Player player = this.getPlayer();
        double d = player == null ? 1.0 : (double)player.getBonus().getQuestRewardRate();
        return Config.RATE_QUESTS_REWARD_EXP_SP * d * this.getQuest().getRates().getExpRate();
    }

    public double getRateQuestsRewardSp() {
        Player player = this.getPlayer();
        double d = player == null ? 1.0 : (double)player.getBonus().getQuestRewardRate();
        return Config.RATE_QUESTS_REWARD_EXP_SP * d * this.getQuest().getRates().getSpRate();
    }

    public boolean rollAndGive(int n, int n2, int n3, int n4, double d) {
        if (d <= 0.0 || n2 <= 0 || n3 <= 0 || n4 <= 0 || n <= 0) {
            return false;
        }
        long l = this.rollDrop(n2, n3, d);
        if (l > 0L) {
            long l2 = this.getQuestItemsCount(n);
            if (l2 + l > (long)n4) {
                l = (long)n4 - l2;
            }
            if (l > 0L) {
                this.giveItems(n, l, false);
                if (l + l2 < (long)n4) {
                    this.playSound("ItemSound.quest_itemget");
                } else {
                    this.playSound("ItemSound.quest_middle");
                    return true;
                }
            }
        }
        return false;
    }

    public void rollAndGive(int n, int n2, int n3, double d) {
        if (d <= 0.0 || n2 <= 0 || n3 <= 0 || n <= 0) {
            return;
        }
        int n4 = this.rollDrop(n2, n3, d);
        if (n4 > 0) {
            this.giveItems(n, n4, false);
            this.playSound("ItemSound.quest_itemget");
        }
    }

    public boolean rollAndGive(int n, int n2, double d) {
        if (d <= 0.0 || n2 <= 0 || n <= 0) {
            return false;
        }
        int n3 = this.rollDrop(n2, d);
        if (n3 > 0) {
            this.giveItems(n, n3, false);
            this.playSound("ItemSound.quest_itemget");
            return true;
        }
        return false;
    }

    public boolean isCompleted() {
        return this.getState() == 3;
    }

    public boolean isStarted() {
        return this.getState() == 2;
    }

    public boolean isCreated() {
        return this.getState() == 1;
    }

    public void killNpcByObjectId(int n) {
        NpcInstance npcInstance = GameObjectsStorage.getNpc(n);
        if (npcInstance != null) {
            npcInstance.doDie(null);
        } else {
            cw.warn("Attemp to kill object that is not npc in quest " + this.getQuest().getQuestIntId());
        }
    }

    public String set(String string, String string2) {
        return this.set(string, string2, true);
    }

    public String set(String string, int n) {
        return this.set(string, String.valueOf(n), true);
    }

    public String set(String string, String string2, boolean bl) {
        if (string2 == null) {
            string2 = "";
        }
        this.bm.put(string, string2);
        if (bl) {
            Quest.updateQuestVarInDb(this, string, string2);
        }
        return string2;
    }

    public Object setState(int n) {
        Player player = this.getPlayer();
        if (player == null) {
            return null;
        }
        this._state = n;
        if (this.getQuest().isVisible() && this.isStarted()) {
            player.sendPacket((IStaticPacket)new ExShowQuestMark(this.getQuest().getQuestIntId(), this.getCond()));
        }
        Quest.updateQuestInDb(this);
        player.sendPacket((IStaticPacket)new QuestList(player));
        player.getListeners().onQuestStateChange(this);
        return n;
    }

    public Object setStateAndNotSave(int n) {
        Player player = this.getPlayer();
        if (player == null) {
            return null;
        }
        this._state = n;
        if (this.getQuest().isVisible() && this.isStarted()) {
            player.sendPacket((IStaticPacket)new ExShowQuestMark(this.getQuest().getQuestIntId(), this.getCond()));
        }
        player.sendPacket((IStaticPacket)new QuestList(player));
        return n;
    }

    public void playSound(String string) {
        Player player = this.getPlayer();
        if (player != null) {
            player.sendPacket((IStaticPacket)new PlaySound(string));
        }
    }

    public void playTutorialVoice(String string) {
        Player player = this.getPlayer();
        if (player != null) {
            player.sendPacket((IStaticPacket)new PlaySound(PlaySound.Type.VOICE, string, 0, 0, player.getLoc()));
        }
    }

    public void onTutorialClientEvent(int n) {
        Player player = this.getPlayer();
        if (player != null) {
            player.sendPacket((IStaticPacket)new TutorialEnableClientEvent(n));
        }
    }

    public void showQuestionMark(int n) {
        Player player = this.getPlayer();
        if (player != null) {
            player.sendPacket((IStaticPacket)new TutorialShowQuestionMark(n, 0));
        }
    }

    public void showTutorialHTML(String string) {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        if (player.isGM()) {
            Functions.sendDebugMessage(player, "Tutorial: quests/_255_Tutorial/" + string);
        }
        String string2 = HtmCache.getInstance().getNotNull("quests/_255_Tutorial/" + string, player);
        player.sendPacket((IStaticPacket)new TutorialShowHtml(string2));
    }

    public void startQuestTimer(String string, long l) {
        this.startQuestTimer(string, l, null);
    }

    public void startQuestTimer(String string, long l, NpcInstance npcInstance) {
        QuestTimer questTimer = new QuestTimer(string, l, npcInstance);
        questTimer.setQuestState(this);
        QuestTimer questTimer2 = this.getTimers().put(string, questTimer);
        if (questTimer2 != null) {
            questTimer2.stop();
        }
        questTimer.start();
    }

    public boolean isRunningQuestTimer(String string) {
        return this.getTimers().get(string) != null;
    }

    public boolean cancelQuestTimer(String string) {
        QuestTimer questTimer = this.removeQuestTimer(string);
        if (questTimer != null) {
            questTimer.stop();
        }
        return questTimer != null;
    }

    QuestTimer removeQuestTimer(String string) {
        QuestTimer questTimer = this.getTimers().remove(string);
        if (questTimer != null) {
            questTimer.setQuestState(null);
        }
        return questTimer;
    }

    public void pauseQuestTimers() {
        this.getQuest().pauseQuestTimers(this);
    }

    public void stopQuestTimers() {
        for (QuestTimer questTimer : this.getTimers().values()) {
            questTimer.setQuestState(null);
            questTimer.stop();
        }
        this.bn.clear();
    }

    public void resumeQuestTimers() {
        this.getQuest().resumeQuestTimers(this);
    }

    Map<String, QuestTimer> getTimers() {
        return this.bn;
    }

    public long takeItems(int n, long l) {
        Player player = this.getPlayer();
        if (player == null) {
            return 0L;
        }
        ItemInstance itemInstance = player.getInventory().getItemByItemId(n);
        if (itemInstance == null) {
            return 0L;
        }
        if (l < 0L || l > itemInstance.getCount()) {
            l = itemInstance.getCount();
        }
        player.getInventory().destroyItemByItemId(n, l);
        player.sendPacket((IStaticPacket)SystemMessage.removeItems(n, l));
        return l;
    }

    public long takeAllItems(int n) {
        return this.takeItems(n, -1L);
    }

    public long takeAllItems(int ... nArray) {
        long l = 0L;
        for (int n : nArray) {
            l += this.takeAllItems(n);
        }
        return l;
    }

    public long takeAllItems(Collection<Integer> collection) {
        long l = 0L;
        for (int n : collection) {
            l += this.takeAllItems(n);
        }
        return l;
    }

    public String unset(String string) {
        if (string == null) {
            return null;
        }
        String string2 = this.bm.remove(string);
        if (string2 != null) {
            Quest.deleteQuestVarInDb(this, string);
        }
        return string2;
    }

    private boolean a(Player player, int n, int n2, GameObject gameObject) {
        if (player == null) {
            return false;
        }
        if (gameObject != null && n2 > 0 && !player.isInRange(gameObject, (long)n2)) {
            return false;
        }
        QuestState questState = player.getQuestState(this.getQuest().getName());
        return questState != null && questState.getState() == n;
    }

    public List<Player> getPartyMembers(int n, int n2, GameObject gameObject) {
        ArrayList<Player> arrayList = new ArrayList<Player>();
        Party party = this.getPlayer().getParty();
        if (party == null) {
            if (this.a(this.getPlayer(), n, n2, gameObject)) {
                arrayList.add(this.getPlayer());
            }
            return arrayList;
        }
        for (Player player : party.getPartyMembers()) {
            if (!this.a(player, n, n2, gameObject)) continue;
            arrayList.add(player);
        }
        return arrayList;
    }

    public Player getRandomPartyMember(int n, int n2) {
        return this.getRandomPartyMember(n, n2, this.getPlayer());
    }

    public Player getRandomPartyMember(int n, int n2, GameObject gameObject) {
        List<Player> list = this.getPartyMembers(n, n2, gameObject);
        if (list.size() == 0) {
            return null;
        }
        return list.get(Rnd.get(list.size()));
    }

    public NpcInstance addSpawn(int n) {
        return this.addSpawn(n, this.getPlayer().getX(), this.getPlayer().getY(), this.getPlayer().getZ(), 0, 0, 0);
    }

    public NpcInstance addSpawn(int n, int n2) {
        return this.addSpawn(n, this.getPlayer().getX(), this.getPlayer().getY(), this.getPlayer().getZ(), 0, 0, n2);
    }

    public NpcInstance addSpawn(int n, int n2, int n3, int n4) {
        return this.addSpawn(n, n2, n3, n4, 0, 0, 0);
    }

    public NpcInstance addSpawn(int n, int n2, int n3, int n4, int n5) {
        return this.addSpawn(n, n2, n3, n4, 0, 0, n5);
    }

    public NpcInstance addSpawn(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        return this.getQuest().addSpawn(n, n2, n3, n4, n5, n6, n7);
    }

    public NpcInstance findTemplate(int n) {
        for (Spawner spawner : SpawnManager.getInstance().getSpawners(PeriodOfDay.ALL.name())) {
            if (spawner == null || spawner.getCurrentNpcId() != n) continue;
            return spawner.getLastSpawn();
        }
        return null;
    }

    public int calculateLevelDiffForDrop(int n, int n2) {
        if (!Config.DEEPBLUE_DROP_RULES) {
            return 0;
        }
        return Math.max(n2 - n - Config.DEEPBLUE_DROP_MAXDIFF, 0);
    }

    public int getCond() {
        if (this.c == null) {
            int n = this.getInt(VAR_COND);
            if ((n & Integer.MIN_VALUE) != 0) {
                n &= Integer.MAX_VALUE;
                for (int i = 1; i < 32; ++i) {
                    if ((n >>= 1) != 0) continue;
                    n = i;
                    break;
                }
            }
            this.c = n;
        }
        return this.c;
    }

    public String setCond(int n) {
        return this.setCond(n, true);
    }

    public String setCond(int n, boolean bl) {
        Player player;
        if (n == this.getCond()) {
            return String.valueOf(n);
        }
        int n2 = this.getInt(VAR_COND);
        this.c = n;
        if ((n2 & Integer.MIN_VALUE) != 0) {
            if (n > 2) {
                n2 &= 0x80000001 | (1 << n) - 1;
                n = n2 | 1 << n - 1;
            }
        } else if (n > 2) {
            n = 0x80000001 | 1 << n - 1 | (1 << n2) - 1;
        }
        String string = String.valueOf(n);
        String string2 = this.set(VAR_COND, string, false);
        if (bl) {
            Quest.updateQuestVarInDb(this, VAR_COND, string);
        }
        if ((player = this.getPlayer()) != null) {
            player.sendPacket((IStaticPacket)new QuestList(player));
            if (n != 0 && this.getQuest().isVisible() && this.isStarted()) {
                player.sendPacket((IStaticPacket)new ExShowQuestMark(this.getQuest().getQuestIntId(), this.getCond()));
            }
        }
        return string2;
    }

    public void setRestartTime() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(11) >= 6) {
            calendar.add(5, 1);
        }
        calendar.set(11, 6);
        calendar.set(12, 30);
        this.set("restartTime", String.valueOf(calendar.getTimeInMillis()));
    }

    public boolean isNowAvailable() {
        String string = this.get("restartTime");
        if (string == null) {
            return true;
        }
        long l = Long.parseLong(string);
        return l <= System.currentTimeMillis();
    }

    public void dropItemDelay(NpcInstance npcInstance, int n, long l) {
        ThreadPoolManager.getInstance().schedule(() -> {
            Player player = this.getPlayer();
            if (player == null) {
                return;
            }
            ItemInstance itemInstance = ItemFunctions.createItem(n);
            itemInstance.setCount(l);
            itemInstance.dropToTheGround(player, npcInstance);
            Log.add("Added itemId: " + n + " count: " + l, "QuestState:giveItems(int, long, int) QuestName: " + this.getQuest().getName(), this.getPlayer());
        }, 4000L);
    }

    public class OnDeathListenerImpl
    implements OnDeathListener {
        @Override
        public void onDeath(Creature creature, Creature creature2) {
            Player player = creature.getPlayer();
            if (player == null) {
                return;
            }
            player.removeListener(this);
            QuestState.this.a.notifyDeath(creature2, creature, QuestState.this);
        }
    }

    public class PlayerOnKillListenerImpl
    implements OnKillListener {
        @Override
        public void onKill(Creature creature, Creature creature2) {
            if (!creature2.isPlayer()) {
                return;
            }
            Player player = (Player)creature;
            List<Object> list = null;
            switch (QuestState.this.a.getParty()) {
                case 0: {
                    list = Collections.singletonList(player);
                    break;
                }
                case 2: {
                    if (player.getParty() == null) {
                        list = Collections.singletonList(player);
                        break;
                    }
                    list = new ArrayList(player.getParty().getMemberCount());
                    for (Player player2 : player.getParty().getPartyMembers()) {
                        if (!player2.isInActingRange(player)) continue;
                        list.add(player2);
                    }
                    break;
                }
                default: {
                    list = Collections.emptyList();
                }
            }
            for (Player player2 : list) {
                QuestState questState = player2.getQuestState(QuestState.this.a.getClass());
                if (questState == null || questState.isCompleted()) continue;
                QuestState.this.a.notifyKill((Player)creature2, questState);
            }
        }

        @Override
        public boolean ignorePetOrSummon() {
            return true;
        }
    }
}
