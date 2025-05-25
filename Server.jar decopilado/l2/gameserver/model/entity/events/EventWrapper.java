/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events;

import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.taskmanager.actionrunner.ActionWrapper;

public class EventWrapper
extends ActionWrapper {
    private final GlobalEvent a;
    private final int ls;

    public EventWrapper(String string, GlobalEvent globalEvent, int n) {
        super(string);
        this.a = globalEvent;
        this.ls = n;
    }

    @Override
    public void runImpl0() throws Exception {
        this.a.timeActions(this.ls);
    }
}
