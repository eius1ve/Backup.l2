/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.xml.holder.EnchantItemHolder
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ChooseInventoryItem
 */
package handler.items;

import handler.items.ScriptItemHandler;
import l2.gameserver.data.xml.holder.EnchantItemHolder;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ChooseInventoryItem;

public class EnchantScrolls
extends ScriptItemHandler {
    private static int[] N = null;

    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        if (playable == null || !playable.isPlayer()) {
            return false;
        }
        Player player = (Player)playable;
        if (player.getEnchantScroll() != null) {
            return false;
        }
        player.setEnchantScroll(itemInstance);
        player.sendPacket((IStaticPacket)new ChooseInventoryItem(itemInstance.getItemId()));
        return true;
    }

    public final int[] getItemIds() {
        if (N == null) {
            N = EnchantItemHolder.getInstance().getScrollIds();
        }
        return N;
    }
}
