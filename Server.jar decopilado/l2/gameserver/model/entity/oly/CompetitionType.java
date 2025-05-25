/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

public final class CompetitionType
extends Enum<CompetitionType> {
    public static final /* enum */ CompetitionType TEAM_CLASS_FREE = new CompetitionType(0);
    public static final /* enum */ CompetitionType CLASS_FREE = new CompetitionType(1);
    public static final /* enum */ CompetitionType CLASS_INDIVIDUAL = new CompetitionType(2);
    public static final /* enum */ CompetitionType CLASS_TYPE_INDIVIDUAL = new CompetitionType(4);
    private int lD;
    private static final /* synthetic */ CompetitionType[] a;

    public static CompetitionType[] values() {
        return (CompetitionType[])a.clone();
    }

    public static CompetitionType valueOf(String string) {
        return Enum.valueOf(CompetitionType.class, string);
    }

    private CompetitionType(int n2) {
        this.lD = n2;
    }

    public int getTypeIdx() {
        return this.lD;
    }

    public static CompetitionType getTypeOf(int n) {
        for (CompetitionType competitionType : CompetitionType.values()) {
            if (competitionType.getTypeIdx() != n) continue;
            return competitionType;
        }
        return null;
    }

    private static /* synthetic */ CompetitionType[] a() {
        return new CompetitionType[]{TEAM_CLASS_FREE, CLASS_FREE, CLASS_INDIVIDUAL, CLASS_TYPE_INDIVIDUAL};
    }

    static {
        a = CompetitionType.a();
    }
}
