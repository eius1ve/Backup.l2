/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates;

public class SkillEnchant {
    private final int Fy;
    private final int Fz;
    private final int FA;
    private final int FB;
    private final long dP;
    private final int FC;
    private final int[] bi;
    private final int FD;
    private final long dQ;
    private final boolean ha;
    private final int FE;

    public SkillEnchant(int n, int n2, int n3, int n4, long l, int n5, int[] nArray, int n6, long l2, boolean bl, int n7) {
        this.Fy = n;
        this.Fz = n2;
        this.FA = n3;
        this.FB = n4;
        this.dP = l;
        this.FC = n5;
        this.bi = nArray;
        this.FD = n6;
        this.dQ = l2;
        this.ha = bl;
        this.FE = n7;
    }

    public int[] getChances() {
        return this.bi;
    }

    public long getExp() {
        return this.dP;
    }

    public long getItemCount() {
        return this.dQ;
    }

    public int getItemId() {
        return this.FD;
    }

    public int getEnchantLevel() {
        return this.FA;
    }

    public int getRouteId() {
        return this.FB;
    }

    public int getSkillId() {
        return this.Fy;
    }

    public int getSkillLevel() {
        return this.Fz;
    }

    public int getSp() {
        return this.FC;
    }

    public boolean isResetOnFailure() {
        return this.ha;
    }

    public int getResetToLevel() {
        return this.FE;
    }
}
