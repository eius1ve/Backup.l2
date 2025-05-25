/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates;

public class SoulCrystal {
    private final int FF;
    private final int FG;
    private final int FH;
    private final int FI;

    public SoulCrystal(int n, int n2, int n3, int n4) {
        this.FF = n;
        this.FG = n2;
        this.FH = n3;
        this.FI = n4;
    }

    public int getItemId() {
        return this.FF;
    }

    public int getLevel() {
        return this.FG;
    }

    public int getNextItemId() {
        return this.FH;
    }

    public int getCursedNextItemId() {
        return this.FI;
    }
}
