/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

import l2.gameserver.templates.item.support.EnchantScrollOnFailAction;
import l2.gameserver.templates.item.support.EnchantTargetType;
import l2.gameserver.templates.item.support.Grade;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class EnchantItem {
    private final int Gt;
    private final double aG;
    private final Grade a;
    private final int Gu;
    private final int Gv;
    private final EnchantScrollOnFailAction a;
    private final EnchantTargetType a;

    public EnchantItem(int n, double d, EnchantScrollOnFailAction enchantScrollOnFailAction, Grade grade, int n2, int n3, EnchantTargetType enchantTargetType) {
        this.Gt = n;
        this.aG = d;
        this.a = enchantScrollOnFailAction;
        this.a = grade;
        this.Gu = n2;
        this.Gv = n3;
        this.a = enchantTargetType;
    }

    public int getItemId() {
        return this.Gt;
    }

    public double getChanceMod() {
        return this.aG;
    }

    public EnchantScrollOnFailAction getResultType() {
        return this.a;
    }

    public Grade getGrade() {
        return this.a;
    }

    public int getMinLvl() {
        return this.Gu;
    }

    public int getMaxLvl() {
        return this.Gv;
    }

    public EnchantTargetType getTargetType() {
        return this.a;
    }
}
