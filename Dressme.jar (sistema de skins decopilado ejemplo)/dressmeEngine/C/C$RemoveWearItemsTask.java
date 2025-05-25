/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExUserInfoEquipSlot
 *  l2.gameserver.network.l2.s2c.InventorySlot
 */
package dressmeEngine.C;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExUserInfoEquipSlot;
import l2.gameserver.network.l2.s2c.InventorySlot;

private static class C.RemoveWearItemsTask
extends RunnableImpl {
    private Player Ai;

    public C.RemoveWearItemsTask(Player param1Player) {
        this.Ai = param1Player;
    }

    public void runImpl() throws Exception {
        this.Ai.sendMessage("Trying mode ended.");
        this.Ai.sendUserInfo(true);
        this.Ai.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(this.Ai, InventorySlot.VALUES));
        this.Ai.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(this.Ai, new InventorySlot[]{InventorySlot.HAIR, InventorySlot.HAIR2, InventorySlot.HEAD, InventorySlot.AGATHION1, InventorySlot.AGATHION2, InventorySlot.AGATHION3, InventorySlot.AGATHION4, InventorySlot.AGATHION5}));
    }
}
