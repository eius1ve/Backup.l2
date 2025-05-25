/*
 * Decompiled with CFR 0.152.
 */
package achievements;

public static final class AchievementUI.HtmlTabUI.TabStyle
extends Enum<AchievementUI.HtmlTabUI.TabStyle> {
    public static final /* enum */ AchievementUI.HtmlTabUI.TabStyle board = new AchievementUI.HtmlTabUI.TabStyle(74, 22, "L2UI_CH3.board_tab1", "L2UI_CH3.board_tab2", "L2UI_CH3.board_tab2");
    public static final /* enum */ AchievementUI.HtmlTabUI.TabStyle chatting = new AchievementUI.HtmlTabUI.TabStyle(true, 64, 22, "L2UI_CH3.chatting_tab1", "L2UI_CH3.chatting_tab2", "L2UI_CH3.chatting_tab2");
    public static final /* enum */ AchievementUI.HtmlTabUI.TabStyle inventory = new AchievementUI.HtmlTabUI.TabStyle(94, 22, "L2UI_CT1.Tab_DF_Tab_Selected", "L2UI_CT1.Tab_DF_Tab_Unselected", "L2UI_CT1.Tab_DF_Tab_Unselected");
    public static final /* enum */ AchievementUI.HtmlTabUI.TabStyle msn = new AchievementUI.HtmlTabUI.TabStyle(114, 22, "L2UI_CH3.msn_tab1", "L2UI_CH3.msn_tab2", "L2UI_CH3.msn_tab2");
    public static final /* enum */ AchievementUI.HtmlTabUI.TabStyle normal = new AchievementUI.HtmlTabUI.TabStyle(74, 22, "L2UI_CT1.Tab_DF_Tab_Selected", "L2UI_CT1.Tab_DF_Tab_Unselected", "L2UI_CT1.Tab_DF_Tab_Unselected");
    final boolean isButtom;
    final int width;
    final int height;
    final String active;
    final String fore;
    final String back;
    private static final /* synthetic */ AchievementUI.HtmlTabUI.TabStyle[] a;

    public static AchievementUI.HtmlTabUI.TabStyle[] values() {
        return (AchievementUI.HtmlTabUI.TabStyle[])a.clone();
    }

    public static AchievementUI.HtmlTabUI.TabStyle valueOf(String string) {
        return Enum.valueOf(AchievementUI.HtmlTabUI.TabStyle.class, string);
    }

    private AchievementUI.HtmlTabUI.TabStyle(boolean bl, int n2, int n3, String string2, String string3, String string4) {
        this.isButtom = bl;
        this.width = n2;
        this.height = n3;
        this.active = string2;
        this.fore = string3;
        this.back = string4;
    }

    private AchievementUI.HtmlTabUI.TabStyle(int n2, int n3, String string2, String string3, String string4) {
        this(false, n2, n3, string2, string3, string4);
    }

    private static /* synthetic */ AchievementUI.HtmlTabUI.TabStyle[] a() {
        return new AchievementUI.HtmlTabUI.TabStyle[]{board, chatting, inventory, msn, normal};
    }

    static {
        a = AchievementUI.HtmlTabUI.TabStyle.a();
    }
}
