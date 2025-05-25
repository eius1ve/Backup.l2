/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.objects;

import l2.gameserver.data.xml.holder.StaticObjectHolder;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.entity.events.objects.SpawnableObject;
import l2.gameserver.model.instances.StaticObjectInstance;

public class StaticObjectObject
implements SpawnableObject {
    private int lz;
    private StaticObjectInstance b;

    public StaticObjectObject(int n) {
        this.lz = n;
    }

    @Override
    public void spawnObject(GlobalEvent globalEvent) {
        this.b = StaticObjectHolder.getInstance().getObject(this.lz);
    }

    @Override
    public void despawnObject(GlobalEvent globalEvent) {
    }

    @Override
    public void refreshObject(GlobalEvent globalEvent) {
        if (!globalEvent.isInProgress()) {
            this.b.removeEvent(globalEvent);
        } else {
            this.b.addEvent(globalEvent);
        }
    }

    public void setMeshIndex(int n) {
        this.b.setMeshIndex(n);
        this.b.broadcastInfo(false);
    }

    public int getUId() {
        return this.lz;
    }
}
