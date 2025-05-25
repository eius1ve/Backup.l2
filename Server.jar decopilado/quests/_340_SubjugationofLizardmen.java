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

public class _340_SubjugationofLizardmen
extends Quest
implements ScriptFile {
    private static int aHU = 30385;
    private static int aHV = 30037;
    private static int acj = 30375;
    private static int aHW = 30989;
    private static int aHX = 20008;
    private static int aHY = 20010;
    private static int aHZ = 20014;
    private static int aIa = 20027;
    private static int aIb = 20024;
    private static int aIc = 25146;
    private static int aId = 4257;
    private static int aIe = 4256;
    private static int aIf = 4255;

    public _340_SubjugationofLizardmen() {
        super(0);
        this.addStartNpc(aHU);
        this.addTalkId(new int[]{aHV, acj, aHW});
        this.addQuestItem(new int[]{aId, aIe, aIf});
        this.addKillId(new int[]{aHX, aHY, aHZ, aIa, aIb, aIc});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("subj_lizardmen");
        if (n == aHU) {
            if (string.equalsIgnoreCase("quest_accept")) {
                if (questState.getPlayer().getLevel() >= 17) {
                    questState.setCond(1);
                    questState.set("subj_lizardmen", String.valueOf(1), true);
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                    string2 = "guard_weisz_q0340_03.htm";
                } else {
                    string2 = "guard_weisz_q0340_02.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=340&reply=1")) {
                string2 = "guard_weisz_q0340_04.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=340&reply=2")) {
                questState.takeItems(aIf, -1L);
                questState.set("subj_lizardmen", String.valueOf(2), true);
                questState.setCond(2);
                string2 = "guard_weisz_q0340_07.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=340&reply=3")) {
                string2 = "guard_weisz_q0340_08.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=340&reply=4")) {
                if (questState.getQuestItemsCount(aIf) >= 30L) {
                    questState.giveItems(57, 4090L);
                    questState.takeItems(aIf, -1L);
                    questState.set("subj_lizardmen", String.valueOf(1), true);
                    string2 = "guard_weisz_q0340_09.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=340&reply=5") && questState.getQuestItemsCount(aIf) >= 30L) {
                questState.giveItems(57, 4090L);
                questState.takeItems(aIf, -1L);
                questState.unset("subj_lizardmen");
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(false);
                string2 = "guard_weisz_q0340_10.htm";
            }
        } else if (n == aHV) {
            if (string.equalsIgnoreCase("menu_select?ask=340&reply=1")) {
                questState.setCond(5);
                questState.set("subj_lizardmen", String.valueOf(5), true);
                questState.playSound("ItemSound.quest_middle");
                string2 = "levian_q0340_02.htm";
            }
        } else if (n == acj) {
            if (string.equalsIgnoreCase("menu_select?ask=340&reply=1")) {
                questState.setCond(3);
                questState.set("subj_lizardmen", String.valueOf(3), true);
                questState.playSound("ItemSound.quest_middle");
                string2 = "priest_adonius_q0340_02.htm";
            }
        } else if (n == aHW && string.equalsIgnoreCase("menu_select?ask=340&reply=1")) {
            if (n2 == 5) {
                questState.setCond(6);
                questState.set("subj_lizardmen", String.valueOf(6), true);
                questState.playSound("ItemSound.quest_middle");
                string2 = "chest_of_bifrons_q0340_02.htm";
                questState.giveItems(4258, 1L);
            } else {
                string2 = "chest_of_bifrons_q0340_03.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("subj_lizardmen");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != aHU) break;
                if (questState.getPlayer().getLevel() < 17 || questState.getPlayer().getLevel() > 22) {
                    string = "guard_weisz_q0340_01.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "guard_weisz_q0340_02.htm";
                break;
            }
            case 2: {
                if (n2 == aHU) {
                    if (n == 1 && questState.getQuestItemsCount(aIf) < 30L) {
                        string = "guard_weisz_q0340_05.htm";
                    } else if (n == 1 && questState.getQuestItemsCount(aIf) >= 30L) {
                        string = "guard_weisz_q0340_06.htm";
                    } else if (n == 2) {
                        string = "guard_weisz_q0340_11.htm";
                    } else if (n >= 3 && n < 7) {
                        string = "guard_weisz_q0340_12.htm";
                    } else if (n == 7) {
                        questState.giveItems(57, 14700L);
                        questState.unset("subj_lizardmen");
                        questState.playSound("ItemSound.quest_finish");
                        this.giveExtraReward(questState.getPlayer());
                        questState.exitCurrentQuest(false);
                        string = "guard_weisz_q0340_13.htm";
                    }
                }
                if (n2 == aHV) {
                    if (n == 4) {
                        string = "levian_q0340_01.htm";
                    } else if (n == 5) {
                        string = "levian_q0340_03.htm";
                    } else if (n == 6) {
                        questState.takeItems(4258, -1L);
                        questState.set("subj_lizardmen", String.valueOf(7), true);
                        questState.setCond(7);
                        questState.playSound("ItemSound.quest_middle");
                        string = "levian_q0340_04.htm";
                    } else if (n == 7) {
                        string = "levian_q0340_05.htm";
                    }
                }
                if (n2 == acj) {
                    if (n == 2) {
                        string = "priest_adonius_q0340_01.htm";
                        break;
                    }
                    if (n == 3 && (questState.getQuestItemsCount(aIe) == 0L || questState.getQuestItemsCount(aId) == 0L)) {
                        string = "priest_adonius_q0340_03.htm";
                        break;
                    }
                    if (n == 3 && questState.getQuestItemsCount(aIe) >= 1L && questState.getQuestItemsCount(aId) >= 1L) {
                        questState.takeItems(aIe, -1L);
                        questState.takeItems(aId, -1L);
                        questState.setCond(4);
                        questState.set("subj_lizardmen", String.valueOf(4), true);
                        questState.playSound("ItemSound.quest_middle");
                        string = "priest_adonius_q0340_04.htm";
                        break;
                    }
                    if (n == 4) {
                        string = "priest_adonius_q0340_05.htm";
                        break;
                    }
                    if (n < 5) break;
                    string = "priest_adonius_q0340_06.htm";
                    break;
                }
                if (n2 != aHW || n != 5) break;
                string = "chest_of_bifrons_q0340_01.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("subj_lizardmen");
        if (questState.getState() != 2) {
            return null;
        }
        if (n == aHX || n == aHY || n == aHZ) {
            if (n2 == 1 && questState.getQuestItemsCount(aIf) < 30L) {
                questState.rollAndGive(aIf, 1, 63.0);
                if (questState.getQuestItemsCount(aIf) >= 30L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == aIa || n == aIb) {
            if (n2 == 3) {
                if (questState.getQuestItemsCount(aIe) == 0L) {
                    if (Rnd.get((int)100) <= 18) {
                        questState.giveItems(aIe, 1L);
                        questState.playSound("ItemSound.quest_itemget");
                    }
                } else if (questState.getQuestItemsCount(aIe) >= 1L && questState.getQuestItemsCount(aId) == 0L && Rnd.get((int)100) <= 18) {
                    questState.giveItems(aId, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == aIc) {
            questState.addSpawn(aHW);
        }
        return null;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
