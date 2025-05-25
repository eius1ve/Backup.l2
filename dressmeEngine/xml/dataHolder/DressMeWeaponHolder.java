/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.data.xml.AbstractHolder
 */
package dressmeEngine.xml.dataHolder;

import dressmeEngine.data.DressMeWeaponData;
import java.util.ArrayList;
import java.util.List;
import l2.commons.data.xml.AbstractHolder;

public final class DressMeWeaponHolder
extends AbstractHolder {
    private static final DressMeWeaponHolder A = new DressMeWeaponHolder();
    private final List<DressMeWeaponData> B = new ArrayList<DressMeWeaponData>();

    public static DressMeWeaponHolder getInstance() {
        return A;
    }

    public void addWeapon(DressMeWeaponData dressMeWeaponData) {
        this.B.add(dressMeWeaponData);
    }

    public List<DressMeWeaponData> getAllWeapons() {
        return this.B;
    }

    public DressMeWeaponData getWeapon(int i) {
        for (DressMeWeaponData dressMeWeaponData : this.B) {
            if (dressMeWeaponData.C() != i) continue;
            return dressMeWeaponData;
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
