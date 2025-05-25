/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity;

import l2.commons.listener.ListenerList;
import l2.gameserver.GameServer;
import l2.gameserver.listener.game.OnSSPeriodListener;
import l2.gameserver.model.entity.SevenSigns;

protected class SevenSigns.SSListenerList
extends ListenerList<GameServer> {
    protected SevenSigns.SSListenerList() {
    }

    public void onPeriodChange() {
        if (SevenSigns.getInstance().getCurrentPeriod() == 3) {
            SevenSigns.getInstance().getCabalHighestScore();
        }
        this.forEachListener(OnSSPeriodListener.class, onSSPeriodListener -> onSSPeriodListener.onPeriodChange(SevenSigns.getInstance().getCurrentPeriod()));
    }
}
