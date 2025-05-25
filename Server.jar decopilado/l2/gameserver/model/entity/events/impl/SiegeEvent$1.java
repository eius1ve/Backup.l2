/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.impl;

import l2.gameserver.model.base.RestartType;

static class SiegeEvent.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$base$RestartType;

    static {
        $SwitchMap$l2$gameserver$model$base$RestartType = new int[RestartType.values().length];
        try {
            SiegeEvent.1.$SwitchMap$l2$gameserver$model$base$RestartType[RestartType.TO_FLAG.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
