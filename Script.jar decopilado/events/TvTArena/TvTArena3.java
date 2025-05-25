/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Announcements
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.instancemanager.ServerVariables
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.listener.actor.player.OnPlayerExitListener
 *  l2.gameserver.listener.actor.player.OnTeleportListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.ReflectionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.TvTArena;

import events.TvTArena.TvTTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import l2.commons.listener.Listener;
import l2.gameserver.Announcements;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.instancemanager.ServerVariables;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.actor.player.OnPlayerExitListener;
import l2.gameserver.listener.actor.player.OnTeleportListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TvTArena3
extends Functions
implements OnDeathListener,
OnPlayerExitListener,
OnTeleportListener,
ScriptFile {
    private static final Logger B = LoggerFactory.getLogger(TvTArena3.class);
    private static TvTTemplate a;
    private List<NpcInstance> _spawns = new ArrayList<NpcInstance>();

    public static TvTTemplate getInstance() {
        if (a == null) {
            a = new TvTArena3Impl();
        }
        return a;
    }

    public void onLoad() {
        CharListenerList.addGlobal((Listener)this);
        TvTArena3.getInstance().onLoad();
        if (this.isActive()) {
            this.spawnEventManagers();
            B.info("Loaded Event: TvT Arena 3 [state: activated]");
        } else {
            B.info("Loaded Event: TvT Arena 3 [state: deactivated]");
        }
    }

    public void onReload() {
        TvTArena3.getInstance().onReload();
        this.unSpawnEventManagers();
        a = null;
    }

    public void onShutdown() {
        this.onReload();
    }

    public void onDeath(Creature creature, Creature creature2) {
        TvTArena3.getInstance().onDeath(creature, creature2);
    }

    public void onPlayerExit(Player player) {
        TvTArena3.getInstance().onPlayerExit(player);
    }

    public void onTeleport(Player player, int n, int n2, int n3, Reflection reflection) {
        TvTArena3.getInstance().onTeleport(player);
    }

    public String DialogAppend_31392(Integer n) {
        if (n == 0) {
            Player player = this.getSelf();
            if (player.isGM()) {
                return HtmCache.getInstance().getNotNull("scripts/events/TvTArena/31392.htm", player) + HtmCache.getInstance().getNotNull("scripts/events/TvTArena/31392-4.htm", player);
            }
            return HtmCache.getInstance().getNotNull("scripts/events/TvTArena/31392.htm", player);
        }
        return "";
    }

    public void create1() {
        TvTArena3.getInstance().template_create1(this.getSelf());
    }

    public void register() {
        TvTArena3.getInstance().template_register(this.getSelf());
    }

    public void check1(String[] stringArray) {
        TvTArena3.getInstance().template_check1(this.getSelf(), this.getNpc(), stringArray);
    }

    public void register_check() {
        TvTArena3.getInstance().template_register_check(this.getSelf());
    }

    public void stop() {
        TvTArena3.getInstance().template_stop();
    }

    public void announce() {
        TvTArena3.getInstance().template_announce();
    }

    public void prepare() {
        TvTArena3.getInstance().template_prepare();
    }

    public void start() {
        TvTArena3.getInstance().template_start();
    }

    public void timeOut() {
        TvTArena3.getInstance().template_timeOut();
    }

    private boolean isActive() {
        return TvTArena3.IsActive((String)"TvT Arena 3");
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (TvTArena3.SetActive((String)"TvT Arena 3", (boolean)true)) {
            this.spawnEventManagers();
            System.out.println("Event: TvT Arena 3 started.");
            Announcements.getInstance().announceToAll("\u041d\u0430\u0447\u0430\u043b\u0441\u044f TvT Arena 3 \u044d\u0432\u0435\u043d\u0442.");
        } else {
            player.sendMessage("TvT Arena 3 Event already started.");
        }
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (TvTArena3.SetActive((String)"TvT Arena 3", (boolean)false)) {
            ServerVariables.unset((String)"TvT Arena 3");
            this.unSpawnEventManagers();
            this.stop();
            System.out.println("TvT Arena 3 Event stopped.");
            Announcements.getInstance().announceToAll("TvT Arena 3 \u044d\u0432\u0435\u043d\u0442 \u043e\u043a\u043e\u043d\u0447\u0435\u043d.");
        } else {
            player.sendMessage("TvT Arena 3 Event not started.");
        }
        this.show("admin/events/events.htm", player);
    }

    private void spawnEventManagers() {
        int[][] nArrayArray = new int[][]{{82840, 148936, -3472, 0}};
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(31392);
        for (int[] nArray : nArrayArray) {
            SimpleSpawner simpleSpawner = new SimpleSpawner(npcTemplate);
            simpleSpawner.setLocx(nArray[0]);
            simpleSpawner.setLocy(nArray[1]);
            simpleSpawner.setLocz(nArray[2]);
            simpleSpawner.setHeading(nArray[3]);
            NpcInstance npcInstance = simpleSpawner.doSpawn(true);
            npcInstance.setName("Arena 3");
            npcInstance.setTitle("TvT Event");
            this._spawns.add(npcInstance);
        }
    }

    private void unSpawnEventManagers() {
        for (NpcInstance npcInstance : this._spawns) {
            npcInstance.deleteMe();
        }
        this._spawns.clear();
    }

    private static class TvTArena3Impl
    extends TvTTemplate {
        private TvTArena3Impl() {
        }

        @Override
        protected void onLoad() {
            this._managerId = 31392;
            this._className = "TvTArena3";
            this._status = 0;
            this._team1list = new CopyOnWriteArrayList();
            this._team2list = new CopyOnWriteArrayList();
            this._team1live = new CopyOnWriteArrayList();
            this._team2live = new CopyOnWriteArrayList();
            this._zoneListener = new TvTTemplate.ZoneListener(this);
            this._zone = ReflectionUtils.getZone((String)"[tvt_arena3]");
            this._zone.addListener((Listener)this._zoneListener);
            this._team1points = new ArrayList();
            this._team2points = new ArrayList();
            this._team1points.add(new Location(-79383, -52724, -11518, -11418));
            this._team1points.add(new Location(-79558, -52793, -11518, -11418));
            this._team1points.add(new Location(-79726, -52867, -11518, -11418));
            this._team1points.add(new Location(-79911, -52845, -11518, -11418));
            this._team1points.add(new Location(-80098, -52822, -11518, -11418));
            this._team1points.add(new Location(-80242, -52714, -11518, -11418));
            this._team1points.add(new Location(-80396, -52597, -11518, -11418));
            this._team1points.add(new Location(-80466, -52422, -11518, -11418));
            this._team1points.add(new Location(-80544, -52250, -11518, -11418));
            this._team1points.add(new Location(-80515, -52054, -11518, -11418));
            this._team1points.add(new Location(-80496, -51878, -11518, -11418));
            this._team1points.add(new Location(-80386, -51739, -11518, -11418));
            this._team2points.add(new Location(-80270, -51582, -11518, -11418));
            this._team2points.add(new Location(-80107, -51519, -11518, -11418));
            this._team2points.add(new Location(-79926, -51435, -11518, -11418));
            this._team2points.add(new Location(-79739, -51465, -11518, -11418));
            this._team2points.add(new Location(-79554, -51482, -11518, -11418));
            this._team2points.add(new Location(-79399, -51600, -11518, -11418));
            this._team2points.add(new Location(-79254, -51711, -11518, -11418));
            this._team2points.add(new Location(-79181, -51884, -11518, -11418));
            this._team2points.add(new Location(-79114, -52057, -11518, -11418));
            this._team2points.add(new Location(-79133, -52246, -11518, -11418));
            this._team2points.add(new Location(-79156, -52427, -11518, -11418));
            this._team2points.add(new Location(-79275, -52583, -11518, -11418));
        }

        @Override
        protected void onReload() {
            if (this._status > 0) {
                this.template_stop();
            }
            this._zone.removeListener((Listener)this._zoneListener);
        }
    }
}
