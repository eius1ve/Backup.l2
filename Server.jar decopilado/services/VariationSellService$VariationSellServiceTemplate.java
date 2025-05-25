/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.xml.holder.VariationGroupHolder
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.instances.player.ShortCut
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.PcInventory
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ActionFail
 *  l2.gameserver.network.l2.s2c.InventoryUpdate
 *  l2.gameserver.network.l2.s2c.ShortCutRegister
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 *  org.apache.commons.lang3.tuple.Pair
 */
package services;

import java.util.List;
import l2.gameserver.data.xml.holder.VariationGroupHolder;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.network.l2.s2c.InventoryUpdate;
import l2.gameserver.network.l2.s2c.ShortCutRegister;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.tuple.Pair;

private static class VariationSellService.VariationSellServiceTemplate {
    private final int bGk;
    private final int bGl;
    private final int bGm;
    private final List<Pair<ItemTemplate, Long>> dZ;

    private VariationSellService.VariationSellServiceTemplate(int n, int n2, int n3, List<Pair<ItemTemplate, Long>> list) {
        this.bGk = n;
        this.bGl = n2;
        this.bGm = n3;
        this.dZ = list;
    }

    private boolean C(Player player) {
        for (Pair<ItemTemplate, Long> pair : this.dZ) {
            if (player.getInventory().getCountOf(((ItemTemplate)pair.getLeft()).getItemId()) >= (Long)pair.getRight()) continue;
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
            return false;
        }
        return true;
    }

    private boolean consume(Player player) {
        PcInventory pcInventory = player.getInventory();
        for (Pair<ItemTemplate, Long> pair : this.dZ) {
            if (ItemFunctions.removeItem((Playable)player, (int)((ItemTemplate)pair.getLeft()).getItemId(), (long)((Long)pair.getRight()), (boolean)true) >= (Long)pair.getRight()) continue;
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
            return false;
        }
        return true;
    }

    public ItemInstance process(Player player) {
        if (player == null || player.isLogoutStarted() || player.isTeleporting() || !Functions.CheckPlayerConditions((Player)player)) {
            return null;
        }
        if (!this.C(player)) {
            return null;
        }
        ItemInstance itemInstance = player.getInventory().getPaperdollItem(5);
        if (itemInstance == null) {
            player.sendMessage(new CustomMessage("services.VariationSellService.process.EquippedItemRequired", player, new Object[0]));
            return null;
        }
        if (itemInstance.isAugmented()) {
            player.sendPacket(new IStaticPacket[]{SystemMsg.ONCE_AN_ITEM_IS_AUGMENTED_IT_CANNOT_BE_AUGMENTED_AGAIN, ActionFail.STATIC});
            return null;
        }
        List list = VariationGroupHolder.getInstance().getDataForItemId(itemInstance.getItemId());
        if (list == null || list.isEmpty()) {
            player.sendPacket(new IStaticPacket[]{SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC});
            return null;
        }
        if (!this.consume(player)) {
            return null;
        }
        boolean bl = itemInstance.isEquipped();
        if (bl) {
            player.getInventory().unEquipItem(itemInstance);
        }
        itemInstance.setVariationStat1(this.bGl);
        itemInstance.setVariationStat2(this.bGm);
        if (bl) {
            player.getInventory().equipItem(itemInstance);
        }
        player.sendPacket((IStaticPacket)new InventoryUpdate().addModifiedItem(itemInstance));
        for (ShortCut shortCut : player.getAllShortCuts()) {
            if (shortCut.getId() != itemInstance.getObjectId() || shortCut.getType() != 1) continue;
            player.sendPacket((IStaticPacket)new ShortCutRegister(player, shortCut));
        }
        player.sendChanges();
        Log.service((String)"VariationSellService", (Player)player, (String)("bought an augment to " + itemInstance.getObjectId() + " [" + itemInstance.getName() + "]  Augment1 " + this.bGl + " Augment2 " + this.bGm));
        return itemInstance;
    }
}
