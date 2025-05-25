/*
 * Decompiled with CFR 0.152.
 */
package services;

private static final class Roulette.GameType
extends Enum<Roulette.GameType> {
    public static final /* enum */ Roulette.GameType StraightUp = new Roulette.GameType();
    public static final /* enum */ Roulette.GameType ColumnBet = new Roulette.GameType();
    public static final /* enum */ Roulette.GameType DozenBet = new Roulette.GameType();
    public static final /* enum */ Roulette.GameType RedOrBlack = new Roulette.GameType();
    public static final /* enum */ Roulette.GameType EvenOrOdd = new Roulette.GameType();
    public static final /* enum */ Roulette.GameType LowOrHigh = new Roulette.GameType();
    private static final /* synthetic */ Roulette.GameType[] a;

    public static Roulette.GameType[] values() {
        return (Roulette.GameType[])a.clone();
    }

    public static Roulette.GameType valueOf(String string) {
        return Enum.valueOf(Roulette.GameType.class, string);
    }

    private static /* synthetic */ Roulette.GameType[] a() {
        return new Roulette.GameType[]{StraightUp, ColumnBet, DozenBet, RedOrBlack, EvenOrOdd, LowOrHigh};
    }

    static {
        a = Roulette.GameType.a();
    }
}
