/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.threading.RunnableImpl;

private class Player.UserInfoTask
extends RunnableImpl {
    private Player.UserInfoTask() {
    }

    @Override
    public void runImpl() throws Exception {
        Player.this.bk();
        Player.this.v = (double)null;
    }
}
