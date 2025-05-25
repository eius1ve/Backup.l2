/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Summon;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class SetSummonRemainTime
extends L2GameServerPacket {
    private final int Bo;
    private final int Bp;

    public SetSummonRemainTime(Summon summon) {
        this.Bp = summon.getCurrentFed();
        this.Bo = summon.getMaxFed();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(209);
        this.writeD(this.Bo);
        this.writeD(this.Bp);
    }
}
