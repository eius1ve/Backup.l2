/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExChooseInventoryAttributeItem
extends L2GameServerPacket {
    private int _itemId;
    private boolean et;
    private boolean eu;
    private boolean ev;
    private boolean ew;
    private boolean ex;
    private boolean ey;
    private int uN;

    public ExChooseInventoryAttributeItem(ItemInstance itemInstance) {
        this._itemId = itemInstance.getItemId();
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(98);
        this.writeD(this._itemId);
    }
}
