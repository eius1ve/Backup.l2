/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.model.Player;
import l2.gameserver.model.base.Race;

public static final class Recipe.ERecipeType
extends Enum<Recipe.ERecipeType> {
    public static final /* enum */ Recipe.ERecipeType ERT_DWARF = new Recipe.ERecipeType();
    public static final /* enum */ Recipe.ERecipeType ERT_COMMON = new Recipe.ERecipeType();
    private static final /* synthetic */ Recipe.ERecipeType[] a;

    public static Recipe.ERecipeType[] values() {
        return (Recipe.ERecipeType[])a.clone();
    }

    public static Recipe.ERecipeType valueOf(String string) {
        return Enum.valueOf(Recipe.ERecipeType.class, string);
    }

    public boolean isApplicableBy(Player player) {
        return this != ERT_DWARF || player.getRace() == Race.dwarf;
    }

    private static /* synthetic */ Recipe.ERecipeType[] a() {
        return new Recipe.ERecipeType[]{ERT_DWARF, ERT_COMMON};
    }

    static {
        a = Recipe.ERecipeType.a();
    }
}
