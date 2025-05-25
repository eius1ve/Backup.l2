/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.threading.RunnableImpl;

class Request.1
extends RunnableImpl {
    Request.1() {
    }

    @Override
    public void runImpl() throws Exception {
        Request.this.timeout();
    }
}
