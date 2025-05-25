/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class NickNameChanged
extends L2GameServerPacket {
    private final int zI;
    private final String fA;

    public NickNameChanged(Creature creature) {
        this.zI = creature.getObjectId();
        this.fA = creature.getTitle();
    }

    @Override
    protected void writeImpl() {
        this.writeC(204);
        this.writeD(this.zI);
        this.writeS(this.fA);
    }
}
