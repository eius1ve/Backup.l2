/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.net.nio.impl;

import java.nio.ByteBuffer;
import l2.commons.net.nio.impl.MMOClient;

public abstract class ReceivablePacket<T extends MMOClient>
extends l2.commons.net.nio.ReceivablePacket<T> {
    protected T _client;
    protected ByteBuffer _buf;

    protected void setByteBuffer(ByteBuffer byteBuffer) {
        this._buf = byteBuffer;
    }

    @Override
    protected ByteBuffer getByteBuffer() {
        return this._buf;
    }

    protected void setClient(T t) {
        this._client = t;
    }

    @Override
    public T getClient() {
        return this._client;
    }

    @Override
    protected abstract boolean read();
}
