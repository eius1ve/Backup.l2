/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai.residences.clanhall;

import l2.gameserver.ai.Fighter;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class RainbowEnragedYeti
extends Fighter {
    public RainbowEnragedYeti(NpcInstance npcInstance) {
        super(npcInstance);
    }

    public void onEvtSpawn() {
        super.onEvtSpawn();
        Functions.npcShoutCustomMessage((NpcInstance)this.getActor(), (String)"clanhall.RainbowEnragedYeti.OOOH_WHO_POURED_NECTAR_ON_MY_HEAD_WHILE_I_WAS_SLEEPING", (Object[])new Object[0]);
    }
}
