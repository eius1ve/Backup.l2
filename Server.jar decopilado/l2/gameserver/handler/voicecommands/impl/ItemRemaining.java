/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.handler.voicecommands.impl;

import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.ArrayUtils;

public class ItemRemaining
extends Functions
implements IVoicedCommandHandler {
    private String[] o = new String[]{"itemremaining", "itemsremaining", "rune", "rune_remaining", "runeremaining"};

    @Override
    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (ArrayUtils.contains((Object[])this.o, (Object)string.toLowerCase())) {
            this.Q(player);
            return true;
        }
        return false;
    }

    @Override
    public String[] getVoicedCommandList() {
        return this.o;
    }

    private void Q(Player player) {
        ItemInstance[] itemInstanceArray;
        for (ItemInstance itemInstance : itemInstanceArray = player.getInventory().getItems()) {
            if (!itemInstance.isTemporalItem()) continue;
            int n = itemInstance.getPeriod();
            String string = Util.formatTime(n);
            player.sendMessage(new CustomMessage("voicedcommandhandlers.ItemRemaining", player, itemInstance, string));
        }
    }
}
