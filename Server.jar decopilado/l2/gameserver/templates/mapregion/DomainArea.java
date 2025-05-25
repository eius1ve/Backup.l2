/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.mapregion;

import l2.gameserver.model.Territory;
import l2.gameserver.templates.mapregion.RegionData;

public class DomainArea
implements RegionData {
    private final int GW;
    private final Territory l;

    public DomainArea(int n, Territory territory) {
        this.GW = n;
        this.l = territory;
    }

    public int getId() {
        return this.GW;
    }

    @Override
    public Territory getTerritory() {
        return this.l;
    }
}
