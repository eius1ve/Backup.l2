/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.net.nio.impl;

import java.nio.ByteBuffer;
import l2.commons.net.nio.impl.MMOClient;
import l2.commons.net.nio.impl.ReceivablePacket;

public interface IPacketHandler<T extends MMOClient> {
    public ReceivablePacket<T> handlePacket(ByteBuffer var1, T var2);
}
