/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.objects;

import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.entity.events.objects.InitableObject;
import l2.gameserver.model.entity.events.objects.SpawnableObject;
import l2.gameserver.model.instances.DoorInstance;

public class DoorObject
implements InitableObject,
SpawnableObject {
    private int _id;
    private DoorInstance b;
    private boolean dl;

    public DoorObject(int n) {
        this._id = n;
    }

    @Override
    public void initObject(GlobalEvent globalEvent) {
        this.b = globalEvent.getReflection().getDoor(this._id);
    }

    @Override
    public void spawnObject(GlobalEvent globalEvent) {
        this.refreshObject(globalEvent);
    }

    @Override
    public void despawnObject(GlobalEvent globalEvent) {
        Reflection reflection = globalEvent.getReflection();
        if (reflection == ReflectionManager.DEFAULT) {
            this.refreshObject(globalEvent);
        }
    }

    @Override
    public void refreshObject(GlobalEvent globalEvent) {
        if (!globalEvent.isInProgress()) {
            this.b.removeEvent(globalEvent);
        } else {
            this.b.addEvent(globalEvent);
        }
        if (this.b.getCurrentHp() <= 0.0) {
            this.b.decayMe();
            this.b.spawnMe();
        }
        this.b.setCurrentHp((double)this.b.getMaxHp() * (this.isWeak() ? 0.5 : 1.0), true);
        this.close(globalEvent);
    }

    public int getUId() {
        return this.b.getDoorId();
    }

    public int getUpgradeValue() {
        return this.b.getUpgradeHp();
    }

    public void setUpgradeValue(GlobalEvent globalEvent, int n) {
        this.b.setUpgradeHp(n);
        this.refreshObject(globalEvent);
    }

    public void open(GlobalEvent globalEvent) {
        this.b.openMe(null, !globalEvent.isInProgress());
    }

    public void close(GlobalEvent globalEvent) {
        this.b.closeMe(null, !globalEvent.isInProgress());
    }

    public DoorInstance getDoor() {
        return this.b;
    }

    public boolean isWeak() {
        return this.dl;
    }

    public void setWeak(boolean bl) {
        this.dl = bl;
    }
}
