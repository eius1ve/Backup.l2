/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2;

public static final class L2LoginClient.LoginClientState
extends Enum<L2LoginClient.LoginClientState> {
    public static final /* enum */ L2LoginClient.LoginClientState CONNECTED = new L2LoginClient.LoginClientState();
    public static final /* enum */ L2LoginClient.LoginClientState AUTHED_GG = new L2LoginClient.LoginClientState();
    public static final /* enum */ L2LoginClient.LoginClientState AUTHED = new L2LoginClient.LoginClientState();
    public static final /* enum */ L2LoginClient.LoginClientState DISCONNECTED = new L2LoginClient.LoginClientState();
    private static final /* synthetic */ L2LoginClient.LoginClientState[] a;

    public static L2LoginClient.LoginClientState[] values() {
        return (L2LoginClient.LoginClientState[])a.clone();
    }

    public static L2LoginClient.LoginClientState valueOf(String string) {
        return Enum.valueOf(L2LoginClient.LoginClientState.class, string);
    }

    private static /* synthetic */ L2LoginClient.LoginClientState[] a() {
        return new L2LoginClient.LoginClientState[]{CONNECTED, AUTHED_GG, AUTHED, DISCONNECTED};
    }

    static {
        a = L2LoginClient.LoginClientState.a();
    }
}
