/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2;

import l2.authserver.network.l2.L2LoginClient;

static class L2LoginClient.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$authserver$network$l2$L2LoginClient$LoginClientState;

    static {
        $SwitchMap$l2$authserver$network$l2$L2LoginClient$LoginClientState = new int[L2LoginClient.LoginClientState.values().length];
        try {
            L2LoginClient.1.$SwitchMap$l2$authserver$network$l2$L2LoginClient$LoginClientState[L2LoginClient.LoginClientState.CONNECTED.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            L2LoginClient.1.$SwitchMap$l2$authserver$network$l2$L2LoginClient$LoginClientState[L2LoginClient.LoginClientState.AUTHED_GG.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            L2LoginClient.1.$SwitchMap$l2$authserver$network$l2$L2LoginClient$LoginClientState[L2LoginClient.LoginClientState.AUTHED.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
