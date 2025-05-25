/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CharacterAI
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.handler.bypass.INpcHtmlAppendHandler
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Location
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.Halloween;

import events.Halloween.PumpkinGhostAI;
import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.handler.bypass.INpcHtmlAppendHandler;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Halloween
extends Functions
implements INpcHtmlAppendHandler,
ScriptFile {
    private static final Logger p = LoggerFactory.getLogger(Halloween.class);
    private static final String G = "HalloweenEvent";
    private static final int[][] c = new int[][]{{81945, 148597, -3472, 600, 15}, {147456, 27480, -2229, 500, 12}, {82610, 55643, -1550, 200, 6}, {18650, 145436, -3153, 350, 9}, {111389, 220161, -3700, 420, 10}, {-14225, 123540, -3121, 200, 7}, {147723, -56388, -2807, 150, 5}, {87356, -141767, -1344, 220, 7}};
    private static final String[] q = new String[]{"Hot Springs:147607,-114850,-2002", "Rainbow Springs Chateau:152587,-126642,-2315", "Forge of the Gods:167693,-113024,-2844", "Wall of Argos:176338,-49702,-3334", "Swamp of Screams:94637,-60140,-2475", "Restless Forest:65725,-48176,-2823", "Valley of Saints:66767,-73106,-3718", "Windtail Waterfall:41303,-92076,-3703", "Cursed Village:57129,-41255,-3177", "Stakato Nest:88413,-43714,-2193", "Beast Farm:44735,-87574,-2578", "Grave Robber Hideout:46146,-106761,-1516", "Crypts of Disgrace:43242,-120040,-3408", "Den of Evil:67288,-112072,-2176", "Archaic Laboratory:92168,-115144,-3344", "Plunderous Plains:127736,-149416,-3736", "Brigand Stronghold:124520,-161496,-1168", "Deamon Fortress:100006,-52612,-673", "Borderland Fortress:155951,-70319,-2804", "Lost Nest:24044,-10452,-2589", "Primeval Plains Waterfall:6810,-12014,-3674", "Mimir's Forest:-82051,51084,-3339", "Chromatic Highlands:154735,152587,-3684"};
    private static final String H = "<br1>[scripts_events.Halloween.Halloween:show|\"Halloween Event\"]<br1>";
    private static final String I = "<br1>[scripts_events.Halloween.Halloween:show|\"\u0418\u0432\u0435\u043d\u0442 \u0425\u044d\u043b\u043b\u043e\u0443\u0438\u043d\"]<br1>";
    private static boolean T = false;
    private static ScheduledFuture<?> z;
    private static ArrayList<NpcInstance> a;
    private static NpcInstance q;

    private static boolean isActive() {
        return Halloween.IsActive((String)G);
    }

    public static void OnPlayerEnter(Player player) {
        if (T) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.Halloween.EventActive", null);
        }
    }

    public static void SpawnGhosts(int n) {
        ArrayList<Location> arrayList = new ArrayList<Location>();
        double d = Math.PI * 2 / (double)c[n][4];
        for (int i = 0; i < c[n][4]; ++i) {
            double d2 = d * (double)i;
            arrayList.add(new Location(c[n][0] + (int)((double)c[n][3] * Math.cos(d2)), c[n][1] + (int)((double)c[n][3] * Math.sin(d2)), c[n][2]));
        }
        Location[] locationArray = arrayList.toArray(new Location[arrayList.size()]);
        arrayList.clear();
        for (int i = 0; i < locationArray.length; ++i) {
            NpcInstance npcInstance = NpcHolder.getInstance().getTemplate(Config.EVENT_PUMPKIN_GHOST_ID).getNewInstance();
            npcInstance.setAI((CharacterAI)new PumpkinGhostAI(npcInstance, locationArray, i, Config.EVENT_PUMPKIN_DROP_CHANCE, Config.EVENT_PUMPKIN_DROP_ITEMS));
            npcInstance.setFlying(false);
            npcInstance.setTargetable(false);
            npcInstance.setShowName(false);
            npcInstance.spawnMe(locationArray[i]);
            npcInstance.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            a.add(npcInstance);
        }
        Announcements.getInstance().announceByCustomMessage("scripts.events.Halloween.PumpkinGhost.spawn." + n, null);
    }

    public static void DespawnGhost() {
        if (a == null || a.isEmpty()) {
            return;
        }
        for (NpcInstance npcInstance : a) {
            npcInstance.deleteMe();
        }
        a.clear();
    }

    public static void RunEvent() {
        Halloween.SkooldieDespawn();
        Halloween.DespawnGhost();
        Halloween.SpawnGhosts(Rnd.get((int)c.length));
        Halloween.executeTask((String)"events.Halloween.Halloween", (String)"DespawnGhost", (Object[])new Object[0], (long)Config.EVENT_PUMPKIN_GHOST_SHOW_TIME);
        Halloween.executeTask((String)"events.Halloween.Halloween", (String)"SkooldieSpawn", (Object[])new Object[0], (long)(Config.EVENT_PUMPKIN_GHOST_SHOW_TIME - 30000));
        Halloween.executeTask((String)"events.Halloween.Halloween", (String)"SkooldieDespawn", (Object[])new Object[0], (long)(Config.EVENT_PUMPKIN_GHOST_SHOW_TIME + Config.EVENT_SKOOLDIE_TIME));
    }

    public static void SkooldieSpawn() {
        if (q != null) {
            q.deleteMe();
        }
        int n = Rnd.get((int)q.length);
        String[] stringArray = q[n].split(":");
        q = Functions.spawn((Location)Location.parseLoc((String)stringArray[1]), (int)Config.EVENT_SKOOLDIE_REWARDER[0]);
        Announcements.getInstance().announceByCustomMessage("scripts.events.Halloween.SkooldieSpawned", new String[]{stringArray[0]});
    }

    public static void SkooldieDespawn() {
        if (q != null) {
            q.deleteMe();
        }
        q = null;
    }

    public static void start() {
        Halloween.stop();
        z = ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)new EventRunner(), 120000L, (long)Config.EVENT_PUMPKIN_DELAY);
    }

    public static void stop() {
        if (z != null) {
            z.cancel(true);
            z = null;
        }
        Halloween.DespawnGhost();
        Halloween.SkooldieDespawn();
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (Halloween.SetActive((String)G, (boolean)true)) {
            Halloween.start();
            p.info("Event: 'HalloweenEvent' started.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.Halloween.AnnounceEventStarted", null);
        } else {
            player.sendMessage("Event 'HalloweenEvent' already started.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        Halloween.DespawnGhost();
        Halloween.SkooldieDespawn();
        if (Halloween.SetActive((String)G, (boolean)false)) {
            Halloween.stop();
            p.info("Event: 'HalloweenEvent' stopped.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.Halloween.AnnounceEventStoped", null);
        } else {
            player.sendMessage("Event 'HalloweenEvent' not started.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    public int[] getNpcIds() {
        return Config.EVENT_SKOOLDIE_REWARDER;
    }

    public String getAppend(Player player, int n, int n2) {
        Halloween halloween = new Halloween();
        halloween.self = player.getRef();
        return halloween.getHtmlAppends(n2);
    }

    public String getHtmlAppends(Integer n) {
        if (!T || n != 0) {
            return "";
        }
        Player player = this.getSelf();
        if (player == null) {
            return "";
        }
        return player.isLangRus() ? I : H;
    }

    public void show() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        this.show("scripts/events/halloween/exchange.htm", player);
    }

    public void exchange() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        long l = Functions.getItemCount((Playable)player, (int)Config.EVENT_HALLOWEEN_CANDY);
        if (l > 0L) {
            Functions.removeItem((Playable)player, (int)Config.EVENT_HALLOWEEN_CANDY, (long)Config.EVENT_HALLOWEEN_CANDY_ITEM_COUNT_NEEDED);
            Functions.addItem((Playable)player, (int)Config.EVENT_HALLOWEEN_TOY_CHEST, (long)Config.EVENT_HALLOWEEN_TOY_CHEST_REWARD_AMOUNT);
            Announcements.getInstance().announceByCustomMessage("scripts.events.Halloween.SkooldieFind", new String[]{player.getName()});
            Halloween.SkooldieDespawn();
            Halloween.DespawnGhost();
        } else {
            this.show("scripts/events/halloween/noitem.htm", player);
        }
    }

    public void onLoad() {
        if (Halloween.isActive()) {
            T = true;
            Halloween.start();
            p.info("Loaded Event: 'HalloweenEvent' [state: activated]");
        } else {
            p.info("Loaded Event: 'HalloweenEvent' [state: deactivated]");
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    static {
        a = new ArrayList();
        q = null;
    }

    public static class EventRunner
    implements Runnable {
        @Override
        public void run() {
            Halloween.RunEvent();
        }
    }
}
