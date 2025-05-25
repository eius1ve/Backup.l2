/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.instancemanager.sepulchers.spawn;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.instancemanager.sepulchers.spawn.ISepulcherSpawnProperty;
import l2.gameserver.instancemanager.sepulchers.spawn.SepulcherSpawnHandler;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SepulcherSpawnDefine {
    protected static final Logger log = LoggerFactory.getLogger(SepulcherSpawnDefine.class);
    private final Location r;
    private final int fT;
    private final int fU;
    private final SepulcherSpawnHandler c;
    private final NpcTemplate c;
    private final ConcurrentLinkedQueue<ScheduledFuture<?>> c = new ConcurrentLinkedQueue();
    private final ConcurrentLinkedQueue<NpcInstance> d = new ConcurrentLinkedQueue();

    public SepulcherSpawnDefine(int n, Location location, int n2, int n3, SepulcherSpawnHandler sepulcherSpawnHandler) {
        this.r = location;
        this.fT = n2;
        this.fU = n3;
        this.c = sepulcherSpawnHandler;
        this.c = NpcHolder.getInstance().getTemplate(n);
    }

    public void spawn() {
        for (int i = 0; i < this.fU; ++i) {
            this.aP();
        }
    }

    public void despawn() {
        ((ConcurrentLinkedQueue)((Object)this.c)).forEach(scheduledFuture -> scheduledFuture.cancel(false));
        this.d.forEach(GameObject::deleteMe);
        ((ConcurrentLinkedQueue)((Object)this.c)).clear();
        this.d.clear();
    }

    private Location d() {
        return this.r == null ? this.c.getTerritory().getRandomLoc(ReflectionManager.DEFAULT.getGeoIndex()) : this.r;
    }

    private NpcInstance d() {
        NpcInstance npcInstance = this.c.getNewInstance();
        if (npcInstance == null) {
            log.warn("Failed to spawn " + this.c);
            return null;
        }
        if (!ISepulcherSpawnProperty.class.isAssignableFrom(npcInstance.getClass())) {
            log.warn("Incorrect npc sepulcher instance! " + npcInstance);
            return null;
        }
        npcInstance.setReflection(0);
        npcInstance.setCurrentHpMp(npcInstance.getMaxHp(), npcInstance.getMaxMp(), true);
        ((ISepulcherSpawnProperty)((Object)npcInstance)).setSepulcherSpawnDefine(this);
        return npcInstance;
    }

    private void aP() {
        Location location = this.d();
        if (location == null) {
            log.warn("Null spawn pos " + this);
            return;
        }
        NpcInstance npcInstance = this.d();
        if (npcInstance == null) {
            return;
        }
        npcInstance.setSpawnedLoc(location);
        npcInstance.spawnMe(location);
        this.d.add(npcInstance);
        this.c.onSpawn(npcInstance);
    }

    private void aQ() {
        ((ConcurrentLinkedQueue)((Object)this.c)).add(ThreadPoolManager.getInstance().schedule(() -> {
            if (this.c.isActive()) {
                this.aP();
            }
            ((ConcurrentLinkedQueue)((Object)this.c)).removeIf(Future::isDone);
        }, this.fT * 1000));
    }

    public void notifyDespawn(NpcInstance npcInstance) {
        this.d.remove(npcInstance);
        this.c.onDespawn(npcInstance);
        if (this.fT > 0 && this.c.isActive()) {
            this.aQ();
        }
    }

    public ConcurrentLinkedQueue<NpcInstance> getAlive() {
        return this.d;
    }

    public SepulcherSpawnHandler getHandler() {
        return this.c;
    }
}
