/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.World;

public class WorldRegion.ActivateTask
extends RunnableImpl {
    private boolean cC;

    public WorldRegion.ActivateTask(boolean bl) {
        this.cC = bl;
    }

    @Override
    public void runImpl() throws Exception {
        if (this.cC) {
            World.activate(WorldRegion.this);
        } else {
            World.deactivate(WorldRegion.this);
        }
    }
}
