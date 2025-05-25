/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExEnchantOneRemoveFail;
import l2.gameserver.network.l2.s2c.ExEnchantOneRemoveOK;

public class RequestNewEnchantRemoveOne
extends L2GameClientPacket {
    private int rw;

    @Override
    protected void readImpl() {
        this.rw = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByObjectId(this.rw);
        if (itemInstance == null) {
            player.sendPacket((IStaticPacket)ExEnchantOneRemoveFail.STATIC);
            return;
        }
        if (player.getSlotOneId() != itemInstance.getObjectId()) {
            player.sendPacket((IStaticPacket)ExEnchantOneRemoveFail.STATIC);
            return;
        }
        player.setSlotOneId(0);
        player.sendPacket((IStaticPacket)ExEnchantOneRemoveOK.STATIC);
    }
}
