/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.Guard
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import l2.gameserver.ai.Guard;
import l2.gameserver.model.instances.NpcInstance;

public class GuardRndWalkAndAnim
extends Guard {
    public GuardRndWalkAndAnim(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected boolean thinkActive() {
        if (super.thinkActive()) {
            return true;
        }
        if (this.randomAnimation()) {
            return true;
        }
        return this.randomWalk();
    }
}
