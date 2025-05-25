/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item;

public final class ItemFlags
extends Enum<ItemFlags> {
    public static final /* enum */ ItemFlags DESTROYABLE = new ItemFlags(true);
    public static final /* enum */ ItemFlags DROPABLE = new ItemFlags(true);
    public static final /* enum */ ItemFlags FREIGHTABLE = new ItemFlags(false);
    public static final /* enum */ ItemFlags AUGMENTABLE = new ItemFlags(true);
    public static final /* enum */ ItemFlags ENCHANTABLE = new ItemFlags(true);
    public static final /* enum */ ItemFlags ATTRIBUTABLE = new ItemFlags(true);
    public static final /* enum */ ItemFlags SELLABLE = new ItemFlags(true);
    public static final /* enum */ ItemFlags TRADEABLE = new ItemFlags(true);
    public static final /* enum */ ItemFlags STOREABLE = new ItemFlags(true);
    public static final ItemFlags[] VALUES;
    private final int FZ;
    private final boolean he;
    private static final /* synthetic */ ItemFlags[] a;

    public static ItemFlags[] values() {
        return (ItemFlags[])a.clone();
    }

    public static ItemFlags valueOf(String string) {
        return Enum.valueOf(ItemFlags.class, string);
    }

    private ItemFlags(boolean bl) {
        this.he = bl;
        this.FZ = 1 << this.ordinal();
    }

    public int mask() {
        return this.FZ;
    }

    public boolean getDefaultValue() {
        return this.he;
    }

    private static /* synthetic */ ItemFlags[] a() {
        return new ItemFlags[]{DESTROYABLE, DROPABLE, FREIGHTABLE, AUGMENTABLE, ENCHANTABLE, ATTRIBUTABLE, SELLABLE, TRADEABLE, STOREABLE};
    }

    static {
        a = ItemFlags.a();
        VALUES = ItemFlags.values();
    }
}
