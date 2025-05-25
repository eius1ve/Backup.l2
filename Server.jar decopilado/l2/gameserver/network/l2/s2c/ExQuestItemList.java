/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.List;
import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.LockType;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class ExQuestItemList
extends AbstractItemListPacket {
    private final boolean eJ;
    private List<ItemInfo> _items;
    private LockType a;
    private int[] aN;

    public ExQuestItemList(boolean bl, List<ItemInfo> list, LockType lockType, int ... nArray) {
        this.eJ = bl;
        this._items = list;
        this.a = lockType;
        this.aN = nArray;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(199);
        this.writeC(this.eJ ? 1 : 2);
        if (this.eJ) {
            this.writeH(this.aN.length);
            if (this.aN.length > 0) {
                this.writeC(this.a.ordinal());
                for (int n : this.aN) {
                    this.writeD(n);
                }
            }
            this.writeD(this._items.size());
        } else {
            this.writeD(this._items.size());
            this.writeD(this._items.size());
            for (ItemInfo itemInfo : this._items) {
                this.writeQuestItemInfo(itemInfo, itemInfo.getCount());
            }
        }
    }

    protected void writeQuestItemInfo(ItemInfo itemInfo, long l) {
        this.writeC(0);
        this.writeD(itemInfo.getObjectId());
        this.writeD(itemInfo.getItemId());
        this.writeC(itemInfo.getEquipSlot());
        this.writeQ(l);
        this.writeC(itemInfo.getItem().getType2ForPackets());
        this.writeC(itemInfo.getCustomType1());
        this.writeH(itemInfo.isEquipped() ? 1 : 0);
        this.writeQ(itemInfo.getItem().getBodyPart());
        this.writeC(itemInfo.getEnchantLevel());
        this.writeC(1);
        this.writeD(itemInfo.getShadowLifeTime());
        this.writeD(itemInfo.getTemporalLifeTime());
        this.writeC(1);
        this.writeC(0);
        this.writeC(0);
    }
}
