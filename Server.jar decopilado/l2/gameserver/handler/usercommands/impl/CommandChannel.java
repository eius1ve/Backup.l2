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
import l2.gameserver.network.l2.s2c.ExMultiPartyCommandChannelInfo;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class CommandChannel
implements IUserCommandHandler {
    private static final int[] at = new int[]{92, 93, 96, 97};

    @Override
    public boolean useUserCommand(int n, Player player) {
        if (n != at[0] && n != at[1] && n != at[2] && n != at[3]) {
            return false;
        }
        switch (n) {
            case 92: {
                player.sendMessage(new CustomMessage("usercommandhandlers.CommandChannel", player, new Object[0]));
                break;
            }
            case 93: {
                if (!player.isInParty() || !player.getParty().isInCommandChannel()) {
                    return true;
                }
                if (player.getParty().getCommandChannel().getChannelLeader() == player) {
                    l2.gameserver.model.CommandChannel commandChannel = player.getParty().getCommandChannel();
                    commandChannel.disbandChannel();
                    break;
                }
                player.sendPacket((IStaticPacket)SystemMsg.ONLY_THE_CREATOR_OF_A_COMMAND_CHANNEL_CAN_USE_THE_CHANNEL_DISMISS_COMMAND);
                break;
            }
            case 96: {
                if (!player.isInParty() || !player.getParty().isInCommandChannel()) {
                    return true;
                }
                if (!player.getParty().isLeader(player)) {
                    player.sendPacket((IStaticPacket)SystemMsg.ONLY_A_PARTY_LEADER_CAN_LEAVE_A_COMMAND_CHANNEL);
                    return true;
                }
                l2.gameserver.model.CommandChannel commandChannel = player.getParty().getCommandChannel();
                if (commandChannel.getChannelLeader() == player) {
                    if (commandChannel.getParties().size() > 1) {
                        return false;
                    }
                    commandChannel.disbandChannel();
                    return true;
                }
                Party party = player.getParty();
                commandChannel.removeParty(party);
                party.broadCast(SystemMsg.YOU_HAVE_QUIT_THE_COMMAND_CHANNEL);
                commandChannel.broadCast(new IStaticPacket[]{new SystemMessage(SystemMsg.C1S_PARTY_HAS_LEFT_THE_COMMAND_CHANNEL).addString(player.getName())});
                break;
            }
            case 97: {
                if (!player.isInParty() || !player.getParty().isInCommandChannel()) {
                    return false;
                }
                player.sendPacket((IStaticPacket)new ExMultiPartyCommandChannelInfo(player.getParty().getCommandChannel()));
            }
        }
        return true;
    }

    @Override
    public final int[] getUserCommandList() {
        return at;
    }
}
