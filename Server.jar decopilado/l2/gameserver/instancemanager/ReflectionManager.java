/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 */
package l2.gameserver.instancemanager;

import gnu.trove.TIntObjectHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import l2.gameserver.data.xml.holder.DoorHolder;
import l2.gameserver.data.xml.holder.InstantZoneHolder;
import l2.gameserver.data.xml.holder.ZoneHolder;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.utils.Location;

public class ReflectionManager {
    public static final Reflection DEFAULT = Reflection.createReflection(0);
    public static final Reflection GIRAN_HARBOR = Reflection.createReflection(-1);
    public static final Reflection JAIL = Reflection.createReflection(-2);
    private static final ReflectionManager a = new ReflectionManager();
    private final TIntObjectHashMap<Reflection> u = new TIntObjectHashMap();
    private final ReadWriteLock d = new ReentrantReadWriteLock();
    private final Lock l = this.d.readLock();
    private final Lock m = this.d.writeLock();

    public static ReflectionManager getInstance() {
        return a;
    }

    private ReflectionManager() {
        this.add(DEFAULT);
        this.add(GIRAN_HARBOR);
        this.add(JAIL);
        DEFAULT.init(DoorHolder.getInstance().getDoors(), ZoneHolder.getInstance().getZones());
        GIRAN_HARBOR.fillSpawns(InstantZoneHolder.getInstance().getInstantZone(10).getSpawnsInfo());
        JAIL.setCoreLoc(new Location(-114648, -249384, -2984));
    }

    public Reflection get(int n) {
        this.l.lock();
        try {
            Reflection reflection = (Reflection)this.u.get(n);
            return reflection;
        }
        finally {
            this.l.unlock();
        }
    }

    public Reflection add(Reflection reflection) {
        this.m.lock();
        try {
            Reflection reflection2 = (Reflection)this.u.put(reflection.getId(), (Object)reflection);
            return reflection2;
        }
        finally {
            this.m.unlock();
        }
    }

    public Reflection remove(Reflection reflection) {
        this.m.lock();
        try {
            Reflection reflection2 = (Reflection)this.u.remove(reflection.getId());
            return reflection2;
        }
        finally {
            this.m.unlock();
        }
    }

    public Reflection[] getAll() {
        this.l.lock();
        try {
            Reflection[] reflectionArray = (Reflection[])this.u.getValues((Object[])new Reflection[this.u.size()]);
            return reflectionArray;
        }
        finally {
            this.l.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getCountByIzId(int n) {
        this.l.lock();
        try {
            int n2 = 0;
            for (Reflection reflection : this.getAll()) {
                if (reflection.getInstancedZoneId() != n) continue;
                ++n2;
            }
            int n3 = n2;
            return n3;
        }
        finally {
            this.l.unlock();
        }
    }

    public int size() {
        return this.u.size();
    }
}
