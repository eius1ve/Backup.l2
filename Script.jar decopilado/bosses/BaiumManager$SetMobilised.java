/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.instances.BossInstance
 */
package bosses;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.instances.BossInstance;

public static class BaiumManager.SetMobilised
extends RunnableImpl {
    private BossInstance c;

    public BaiumManager.SetMobilised(BossInstance bossInstance) {
        this.c = bossInstance;
    }

    public void runImpl() throws Exception {
        this.c.stopImmobilized();
    }
}
