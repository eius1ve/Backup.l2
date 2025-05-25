/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.npc;

public static final class AbsorbInfo.AbsorbType
extends Enum<AbsorbInfo.AbsorbType> {
    public static final /* enum */ AbsorbInfo.AbsorbType LAST_HIT = new AbsorbInfo.AbsorbType();
    public static final /* enum */ AbsorbInfo.AbsorbType PARTY_ONE = new AbsorbInfo.AbsorbType();
    public static final /* enum */ AbsorbInfo.AbsorbType PARTY_ALL = new AbsorbInfo.AbsorbType();
    public static final /* enum */ AbsorbInfo.AbsorbType PARTY_RANDOM = new AbsorbInfo.AbsorbType();
    private static final /* synthetic */ AbsorbInfo.AbsorbType[] a;

    public static AbsorbInfo.AbsorbType[] values() {
        return (AbsorbInfo.AbsorbType[])a.clone();
    }

    public static AbsorbInfo.AbsorbType valueOf(String string) {
        return Enum.valueOf(AbsorbInfo.AbsorbType.class, string);
    }

    private static /* synthetic */ AbsorbInfo.AbsorbType[] a() {
        return new AbsorbInfo.AbsorbType[]{LAST_HIT, PARTY_ONE, PARTY_ALL, PARTY_RANDOM};
    }

    static {
        a = AbsorbInfo.AbsorbType.a();
    }
}
