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

public class _152_ShardsOfGolem
extends Quest
implements ScriptFile {
    private static final int Ty = 30035;
    private static final int Tz = 30283;
    private static final int TA = 20016;
    private static final int TB = 1008;
    private static final int TC = 1009;
    private static final int TD = 1010;
    private static final int TE = 1011;
    private static final int TF = 23;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _152_ShardsOfGolem() {
        super(0);
        this.addStartNpc(30035);
        this.addTalkId(new int[]{30283});
        this.addKillId(new int[]{20016});
        this.addQuestItem(new int[]{1008, 1009, 1010, 1011});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30035) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                if (questState.getQuestItemsCount(1008) == 0L) {
                    questState.giveItems(1008, 1L);
                }
                string2 = "harry_q0152_04.htm";
            }
        } else if (n == 30283 && string.equalsIgnoreCase("reply=2") && questState.getQuestItemsCount(1008) > 0L) {
            questState.setCond(2);
            questState.takeItems(1008, -1L);
            questState.playSound("ItemSound.quest_middle");
            if (questState.getQuestItemsCount(1009) == 0L) {
                questState.giveItems(1009, 1L);
            }
            string2 = "blacksmith_alltran_q0152_02.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 30035) break;
                if (questState.getPlayer().getLevel() < 10) {
                    string = "harry_q0152_02.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "harry_q0152_03.htm";
                break;
            }
            case 2: {
                if (n == 30035) {
                    if (questState.getQuestItemsCount(1008) != 0L && questState.getQuestItemsCount(1011) == 0L) {
                        string = "harry_q0152_05a.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1009) != 0L && questState.getQuestItemsCount(1011) == 0L) {
                        string = "harry_q0152_05.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1011) == 0L) break;
                    questState.takeItems(1011, -1L);
                    questState.takeItems(1009, -1L);
                    questState.giveItems(23, 1L);
                    questState.addExpAndSp(5000L, 0L);
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(false);
                    string = "harry_q0152_06.htm";
                    break;
                }
                if (n != 30283) break;
                if (questState.getQuestItemsCount(1008) != 0L) {
                    string = "blacksmith_alltran_q0152_01.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1009) != 0L && questState.getQuestItemsCount(1010) < 5L && questState.getQuestItemsCount(1011) == 0L) {
                    string = "blacksmith_alltran_q0152_03.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1009) != 0L && questState.getQuestItemsCount(1010) >= 5L && questState.getQuestItemsCount(1011) == 0L) {
                    questState.setCond(4);
                    questState.takeItems(1010, -1L);
                    if (questState.getQuestItemsCount(1011) == 0L) {
                        questState.giveItems(1011, 1L);
                    }
                    questState.playSound("ItemSound.quest_middle");
                    string = "blacksmith_alltran_q0152_04.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1009) == 0L || questState.getQuestItemsCount(1011) == 0L) break;
                string = "blacksmith_alltran_q0152_05.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20016 && questState.getQuestItemsCount(1010) < 5L) {
            questState.rollAndGive(1010, 1, 30.0);
            if (questState.getQuestItemsCount(1010) >= 5L) {
                questState.setCond(3);
                questState.playSound("ItemSound.quest_middle");
            }
        }
        return null;
    }
}
