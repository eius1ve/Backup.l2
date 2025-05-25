/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver;

import l2.commons.listener.ListenerList;
import l2.gameserver.GameServer;
import l2.gameserver.listener.game.OnDayNightChangeListener;

protected class GameTimeController.GameTimeListenerList
extends ListenerList<GameServer> {
    protected GameTimeController.GameTimeListenerList() {
    }

    public void onDay() {
        this.forEachListener(OnDayNightChangeListener.class, onDayNightChangeListener -> {
            try {
                onDayNightChangeListener.onDay();
            }
            catch (Exception exception) {
                ao.warn("Exception during day change", (Throwable)exception);
            }
        });
    }

    public void onNight() {
        this.forEachListener(OnDayNightChangeListener.class, onDayNightChangeListener -> {
            try {
                onDayNightChangeListener.onNight();
            }
            catch (Exception exception) {
                ao.warn("Exception during night change", (Throwable)exception);
            }
        });
    }
}
