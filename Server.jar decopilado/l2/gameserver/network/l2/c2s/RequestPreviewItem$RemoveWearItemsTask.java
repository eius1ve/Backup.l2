/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExUserInfoEquipSlot;
import l2.gameserver.network.l2.s2c.InventorySlot;

private static class RequestPreviewItem.RemoveWearItemsTask
extends RunnableImpl {
    private Player c;

    public RequestPreviewItem.RemoveWearItemsTask(Player player) {
        this.c = player;
    }

    @Override
    public void runImpl() throws Exception {
        this.c.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NO_LONGER_TRYING_ON_EQUIPMENT);
        this.c.sendUserInfo(true);
        this.c.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(this.c, InventorySlot.VALUES));
    }
}
