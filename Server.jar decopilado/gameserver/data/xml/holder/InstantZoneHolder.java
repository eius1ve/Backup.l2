/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.HashIntObjectMap
 */
package l2.gameserver.data.xml.holder;

import java.util.ArrayList;
import java.util.List;
import l2.commons.data.xml.AbstractHolder;
import l2.commons.time.cron.SchedulingPattern;
import l2.gameserver.templates.InstantZone;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.HashIntObjectMap;

public class InstantZoneHolder
extends AbstractHolder {
    private static final InstantZoneHolder a = new InstantZoneHolder();
    private IntObjectMap<InstantZone> d = new HashIntObjectMap();

    public static InstantZoneHolder getInstance() {
        return a;
    }

    public void addInstantZone(InstantZone instantZone) {
        this.d.put(instantZone.getId(), (Object)instantZone);
    }

    public InstantZone getInstantZone(int n) {
        return (InstantZone)this.d.get(n);
    }

    public SchedulingPattern getResetReuseById(int n) {
        InstantZone instantZone = this.getInstantZone(n);
        return instantZone == null ? null : instantZone.getResetReuse();
    }

    public List<Integer> getSharedReuseInstanceIds(int n) {
        if (this.getInstantZone(n).getSharedReuseGroup() < 1) {
            return null;
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (InstantZone instantZone : this.d.values()) {
            if (instantZone.getSharedReuseGroup() <= 0 || this.getInstantZone(n).getSharedReuseGroup() <= 0 || instantZone.getSharedReuseGroup() != this.getInstantZone(n).getSharedReuseGroup()) continue;
            arrayList.add(instantZone.getId());
        }
        return arrayList;
    }

    public List<Integer> getSharedReuseInstanceIdsByGroup(int n) {
        if (n < 1) {
            return null;
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (InstantZone instantZone : this.d.values()) {
            if (instantZone.getSharedReuseGroup() <= 0 || instantZone.getSharedReuseGroup() != n) continue;
            arrayList.add(instantZone.getId());
        }
        return arrayList;
    }

    @Override
    public int size() {
        return this.d.size();
    }

    @Override
    public void clear() {
        this.d.clear();
    }
}
