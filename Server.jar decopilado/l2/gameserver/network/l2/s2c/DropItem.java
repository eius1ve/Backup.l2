/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class DropItem
extends L2GameServerPacket {
    private Location _loc;
    private int ph;
    private int tO;
    private int tP;
    private int po;
    private int tQ;
    private long aT;
    private static boolean em;
    private static boolean en;

    public DropItem(ItemInstance itemInstance, int n) {
        this.ph = n;
        this.tO = itemInstance.getObjectId();
        this.tP = itemInstance.getItemId();
        this._loc = itemInstance.getLoc();
        en = itemInstance.isStackable();
        this.aT = itemInstance.getCount();
        this.po = itemInstance.getEnchantLevel();
        em = itemInstance.isAugmented();
        this.tQ = (itemInstance.getEnsoulSlotN1() > 0 ? 1 : 0) + (itemInstance.getEnsoulSlotN2() > 0 ? 1 : 0) + (itemInstance.getEnsoulSlotBm() > 0 ? 1 : 0);
    }

    public DropItem(int n, int n2, int n3, Location location, boolean bl, int n4) {
        this.ph = n;
        this.tO = n2;
        this.tP = n3;
        this._loc = location.clone();
        en = bl;
        this.aT = n4;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(22);
        this.writeD(this.ph);
        this.writeD(this.tO);
        this.writeD(this.tP);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z + Config.CLIENT_Z_SHIFT);
        this.writeC(en);
        this.writeQ(this.aT);
        this.writeC(0);
        this.writeC(this.po);
        this.writeC(em);
        this.writeC(this.tQ);
    }
}
