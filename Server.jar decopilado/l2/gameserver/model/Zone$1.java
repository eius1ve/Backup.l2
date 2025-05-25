/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.model.Zone;

static class Zone.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$Zone$ZoneTarget;

    static {
        $SwitchMap$l2$gameserver$model$Zone$ZoneTarget = new int[Zone.ZoneTarget.values().length];
        try {
            Zone.1.$SwitchMap$l2$gameserver$model$Zone$ZoneTarget[Zone.ZoneTarget.pc.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Zone.1.$SwitchMap$l2$gameserver$model$Zone$ZoneTarget[Zone.ZoneTarget.only_pc.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Zone.1.$SwitchMap$l2$gameserver$model$Zone$ZoneTarget[Zone.ZoneTarget.npc.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
