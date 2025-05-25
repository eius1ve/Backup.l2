/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import java.util.ArrayList;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.ClanTable;

public class RequestAllyInfo
extends L2GameClientPacket {
    @Override
    protected void readImpl() {
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Alliance alliance = player.getAlliance();
        if (alliance == null) {
            return;
        }
        int n = 0;
        Clan clan = player.getAlliance().getLeader();
        n = ClanTable.getInstance().getAlliance(clan.getAllyId()).getMembers().length;
        int[] nArray = new int[n + 1];
        int[] nArray2 = new int[n + 1];
        Clan[] clanArray = player.getAlliance().getMembers();
        for (int i = 0; i < n; ++i) {
            nArray[i + 1] = clanArray[i].getOnlineMembers(0).size();
            nArray2[i + 1] = clanArray[i].getAllSize();
            nArray[0] = nArray[0] + nArray[i + 1];
            nArray2[0] = nArray2[0] + nArray2[i + 1];
        }
        ArrayList<SystemMessage> arrayList = new ArrayList<SystemMessage>(7 + 5 * n);
        arrayList.add(new SystemMessage(SystemMsg.ALLIANCE_INFORMATION));
        arrayList.add((SystemMessage)new SystemMessage(SystemMsg.ALLIANCE_NAME_S1).addString(player.getClan().getAlliance().getAllyName()));
        arrayList.add((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.CONNECTION_S1__TOTAL_S2).addNumber(nArray[0])).addNumber(nArray2[0]));
        arrayList.add((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.ALLIANCE_LEADER_S2_OF_S1).addString(clan.getName())).addString(clan.getLeaderName()));
        arrayList.add((SystemMessage)new SystemMessage(SystemMsg.AFFILIATED_CLANS_TOTAL_S1_CLANS).addNumber(n));
        arrayList.add(new SystemMessage(SystemMsg.CLAN_INFORMATION));
        for (int i = 0; i < n; ++i) {
            arrayList.add((SystemMessage)new SystemMessage(SystemMsg.CLAN_NAME_S1).addString(clanArray[i].getName()));
            arrayList.add((SystemMessage)new SystemMessage(SystemMsg.CLAN_LEADER__S1).addString(clanArray[i].getLeaderName()));
            arrayList.add((SystemMessage)new SystemMessage(SystemMsg.CLAN_LEVEL_S1).addNumber(clanArray[i].getLevel()));
            arrayList.add((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.CONNECTION_S1__TOTAL_S2).addNumber(nArray[i + 1])).addNumber(nArray2[i + 1]));
            arrayList.add(new SystemMessage(SystemMsg.ID500));
        }
        arrayList.add(new SystemMessage(SystemMsg.ID490));
        player.sendPacket(arrayList);
    }
}
