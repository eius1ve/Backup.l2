/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

public final class Race
extends Enum<Race> {
    public static final /* enum */ Race human = new Race();
    public static final /* enum */ Race elf = new Race();
    public static final /* enum */ Race darkelf = new Race();
    public static final /* enum */ Race orc = new Race();
    public static final /* enum */ Race dwarf = new Race();
    private static final /* synthetic */ Race[] a;

    public static Race[] values() {
        return (Race[])a.clone();
    }

    public static Race valueOf(String string) {
        return Enum.valueOf(Race.class, string);
    }

    private static /* synthetic */ Race[] a() {
        return new Race[]{human, elf, darkelf, orc, dwarf};
    }

    static {
        a = Race.a();
    }
}
