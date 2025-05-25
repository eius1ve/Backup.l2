/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.Collection;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ProductHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.ProductItem;
import l2.gameserver.model.ProductItemComponent;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.ItemFunctions;

public class ReceiveVipProductList
extends L2GameServerPacket {
    private final long dv;
    private final long dw;
    private final long dx;

    public ReceiveVipProductList(Player player) {
        this.dv = player.getAdena();
        this.dw = ItemFunctions.getItemCount(player, Config.PRIME_SHOP_SILVER_ITEM_ID);
        this.dx = ItemFunctions.getItemCount(player, Config.PRIME_SHOP_GOLD_ITEM_ID);
    }

    @Override
    protected void writeImpl() {
        this.writeEx(389);
        this.writeQ(this.dv);
        this.writeQ(this.dx);
        this.writeQ(this.dw);
        this.writeC(Config.ENABLE_PRIME_SHOP_REWARD_COIN_TAB ? 1 : 0);
        Collection<ProductItem> collection = ProductHolder.getInstance().getAllItems();
        this.writeD(collection.size());
        for (ProductItem productItem : collection) {
            this.writeD(productItem.getProductId());
            this.writeC(productItem.getCategory());
            this.writeC(productItem.getCategory() == 15 ? 3 : 0);
            this.writeD(productItem.getCategory() == 15 ? productItem.getGoldCoins() : productItem.getPoints());
            this.writeD(productItem.getCategory() == 15 ? productItem.getSilverCoins() : productItem.getPoints());
            this.writeC(productItem.getTabId());
            this.writeC(productItem.getVipLevelRequire());
            this.writeC(Config.PRIME_SHOP_VIP_SYSTEM_MAX_LEVEL);
            ArrayList<ProductItemComponent> arrayList = productItem.getComponents();
            this.writeC(arrayList.size());
            for (ProductItemComponent productItemComponent : arrayList) {
                this.writeD(productItemComponent.getItemId());
                this.writeD(productItemComponent.getCount());
            }
        }
    }
}
