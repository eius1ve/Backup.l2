/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.data.xml.holder.BuyListHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ShopPreviewList
extends L2GameServerPacket {
    private int fq;
    private List<ItemInfo> cO;
    private long da;

    public ShopPreviewList(BuyListHolder.NpcTradeList npcTradeList, Player player) {
        this.fq = npcTradeList.getListId();
        this.da = player.getAdena();
        List<TradeItem> list = npcTradeList.getItems();
        this.cO = new ArrayList<ItemInfo>(list.size());
        for (TradeItem tradeItem : npcTradeList.getItems()) {
            if (!tradeItem.getItem().isEquipable()) continue;
            this.cO.add(tradeItem);
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(245);
        this.writeD(5056);
        this.writeQ(this.da);
        this.writeD(this.fq);
        this.writeH(this.cO.size());
        for (ItemInfo itemInfo : this.cO) {
            if (!itemInfo.getItem().isEquipable()) continue;
            this.writeD(itemInfo.getItemId());
            this.writeH(itemInfo.getItem().getType2ForPackets());
            this.writeQ(itemInfo.getItem().isEquipable() ? itemInfo.getItem().getBodyPart() : 0L);
            this.writeQ(itemInfo.getItem().getItemGrade().getWearPreviewPrice());
        }
    }
}
