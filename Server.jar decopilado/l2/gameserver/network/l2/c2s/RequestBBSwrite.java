/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.handler.bbs.CommunityBoardManager;
import l2.gameserver.handler.bbs.ICommunityBoardHandler;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class RequestBBSwrite
extends L2GameClientPacket {
    private String dU;
    private String dV;
    private String dW;
    private String dX;
    private String dY;
    private String dZ;

    @Override
    public void readImpl() {
        this.dU = this.readS();
        this.dV = this.readS();
        this.dW = this.readS();
        this.dX = this.readS();
        this.dY = this.readS();
        this.dZ = this.readS();
    }

    @Override
    public void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        ICommunityBoardHandler iCommunityBoardHandler = CommunityBoardManager.getInstance().getCommunityHandler(this.dU, player);
        if (iCommunityBoardHandler != null) {
            if (!Config.COMMUNITYBOARD_ENABLED) {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_COMMUNITY_SERVER_IS_CURRENTLY_OFFLINE));
            } else {
                iCommunityBoardHandler.onWriteCommand(player, this.dU, this.dV, this.dW, this.dX, this.dY, this.dZ);
            }
        }
    }
}
