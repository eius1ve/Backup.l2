/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.TreeIntObjectMap
 */
package l2.gameserver.data.xml.holder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.entity.residence.Residence;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.TreeIntObjectMap;

public final class ResidenceHolder
extends AbstractHolder {
    private static ResidenceHolder a = new ResidenceHolder();
    private IntObjectMap<Residence> f = new TreeIntObjectMap();
    private Map<Class, List<Residence>> aa = new HashMap<Class, List<Residence>>(4);

    public static ResidenceHolder getInstance() {
        return a;
    }

    private ResidenceHolder() {
    }

    public void addResidence(Residence residence) {
        this.f.put(residence.getId(), (Object)residence);
    }

    public <R extends Residence> R getResidence(int n) {
        return (R)((Residence)this.f.get(n));
    }

    public <R extends Residence> R getResidence(Class<R> clazz, int n) {
        R r = this.getResidence(n);
        if (r == null || r.getClass() != clazz) {
            return null;
        }
        return r;
    }

    public <R extends Residence> List<R> getResidenceList(Class<R> clazz) {
        return this.aa.get(clazz);
    }

    public Collection<Residence> getResidences() {
        return this.f.values();
    }

    public <R extends Residence> R getResidenceByObject(Class<? extends Residence> clazz, GameObject gameObject) {
        return (R)this.getResidenceByCoord(clazz, gameObject.getX(), gameObject.getY(), gameObject.getZ(), gameObject.getReflection());
    }

    public <R extends Residence> R getResidenceByCoord(Class<R> clazz, int n, int n2, int n3, Reflection reflection) {
        Collection<Residence> collection = clazz == null ? this.getResidences() : this.getResidenceList(clazz);
        for (Residence residence : collection) {
            if (!residence.checkIfInZone(n, n2, n3, reflection)) continue;
            return (R)residence;
        }
        return null;
    }

    public <R extends Residence> R findNearestResidence(Class<R> clazz, int n, int n2, int n3, Reflection reflection, int n4) {
        Object object = this.getResidenceByCoord(clazz, n, n2, n3, reflection);
        if (object == null) {
            double d = n4;
            for (Residence residence : this.getResidenceList(clazz)) {
                double d2 = residence.getZone().findDistanceToZone(n, n2, n3, false);
                if (!(d > d2)) continue;
                d = d2;
                object = residence;
            }
        }
        return object;
    }

    public void callInit() {
        for (Residence residence : this.getResidences()) {
            residence.init();
        }
    }

    private void ar() {
        for (Residence residence : this.f.values()) {
            List<Residence> list = this.aa.get(residence.getClass());
            if (list == null) {
                list = new ArrayList<Residence>();
                this.aa.put(residence.getClass(), list);
            }
            list.add(residence);
        }
    }

    @Override
    public void log() {
        this.ar();
        this.info("total size: " + this.f.size());
        for (Map.Entry<Class, List<Residence>> entry : this.aa.entrySet()) {
            this.info(" - load " + entry.getValue().size() + " " + entry.getKey().getSimpleName().toLowerCase() + "(s).");
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {
        this.f.clear();
        this.aa.clear();
    }
}
