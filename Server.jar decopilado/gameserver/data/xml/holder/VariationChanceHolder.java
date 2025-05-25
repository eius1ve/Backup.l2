/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.data.xml.holder;

import java.util.HashMap;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.templates.item.support.VariationChanceData;
import org.apache.commons.lang3.tuple.Pair;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class VariationChanceHolder
extends AbstractHolder {
    private static final VariationChanceHolder a = new VariationChanceHolder();
    private HashMap<Integer, Pair<VariationChanceData, VariationChanceData>> a = new HashMap();

    public static VariationChanceHolder getInstance() {
        return a;
    }

    private VariationChanceHolder() {
    }

    @Override
    public int size() {
        return ((HashMap)((Object)this.a)).size();
    }

    @Override
    public void clear() {
        ((HashMap)((Object)this.a)).clear();
    }

    public void add(Pair<VariationChanceData, VariationChanceData> pair) {
        if (pair.getLeft() != null && pair.getRight() != null && ((VariationChanceData)pair.getLeft()).getMineralItemId() == ((VariationChanceData)pair.getRight()).getMineralItemId()) {
            ((HashMap)((Object)this.a)).put(((VariationChanceData)pair.getLeft()).getMineralItemId(), pair);
        } else if (pair.getLeft() != null) {
            ((HashMap)((Object)this.a)).put(((VariationChanceData)pair.getLeft()).getMineralItemId(), pair);
        } else if (pair.getRight() != null) {
            ((HashMap)((Object)this.a)).put(((VariationChanceData)pair.getRight()).getMineralItemId(), pair);
        } else {
            throw new RuntimeException("Empty mineral");
        }
    }

    public Pair<VariationChanceData, VariationChanceData> getVariationChanceDataForMineral(int n) {
        return (Pair)((HashMap)((Object)this.a)).get(n);
    }
}
