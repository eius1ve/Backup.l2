/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.QuestEventType
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.tables.SkillTable
 */
package ai;

import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.QuestEventType;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.tables.SkillTable;

public class Quest421FairyTree
extends Fighter {
    private static final Skill f = SkillTable.getInstance().getInfo(4243, 1);

    public Quest421FairyTree(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.startImmobilized();
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (creature != null) {
            List list;
            Player player;
            if (creature.isPlayer() && Rnd.chance((int)29)) {
                f.getEffects((Creature)npcInstance, creature, false, false);
            } else if (creature.isPet() && (player = creature.getPlayer()) != null && (list = player.getQuestsForEvent(npcInstance, QuestEventType.ATTACKED_WITH_QUEST)) != null) {
                for (QuestState questState : list) {
                    questState.getQuest().notifyAttack(npcInstance, questState);
                }
            }
        }
    }

    protected boolean randomWalk() {
        return false;
    }
}
