/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExEnsoulResult
extends L2GameServerPacket {
    public static final ExEnsoulResult ENSOUL_FAILED_STATIC = new ExEnsoulResult(false);
    private final boolean eC;
    private final int ve;
    private final int vf;
    private final int vg;

    public ExEnsoulResult(boolean bl, int n, int n2, int n3) {
        this.eC = bl;
        this.ve = n;
        this.vf = n2;
        this.vg = n3;
    }

    public ExEnsoulResult(boolean bl, ItemInstance itemInstance) {
        this(bl, itemInstance.getEnsoulSlotN1(), itemInstance.getEnsoulSlotN2(), itemInstance.getEnsoulSlotBm());
    }

    public ExEnsoulResult(boolean bl) {
        this(bl, 0, 0, 0);
    }

    @Override
    protected void writeImpl() {
        this.writeEx(385);
        if (!this.eC) {
            this.writeC(0);
        } else {
            this.writeC(1);
            if (this.ve > 0 && this.vf > 0) {
                this.writeC(2);
                this.writeD(this.ve);
                this.writeD(this.vf);
            } else if (this.ve > 0) {
                this.writeC(1);
                this.writeD(this.ve);
            } else if (this.vf > 0) {
                this.writeC(1);
                this.writeD(this.vf);
            } else {
                this.writeC(0);
            }
            if (this.vg > 0) {
                this.writeC(1);
                this.writeD(this.vg);
            } else {
                this.writeC(0);
            }
        }
    }
}
