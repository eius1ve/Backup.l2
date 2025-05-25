/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.data.xml.AbstractHolder
 */
package dressmeEngine.xml.dataHolder;

import dressmeEngine.data.DressMeArmorData;
import java.util.ArrayList;
import java.util.List;
import l2.commons.data.xml.AbstractHolder;

public final class DressMeArmorHolder
extends AbstractHolder {
    private static final DressMeArmorHolder A = new DressMeArmorHolder();
    private final List<DressMeArmorData> B = new ArrayList<DressMeArmorData>();

    public static DressMeArmorHolder getInstance() {
        return A;
    }

    public void addDress(DressMeArmorData dressMeArmorData) {
        this.B.add(dressMeArmorData);
    }

    public List<DressMeArmorData> getAllDress() {
        return this.B;
    }

    public DressMeArmorData getArmor(int i) {
        for (DressMeArmorData dressMeArmorData : this.B) {
            if (dressMeArmorData.C() != i) continue;
            return dressMeArmorData;
        }
        return null;
    }

    public DressMeArmorData getArmorByPartId(int r4) {
        throw new UnsupportedOperationException("Method not decompiled: dressmeEngine.xml.dataHolder.DressMeArmorHolder.getArmorByPartId(int):dressmeEngine.data.DressMeArmorData");
    }

    public int size() {
        return this.B.size();
    }

    public void clear() {
        this.B.clear();
    }
}
