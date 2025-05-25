/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.pfilter;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.s2c.LeaveWorld;
import l2.gameserver.network.pfilter.LimitAction;

class LimitAction.3
extends LimitAction {
    LimitAction.3() {
    }

    @Override
    public void doIt(GameClient gameClient) {
        Player player = gameClient.getActiveChar();
        if (player != null) {
            player.kick();
        } else {
            gameClient.close(LeaveWorld.STATIC);
        }
    }
}
