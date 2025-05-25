/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.listener.actor.player.OnAnswerListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;

class AdminEditChar.1
implements OnAnswerListener {
    final /* synthetic */ Player val$finalTarget;
    final /* synthetic */ Player val$activeChar;

    AdminEditChar.1() {
        this.val$finalTarget = player;
        this.val$activeChar = player2;
    }

    @Override
    public void sayYes() {
        for (ItemInstance itemInstance : this.val$finalTarget.getInventory().getItems()) {
            this.val$finalTarget.getInventory().destroyItem(itemInstance);
            this.val$finalTarget.sendPacket((IStaticPacket)SystemMessage.removeItems(itemInstance.getItemId(), itemInstance.getCount()));
        }
        this.val$activeChar.sendMessage("All items at " + this.val$finalTarget.getName() + " were successfully removed.");
    }

    @Override
    public void sayNo() {
        this.val$activeChar.sendMessage("Removal of items from " + this.val$finalTarget.getName() + " has been cancelled.");
    }
}
