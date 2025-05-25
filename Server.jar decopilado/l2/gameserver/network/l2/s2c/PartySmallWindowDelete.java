/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PartySmallWindowDelete
extends L2GameServerPacket {
    private final int zQ;
    private final String fB;

    public PartySmallWindowDelete(Player player) {
        this.zQ = player.getObjectId();
        this.fB = player.getName();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(81);
        this.writeD(this.zQ);
        this.writeS(this.fB);
    }
}
