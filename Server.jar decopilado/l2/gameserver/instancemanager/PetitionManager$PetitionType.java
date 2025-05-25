/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager;

public static final class PetitionManager.PetitionType
extends Enum<PetitionManager.PetitionType> {
    public static final /* enum */ PetitionManager.PetitionType Immobility = new PetitionManager.PetitionType();
    public static final /* enum */ PetitionManager.PetitionType Recovery_Related = new PetitionManager.PetitionType();
    public static final /* enum */ PetitionManager.PetitionType Bug_Report = new PetitionManager.PetitionType();
    public static final /* enum */ PetitionManager.PetitionType Quest_Related = new PetitionManager.PetitionType();
    public static final /* enum */ PetitionManager.PetitionType Bad_User = new PetitionManager.PetitionType();
    public static final /* enum */ PetitionManager.PetitionType Suggestions = new PetitionManager.PetitionType();
    public static final /* enum */ PetitionManager.PetitionType Game_Tip = new PetitionManager.PetitionType();
    public static final /* enum */ PetitionManager.PetitionType Operation_Related = new PetitionManager.PetitionType();
    public static final /* enum */ PetitionManager.PetitionType Other = new PetitionManager.PetitionType();
    private static final /* synthetic */ PetitionManager.PetitionType[] a;

    public static PetitionManager.PetitionType[] values() {
        return (PetitionManager.PetitionType[])a.clone();
    }

    public static PetitionManager.PetitionType valueOf(String string) {
        return Enum.valueOf(PetitionManager.PetitionType.class, string);
    }

    private static /* synthetic */ PetitionManager.PetitionType[] a() {
        return new PetitionManager.PetitionType[]{Immobility, Recovery_Related, Bug_Report, Quest_Related, Bad_User, Suggestions, Game_Tip, Operation_Related, Other};
    }

    static {
        a = PetitionManager.PetitionType.a();
    }
}
