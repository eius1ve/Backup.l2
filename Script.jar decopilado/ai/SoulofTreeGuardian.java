/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Mystic
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.Mystic;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class SoulofTreeGuardian
extends Mystic {
    private boolean j = true;

    public SoulofTreeGuardian(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtSpawn() {
        this.j = true;
        super.onEvtSpawn();
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (this.j) {
            this.j = false;
            if (Rnd.chance((int)10)) {
                Functions.npcSayInRangeCustomMessage((NpcInstance)npcInstance, (int)500, (String)"scripts.ai.SoulofTreeGuardian.WE_MUST_PROTECT", (Object[])new Object[0]);
            }
        } else if (Rnd.chance((int)10)) {
            Functions.npcSayInRangeCustomMessage((NpcInstance)npcInstance, (int)500, (String)"scripts.ai.SoulofTreeGuardian.GET_OUT", (Object[])new Object[0]);
        }
    }
}
