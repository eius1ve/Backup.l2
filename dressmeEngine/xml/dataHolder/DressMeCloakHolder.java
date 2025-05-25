/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.data.xml.AbstractHolder
 */
package dressmeEngine.xml.dataHolder;

import dressmeEngine.data.DressMeCloakData;
import java.util.ArrayList;
import java.util.List;
import l2.commons.data.xml.AbstractHolder;

public final class DressMeCloakHolder
extends AbstractHolder {
    private static final DressMeCloakHolder A = new DressMeCloakHolder();
    private final List<DressMeCloakData> B = new ArrayList<DressMeCloakData>();

    public static DressMeCloakHolder getInstance() {
        return A;
    }

    public void addCloak(DressMeCloakData dressMeCloakData) {
        this.B.add(dressMeCloakData);
    }

    public List<DressMeCloakData> getAllCloaks() {
        return this.B;
    }

    public DressMeCloakData getCloak(int i) {
        for (DressMeCloakData dressMeCloakData : this.B) {
            if (dressMeCloakData.C() != i) continue;
            return dressMeCloakData;
        }
        return null;
    }

    public DressMeCloakData getCloakByItemId(int i) {
        for (DressMeCloakData dressMeCloakData : this.B) {
            if (dressMeCloakData.P() != i) continue;
            return dressMeCloakData;
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
