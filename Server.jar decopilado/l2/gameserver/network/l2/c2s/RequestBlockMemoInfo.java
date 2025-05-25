/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.CharacterBlockListEntry;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExBlockDetailInfo;

public class RequestBlockMemoInfo
extends L2GameClientPacket {
    private String eb;

    @Override
    protected void readImpl() throws Exception {
        this.eb = this.readS(Config.CNAME_MAXLEN);
    }

    @Override
    protected void runImpl() throws Exception {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        CharacterBlockListEntry characterBlockListEntry = player.getBlockList().getEntryByName(this.eb);
        if (characterBlockListEntry != null) {
            player.sendPacket((IStaticPacket)new ExBlockDetailInfo(characterBlockListEntry));
        }
    }
}
