/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;

public class ExPCCafeRequestOpenWindowWithoutNPC
extends L2GameClientPacket {
    @Override
    protected void readImpl() throws Exception {
    }

    @Override
    protected void runImpl() throws Exception {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player != null && Config.ALT_PCBANG_POINTS_ENABLED) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(0);
            npcHtmlMessage.setFile("merchant/pccafe_shop.htm");
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        }
    }
}
