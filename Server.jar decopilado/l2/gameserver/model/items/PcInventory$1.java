/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

import l2.gameserver.model.items.LockType;

static class PcInventory.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$items$LockType;

    static {
        $SwitchMap$l2$gameserver$model$items$LockType = new int[LockType.values().length];
        try {
            PcInventory.1.$SwitchMap$l2$gameserver$model$items$LockType[LockType.INCLUDE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PcInventory.1.$SwitchMap$l2$gameserver$model$items$LockType[LockType.EXCLUDE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
