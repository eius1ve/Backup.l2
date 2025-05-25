/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.threading.RunnableImpl;

public class Summon.BroadcastCharInfoTask
extends RunnableImpl {
    @Override
    public void runImpl() throws Exception {
        Summon.this.broadcastCharInfoImpl();
        Summon.this.R = null;
    }
}
