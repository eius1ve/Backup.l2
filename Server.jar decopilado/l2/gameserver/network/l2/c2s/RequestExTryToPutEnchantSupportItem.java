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
import l2.gameserver.network.l2.s2c.ExPutEnchantSupportItemResult;
import l2.gameserver.templates.item.support.EnchantCatalyzer;

public class RequestExTryToPutEnchantSupportItem
extends L2GameClientPacket {
    private int _itemId;
    private int rc;

    @Override
    protected void readImpl() {
        this.rc = this.readD();
        this._itemId = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        PcInventory pcInventory = player.getInventory();
        ItemInstance itemInstance = pcInventory.getItemByObjectId(this._itemId);
        ItemInstance itemInstance2 = pcInventory.getItemByObjectId(this.rc);
        if (itemInstance == null || itemInstance2 == null) {
            player.sendPacket((IStaticPacket)ExPutEnchantSupportItemResult.FAIL);
            return;
        }
        EnchantCatalyzer enchantCatalyzer = EnchantItemHolder.getInstance().getEnchantCatalyzer(itemInstance2.getItemId());
        if (enchantCatalyzer == null || !enchantCatalyzer.isUsableWith(itemInstance)) {
            player.sendPacket((IStaticPacket)ExPutEnchantSupportItemResult.FAIL);
            return;
        }
        player.sendPacket((IStaticPacket)ExPutEnchantSupportItemResult.SUCCESS);
    }
}
