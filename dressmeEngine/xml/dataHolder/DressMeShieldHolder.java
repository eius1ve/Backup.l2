/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.data.xml.AbstractHolder
 */
package dressmeEngine.xml.dataHolder;

import dressmeEngine.data.DressMeShieldData;
import java.util.ArrayList;
import java.util.List;
import l2.commons.data.xml.AbstractHolder;

public final class DressMeShieldHolder
extends AbstractHolder {
    private static final DressMeShieldHolder A = new DressMeShieldHolder();
    private final List<DressMeShieldData> B = new ArrayList<DressMeShieldData>();

    public static DressMeShieldHolder getInstance() {
        return A;
    }

    public void addShield(DressMeShieldData dressMeShieldData) {
        this.B.add(dressMeShieldData);
    }

    public List<DressMeShieldData> getAllShields() {
        return this.B;
    }

    public DressMeShieldData getShield(int i) {
        for (DressMeShieldData dressMeShieldData : this.B) {
            if (dressMeShieldData.C() != i) continue;
            return dressMeShieldData;
        }
        return null;
    }

    public DressMeShieldData getShieldByItemId(int i) {
        for (DressMeShieldData dressMeShieldData : this.B) {
            if (dressMeShieldData.W() != i) continue;
            return dressMeShieldData;
        }
        return null;
    }

    public int size() {
        return this.B.size();
    }

    public void clear() {
        this.B.clear();
    }
}
