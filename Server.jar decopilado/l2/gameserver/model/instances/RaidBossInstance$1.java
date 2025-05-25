/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.commons.threading.RunnableImpl;

class RaidBossInstance.1
extends RunnableImpl {
    RaidBossInstance.1() {
    }

    @Override
    public void runImpl() throws Exception {
        if (RaidBossInstance.this.isDead()) {
            RaidBossInstance.this.getMinionList().unspawnMinions();
        }
    }
}
