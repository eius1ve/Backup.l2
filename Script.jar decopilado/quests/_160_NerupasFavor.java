/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.Player;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _160_NerupasFavor
extends Quest
implements ScriptFile {
    private static final int TY = 1026;
    private static final int TZ = 1027;
    private static final int Ua = 1028;
    private static final int Ub = 1029;
    private static final int Uc = 1060;
    private static final int Ud = 30370;
    private static final int Ue = 30147;
    private static final int Uf = 30149;
    private static final int Ug = 30152;

    public _160_NerupasFavor() {
        super(0);
        this.addStartNpc(30370);
        this.addTalkId(new int[]{30147, 30149, 30152});
        this.addQuestItem(new int[]{1026, 1027, 1028, 1029});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            if (questState.getQuestItemsCount(1026) == 0L) {
                questState.giveItems(1026, 1L);
            }
            string2 = "nerupa_q0311_04.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        Player player = questState.getPlayer();
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 30370) break;
                if (player.getRace() != Race.elf) {
                    string = "nerupa_q0311_00.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (player.getLevel() >= 3) {
                    string = "nerupa_q0311_03.htm";
                    break;
                }
                string = "nerupa_q0311_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n == 30370) {
                    if (questState.getQuestItemsCount(1026) != 0L || questState.getQuestItemsCount(1027) != 0L || questState.getQuestItemsCount(1028) != 0L) {
                        string = "nerupa_q0311_05.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1029) == 0L) break;
                    questState.takeItems(1029, questState.getQuestItemsCount(1029));
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.giveItems(1060, 5L);
                    questState.addExpAndSp(1000L, 0L);
                    questState.exitCurrentQuest(false);
                    string = "nerupa_q0311_06.htm";
                    break;
                }
                if (n == 30147) {
                    if (questState.getQuestItemsCount(1026) != 0L) {
                        questState.takeItems(1026, -1L);
                        if (questState.getQuestItemsCount(1027) == 0L) {
                            questState.giveItems(1027, 1L);
                        }
                        string = "uno_q0311_01.htm";
                        questState.setCond(2);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(1027) != 0L) {
                        string = "uno_q0311_02.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1029) == 0L) break;
                    string = "uno_q0311_03.htm";
                    break;
                }
                if (n == 30149) {
                    if (questState.getQuestItemsCount(1027) != 0L) {
                        questState.takeItems(1027, -1L);
                        if (questState.getQuestItemsCount(1028) == 0L) {
                            questState.giveItems(1028, 1L);
                        }
                        string = "cel_q0311_01.htm";
                        questState.setCond(3);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(1028) != 0L) {
                        string = "cel_q0311_02.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1029) == 0L) break;
                    string = "cel_q0311_03.htm";
                    break;
                }
                if (n != 30152) break;
                if (questState.getQuestItemsCount(1028) != 0L) {
                    questState.takeItems(1028, -1L);
                    if (questState.getQuestItemsCount(1029) != 0L) break;
                    questState.giveItems(1029, 1L);
                    string = "jud_q0311_01.htm";
                    questState.setCond(4);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (questState.getQuestItemsCount(1029) == 0L) break;
                string = "jud_q0311_02.htm";
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
