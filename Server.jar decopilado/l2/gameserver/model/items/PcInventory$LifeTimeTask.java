/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

protected class PcInventory.LifeTimeTask
extends RunnableImpl {
    private ItemInstance item;

    PcInventory.LifeTimeTask(ItemInstance itemInstance) {
        this.item = itemInstance;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void runImpl() throws Exception {
        int n;
        Player player = PcInventory.this.getActor();
        ItemInstance itemInstance = this.item;
        synchronized (itemInstance) {
            n = this.item.getPeriod();
            if (n <= 0) {
                PcInventory.this.destroyItem(this.item);
            }
        }
        if (n <= 0) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_DISAPPEARED_BECAUSE_ITS_TIME_PERIOD_HAS_EXPIRED).addItemName(this.item.getItemId()));
        }
    }
}
