/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.items.Warehouse;

static class WareHouseDepositList.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$items$Warehouse$WarehouseType;

    static {
        $SwitchMap$l2$gameserver$model$items$Warehouse$WarehouseType = new int[Warehouse.WarehouseType.values().length];
        try {
            WareHouseDepositList.1.$SwitchMap$l2$gameserver$model$items$Warehouse$WarehouseType[Warehouse.WarehouseType.PRIVATE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            WareHouseDepositList.1.$SwitchMap$l2$gameserver$model$items$Warehouse$WarehouseType[Warehouse.WarehouseType.CLAN.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
