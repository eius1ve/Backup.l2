/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.tables.ClanTable;

public class RequestStartPledgeWar
extends L2GameClientPacket {
    private String eB;

    @Override
    protected void readImpl() {
        this.eB = this.readS(32);
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            player.sendActionFailed();
            return;
        }
        if ((player.getClanPrivileges() & 0x20) != 32) {
            player.sendActionFailed();
            return;
        }
        if (clan.getWarsCount() >= 30) {
            player.sendPacket(SystemMsg.A_DECLARATION_OF_WAR_AGAINST_MORE_THAN_30_CLANS_CANT_BE_MADE_AT_THE_SAME_TIME, ActionFail.STATIC);
            return;
        }
        if (clan.getLevel() < Config.MIN_CLAN_LEVEL_FOR_DECLARED_WAR || clan.getAllSize() < Config.MIN_CLAN_MEMBER_FOR_DECLARED_WAR) {
            player.sendPacket(SystemMsg.A_CLAN_WAR_CAN_ONLY_BE_DECLARED_IF_THE_CLAN_IS_LEVEL_3_OR_ABOVE_AND_THE_NUMBER_OF_CLAN_MEMBERS_IS_FIFTEEN_OR_GREATER, ActionFail.STATIC);
            return;
        }
        Clan clan2 = ClanTable.getInstance().getClanByName(this.eB);
        if (clan2 == null) {
            player.sendPacket(SystemMsg.A_CLAN_WAR_CANNOT_BE_DECLARED_AGAINST_A_CLAN_THAT_DOES_NOT_EXIST, ActionFail.STATIC);
            return;
        }
        if (clan.equals(clan2)) {
            player.sendPacket(SystemMsg.FOOL_YOU_CANNOT_DECLARE_WAR_AGAINST_YOUR_OWN_CLAN, ActionFail.STATIC);
            return;
        }
        if (clan.isAtWarWith(clan2.getClanId())) {
            player.sendPacket(SystemMsg.WAR_HAS_ALREADY_BEEN_DECLARED_AGAINST_THAT_CLAN_BUT_ILL_MAKE_NOTE_THAT_YOU_REALLY_DONT_LIKE_THEM, ActionFail.STATIC);
            return;
        }
        if (clan.getAllyId() == clan2.getAllyId() && clan.getAllyId() != 0) {
            player.sendPacket(SystemMsg.A_DECLARATION_OF_CLAN_WAR_AGAINST_AN_ALLIED_CLAN_CANT_BE_MADE, ActionFail.STATIC);
            return;
        }
        if (clan2.getLevel() < Config.MIN_CLAN_LEVEL_FOR_DECLARED_WAR || clan2.getAllSize() < Config.MIN_CLAN_MEMBER_FOR_DECLARED_WAR) {
            player.sendPacket(SystemMsg.A_CLAN_WAR_CAN_ONLY_BE_DECLARED_IF_THE_CLAN_IS_LEVEL_3_OR_ABOVE_AND_THE_NUMBER_OF_CLAN_MEMBERS_IS_FIFTEEN_OR_GREATER, ActionFail.STATIC);
            return;
        }
        ClanTable.getInstance().startClanWar(player.getClan(), clan2);
    }
}
