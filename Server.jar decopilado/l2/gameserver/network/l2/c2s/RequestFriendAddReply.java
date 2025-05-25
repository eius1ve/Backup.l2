/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.Request;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.FriendAddRequestResult;
import l2.gameserver.network.l2.s2c.L2Friend;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class RequestFriendAddReply
extends L2GameClientPacket {
    private int dY;

    @Override
    protected void readImpl() {
        this.readC();
        this.dY = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Request request = player.getRequest();
        if (request == null || !request.isTypeOf(Request.L2RequestType.FRIEND)) {
            return;
        }
        if (player.isOutOfControl()) {
            request.cancel();
            player.sendActionFailed();
            return;
        }
        if (!request.isInProgress()) {
            request.cancel();
            player.sendActionFailed();
            return;
        }
        if (player.isOutOfControl()) {
            request.cancel();
            player.sendActionFailed();
            return;
        }
        Player player2 = request.getRequestor();
        if (player2 == null) {
            request.cancel();
            player.sendPacket((IStaticPacket)SystemMsg.THE_USER_WHO_REQUESTED_TO_BECOME_FRIENDS_IS_NOT_FOUND_IN_THE_GAME);
            player.sendActionFailed();
            return;
        }
        if (player2.getRequest() != request) {
            request.cancel();
            player.sendActionFailed();
            return;
        }
        if (this.dY == 0) {
            request.cancel();
            player2.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_FAILED_TO_ADD_A_FRIEND_TO_YOUR_FRIENDS_LIST);
            player.sendActionFailed();
            return;
        }
        player2.getFriendList().addFriend(player);
        player.getFriendList().addFriend(player2);
        player2.sendPacket(new IStaticPacket[]{SystemMsg.THAT_PERSON_HAS_BEEN_SUCCESSFULLY_ADDED_TO_YOUR_FRIEND_LIST, new SystemMessage(SystemMsg.S1_HAS_BEEN_ADDED_TO_YOUR_FRIENDS_LIST).addString(player.getName()), new L2Friend(player, true)});
        player.sendPacket(new IStaticPacket[]{new SystemMessage(SystemMsg.S1_HAS_JOINED_AS_A_FRIEND).addString(player2.getName()), new L2Friend(player2, true)});
        player.sendPacket((IStaticPacket)new FriendAddRequestResult(player2, 1));
        player2.sendPacket((IStaticPacket)new FriendAddRequestResult(player, 1));
    }
}
