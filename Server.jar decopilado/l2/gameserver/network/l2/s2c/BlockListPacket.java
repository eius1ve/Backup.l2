/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.CharacterBlockListEntry;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class BlockListPacket
extends L2GameServerPacket {
    private final List<CharacterBlockListEntry> bU = new ArrayList<CharacterBlockListEntry>();

    public BlockListPacket(Player player) {
        this.bU.addAll(player.getBlockList().getBlockList());
    }

    @Override
    protected void writeImpl() {
        this.writeC(213);
        this.writeD(this.bU.size());
        for (CharacterBlockListEntry characterBlockListEntry : this.bU) {
            this.writeS(characterBlockListEntry.getName());
        }
    }
}
