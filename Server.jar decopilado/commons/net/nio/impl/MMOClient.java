/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.net.nio.impl;

import java.nio.ByteBuffer;
import l2.commons.net.nio.impl.MMOConnection;

public abstract class MMOClient<T extends MMOConnection> {
    private T a;
    private boolean ax;

    public MMOClient(T t) {
        this.a = t;
    }

    protected void setConnection(T t) {
        this.a = t;
    }

    public T getConnection() {
        return this.a;
    }

    public boolean isAuthed() {
        return this.ax;
    }

    public void setAuthed(boolean bl) {
        this.ax = bl;
    }

    public void closeNow(boolean bl) {
        if (this.isConnected()) {
            ((MMOConnection)this.a).closeNow();
        }
    }

    public void closeLater() {
        if (this.isConnected()) {
            ((MMOConnection)this.a).closeLater();
        }
    }

    public boolean isConnected() {
        return this.a != null && !((MMOConnection)this.a).isClosed();
    }

    public abstract boolean decrypt(ByteBuffer var1, int var2);

    public abstract boolean encrypt(ByteBuffer var1, int var2);

    protected void onDisconnection() {
    }

    protected void onForcedDisconnection() {
    }

    public String getIpAddr() {
        return ((MMOConnection)this.getConnection()).getIpAddr();
    }
}
