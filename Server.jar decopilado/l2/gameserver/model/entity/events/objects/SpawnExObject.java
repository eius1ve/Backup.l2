/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.entity.events.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.model.Spawner;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.entity.events.objects.SpawnableObject;
import l2.gameserver.model.instances.NpcInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpawnExObject
implements SpawnableObject {
    private static final Logger bV = LoggerFactory.getLogger(SpawnExObject.class);
    private final List<Spawner> bC;
    private boolean q;
    private String _name;

    public SpawnExObject(String string) {
        this._name = string;
        this.bC = SpawnManager.getInstance().getSpawners(this._name);
        if (this.bC.isEmpty() && Config.ALT_DEBUG_ENABLED) {
            bV.info("SpawnExObject: not found spawn group: " + string);
        }
    }

    @Override
    public void spawnObject(GlobalEvent globalEvent) {
        if (this.q) {
            bV.info("SpawnExObject: can't spawn twice: " + this._name + "; event: " + globalEvent, (Throwable)new Exception());
        } else {
            for (Spawner spawner : this.bC) {
                if (globalEvent.isInProgress()) {
                    spawner.addEvent(globalEvent);
                } else {
                    spawner.removeEvent(globalEvent);
                }
                spawner.setReflection(globalEvent.getReflection());
                spawner.init();
            }
            this.q = true;
        }
    }

    @Override
    public void despawnObject(GlobalEvent globalEvent) {
        if (!this.q) {
            return;
        }
        this.q = false;
        for (Spawner spawner : this.bC) {
            spawner.removeEvent(globalEvent);
            spawner.deleteAll();
        }
    }

    @Override
    public void refreshObject(GlobalEvent globalEvent) {
        for (NpcInstance npcInstance : this.getAllSpawned()) {
            if (globalEvent.isInProgress()) {
                npcInstance.addEvent(globalEvent);
                continue;
            }
            npcInstance.removeEvent(globalEvent);
        }
    }

    public List<Spawner> getSpawns() {
        return this.bC;
    }

    public List<NpcInstance> getAllSpawned() {
        ArrayList arrayList = new ArrayList();
        for (Spawner spawner : this.bC) {
            arrayList.addAll(spawner.getAllSpawned());
        }
        return arrayList.isEmpty() ? Collections.emptyList() : arrayList;
    }

    public NpcInstance getFirstSpawned() {
        List<NpcInstance> list = this.getAllSpawned();
        return list.size() > 0 ? list.get(0) : null;
    }

    public boolean isSpawned() {
        return this.q;
    }
}
