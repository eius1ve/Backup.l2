/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.PcInventory
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 */
package services;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import services.StatModifier;

class StatModifier.2
extends StatModifier.ModCond {
    final /* synthetic */ ItemTemplate val$itemTemplate;

    StatModifier.2(ItemTemplate itemTemplate) {
        this.val$itemTemplate = itemTemplate;
    }

    private boolean a(PcInventory pcInventory, ItemTemplate itemTemplate) {
        if (pcInventory == null || itemTemplate == null) {
            return false;
        }
        int n = ItemFunctions.getPaperdollIndex((long)itemTemplate.getBodyPart());
        if (n < 0) {
            return false;
        }
        ItemInstance itemInstance = pcInventory.getPaperdollItem(n);
        return itemInstance != null && itemInstance.getItemId() == itemTemplate.getItemId();
    }

    @Override
    public boolean test(Player player, Creature creature) {
        if (player == null) {
            return false;
        }
        return this.a(player.getInventory(), this.val$itemTemplate);
    }
}
