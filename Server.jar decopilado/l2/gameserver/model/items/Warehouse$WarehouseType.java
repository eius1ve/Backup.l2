/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

public static final class Warehouse.WarehouseType
extends Enum<Warehouse.WarehouseType> {
    public static final /* enum */ Warehouse.WarehouseType NONE = new Warehouse.WarehouseType();
    public static final /* enum */ Warehouse.WarehouseType PRIVATE = new Warehouse.WarehouseType();
    public static final /* enum */ Warehouse.WarehouseType CLAN = new Warehouse.WarehouseType();
    public static final /* enum */ Warehouse.WarehouseType CASTLE = new Warehouse.WarehouseType();
    public static final /* enum */ Warehouse.WarehouseType FREIGHT = new Warehouse.WarehouseType();
    private static final /* synthetic */ Warehouse.WarehouseType[] a;

    public static Warehouse.WarehouseType[] values() {
        return (Warehouse.WarehouseType[])a.clone();
    }

    public static Warehouse.WarehouseType valueOf(String string) {
        return Enum.valueOf(Warehouse.WarehouseType.class, string);
    }

    private static /* synthetic */ Warehouse.WarehouseType[] a() {
        return new Warehouse.WarehouseType[]{NONE, PRIVATE, CLAN, CASTLE, FREIGHT};
    }

    static {
        a = Warehouse.WarehouseType.a();
    }
}
