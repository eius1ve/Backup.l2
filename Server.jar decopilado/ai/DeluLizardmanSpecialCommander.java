/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai;

import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class DeluLizardmanSpecialCommander
extends Fighter {
    private boolean l = false;

    public DeluLizardmanSpecialCommander(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtSpawn() {
        this.l = false;
        super.onEvtSpawn();
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (Rnd.chance((int)40) && !this.l) {
            this.l = true;
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1000390", (Object[])new Object[0]);
            List list = npcInstance.getAroundNpc(1000, 300);
            if (list != null && !list.isEmpty()) {
                for (NpcInstance npcInstance2 : list) {
                    if (!npcInstance2.isMonster()) continue;
                    npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)5000);
                }
            }
        }
        super.onEvtAttacked(creature, n);
    }
}
