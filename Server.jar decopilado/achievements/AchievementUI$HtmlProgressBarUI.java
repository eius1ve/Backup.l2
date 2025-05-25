/*
 * Decompiled with CFR 0.152.
 */
package achievements;

public static class AchievementUI.HtmlProgressBarUI {
    private final ProgressBarStyle a;
    private int l;
    private int m = -1;
    private int n = -1;
    private int value = -1;

    public AchievementUI.HtmlProgressBarUI(ProgressBarStyle progressBarStyle) {
        this.a = progressBarStyle;
        this.l = progressBarStyle.maxWidth >= 0 ? progressBarStyle.maxWidth : 100;
    }

    public int getBarWidth() {
        return this.l;
    }

    public AchievementUI.HtmlProgressBarUI setBarWidth(int n) {
        this.l = n;
        return this;
    }

    public int getValue() {
        return this.value;
    }

    public AchievementUI.HtmlProgressBarUI setValue(int n) {
        this.value = n;
        if (n > this.n) {
            this.n = n;
        }
        return this;
    }

    public int getFull() {
        return this.n;
    }

    public AchievementUI.HtmlProgressBarUI setFull(int n) {
        this.n = n;
        return this;
    }

    public int getPercent() {
        return this.m;
    }

    public void setPercent(int n) {
        this.m = n;
    }

    public String toHtml() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = this.a.maxWidth >= 0 ? Math.min(this.l, this.a.maxWidth) : this.l;
        stringBuilder.append("<table width=").append(n).append(" border=0 cellspacing=0 cellpadding=0><tr>");
        if (this.value >= 0 && this.n >= 0) {
            if (this.value < this.n) {
                int n2 = (int)((float)n / (float)this.n * (float)this.value);
                stringBuilder.append("<td><img src=\"").append(this.a.valueTexture).append("\" width=").append(n2).append(" height=").append(this.a.height).append("></td>");
                stringBuilder.append("<td><img src=\"").append(this.a.backTexture).append("\" width=").append(n - n2).append(" height=").append(this.a.height).append("></td>");
            } else {
                stringBuilder.append("<td><img src=\"").append(this.n == 0 ? this.a.backTexture : this.a.valueTexture).append("\" width=").append(n).append(" height=").append(this.a.height).append("></td>");
            }
        } else if (this.m >= 0) {
            int n3 = (int)((float)this.m / 100.0f * (float)this.value);
            if (this.m < 100) {
                stringBuilder.append("<td><img src=\"").append(this.a.valueTexture).append("\" width=").append(n3).append(" height=").append(this.a.height).append("></td>");
                stringBuilder.append("<td><img src=\"").append(this.a.backTexture).append("\" width=").append(n - n3).append(" height=").append(this.a.height).append("></td>");
            } else {
                stringBuilder.append("<td><img src=\"").append(this.m == 0 ? this.a.backTexture : this.a.valueTexture).append("\" width=").append(n).append(" height=").append(this.a.height).append("></td>");
            }
        }
        stringBuilder.append("</tr></table>");
        return stringBuilder.toString();
    }

    public static final class ProgressBarStyle
    extends Enum<ProgressBarStyle> {
        public static final /* enum */ ProgressBarStyle classic_red = new ProgressBarStyle(3, 96, "sek.cbui62", "sek.cbui64");
        public static final /* enum */ ProgressBarStyle classic_blue = new ProgressBarStyle(3, 96, "sek.cbui63", "sek.cbui64");
        public static final /* enum */ ProgressBarStyle yellow = new ProgressBarStyle(12, -1, "L2UI_CH3.br_bar1_cp", "L2UI_CH3.br_bar1back_cp");
        public static final /* enum */ ProgressBarStyle flax = new ProgressBarStyle(12, -1, "L2UI_CH3.br_bar1_cp1", "L2UI_CH3.br_bar1back_cp");
        public static final /* enum */ ProgressBarStyle flax_light = new ProgressBarStyle(8, -1, "L2UI_CH3.br_bar1_cp1", "L2UI_CH3.br_bar1back_cp");
        final int height;
        final int maxWidth;
        final String valueTexture;
        final String backTexture;
        private static final /* synthetic */ ProgressBarStyle[] a;

        public static ProgressBarStyle[] values() {
            return (ProgressBarStyle[])a.clone();
        }

        public static ProgressBarStyle valueOf(String string) {
            return Enum.valueOf(ProgressBarStyle.class, string);
        }

        private ProgressBarStyle(int n2, int n3, String string2, String string3) {
            this.height = n2;
            this.maxWidth = n3;
            this.valueTexture = string2;
            this.backTexture = string3;
        }

        private static /* synthetic */ ProgressBarStyle[] a() {
            return new ProgressBarStyle[]{classic_red, classic_blue, yellow, flax, flax_light};
        }

        static {
            a = ProgressBarStyle.a();
        }
    }
}
