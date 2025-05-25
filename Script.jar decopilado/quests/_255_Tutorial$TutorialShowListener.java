/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.instancemanager.QuestManager
 *  l2.gameserver.listener.actor.OnCurrentHpDamageListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.quest.Quest
 */
package quests;

import l2.commons.listener.Listener;
import l2.gameserver.instancemanager.QuestManager;
import l2.gameserver.listener.actor.OnCurrentHpDamageListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.quest.Quest;
import quests._255_Tutorial;

public static class _255_Tutorial.TutorialShowListener
implements OnCurrentHpDamageListener {
    public void onCurrentHpDamage(Creature creature, double d, Creature creature2, Skill skill) {
        Quest quest = QuestManager.getQuest(_255_Tutorial.class);
        Player player = creature.getPlayer();
        if (player.getCurrentHpPercents() < 25.0) {
            player.removeListener((Listener)a);
            player.processQuestEvent(quest.getName(), "CE256", null);
        } else if (player.getLevel() > 5) {
            player.removeListener((Listener)a);
        }
    }
}
