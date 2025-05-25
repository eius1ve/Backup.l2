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
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Spawner
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
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.data.StringHolder;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Spawner;
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
public class ValakasManager
extends Functions
implements OnDeathListener,
ScriptFile {
    private static final Logger g = LoggerFactory.getLogger(ValakasManager.class);
    private static final int[][] a = new int[][]{{214880, -116144, -1644, 0}, {213696, -116592, -1644, 0}, {212112, -116688, -1644, 0}, {211184, -115472, -1664, 0}, {210336, -114592, -1644, 0}, {211360, -113904, -1644, 0}, {213152, -112352, -1644, 0}, {214032, -113232, -1644, 0}, {214752, -114592, -1644, 0}, {209824, -115568, -1421, 0}, {210528, -112192, -1403, 0}, {213120, -111136, -1408, 0}, {215184, -111504, -1392, 0}, {215456, -117328, -1392, 0}, {213200, -118160, -1424, 0}};
    private static List<NpcInstance> D = new ArrayList<NpcInstance>();
    private static BossInstance d;
    private static ScheduledFuture<?> q;
    private static ScheduledFuture<?> e;
    private static ScheduledFuture<?> f;
    private static ScheduledFuture<?> n;
    private static ScheduledFuture<?> g;
    private static ScheduledFuture<?> r;
    private static ScheduledFuture<?> h;
    private static ScheduledFuture<?> i;
    private static ScheduledFuture<?> j;
    public static final int VALAKAS_NPC_ID = 29028;
    private static final int bD = 31759;
    private static EpicBossState _state;
    private static Zone _zone;
    private static long S;
    private static long T;
    private static AtomicBoolean a;
    private static final Location k;
    private static volatile boolean P;

    public static EpicBossState getEpicBossState() {
        return _state;
    }

    private static void j() {
        for (Player player : ValakasManager.a()) {
            player.teleToClosestTown();
        }
    }

    private static synchronized void k() {
        if (j == null && ValakasManager.c()) {
            j = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new onAnnihilated()), Config.VALAKAS_CLEAR_ZONE_IF_ALL_DIE);
        }
    }

    private static List<Player> a() {
        return ValakasManager.getZone().getInsidePlayers();
    }

    private static long a() {
        SchedulingPattern schedulingPattern = null;
        if (!Config.FWA_FIXTIMEPATTERNOFVALAKAS.isEmpty()) {
            long l = System.currentTimeMillis();
            try {
                schedulingPattern = new SchedulingPattern(Config.FWA_FIXTIMEPATTERNOFVALAKAS);
                long l2 = schedulingPattern.next(l) - l;
                return Math.max(60000L, l2);
            }
            catch (SchedulingPattern.InvalidPatternException invalidPatternException) {
                throw new RuntimeException("Invalid respawn data \"" + Config.FWA_FIXTIMEPATTERNOFVALAKAS + "\" in " + ValakasManager.class.getSimpleName(), invalidPatternException);
            }
        }
        return (long)(Config.ALT_RAID_RESPAWN_MULTIPLIER * (double)(Config.FWV_FIXINTERVALOFVALAKAS + Rnd.get((long)0L, (long)Config.FWV_RANDOMINTERVALOFVALAKAS)));
    }

    public static Zone getZone() {
        return _zone;
    }

    private static boolean c() {
        for (Player player : ValakasManager.a()) {
            if (player.isDead()) continue;
            return false;
        }
        return true;
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (creature.isPlayer() && _state != null && _state.getState() == EpicBossState.State.ALIVE && _zone != null && _zone.checkIfInZone(creature.getX(), creature.getY())) {
            ValakasManager.k();
        } else if (creature.isNpc() && creature.getNpcId() == 29028) {
            ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(12)), 1L);
        }
    }

    private static void w() {
        if (!a.compareAndSet(false, true)) {
            return;
        }
        _state.setRespawnDate(ValakasManager.a());
        _state.setState(EpicBossState.State.INTERVAL);
        _state.update();
        ValakasManager.m();
        P = false;
        for (int[] nArray : a) {
            D.add(Functions.spawn((Location)new Location(nArray[0], nArray[1], nArray[2], nArray[3]), (int)31759));
        }
        Log.add((String)"Valakas died", (String)"bosses");
    }

    private static void setIntervalEndTask() {
        ValakasManager.setUnspawn();
        if (_state.getState().equals((Object)EpicBossState.State.ALIVE)) {
            _state.setState(EpicBossState.State.NOTSPAWN);
            _state.update();
            return;
        }
        if (!_state.getState().equals((Object)EpicBossState.State.INTERVAL)) {
            _state.setRespawnDate(ValakasManager.a());
            _state.setState(EpicBossState.State.INTERVAL);
            _state.update();
        }
        ValakasManager.m();
    }

    private static void m() {
        if (e != null) {
            e.cancel(false);
            e = null;
        }
        e = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new IntervalEnd()), _state.getInterval());
    }

    private static void setUnspawn() {
        ValakasManager.j();
        P = false;
        if (d != null) {
            d.deleteMe();
        }
        for (NpcInstance npcInstance : D) {
            if (npcInstance == null) continue;
            Spawner spawner = npcInstance.getSpawn();
            if (spawner != null) {
                spawner.stopRespawn();
            }
            npcInstance.deleteMe();
        }
        D.clear();
        if (q != null) {
            q.cancel(false);
            q = null;
        }
        if (e != null) {
            e.cancel(false);
            e = null;
        }
        if (f != null) {
            f.cancel(false);
            f = null;
        }
        if (n != null) {
            n.cancel(false);
            n = null;
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
        if (r != null) {
            r.cancel(false);
            r = null;
        }
        if (j != null) {
            j.cancel(false);
            j = null;
        }
    }

    private static void n() {
        if (Config.VALAKAS_ANNOUNCE_PREPARING_SPAWN) {
            Announcements.getInstance().announceByCustomMessage("ValakasGoingSleep", null);
        }
        ValakasManager.setUnspawn();
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
        i = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new MaxTimeCheck()), Config.FWA_LIMITMAXUNTILSLEEPVALAKAS);
    }

    public static synchronized void setValakasSpawnTask() {
        if (q == null) {
            q = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(1)), Config.FWV_APPTIMEOFVALAKAS);
            if (Config.VALAKAS_ANNOUNCE_PREPARING_SPAWN) {
                Announcements.getInstance().announceByCustomMessage("ValakasPreparingToWakeUp", new String[]{Long.toString(Config.FWV_APPTIMEOFVALAKAS / 60000L)});
            }
        }
        if (Config.FWA_LIMITMAXUNTILSLEEPVALAKAS > 0L) {
            ValakasManager.setLastEnterTime();
        }
    }

    public static void broadcastCustomScreenMessage(String string) {
        for (Player player : ValakasManager.a()) {
            player.sendPacket((IStaticPacket)new ExShowScreenMessage(StringHolder.getInstance().getNotNull(player, string), 8000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false));
        }
    }

    private void init() {
        CharListenerList.addGlobal((Listener)this);
        _state = new EpicBossState(29028);
        _zone = ReflectionUtils.getZone((String)"[valakas_epic]");
        g.info("ValakasManager: State of Valakas is " + _state.getState() + ".");
        if (!_state.getState().equals((Object)EpicBossState.State.NOTSPAWN)) {
            ValakasManager.setIntervalEndTask();
        }
        g.info("ValakasManager: Next spawn date of Valakas is " + TimeUtils.toSimpleFormat((long)_state.getRespawnDate()) + ".");
    }

    public static boolean isEnableEnterToLair() {
        return _state.getState() == EpicBossState.State.NOTSPAWN;
    }

    public static void enterTheLair(Player player) {
        if (player == null) {
            return;
        }
        if (P) {
            player.sendMessage(new CustomMessage("ValakasStillReborn", player, new Object[0]));
            return;
        }
        switch (_state.getState()) {
            case ALIVE: {
                player.sendMessage(new CustomMessage("ValakasAlreadyReborn", player, new Object[0]));
                return;
            }
            case DEAD: 
            case INTERVAL: {
                player.sendMessage(new CustomMessage("ValakasStillReborn", player, new Object[0]));
                return;
            }
        }
        if (player.isDead() || player.isFlying() || player.isCursedWeaponEquipped() || !player.isInRange((GameObject)player, 500L)) {
            player.sendMessage(new CustomMessage("ValakasPlayerNotRequirements", player, new Object[0]));
            return;
        }
        player.teleToLocation(k);
        ValakasManager.setValakasSpawnTask();
    }

    public void onLoad() {
        this.init();
    }

    public void onReload() {
        ValakasManager.n();
    }

    public void onShutdown() {
    }

    static {
        q = null;
        e = null;
        f = null;
        n = null;
        g = null;
        r = null;
        h = null;
        j = null;
        S = 0L;
        T = 0L;
        a = new AtomicBoolean(false);
        k = new Location(203940, -111840, 66);
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
            Class<ValakasManager> clazz = ValakasManager.class;
            synchronized (ValakasManager.class) {
                if (!ValakasManager.c() || _state.getState() == EpicBossState.State.INTERVAL) {
                    j = null;
                    // ** MonitorExit[var1_1] (shouldn't be in output)
                    return;
                }
                // ** MonitorExit[var1_1] (shouldn't be in output)
                ValakasManager.n();
                return;
            }
        }
    }

    private static class SpawnDespawn
    extends RunnableImpl {
        private int bo = 2550;
        private int bp;
        private List<Player> q = ValakasManager.a();

        SpawnDespawn(int n) {
            this.bp = n;
        }

        public void runImpl() throws Exception {
            switch (this.bp) {
                case 1: {
                    if (Config.VALAKAS_ANNOUNCE_PREPARING_SPAWN) {
                        Announcements.getInstance().announceByCustomMessage("ValakasWakeUp", null);
                    }
                    d = (BossInstance)Functions.spawn((Location)new Location(212852, -114842, -1632, 833), (int)29028);
                    a.set(false);
                    d.block();
                    d.broadcastPacket(new L2GameServerPacket[]{new PlaySound(PlaySound.Type.MUSIC, "BS03_A", 1, d.getObjectId(), d.getLoc())});
                    _state.setRespawnDate((long)(Config.ALT_RAID_RESPAWN_MULTIPLIER * (double)(Config.FWV_FIXINTERVALOFVALAKAS + Rnd.get((long)0L, (long)Config.FWV_RANDOMINTERVALOFVALAKAS))));
                    _state.setState(EpicBossState.State.ALIVE);
                    _state.update();
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(2)), 16L);
                    P = true;
                    break;
                }
                case 2: {
                    d.broadcastPacket(new L2GameServerPacket[]{new SocialAction(d.getObjectId(), 1)});
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 1800, 180, -1, 1500, 15000, 0, 0, 1, 0);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(3)), 1500L);
                    break;
                }
                case 3: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 1300, 180, -5, 3000, 15000, 0, -5, 1, 0);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(4)), 3300L);
                    break;
                }
                case 4: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 500, 180, -8, 600, 15000, 0, 60, 1, 0);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(5)), 2900L);
                    break;
                }
                case 5: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 800, 180, -8, 2700, 15000, 0, 30, 1, 0);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(6)), 2700L);
                    break;
                }
                case 6: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 200, 250, 70, 0, 15000, 30, 80, 1, 0);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(7)), 1L);
                    break;
                }
                case 7: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 1100, 250, 70, 2500, 15000, 30, 80, 1, 0);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(8)), 3200L);
                    break;
                }
                case 8: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 700, 150, 30, 0, 15000, -10, 60, 1, 0);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(9)), 1400L);
                    break;
                }
                case 9: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 1200, 150, 20, 2900, 15000, -10, 30, 1, 0);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(10)), 6700L);
                    break;
                }
                case 10: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 750, 170, -10, 3400, 15000, 10, -15, 1, 0);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(11)), 5700L);
                    break;
                }
                case 11: {
                    for (Player player : this.q) {
                        player.leaveMovieMode();
                    }
                    d.unblock();
                    ValakasManager.broadcastCustomScreenMessage("ValakasFool");
                    if (d.getAI().getIntention() == CtrlIntention.AI_INTENTION_ACTIVE) {
                        d.moveToLocation(new Location(Rnd.get((int)211080, (int)214909), Rnd.get((int)-115841, (int)-112822), -1662, 0), 0, false);
                    }
                    h = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new CheckLastAttack()), 600000L);
                    break;
                }
                case 12: {
                    d.broadcastPacket(new L2GameServerPacket[]{new PlaySound(PlaySound.Type.MUSIC, "B03_D", 1, d.getObjectId(), d.getLoc())});
                    ValakasManager.broadcastCustomScreenMessage("VALAKAS_THE_EVIL_FIRE_DRAGON_VALAKAS_DEFEATED");
                    ValakasManager.w();
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 2000, 130, -1, 0, 15000, 0, 0, 1, 1);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(13)), 500L);
                    break;
                }
                case 13: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 1100, 210, -5, 3000, 15000, -13, 0, 1, 1);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(14)), 3500L);
                    break;
                }
                case 14: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 1300, 200, -8, 3000, 15000, 0, 15, 1, 1);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(15)), 4500L);
                    break;
                }
                case 15: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 1000, 190, 0, 500, 15000, 0, 10, 1, 1);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(16)), 500L);
                    break;
                }
                case 16: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 1700, 120, 0, 2500, 15000, 12, 40, 1, 1);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(17)), 4600L);
                    break;
                }
                case 17: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 1700, 20, 0, 700, 15000, 10, 10, 1, 1);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(18)), 750L);
                    break;
                }
                case 18: {
                    for (Player player : this.q) {
                        if (player.getDistance((GameObject)d) <= (double)this.bo) {
                            player.enterMovieMode();
                            player.specialCamera((GameObject)d, 1700, 10, 0, 1000, 15000, 20, 70, 1, 1);
                            continue;
                        }
                        player.leaveMovieMode();
                    }
                    f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SpawnDespawn(19)), 2500L);
                    break;
                }
                case 19: {
                    for (Player player : this.q) {
                        player.leaveMovieMode();
                    }
                    break;
                }
            }
        }
    }

    private static class IntervalEnd
    extends RunnableImpl {
        private IntervalEnd() {
        }

        public void runImpl() throws Exception {
            ValakasManager.setUnspawn();
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
            if (T + Config.FWA_LIMITMAXUNTILSLEEPVALAKAS <= System.currentTimeMillis()) {
                ValakasManager.n();
            }
        }
    }

    private static class CheckLastAttack
    extends RunnableImpl {
        private CheckLastAttack() {
        }

        public void runImpl() throws Exception {
            if (_state.getState() == EpicBossState.State.ALIVE) {
                if (S + Config.FWV_LIMITUNTILSLEEPVALAKAS < System.currentTimeMillis()) {
                    ValakasManager.n();
                } else {
                    h = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new CheckLastAttack()), 60000L);
                }
            }
        }
    }
}
