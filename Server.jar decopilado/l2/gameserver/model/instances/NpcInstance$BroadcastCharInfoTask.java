/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.commons.threading.RunnableImpl;

public class NpcInstance.BroadcastCharInfoTask
extends RunnableImpl {
    @Override
    public void runImpl() throws Exception {
        NpcInstance.this.broadcastCharInfoImpl();
        NpcInstance.this.R = null;
    }
}
