/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.instancemanager.QuestManager
 *  l2.gameserver.listener.actor.player.OnLevelUpListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 */
package quests;

import l2.gameserver.instancemanager.QuestManager;
import l2.gameserver.listener.actor.player.OnLevelUpListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import quests._255_Tutorial;

public static class _255_Tutorial.OnLevelUpListenerImpl
implements OnLevelUpListener {
    public void onLevelUp(Player player, int n) {
        QuestState questState = player.getQuestState(_255_Tutorial.class);
        Quest quest = QuestManager.getQuest(_255_Tutorial.class);
        if (questState == null) {
            return;
        }
        switch (n) {
            case 5: {
                player.processQuestEvent(quest.getName(), "CE1024", null);
                break;
            }
            case 6: {
                player.processQuestEvent(quest.getName(), "CE134217728", null);
                break;
            }
            case 7: {
                player.processQuestEvent(quest.getName(), "CE2048", null);
                break;
            }
            case 9: {
                player.processQuestEvent(quest.getName(), "CE268435456", null);
                break;
            }
            case 10: {
                player.processQuestEvent(quest.getName(), "CE536870912", null);
                break;
            }
            case 12: {
                player.processQuestEvent(quest.getName(), "CE1073741824", null);
                break;
            }
            case 15: {
                player.processQuestEvent(quest.getName(), "CE67108864", null);
                break;
            }
            case 20: {
                player.processQuestEvent(quest.getName(), "CE4096", null);
                break;
            }
            case 35: {
                player.processQuestEvent(quest.getName(), "CE16777216", null);
            }
        }
    }
}
