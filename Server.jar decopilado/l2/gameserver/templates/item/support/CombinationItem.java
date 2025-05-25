/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

public class CombinationItem {
    private final int Gp;
    private final int Gq;
    private final double aF;
    private final int Gr;
    private final int Gs;

    public CombinationItem(int n, int n2, double d, int n3, int n4) {
        this.Gp = n;
        this.Gq = n2;
        this.aF = d;
        this.Gr = n3;
        this.Gs = n4;
    }

    public int getSlotoneId() {
        return this.Gp;
    }

    public int getSlottwoId() {
        return this.Gq;
    }

    public double getChance() {
        return this.aF;
    }

    public int getOnsuccess() {
        return this.Gr;
    }

    public int getOnfail() {
        return this.Gs;
    }
}
