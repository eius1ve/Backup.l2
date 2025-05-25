/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import l2.gameserver.utils.Location;

private static class StadiumPool.StadiumTemplate {
    public Location[] plocs;
    public Location[] blocs;
    public Location oloc;
    public int zid;

    public StadiumPool.StadiumTemplate(int n, Location location) {
        this.oloc = location;
        this.zid = n;
    }
}
