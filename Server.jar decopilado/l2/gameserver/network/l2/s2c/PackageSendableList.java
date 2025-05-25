/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.LinkedList;
import java.util.List;
import l2.commons.lang.ArrayUtils;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.Warehouse;
import l2.gameserver.network.l2.c2s.RequestPackageSend;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class PackageSendableList
extends AbstractItemListPacket {
    private final int zM;
    private final long dl;
    private final List<ItemInfo> cO;
    private final int zN;

    public PackageSendableList(int n, int n2, Player player) {
        this.zN = n;
        this.dl = player.getAdena();
        this.zM = n2;
        ItemInstance[] itemInstanceArray = player.getInventory().getItems();
        ArrayUtils.eqSort(itemInstanceArray, Warehouse.ItemClassComparator.getInstance());
        this.cO = new LinkedList<ItemInfo>();
        for (ItemInstance itemInstance : itemInstanceArray) {
            if ((!itemInstance.canBeFreighted(player) || !Config.FREIGHT_SEND_TYPE) && (Config.FREIGHT_SEND_TYPE || !RequestPackageSend.CanSendItem(itemInstance))) continue;
            this.cO.add(new ItemInfo(itemInstance));
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(210);
        this.writeC(this.zN);
        if (this.zN == 2) {
            this.writeD(this.cO.size());
            this.writeD(this.cO.size());
            for (ItemInfo itemInfo : this.cO) {
                this.writeItemInfo(itemInfo);
                this.writeD(itemInfo.getObjectId());
            }
        } else {
            this.writeD(this.zM);
            this.writeQ(this.dl);
            this.writeD(this.cO.size());
        }
    }
}
