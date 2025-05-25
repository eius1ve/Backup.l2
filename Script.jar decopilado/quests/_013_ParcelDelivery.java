/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _013_ParcelDelivery
extends Quest
implements ScriptFile {
    private static final int JO = 7263;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _013_ParcelDelivery() {
        super(0);
        this.addStartNpc(31274);
        this.addTalkId(new int[]{31274});
        this.addTalkId(new int[]{31539});
        this.addQuestItem(new int[]{7263});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("mineral_trader_fundin_q0013_0104.htm")) {
            questState.setCond(1);
            questState.giveItems(7263, 1L);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("warsmith_vulcan_q0013_0201.htm") && questState.getQuestItemsCount(7263) > 0L) {
            questState.takeItems(7263, -1L);
            questState.giveItems(57, 82656L, true);
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(false);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 31274) {
            if (n2 == 0) {
                if (questState.getPlayer().getLevel() >= 74) {
                    string = "mineral_trader_fundin_q0013_0101.htm";
                } else {
                    string = "mineral_trader_fundin_q0013_0103.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n2 == 1) {
                string = "mineral_trader_fundin_q0013_0105.htm";
            }
        } else if (n == 31539 && n2 == 1 && questState.getQuestItemsCount(7263) == 1L) {
            string = "warsmith_vulcan_q0013_0101.htm";
        }
        return string;
    }
}
