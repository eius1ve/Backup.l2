/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.actions;

import l2.gameserver.model.entity.events.EventAction;
import l2.gameserver.model.entity.events.GlobalEvent;

public class ActiveDeactiveAction
implements EventAction {
    private final boolean cZ;
    private final String db;

    public ActiveDeactiveAction(boolean bl, String string) {
        this.cZ = bl;
        this.db = string;
    }

    @Override
    public void call(GlobalEvent globalEvent) {
        globalEvent.zoneAction(this.db, this.cZ);
    }
}
