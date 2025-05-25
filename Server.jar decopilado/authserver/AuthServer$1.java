/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver;

import l2.authserver.network.l2.SelectorHelper;
import l2.commons.net.nio.impl.MMOConnection;
import l2.commons.net.nio.impl.SelectorConfig;

class AuthServer.1
extends SelectorConfig {
    final /* synthetic */ SelectorHelper val$sh;

    AuthServer.1(SelectorHelper selectorHelper) {
        this.val$sh = selectorHelper;
    }

    @Override
    public void onIncorrectPacketSize(MMOConnection mMOConnection, int n) {
        this.val$sh.onIncorrectPacketSize(mMOConnection, n);
    }
}
