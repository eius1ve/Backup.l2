/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.mapregion;

import java.util.Map;
import l2.gameserver.model.Territory;
import l2.gameserver.model.base.Race;
import l2.gameserver.templates.mapregion.RegionData;
import l2.gameserver.templates.mapregion.RestartPoint;

public class RestartArea
implements RegionData {
    private final Territory m;
    private final Map<Race, RestartPoint> bW;

    public RestartArea(Territory territory, Map<Race, RestartPoint> map) {
        this.m = territory;
        this.bW = map;
    }

    @Override
    public Territory getTerritory() {
        return this.m;
    }

    public Map<Race, RestartPoint> getRestartPoint() {
        return this.bW;
    }
}
