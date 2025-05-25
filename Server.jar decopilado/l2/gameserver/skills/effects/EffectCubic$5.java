/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.templates.CubicTemplate;

static class EffectCubic.5 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$templates$CubicTemplate$ActionType;

    static {
        $SwitchMap$l2$gameserver$templates$CubicTemplate$ActionType = new int[CubicTemplate.ActionType.values().length];
        try {
            EffectCubic.5.$SwitchMap$l2$gameserver$templates$CubicTemplate$ActionType[CubicTemplate.ActionType.ATTACK.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EffectCubic.5.$SwitchMap$l2$gameserver$templates$CubicTemplate$ActionType[CubicTemplate.ActionType.DEBUFF.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EffectCubic.5.$SwitchMap$l2$gameserver$templates$CubicTemplate$ActionType[CubicTemplate.ActionType.HEAL.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EffectCubic.5.$SwitchMap$l2$gameserver$templates$CubicTemplate$ActionType[CubicTemplate.ActionType.CANCEL.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
