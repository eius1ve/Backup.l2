/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver;

import l2.gameserver.GameTimeController;

static class GameTimeController.2 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$GameTimeController$DayPart;

    static {
        $SwitchMap$l2$gameserver$GameTimeController$DayPart = new int[GameTimeController.DayPart.values().length];
        try {
            GameTimeController.2.$SwitchMap$l2$gameserver$GameTimeController$DayPart[GameTimeController.DayPart.DAY.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            GameTimeController.2.$SwitchMap$l2$gameserver$GameTimeController$DayPart[GameTimeController.DayPart.NIGHT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
