/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.handler.voicecommands.impl;

import l2.gameserver.Config;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import org.apache.commons.lang3.ArrayUtils;

public class OpenBoxService
implements IVoicedCommandHandler {
    private boolean k(Player player) {
        if (!player.getInventory().validateWeight(1L)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
            return false;
        }
        if (!player.getInventory().validateCapacity(1L)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_INVENTORY_IS_FULL);
            return false;
        }
        return true;
    }

    @Override
    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (ArrayUtils.isEmpty((int[])Config.SERVICE_OPEN_BOX_ITEMS_ID)) {
            return false;
        }
        if (player == null) {
            return false;
        }
        if (player.isOutOfControl() || player.isParalyzed() || player.isOlyParticipant()) {
            player.sendMessage(new CustomMessage("common.TryLater", player, new Object[0]));
            return false;
        }
        if (player.isSitting()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_MOVE_WHILE_SITTING);
            return false;
        }
        int n = 0;
        for (int n2 : Config.SERVICE_OPEN_BOX_ITEMS_ID) {
            for (ItemInstance itemInstance : player.getInventory().getItemsByItemId(n2)) {
                int n3 = (int)itemInstance.getCount();
                for (int i = 0; i < n3 && this.k(player); ++i) {
                    if (!itemInstance.getTemplate().getHandler().useItem(player, itemInstance, false)) continue;
                    ++n;
                }
            }
        }
        if (n > 0) {
            player.sendMessage(new CustomMessage("ServiceOpenBoxUseItemsAmount", player, new Object[0]).addNumber(n));
        } else {
            player.sendMessage(new CustomMessage("ServiceOpenBoxNoItemsForUse", player, new Object[0]));
        }
        return true;
    }

    @Override
    public String[] getVoicedCommandList() {
        return Config.SERVICE_OPEN_BOX_COMMAND_NAME;
    }
}
