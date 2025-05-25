/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NickNameChanged;
import l2.gameserver.utils.Util;

public class RequestGiveNickName
extends L2GameClientPacket {
    private String ek;
    private String _title;

    @Override
    protected void readImpl() {
        this.ek = this.readS(Config.CNAME_MAXLEN);
        this._title = this.readS();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (!this._title.isEmpty() && !Util.isMatchingRegexp(this._title, Config.CLAN_TITLE_TEMPLATE)) {
            player.sendMessage(new CustomMessage("l2p.gameserver.clientpackets.RequestGiveNickName.IncorrectTittle", player, new Object[0]));
            return;
        }
        if (player.isNoble() && this.ek.equals(player.getName())) {
            player.setTitle(this._title);
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_TITLE_HAS_BEEN_CHANGED);
            player.broadcastPacket(new NickNameChanged(player));
            return;
        }
        if ((player.getClanPrivileges() & 4) != 4) {
            return;
        }
        if (player.getClan().getLevel() < 3) {
            player.sendPacket((IStaticPacket)SystemMsg.A_PLAYER_CAN_ONLY_BE_GRANTED_A_TITLE_IF_THE_CLAN_IS_LEVEL_3_OR_ABOVE);
            return;
        }
        UnitMember unitMember = player.getClan().getAnyMember(this.ek);
        if (unitMember != null) {
            unitMember.setTitle(this._title);
            if (unitMember.isOnline()) {
                unitMember.getPlayer().sendPacket((IStaticPacket)SystemMsg.YOUR_TITLE_HAS_BEEN_CHANGED);
                unitMember.getPlayer().sendChanges();
            }
        } else {
            player.sendMessage(new CustomMessage("l2p.gameserver.clientpackets.RequestGiveNickName.NotInClan", player, new Object[0]));
        }
    }
}
