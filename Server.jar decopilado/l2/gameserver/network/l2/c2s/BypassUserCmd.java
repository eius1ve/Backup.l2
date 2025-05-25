/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.handler.usercommands.IUserCommandHandler;
import l2.gameserver.handler.usercommands.UserCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;

public class BypassUserCmd
extends L2GameClientPacket {
    private int pX;

    @Override
    protected void readImpl() {
        this.pX = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        IUserCommandHandler iUserCommandHandler = UserCommandHandler.getInstance().getUserCommandHandler(this.pX);
        if (iUserCommandHandler == null) {
            player.sendMessage(new CustomMessage("common.S1NotImplemented", player, new Object[0]).addString(String.valueOf(this.pX)));
        } else {
            iUserCommandHandler.useUserCommand(this.pX, player);
        }
    }
}
