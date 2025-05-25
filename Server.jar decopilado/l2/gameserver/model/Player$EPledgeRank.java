/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

public static final class Player.EPledgeRank
extends Enum<Player.EPledgeRank> {
    public static final /* enum */ Player.EPledgeRank VAGABOND = new Player.EPledgeRank(0);
    public static final /* enum */ Player.EPledgeRank VASSAL = new Player.EPledgeRank(1);
    public static final /* enum */ Player.EPledgeRank HEIR = new Player.EPledgeRank(2);
    public static final /* enum */ Player.EPledgeRank KNIGHT = new Player.EPledgeRank(3);
    public static final /* enum */ Player.EPledgeRank WISEMAN = new Player.EPledgeRank(4);
    public static final /* enum */ Player.EPledgeRank BARON = new Player.EPledgeRank(5);
    public static final /* enum */ Player.EPledgeRank VISCOUNT = new Player.EPledgeRank(6);
    public static final /* enum */ Player.EPledgeRank COUNT = new Player.EPledgeRank(7);
    public static final /* enum */ Player.EPledgeRank MARQUIS = new Player.EPledgeRank(8);
    private final int iQ;
    public static Player.EPledgeRank[] VALUES;
    private static final /* synthetic */ Player.EPledgeRank[] a;

    public static Player.EPledgeRank[] values() {
        return (Player.EPledgeRank[])a.clone();
    }

    public static Player.EPledgeRank valueOf(String string) {
        return Enum.valueOf(Player.EPledgeRank.class, string);
    }

    private Player.EPledgeRank(int n2) {
        this.iQ = n2;
    }

    public int getRankId() {
        return this.iQ;
    }

    public static Player.EPledgeRank getPledgeRank(int n) {
        for (Player.EPledgeRank ePledgeRank : VALUES) {
            if (ePledgeRank.getRankId() != n) continue;
            return ePledgeRank;
        }
        return null;
    }

    private static /* synthetic */ Player.EPledgeRank[] a() {
        return new Player.EPledgeRank[]{VAGABOND, VASSAL, HEIR, KNIGHT, WISEMAN, BARON, VISCOUNT, COUNT, MARQUIS};
    }

    static {
        a = Player.EPledgeRank.a();
        VALUES = Player.EPledgeRank.values();
    }
}
