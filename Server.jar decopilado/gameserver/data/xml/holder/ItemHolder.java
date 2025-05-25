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
import java.util.HashMap;
import l2.commons.data.xml.AbstractHolder;
import l2.commons.lang.ArrayUtils;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.item.support.Grade;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ItemHolder
extends AbstractHolder {
    private static final ItemHolder a = new ItemHolder();
    private final TIntObjectHashMap<ItemTemplate> h = new TIntObjectHashMap();
    private ItemTemplate[] a;

    public static ItemHolder getInstance() {
        return a;
    }

    private ItemHolder() {
    }

    public void addItem(ItemTemplate itemTemplate) {
        this.h.put(itemTemplate.getItemId(), (Object)itemTemplate);
    }

    private void an() {
        int n = 0;
        for (int n2 : this.h.keys()) {
            if (n2 <= n) continue;
            n = n2;
        }
        this.a = new ItemTemplate[n + 1];
        TIntObjectIterator tIntObjectIterator = this.h.iterator();
        while (tIntObjectIterator.hasNext()) {
            tIntObjectIterator.advance();
            this.a[tIntObjectIterator.key()] = (ItemTemplate)tIntObjectIterator.value();
        }
    }

    public ItemTemplate getTemplate(int n) {
        ItemTemplate itemTemplate = ArrayUtils.valid(this.a, n);
        if (itemTemplate == null) {
            this.warn("Not defined item id : " + n + ", or out of range!", new Exception());
            return null;
        }
        return this.a[n];
    }

    public ItemTemplate[] getAllTemplates() {
        return this.a;
    }

    private void ao() {
        HashMap<Grade, Long> hashMap = new HashMap<Grade, Long>();
        for (Grade object : Grade.values()) {
            if (object.getCry() <= 0) continue;
            ItemTemplate itemTemplate = this.getTemplate(object.getCry());
            hashMap.put(object, itemTemplate.getReferencePrice());
        }
        TIntObjectIterator tIntObjectIterator = this.h.iterator();
        while (tIntObjectIterator.hasNext()) {
            tIntObjectIterator.advance();
            ItemTemplate itemTemplate = (ItemTemplate)tIntObjectIterator.value();
            if (itemTemplate == null) continue;
            int n = itemTemplate.getCrystalCount();
            long l = itemTemplate.getReferencePrice();
            Grade grade = itemTemplate.getCrystalType();
            Long l2 = (Long)hashMap.get(grade);
            if (l2 == null || grade.getCry() == itemTemplate.getItemId() || n == 0 || l == 0L) continue;
            long l3 = (long)n * l2;
            if (l2 <= l) continue;
            this.warn("Reference price (" + l + ") of item \"" + itemTemplate.getItemId() + "\" lower than crystal price (" + l3 + ")");
        }
    }

    private void ap() {
        this.ao();
    }

    @Override
    protected void process() {
        this.an();
        this.ap();
    }

    @Override
    public int size() {
        return this.h.size();
    }

    @Override
    public void clear() {
        this.h.clear();
    }
}
