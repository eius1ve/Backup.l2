/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Recipe$ERecipeType
 */
package handler.items;

import l2.gameserver.model.Recipe;

static class Recipes.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$Recipe$ERecipeType;

    static {
        $SwitchMap$l2$gameserver$model$Recipe$ERecipeType = new int[Recipe.ERecipeType.values().length];
        try {
            Recipes.1.$SwitchMap$l2$gameserver$model$Recipe$ERecipeType[Recipe.ERecipeType.ERT_DWARF.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Recipes.1.$SwitchMap$l2$gameserver$model$Recipe$ERecipeType[Recipe.ERecipeType.ERT_COMMON.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
