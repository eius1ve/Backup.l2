/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.text.PrintfFormat
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Util
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.heart;

import java.util.HashMap;
import java.util.Map;
import l2.commons.listener.Listener;
import l2.commons.text.PrintfFormat;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class heart
extends Functions
implements OnDeathListener,
OnPlayerEnterListener,
ScriptFile {
    private static final Logger D = LoggerFactory.getLogger(heart.class);
    private static boolean T = false;
    private static final Map<Integer, Integer> n = new HashMap<Integer, Integer>();
    private static String aj = "";
    private static String ak = "";
    private static final String[][] a = new String[][]{{"Rock", "\u041a\u0430\u043c\u0435\u043d\u044c"}, {"Scissors", "\u041d\u043e\u0436\u043d\u0438\u0446\u044b"}, {"Paper", "\u0411\u0443\u043c\u0430\u0433\u0430"}};
    private static final String al = "[event_heart_spawn]";
    private static final int[] I;
    private static final int[] J;
    private static final int[] K;

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (heart.SetActive((String)"heart", (boolean)true)) {
            this.spawnEventManagers();
            System.out.println("Event 'Change of Heart' started.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.ChangeofHeart.AnnounceEventStarted", null);
        } else {
            player.sendMessage("Event 'Change of Heart' already started.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (heart.SetActive((String)"heart", (boolean)false)) {
            this.unSpawnEventManagers();
            System.out.println("Event 'Change of Heart' stopped.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.ChangeofHeart.AnnounceEventStoped", null);
        } else {
            player.sendMessage("Event 'Change of Heart' not started.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    public void letsplay() {
        Player player = this.getSelf();
        if (!player.isQuestContinuationPossible(true)) {
            return;
        }
        this.n(player);
        if (this.e(player)) {
            this.show(this.a(HtmCache.getInstance().getNotNull("scripts/events/heart/hearts_01.htm", player), this.d(player)), player);
        } else {
            this.show("scripts/events/heart/hearts_00.htm", player);
        }
    }

    public void play(String[] stringArray) {
        Player player = this.getSelf();
        if (!player.isQuestContinuationPossible(true) || stringArray.length == 0) {
            return;
        }
        if (!this.e(player)) {
            if (stringArray[0].equalsIgnoreCase("Quit")) {
                this.show("scripts/events/heart/hearts_00b.htm", player);
            } else {
                this.show("scripts/events/heart/hearts_00a.htm", player);
            }
            return;
        }
        if (stringArray[0].equalsIgnoreCase("Quit")) {
            int n = this.b(player);
            this.o(player);
            this.c(player, n);
            this.show("scripts/events/heart/hearts_reward_" + n + ".htm", player);
            this.n(player);
            return;
        }
        int n = Rnd.get((int)a.length);
        int n2 = 0;
        try {
            n2 = Integer.parseInt(stringArray[0]);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
        if (n2 == n) {
            this.show(this.a(HtmCache.getInstance().getNotNull("scripts/events/heart/hearts_same.htm", player), n2, n, player), player);
            return;
        }
        if (this.a(n2, n)) {
            this.m(player);
            int n3 = this.b(player);
            if (n3 == 10) {
                this.o(player);
                this.c(player, n3);
                this.n(player);
            }
            this.show(this.a(HtmCache.getInstance().getNotNull("scripts/events/heart/hearts_level_" + n3 + ".htm", player), n2, n, player), player);
            return;
        }
        this.o(player);
        this.c(player, this.b(player) - 1);
        this.show(this.a(HtmCache.getInstance().getNotNull("scripts/events/heart/hearts_loose.htm", player), n2, n, player), player);
        this.n(player);
    }

    private void c(Player player, int n) {
        switch (n) {
            case -1: 
            case 0: {
                heart.addItem((Playable)player, (int)K[Rnd.get((int)K.length)], (long)1L);
                break;
            }
            case 1: {
                heart.addItem((Playable)player, (int)J[Rnd.get((int)J.length)], (long)10L);
                break;
            }
            case 2: {
                heart.addItem((Playable)player, (int)1538, (long)1L);
                break;
            }
            case 3: {
                heart.addItem((Playable)player, (int)3936, (long)1L);
                break;
            }
            case 4: {
                heart.addItem((Playable)player, (int)951, (long)2L);
                break;
            }
            case 5: {
                heart.addItem((Playable)player, (int)948, (long)4L);
                break;
            }
            case 6: {
                heart.addItem((Playable)player, (int)947, (long)1L);
                break;
            }
            case 7: {
                heart.addItem((Playable)player, (int)730, (long)3L);
                break;
            }
            case 8: {
                heart.addItem((Playable)player, (int)729, (long)1L);
                break;
            }
            case 9: {
                heart.addItem((Playable)player, (int)960, (long)2L);
                break;
            }
            case 10: {
                heart.addItem((Playable)player, (int)959, (long)1L);
            }
        }
    }

    private String a(String string, int n, int n2, Player player) {
        boolean bl = this.d(player);
        return this.a(string.replaceFirst("Player", player.getName()).replaceFirst("%var_payer%", a[n][bl ? 1 : 0]).replaceFirst("%var_cat%", a[n2][bl ? 1 : 0]), bl);
    }

    private boolean d(Player player) {
        return player.isLangRus();
    }

    private String a(String string, boolean bl) {
        return string.replaceFirst("%links%", bl ? ak : aj);
    }

    private boolean a(int n, int n2) {
        if (n == 0) {
            return n2 == 1;
        }
        if (n == 1) {
            return n2 == 2;
        }
        if (n == 2) {
            return n2 == 0;
        }
        return false;
    }

    private int b(Player player) {
        return n.containsKey(player.getObjectId()) ? n.get(player.getObjectId()) : 0;
    }

    private void m(Player player) {
        int n = 1;
        if (heart.n.containsKey(player.getObjectId())) {
            n = heart.n.remove(player.getObjectId()) + 1;
        }
        heart.n.put(player.getObjectId(), n);
    }

    private void n(Player player) {
        if (n.containsKey(player.getObjectId())) {
            n.remove(player.getObjectId());
        }
    }

    private void o(Player player) {
        for (int n : I) {
            heart.removeItem((Playable)player, (int)n, (long)1L, (boolean)false);
        }
    }

    private boolean e(Player player) {
        for (int n : I) {
            if (player.getInventory().getCountOf(n) >= 1L) continue;
            return false;
        }
        return true;
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (T && heart.simpleCheckDrop((Creature)creature, (Creature)creature2)) {
            ((NpcInstance)creature).dropItem(creature2.getPlayer(), I[Rnd.get((int)I.length)], Util.rollDrop((long)1L, (long)1L, (double)(Config.EVENT_CHANGE_OF_HEART_CHANCE * creature2.getPlayer().getRateItems() * ((MonsterInstance)creature).getTemplate().rateHp * 10000.0), (boolean)true));
        }
    }

    public void onPlayerEnter(Player player) {
        if (T) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.ChangeofHeart.AnnounceEventStarted", null);
        }
    }

    private static boolean isActive() {
        return heart.IsActive((String)"heart");
    }

    private void spawnEventManagers() {
        SpawnManager.getInstance().spawn(al);
    }

    private void unSpawnEventManagers() {
        SpawnManager.getInstance().despawn(al);
    }

    public void onLoad() {
        CharListenerList.addGlobal((Listener)this);
        if (heart.isActive()) {
            T = true;
            this.spawnEventManagers();
            D.info("Loaded Event: Change of Heart [state: activated]");
        } else {
            D.info("Loaded Event: Change of Heart[state: deactivated]");
        }
    }

    public void onReload() {
        this.unSpawnEventManagers();
    }

    public void onShutdown() {
        this.unSpawnEventManagers();
    }

    static {
        PrintfFormat printfFormat = new PrintfFormat("<br><a action=\"bypass -h scripts_events.heart.heart:play %d\">\"%s!\"</a>");
        for (int i = 0; i < a.length; ++i) {
            aj = aj + printfFormat.sprintf(new Object[]{i, a[i][0]});
            ak = ak + printfFormat.sprintf(new Object[]{i, a[i][1]});
        }
        I = new int[]{4209, 4210, 4211, 4212, 4213, 4214, 4215, 4216, 4217};
        J = new int[]{1374, 1375, 6036, 1539};
        K = new int[]{3926, 3927, 3928, 3929, 3930, 3931, 3932, 3933, 3934, 3935};
    }
}
