/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2;

import l2.gameserver.network.l2.GameClient;

public static class CGModule.CGContext {
    private GameClient b;

    public CGModule.CGContext(GameClient gameClient) {
        this.b = gameClient;
    }

    public GameClient getGameClient() {
        return this.b;
    }

    public void setGameClient(GameClient gameClient) {
        this.b = gameClient;
    }
}
