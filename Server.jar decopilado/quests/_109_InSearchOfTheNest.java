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

public class _109_InSearchOfTheNest
extends Quest
implements ScriptFile {
    private static final int Qr = 31553;
    private static final int Qs = 32015;
    private static final int Qt = 31554;
    private static final int Qu = 8083;
    private static final int Qv = 7246;
    private static final int Qw = 7247;

    public _109_InSearchOfTheNest() {
        super(0);
        this.addStartNpc(31553);
        this.addTalkId(new int[]{32015, 31554});
        this.addQuestItem(new int[]{8083});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("in_search_of_nest");
        int n2 = npcInstance.getNpcId();
        if (n2 == 31553) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setState(2);
                questState.setCond(1);
                questState.playSound("ItemSound.quest_accept");
                questState.set("in_search_of_nest", String.valueOf(11), true);
                string2 = "merc_cap_peace_q0109_0104.htm";
            } else if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(8083) >= 1L) {
                questState.takeItems(8083, 1L);
                string2 = "merc_cap_peace_q0109_0301.htm";
                questState.set("in_search_of_nest", 31);
                questState.setCond(3);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n2 == 32015) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "corpse_of_scout_q0109_0201.htm";
                questState.set("in_search_of_nest", String.valueOf(21), true);
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
                questState.giveItems(8083, 1L);
            }
        } else if (n2 == 31554 && string.equalsIgnoreCase("reply_3") && n >= 31) {
            string2 = "merc_kahmun_q0109_0401.htm";
            this.giveExtraReward(questState.getPlayer());
            questState.giveItems(57, 5168L);
            questState.exitCurrentQuest(false);
            questState.playSound("ItemSound.quest_finish");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("in_search_of_nest");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 31553) break;
                if (questState.getPlayer().getLevel() >= 66 && (questState.getQuestItemsCount(7246) >= 1L || questState.getQuestItemsCount(7247) >= 1L)) {
                    string = "merc_cap_peace_q0109_0101.htm";
                    break;
                }
                string = "merc_cap_peace_q0109_0103.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n2 == 31553) {
                    if (n == 11) {
                        string = "merc_cap_peace_q0109_0105.htm";
                        break;
                    }
                    if (n == 21 && questState.getQuestItemsCount(8083) >= 1L) {
                        string = "merc_cap_peace_q0109_0201.htm";
                        break;
                    }
                    if (n != 31) break;
                    string = "merc_cap_peace_q0109_0303.htm";
                    break;
                }
                if (n2 == 32015) {
                    if (n == 11) {
                        string = "corpse_of_scout_q0109_0101.htm";
                        break;
                    }
                    if (n != 21) break;
                    string = "corpse_of_scout_q0109_0203.htm";
                    break;
                }
                if (n2 != 31554 || n != 31) break;
                string = "merc_kahmun_q0109_0301.htm";
            }
        }
        return string;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
