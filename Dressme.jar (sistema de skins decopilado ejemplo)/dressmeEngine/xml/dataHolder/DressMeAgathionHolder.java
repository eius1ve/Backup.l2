/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.data.xml.AbstractHolder
 */
package dressmeEngine.xml.dataHolder;

import dressmeEngine.data.DressMeAgathionData;
import java.util.ArrayList;
import java.util.List;
import l2.commons.data.xml.AbstractHolder;

public final class DressMeAgathionHolder
extends AbstractHolder {
    private static final DressMeAgathionHolder A = new DressMeAgathionHolder();
    private final List<DressMeAgathionData> B = new ArrayList<DressMeAgathionData>();

    public static DressMeAgathionHolder getInstance() {
        return A;
    }

    public void addAgathion(DressMeAgathionData dressMeAgathionData) {
        this.B.add(dressMeAgathionData);
    }

    public List<DressMeAgathionData> getAllAgathions() {
        return this.B;
    }

    public DressMeAgathionData getAgathion(int i) {
        for (DressMeAgathionData dressMeAgathionData : this.B) {
            if (dressMeAgathionData.C() != i) continue;
            return dressMeAgathionData;
        }
        return null;
    }

    public DressMeAgathionData getAgathionById(int i) {
        for (DressMeAgathionData dressMeAgathionData : this.B) {
            if (dressMeAgathionData.O() != i) continue;
            return dressMeAgathionData;
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
