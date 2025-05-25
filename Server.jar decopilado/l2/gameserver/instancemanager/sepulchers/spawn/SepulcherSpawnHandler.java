/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.sepulchers.spawn;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import l2.gameserver.instancemanager.sepulchers.event.EventHandler;
import l2.gameserver.instancemanager.sepulchers.event.OnEveryoneDespawned;
import l2.gameserver.instancemanager.sepulchers.spawn.SepulcherSpawnDefine;
import l2.gameserver.model.Territory;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

public class SepulcherSpawnHandler {
    private final String cI;
    private final Territory i;
    private final List<SepulcherSpawnDefine> aJ;
    private final ConcurrentLinkedQueue<SepulcherSpawnDefine> e = new ConcurrentLinkedQueue();
    private final EventHandler a = new EventHandler();
    private volatile boolean active = false;

    public SepulcherSpawnHandler(String string, Territory territory, List<SepulcherSpawnDefine> list) {
        this.cI = string;
        this.i = territory;
        this.aJ = list;
    }

    public void spawn() {
        this.active = true;
        this.a.reset();
        this.aJ.forEach(SepulcherSpawnDefine::spawn);
    }

    public void despawn() {
        this.active = false;
        this.aJ.forEach(SepulcherSpawnDefine::despawn);
        this.e.forEach(SepulcherSpawnDefine::despawn);
        this.e.clear();
    }

    public int aliveCount() {
        return this.aJ.stream().mapToInt(sepulcherSpawnDefine -> sepulcherSpawnDefine.getAlive().size()).sum();
    }

    public Territory getTerritory() {
        return this.i;
    }

    public void onSpawn(NpcInstance npcInstance) {
    }

    public void onDespawn(NpcInstance npcInstance) {
        if (this.active && this.aliveCount() == 0) {
            this.a.trigger(OnEveryoneDespawned.class, OnEveryoneDespawned::onAction);
        }
    }

    public boolean isActive() {
        return this.active;
    }

    public void spawnCustom(int n, Location location, int n2, int n3, boolean bl) {
        SepulcherSpawnDefine sepulcherSpawnDefine = new SepulcherSpawnDefine(n, location, n2, n3, this);
        this.e.add(sepulcherSpawnDefine);
        if (bl) {
            sepulcherSpawnDefine.spawn();
        }
    }

    public void spawnCustomSingle(int n, Location location) {
        this.spawnCustom(n, location, 0, 1, true);
    }

    public List<NpcInstance> collectAlive() {
        return this.aJ.stream().flatMap(sepulcherSpawnDefine -> sepulcherSpawnDefine.getAlive().stream()).collect(Collectors.toList());
    }

    public EventHandler getEventHandler() {
        return this.a;
    }

    public String getName() {
        return this.cI;
    }
}
