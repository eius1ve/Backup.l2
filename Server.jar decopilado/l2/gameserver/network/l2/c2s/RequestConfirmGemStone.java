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
import l2.gameserver.network.l2.s2c.ExPutCommissionResultForVariationMake;

public class RequestConfirmGemStone
extends L2GameClientPacket {
    private static final int qw = 2130;
    private static final int qx = 2131;
    private static final int qy = 2132;
    private int qz;
    private int qA;
    private int qB;
    private long cR;

    @Override
    protected void readImpl() {
        this.qz = this.readD();
        this.qA = this.readD();
        this.qB = this.readD();
        this.cR = this.readD();
    }

    @Override
    protected void runImpl() {
        if (this.cR <= 0L) {
            return;
        }
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByObjectId(this.qz);
        ItemInstance itemInstance2 = player.getInventory().getItemByObjectId(this.qA);
        ItemInstance itemInstance3 = player.getInventory().getItemByObjectId(this.qB);
        IRefineryHandler iRefineryHandler = player.getRefineryHandler();
        if (itemInstance == null || itemInstance2 == null || itemInstance3 == null || iRefineryHandler == null) {
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ExPutCommissionResultForVariationMake.FAIL_PACKET);
            return;
        }
        iRefineryHandler.onPutGemstoneItem(player, itemInstance, itemInstance2, itemInstance3, this.cR);
    }
}
