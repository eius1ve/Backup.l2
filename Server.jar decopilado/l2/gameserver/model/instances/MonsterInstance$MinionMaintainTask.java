/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.commons.threading.RunnableImpl;

public class MonsterInstance.MinionMaintainTask
extends RunnableImpl {
    @Override
    public void runImpl() throws Exception {
        if (MonsterInstance.this.isDead()) {
            return;
        }
        MonsterInstance.this.getMinionList().spawnMinions();
    }
}
