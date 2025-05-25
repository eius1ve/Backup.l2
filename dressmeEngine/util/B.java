/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.model.Player
 *  l2.gameserver.templates.item.ItemTemplate
 */
package dressmeEngine.util;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Logger;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.Player;
import l2.gameserver.templates.item.ItemTemplate;

public class B {
    protected static final Logger _log = Logger.getLogger(B.class.getName());
    private static NumberFormat B = NumberFormat.getIntegerInstance(Locale.US);

    public static String B(int i) {
        return i == -300 ? "Fame" : (i == -100 ? "PC Bang point" : (i == -200 ? "Clan reputation" : ""));
    }

    public static boolean ItemExist(int i) {
        return ItemHolder.getInstance().getTemplate(i) != null;
    }

    public static String A(int i) {
        String icon = "icon.NOICON";
        ItemTemplate template = null;
        try {
            try {
                template = ItemHolder.getInstance().getTemplate(i);
            }
            catch (Exception exception) {
                if (template != null) {
                    icon = template.getIcon();
                }
            }
        }
        finally {
            if (template != null) {
                icon = template.getIcon();
            }
        }
        return icon;
    }

    public static String A(Player Player2, long j, int i) {
        return j > 1L ? String.valueOf(dressmeEngine.util.B.A(j)) + " " + dressmeEngine.util.B.B(i) : (j == 1L ? dressmeEngine.util.B.B(i) : "Free");
    }

    public static String A(long j) {
        return B.format(j);
    }
}
