/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.utils.PositionUtils;

static class Formulas.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$utils$PositionUtils$TargetDirection;

    static {
        $SwitchMap$l2$gameserver$utils$PositionUtils$TargetDirection = new int[PositionUtils.TargetDirection.values().length];
        try {
            Formulas.1.$SwitchMap$l2$gameserver$utils$PositionUtils$TargetDirection[PositionUtils.TargetDirection.BEHIND.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Formulas.1.$SwitchMap$l2$gameserver$utils$PositionUtils$TargetDirection[PositionUtils.TargetDirection.SIDE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Formulas.1.$SwitchMap$l2$gameserver$utils$PositionUtils$TargetDirection[PositionUtils.TargetDirection.FRONT.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
