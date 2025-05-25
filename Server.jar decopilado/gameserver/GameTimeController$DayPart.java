/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

static final class GameTimeController.DayPart
extends Enum<GameTimeController.DayPart> {
    public static final /* enum */ GameTimeController.DayPart DAY = new GameTimeController.DayPart();
    public static final /* enum */ GameTimeController.DayPart NIGHT = new GameTimeController.DayPart();
    private static final /* synthetic */ GameTimeController.DayPart[] a;

    public static GameTimeController.DayPart[] values() {
        return (GameTimeController.DayPart[])a.clone();
    }

    public static GameTimeController.DayPart valueOf(String string) {
        return Enum.valueOf(GameTimeController.DayPart.class, string);
    }

    private static int s() {
        Calendar calendar = Calendar.getInstance();
        return (int)((aH + (TimeUnit.HOURS.toMinutes(calendar.get(11)) + (long)calendar.get(12)) * 6L) % TimeUnit.DAYS.toMinutes(1L));
    }

    public static GameTimeController.DayPart now() {
        if (TimeUnit.MINUTES.toHours(GameTimeController.DayPart.s()) % TimeUnit.DAYS.toHours(1L) < 6L) {
            return NIGHT;
        }
        return DAY;
    }

    public static boolean isNow(GameTimeController.DayPart dayPart) {
        return GameTimeController.DayPart.now() == dayPart;
    }

    public boolean isItNow() {
        return GameTimeController.DayPart.isNow(this);
    }

    private static /* synthetic */ GameTimeController.DayPart[] a() {
        return new GameTimeController.DayPart[]{DAY, NIGHT};
    }

    static {
        a = GameTimeController.DayPart.a();
    }
}
