/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.impl;

import l2.gameserver.model.base.TeamType;

static class PlayerVsPlayerDuelEvent.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$base$TeamType;

    static {
        $SwitchMap$l2$gameserver$model$base$TeamType = new int[TeamType.values().length];
        try {
            PlayerVsPlayerDuelEvent.1.$SwitchMap$l2$gameserver$model$base$TeamType[TeamType.NONE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PlayerVsPlayerDuelEvent.1.$SwitchMap$l2$gameserver$model$base$TeamType[TeamType.RED.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PlayerVsPlayerDuelEvent.1.$SwitchMap$l2$gameserver$model$base$TeamType[TeamType.BLUE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
