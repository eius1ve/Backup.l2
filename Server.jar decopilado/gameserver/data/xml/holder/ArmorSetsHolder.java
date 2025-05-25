/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.holder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.model.ArmorSet;
import l2.gameserver.templates.item.ItemTemplate;

public final class ArmorSetsHolder
extends AbstractHolder {
    private static final ArmorSetsHolder a = new ArmorSetsHolder();
    private List<ArmorSet> Z = new ArrayList<ArmorSet>();
    private Map<Integer, ArmorSet> E = new HashMap<Integer, ArmorSet>();

    public static ArmorSetsHolder getInstance() {
        return a;
    }

    public void addArmorSet(ArmorSet armorSet) {
        this.Z.add(armorSet);
        for (ItemTemplate itemTemplate : armorSet.getChestItems()) {
            this.E.put(itemTemplate.getItemId(), armorSet);
        }
    }

    public ArmorSet getArmorSetByChestItemId(int n) {
        return this.E.get(n);
    }

    @Override
    public int size() {
        return this.Z.size();
    }

    @Override
    public void clear() {
        this.Z.clear();
        this.E.clear();
    }
}
