/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class GetItem
extends L2GameServerPacket {
    private int ph;
    private int _itemObjId;
    private Location _loc;

    public GetItem(ItemInstance itemInstance, int n) {
        this._itemObjId = itemInstance.getObjectId();
        this._loc = itemInstance.getLoc();
        this.ph = n;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(23);
        this.writeD(this.ph);
        this.writeD(this._itemObjId);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
    }
}
