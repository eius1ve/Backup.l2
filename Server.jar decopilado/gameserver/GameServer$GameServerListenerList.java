/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver;

import l2.commons.listener.ListenerList;
import l2.gameserver.GameServer;
import l2.gameserver.listener.game.OnShutdownListener;
import l2.gameserver.listener.game.OnStartListener;

public class GameServer.GameServerListenerList
extends ListenerList<GameServer> {
    public void onStart() {
        this.forEachListener(OnStartListener.class, OnStartListener::onStart);
    }

    public void onShutdown() {
        this.forEachListener(OnShutdownListener.class, OnShutdownListener::onShutdown);
    }
}
