/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class FinishRotating
extends L2GameServerPacket {
    private int fX;
    private int qc;
    private int hH;

    public FinishRotating(Creature creature, int n, int n2) {
        this.fX = creature.getObjectId();
        this.qc = n;
        this.hH = n2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(97);
        this.writeD(this.fX);
        this.writeD(this.qc);
        this.writeD(this.hH);
        this.writeD(0);
    }
}
