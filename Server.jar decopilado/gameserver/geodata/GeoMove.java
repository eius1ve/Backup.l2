/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.geodata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.geodata.PathFind;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.PositionUtils;

public class GeoMove {
    private static List<Location> a(int n, int n2, int n3, int n4, int n5, int n6, boolean bl, int n7) {
        if (Math.abs(n3 - n6) > 256) {
            return Collections.emptyList();
        }
        PathFind pathFind = new PathFind(n, n2, n3 = GeoEngine.getHeight(n, n2, n3, n7), n4, n5, n6 = GeoEngine.getHeight(n4, n5, n6, n7), bl, n7);
        if (pathFind.getPath() == null || pathFind.getPath().isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<Location> arrayList = new ArrayList<Location>(pathFind.getPath().size() + 2);
        arrayList.add(new Location(n, n2, n3));
        for (Location location : pathFind.getPath()) {
            arrayList.add(location.geo2world());
        }
        arrayList.add(new Location(n4, n5, n6));
        if (Config.PATH_CLEAN) {
            GeoMove.a(arrayList, n7);
        }
        return arrayList;
    }

    public static List<List<Location>> findMovePath(int n, int n2, int n3, int n4, int n5, int n6, boolean bl, int n7) {
        return GeoMove.a(GeoMove.a(n, n2, n3, n4, n5, n6, bl, n7), n7);
    }

    private static List<List<Location>> a(List<Location> list, int n) {
        int n2 = list.size();
        if (n2 <= 1) {
            return Collections.emptyList();
        }
        ArrayList<List<Location>> arrayList = new ArrayList<List<Location>>(n2);
        for (int i = 1; i < n2; ++i) {
            Location location = list.get(i);
            Location location2 = list.get(i - 1);
            List<Location> list2 = GeoEngine.MoveList(location2.x, location2.y, location2.z, location.x, location.y, n, true);
            if (list2 == null) {
                return Collections.emptyList();
            }
            if (list2.isEmpty()) continue;
            arrayList.add(list2);
        }
        return arrayList;
    }

    public static List<Location> constructMoveList(Location location, Location location2) {
        location = location.world2geo();
        location2 = location2.world2geo();
        int n = location2.x - location.x;
        int n2 = location2.y - location.y;
        int n3 = location2.z - location.z;
        int n4 = Math.abs(n);
        int n5 = Math.abs(n2);
        int n6 = Math.abs(n3);
        float f = Math.max(Math.max(n4, n5), n6);
        if (f == 0.0f) {
            return Collections.emptyList();
        }
        float f2 = (float)n / f;
        float f3 = (float)n2 / f;
        float f4 = (float)n3 / f;
        float f5 = location.x;
        float f6 = location.y;
        float f7 = location.z;
        ArrayList<Location> arrayList = new ArrayList<Location>((int)f + 1);
        arrayList.add(new Location(location.x, location.y, location.z));
        int n7 = 0;
        while ((float)n7 < f) {
            arrayList.add(new Location((int)((f5 += f2) + 0.5f), (int)((f6 += f3) + 0.5f), (int)((f7 += f4) + 0.5f)));
            ++n7;
        }
        return arrayList;
    }

    private static void a(List<Location> list, int n) {
        Location location;
        Location location2;
        int n2;
        int n3 = list.size();
        if (n3 > 2) {
            for (n2 = 2; n2 < n3; ++n2) {
                Location location3 = list.get(n2);
                location2 = list.get(n2 - 1);
                location = list.get(n2 - 2);
                if (!location.equals(location2) && !location3.equals(location2) && !GeoMove.a(location, location2, location3)) continue;
                list.remove(n2 - 1);
                --n3;
                n2 = Math.max(2, n2 - 2);
            }
        }
        for (n2 = 0; n2 < list.size() - 2; ++n2) {
            location2 = list.get(n2);
            for (int i = n2 + 2; i < list.size(); ++i) {
                location = list.get(i);
                if (!location2.equals(location) && !GeoEngine.canMoveWithCollision(location2.x, location2.y, location2.z, location.x, location.y, location.z, n)) continue;
                while (n2 + 1 < i) {
                    list.remove(n2 + 1);
                    --i;
                }
            }
        }
    }

    private static boolean a(Location location, Location location2, Location location3) {
        if (location.x == location3.x && location3.x == location2.x || location.y == location3.y && location3.y == location2.y) {
            return true;
        }
        return (location.x - location2.x) * (location.y - location2.y) == (location2.x - location3.x) * (location2.y - location3.y);
    }

    public static List<Location> applyGeoIndent(List<Location> list, int n) {
        long l;
        long l2;
        if (n <= 0) {
            return list;
        }
        long l3 = list.get(list.size() - 1).getX() - list.get(0).getX();
        double d = Math.sqrt(l3 * l3 + (l2 = (long)(list.get(list.size() - 1).getY() - list.get(0).getY())) * l2 + (l = (long)(list.get(list.size() - 1).getZ() - list.get(0).getZ())) * l);
        if (d <= (double)n) {
            Location location = list.get(0);
            list.clear();
            list.add(location);
            return list;
        }
        if (d >= 1.0) {
            double d2 = (double)n / d;
            int n2 = (int)((double)list.size() * d2 + 0.5);
            for (int i = 1; i <= n2 && list.size() > 0; ++i) {
                list.remove(list.size() - 1);
            }
        }
        return list;
    }

    public static List<Location> straightLineGeoPath(Location location, Location location2) {
        int n = location2.getX() - location.getX();
        int n2 = location2.getY() - location.getY();
        int n3 = location2.getZ() - location.getZ();
        int n4 = Math.abs(n);
        int n5 = Math.abs(n2);
        int n6 = Math.abs(n3);
        float f = Math.max(Math.max(n4, n5), n6);
        if (f == 0.0f) {
            return Collections.emptyList();
        }
        float f2 = (float)n / f;
        float f3 = (float)n2 / f;
        float f4 = (float)n3 / f;
        float f5 = location.getX();
        float f6 = location.getY();
        float f7 = location.getZ();
        ArrayList<Location> arrayList = new ArrayList<Location>((int)f + 1);
        arrayList.add(new Location(location.getX(), location.getY(), location.getZ()));
        int n7 = 0;
        while ((float)n7 < f) {
            arrayList.add(new Location((int)((f5 += f2) + 0.5f), (int)((f6 += f3) + 0.5f), (int)((f7 += f4) + 0.5f)));
            ++n7;
        }
        return arrayList;
    }

    public static Location getIntersectPoint(Location location, Location location2, int n, int n2) {
        if (n2 == 0 || n == 0 || !PositionUtils.isFacing(location, location2, 90)) {
            return new Location(location2.getX(), location2.getY(), location2.getZ());
        }
        double d = PositionUtils.convertHeadingToDegree(location2.getHeading());
        double d2 = Math.toRadians(d - 90.0);
        double d3 = (double)n2 * ((double)n / 1000.0);
        return new Location((int)((double)location2.getX() - d3 * Math.sin(d2)), (int)((double)location2.getY() + d3 * Math.cos(d2)), location2.getZ());
    }

    public static List<Location> buildGeoLine(Location location, Location location2, int n, boolean bl, boolean bl2, int n2, int n3) {
        if (location.equals(location2)) {
            return Collections.emptyList();
        }
        Location location3 = location.clone().geo2world();
        Location location4 = location2.clone().geo2world();
        if (bl2) {
            Location location5 = n3 > 0 ? location4.clone().indent(location3, n3, true) : location4;
            List<Location> list = GeoMove.straightLineGeoPath(location, location5.clone().world2geo());
            if (!list.isEmpty()) {
                return list;
            }
            return null;
        }
        if (bl) {
            Location location6 = n3 > 0 ? location4.clone().indent(location3, n3, true) : location4;
            Location location7 = GeoEngine.moveCheckInAir(location3.getX(), location3.getY(), location3.getZ(), location6.getX(), location6.getY(), location6.getZ(), 16.0, n);
            if (location7 != null && !location7.equals(location3)) {
                List<Location> list = GeoMove.straightLineGeoPath(location, location7.world2geo());
                if (list.isEmpty()) {
                    return null;
                }
                return list;
            }
            return null;
        }
        Location location8 = n3 > 0 ? location4.clone().indent(location3, n3, false) : location4;
        List<Location> list = GeoEngine.MoveList(location3.getX(), location3.getY(), location3.getZ(), location8.getX(), location8.getY(), n, false);
        if (list != null) {
            if (list.isEmpty()) {
                return null;
            }
            return list;
        }
        return null;
    }

    public static boolean buildGeoPath(List<List<Location>> list, Location location, Location location2, int n, int n2, int n3, int n4, boolean bl, boolean bl2, boolean bl3, boolean bl4, int n5, boolean bl5) {
        List<List<Location>> list2;
        Location location3;
        list.clear();
        if (location.equals(location2)) {
            return true;
        }
        Location location4 = location.clone().geo2world();
        Location location5 = location2.clone().geo2world();
        Location location6 = n4 > 0 ? location5.clone().indent(location4, n4, !bl4 && !bl3) : (location3 = location5);
        if (bl5 || !Config.ALLOW_GEODATA) {
            List<Location> list3 = GeoMove.straightLineGeoPath(location, location3.world2geo());
            if (list3.isEmpty()) {
                return false;
            }
            list.add(list3);
            return true;
        }
        if (bl3) {
            if (GeoEngine.canSeeCoord(location4.getX(), location4.getY(), location4.getZ() + n3 + 64, location3.getX(), location3.getY(), location3.getZ(), true, n)) {
                List<Location> list4 = GeoMove.straightLineGeoPath(location, location3.world2geo());
                if (list4.isEmpty()) {
                    return false;
                }
                list.add(list4);
                return true;
            }
            Location location7 = GeoEngine.moveCheckInAir(location4.getX(), location4.getY(), location4.getZ(), location3.getX(), location3.getY(), location3.getZ(), n2, n);
            if (location7 != null && !location7.equals(location4)) {
                List<Location> list5 = GeoMove.straightLineGeoPath(location, location7.world2geo());
                if (list5.isEmpty()) {
                    return false;
                }
                list.add(list5);
                return true;
            }
            return false;
        }
        if (bl4) {
            Location location8 = GeoEngine.moveInWaterCheck(location4.getX(), location4.getY(), location4.getZ(), location3.getX(), location3.getY(), location3.getZ(), n5, n);
            List<Location> list6 = GeoMove.straightLineGeoPath(location, location8.clone().world2geo());
            if (!list6.isEmpty()) {
                list.add(list6);
            }
            int n6 = location3.getZ() - location8.getZ();
            if (!location8.clone().world2geo().equals(location3.clone().world2geo())) {
                if (bl) {
                    List<List<Location>> list7 = GeoMove.findMovePath(location8.getX(), location8.getY(), location8.getZ(), location5.getX(), location5.getY(), location5.getZ(), bl2, n);
                    if (!list7.isEmpty()) {
                        if (n4 > 0) {
                            List<Location> list8 = list7.remove(list7.size() - 1);
                            if (!(list8 = GeoMove.applyGeoIndent(list8, n4 >> 4)).isEmpty()) {
                                list7.add(list8);
                            }
                        }
                        if (!list7.isEmpty()) {
                            list.addAll(list7);
                        }
                    }
                } else {
                    List<Location> list9 = GeoEngine.MoveList(location8.getX(), location8.getY(), location8.getZ(), location5.getX(), location5.getY(), n, false);
                    if (list9 != null && !list9.isEmpty()) {
                        list.add(list9);
                    }
                }
            }
            return !list.isEmpty();
        }
        List<Location> list10 = GeoEngine.MoveList(location4.getX(), location4.getY(), location4.getZ(), location5.getX(), location5.getY(), n, true);
        if (list10 != null) {
            if (list10.isEmpty()) {
                return false;
            }
            if ((list10 = GeoMove.applyGeoIndent(list10, n4 >> 4)).isEmpty()) {
                return false;
            }
            list.add(list10);
            return true;
        }
        if (bl && !(list2 = GeoMove.findMovePath(location4.getX(), location4.getY(), location4.getZ(), location5.getX(), location5.getY(), location5.getZ(), bl2, n)).isEmpty()) {
            if (n4 > 0) {
                List<Location> list11 = list2.remove(list2.size() - 1);
                if (!(list11 = GeoMove.applyGeoIndent(list11, n4 >> 4)).isEmpty()) {
                    list2.add(list11);
                }
            }
            if (!list2.isEmpty()) {
                list.addAll(list2);
                return true;
            }
        }
        if ((list10 = GeoEngine.MoveList(location4.getX(), location4.getY(), location4.getZ(), location3.getX(), location3.getY(), n, false)) != null) {
            if (list10.size() < 2) {
                return false;
            }
            list.add(list10);
            return true;
        }
        return false;
    }
}
