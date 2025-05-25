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

public class TvTArena1
extends Functions
implements OnDeathListener,
OnPlayerExitListener,
OnTeleportListener,
ScriptFile {
    private static final Logger z = LoggerFactory.getLogger(TvTArena1.class);
    private static TvTTemplate a;
    private List<NpcInstance> _spawns = new ArrayList<NpcInstance>();

    public static TvTTemplate getInstance() {
        if (a == null) {
            a = new TvTArena1Impl();
        }
        return a;
    }

    public void onLoad() {
        CharListenerList.addGlobal((Listener)this);
        TvTArena1.getInstance().onLoad();
        if (this.isActive()) {
            this.spawnEventManagers();
            z.info("Loaded Event: TvT Arena 1 [state: activated]");
        } else {
            z.info("Loaded Event: TvT Arena 1 [state: deactivated]");
        }
    }

    public void onReload() {
        TvTArena1.getInstance().onReload();
        this.unSpawnEventManagers();
        a = null;
    }

    public void onShutdown() {
    }

    public void onDeath(Creature creature, Creature creature2) {
        TvTArena1.getInstance().onDeath(creature, creature2);
    }

    public void onPlayerExit(Player player) {
        TvTArena1.getInstance().onPlayerExit(player);
    }

    public void onTeleport(Player player, int n, int n2, int n3, Reflection reflection) {
        TvTArena1.getInstance().onTeleport(player);
    }

    public String DialogAppend_31390(Integer n) {
        if (n == 0) {
            Player player = this.getSelf();
            if (player.isGM()) {
                return HtmCache.getInstance().getNotNull("scripts/events/TvTArena/31390.htm", player) + HtmCache.getInstance().getNotNull("scripts/events/TvTArena/31390-4.htm", player);
            }
            return HtmCache.getInstance().getNotNull("scripts/events/TvTArena/31390.htm", player);
        }
        return "";
    }

    public void create1() {
        TvTArena1.getInstance().template_create1(this.getSelf());
    }

    public void register() {
        TvTArena1.getInstance().template_register(this.getSelf());
    }

    public void check1(String[] stringArray) {
        TvTArena1.getInstance().template_check1(this.getSelf(), this.getNpc(), stringArray);
    }

    public void register_check() {
        TvTArena1.getInstance().template_register_check(this.getSelf());
    }

    public void stop() {
        TvTArena1.getInstance().template_stop();
    }

    public void announce() {
        TvTArena1.getInstance().template_announce();
    }

    public void prepare() {
        TvTArena1.getInstance().template_prepare();
    }

    public void start() {
        TvTArena1.getInstance().template_start();
    }

    public void timeOut() {
        TvTArena1.getInstance().template_timeOut();
    }

    private boolean isActive() {
        return TvTArena1.IsActive((String)"TvT Arena 1");
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (TvTArena1.SetActive((String)"TvT Arena 1", (boolean)true)) {
            this.spawnEventManagers();
            System.out.println("Event: TvT Arena 1 started.");
            Announcements.getInstance().announceToAll("\u041d\u0430\u0447\u0430\u043b\u0441\u044f TvT Arena 1 \u044d\u0432\u0435\u043d\u0442.");
        } else {
            player.sendMessage("TvT Arena 1 Event already started.");
        }
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (TvTArena1.SetActive((String)"TvT Arena 1", (boolean)false)) {
            ServerVariables.unset((String)"TvT Arena 1");
            this.unSpawnEventManagers();
            this.stop();
            System.out.println("TvT Arena 1 Event stopped.");
            Announcements.getInstance().announceToAll("TvT Arena 1 \u044d\u0432\u0435\u043d\u0442 \u043e\u043a\u043e\u043d\u0447\u0435\u043d.");
        } else {
            player.sendMessage("TvT Arena 1 Event not started.");
        }
        this.show("admin/events/events.htm", player);
    }

    private void spawnEventManagers() {
        int[][] nArrayArray = new int[][]{{82840, 149167, -3495, 0}};
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(31390);
        for (int[] nArray : nArrayArray) {
            SimpleSpawner simpleSpawner = new SimpleSpawner(npcTemplate);
            simpleSpawner.setLocx(nArray[0]);
            simpleSpawner.setLocy(nArray[1]);
            simpleSpawner.setLocz(nArray[2]);
            simpleSpawner.setHeading(nArray[3]);
            NpcInstance npcInstance = simpleSpawner.doSpawn(true);
            npcInstance.setName("Arena 1");
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

    private static class TvTArena1Impl
    extends TvTTemplate {
        private TvTArena1Impl() {
        }

        @Override
        protected void onLoad() {
            this._managerId = 31390;
            this._className = "TvTArena1";
            this._status = 0;
            this._team1list = new CopyOnWriteArrayList();
            this._team2list = new CopyOnWriteArrayList();
            this._team1live = new CopyOnWriteArrayList();
            this._team2live = new CopyOnWriteArrayList();
            this._zoneListener = new TvTTemplate.ZoneListener(this);
            this._zone = ReflectionUtils.getZone((String)"[tvt_arena1]");
            this._zone.addListener((Listener)this._zoneListener);
            this._team1points = new ArrayList();
            this._team2points = new ArrayList();
            this._team1points.add(new Location(-81806, -44865, -11418));
            this._team1points.add(new Location(-81617, -44893, -11418));
            this._team1points.add(new Location(-81440, -44945, -11418));
            this._team1points.add(new Location(-81301, -48066, -11418));
            this._team1points.add(new Location(-81168, -45208, -11418));
            this._team1points.add(new Location(-81114, -46379, -11418));
            this._team1points.add(new Location(-81068, -45570, -11418));
            this._team1points.add(new Location(-81114, -45728, -11418));
            this._team1points.add(new Location(-81162, -45934, -11418));
            this._team1points.add(new Location(-81280, -46045, -11418));
            this._team1points.add(new Location(-81424, -46196, -11418));
            this._team1points.add(new Location(-81578, -46238, -11418));
            this._team2points.add(new Location(-81792, -46299, -11418));
            this._team2points.add(new Location(-81959, -46247, -11418));
            this._team2points.add(new Location(-82147, -46206, -11418));
            this._team2points.add(new Location(-82256, -46093, -11418));
            this._team2points.add(new Location(-82418, -45940, -11418));
            this._team2points.add(new Location(-82455, -45779, -11418));
            this._team2points.add(new Location(-82513, -45573, -11418));
            this._team2points.add(new Location(-82464, -45499, -11418));
            this._team2points.add(new Location(-82421, -45215, -11418));
            this._team2points.add(new Location(-82308, -45106, -11418));
            this._team2points.add(new Location(-82160, -44948, -11418));
            this._team2points.add(new Location(-81978, -44904, -11418));
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
