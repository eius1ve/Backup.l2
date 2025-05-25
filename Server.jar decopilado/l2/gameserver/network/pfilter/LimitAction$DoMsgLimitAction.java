/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.pfilter;

import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.pfilter.LimitAction;

private static final class LimitAction.DoMsgLimitAction
extends LimitAction {
    private final String fQ;

    private LimitAction.DoMsgLimitAction(String string) {
        this.fQ = string;
    }

    @Override
    public void doIt(GameClient gameClient) {
        gameClient.sendMessage(this.fQ);
    }

    @Override
    public boolean dropPacket() {
        return false;
    }
}
