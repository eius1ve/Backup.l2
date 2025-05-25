/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Priest
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import java.util.List;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Priest;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;

public class RagnaHealer
extends Priest {
    private long s;

    public RagnaHealer(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (creature == null) {
            return;
        }
        if (System.currentTimeMillis() - this.s > 10000L) {
            this.s = System.currentTimeMillis();
            List list = npcInstance.getAroundNpc(500, 300);
            if (list != null && !list.isEmpty()) {
                for (NpcInstance npcInstance2 : list) {
                    if (!npcInstance2.isMonster() || npcInstance2.getNpcId() < 22691 || npcInstance2.getNpcId() > 22702) continue;
                    npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)5000);
                }
            }
        }
        super.onEvtAttacked(creature, n);
    }
}
