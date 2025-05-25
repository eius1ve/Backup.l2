/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.Collection;
import l2.gameserver.data.xml.holder.ProductHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.ProductItem;
import l2.gameserver.model.ProductItemComponent;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExBR_ProductList
extends L2GameServerPacket {
    private long de;

    public ExBR_ProductList(Player player) {
        this.de = player.getAdena();
    }

    @Override
    protected void writeImpl() {
        this.writeEx(215);
        this.writeQ(this.de);
        this.writeQ(0L);
        this.writeC(0);
        Collection<ProductItem> collection = ProductHolder.getInstance().getAllItems();
        this.writeD(collection.size());
        for (ProductItem productItem : collection) {
            if (System.currentTimeMillis() < productItem.getStartTimeSale() || System.currentTimeMillis() > productItem.getEndTimeSale()) continue;
            this.writeD(productItem.getProductId());
            this.writeC(productItem.getCategory());
            this.writeC(0);
            this.writeD(productItem.getPoints());
            this.writeC(productItem.getTabId());
            this.writeD(0);
            this.writeD((int)(productItem.getStartTimeSale() / 1000L));
            this.writeD((int)(productItem.getEndTimeSale() / 1000L));
            this.writeC(127);
            this.writeC(productItem.getStartHour());
            this.writeC(productItem.getStartMin());
            this.writeC(productItem.getEndHour());
            this.writeC(productItem.getEndMin());
            this.writeD(-1);
            this.writeD(-1);
            this.writeC(0);
            this.writeC(0);
            this.writeC(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            ArrayList<ProductItemComponent> arrayList = productItem.getComponents();
            this.writeC(arrayList.size());
            for (ProductItemComponent productItemComponent : arrayList) {
                this.writeD(productItemComponent.getItemId());
                this.writeD(productItemComponent.getCount());
                this.writeD(productItemComponent.getWeight());
                this.writeD(productItemComponent.isDropable());
            }
        }
    }
}
