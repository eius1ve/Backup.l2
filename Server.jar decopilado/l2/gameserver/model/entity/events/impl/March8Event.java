/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.impl;

import java.util.Calendar;
import l2.commons.collections.MultiValueSet;
import l2.gameserver.Announcements;
import l2.gameserver.model.entity.events.GlobalEvent;

public class March8Event
extends GlobalEvent {
    private Calendar a = Calendar.getInstance();
    private static final long cb = 604800000L;

    public March8Event(MultiValueSet<String> multiValueSet) {
        super(multiValueSet);
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void startEvent() {
        super.startEvent();
        Announcements.getInstance().announceToAll("Test startEvent");
    }

    @Override
    public void stopEvent() {
        super.stopEvent();
        Announcements.getInstance().announceToAll("Test stopEvent");
    }

    @Override
    public void reCalcNextTime(boolean bl) {
        this.clearActions();
        if (bl) {
            this.a.set(2, 2);
            this.a.set(5, 8);
            this.a.set(11, 0);
            this.a.set(12, 0);
            this.a.set(13, 0);
            if (this.a.getTimeInMillis() + 604800000L < System.currentTimeMillis()) {
                this.a.add(1, 1);
            }
        } else {
            this.a.add(1, 1);
        }
        this.registerActions();
    }

    @Override
    protected long startTimeMillis() {
        return this.a.getTimeInMillis();
    }
}
