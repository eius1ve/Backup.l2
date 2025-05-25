/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class GMViewItemList
extends AbstractItemListPacket {
    private final int yS;
    private final int yT;
    private final ItemInstance[] d;
    private final int yU;
    private final String fw;

    public GMViewItemList(int n, Player player, ItemInstance[] itemInstanceArray, int n2) {
        this.yS = n;
        this.yT = n2;
        this.d = itemInstanceArray;
        this.fw = player.getName();
        this.yU = player.getInventoryLimit();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(154);
        this.writeC(this.yS);
        if (this.yS == 1) {
            this.writeS(this.fw);
            this.writeD(this.yU);
            this.writeD(this.yT);
        } else if (this.yS == 2) {
            this.writeD(this.yT);
            this.writeD(this.yT);
            for (ItemInstance itemInstance : this.d) {
                if (itemInstance.getTemplate().isQuest()) continue;
                this.writeItemInfo(new ItemInfo(itemInstance));
            }
        }
    }
}
