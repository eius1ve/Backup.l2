/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.actor.instances.player;

public class ShortCut {
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_SKILL = 2;
    public static final int TYPE_ACTION = 3;
    public static final int TYPE_MACRO = 4;
    public static final int TYPE_RECIPE = 5;
    public static final int TYPE_TPBOOKMARK = 6;
    public static final int PAGE_NORMAL_0 = 0;
    public static final int PAGE_NORMAL_1 = 1;
    public static final int PAGE_NORMAL_2 = 2;
    public static final int PAGE_NORMAL_3 = 3;
    public static final int PAGE_NORMAL_4 = 4;
    public static final int PAGE_NORMAL_5 = 5;
    public static final int PAGE_NORMAL_6 = 6;
    public static final int PAGE_NORMAL_7 = 7;
    public static final int PAGE_NORMAL_8 = 8;
    public static final int PAGE_NORMAL_9 = 9;
    public static final int PAGE_NORMAL_10 = 10;
    public static final int PAGE_NORMAL_11 = 11;
    public static final int PAGE_NORMAL_12 = 12;
    public static final int PAGE_NORMAL_13 = 13;
    public static final int PAGE_NORMAL_14 = 14;
    public static final int PAGE_NORMAL_15 = 15;
    public static final int PAGE_NORMAL_16 = 16;
    public static final int PAGE_NORMAL_17 = 17;
    public static final int PAGE_NORMAL_18 = 18;
    public static final int PAGE_NORMAL_19 = 19;
    public static final int PAGE_MAX = 19;
    private final int kk;
    private final int kl;
    private final int _type;
    private final int km;
    private final int kn;
    private final int ko;

    public ShortCut(int n, int n2, int n3, int n4, int n5, int n6) {
        this.kk = n;
        this.kl = n2;
        this._type = n3;
        this.km = n4;
        this.kn = n5;
        this.ko = n6;
    }

    public int getSlot() {
        return this.kk;
    }

    public int getPage() {
        return this.kl;
    }

    public int getType() {
        return this._type;
    }

    public int getId() {
        return this.km;
    }

    public int getLevel() {
        return this.kn;
    }

    public int getCharacterType() {
        return this.ko;
    }

    public String toString() {
        return "ShortCut: " + this.kk + "/" + this.kl + " ( " + this._type + "," + this.km + "," + this.kn + "," + this.ko + ")";
    }
}
