/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.sepulchers.model;

import java.util.List;
import l2.gameserver.model.Zone;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.utils.Location;

public class WayData {
    private final Location p;
    private final Location q;
    private final List<DoorInstance> aI;
    private final Zone C;

    public WayData(Location location, Location location2, List<DoorInstance> list, Zone zone) {
        this.p = location;
        this.q = location2;
        this.aI = list;
        this.C = zone;
    }

    public List<DoorInstance> getDoors() {
        return this.aI;
    }

    public Location getEntry() {
        return this.p;
    }

    public Location getEscape() {
        return this.q;
    }

    public Zone getZone() {
        return this.C;
    }
}
