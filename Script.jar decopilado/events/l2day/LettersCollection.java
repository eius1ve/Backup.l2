/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.handler.bypass.INpcHtmlAppendHandler
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.reward.RewardData
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.npc.NpcTemplate
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.l2day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import l2.commons.listener.Listener;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.handler.bypass.INpcHtmlAppendHandler;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.reward.RewardData;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.npc.NpcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class LettersCollection
extends Functions
implements INpcHtmlAppendHandler,
OnDeathListener,
OnPlayerEnterListener,
ScriptFile {
    private static final Logger E = LoggerFactory.getLogger(LettersCollection.class);
    private static boolean T;
    private static final String am = "LettersCollection";
    private static final String an = "scripts.events.l2day.AnnounceEventStarted";
    private static final String ao = "scripts.events.l2day.AnnounceEventStoped";
    private static final String ap = "[event_letter_collection_spawn]";
    protected static Map<String, Integer[][]> _words;
    protected static Map<String, RewardData[]> _rewards;
    protected static List<Integer[]> lettersDrop;

    public void onLoad() {
        CharListenerList.addGlobal((Listener)this);
        if (LettersCollection.isActive()) {
            this.loadWords();
            this.loadRewards();
            this.loadLettersDrop();
            T = true;
            this.spawnEventManagers();
            E.info("Loaded Event: LettersCollection [state: activated]");
        } else {
            E.info("Loaded Event: LettersCollection [state: deactivated]");
        }
    }

    protected static boolean isActive() {
        return LettersCollection.IsActive((String)am);
    }

    protected void spawnEventManagers() {
        SpawnManager.getInstance().spawn(ap);
    }

    protected void unSpawnEventManagers() {
        SpawnManager.getInstance().despawn(ap);
    }

    public void onReload() {
        this.unSpawnEventManagers();
    }

    public void onShutdown() {
        this.unSpawnEventManagers();
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (T && LettersCollection.simpleCheckDrop((Creature)creature, (Creature)creature2)) {
            ArrayList<Integer[]> arrayList = new ArrayList<Integer[]>(lettersDrop);
            if (arrayList.isEmpty()) {
                return;
            }
            Integer[] integerArray = (Integer[])arrayList.get(Rnd.get((int)arrayList.size()));
            if (Rnd.chance((double)((double)integerArray[1].intValue() * ((NpcTemplate)creature.getTemplate()).rateHp))) {
                ((NpcInstance)creature).dropItem(creature2.getPlayer(), integerArray[0].intValue(), 1L);
            }
        }
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (LettersCollection.SetActive((String)am, (boolean)true)) {
            this.loadWords();
            this.loadRewards();
            this.loadLettersDrop();
            this.spawnEventManagers();
            Announcements.getInstance().announceByCustomMessage(an, null);
        } else {
            player.sendMessage("Event 'LettersCollection' already started.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (LettersCollection.SetActive((String)am, (boolean)false)) {
            this.unSpawnEventManagers();
            Announcements.getInstance().announceByCustomMessage(ao, null);
        } else {
            player.sendMessage("Event 'LettersCollection' not started.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    public void exchange(String[] stringArray) {
        int n;
        int n2;
        Player player = this.getSelf();
        if (!player.isQuestContinuationPossible(true)) {
            return;
        }
        if (!NpcInstance.canBypassCheck((Player)player, (NpcInstance)player.getLastNpc())) {
            return;
        }
        String string = stringArray[0];
        Integer[][] integerArray = _words.get(string);
        if (integerArray == null) {
            player.sendMessage("Wrong word.");
            return;
        }
        for (Integer[] integerArray2 : integerArray) {
            n2 = integerArray2[0];
            n = integerArray2[1];
            if (LettersCollection.getItemCount((Playable)player, (int)n2) >= (long)n) continue;
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
            return;
        }
        for (Integer[] integerArray2 : integerArray) {
            n2 = integerArray2[0];
            n = integerArray2[1];
            LettersCollection.removeItem((Playable)player, (int)n2, (long)n, (boolean)false);
        }
        Integer[][] integerArray3 = _rewards.get(string);
        if (integerArray3 == null || integerArray3.length == 0) {
            player.sendMessage("There is no reward set for this word.");
            return;
        }
        int n3 = 0;
        for (Integer[] integerArray4 : integerArray3) {
            n3 = (int)((double)n3 + integerArray4.getChance());
        }
        int n4 = Rnd.get((int)n3);
        n3 = 0;
        for (Integer[] integerArray5 : integerArray3) {
            if ((n3 = (int)((double)n3 + integerArray5.getChance())) <= n4) continue;
            LettersCollection.addItem((Playable)player, (int)integerArray5.getItemId(), (long)Rnd.get((long)integerArray5.getMinDrop(), (long)integerArray5.getMaxDrop()));
            return;
        }
    }

    public void onPlayerEnter(Player player) {
        if (T) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player, an, null);
        }
    }

    public String getHtmlAppends(Integer n) {
        if (!T) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder("<br1>");
        for (String string : _words.keySet()) {
            stringBuilder.append("[scripts_").append(((Object)((Object)this)).getClass().getName()).append(":exchange ").append(string).append("|").append(string).append("]<br1>");
        }
        return stringBuilder.toString();
    }

    public void loadRewards() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse("data/events/l2day/l2day_rewards.xml");
            NodeList nodeList = document.getElementsByTagName("reward");
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Element element = (Element)nodeList.item(i);
                String string = element.getAttribute("word");
                ArrayList<RewardData> arrayList = new ArrayList<RewardData>();
                NodeList nodeList2 = element.getElementsByTagName("item");
                for (int j = 0; j < nodeList2.getLength(); ++j) {
                    Element element2 = (Element)nodeList2.item(j);
                    int n = Integer.parseInt(element2.getAttribute("id"));
                    int n2 = Integer.parseInt(element2.getAttribute("min"));
                    int n3 = Integer.parseInt(element2.getAttribute("max"));
                    int n4 = Integer.parseInt(element2.getAttribute("chance"));
                    arrayList.add(new RewardData(n, (long)n2, (long)n3, (double)n4));
                }
                _rewards.put(string, arrayList.toArray(new RewardData[0]));
            }
        }
        catch (Exception exception) {
            E.error("Loading error rewards.xml: ", (Throwable)exception);
        }
    }

    public void loadWords() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse("data/events/l2day/l2day_words.xml");
            NodeList nodeList = document.getElementsByTagName("word");
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Element element = (Element)nodeList.item(i);
                String string = element.getAttribute("name");
                ArrayList<Integer[]> arrayList = new ArrayList<Integer[]>();
                NodeList nodeList2 = element.getElementsByTagName("letter");
                for (int j = 0; j < nodeList2.getLength(); ++j) {
                    Element element2 = (Element)nodeList2.item(j);
                    int n = Integer.parseInt(element2.getAttribute("id"));
                    int n2 = Integer.parseInt(element2.getAttribute("quantity"));
                    arrayList.add(new Integer[]{n, n2});
                }
                _words.put(string, (Integer[][])arrayList.toArray((T[])new Integer[0][0]));
            }
        }
        catch (Exception exception) {
            E.error("Loading error words.xml: ", (Throwable)exception);
        }
    }

    public void loadLettersDrop() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse("data/events/l2day/l2day_letters_drop.xml");
            NodeList nodeList = document.getElementsByTagName("letter");
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Element element = (Element)nodeList.item(i);
                int n = Integer.parseInt(element.getAttribute("id"));
                int n2 = Integer.parseInt(element.getAttribute("chance"));
                lettersDrop.add(new Integer[]{n, n2});
            }
        }
        catch (Exception exception) {
            E.error("Loading error letters_drop.xml: ", (Throwable)exception);
        }
    }

    public int[] getNpcIds() {
        return Config.EVENT_L2DAY_LETTER_NPC_ID;
    }

    public String getAppend(Player player, int n, int n2) {
        LettersCollection lettersCollection = new LettersCollection();
        lettersCollection.self = player.getRef();
        return lettersCollection.getHtmlAppends(n2);
    }

    static {
        _words = new HashMap<String, Integer[][]>();
        _rewards = new HashMap<String, RewardData[]>();
        lettersDrop = new ArrayList<Integer[]>();
    }
}
