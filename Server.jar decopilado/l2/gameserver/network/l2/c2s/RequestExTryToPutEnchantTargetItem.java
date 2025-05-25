/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.data.xml.holder.EnchantItemHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExPutEnchantTargetItemResult;
import l2.gameserver.templates.item.support.EnchantScroll;
import l2.gameserver.utils.Log;

public class RequestExTryToPutEnchantTargetItem
extends L2GameClientPacket {
    private int fW;

    @Override
    protected void readImpl() {
        this.fW = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.isActionsDisabled() || player.isInStoreMode() || player.isInTrade()) {
            player.sendPacket((IStaticPacket)ExPutEnchantTargetItemResult.FAIL);
            player.setEnchantScroll(null);
            return;
        }
        PcInventory pcInventory = player.getInventory();
        ItemInstance itemInstance = pcInventory.getItemByObjectId(this.fW);
        ItemInstance itemInstance2 = player.getEnchantScroll();
        if (itemInstance == null || itemInstance2 == null) {
            player.sendPacket((IStaticPacket)ExPutEnchantTargetItemResult.FAIL);
            player.setEnchantScroll(null);
            return;
        }
        Log.add(player.getName() + "|Trying to put enchant|" + itemInstance.getItemId() + "|+" + itemInstance.getEnchantLevel() + "|" + itemInstance.getObjectId(), "enchants");
        int n = itemInstance2.getItemId();
        if (!itemInstance.canBeEnchanted(false) || itemInstance.isStackable()) {
            player.sendPacket((IStaticPacket)ExPutEnchantTargetItemResult.FAIL);
            player.sendPacket((IStaticPacket)SystemMsg.DOES_NOT_FIT_STRENGTHENING_CONDITIONS_OF_THE_SCROLL);
            player.setEnchantScroll(null);
            return;
        }
        if (player.isInStoreMode()) {
            player.sendPacket((IStaticPacket)ExPutEnchantTargetItemResult.FAIL);
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_ENCHANT_WHILE_OPERATING_A_PRIVATE_STORE_OR_PRIVATE_WORKSHOP);
            player.setEnchantScroll(null);
            return;
        }
        if (itemInstance.getLocation() != ItemInstance.ItemLocation.INVENTORY && itemInstance.getLocation() != ItemInstance.ItemLocation.PAPERDOLL) {
            player.sendPacket((IStaticPacket)ExPutEnchantTargetItemResult.FAIL);
            player.sendPacket((IStaticPacket)SystemMsg.INAPPROPRIATE_ENCHANT_CONDITIONS);
            player.setEnchantScroll(null);
            return;
        }
        if ((itemInstance2 = pcInventory.getItemByObjectId(itemInstance2.getObjectId())) == null) {
            player.sendPacket((IStaticPacket)ExPutEnchantTargetItemResult.FAIL);
            player.setEnchantScroll(null);
            return;
        }
        if (itemInstance.getOwnerId() != player.getObjectId()) {
            player.sendPacket((IStaticPacket)ExPutEnchantTargetItemResult.FAIL);
            player.setEnchantScroll(null);
            return;
        }
        EnchantScroll enchantScroll = EnchantItemHolder.getInstance().getEnchantScroll(n);
        if (enchantScroll == null) {
            player.sendPacket(SystemMsg.INAPPROPRIATE_ENCHANT_CONDITIONS, ExPutEnchantTargetItemResult.FAIL);
            player.setEnchantScroll(null);
            return;
        }
        if (!enchantScroll.isUsableWith(itemInstance, null)) {
            player.sendPacket((IStaticPacket)ExPutEnchantTargetItemResult.FAIL);
            player.sendPacket((IStaticPacket)SystemMsg.DOES_NOT_FIT_STRENGTHENING_CONDITIONS_OF_THE_SCROLL);
            player.setEnchantScroll(null);
            return;
        }
        player.sendPacket((IStaticPacket)ExPutEnchantTargetItemResult.SUCCESS);
    }
}
