/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class PrivateStoreListSell
extends AbstractItemListPacket {
    private final int AD;
    private final long dp;
    private final boolean fe;
    private final List<TradeItem> cT;

    public PrivateStoreListSell(Player player, Player player2) {
        this.AD = player2.getObjectId();
        this.dp = player.getAdena();
        this.fe = player2.getPrivateStoreType() == 8;
        this.cT = player2.getSellList();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(161);
        this.writeD(this.AD);
        this.writeD(this.fe ? 1 : 0);
        this.writeQ(this.dp);
        this.writeD(0);
        this.writeD(this.cT.size());
        for (TradeItem tradeItem : this.cT) {
            this.writeItemInfo(tradeItem);
            this.writeQ(tradeItem.getOwnersPrice());
            this.writeQ(tradeItem.getStorePrice());
        }
    }
}
