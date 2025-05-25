/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.commons.collections.CollectionUtils;
import l2.gameserver.dao.CharacterTPBookmarkDAO;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.TpBookMark;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExGetBookMarkInfo;

public class RequestModifyBookMarkSlot
extends L2GameClientPacket {
    private String _name;
    private String cX;
    private int kp;
    private int kk;

    @Override
    protected void readImpl() {
        this.kk = this.readD();
        this._name = this.readS(32);
        this.kp = this.readD();
        this.cX = this.readS(4);
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        TpBookMark tpBookMark = CollectionUtils.safeGet(player.getTpBookMarks(), this.kk - 1);
        if (tpBookMark != null) {
            tpBookMark.setName(this._name);
            tpBookMark.setIcon(this.kp);
            tpBookMark.setAcronym(this.cX);
            CharacterTPBookmarkDAO.getInstance().update(player, tpBookMark);
            player.sendPacket((IStaticPacket)new ExGetBookMarkInfo(player));
        }
    }
}
