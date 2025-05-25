/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import l2.commons.lang.ArrayUtils;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.Zone;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WorldRegion
implements Iterable<GameObject> {
    public static final WorldRegion[] EMPTY_L2WORLDREGION_ARRAY = new WorldRegion[0];
    private static final Logger bK = LoggerFactory.getLogger(WorldRegion.class);
    private final int jJ;
    private final int jK;
    private final int jL;
    private volatile GameObject[] b = GameObject.EMPTY_L2OBJECT_ARRAY;
    private int jM = 0;
    private volatile Zone[] a = Zone.EMPTY_L2ZONE_ARRAY;
    private int jN = 0;
    private final AtomicBoolean i = new AtomicBoolean();
    private Future<?> B;
    private final Lock u = new ReentrantLock();

    WorldRegion(int n, int n2, int n3) {
        this.jJ = n;
        this.jK = n2;
        this.jL = n3;
    }

    int getX() {
        return this.jJ;
    }

    int getY() {
        return this.jK;
    }

    int getZ() {
        return this.jL;
    }

    void setActive(boolean bl) {
        if (!this.i.compareAndSet(!bl, bl)) {
            return;
        }
        for (GameObject gameObject : this) {
            NpcInstance npcInstance;
            if (!gameObject.isNpc() || (npcInstance = (NpcInstance)gameObject).getAI().isActive() == this.isActive()) continue;
            if (this.isActive()) {
                npcInstance.getAI().startAITask();
                npcInstance.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                npcInstance.startRandomAnimation();
                continue;
            }
            if (npcInstance.getAI().isGlobalAI()) continue;
            npcInstance.getAI().stopAITask();
            npcInstance.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
            npcInstance.stopRandomAnimation();
        }
    }

    void addToPlayers(GameObject gameObject, Creature creature) {
        if (gameObject == null) {
            return;
        }
        Player player = null;
        if (gameObject.isPlayer()) {
            player = (Player)gameObject;
        }
        int n = gameObject.getObjectId();
        int n2 = gameObject.getReflectionId();
        for (GameObject gameObject2 : this) {
            if (gameObject2.getObjectId() == n || gameObject2.getReflectionId() != n2) continue;
            if (player != null) {
                player.sendPacket(player.addVisibleObject(gameObject2, null));
            }
            if (!gameObject2.isPlayer()) continue;
            Player player2 = (Player)gameObject2;
            player2.sendPacket(player2.addVisibleObject(gameObject, creature));
        }
    }

    void removeFromPlayers(GameObject gameObject) {
        if (gameObject == null) {
            return;
        }
        Player player = null;
        if (gameObject.isPlayer()) {
            player = (Player)gameObject;
        }
        int n = gameObject.getObjectId();
        Reflection reflection = gameObject.getReflection();
        List<L2GameServerPacket> list = null;
        for (GameObject gameObject2 : this) {
            if (gameObject2.getObjectId() == n || gameObject2.getReflection() != reflection) continue;
            if (player != null) {
                player.sendPacket(player.removeVisibleObject(gameObject2, null));
            }
            if (!gameObject2.isPlayer()) continue;
            Player player2 = (Player)gameObject2;
            player2.sendPacket(player2.removeVisibleObject(gameObject, list == null ? gameObject.deletePacketList() : list));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addObject(GameObject gameObject) {
        if (gameObject == null) {
            return;
        }
        this.u.lock();
        try {
            GameObject[] gameObjectArray = this.b;
            GameObject[] gameObjectArray2 = new GameObject[this.jM + 1];
            System.arraycopy(gameObjectArray, 0, gameObjectArray2, 0, this.jM);
            gameObjectArray = gameObjectArray2;
            gameObjectArray[this.jM++] = gameObject;
            this.b = gameObjectArray2;
            if (gameObject.isPlayer() && this.jN++ == 0) {
                if (this.B != null) {
                    this.B.cancel(false);
                }
                this.B = ThreadPoolManager.getInstance().schedule(new ActivateTask(true), 1000L);
            }
        }
        finally {
            this.u.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeObject(GameObject gameObject) {
        if (gameObject == null) {
            return;
        }
        this.u.lock();
        try {
            GameObject[] gameObjectArray = this.b;
            int n = -1;
            for (int i = 0; i < this.jM; ++i) {
                if (gameObjectArray[i] != gameObject) continue;
                n = i;
                break;
            }
            if (n == -1) {
                return;
            }
            --this.jM;
            GameObject[] gameObjectArray2 = new GameObject[this.jM];
            gameObjectArray[n] = gameObjectArray[this.jM];
            System.arraycopy(gameObjectArray, 0, gameObjectArray2, 0, this.jM);
            this.b = gameObjectArray2;
            if (gameObject.isPlayer() && --this.jN == 0) {
                if (this.B != null) {
                    this.B.cancel(false);
                }
                this.B = ThreadPoolManager.getInstance().schedule(new ActivateTask(false), 60000L);
            }
        }
        finally {
            this.u.unlock();
        }
    }

    public int getObjectsSize() {
        return this.jM;
    }

    public int getPlayersCount() {
        return this.jN;
    }

    public boolean isEmpty() {
        return this.jN == 0;
    }

    public boolean isActive() {
        return this.i.get();
    }

    void addZone(Zone zone) {
        this.u.lock();
        try {
            this.a = ArrayUtils.add(this.a, zone);
        }
        finally {
            this.u.unlock();
        }
    }

    void removeZone(Zone zone) {
        this.u.lock();
        try {
            this.a = ArrayUtils.remove(this.a, zone);
        }
        finally {
            this.u.unlock();
        }
    }

    Zone[] getZones() {
        return this.a;
    }

    public String toString() {
        return "[" + this.jJ + ", " + this.jK + ", " + this.jL + "]";
    }

    @Override
    public Iterator<GameObject> iterator() {
        return new InternalIterator(this.b);
    }

    public class ActivateTask
    extends RunnableImpl {
        private boolean cC;

        public ActivateTask(boolean bl) {
            this.cC = bl;
        }

        @Override
        public void runImpl() throws Exception {
            if (this.cC) {
                World.activate(WorldRegion.this);
            } else {
                World.deactivate(WorldRegion.this);
            }
        }
    }

    private class InternalIterator
    implements Iterator<GameObject> {
        final GameObject[] objects;
        int cursor = 0;

        public InternalIterator(GameObject[] gameObjectArray) {
            this.objects = gameObjectArray;
        }

        @Override
        public boolean hasNext() {
            if (this.cursor < this.objects.length) {
                return this.objects[this.cursor] != null;
            }
            return false;
        }

        @Override
        public GameObject next() {
            return this.objects[this.cursor++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
