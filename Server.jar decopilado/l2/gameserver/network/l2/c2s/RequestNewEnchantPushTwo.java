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
import l2.gameserver.network.l2.s2c.ExEnchantTwoFail;
import l2.gameserver.network.l2.s2c.ExEnchantTwoOK;
import l2.gameserver.templates.item.support.CombinationItem;

public class RequestNewEnchantPushTwo
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
        if (itemInstance == null || player.getSlotOneId() == 0) {
            player.sendPacket((IStaticPacket)ExEnchantTwoFail.STATIC);
            return;
        }
        ItemInstance itemInstance2 = player.getInventory().getItemByObjectId(player.getSlotOneId());
        if (itemInstance2 == itemInstance && itemInstance2.getCount() < 2L) {
            player.sendPacket((IStaticPacket)ExEnchantTwoFail.STATIC);
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
            player.sendPacket((IStaticPacket)ExEnchantTwoFail.STATIC);
            return;
        }
        player.setSlotTwoId(itemInstance.getObjectId());
        player.sendPacket((IStaticPacket)ExEnchantTwoOK.STATIC);
    }
}
