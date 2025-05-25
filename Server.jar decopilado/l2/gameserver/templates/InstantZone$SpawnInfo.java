/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates;

import java.util.List;
import l2.gameserver.model.Territory;
import l2.gameserver.utils.Location;

@Deprecated
public static class InstantZone.SpawnInfo {
    private final int Fs;
    private final int Ft;
    private final int Fu;
    private final int Fv;
    private final int Fw;
    private final List<Location> dp;
    private final Territory j;

    public InstantZone.SpawnInfo(int n, int n2, int n3, int n4, int n5, Territory territory) {
        this(n, n2, n3, n4, n5, null, territory);
    }

    public InstantZone.SpawnInfo(int n, int n2, int n3, int n4, int n5, List<Location> list) {
        this(n, n2, n3, n4, n5, list, null);
    }

    public InstantZone.SpawnInfo(int n, int n2, int n3, int n4, int n5, List<Location> list, Territory territory) {
        this.Fs = n;
        this.Ft = n2;
        this.Fu = n3;
        this.Fv = n4;
        this.Fw = n5;
        this.dp = list;
        this.j = territory;
    }

    public int getSpawnType() {
        return this.Fs;
    }

    public int getNpcId() {
        return this.Ft;
    }

    public int getCount() {
        return this.Fu;
    }

    public int getRespawnDelay() {
        return this.Fv;
    }

    public int getRespawnRnd() {
        return this.Fw;
    }

    public List<Location> getCoords() {
        return this.dp;
    }

    public Territory getLoc() {
        return this.j;
    }
}
