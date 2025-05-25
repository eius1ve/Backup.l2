/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.gamecomm;

import l2.authserver.Config;
import l2.authserver.network.gamecomm.as2gs.PingRequest;
import l2.commons.threading.RunnableImpl;

private class GameServerConnection.PingTask
extends RunnableImpl {
    private GameServerConnection.PingTask() {
    }

    @Override
    public void runImpl() {
        if (Config.GAME_SERVER_PING_RETRY > 0 && GameServerConnection.this.dv > Config.GAME_SERVER_PING_RETRY) {
            _log.warn("Gameserver " + GameServerConnection.this.a.getId() + " [" + GameServerConnection.this.a.getName() + "] : ping timeout!");
            GameServerConnection.this.closeNow();
            return;
        }
        ++GameServerConnection.this.dv;
        GameServerConnection.this.sendPacket(new PingRequest());
    }
}
