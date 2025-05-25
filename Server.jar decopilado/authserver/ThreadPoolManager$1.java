/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver;

import l2.commons.threading.RunnableImpl;

class ThreadPoolManager.1
extends RunnableImpl {
    ThreadPoolManager.1() {
    }

    @Override
    public void runImpl() {
        ThreadPoolManager.this.a.purge();
        ThreadPoolManager.this.a.purge();
    }
}
