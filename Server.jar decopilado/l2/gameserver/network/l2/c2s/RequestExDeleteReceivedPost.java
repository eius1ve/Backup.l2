/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.network.l2.c2s;

import java.util.List;
import l2.gameserver.dao.MailDAO;
import l2.gameserver.model.Player;
import l2.gameserver.model.mail.Mail;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowReceivedPostList;
import org.apache.commons.lang3.ArrayUtils;

public class RequestExDeleteReceivedPost
extends L2GameClientPacket {
    private int gT;
    private int[] aR;

    @Override
    protected void readImpl() {
        this.gT = this.readD();
        if (this.gT * 4 > this._buf.remaining() || this.gT > Short.MAX_VALUE || this.gT < 1) {
            this.gT = 0;
            return;
        }
        this.aR = new int[this.gT];
        for (int i = 0; i < this.gT; ++i) {
            this.aR[i] = this.readD();
        }
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || this.gT == 0) {
            return;
        }
        List<Mail> list = MailDAO.getInstance().getReceivedMailByOwnerId(player.getObjectId());
        if (!list.isEmpty()) {
            for (Mail mail : list) {
                if (!ArrayUtils.contains((int[])this.aR, (int)mail.getMessageId()) || !mail.getAttachments().isEmpty()) continue;
                MailDAO.getInstance().deleteReceivedMailByMailId(player.getObjectId(), mail.getMessageId());
            }
        }
        player.sendPacket((IStaticPacket)new ExShowReceivedPostList(player));
    }
}
