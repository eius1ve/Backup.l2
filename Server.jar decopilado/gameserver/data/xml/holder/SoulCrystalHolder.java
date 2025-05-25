/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 */
package l2.gameserver.data.xml.holder;

import gnu.trove.TIntObjectHashMap;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.templates.SoulCrystal;

public final class SoulCrystalHolder
extends AbstractHolder {
    private static final SoulCrystalHolder a = new SoulCrystalHolder();
    private final TIntObjectHashMap<SoulCrystal> q = new TIntObjectHashMap();

    public static SoulCrystalHolder getInstance() {
        return a;
    }

    public void addCrystal(SoulCrystal soulCrystal) {
        this.q.put(soulCrystal.getItemId(), (Object)soulCrystal);
    }

    public SoulCrystal getCrystal(int n) {
        return (SoulCrystal)this.q.get(n);
    }

    public SoulCrystal[] getCrystals() {
        return (SoulCrystal[])this.q.getValues((Object[])new SoulCrystal[this.q.size()]);
    }

    @Override
    public int size() {
        return this.q.size();
    }

    @Override
    public void clear() {
        this.q.clear();
    }
}
