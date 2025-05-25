/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill$SkillTargetType
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai.residences.castle;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;

class ArtefactAI.notifyGuard
extends RunnableImpl {
    private HardReference<Player> a;

    public ArtefactAI.notifyGuard(Player player) {
        this.a = player.getRef();
    }

    public void runImpl() throws Exception {
        NpcInstance npcInstance;
        Player player = (Player)this.a.get();
        if (player == null || (npcInstance = (NpcInstance)ArtefactAI.this.getActor()) == null) {
            return;
        }
        for (NpcInstance npcInstance2 : npcInstance.getAroundNpc(1500, 200)) {
            if (!npcInstance2.isSiegeGuard() || !Rnd.chance((int)20)) continue;
            npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)player, (Object)5000);
        }
        if (player.getCastingSkill() != null && player.getCastingSkill().getTargetType() == Skill.SkillTargetType.TARGET_HOLY) {
            ThreadPoolManager.getInstance().schedule((Runnable)((Object)this), 10000L);
        }
    }
}
