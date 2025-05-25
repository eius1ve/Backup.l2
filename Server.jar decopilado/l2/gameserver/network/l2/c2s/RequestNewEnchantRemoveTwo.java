/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExEnchantTwoRemoveFail;
import l2.gameserver.network.l2.s2c.ExEnchantTwoRemoveOK;

public class RequestNewEnchantRemoveTwo
extends L2GameClientPacket {
    private int rx;

    @Override
    protected void readImpl() {
        this.rx = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByObjectId(this.rx);
        if (itemInstance == null) {
            player.sendPacket((IStaticPacket)ExEnchantTwoRemoveFail.STATIC);
            return;
        }
        if (player.getSlotTwoId() != itemInstance.getObjectId()) {
            player.sendPacket((IStaticPacket)ExEnchantTwoRemoveFail.STATIC);
            return;
        }
        player.setSlotTwoId(0);
        player.sendPacket((IStaticPacket)ExEnchantTwoRemoveOK.STATIC);
    }
}
