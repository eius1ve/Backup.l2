/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.handler.items.IItemHandler
 *  l2.gameserver.handler.items.ItemHandler
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.Log
 *  l2.gameserver.utils.Log$ItemLog
 */
package handler.items;

import l2.gameserver.handler.items.IItemHandler;
import l2.gameserver.handler.items.ItemHandler;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.Log;

public abstract class ScriptItemHandler
implements IItemHandler,
ScriptFile {
    public void dropItem(Player player, ItemInstance itemInstance, long l, Location location) {
        if (itemInstance.isEquipped()) {
            player.getInventory().unEquipItem(itemInstance);
            player.sendUserInfo(true);
        }
        if ((itemInstance = player.getInventory().removeItemByObjectId(itemInstance.getObjectId(), l)) == null) {
            player.sendActionFailed();
            return;
        }
        Log.LogItem((Player)player, (Log.ItemLog)Log.ItemLog.Drop, (ItemInstance)itemInstance);
        itemInstance.dropToTheGround((Playable)player, location);
        player.disableDrop(1000);
        player.sendChanges();
    }

    public boolean pickupItem(Playable playable, ItemInstance itemInstance) {
        return true;
    }

    public void onLoad() {
        ItemHandler.getInstance().registerItemHandler((IItemHandler)this);
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
