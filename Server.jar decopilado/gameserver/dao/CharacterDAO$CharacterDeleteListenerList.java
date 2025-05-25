/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.dao;

import l2.commons.listener.ListenerList;
import l2.gameserver.GameServer;
import l2.gameserver.listener.game.OnCharacterDeleteListener;

public class CharacterDAO.CharacterDeleteListenerList
extends ListenerList<GameServer> {
    public void onCharacterDelete(int n) {
        this.forEachListener(OnCharacterDeleteListener.class, onCharacterDeleteListener -> {
            try {
                onCharacterDeleteListener.onCharacterDelete(n);
            }
            catch (Exception exception) {
                ay.warn("Character delete listener", (Throwable)exception);
            }
        });
    }
}
