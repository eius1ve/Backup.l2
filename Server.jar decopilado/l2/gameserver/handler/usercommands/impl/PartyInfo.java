/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.usercommands.impl;

import l2.gameserver.handler.usercommands.IUserCommandHandler;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class PartyInfo
implements IUserCommandHandler {
    private static final int[] ay = new int[]{81};

    @Override
    public boolean useUserCommand(int n, Player player) {
        if (n != ay[0]) {
            return false;
        }
        Party party = player.getParty();
        if (!player.isInParty()) {
            return false;
        }
        Player player2 = party.getPartyLeader();
        if (player2 == null) {
            return false;
        }
        int n2 = party.getMemberCount();
        int n3 = party.getLootDistribution();
        player.sendPacket((IStaticPacket)SystemMsg.PARTY_INFORMATION);
        switch (n3) {
            case 0: {
                player.sendPacket((IStaticPacket)SystemMsg.LOOTING_METHOD_FINDERS_KEEPERS);
                break;
            }
            case 3: {
                player.sendPacket((IStaticPacket)SystemMsg.LOOTING_METHOD_BY_TURN);
                break;
            }
            case 4: {
                player.sendPacket((IStaticPacket)SystemMsg.LOOTING_METHOD_BY_TURN_INCLUDING_SPOIL);
                break;
            }
            case 1: {
                player.sendPacket((IStaticPacket)SystemMsg.LOOTING_METHOD_RANDOM);
                break;
            }
            case 2: {
                player.sendPacket((IStaticPacket)SystemMsg.LOOTING_METHOD_RANDOM_INCLUDING_SPOIL);
            }
        }
        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.PARTY_LEADER_C1).addString(player2.getName()));
        player.sendMessage(new CustomMessage("scripts.commands.user.PartyInfo.Members", player, new Object[0]).addNumber(n2));
        player.sendPacket((IStaticPacket)SystemMsg.ID500);
        return true;
    }

    @Override
    public final int[] getUserCommandList() {
        return ay;
    }
}
