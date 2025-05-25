/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.pfilter;

import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.network.pfilter.LimitAction;

class LimitAction.2
extends LimitAction {
    LimitAction.2() {
    }

    @Override
    public void doIt(GameClient gameClient) {
        gameClient.sendPacket(ActionFail.STATIC);
    }
}
