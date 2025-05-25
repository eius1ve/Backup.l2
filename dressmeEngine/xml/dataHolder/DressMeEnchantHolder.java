/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.data.xml.AbstractHolder
 */
package dressmeEngine.xml.dataHolder;

import dressmeEngine.data.DressMeEnchantData;
import java.util.ArrayList;
import java.util.List;
import l2.commons.data.xml.AbstractHolder;

public final class DressMeEnchantHolder
extends AbstractHolder {
    private static final DressMeEnchantHolder A = new DressMeEnchantHolder();
    private final List<DressMeEnchantData> B = new ArrayList<DressMeEnchantData>();

    public static DressMeEnchantHolder getInstance() {
        return A;
    }

    public void addEnchant(DressMeEnchantData dressMeEnchantData) {
        this.B.add(dressMeEnchantData);
    }

    public List<DressMeEnchantData> getAllEnchants() {
        return this.B;
    }

    public DressMeEnchantData getEnchant(int i) {
        for (DressMeEnchantData dressMeEnchantData : this.B) {
            if (dressMeEnchantData.C() != i) continue;
            return dressMeEnchantData;
        }
        return null;
    }

    public DressMeEnchantData getEnchantById(int i) {
        for (DressMeEnchantData dressMeEnchantData : this.B) {
            if (dressMeEnchantData.R() != i) continue;
            return dressMeEnchantData;
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
