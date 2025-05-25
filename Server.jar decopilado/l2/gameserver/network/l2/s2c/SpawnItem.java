/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class SpawnItem
extends L2GameServerPacket {
    private int fW;
    private int _itemId;
    private int _x;
    private int _y;
    private int gl;
    private int BD;
    private int po;
    private int tQ;
    private long aT;
    private static boolean em;

    public SpawnItem(ItemInstance itemInstance) {
        this.fW = itemInstance.getObjectId();
        this._itemId = itemInstance.getItemId();
        this._x = itemInstance.getX();
        this._y = itemInstance.getY();
        this.gl = itemInstance.getZ();
        this.BD = itemInstance.isStackable() ? 1 : 0;
        this.aT = itemInstance.getCount();
        this.po = itemInstance.getEnchantLevel();
        em = itemInstance.isAugmented();
        this.tQ = (itemInstance.getEnsoulSlotN1() > 0 ? 1 : 0) + (itemInstance.getEnsoulSlotN2() > 0 ? 1 : 0) + (itemInstance.getEnsoulSlotBm() > 0 ? 1 : 0);
    }

    @Override
    protected final void writeImpl() {
        this.writeC(5);
        this.writeD(this.fW);
        this.writeD(this._itemId);
        this.writeD(this._x);
        this.writeD(this._y);
        this.writeD(this.gl + Config.CLIENT_Z_SHIFT);
        this.writeD(this.BD);
        this.writeQ(this.aT);
        this.writeD(0);
        this.writeC(this.po);
        this.writeC(em);
        this.writeC(this.tQ);
    }
}
