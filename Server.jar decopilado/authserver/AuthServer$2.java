/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver;

import l2.commons.threading.RunnableImpl;

class AuthServer.2
extends RunnableImpl {
    AuthServer.2() {
    }

    @Override
    public void runImpl() throws Exception {
        Runtime.getRuntime().exit(2);
    }
}
