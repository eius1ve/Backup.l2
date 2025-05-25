/*
 * Decompiled with CFR 0.152.
 */
package achievements;

import java.util.ArrayList;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public static class AchievementUI.HtmlTabUI {
    private final TabStyle a;
    private TabRecord a;
    private List<TabRecord> f = new ArrayList<TabRecord>();
    private int o = -1;

    public AchievementUI.HtmlTabUI(TabStyle tabStyle) {
        this(tabStyle, 296 / tabStyle.width);
    }

    public AchievementUI.HtmlTabUI(TabStyle tabStyle, int n) {
        this.a = tabStyle;
        this.o = n;
    }

    public TabRecord addTab(String string, String string2, boolean bl) {
        TabRecord tabRecord = new TabRecord(string, string2);
        if (bl) {
            this.setActive(tabRecord);
        }
        this.f.add(tabRecord);
        return tabRecord;
    }

    public TabRecord addTab(String string, String string2) {
        return this.addTab(string, string2, false);
    }

    public void setActive(TabRecord tabRecord) {
        this.a = tabRecord;
    }

    public void setTabsPerRow(int n) {
        this.o = n;
    }

    public String toHtml() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table width=").append(this.o * this.a.width).append(" border=0 cellspacing=0 cellpadding=0>");
        int n = 0;
        while (n * this.o < this.f.size()) {
            stringBuilder.append("<tr>");
            for (int i = 0; i < this.o; ++i) {
                stringBuilder.append("<td width=").append(this.a.width).append(">");
                int n2 = i + n * this.o;
                if (n2 < this.f.size()) {
                    TabRecord tabRecord = this.f.get(n2);
                    stringBuilder.append("<button").append(" width=").append(this.a.width).append(" height=").append(this.a.height);
                    if (tabRecord.title != null) {
                        stringBuilder.append(" value=\"").append(tabRecord.title).append("\"");
                    }
                    if (tabRecord.bypass != null) {
                        stringBuilder.append(" action=\"bypass -h ").append(tabRecord.bypass).append("\"");
                    }
                    if (tabRecord == this.a) {
                        stringBuilder.append(" fore=").append(this.a.active).append(" back=").append(this.a.active);
                    } else {
                        stringBuilder.append(" fore=").append(this.a.fore).append(" back=").append(this.a.back);
                    }
                    stringBuilder.append(">");
                } else {
                    stringBuilder.append("&nbsp;");
                }
                stringBuilder.append("</td>");
            }
            stringBuilder.append("</tr>");
            ++n;
        }
        stringBuilder.append("</table>");
        return stringBuilder.toString();
    }

    public static final class TabStyle
    extends Enum<TabStyle> {
        public static final /* enum */ TabStyle board = new TabStyle(74, 22, "L2UI_CH3.board_tab1", "L2UI_CH3.board_tab2", "L2UI_CH3.board_tab2");
        public static final /* enum */ TabStyle chatting = new TabStyle(true, 64, 22, "L2UI_CH3.chatting_tab1", "L2UI_CH3.chatting_tab2", "L2UI_CH3.chatting_tab2");
        public static final /* enum */ TabStyle inventory = new TabStyle(94, 22, "L2UI_CT1.Tab_DF_Tab_Selected", "L2UI_CT1.Tab_DF_Tab_Unselected", "L2UI_CT1.Tab_DF_Tab_Unselected");
        public static final /* enum */ TabStyle msn = new TabStyle(114, 22, "L2UI_CH3.msn_tab1", "L2UI_CH3.msn_tab2", "L2UI_CH3.msn_tab2");
        public static final /* enum */ TabStyle normal = new TabStyle(74, 22, "L2UI_CT1.Tab_DF_Tab_Selected", "L2UI_CT1.Tab_DF_Tab_Unselected", "L2UI_CT1.Tab_DF_Tab_Unselected");
        final boolean isButtom;
        final int width;
        final int height;
        final String active;
        final String fore;
        final String back;
        private static final /* synthetic */ TabStyle[] a;

        public static TabStyle[] values() {
            return (TabStyle[])a.clone();
        }

        public static TabStyle valueOf(String string) {
            return Enum.valueOf(TabStyle.class, string);
        }

        private TabStyle(boolean bl, int n2, int n3, String string2, String string3, String string4) {
            this.isButtom = bl;
            this.width = n2;
            this.height = n3;
            this.active = string2;
            this.fore = string3;
            this.back = string4;
        }

        private TabStyle(int n2, int n3, String string2, String string3, String string4) {
            this(false, n2, n3, string2, string3, string4);
        }

        private static /* synthetic */ TabStyle[] a() {
            return new TabStyle[]{board, chatting, inventory, msn, normal};
        }

        static {
            a = TabStyle.a();
        }
    }

    public class TabRecord {
        final String title;
        final String bypass;

        private TabRecord(String string, String string2) {
            this.title = string;
            this.bypass = string2;
        }
    }
}
