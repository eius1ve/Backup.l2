/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

public final class CharacterDeleteFailType
extends Enum<CharacterDeleteFailType> {
    public static final /* enum */ CharacterDeleteFailType NONE = new CharacterDeleteFailType();
    public static final /* enum */ CharacterDeleteFailType UNKNOWN = new CharacterDeleteFailType();
    public static final /* enum */ CharacterDeleteFailType PLEDGE_MEMBER = new CharacterDeleteFailType();
    public static final /* enum */ CharacterDeleteFailType PLEDGE_MASTER = new CharacterDeleteFailType();
    public static final /* enum */ CharacterDeleteFailType PROHIBIT_CHAR_DELETION = new CharacterDeleteFailType();
    public static final /* enum */ CharacterDeleteFailType COMMISSION = new CharacterDeleteFailType();
    public static final /* enum */ CharacterDeleteFailType MENTOR = new CharacterDeleteFailType();
    public static final /* enum */ CharacterDeleteFailType MENTEE = new CharacterDeleteFailType();
    public static final /* enum */ CharacterDeleteFailType MAIL = new CharacterDeleteFailType();
    private static final /* synthetic */ CharacterDeleteFailType[] a;

    public static CharacterDeleteFailType[] values() {
        return (CharacterDeleteFailType[])a.clone();
    }

    public static CharacterDeleteFailType valueOf(String string) {
        return Enum.valueOf(CharacterDeleteFailType.class, string);
    }

    private static /* synthetic */ CharacterDeleteFailType[] a() {
        return new CharacterDeleteFailType[]{NONE, UNKNOWN, PLEDGE_MEMBER, PLEDGE_MASTER, PROHIBIT_CHAR_DELETION, COMMISSION, MENTOR, MENTEE, MAIL};
    }

    static {
        a = CharacterDeleteFailType.a();
    }
}
