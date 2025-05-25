/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.utils;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.NpcString;
import l2.gameserver.network.l2.components.SysString;
import org.apache.commons.lang3.StringUtils;

public class HtmlUtils {
    public static final String PREV_BUTTON = "<button value=\"&$1037;\" action=\"bypass %prev_bypass%\" width=60 height=25 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">";
    public static final String NEXT_BUTTON = "<button value=\"&$1038;\" action=\"bypass %next_bypass%\" width=60 height=25 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">";

    public static String htmlResidenceName(int n) {
        return "&%" + n + ";";
    }

    public static String htmlNpcName(int n) {
        return "&@" + n + ";";
    }

    public static String htmlSysString(SysString sysString) {
        return HtmlUtils.htmlSysString(sysString.getId());
    }

    public static String htmlSysString(int n) {
        return "&$" + n + ";";
    }

    public static String htmlItemName(int n) {
        return "&#" + n + ";";
    }

    public static String htmlClassName(int n, Player player) {
        return new CustomMessage(String.format("ClassName.%d", n), player, new Object[0]).toString();
    }

    public static String htmlNpcString(NpcString npcString, Object ... objectArray) {
        return HtmlUtils.htmlNpcString(npcString.getId(), objectArray);
    }

    public static String htmlNpcString(int n, Object ... objectArray) {
        Object object = "<fstring";
        if (objectArray.length > 0) {
            for (int i = 0; i < objectArray.length; ++i) {
                object = (String)object + " p" + (i + 1) + "=\"" + String.valueOf(objectArray[i]) + "\"";
            }
        }
        object = (String)object + ">" + n + "</fstring>";
        return object;
    }

    public static String htmlButton(String string, String string2, int n) {
        return HtmlUtils.htmlButton(string, string2, n, 22);
    }

    public static String htmlButton(String string, String string2, int n, int n2) {
        return String.format("<button value=\"%s\" action=\"%s\" back=\"L2UI_CT1.Button_DF_Down\" width=%d height=%d fore=\"L2UI_CT1.Button_DF\">", string, string2, n, n2);
    }

    public static String sanitizeHtml(String string, int n) {
        return StringUtils.truncate((String)StringUtils.replaceChars((String)StringUtils.trimToEmpty((String)string), (String)"><$\"", (String)""), (int)n);
    }

    public static String sanitizeHtml(String string) {
        return HtmlUtils.sanitizeHtml(string, 255);
    }

    public static String getMpGauge(int n, long l, long l2, boolean bl) {
        return HtmlUtils.a(n, l, l2, bl, "L2UI_CT1.Gauges.Gauge_DF_Large_MP_bg_Center", "L2UI_CT1.Gauges.Gauge_DF_Large_MP_Center", 17L, -13L);
    }

    private static String a(int n, long l, long l2, boolean bl, String string, String string2, long l3, long l4) {
        long l5 = Math.min(l, l2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table width=");
        stringBuilder.append(n);
        stringBuilder.append(" cellpadding=0 cellspacing=0>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td background=\"");
        stringBuilder.append(string);
        stringBuilder.append("\">");
        stringBuilder.append("<img src=\"");
        stringBuilder.append(string2);
        stringBuilder.append("\" width=");
        stringBuilder.append((long)((double)l5 / (double)l2 * (double)n));
        stringBuilder.append(" height=");
        stringBuilder.append(l3);
        stringBuilder.append(">");
        stringBuilder.append("</td>");
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td align=center>");
        stringBuilder.append("<table cellpadding=0 cellspacing=");
        stringBuilder.append(l4);
        stringBuilder.append(">");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td>");
        if (bl) {
            stringBuilder.append("<table cellpadding=0 cellspacing=2>");
            stringBuilder.append("<tr><td>");
            stringBuilder.append(String.format("%.2f%%", (double)l5 / (double)l2 * 100.0));
            stringBuilder.append("</td></tr>");
            stringBuilder.append("</table>");
        } else {
            int n2 = (n - 10) / 2;
            stringBuilder.append("<table cellpadding=0 cellspacing=0>");
            stringBuilder.append("<tr>");
            stringBuilder.append("<td width=");
            stringBuilder.append(n2);
            stringBuilder.append(" align=right>");
            stringBuilder.append(l5);
            stringBuilder.append("</td>");
            stringBuilder.append("<td width=10 align=center>/</td>");
            stringBuilder.append("<td width=");
            stringBuilder.append(n2);
            stringBuilder.append(">");
            stringBuilder.append(l2);
            stringBuilder.append("</td>");
            stringBuilder.append("</tr>");
            stringBuilder.append("</table>");
        }
        stringBuilder.append("</td>");
        stringBuilder.append("</tr>");
        stringBuilder.append("</table>");
        stringBuilder.append("</td>");
        stringBuilder.append("</tr>");
        stringBuilder.append("</table>");
        return stringBuilder.toString();
    }
}
