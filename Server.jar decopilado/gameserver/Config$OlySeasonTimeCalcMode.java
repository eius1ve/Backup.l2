/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver;

public static final class Config.OlySeasonTimeCalcMode
extends Enum<Config.OlySeasonTimeCalcMode> {
    public static final /* enum */ Config.OlySeasonTimeCalcMode NORMAL = new Config.OlySeasonTimeCalcMode();
    public static final /* enum */ Config.OlySeasonTimeCalcMode CUSTOM = new Config.OlySeasonTimeCalcMode();
    private static final /* synthetic */ Config.OlySeasonTimeCalcMode[] a;

    public static Config.OlySeasonTimeCalcMode[] values() {
        return (Config.OlySeasonTimeCalcMode[])a.clone();
    }

    public static Config.OlySeasonTimeCalcMode valueOf(String string) {
        return Enum.valueOf(Config.OlySeasonTimeCalcMode.class, string);
    }

    private static /* synthetic */ Config.OlySeasonTimeCalcMode[] a() {
        return new Config.OlySeasonTimeCalcMode[]{NORMAL, CUSTOM};
    }

    static {
        a = Config.OlySeasonTimeCalcMode.a();
    }
}
