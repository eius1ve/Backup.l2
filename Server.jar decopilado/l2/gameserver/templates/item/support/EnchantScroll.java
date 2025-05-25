/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 *  org.napile.primitive.Containers
 *  org.napile.primitive.sets.IntSet
 *  org.napile.primitive.sets.impl.HashIntSet
 */
package l2.gameserver.templates.item.support;

import java.util.Map;
import l2.gameserver.Config;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.templates.item.support.EnchantCatalyzer;
import l2.gameserver.templates.item.support.EnchantChanceType;
import l2.gameserver.templates.item.support.EnchantItem;
import l2.gameserver.templates.item.support.EnchantScrollOnFailAction;
import l2.gameserver.templates.item.support.EnchantTargetType;
import l2.gameserver.templates.item.support.Grade;
import org.apache.commons.lang3.ArrayUtils;
import org.napile.primitive.Containers;
import org.napile.primitive.sets.IntSet;
import org.napile.primitive.sets.impl.HashIntSet;

public class EnchantScroll
extends EnchantItem {
    private final EnchantScrollOnFailAction b;
    private final boolean hl;
    private final boolean hm;
    private final int Gw;
    private final int Gx;
    private final int Gy;
    private final boolean hn;
    private final Map<EnchantChanceType, double[]> bU;
    private IntSet e = Containers.EMPTY_INT_SET;

    public EnchantScroll(int n, int n2, int n3, double d, Grade grade, int n4, int n5, EnchantTargetType enchantTargetType, EnchantScrollOnFailAction enchantScrollOnFailAction, int n6, boolean bl, boolean bl2, boolean bl3, Map<EnchantChanceType, double[]> map) {
        super(n, d, enchantScrollOnFailAction, grade, n4, n5, enchantTargetType);
        this.Gx = n2;
        this.Gy = n3;
        this.hn = bl2;
        this.Gw = n6;
        this.b = enchantScrollOnFailAction;
        this.hl = bl;
        this.hm = bl3;
        this.bU = map;
    }

    public int getIncrement() {
        return this.Gx;
    }

    public int getIncrementMax() {
        return this.Gy;
    }

    public int getFailResultLevel() {
        return this.Gw;
    }

    public EnchantScrollOnFailAction getOnFailAction() {
        return this.b;
    }

    public boolean isCloseEnchantWindowOnFail() {
        return this.hn;
    }

    public void addItemRestrict(int n) {
        if (this.e.isEmpty()) {
            this.e = new HashIntSet();
        }
        this.e.add(n);
    }

    public boolean isHasAbnormalVisualEffect() {
        return this.hm;
    }

    public boolean isInfallible() {
        return this.hl;
    }

    public boolean isUsableWith(ItemInstance itemInstance, EnchantCatalyzer enchantCatalyzer) {
        if (!this.e.isEmpty() && !this.e.contains(itemInstance.getItemId())) {
            return false;
        }
        if (itemInstance.getEnchantLevel() >= this.getMaxLvl()) {
            return false;
        }
        int n = Math.min(this.getMaxLvl(), itemInstance.getEnchantLevel() + Math.max(this.getIncrement(), this.getIncrementMax()));
        Grade grade = itemInstance.getCrystalType();
        if (this.e.isEmpty() && grade.gradeOrd() != this.getGrade().gradeOrd()) {
            return false;
        }
        if (n < this.getMinLvl()) {
            return false;
        }
        if (enchantCatalyzer != null) {
            if (this.getTargetType() != enchantCatalyzer.getTargetType()) {
                return false;
            }
            if (grade.gradeOrd() != enchantCatalyzer.getGrade().gradeOrd()) {
                return false;
            }
            if (n < enchantCatalyzer.getMinLvl() || n > enchantCatalyzer.getMaxLvl()) {
                return false;
            }
        }
        return this.getTargetType().isUsableOn(itemInstance);
    }

    public double getEnchantChance(ItemInstance itemInstance) {
        int n = itemInstance.getEnchantLevel();
        double[] dArray = null;
        long l = itemInstance.getTemplate().getBodyPart();
        if (32768L == l) {
            dArray = this.bU.get((Object)EnchantChanceType.FULL_ARMOR);
        } else if (l == 8L || l == 6L || l == 48L || l == 0x100000L || l == 0x200000L || l == 0x20000000L) {
            dArray = this.bU.get((Object)EnchantChanceType.JEWELRY);
        } else if (l == 128L || l == 16384L) {
            dArray = this.bU.get((Object)EnchantChanceType.WEAPON);
        } else if (ArrayUtils.contains((long[])new long[]{64L, 256L, 512L, 1024L, 2048L, 4096L, 8192L, 1L}, (long)l)) {
            dArray = this.bU.get((Object)EnchantChanceType.ARMOR);
        }
        if (dArray == null && Config.ALT_HAIR_TO_ACC_SLOT && (itemInstance.getTemplate().getBodyPart() == 65536L || itemInstance.getTemplate().getBodyPart() == 262144L || itemInstance.getTemplate().getBodyPart() == 524288L)) {
            dArray = this.bU.get((Object)EnchantChanceType.ARMOR);
        }
        if (dArray == null) {
            return 0.0;
        }
        if (n >= dArray.length) {
            n = dArray.length - 1;
        }
        return dArray[n];
    }
}
