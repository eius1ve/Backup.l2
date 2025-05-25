/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model;

import java.util.Collections;
import java.util.List;
import l2.commons.collections.LazyArrayList;
import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Territory;
import l2.gameserver.model.WorldRegion;
import l2.gameserver.model.Zone;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class World {
    private static final Logger bJ = LoggerFactory.getLogger(World.class);
    public static final int MAP_MIN_X = Config.GEO_X_FIRST - 20 << 15;
    public static final int MAP_MAX_X = (Config.GEO_X_LAST - 20 + 1 << 15) - 1;
    public static final int MAP_MIN_Y = Config.GEO_Y_FIRST - 18 << 15;
    public static final int MAP_MAX_Y = (Config.GEO_Y_LAST - 18 + 1 << 15) - 1;
    public static final int MAP_MIN_Z = Config.MAP_MIN_Z;
    public static final int MAP_MAX_Z = Config.MAP_MAX_Z;
    public static final int WORLD_SIZE_X = Config.GEO_X_LAST - Config.GEO_X_FIRST + 1;
    public static final int WORLD_SIZE_Y = Config.GEO_Y_LAST - Config.GEO_Y_FIRST + 1;
    public static final int SHIFT_BY = Config.SHIFT_BY;
    public static final int SHIFT_BY_Z = Config.SHIFT_BY_Z;
    public static final int OFFSET_X = Math.abs(MAP_MIN_X >> SHIFT_BY);
    public static final int OFFSET_Y = Math.abs(MAP_MIN_Y >> SHIFT_BY);
    public static final int OFFSET_Z = Math.abs(MAP_MIN_Z >> SHIFT_BY_Z);
    private static final int jG = (MAP_MAX_X >> SHIFT_BY) + OFFSET_X;
    private static final int jH = (MAP_MAX_Y >> SHIFT_BY) + OFFSET_Y;
    private static final int jI = (MAP_MAX_Z >> SHIFT_BY_Z) + OFFSET_Z;
    private static volatile WorldRegion[][][] a = new WorldRegion[jG + 1][jH + 1][jI + 1];

    public static void init() {
        bJ.info("World Build: Creating regions: [" + (jG + 1) + "][" + (jH + 1) + "][" + (jI + 1) + "].");
    }

    private static WorldRegion[][][] a() {
        return a;
    }

    private static int c(int n) {
        if (n < 0) {
            n = 0;
        } else if (n > jG) {
            n = jG;
        }
        return n;
    }

    private static int d(int n) {
        if (n < 0) {
            n = 0;
        } else if (n > jH) {
            n = jH;
        }
        return n;
    }

    private static int e(int n) {
        if (n < 0) {
            n = 0;
        } else if (n > jI) {
            n = jI;
        }
        return n;
    }

    public static int validCoordX(int n) {
        if (n < MAP_MIN_X) {
            n = MAP_MIN_X + 1;
        } else if (n > MAP_MAX_X) {
            n = MAP_MAX_X - 1;
        }
        return n;
    }

    public static int validCoordY(int n) {
        if (n < MAP_MIN_Y) {
            n = MAP_MIN_Y + 1;
        } else if (n > MAP_MAX_Y) {
            n = MAP_MAX_Y - 1;
        }
        return n;
    }

    public static int validCoordZ(int n) {
        if (n < MAP_MIN_Z) {
            n = MAP_MIN_Z + 1;
        } else if (n > MAP_MAX_Z) {
            n = MAP_MAX_Z - 1;
        }
        return n;
    }

    private static int regionX(int n) {
        return (n >> SHIFT_BY) + OFFSET_X;
    }

    private static int regionY(int n) {
        return (n >> SHIFT_BY) + OFFSET_Y;
    }

    private static int f(int n) {
        return (n >> SHIFT_BY_Z) + OFFSET_Z;
    }

    static boolean isNeighbour(int n, int n2, int n3, int n4, int n5, int n6) {
        return n <= n4 + 1 && n >= n4 - 1 && n2 <= n5 + 1 && n2 >= n5 - 1 && n3 <= n6 + 1 && n3 >= n6 - 1;
    }

    public static WorldRegion getRegion(Location location) {
        return World.a(World.c(World.regionX(location.x)), World.d(World.regionY(location.y)), World.e(World.f(location.z)));
    }

    public static WorldRegion getRegion(GameObject gameObject) {
        return World.a(World.c(World.regionX(gameObject.getX())), World.d(World.regionY(gameObject.getY())), World.e(World.f(gameObject.getZ())));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static WorldRegion a(int n, int n2, int n3) {
        WorldRegion[][][] worldRegionArray = World.a();
        WorldRegion worldRegion = null;
        worldRegion = worldRegionArray[n][n2][n3];
        if (worldRegion != null) return worldRegion;
        WorldRegion[][][] worldRegionArray2 = worldRegionArray;
        synchronized (worldRegionArray) {
            worldRegion = worldRegionArray[n][n2][n3];
            if (worldRegion != null) return worldRegion;
            WorldRegion worldRegion2 = new WorldRegion(n, n2, n3);
            worldRegionArray[n][n2][n3] = worldRegion2;
            return worldRegion2;
        }
    }

    public static Player getPlayer(String string) {
        return GameObjectsStorage.getPlayer(string);
    }

    public static Player getPlayer(int n) {
        return GameObjectsStorage.getPlayer(n);
    }

    public static void addVisibleObject(GameObject gameObject, Creature creature) {
        if (gameObject == null || !gameObject.isVisible() || gameObject.isInObserverMode()) {
            return;
        }
        WorldRegion worldRegion = World.getRegion(gameObject);
        WorldRegion worldRegion2 = gameObject.getCurrentRegion();
        if (worldRegion2 == worldRegion) {
            return;
        }
        if (worldRegion2 == null) {
            gameObject.setCurrentRegion(worldRegion);
            worldRegion.addObject(gameObject);
            for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
                for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                    for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                        World.a(i, j, k).addToPlayers(gameObject, creature);
                    }
                }
            }
        } else {
            int n;
            int n2;
            int n3;
            worldRegion2.removeObject(gameObject);
            gameObject.setCurrentRegion(worldRegion);
            worldRegion.addObject(gameObject);
            for (n3 = World.c(worldRegion2.getX() - 1); n3 <= World.c(worldRegion2.getX() + 1); ++n3) {
                for (n2 = World.d(worldRegion2.getY() - 1); n2 <= World.d(worldRegion2.getY() + 1); ++n2) {
                    for (n = World.e(worldRegion2.getZ() - 1); n <= World.e(worldRegion2.getZ() + 1); ++n) {
                        if (World.isNeighbour(worldRegion.getX(), worldRegion.getY(), worldRegion.getZ(), n3, n2, n)) continue;
                        World.a(n3, n2, n).removeFromPlayers(gameObject);
                    }
                }
            }
            for (n3 = World.c(worldRegion.getX() - 1); n3 <= World.c(worldRegion.getX() + 1); ++n3) {
                for (n2 = World.d(worldRegion.getY() - 1); n2 <= World.d(worldRegion.getY() + 1); ++n2) {
                    for (n = World.e(worldRegion.getZ() - 1); n <= World.e(worldRegion.getZ() + 1); ++n) {
                        if (World.isNeighbour(worldRegion2.getX(), worldRegion2.getY(), worldRegion2.getZ(), n3, n2, n)) continue;
                        World.a(n3, n2, n).addToPlayers(gameObject, creature);
                    }
                }
            }
        }
    }

    public static void removeVisibleObject(GameObject gameObject) {
        if (gameObject == null || gameObject.isVisible() || gameObject.isInObserverMode()) {
            return;
        }
        WorldRegion worldRegion = gameObject.getCurrentRegion();
        if (worldRegion == null) {
            return;
        }
        gameObject.setCurrentRegion(null);
        worldRegion.removeObject(gameObject);
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    World.a(i, j, k).removeFromPlayers(gameObject);
                }
            }
        }
    }

    public static GameObject getAroundObjectById(GameObject gameObject, int n) {
        WorldRegion worldRegion = gameObject.getCurrentRegion();
        if (worldRegion == null) {
            return null;
        }
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    for (GameObject gameObject2 : World.a(i, j, k)) {
                        if (gameObject2.getObjectId() != n) continue;
                        return gameObject2;
                    }
                }
            }
        }
        return null;
    }

    public static List<GameObject> getAroundObjects(GameObject gameObject) {
        WorldRegion worldRegion = gameObject.getCurrentRegion();
        if (worldRegion == null) {
            return Collections.emptyList();
        }
        int n = gameObject.getObjectId();
        int n2 = gameObject.getReflectionId();
        LazyArrayList<GameObject> lazyArrayList = new LazyArrayList<GameObject>(128);
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    for (GameObject gameObject2 : World.a(i, j, k)) {
                        if (gameObject2.getObjectId() == n || gameObject2.getReflectionId() != n2) continue;
                        lazyArrayList.add(gameObject2);
                    }
                }
            }
        }
        return lazyArrayList;
    }

    public static List<GameObject> getAroundObjects(GameObject gameObject, int n, int n2) {
        WorldRegion worldRegion = gameObject.getCurrentRegion();
        if (worldRegion == null) {
            return Collections.emptyList();
        }
        int n3 = gameObject.getObjectId();
        int n4 = gameObject.getReflectionId();
        int n5 = gameObject.getX();
        int n6 = gameObject.getY();
        int n7 = gameObject.getZ();
        int n8 = n * n;
        LazyArrayList<GameObject> lazyArrayList = new LazyArrayList<GameObject>(128);
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    for (GameObject gameObject2 : World.a(i, j, k)) {
                        int n9;
                        int n10;
                        if (gameObject2.getObjectId() == n3 || gameObject2.getReflectionId() != n4 || Math.abs(gameObject2.getZ() - n7) > n2 || (n10 = Math.abs(gameObject2.getX() - n5)) > n || (n9 = Math.abs(gameObject2.getY() - n6)) > n || n10 * n10 + n9 * n9 > n8) continue;
                        lazyArrayList.add(gameObject2);
                    }
                }
            }
        }
        return lazyArrayList;
    }

    public static List<Creature> getAroundCharacters(GameObject gameObject) {
        WorldRegion worldRegion = gameObject.getCurrentRegion();
        if (worldRegion == null) {
            return Collections.emptyList();
        }
        int n = gameObject.getObjectId();
        int n2 = gameObject.getReflectionId();
        LazyArrayList<Creature> lazyArrayList = new LazyArrayList<Creature>(64);
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    for (GameObject gameObject2 : World.a(i, j, k)) {
                        if (!gameObject2.isCreature() || gameObject2.getObjectId() == n || gameObject2.getReflectionId() != n2) continue;
                        lazyArrayList.add((Creature)gameObject2);
                    }
                }
            }
        }
        return lazyArrayList;
    }

    public static List<Creature> getAroundCharacters(GameObject gameObject, int n, int n2) {
        WorldRegion worldRegion = gameObject.getCurrentRegion();
        if (worldRegion == null) {
            return Collections.emptyList();
        }
        int n3 = gameObject.getObjectId();
        int n4 = gameObject.getReflectionId();
        int n5 = gameObject.getX();
        int n6 = gameObject.getY();
        int n7 = gameObject.getZ();
        int n8 = n * n;
        LazyArrayList<Creature> lazyArrayList = new LazyArrayList<Creature>(64);
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    for (GameObject gameObject2 : World.a(i, j, k)) {
                        int n9;
                        int n10;
                        if (!gameObject2.isCreature() || gameObject2.getObjectId() == n3 || gameObject2.getReflectionId() != n4 || Math.abs(gameObject2.getZ() - n7) > n2 || (n10 = Math.abs(gameObject2.getX() - n5)) > n || (n9 = Math.abs(gameObject2.getY() - n6)) > n || n10 * n10 + n9 * n9 > n8) continue;
                        lazyArrayList.add((Creature)gameObject2);
                    }
                }
            }
        }
        return lazyArrayList;
    }

    public static List<NpcInstance> getAroundNpc(GameObject gameObject) {
        WorldRegion worldRegion = gameObject.getCurrentRegion();
        if (worldRegion == null) {
            return Collections.emptyList();
        }
        int n = gameObject.getObjectId();
        int n2 = gameObject.getReflectionId();
        LazyArrayList<NpcInstance> lazyArrayList = new LazyArrayList<NpcInstance>(64);
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    for (GameObject gameObject2 : World.a(i, j, k)) {
                        if (!gameObject2.isNpc() || gameObject2.getObjectId() == n || gameObject2.getReflectionId() != n2) continue;
                        lazyArrayList.add((NpcInstance)gameObject2);
                    }
                }
            }
        }
        return lazyArrayList;
    }

    public static List<NpcInstance> getAroundNpc(GameObject gameObject, int n, int n2) {
        WorldRegion worldRegion = gameObject.getCurrentRegion();
        if (worldRegion == null) {
            return Collections.emptyList();
        }
        int n3 = gameObject.getObjectId();
        int n4 = gameObject.getReflectionId();
        int n5 = gameObject.getX();
        int n6 = gameObject.getY();
        int n7 = gameObject.getZ();
        int n8 = n * n;
        LazyArrayList<NpcInstance> lazyArrayList = new LazyArrayList<NpcInstance>(64);
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    for (GameObject gameObject2 : World.a(i, j, k)) {
                        int n9;
                        int n10;
                        if (!gameObject2.isNpc() || gameObject2.getObjectId() == n3 || gameObject2.getReflectionId() != n4 || Math.abs(gameObject2.getZ() - n7) > n2 || (n10 = Math.abs(gameObject2.getX() - n5)) > n || (n9 = Math.abs(gameObject2.getY() - n6)) > n || n10 * n10 + n9 * n9 > n8) continue;
                        lazyArrayList.add((NpcInstance)gameObject2);
                    }
                }
            }
        }
        return lazyArrayList;
    }

    public static List<Playable> getAroundPlayables(GameObject gameObject) {
        WorldRegion worldRegion = gameObject.getCurrentRegion();
        if (worldRegion == null) {
            return Collections.emptyList();
        }
        int n = gameObject.getObjectId();
        int n2 = gameObject.getReflectionId();
        LazyArrayList<Playable> lazyArrayList = new LazyArrayList<Playable>(64);
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    for (GameObject gameObject2 : World.a(i, j, k)) {
                        if (!gameObject2.isPlayable() || gameObject2.getObjectId() == n || gameObject2.getReflectionId() != n2) continue;
                        lazyArrayList.add((Playable)gameObject2);
                    }
                }
            }
        }
        return lazyArrayList;
    }

    public static List<Playable> getAroundPlayables(GameObject gameObject, int n, int n2) {
        WorldRegion worldRegion = gameObject.getCurrentRegion();
        if (worldRegion == null) {
            return Collections.emptyList();
        }
        int n3 = gameObject.getObjectId();
        int n4 = gameObject.getReflectionId();
        int n5 = gameObject.getX();
        int n6 = gameObject.getY();
        int n7 = gameObject.getZ();
        int n8 = n * n;
        LazyArrayList<Playable> lazyArrayList = new LazyArrayList<Playable>(64);
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    for (GameObject gameObject2 : World.a(i, j, k)) {
                        int n9;
                        int n10;
                        if (!gameObject2.isPlayable() || gameObject2.getObjectId() == n3 || gameObject2.getReflectionId() != n4 || Math.abs(gameObject2.getZ() - n7) > n2 || (n10 = Math.abs(gameObject2.getX() - n5)) > n || (n9 = Math.abs(gameObject2.getY() - n6)) > n || n10 * n10 + n9 * n9 > n8) continue;
                        lazyArrayList.add((Playable)gameObject2);
                    }
                }
            }
        }
        return lazyArrayList;
    }

    public static List<Player> getAroundPlayers(GameObject gameObject) {
        WorldRegion worldRegion = gameObject.getCurrentRegion();
        if (worldRegion == null) {
            return Collections.emptyList();
        }
        int n = gameObject.getObjectId();
        int n2 = gameObject.getReflectionId();
        LazyArrayList<Player> lazyArrayList = new LazyArrayList<Player>(64);
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    for (GameObject gameObject2 : World.a(i, j, k)) {
                        if (!gameObject2.isPlayer() || gameObject2.getObjectId() == n || gameObject2.getReflectionId() != n2) continue;
                        lazyArrayList.add((Player)gameObject2);
                    }
                }
            }
        }
        return lazyArrayList;
    }

    public static List<Player> getAroundPlayers(GameObject gameObject, int n, int n2) {
        WorldRegion worldRegion = gameObject.getCurrentRegion();
        if (worldRegion == null) {
            return Collections.emptyList();
        }
        int n3 = gameObject.getObjectId();
        int n4 = gameObject.getReflectionId();
        int n5 = gameObject.getX();
        int n6 = gameObject.getY();
        int n7 = gameObject.getZ();
        int n8 = n * n;
        LazyArrayList<Player> lazyArrayList = new LazyArrayList<Player>(64);
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    for (GameObject gameObject2 : World.a(i, j, k)) {
                        int n9;
                        int n10;
                        if (!gameObject2.isPlayer() || gameObject2.getObjectId() == n3 || gameObject2.getReflectionId() != n4 || Math.abs(gameObject2.getZ() - n7) > n2 || (n10 = Math.abs(gameObject2.getX() - n5)) > n || (n9 = Math.abs(gameObject2.getY() - n6)) > n || n10 * n10 + n9 * n9 > n8) continue;
                        lazyArrayList.add((Player)gameObject2);
                    }
                }
            }
        }
        return lazyArrayList;
    }

    public static boolean isNeighborsEmpty(WorldRegion worldRegion) {
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    if (World.a(i, j, k).isEmpty()) continue;
                    return false;
                }
            }
        }
        return true;
    }

    public static void activate(WorldRegion worldRegion) {
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    World.a(i, j, k).setActive(true);
                }
            }
        }
    }

    public static void deactivate(WorldRegion worldRegion) {
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    if (!World.isNeighborsEmpty(World.a(i, j, k))) continue;
                    World.a(i, j, k).setActive(false);
                }
            }
        }
    }

    public static void showObjectsToPlayer(Player player) {
        WorldRegion worldRegion;
        WorldRegion worldRegion2 = worldRegion = player.isInObserverMode() ? player.getObserverRegion() : player.getCurrentRegion();
        if (worldRegion == null) {
            return;
        }
        int n = player.getObjectId();
        int n2 = player.getReflectionId();
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    for (GameObject gameObject : World.a(i, j, k)) {
                        if (gameObject.getObjectId() == n || gameObject.getReflectionId() != n2) continue;
                        player.sendPacket(player.addVisibleObject(gameObject, null));
                    }
                }
            }
        }
    }

    public static void removeObjectsFromPlayer(Player player) {
        WorldRegion worldRegion;
        WorldRegion worldRegion2 = worldRegion = player.isInObserverMode() ? player.getObserverRegion() : player.getCurrentRegion();
        if (worldRegion == null) {
            return;
        }
        int n = player.getObjectId();
        int n2 = player.getReflectionId();
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    for (GameObject gameObject : World.a(i, j, k)) {
                        if (gameObject.getObjectId() == n || gameObject.getReflectionId() != n2) continue;
                        player.sendPacket(player.removeVisibleObject(gameObject, null));
                    }
                }
            }
        }
    }

    public static void removeObjectFromPlayers(GameObject gameObject) {
        WorldRegion worldRegion = gameObject.getCurrentRegion();
        if (worldRegion == null) {
            return;
        }
        int n = gameObject.getObjectId();
        int n2 = gameObject.getReflectionId();
        List<L2GameServerPacket> list = null;
        for (int i = World.c(worldRegion.getX() - 1); i <= World.c(worldRegion.getX() + 1); ++i) {
            for (int j = World.d(worldRegion.getY() - 1); j <= World.d(worldRegion.getY() + 1); ++j) {
                for (int k = World.e(worldRegion.getZ() - 1); k <= World.e(worldRegion.getZ() + 1); ++k) {
                    for (GameObject gameObject2 : World.a(i, j, k)) {
                        if (!gameObject2.isPlayer() || gameObject2.getObjectId() == n || gameObject2.getReflectionId() != n2) continue;
                        Player player = (Player)gameObject2;
                        player.sendPacket(player.removeVisibleObject(gameObject, list == null ? gameObject.deletePacketList() : list));
                    }
                }
            }
        }
    }

    static void addZone(Zone zone) {
        Reflection reflection = zone.getReflection();
        Territory territory = zone.getTerritory();
        if (territory == null) {
            bJ.info("World: zone - " + zone.getName() + " not has territory.");
            return;
        }
        for (int i = World.c(World.regionX(territory.getXmin())); i <= World.c(World.regionX(territory.getXmax())); ++i) {
            for (int j = World.d(World.regionY(territory.getYmin())); j <= World.d(World.regionY(territory.getYmax())); ++j) {
                for (int k = World.e(World.f(territory.getZmin())); k <= World.e(World.f(territory.getZmax())); ++k) {
                    WorldRegion worldRegion = World.a(i, j, k);
                    worldRegion.addZone(zone);
                    for (GameObject gameObject : worldRegion) {
                        if (!gameObject.isCreature() || gameObject.getReflection() != reflection) continue;
                        ((Creature)gameObject).updateZones();
                    }
                }
            }
        }
    }

    static void removeZone(Zone zone) {
        Reflection reflection = zone.getReflection();
        Territory territory = zone.getTerritory();
        if (territory == null) {
            bJ.info("World: zone - " + zone.getName() + " not has territory.");
            return;
        }
        for (int i = World.c(World.regionX(territory.getXmin())); i <= World.c(World.regionX(territory.getXmax())); ++i) {
            for (int j = World.d(World.regionY(territory.getYmin())); j <= World.d(World.regionY(territory.getYmax())); ++j) {
                for (int k = World.e(World.f(territory.getZmin())); k <= World.e(World.f(territory.getZmax())); ++k) {
                    WorldRegion worldRegion = World.a(i, j, k);
                    worldRegion.removeZone(zone);
                    for (GameObject gameObject : worldRegion) {
                        if (!gameObject.isCreature() || gameObject.getReflection() != reflection) continue;
                        ((Creature)gameObject).updateZones();
                    }
                }
            }
        }
    }

    public static void getZones(List<Zone> list, Location location, Reflection reflection) {
        WorldRegion worldRegion = World.getRegion(location);
        Zone[] zoneArray = worldRegion.getZones();
        if (zoneArray.length == 0) {
            return;
        }
        for (Zone zone : zoneArray) {
            if (!zone.checkIfInZone(location.x, location.y, location.z, reflection)) continue;
            list.add(zone);
        }
    }

    public static boolean isWater(Location location, Reflection reflection) {
        return World.getWater(location, reflection) != null;
    }

    public static Zone getWater(Location location, Reflection reflection) {
        WorldRegion worldRegion = World.getRegion(location);
        Zone[] zoneArray = worldRegion.getZones();
        if (zoneArray.length == 0) {
            return null;
        }
        for (Zone zone : zoneArray) {
            if (zone == null || !zone.isType(Zone.ZoneType.water) || !zone.checkIfInZone(location.x, location.y, location.z, reflection)) continue;
            return zone;
        }
        return null;
    }

    public static void broadcast(L2GameServerPacket ... l2GameServerPacketArray) {
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            player.sendPacket(l2GameServerPacketArray);
        }
    }

    public static int[] getStats() {
        int[] nArray = new int[32];
        for (int i = 0; i <= jG; ++i) {
            for (int j = 0; j <= jH; ++j) {
                for (int k = 0; k <= jI; ++k) {
                    nArray[0] = nArray[0] + 1;
                    WorldRegion worldRegion = a[i][j][k];
                    if (worldRegion != null) {
                        if (worldRegion.isActive()) {
                            nArray[1] = nArray[1] + 1;
                        } else {
                            nArray[2] = nArray[2] + 1;
                        }
                        for (GameObject gameObject : worldRegion) {
                            nArray[10] = nArray[10] + 1;
                            if (gameObject.isCreature()) {
                                Creature creature;
                                nArray[11] = nArray[11] + 1;
                                if (gameObject.isPlayer()) {
                                    nArray[12] = nArray[12] + 1;
                                    creature = (Player)gameObject;
                                    if (!((Player)creature).isInOfflineMode()) continue;
                                    nArray[13] = nArray[13] + 1;
                                    continue;
                                }
                                if (gameObject.isNpc()) {
                                    nArray[14] = nArray[14] + 1;
                                    if (gameObject.isMonster()) {
                                        nArray[16] = nArray[16] + 1;
                                        if (gameObject.isMinion()) {
                                            nArray[17] = nArray[17] + 1;
                                        }
                                    }
                                    if (!(creature = (NpcInstance)gameObject).hasAI() || !((NpcInstance)creature).getAI().isActive()) continue;
                                    nArray[15] = nArray[15] + 1;
                                    continue;
                                }
                                if (gameObject.isPlayable()) {
                                    nArray[18] = nArray[18] + 1;
                                    continue;
                                }
                                if (!gameObject.isDoor()) continue;
                                nArray[19] = nArray[19] + 1;
                                continue;
                            }
                            if (!gameObject.isItem()) continue;
                            nArray[20] = nArray[20] + 1;
                        }
                        continue;
                    }
                    nArray[3] = nArray[3] + 1;
                }
            }
        }
        return nArray;
    }
}
