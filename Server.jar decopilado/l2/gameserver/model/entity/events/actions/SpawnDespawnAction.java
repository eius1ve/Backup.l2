/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.actions;

import l2.gameserver.model.entity.events.EventAction;
import l2.gameserver.model.entity.events.GlobalEvent;

public class SpawnDespawnAction
implements EventAction {
    private final boolean dc;
    private final String dg;

    public SpawnDespawnAction(String string, boolean bl) {
        this.dc = bl;
        this.dg = string;
    }

    @Override
    public void call(GlobalEvent globalEvent) {
        globalEvent.spawnAction(this.dg, this.dc);
    }
}
