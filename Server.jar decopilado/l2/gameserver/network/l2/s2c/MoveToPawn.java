/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class MoveToPawn
extends L2GameServerPacket {
    private int zq;
    private int _targetId;
    private int zE;
    private int _x;
    private int _y;
    private int gl;
    private int sL;
    private int sM;
    private int sN;

    public MoveToPawn(Creature creature, GameObject gameObject, int n) {
        this.zq = creature.getObjectId();
        this._targetId = gameObject.getObjectId();
        this.zE = n;
        this._x = creature.getX();
        this._y = creature.getY();
        this.gl = creature.getZ();
        this.sL = gameObject.getX();
        this.sM = gameObject.getY();
        this.sN = gameObject.getZ();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(114);
        this.writeD(this.zq);
        this.writeD(this._targetId);
        this.writeD(this.zE);
        this.writeD(this._x);
        this.writeD(this._y);
        this.writeD(this.gl);
        this.writeD(this.sL);
        this.writeD(this.sM);
        this.writeD(this.sN);
    }
}
