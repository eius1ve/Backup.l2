/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.listener.actor.OnCurrentHpDamageListener
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.Earthquake
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillCanceled
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.Location
 *  org.apache.commons.lang3.ArrayUtils
 */
package instances;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.listener.actor.OnCurrentHpDamageListener;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.Earthquake;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillCanceled;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.ArrayUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Frintezza
extends Reflection {
    private static final int cH = 18328;
    private static final int cI = 18339;
    private static final int cJ = 29046;
    private static final int cK = 29047;
    private static final int cL = 29061;
    private static final int cM = 7903;
    private static final int cN = 8556;
    private static final int[] Q = new int[]{25150051, 25150052, 25150053, 25150054, 25150055, 25150056, 25150057, 25150058};
    private static final int[] R = new int[]{25150042, 25150043};
    private static final int[] S = new int[]{25150061, 25150062, 25150063, 25150064, 25150065, 25150066, 25150067, 25150068, 25150069, 25150070};
    private static final int[] T = new int[]{25150045, 25150046};
    private static final int[] U = new int[]{18329, 18330, 18331, 18333};
    private static final int[] V = new int[]{18334, 18335, 18336, 18337, 18338};
    private static int bt = 30000;
    private static NpcLocation a = new NpcLocation(174240, -89805, -5022, 16048, 29045);
    private static NpcLocation b = new NpcLocation(174234, -88015, -5116, 48028, 29046);
    private static NpcLocation[] a = new NpcLocation[]{new NpcLocation(175880, -88696, -5104, 35048, 29048), new NpcLocation(175816, -87160, -5104, 28205, 29049), new NpcLocation(172648, -87176, -5104, 64817, 29048), new NpcLocation(172600, -88664, -5104, 57730, 29049)};
    private static NpcLocation[] b = new NpcLocation[]{new NpcLocation(175880, -88696, -5104, 35048, 29050), new NpcLocation(175816, -87160, -5104, 28205, 29051), new NpcLocation(172648, -87176, -5104, 64817, 29051), new NpcLocation(172600, -88664, -5104, 57730, 29050)};
    private NpcInstance g;
    private NpcInstance h;
    private NpcInstance i;
    private NpcInstance j;
    private NpcInstance[] a;
    private NpcInstance[] b;
    private int bu = 0;
    private static final long ae = 300000L;
    private DeathListener a;
    private CurrentHpListener a;
    private ZoneListener a = new NpcInstance[4];
    private ScheduledFuture<?> o;

    public Frintezza() {
        this.b = new NpcInstance[4];
        this.a = new DeathListener();
        this.a = new CurrentHpListener();
        this.a = new ZoneListener();
    }

    protected void onCreate() {
        super.onCreate();
        this.getZone("[Frintezza]").addListener((Listener)this.a);
        for (NpcInstance npcInstance : this.getNpcs()) {
            npcInstance.addListener((Listener)this.a);
        }
        this.a(true, U);
    }

    private NpcInstance a(NpcLocation npcLocation) {
        return this.addSpawnWithoutRespawn(npcLocation.npcId, npcLocation, 0);
    }

    private void a(NpcInstance npcInstance, int n, int n2, int n3, int n4, int n5, int n6) {
        if (npcInstance == null) {
            return;
        }
        for (Player player : this.getPlayers()) {
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

    private void cleanUp() {
        this.startCollapseTimer(900000L);
        for (Player player : this.getPlayers()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THIS_DUNGEON_WILL_EXPIRE_IN_S1_MINUTES).addNumber(15));
        }
        for (Player player : this.getNpcs()) {
            player.deleteMe();
        }
    }

    private void a(boolean bl, int[] nArray) {
        for (NpcInstance npcInstance : this.getNpcs()) {
            if (!ArrayUtils.contains((int[])nArray, (int)npcInstance.getNpcId())) continue;
            if (bl) {
                npcInstance.block();
                npcInstance.setIsInvul(true);
                continue;
            }
            npcInstance.unblock();
            npcInstance.setIsInvul(false);
        }
    }

    protected void onCollapse() {
        super.onCollapse();
        if (this.o != null) {
            this.o.cancel(true);
        }
    }

    private class DeathListener
    implements OnDeathListener {
        private DeathListener() {
        }

        public void onDeath(Creature creature, Creature creature2) {
            if (creature.isNpc()) {
                if (creature.getNpcId() == 18328) {
                    for (int i = 0; i < Q.length; ++i) {
                        Frintezza.this.openDoor(Q[i]);
                    }
                    Frintezza.this.a(false, U);
                    for (NpcInstance npcInstance : Frintezza.this.getNpcs()) {
                        if (!ArrayUtils.contains((int[])U, (int)npcInstance.getNpcId())) continue;
                        npcInstance.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, Frintezza.this.getPlayers().get(Rnd.get((int)Frintezza.this.getPlayers().size())), (Object)200);
                    }
                } else if (ArrayUtils.contains((int[])U, (int)creature.getNpcId())) {
                    for (NpcInstance npcInstance : Frintezza.this.getNpcs()) {
                        if (!ArrayUtils.contains((int[])U, (int)npcInstance.getNpcId()) || npcInstance.isDead()) continue;
                        return;
                    }
                    for (int i = 0; i < R.length; ++i) {
                        Frintezza.this.openDoor(R[i]);
                    }
                    Frintezza.this.a(true, V);
                } else if (creature.getNpcId() == 18339) {
                    for (NpcInstance npcInstance : Frintezza.this.getNpcs()) {
                        if (npcInstance.getNpcId() != 18339 || npcInstance.isDead()) continue;
                        return;
                    }
                    for (int i = 0; i < S.length; ++i) {
                        Frintezza.this.openDoor(S[i]);
                    }
                    Frintezza.this.a(false, V);
                } else if (ArrayUtils.contains((int[])V, (int)creature.getNpcId())) {
                    if (Rnd.chance((int)10)) {
                        ((NpcInstance)creature).dropItem(creature2.getPlayer(), 8556, 1L);
                    }
                    for (NpcInstance npcInstance : Frintezza.this.getNpcs()) {
                        if (!ArrayUtils.contains((int[])V, (int)npcInstance.getNpcId()) && !ArrayUtils.contains((int[])U, (int)npcInstance.getNpcId()) || npcInstance.isDead()) continue;
                        return;
                    }
                    for (int i = 0; i < T.length; ++i) {
                        Frintezza.this.openDoor(T[i]);
                    }
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaStart()), 300000L);
                } else {
                    if (creature.getNpcId() == 29046) {
                        creature.decayMe();
                        return;
                    }
                    if (creature.getNpcId() == 29047) {
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Die(1)), 10L);
                        Frintezza.this.setReenterTime(System.currentTimeMillis());
                    }
                }
            }
        }
    }

    public class CurrentHpListener
    implements OnCurrentHpDamageListener {
        public void onCurrentHpDamage(Creature creature, double d, Creature creature2, Skill skill) {
            if (creature.isDead() || creature != Frintezza.this.i) {
                return;
            }
            double d2 = creature.getCurrentHp() - d;
            double d3 = creature.getMaxHp();
            switch (Frintezza.this.bu) {
                case 1: {
                    if (!(d2 < 0.75 * d3)) break;
                    Frintezza.this.bu = 2;
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SecondMorph(1)), 1100L);
                    break;
                }
                case 2: {
                    if (!(d2 < 0.1 * d3)) break;
                    Frintezza.this.bu = 3;
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(1)), 2000L);
                }
            }
        }
    }

    public class ZoneListener
    implements OnZoneEnterLeaveListener {
        public void onZoneEnter(Zone zone, Creature creature) {
        }

        public void onZoneLeave(Zone zone, Creature creature) {
            if (creature.isNpc() && (creature.getNpcId() == 29046 || creature.getNpcId() == 29047)) {
                creature.teleToLocation(new Location(174240, -88020, -5112));
                ((NpcInstance)creature).getAggroList().clear(true);
                creature.setCurrentHpMp((double)creature.getMaxHp(), (double)creature.getMaxMp());
                creature.broadcastCharInfo();
            }
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
                        Frintezza.this.a(true);
                        int n = Math.abs((Frintezza.this.j.getHeading() < 32768 ? 180 : 540) - (int)((double)Frintezza.this.j.getHeading() / 182.044444444));
                        Frintezza.this.a(Frintezza.this.j, 300, n - 180, 5, 0, 7000, 0);
                        Frintezza.this.a(Frintezza.this.j, 200, n, 85, 4000, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Die(2)), 7500L);
                        break;
                    }
                    case 2: {
                        Frintezza.this.a(Frintezza.this.h, 100, 120, 5, 0, 7000, 0);
                        Frintezza.this.a(Frintezza.this.h, 100, 90, 5, 5000, 15000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Die(3)), 6000L);
                        break;
                    }
                    case 3: {
                        Frintezza.this.a(Frintezza.this.h, 900, 90, 25, 7000, 10000, 0);
                        Frintezza.this.h.doDie((Creature)Frintezza.this.h);
                        Frintezza.this.h = null;
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Die(4)), 7000L);
                        break;
                    }
                    case 4: {
                        for (Player player : Frintezza.this.getPlayers()) {
                            player.leaveMovieMode();
                        }
                        Frintezza.this.cleanUp();
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
                        this.bw = Math.abs((Frintezza.this.i.getHeading() < 32768 ? 180 : 540) - (int)((double)Frintezza.this.i.getHeading() / 182.044444444));
                        for (Player player : Frintezza.this.getPlayers()) {
                            player.enterMovieMode();
                        }
                        Frintezza.this.a(true);
                        Frintezza.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillCanceled((Creature)Frintezza.this.h)});
                        Frintezza.this.h.broadcastPacket(new L2GameServerPacket[]{new SocialAction(Frintezza.this.h.getObjectId(), 4)});
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(2)), 100L);
                        break;
                    }
                    case 2: {
                        Frintezza.this.a(Frintezza.this.h, 250, 120, 15, 0, 1000, 0);
                        Frintezza.this.a(Frintezza.this.h, 250, 120, 15, 0, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(3)), 6500L);
                        break;
                    }
                    case 3: {
                        Frintezza.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)Frintezza.this.h, (Creature)Frintezza.this.h, 5006, 1, 34000, 0L)});
                        Frintezza.this.a(Frintezza.this.h, 500, 70, 15, 3000, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(4)), 3000L);
                        break;
                    }
                    case 4: {
                        Frintezza.this.a(Frintezza.this.h, 2500, 90, 12, 6000, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(5)), 3000L);
                        break;
                    }
                    case 5: {
                        Frintezza.this.a(Frintezza.this.i, 250, this.bw, 12, 0, 1000, 0);
                        Frintezza.this.a(Frintezza.this.i, 250, this.bw, 12, 0, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(6)), 500L);
                        break;
                    }
                    case 6: {
                        Frintezza.this.i.doDie((Creature)Frintezza.this.i);
                        Frintezza.this.a(Frintezza.this.i, 450, this.bw, 14, 8000, 8000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(7)), 6250L);
                        break;
                    }
                    case 7: {
                        NpcLocation npcLocation = new NpcLocation();
                        npcLocation.set(Frintezza.this.i.getLoc());
                        npcLocation.npcId = 29047;
                        Frintezza.this.i.deleteMe();
                        Frintezza.this.i = null;
                        Frintezza.this.j = Frintezza.this.a(npcLocation);
                        Frintezza.this.j.addListener((Listener)Frintezza.this.a);
                        Frintezza.this.a(Frintezza.this.j, true);
                        Frintezza.this.a(Frintezza.this.j, 450, this.bw, 12, 500, 14000, 2);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ThirdMorph(9)), 5000L);
                        break;
                    }
                    case 9: {
                        Frintezza.this.a(false);
                        for (Player player : Frintezza.this.getPlayers()) {
                            player.leaveMovieMode();
                        }
                        Skill skill = SkillTable.getInstance().getInfo(5017, 1);
                        skill.getEffects((Creature)Frintezza.this.j, (Creature)Frintezza.this.j, false, false);
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
                        int n = Math.abs((Frintezza.this.i.getHeading() < 32768 ? 180 : 540) - (int)((double)Frintezza.this.i.getHeading() / 182.044444444));
                        for (Player player : Frintezza.this.getPlayers()) {
                            player.enterMovieMode();
                        }
                        Frintezza.this.a(true);
                        Frintezza.this.a(Frintezza.this.i, 500, n, 5, 500, 15000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SecondMorph(2)), 2000L);
                        break;
                    }
                    case 2: {
                        Frintezza.this.i.broadcastPacket(new L2GameServerPacket[]{new SocialAction(Frintezza.this.i.getObjectId(), 1)});
                        Frintezza.this.i.setCurrentHp((double)(Frintezza.this.i.getMaxHp() * 3 / 4), false);
                        Frintezza.this.i.setRHandId(7903);
                        Frintezza.this.i.broadcastCharInfo();
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SecondMorph(3)), 5500L);
                        break;
                    }
                    case 3: {
                        Frintezza.this.i.broadcastPacket(new L2GameServerPacket[]{new SocialAction(Frintezza.this.i.getObjectId(), 4)});
                        Frintezza.this.a(false);
                        Skill skill = SkillTable.getInstance().getInfo(5017, 1);
                        skill.getEffects((Creature)Frintezza.this.i, (Creature)Frintezza.this.i, false, false);
                        for (Player player : Frintezza.this.getPlayers()) {
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

    private class SongEffectLaunched
    extends RunnableImpl {
        private final List<Creature> L;
        private final int cO;
        private final int cP;

        public SongEffectLaunched(List<Creature> list, int n, int n2) {
            this.L = list;
            this.cO = n;
            this.cP = n2;
        }

        public void runImpl() throws Exception {
            if (Frintezza.this.h == null) {
                return;
            }
            if (this.cP > bt) {
                return;
            }
            SongEffectLaunched songEffectLaunched = new SongEffectLaunched(this.L, this.cO, this.cP + bt / 10);
            ThreadPoolManager.getInstance().schedule((Runnable)((Object)songEffectLaunched), (long)(bt / 10));
            Frintezza.this.h.callSkill(SkillTable.getInstance().getInfo(5008, this.cO), this.L, false);
        }
    }

    private class Music
    extends RunnableImpl {
        private Music() {
        }

        public void runImpl() throws Exception {
            String string;
            if (Frintezza.this.h == null) {
                return;
            }
            int n = Math.max(1, Math.min(4, this.f()));
            switch (n) {
                case 1: {
                    string = "Requiem of Hatred";
                    break;
                }
                case 2: {
                    string = "Frenetic Toccata";
                    break;
                }
                case 3: {
                    string = "Fugue of Jubilation";
                    break;
                }
                case 4: {
                    string = "Mournful Chorale Prelude";
                    break;
                }
                default: {
                    return;
                }
            }
            if (!Frintezza.this.h.isBlocked()) {
                Frintezza.this.h.broadcastPacket(new L2GameServerPacket[]{new ExShowScreenMessage(string, 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, 1, -1, true)});
                Frintezza.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)Frintezza.this.h, (Creature)Frintezza.this.h, 5007, n, bt, 0L)});
                ThreadPoolManager.getInstance().schedule((Runnable)((Object)new SongEffectLaunched(this.a(n), n, 10000)), 10000L);
            }
            Frintezza.this.o = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Music()), (long)(bt + Rnd.get((int)10000)));
        }

        private List<Creature> a(int n) {
            ArrayList<Creature> arrayList = new ArrayList<Creature>();
            if (n < 4) {
                if (Frintezza.this.i != null && !Frintezza.this.i.isDead()) {
                    arrayList.add((Creature)Frintezza.this.i);
                }
                if (Frintezza.this.j != null && !Frintezza.this.j.isDead()) {
                    arrayList.add((Creature)Frintezza.this.j);
                }
                for (int i = 0; i < 4; ++i) {
                    if (Frintezza.this.a[i] != null && !Frintezza.this.a[i].isDead()) {
                        arrayList.add((Creature)Frintezza.this.a[i]);
                    }
                    if (Frintezza.this.b[i] == null || Frintezza.this.b[i].isDead()) continue;
                    arrayList.add((Creature)Frintezza.this.b[i]);
                }
            } else {
                for (Player player : Frintezza.this.getPlayers()) {
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
            return Rnd.get((int)2, (int)4);
        }

        private boolean d() {
            if (!Rnd.chance((int)40)) {
                return false;
            }
            if (Frintezza.this.i != null && !Frintezza.this.i.isAlikeDead() && Frintezza.this.i.getCurrentHp() < (double)(Frintezza.this.i.getMaxHp() * 2 / 3)) {
                return true;
            }
            if (Frintezza.this.j != null && !Frintezza.this.j.isAlikeDead() && Frintezza.this.j.getCurrentHp() < (double)(Frintezza.this.j.getMaxHp() * 2 / 3)) {
                return true;
            }
            for (int i = 0; i < 4; ++i) {
                if (Frintezza.this.a[i] != null && !Frintezza.this.a[i].isDead() && Frintezza.this.a[i].getCurrentHp() < (double)(Frintezza.this.a[i].getMaxHp() / 3)) {
                    return true;
                }
                if (Frintezza.this.b[i] == null || Frintezza.this.b[i].isDead() || !(Frintezza.this.b[i].getCurrentHp() < (double)(Frintezza.this.b[i].getMaxHp() / 3))) continue;
                return true;
            }
            return false;
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
                        Frintezza.this.g = Frintezza.this.a(new NpcLocation(174232, -89816, -5016, 16048, 29059));
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(2)), 1000L);
                        break;
                    }
                    case 2: {
                        Frintezza.this.closeDoor(T[1]);
                        Frintezza.this.h = Frintezza.this.a(a);
                        Frintezza.this.a(Frintezza.this.h, 500, 90, 0, 6500, 8000, 0);
                        for (int i = 0; i < 4; ++i) {
                            Frintezza.this.a[i] = Frintezza.this.a(a[i]);
                            Frintezza.this.a[i].startImmobilized();
                            Frintezza.this.b[i] = Frintezza.this.a(b[i]);
                        }
                        Frintezza.this.a(true);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(3)), 6500L);
                        break;
                    }
                    case 3: {
                        Frintezza.this.a(Frintezza.this.g, 1800, 90, 8, 6500, 7000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(4)), 900L);
                        break;
                    }
                    case 4: {
                        Frintezza.this.a(Frintezza.this.g, 140, 90, 10, 2500, 4500, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(5)), 4000L);
                        break;
                    }
                    case 5: {
                        Frintezza.this.a(Frintezza.this.h, 40, 75, -10, 0, 1000, 0);
                        Frintezza.this.a(Frintezza.this.h, 40, 75, -10, 0, 12000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(6)), 1350L);
                        break;
                    }
                    case 6: {
                        Frintezza.this.h.broadcastPacket(new L2GameServerPacket[]{new SocialAction(Frintezza.this.h.getObjectId(), 2)});
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(7)), 7000L);
                        break;
                    }
                    case 7: {
                        Frintezza.this.g.deleteMe();
                        Frintezza.this.g = null;
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(8)), 1000L);
                        break;
                    }
                    case 8: {
                        Frintezza.this.a(Frintezza.this.b[0], 140, 0, 3, 22000, 3000, 1);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(9)), 2800L);
                        break;
                    }
                    case 9: {
                        Frintezza.this.a(Frintezza.this.b[1], 140, 0, 3, 22000, 3000, 1);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(10)), 2800L);
                        break;
                    }
                    case 10: {
                        Frintezza.this.a(Frintezza.this.b[2], 140, 180, 3, 22000, 3000, 1);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(11)), 2800L);
                        break;
                    }
                    case 11: {
                        Frintezza.this.a(Frintezza.this.b[3], 140, 180, 3, 22000, 3000, 1);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(12)), 3000L);
                        break;
                    }
                    case 12: {
                        Frintezza.this.a(Frintezza.this.h, 240, 90, 0, 0, 1000, 0);
                        Frintezza.this.a(Frintezza.this.h, 240, 90, 25, 5500, 10000, 3);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(13)), 3000L);
                        break;
                    }
                    case 13: {
                        Frintezza.this.a(Frintezza.this.h, 100, 195, 35, 0, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(14)), 700L);
                        break;
                    }
                    case 14: {
                        Frintezza.this.a(Frintezza.this.h, 100, 195, 35, 0, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(15)), 1300L);
                        break;
                    }
                    case 15: {
                        Frintezza.this.a(Frintezza.this.h, 120, 180, 45, 1500, 10000, 0);
                        Frintezza.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)Frintezza.this.h, (Creature)Frintezza.this.h, 5006, 1, 34000, 0L)});
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(16)), 1500L);
                        break;
                    }
                    case 16: {
                        Frintezza.this.a(Frintezza.this.h, 520, 135, 45, 8000, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(17)), 7500L);
                        break;
                    }
                    case 17: {
                        Frintezza.this.a(Frintezza.this.h, 1500, 110, 25, 10000, 13000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(18)), 9500L);
                        break;
                    }
                    case 18: {
                        Frintezza.this.i = Frintezza.this.a(b);
                        Frintezza.this.a(Frintezza.this.i, true);
                        Frintezza.this.i.addListener((Listener)Frintezza.this.a);
                        Frintezza.this.i.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)Frintezza.this.i, (Creature)Frintezza.this.i, 5016, 1, 3000, 0L)});
                        Earthquake earthquake = new Earthquake(Frintezza.this.i.getLoc(), 50, 6);
                        for (Player player : Frintezza.this.getPlayers()) {
                            player.broadcastPacket(new L2GameServerPacket[]{earthquake});
                        }
                        Frintezza.this.a(Frintezza.this.i, 1000, 160, 20, 6000, 6000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(19)), 5500L);
                        break;
                    }
                    case 19: {
                        Frintezza.this.a(Frintezza.this.i, 800, 160, 5, 1000, 10000, 2);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(20)), 2100L);
                        break;
                    }
                    case 20: {
                        Frintezza.this.a(Frintezza.this.i, 300, 60, 8, 0, 10000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(21)), 2000L);
                        break;
                    }
                    case 21: {
                        Frintezza.this.a(Frintezza.this.i, 1000, 90, 10, 3000, 5000, 0);
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(22)), 3000L);
                        break;
                    }
                    case 22: {
                        for (Player player : Frintezza.this.getPlayers()) {
                            player.leaveMovieMode();
                        }
                        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(23)), 2000L);
                        break;
                    }
                    case 23: {
                        Frintezza.this.a(false);
                        Frintezza.this.a(new NpcLocation(174056, -76024, -5104, 0, 29061));
                        Frintezza.this.bu = 1;
                        Frintezza.this.o = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Music()), 5000L);
                    }
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private class FrintezzaStart
    extends RunnableImpl {
        private FrintezzaStart() {
        }

        public void runImpl() throws Exception {
            ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Spawn(1)), 1000L);
        }
    }
}
