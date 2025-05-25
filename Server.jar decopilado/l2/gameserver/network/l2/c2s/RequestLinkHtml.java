/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLinkHtml
extends L2GameClientPacket {
    private static final Logger cT = LoggerFactory.getLogger(RequestLinkHtml.class);
    private String ep;

    @Override
    protected void readImpl() {
        this.ep = this.readS();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (this.ep.contains("..") || !this.ep.endsWith(".htm")) {
            cT.warn("[RequestLinkHtml] hack? link contains prohibited characters: '" + this.ep + "', skipped");
            return;
        }
        try {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(0);
            npcHtmlMessage.setFile(this.ep);
            this.sendPacket((L2GameServerPacket)npcHtmlMessage);
        }
        catch (Exception exception) {
            cT.warn("Bad RequestLinkHtml: ", (Throwable)exception);
        }
    }
}
