/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExNavitAdventEffect
extends L2GameServerPacket {
    private int _time = 0;

    public ExNavitAdventEffect(int n) {
        this._time = n;
    }

    @Override
    protected void writeImpl() {
    }
}
