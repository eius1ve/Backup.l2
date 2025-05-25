/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExMagicSkillUseGround
extends L2GameServerPacket {
    private final int vG;
    private final int vH;
    private final Location S;

    public ExMagicSkillUseGround(int n, int n2, Location location) {
        this.vG = n;
        this.vH = n2;
        this.S = location;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(680);
        this.writeD(this.vG);
        this.writeD(this.vH);
        this.writeD(this.S.getX());
        this.writeD(this.S.getY());
        this.writeD(this.S.getZ());
    }
}
