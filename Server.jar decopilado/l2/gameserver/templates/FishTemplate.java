/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates;

public class FishTemplate {
    private final int Ep;
    private final int Eq;
    private final String gj;
    private final int Er;
    private final int Es;
    private final int Et;
    private final int Eu;
    private final int Ev;
    private final int Ew;
    private final int Ex;
    private final int Ey;

    public FishTemplate(int n, int n2, String string, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        this.Ep = n;
        this.Eq = n2;
        this.gj = string.intern();
        this.Er = n3;
        this.Es = n4;
        this.Et = n5;
        this.Eu = n6;
        this.Ev = n7;
        this.Ew = n8;
        this.Ex = n9;
        this.Ey = n10;
    }

    public int getId() {
        return this.Ep;
    }

    public int getLevel() {
        return this.Eq;
    }

    public String getName() {
        return this.gj;
    }

    public int getHP() {
        return this.Er;
    }

    public int getHpRegen() {
        return this.Es;
    }

    public int getType() {
        return this.Et;
    }

    public int getGroup() {
        return this.Eu;
    }

    public int getFishGuts() {
        return this.Ev;
    }

    public int getGutsCheckTime() {
        return this.Ew;
    }

    public int getWaitTime() {
        return this.Ex;
    }

    public int getCombatTime() {
        return this.Ey;
    }
}
