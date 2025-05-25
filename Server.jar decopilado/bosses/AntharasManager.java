/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.time.cron.SchedulingPattern
 *  l2.commons.time.cron.SchedulingPattern$InvalidPatternException
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.instances.BossInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.PlaySound
 *  l2.gameserver.network.l2.s2c.PlaySound$Type
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
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.StringHolder;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.instances.BossInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PlaySound;
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
public class AntharasManager
extends Functions
implements OnDeathListener,
ScriptFile {
    private static final Logger _log = LoggerFactory.getLogger(AntharasManager.class);
    public static final int ANTHARAS_STRONG = 29068;
    private static final int bm = 31859;
    private static final int bn = 3865;
    private static final Location c = new Location(179892, 114915, -7704);
    private static final Location d = new Location(177615, 114941, -7709, 0);
    private static final Location e = new Location(181911, 114835, -7678, 32542);
    private static BossInstance a;
    private static NpcInstance d;
    private static List<NpcInstance> p;
    private static ScheduledFuture<?> d;
    private static ScheduledFuture<?> e;
    private static ScheduledFuture<?> f;
    private static ScheduledFuture<?> g;
    private static ScheduledFuture<?> h;
    private static ScheduledFuture<?> i;
    private static ScheduledFuture<?> j;
    private static EpicBossState _state;
    private static Zone _zone;
    private static long S;
    private static long T;
    private static AtomicBoolean a;
    private static volatile boolean P;

    public static EpicBossState getEpicBossState() {
        return _state;
    }

    private static void j() {
        for (Player player : AntharasManager.a()) {
            player.teleToClosestTown();
        }
    }

    private static synchronized void k() {
        if (j == null && AntharasManager.c()) {
            j = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new onAnnihilated()), Config.ANTHARAS_CLEAR_ZONE_IF_ALL_DIE);
        }
    }

    private static List<Player> a() {
        return AntharasManager.getZone().getInsidePlayers();
    }

    private static long a() {
        SchedulingPattern schedulingPattern = null;
        if (!Config.FWA_FIXTIMEPATTERNOFANTHARAS.isEmpty()) {
            long l = System.currentTimeMillis();
            try {
                schedulingPattern = new SchedulingPattern(Config.FWA_FIXTIMEPATTERNOFANTHARAS);
                long l2 = schedulingPattern.next(l) - l;
                return Math.max(60000L, l2);
            }
            catch (SchedulingPattern.InvalidPatternException invalidPatternException) {
                throw new RuntimeException("Invalid respawn data \"" + Config.FWA_FIXTIMEPATTERNOFANTHARAS + "\" in " + AntharasManager.class.getSimpleName(), invalidPatternException);
            }
        }
        return (long)(Config.ALT_RAID_RESPAWN_MULTIPLIER * (double)(Config.FWA_FIXTIMEINTERVALOFANTHARAS + Rnd.get((long)0L, (long)Config.FWA_RANDOMINTERVALOFANTHARAS)));
    }

    public static Zone getZone() {
        return _zone;
    }

    private static boolean c() {
        for (Player player : AntharasManager.a()) {
            if (player.isDead()) continue;
            return false;
        }
        return true;
    }

    private static void l() {
        if (!a.compareAndSet(false, true)) {
            return;
        }
        _state.setRespawnDate(AntharasManager.a());
        _state.setState(EpicBossState.State.INTERVAL);
        _state.update();
        AntharasManager.m();
        P = false;
        d = Functions.spawn((Location)d, (int)31859);
        Log.add((String)"Antharas died", (String)"bosses");
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (creature.isPlayer() && _state != null && _state.getState() == EpicBossState.State.ALIVE && _zone != null && _zone.checkIfInZone(creature.getX(), creature.getY())) {
            AntharasManager.k();
        } else if (creature.isNpc() && creature.getNpcId() == 29068) {
            ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasSpawn(8)), 10L);
        }
    }

    private static void setIntervalEndTask() {
        AntharasManager.setUnspawn();
        if (_state.getState().equals((Object)EpicBossState.State.ALIVE)) {
            _state.setState(EpicBossState.State.NOTSPAWN);
            _state.update();
            return;
        }
        if (!_state.getState().equals((Object)EpicBossState.State.INTERVAL)) {
            _state.setRespawnDate(AntharasManager.a());
            _state.setState(EpicBossState.State.INTERVAL);
            _state.update();
        }
        AntharasManager.m();
    }

    private static void m() {
        if (e != null) {
            e.cancel(false);
            e = null;
        }
        e = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new IntervalEnd()), _state.getInterval());
    }

    private static void setUnspawn() {
        AntharasManager.j();
        if (a != null) {
            a.deleteMe();
        }
        for (NpcInstance npcInstance : p) {
            npcInstance.deleteMe();
        }
        if (d != null) {
            d.deleteMe();
        }
        P = false;
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
        if (g != null) {
            g.cancel(false);
            g = null;
        }
        if (h != null) {
            h.cancel(false);
            h = null;
        }
        if (i != null) {
            i.cancel(false);
            i = null;
        }
        if (j != null) {
            j.cancel(false);
            j = null;
        }
    }

    private void init() {
        _state = new EpicBossState(29068);
        _zone = ReflectionUtils.getZone((String)"[antharas_epic]");
        CharListenerList.addGlobal((Listener)this);
        _log.info("AntharasManager: State of Antharas is " + _state.getState() + ".");
        if (!_state.getState().equals((Object)EpicBossState.State.NOTSPAWN)) {
            AntharasManager.setIntervalEndTask();
        }
        _log.info("AntharasManager: Next spawn date of Antharas is " + TimeUtils.toSimpleFormat((long)_state.getRespawnDate()) + ".");
    }

    private static void n() {
        if (Config.ANTHARAS_ANNOUNCE_PREPARING_SPAWN) {
            Announcements.getInstance().announceByCustomMessage("AntharasGoingSleep", null);
        }
        AntharasManager.setUnspawn();
        if (_state.getState().equals((Object)EpicBossState.State.ALIVE)) {
            _state.setState(EpicBossState.State.NOTSPAWN);
            _state.update();
        }
    }

    public static void setLastAttackTime() {
        S = System.currentTimeMillis();
    }

    public static void setLastEnterTime() {
        T = System.currentTimeMillis();
        i = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new MaxTimeCheck()), Config.FWA_LIMITMAXUNTILSLEEPANTHARAS);
    }

    public static synchronized void setAntharasSpawnTask() {
        if (d == null) {
            if (Config.ANTHARAS_ANNOUNCE_PREPARING_SPAWN) {
                Announcements.getInstance().announceByCustomMessage("AntharasPreparingToWakeUp", new String[]{Long.toString(Config.FWA_APPTIMEOFANTHARAS / 60000L)});
            }
            d = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasSpawn(1)), Config.FWA_APPTIMEOFANTHARAS);
        }
        if (Config.FWA_LIMITMAXUNTILSLEEPANTHARAS > 0L) {
            AntharasManager.setLastEnterTime();
        }
    }

    public static void broadcastCustomScreenMessage(String string) {
        for (Player player : AntharasManager.a()) {
            player.sendPacket((IStaticPacket)new ExShowScreenMessage(StringHolder.getInstance().getNotNull(player, string), 8000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false));
        }
    }

    public static void addSpawnedMinion(NpcInstance npcInstance) {
        p.add(npcInstance);
    }

    public static void enterTheLair(Player player) {
        if (player == null) {
            return;
        }
        if (P) {
            player.sendMessage(new CustomMessage("AntharasStillReborn", player, new Object[0]));
            return;
        }
        switch (_state.getState()) {
            case ALIVE: {
                player.sendMessage(new CustomMessage("AntharasAlreadyReborn", player, new Object[0]));
                return;
            }
            case DEAD: 
            case INTERVAL: {
                player.sendMessage(new CustomMessage("AntharasStillReborn", player, new Object[0]));
                return;
            }
        }
        if (player.isDead() || player.isFlying() || player.isCursedWeaponEquipped() || player.getInventory().getCountOf(3865) < 1L || !player.isInRange((GameObject)player, 500L)) {
            player.sendMessage(new CustomMessage("AntharasPlayerNotRequirements", player, new Object[0]));
            return;
        }
        player.getInventory().destroyItemByItemId(3865, 1L);
        player.teleToLocation(c);
        AntharasManager.setAntharasSpawnTask();
    }

    public void onLoad() {
        this.init();
    }

    public void onReload() {
        AntharasManager.n();
    }

    public void onShutdown() {
    }

    static {
        p = new ArrayList<NpcInstance>();
        S = 0L;
        T = 0L;
        a = new AtomicBoolean(false);
        P = false;
    }

    private static class onAnnihilated
    extends RunnableImpl {
        private onAnnihilated() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void runImpl() {
            Class<AntharasManager> clazz = AntharasManager.class;
            synchronized (AntharasManager.class) {
                if (!AntharasManager.c() || _state.getState() == EpicBossState.State.INTERVAL) {
                    j = null;
                    // ** MonitorExit[var1_1] (shouldn't be in output)
                    return;
                }
                // ** MonitorExit[var1_1] (shouldn't be in output)
                AntharasManager.n();
                return;
            }
        }
    }

    private static class AntharasSpawn
    extends RunnableImpl {
        private int bo = 5000;
        private int bp = 0;
        private List<Player> q = AntharasManager.a();

        AntharasSpawn(int n) {
            this.bp = n;
        }

        public void runImpl() {
            switch (this.bp) {
                case 1: {
                    if (Config.ANTHARAS_ANNOUNCE_PREPARING_SPAWN) {
                        Announcements.getInstance().announceByCustomMessage("AntharasWakeUp", null);
                    }
                    a.set(false);
                    a = (BossInstance)Functions.spawn((Location)e, (int)29068);
                    a.setAggroRange(0);
                    _state.setRespawnDate(AntharasManager.a());
                    _state.setState(EpicBossState.State.ALIVE);
                    _state.update();
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasSpawn(2)), 2000L);
                    P = true;
                    break;
                }
                case 2: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)a) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)a, 700, 13, -19, 0, 20000, 0, 0, 0, 0);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasSpawn(3)), 3000L);
                    break;
                }
                case 3: {
                    a.broadcastPacket(new L2GameServerPacket[]{new SocialAction(a.getObjectId(), 1)});
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)a) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)a, 700, 13, 0, 6000, 20000, 0, 0, 0, 0);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasSpawn(4)), 10000L);
                    break;
                }
                case 4: {
                    a.broadcastPacket(new L2GameServerPacket[]{new SocialAction(a.getObjectId(), 2)});
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)a) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)a, 3700, 0, -3, 0, 10000, 0, 0, 0, 0);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasSpawn(5)), 200L);
                    break;
                }
                case 5: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)a) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)a, 1100, 0, -3, 22000, 30000, 0, 0, 0, 0);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasSpawn(6)), 10800L);
                    break;
                }
                case 6: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)a) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)a, 1100, 0, -3, 300, 7000, 0, 0, 0, 0);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasSpawn(7)), 7000L);
                    break;
                }
                case 7: {
                    for (Player player : this.q) {
                        player.leaveMovieMode();
                    }
                    AntharasManager.broadcastCustomScreenMessage("AntharasYoucannothope");
                    a.broadcastPacket(new L2GameServerPacket[]{new PlaySound(PlaySound.Type.MUSIC, "BS02_A", 1, a.getObjectId(), a.getLoc())});
                    a.setAggroRange(AntharasManager.a.getTemplate().aggroRange);
                    a.setRunning();
                    a.moveToLocation(new Location(179011, 114871, -7704), 0, false);
                    h = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new CheckLastAttack()), 600000L);
                    break;
                }
                case 8: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)a) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)a, 1200, 20, -10, 0, 13000, 0, 0, 0, 0);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasSpawn(9)), 13000L);
                    break;
                }
                case 9: {
                    for (Player player : this.q) {
                        player.leaveMovieMode();
                    }
                    AntharasManager.broadcastCustomScreenMessage("ANTHARAS_THE_EVIL_LAND_DRAGON_ANTHARAS_DEFEATED");
                    AntharasManager.l();
                }
            }
        }
    }

    private static class IntervalEnd
    extends RunnableImpl {
        private IntervalEnd() {
        }

        public void runImpl() {
            AntharasManager.setUnspawn();
            _state.setState(EpicBossState.State.NOTSPAWN);
            _state.update();
        }
    }

    private static class MaxTimeCheck
    extends RunnableImpl {
        private MaxTimeCheck() {
        }

        public void runImpl() throws Exception {
            if (_state.getState() != EpicBossState.State.ALIVE) {
                return;
            }
            if (T + Config.FWA_LIMITMAXUNTILSLEEPANTHARAS <= System.currentTimeMillis()) {
                AntharasManager.n();
            }
        }
    }

    private static class CheckLastAttack
    extends RunnableImpl {
        private CheckLastAttack() {
        }

        public void runImpl() {
            if (_state.getState() == EpicBossState.State.ALIVE) {
                if (S + Config.FWA_LIMITUNTILSLEEPANTHARAS < System.currentTimeMillis()) {
                    AntharasManager.n();
                } else {
                    h = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new CheckLastAttack()), 60000L);
                }
            }
        }
    }
}
