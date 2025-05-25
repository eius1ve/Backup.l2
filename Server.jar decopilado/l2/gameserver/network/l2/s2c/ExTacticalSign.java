/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExTacticalSign
extends L2GameServerPacket {
    private final int xJ;
    private final int xK;

    public ExTacticalSign(Creature creature, int n) {
        this.xJ = creature.getObjectId();
        this.xK = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(256);
        this.writeD(this.xJ);
        this.writeD(this.xK);
    }
}
