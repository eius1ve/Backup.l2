/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.net.nio.impl;

import l2.commons.net.nio.impl.MMOClient;

public interface IMMOExecutor<T extends MMOClient> {
    public void execute(Runnable var1);
}
