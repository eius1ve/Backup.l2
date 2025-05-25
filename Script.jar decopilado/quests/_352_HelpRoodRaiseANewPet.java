/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _352_HelpRoodRaiseANewPet
extends Quest
implements ScriptFile {
    private static int aMA = 31067;
    private static int aMB = 20786;
    private static int aMC = 20787;
    private static int aMD = 5860;
    private static int aME = 5861;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _352_HelpRoodRaiseANewPet() {
        super(0);
        this.addStartNpc(aMA);
        this.addKillId(new int[]{aMB, aMC});
        this.addQuestItem(new int[]{aMD, aME});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == aMA) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("how_about_new_pet", String.valueOf(1), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "pet_manager_rood_q0352_05.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                string2 = "pet_manager_rood_q0352_09.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "pet_manager_rood_q0352_10.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                questState.unset("how_about_new_pet");
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
                string2 = "pet_manager_rood_q0352_11.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                string2 = "pet_manager_rood_q0352_04.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("how_about_new_pet");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != aMA) break;
                if (questState.getPlayer().getLevel() >= 39) {
                    string = "pet_manager_rood_q0352_02.htm";
                    break;
                }
                questState.exitCurrentQuest(true);
                string = "pet_manager_rood_q0352_01.htm";
                break;
            }
            case 2: {
                if (n2 != aMA || n != 1) break;
                if (questState.getQuestItemsCount(aMD) < 1L && questState.getQuestItemsCount(aME) < 1L) {
                    string = "pet_manager_rood_q0352_06.htm";
                    break;
                }
                if (questState.getQuestItemsCount(aMD) >= 1L && questState.getQuestItemsCount(aME) < 1L) {
                    if (questState.getQuestItemsCount(aMD) >= 10L) {
                        questState.giveItems(57, questState.getQuestItemsCount(aMD) * 34L + 4000L);
                    } else {
                        questState.giveItems(57, questState.getQuestItemsCount(aMD) * 34L + 2000L);
                    }
                    questState.takeItems(aMD, -1L);
                    this.giveExtraReward(questState.getPlayer());
                    string = "pet_manager_rood_q0352_07.htm";
                    break;
                }
                if (questState.getQuestItemsCount(aME) < 1L) break;
                questState.giveItems(57, 4000L + (questState.getQuestItemsCount(aMD) * 34L + questState.getQuestItemsCount(aME) * 1025L));
                this.giveExtraReward(questState.getPlayer());
                questState.takeItems(aME, -1L);
                questState.takeItems(aMD, -1L);
                string = "pet_manager_rood_q0352_08.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("how_about_new_pet");
        int n2 = npcInstance.getNpcId();
        if (n == 1) {
            if (n2 == aMB) {
                int n3 = Rnd.get((int)100);
                if (n3 < 46) {
                    questState.giveItems(aMD, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                } else if (n3 < 48) {
                    questState.giveItems(aME, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (n2 == aMC) {
                int n4 = Rnd.get((int)100);
                if (n4 < 69) {
                    questState.giveItems(aMD, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                } else if (n4 < 71) {
                    questState.giveItems(aME, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        }
        return null;
    }
}
