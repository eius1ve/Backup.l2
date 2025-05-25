/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.instancemanager.QuestManager
 *  l2.gameserver.listener.actor.player.OnItemPickupListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.quest.Quest
 */
package quests;

import l2.gameserver.instancemanager.QuestManager;
import l2.gameserver.listener.actor.player.OnItemPickupListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.quest.Quest;
import quests._255_Tutorial;

public static class _255_Tutorial.OnItemPickupListenerImpl
implements OnItemPickupListener {
    public void onItemPickup(Player player, ItemInstance itemInstance) {
        Quest quest = QuestManager.getQuest(_255_Tutorial.class);
        if (itemInstance.getItemId() == 6353 || itemInstance.getItemId() == 57) {
            if (itemInstance.getItemId() == 57 && !player.getVarB("QUEST_STATE_OF_255_ADENA_GIVEN")) {
                player.processQuestEvent(quest.getName(), "CE2097152", null);
                player.setVar("QUEST_STATE_OF_255_ADENA_GIVEN", "true", -1L);
            }
            if (itemInstance.getItemId() == 6353 && !player.getVarB("QUEST_STATE_OF_255_BLUE_GEM_GIVEN")) {
                player.processQuestEvent(quest.getName(), "CE1048576", null);
                player.setVar("QUEST_STATE_OF_255_BLUE_GEM_GIVEN", "true", -1L);
            }
        }
    }
}
