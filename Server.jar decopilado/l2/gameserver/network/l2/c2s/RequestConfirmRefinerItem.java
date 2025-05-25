/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.handler.items.IRefineryHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExPutIntensiveResultForVariationMake;

public class RequestConfirmRefinerItem
extends L2GameClientPacket {
    private int qz;
    private int qA;

    @Override
    protected void readImpl() {
        this.qz = this.readD();
        this.qA = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByObjectId(this.qz);
        ItemInstance itemInstance2 = player.getInventory().getItemByObjectId(this.qA);
        IRefineryHandler iRefineryHandler = player.getRefineryHandler();
        if (itemInstance == null || itemInstance2 == null || iRefineryHandler == null) {
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ExPutIntensiveResultForVariationMake.FAIL_PACKET);
            return;
        }
        iRefineryHandler.onPutMineralItem(player, itemInstance, itemInstance2);
    }
}
