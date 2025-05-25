/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.ImmutablePair
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.data.xml.holder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.templates.item.support.VariationGroupData;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class VariationGroupHolder
extends AbstractHolder {
    private static final VariationGroupHolder a = new VariationGroupHolder();
    private List<Pair<int[], VariationGroupData>> an = new ArrayList<Pair<int[], VariationGroupData>>();

    public static VariationGroupHolder getInstance() {
        return a;
    }

    private VariationGroupHolder() {
    }

    @Override
    public int size() {
        return this.an.size();
    }

    @Override
    public void clear() {
        this.an.clear();
    }

    public void add(int[] nArray, VariationGroupData variationGroupData) {
        int[] nArray2 = (int[])nArray.clone();
        Arrays.sort(nArray2);
        this.an.add((Pair<int[], VariationGroupData>)new ImmutablePair((Object)nArray2, (Object)variationGroupData));
    }

    public void addSorted(int[] nArray, VariationGroupData variationGroupData) {
        this.an.add((Pair<int[], VariationGroupData>)new ImmutablePair((Object)nArray, (Object)variationGroupData));
    }

    public List<VariationGroupData> getDataForItemId(int n) {
        ArrayList<VariationGroupData> arrayList = new ArrayList<VariationGroupData>();
        for (Pair<int[], VariationGroupData> pair : this.an) {
            int[] nArray = (int[])pair.getLeft();
            if (Arrays.binarySearch(nArray, n) < 0) continue;
            arrayList.add((VariationGroupData)pair.getRight());
        }
        return arrayList;
    }
}
