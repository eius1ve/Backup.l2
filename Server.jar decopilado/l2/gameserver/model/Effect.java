/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.listener.actor.OnAttackListener;
import l2.gameserver.listener.actor.OnMagicUseListener;
import l2.gameserver.listener.inventory.OnEquipListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.AbnormalStatusUpdate;
import l2.gameserver.network.l2.s2c.ExAbnormalStatusUpdateFromTarget;
import l2.gameserver.network.l2.s2c.ExOlympiadSpelledInfo;
import l2.gameserver.network.l2.s2c.PartySpelled;
import l2.gameserver.network.l2.s2c.ShortBuffStatusUpdate;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.skills.AbnormalEffect;
import l2.gameserver.skills.EffectType;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.stats.funcs.FuncOwner;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.taskmanager.EffectTaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class Effect
extends RunnableImpl
implements Comparable<Effect>,
FuncOwner {
    protected static final Logger _log = LoggerFactory.getLogger(Effect.class);
    public static final Effect[] EMPTY_L2EFFECT_ARRAY = new Effect[0];
    public static int SUSPENDED = -1;
    public static int STARTING = 0;
    public static int STARTED = 1;
    public static int ACTING = 2;
    public static int FINISHING = 3;
    public static int FINISHED = 4;
    protected final Creature _effector;
    protected final Creature _effected;
    protected final Skill _skill;
    protected final int _displayId;
    protected final int _displayLevel;
    private final double _value;
    private final AtomicInteger m;
    private int gT;
    private long ba;
    private long bb;
    private long bc;
    private boolean bv = false;
    private Effect a;
    private boolean T = false;
    protected final EffectTemplate _template;
    private Future<?> i;
    private final EEffectSlot a;
    private ActionDispelListener a;
    private ActionDispelOnItemUnequipListener a = null;

    protected Effect(Env env, EffectTemplate effectTemplate) {
        this._skill = env.skill;
        this._effector = env.character;
        this._effected = env.target;
        this._template = effectTemplate;
        this._value = effectTemplate._value;
        this.gT = effectTemplate.getCount();
        this.ba = effectTemplate.getPeriod();
        this.bc = this.ba * (long)this.gT;
        this._displayId = effectTemplate._displayId != 0 ? effectTemplate._displayId : this._skill.getDisplayId();
        this._displayLevel = effectTemplate._displayLevel != 0 ? effectTemplate._displayLevel : this._skill.getDisplayLevel();
        this.m = new AtomicInteger(STARTING);
        this.a = EEffectSlot.getBySkill(this._skill);
    }

    public long getPeriod() {
        return this.ba;
    }

    public void setPeriod(long l) {
        this.ba = l;
        this.bc = this.ba * (long)this.gT;
    }

    public int getCount() {
        return this.gT;
    }

    public void setCount(int n) {
        this.gT = n;
        this.bc = this.ba * (long)this.gT;
    }

    public boolean isOneTime() {
        return this.ba == 0L;
    }

    public long getStartTime() {
        if (this.bb == 0L) {
            return System.currentTimeMillis();
        }
        return this.bb;
    }

    public long getTime() {
        return System.currentTimeMillis() - this.getStartTime();
    }

    public long getDuration() {
        return this.bc;
    }

    public int getTimeLeft() {
        return (int)((this.getDuration() - this.getTime()) / 1000L);
    }

    public boolean isTimeLeft() {
        return this.getDuration() - this.getTime() > 0L;
    }

    public boolean isInUse() {
        return this.bv;
    }

    public void setInUse(boolean bl) {
        this.bv = bl;
    }

    public boolean isActive() {
        return this.T;
    }

    public void setActive(boolean bl) {
        this.T = bl;
    }

    public EffectTemplate getTemplate() {
        return this._template;
    }

    public EEffectSlot getEffectSlot() {
        return this.a;
    }

    public String getStackType() {
        return this.getTemplate()._stackType;
    }

    public String getStackType2() {
        return this.getTemplate()._stackType2;
    }

    public boolean isStackTypeMatch(String ... stringArray) {
        String string = this.getStackType();
        String string2 = this.getStackType2();
        for (int i = 0; i < stringArray.length; ++i) {
            if (stringArray[i].equalsIgnoreCase(string)) {
                return true;
            }
            if (!stringArray[i].equalsIgnoreCase(string2)) continue;
            return true;
        }
        return false;
    }

    public boolean isStackTypeMatch(Effect effect) {
        return this.isStackTypeMatch(effect.getStackType()) || this.isStackTypeMatch(effect.getStackType2());
    }

    public int getStackOrder() {
        return this.getTemplate()._stackOrder;
    }

    public Skill getSkill() {
        return this._skill;
    }

    public Creature getEffector() {
        return this._effector;
    }

    public Creature getEffected() {
        return this._effected;
    }

    public double calc() {
        return this._value;
    }

    public boolean isEnded() {
        return this.isFinished() || this.isFinishing();
    }

    public boolean isFinishing() {
        return this.getState() == FINISHING;
    }

    public boolean isFinished() {
        return this.getState() == FINISHED;
    }

    private int getState() {
        return this.m.get();
    }

    private boolean c(int n, int n2) {
        return this.m.compareAndSet(n, n2);
    }

    public boolean checkCondition() {
        return true;
    }

    protected void onStart() {
        Creature creature = this.getEffected();
        creature.addStatFuncs(this.getStatFuncs());
        creature.addTriggers(this.getTemplate());
        if (!this.getTemplate()._abnormalEffect.isEmpty()) {
            for (AbnormalEffect abnormalEffect : this.getTemplate()._abnormalEffect) {
                creature.startAbnormalEffect(abnormalEffect);
            }
        } else if (this.getEffectType().getAbnormal() != null) {
            creature.startAbnormalEffect(this.getEffectType().getAbnormal());
        }
        if (this._template._cancelOnAction) {
            this.a = new ActionDispelListener();
            this.getEffected().addListener(this.a);
        }
        if (this.getEffected().isPlayer()) {
            Player player = this.getEffected().getPlayer();
            if (this._template._cancelOnItem > 0) {
                this.a = new ActionDispelOnItemUnequipListener();
                player.getInventory().addListener(this.a);
            }
            if (!this.getSkill().canUseTeleport()) {
                player.getPlayerAccess().UseTeleport = false;
            }
        }
    }

    protected abstract boolean onActionTime();

    protected void onExit() {
        Creature creature = this.getEffected();
        this.getEffected().removeStatsOwner(this);
        creature.removeTriggers(this.getTemplate());
        if (!this.getTemplate()._abnormalEffect.isEmpty()) {
            for (AbnormalEffect abnormalEffect : this.getTemplate()._abnormalEffect) {
                creature.stopAbnormalEffect(abnormalEffect);
            }
        } else if (this.getEffectType().getAbnormal() != null) {
            creature.stopAbnormalEffect(this.getEffectType().getAbnormal());
        }
        if (this._template._cancelOnAction) {
            this.getEffected().removeListener(this.a);
        }
        if (this.getEffected().isPlayer()) {
            Player player = this.getEffected().getPlayer();
            if (this.a != null) {
                player.getInventory().removeListener(this.a);
            }
            if (this.isStackTypeMatch("HpRecoverCast")) {
                player.sendPacket((IStaticPacket)new ShortBuffStatusUpdate());
            }
            if (!this.getSkill().canUseTeleport() && !player.getPlayerAccess().UseTeleport) {
                player.getPlayerAccess().UseTeleport = true;
            }
        }
    }

    private void aW() {
        if (this.i != null) {
            this.i.cancel(false);
        }
    }

    private void aX() {
        if (this.i == null) {
            this.bb = System.currentTimeMillis();
            this.i = EffectTaskManager.getInstance().scheduleAtFixedRate(this, this.ba, this.ba);
        }
    }

    public final void schedule() {
        Creature creature = this.getEffected();
        if (creature == null) {
            return;
        }
        if (!this.checkCondition()) {
            return;
        }
        this.getEffected().getEffectList().addEffect(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void aY() {
        if (this.c(STARTING, SUSPENDED)) {
            this.aX();
        } else if (this.c(STARTED, SUSPENDED) || this.c(ACTING, SUSPENDED)) {
            Effect effect = this;
            synchronized (effect) {
                if (this.isInUse()) {
                    this.setInUse(false);
                    this.setActive(false);
                    this.onExit();
                }
            }
            this.getEffected().getEffectList().removeEffect(this);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void start() {
        if (this.c(STARTING, STARTED)) {
            Effect effect = this;
            synchronized (effect) {
                if (this.isInUse()) {
                    this.setActive(true);
                    this.onStart();
                    this.aX();
                }
            }
        }
        this.run();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void runImpl() throws Exception {
        if (this.c(STARTED, ACTING)) {
            if (!this.getSkill().isHideStartMessage() && !this.getSkill().isToggle() && this.getEffected().getEffectList().getEffectsCountForSkill(this.getSkill().getId()) == 1) {
                this.getEffected().sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1S_EFFECT_CAN_BE_FELT).addSkillName(this._displayId, this._displayLevel));
            }
            if (this.getSkill().getSecondSkill() > 0) {
                SkillTable.getInstance().getInfo(this.getSkill().getSecondSkill(), 1).getEffects(this._effector, this._effected, false, false);
            }
            return;
        }
        if (this.getState() == SUSPENDED) {
            if (this.isTimeLeft()) {
                --this.gT;
                if (this.isTimeLeft()) {
                    return;
                }
            }
            this.exit();
            return;
        }
        if (this.getState() == ACTING && this.isTimeLeft()) {
            --this.gT;
            if ((!this.isActive() || this.onActionTime()) && this.isTimeLeft()) {
                return;
            }
        }
        if (this.c(ACTING, FINISHING)) {
            this.setInUse(false);
        }
        if (this.c(FINISHING, FINISHED)) {
            Effect effect = this;
            synchronized (effect) {
                this.setActive(false);
                this.aW();
                this.onExit();
            }
            effect = this.getNext();
            if (effect != null && effect.c(SUSPENDED, STARTING)) {
                effect.schedule();
            }
            if (this.getSkill().getDelayedEffect() > 0) {
                SkillTable.getInstance().getInfo(this.getSkill().getDelayedEffect(), 1).getEffects(this._effector, this._effected, false, false);
            }
            boolean bl = !this.isHidden() && this.getEffected().getEffectList().getEffectsCountForSkill(this.getSkill().getId()) == 1;
            this.getEffected().getEffectList().removeEffect(this);
            if (bl) {
                this.getEffected().sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_WORN_OFF).addSkillName(this._displayId, this._displayLevel));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void exit() {
        Effect effect = this.getNext();
        if (effect != null) {
            effect.exit();
        }
        this.aZ();
        if (this.c(STARTING, FINISHED)) {
            this.getEffected().getEffectList().removeEffect(this);
        } else if (this.c(SUSPENDED, FINISHED)) {
            this.aW();
        } else if (this.c(STARTED, FINISHED) || this.c(ACTING, FINISHED)) {
            Effect effect2 = this;
            synchronized (effect2) {
                if (this.isInUse()) {
                    this.setInUse(false);
                    this.setActive(false);
                    this.aW();
                    this.onExit();
                }
            }
            this.getEffected().getEffectList().removeEffect(this);
        }
    }

    private boolean a(Effect effect) {
        if (effect == null || effect.isEnded()) {
            return false;
        }
        Effect effect2 = this.getNext();
        if (effect2 != null && !effect2.maybeScheduleNext(effect)) {
            return false;
        }
        this.a = effect;
        return true;
    }

    public Effect getNext() {
        return this.a;
    }

    private void aZ() {
        this.a = null;
    }

    public boolean maybeScheduleNext(Effect effect) {
        if (effect.getStackOrder() < this.getStackOrder()) {
            if (effect.getTimeLeft() > this.getTimeLeft()) {
                effect.aY();
                this.a(effect);
            }
            return false;
        }
        if (effect.getTimeLeft() >= this.getTimeLeft()) {
            if (this.getNext() != null && this.getNext().getTimeLeft() > effect.getTimeLeft()) {
                effect.a(this.getNext());
                this.aZ();
            }
            this.exit();
        } else {
            this.aY();
            effect.a(this);
        }
        return true;
    }

    public Func[] getStatFuncs() {
        return this.getTemplate().getStatFuncs(this);
    }

    public void addIcon(AbnormalStatusUpdate abnormalStatusUpdate) {
        if (!this.isActive() || this.isHidden()) {
            return;
        }
        int n = this._skill.isToggle() ? -1 : this.getTimeLeft();
        int n2 = this.getDisplayLevel();
        if (n2 < 100) {
            abnormalStatusUpdate.addEffect(this.getDisplayId(), n2, n);
        } else {
            abnormalStatusUpdate.addEffect(this.getDisplayId(), this.getSkill() != null ? this.getSkill().getBaseLevel() : 1, n);
        }
    }

    public void addIcon(ExAbnormalStatusUpdateFromTarget exAbnormalStatusUpdateFromTarget) {
        if (!this.isActive() || this.isHidden()) {
            return;
        }
        exAbnormalStatusUpdateFromTarget.addEffect(this);
    }

    public void addPartySpelledIcon(PartySpelled partySpelled) {
        if (!this.isActive() || this.isHidden()) {
            return;
        }
        int n = this._skill.isToggle() ? -1 : this.getTimeLeft();
        int n2 = this.getDisplayLevel();
        if (n2 < 100) {
            partySpelled.addPartySpelledEffect(this._displayId, n2, n);
        } else {
            partySpelled.addPartySpelledEffect(this._displayId, this.getSkill() != null ? this.getSkill().getBaseLevel() : 1, n);
        }
    }

    public void addOlympiadSpelledIcon(Player player, ExOlympiadSpelledInfo exOlympiadSpelledInfo) {
        if (!this.isActive() || this.isHidden()) {
            return;
        }
        int n = this._skill.isToggle() ? -1 : this.getTimeLeft();
        exOlympiadSpelledInfo.addSpellRecivedPlayer(player);
        int n2 = this.getDisplayLevel();
        if (n2 < 100) {
            exOlympiadSpelledInfo.addEffect(this._displayId, n2, n);
        } else {
            exOlympiadSpelledInfo.addEffect(this._displayId, this.getSkill() != null ? this.getSkill().getBaseLevel() : 1, n);
        }
    }

    protected int getLevel() {
        return this._skill.getLevel();
    }

    public EffectType getEffectType() {
        return this.getTemplate()._effectType;
    }

    public boolean isHidden() {
        return this._displayId < 0;
    }

    @Override
    public int compareTo(Effect effect) {
        if (effect.equals(this)) {
            return 0;
        }
        return 1;
    }

    public boolean isSaveable() {
        return this._template.isSaveable(this.getSkill().isSaveable()) && this.getTimeLeft() >= Config.ALT_SAVE_EFFECTS_REMAINING_TIME;
    }

    public int getDisplayId() {
        return this._displayId;
    }

    public int getDisplayLevel() {
        return this._displayLevel;
    }

    public boolean isCancelable() {
        return this._template.isCancelable(this.getSkill().isCancelable());
    }

    public String toString() {
        return "Skill: " + this._skill + ", state: " + this.getState() + ", inUse: " + this.bv + ", active : " + this.T;
    }

    @Override
    public boolean isFuncEnabled() {
        return this.isInUse();
    }

    @Override
    public boolean overrideLimits() {
        return false;
    }

    public boolean isOffensive() {
        return this._template.isOffensive(this.getSkill().isOffensive());
    }

    public static final class EEffectSlot
    extends Enum<EEffectSlot> {
        public static final /* enum */ EEffectSlot EFFECT_SLOT_NORMAL = new EEffectSlot();
        public static final /* enum */ EEffectSlot EFFECT_SLOT_SONG_DANCE = new EEffectSlot();
        public static final /* enum */ EEffectSlot EFFECT_SLOT_TOGGLE = new EEffectSlot();
        public static final /* enum */ EEffectSlot EFFECT_SLOT_TRIGGER = new EEffectSlot();
        public static final /* enum */ EEffectSlot EFFECT_SLOT_ETC = new EEffectSlot();
        public static final /* enum */ EEffectSlot EFFECT_SLOT_DEBUFF = new EEffectSlot();
        public static final EEffectSlot[] VALUES;
        private static final /* synthetic */ EEffectSlot[] a;

        public static EEffectSlot[] values() {
            return (EEffectSlot[])a.clone();
        }

        public static EEffectSlot valueOf(String string) {
            return Enum.valueOf(EEffectSlot.class, string);
        }

        public static EEffectSlot getBySkill(Skill skill) {
            if (Config.ALT_BUFF_SLOT_SEPARATE) {
                if (skill.isToggle()) {
                    return EFFECT_SLOT_TOGGLE;
                }
                if (skill.isMusic()) {
                    return EFFECT_SLOT_SONG_DANCE;
                }
                if (skill.isOffensive()) {
                    return EFFECT_SLOT_DEBUFF;
                }
                if (skill.isTrigger()) {
                    return EFFECT_SLOT_TRIGGER;
                }
                if (skill.isCommon()) {
                    return EFFECT_SLOT_ETC;
                }
                return EFFECT_SLOT_NORMAL;
            }
            if (skill.isOffensive()) {
                return EFFECT_SLOT_DEBUFF;
            }
            return EFFECT_SLOT_NORMAL;
        }

        private static /* synthetic */ EEffectSlot[] a() {
            return new EEffectSlot[]{EFFECT_SLOT_NORMAL, EFFECT_SLOT_SONG_DANCE, EFFECT_SLOT_TOGGLE, EFFECT_SLOT_TRIGGER, EFFECT_SLOT_ETC, EFFECT_SLOT_DEBUFF};
        }

        static {
            a = EEffectSlot.a();
            VALUES = EEffectSlot.values();
        }
    }

    private class ActionDispelListener
    implements OnAttackListener,
    OnMagicUseListener {
        private ActionDispelListener() {
        }

        @Override
        public void onMagicUse(Creature creature, Skill skill, Creature creature2, boolean bl) {
            Effect.this.exit();
        }

        @Override
        public void onAttack(Creature creature, Creature creature2) {
            Effect.this.exit();
        }
    }

    private class ActionDispelOnItemUnequipListener
    implements OnEquipListener {
        private ActionDispelOnItemUnequipListener() {
        }

        @Override
        public void onEquip(int n, ItemInstance itemInstance, Playable playable) {
        }

        @Override
        public void onUnequip(int n, ItemInstance itemInstance, Playable playable) {
            if (itemInstance.isWeapon() && (Effect.this.getTemplate()._cancelOnItem & 1) != 0 || itemInstance.isArmor() && (Effect.this.getTemplate()._cancelOnItem & 2) != 0) {
                Effect.this.exit();
            }
        }
    }
}
