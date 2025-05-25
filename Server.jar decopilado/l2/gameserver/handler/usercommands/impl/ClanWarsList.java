/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.handler.usercommands.impl;

import java.util.Collection;
import java.util.LinkedHashSet;
import l2.gameserver.handler.usercommands.IUserCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import org.apache.commons.lang3.ArrayUtils;

public class ClanWarsList
implements IUserCommandHandler {
    private static final int fE = 88;
    private static final int fF = 89;
    private static final int fG = 90;
    private static final int[] as = new int[]{88, 89, 90};

    private static void a(Player player, Clan clan) {
        player.sendPacket((IStaticPacket)SystemMsg.CLANS_YOUVE_DECLARED_WAR_ON);
        ClanWarsList.a(player, clan.getEnemyClans());
    }

    private static void b(Player player, Clan clan) {
        player.sendPacket((IStaticPacket)SystemMsg.CLANS_THAT_HAVE_DECLARED_WAR_ON_YOU);
        ClanWarsList.a(player, clan.getAttackerClans());
    }

    private static void c(Player player, Clan clan) {
        LinkedHashSet<Clan> linkedHashSet = new LinkedHashSet<Clan>(clan.getEnemyClans());
        linkedHashSet.retainAll(clan.getAttackerClans());
        player.sendPacket((IStaticPacket)SystemMsg.WAR_LIST);
        ClanWarsList.a(player, linkedHashSet);
    }

    private static void a(Player player, Collection<Clan> collection) {
        for (Clan clan : collection) {
            Alliance alliance = clan.getAlliance();
            player.sendPacket((IStaticPacket)(alliance != null ? ((SystemMessage)new SystemMessage(SystemMsg._S1_S2_ALLIANCE).addString(clan.getName())).addString(alliance.getAllyName()) : new SystemMessage(SystemMsg._S1_NO_ALLIANCE_EXISTS).addString(clan.getName())));
        }
        player.sendPacket((IStaticPacket)SystemMsg.ID490);
    }

    @Override
    public boolean useUserCommand(int n, Player player) {
        if (!ArrayUtils.contains((int[])as, (int)n)) {
            return false;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            player.sendPacket((IStaticPacket)SystemMsg.NOT_JOINED_IN_ANY_CLAN);
            return false;
        }
        switch (n) {
            case 88: {
                ClanWarsList.a(player, clan);
                return true;
            }
            case 89: {
                ClanWarsList.b(player, clan);
                return true;
            }
            case 90: {
                ClanWarsList.c(player, clan);
                return true;
            }
        }
        return false;
    }

    @Override
    public int[] getUserCommandList() {
        return as;
    }
}
