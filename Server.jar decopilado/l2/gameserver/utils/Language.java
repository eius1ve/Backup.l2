/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.utils;

public final class Language
extends Enum<Language> {
    public static final /* enum */ Language ENGLISH = new Language("en");
    public static final /* enum */ Language RUSSIAN = new Language("ru");
    public static final Language[] VALUES;
    private String gD;
    private static final /* synthetic */ Language[] a;

    public static Language[] values() {
        return (Language[])a.clone();
    }

    public static Language valueOf(String string) {
        return Enum.valueOf(Language.class, string);
    }

    private Language(String string2) {
        this.gD = string2;
    }

    public String getShortName() {
        return this.gD;
    }

    private static /* synthetic */ Language[] a() {
        return new Language[]{ENGLISH, RUSSIAN};
    }

    static {
        a = Language.a();
        VALUES = Language.values();
    }
}
