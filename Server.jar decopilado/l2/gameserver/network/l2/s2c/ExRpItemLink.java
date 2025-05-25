/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class ExRpItemLink
extends AbstractItemListPacket {
    private ItemInfo a;

    public ExRpItemLink(ItemInfo itemInfo) {
        this.a = itemInfo;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(109);
        this.writeItemInfo(this.a);
    }
}
