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

public class _627_HeartInSearchOfPower
extends Quest
implements ScriptFile {
    private static final int bwq = 31518;
    private static final int bwr = 31519;
    private static final int bws = 21520;
    private static final int bwt = 21523;
    private static final int bwu = 21524;
    private static final int bwv = 21525;
    private static final int bww = 21526;
    private static final int bwx = 21529;
    private static final int bwy = 21530;
    private static final int bwz = 21531;
    private static final int bwA = 21532;
    private static final int bwB = 21535;
    private static final int bwC = 21536;
    private static final int bwD = 21539;
    private static final int bwE = 21540;
    private static final int bwF = 21658;
    private static final int bwG = 7170;
    private static final int bwH = 7171;
    private static final int bwI = 7172;
    private static final int bwJ = 4041;
    private static final int bwK = 4042;
    private static final int bwL = 4043;
    private static final int bwM = 4044;

    public _627_HeartInSearchOfPower() {
        super(1);
        this.addStartNpc(31518);
        this.addTalkId(new int[]{31519});
        this.addQuestItem(new int[]{7171, 7170, 7172});
        this.addKillId(new int[]{21520, 21523, 21524, 21525, 21526, 21529, 21530, 21531, 21532, 21535, 21536, 21539, 21540, 21658});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("temptation_of_power_cookie");
        int n2 = npcInstance.getNpcId();
        if (n2 == 31518) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("temptation_of_power", String.valueOf(11), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "dark_necromancer_q0627_0104.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=627&reply=1") && n == 1) {
                if (questState.getQuestItemsCount(7171) >= 300L) {
                    questState.setCond(3);
                    questState.set("temptation_of_power", String.valueOf(21), true);
                    questState.takeItems(7171, -1L);
                    questState.giveItems(7170, 1L, false);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "dark_necromancer_q0627_0201.htm";
                } else {
                    string2 = "dark_necromancer_q0627_0202.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=627&reply=3") && n == 3) {
                string2 = "dark_necromancer_q0627_0401.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=627&reply=11") && n == 3) {
                if (questState.getQuestItemsCount(7172) >= 1L) {
                    questState.takeItems(7172, -1L);
                    questState.giveItems(57, 100000L, true);
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(true);
                    string2 = "dark_necromancer_q0627_0402.htm";
                } else {
                    string2 = "dark_necromancer_q0627_0403.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=627&reply=12") && n == 3) {
                if (questState.getQuestItemsCount(7172) >= 1L) {
                    questState.takeItems(7172, -1L);
                    questState.giveItems(4043, 13L, true);
                    questState.giveItems(57, 6400L, true);
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(true);
                    string2 = "dark_necromancer_q0627_0402.htm";
                } else {
                    string2 = "dark_necromancer_q0627_0403.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=627&reply=13") && n == 3) {
                if (questState.getQuestItemsCount(7172) >= 1L) {
                    questState.takeItems(7172, -1L);
                    questState.giveItems(4044, 13L, true);
                    questState.giveItems(57, 6400L, true);
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(true);
                    string2 = "dark_necromancer_q0627_0402.htm";
                } else {
                    string2 = "dark_necromancer_q0627_0403.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=627&reply=14") && n == 3) {
                if (questState.getQuestItemsCount(7172) >= 1L) {
                    questState.takeItems(7172, -1L);
                    questState.giveItems(4042, 6L, true);
                    questState.giveItems(57, 13600L, true);
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(true);
                    string2 = "dark_necromancer_q0627_0402.htm";
                } else {
                    string2 = "dark_necromancer_q0627_0403.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=627&reply=15") && n == 3) {
                if (questState.getQuestItemsCount(7172) >= 1L) {
                    questState.takeItems(7172, -1L);
                    questState.giveItems(4041, 3L, true);
                    questState.giveItems(57, 17200L, true);
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(true);
                    string2 = "dark_necromancer_q0627_0402.htm";
                } else {
                    string2 = "dark_necromancer_q0627_0403.htm";
                }
            }
        } else if (n2 == 31519 && string.equalsIgnoreCase("menu_select?ask=627&reply=1") && n == 2) {
            if (questState.getQuestItemsCount(7170) >= 1L) {
                questState.setCond(4);
                questState.set("temptation_of_power", String.valueOf(31), true);
                questState.takeItems(7170, 1L);
                questState.giveItems(7172, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "enfeux_q0627_0301.htm";
            } else {
                string2 = "enfeux_q0627_0302.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("temptation_of_power");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 31518) break;
                if (questState.getPlayer().getLevel() >= 60) {
                    string = "dark_necromancer_q0627_0101.htm";
                    break;
                }
                questState.exitCurrentQuest(true);
                string = "dark_necromancer_q0627_0103.htm";
                break;
            }
            case 2: {
                if (n2 == 31518) {
                    if (n >= 11 && n <= 12) {
                        if (n == 12 && questState.getQuestItemsCount(7171) >= 300L) {
                            questState.set("temptation_of_power_cookie", String.valueOf(1), true);
                            string = "dark_necromancer_q0627_0105.htm";
                            break;
                        }
                        string = "dark_necromancer_q0627_0106.htm";
                        break;
                    }
                    if (n == 21) {
                        string = "dark_necromancer_q0627_0203.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(7172) < 1L || n != 31) break;
                    questState.set("temptation_of_power_cookie", String.valueOf(3), true);
                    string = "dark_necromancer_q0627_0301.htm";
                    break;
                }
                if (n2 != 31519) break;
                if (questState.getQuestItemsCount(7170) >= 1L && n == 21) {
                    questState.set("temptation_of_power_cookie", String.valueOf(2), true);
                    string = "enfeux_q0627_0201.htm";
                    break;
                }
                if (n != 31) break;
                string = "enfeux_q0627_0303.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        long l = questState.getQuestItemsCount(7171);
        int n2 = questState.getInt("temptation_of_power");
        if (n2 == 11) {
            if (n == 21520) {
                if (l < 300L) {
                    questState.rollAndGive(7171, 1, 66.1);
                    l = questState.getQuestItemsCount(7171);
                }
                if (l >= 300L) {
                    questState.set("temptation_of_power", String.valueOf(12), true);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else if (n == 21523) {
                if (l < 300L) {
                    questState.rollAndGive(7171, 1, 66.8);
                    l = questState.getQuestItemsCount(7171);
                }
                if (l >= 300L) {
                    questState.set("temptation_of_power", String.valueOf(12), true);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else if (n == 21524 || n == 21525) {
                if (l < 300L) {
                    questState.rollAndGive(7171, 1, 71.4);
                    l = questState.getQuestItemsCount(7171);
                }
                if (l >= 300L) {
                    questState.set("temptation_of_power", String.valueOf(12), true);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else if (n == 21526) {
                if (l < 300L) {
                    questState.rollAndGive(7171, 1, 79.6);
                    l = questState.getQuestItemsCount(7171);
                }
                if (l >= 300L) {
                    questState.set("temptation_of_power", String.valueOf(12), true);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else if (n == 21529) {
                if (l < 300L) {
                    questState.rollAndGive(7171, 1, 65.9);
                    l = questState.getQuestItemsCount(7171);
                }
                if (l >= 300L) {
                    questState.set("temptation_of_power", String.valueOf(12), true);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else if (n == 21530) {
                if (l < 300L) {
                    questState.rollAndGive(7171, 1, 70.4);
                    l = questState.getQuestItemsCount(7171);
                }
                if (l >= 300L) {
                    questState.set("temptation_of_power", String.valueOf(12), true);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else if (n == 21531 || n == 21658) {
                if (l < 300L) {
                    questState.rollAndGive(7171, 1, 79.1);
                    l = questState.getQuestItemsCount(7171);
                }
                if (l >= 300L) {
                    questState.set("temptation_of_power", String.valueOf(12), true);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else if (n == 21532) {
                if (l < 300L) {
                    questState.rollAndGive(7171, 1, 82.0);
                    l = questState.getQuestItemsCount(7171);
                }
                if (l >= 300L) {
                    questState.set("temptation_of_power", String.valueOf(12), true);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else if (n == 21535) {
                if (l < 300L) {
                    questState.rollAndGive(7171, 1, 82.7);
                    l = questState.getQuestItemsCount(7171);
                }
                if (l >= 300L) {
                    questState.set("temptation_of_power", String.valueOf(12), true);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else if (n == 21536) {
                if (l < 300L) {
                    questState.rollAndGive(7171, 1, 79.8);
                    l = questState.getQuestItemsCount(7171);
                }
                if (l >= 300L) {
                    questState.set("temptation_of_power", String.valueOf(12), true);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else if (n == 21539 || n == 21540) {
                if (l < 300L) {
                    questState.rollAndGive(7171, 1, 87.5);
                    l = questState.getQuestItemsCount(7171);
                }
                if (l >= 300L) {
                    questState.set("temptation_of_power", String.valueOf(12), true);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                }
            }
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
