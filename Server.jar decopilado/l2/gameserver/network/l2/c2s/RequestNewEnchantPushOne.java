/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.data.xml.holder.CombinationDataHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExEnchantOneFail;
import l2.gameserver.network.l2.s2c.ExEnchantOneOK;
import l2.gameserver.templates.item.support.CombinationItem;

public class RequestNewEnchantPushOne
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
        if (player.isActionsDisabled()) {
            player.sendActionFailed();
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByObjectId(this.rw);
        if (itemInstance == null) {
            player.sendPacket((IStaticPacket)ExEnchantOneFail.STATIC);
            return;
        }
        ItemInstance itemInstance2 = player.getInventory().getItemByObjectId(player.getSlotTwoId());
        if (itemInstance == itemInstance2) {
            player.sendPacket((IStaticPacket)ExEnchantOneFail.STATIC);
            return;
        }
        CombinationItem combinationItem = null;
        for (CombinationItem combinationItem2 : CombinationDataHolder.getInstance().getDatas()) {
            if ((itemInstance2 == null || itemInstance2.getItemId() == combinationItem2.getSlotoneId()) && itemInstance.getItemId() == combinationItem2.getSlottwoId()) {
                combinationItem = combinationItem2;
                break;
            }
            if (itemInstance2 != null && itemInstance2.getItemId() != combinationItem2.getSlottwoId() || itemInstance.getItemId() != combinationItem2.getSlotoneId()) continue;
            combinationItem = combinationItem2;
            break;
        }
        if (combinationItem == null) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_VALID_COMBINATION);
            player.sendPacket((IStaticPacket)ExEnchantOneFail.STATIC);
            return;
        }
        player.setSlotOneId(itemInstance.getObjectId());
        player.sendPacket((IStaticPacket)ExEnchantOneOK.STATIC);
    }
}
