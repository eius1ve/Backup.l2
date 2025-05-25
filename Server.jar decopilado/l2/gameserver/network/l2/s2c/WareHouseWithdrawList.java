/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import l2.commons.lang.ArrayUtils;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.Warehouse;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;
import l2.gameserver.templates.item.ItemTemplate;

public class WareHouseWithdrawList
extends AbstractItemListPacket {
    private final long dB;
    private final List<ItemInfo> df;
    private int _type;
    private final boolean fv;
    private final int CN;

    public WareHouseWithdrawList(boolean bl, Player player, Warehouse.WarehouseType warehouseType, ItemTemplate.ItemClass itemClass) {
        this.fv = bl;
        this.dB = player.getAdena();
        this._type = warehouseType.ordinal();
        this.CN = player.getInventory().getSize();
        ItemInstance[] itemInstanceArray = switch (warehouseType) {
            case Warehouse.WarehouseType.PRIVATE -> player.getWarehouse().getItems(itemClass);
            case Warehouse.WarehouseType.FREIGHT -> player.getFreight().getItems(itemClass);
            case Warehouse.WarehouseType.CLAN, Warehouse.WarehouseType.CASTLE -> player.getClan().getWarehouse().getItems(itemClass);
            default -> new ItemInstance[]{};
        };
        ArrayUtils.eqSort(itemInstanceArray, Warehouse.ItemClassComparator.getInstance());
        this.df = Arrays.stream(itemInstanceArray).map(ItemInfo::new).collect(Collectors.toList());
    }

    @Override
    protected final void writeImpl() {
        this.writeC(66);
        this.writeC(this.fv ? 1 : 2);
        if (this.fv) {
            this.writeH(this._type);
            this.writeQ(this.dB);
            this.writeD(this.CN);
            this.writeD(this.df.size());
        } else {
            if (this._type != 4) {
                this.writeH(0);
            }
            this.writeD(this.df.size());
            this.writeD(this.df.size());
            for (ItemInfo itemInfo : this.df) {
                this.writeItemInfo(itemInfo);
                this.writeD(itemInfo.getObjectId());
                this.writeD(0);
                this.writeD(0);
            }
        }
    }
}
