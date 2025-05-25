/*
 * Decompiled with CFR 0.152.
 */
package achievements;

private static final class AchievementUI.AchFaceLevelDisplayType
extends Enum<AchievementUI.AchFaceLevelDisplayType> {
    public static final /* enum */ AchievementUI.AchFaceLevelDisplayType DISPLAY_DEFAULT = new AchievementUI.AchFaceLevelDisplayType("level.htm", false, false, true);
    public static final /* enum */ AchievementUI.AchFaceLevelDisplayType DISPLAY_PROGRESSING = new AchievementUI.AchFaceLevelDisplayType("level.progressing.htm", true, false, true);
    public static final /* enum */ AchievementUI.AchFaceLevelDisplayType DISPLAY_COMPLETED = new AchievementUI.AchFaceLevelDisplayType("level.completed.htm", false, true, true);
    public static final /* enum */ AchievementUI.AchFaceLevelDisplayType DISPLAY_REWARDED = new AchievementUI.AchFaceLevelDisplayType("level.rewarded.htm", false, false, false);
    final String templateFileName;
    final boolean haveProgressBar;
    final boolean haveRewardBypass;
    final boolean haveRewardList;
    private static final /* synthetic */ AchievementUI.AchFaceLevelDisplayType[] a;

    public static AchievementUI.AchFaceLevelDisplayType[] values() {
        return (AchievementUI.AchFaceLevelDisplayType[])a.clone();
    }

    public static AchievementUI.AchFaceLevelDisplayType valueOf(String string) {
        return Enum.valueOf(AchievementUI.AchFaceLevelDisplayType.class, string);
    }

    private AchievementUI.AchFaceLevelDisplayType(String string2, boolean bl, boolean bl2, boolean bl3) {
        this.templateFileName = string2;
        this.haveProgressBar = bl;
        this.haveRewardBypass = bl2;
        this.haveRewardList = bl3;
    }

    private static /* synthetic */ AchievementUI.AchFaceLevelDisplayType[] a() {
        return new AchievementUI.AchFaceLevelDisplayType[]{DISPLAY_DEFAULT, DISPLAY_PROGRESSING, DISPLAY_COMPLETED, DISPLAY_REWARDED};
    }

    static {
        a = AchievementUI.AchFaceLevelDisplayType.a();
    }
}
