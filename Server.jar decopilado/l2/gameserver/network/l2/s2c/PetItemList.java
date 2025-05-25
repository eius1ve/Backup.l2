/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.instances.PetInstance;
import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class PetItemList
extends AbstractItemListPacket {
    private ItemInstance[] f;

    public PetItemList(PetInstance petInstance) {
        this.f = petInstance.getInventory().getItems();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(179);
        this.writeH(this.f.length);
        for (ItemInstance itemInstance : this.f) {
            this.writeItemInfo(new ItemInfo(itemInstance));
        }
    }
}
