/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

public static final class ItemInstance.ItemLocation
extends Enum<ItemInstance.ItemLocation> {
    public static final /* enum */ ItemInstance.ItemLocation VOID = new ItemInstance.ItemLocation();
    public static final /* enum */ ItemInstance.ItemLocation INVENTORY = new ItemInstance.ItemLocation();
    public static final /* enum */ ItemInstance.ItemLocation PAPERDOLL = new ItemInstance.ItemLocation();
    public static final /* enum */ ItemInstance.ItemLocation PET_INVENTORY = new ItemInstance.ItemLocation();
    public static final /* enum */ ItemInstance.ItemLocation PET_PAPERDOLL = new ItemInstance.ItemLocation();
    public static final /* enum */ ItemInstance.ItemLocation WAREHOUSE = new ItemInstance.ItemLocation();
    public static final /* enum */ ItemInstance.ItemLocation CLANWH = new ItemInstance.ItemLocation();
    public static final /* enum */ ItemInstance.ItemLocation FREIGHT = new ItemInstance.ItemLocation();
    @Deprecated
    public static final /* enum */ ItemInstance.ItemLocation LEASE = new ItemInstance.ItemLocation();
    public static final /* enum */ ItemInstance.ItemLocation MAIL = new ItemInstance.ItemLocation();
    private static final /* synthetic */ ItemInstance.ItemLocation[] a;

    public static ItemInstance.ItemLocation[] values() {
        return (ItemInstance.ItemLocation[])a.clone();
    }

    public static ItemInstance.ItemLocation valueOf(String string) {
        return Enum.valueOf(ItemInstance.ItemLocation.class, string);
    }

    private static /* synthetic */ ItemInstance.ItemLocation[] a() {
        return new ItemInstance.ItemLocation[]{VOID, INVENTORY, PAPERDOLL, PET_INVENTORY, PET_PAPERDOLL, WAREHOUSE, CLANWH, FREIGHT, LEASE, MAIL};
    }

    static {
        a = ItemInstance.ItemLocation.a();
    }
}
