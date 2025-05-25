/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity;

import l2.gameserver.model.Zone;

static class Reflection.4 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$Zone$ZoneType;

    static {
        $SwitchMap$l2$gameserver$model$Zone$ZoneType = new int[Zone.ZoneType.values().length];
        try {
            Reflection.4.$SwitchMap$l2$gameserver$model$Zone$ZoneType[Zone.ZoneType.no_landing.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Reflection.4.$SwitchMap$l2$gameserver$model$Zone$ZoneType[Zone.ZoneType.SIEGE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Reflection.4.$SwitchMap$l2$gameserver$model$Zone$ZoneType[Zone.ZoneType.RESIDENCE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
