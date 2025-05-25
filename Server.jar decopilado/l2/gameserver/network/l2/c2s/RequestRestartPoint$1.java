/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.base.RestartType;

static class RequestRestartPoint.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$base$RestartType;

    static {
        $SwitchMap$l2$gameserver$model$base$RestartType = new int[RestartType.values().length];
        try {
            RequestRestartPoint.1.$SwitchMap$l2$gameserver$model$base$RestartType[RestartType.FIXED.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestRestartPoint.1.$SwitchMap$l2$gameserver$model$base$RestartType[RestartType.TO_CLANHALL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestRestartPoint.1.$SwitchMap$l2$gameserver$model$base$RestartType[RestartType.TO_CASTLE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
