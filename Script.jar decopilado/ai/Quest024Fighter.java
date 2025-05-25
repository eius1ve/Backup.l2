/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.instancemanager.QuestManager
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.World
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 */
package ai;

import l2.gameserver.ai.Fighter;
import l2.gameserver.instancemanager.QuestManager;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import quests._024_InhabitantsOfTheForestOfTheDead;

public class Quest024Fighter
extends Fighter {
    public Quest024Fighter(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected boolean thinkActive() {
        Quest quest = QuestManager.getQuest(_024_InhabitantsOfTheForestOfTheDead.class);
        if (quest != null) {
            for (Player player : World.getAroundPlayers((GameObject)this.getActor(), (int)300, (int)200)) {
                QuestState questState = player.getQuestState(_024_InhabitantsOfTheForestOfTheDead.class);
                if (questState == null || questState.getCond() != 3) continue;
                quest.notifyEvent("see_creature", questState, this.getActor());
            }
        }
        return super.thinkActive();
    }
}
