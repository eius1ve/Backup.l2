/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity;

public static final class MonsterRace.RaceState
extends Enum<MonsterRace.RaceState> {
    public static final /* enum */ MonsterRace.RaceState ACCEPTING_BETS = new MonsterRace.RaceState();
    public static final /* enum */ MonsterRace.RaceState WAITING = new MonsterRace.RaceState();
    public static final /* enum */ MonsterRace.RaceState STARTING_RACE = new MonsterRace.RaceState();
    public static final /* enum */ MonsterRace.RaceState RACE_END = new MonsterRace.RaceState();
    private static final /* synthetic */ MonsterRace.RaceState[] a;

    public static MonsterRace.RaceState[] values() {
        return (MonsterRace.RaceState[])a.clone();
    }

    public static MonsterRace.RaceState valueOf(String string) {
        return Enum.valueOf(MonsterRace.RaceState.class, string);
    }

    private static /* synthetic */ MonsterRace.RaceState[] a() {
        return new MonsterRace.RaceState[]{ACCEPTING_BETS, WAITING, STARTING_RACE, RACE_END};
    }

    static {
        a = MonsterRace.RaceState.a();
    }
}
