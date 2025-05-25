/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.actions;

import l2.gameserver.model.entity.events.EventAction;
import l2.gameserver.model.entity.events.GlobalEvent;

public class StartStopAction
implements EventAction {
    public static final String EVENT = "event";
    private final String dh;
    private final boolean dd;

    public StartStopAction(String string, boolean bl) {
        this.dh = string;
        this.dd = bl;
    }

    @Override
    public void call(GlobalEvent globalEvent) {
        globalEvent.action(this.dh, this.dd);
    }
}
