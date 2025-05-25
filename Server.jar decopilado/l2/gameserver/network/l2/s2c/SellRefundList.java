/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.LinkedList;
import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class SellRefundList
extends L2GameServerPacket {
    private final List<TradeItem> cZ;
    private final int Bb;
    private final boolean fk;

    public SellRefundList(Player player, boolean bl) {
        this.Bb = (int)player.getAdena();
        this.fk = bl;
        ItemInstance[] itemInstanceArray = player.getInventory().getItems();
        this.cZ = new LinkedList<TradeItem>();
        for (ItemInstance itemInstance : itemInstanceArray) {
            if (!itemInstance.canBeSold(player)) continue;
            this.cZ.add(new TradeItem(itemInstance));
        }
    }

    @Override
    protected void writeImpl() {
        this.writeC(16);
        this.writeD(this.Bb);
        this.writeD(this.fk);
        this.writeH(this.cZ.size());
        for (TradeItem tradeItem : this.cZ) {
            this.writeH(tradeItem.getItem().getType1());
            this.writeD(tradeItem.getObjectId());
            this.writeD(tradeItem.getItemId());
            this.writeD((int)tradeItem.getCount());
            this.writeH(tradeItem.getItem().getType2ForPackets());
            this.writeH(tradeItem.getCustomType1());
            this.writeQ(tradeItem.getItem().getBodyPart());
            this.writeH(tradeItem.getEnchantLevel());
            this.writeH(tradeItem.getCustomType2());
            this.writeH(0);
            this.writeD((int)Math.max(1L, tradeItem.getReferencePrice() / Config.ALT_SHOP_REFUND_SELL_DIVISOR));
        }
    }
}
