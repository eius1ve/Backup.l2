/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events;

import l2.commons.listener.ListenerList;
import l2.gameserver.listener.event.OnStartStopListener;
import l2.gameserver.model.entity.events.GlobalEvent;

private class GlobalEvent.ListenerListImpl
extends ListenerList<GlobalEvent> {
    private GlobalEvent.ListenerListImpl() {
    }

    public void onStart() {
        this.forEachListener(OnStartStopListener.class, onStartStopListener -> onStartStopListener.onStart(GlobalEvent.this));
    }

    public void onStop() {
        this.forEachListener(OnStartStopListener.class, onStartStopListener -> onStartStopListener.onStop(GlobalEvent.this));
    }
}
