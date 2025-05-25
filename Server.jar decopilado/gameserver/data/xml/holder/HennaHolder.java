/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 *  gnu.trove.TIntObjectIterator
 */
package l2.gameserver.data.xml.holder;

import gnu.trove.TIntObjectHashMap;
import gnu.trove.TIntObjectIterator;
import java.util.ArrayList;
import java.util.List;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.model.Player;
import l2.gameserver.templates.Henna;

public final class HennaHolder
extends AbstractHolder {
    private static final HennaHolder a = new HennaHolder();
    private TIntObjectHashMap<Henna> g = new TIntObjectHashMap();

    public static HennaHolder getInstance() {
        return a;
    }

    public void addHenna(Henna henna) {
        this.g.put(henna.getSymbolId(), (Object)henna);
    }

    public Henna getHenna(int n) {
        return (Henna)this.g.get(n);
    }

    public List<Henna> generateList(Player player) {
        ArrayList<Henna> arrayList = new ArrayList<Henna>();
        TIntObjectIterator tIntObjectIterator = this.g.iterator();
        while (tIntObjectIterator.hasNext()) {
            tIntObjectIterator.advance();
            Henna henna = (Henna)tIntObjectIterator.value();
            if (!henna.isForThisClass(player)) continue;
            arrayList.add(henna);
        }
        return arrayList;
    }

    @Override
    public int size() {
        return this.g.size();
    }

    @Override
    public void clear() {
        this.g.clear();
    }
}
