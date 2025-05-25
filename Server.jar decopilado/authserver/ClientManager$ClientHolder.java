/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver;

import l2.authserver.network.l2.L2LoginClient;

private static class ClientManager.ClientHolder {
    private L2LoginClient a;
    private final long ai;
    private final int cZ;

    private ClientManager.ClientHolder(L2LoginClient l2LoginClient, long l, int n) {
        this.a = l2LoginClient;
        this.ai = l;
        this.cZ = n;
    }

    public L2LoginClient getLoginClient() {
        return this.a;
    }

    public ClientManager.ClientHolder setLoginClient(L2LoginClient l2LoginClient) {
        this.a = l2LoginClient;
        return this;
    }

    public long getAddTime() {
        return this.ai;
    }

    public int getCookieId() {
        return this.cZ;
    }
}
