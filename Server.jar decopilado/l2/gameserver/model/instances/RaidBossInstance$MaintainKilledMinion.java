/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.instances.MinionInstance;

private class RaidBossInstance.MaintainKilledMinion
extends RunnableImpl {
    private final MinionInstance a;

    public RaidBossInstance.MaintainKilledMinion(MinionInstance minionInstance) {
        this.a = minionInstance;
    }

    @Override
    public void runImpl() throws Exception {
        if (!RaidBossInstance.this.isDead()) {
            this.a.refreshID();
            RaidBossInstance.this.spawnMinion(this.a);
        }
    }
}
