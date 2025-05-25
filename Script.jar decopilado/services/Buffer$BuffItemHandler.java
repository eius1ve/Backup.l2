/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.handler.items.IItemHandler
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.Log
 *  l2.gameserver.utils.Log$ItemLog
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.handler.items.IItemHandler;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.Log;
import services.Buffer;

private static class Buffer.BuffItemHandler
implements IItemHandler {
    private Buffer.BuffItemHandler() {
    }

    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        try {
            Player player = playable.getPlayer();
            if (player != null) {
                String string = "item_-" + itemInstance.getItemId();
                if (player.hasBonus() && Config.ALT_NPC_BUFFER_PREMIUM_HTML_PREFIX) {
                    string = "item-pa-" + itemInstance.getItemId();
                } else if (Config.ALT_NPC_BUFFER_PREMIUM_ITEM_IDS != null && Config.ALT_NPC_BUFFER_PREMIUM_ITEM_IDS.length > 0) {
                    for (int n : Config.ALT_NPC_BUFFER_PREMIUM_ITEM_IDS) {
                        if (Functions.getItemCount((Playable)player, (int)n) <= 0L) continue;
                        string = "item-pa-" + itemInstance.getItemId();
                        break;
                    }
                }
                Buffer.showPage(playable.getPlayer(), string, null);
                return true;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public void dropItem(Player player, ItemInstance itemInstance, long l, Location location) {
        if (!player.getInventory().destroyItem(itemInstance, l)) {
            player.sendActionFailed();
            return;
        }
        Log.LogItem((Player)player, (Log.ItemLog)Log.ItemLog.Delete, (ItemInstance)itemInstance);
        player.disableDrop(1000);
        player.sendChanges();
    }

    public boolean pickupItem(Playable playable, ItemInstance itemInstance) {
        return false;
    }

    public int[] getItemIds() {
        return bU;
    }
}
