/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.utils;

import l2.gameserver.Config;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.World;

public class MapRegionUtils {
    private MapRegionUtils() {
    }

    public static int regionX(GameObject gameObject) {
        return MapRegionUtils.regionX(gameObject.getX());
    }

    public static int regionY(GameObject gameObject) {
        return MapRegionUtils.regionY(gameObject.getY());
    }

    public static int regionX(int n) {
        return (n - World.MAP_MIN_X >> 15) + Config.GEO_X_FIRST;
    }

    public static int regionY(int n) {
        return (n - World.MAP_MIN_Y >> 15) + Config.GEO_Y_FIRST;
    }
}
