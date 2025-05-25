/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import java.util.Calendar;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.ProductHolder;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.ProductItem;
import l2.gameserver.model.ProductItemComponent;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExBRNewIconCashBtnWnd;
import l2.gameserver.network.l2.s2c.ExBR_BuyProduct;
import l2.gameserver.network.l2.s2c.ExBR_GamePoint;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;

public class RequestExBR_BuyProduct
extends L2GameClientPacket {
    private int iS;
    private int gT;

    @Override
    protected void readImpl() {
        this.iS = this.readD();
        this.gT = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (!Config.ENABLE_PRIME_SHOP) {
            return;
        }
        if (this.gT > 99 || this.gT < 0) {
            return;
        }
        ProductItem productItem = ProductHolder.getInstance().getProduct(this.iS);
        if (productItem == null) {
            player.sendPacket((IStaticPacket)new ExBR_BuyProduct(-2));
            return;
        }
        if (System.currentTimeMillis() < productItem.getStartTimeSale() || System.currentTimeMillis() > productItem.getEndTimeSale()) {
            player.sendPacket((IStaticPacket)new ExBR_BuyProduct(-7));
            return;
        }
        int n = productItem.getPoints() * this.gT;
        int n2 = productItem.getGoldCoins() * this.gT;
        int n3 = productItem.getSilverCoins() * this.gT;
        boolean bl = productItem.isVipItem();
        int n4 = productItem.getVipLevelRequire();
        if (n < 0 || n2 < 0 || n3 < 0) {
            player.sendPacket((IStaticPacket)new ExBR_BuyProduct(-2));
            return;
        }
        long l = player.getPremiumPoints();
        long l2 = ItemFunctions.getItemCount(player, Config.PRIME_SHOP_SILVER_ITEM_ID);
        long l3 = ItemFunctions.getItemCount(player, Config.PRIME_SHOP_GOLD_ITEM_ID);
        if ((long)n > l || (long)n2 > l3 || (long)n3 > l2) {
            player.sendPacket((IStaticPacket)new ExBR_BuyProduct(-1));
            return;
        }
        int n5 = 0;
        for (ProductItemComponent iterator : productItem.getComponents()) {
            n5 += iterator.getWeight();
        }
        n5 *= this.gT;
        int n6 = 0;
        for (ProductItemComponent productItemComponent : productItem.getComponents()) {
            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(productItemComponent.getItemId());
            if (itemTemplate == null) {
                player.sendPacket((IStaticPacket)new ExBR_BuyProduct(-2));
                return;
            }
            n6 += itemTemplate.isStackable() ? 1 : productItemComponent.getCount() * this.gT;
        }
        if (!player.getInventory().validateCapacity(n6) || !player.getInventory().validateWeight(n5)) {
            player.sendPacket((IStaticPacket)new ExBR_BuyProduct(-4));
            return;
        }
        if (n4 > player.getVipLevel() || bl && !RequestExBR_BuyProduct.a(player, productItem)) {
            player.sendPacket((IStaticPacket)new ExBR_BuyProduct(-14));
            return;
        }
        if (bl) {
            player.setVar("VipItemBought", Calendar.getInstance().getTimeInMillis(), -1L);
            player.sendPacket((IStaticPacket)new ExBRNewIconCashBtnWnd(player));
        }
        if (n3 > 0) {
            ItemFunctions.removeItem(player, Config.PRIME_SHOP_SILVER_ITEM_ID, n3, true);
        }
        if (n2 > 0) {
            ItemFunctions.removeItem(player, Config.PRIME_SHOP_GOLD_ITEM_ID, n2, true);
        }
        if (n > 0) {
            player.reducePremiumPoints(n);
        }
        if (Config.PRIME_SHOP_VIP_SYSTEM_ENABLED && Config.PRIME_SHOP_PURCHASING_ADD_VIP_POINTS) {
            player.updateVipPoints(Math.round((double)n * Config.PRIME_SHOP_PURCHASING_ADD_VIP_COEFFICIENT));
        }
        for (ProductItemComponent productItemComponent : productItem.getComponents()) {
            ItemFunctions.addItem((Playable)player, productItemComponent.getItemId(), (long)(productItemComponent.getCount() * this.gT), true);
        }
        player.sendPacket((IStaticPacket)new ExBR_GamePoint(player));
        player.sendPacket((IStaticPacket)new ExBR_BuyProduct(1));
        player.sendChanges();
    }

    private static boolean a(Player player, ProductItem productItem) {
        ProductItem productItem2 = ProductHolder.getInstance().getProduct(productItem.getProductId());
        if (player.getVipLevel() <= 0) {
            return false;
        }
        if (productItem2.getVipLevelRequire() != player.getVipLevel()) {
            return false;
        }
        long l = player.getVarLong("VipItemBought", 0L);
        return l <= 0L;
    }
}
