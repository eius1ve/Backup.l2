/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.Territory;
import l2.gameserver.utils.Location;

public class DimensionalRiftManager.DimensionalRiftRoom {
    private final Territory h;
    private final Location o;
    private final boolean bg;
    private final List<SimpleSpawner> ax;

    public DimensionalRiftManager.DimensionalRiftRoom(Territory territory, Location location, boolean bl) {
        this.h = territory;
        this.o = location;
        this.bg = bl;
        this.ax = new ArrayList<SimpleSpawner>();
    }

    public Location getTeleportCoords() {
        return this.o;
    }

    public boolean checkIfInZone(Location location) {
        return this.checkIfInZone(location.x, location.y, location.z);
    }

    public boolean checkIfInZone(int n, int n2, int n3) {
        return this.h.isInside(n, n2, n3);
    }

    public boolean isBossRoom() {
        return this.bg;
    }

    public List<SimpleSpawner> getSpawns() {
        return this.ax;
    }
}
