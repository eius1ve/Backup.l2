/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.net.nio.impl;

import java.nio.channels.SocketChannel;

public interface IAcceptFilter {
    public boolean accept(SocketChannel var1);
}
