/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExVoteSystemInfo;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;

public class RequestVoteNew
extends L2GameClientPacket {
    private int sg;

    @Override
    protected void readImpl() {
        this.sg = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.getTarget() == null) {
            player.sendPacket((IStaticPacket)SystemMsg.SELECT_TARGET);
            return;
        }
        if (!player.getTarget().isPlayer() || player.getTarget().getObjectId() != this.sg) {
            player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
            return;
        }
        Player player2 = player.getTarget().getPlayer();
        if (player2 == player) {
            player.sendPacket((IStaticPacket)SystemMsg.SELECT_TARGET);
            return;
        }
        if (player.getLevel() < 10) {
            player.sendPacket((IStaticPacket)SystemMsg.ONLY_CHARACTERS_OF_LEVEL_10_OR_ABOVE_ARE_AUTHORIZED_TO_MAKE_RECOMMENDATIONS);
            return;
        }
        if (player.getGivableRec() <= 0) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_MAKE_FURTHER_RECOMMENDATIONS_AT_THIS_TIME);
            return;
        }
        if (player.isRecommended(player2)) {
            player.sendPacket((IStaticPacket)SystemMsg.THAT_CHARACTER_HAS_ALREADY_BEEN_RECOMMENDED);
            return;
        }
        if (player2.getReceivedRec() >= 255) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_SELECTED_TARGET_CAN_NO_LONGER_RECEIVE_A_RECOMMENDATION);
            return;
        }
        player.giveRecommendation(player2);
        player.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.YOU_HAVE_RECOMMENDED_C1_YOU_HAVE_S2_RECOMMENDATIONS_LEFT).addName(player2)).addNumber(player.getGivableRec()));
        player2.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_BEEN_RECOMMENDED_BY_C1).addName(player));
        player.sendUserInfo(false);
        player.sendPacket((IStaticPacket)new ExVoteSystemInfo(player));
        player2.sendPacket((IStaticPacket)new ExVoteSystemInfo(player2));
        player2.broadcastUserInfo(true, new UserInfoType[0]);
    }
}
