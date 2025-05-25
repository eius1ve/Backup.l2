/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.InventoryUpdate;
import l2.gameserver.network.l2.s2c.SysMsgContainer;
import l2.gameserver.network.l2.s2c.SystemMessage;

protected class PcInventory.ShadowLifeTimeTask
extends RunnableImpl {
    private ItemInstance item;

    PcInventory.ShadowLifeTimeTask(ItemInstance itemInstance) {
        this.item = itemInstance;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void runImpl() throws Exception {
        int n;
        Player player = PcInventory.this.getActor();
        if (!this.item.isEquipped()) {
            return;
        }
        Object object = this.item;
        synchronized (object) {
            n = Math.max(0, this.item.getDuration() - 1);
            this.item.setDuration(n);
            if (n == 0) {
                PcInventory.this.destroyItem(this.item);
            }
        }
        object = null;
        if (n == 10) {
            object = new SystemMessage(SystemMsg.S1S_REMAINING_MANA_IS_NOW_10);
        } else if (n == 5) {
            object = new SystemMessage(SystemMsg.S1S_REMAINING_MANA_IS_NOW_5);
        } else if (n == 1) {
            object = new SystemMessage(SystemMsg.S1S_REMAINING_MANA_IS_NOW_1);
        } else if (n <= 0) {
            object = new SystemMessage(SystemMsg.S1S_REMAINING_MANA_IS_NOW_0_AND_THE_ITEM_HAS_DISAPPEARED);
        } else {
            player.sendPacket((IStaticPacket)new InventoryUpdate().addModifiedItem(this.item));
        }
        if (object != null) {
            ((SysMsgContainer)object).addItemName(this.item.getItemId());
            player.sendPacket((IStaticPacket)object);
        }
    }
}
