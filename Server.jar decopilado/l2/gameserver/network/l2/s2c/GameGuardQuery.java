/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class GameGuardQuery
extends L2GameServerPacket {
    @Override
    protected final void writeImpl() {
        this.writeC(116);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
    }
}
