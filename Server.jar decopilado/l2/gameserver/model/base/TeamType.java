/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

public final class TeamType
extends Enum<TeamType> {
    public static final /* enum */ TeamType NONE = new TeamType();
    public static final /* enum */ TeamType BLUE = new TeamType();
    public static final /* enum */ TeamType RED = new TeamType();
    private static final /* synthetic */ TeamType[] a;

    public static TeamType[] values() {
        return (TeamType[])a.clone();
    }

    public static TeamType valueOf(String string) {
        return Enum.valueOf(TeamType.class, string);
    }

    public TeamType revert() {
        return this == BLUE ? RED : (this == RED ? BLUE : NONE);
    }

    private static /* synthetic */ TeamType[] a() {
        return new TeamType[]{NONE, BLUE, RED};
    }

    static {
        a = TeamType.a();
    }
}
