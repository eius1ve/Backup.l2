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
import l2.gameserver.network.l2.s2c.ExEnchantRetryToPutItemFail;
import l2.gameserver.network.l2.s2c.ExEnchantRetryToPutItemOk;
import l2.gameserver.templates.item.support.CombinationItem;

public class RequestNewEnchantRetryToPutItems
extends L2GameClientPacket {
    private int ry;
    private int rz;

    @Override
    protected void readImpl() throws Exception {
        this.ry = this.readD();
        this.rz = this.readD();
    }

    @Override
    protected void runImpl() throws Exception {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.isActionsDisabled() || player.isInStoreMode() || player.isInTrade() || player.isFishing() || player.isAlikeDead() || player.isBlocked()) {
            player.sendPacket((IStaticPacket)ExEnchantRetryToPutItemFail.STATIC);
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByObjectId(this.ry);
        ItemInstance itemInstance2 = player.getInventory().getItemByObjectId(this.rz);
        if (itemInstance == null) {
            player.sendPacket((IStaticPacket)ExEnchantRetryToPutItemFail.STATIC);
            return;
        }
        if (itemInstance2 == null || player.getSlotOneId() == 0) {
            player.sendPacket((IStaticPacket)ExEnchantRetryToPutItemFail.STATIC);
            return;
        }
        if (itemInstance == itemInstance2 && itemInstance.getCount() < 2L) {
            player.sendPacket((IStaticPacket)ExEnchantRetryToPutItemFail.STATIC);
            return;
        }
        CombinationItem combinationItem = null;
        for (CombinationItem combinationItem2 : CombinationDataHolder.getInstance().getDatas()) {
            if ((itemInstance == null || itemInstance.getItemId() == combinationItem2.getSlotoneId()) && itemInstance2.getItemId() == combinationItem2.getSlottwoId()) {
                combinationItem = combinationItem2;
                break;
            }
            if (itemInstance != null && itemInstance.getItemId() != combinationItem2.getSlottwoId() || itemInstance2.getItemId() != combinationItem2.getSlotoneId()) continue;
            combinationItem = combinationItem2;
            break;
        }
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
            player.sendPacket((IStaticPacket)ExEnchantRetryToPutItemFail.STATIC);
            return;
        }
        player.setSlotOneId(itemInstance.getObjectId());
        player.setSlotTwoId(itemInstance2.getObjectId());
        player.sendPacket((IStaticPacket)ExEnchantRetryToPutItemOk.STATIC);
    }
}
