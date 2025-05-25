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
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.Log
 *  l2.gameserver.utils.ReflectionUtils
 *  l2.gameserver.utils.TimeUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package bosses;

import bosses.EpicBossState;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.commons.time.cron.SchedulingPattern;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.ReflectionUtils;
import l2.gameserver.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SailrenManager
extends Functions
implements OnDeathListener,
ScriptFile {
    private static final Logger f = LoggerFactory.getLogger(SailrenManager.class);
    private static NpcInstance k;
    private static NpcInstance l;
    private static NpcInstance m;
    private static NpcInstance n;
    private static NpcInstance f;
    private static ScheduledFuture<?> l;
    private static ScheduledFuture<?> d;
    private static ScheduledFuture<?> e;
    private static ScheduledFuture<?> f;
    private static ScheduledFuture<?> p;
    private static ScheduledFuture<?> j;
    private static final int bx = 29065;
    private static final int by = 22198;
    private static final int bz = 22199;
    private static final int bA = 22217;
    private static final int bB = 32107;
    private static EpicBossState _state;
    private static Zone _zone;
    private static Location i;
    private static final boolean Q;
    private static AtomicBoolean b;
    private static AtomicBoolean a;

    private static void j() {
        for (Player player : SailrenManager.a()) {
            player.teleToClosestTown();
        }
    }

    private static synchronized void k() {
        if (j == null && SailrenManager.c()) {
            j = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AnnihilateTask()), Config.SAILREN_CLEAR_ZONE_IF_ALL_DIE);
        }
    }

    private static List<Player> a() {
        return SailrenManager.getZone().getInsidePlayers();
    }

    private static long a() {
        SchedulingPattern schedulingPattern = null;
        if (!Config.FWA_FIXTIMEPATTERNOFSAILREN.isEmpty()) {
            long l = System.currentTimeMillis();
            try {
                schedulingPattern = new SchedulingPattern(Config.FWA_FIXTIMEPATTERNOFSAILREN);
                long l2 = schedulingPattern.next(l) - l;
                return Math.max(60000L, l2);
            }
            catch (SchedulingPattern.InvalidPatternException invalidPatternException) {
                throw new RuntimeException("Invalid respawn data \"" + Config.FWA_FIXTIMEPATTERNOFSAILREN + "\" in " + SailrenManager.class.getSimpleName(), invalidPatternException);
            }
        }
        return (long)(Config.ALT_RAID_RESPAWN_MULTIPLIER * (double)(Config.FWS_FIXINTERVALOFSAILRENSPAWN + Rnd.get((long)0L, (long)Config.FWS_RANDOMINTERVALOFSAILRENSPAWN)));
    }

    public static Zone getZone() {
        return _zone;
    }

    private void init() {
        CharListenerList.addGlobal((Listener)this);
        _state = new EpicBossState(29065);
        _zone = ReflectionUtils.getZone((String)"[sailren_epic]");
        f.info("SailrenManager: State of Sailren is " + _state.getState() + ".");
        if (!_state.getState().equals((Object)EpicBossState.State.NOTSPAWN)) {
            SailrenManager.setIntervalEndTask();
        }
        f.info("SailrenManager: Next spawn date of Sailren is " + TimeUtils.toSimpleFormat((long)_state.getRespawnDate()) + ".");
    }

    private static boolean c() {
        for (Player player : SailrenManager.a()) {
            if (player.isDead()) continue;
            return false;
        }
        return true;
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (creature.isPlayer() && _state != null && _state.getState() == EpicBossState.State.ALIVE && _zone != null && _zone.checkIfInZone(creature.getX(), creature.getY())) {
            SailrenManager.k();
        } else if (creature == k) {
            if (d != null) {
                d.cancel(false);
            }
            d = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SailrenSpawn(22199)), Config.FWS_INTERVALOFNEXTMONSTER);
        } else if (creature == l) {
            if (d != null) {
                d.cancel(false);
            }
            d = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SailrenSpawn(22217)), Config.FWS_INTERVALOFNEXTMONSTER);
        } else if (creature == m) {
            if (d != null) {
                d.cancel(false);
            }
            d = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SailrenSpawn(29065)), Config.FWS_INTERVALOFNEXTMONSTER);
        } else if (creature == n) {
            SailrenManager.e(creature2);
        }
    }

    private static void e(Creature creature) {
        if (!a.compareAndSet(false, true)) {
            return;
        }
        _state.setRespawnDate(SailrenManager.a());
        _state.setState(EpicBossState.State.INTERVAL);
        _state.update();
        SailrenManager.m();
        Log.add((String)"Sailren died", (String)"bosses");
        l = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new CubeSpawn()), 10000L);
    }

    private static void setIntervalEndTask() {
        SailrenManager.setUnspawn();
        if (_state.getState().equals((Object)EpicBossState.State.ALIVE)) {
            _state.setState(EpicBossState.State.NOTSPAWN);
            _state.update();
            return;
        }
        if (!_state.getState().equals((Object)EpicBossState.State.INTERVAL)) {
            _state.setRespawnDate(SailrenManager.a());
            _state.setState(EpicBossState.State.INTERVAL);
            _state.update();
        }
        SailrenManager.m();
    }

    private static void m() {
        if (e != null) {
            e.cancel(false);
            e = null;
        }
        e = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new IntervalEnd()), _state.getInterval());
    }

    private static void setUnspawn() {
        SailrenManager.j();
        if (k != null) {
            if (k.getSpawn() != null) {
                k.getSpawn().stopRespawn();
            }
            k.deleteMe();
            k = null;
        }
        if (l != null) {
            if (l.getSpawn() != null) {
                l.getSpawn().stopRespawn();
            }
            l.deleteMe();
            l = null;
        }
        if (m != null) {
            if (m.getSpawn() != null) {
                m.getSpawn().stopRespawn();
            }
            m.deleteMe();
            m = null;
        }
        if (n != null) {
            if (n.getSpawn() != null) {
                n.getSpawn().stopRespawn();
            }
            n.deleteMe();
            n = null;
        }
        if (f != null) {
            if (f.getSpawn() != null) {
                f.getSpawn().stopRespawn();
            }
            f.deleteMe();
            f = null;
        }
        if (l != null) {
            l.cancel(false);
            l = null;
        }
        if (d != null) {
            d.cancel(false);
            d = null;
        }
        if (e != null) {
            e.cancel(false);
            e = null;
        }
        if (f != null) {
            f.cancel(false);
            f = null;
        }
        if (p != null) {
            p.cancel(false);
            p = null;
        }
        if (j != null) {
            j.cancel(false);
            j = null;
        }
    }

    private static void n() {
        SailrenManager.setUnspawn();
        b.set(false);
        if (_state.getState().equals((Object)EpicBossState.State.ALIVE)) {
            _state.setState(EpicBossState.State.NOTSPAWN);
            _state.update();
        }
    }

    public static synchronized void setSailrenSpawnTask() {
        if (d == null) {
            d = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SailrenSpawn(22198)), Config.FWS_INTERVALOFNEXTMONSTER);
        }
    }

    public static boolean isEnableEnterToLair() {
        return _state.getState() == EpicBossState.State.NOTSPAWN;
    }

    public static CanIntoSailrenLairResult canIntoSailrenLair(Player player) {
        if (!Q && player.getParty() == null) {
            return CanIntoSailrenLairResult.Fail_NoParty;
        }
        if (b.get()) {
            return CanIntoSailrenLairResult.Fail_InUse;
        }
        if (_state.getState().equals((Object)EpicBossState.State.ALIVE) || _state.getState().equals((Object)EpicBossState.State.DEAD)) {
            return CanIntoSailrenLairResult.Fail_Awake;
        }
        if (_state.getState().equals((Object)EpicBossState.State.INTERVAL)) {
            return CanIntoSailrenLairResult.Fail_Reborn;
        }
        return CanIntoSailrenLairResult.Ok;
    }

    public static void entryToSailrenLair(Player player) {
        if (b.compareAndSet(false, true)) {
            if (player.getParty() == null) {
                player.teleToLocation(Location.findPointToStay((Location)i, (int)80, (int)player.getGeoIndex()));
            } else {
                ArrayList<Player> arrayList = new ArrayList<Player>();
                for (Player player2 : player.getParty().getPartyMembers()) {
                    if (player2 == null || player2.isDead() || !player2.isInRange((GameObject)player, 1000L)) continue;
                    arrayList.add(player2);
                }
                for (Player player2 : arrayList) {
                    player2.teleToLocation(Location.findPointToStay((Location)i, (int)80, (int)player2.getGeoIndex()));
                }
            }
        }
    }

    public void onLoad() {
        this.init();
    }

    public void onReload() {
        SailrenManager.n();
    }

    public void onShutdown() {
    }

    static {
        l = null;
        d = null;
        e = null;
        f = null;
        p = null;
        j = null;
        i = new Location(27734, -6938, -1982);
        Q = Boolean.TRUE;
        b = new AtomicBoolean(false);
        a = new AtomicBoolean(false);
    }

    private static class AnnihilateTask
    extends RunnableImpl {
        private AnnihilateTask() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void runImpl() {
            Class<SailrenManager> clazz = SailrenManager.class;
            synchronized (SailrenManager.class) {
                if (!SailrenManager.c() || _state.getState() == EpicBossState.State.INTERVAL) {
                    j = null;
                    // ** MonitorExit[var1_1] (shouldn't be in output)
                    return;
                }
                // ** MonitorExit[var1_1] (shouldn't be in output)
                SailrenManager.n();
                return;
            }
        }
    }

    private static class SailrenSpawn
    extends RunnableImpl {
        private int _npcId;
        private final Location j = new Location(27628, -6109, -1982, 44732);

        SailrenSpawn(int n) {
            this._npcId = n;
        }

        public void runImpl() throws Exception {
            if (f != null) {
                f.cancel(false);
                f = null;
            }
            switch (this._npcId) {
                case 22198: {
                    a.set(false);
                    k = Functions.spawn((Location)new Location(27852, -5536, -1983, 44732), (int)22198);
                    ((DefaultAI)k.getAI()).addTaskMove(this.j, false);
                    if (f != null) {
                        f.cancel(false);
                        f = null;
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Social(k, 2)), 6000L);
                    if (p != null) {
                        p.cancel(false);
                        p = null;
                    }
                    p = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ActivityTimeEnd()), Config.FWS_ACTIVITYTIMEOFMOBS);
                    break;
                }
                case 22199: {
                    l = Functions.spawn((Location)new Location(27852, -5536, -1983, 44732), (int)22199);
                    ((DefaultAI)l.getAI()).addTaskMove(this.j, false);
                    if (f != null) {
                        f.cancel(false);
                        f = null;
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Social(l, 2)), 6000L);
                    if (p != null) {
                        p.cancel(false);
                        p = null;
                    }
                    p = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ActivityTimeEnd()), Config.FWS_ACTIVITYTIMEOFMOBS);
                    break;
                }
                case 22217: {
                    m = Functions.spawn((Location)new Location(27852, -5536, -1983, 44732), (int)22217);
                    ((DefaultAI)m.getAI()).addTaskMove(this.j, false);
                    if (f != null) {
                        f.cancel(false);
                        f = null;
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Social(m, 2)), 6000L);
                    if (p != null) {
                        p.cancel(false);
                        p = null;
                    }
                    p = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ActivityTimeEnd()), Config.FWS_ACTIVITYTIMEOFMOBS);
                    break;
                }
                case 29065: {
                    n = Functions.spawn((Location)new Location(27810, -5655, -1983, 44732), (int)29065);
                    _state.setRespawnDate(SailrenManager.a() + Config.FWS_ACTIVITYTIMEOFMOBS);
                    _state.setState(EpicBossState.State.ALIVE);
                    _state.update();
                    n.setRunning();
                    ((DefaultAI)n.getAI()).addTaskMove(this.j, false);
                    if (f != null) {
                        f.cancel(false);
                        f = null;
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Social(n, 2)), 6000L);
                    if (p != null) {
                        p.cancel(false);
                        p = null;
                    }
                    p = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ActivityTimeEnd()), Config.FWS_ACTIVITYTIMEOFMOBS);
                }
            }
        }
    }

    private static class CubeSpawn
    extends RunnableImpl {
        private CubeSpawn() {
        }

        public void runImpl() throws Exception {
            f = Functions.spawn((Location)new Location(27734, -6838, -1982, 0), (int)32107);
        }
    }

    private static class IntervalEnd
    extends RunnableImpl {
        private IntervalEnd() {
        }

        public void runImpl() throws Exception {
            SailrenManager.setUnspawn();
            b.set(false);
            _state.setState(EpicBossState.State.NOTSPAWN);
            _state.update();
        }
    }

    public static final class CanIntoSailrenLairResult
    extends Enum<CanIntoSailrenLairResult> {
        public static final /* enum */ CanIntoSailrenLairResult Ok = new CanIntoSailrenLairResult();
        public static final /* enum */ CanIntoSailrenLairResult Fail_Awake = new CanIntoSailrenLairResult();
        public static final /* enum */ CanIntoSailrenLairResult Fail_InUse = new CanIntoSailrenLairResult();
        public static final /* enum */ CanIntoSailrenLairResult Fail_Reborn = new CanIntoSailrenLairResult();
        public static final /* enum */ CanIntoSailrenLairResult Fail_NoParty = new CanIntoSailrenLairResult();
        private static final /* synthetic */ CanIntoSailrenLairResult[] a;

        public static CanIntoSailrenLairResult[] values() {
            return (CanIntoSailrenLairResult[])a.clone();
        }

        public static CanIntoSailrenLairResult valueOf(String string) {
            return Enum.valueOf(CanIntoSailrenLairResult.class, string);
        }

        private static /* synthetic */ CanIntoSailrenLairResult[] a() {
            return new CanIntoSailrenLairResult[]{Ok, Fail_Awake, Fail_InUse, Fail_Reborn, Fail_NoParty};
        }

        static {
            a = CanIntoSailrenLairResult.a();
        }
    }

    private static class Social
    extends RunnableImpl {
        private int bC;
        private NpcInstance _npc;

        public Social(NpcInstance npcInstance, int n) {
            this._npc = npcInstance;
            this.bC = n;
        }

        public void runImpl() throws Exception {
            this._npc.broadcastPacket(new L2GameServerPacket[]{new SocialAction(this._npc.getObjectId(), this.bC)});
        }
    }

    private static class ActivityTimeEnd
    extends RunnableImpl {
        private ActivityTimeEnd() {
        }

        public void runImpl() throws Exception {
            SailrenManager.n();
        }
    }
}
