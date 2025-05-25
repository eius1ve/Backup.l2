/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.List;
import l2.commons.collections.CollectionUtils;
import l2.gameserver.dao.MailDAO;
import l2.gameserver.model.Player;
import l2.gameserver.model.mail.Mail;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExShowReceivedPostList
extends L2GameServerPacket {
    private final List<Mail> cB;
    private static final int xw = 100;
    private static final int xx = 1000;

    public ExShowReceivedPostList(Player player) {
        this.cB = MailDAO.getInstance().getReceivedMailByOwnerId(player.getObjectId());
        CollectionUtils.shellSort(this.cB);
    }

    @Override
    protected void writeImpl() {
        this.writeEx(171);
        this.writeD((int)(System.currentTimeMillis() / 1000L));
        this.writeD(this.cB.size());
        for (Mail mail : this.cB) {
            this.writeD(mail.getType().ordinal());
            this.writeD(mail.getMessageId());
            this.writeS(mail.getTopic());
            this.writeS(mail.getSenderName());
            this.writeD(mail.isPayOnDelivery() ? 1 : 0);
            this.writeD(mail.getExpireTime());
            this.writeD(mail.isUnread() ? 1 : 0);
            this.writeD(mail.getType() == Mail.SenderType.NORMAL ? 0 : 1);
            this.writeD(mail.getAttachments().isEmpty() ? 0 : 1);
            this.writeD(0);
            this.writeD(0);
        }
        this.writeD(100);
        this.writeD(1000);
    }
}
