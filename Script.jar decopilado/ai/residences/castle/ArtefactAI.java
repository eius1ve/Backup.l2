/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CharacterAI
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill$SkillTargetType
 *  l2.gameserver.model.entity.events.impl.SiegeEvent
 *  l2.gameserver.model.entity.events.objects.SiegeClanObject
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai.residences.castle;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.entity.events.objects.SiegeClanObject;
import l2.gameserver.model.instances.NpcInstance;

public class ArtefactAI
extends CharacterAI {
    public ArtefactAI(NpcInstance npcInstance) {
        super((Creature)npcInstance);
    }

    protected void onEvtAggression(Creature creature, int n) {
        NpcInstance npcInstance;
        Player player;
        if (creature == null || (player = creature.getPlayer()) == null || (npcInstance = (NpcInstance)this.getActor()) == null) {
            return;
        }
        SiegeEvent siegeEvent = (SiegeEvent)npcInstance.getEvent(SiegeEvent.class);
        SiegeEvent siegeEvent2 = (SiegeEvent)player.getEvent(SiegeEvent.class);
        SiegeClanObject siegeClanObject = siegeEvent.getSiegeClan("attackers", player.getClan());
        if (siegeEvent2 == null || siegeEvent == siegeEvent2 && siegeClanObject != null) {
            ThreadPoolManager.getInstance().schedule((Runnable)((Object)new notifyGuard(player)), 1000L);
        }
    }

    class notifyGuard
    extends RunnableImpl {
        private HardReference<Player> a;

        public notifyGuard(Player player) {
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
}
