/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.dao.CharacterTPBookmarkDAO;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.TpBookMark;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExGetBookMarkInfo;

public class RequestDeleteBookMarkSlot
extends L2GameClientPacket {
    private int kk;

    @Override
    protected void readImpl() {
        this.kk = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        TpBookMark tpBookMark = player.getTpBookMarks().remove(this.kk - 1);
        if (tpBookMark != null) {
            CharacterTPBookmarkDAO.getInstance().delete(player, tpBookMark);
            player.sendPacket((IStaticPacket)new ExGetBookMarkInfo(player));
        }
    }
}
