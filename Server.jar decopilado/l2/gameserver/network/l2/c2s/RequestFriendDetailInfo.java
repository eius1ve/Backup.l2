/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.Friend;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.ExFriendDetailInfo;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class RequestFriendDetailInfo
extends L2GameClientPacket {
    private String ei;

    @Override
    protected void readImpl() throws Exception {
        this.ei = this.readS(Config.CNAME_MAXLEN);
    }

    @Override
    protected void runImpl() throws Exception {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        int n = CharacterDAO.getInstance().getObjectIdByName(this.ei);
        if (n == 0) {
            return;
        }
        Friend friend = player.getFriendList().getList().get(n);
        if (friend == null) {
            return;
        }
        this.sendPacket((L2GameServerPacket)new ExFriendDetailInfo(player.getObjectId(), friend));
    }
}
