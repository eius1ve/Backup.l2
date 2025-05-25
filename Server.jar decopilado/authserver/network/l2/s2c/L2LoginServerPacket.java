/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.network.l2.s2c;

import l2.authserver.network.l2.L2LoginClient;
import l2.commons.net.nio.impl.MMOClient;
import l2.commons.net.nio.impl.SendablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class L2LoginServerPacket
extends SendablePacket<L2LoginClient> {
    private static final Logger aa = LoggerFactory.getLogger(L2LoginServerPacket.class);

    @Override
    public final boolean write() {
        try {
            this.writeImpl();
            return true;
        }
        catch (Exception exception) {
            aa.error("Client: " + (MMOClient)this.getClient() + " - Failed writing: " + this.getClass().getSimpleName() + "!", (Throwable)exception);
            return false;
        }
    }

    protected abstract void writeImpl();
}
