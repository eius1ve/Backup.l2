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
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.World
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.instances.BossInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.Earthquake
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.PlaySound
 *  l2.gameserver.network.l2.s2c.PlaySound$Type
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.npc.NpcTemplate
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.commons.time.cron.SchedulingPattern;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.Skill;
import l2.gameserver.model.World;
import l2.gameserver.model.Zone;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.instances.BossInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.Earthquake;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.ReflectionUtils;
import l2.gameserver.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BaiumManager
extends Functions
implements OnDeathListener,
ScriptFile {
    private static final Logger c = LoggerFactory.getLogger(BaiumManager.class);
    private static ScheduledFuture<?> k = null;
    private static ScheduledFuture<?> l = null;
    private static ScheduledFuture<?> e = null;
    private static ScheduledFuture<?> m = null;
    private static ScheduledFuture<?> n = null;
    private static ScheduledFuture<?> g = null;
    private static ScheduledFuture<?> h = null;
    private static ScheduledFuture<?> i;
    private static ScheduledFuture<?> j;
    private static EpicBossState _state;
    private static long S;
    private static long T;
    private static NpcInstance e;
    private static SimpleSpawner a;
    private static NpcInstance f;
    private static SimpleSpawner b;
    private static List<NpcInstance> r;
    private static Map<Integer, SimpleSpawner> e;
    private static List<NpcInstance> s;
    private static List<SimpleSpawner> t;
    private static Zone _zone;
    public static final int BAIUM_NPC_ID = 29020;
    private static final int bq = 29021;
    private static final int br = 29025;
    private static AtomicBoolean a;
    private static final Location[] e;
    private static final Location f;
    private static final Location g;
    private static final int bs = 31842;

    public static EpicBossState getEpicBossState() {
        return _state;
    }

    private static void j() {
        for (Player player : BaiumManager.a()) {
            player.teleToClosestTown();
        }
    }

    private static synchronized void k() {
        if (j == null && BaiumManager.c()) {
            j = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new onAnnihilated()), Config.BAIUM_CLEAR_ZONE_IF_ALL_DIE);
        }
    }

    private static void o() {
        for (NpcInstance npcInstance : s) {
            if (npcInstance == null || npcInstance.getSpawn() == null) continue;
            npcInstance.getSpawn().stopRespawn();
            npcInstance.deleteMe();
        }
        s.clear();
    }

    private static List<Player> a() {
        return BaiumManager.getZone().getInsidePlayers();
    }

    public static Zone getZone() {
        return _zone;
    }

    private void init() {
        NpcTemplate npcTemplate;
        _state = new EpicBossState(29020);
        _zone = ReflectionUtils.getZone((String)"[baium_epic]");
        CharListenerList.addGlobal((Listener)this);
        try {
            a = new SimpleSpawner(NpcHolder.getInstance().getTemplate(29025));
            a.setAmount(1);
            a.setLoc(g);
            a.stopRespawn();
            npcTemplate = new SimpleSpawner(NpcHolder.getInstance().getTemplate(29020));
            npcTemplate.setAmount(1);
            e.put(29020, npcTemplate);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            npcTemplate = NpcHolder.getInstance().getTemplate(31842);
            b = new SimpleSpawner(npcTemplate);
            b.setAmount(1);
            b.setLoc(f);
            b.setRespawnDelay(60);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            int n;
            npcTemplate = NpcHolder.getInstance().getTemplate(29021);
            t.clear();
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            for (int i = 0; i < 5; ++i) {
                n = -1;
                while (n == -1 || arrayList.contains(n)) {
                    n = Rnd.get((int)10);
                }
                arrayList.add(n);
            }
            Iterator iterator = arrayList.iterator();
            while (iterator.hasNext()) {
                n = (Integer)iterator.next();
                SimpleSpawner simpleSpawner = new SimpleSpawner(npcTemplate);
                simpleSpawner.setAmount(1);
                simpleSpawner.setLoc(e[n]);
                simpleSpawner.setRespawnDelay(300000);
                t.add(simpleSpawner);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        c.info("BaiumManager: State of Baium is " + _state.getState() + ".");
        if (_state.getState().equals((Object)EpicBossState.State.NOTSPAWN)) {
            a.doSpawn(true);
        } else if (_state.getState().equals((Object)EpicBossState.State.ALIVE)) {
            _state.setState(EpicBossState.State.NOTSPAWN);
            _state.update();
            a.doSpawn(true);
        } else if (_state.getState().equals((Object)EpicBossState.State.INTERVAL) || _state.getState().equals((Object)EpicBossState.State.DEAD)) {
            BaiumManager.setIntervalEndTask();
        }
        c.info("BaiumManager: Next spawn date: " + TimeUtils.toSimpleFormat((long)_state.getRespawnDate()));
    }

    private static boolean c() {
        for (Player player : BaiumManager.a()) {
            if (player.isDead()) continue;
            return false;
        }
        return true;
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (creature.isPlayer() && _state != null && _state.getState() == EpicBossState.State.ALIVE && _zone != null && _zone.checkIfInZone(creature)) {
            BaiumManager.k();
        } else if (creature.isNpc() && creature.getNpcId() == 29020) {
            BaiumManager.onBaiumDie(creature);
        }
    }

    public static void onBaiumDie(Creature creature) {
        if (!a.compareAndSet(false, true)) {
            return;
        }
        creature.broadcastPacket(new L2GameServerPacket[]{new PlaySound(PlaySound.Type.MUSIC, "BS02_D", 1, 0, creature.getLoc())});
        _state.setRespawnDate(BaiumManager.a());
        _state.setState(EpicBossState.State.INTERVAL);
        _state.update();
        BaiumManager.m();
        Log.add((String)"Baium died", (String)"bosses");
        BaiumManager.o();
        l = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new CubeSpawn()), 10000L);
    }

    private static long a() {
        SchedulingPattern schedulingPattern = null;
        if (!Config.FWA_FIXTIMEPATTERNOFBAIUM.isEmpty()) {
            long l = System.currentTimeMillis();
            try {
                schedulingPattern = new SchedulingPattern(Config.FWA_FIXTIMEPATTERNOFBAIUM);
                long l2 = schedulingPattern.next(l) - l;
                return Math.max(60000L, l2);
            }
            catch (SchedulingPattern.InvalidPatternException invalidPatternException) {
                throw new RuntimeException("Invalid respawn data \"" + Config.FWA_FIXTIMEPATTERNOFBAIUM + "\" in " + BaiumManager.class.getSimpleName(), invalidPatternException);
            }
        }
        return (long)(Config.ALT_RAID_RESPAWN_MULTIPLIER * (double)(Config.FWB_FIXINTERVALOFBAIUM + Rnd.get((long)0L, (long)Config.FWB_RANDOMINTERVALOFBAIUM)));
    }

    private static void setIntervalEndTask() {
        BaiumManager.setUnspawn();
        if (!_state.getState().equals((Object)EpicBossState.State.INTERVAL)) {
            _state.setRespawnDate(BaiumManager.a());
            _state.setState(EpicBossState.State.INTERVAL);
            _state.update();
        }
        BaiumManager.m();
    }

    private static void m() {
        if (e != null) {
            e.cancel(false);
            e = null;
        }
        e = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new IntervalEnd()), _state.getInterval());
    }

    public static void setLastAttackTime() {
        S = System.currentTimeMillis();
    }

    public static void setUnspawn() {
        BaiumManager.j();
        BaiumManager.o();
        for (NpcInstance npcInstance : r) {
            npcInstance.getSpawn().stopRespawn();
            npcInstance.deleteMe();
        }
        r.clear();
        if (f != null) {
            f.getSpawn().stopRespawn();
            f.deleteMe();
            f = null;
        }
        if (l != null) {
            l.cancel(false);
            l = null;
        }
        if (e != null) {
            e.cancel(false);
            e = null;
        }
        if (n != null) {
            n.cancel(false);
            n = null;
        }
        if (g != null) {
            g.cancel(false);
            g = null;
        }
        if (m != null) {
            m.cancel(false);
            m = null;
        }
        if (k != null) {
            k.cancel(false);
            k = null;
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

    private static void p() {
        BaiumManager.setUnspawn();
        Log.add((String)"Baium going to sleep, spawning statue", (String)"bosses");
        _state.setState(EpicBossState.State.NOTSPAWN);
        _state.update();
        a.doSpawn(true);
    }

    public static void spawnBaium(NpcInstance npcInstance, Player player) {
        a.set(false);
        e = npcInstance;
        SimpleSpawner simpleSpawner = (SimpleSpawner)e.get(29020);
        simpleSpawner.setLoc(e.getLoc());
        e.getSpawn().stopRespawn();
        e.deleteMe();
        BossInstance bossInstance = (BossInstance)simpleSpawner.doSpawn(true);
        r.add((NpcInstance)bossInstance);
        _state.setRespawnDate(BaiumManager.a());
        _state.setState(EpicBossState.State.ALIVE);
        _state.update();
        Log.add((String)("Spawned Baium, awake by: " + player), (String)"bosses");
        BaiumManager.setLastAttackTime();
        bossInstance.startImmobilized();
        bossInstance.broadcastPacket(new L2GameServerPacket[]{new PlaySound(PlaySound.Type.MUSIC, "BS02_A", 1, 0, bossInstance.getLoc())});
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new EarthquakeTask(bossInstance)), 5000L);
        bossInstance.broadcastPacket(new L2GameServerPacket[]{new SocialAction(bossInstance.getObjectId(), 3)});
        BaiumManager.broadcastCustomScreenMessageAboutHero((NpcInstance)bossInstance);
        m = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new KillPc(player, bossInstance)), 10000L);
        k = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new CallArchAngel()), 12000L);
        n = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SetMobilised(bossInstance)), 20000L);
        Location location = new Location(Rnd.get((int)112826, (int)116241), Rnd.get((int)15575, (int)16375), 10078, 0);
        g = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new MoveAtRandom((NpcInstance)bossInstance, location)), 36000L);
        h = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new CheckLastAttack()), 600000L);
        if (Config.FWA_LIMITMAXUNTILSLEEPBAIUM > 0L) {
            BaiumManager.setLastEnterTime();
        }
    }

    public static void setLastEnterTime() {
        T = System.currentTimeMillis();
        i = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new MaxTimeCheck()), Config.FWA_LIMITMAXUNTILSLEEPBAIUM);
    }

    public static void broadcastCustomScreenMessageAboutHero(NpcInstance npcInstance) {
        List list = World.getAroundPlayers((GameObject)npcInstance, (int)4000, (int)400);
        Object object = null;
        for (Object object2 : list) {
            if (object2 == null || !object2.isHero()) continue;
            object = object2;
            break;
        }
        if (object != null) {
            String string = object.getName();
            for (Player player : list) {
                if (player == null) continue;
                player.sendPacket((IStaticPacket)new ExShowScreenMessage(new CustomMessage("1000521", player, new Object[0]).addString(string).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false));
            }
        }
    }

    public void onLoad() {
        if (Config.ALLOW_BAIUM_RAID) {
            this.init();
        }
    }

    public void onReload() {
        BaiumManager.p();
    }

    public void onShutdown() {
    }

    static {
        j = null;
        S = 0L;
        T = 0L;
        a = null;
        f = null;
        b = null;
        r = new ArrayList<NpcInstance>();
        e = new ConcurrentHashMap();
        s = new ArrayList<NpcInstance>();
        t = new ArrayList<SimpleSpawner>();
        a = new AtomicBoolean(false);
        e = new Location[]{new Location(113004, 16209, 10136, 60242), new Location(114053, 16642, 10136, 4411), new Location(114563, 17184, 10136, 49241), new Location(116356, 16402, 10136, 31109), new Location(115015, 16393, 10136, 32760), new Location(115481, 15335, 10136, 16241), new Location(114680, 15407, 10136, 32485), new Location(114886, 14437, 10136, 16868), new Location(115391, 17593, 10136, 55346), new Location(115245, 17558, 10136, 35536)};
        f = new Location(115203, 16620, 10078, 0);
        g = new Location(115996, 17417, 10106, 41740);
    }

    public static class onAnnihilated
    extends RunnableImpl {
        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void runImpl() {
            Class<BaiumManager> clazz = BaiumManager.class;
            synchronized (BaiumManager.class) {
                if (!BaiumManager.c() || _state.getState() == EpicBossState.State.INTERVAL) {
                    j = null;
                    // ** MonitorExit[var1_1] (shouldn't be in output)
                    return;
                }
                // ** MonitorExit[var1_1] (shouldn't be in output)
                BaiumManager.p();
                return;
            }
        }
    }

    public static class CubeSpawn
    extends RunnableImpl {
        public void runImpl() throws Exception {
            f = b.doSpawn(true);
        }
    }

    public static class IntervalEnd
    extends RunnableImpl {
        public void runImpl() throws Exception {
            BaiumManager.setUnspawn();
            _state.setState(EpicBossState.State.NOTSPAWN);
            _state.update();
            a.doSpawn(true);
        }
    }

    public static class EarthquakeTask
    extends RunnableImpl {
        private final BossInstance b;

        public EarthquakeTask(BossInstance bossInstance) {
            this.b = bossInstance;
        }

        public void runImpl() throws Exception {
            Earthquake earthquake = new Earthquake(this.b.getLoc(), 40, 5);
            this.b.broadcastPacket(new L2GameServerPacket[]{earthquake});
        }
    }

    public static class KillPc
    extends RunnableImpl {
        private BossInstance c;
        private Player a;

        public KillPc(Player player, BossInstance bossInstance) {
            this.a = player;
            this.c = bossInstance;
        }

        public void runImpl() throws Exception {
            Skill skill = SkillTable.getInstance().getInfo(4136, 1);
            Location location = Location.findFrontPosition((GameObject)this.c, (GameObject)this.a, (int)200, (int)250);
            if (this.a != null && skill != null) {
                this.a.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE, null, null);
                this.a.teleToLocation(location);
                this.c.setTarget((GameObject)this.a);
                this.c.doCast(skill, (Creature)this.a, false);
            }
        }
    }

    public static class CallArchAngel
    extends RunnableImpl {
        public void runImpl() throws Exception {
            for (SimpleSpawner simpleSpawner : t) {
                s.add(simpleSpawner.doSpawn(true));
            }
        }
    }

    public static class SetMobilised
    extends RunnableImpl {
        private BossInstance c;

        public SetMobilised(BossInstance bossInstance) {
            this.c = bossInstance;
        }

        public void runImpl() throws Exception {
            this.c.stopImmobilized();
        }
    }

    public static class MoveAtRandom
    extends RunnableImpl {
        private NpcInstance _npc;
        private Location _pos;

        public MoveAtRandom(NpcInstance npcInstance, Location location) {
            this._npc = npcInstance;
            this._pos = location;
        }

        public void runImpl() throws Exception {
            if (this._npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_ACTIVE) {
                this._npc.moveToLocation(this._pos, 0, false);
            }
        }
    }

    public static class CheckLastAttack
    extends RunnableImpl {
        public void runImpl() throws Exception {
            if (_state.getState().equals((Object)EpicBossState.State.ALIVE)) {
                if (S + Config.FWB_LIMITUNTILSLEEPBAIUM < System.currentTimeMillis()) {
                    BaiumManager.p();
                } else {
                    h = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new CheckLastAttack()), 60000L);
                }
            }
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
            if (T + Config.FWA_LIMITMAXUNTILSLEEPBAIUM <= System.currentTimeMillis()) {
                BaiumManager.p();
            }
        }
    }
}
