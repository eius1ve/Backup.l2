/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Location
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Fighter;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Location;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TimakOrcTroopLeader
extends Fighter {
    private static final int[] j = new int[]{20768, 20769, 20770};
    private boolean j = true;

    public TimakOrcTroopLeader(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (!npcInstance.isDead() && this.j) {
            this.j = false;
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1000403", (Object[])new Object[0]);
            for (int n2 : j) {
                try {
                    NpcInstance npcInstance2 = NpcHolder.getInstance().getTemplate(n2).getNewInstance();
                    npcInstance2.setSpawnedLoc(((MonsterInstance)npcInstance).getMinionPosition());
                    npcInstance2.setReflection(npcInstance.getReflection());
                    npcInstance2.setCurrentHpMp((double)npcInstance2.getMaxHp(), (double)npcInstance2.getMaxMp(), true);
                    npcInstance2.spawnMe(Location.findPointToStay((GameObject)creature, (int)50, (int)100));
                    npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)Rnd.get((int)1, (int)100));
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        super.onEvtAttacked(creature, n);
    }

    protected void onEvtDead(Creature creature) {
        this.j = true;
        super.onEvtDead(creature);
    }
}
