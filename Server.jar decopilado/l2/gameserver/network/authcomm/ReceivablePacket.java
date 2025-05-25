/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.authcomm;

import java.nio.ByteBuffer;
import l2.gameserver.network.authcomm.AuthServerCommunication;
import l2.gameserver.network.authcomm.SendablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ReceivablePacket
extends l2.commons.net.nio.ReceivablePacket<AuthServerCommunication> {
    private static final Logger cz = LoggerFactory.getLogger(ReceivablePacket.class);

    @Override
    public AuthServerCommunication getClient() {
        return AuthServerCommunication.getInstance();
    }

    @Override
    protected ByteBuffer getByteBuffer() {
        return this.getClient().getReadBuffer();
    }

    @Override
    public final boolean read() {
        try {
            this.readImpl();
        }
        catch (Exception exception) {
            cz.error("", (Throwable)exception);
        }
        return true;
    }

    @Override
    public final void run() {
        try {
            this.runImpl();
        }
        catch (Exception exception) {
            cz.error("", (Throwable)exception);
        }
    }

    protected abstract void readImpl();

    protected abstract void runImpl();

    protected void sendPacket(SendablePacket sendablePacket) {
        this.getClient().sendPacket(sendablePacket);
    }
}
