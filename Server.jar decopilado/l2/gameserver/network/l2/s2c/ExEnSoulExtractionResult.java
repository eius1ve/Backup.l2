/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExEnSoulExtractionResult
extends L2GameServerPacket {
    public static final ExEnSoulExtractionResult ENSOUL_EXTRACT_FAILED_STATIC = new ExEnSoulExtractionResult(false);
    private final boolean eB;
    private final int uV;
    private final int uW;
    private final int uX;

    public ExEnSoulExtractionResult(boolean bl, int n, int n2, int n3) {
        this.eB = bl;
        this.uV = n;
        this.uW = n2;
        this.uX = n3;
    }

    public ExEnSoulExtractionResult(boolean bl, ItemInstance itemInstance) {
        this(bl, itemInstance.getEnsoulSlotN1(), itemInstance.getEnsoulSlotN2(), itemInstance.getEnsoulSlotBm());
    }

    public ExEnSoulExtractionResult(boolean bl) {
        this(bl, 0, 0, 0);
    }

    @Override
    protected void writeImpl() {
        this.writeEx(437);
        if (!this.eB) {
            this.writeC(0);
        } else {
            this.writeC(1);
            if (this.uV > 0 || this.uW > 0) {
                this.writeC(2);
                this.writeD(this.uV);
                this.writeD(this.uW);
            } else if (this.uV > 0) {
                this.writeC(1);
                this.writeD(this.uV);
            } else if (this.uW > 0) {
                this.writeC(1);
                this.writeD(this.uW);
            } else {
                this.writeC(0);
            }
            if (this.uX > 0) {
                this.writeC(1);
                this.writeD(this.uX);
            }
        }
    }
}
