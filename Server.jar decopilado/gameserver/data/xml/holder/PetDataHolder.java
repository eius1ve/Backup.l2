/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntIntHashMap
 *  gnu.trove.TIntObjectHashMap
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.data.xml.holder;

import gnu.trove.TIntIntHashMap;
import gnu.trove.TIntObjectHashMap;
import java.util.Arrays;
import java.util.List;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.model.PetData;
import l2.gameserver.model.Summon;
import l2.gameserver.model.items.ItemInstance;
import org.apache.commons.lang3.ArrayUtils;

public class PetDataHolder
extends AbstractHolder {
    private static final PetDataHolder a = new PetDataHolder();
    private final TIntObjectHashMap<PetData> l = new TIntObjectHashMap();
    private final TIntObjectHashMap<PetData> m = new TIntObjectHashMap();
    private final TIntIntHashMap d = new TIntIntHashMap();

    public static PetDataHolder getInstance() {
        return a;
    }

    private PetDataHolder() {
    }

    public void addPetData(PetData petData) {
        if (petData.getControlItemId() > 0 && !this.m.contains(petData.getControlItemId())) {
            this.m.put(petData.getControlItemId(), (Object)petData);
        }
        this.l.put(petData.getID() * 100 + petData.getLevel(), (Object)petData);
        if (petData.getLevel() > this.d.get(petData.getID())) {
            this.d.put(petData.getID(), petData.getLevel());
        }
    }

    public Integer getMaxLevel(int n) {
        return this.d.containsKey(n) ? Integer.valueOf(this.d.get(n)) : null;
    }

    public PetData getInfo(int n) {
        return this.getInfo(n, 1);
    }

    public PetData getInfoMaxLevel(int n) {
        Integer n2 = this.getMaxLevel(n);
        return n2 != null ? this.getInfo(n, n2) : null;
    }

    public PetData getInfo(int n, int n2) {
        PetData petData = null;
        while (petData == null && n2 < 100) {
            petData = (PetData)this.l.get(n * 100 + n2);
            ++n2;
        }
        return petData;
    }

    public int getAvailableSkillLevel(Summon summon, int n) {
        if (summon == null || n < 1) {
            return 0;
        }
        PetData petData = this.getInfo(summon.getNpcId(), summon.getLevel());
        if (petData == null) {
            return 0;
        }
        return petData.getAvailableLevel(summon, n);
    }

    public List<PetData> getAllPetData() {
        return Arrays.asList((PetData[])this.l.getValues((Object[])new PetData[this.l.size()]));
    }

    public int[] getAllControlItemIds() {
        return ArrayUtils.clone((int[])this.m.keys());
    }

    public PetData getByControlItemId(ItemInstance itemInstance) {
        if (itemInstance == null) {
            return null;
        }
        return this.getByControlItemId(itemInstance.getItemId());
    }

    public PetData getByControlItemId(int n) {
        return (PetData)this.m.get(n);
    }

    @Override
    public int size() {
        return this.l.size();
    }

    @Override
    public void clear() {
        this.l.clear();
    }
}
