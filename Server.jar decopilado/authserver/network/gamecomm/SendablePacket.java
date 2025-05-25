/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.network.gamecomm;

import java.nio.ByteBuffer;
import l2.authserver.network.gamecomm.GameServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SendablePacket
extends l2.commons.net.nio.SendablePacket<GameServer> {
    private static final Logger V = LoggerFactory.getLogger(SendablePacket.class);
    protected GameServer _gs;
    protected ByteBuffer _buf;

    protected void setByteBuffer(ByteBuffer byteBuffer) {
        this._buf = byteBuffer;
    }

    @Override
    protected ByteBuffer getByteBuffer() {
        return this._buf;
    }

    protected void setClient(GameServer gameServer) {
        this._gs = gameServer;
    }

    @Override
    public GameServer getClient() {
        return this._gs;
    }

    public GameServer getGameServer() {
        return this.getClient();
    }

    @Override
    public boolean write() {
        try {
            this.writeImpl();
        }
        catch (Exception exception) {
            V.error("", (Throwable)exception);
        }
        return true;
    }

    protected abstract void writeImpl();
}
