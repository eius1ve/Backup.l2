/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.utils.DeclensionKey;
import l2.gameserver.utils.Language;
import l2.gameserver.utils.Util;

public class TimeUtils {
    private static final SimpleDateFormat f = new SimpleDateFormat("HH:mm dd.MM.yyyy");
    private static final SimpleDateFormat g = new SimpleDateFormat("HH:mm dd.MM.yyyy");

    public static String toSimpleFormat(Calendar calendar) {
        return f.format(calendar.getTime());
    }

    public static String toSimpleFormat(long l) {
        return f.format(l);
    }

    public static String toHeroRecordFormat(long l) {
        return g.format(l);
    }

    public static String formatTime(int n, boolean bl) {
        return TimeUtils.formatTime(n, bl, Language.valueOf(Config.DEFAULT_LANG));
    }

    public static String formatTime(int n, boolean bl, Player player) {
        return TimeUtils.formatTime(n, bl, player.getLanguage());
    }

    public static String formatTime(int n, boolean bl, Language language) {
        int n2 = n / 86400;
        int n3 = (n - n2 * 24 * 3600) / 3600;
        int n4 = (n - n2 * 24 * 3600 - n3 * 3600) / 60;
        String string = n2 >= 1 ? (n3 < 1 || bl ? n2 + " " + Util.declension(n2, DeclensionKey.DAYS, language) : n2 + " " + Util.declension(n2, DeclensionKey.DAYS, language) + " " + n3 + " " + Util.declension(n3, DeclensionKey.HOUR, language)) : (n3 >= 1 ? (n4 < 1 || bl ? n3 + " " + Util.declension(n3, DeclensionKey.HOUR, language) : n3 + " " + Util.declension(n3, DeclensionKey.HOUR, language) + " " + n4 + " " + Util.declension(n4, DeclensionKey.MINUTES, language)) : (n < 60 ? "1 " + Util.declension(n4, DeclensionKey.MINUTES, language) : n4 + " " + Util.declension(n4, DeclensionKey.MINUTES, language)));
        return string;
    }

    public static long parse(String string) throws ParseException {
        return f.parse(string).getTime();
    }

    public static boolean isWithinTime(String string, int n) {
        Pattern pattern = Pattern.compile("(\\d{2})(\\d{2})-(\\d{2})(\\d{2})");
        Matcher matcher = pattern.matcher(string);
        if (matcher.matches()) {
            int n2 = Integer.parseInt(matcher.group(1));
            int n3 = Integer.parseInt(matcher.group(2));
            int n4 = Integer.parseInt(matcher.group(3));
            int n5 = Integer.parseInt(matcher.group(4));
            int n6 = n2 * 100 + n3;
            int n7 = n4 * 100 + n5;
            return n >= n6 && n < n7;
        }
        return false;
    }
}
