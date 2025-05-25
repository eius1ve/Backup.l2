/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.instancemanager;

import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.World;
import l2.gameserver.templates.mapregion.RegionData;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.ArrayUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MapRegionManager
extends AbstractHolder {
    private static final MapRegionManager a = new MapRegionManager();
    private RegionData[][][] a = new RegionData[World.WORLD_SIZE_X][World.WORLD_SIZE_Y][0];

    public static MapRegionManager getInstance() {
        return a;
    }

    private MapRegionManager() {
    }

    private int regionX(int n) {
        return n - World.MAP_MIN_X >> 15;
    }

    private int regionY(int n) {
        return n - World.MAP_MIN_Y >> 15;
    }

    public void addRegionData(RegionData regionData) {
        for (int i = this.regionX(regionData.getTerritory().getXmin()); i <= this.regionX(regionData.getTerritory().getXmax()); ++i) {
            for (int j = this.regionY(regionData.getTerritory().getYmin()); j <= this.regionY(regionData.getTerritory().getYmax()); ++j) {
                this.a[i][j] = (RegionData[])ArrayUtils.add((Object[])this.a[i][j], (Object)regionData);
            }
        }
    }

    public <T extends RegionData> T getRegionData(Class<T> clazz, GameObject gameObject) {
        return this.getRegionData(clazz, gameObject.getX(), gameObject.getY(), gameObject.getZ());
    }

    public <T extends RegionData> T getRegionData(Class<T> clazz, Location location) {
        return this.getRegionData(clazz, location.getX(), location.getY(), location.getZ());
    }

    public <T extends RegionData> T getRegionData(Class<T> clazz, int n, int n2, int n3) {
        for (RegionData regionData : this.a[this.regionX(n)][this.regionY(n2)]) {
            if (regionData.getClass() != clazz || !regionData.getTerritory().isInside(n, n2, n3)) continue;
            return (T)regionData;
        }
        return null;
    }

    @Override
    public int size() {
        return World.WORLD_SIZE_X * World.WORLD_SIZE_Y;
    }

    @Override
    public void clear() {
    }
}
