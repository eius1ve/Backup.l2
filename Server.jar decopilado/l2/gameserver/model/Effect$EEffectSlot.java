/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.Config;
import l2.gameserver.model.Skill;

public static final class Effect.EEffectSlot
extends Enum<Effect.EEffectSlot> {
    public static final /* enum */ Effect.EEffectSlot EFFECT_SLOT_NORMAL = new Effect.EEffectSlot();
    public static final /* enum */ Effect.EEffectSlot EFFECT_SLOT_SONG_DANCE = new Effect.EEffectSlot();
    public static final /* enum */ Effect.EEffectSlot EFFECT_SLOT_TOGGLE = new Effect.EEffectSlot();
    public static final /* enum */ Effect.EEffectSlot EFFECT_SLOT_TRIGGER = new Effect.EEffectSlot();
    public static final /* enum */ Effect.EEffectSlot EFFECT_SLOT_ETC = new Effect.EEffectSlot();
    public static final /* enum */ Effect.EEffectSlot EFFECT_SLOT_DEBUFF = new Effect.EEffectSlot();
    public static final Effect.EEffectSlot[] VALUES;
    private static final /* synthetic */ Effect.EEffectSlot[] a;

    public static Effect.EEffectSlot[] values() {
        return (Effect.EEffectSlot[])a.clone();
    }

    public static Effect.EEffectSlot valueOf(String string) {
        return Enum.valueOf(Effect.EEffectSlot.class, string);
    }

    public static Effect.EEffectSlot getBySkill(Skill skill) {
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

    private static /* synthetic */ Effect.EEffectSlot[] a() {
        return new Effect.EEffectSlot[]{EFFECT_SLOT_NORMAL, EFFECT_SLOT_SONG_DANCE, EFFECT_SLOT_TOGGLE, EFFECT_SLOT_TRIGGER, EFFECT_SLOT_ETC, EFFECT_SLOT_DEBUFF};
    }

    static {
        a = Effect.EEffectSlot.a();
        VALUES = Effect.EEffectSlot.values();
    }
}
