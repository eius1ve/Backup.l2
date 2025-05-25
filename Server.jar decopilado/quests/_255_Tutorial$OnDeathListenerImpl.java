/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.instancemanager.QuestManager
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.quest.Quest
 */
package quests;

import l2.gameserver.instancemanager.QuestManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.quest.Quest;
import quests._255_Tutorial;

public class _255_Tutorial.OnDeathListenerImpl
implements OnDeathListener {
    final Quest q = QuestManager.getQuest(_255_Tutorial.class);

    public void onDeath(Creature creature, Creature creature2) {
        if (creature.getPlayer() != null && creature.getPlayer().getLevel() < 9) {
            creature.getPlayer().processQuestEvent(this.q.getName(), "CE512", null);
        }
    }
}
