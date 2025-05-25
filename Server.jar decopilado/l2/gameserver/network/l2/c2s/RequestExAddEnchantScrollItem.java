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
import l2.gameserver.network.l2.s2c.ExPutEnchantScrollItemResult;
import l2.gameserver.templates.item.support.EnchantScroll;

public class RequestExAddEnchantScrollItem
extends L2GameClientPacket {
    private int qG;
    private int qH;

    @Override
    protected void readImpl() {
        this.qG = this.readD();
        this.qH = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.isActionsDisabled() || player.isInStoreMode() || player.isInTrade()) {
            player.sendPacket((IStaticPacket)ExPutEnchantScrollItemResult.FAIL);
            return;
        }
        PcInventory pcInventory = player.getInventory();
        ItemInstance itemInstance = pcInventory.getItemByObjectId(this.qH);
        ItemInstance itemInstance2 = pcInventory.getItemByObjectId(this.qG);
        if (itemInstance == null || itemInstance2 == null) {
            player.sendPacket((IStaticPacket)ExPutEnchantScrollItemResult.FAIL);
            return;
        }
        int n = itemInstance2.getItemId();
        EnchantScroll enchantScroll = EnchantItemHolder.getInstance().getEnchantScroll(n);
        if (enchantScroll == null) {
            player.sendPacket((IStaticPacket)ExPutEnchantScrollItemResult.FAIL);
            player.sendPacket((IStaticPacket)SystemMsg.DOES_NOT_FIT_STRENGTHENING_CONDITIONS_OF_THE_SCROLL);
            return;
        }
        if (player.isActionsDisabled()) {
            player.setEnchantScroll(null);
            player.sendActionFailed();
            return;
        }
        if (player.isInTrade()) {
            player.setEnchantScroll(null);
            player.sendActionFailed();
            return;
        }
        if (player.isInStoreMode()) {
            player.setEnchantScroll(null);
            player.sendPacket((IStaticPacket)ExPutEnchantScrollItemResult.FAIL);
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_ENCHANT_WHILE_OPERATING_A_PRIVATE_STORE_OR_PRIVATE_WORKSHOP);
            player.sendActionFailed();
            return;
        }
        if (!itemInstance.canBeEnchanted(false)) {
            player.sendPacket((IStaticPacket)ExPutEnchantScrollItemResult.FAIL);
            player.sendPacket((IStaticPacket)SystemMsg.INAPPROPRIATE_ENCHANT_CONDITIONS);
            player.sendActionFailed();
            return;
        }
        if (!enchantScroll.isUsableWith(itemInstance, null)) {
            player.sendPacket((IStaticPacket)ExPutEnchantScrollItemResult.FAIL);
            player.sendPacket((IStaticPacket)SystemMsg.DOES_NOT_FIT_STRENGTHENING_CONDITIONS_OF_THE_SCROLL);
            player.sendActionFailed();
            return;
        }
        if (player.isInStoreMode()) {
            player.sendPacket((IStaticPacket)ExPutEnchantScrollItemResult.FAIL);
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_ENCHANT_WHILE_OPERATING_A_PRIVATE_STORE_OR_PRIVATE_WORKSHOP);
            return;
        }
        if ((itemInstance2 = pcInventory.getItemByObjectId(itemInstance2.getObjectId())) == null) {
            player.sendPacket((IStaticPacket)ExPutEnchantScrollItemResult.FAIL);
            return;
        }
        if (itemInstance.getOwnerId() != player.getObjectId()) {
            player.sendPacket((IStaticPacket)ExPutEnchantScrollItemResult.FAIL);
            return;
        }
        player.setEnchantScroll(itemInstance2);
        player.sendPacket((IStaticPacket)new ExPutEnchantScrollItemResult(itemInstance2.getObjectId()));
    }
}
