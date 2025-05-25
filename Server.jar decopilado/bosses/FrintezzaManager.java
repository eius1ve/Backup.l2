/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.time.cron.SchedulingPattern
 *  l2.commons.time.cron.SchedulingPattern$InvalidPatternException
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.handler.admincommands.AdminCommandHandler
 *  l2.gameserver.handler.admincommands.IAdminCommandHandler
 *  l2.gameserver.handler.items.IItemHandler
 *  l2.gameserver.handler.items.ItemHandler
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.listener.CharListener
 *  l2.gameserver.listener.actor.OnCurrentHpDamageListener
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Party
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Spawner
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.instances.DoorInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.Earthquake
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillCanceled
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.NpcUtils
 *  l2.gameserver.utils.ReflectionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package bosses;

import bosses.EpicBossState;
import handler.items.SimpleItemHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.commons.time.cron.SchedulingPattern;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.handler.admincommands.AdminCommandHandler;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.handler.items.IItemHandler;
import l2.gameserver.handler.items.ItemHandler;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.listener.CharListener;
import l2.gameserver.listener.actor.OnCurrentHpDamageListener;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Party;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Spawner;
import l2.gameserver.model.Zone;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.Earthquake;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillCanceled;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.NpcUtils;
import l2.gameserver.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FrintezzaManager
implements IAdminCommandHandler,
ScriptFile {
    private static final Logger e = LoggerFactory.getLogger(FrintezzaManager.class);
    private static final FrintezzaManager a = new FrintezzaManager();
    public static final int FRINTEZZA_NPC_ID = 29045;
    EpicBossState _state;
    private static final Location[] f = new Location[]{new Location(173247, -76979, -5104, 1000), new Location(174985, -75161, -5104, 1000), new Location(173917, -76109, -5104, 1000), new Location(174037, -76321, -5104, 1000), new Location(173249, -75154, -5104, 1000)};
    private static final Location h = new Location(174259, -86550, -5088, 16440);
    static final int BREAKING_ARROW_ITEM_ID = 8192;
    static final int DEWDROP_OF_DESSTRUCTION_ID = 8556;
    static final int FRINTEZZA_SEEKER_NPC_ID = 29059;
    static final long BREAKING_ARROW_CANCEL_TIME = 60000L;
    private static final String m = "[LastImperialTomb]";
    private static final String n = "[Frintezza]";
    private static final String o = "[last_imperial_tomb_a_alarm_devices]";
    private static final String p = "[last_imperial_tomb_a_hall_keeper]";
    private static final String q = "[last_imperial_tomb_b_undeadband_players]";
    private static final String r = "[last_imperial_tomb_b_undeadband_masters]";
    private static final String s = "[last_imperial_tomb_b_undeadband_member]";
    private Skill n;
    private Zone e;
    private Zone f;
    private Zone g;
    private Zone h;
    private Zone i;
    private Zone j;
    private Zone k;
    private Zone l;
    private Zone m;
    private Zone n;
    private Zone o;
    private Zone p;
    private List<Spawner> u = new ArrayList<Spawner>();
    private List<Spawner> v = new ArrayList<Spawner>();
    private List<Spawner> w = new ArrayList<Spawner>();
    private List<Spawner> x = new ArrayList<Spawner>();
    private List<Spawner> y = new ArrayList<Spawner>();
    private List<DoorInstance> z = new ArrayList<DoorInstance>();
    private List<DoorInstance> A = new ArrayList<DoorInstance>();
    private List<DoorInstance> B = new ArrayList<DoorInstance>();
    private List<DoorInstance> C = new ArrayList<DoorInstance>();
    private DeathListener a;
    private FrintessaEnterListener a;
    private CurrentHpListener a;
    private AtomicInteger a;
    private Future<?> a;
    private static int bt = 30000;
    private static NpcLocation a = new NpcLocation(174240, -89805, -5022, 16048, 29045);
    private static NpcLocation b = new NpcLocation(174234, -88015, -5116, 48028, 29046);
    private static NpcLocation[] a = new NpcLocation[]{new NpcLocation(175880, -88696, -5104, 35048, 29048), new NpcLocation(175816, -87160, -5104, 28205, 29049), new NpcLocation(172648, -87176, -5104, 64817, 29048), new NpcLocation(172600, -88664, -5104, 57730, 29049)};
    private static NpcLocation[] b = new NpcLocation[]{new NpcLocation(175880, -88696, -5104, 35048, 29050), new NpcLocation(175816, -87160, -5104, 28205, 29051), new NpcLocation(172648, -87176, -5104, 64817, 29051), new NpcLocation(172600, -88664, -5104, 57730, 29050)};
    private NpcInstance g;
    private NpcInstance h;
    private NpcInstance i;
    private NpcInstance j;
    private NpcInstance[] a = new NpcInstance[4];
    private NpcInstance[] b;
    private Future<?> b = new NpcInstance[4];
    private Future<?> c;
    private int bu = 0;
    private static final long V = 300000L;
    private ScheduledFuture<?> o;

    public static final FrintezzaManager getInstance() {
        return a;
    }

    public EpicBossState getEpicBossState() {
        return this._state;
    }

    private void init() {
        int n;
        this._state = new EpicBossState(29045);
        this.a = new AtomicInteger(0);
        this.e = ReflectionUtils.getZone((String)m);
        this.f = ReflectionUtils.getZone((String)n);
        e.info("FrintezzaManager: State of Frintezza is " + this._state.getState() + ".");
        if (this._state.getState().equals((Object)EpicBossState.State.ALIVE)) {
            this._state.setState(EpicBossState.State.NOTSPAWN);
            this._state.update();
        } else if (this._state.getState().equals((Object)EpicBossState.State.INTERVAL) || this._state.getState().equals((Object)EpicBossState.State.DEAD)) {
            this.setIntervalEndTask();
        }
        Date date = new Date(this._state.getRespawnDate());
        e.info("FrintezzaManager: Next spawn date of Frintezza is " + date.toString() + ".");
        this.g = ReflectionUtils.getZone((String)"[25_15_frintessa_01_01]");
        this.h = ReflectionUtils.getZone((String)"[25_15_frintessa_02_01]");
        this.i = ReflectionUtils.getZone((String)"[25_15_frintessa_01_02]");
        this.j = ReflectionUtils.getZone((String)"[25_15_frintessa_02_02]");
        this.k = ReflectionUtils.getZone((String)"[25_15_frintessa_01_03]");
        this.l = ReflectionUtils.getZone((String)"[25_15_frintessa_02_03]");
        this.m = ReflectionUtils.getZone((String)"[25_15_frintessa_01_04]");
        this.n = ReflectionUtils.getZone((String)"[25_15_frintessa_02_04]");
        this.o = ReflectionUtils.getZone((String)"[25_15_frintessa_01_05]");
        this.p = ReflectionUtils.getZone((String)"[25_15_frintessa_02_05]");
        for (n = 25150042; n <= 25150043; ++n) {
            this.z.add(ReflectionUtils.getDoor((int)n));
        }
        for (n = 25150045; n <= 25150046; ++n) {
            this.A.add(ReflectionUtils.getDoor((int)n));
        }
        for (n = 25150051; n <= 25150058; ++n) {
            this.B.add(ReflectionUtils.getDoor((int)n));
        }
        for (n = 25150061; n <= 25150070; ++n) {
            this.C.add(ReflectionUtils.getDoor((int)n));
        }
        this.u.addAll(SpawnManager.getInstance().getSpawners(o));
        this.v.addAll(SpawnManager.getInstance().getSpawners(p));
        this.w.addAll(SpawnManager.getInstance().getSpawners(q));
        this.x.addAll(SpawnManager.getInstance().getSpawners(r));
        this.y.addAll(SpawnManager.getInstance().getSpawners(s));
        this.a = new DeathListener();
        this.a = new CurrentHpListener();
        this.a = new FrintessaEnterListener();
        this.n = SkillTable.getInstance().getInfo(2234, 1);
        this.f.addListener((Listener)this.a);
        ItemHandler.getInstance().registerItemHandler((IItemHandler)new FrintezzaItemHandler());
    }

    private static long a() {
        SchedulingPattern schedulingPattern = null;
        if (!Config.FWA_FIXTIMEPATTERNOFFRINTEZZA.isEmpty()) {
            long l = System.currentTimeMillis();
            try {
                schedulingPattern = new SchedulingPattern(Config.FWA_FIXTIMEPATTERNOFFRINTEZZA);
                long l2 = schedulingPattern.next(l) - l;
                return Math.max(60000L, l2);
            }
            catch (SchedulingPattern.InvalidPatternException invalidPatternException) {
                throw new RuntimeException("Invalid respawn data \"" + Config.FWA_FIXTIMEPATTERNOFFRINTEZZA + "\" in " + FrintezzaManager.class.getSimpleName(), invalidPatternException);
            }
        }
        return (long)(Config.ALT_RAID_RESPAWN_MULTIPLIER * (double)(Config.FWF_FIXINTERVALOFFRINTEZZA + Rnd.get((long)0L, (long)Config.FWF_RANDOMINTERVALOFFRINTEZZA)));
    }

    public void setIntervalEndTask() {
        if (!EpicBossState.State.INTERVAL.equals((Object)this._state.getState())) {
            this._state.setRespawnDate(FrintezzaManager.a());
            this._state.setState(EpicBossState.State.INTERVAL);
            this._state.update();
        }
        this.b = ThreadPoolManager.getInstance().schedule((Runnable)new IntervalEnd(), this._state.getInterval());
    }

    private void done() {
    }

    public boolean isInUse() {
        return this.a.get() != 0;
    }

    public boolean canEnter() {
        if (this.isInUse()) {
            return false;
        }
        return this._state.getState() == EpicBossState.State.NOTSPAWN;
    }

    public boolean tryEnter(List<Party> list) {
        try {
            if (!this.startUp()) {
                return false;
            }
            for (int i = 0; i < list.size(); ++i) {
                Party party = list.get(i);
                Location location = f[i % f.length];
                for (Player player : party.getPartyMembers()) {
                    this.preparePlayableToEnter((Playable)player);
                    Location location2 = Location.findPointToStay((Location)location, (int)256, (int)player.getGeoIndex());
                    player.teleToLocation(location2);
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    public boolean startUp() {
        try {
            if (this.a.compareAndSet(0, 1)) {
                this.j();
                SpawnManager.getInstance().despawn("[frintezza_teleporter]");
                this.q();
                this.b(this.B);
                this.b(this.z);
                this.b(this.u, (CharListener)this.a);
                this.e(this.u);
                this.e(this.v);
                this.b(this.w, (CharListener)this.a);
                this.e(this.w);
                this.b(this.x, (CharListener)this.a);
                this.e(this.x);
                this.e(this.y);
                this.c(this.u);
                this.d(this.u);
                this.a(this.u, (CharListener)this.a);
                this.c(this.v);
                this.a = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new TombTimeoutTask()), 5000L);
                return true;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private void q() {
        int n;
        if (this.g != null) {
            this.g.deleteMe();
            this.g = null;
        }
        if (this.h != null) {
            this.h.deleteMe();
            this.h = null;
        }
        if (this.i != null) {
            this.i.deleteMe();
            this.i = null;
        }
        if (this.j != null) {
            this.j.deleteMe();
            this.j = null;
        }
        for (n = 0; n < this.a.length; ++n) {
            if (this.a[n] == null) continue;
            this.a[n].deleteMe();
            this.a[n] = null;
        }
        for (n = 0; n < this.b.length; ++n) {
            if (this.b[n] == null) continue;
            this.b[n].deleteMe();
            this.b[n] = null;
        }
        for (Creature creature : this.e.getObjects()) {
            if (creature == null || !(creature instanceof NpcInstance)) continue;
            creature.deleteMe();
        }
        if (this.o != null) {
            this.o.cancel(true);
            this.o = null;
        }
        if (this.c != null) {
            this.c.cancel(true);
            this.c = null;
        }
        this.v();
    }

    public boolean cleanUp() {
        if (this.a.get() != 0) {
            this.j();
            this.b(this.u, (CharListener)this.a);
            this.e(this.u);
            this.e(this.v);
            this.b(this.w, (CharListener)this.a);
            this.e(this.w);
            this.b(this.x, (CharListener)this.a);
            this.e(this.x);
            this.e(this.y);
            this.b(this.B);
            this.b(this.z);
            this.b(this.C);
            this.b(this.A);
            this.q();
            if (this.b != null) {
                this.b.cancel(true);
                this.b = null;
            }
            if (this._state.getState() == EpicBossState.State.ALIVE || this.a.get() > 0 && this.a.get() < 5) {
                this._state.setState(EpicBossState.State.NOTSPAWN);
                this._state.update();
            } else {
                this.setIntervalEndTask();
            }
            this.a.set(0);
            return true;
        }
        return false;
    }

    private void a(List<DoorInstance> list) {
        for (DoorInstance doorInstance : list) {
            if (doorInstance == null) continue;
            doorInstance.openMe();
        }
    }

    private void b(List<DoorInstance> list) {
        for (DoorInstance doorInstance : list) {
            if (doorInstance == null) continue;
            doorInstance.closeMe();
        }
    }

    private void c(List<Spawner> list) {
        for (Spawner spawner : list) {
            spawner.init();
        }
    }

    private void d(List<Spawner> list) {
        for (Spawner spawner : list) {
            spawner.stopRespawn();
        }
    }

    private void e(List<Spawner> list) {
        for (Spawner spawner : list) {
            spawner.deleteAll();
        }
    }

    private List<NpcInstance> a(List<Spawner> list) {
        ArrayList<NpcInstance> arrayList = new ArrayList<NpcInstance>();
        for (Spawner spawner : list) {
            arrayList.addAll(spawner.getAllSpawned());
        }
        return arrayList;
    }

    private boolean a(NpcInstance npcInstance, List<Spawner> list) {
        Spawner spawner = npcInstance.getSpawn();
        for (Spawner spawner2 : list) {
            if (spawner != spawner2) continue;
            return true;
        }
        return false;
    }

    private List<NpcInstance> b(List<Spawner> list) {
        ArrayList<NpcInstance> arrayList = new ArrayList<NpcInstance>();
        for (Spawner spawner : list) {
            List list2 = spawner.getAllSpawned();
            for (NpcInstance npcInstance : list2) {
                if (npcInstance.isDead()) continue;
                arrayList.add(npcInstance);
            }
        }
        return arrayList;
    }

    private boolean a(List<Spawner> list) {
        for (Spawner spawner : list) {
            List list2 = spawner.getAllSpawned();
            for (NpcInstance npcInstance : list2) {
                if (npcInstance.isDead()) continue;
                return false;
            }
        }
        return true;
    }

    private void a(List<Spawner> list, CharListener charListener) {
        List<NpcInstance> list2 = this.a(list);
        for (NpcInstance npcInstance : list2) {
            npcInstance.addListener((Listener)charListener);
        }
    }

    private void b(List<Spawner> list, CharListener charListener) {
        List<NpcInstance> list2 = this.a(list);
        for (NpcInstance npcInstance : list2) {
            npcInstance.removeListener((Listener)charListener);
        }
    }

    private void d(NpcInstance npcInstance) {
        if (this.a(this.u)) {
            if (this.a.compareAndSet(1, 2)) {
                this.b(this.u, (CharListener)this.a);
                this.e(this.v);
                this.a(this.B);
                this.a(this.z);
                Functions.npcShoutCustomMessage((NpcInstance)npcInstance, (String)"LastImperialTomb.DeactivateTheAlarm", (Object[])new Object[0]);
                this.b(this.u, (CharListener)this.a);
                this.b(this.C);
                this.b(this.A);
                this.c(this.w);
                this.d(this.w);
                this.a(this.w, (CharListener)this.a);
                this.c(this.x);
                this.d(this.x);
                this.a(this.x, (CharListener)this.a);
                this.c(this.y);
            }
        } else {
            Functions.npcShoutCustomMessage((NpcInstance)npcInstance, (String)"LastImperialTomb.Intruders", (Object[])new Object[0]);
        }
    }

    private void r() {
        NpcInstance npcInstance;
        List<NpcInstance> list = this.b(this.x);
        if (!list.isEmpty() && (npcInstance = list.get(Rnd.get((int)list.size()))) != null) {
            Functions.npcShoutCustomMessage((NpcInstance)npcInstance, (String)String.format("LastImperialTomb.UndeadBandMasterShout%d", 1 + Rnd.get((int)3)), (Object[])new Object[0]);
        }
    }

    private void s() {
        if (this.a(this.x) && this.a.compareAndSet(2, 3)) {
            this.d(this.y);
            this.e(this.y);
            this.b(this.x, (CharListener)this.a);
            this.e(this.x);
            this.a(this.A);
        }
    }

    private void e(NpcInstance npcInstance) {
        if (this.a(this.w)) {
            this.b(this.B);
            this.b(this.z);
            this.a(this.C);
            this.r();
            this.b(this.w, (CharListener)this.a);
            this.e(this.w);
            this.r();
        }
        this.s();
    }

    private void a(NpcInstance npcInstance, Creature creature) {
        if (creature != null && creature.getPlayer() != null && this.b(this.x).size() % 2 == 0) {
            npcInstance.dropItem(creature.getPlayer(), 8192, 1L);
        }
        this.s();
    }

    private void t() {
        if (this.a.compareAndSet(3, 4)) {
            this.c = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(1)), 300000L);
        }
    }

    private void u() {
        if (this.o != null) {
            this.o.cancel(false);
        }
        if (this.h != null) {
            this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillCanceled((Creature)this.h)});
            this.o = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Music(1)), 60000L + (long)Rnd.get((int)10000));
        }
    }

    private void v() {
        this.g.setActive(false);
        this.h.setActive(false);
        this.i.setActive(false);
        this.j.setActive(false);
        this.k.setActive(false);
        this.l.setActive(false);
        this.m.setActive(false);
        this.n.setActive(false);
        this.o.setActive(false);
        this.p.setActive(false);
    }

    private NpcInstance a(NpcLocation npcLocation) {
        return NpcUtils.spawnSingle((int)npcLocation.npcId, (Location)npcLocation);
    }

    private void a(NpcInstance npcInstance, int n, int n2, int n3, int n4, int n5, int n6) {
        if (npcInstance == null) {
            return;
        }
        for (Player player : this.f.getInsidePlayers()) {
            if (player.getDistance((GameObject)npcInstance) <= 2550.0) {
                player.enterMovieMode();
                player.specialCamera((GameObject)npcInstance, n, n2, n3, n4, n5);
                continue;
            }
            player.leaveMovieMode();
        }
        if (n6 > 0 && n6 < 5) {
            npcInstance.broadcastPacket(new L2GameServerPacket[]{new SocialAction(npcInstance.getObjectId(), n6)});
        }
    }

    private void a(boolean bl) {
        this.a(this.h, bl);
        this.a(this.i, bl);
        this.a(this.j, bl);
        for (int i = 0; i < 4; ++i) {
            this.a(this.a[i], bl);
            this.a(this.b[i], bl);
        }
    }

    private void a(NpcInstance npcInstance, boolean bl) {
        if (npcInstance == null || npcInstance.isDead()) {
            return;
        }
        if (bl) {
            npcInstance.abortAttack(true, false);
            npcInstance.abortCast(true, true);
            npcInstance.setTarget(null);
            if (npcInstance.isMoving()) {
                npcInstance.stopMove();
            }
            npcInstance.block();
        } else {
            npcInstance.unblock();
        }
        npcInstance.setIsInvul(bl);
    }

    public void preparePlayableToEnter(Playable playable) {
        long l;
        long l2 = ItemFunctions.getItemCount((Playable)playable, (int)8192);
        if (l2 > 0L) {
            ItemFunctions.removeItem((Playable)playable, (int)8192, (long)l2, (boolean)true);
        }
        if ((l = ItemFunctions.getItemCount((Playable)playable, (int)8556)) > 0L) {
            ItemFunctions.removeItem((Playable)playable, (int)8556, (long)l, (boolean)true);
        }
        if (playable.getPet() != null) {
            this.preparePlayableToEnter((Playable)playable.getPet());
        }
    }

    private void j() {
        for (Player player : this.e.getInsidePlayers()) {
            player.teleToClosestTown();
        }
    }

    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        switch (commands) {
            case admin_fri_startup: {
                FrintezzaManager.getInstance().startUp();
                break;
            }
            case admin_fri_cleanup: {
                FrintezzaManager.getInstance().cleanUp();
                break;
            }
            case admin_fri_go: {
                player.teleToLocation(f[Rnd.get((int)f.length)]);
                break;
            }
            case admin_fri_devdump: {
                System.out.println("progress: " + this.a.get());
                System.out.println("room A devices: " + Arrays.deepToString(this.u.toArray(new Spawner[this.u.size()])));
                System.out.println("room A keepers: " + Arrays.deepToString(this.v.toArray(new Spawner[this.v.size()])));
                System.out.println("room B Players: " + Arrays.deepToString(this.w.toArray(new Spawner[this.w.size()])));
                System.out.println("room B masters: " + Arrays.deepToString(this.x.toArray(new Spawner[this.x.size()])));
                System.out.println("state: " + this._state.getState());
                break;
            }
            case admin_fri_musdump: {
                System.out.println("areadataHeal1: " + this.g.toString() + " | " + this.g.isActive() + " " + this.g.getInsidePlayers().size());
                System.out.println("areadataHeal2: " + this.h.toString() + " | " + this.h.isActive() + " " + this.h.getInsidePlayers().size());
                System.out.println("areadataPower1: " + this.i.toString() + " | " + this.i.isActive() + " " + this.i.getInsidePlayers().size());
                System.out.println("areadataPower2: " + this.j.toString() + " | " + this.j.isActive() + " " + this.j.getInsidePlayers().size());
                System.out.println("areadataPsycho1: " + this.k.toString() + " | " + this.k.isActive() + " " + this.k.getInsidePlayers().size());
                System.out.println("areadataPsycho2: " + this.l.toString() + " | " + this.l.isActive() + " " + this.l.getInsidePlayers().size());
                System.out.println("areadataRampage1: " + this.m.toString() + " | " + this.m.isActive() + " " + this.m.getInsidePlayers().size());
                System.out.println("areadataRampage2: " + this.n.toString() + " | " + this.n.isActive() + " " + this.n.getInsidePlayers().size());
                System.out.println("areadataPlague1: " + this.o.toString() + " | " + this.o.isActive() + " " + this.o.getInsidePlayers().size());
                System.out.println("areadataPlague2: " + this.p.toString() + " | " + this.p.isActive() + " " + this.p.getInsidePlayers().size());
            }
        }
        return false;
    }

    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    public void onLoad() {
        AdminCommandHandler.getInstance().registerAdminCommandHandler((IAdminCommandHandler)FrintezzaManager.getInstance());
        FrintezzaManager.getInstance().init();
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public class CurrentHpListener
    implements OnCurrentHpDamageListener {
        public void onCurrentHpDamage(Creature creature, double d, Creature creature2, Skill skill) {
            if (creature.isDead() || creature != FrintezzaManager.this.i) {
                return;
            }
            double d2 = creature.getCurrentHp() - d;
            double d3 = creature.getMaxHp();
            switch (FrintezzaManager.this.bu) {
                case 1: {
                    if (!(d2 < 0.75 * d3)) break;
                    FrintezzaManager.this.bu = 2;
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SecondMorph(1)), 1100L);
                    break;
                }
                case 2: {
                    if (!(d2 < 0.1 * d3)) break;
                    FrintezzaManager.this.bu = 3;
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(1)), 2000L);
                }
            }
        }
    }

    private class DeathListener
    implements OnDeathListener {
        private DeathListener() {
        }

        public void onDeath(Creature creature, Creature creature2) {
            if (creature.isNpc()) {
                NpcInstance npcInstance = (NpcInstance)creature;
                if (npcInstance == FrintezzaManager.this.i) {
                    npcInstance.decayMe();
                } else if (npcInstance == FrintezzaManager.this.j) {
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Die(1)), 10L);
                } else if (FrintezzaManager.this.a(npcInstance, FrintezzaManager.this.u)) {
                    FrintezzaManager.this.d(npcInstance);
                } else if (FrintezzaManager.this.a(npcInstance, FrintezzaManager.this.w)) {
                    FrintezzaManager.this.e((NpcInstance)creature);
                } else if (FrintezzaManager.this.a(npcInstance, FrintezzaManager.this.x)) {
                    FrintezzaManager.this.a(npcInstance, creature2);
                }
            }
        }
    }

    private class FrintessaEnterListener
    implements OnZoneEnterLeaveListener {
        private FrintessaEnterListener() {
        }

        public void onZoneEnter(Zone zone, Creature creature) {
            FrintezzaManager.this.t();
        }

        public void onZoneLeave(Zone zone, Creature creature) {
            if (creature.isNpc() && (creature == FrintezzaManager.this.i || creature == FrintezzaManager.this.j)) {
                creature.teleToLocation(new Location(174240, -88020, -5112));
                ((NpcInstance)creature).getAggroList().clear(true);
                creature.setCurrentHpMp((double)creature.getMaxHp(), (double)creature.getMaxMp());
                creature.broadcastCharInfo();
            }
        }
    }

    private class FrintezzaItemHandler
    extends SimpleItemHandler {
        private FrintezzaItemHandler() {
        }

        @Override
        protected boolean useItemImpl(Player player, ItemInstance itemInstance, boolean bl) {
            int n = itemInstance.getItemId();
            if (!SimpleItemHandler.useItem(player, itemInstance, 1L)) {
                return false;
            }
            switch (n) {
                case 8192: {
                    NpcInstance npcInstance;
                    if (player.getTarget() == null || !player.getTarget().isNpc() || player.getTarget() != FrintezzaManager.getInstance().h || (npcInstance = (NpcInstance)player.getTarget()).getNpcId() != 29045) break;
                    player.callSkill(FrintezzaManager.this.n, Collections.singletonList(FrintezzaManager.getInstance().h), false);
                    player.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)player, (Creature)FrintezzaManager.getInstance().h, 2234, 1, 1000, 0L)});
                    FrintezzaManager.getInstance().u();
                    break;
                }
                case 8556: {
                    NpcInstance npcInstance;
                    if (player.getTarget() == null || !player.getTarget().isNpc() || (npcInstance = (NpcInstance)player.getTarget()).getNpcId() != 29048 && npcInstance.getNpcId() != 29049) break;
                    npcInstance.doDie((Creature)player);
                }
            }
            return false;
        }

        public int[] getItemIds() {
            return new int[]{8556, 8192};
        }
    }

    private class IntervalEnd
    implements Runnable {
        private IntervalEnd() {
        }

        @Override
        public void run() {
            FrintezzaManager.this._state.setState(EpicBossState.State.NOTSPAWN);
            FrintezzaManager.this._state.update();
        }
    }

    private class TombTimeoutTask
    extends RunnableImpl {
        private int _timeLeft = Config.FRINTEZZA_TOMB_TIMEOUT;

        public void runImpl() throws Exception {
            if (this._timeLeft > 0) {
                List list = FrintezzaManager.this.e.getInsidePlayers();
                for (Player player : list) {
                    player.sendPacket((IStaticPacket)new ExShowScreenMessage(new CustomMessage("LastImperialTomb.Remaining", player, new Object[]{this._timeLeft}).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.BOTTOM_RIGHT, false));
                }
                if (this._timeLeft > 5) {
                    this._timeLeft -= 5;
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)this), 300000L);
                } else {
                    --this._timeLeft;
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)this), 60000L);
                }
            } else {
                FrintezzaManager.this.cleanUp();
            }
        }
    }

    private class Spawn
    extends RunnableImpl {
        private int bp = 0;

        public Spawn(int n) {
            this.bp = n;
        }

        public void runImpl() throws Exception {
            try {
                switch (this.bp) {
                    case 1: {
                        FrintezzaManager.this.g = FrintezzaManager.this.a(new NpcLocation(174232, -89816, -5016, 16048, 29059));
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(2)), 1000L);
                        break;
                    }
                    case 2: {
                        FrintezzaManager.this._state.setRespawnDate(FrintezzaManager.a());
                        FrintezzaManager.this._state.setState(EpicBossState.State.ALIVE);
                        FrintezzaManager.this._state.update();
                        FrintezzaManager.this.a.compareAndSet(4, 5);
                        FrintezzaManager.this.b(FrintezzaManager.this.A);
                        FrintezzaManager.this.h = FrintezzaManager.this.a(a);
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 500, 90, 0, 6500, 8000, 0);
                        for (int i = 0; i < 4; ++i) {
                            FrintezzaManager.this.a[i] = FrintezzaManager.this.a(a[i]);
                            FrintezzaManager.this.a[i].startImmobilized();
                            FrintezzaManager.this.b[i] = FrintezzaManager.this.a(b[i]);
                        }
                        FrintezzaManager.this.a(true);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(3)), 6500L);
                        break;
                    }
                    case 3: {
                        FrintezzaManager.this.a(FrintezzaManager.this.g, 1800, 90, 8, 6500, 7000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(4)), 900L);
                        break;
                    }
                    case 4: {
                        FrintezzaManager.this.a(FrintezzaManager.this.g, 140, 90, 10, 2500, 4500, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(5)), 4000L);
                        break;
                    }
                    case 5: {
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 40, 75, -10, 0, 1000, 0);
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 40, 75, -10, 0, 12000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(6)), 1350L);
                        break;
                    }
                    case 6: {
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new SocialAction(FrintezzaManager.this.h.getObjectId(), 2)});
                        FrintezzaManager.this.o = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Music(0)), 5000L);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(7)), 7000L);
                        break;
                    }
                    case 7: {
                        FrintezzaManager.this.g.deleteMe();
                        FrintezzaManager.this.g = null;
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(8)), 1000L);
                        break;
                    }
                    case 8: {
                        FrintezzaManager.this.a(FrintezzaManager.this.b[0], 140, 0, 3, 22000, 3000, 1);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(9)), 2800L);
                        break;
                    }
                    case 9: {
                        FrintezzaManager.this.a(FrintezzaManager.this.b[1], 140, 0, 3, 22000, 3000, 1);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(10)), 2800L);
                        break;
                    }
                    case 10: {
                        FrintezzaManager.this.a(FrintezzaManager.this.b[2], 140, 180, 3, 22000, 3000, 1);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(11)), 2800L);
                        break;
                    }
                    case 11: {
                        FrintezzaManager.this.a(FrintezzaManager.this.b[3], 140, 180, 3, 22000, 3000, 1);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(12)), 3000L);
                        break;
                    }
                    case 12: {
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 240, 90, 0, 0, 1000, 0);
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 240, 90, 25, 5500, 10000, 3);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(13)), 3000L);
                        break;
                    }
                    case 13: {
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 100, 195, 35, 0, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(14)), 700L);
                        break;
                    }
                    case 14: {
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 100, 195, 35, 0, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(15)), 1300L);
                        break;
                    }
                    case 15: {
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 120, 180, 45, 1500, 10000, 0);
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5006, 1, 34000, 0L)});
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(16)), 1500L);
                        break;
                    }
                    case 16: {
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 520, 135, 45, 8000, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(17)), 7500L);
                        break;
                    }
                    case 17: {
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 1500, 110, 25, 10000, 13000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(18)), 9500L);
                        break;
                    }
                    case 18: {
                        FrintezzaManager.this.i = FrintezzaManager.this.a(b);
                        FrintezzaManager.this.a(FrintezzaManager.this.i, true);
                        FrintezzaManager.this.i.addListener((Listener)FrintezzaManager.this.a);
                        FrintezzaManager.this.i.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.i, (Creature)FrintezzaManager.this.i, 5016, 1, 3000, 0L)});
                        FrintezzaManager.this.f.broadcastPacket((L2GameServerPacket)new Earthquake(FrintezzaManager.this.i.getLoc(), 50, 6), false);
                        FrintezzaManager.this.a(FrintezzaManager.this.i, 1000, 160, 20, 6000, 6000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(19)), 5500L);
                        break;
                    }
                    case 19: {
                        FrintezzaManager.this.a(FrintezzaManager.this.i, 800, 160, 5, 1000, 10000, 2);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(20)), 2100L);
                        break;
                    }
                    case 20: {
                        FrintezzaManager.this.a(FrintezzaManager.this.i, 300, 60, 8, 0, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(21)), 2000L);
                        break;
                    }
                    case 21: {
                        FrintezzaManager.this.a(FrintezzaManager.this.i, 1000, 90, 10, 3000, 5000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(22)), 3000L);
                        break;
                    }
                    case 22: {
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(23)), 2000L);
                        for (Player player : FrintezzaManager.this.f.getInsidePlayers()) {
                            player.leaveMovieMode();
                        }
                        break;
                    }
                    case 23: {
                        FrintezzaManager.this.a(false);
                        FrintezzaManager.this.bu = 1;
                    }
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private class Music
    extends RunnableImpl {
        private int bv;

        private Music(int n) {
            this.bv = n;
        }

        public void runImpl() throws Exception {
            if (FrintezzaManager.this.h == null) {
                return;
            }
            FrintezzaManager.this.v();
            if (!FrintezzaManager.this.h.isBlocked()) {
                switch (this.bv) {
                    case 1: {
                        String string = "Requiem of Hatred";
                        FrintezzaManager.this.g.setActive(true);
                        FrintezzaManager.this.h.setActive(true);
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new ExShowScreenMessage(string, 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, 1, -1, true)});
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5007, 1, bt, 0L)});
                        break;
                    }
                    case 2: {
                        String string = "Frenetic Toccata";
                        FrintezzaManager.this.m.setActive(true);
                        FrintezzaManager.this.n.setActive(true);
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new ExShowScreenMessage(string, 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, 1, -1, true)});
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5007, 2, bt, 0L)});
                        break;
                    }
                    case 3: {
                        String string = "Fugue of Jubilation";
                        FrintezzaManager.this.i.setActive(true);
                        FrintezzaManager.this.j.setActive(true);
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new ExShowScreenMessage(string, 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, 1, -1, true)});
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5007, 3, bt, 0L)});
                        break;
                    }
                    case 4: {
                        String string = "Mournful Chorale Prelude";
                        FrintezzaManager.this.o.setActive(true);
                        FrintezzaManager.this.p.setActive(true);
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new ExShowScreenMessage(string, 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, 1, -1, true)});
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5007, 4, bt, 0L)});
                        break;
                    }
                    case 5: {
                        String string = "Hypnotic Mazurka";
                        FrintezzaManager.this.k.setActive(true);
                        FrintezzaManager.this.l.setActive(true);
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new ExShowScreenMessage(string, 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, 1, -1, true)});
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5007, 5, bt, 0L)});
                        break;
                    }
                    default: {
                        String string = "Rondo of Solitude";
                        if (FrintezzaManager.this.h != null) {
                            FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5006, 1, bt, 0L)});
                        }
                        FrintezzaManager.this.v();
                    }
                }
            }
            FrintezzaManager.this.o = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Music(this.f())), (long)(bt + Rnd.get((int)10000)));
        }

        private List<Creature> a(int n) {
            ArrayList<Creature> arrayList = new ArrayList<Creature>();
            if (n < 4) {
                if (FrintezzaManager.this.i != null && !FrintezzaManager.this.i.isDead()) {
                    arrayList.add((Creature)FrintezzaManager.this.i);
                }
                if (FrintezzaManager.this.j != null && !FrintezzaManager.this.j.isDead()) {
                    arrayList.add((Creature)FrintezzaManager.this.j);
                }
                for (int i = 0; i < 4; ++i) {
                    if (FrintezzaManager.this.a[i] != null && !FrintezzaManager.this.a[i].isDead() && FrintezzaManager.this.a[i] != null) {
                        arrayList.add((Creature)FrintezzaManager.this.a[i]);
                    }
                    if (FrintezzaManager.this.b[i] == null || FrintezzaManager.this.b[i].isDead()) continue;
                    arrayList.add((Creature)FrintezzaManager.this.b[i]);
                }
            } else {
                for (Player player : FrintezzaManager.this.f.getInsidePlayers()) {
                    if (player.isDead()) continue;
                    arrayList.add((Creature)player);
                }
            }
            return arrayList;
        }

        private int f() {
            if (this.d()) {
                return 1;
            }
            return 1 + Rnd.get((int)5);
        }

        private boolean d() {
            if (!Rnd.chance((int)40)) {
                return false;
            }
            if (FrintezzaManager.this.i != null && !FrintezzaManager.this.i.isAlikeDead() && FrintezzaManager.this.i.getCurrentHp() < (double)(FrintezzaManager.this.i.getMaxHp() * 2 / 3)) {
                return true;
            }
            if (FrintezzaManager.this.j != null && !FrintezzaManager.this.j.isAlikeDead() && FrintezzaManager.this.j.getCurrentHp() < (double)(FrintezzaManager.this.j.getMaxHp() * 2 / 3)) {
                return true;
            }
            for (int i = 0; i < 4; ++i) {
                if (FrintezzaManager.this.a[i] != null && !FrintezzaManager.this.a[i].isDead() && FrintezzaManager.this.a[i].getCurrentHp() < (double)(FrintezzaManager.this.a[i].getMaxHp() / 3)) {
                    return true;
                }
                if (FrintezzaManager.this.b[i] == null || FrintezzaManager.this.b[i].isDead() || !(FrintezzaManager.this.b[i].getCurrentHp() < (double)(FrintezzaManager.this.b[i].getMaxHp() / 3))) continue;
                return true;
            }
            return false;
        }
    }

    public static class NpcLocation
    extends Location {
        public int npcId;

        public NpcLocation() {
        }

        public NpcLocation(int n, int n2, int n3, int n4, int n5) {
            super(n, n2, n3, n4);
            this.npcId = n5;
        }
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_fri_startup = new Commands();
        public static final /* enum */ Commands admin_fri_cleanup = new Commands();
        public static final /* enum */ Commands admin_fri_go = new Commands();
        public static final /* enum */ Commands admin_fri_devdump = new Commands();
        public static final /* enum */ Commands admin_fri_musdump = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_fri_startup, admin_fri_cleanup, admin_fri_go, admin_fri_devdump, admin_fri_musdump};
        }

        static {
            a = Commands.a();
        }
    }

    private class Die
    extends RunnableImpl {
        private int bp = 0;

        public Die(int n) {
            this.bp = n;
        }

        public void runImpl() throws Exception {
            try {
                switch (this.bp) {
                    case 1: {
                        FrintezzaManager.this.a(true);
                        int n = Math.abs((FrintezzaManager.this.j.getHeading() < 32768 ? 180 : 540) - (int)((double)FrintezzaManager.this.j.getHeading() / 182.044444444));
                        FrintezzaManager.this.a(FrintezzaManager.this.j, 300, n - 180, 5, 0, 7000, 0);
                        FrintezzaManager.this.a(FrintezzaManager.this.j, 200, n, 85, 4000, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Die(2)), 7500L);
                        break;
                    }
                    case 2: {
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 100, 120, 5, 0, 7000, 0);
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 100, 90, 5, 5000, 15000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Die(3)), 6000L);
                        break;
                    }
                    case 3: {
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 900, 90, 25, 7000, 10000, 0);
                        FrintezzaManager.this.h.doDie((Creature)FrintezzaManager.this.h);
                        FrintezzaManager.this.h = null;
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Die(4)), 7000L);
                        break;
                    }
                    case 4: {
                        FrintezzaManager.this.v();
                        for (Player player : FrintezzaManager.this.f.getInsidePlayers()) {
                            player.leaveMovieMode();
                        }
                        SpawnManager.getInstance().spawn("[frintezza_teleporter]");
                        FrintezzaManager.this._state.setState(EpicBossState.State.DEAD);
                        FrintezzaManager.this._state.update();
                    }
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private class ThirdMorph
    extends RunnableImpl {
        private int bp = 0;
        private int bw = 0;

        public ThirdMorph(int n) {
            this.bp = n;
        }

        public void runImpl() throws Exception {
            try {
                switch (this.bp) {
                    case 1: {
                        this.bw = Math.abs((FrintezzaManager.this.i.getHeading() < 32768 ? 180 : 540) - (int)((double)FrintezzaManager.this.i.getHeading() / 182.044444444));
                        for (Player player : FrintezzaManager.this.f.getInsidePlayers()) {
                            player.enterMovieMode();
                        }
                        FrintezzaManager.this.a(true);
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillCanceled((Creature)FrintezzaManager.this.h)});
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new SocialAction(FrintezzaManager.this.h.getObjectId(), 4)});
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(2)), 100L);
                        break;
                    }
                    case 2: {
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 250, 120, 15, 0, 1000, 0);
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 250, 120, 15, 0, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(3)), 6500L);
                        break;
                    }
                    case 3: {
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5006, 1, 34000, 0L)});
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 500, 70, 15, 3000, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(4)), 3000L);
                        break;
                    }
                    case 4: {
                        FrintezzaManager.this.a(FrintezzaManager.this.h, 2500, 90, 12, 6000, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(5)), 3000L);
                        break;
                    }
                    case 5: {
                        FrintezzaManager.this.a(FrintezzaManager.this.i, 250, this.bw, 12, 0, 1000, 0);
                        FrintezzaManager.this.a(FrintezzaManager.this.i, 250, this.bw, 12, 0, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(6)), 500L);
                        break;
                    }
                    case 6: {
                        FrintezzaManager.this.i.doDie((Creature)FrintezzaManager.this.i);
                        FrintezzaManager.this.a(FrintezzaManager.this.i, 450, this.bw, 14, 8000, 8000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(7)), 6250L);
                        break;
                    }
                    case 7: {
                        NpcLocation npcLocation = new NpcLocation();
                        npcLocation.set(FrintezzaManager.this.i.getLoc());
                        npcLocation.npcId = 29047;
                        FrintezzaManager.this.i.deleteMe();
                        FrintezzaManager.this.i = null;
                        FrintezzaManager.this.j = FrintezzaManager.this.a(npcLocation);
                        FrintezzaManager.this.j.addListener((Listener)FrintezzaManager.this.a);
                        FrintezzaManager.this.a(FrintezzaManager.this.j, true);
                        FrintezzaManager.this.a(FrintezzaManager.this.j, 450, this.bw, 12, 500, 14000, 2);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(9)), 5000L);
                        break;
                    }
                    case 9: {
                        FrintezzaManager.this.a(false);
                        for (Player player : FrintezzaManager.this.f.getInsidePlayers()) {
                            player.leaveMovieMode();
                        }
                        Skill skill = SkillTable.getInstance().getInfo(5017, 1);
                        skill.getEffects((Creature)FrintezzaManager.this.j, (Creature)FrintezzaManager.this.j, false, false);
                    }
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private class SecondMorph
    extends RunnableImpl {
        private int bp = 0;

        public SecondMorph(int n) {
            this.bp = n;
        }

        public void runImpl() throws Exception {
            try {
                switch (this.bp) {
                    case 1: {
                        int n = Math.abs((FrintezzaManager.this.i.getHeading() < 32768 ? 180 : 540) - (int)((double)FrintezzaManager.this.i.getHeading() / 182.044444444));
                        for (Player player : FrintezzaManager.this.f.getInsidePlayers()) {
                            player.enterMovieMode();
                        }
                        FrintezzaManager.this.a(true);
                        FrintezzaManager.this.a(FrintezzaManager.this.i, 500, n, 5, 500, 15000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SecondMorph(2)), 2000L);
                        break;
                    }
                    case 2: {
                        FrintezzaManager.this.i.broadcastPacket(new L2GameServerPacket[]{new SocialAction(FrintezzaManager.this.i.getObjectId(), 1)});
                        FrintezzaManager.this.i.setCurrentHp((double)(FrintezzaManager.this.i.getMaxHp() * 3 / 4), false);
                        FrintezzaManager.this.i.setRHandId(7903);
                        FrintezzaManager.this.i.broadcastCharInfo();
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SecondMorph(3)), 5500L);
                        break;
                    }
                    case 3: {
                        FrintezzaManager.this.i.broadcastPacket(new L2GameServerPacket[]{new SocialAction(FrintezzaManager.this.i.getObjectId(), 4)});
                        FrintezzaManager.this.a(false);
                        Skill skill = SkillTable.getInstance().getInfo(5017, 1);
                        skill.getEffects((Creature)FrintezzaManager.this.i, (Creature)FrintezzaManager.this.i, false, false);
                        for (Player player : FrintezzaManager.this.f.getInsidePlayers()) {
                            player.leaveMovieMode();
                        }
                        break;
                    }
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
