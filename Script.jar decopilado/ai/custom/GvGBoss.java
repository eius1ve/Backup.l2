/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai.custom;

import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class GvGBoss
extends Fighter {
    boolean phrase1 = false;
    boolean phrase2 = false;
    boolean phrase3 = false;

    public GvGBoss(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.startImmobilized();
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.getCurrentHpPercents() < 50.0 && !this.phrase1) {
            this.phrase1 = true;
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"GvG.1", (Object[])new Object[0]);
        } else if (npcInstance.getCurrentHpPercents() < 30.0 && !this.phrase2) {
            this.phrase2 = true;
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"GvG.2", (Object[])new Object[0]);
        } else if (npcInstance.getCurrentHpPercents() < 5.0 && !this.phrase3) {
            this.phrase3 = true;
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"GvG.3", (Object[])new Object[0]);
        }
        super.onEvtAttacked(creature, n);
    }
}
