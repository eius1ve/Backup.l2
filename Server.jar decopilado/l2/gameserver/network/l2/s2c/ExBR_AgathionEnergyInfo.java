/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExBR_AgathionEnergyInfo
extends L2GameServerPacket {
    private int sq;
    private ItemInstance[] b = null;

    public ExBR_AgathionEnergyInfo(int n, ItemInstance ... itemInstanceArray) {
        this.b = itemInstanceArray;
        this.sq = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(222);
        this.writeD(this.sq);
        for (ItemInstance itemInstance : this.b) {
            this.writeD(itemInstance.getObjectId());
            this.writeD(itemInstance.getItemId());
            this.writeD(0x200000);
            this.writeD(0);
            this.writeD(0);
        }
    }
}
