/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.utils.Location;

private static class Player.MoveToLocationOffloadData {
    private final Location D;
    private final int iR;
    private final boolean cl;

    public Player.MoveToLocationOffloadData(Location location, int n, boolean bl) {
        this.D = location;
        this.iR = n;
        this.cl = bl;
    }

    public Location getDest() {
        return this.D;
    }

    public int getIndent() {
        return this.iR;
    }

    public boolean isPathfind() {
        return this.cl;
    }
}
