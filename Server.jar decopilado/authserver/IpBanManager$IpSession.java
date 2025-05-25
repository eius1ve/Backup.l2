/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver;

private class IpBanManager.IpSession {
    public int tryCount;
    public long lastTry;
    public long banExpire;

    private IpBanManager.IpSession() {
    }
}
