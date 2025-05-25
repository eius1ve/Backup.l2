/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.data.xml.holder.ProductHolder;
import l2.gameserver.model.ProductItem;
import l2.gameserver.model.ProductItemComponent;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExBR_ProductInfo
extends L2GameServerPacket {
    private ProductItem a;

    public ExBR_ProductInfo(int n) {
        this.a = ProductHolder.getInstance().getProduct(n);
    }

    @Override
    protected void writeImpl() {
        if (this.a == null) {
            return;
        }
        this.writeEx(216);
        this.writeD(this.a.getProductId());
        this.writeD(this.a.getPoints());
        this.writeD(this.a.getComponents().size());
        for (ProductItemComponent productItemComponent : this.a.getComponents()) {
            this.writeD(productItemComponent.getItemId());
            this.writeD(productItemComponent.getCount());
            this.writeD(productItemComponent.getWeight());
            this.writeD(productItemComponent.isDropable() ? 1 : 0);
        }
    }
}
