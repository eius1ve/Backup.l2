/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.commons.lang.ArrayUtils;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.Warehouse;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class WareHouseDepositList
extends AbstractItemListPacket {
    private final boolean fu;
    private int CK;
    private long dg;
    private List<ItemInfo> cO;
    private int CL;

    public WareHouseDepositList(boolean bl, Player player, Warehouse.WarehouseType warehouseType) {
        this.fu = bl;
        this.CK = warehouseType.ordinal();
        this.dg = player.getAdena();
        switch (warehouseType) {
            case PRIVATE: {
                this.CL = player.getWarehouse().getSize();
                break;
            }
            case CLAN: {
                this.CL = player.getClan().getWarehouse().getSize();
            }
        }
        boolean bl2 = this.CK == 1;
        ItemInstance[] itemInstanceArray = player.getInventory().getItems();
        ArrayUtils.eqSort(itemInstanceArray, Warehouse.ItemClassComparator.getInstance());
        this.cO = new ArrayList<ItemInfo>(itemInstanceArray.length);
        for (ItemInstance itemInstance : itemInstanceArray) {
            if (!itemInstance.canBeStored(player, bl2)) continue;
            this.cO.add(new ItemInfo(itemInstance));
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(65);
        this.writeC(this.fu ? 1 : 2);
        if (this.fu) {
            this.writeH(this.CK);
            this.writeQ(this.dg);
            this.writeD(this.CL);
            this.writeH(0);
            this.writeD(this.cO.size());
        } else {
            this.writeD(this.cO.size());
            this.writeD(this.cO.size());
            for (ItemInfo itemInfo : this.cO) {
                this.writeItemInfo(itemInfo);
                this.writeD(itemInfo.getObjectId());
            }
        }
    }
}
