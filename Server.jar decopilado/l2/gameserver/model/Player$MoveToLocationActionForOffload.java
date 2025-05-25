/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.concurrent.atomic.AtomicReference;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.utils.Location;

private static class Player.MoveToLocationActionForOffload
extends Creature.MoveToLocationAction {
    public Player.MoveToLocationActionForOffload(Creature creature, Location location, Location location2, boolean bl, int n, boolean bl2) {
        super(creature, location, location2, bl, n, bl2);
    }

    private void br() {
        Player player = (Player)this.getActor();
        Player.MoveToLocationOffloadData moveToLocationOffloadData = null;
        if (player != null && (moveToLocationOffloadData = (Player.MoveToLocationOffloadData)((AtomicReference)((Object)player.a)).get()) != null && ((AtomicReference)((Object)player.a)).compareAndSet(moveToLocationOffloadData, null)) {
            player.moveToLocation(moveToLocationOffloadData.getDest(), moveToLocationOffloadData.getIndent(), moveToLocationOffloadData.isPathfind());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected boolean onTick(double d) {
        boolean bl;
        try {
            bl = super.onTick(d);
        }
        finally {
            this.br();
        }
        return bl;
    }

    @Override
    protected void onFinish(boolean bl, boolean bl2) {
        try {
            super.onFinish(bl, bl2);
        }
        finally {
            this.br();
        }
    }
}
