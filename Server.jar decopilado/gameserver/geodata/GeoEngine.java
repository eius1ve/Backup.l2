/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.geodata;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import l2.commons.geometry.Shape;
import l2.commons.util.NaturalOrderComparator;
import l2.gameserver.Config;
import l2.gameserver.geodata.GeoCollision;
import l2.gameserver.geodata.GeoOptimizer;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.ArtefactInstance;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class GeoEngine {
    private static final Logger bc = LoggerFactory.getLogger(GeoEngine.class);
    public static final byte EAST = 1;
    public static final byte WEST = 2;
    public static final byte SOUTH = 4;
    public static final byte NORTH = 8;
    public static final byte NSWE_ALL = 15;
    public static final byte NSWE_NONE = 0;
    public static final byte BLOCKTYPE_FLAT = 0;
    public static final byte BLOCKTYPE_COMPLEX = 1;
    public static final byte BLOCKTYPE_MULTILEVEL = 2;
    public static final int BLOCKS_IN_MAP = 65536;
    public static int MAX_LAYERS = 1;
    private static final ByteBuffer[][] a = new ByteBuffer[World.WORLD_SIZE_X][World.WORLD_SIZE_Y];
    private static final byte[][][][][] a = new byte[World.WORLD_SIZE_X][World.WORLD_SIZE_Y][1][][];
    private static GeoFileLoader[][] a = new GeoFileLoader[World.WORLD_SIZE_X][World.WORLD_SIZE_Y];

    public static short getType(int n, int n2, int n3) {
        return GeoEngine.a(n - World.MAP_MIN_X >> 4, n2 - World.MAP_MIN_Y >> 4, n3);
    }

    public static int getHeight(Location location, int n) {
        return GeoEngine.getHeight(location.x, location.y, location.z, n);
    }

    public static int getHeight(int n, int n2, int n3, int n4) {
        return GeoEngine.NgetHeight(n - World.MAP_MIN_X >> 4, n2 - World.MAP_MIN_Y >> 4, n3, n4);
    }

    public static boolean canMoveToCoord(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        return GeoEngine.a(n, n2, n3, n4, n5, n6, false, n7) == 0;
    }

    public static byte getNSWE(int n, int n2, int n3, int n4) {
        return GeoEngine.NgetNSWE(n - World.MAP_MIN_X >> 4, n2 - World.MAP_MIN_Y >> 4, n3, n4);
    }

    public static Location moveCheck(int n, int n2, int n3, int n4, int n5, int n6) {
        return GeoEngine.a(n, n2, n3, n4, n5, false, false, false, n6);
    }

    public static Location moveCheck(int n, int n2, int n3, int n4, int n5, boolean bl, int n6) {
        return GeoEngine.a(n, n2, n3, n4, n5, false, false, bl, n6);
    }

    public static Location moveCheckWithCollision(int n, int n2, int n3, int n4, int n5, int n6) {
        return GeoEngine.a(n, n2, n3, n4, n5, true, false, false, n6);
    }

    public static Location moveCheckWithCollision(int n, int n2, int n3, int n4, int n5, boolean bl, int n6) {
        return GeoEngine.a(n, n2, n3, n4, n5, true, false, bl, n6);
    }

    public static Location moveCheckBackward(int n, int n2, int n3, int n4, int n5, int n6) {
        return GeoEngine.a(n, n2, n3, n4, n5, false, true, false, n6);
    }

    public static Location moveCheckBackward(int n, int n2, int n3, int n4, int n5, boolean bl, int n6) {
        return GeoEngine.a(n, n2, n3, n4, n5, false, true, bl, n6);
    }

    public static Location moveCheckBackwardWithCollision(int n, int n2, int n3, int n4, int n5, int n6) {
        return GeoEngine.a(n, n2, n3, n4, n5, true, true, false, n6);
    }

    public static Location moveCheckBackwardWithCollision(int n, int n2, int n3, int n4, int n5, boolean bl, int n6) {
        return GeoEngine.a(n, n2, n3, n4, n5, true, true, bl, n6);
    }

    public static Location moveInWaterCheck(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        return GeoEngine.a(n - World.MAP_MIN_X >> 4, n2 - World.MAP_MIN_Y >> 4, n3, n4 - World.MAP_MIN_X >> 4, n5 - World.MAP_MIN_Y >> 4, n6, n7, n8);
    }

    public static Location moveCheckForAI(Location location, Location location2, int n) {
        return GeoEngine.a(location.x - World.MAP_MIN_X >> 4, location.y - World.MAP_MIN_Y >> 4, location.z, location2.x - World.MAP_MIN_X >> 4, location2.y - World.MAP_MIN_Y >> 4, n);
    }

    public static Location moveCheckInAir(int n, int n2, int n3, int n4, int n5, int n6, double d, int n7) {
        Location location;
        int n8 = n - World.MAP_MIN_X >> 4;
        int n9 = n2 - World.MAP_MIN_Y >> 4;
        int n10 = n4 - World.MAP_MIN_X >> 4;
        int n11 = n5 - World.MAP_MIN_Y >> 4;
        int n12 = GeoEngine.NgetHeight(n10, n11, n6, n7);
        if (n6 <= n12 + 32) {
            n6 = n12 + 32;
        }
        if ((location = GeoEngine.canSee(n8, n9, n3, n10, n11, n6, true, n7)).equals(n8, n9, n3)) {
            return null;
        }
        return location.geo2world();
    }

    public static boolean canSeeTarget(GameObject gameObject, GameObject gameObject2, boolean bl) {
        if (gameObject2 == null) {
            return false;
        }
        if (gameObject2 instanceof GeoCollision || gameObject.equals(gameObject2)) {
            return true;
        }
        if (gameObject2 instanceof ArtefactInstance && gameObject.isInRange(gameObject2, 120L) && Math.abs(gameObject.getZ() - gameObject2.getZ()) < 64) {
            return true;
        }
        return GeoEngine.canSeeCoord(gameObject, gameObject2.getX(), gameObject2.getY(), gameObject2.getZ() + (int)gameObject2.getColHeight() + 64, bl);
    }

    public static boolean canSeeCoord(GameObject gameObject, int n, int n2, int n3, boolean bl) {
        return gameObject != null && GeoEngine.canSeeCoord(gameObject.getX(), gameObject.getY(), gameObject.getZ() + (int)gameObject.getColHeight() + 64, n, n2, n3, bl, gameObject.getGeoIndex());
    }

    public static boolean canSeeCoord(int n, int n2, int n3, int n4, int n5, int n6, boolean bl, int n7) {
        int n8 = n - World.MAP_MIN_X >> 4;
        int n9 = n2 - World.MAP_MIN_Y >> 4;
        int n10 = n4 - World.MAP_MIN_X >> 4;
        int n11 = n5 - World.MAP_MIN_Y >> 4;
        return GeoEngine.canSee(n8, n9, n3, n10, n11, n6, bl, n7).equals(n10, n11, n6) && GeoEngine.canSee(n10, n11, n6, n8, n9, n3, bl, n7).equals(n8, n9, n3);
    }

    public static boolean canMoveWithCollision(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        return GeoEngine.a(n, n2, n3, n4, n5, n6, true, n7) == 0;
    }

    public static boolean checkNSWE(byte by, int n, int n2, int n3, int n4) {
        if (by == 15) {
            return true;
        }
        if (by == 0) {
            return false;
        }
        if (n3 > n ? (by & 1) == 0 : n3 < n && (by & 2) == 0) {
            return false;
        }
        return !(n4 > n2 ? (by & 4) == 0 : n4 < n2 && (by & 8) == 0);
    }

    public static String geoXYZ2Str(int n, int n2, int n3) {
        return "(" + String.valueOf((n << 4) + World.MAP_MIN_X + 8) + " " + String.valueOf((n2 << 4) + World.MAP_MIN_Y + 8) + " " + n3 + ")";
    }

    public static String NSWE2Str(byte by) {
        Object object = "";
        if ((by & 8) == 8) {
            object = (String)object + "N";
        }
        if ((by & 4) == 4) {
            object = (String)object + "S";
        }
        if ((by & 2) == 2) {
            object = (String)object + "W";
        }
        if ((by & 1) == 1) {
            object = (String)object + "E";
        }
        return ((String)object).isEmpty() ? "X" : object;
    }

    private static boolean a(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8;
        short s;
        int n9;
        short[] sArray = new short[MAX_LAYERS + 1];
        short[] sArray2 = new short[MAX_LAYERS + 1];
        GeoEngine.NGetLayers(n, n2, sArray, n7);
        GeoEngine.NGetLayers(n4, n5, sArray2, n7);
        if (sArray[0] == 0 || sArray2[0] == 0) {
            return true;
        }
        int n10 = Short.MIN_VALUE;
        for (n9 = 1; n9 <= sArray2[0]; ++n9) {
            s = (short)((short)(sArray2[n9] & 0xFFF0) >> 1);
            if (Math.abs(n6 - n10) <= Math.abs(n6 - s)) continue;
            n10 = s;
        }
        if (n6 + 32 >= n10) {
            return true;
        }
        n9 = Short.MIN_VALUE;
        for (n8 = 1; n8 <= sArray2[0]; ++n8) {
            s = (short)((short)(sArray2[n8] & 0xFFF0) >> 1);
            if (s >= n10 + Config.MIN_LAYER_HEIGHT || Math.abs(n6 - n9) <= Math.abs(n6 - s)) continue;
            n9 = s;
        }
        if (n9 == Short.MIN_VALUE) {
            return false;
        }
        n8 = Short.MIN_VALUE;
        byte by = 15;
        for (int i = 1; i <= sArray[0]; ++i) {
            s = (short)((short)(sArray[i] & 0xFFF0) >> 1);
            if (s >= n3 + Config.MIN_LAYER_HEIGHT || Math.abs(n3 - n8) <= Math.abs(n3 - s)) continue;
            n8 = s;
            by = (byte)(sArray[i] & 0xF);
        }
        return GeoEngine.checkNSWE(by, n, n2, n4, n5);
    }

    private static int a(short[] sArray, int n) {
        short s = Short.MIN_VALUE;
        int n2 = Integer.MIN_VALUE;
        for (int i = 1; i <= sArray[0]; ++i) {
            short s2 = (short)((short)(sArray[i] & 0xFFF0) >> 1);
            if (s2 >= n || s >= s2) continue;
            s = s2;
            n2 = sArray[i];
        }
        return n2;
    }

    private static short a(short[] sArray, int n, int n2) {
        int n3;
        int n4;
        if (n > n2) {
            n4 = n2;
            n3 = n;
        } else {
            n4 = n;
            n3 = n2;
        }
        short s = Short.MIN_VALUE;
        short s2 = Short.MIN_VALUE;
        for (int i = 1; i <= sArray[0]; ++i) {
            short s3 = (short)((short)(sArray[i] & 0xFFF0) >> 1);
            if (n4 <= s3 && s3 <= n3) {
                return Short.MIN_VALUE;
            }
            if (s3 >= n || s2 >= s3) continue;
            s2 = s3;
            s = sArray[i];
        }
        return s;
    }

    public static boolean canSeeWallCheck(short s, short s2, byte by, int n, boolean bl) {
        short s3 = (short)((short)(s2 & 0xFFF0) >> 1);
        if (bl) {
            return s3 < n;
        }
        short s4 = (short)((short)(s & 0xFFF0) >> 1);
        int n2 = s3 - s4;
        return (s & 0xF & by) != 0 || n2 > -Config.MAX_Z_DIFF && n2 != 0;
    }

    public static Location canSee(int n, int n2, int n3, int n4, int n5, int n6, boolean bl, int n7) {
        int n8 = n4 - n;
        int n9 = n5 - n2;
        int n10 = n6 - n3;
        int n11 = Math.abs(n8);
        int n12 = Math.abs(n9);
        float f = Math.max(n11, n12);
        int n13 = n;
        int n14 = n2;
        int n15 = n3;
        short[] sArray = new short[MAX_LAYERS + 1];
        GeoEngine.NGetLayers(n13, n14, sArray, n7);
        Location location = new Location(n, n2, n3, -1);
        if (f == 0.0f) {
            if (GeoEngine.a(sArray, n15, n15 + n10) != Short.MIN_VALUE) {
                location.set(n4, n5, n6, 1);
            }
            return location;
        }
        float f2 = (float)n8 / f;
        float f3 = (float)n9 / f;
        float f4 = (float)n10 / f;
        float f5 = f4 / 2.0f;
        float f6 = n13;
        float f7 = n14;
        float f8 = n15;
        short[] sArray2 = new short[MAX_LAYERS + 1];
        int n16 = 0;
        while ((float)n16 < f) {
            if (sArray[0] == 0) {
                location.set(n4, n5, n6, 0);
                return location;
            }
            int n17 = (int)((f6 += f2) + 0.5f);
            int n18 = (int)((f7 += f3) + 0.5f);
            int n19 = (int)((f8 += f4) + 0.5f);
            int n20 = (int)((float)n15 + f5);
            short s = GeoEngine.a(sArray, n15, n20);
            if (s == Short.MIN_VALUE) {
                return location.setH(-10);
            }
            GeoEngine.NGetLayers(n13, n14, sArray, n7);
            if (sArray[0] == 0) {
                location.set(n4, n5, n6, 0);
                return location;
            }
            short s2 = GeoEngine.a(sArray, n19, n20);
            if (s2 == Short.MIN_VALUE) {
                return location.setH(-11);
            }
            if (n13 == n17) {
                if (!GeoEngine.canSeeWallCheck(s, s2, n18 > n14 ? (byte)4 : 8, n15, bl)) {
                    return location.setH(-20);
                }
            } else if (n14 == n18) {
                if (!GeoEngine.canSeeWallCheck(s, s2, n17 > n13 ? (byte)1 : 2, n15, bl)) {
                    return location.setH(-21);
                }
            } else {
                GeoEngine.NGetLayers(n13, n18, sArray2, n7);
                if (sArray2[0] == 0) {
                    location.set(n4, n5, n6, 0);
                    return location;
                }
                short s3 = GeoEngine.a(sArray2, n19, n20);
                if (s3 == Short.MIN_VALUE) {
                    return location.setH(-30);
                }
                if (!GeoEngine.canSeeWallCheck(s, s3, n18 > n14 ? (byte)4 : 8, n15, bl) || !GeoEngine.canSeeWallCheck(s3, s2, n17 > n13 ? (byte)1 : 2, n15, bl)) {
                    GeoEngine.NGetLayers(n17, n14, sArray2, n7);
                    if (sArray2[0] == 0) {
                        location.set(n4, n5, n6, 0);
                        return location;
                    }
                    s3 = GeoEngine.a(sArray2, n19, n20);
                    if (s3 == Short.MIN_VALUE) {
                        return location.setH(-31);
                    }
                    if (!GeoEngine.canSeeWallCheck(s, s3, n17 > n13 ? (byte)1 : 2, n15, bl)) {
                        return location.setH(-32);
                    }
                    if (!GeoEngine.canSeeWallCheck(s3, s2, n17 > n13 ? (byte)1 : 2, n15, bl)) {
                        return location.setH(-33);
                    }
                }
            }
            location.set(n13, n14, n15);
            n13 = n17;
            n14 = n18;
            n15 = n19;
            ++n16;
        }
        location.set(n4, n5, n6, 255);
        return location;
    }

    private static Location a(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        int n9 = n4 - n;
        int n10 = n5 - n2;
        int n11 = n6 - n3;
        byte by = GeoEngine.a(n9);
        byte by2 = GeoEngine.a(n10);
        if ((n9 = Math.abs(n9)) + (n10 = Math.abs(n10)) == 0) {
            return new Location(n, n2, n3).geo2world();
        }
        float f = n9 == 0 ? 0.0f : (float)(n11 / n9);
        float f2 = n10 == 0 ? 0.0f : (float)(n11 / n10);
        float f3 = n;
        float f4 = n2;
        float f5 = n3;
        if (n9 >= n10) {
            int n12 = 2 * n10;
            int n13 = n12 - n9;
            int n14 = n12 - 2 * n9;
            for (int i = 0; i < n9; ++i) {
                int n15 = n;
                int n16 = n2;
                int n17 = n3;
                n = (int)f3;
                n2 = (int)f4;
                n3 = (int)f5;
                if (n13 > 0) {
                    n13 += n14;
                    f3 += (float)by;
                    f5 += f;
                    f4 += (float)by2;
                    f5 += f2;
                } else {
                    n13 += n12;
                    f3 += (float)by;
                    f5 += f;
                }
                if (!(f5 >= (float)n7) && GeoEngine.a(n, n2, n3, (int)f3, (int)f4, (int)f5, n8)) continue;
                return new Location(n15, n16, n17).geo2world();
            }
        } else {
            int n18 = 2 * n9;
            int n19 = n18 - n10;
            int n20 = n18 - 2 * n10;
            for (int i = 0; i < n10; ++i) {
                int n21 = n;
                int n22 = n2;
                int n23 = n3;
                n = (int)f3;
                n2 = (int)f4;
                n3 = (int)f5;
                if (n19 > 0) {
                    n19 += n20;
                    f3 += (float)by;
                    f5 += f;
                    f4 += (float)by2;
                    f5 += f2;
                } else {
                    n19 += n18;
                    f4 += (float)by2;
                    f5 += f2;
                }
                if (!(f5 >= (float)n7) && GeoEngine.a(n, n2, n3, (int)f3, (int)f4, (int)f5, n8)) continue;
                return new Location(n21, n22, n23).geo2world();
            }
        }
        return new Location((int)f3, (int)f4, (int)f5).geo2world();
    }

    private static int a(int n, int n2, int n3, int n4, int n5, int n6, boolean bl, int n7) {
        int n8 = n - World.MAP_MIN_X >> 4;
        int n9 = n2 - World.MAP_MIN_Y >> 4;
        int n10 = n4 - World.MAP_MIN_X >> 4;
        int n11 = n5 - World.MAP_MIN_Y >> 4;
        int n12 = n10 - n8;
        int n13 = n11 - n9;
        int n14 = n6 - n3;
        int n15 = Math.abs(n12);
        int n16 = Math.abs(n13);
        int n17 = Math.abs(n14);
        float f = Math.max(n15, n16);
        if (f == 0.0f) {
            return -5;
        }
        int n18 = n8;
        int n19 = n9;
        int n20 = n3;
        short[] sArray = new short[MAX_LAYERS + 1];
        GeoEngine.NGetLayers(n18, n19, sArray, n7);
        if (sArray[0] == 0) {
            return 0;
        }
        float f2 = (float)n12 / f;
        float f3 = (float)n13 / f;
        float f4 = n18;
        float f5 = n19;
        short[] sArray2 = new short[MAX_LAYERS + 1];
        short[] sArray3 = new short[MAX_LAYERS + 1];
        int n21 = 0;
        while ((float)n21 < f) {
            int n22 = (int)((f4 += f2) + 0.5f);
            int n23 = (int)((f5 += f3) + 0.5f);
            GeoEngine.NGetLayers(n22, n23, sArray2, n7);
            n20 = GeoEngine.NcanMoveNext(n18, n19, n20, sArray, n22, n23, sArray2, sArray3, bl, n7);
            if (n20 == Integer.MIN_VALUE) {
                return 1;
            }
            short[] sArray4 = sArray;
            sArray = sArray2;
            sArray2 = sArray4;
            n18 = n22;
            n19 = n23;
            ++n21;
        }
        n14 = n20 - n6;
        n17 = Math.abs(n14);
        if (Config.ALLOW_FALL_FROM_WALLS) {
            return n14 < Config.MAX_Z_DIFF ? 0 : n14 * 10000;
        }
        return n17 > Config.MAX_Z_DIFF ? n17 * 1000 : 0;
    }

    private static Location a(int n, int n2, int n3, int n4, int n5, boolean bl, boolean bl2, boolean bl3, int n6) {
        int n7;
        int n8 = n - World.MAP_MIN_X >> 4;
        int n9 = n2 - World.MAP_MIN_Y >> 4;
        int n10 = n4 - World.MAP_MIN_X >> 4;
        int n11 = n5 - World.MAP_MIN_Y >> 4;
        int n12 = n10 - n8;
        int n13 = n11 - n9;
        int n14 = Math.abs(n12);
        float f = Math.max(n14, n7 = Math.abs(n13));
        if (f == 0.0f) {
            return new Location(n, n2, n3);
        }
        float f2 = (float)n12 / f;
        float f3 = (float)n13 / f;
        int n15 = n8;
        int n16 = n9;
        int n17 = n3;
        float f4 = n15;
        float f5 = n16;
        int n18 = n17;
        short[] sArray = new short[MAX_LAYERS + 1];
        short[] sArray2 = new short[MAX_LAYERS + 1];
        short[] sArray3 = new short[MAX_LAYERS + 1];
        GeoEngine.NGetLayers(n15, n16, sArray3, n6);
        int n19 = n15;
        int n20 = n16;
        int n21 = n17;
        int n22 = 0;
        while ((float)n22 < f) {
            int n23 = (int)((f4 += f2) + 0.5f);
            int n24 = (int)((f5 += f3) + 0.5f);
            GeoEngine.NGetLayers(n23, n24, sArray, n6);
            n18 = GeoEngine.NcanMoveNext(n15, n16, n17, sArray3, n23, n24, sArray, sArray2, bl, n6);
            if (n18 == Integer.MIN_VALUE || bl2 && GeoEngine.NcanMoveNext(n23, n24, n18, sArray, n15, n16, sArray3, sArray2, bl, n6) == Integer.MIN_VALUE) break;
            short[] sArray4 = sArray3;
            sArray3 = sArray;
            sArray = sArray4;
            if (bl3) {
                n19 = n15;
                n20 = n16;
                n21 = n17;
            }
            n15 = n23;
            n16 = n24;
            n17 = n18;
            ++n22;
        }
        if (bl3) {
            n15 = n19;
            n16 = n20;
            n17 = n21;
        }
        return new Location(n15, n16, n17).geo2world();
    }

    public static List<Location> MoveList(int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
        int n7;
        int n8;
        byte by;
        byte by2;
        int n9 = n - World.MAP_MIN_X >> 4;
        int n10 = n2 - World.MAP_MIN_Y >> 4;
        int n11 = n4 - World.MAP_MIN_X >> 4;
        int n12 = n5 - World.MAP_MIN_Y >> 4;
        int n13 = n11 - n9;
        int n14 = n12 - n10;
        byte by3 = GeoEngine.a(n13);
        byte by4 = GeoEngine.a(n14);
        if (n13 < 0) {
            n13 = -n13;
        }
        if (n14 < 0) {
            n14 = -n14;
        }
        if (n13 > n14) {
            by2 = by3;
            by = 0;
            n8 = n14;
            n7 = n13;
        } else {
            by2 = 0;
            by = by4;
            n8 = n13;
            n7 = n14;
        }
        int n15 = n7 / 2;
        int n16 = n9;
        int n17 = n10;
        int n18 = n3;
        int n19 = n16;
        int n20 = n17;
        int n21 = n18;
        short[] sArray = new short[MAX_LAYERS + 1];
        short[] sArray2 = new short[MAX_LAYERS + 1];
        short[] sArray3 = new short[MAX_LAYERS + 1];
        GeoEngine.NGetLayers(n16, n17, sArray3, n6);
        if (sArray3[0] == 0) {
            return null;
        }
        ArrayList<Location> arrayList = new ArrayList<Location>(Math.min(1024, n7 + 1));
        arrayList.add(new Location(n16, n17, n18));
        for (int i = 0; i < n7; ++i) {
            if ((n15 -= n8) < 0) {
                n15 += n7;
                n19 += by3;
                n20 += by4;
            } else {
                n19 += by2;
                n20 += by;
            }
            GeoEngine.NGetLayers(n19, n20, sArray, n6);
            n21 = GeoEngine.NcanMoveNext(n16, n17, n18, sArray3, n19, n20, sArray, sArray2, false, n6);
            if (n21 == Integer.MIN_VALUE) {
                if (!bl) break;
                return null;
            }
            short[] sArray4 = sArray3;
            sArray3 = sArray;
            sArray = sArray4;
            n16 = n19;
            n17 = n20;
            n18 = n21;
            arrayList.add(new Location(n16, n17, n18));
        }
        return arrayList;
    }

    private static Location a(int n, int n2, int n3, int n4, int n5, int n6) {
        int n7 = n4 - n;
        int n8 = n5 - n2;
        byte by = GeoEngine.a(n7);
        byte by2 = GeoEngine.a(n8);
        if ((n7 = Math.abs(n7)) + (n8 = Math.abs(n8)) < 2 || n7 == 2 && n8 == 0 || n7 == 0 && n8 == 2) {
            return new Location(n, n2, n3).geo2world();
        }
        int n9 = n;
        int n10 = n2;
        int n11 = n3;
        int n12 = n;
        int n13 = n2;
        int n14 = n3;
        if (n7 >= n8) {
            int n15 = 2 * n8;
            int n16 = n15 - n7;
            int n17 = n15 - 2 * n7;
            for (int i = 0; i < n7; ++i) {
                n9 = n;
                n10 = n2;
                n11 = n3;
                n = n12;
                n2 = n13;
                n3 = n14;
                if (n16 > 0) {
                    n16 += n17;
                    n12 += by;
                    n13 += by2;
                } else {
                    n16 += n15;
                    n12 += by;
                }
                n14 = GeoEngine.NcanMoveNextForAI(n, n2, n3, n12, n13, n6);
                if (n14 != 0) continue;
                return new Location(n9, n10, n11).geo2world();
            }
        } else {
            int n18 = 2 * n7;
            int n19 = n18 - n8;
            int n20 = n18 - 2 * n8;
            for (int i = 0; i < n8; ++i) {
                n9 = n;
                n10 = n2;
                n11 = n3;
                n = n12;
                n2 = n13;
                n3 = n14;
                if (n19 > 0) {
                    n19 += n20;
                    n12 += by;
                    n13 += by2;
                } else {
                    n19 += n18;
                    n13 += by2;
                }
                n14 = GeoEngine.NcanMoveNextForAI(n, n2, n3, n12, n13, n6);
                if (n14 != 0) continue;
                return new Location(n9, n10, n11).geo2world();
            }
        }
        return new Location(n12, n13, n14).geo2world();
    }

    private static boolean a(int n, int n2, int n3, int n4, int n5, int n6, short[] sArray, int n7) {
        GeoEngine.NGetLayers(n, n2, sArray, n7);
        if (sArray[0] == 0) {
            return true;
        }
        int n8 = GeoEngine.a(sArray, n3 + Config.MIN_LAYER_HEIGHT);
        if (n8 == Integer.MIN_VALUE) {
            return false;
        }
        short s = (short)((short)(n8 & 0xFFF0) >> 1);
        if (Math.abs(s - n6) >= Config.MAX_Z_DIFF || Math.abs(s - n3) >= Config.MAX_Z_DIFF) {
            return false;
        }
        return GeoEngine.checkNSWE((byte)(n8 & 0xF), n, n2, n4, n5);
    }

    public static int NcanMoveNext(int n, int n2, int n3, short[] sArray, int n4, int n5, short[] sArray2, short[] sArray3, boolean bl, int n6) {
        if (sArray[0] == 0 || sArray2[0] == 0) {
            return n3;
        }
        int n7 = GeoEngine.a(sArray, n3 + Config.MIN_LAYER_HEIGHT);
        if (n7 == Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        byte by = (byte)(n7 & 0xF);
        if (!GeoEngine.checkNSWE(by, n, n2, n4, n5)) {
            return Integer.MIN_VALUE;
        }
        short s = (short)((short)(n7 & 0xFFF0) >> 1);
        int n8 = GeoEngine.a(sArray2, s + Config.MIN_LAYER_HEIGHT);
        if (n8 == Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        short s2 = (short)((short)(n8 & 0xFFF0) >> 1);
        if (n == n4 || n2 == n5) {
            if (bl) {
                if (n == n4) {
                    GeoEngine.NgetHeightAndNSWE(n - 1, n2, s, sArray3, n6);
                    if (Math.abs(sArray3[0] - s) > 15 || !GeoEngine.checkNSWE(by, n - 1, n2, n, n2) || !GeoEngine.checkNSWE((byte)sArray3[1], n - 1, n2, n - 1, n5)) {
                        return Integer.MIN_VALUE;
                    }
                    GeoEngine.NgetHeightAndNSWE(n + 1, n2, s, sArray3, n6);
                    if (Math.abs(sArray3[0] - s) > 15 || !GeoEngine.checkNSWE(by, n + 1, n2, n, n2) || !GeoEngine.checkNSWE((byte)sArray3[1], n + 1, n2, n + 1, n5)) {
                        return Integer.MIN_VALUE;
                    }
                    return s2;
                }
                GeoEngine.NgetHeightAndNSWE(n, n2 - 1, s, sArray3, n6);
                if (Math.abs(sArray3[0] - s) >= Config.MAX_Z_DIFF || !GeoEngine.checkNSWE(by, n, n2 - 1, n, n2) || !GeoEngine.checkNSWE((byte)sArray3[1], n, n2 - 1, n4, n2 - 1)) {
                    return Integer.MIN_VALUE;
                }
                GeoEngine.NgetHeightAndNSWE(n, n2 + 1, s, sArray3, n6);
                if (Math.abs(sArray3[0] - s) >= Config.MAX_Z_DIFF || !GeoEngine.checkNSWE(by, n, n2 + 1, n, n2) || !GeoEngine.checkNSWE((byte)sArray3[1], n, n2 + 1, n4, n2 + 1)) {
                    return Integer.MIN_VALUE;
                }
            }
            return s2;
        }
        if (!GeoEngine.a(n, n5, (int)s, n4, n5, (int)s2, sArray3, n6)) {
            return Integer.MIN_VALUE;
        }
        if (!GeoEngine.a(n4, n2, (int)s, n4, n5, (int)s2, sArray3, n6)) {
            return Integer.MIN_VALUE;
        }
        return s2;
    }

    public static int NcanMoveNextForAI(int n, int n2, int n3, int n4, int n5, int n6) {
        short s;
        int n7;
        short[] sArray = new short[MAX_LAYERS + 1];
        short[] sArray2 = new short[MAX_LAYERS + 1];
        GeoEngine.NGetLayers(n, n2, sArray, n6);
        GeoEngine.NGetLayers(n4, n5, sArray2, n6);
        if (sArray[0] == 0 || sArray2[0] == 0) {
            return n3 == 0 ? 1 : n3;
        }
        int n8 = Short.MIN_VALUE;
        byte by = 15;
        for (n7 = 1; n7 <= sArray[0]; ++n7) {
            s = (short)((short)(sArray[n7] & 0xFFF0) >> 1);
            if (Math.abs(n3 - n8) <= Math.abs(n3 - s)) continue;
            n8 = s;
            by = (byte)(sArray[n7] & 0xF);
        }
        if (n8 == Short.MIN_VALUE) {
            return 0;
        }
        n7 = Short.MIN_VALUE;
        byte by2 = 15;
        for (int i = 1; i <= sArray2[0]; ++i) {
            s = (short)((short)(sArray2[i] & 0xFFF0) >> 1);
            if (Math.abs(n3 - n7) <= Math.abs(n3 - s)) continue;
            n7 = s;
            by2 = (byte)(sArray2[i] & 0xF);
        }
        if (n7 == Short.MIN_VALUE) {
            return 0;
        }
        if (n8 > n7 && n8 - n7 > Config.MAX_Z_DIFF) {
            return 0;
        }
        if (!GeoEngine.checkNSWE(by, n, n2, n4, n5) || !GeoEngine.checkNSWE(by2, n4, n5, n, n2)) {
            return 0;
        }
        return n7 == 0 ? 1 : n7;
    }

    public static void NGetLayers(int n, int n2, short[] sArray, int n3) {
        sArray[0] = 0;
        byte[] byArray = GeoEngine.a(n, n2, n3);
        if (byArray == null) {
            return;
        }
        int n4 = 0;
        byte by = byArray[n4];
        ++n4;
        switch (by) {
            case 0: {
                short s = GeoEngine.makeShort(byArray[n4 + 1], byArray[n4]);
                s = (short)(s & 0xFFF0);
                sArray[0] = (short)(sArray[0] + 1);
                sArray[1] = (short)((short)(s << 1) | 0xF);
                return;
            }
            case 1: {
                int n5 = GeoEngine.getCell(n);
                int n6 = GeoEngine.getCell(n2);
                short s = GeoEngine.makeShort(byArray[(n4 += (n5 << 3) + n6 << 1) + 1], byArray[n4]);
                sArray[0] = (short)(sArray[0] + 1);
                sArray[1] = s;
                return;
            }
            case 2: {
                byte by2;
                int n7 = GeoEngine.getCell(n);
                int n8 = GeoEngine.getCell(n2);
                for (int i = (n7 << 3) + n8; i > 0; --i) {
                    by2 = byArray[n4];
                    n4 += (by2 << 1) + 1;
                }
                by2 = byArray[n4];
                ++n4;
                if (by2 <= 0 || by2 > MAX_LAYERS) {
                    return;
                }
                sArray[0] = by2;
                while (by2 > 0) {
                    sArray[by2] = GeoEngine.makeShort(byArray[n4 + 1], byArray[n4]);
                    by2 = (byte)(by2 - 1);
                    n4 += 2;
                }
                return;
            }
        }
        bc.error("GeoEngine: Unknown block type");
    }

    private static short a(int n, int n2, int n3) {
        byte[] byArray = GeoEngine.a(n, n2, n3);
        if (byArray == null) {
            return 0;
        }
        return byArray[0];
    }

    public static int NgetHeight(int n, int n2, int n3, int n4) {
        byte[] byArray = GeoEngine.a(n, n2, n4);
        if (byArray == null) {
            return n3;
        }
        int n5 = 0;
        byte by = byArray[n5];
        ++n5;
        switch (by) {
            case 0: {
                short s = GeoEngine.makeShort(byArray[n5 + 1], byArray[n5]);
                return (short)(s & 0xFFF0);
            }
            case 1: {
                int n6 = GeoEngine.getCell(n);
                int n7 = GeoEngine.getCell(n2);
                short s = GeoEngine.makeShort(byArray[(n5 += (n6 << 3) + n7 << 1) + 1], byArray[n5]);
                return (short)((short)(s & 0xFFF0) >> 1);
            }
            case 2: {
                byte by2;
                int n8 = GeoEngine.getCell(n);
                int n9 = GeoEngine.getCell(n2);
                for (int i = (n8 << 3) + n9; i > 0; --i) {
                    by2 = byArray[n5];
                    n5 += (by2 << 1) + 1;
                }
                by2 = byArray[n5];
                ++n5;
                if (by2 <= 0 || by2 > MAX_LAYERS) {
                    return (short)n3;
                }
                int n10 = n3 + Config.MIN_LAYER_HEIGHT;
                int n11 = Integer.MIN_VALUE;
                int n12 = Integer.MIN_VALUE;
                while (by2 > 0) {
                    short s = (short)((short)(GeoEngine.makeShort(byArray[n5 + 1], byArray[n5]) & 0xFFF0) >> 1);
                    if (s < n10) {
                        n11 = Math.max(n11, s);
                    } else if (Math.abs(n3 - s) < Math.abs(n3 - n12)) {
                        n12 = s;
                    }
                    by2 = (byte)(by2 - 1);
                    n5 += 2;
                }
                return n11 != Integer.MIN_VALUE ? n11 : n12;
            }
        }
        bc.error("GeoEngine: Unknown blockType");
        return n3;
    }

    public static byte NgetNSWE(int n, int n2, int n3, int n4) {
        byte[] byArray = GeoEngine.a(n, n2, n4);
        if (byArray == null) {
            return 15;
        }
        int n5 = 0;
        byte by = byArray[n5];
        ++n5;
        switch (by) {
            case 0: {
                return 15;
            }
            case 1: {
                int n6 = GeoEngine.getCell(n);
                int n7 = GeoEngine.getCell(n2);
                short s = GeoEngine.makeShort(byArray[(n5 += (n6 << 3) + n7 << 1) + 1], byArray[n5]);
                return (byte)(s & 0xF);
            }
            case 2: {
                byte by2;
                int n8 = GeoEngine.getCell(n);
                int n9 = GeoEngine.getCell(n2);
                for (int i = (n8 << 3) + n9; i > 0; --i) {
                    by2 = byArray[n5];
                    n5 += (by2 << 1) + 1;
                }
                by2 = byArray[n5];
                ++n5;
                if (by2 <= 0 || by2 > MAX_LAYERS) {
                    return 15;
                }
                short s = Short.MIN_VALUE;
                int n10 = Short.MIN_VALUE;
                int n11 = 0;
                int n12 = 0;
                int n13 = n3 + Config.MIN_LAYER_HEIGHT;
                while (by2 > 0) {
                    short s2 = (short)((short)(GeoEngine.makeShort(byArray[n5 + 1], byArray[n5]) & 0xFFF0) >> 1);
                    if (s2 < n13) {
                        if (s2 > s) {
                            s = s2;
                            n11 = n5;
                        }
                    } else if (Math.abs(n3 - s2) < Math.abs(n3 - n10)) {
                        n10 = s2;
                        n12 = n5;
                    }
                    by2 = (byte)(by2 - 1);
                    n5 += 2;
                }
                if (n11 > 0) {
                    return (byte)(GeoEngine.makeShort(byArray[n11 + 1], byArray[n11]) & 0xF);
                }
                if (n12 > 0) {
                    return (byte)(GeoEngine.makeShort(byArray[n12 + 1], byArray[n12]) & 0xF);
                }
                return 15;
            }
        }
        bc.error("GeoEngine: Unknown block type.");
        return 15;
    }

    public static void NgetHeightAndNSWE(int n, int n2, short s, short[] sArray, int n3) {
        byte[] byArray = GeoEngine.a(n, n2, n3);
        if (byArray == null) {
            sArray[0] = s;
            sArray[1] = 15;
            return;
        }
        int n4 = 0;
        int n5 = 15;
        byte by = byArray[n4];
        ++n4;
        switch (by) {
            case 0: {
                short s2 = GeoEngine.makeShort(byArray[n4 + 1], byArray[n4]);
                sArray[0] = (short)(s2 & 0xFFF0);
                sArray[1] = 15;
                return;
            }
            case 1: {
                int n6 = GeoEngine.getCell(n);
                int n7 = GeoEngine.getCell(n2);
                short s3 = GeoEngine.makeShort(byArray[(n4 += (n6 << 3) + n7 << 1) + 1], byArray[n4]);
                sArray[0] = (short)((short)(s3 & 0xFFF0) >> 1);
                sArray[1] = (short)(s3 & 0xF);
                return;
            }
            case 2: {
                byte by2;
                int n8 = GeoEngine.getCell(n);
                int n9 = GeoEngine.getCell(n2);
                for (int i = (n8 << 3) + n9; i > 0; --i) {
                    by2 = byArray[n4];
                    n4 += (by2 << 1) + 1;
                }
                by2 = byArray[n4];
                ++n4;
                if (by2 <= 0 || by2 > MAX_LAYERS) {
                    sArray[0] = s;
                    sArray[1] = 15;
                    return;
                }
                short s4 = Short.MIN_VALUE;
                short s5 = Short.MIN_VALUE;
                int n10 = 0;
                int n11 = 0;
                int n12 = s + Config.MIN_LAYER_HEIGHT;
                while (by2 > 0) {
                    short s6 = (short)((short)(GeoEngine.makeShort(byArray[n4 + 1], byArray[n4]) & 0xFFF0) >> 1);
                    if (s6 < n12) {
                        if (s6 > s4) {
                            s4 = s6;
                            n10 = n4;
                        }
                    } else if (Math.abs(s - s6) < Math.abs(s - s5)) {
                        s5 = s6;
                        n11 = n4;
                    }
                    by2 = (byte)(by2 - 1);
                    n4 += 2;
                }
                if (n10 > 0) {
                    n5 = GeoEngine.makeShort(byArray[n10 + 1], byArray[n10]);
                    n5 = (short)(n5 & 0xF);
                } else if (n11 > 0) {
                    n5 = GeoEngine.makeShort(byArray[n11 + 1], byArray[n11]);
                    n5 = (short)(n5 & 0xF);
                }
                sArray[0] = s4 > Short.MIN_VALUE ? s4 : s5;
                sArray[1] = n5;
                return;
            }
        }
        bc.error("GeoEngine: Unknown block type.");
        sArray[0] = s;
        sArray[1] = 15;
    }

    protected static short makeShort(byte by, byte by2) {
        return (short)(by << 8 | by2 & 0xFF);
    }

    protected static int getBlock(int n) {
        return (n >> 3) % 256;
    }

    protected static int getCell(int n) {
        return n % 8;
    }

    protected static int getBlockIndex(int n, int n2) {
        return (n << 8) + n2;
    }

    private static byte a(int n) {
        if (n >= 0) {
            return 1;
        }
        return -1;
    }

    private static byte[] a(int n, int n2, int n3) {
        if (!Config.ALLOW_GEODATA) {
            return null;
        }
        int n4 = n >> 11;
        int n5 = n2 >> 11;
        if (n4 < 0 || n4 >= World.WORLD_SIZE_X || n5 < 0 || n5 >= World.WORLD_SIZE_Y) {
            return null;
        }
        byte[][][] byArray = a[n4][n5];
        int n6 = GeoEngine.getBlock(n);
        int n7 = GeoEngine.getBlock(n2);
        int n8 = 0;
        if ((n3 & 0xF000000) == 0xF000000) {
            int n9 = (n3 & 0xFF0000) >> 16;
            int n10 = (n3 & 0xFF00) >> 8;
            if (n4 == n9 && n5 == n10) {
                n8 = n3 & 0xFF;
            }
        }
        return byArray[n8][GeoEngine.getBlockIndex(n6, n7)];
    }

    public static int getGeoX(int n) {
        return n - World.MAP_MIN_X >> 4;
    }

    public static int getGeoY(int n) {
        return n - World.MAP_MIN_Y >> 4;
    }

    public static int getWorldX(int n) {
        return (n << 4) + World.MAP_MIN_X + 8;
    }

    public static int getWorldY(int n) {
        return (n << 4) + World.MAP_MIN_Y + 8;
    }

    public static void load() {
        if (!Config.ALLOW_GEODATA) {
            return;
        }
        bc.info("GeoEngine: Loading Geodata...");
        File file = new File(Config.DATAPACK_ROOT, "geodata");
        if (!file.exists() || !file.isDirectory()) {
            bc.info("GeoEngine: Files missing, loading aborted.");
            return;
        }
        int n = 0;
        Pattern pattern = Pattern.compile(Config.GEOFILES_PATTERN);
        ArrayList<File> arrayList = new ArrayList<File>();
        for (File stringArray : file.listFiles()) {
            String string;
            Matcher matcher;
            if (stringArray.isDirectory() || !(matcher = pattern.matcher(string = stringArray.getName())).matches()) continue;
            arrayList.add(stringArray);
        }
        Collections.sort(arrayList, NaturalOrderComparator.FILE_NAME_COMPARATOR);
        for (File file2 : arrayList) {
            byte by;
            int n2;
            String string = file2.getName();
            String[] stringArray = (string = string.substring(0, 5)).split("_");
            byte by2 = Byte.parseByte(stringArray[0]);
            int n3 = by2 - Config.GEO_X_FIRST;
            if (a[n3][n2 = (by = Byte.parseByte(stringArray[1])) - Config.GEO_Y_FIRST] != null) {
                bc.info("GepEngine: Ignore " + file2.getName());
                continue;
            }
            GeoEngine.a[n3][n2] = GeoFileLoader.LoadGeodataFile(file2, n3, n2);
            GeoFileLoader geoFileLoader = GeoEngine.a[n3][n2];
            if (geoFileLoader == null) {
                bc.error("GepEngine: Bypass " + file2.getName());
                continue;
            }
            geoFileLoader.load(a[n3][n2], a[n3][n2], 0);
            ++n;
        }
        bc.info("GeoEngine: Loaded " + n + " map(s), max layers: " + MAX_LAYERS);
        if (Config.COMPACT_GEO) {
            GeoEngine.compact();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int NextGeoIndex(int n, int n2, int n3) {
        if (!Config.ALLOW_GEODATA) {
            return 0;
        }
        int n4 = n - Config.GEO_X_FIRST;
        int n5 = n2 - Config.GEO_Y_FIRST;
        int n6 = -1;
        byte[][][][][] byArray = a;
        synchronized (a) {
            GeoFileLoader geoFileLoader;
            byte[][][] byArray2 = a[n4][n5];
            for (int i = 0; i < byArray2.length; ++i) {
                if (byArray2[i] != null) continue;
                n6 = i;
                break;
            }
            if (n6 == -1) {
                n6 = byArray2.length;
                byte[][][] byArrayArray = new byte[n6 + 1][][];
                for (int i = 0; i < byArray2.length; ++i) {
                    byArrayArray[i] = byArray2[i];
                }
                GeoEngine.a[n4][n5] = byArrayArray;
            }
            if ((geoFileLoader = a[n4][n5]) != null) {
                geoFileLoader.load(a[n4][n5], a[n4][n5], n6);
            }
            // ** MonitorExit[var6_6] (shouldn't be in output)
            return 0xF000000 | n4 << 16 | n5 << 8 | n6;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void FreeGeoIndex(int n) {
        if (!Config.ALLOW_GEODATA) {
            return;
        }
        if ((n & 0xF000000) != 0xF000000) {
            return;
        }
        int n2 = (n & 0xFF0000) >> 16;
        int n3 = (n & 0xFF00) >> 8;
        int n4 = n & 0xFF;
        byte[][][][][] byArray = a;
        synchronized (a) {
            GeoEngine.a[n2][n3][n4] = null;
            // ** MonitorExit[var4_4] (shouldn't be in output)
            return;
        }
    }

    public static void removeGeoCollision(GeoCollision geoCollision, int n) {
        Shape shape = geoCollision.getShape();
        byte[][] byArray = geoCollision.getGeoAround();
        if (byArray == null) {
            throw new RuntimeException("Attempt to remove unitialized collision: " + geoCollision);
        }
        int n2 = shape.getXmin() - World.MAP_MIN_X - 16 >> 4;
        int n3 = shape.getYmin() - World.MAP_MIN_Y - 16 >> 4;
        int n4 = shape.getZmin();
        int n5 = shape.getZmax();
        for (int i = 0; i < byArray.length; ++i) {
            block5: for (int j = 0; j < byArray[i].length; ++j) {
                int n6 = n2 + i;
                int n7 = n3 + j;
                byte[] byArray2 = GeoEngine.a(n6, n7, n);
                if (byArray2 == null) continue;
                int n8 = GeoEngine.getCell(n6);
                int n9 = GeoEngine.getCell(n7);
                int n10 = 0;
                byte by = byArray2[n10];
                ++n10;
                switch (by) {
                    case 1: {
                        short s = GeoEngine.makeShort(byArray2[(n10 += (n8 << 3) + n9 << 1) + 1], byArray2[n10]);
                        int n11 = (byte)(s & 0xF);
                        s = (short)(s & 0xFFF0);
                        s = (short)(s >> 1);
                        if (s < n4 || s > n5) continue block5;
                        s = (short)(s << 1);
                        s = (short)(s & 0xFFF0);
                        s = (short)(s | n11);
                        s = geoCollision.isConcrete() ? (short)(s | byArray[i][j]) : (short)(s & ~byArray[i][j]);
                        byArray2[n10 + 1] = (byte)(s >> 8);
                        byArray2[n10] = (byte)(s & 0xFF);
                        continue block5;
                    }
                    case 2: {
                        byte by2;
                        short s;
                        int n12 = -1;
                        for (int k = (n8 << 3) + n9; k > 0; --k) {
                            by2 = byArray2[n10];
                            n10 += (by2 << 1) + 1;
                        }
                        by2 = byArray2[n10];
                        ++n10;
                        if (by2 <= 0 || by2 > MAX_LAYERS) continue block5;
                        int n13 = Short.MIN_VALUE;
                        int n11 = 15;
                        while (by2 > 0) {
                            int n14;
                            s = GeoEngine.makeShort(byArray2[n10 + 1], byArray2[n10]);
                            byte by3 = (byte)(s & 0xF);
                            s = (short)(s & 0xFFF0);
                            s = (short)(s >> 1);
                            int n15 = Math.abs(n4 - n13);
                            if (n15 > (n14 = Math.abs(n5 - s))) {
                                n11 = by3;
                                n13 = s;
                                n12 = n10;
                            }
                            by2 = (byte)(by2 - 1);
                            n10 += 2;
                        }
                        if (n13 == Short.MIN_VALUE || n13 < n4 || n13 > n5) continue block5;
                        n13 = (short)(n13 << 1);
                        n13 = (short)(n13 & 0xFFF0);
                        n13 = (short)(n13 | n11);
                        n13 = geoCollision.isConcrete() ? (int)((short)(n13 | byArray[i][j])) : (int)((short)(n13 & ~byArray[i][j]));
                        byArray2[n12 + 1] = (byte)(n13 >> 8);
                        byArray2[n12] = (byte)(n13 & 0xFF);
                    }
                }
            }
        }
    }

    public static void applyGeoCollision(GeoCollision geoCollision, int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        Shape shape = geoCollision.getShape();
        if (shape.getXmax() == shape.getYmax() && shape.getXmax() == 0) {
            throw new RuntimeException("Attempt to add incorrect collision: " + geoCollision);
        }
        boolean bl = false;
        int n8 = shape.getXmin() - World.MAP_MIN_X - 16 >> 4;
        int n9 = shape.getXmax() - World.MAP_MIN_X + 16 >> 4;
        int n10 = shape.getYmin() - World.MAP_MIN_Y - 16 >> 4;
        int n11 = shape.getYmax() - World.MAP_MIN_Y + 16 >> 4;
        int n12 = shape.getZmin();
        int n13 = shape.getZmax();
        byte[][] byArray = geoCollision.getGeoAround();
        if (byArray == null) {
            bl = true;
            byte[][] byArray2 = new byte[n9 - n8 + 1][n11 - n10 + 1];
            for (n7 = n8; n7 <= n9; ++n7) {
                block5: for (n6 = n10; n6 <= n11; ++n6) {
                    n5 = (n7 << 4) + World.MAP_MIN_X;
                    n4 = (n6 << 4) + World.MAP_MIN_Y;
                    for (n3 = n5; n3 < n5 + 16; ++n3) {
                        for (n2 = n4; n2 < n4 + 16; ++n2) {
                            if (!shape.isInside(n3, n2)) continue;
                            byArray2[n7 - n8][n6 - n10] = 1;
                            continue block5;
                        }
                    }
                }
            }
            byArray = new byte[n9 - n8 + 1][n11 - n10 + 1];
            for (n7 = 0; n7 < byArray2.length; ++n7) {
                for (n6 = 0; n6 < byArray2[n7].length; ++n6) {
                    if (byArray2[n7][n6] != 1) continue;
                    byArray[n7][n6] = 15;
                    if (n6 > 0 && byArray2[n7][n6 - 1] == 0) {
                        n5 = byArray[n7][n6 - 1];
                        byArray[n7][n6 - 1] = n5 = (int)((byte)(n5 | 4));
                    }
                    if (n6 + 1 < byArray2[n7].length && byArray2[n7][n6 + 1] == 0) {
                        n5 = byArray[n7][n6 + 1];
                        byArray[n7][n6 + 1] = n5 = (int)((byte)(n5 | 8));
                    }
                    if (n7 > 0 && byArray2[n7 - 1][n6] == 0) {
                        n5 = byArray[n7 - 1][n6];
                        byArray[n7 - 1][n6] = n5 = (int)((byte)(n5 | 1));
                    }
                    if (n7 + 1 >= byArray2.length || byArray2[n7 + 1][n6] != 0) continue;
                    n5 = byArray[n7 + 1][n6];
                    byArray[n7 + 1][n6] = n5 = (int)((byte)(n5 | 2));
                }
            }
            geoCollision.setGeoAround(byArray);
        }
        for (n5 = 0; n5 < byArray.length; ++n5) {
            block11: for (n4 = 0; n4 < byArray[n5].length; ++n4) {
                n3 = n8 + n5;
                n2 = n10 + n4;
                byte[] byArray3 = GeoEngine.a(n3, n2, n);
                if (byArray3 == null) continue;
                int n14 = GeoEngine.getCell(n3);
                int n15 = GeoEngine.getCell(n2);
                int n16 = 0;
                byte by = byArray3[n16];
                ++n16;
                switch (by) {
                    case 1: {
                        short s = GeoEngine.makeShort(byArray3[(n16 += (n14 << 3) + n15 << 1) + 1], byArray3[n16]);
                        n7 = (byte)(s & 0xF);
                        s = (short)(s & 0xFFF0);
                        s = (short)(s >> 1);
                        if (s < n12 || s > n13) continue block11;
                        n6 = byArray[n5][n4];
                        if (bl) {
                            n6 = geoCollision.isConcrete() ? (int)((byte)(n6 & n7)) : (int)((byte)(n6 & ~n7));
                            byArray[n5][n4] = n6;
                        }
                        s = (short)(s << 1);
                        s = (short)(s & 0xFFF0);
                        s = (short)(s | n7);
                        s = geoCollision.isConcrete() ? (short)(s & ~n6) : (short)(s | n6);
                        byArray3[n16 + 1] = (byte)(s >> 8);
                        byArray3[n16] = (byte)(s & 0xFF);
                        continue block11;
                    }
                    case 2: {
                        byte by2;
                        int n17 = -1;
                        for (int i = (n14 << 3) + n15; i > 0; --i) {
                            by2 = byArray3[n16];
                            n16 += (by2 << 1) + 1;
                        }
                        by2 = byArray3[n16];
                        ++n16;
                        if (by2 <= 0 || by2 > MAX_LAYERS) continue block11;
                        int n18 = Short.MIN_VALUE;
                        n7 = 15;
                        while (by2 > 0) {
                            int n19;
                            short s = GeoEngine.makeShort(byArray3[n16 + 1], byArray3[n16]);
                            byte by3 = (byte)(s & 0xF);
                            s = (short)(s & 0xFFF0);
                            s = (short)(s >> 1);
                            int n20 = Math.abs(n12 - n18);
                            if (n20 > (n19 = Math.abs(n13 - s))) {
                                n7 = by3;
                                n18 = s;
                                n17 = n16;
                            }
                            by2 = (byte)(by2 - 1);
                            n16 += 2;
                        }
                        if (n18 == Short.MIN_VALUE || n18 < n12 || n18 > n13) continue block11;
                        n6 = byArray[n5][n4];
                        if (bl) {
                            n6 = geoCollision.isConcrete() ? (int)((byte)(n6 & n7)) : (int)((byte)(n6 & ~n7));
                            byArray[n5][n4] = n6;
                        }
                        n18 = (short)(n18 << 1);
                        n18 = (short)(n18 & 0xFFF0);
                        n18 = (short)(n18 | n7);
                        n18 = geoCollision.isConcrete() ? (int)((short)(n18 & ~n6)) : (int)((short)(n18 | n6));
                        byArray3[n17 + 1] = (byte)(n18 >> 8);
                        byArray3[n17] = (byte)(n18 & 0xFF);
                    }
                }
            }
        }
    }

    public static void compact() {
        long l = 0L;
        long l2 = 0L;
        for (int i = 0; i < World.WORLD_SIZE_X; ++i) {
            for (int j = 0; j < World.WORLD_SIZE_Y; ++j) {
                if (a[i][j] == null) continue;
                l += 65536L;
                GeoOptimizer.BlockLink[] blockLinkArray = GeoOptimizer.loadBlockMatches("geodata/matches/" + (i + Config.GEO_X_FIRST) + "_" + (j + Config.GEO_Y_FIRST) + ".matches");
                if (blockLinkArray == null) continue;
                for (int k = 0; k < blockLinkArray.length; ++k) {
                    byte[][][] byArray = a[blockLinkArray[k].linkMapX][blockLinkArray[k].linkMapY];
                    if (byArray == null) continue;
                    byArray[blockLinkArray[k].linkBlockIndex][0] = a[i][j][blockLinkArray[k].blockIndex][0];
                    ++l2;
                }
            }
        }
        bc.info(String.format("GeoEngine: - Compacted %d of %d blocks...", l2, l));
    }

    public static boolean equalsData(byte[] byArray, byte[] byArray2) {
        if (byArray.length != byArray2.length) {
            return false;
        }
        for (int i = 0; i < byArray.length; ++i) {
            if (byArray[i] == byArray2[i]) continue;
            return false;
        }
        return true;
    }

    public static boolean compareGeoBlocks(int n, int n2, int n3, int n4, int n5, int n6) {
        return GeoEngine.equalsData(a[n][n2][n3][0], a[n4][n5][n6][0]);
    }

    private static void as() {
        bc.info("GeoEngine: - Generating Checksums...");
        new File(Config.DATAPACK_ROOT, "geodata/checksum").mkdirs();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        GeoOptimizer.checkSums = new int[World.WORLD_SIZE_X][World.WORLD_SIZE_Y][];
        for (int i = 0; i < World.WORLD_SIZE_X; ++i) {
            for (int j = 0; j < World.WORLD_SIZE_Y; ++j) {
                if (a[i][j] == null) continue;
                executorService.execute(new GeoOptimizer.CheckSumLoader(i, j, a[i][j]));
            }
        }
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        }
        catch (InterruptedException interruptedException) {
            bc.error("", (Throwable)interruptedException);
        }
    }

    private static void c(int n) {
        bc.info("GeoEngine: Generating Block Matches...");
        new File(Config.DATAPACK_ROOT, "geodata/matches").mkdirs();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < World.WORLD_SIZE_X; ++i) {
            for (int j = 0; j < World.WORLD_SIZE_Y; ++j) {
                if (a[i][j] == null || GeoOptimizer.checkSums == null || GeoOptimizer.checkSums[i][j] == null) continue;
                executorService.execute(new GeoOptimizer.GeoBlocksMatchFinder(i, j, n));
            }
        }
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        }
        catch (InterruptedException interruptedException) {
            bc.error("", (Throwable)interruptedException);
        }
    }

    public static void deleteChecksumFiles() {
        for (int i = 0; i < World.WORLD_SIZE_X; ++i) {
            for (int j = 0; j < World.WORLD_SIZE_Y; ++j) {
                if (a[i][j] == null) continue;
                new File(Config.DATAPACK_ROOT, "geodata/checksum/" + (i + Config.GEO_X_FIRST) + "_" + (j + Config.GEO_Y_FIRST) + ".crc").delete();
            }
        }
    }

    public static void genBlockMatches(int n) {
        GeoEngine.as();
        GeoEngine.c(n);
    }

    public static void unload() {
        for (int i = 0; i < World.WORLD_SIZE_X; ++i) {
            for (int j = 0; j < World.WORLD_SIZE_Y; ++j) {
                GeoEngine.a[i][j] = null;
            }
        }
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    private static abstract class GeoFileLoader
    extends Enum<GeoFileLoader> {
        public static final /* enum */ GeoFileLoader L2J = new GeoFileLoader(){

            @Override
            public boolean matches(File file) {
                return file.getName().endsWith(".l2j");
            }

            /*
             * Enabled aggressive exception aggregation
             */
            @Override
            public ByteBuffer read(File file, int n, int n2) {
                try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");){
                    MappedByteBuffer mappedByteBuffer;
                    block14: {
                        FileChannel fileChannel = randomAccessFile.getChannel();
                        try {
                            int n3 = (int)fileChannel.size();
                            if (n3 < 196608) {
                                throw new RuntimeException("Invalid geodata : " + file.getName() + "!");
                            }
                            MappedByteBuffer mappedByteBuffer2 = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, n3);
                            mappedByteBuffer2.order(ByteOrder.LITTLE_ENDIAN);
                            mappedByteBuffer = mappedByteBuffer2;
                            if (fileChannel == null) break block14;
                        }
                        catch (Throwable throwable) {
                            if (fileChannel != null) {
                                try {
                                    fileChannel.close();
                                }
                                catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                        fileChannel.close();
                    }
                    return mappedByteBuffer;
                }
                catch (IOException iOException) {
                    bc.error("", (Throwable)iOException);
                    return null;
                }
            }
        };
        public static final /* enum */ GeoFileLoader L2G = new GeoFileLoader(){

            @Override
            public boolean matches(File file) {
                return file.getName().endsWith(".l2g");
            }

            @Override
            public ByteBuffer read(File file, int n, int n2) {
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                    FileChannel fileChannel = randomAccessFile.getChannel();
                    int n3 = 0;
                    int n4 = Math.max(4, (int)fileChannel.size() - 4);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(n4).order(ByteOrder.LITTLE_ENDIAN);
                    byteBuffer.limit(4);
                    fileChannel.read(byteBuffer);
                    byteBuffer.flip();
                    n3 = 0x814141AB ^ byteBuffer.getInt();
                    byteBuffer.clear();
                    fileChannel.read(byteBuffer);
                    byteBuffer.rewind();
                    byte by = (byte)(n3 >> 24 & 0xFF ^ n3 >> 16 & 0xFF ^ n3 >> 8 & 0xFF ^ n3 >> 0 & 0xFF);
                    while (byteBuffer.hasRemaining()) {
                        byteBuffer.put(byteBuffer.position(), (byte)(byteBuffer.get() ^ by));
                        by = byteBuffer.get(byteBuffer.position() - 1);
                        n3 -= by;
                    }
                    byteBuffer.rewind();
                    if (n3 != 0) {
                        throw new RuntimeException("Test failed!");
                    }
                    if (n4 < 196608 || n3 != 0) {
                        throw new RuntimeException("Invalid geodata : " + file.getName() + "!");
                    }
                    return byteBuffer;
                }
                catch (IOException iOException) {
                    bc.error("", (Throwable)iOException);
                    return null;
                }
            }
        };
        public static final /* enum */ GeoFileLoader DAT = new GeoFileLoader(){

            @Override
            public boolean matches(File file) {
                return file.getName().endsWith(".dat");
            }

            @Override
            public ByteBuffer read(File file, int n, int n2) {
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                    FileChannel fileChannel = randomAccessFile.getChannel();
                    int n3 = (int)fileChannel.size();
                    MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, n3);
                    mappedByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                    byte by = mappedByteBuffer.get();
                    byte by2 = mappedByteBuffer.get();
                    if (n3 < 393234 || by != n || by2 != n2) {
                        fileChannel.close();
                        randomAccessFile.close();
                        throw new RuntimeException("Invalid geodata : " + file.getName() + "!");
                    }
                    mappedByteBuffer.rewind();
                    return mappedByteBuffer;
                }
                catch (IOException iOException) {
                    bc.error("", (Throwable)iOException);
                    return null;
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public boolean load(byte[][][] byArray, ByteBuffer byteBuffer, int n) {
                int n2 = 18;
                int n3 = 0;
                byte[][][] byArray2 = byArray;
                synchronized (byArray) {
                    Object object = byArray[n];
                    if (object == null) {
                        byte[][] byArrayArray = new byte[65536][];
                        object = byArrayArray;
                        byArray[n] = byArrayArray;
                    }
                    // ** MonitorExit[var7_6] (shouldn't be in output)
                    byteBuffer.rewind();
                    block10: for (n3 = 0; n3 < 65536; ++n3) {
                        short s = byteBuffer.getShort(n2);
                        n2 += 2;
                        switch (s) {
                            case 0: {
                                byte[] byArray3 = new byte[]{0, byteBuffer.get(n2), byteBuffer.get(n2 + 1)};
                                n2 += 4;
                                object[n3] = byArray3;
                                continue block10;
                            }
                            case 64: {
                                byte[] byArray3 = new byte[129];
                                byArray3[0] = 1;
                                byteBuffer.position(n2);
                                byteBuffer.get(byArray3, 1, 128);
                                n2 += 128;
                                object[n3] = byArray3;
                                continue block10;
                            }
                            default: {
                                byte[] byArray3;
                                ByteBuffer byteBuffer2 = tempBuff;
                                synchronized (byteBuffer2) {
                                    tempBuff.clear();
                                    tempBuff.put((byte)2);
                                    for (int i = 0; i < 64; ++i) {
                                        byte by = (byte)byteBuffer.getShort(n2);
                                        if (by <= 0 || by > 127) {
                                            throw new RuntimeException("Invalid layer count for MultilayerBlock");
                                        }
                                        MAX_LAYERS = Math.max(MAX_LAYERS, by);
                                        n2 += 2;
                                        tempBuff.put(by);
                                        for (int j = 0; j < by << 1; ++j) {
                                            tempBuff.put(byteBuffer.get(n2++));
                                        }
                                    }
                                    tempBuff.flip();
                                    byArray3 = Arrays.copyOf(tempBuff.array(), tempBuff.remaining());
                                    tempBuff.rewind();
                                }
                                object[n3] = byArray3;
                            }
                        }
                    }
                    byteBuffer.position(0);
                    return true;
                }
            }
        };
        protected static final ByteBuffer tempBuff;
        private static final /* synthetic */ GeoFileLoader[] a;

        public static GeoFileLoader[] values() {
            return (GeoFileLoader[])a.clone();
        }

        public static GeoFileLoader valueOf(String string) {
            return Enum.valueOf(GeoFileLoader.class, string);
        }

        public abstract boolean matches(File var1);

        public abstract ByteBuffer read(File var1, int var2, int var3);

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public boolean load(byte[][][] byArray, ByteBuffer byteBuffer, int n) {
            int n2 = 0;
            int n3 = 0;
            byte by = 0;
            byte[][][] byArray2 = byArray;
            synchronized (byArray) {
                Object object = byArray[n];
                if (object == null) {
                    byte[][] byArrayArray = new byte[65536][];
                    object = byArrayArray;
                    byArray[n] = byArrayArray;
                }
                // ** MonitorExit[var9_7] (shouldn't be in output)
                block8: for (n3 = 0; n3 < 65536; ++n3) {
                    byte by2 = byteBuffer.get(n2);
                    ++n2;
                    switch (by2) {
                        case 0: {
                            byte[] byArray3 = new byte[]{by2, byteBuffer.get(n2), byteBuffer.get(n2 + 1)};
                            n2 += 2;
                            object[n3] = byArray3;
                            continue block8;
                        }
                        case 1: {
                            byte[] byArray3 = new byte[129];
                            byArray3[0] = by2;
                            byteBuffer.position(n2);
                            byteBuffer.get(byArray3, 1, 128);
                            n2 += 128;
                            object[n3] = byArray3;
                            continue block8;
                        }
                        case 2: {
                            int n4;
                            int n5 = n2;
                            for (n4 = 0; n4 < 64; ++n4) {
                                byte by3 = byteBuffer.get(n2);
                                MAX_LAYERS = Math.max(MAX_LAYERS, by3);
                                n2 += (by3 << 1) + 1;
                                if (by3 <= by) continue;
                                by = by3;
                            }
                            n4 = n2 - n5;
                            byte[] byArray3 = new byte[n4 + 1];
                            byArray3[0] = by2;
                            byteBuffer.position(n5);
                            byteBuffer.get(byArray3, 1, n4);
                            object[n3] = byArray3;
                            continue block8;
                        }
                        default: {
                            throw new RuntimeException("Invalid geodata!");
                        }
                    }
                }
                return true;
            }
        }

        public static GeoFileLoader LoadGeodataFile(File file, int n, int n2) {
            String string = file.getName();
            if (n < 0 || n2 < 0 || n > (World.MAP_MAX_X >> 15) + Math.abs(World.MAP_MIN_X >> 15) || n2 > (World.MAP_MAX_Y >> 15) + Math.abs(World.MAP_MIN_Y >> 15)) {
                bc.info("GeoEngine: File " + string + " was not loaded!!! ");
                return null;
            }
            bc.info("GeoEngine: Loading: " + string);
            GeoFileLoader geoFileLoader = null;
            for (GeoFileLoader geoFileLoader2 : GeoFileLoader.values()) {
                if (!geoFileLoader2.matches(file)) continue;
                geoFileLoader = geoFileLoader2;
                break;
            }
            if (geoFileLoader == null) {
                bc.error("GeoEngine: Unknown file format: " + string);
                return null;
            }
            ByteBuffer byteBuffer = geoFileLoader.read(file, n + Config.GEO_X_FIRST, n2 + Config.GEO_Y_FIRST);
            if (byteBuffer == null) {
                bc.error("GeoEngine: Can't read " + string);
                return null;
            }
            GeoEngine.a[n][n2] = byteBuffer;
            return geoFileLoader;
        }

        private static /* synthetic */ GeoFileLoader[] a() {
            return new GeoFileLoader[]{L2J, L2G, DAT};
        }

        static {
            a = GeoFileLoader.a();
            tempBuff = ByteBuffer.allocate(24384).order(ByteOrder.LITTLE_ENDIAN);
        }
    }
}
