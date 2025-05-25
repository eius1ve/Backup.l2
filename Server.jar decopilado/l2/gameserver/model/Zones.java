/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.model;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import l2.commons.collections.LazyArrayList;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Zone;
import org.apache.commons.lang3.ArrayUtils;

public class Zones
implements Iterable<Zone> {
    private final AtomicReference<Zone[]> b = new AtomicReference<Zone[]>(Zone.EMPTY_L2ZONE_ARRAY);

    @Override
    public Iterator<Zone> iterator() {
        return new Iterator<Zone>(){
            private final Zone[] b;
            private int cursor;
            {
                this.b = Zones.this.b.get();
                this.cursor = 0;
            }

            @Override
            public boolean hasNext() {
                return this.cursor < this.b.length;
            }

            @Override
            public Zone next() {
                try {
                    return this.b[this.cursor++];
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    public Stream<Zone> stream() {
        return Stream.of(this.b.get());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean update(Creature creature) {
        Object object;
        int n;
        Object[] objectArray;
        Object[] objectArray2 = creature.isVisible() ? creature.getCurrentRegion().getZones() : Zone.EMPTY_L2ZONE_ARRAY;
        Object[] objectArray3 = objectArray = this.b.get();
        if (objectArray.length > 0) {
            for (n = 0; n < objectArray.length; ++n) {
                object = objectArray[n];
                if (ArrayUtils.contains((Object[])objectArray2, (Object)object) && ((Zone)object).checkIfInZone(creature.getLoc(), creature.getReflection())) continue;
                objectArray3 = (Zone[])ArrayUtils.removeElement((Object[])objectArray3, (Object)object);
            }
        }
        if (objectArray2.length > 0) {
            for (n = 0; n < objectArray2.length; ++n) {
                object = objectArray2[n];
                if (ArrayUtils.contains((Object[])objectArray, (Object)object) || !((Zone)object).checkIfInZone(creature.getLoc(), creature.getReflection())) continue;
                objectArray3 = (Zone[])ArrayUtils.add((Object[])objectArray3, (Object)object);
            }
        }
        if (objectArray != objectArray3 && this.b.compareAndSet((Zone[])objectArray, (Zone[])objectArray3)) {
            LazyArrayList<Zone> lazyArrayList = LazyArrayList.newInstance();
            LazyArrayList<Zone> lazyArrayList2 = LazyArrayList.newInstance();
            try {
                int n2;
                for (n2 = 0; n2 < objectArray.length; ++n2) {
                    object = objectArray[n2];
                    if (ArrayUtils.contains((Object[])objectArray3, (Object)object)) continue;
                    lazyArrayList.add((Zone)object);
                }
                for (n2 = 0; n2 < objectArray3.length; ++n2) {
                    object = objectArray3[n2];
                    if (ArrayUtils.contains((Object[])objectArray, (Object)object)) continue;
                    lazyArrayList2.add((Zone)object);
                }
                creature.onUpdateZones(lazyArrayList, lazyArrayList2);
            }
            finally {
                LazyArrayList.recycle(lazyArrayList);
                LazyArrayList.recycle(lazyArrayList2);
            }
            return true;
        }
        return false;
    }

    public boolean isInZone(Zone.ZoneType zoneType) {
        for (Zone zone : this) {
            if (!zone.isType(zoneType)) continue;
            return true;
        }
        return false;
    }

    public boolean isInZone(String string) {
        for (Zone zone : this) {
            if (!zone.getName().equals(string)) continue;
            return true;
        }
        return false;
    }

    public boolean isInZone(Zone zone) {
        return ArrayUtils.contains((Object[])this.b.get(), (Object)zone);
    }

    public Zone getZone(Zone.ZoneType zoneType) {
        for (Zone zone : this) {
            if (!zone.isType(zoneType)) continue;
            return zone;
        }
        return null;
    }

    public boolean isInAnyZone(Zone.ZoneType ... zoneTypeArray) {
        for (Zone zone : this) {
            if (!zone.isAnyType(zoneTypeArray)) continue;
            return true;
        }
        return false;
    }

    public int getWaterZ() {
        int n = Integer.MIN_VALUE;
        for (Zone zone : this) {
            if (!zone.isType(Zone.ZoneType.water) || n != Integer.MIN_VALUE && n >= zone.getTerritory().getZmax()) continue;
            n = zone.getTerritory().getZmax();
        }
        return n;
    }
}
