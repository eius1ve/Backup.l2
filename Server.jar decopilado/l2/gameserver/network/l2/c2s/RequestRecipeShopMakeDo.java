/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.network.l2.c2s;

import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.data.xml.holder.RecipeHolder;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Recipe;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.ManufactureItem;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.RecipeShopItemInfo;
import l2.gameserver.network.l2.s2c.SysMsgContainer;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.TradeHelper;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.tuple.Pair;

public class RequestRecipeShopMakeDo
extends L2GameClientPacket {
    private int _manufacturerId;
    private int oD;
    private long _price;

    @Override
    protected void readImpl() {
        this._manufacturerId = this.readD();
        this.oD = this.readD();
        this._price = this.readD();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() {
        long n4;
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.isActionsDisabled()) {
            player.sendActionFailed();
            return;
        }
        if (player.isInStoreMode()) {
            player.sendPacket((IStaticPacket)SystemMsg.WHILE_OPERATING_A_PRIVATE_STORE_OR_WORKSHOP_YOU_CANNOT_DISCARD_DESTROY_OR_TRADE_AN_ITEM);
            return;
        }
        if (player.isInTrade()) {
            player.sendActionFailed();
            return;
        }
        if (player.isFishing()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DO_THAT_WHILE_FISHING_2);
            return;
        }
        if (!player.getPlayerAccess().UseTrade) {
            player.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_____);
            return;
        }
        Player player2 = (Player)player.getVisibleObject(this._manufacturerId);
        if (player2 == null || player2.getPrivateStoreType() != 5 || !player2.isInActingRange(player)) {
            player.sendActionFailed();
            return;
        }
        Recipe recipe = null;
        for (ManufactureItem object2 : player2.getCreateList()) {
            if (object2.getRecipeId() != this.oD || this._price != object2.getCost()) continue;
            recipe = RecipeHolder.getInstance().getRecipeById(this.oD);
            break;
        }
        if (recipe == null) {
            player.sendActionFailed();
            return;
        }
        int n = 0;
        if (recipe.getProducts().isEmpty() || recipe.getMaterials().isEmpty()) {
            player2.sendMessage(new CustomMessage("l2p.gameserver.RecipeController.NoRecipe", player2, new Object[0]).addItemName(recipe.getItem()));
            player.sendMessage(new CustomMessage("l2p.gameserver.RecipeController.NoRecipe", player2, new Object[0]).addItemName(recipe.getItem()));
            return;
        }
        if (!player2.findRecipe(this.oD)) {
            player.sendActionFailed();
            return;
        }
        if (player2.getCurrentMp() < (double)recipe.getMpConsume()) {
            player2.sendPacket((IStaticPacket)SystemMsg.NOT_ENOUGH_MP);
            player.sendPacket(SystemMsg.NOT_ENOUGH_MP, new RecipeShopItemInfo(player, player2, this.oD, this._price, n));
            return;
        }
        List<Pair<ItemTemplate, Long>> list = recipe.getMaterials();
        List<Pair<ItemTemplate, Long>> list2 = Util.calcProducts(recipe.getProducts());
        player.getInventory().writeLock();
        try {
            Object object;
            if (player.getAdena() < this._price) {
                player.sendPacket(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA, new RecipeShopItemInfo(player, player2, this.oD, this._price, n));
                return;
            }
            for (Pair<ItemTemplate, Long> l3 : list) {
                ItemTemplate n3 = (ItemTemplate)l3.getKey();
                long iterator2 = (Long)l3.getValue();
                if (iterator2 <= 0L) continue;
                object = player.getInventory().getItemByItemId(n3.getItemId());
                if (object != null && ((ItemInstance)object).getCount() >= iterator2) continue;
                player.sendPacket(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_MATERIALS_TO_PERFORM_THAT_ACTION, new RecipeShopItemInfo(player, player2, this.oD, this._price, n));
                return;
            }
            int n2 = 0;
            long pair = 0L;
            for (Pair<ItemTemplate, Long> l5 : list2) {
                n2 = (int)((long)n2 + (long)((ItemTemplate)l5.getKey()).getWeight() * (Long)l5.getValue());
                pair += ((ItemTemplate)l5.getKey()).isStackable() ? 1L : (Long)l5.getValue();
            }
            if (!player.getInventory().validateWeight(n2) || !player.getInventory().validateCapacity(pair)) {
                player.sendPacket(SystemMsg.THE_WEIGHT_AND_VOLUME_LIMIT_OF_YOUR_INVENTORY_CANNOT_BE_EXCEEDED, new RecipeShopItemInfo(player, player2, this.oD, this._price, n));
                return;
            }
            if (!player.reduceAdena(this._price, false)) {
                player.sendPacket(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA, new RecipeShopItemInfo(player, player2, this.oD, this._price, n));
                return;
            }
            for (Pair<ItemTemplate, Long> pair2 : list) {
                object = (ItemTemplate)pair2.getKey();
                long l = (Long)pair2.getValue();
                if (l <= 0L) continue;
                player.getInventory().destroyItemByItemId(((ItemTemplate)object).getItemId(), l);
                player.sendPacket((IStaticPacket)SystemMessage.removeItems(((ItemTemplate)object).getItemId(), l));
            }
            n4 = TradeHelper.getTax(player2, this._price);
            if (n4 > 0L) {
                this._price -= n4;
                player2.sendMessage(new CustomMessage("trade.HavePaidTax", player2, new Object[0]).addNumber(n4));
            }
            player2.addAdena(this._price);
        }
        finally {
            player.getInventory().writeUnlock();
        }
        for (Pair<ItemTemplate, Long> pair : list2) {
            player2.sendMessage(new CustomMessage("l2p.gameserver.RecipeController.GotOrder", player2, new Object[0]).addItemName((ItemTemplate)pair.getKey()));
        }
        player2.reduceCurrentMp(recipe.getMpConsume(), null);
        player2.sendStatusUpdate(false, false, 11);
        if (Rnd.chance(recipe.getSuccessRate())) {
            for (Pair<ItemTemplate, Long> pair : list2) {
                int pair3 = ((ItemTemplate)pair.getKey()).getItemId();
                n4 = (Long)pair.getValue();
                ItemFunctions.addItem((Playable)player, pair3, n4, true);
            }
            n = 1;
        }
        if (n == 0) {
            for (Pair<ItemTemplate, Long> pair : list2) {
                int n5 = ((ItemTemplate)pair.getKey()).getItemId();
                var7_11 = new SystemMessage(SystemMsg.C1_HAS_FAILED_TO_CREATE_S2_AT_THE_PRICE_OF_S3_ADENA);
                ((SysMsgContainer)var7_11).addString(player2.getName());
                ((SysMsgContainer)var7_11).addItemName(n5);
                ((SysMsgContainer)var7_11).addNumber(this._price);
                player.sendPacket((IStaticPacket)var7_11);
                var7_11 = new SystemMessage(SystemMsg.YOUR_ATTEMPT_TO_CREATE_S2_FOR_C1_AT_THE_PRICE_OF_S3_ADENA_HAS_FAILED);
                ((SysMsgContainer)var7_11).addString(player.getName());
                ((SysMsgContainer)var7_11).addItemName(n5);
                ((SysMsgContainer)var7_11).addNumber(this._price);
                player2.sendPacket((IStaticPacket)var7_11);
            }
        } else {
            for (Pair<ItemTemplate, Long> pair : list2) {
                int n3 = ((ItemTemplate)pair.getKey()).getItemId();
                long l = (Long)pair.getValue();
                if (l > 1L) {
                    var7_11 = new SystemMessage(SystemMsg.C1_CREATED_S2_S3_AT_THE_PRICE_OF_S4_ADENA);
                    ((SysMsgContainer)var7_11).addString(player2.getName());
                    ((SysMsgContainer)var7_11).addItemName(n3);
                    ((SysMsgContainer)var7_11).addNumber(l);
                    ((SysMsgContainer)var7_11).addNumber(this._price);
                    player.sendPacket((IStaticPacket)var7_11);
                    var7_11 = new SystemMessage(SystemMsg.S2_S3_HAVE_BEEN_SOLD_TO_C1_FOR_S4_ADENA);
                    ((SysMsgContainer)var7_11).addString(player.getName());
                    ((SysMsgContainer)var7_11).addItemName(n3);
                    ((SysMsgContainer)var7_11).addNumber(l);
                    ((SysMsgContainer)var7_11).addNumber(this._price);
                    player2.sendPacket((IStaticPacket)var7_11);
                    continue;
                }
                var7_11 = new SystemMessage(SystemMsg.C1_CREATED_S2_AFTER_RECEIVING_S3_ADENA);
                ((SysMsgContainer)var7_11).addString(player2.getName());
                ((SysMsgContainer)var7_11).addItemName(n3);
                ((SysMsgContainer)var7_11).addNumber(this._price);
                player.sendPacket((IStaticPacket)var7_11);
                var7_11 = new SystemMessage(SystemMsg.S2_IS_SOLD_TO_C1_FOR_THE_PRICE_OF_S3_ADENA);
                ((SysMsgContainer)var7_11).addString(player.getName());
                ((SysMsgContainer)var7_11).addItemName(n3);
                ((SysMsgContainer)var7_11).addNumber(this._price);
                player2.sendPacket((IStaticPacket)var7_11);
            }
        }
        player.sendChanges();
        player.sendPacket((IStaticPacket)new RecipeShopItemInfo(player, player2, this.oD, this._price, n));
    }
}
