/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import java.util.List;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;

public class KanadisFollower
extends Fighter {
    public KanadisFollower(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
        NpcInstance npcInstance = this.getActor();
        List list = npcInstance.getAroundNpc(7000, 300);
        if (list != null && !list.isEmpty()) {
            for (NpcInstance npcInstance2 : list) {
                if (npcInstance2.getNpcId() != 36562) continue;
                npcInstance.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, (Object)npcInstance2, (Object)500);
            }
        }
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (creature.getNpcId() == 36562) {
            npcInstance.getAggroList().addDamageHate(creature, 0, 100);
            this.startRunningTask(2000L);
            this.setIntention(CtrlIntention.AI_INTENTION_ATTACK, creature);
        }
        super.onEvtAttacked(creature, n);
    }

    protected boolean maybeMoveToHome() {
        return false;
    }
}
