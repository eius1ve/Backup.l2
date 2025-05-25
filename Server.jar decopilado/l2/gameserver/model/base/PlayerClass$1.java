/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

import l2.gameserver.model.base.Race;

static class PlayerClass.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$base$Race;

    static {
        $SwitchMap$l2$gameserver$model$base$Race = new int[Race.values().length];
        try {
            PlayerClass.1.$SwitchMap$l2$gameserver$model$base$Race[Race.elf.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PlayerClass.1.$SwitchMap$l2$gameserver$model$base$Race[Race.darkelf.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
