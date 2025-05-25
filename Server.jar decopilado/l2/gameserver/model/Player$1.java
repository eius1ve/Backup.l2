/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.threading.RunnableImpl;

class Player.1
extends RunnableImpl {
    Player.1() {
    }

    @Override
    public void runImpl() throws Exception {
        if (!Player.this.isConnected()) {
            Player.this.bh();
            Player.this.deleteMe();
        }
    }
}
