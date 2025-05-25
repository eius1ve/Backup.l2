/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item;

public static final class ItemTemplate.ItemClass
extends Enum<ItemTemplate.ItemClass> {
    public static final /* enum */ ItemTemplate.ItemClass ALL = new ItemTemplate.ItemClass();
    public static final /* enum */ ItemTemplate.ItemClass WEAPON = new ItemTemplate.ItemClass();
    public static final /* enum */ ItemTemplate.ItemClass ARMOR = new ItemTemplate.ItemClass();
    public static final /* enum */ ItemTemplate.ItemClass JEWELRY = new ItemTemplate.ItemClass();
    public static final /* enum */ ItemTemplate.ItemClass ACCESSORY = new ItemTemplate.ItemClass();
    public static final /* enum */ ItemTemplate.ItemClass CONSUMABLE = new ItemTemplate.ItemClass();
    public static final /* enum */ ItemTemplate.ItemClass MATHERIALS = new ItemTemplate.ItemClass();
    public static final /* enum */ ItemTemplate.ItemClass PIECES = new ItemTemplate.ItemClass();
    public static final /* enum */ ItemTemplate.ItemClass RECIPIES = new ItemTemplate.ItemClass();
    public static final /* enum */ ItemTemplate.ItemClass SPELLBOOKS = new ItemTemplate.ItemClass();
    public static final /* enum */ ItemTemplate.ItemClass MISC = new ItemTemplate.ItemClass();
    public static final /* enum */ ItemTemplate.ItemClass OTHER = new ItemTemplate.ItemClass();
    private static final /* synthetic */ ItemTemplate.ItemClass[] a;

    public static ItemTemplate.ItemClass[] values() {
        return (ItemTemplate.ItemClass[])a.clone();
    }

    public static ItemTemplate.ItemClass valueOf(String string) {
        return Enum.valueOf(ItemTemplate.ItemClass.class, string);
    }

    private static /* synthetic */ ItemTemplate.ItemClass[] a() {
        return new ItemTemplate.ItemClass[]{ALL, WEAPON, ARMOR, JEWELRY, ACCESSORY, CONSUMABLE, MATHERIALS, PIECES, RECIPIES, SPELLBOOKS, MISC, OTHER};
    }

    static {
        a = ItemTemplate.ItemClass.a();
    }
}
