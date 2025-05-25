/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.sepulchers.event;

import java.util.concurrent.atomic.AtomicBoolean;
import l2.gameserver.instancemanager.sepulchers.event.SepulcherEvent;

public class EventData {
    private final SepulcherEvent a;
    private final boolean bj;
    private final AtomicBoolean f = new AtomicBoolean();

    public EventData(SepulcherEvent sepulcherEvent, boolean bl) {
        this.a = sepulcherEvent;
        this.bj = bl;
    }

    public SepulcherEvent getEvent() {
        return this.a;
    }

    public AtomicBoolean getCallFlag() {
        return this.f;
    }

    public boolean isSingleCall() {
        return this.bj;
    }
}
