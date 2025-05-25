/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

public static final class Request.L2RequestType
extends Enum<Request.L2RequestType> {
    public static final /* enum */ Request.L2RequestType CUSTOM = new Request.L2RequestType();
    public static final /* enum */ Request.L2RequestType PARTY = new Request.L2RequestType();
    public static final /* enum */ Request.L2RequestType PARTY_ROOM = new Request.L2RequestType();
    public static final /* enum */ Request.L2RequestType CLAN = new Request.L2RequestType();
    public static final /* enum */ Request.L2RequestType ALLY = new Request.L2RequestType();
    public static final /* enum */ Request.L2RequestType TRADE = new Request.L2RequestType();
    public static final /* enum */ Request.L2RequestType TRADE_REQUEST = new Request.L2RequestType();
    public static final /* enum */ Request.L2RequestType FRIEND = new Request.L2RequestType();
    public static final /* enum */ Request.L2RequestType CHANNEL = new Request.L2RequestType();
    public static final /* enum */ Request.L2RequestType DUEL = new Request.L2RequestType();
    private static final /* synthetic */ Request.L2RequestType[] a;

    public static Request.L2RequestType[] values() {
        return (Request.L2RequestType[])a.clone();
    }

    public static Request.L2RequestType valueOf(String string) {
        return Enum.valueOf(Request.L2RequestType.class, string);
    }

    private static /* synthetic */ Request.L2RequestType[] a() {
        return new Request.L2RequestType[]{CUSTOM, PARTY, PARTY_ROOM, CLAN, ALLY, TRADE, TRADE_REQUEST, FRIEND, CHANNEL, DUEL};
    }

    static {
        a = Request.L2RequestType.a();
    }
}
