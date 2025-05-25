/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.actions;

import l2.gameserver.model.entity.events.EventAction;
import l2.gameserver.model.entity.events.GlobalEvent;

public class OpenCloseAction
implements EventAction {
    private final boolean db;
    private final String dc;

    public OpenCloseAction(boolean bl, String string) {
        this.db = bl;
        this.dc = string;
    }

    @Override
    public void call(GlobalEvent globalEvent) {
        globalEvent.doorAction(this.dc, this.db);
    }
}
