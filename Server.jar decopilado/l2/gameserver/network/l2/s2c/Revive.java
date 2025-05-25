/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.GameObject;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class Revive
extends L2GameServerPacket {
    private int fW;

    public Revive(GameObject gameObject) {
        this.fW = gameObject.getObjectId();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(1);
        this.writeD(this.fW);
    }
}
