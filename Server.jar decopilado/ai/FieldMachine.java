/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai;

import java.util.List;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class FieldMachine
extends DefaultAI {
    private long o;

    public FieldMachine(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (creature == null || creature.getPlayer() == null) {
            return;
        }
        if (System.currentTimeMillis() - this.o > 15000L) {
            this.o = System.currentTimeMillis();
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)("scripts.ai.FieldMachine." + npcInstance.getNpcId()), (Object[])new Object[0]);
            List list = npcInstance.getAroundNpc(1500, 300);
            if (list != null && !list.isEmpty()) {
                for (NpcInstance npcInstance2 : list) {
                    if (!npcInstance2.isMonster() || npcInstance2.getNpcId() < 22656 || npcInstance2.getNpcId() > 22659) continue;
                    npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)5000);
                }
            }
        }
    }
}
