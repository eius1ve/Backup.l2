/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.World
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.tables.SkillTable
 *  org.apache.commons.lang3.ArrayUtils
 */
package ai;

import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.tables.SkillTable;
import org.apache.commons.lang3.ArrayUtils;

public class SeducedInvestigator
extends Fighter {
    private int[] h = new int[]{25659, 25660, 25661, 25662, 25663, 25664};
    private long t = 0L;

    public SeducedInvestigator(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.startImmobilized();
        npcInstance.startHealBlocked();
        this.AI_TASK_ACTIVE_DELAY = 5000L;
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return false;
        }
        List list = npcInstance.getAroundNpc(1000, 300);
        if (list != null && !list.isEmpty()) {
            for (NpcInstance npcInstance2 : list) {
                if (!ArrayUtils.contains((int[])this.h, (int)npcInstance2.getNpcId())) continue;
                npcInstance.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, (Object)npcInstance2, (Object)300);
            }
        }
        if (Rnd.chance((double)0.1) && this.t + 30000L < System.currentTimeMillis()) {
            NpcInstance npcInstance2;
            List list2 = World.getAroundPlayers((GameObject)npcInstance, (int)500, (int)200);
            if (list2 == null || list2.size() < 1) {
                return false;
            }
            npcInstance2 = (Player)list2.get(Rnd.get((int)list2.size()));
            if (npcInstance2.getReflectionId() == npcInstance.getReflectionId()) {
                this.t = System.currentTimeMillis();
                int[] nArray = new int[]{5970, 5971, 5972, 5973};
                if (npcInstance.getNpcId() == 36562) {
                    npcInstance.doCast(SkillTable.getInstance().getInfo(nArray[0], 1), (Creature)npcInstance2, true);
                } else if (npcInstance.getNpcId() == 36563) {
                    npcInstance.doCast(SkillTable.getInstance().getInfo(nArray[1], 1), (Creature)npcInstance2, true);
                } else if (npcInstance.getNpcId() == 36564) {
                    npcInstance.doCast(SkillTable.getInstance().getInfo(nArray[2], 1), (Creature)npcInstance2, true);
                } else {
                    npcInstance.doCast(SkillTable.getInstance().getInfo(nArray[3], 1), (Creature)npcInstance2, true);
                }
            }
        }
        return true;
    }

    protected void onEvtDead(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        Reflection reflection = npcInstance.getReflection();
        List list = reflection.getPlayers();
        for (Player player : list) {
            player.sendPacket((IStaticPacket)new ExShowScreenMessage("The Investigator has been killed. The mission is failed.", 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
        }
        reflection.startCollapseTimer(5000L);
        super.onEvtDead(creature);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (creature == null) {
            return;
        }
        if (creature.isPlayable()) {
            return;
        }
        if (creature.getNpcId() == 25659 || creature.getNpcId() == 25660 || creature.getNpcId() == 25661) {
            npcInstance.getAggroList().addDamageHate(creature, 0, 20);
        }
        super.onEvtAttacked(creature, n);
    }

    protected void onEvtAggression(Creature creature, int n) {
        if (creature.isPlayer() || creature.isPet() || creature.isSummon()) {
            return;
        }
        super.onEvtAggression(creature, n);
    }

    public boolean checkAggression(Creature creature) {
        if (creature.isPlayable()) {
            return false;
        }
        return super.checkAggression(creature);
    }
}
