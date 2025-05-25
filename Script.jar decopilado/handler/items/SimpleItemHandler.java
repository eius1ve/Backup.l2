/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.SystemMessage
 */
package handler.items;

import handler.items.ScriptItemHandler;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

public abstract class SimpleItemHandler
extends ScriptItemHandler {
    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        Player player;
        if (playable.isPlayer()) {
            player = (Player)playable;
        } else if (playable.isPet()) {
            player = playable.getPlayer();
        } else {
            return false;
        }
        if (player.isInFlyingTransform()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(itemInstance.getItemId()));
            return false;
        }
        return this.useItemImpl(player, itemInstance, bl);
    }

    protected abstract boolean useItemImpl(Player var1, ItemInstance var2, boolean var3);

    public static boolean useItem(Player player, ItemInstance itemInstance, long l) {
        if (player.getInventory().destroyItem(itemInstance, l)) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_BEGIN_TO_USE_AN_S1).addItemName(itemInstance.getItemId()));
            return true;
        }
        player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
        return false;
    }
}
