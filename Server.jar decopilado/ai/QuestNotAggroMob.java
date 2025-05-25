/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.QuestEventType
 *  l2.gameserver.model.quest.QuestState
 */
package ai;

import java.util.List;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.QuestEventType;
import l2.gameserver.model.quest.QuestState;

public class QuestNotAggroMob
extends DefaultAI {
    public QuestNotAggroMob(NpcInstance npcInstance) {
        super(npcInstance);
    }

    public boolean thinkActive() {
        return false;
    }

    public void onEvtAttacked(Creature creature, int n) {
        List list;
        NpcInstance npcInstance = this.getActor();
        Player player = creature.getPlayer();
        if (player != null && (list = player.getQuestsForEvent(npcInstance, QuestEventType.ATTACKED_WITH_QUEST)) != null) {
            for (QuestState questState : list) {
                questState.getQuest().notifyAttack(npcInstance, questState);
            }
        }
    }

    public void onEvtAggression(Creature creature, int n) {
    }
}
