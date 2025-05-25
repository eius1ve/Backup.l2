/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.sets.impl.HashIntSet
 */
package l2.gameserver.data.xml.holder;

import java.util.HashMap;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.templates.item.support.EnchantCatalyzer;
import l2.gameserver.templates.item.support.EnchantItem;
import l2.gameserver.templates.item.support.EnchantScroll;
import org.napile.primitive.sets.impl.HashIntSet;

public class EnchantItemHolder
extends AbstractHolder {
    private static EnchantItemHolder a = new EnchantItemHolder();
    private Map<Integer, EnchantItem> L = new HashMap<Integer, EnchantItem>();

    public static EnchantItemHolder getInstance() {
        return a;
    }

    private EnchantItemHolder() {
    }

    @Override
    public void log() {
        this.info("load " + this.L.size() + " enchant item(s).");
    }

    public EnchantItem getEnchantItem(int n) {
        return this.L.get(n);
    }

    public EnchantScroll getEnchantScroll(int n) {
        EnchantItem enchantItem = this.getEnchantItem(n);
        if (enchantItem != null && enchantItem instanceof EnchantScroll) {
            return (EnchantScroll)enchantItem;
        }
        return null;
    }

    public EnchantCatalyzer getEnchantCatalyzer(int n) {
        EnchantItem enchantItem = this.getEnchantItem(n);
        if (enchantItem != null && enchantItem instanceof EnchantCatalyzer) {
            return (EnchantCatalyzer)enchantItem;
        }
        return null;
    }

    public void addEnchantItem(EnchantItem enchantItem) {
        this.L.put(enchantItem.getItemId(), enchantItem);
    }

    public int[] getScrollIds() {
        HashIntSet hashIntSet = new HashIntSet();
        for (EnchantItem enchantItem : this.L.values()) {
            if (!(enchantItem instanceof EnchantScroll)) continue;
            hashIntSet.add(enchantItem.getItemId());
        }
        return hashIntSet.toArray(new int[hashIntSet.size()]);
    }

    @Override
    public int size() {
        return this.L.size();
    }

    @Override
    public void clear() {
        this.L.clear();
    }
}
