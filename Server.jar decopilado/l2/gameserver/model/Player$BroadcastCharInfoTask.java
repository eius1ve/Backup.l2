/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.threading.RunnableImpl;

public class Player.BroadcastCharInfoTask
extends RunnableImpl {
    @Override
    public void runImpl() throws Exception {
        Player.this.broadcastCharInfoImpl();
        Player.this.R = null;
    }
}
