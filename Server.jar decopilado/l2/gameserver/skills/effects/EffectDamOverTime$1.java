/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.skills.EffectType;

static class EffectDamOverTime.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$skills$EffectType;

    static {
        $SwitchMap$l2$gameserver$skills$EffectType = new int[EffectType.values().length];
        try {
            EffectDamOverTime.1.$SwitchMap$l2$gameserver$skills$EffectType[EffectType.Poison.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EffectDamOverTime.1.$SwitchMap$l2$gameserver$skills$EffectType[EffectType.Bleed.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
