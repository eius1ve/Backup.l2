/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.commons.lang.ArrayUtils;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.Warehouse;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class GMViewWarehouseWithdrawList
extends AbstractItemListPacket {
    private final int zc;
    private final ItemInstance[] e;
    private final String fy;
    private final long dk;

    public GMViewWarehouseWithdrawList(int n, Player player) {
        this.zc = n;
        this.fy = player.getName();
        this.dk = player.getAdena();
        this.e = player.getWarehouse().getItems();
        ArrayUtils.eqSort(this.e, Warehouse.ItemClassComparator.getInstance());
    }

    @Override
    protected final void writeImpl() {
        this.writeC(155);
        this.writeC(this.zc);
        if (this.zc == 2) {
            this.writeD(this.e.length);
            this.writeD(this.e.length);
            for (ItemInstance itemInstance : this.e) {
                this.writeItemInfo(new ItemInfo(itemInstance));
                this.writeD(itemInstance.getObjectId());
            }
        } else {
            this.writeS(this.fy);
            this.writeQ(this.dk);
            this.writeD(this.e.length);
        }
    }
}
