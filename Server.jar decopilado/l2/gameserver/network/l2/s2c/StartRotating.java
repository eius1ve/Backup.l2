/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class StartRotating
extends L2GameServerPacket {
    private int fX;
    private int qc;
    private int lM;
    private int hH;

    public StartRotating(Creature creature, int n, int n2, int n3) {
        this.fX = creature.getObjectId();
        this.qc = n;
        this.lM = n2;
        this.hH = n3;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(122);
        this.writeD(this.fX);
        this.writeD(this.qc);
        this.writeD(this.lM);
        this.writeD(this.hH);
    }
}
