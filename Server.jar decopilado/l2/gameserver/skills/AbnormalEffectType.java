/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills;

@Deprecated
public final class AbnormalEffectType
extends Enum<AbnormalEffectType> {
    public static final /* enum */ AbnormalEffectType FIRST = new AbnormalEffectType();
    public static final /* enum */ AbnormalEffectType SECOND = new AbnormalEffectType();
    public static final /* enum */ AbnormalEffectType BRANCH = new AbnormalEffectType();
    public static AbnormalEffectType[] VALUES;
    private static final /* synthetic */ AbnormalEffectType[] a;

    public static AbnormalEffectType[] values() {
        return (AbnormalEffectType[])a.clone();
    }

    public static AbnormalEffectType valueOf(String string) {
        return Enum.valueOf(AbnormalEffectType.class, string);
    }

    private static /* synthetic */ AbnormalEffectType[] a() {
        return new AbnormalEffectType[]{FIRST, SECOND, BRANCH};
    }

    static {
        a = AbnormalEffectType.a();
        VALUES = AbnormalEffectType.values();
    }
}
