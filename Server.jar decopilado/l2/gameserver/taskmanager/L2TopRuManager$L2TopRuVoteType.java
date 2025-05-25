/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

private static final class L2TopRuManager.L2TopRuVoteType
extends Enum<L2TopRuManager.L2TopRuVoteType> {
    public static final /* enum */ L2TopRuManager.L2TopRuVoteType WEB = new L2TopRuManager.L2TopRuVoteType();
    public static final /* enum */ L2TopRuManager.L2TopRuVoteType SMS = new L2TopRuManager.L2TopRuVoteType();
    private static final /* synthetic */ L2TopRuManager.L2TopRuVoteType[] a;

    public static L2TopRuManager.L2TopRuVoteType[] values() {
        return (L2TopRuManager.L2TopRuVoteType[])a.clone();
    }

    public static L2TopRuManager.L2TopRuVoteType valueOf(String string) {
        return Enum.valueOf(L2TopRuManager.L2TopRuVoteType.class, string);
    }

    private static /* synthetic */ L2TopRuManager.L2TopRuVoteType[] a() {
        return new L2TopRuManager.L2TopRuVoteType[]{WEB, SMS};
    }

    static {
        a = L2TopRuManager.L2TopRuVoteType.a();
    }
}
