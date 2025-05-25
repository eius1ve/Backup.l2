/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.handler.items;

import l2.gameserver.handler.items.IItemHandler;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.ArrayUtils;

class IItemHandler.1
implements IItemHandler {
    IItemHandler.1() {
    }

    @Override
    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        return false;
    }

    @Override
    public void dropItem(Player player, ItemInstance itemInstance, long l, Location location) {
        if (itemInstance.isEquipped()) {
            player.getInventory().unEquipItem(itemInstance);
            player.sendUserInfo(true);
        }
        if ((itemInstance = player.getInventory().removeItemByObjectId(itemInstance.getObjectId(), l)) == null) {
            player.sendActionFailed();
            return;
        }
        Log.LogItem(player, Log.ItemLog.Drop, itemInstance);
        itemInstance.dropToTheGround((Playable)player, location);
        player.disableDrop(1000);
        player.sendChanges();
    }

    @Override
    public boolean pickupItem(Playable playable, ItemInstance itemInstance) {
        return true;
    }

    @Override
    public int[] getItemIds() {
        return ArrayUtils.EMPTY_INT_ARRAY;
    }
}
