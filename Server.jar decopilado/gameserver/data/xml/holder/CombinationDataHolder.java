/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.holder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.templates.item.support.CombinationItem;

public final class CombinationDataHolder
extends AbstractHolder {
    private static final CombinationDataHolder a = new CombinationDataHolder();
    private List<CombinationItem> ab = new ArrayList<CombinationItem>();

    public static CombinationDataHolder getInstance() {
        return a;
    }

    public void addData(CombinationItem combinationItem) {
        this.ab.add(combinationItem);
    }

    public List<CombinationItem> getDatas() {
        return Collections.unmodifiableList(this.ab);
    }

    public CombinationItem getDataBySlowOneAndTwoItemId(int n, int n2) {
        for (CombinationItem combinationItem : this.ab) {
            if ((n != combinationItem.getSlotoneId() || n2 != combinationItem.getSlottwoId()) && (n2 != combinationItem.getSlotoneId() || n != combinationItem.getSlottwoId())) continue;
            return combinationItem;
        }
        return null;
    }

    @Override
    public int size() {
        return this.ab.size();
    }

    @Override
    public void clear() {
        this.ab.clear();
    }
}
