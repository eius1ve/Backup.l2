/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.components;

public final class ClanEntryStatus
extends Enum<ClanEntryStatus> {
    public static final /* enum */ ClanEntryStatus DEFAULT = new ClanEntryStatus();
    public static final /* enum */ ClanEntryStatus ORDERED = new ClanEntryStatus();
    public static final /* enum */ ClanEntryStatus CLAN_REGISTRATION = new ClanEntryStatus();
    public static final /* enum */ ClanEntryStatus UNKNOWN = new ClanEntryStatus();
    public static final /* enum */ ClanEntryStatus WAITING = new ClanEntryStatus();
    private static final /* synthetic */ ClanEntryStatus[] a;

    public static ClanEntryStatus[] values() {
        return (ClanEntryStatus[])a.clone();
    }

    public static ClanEntryStatus valueOf(String string) {
        return Enum.valueOf(ClanEntryStatus.class, string);
    }

    private static /* synthetic */ ClanEntryStatus[] a() {
        return new ClanEntryStatus[]{DEFAULT, ORDERED, CLAN_REGISTRATION, UNKNOWN, WAITING};
    }

    static {
        a = ClanEntryStatus.a();
    }
}
