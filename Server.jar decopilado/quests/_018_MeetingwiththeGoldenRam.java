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

public class _018_MeetingwiththeGoldenRam
extends Quest
implements ScriptFile {
    private static final int JX = 7245;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _018_MeetingwiththeGoldenRam() {
        super(0);
        this.addStartNpc(31314);
        this.addTalkId(new int[]{31314});
        this.addTalkId(new int[]{31315});
        this.addTalkId(new int[]{31555});
        this.addQuestItem(new int[]{7245});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("warehouse_chief_donal_q0018_0104.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equals("freighter_daisy_q0018_0201.htm")) {
            questState.setCond(2);
            questState.giveItems(7245, 1L);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equals("supplier_abercrombie_q0018_0301.htm")) {
            questState.takeItems(7245, -1L);
            questState.giveItems(57, 15000L);
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
        if (n == 31314) {
            if (n2 == 0) {
                if (questState.getPlayer().getLevel() >= 66) {
                    string = "warehouse_chief_donal_q0018_0101.htm";
                } else {
                    string = "warehouse_chief_donal_q0018_0103.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n2 == 1) {
                string = "warehouse_chief_donal_q0018_0105.htm";
            }
        } else if (n == 31315) {
            if (n2 == 1) {
                string = "freighter_daisy_q0018_0101.htm";
            } else if (n2 == 2) {
                string = "freighter_daisy_q0018_0202.htm";
            }
        } else if (n == 31555 && n2 == 2 && questState.getQuestItemsCount(7245) == 1L) {
            string = "supplier_abercrombie_q0018_0201.htm";
        }
        return string;
    }
}
