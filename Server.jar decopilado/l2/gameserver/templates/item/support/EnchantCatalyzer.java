/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.templates.item.support.EnchantItem;
import l2.gameserver.templates.item.support.EnchantScrollOnFailAction;
import l2.gameserver.templates.item.support.EnchantTargetType;
import l2.gameserver.templates.item.support.Grade;

public class EnchantCatalyzer
extends EnchantItem {
    public EnchantCatalyzer(int n, double d, EnchantScrollOnFailAction enchantScrollOnFailAction, Grade grade, int n2, int n3, EnchantTargetType enchantTargetType) {
        super(n, d, enchantScrollOnFailAction, grade, n2, n3, enchantTargetType);
    }

    public boolean isUsableWith(ItemInstance itemInstance) {
        int n = itemInstance.getEnchantLevel() + 1;
        Grade grade = itemInstance.getCrystalType();
        if (grade.gradeOrd() != this.getGrade().gradeOrd()) {
            return false;
        }
        if (n < this.getMinLvl() || n > this.getMaxLvl()) {
            return false;
        }
        return this.getTargetType().isUsableOn(itemInstance);
    }
}
