/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class ExReplyPostItemList
extends AbstractItemListPacket {
    private final boolean eK;
    private List<ItemInfo> cs = new ArrayList<ItemInfo>();

    public ExReplyPostItemList(boolean bl, Player player) {
        ItemInstance[] itemInstanceArray;
        this.eK = bl;
        for (ItemInstance itemInstance : itemInstanceArray = player.getInventory().getItems()) {
            if (!itemInstance.canBeTraded(player)) continue;
            this.cs.add(new ItemInfo(itemInstance));
        }
    }

    @Override
    protected void writeImpl() {
        this.writeEx(179);
        this.writeC(this.eK ? 1 : 2);
        if (this.eK) {
            this.writeD(this.cs.size());
        } else {
            this.writeD(this.cs.size());
            this.writeD(this.cs.size());
            for (ItemInfo itemInfo : this.cs) {
                this.writeItemInfo(itemInfo);
            }
        }
    }
}
