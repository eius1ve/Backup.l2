/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.data.xml.AbstractHolder
 */
package dressmeEngine.xml.dataHolder;

import dressmeEngine.data.DressMeHatData;
import java.util.ArrayList;
import java.util.List;
import l2.commons.data.xml.AbstractHolder;

public final class DressMeHatHolder
extends AbstractHolder {
    private static final DressMeHatHolder A = new DressMeHatHolder();
    private final List<DressMeHatData> B = new ArrayList<DressMeHatData>();

    public static DressMeHatHolder getInstance() {
        return A;
    }

    public void addHat(DressMeHatData dressMeHatData) {
        this.B.add(dressMeHatData);
    }

    public List<DressMeHatData> getAllHats() {
        return this.B;
    }

    public DressMeHatData getHat(int i) {
        for (DressMeHatData dressMeHatData : this.B) {
            if (dressMeHatData.C() != i) continue;
            return dressMeHatData;
        }
        return null;
    }

    public DressMeHatData getHatById(int i) {
        for (DressMeHatData dressMeHatData : this.B) {
            if (dressMeHatData.F() != i) continue;
            return dressMeHatData;
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
