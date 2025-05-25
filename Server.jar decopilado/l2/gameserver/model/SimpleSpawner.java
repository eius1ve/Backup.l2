/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model;

import java.util.ArrayList;
import l2.commons.collections.MultiValueSet;
import l2.commons.util.Rnd;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.Spawner;
import l2.gameserver.model.Territory;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.templates.spawn.SpawnRange;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class SimpleSpawner
extends Spawner
implements Cloneable {
    private static final Logger bH = LoggerFactory.getLogger(SimpleSpawner.class);
    private NpcTemplate d;
    private int jp;
    private int jq;
    private int jr;
    private int gy;
    private Territory h;

    public SimpleSpawner(NpcTemplate npcTemplate) {
        if (npcTemplate == null) {
            throw new NullPointerException();
        }
        this.d = npcTemplate;
        this._spawned = new ArrayList(1);
    }

    public SimpleSpawner(int n) {
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(n);
        if (npcTemplate == null) {
            throw new NullPointerException("Not find npc: " + n);
        }
        this.d = npcTemplate;
        this._spawned = new ArrayList(1);
    }

    public int getAmount() {
        return this._maximumCount;
    }

    public int getSpawnedCount() {
        return this._currentCount.get();
    }

    public int getSheduledCount() {
        return this._scheduledCount.get();
    }

    public Territory getTerritory() {
        return this.h;
    }

    public Location getLoc() {
        return new Location(this.jp, this.jq, this.jr);
    }

    public int getLocx() {
        return this.jp;
    }

    public int getLocy() {
        return this.jq;
    }

    public int getLocz() {
        return this.jr;
    }

    @Override
    public int getCurrentNpcId() {
        return this.d.getNpcId();
    }

    @Override
    public SpawnRange getCurrentSpawnRange() {
        if (this.jp == 0 && this.jr == 0) {
            return this.h;
        }
        return this.getLoc();
    }

    public int getHeading() {
        return this.gy;
    }

    public void restoreAmount() {
        this._maximumCount = this._referenceCount;
    }

    public void setTerritory(Territory territory) {
        this.h = territory;
    }

    public void setLoc(Location location) {
        this.jp = location.x;
        this.jq = location.y;
        this.jr = location.z;
        this.gy = location.h;
    }

    public void setLocx(int n) {
        this.jp = n;
    }

    public void setLocy(int n) {
        this.jq = n;
    }

    public void setLocz(int n) {
        this.jr = n;
    }

    public void setHeading(int n) {
        this.gy = n;
    }

    @Override
    public void decreaseCount(NpcInstance npcInstance) {
        this.decreaseCount0(this.d, npcInstance, npcInstance.getDeadTime());
    }

    @Override
    public NpcInstance doSpawn(boolean bl) {
        return this.doSpawn0(this.d, bl, StatsSet.EMPTY);
    }

    @Override
    protected NpcInstance initNpc(NpcInstance npcInstance, boolean bl, MultiValueSet<String> multiValueSet) {
        Location location;
        if (this.h != null) {
            location = this.h.getRandomLoc(this._reflection.getGeoIndex());
            location.setH(Rnd.get(65535));
        } else {
            location = this.getLoc();
            location.h = this.getHeading() == -1 ? Rnd.get(65535) : this.getHeading();
        }
        return this.initNpc0(npcInstance, location, bl, multiValueSet);
    }

    @Override
    public void respawnNpc(NpcInstance npcInstance) {
        npcInstance.refreshID();
        this.initNpc(npcInstance, true, StatsSet.EMPTY);
    }

    public SimpleSpawner clone() {
        SimpleSpawner simpleSpawner = new SimpleSpawner(this.d);
        simpleSpawner.setTerritory(this.h);
        simpleSpawner.setLocx(this.jp);
        simpleSpawner.setLocy(this.jq);
        simpleSpawner.setLocz(this.jr);
        simpleSpawner.setHeading(this.gy);
        simpleSpawner.setAmount(this._maximumCount);
        simpleSpawner.setRespawnDelay(this._respawnDelay, this._respawnDelayRandom);
        simpleSpawner.setRespawnCron(this.getRespawnCron());
        return simpleSpawner;
    }
}
