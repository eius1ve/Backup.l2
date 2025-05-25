/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.network.l2.GameClient;

private static class GameClient.PingTask
extends RunnableImpl {
    private final GameClient c;

    private GameClient.PingTask(GameClient gameClient) {
        this.c = gameClient;
    }

    @Override
    public void runImpl() throws Exception {
        if (this.c == null || !this.c.isConnected()) {
            return;
        }
        this.c.bS();
    }
}
