/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.CHashIntObjectMap
 */
package l2.gameserver.model;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import l2.commons.collections.MultiValueSet;
import l2.gameserver.model.Spawner;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.spawn.SpawnNpcInfo;
import l2.gameserver.templates.spawn.SpawnRange;
import l2.gameserver.templates.spawn.SpawnTemplate;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.CHashIntObjectMap;

public class HardSpawner
extends Spawner {
    private IntObjectMap<Queue<NpcInstance>> i = new CHashIntObjectMap();
    private final SpawnTemplate a;
    private int hj;
    private int hk;
    private List<NpcInstance> aR = new CopyOnWriteArrayList<NpcInstance>();

    public HardSpawner(SpawnTemplate spawnTemplate) {
        this.a = spawnTemplate;
        this._spawned = new CopyOnWriteArrayList();
    }

    @Override
    public void decreaseCount(NpcInstance npcInstance) {
        this.f(npcInstance);
        this._spawned.remove(npcInstance);
        SpawnNpcInfo spawnNpcInfo = this.a();
        NpcInstance npcInstance2 = this.b(spawnNpcInfo.getTemplate().getNpcId());
        if (npcInstance2 == null) {
            npcInstance2 = spawnNpcInfo.getTemplate().getNewInstance();
        } else {
            npcInstance2.refreshID();
        }
        npcInstance2.setSpawn(this);
        this.aR.add(npcInstance2);
        this.decreaseCount0(spawnNpcInfo.getTemplate(), npcInstance2, npcInstance.getDeadTime());
    }

    @Override
    public NpcInstance doSpawn(boolean bl) {
        SpawnNpcInfo spawnNpcInfo = this.a();
        return this.doSpawn0(spawnNpcInfo.getTemplate(), bl, spawnNpcInfo.getParameters());
    }

    @Override
    protected NpcInstance initNpc(NpcInstance npcInstance, boolean bl, MultiValueSet<String> multiValueSet) {
        this.aR.remove(npcInstance);
        SpawnRange spawnRange = this.a.getSpawnRange(this.t());
        npcInstance.setSpawnRange(spawnRange);
        return this.initNpc0(npcInstance, spawnRange.getRandomLoc(this.getReflection().getGeoIndex()), bl, multiValueSet);
    }

    @Override
    public int getCurrentNpcId() {
        SpawnNpcInfo spawnNpcInfo = this.a.getNpcId(this.hk);
        return spawnNpcInfo.getTemplate().npcId;
    }

    @Override
    public SpawnRange getCurrentSpawnRange() {
        return this.a.getSpawnRange(this.hj);
    }

    @Override
    public void respawnNpc(NpcInstance npcInstance) {
        this.initNpc(npcInstance, true, StatsSet.EMPTY);
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
        for (NpcInstance object : this.aR) {
            this.f(object);
        }
        this.aR.clear();
        for (Collection collection : this.i.values()) {
            collection.clear();
        }
        this.i.clear();
    }

    private synchronized SpawnNpcInfo a() {
        SpawnNpcInfo spawnNpcInfo = null;
        int n = 0;
        int n2 = this.hk++;
        if (this.hk >= this.a.getNpcSize()) {
            this.hk = 0;
        }
        if ((spawnNpcInfo = this.a.getNpcId(n2)).getMax() > 0) {
            int n3 = 0;
            for (NpcInstance npcInstance : this._spawned) {
                if (npcInstance.getNpcId() != spawnNpcInfo.getTemplate().getNpcId()) continue;
                ++n3;
            }
            if (n3 >= spawnNpcInfo.getMax() && n++ > this.a.getNpcSize() * 2) {
                throw new IllegalStateException("getNextNpcInfo failed (" + n3 + ", " + spawnNpcInfo.getMax() + ", " + spawnNpcInfo.getNpcId() + ")");
            }
        }
        return spawnNpcInfo;
    }

    private synchronized int t() {
        int n = this.hj++;
        if (this.hj >= this.a.getSpawnRangeSize()) {
            this.hj = 0;
        }
        return n;
    }

    public HardSpawner clone() {
        HardSpawner hardSpawner = new HardSpawner(this.a);
        hardSpawner.setAmount(this._maximumCount);
        hardSpawner.setRespawnDelay(this._respawnDelay, this._respawnDelayRandom);
        hardSpawner.setRespawnTime(0);
        hardSpawner.setRespawnCron(this.getRespawnCron());
        return hardSpawner;
    }

    private void f(NpcInstance npcInstance) {
        npcInstance.setSpawn(null);
        npcInstance.decayMe();
        ArrayDeque<NpcInstance> arrayDeque = (ArrayDeque<NpcInstance>)this.i.get(npcInstance.getNpcId());
        if (arrayDeque == null) {
            arrayDeque = new ArrayDeque<NpcInstance>();
            this.i.put(npcInstance.getNpcId(), arrayDeque);
        }
        arrayDeque.add(npcInstance);
    }

    private NpcInstance b(int n) {
        Queue queue = (Queue)this.i.get(n);
        if (queue == null) {
            return null;
        }
        NpcInstance npcInstance = (NpcInstance)queue.poll();
        if (npcInstance != null && npcInstance.isDeleted()) {
            _log.info("Npc: " + n + " is deleted, cant used by cache.");
            return this.b(n);
        }
        return npcInstance;
    }

    public SpawnTemplate getTemplate() {
        return this.a;
    }
}
