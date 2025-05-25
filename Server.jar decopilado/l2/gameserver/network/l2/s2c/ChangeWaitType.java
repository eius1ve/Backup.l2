/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ChangeWaitType
extends L2GameServerPacket {
    private int fW;
    private int qU;
    private int _x;
    private int _y;
    private int gl;
    public static final int WT_SITTING = 0;
    public static final int WT_STANDING = 1;
    public static final int WT_START_FAKEDEATH = 2;
    public static final int WT_STOP_FAKEDEATH = 3;

    public ChangeWaitType(Creature creature, int n) {
        this.fW = creature.getObjectId();
        this.qU = n;
        this._x = creature.getX();
        this._y = creature.getY();
        this.gl = creature.getZ();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(41);
        this.writeD(this.fW);
        this.writeD(this.qU);
        this.writeD(this._x);
        this.writeD(this._y);
        this.writeD(this.gl);
    }
}
