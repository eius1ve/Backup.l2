/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.objects;

import java.util.Set;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.entity.events.objects.SpawnableObject;
import l2.gameserver.model.instances.residences.SiegeToggleNpcInstance;
import l2.gameserver.utils.Location;

public class SiegeToggleNpcObject
implements SpawnableObject {
    private SiegeToggleNpcInstance a;
    private Location s;

    public SiegeToggleNpcObject(int n, int n2, Location location, int n3, Set<String> set) {
        this.s = location;
        this.a = (SiegeToggleNpcInstance)NpcHolder.getInstance().getTemplate(n).getNewInstance();
        this.a.initFake(n2);
        this.a.setMaxHp(n3);
        this.a.setZoneList(set);
    }

    @Override
    public void spawnObject(GlobalEvent globalEvent) {
        this.a.decayFake();
        if (globalEvent.isInProgress()) {
            this.a.addEvent(globalEvent);
        } else {
            this.a.removeEvent(globalEvent);
        }
        this.a.setCurrentHp(this.a.getMaxHp(), true);
        this.a.spawnMe(this.s);
    }

    @Override
    public void despawnObject(GlobalEvent globalEvent) {
        this.a.removeEvent(globalEvent);
        this.a.decayFake();
        this.a.decayMe();
    }

    @Override
    public void refreshObject(GlobalEvent globalEvent) {
    }

    public SiegeToggleNpcInstance getToggleNpc() {
        return this.a;
    }

    public boolean isAlive() {
        return this.a.isVisible();
    }
}
