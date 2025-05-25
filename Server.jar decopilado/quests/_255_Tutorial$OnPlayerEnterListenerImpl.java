/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Config
 *  l2.gameserver.instancemanager.QuestManager
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.quest.Quest
 *  org.apache.commons.lang3.ArrayUtils
 */
package quests;

import l2.commons.listener.Listener;
import l2.gameserver.Config;
import l2.gameserver.instancemanager.QuestManager;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.quest.Quest;
import org.apache.commons.lang3.ArrayUtils;
import quests._255_Tutorial;

public static class _255_Tutorial.OnPlayerEnterListenerImpl
implements OnPlayerEnterListener {
    public void onPlayerEnter(Player player) {
        Quest quest;
        if (ArrayUtils.contains((int[])Config.ALT_INITIAL_QUESTS, (int)255) && (quest = QuestManager.getQuest(_255_Tutorial.class)) != null) {
            if (player.getQuestState(_255_Tutorial.class) == null) {
                quest.newQuestState(player, 2);
                player.processQuestEvent(quest.getName(), "UC", null);
            } else {
                player.processQuestEvent(quest.getName(), "UC", null);
            }
            if (player.getLevel() < 6) {
                player.addListener((Listener)a);
            }
        }
    }
}
