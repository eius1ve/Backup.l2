/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity;

public static class MonsterRace.HistoryInfo {
    private final int lg;
    private int lh;
    private int li;
    private double z;

    public MonsterRace.HistoryInfo(int n, int n2, int n3, double d) {
        this.lg = n;
        this.lh = n2;
        this.li = n3;
        this.z = d;
    }

    public int getRaceId() {
        return this.lg;
    }

    public int getFirst() {
        return this.lh;
    }

    public int getSecond() {
        return this.li;
    }

    public double getOddRate() {
        return this.z;
    }

    public void setFirst(int n) {
        this.lh = n;
    }

    public void setSecond(int n) {
        this.li = n;
    }

    public void setOddRate(double d) {
        this.z = d;
    }
}
