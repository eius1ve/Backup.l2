/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.net.nio.impl;

import l2.commons.net.nio.impl.MMOClient;
import l2.commons.net.nio.impl.MMOConnection;

public interface IClientFactory<T extends MMOClient> {
    public T create(MMOConnection<T> var1);
}
