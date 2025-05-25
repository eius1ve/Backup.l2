/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.mail;

public static final class Mail.SenderType
extends Enum<Mail.SenderType> {
    public static final /* enum */ Mail.SenderType NORMAL = new Mail.SenderType();
    public static final /* enum */ Mail.SenderType NEWS_INFORMER = new Mail.SenderType();
    public static final /* enum */ Mail.SenderType NONE = new Mail.SenderType();
    public static final /* enum */ Mail.SenderType BIRTHDAY = new Mail.SenderType();
    public static Mail.SenderType[] VALUES;
    private static final /* synthetic */ Mail.SenderType[] a;

    public static Mail.SenderType[] values() {
        return (Mail.SenderType[])a.clone();
    }

    public static Mail.SenderType valueOf(String string) {
        return Enum.valueOf(Mail.SenderType.class, string);
    }

    private static /* synthetic */ Mail.SenderType[] a() {
        return new Mail.SenderType[]{NORMAL, NEWS_INFORMER, NONE, BIRTHDAY};
    }

    static {
        a = Mail.SenderType.a();
        VALUES = Mail.SenderType.values();
    }
}
