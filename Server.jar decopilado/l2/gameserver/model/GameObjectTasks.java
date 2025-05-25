/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.model;

import java.util.List;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.ai.NextAction;
import l2.gameserver.ai.PlayableAI;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.MagicSkillLaunched;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.StringUtils;

public class GameObjectTasks {

    public static class NotifyAITask
    extends RunnableImpl {
        private final CtrlEvent a;
        private final Object g;
        private final Object h;
        private final HardReference<? extends Creature> K;

        public NotifyAITask(Creature creature, CtrlEvent ctrlEvent, Object object, Object object2) {
            this.K = creature.getRef();
            this.a = ctrlEvent;
            this.g = object;
            this.h = object2;
        }

        public NotifyAITask(Creature creature, CtrlEvent ctrlEvent, Object object) {
            this(creature, ctrlEvent, object, null);
        }

        public NotifyAITask(Creature creature, CtrlEvent ctrlEvent) {
            this(creature, ctrlEvent, null, null);
        }

        @Override
        public void runImpl() {
            Creature creature = this.K.get();
            if (creature == null || !creature.hasAI()) {
                return;
            }
            creature.getAI().notifyEvent(this.a, this.g, this.h);
        }
    }

    public static class MagicLaunchedTask
    extends RunnableImpl {
        public boolean _forceUse;
        private final HardReference<? extends Creature> I;

        public MagicLaunchedTask(Creature creature, boolean bl) {
            this.I = creature.getRef();
            this._forceUse = bl;
        }

        @Override
        public void runImpl() {
            Creature creature = this.I.get();
            if (creature == null) {
                return;
            }
            Skill skill = creature.getCastingSkill();
            Creature creature2 = creature.getCastingTarget();
            if (skill == null || creature2 == null) {
                creature.clearCastVars();
                return;
            }
            if (!skill.checkCondition(creature, creature2, this._forceUse, false, false)) {
                creature.abortCast(true, false);
                return;
            }
            List<Creature> list = skill.getTargets(creature, creature2, this._forceUse);
            creature.broadcastPacket(new MagicSkillLaunched(creature, skill, list));
        }
    }

    public static class MagicUseTask
    extends RunnableImpl {
        public boolean _forceUse;
        private final HardReference<? extends Creature> J;

        public MagicUseTask(Creature creature, boolean bl) {
            this.J = creature.getRef();
            this._forceUse = bl;
        }

        @Override
        public void runImpl() {
            Creature creature = this.J.get();
            if (creature == null) {
                return;
            }
            Skill skill = creature.getCastingSkill();
            Creature creature2 = creature.getCastingTarget();
            if (skill == null || creature2 == null) {
                creature.clearCastVars();
                return;
            }
            creature.onMagicUseTimer(creature2, skill, this._forceUse);
        }
    }

    public static class ActReadyTask
    extends RunnableImpl {
        private final HardReference<? extends Creature> w;

        public ActReadyTask(Creature creature) {
            this.w = creature.getRef();
        }

        @Override
        public void runImpl() throws Exception {
            Creature creature = this.w.get();
            if (creature == null) {
                return;
            }
            creature.getAI().notifyEvent(CtrlEvent.EVT_READY_TO_ACT);
        }
    }

    public static class HitTask
    extends RunnableImpl {
        boolean _crit;
        boolean _miss;
        boolean _shld;
        boolean _soulshot;
        boolean _unchargeSS;
        boolean _notify;
        boolean _byBow;
        int _damage;
        long _actDelay;
        private final HardReference<? extends Creature> E;
        private final HardReference<? extends Creature> F;

        public HitTask(Creature creature, Creature creature2, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6) {
            this.E = creature.getRef();
            this.F = creature2.getRef();
            this._damage = n;
            this._crit = bl;
            this._shld = bl4;
            this._miss = bl2;
            this._soulshot = bl3;
            this._unchargeSS = bl5;
            this._notify = bl6;
            this._actDelay = 0L;
            this._byBow = false;
        }

        public HitTask(Creature creature, Creature creature2, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, long l) {
            this.E = creature.getRef();
            this.F = creature2.getRef();
            this._damage = n;
            this._crit = bl;
            this._shld = bl4;
            this._miss = bl2;
            this._soulshot = bl3;
            this._unchargeSS = bl5;
            this._notify = bl6;
            this._actDelay = l;
            this._byBow = false;
        }

        public HitTask(Creature creature, Creature creature2, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7, long l) {
            this.E = creature.getRef();
            this.F = creature2.getRef();
            this._damage = n;
            this._crit = bl;
            this._shld = bl4;
            this._miss = bl2;
            this._soulshot = bl3;
            this._unchargeSS = bl5;
            this._notify = bl7;
            this._actDelay = l;
            this._byBow = bl6;
        }

        @Override
        public void runImpl() {
            Creature creature;
            Creature creature2 = this.E.get();
            if (creature2 == null || (creature = this.F.get()) == null) {
                return;
            }
            if (creature2.isAttackAborted()) {
                return;
            }
            creature2.onHitTimer(creature, this._damage, this._crit, this._miss, this._soulshot, this._shld, this._unchargeSS);
            if (this._notify) {
                if (creature2.isPlayable() && ((PlayableAI)creature2.getAI()).getNextAction() == NextAction.MOVE) {
                    creature2.getAI().notifyEvent(CtrlEvent.EVT_READY_TO_ACT);
                } else if (this._actDelay > 0L) {
                    ThreadPoolManager.getInstance().schedule(new ActReadyTask(creature2), this._actDelay);
                } else {
                    creature2.getAI().notifyEvent(CtrlEvent.EVT_READY_TO_ACT);
                }
            }
        }
    }

    public static class CastEndTimeTask
    extends RunnableImpl {
        private final HardReference<? extends Creature> z;

        public CastEndTimeTask(Creature creature) {
            this.z = creature.getRef();
        }

        @Override
        public void runImpl() {
            Creature creature = this.z.get();
            if (creature == null) {
                return;
            }
            creature.onCastEndTime();
        }
    }

    public static class AltMagicUseTask
    extends RunnableImpl {
        public final Skill _skill;
        private final HardReference<? extends Creature> x;
        private final HardReference<? extends Creature> y;

        public AltMagicUseTask(Creature creature, Creature creature2, Skill skill) {
            this.x = creature.getRef();
            this.y = creature2.getRef();
            this._skill = skill;
        }

        @Override
        public void runImpl() {
            Creature creature;
            Creature creature2 = this.x.get();
            if (creature2 == null || (creature = this.y.get()) == null) {
                return;
            }
            creature2.altOnMagicUseTimer(creature, this._skill);
        }
    }

    public static class EndCustomHeroTask
    extends RunnableImpl {
        private final HardReference<Player> B;

        public EndCustomHeroTask(Player player) {
            this.B = player.getRef();
        }

        @Override
        public void runImpl() {
            Player player = this.B.get();
            if (player == null) {
                return;
            }
            if (player.getVar("CustomHeroEndTime") == null || HeroController.getInstance().isCurrentHero(player)) {
                return;
            }
            player.setHero(false);
            HeroController.removeSkills(player);
            HeroController.checkHeroWeaponary(player);
            player.broadcastUserInfo(true, new UserInfoType[0]);
        }
    }

    public static class EndStandUpTask
    extends RunnableImpl {
        private final HardReference<Player> D;

        public EndStandUpTask(Player player) {
            this.D = player.getRef();
        }

        @Override
        public void runImpl() {
            Player player = this.D.get();
            if (player == null) {
                return;
            }
            player.sittingTaskLaunched = false;
            player.setSitting(false);
            if (!player.getAI().setNextIntention()) {
                player.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            }
        }
    }

    public static class EndSitDownTask
    extends RunnableImpl {
        private final HardReference<Player> C;

        public EndSitDownTask(Player player) {
            this.C = player.getRef();
        }

        @Override
        public void runImpl() {
            Player player = this.C.get();
            if (player == null) {
                return;
            }
            player.sittingTaskLaunched = false;
            player.getAI().clearNextAction();
        }
    }

    public static class UnJailTask
    extends RunnableImpl {
        private final HardReference<Player> N;

        public UnJailTask(Player player) {
            this.N = player.getRef();
        }

        @Override
        public void runImpl() {
            Player player = this.N.get();
            if (player == null) {
                return;
            }
            String string = player.getVar("jailed");
            if (StringUtils.isBlank((CharSequence)string)) {
                player.teleToClosestTown();
                return;
            }
            Location location = Location.parseLoc(string.substring(string.indexOf(59) + 1));
            Reflection reflection = null;
            if (location.getH() != 0) {
                reflection = ReflectionManager.getInstance().get(location.getH());
            }
            player.standUp();
            if (reflection != null) {
                if (reflection.isCollapseStarted()) {
                    player.teleToClosestTown();
                } else {
                    player.teleToLocation(location.getX(), location.getY(), location.getZ(), reflection);
                }
            } else {
                player.teleToLocation(location.getX(), location.getY(), location.getZ(), ReflectionManager.DEFAULT);
            }
            player.unblock();
            player.unsetVar("jailed");
        }
    }

    public static class KickTask
    extends RunnableImpl {
        private final HardReference<Player> H;

        public KickTask(Player player) {
            this.H = player.getRef();
        }

        @Override
        public void runImpl() {
            Player player = this.H.get();
            if (player == null) {
                return;
            }
            player.setOfflineMode(false);
            player.kick();
        }
    }

    public static class WaterTask
    extends RunnableImpl {
        private final HardReference<Player> O;

        public WaterTask(Player player) {
            this.O = player.getRef();
        }

        @Override
        public void runImpl() {
            Player player = this.O.get();
            if (player == null) {
                return;
            }
            if (player.isDead() || !player.isInWater()) {
                player.stopWaterTask();
                return;
            }
            double d = player.getMaxHp() < 100 ? 1.0 : (double)(player.getMaxHp() / 100);
            player.reduceCurrentHp(d, player, null, false, false, true, false, false, false, false);
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_TAKEN_S1_DAMAGE_BECAUSE_YOU_WERE_UNABLE_TO_BREATHE).addNumber((long)d));
        }
    }

    public static class HourlyTask
    extends RunnableImpl {
        private final HardReference<Player> G;

        public HourlyTask(Player player) {
            this.G = player.getRef();
        }

        @Override
        public void runImpl() {
            Player player = this.G.get();
            if (player == null) {
                return;
            }
            int n = player.getHoursInGame();
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_BEEN_PLAYING_FOR_AN_EXTENDED_PERIOD_OF_TIME_PLEASE_CONSIDER_TAKING_A_BREAK_S1).addNumber(n));
        }
    }

    public static class PvPFlagTask
    extends RunnableImpl {
        private final HardReference<Player> L;

        public PvPFlagTask(Player player) {
            this.L = player.getRef();
        }

        @Override
        public void runImpl() {
            Player player = this.L.get();
            if (player == null) {
                return;
            }
            long l = Math.abs(System.currentTimeMillis() - player.getlastPvpAttack());
            if (l > (long)(Config.PVP_TIME + Config.PVP_BLINKING_UNFLAG_TIME)) {
                player.stopPvPFlag();
            } else if (l > (long)Config.PVP_TIME) {
                player.updatePvPFlag(2);
            } else {
                player.updatePvPFlag(1);
            }
        }
    }

    public static class SoulConsumeTask
    extends RunnableImpl {
        private final HardReference<Player> M;

        public SoulConsumeTask(Player player) {
            this.M = player.getRef();
        }

        @Override
        public void runImpl() {
            Player player = this.M.get();
            if (player == null) {
                return;
            }
            player.setConsumedSouls(player.getConsumedSouls() + 1, null);
        }
    }

    public static class DeleteTask
    extends RunnableImpl {
        private final HardReference<? extends Creature> A;

        public DeleteTask(Creature creature) {
            this.A = creature.getRef();
        }

        @Override
        public void runImpl() {
            Creature creature = this.A.get();
            if (creature != null) {
                creature.deleteMe();
            }
        }
    }
}
