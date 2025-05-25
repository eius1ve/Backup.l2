/*
 * Decompiled with CFR 0.152.
 */
package achievements;

public static final class AchievementUI.HtmlProgressBarUI.ProgressBarStyle
extends Enum<AchievementUI.HtmlProgressBarUI.ProgressBarStyle> {
    public static final /* enum */ AchievementUI.HtmlProgressBarUI.ProgressBarStyle classic_red = new AchievementUI.HtmlProgressBarUI.ProgressBarStyle(3, 96, "sek.cbui62", "sek.cbui64");
    public static final /* enum */ AchievementUI.HtmlProgressBarUI.ProgressBarStyle classic_blue = new AchievementUI.HtmlProgressBarUI.ProgressBarStyle(3, 96, "sek.cbui63", "sek.cbui64");
    public static final /* enum */ AchievementUI.HtmlProgressBarUI.ProgressBarStyle yellow = new AchievementUI.HtmlProgressBarUI.ProgressBarStyle(12, -1, "L2UI_CH3.br_bar1_cp", "L2UI_CH3.br_bar1back_cp");
    public static final /* enum */ AchievementUI.HtmlProgressBarUI.ProgressBarStyle flax = new AchievementUI.HtmlProgressBarUI.ProgressBarStyle(12, -1, "L2UI_CH3.br_bar1_cp1", "L2UI_CH3.br_bar1back_cp");
    public static final /* enum */ AchievementUI.HtmlProgressBarUI.ProgressBarStyle flax_light = new AchievementUI.HtmlProgressBarUI.ProgressBarStyle(8, -1, "L2UI_CH3.br_bar1_cp1", "L2UI_CH3.br_bar1back_cp");
    final int height;
    final int maxWidth;
    final String valueTexture;
    final String backTexture;
    private static final /* synthetic */ AchievementUI.HtmlProgressBarUI.ProgressBarStyle[] a;

    public static AchievementUI.HtmlProgressBarUI.ProgressBarStyle[] values() {
        return (AchievementUI.HtmlProgressBarUI.ProgressBarStyle[])a.clone();
    }

    public static AchievementUI.HtmlProgressBarUI.ProgressBarStyle valueOf(String string) {
        return Enum.valueOf(AchievementUI.HtmlProgressBarUI.ProgressBarStyle.class, string);
    }

    private AchievementUI.HtmlProgressBarUI.ProgressBarStyle(int n2, int n3, String string2, String string3) {
        this.height = n2;
        this.maxWidth = n3;
        this.valueTexture = string2;
        this.backTexture = string3;
    }

    private static /* synthetic */ AchievementUI.HtmlProgressBarUI.ProgressBarStyle[] a() {
        return new AchievementUI.HtmlProgressBarUI.ProgressBarStyle[]{classic_red, classic_blue, yellow, flax, flax_light};
    }

    static {
        a = AchievementUI.HtmlProgressBarUI.ProgressBarStyle.a();
    }
}
