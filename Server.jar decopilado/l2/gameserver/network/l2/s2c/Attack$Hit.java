/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.GameObject;

private class Attack.Hit {
    int _targetId;
    int _damage;
    int _flags;

    Attack.Hit(GameObject gameObject, int n, boolean bl, boolean bl2, boolean bl3) {
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
