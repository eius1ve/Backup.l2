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

public class TvTArena2
extends Functions
implements OnDeathListener,
OnPlayerExitListener,
OnTeleportListener,
ScriptFile {
    private static final Logger A = LoggerFactory.getLogger(TvTArena2.class);
    private static TvTTemplate a;
    private List<NpcInstance> _spawns = new ArrayList<NpcInstance>();

    public static TvTTemplate getInstance() {
        if (a == null) {
            a = new TvTArena2Impl();
        }
        return a;
    }

    public void onLoad() {
        CharListenerList.addGlobal((Listener)this);
        TvTArena2.getInstance().onLoad();
        if (this.isActive()) {
            this.spawnEventManagers();
            A.info("Loaded Event: TvT Arena 2 [state: activated]");
        } else {
            A.info("Loaded Event: TvT Arena 2 [state: deactivated]");
        }
    }

    public void onReload() {
        TvTArena2.getInstance().onReload();
        this.unSpawnEventManagers();
        a = null;
    }

    public void onShutdown() {
    }

    public void onDeath(Creature creature, Creature creature2) {
        TvTArena2.getInstance().onDeath(creature, creature2);
    }

    public void onPlayerExit(Player player) {
        TvTArena2.getInstance().onPlayerExit(player);
    }

    public void onTeleport(Player player, int n, int n2, int n3, Reflection reflection) {
        TvTArena2.getInstance().onTeleport(player);
    }

    public String DialogAppend_31391(Integer n) {
        if (n == 0) {
            Player player = this.getSelf();
            if (player.isGM()) {
                return HtmCache.getInstance().getNotNull("scripts/events/TvTArena/31391.htm", player) + HtmCache.getInstance().getNotNull("scripts/events/TvTArena/31391-4.htm", player);
            }
            return HtmCache.getInstance().getNotNull("scripts/events/TvTArena/31391.htm", player);
        }
        return "";
    }

    public void create1() {
        TvTArena2.getInstance().template_create1(this.getSelf());
    }

    public void register() {
        TvTArena2.getInstance().template_register(this.getSelf());
    }

    public void check1(String[] stringArray) {
        TvTArena2.getInstance().template_check1(this.getSelf(), this.getNpc(), stringArray);
    }

    public void register_check() {
        TvTArena2.getInstance().template_register_check(this.getSelf());
    }

    public void stop() {
        TvTArena2.getInstance().template_stop();
    }

    public void announce() {
        TvTArena2.getInstance().template_announce();
    }

    public void prepare() {
        TvTArena2.getInstance().template_prepare();
    }

    public void start() {
        TvTArena2.getInstance().template_start();
    }

    public void timeOut() {
        TvTArena2.getInstance().template_timeOut();
    }

    private boolean isActive() {
        return TvTArena2.IsActive((String)"TvT Arena 2");
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (TvTArena2.SetActive((String)"TvT Arena 2", (boolean)true)) {
            this.spawnEventManagers();
            System.out.println("Event: TvT Arena 2 started.");
            Announcements.getInstance().announceToAll("\u041d\u0430\u0447\u0430\u043b\u0441\u044f TvT Arena 2 \u044d\u0432\u0435\u043d\u0442.");
        } else {
            player.sendMessage("TvT Arena 2 Event already started.");
        }
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (TvTArena2.SetActive((String)"TvT Arena 2", (boolean)false)) {
            ServerVariables.unset((String)"TvT Arena 2");
            this.unSpawnEventManagers();
            this.stop();
            System.out.println("TvT Arena 2 Event stopped.");
            Announcements.getInstance().announceToAll("TvT Arena 2 \u044d\u0432\u0435\u043d\u0442 \u043e\u043a\u043e\u043d\u0447\u0435\u043d.");
        } else {
            player.sendMessage("TvT Arena 2 Event not started.");
        }
        this.show("admin/events/events.htm", player);
    }

    private void spawnEventManagers() {
        int[][] nArrayArray = new int[][]{{82840, 149048, -3472, 0}};
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(31391);
        for (int[] nArray : nArrayArray) {
            SimpleSpawner simpleSpawner = new SimpleSpawner(npcTemplate);
            simpleSpawner.setLocx(nArray[0]);
            simpleSpawner.setLocy(nArray[1]);
            simpleSpawner.setLocz(nArray[2]);
            simpleSpawner.setHeading(nArray[3]);
            NpcInstance npcInstance = simpleSpawner.doSpawn(true);
            npcInstance.setName("Arena 2");
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

    private static class TvTArena2Impl
    extends TvTTemplate {
        private TvTArena2Impl() {
        }

        @Override
        protected void onLoad() {
            this._managerId = 31391;
            this._className = "TvTArena2";
            this._status = 0;
            this._team1list = new CopyOnWriteArrayList();
            this._team2list = new CopyOnWriteArrayList();
            this._team1live = new CopyOnWriteArrayList();
            this._team2live = new CopyOnWriteArrayList();
            this._zoneListener = new TvTTemplate.ZoneListener(this);
            this._zone = ReflectionUtils.getZone((String)"[tvt_arena2]");
            this._zone.addListener((Listener)this._zoneListener);
            this._team1points = new ArrayList();
            this._team2points = new ArrayList();
            this._team1points.add(new Location(-77724, -47901, -11518, -11418));
            this._team1points.add(new Location(-77718, -48080, -11518, -11418));
            this._team1points.add(new Location(-77699, -48280, -11518, -11418));
            this._team1points.add(new Location(-77777, -48442, -11518, -11418));
            this._team1points.add(new Location(-77863, -48622, -11518, -11418));
            this._team1points.add(new Location(-78002, -48714, -11518, -11418));
            this._team1points.add(new Location(-78168, -48835, -11518, -11418));
            this._team1points.add(new Location(-78353, -48851, -11518, -11418));
            this._team1points.add(new Location(-78543, -48864, -11518, -11418));
            this._team1points.add(new Location(-78709, -48784, -11518, -11418));
            this._team1points.add(new Location(-78881, -48702, -11518, -11418));
            this._team1points.add(new Location(-78981, -48555, -11518, -11418));
            this._team2points.add(new Location(-79097, -48400, -11518, -11418));
            this._team2points.add(new Location(-79107, -48214, -11518, -11418));
            this._team2points.add(new Location(-79125, -48027, -11518, -11418));
            this._team2points.add(new Location(-79047, -47861, -11518, -11418));
            this._team2points.add(new Location(-78965, -47689, -11518, -11418));
            this._team2points.add(new Location(-78824, -47594, -11518, -11418));
            this._team2points.add(new Location(-78660, -47474, -11518, -11418));
            this._team2points.add(new Location(-78483, -47456, -11518, -11418));
            this._team2points.add(new Location(-78288, -47440, -11518, -11418));
            this._team2points.add(new Location(-78125, -47515, -11518, -11418));
            this._team2points.add(new Location(-77953, -47599, -11518, -11418));
            this._team2points.add(new Location(-77844, -47747, -11518, -11418));
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
