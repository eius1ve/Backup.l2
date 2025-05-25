/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntArrayList
 *  gnu.trove.TIntHashSet
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.model;

import gnu.trove.TIntArrayList;
import gnu.trove.TIntHashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.skills.EffectType;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.skills.skillclasses.Transformation;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.FuncTemplate;
import l2.gameserver.utils.EffectsComparator;
import org.apache.commons.lang3.ArrayUtils;

public class EffectList {
    public static final int NONE_SLOT_TYPE = -1;
    public static final int BUFF_SLOT_TYPE = 0;
    public static final int MUSIC_SLOT_TYPE = 1;
    public static final int TRIGGER_SLOT_TYPE = 2;
    public static final int DEBUFF_SLOT_TYPE = 3;
    public static final int DEBUFF_LIMIT = 8;
    public static final int MUSIC_LIMIT = 12;
    public static final int TRIGGER_LIMIT = 12;
    private Creature _actor;
    private List<Effect> aP;
    private Lock g = new ReentrantLock();

    public EffectList(Creature creature) {
        this._actor = creature;
    }

    public int getEffectsCountForSkill(int n) {
        if (this.isEmpty()) {
            return 0;
        }
        int n2 = 0;
        for (Effect effect : this.aP) {
            if (effect.getSkill().getId() != n) continue;
            ++n2;
        }
        return n2;
    }

    public Effect getEffectByType(EffectType effectType) {
        if (this.isEmpty()) {
            return null;
        }
        for (Effect effect : this.aP) {
            if (effect.getEffectType() != effectType) continue;
            return effect;
        }
        return null;
    }

    public Effect getFirstEffectBySkillId(int n) {
        if (this.isEmpty()) {
            return null;
        }
        for (Effect effect : this.aP) {
            if (effect.getSkill().getId() != n) continue;
            return effect;
        }
        return null;
    }

    public Effect getFirstEffectBySkill(Skill skill) {
        return skill != null ? this.getFirstEffectBySkillId(skill.getId()) : null;
    }

    public List<Effect> getEffectsBySkill(Skill skill) {
        return skill != null ? this.getEffectsBySkillId(skill.getId()) : null;
    }

    public int getActiveMusicCount(int n) {
        if (this.isEmpty()) {
            return 0;
        }
        int n2 = 0;
        for (Effect effect : this.aP) {
            if (!Config.ALT_ADDITIONAL_DANCE_SONG_MANA_CONSUME || !effect.getSkill().isMusic() || effect.getSkill().getId() == n || effect.getTimeLeft() <= Config.ALT_MUSIC_COST_GUARD_INTERVAL) continue;
            ++n2;
        }
        return n2;
    }

    public List<Effect> getEffectsBySkillId(int n) {
        if (this.isEmpty()) {
            return null;
        }
        ArrayList<Effect> arrayList = new ArrayList<Effect>(2);
        for (Effect effect : this.aP) {
            if (effect.getSkill().getId() != n) continue;
            arrayList.add(effect);
        }
        return arrayList.isEmpty() ? null : arrayList;
    }

    public Effect getEffectByIndexAndType(int n, EffectType effectType) {
        if (this.isEmpty()) {
            return null;
        }
        for (Effect effect : this.aP) {
            if (effect.getSkill().getId() != n || effect.getEffectType() != effectType) continue;
            return effect;
        }
        return null;
    }

    public Effect getEffectByStackType(String string) {
        if (this.isEmpty()) {
            return null;
        }
        for (Effect effect : this.aP) {
            if (!effect.getStackType().equals(string)) continue;
            return effect;
        }
        return null;
    }

    public boolean containEffectFromSkills(int ... nArray) {
        if (this.isEmpty()) {
            return false;
        }
        for (Effect effect : this.aP) {
            int n = effect.getSkill().getId();
            if (!ArrayUtils.contains((int[])nArray, (int)n)) continue;
            return true;
        }
        return false;
    }

    public List<Effect> getAllEffects() {
        if (this.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<Effect>(this.aP);
    }

    public boolean isEmpty() {
        return this.aP == null || this.aP.isEmpty();
    }

    public Collection<Effect> getAllFirstEffects() {
        if (this.isEmpty()) {
            return Collections.emptyList();
        }
        LinkedHashMap<Skill, Effect> linkedHashMap = new LinkedHashMap<Skill, Effect>();
        for (Effect effect : this.aP) {
            if (linkedHashMap.containsKey(effect.getSkill())) continue;
            linkedHashMap.put(effect.getSkill(), effect);
        }
        ArrayList arrayList = new ArrayList(linkedHashMap.values());
        arrayList.sort(EffectsComparator.getInstance());
        return Collections.unmodifiableCollection(arrayList);
    }

    private void a(Effect effect) {
        if (this.aP == null) {
            return;
        }
        int n = EffectList.getSlotType(effect);
        if (n == -1) {
            return;
        }
        int n2 = 0;
        TIntArrayList tIntArrayList = new TIntArrayList();
        for (Effect effect2 : this.aP) {
            int n3;
            if (!effect2.isInUse()) continue;
            if (effect2.getSkill().equals(effect.getSkill())) {
                return;
            }
            if (tIntArrayList.contains(effect2.getSkill().getId()) || (n3 = EffectList.getSlotType(effect2)) != n) continue;
            ++n2;
            tIntArrayList.add(effect2.getSkill().getId());
        }
        int n4 = 0;
        switch (n) {
            case 0: {
                n4 = this._actor.getBuffLimit();
                break;
            }
            case 1: {
                if (!Config.ALT_BUFF_SLOT_SEPARATE) break;
                n4 = Config.ALT_MUSIC_LIMIT;
                break;
            }
            case 3: {
                n4 = Config.ALT_DEBUFF_LIMIT;
                break;
            }
            case 2: {
                n4 = Config.ALT_TRIGGER_LIMIT;
            }
        }
        if (n2 < n4) {
            return;
        }
        int n5 = 0;
        for (Effect effect3 : this.aP) {
            if (!effect3.isInUse() || EffectList.getSlotType(effect3) != n) continue;
            n5 = effect3.getSkill().getId();
            break;
        }
        if (n5 != 0) {
            this.stopEffect(n5);
        }
    }

    public static int getSlotType(Effect effect) {
        if (effect.getSkill().isPassive() || effect.getSkill().isSlotNone() || effect.getSkill().isToggle() || effect.getSkill() instanceof Transformation || effect.isStackTypeMatch("HpRecoverCast") || effect.getEffectType() == EffectType.Cubic) {
            return -1;
        }
        if (effect.getSkill().isOffensive()) {
            return 3;
        }
        if (effect.getSkill().isMusic() && Config.ALT_BUFF_SLOT_SEPARATE) {
            return 1;
        }
        if (effect.getSkill().isTrigger()) {
            return 2;
        }
        return 0;
    }

    public static boolean checkStackType(EffectTemplate effectTemplate, EffectTemplate effectTemplate2) {
        if (!effectTemplate._stackType.equals("none") && effectTemplate._stackType.equalsIgnoreCase(effectTemplate2._stackType)) {
            return true;
        }
        if (!effectTemplate._stackType.equals("none") && effectTemplate._stackType.equalsIgnoreCase(effectTemplate2._stackType2)) {
            return true;
        }
        if (!effectTemplate._stackType2.equals("none") && effectTemplate._stackType2.equalsIgnoreCase(effectTemplate2._stackType)) {
            return true;
        }
        return !effectTemplate._stackType2.equals("none") && effectTemplate._stackType2.equalsIgnoreCase(effectTemplate2._stackType2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addEffect(Effect effect) {
        double d = this._actor.getCurrentHp();
        double d2 = this._actor.getCurrentMp();
        double d3 = this._actor.getCurrentCp();
        String string = effect.getStackType();
        boolean bl = false;
        HashSet<Skill> hashSet = new HashSet<Skill>();
        this.g.lock();
        try {
            if (this.aP == null) {
                this.aP = new CopyOnWriteArrayList<Effect>();
            }
            if (string.equals("none")) {
                for (Effect object : this.aP) {
                    if (!object.isInUse()) continue;
                    if (object.getSkill().getId() != effect.getSkill().getId() || object.getEffectType() != effect.getEffectType() || !object.getStackType().equals("none")) continue;
                    if (effect.getTimeLeft() > object.getTimeLeft()) {
                        hashSet.add(object.getSkill());
                        object.exit();
                        continue;
                    }
                    return;
                }
            } else {
                for (Effect effect2 : this.aP) {
                    if (!effect2.isInUse() || !EffectList.checkStackType(effect2.getTemplate(), effect.getTemplate())) continue;
                    if (effect2.getSkill().getId() != effect.getSkill().getId() || effect2.getEffectType() == effect.getEffectType()) {
                        if (effect2.getStackOrder() == -1) {
                            return;
                        }
                        if (!effect2.maybeScheduleNext(effect)) {
                            return;
                        }
                        hashSet.add(effect2.getSkill());
                        continue;
                    }
                    break;
                }
            }
            this.a(effect);
            bl = this.aP.add(effect);
            if (bl) {
                effect.setInUse(true);
            }
        }
        finally {
            this.g.unlock();
        }
        if (!bl) {
            return;
        }
        if (!hashSet.isEmpty()) {
            for (Skill skill : hashSet) {
                effect.getEffected().sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_WORN_OFF).addSkillName(skill.getDisplayId(), skill.getDisplayLevel()));
            }
        }
        effect.start();
        for (Iterator<Effect> iterator : effect.getTemplate().getAttachedFuncs()) {
            if (((FuncTemplate)((Object)iterator))._stat == Stats.MAX_HP) {
                this._actor.setCurrentHp(d, false);
                continue;
            }
            if (((FuncTemplate)((Object)iterator))._stat == Stats.MAX_MP) {
                this._actor.setCurrentMp(d2);
                continue;
            }
            if (((FuncTemplate)((Object)iterator))._stat != Stats.MAX_CP) continue;
            this._actor.setCurrentCp(d3);
        }
        this._actor.updateStats();
        this._actor.updateEffectIcons();
    }

    public void removeEffect(Effect effect) {
        if (effect == null) {
            return;
        }
        boolean bl = false;
        this.g.lock();
        try {
            if (this.aP == null) {
                return;
            }
            bl = this.aP.remove(effect);
            if (!bl) {
                return;
            }
        }
        finally {
            this.g.unlock();
        }
        if (!bl) {
            return;
        }
        this._actor.updateStats();
        this._actor.updateEffectIcons();
    }

    public void stopAllEffects() {
        if (this.isEmpty()) {
            return;
        }
        this.g.lock();
        try {
            for (Effect effect : this.aP) {
                effect.exit();
            }
        }
        finally {
            this.g.unlock();
        }
        this._actor.updateStats();
        this._actor.updateEffectIcons();
    }

    public void stopAllNegateEffects() {
        this.getAllEffects().stream().map(Effect::getSkill).distinct().filter(Skill::isOffensive).flatMap(skill -> this.getEffectsBySkill((Skill)skill).stream()).forEach(Effect::exit);
    }

    public void stopEffect(int n) {
        if (this.isEmpty()) {
            return;
        }
        for (Effect effect : this.aP) {
            if (effect.getSkill().getId() != n) continue;
            effect.exit();
        }
    }

    public void stopEffect(Skill skill) {
        if (skill != null) {
            this.stopEffect(skill.getId());
        }
    }

    public void stopEffectByDisplayId(int n) {
        if (this.isEmpty()) {
            return;
        }
        for (Effect effect : this.aP) {
            if (effect.getSkill().getDisplayId() != n) continue;
            effect.exit();
        }
    }

    public void stopEffects(EffectType effectType) {
        if (this.isEmpty()) {
            return;
        }
        for (Effect effect : this.aP) {
            if (effect.getEffectType() != effectType) continue;
            effect.exit();
        }
    }

    public void stopAllSkillEffects(EffectType effectType) {
        if (this.isEmpty()) {
            return;
        }
        TIntHashSet tIntHashSet = new TIntHashSet();
        for (Effect effect : this.aP) {
            if (effect.getEffectType() != effectType) continue;
            tIntHashSet.add(effect.getSkill().getId());
        }
        for (Object object : (Object)tIntHashSet.toArray()) {
            this.stopEffect((int)object);
        }
    }
}
