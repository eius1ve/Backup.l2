/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

public class Manor.SeedData {
    private int _id;
    private int _level;
    private int hn;
    private int ho;
    private int _type1;
    private int _type2;
    private int hp;
    private int hq;
    private long bg;
    private long bh;

    public Manor.SeedData(int n, int n2, int n3) {
        this._level = n;
        this.hn = n2;
        this.ho = n3;
    }

    public Manor.SeedData setData(int n, int n2, int n3, int n4, int n5, long l, long l2) {
        this._id = n;
        this._type1 = n2;
        this._type2 = n3;
        this.hp = n4;
        this.hq = n5;
        this.bg = l;
        this.bh = l2;
        return this;
    }

    public int getManorId() {
        return this.hp;
    }

    public int getId() {
        return this._id;
    }

    public int getCrop() {
        return this.hn;
    }

    public int getMature() {
        return this.ho;
    }

    public int getReward(int n) {
        return n == 1 ? this._type1 : this._type2;
    }

    public int getLevel() {
        return this._level;
    }

    public boolean isAlternative() {
        return this.hq == 1;
    }

    public long getSeedLimit() {
        return this.bg;
    }

    public long getCropLimit() {
        return this.bh;
    }
}
