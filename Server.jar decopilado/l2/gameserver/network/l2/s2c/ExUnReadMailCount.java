/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.dao.MailDAO;
import l2.gameserver.model.Player;
import l2.gameserver.model.mail.Mail;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExUnReadMailCount
extends L2GameServerPacket {
    private int xL;

    public ExUnReadMailCount(Player player) {
        for (Mail mail : MailDAO.getInstance().getReceivedMailByOwnerId(player.getObjectId())) {
            if (!mail.isUnread()) continue;
            ++this.xL;
        }
    }

    @Override
    protected void writeImpl() {
        this.writeEx(316);
        this.writeD(this.xL);
    }
}
