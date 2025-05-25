/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestUpdateFriendMemo
extends L2GameClientPacket {
    private String _name;
    private String cW;

    @Override
    protected void readImpl() throws Exception {
        this._name = this.readS();
        this.cW = this.readS();
    }

    @Override
    protected void runImpl() throws Exception {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        player.getFriendList().updateMemo(this._name, this.cW);
    }
}
