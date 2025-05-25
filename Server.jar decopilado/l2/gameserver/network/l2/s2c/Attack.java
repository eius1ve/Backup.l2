/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class Attack
extends L2GameServerPacket {
    public final int _attackerId;
    public final boolean _soulshot;
    private final int sH;
    private final int sI;
    private final int sJ;
    private final int sK;
    private final int sL;
    private final int sM;
    private final int sN;
    private Hit[] a;

    public Attack(Creature creature, Creature creature2, boolean bl, int n) {
        this._attackerId = creature.getObjectId();
        this._soulshot = bl;
        this.sH = n;
        this.sI = creature.getX();
        this.sJ = creature.getY();
        this.sK = creature.getZ();
        this.sL = creature2.getX();
        this.sM = creature2.getY();
        this.sN = creature2.getZ();
        this.a = new Hit[0];
    }

    public void addHit(GameObject gameObject, int n, boolean bl, boolean bl2, boolean bl3) {
        int n2 = this.a.length;
        Hit[] hitArray = new Hit[n2 + 1];
        System.arraycopy(this.a, 0, hitArray, 0, this.a.length);
        hitArray[n2] = new Hit(gameObject, n, bl, bl2, bl3);
        this.a = hitArray;
    }

    public boolean hasHits() {
        return this.a.length > 0;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(51);
        this.writeD(this._attackerId);
        this.writeD(this.a[0]._targetId);
        this.writeD(0);
        this.writeD(this.a[0]._damage);
        this.writeD(this.a[0]._flags);
        this.writeD(this._soulshot ? this.sH : 0);
        this.writeD(this.sI);
        this.writeD(this.sJ);
        this.writeD(this.sK);
        this.writeH(this.a.length - 1);
        for (int i = 1; i < this.a.length; ++i) {
            this.writeD(this.a[i]._targetId);
            this.writeD(this.a[i]._damage);
            this.writeD(this.a[i]._flags);
            this.writeD(this._soulshot ? this.sH : 0);
        }
        this.writeD(this.sL);
        this.writeD(this.sM);
        this.writeD(this.sN);
    }

    private class Hit {
        int _targetId;
        int _damage;
        int _flags;

        Hit(GameObject gameObject, int n, boolean bl, boolean bl2, boolean bl3) {
            this._targetId = gameObject.getObjectId();
            this._damage = n;
            if (Attack.this._soulshot) {
                this._flags |= 8;
            }
            if (bl2) {
                this._flags |= 4;
            }
            if (bl3) {
                this._flags |= 2;
            }
            if (bl) {
                this._flags |= 1;
            }
        }
    }
}
