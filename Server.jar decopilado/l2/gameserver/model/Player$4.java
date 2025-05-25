/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.model.Recipe;

static class Player.4 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$Recipe$ERecipeType;

    static {
        $SwitchMap$l2$gameserver$model$Recipe$ERecipeType = new int[Recipe.ERecipeType.values().length];
        try {
            Player.4.$SwitchMap$l2$gameserver$model$Recipe$ERecipeType[Recipe.ERecipeType.ERT_COMMON.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Player.4.$SwitchMap$l2$gameserver$model$Recipe$ERecipeType[Recipe.ERecipeType.ERT_DWARF.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
