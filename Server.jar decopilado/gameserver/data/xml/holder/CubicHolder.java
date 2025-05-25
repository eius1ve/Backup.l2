/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 */
package l2.gameserver.data.xml.holder;

import gnu.trove.TIntObjectHashMap;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.templates.CubicTemplate;

public final class CubicHolder
extends AbstractHolder {
    private static CubicHolder a = new CubicHolder();
    private final TIntObjectHashMap<CubicTemplate> f = new TIntObjectHashMap(10);

    public static CubicHolder getInstance() {
        return a;
    }

    private CubicHolder() {
    }

    public void addCubicTemplate(CubicTemplate cubicTemplate) {
        this.f.put(this.hash(cubicTemplate.getId(), cubicTemplate.getLevel()), (Object)cubicTemplate);
    }

    public CubicTemplate getTemplate(int n, int n2) {
        return (CubicTemplate)this.f.get(this.hash(n, n2));
    }

    public int hash(int n, int n2) {
        return n * 10000 + n2;
    }

    @Override
    public int size() {
        return this.f.size();
    }

    @Override
    public void clear() {
        this.f.clear();
    }
}
