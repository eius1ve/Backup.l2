/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.actor.instances.player.CharacterBlockListEntry;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExBlockDetailInfo
extends L2GameServerPacket {
    private final String eR;
    private final String eS;

    public ExBlockDetailInfo(CharacterBlockListEntry characterBlockListEntry) {
        this.eR = characterBlockListEntry.getName();
        this.eS = characterBlockListEntry.getMemo();
    }

    @Override
    protected void writeImpl() {
        this.writeEx(239);
        this.writeS(this.eR);
        this.writeS(this.eS);
    }
}
