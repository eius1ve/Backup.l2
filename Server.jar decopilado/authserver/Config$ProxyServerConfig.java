/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver;

public static class Config.ProxyServerConfig {
    private final int da;
    private final int db;
    private final String ar;
    private final int dc;
    private int dd;
    private int de;
    private boolean ac;

    public Config.ProxyServerConfig(int n, int n2, String string, int n3) {
        this.da = n;
        this.db = n2;
        this.ar = string;
        this.dc = n3;
    }

    public int getOrigServerId() {
        return this.da;
    }

    public int getProxyId() {
        return this.db;
    }

    public String getPorxyHost() {
        return this.ar;
    }

    public int getProxyPort() {
        return this.dc;
    }

    public boolean isHideMain() {
        return this.ac;
    }

    public Config.ProxyServerConfig setHideMain(boolean bl) {
        this.ac = bl;
        return this;
    }

    public int getMinAccessLevel() {
        return this.dd;
    }

    public Config.ProxyServerConfig setMinAccessLevel(int n) {
        this.dd = n;
        return this;
    }

    public int getMaxAccessLevel() {
        return this.de;
    }

    public Config.ProxyServerConfig setMaxAccessLevel(int n) {
        this.de = n;
        return this;
    }
}
