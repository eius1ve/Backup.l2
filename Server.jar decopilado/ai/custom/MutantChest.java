/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai.custom;

import l2.commons.util.Rnd;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class MutantChest
extends Fighter {
    public MutantChest(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.startImmobilized();
    }

    protected void onEvtDead(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        if (Rnd.chance((int)30)) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"MutantChest", (Object[])new Object[0]);
        }
        npcInstance.deleteMe();
    }
}
