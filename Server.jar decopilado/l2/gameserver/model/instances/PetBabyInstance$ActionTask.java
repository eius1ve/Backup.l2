/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Skill;

class PetBabyInstance.ActionTask
extends RunnableImpl {
    PetBabyInstance.ActionTask() {
    }

    @Override
    public void runImpl() throws Exception {
        Skill skill = PetBabyInstance.this.onActionTask();
        PetBabyInstance.this.L = ThreadPoolManager.getInstance().schedule(new PetBabyInstance.ActionTask(), skill == null ? 1000L : (long)(skill.getHitTime() * 333 / Math.max(PetBabyInstance.this.getMAtkSpd(), 1) - 100));
    }
}
