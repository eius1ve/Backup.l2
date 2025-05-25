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

public class _376_ExplorationOfGiantsCavePart1
extends Quest
implements ScriptFile {
    private static final int aTb = 31147;
    private static final int aTc = 30182;
    private static final int aTd = 20647;
    private static final int aTe = 20648;
    private static final int aTf = 20649;
    private static final int aTg = 20650;
    private static final int aTh = 5922;
    private static final int aTi = 5923;
    private static final int aTj = 5924;
    private static final int aTk = 5925;
    private static final int aTl = 5926;
    private static final int aTm = 5927;
    private static final int aTn = 5928;
    private static final int aTo = 5929;
    private static final int aTp = 5930;
    private static final int aTq = 5931;
    private static final int aTr = 5932;
    private static final int aTs = 5933;
    private static final int aTt = 5934;
    private static final int aTu = 5935;
    private static final int aTv = 5936;
    private static final int aTw = 5937;
    private static final int aTx = 5938;
    private static final int aTy = 5939;
    private static final int aTz = 5940;
    private static final int aTA = 5941;
    private static final int aTB = 5891;
    private static final int aTC = 5890;
    private static final int aTD = 5944;
    private static final int aTE = 5354;
    private static final int aTF = 5346;
    private static final int aTG = 5416;
    private static final int aTH = 5418;
    private static final int aTI = 5424;
    private static final int aTJ = 5340;
    private static final int aTK = 5332;
    private static final int aTL = 5334;

    public _376_ExplorationOfGiantsCavePart1() {
        super(1);
        this.addStartNpc(31147);
        this.addTalkId(new int[]{31147, 30182});
        this.addKillId(new int[]{20647, 20648, 20649, 20650});
        this.addQuestItem(new int[]{5891, 5890});
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getState();
        int n2 = npcInstance.getNpcId();
        switch (n) {
            case 1: {
                if (questState.getPlayer().getLevel() >= 51) {
                    string = "sobling_q0376_01.htm";
                    break;
                }
                string = "sobling_q0376_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n2 == 31147) {
                    if (!(questState.getCond() < 1 || questState.getQuestItemsCount(5890) != 0L || questState.getQuestItemsCount(5922) >= 1L && questState.getQuestItemsCount(5923) >= 1L && questState.getQuestItemsCount(5924) >= 1L && questState.getQuestItemsCount(5925) >= 1L && questState.getQuestItemsCount(5926) >= 1L || questState.getQuestItemsCount(5927) >= 1L && questState.getQuestItemsCount(5928) >= 1L && questState.getQuestItemsCount(5929) >= 1L && questState.getQuestItemsCount(5930) >= 1L && questState.getQuestItemsCount(5931) >= 1L || questState.getQuestItemsCount(5932) >= 1L && questState.getQuestItemsCount(5933) >= 1L && questState.getQuestItemsCount(5934) >= 1L && questState.getQuestItemsCount(5935) >= 1L && questState.getQuestItemsCount(5936) >= 1L || questState.getQuestItemsCount(5937) >= 1L && questState.getQuestItemsCount(5938) >= 1L && questState.getQuestItemsCount(5939) >= 1L && questState.getQuestItemsCount(5940) >= 1L && questState.getQuestItemsCount(5941) >= 1L)) {
                        string = "sobling_q0376_04.htm";
                        break;
                    }
                    if (questState.getCond() >= 1 && questState.getQuestItemsCount(5890) == 0L && (questState.getQuestItemsCount(5922) >= 1L && questState.getQuestItemsCount(5923) >= 1L && questState.getQuestItemsCount(5924) >= 1L && questState.getQuestItemsCount(5925) >= 1L && questState.getQuestItemsCount(5926) >= 1L || questState.getQuestItemsCount(5927) >= 1L && questState.getQuestItemsCount(5928) >= 1L && questState.getQuestItemsCount(5929) >= 1L && questState.getQuestItemsCount(5930) >= 1L && questState.getQuestItemsCount(5931) >= 1L || questState.getQuestItemsCount(5932) >= 1L && questState.getQuestItemsCount(5933) >= 1L && questState.getQuestItemsCount(5934) >= 1L && questState.getQuestItemsCount(5935) >= 1L && questState.getQuestItemsCount(5936) >= 1L || questState.getQuestItemsCount(5937) >= 1L && questState.getQuestItemsCount(5938) >= 1L && questState.getQuestItemsCount(5939) >= 1L && questState.getQuestItemsCount(5940) >= 1L && questState.getQuestItemsCount(5941) >= 1L)) {
                        if (questState.getQuestItemsCount(5922) > 0L && questState.getQuestItemsCount(5923) > 0L && questState.getQuestItemsCount(5924) > 0L && questState.getQuestItemsCount(5925) > 0L && questState.getQuestItemsCount(5926) > 0L) {
                            questState.takeItems(5922, 1L);
                            questState.takeItems(5923, 1L);
                            questState.takeItems(5924, 1L);
                            questState.takeItems(5925, 1L);
                            questState.takeItems(5926, 1L);
                            this.giveExtraReward(questState.getPlayer());
                            if (Rnd.get((int)2) == 0) {
                                questState.giveItems(5416, 1L);
                            } else {
                                questState.giveItems(5418, 1L);
                            }
                        }
                        if (questState.getQuestItemsCount(5927) > 0L && questState.getQuestItemsCount(5928) > 0L && questState.getQuestItemsCount(5929) > 0L && questState.getQuestItemsCount(5930) > 0L && questState.getQuestItemsCount(5931) > 0L) {
                            questState.takeItems(5927, 1L);
                            questState.takeItems(5928, 1L);
                            questState.takeItems(5929, 1L);
                            questState.takeItems(5930, 1L);
                            questState.takeItems(5931, 1L);
                            this.giveExtraReward(questState.getPlayer());
                            if (Rnd.get((int)2) == 0) {
                                questState.giveItems(5424, 1L);
                            } else {
                                questState.giveItems(5340, 1L);
                            }
                        }
                        if (questState.getQuestItemsCount(5932) > 0L && questState.getQuestItemsCount(5933) > 0L && questState.getQuestItemsCount(5934) > 0L && questState.getQuestItemsCount(5935) > 0L && questState.getQuestItemsCount(5936) > 0L) {
                            questState.takeItems(5932, 1L);
                            questState.takeItems(5933, 1L);
                            questState.takeItems(5934, 1L);
                            questState.takeItems(5935, 1L);
                            questState.takeItems(5936, 1L);
                            this.giveExtraReward(questState.getPlayer());
                            if (Rnd.get((int)2) == 0) {
                                questState.giveItems(5332, 1L);
                            } else {
                                questState.giveItems(5334, 1L);
                            }
                        }
                        if (questState.getQuestItemsCount(5937) > 0L && questState.getQuestItemsCount(5938) > 0L && questState.getQuestItemsCount(5939) > 0L && questState.getQuestItemsCount(5940) > 0L && questState.getQuestItemsCount(5941) > 0L) {
                            questState.takeItems(5937, 1L);
                            questState.takeItems(5938, 1L);
                            questState.takeItems(5939, 1L);
                            questState.takeItems(5940, 1L);
                            questState.takeItems(5941, 1L);
                            this.giveExtraReward(questState.getPlayer());
                            if (Rnd.get((int)2) == 0) {
                                questState.giveItems(5354, 1L);
                            } else {
                                questState.giveItems(5346, 1L);
                            }
                        }
                        string = "sobling_q0376_05.htm";
                        break;
                    }
                    if (questState.getCond() == 1 && questState.getQuestItemsCount(5890) == 1L) {
                        questState.setCond(2);
                        string = "sobling_q0376_08.htm";
                        break;
                    }
                    if (questState.getCond() != 2 || questState.getQuestItemsCount(5890) != 1L) break;
                    string = "sobling_q0376_09.htm";
                    break;
                }
                if (n2 != 30182) break;
                if (questState.getCond() == 2 && questState.getQuestItemsCount(5890) >= 1L) {
                    string = "cliff_q0376_01.htm";
                    break;
                }
                if (questState.getCond() != 2 || questState.getQuestItemsCount(5892) < 1L) break;
                string = "cliff_q0376_03.htm";
            }
        }
        return string;
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.giveItems(5891, 1L);
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "sobling_q0376_03.htm";
        } else if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(5922) >= 1L && questState.getQuestItemsCount(5923) >= 1L && questState.getQuestItemsCount(5924) >= 1L && questState.getQuestItemsCount(5925) >= 1L && questState.getQuestItemsCount(5926) >= 1L || questState.getQuestItemsCount(5927) >= 1L && questState.getQuestItemsCount(5928) >= 1L && questState.getQuestItemsCount(5929) >= 1L && questState.getQuestItemsCount(5930) >= 1L && questState.getQuestItemsCount(5931) >= 1L || questState.getQuestItemsCount(5932) >= 1L && questState.getQuestItemsCount(5933) >= 1L && questState.getQuestItemsCount(5934) >= 1L && questState.getQuestItemsCount(5935) >= 1L && questState.getQuestItemsCount(5936) >= 1L || questState.getQuestItemsCount(5937) >= 1L && questState.getQuestItemsCount(5938) >= 1L && questState.getQuestItemsCount(5939) >= 1L && questState.getQuestItemsCount(5940) >= 1L && questState.getQuestItemsCount(5941) >= 1L) {
            if (questState.getQuestItemsCount(5922) > 0L && questState.getQuestItemsCount(5923) > 0L && questState.getQuestItemsCount(5924) > 0L && questState.getQuestItemsCount(5925) > 0L && questState.getQuestItemsCount(5926) > 0L) {
                questState.takeItems(5922, 1L);
                questState.takeItems(5923, 1L);
                questState.takeItems(5924, 1L);
                questState.takeItems(5925, 1L);
                questState.takeItems(5926, 1L);
                this.giveExtraReward(questState.getPlayer());
                if (Rnd.get((int)2) == 0) {
                    questState.giveItems(5416, 1L);
                } else {
                    questState.giveItems(5418, 1L);
                }
            }
            if (questState.getQuestItemsCount(5927) > 0L && questState.getQuestItemsCount(5928) > 0L && questState.getQuestItemsCount(5929) > 0L && questState.getQuestItemsCount(5930) > 0L && questState.getQuestItemsCount(5931) > 0L) {
                questState.takeItems(5927, 1L);
                questState.takeItems(5928, 1L);
                questState.takeItems(5929, 1L);
                questState.takeItems(5930, 1L);
                questState.takeItems(5931, 1L);
                this.giveExtraReward(questState.getPlayer());
                if (Rnd.get((int)2) == 0) {
                    questState.giveItems(5424, 1L);
                } else {
                    questState.giveItems(5340, 1L);
                }
            }
            if (questState.getQuestItemsCount(5932) > 0L && questState.getQuestItemsCount(5933) > 0L && questState.getQuestItemsCount(5934) > 0L && questState.getQuestItemsCount(5935) > 0L && questState.getQuestItemsCount(5936) > 0L) {
                questState.takeItems(5932, 1L);
                questState.takeItems(5933, 1L);
                questState.takeItems(5934, 1L);
                questState.takeItems(5935, 1L);
                questState.takeItems(5936, 1L);
                this.giveExtraReward(questState.getPlayer());
                if (Rnd.get((int)2) == 0) {
                    questState.giveItems(5332, 1L);
                } else {
                    questState.giveItems(5334, 1L);
                }
            }
            if (questState.getQuestItemsCount(5937) > 0L && questState.getQuestItemsCount(5938) > 0L && questState.getQuestItemsCount(5939) > 0L && questState.getQuestItemsCount(5940) > 0L && questState.getQuestItemsCount(5941) > 0L) {
                questState.takeItems(5937, 1L);
                questState.takeItems(5938, 1L);
                questState.takeItems(5939, 1L);
                questState.takeItems(5940, 1L);
                questState.takeItems(5941, 1L);
                this.giveExtraReward(questState.getPlayer());
                if (Rnd.get((int)2) == 0) {
                    questState.giveItems(5354, 1L);
                } else {
                    questState.giveItems(5346, 1L);
                }
            }
            string2 = "sobling_q0376_05.htm";
        } else if (string.equalsIgnoreCase("reply_1")) {
            string2 = "sobling_q0376_04.htm";
        } else if (string.equalsIgnoreCase("reply_2")) {
            string2 = "sobling_q0376_06.htm";
        } else if (string.equalsIgnoreCase("reply_3")) {
            questState.takeItems(5891, 1L);
            questState.exitCurrentQuest(true);
            questState.playSound("ItemSound.quest_finish");
            string2 = "sobling_q0376_07.htm";
        } else if (string.equalsIgnoreCase("reply_4")) {
            questState.giveItems(5892, 1L);
            questState.takeItems(5890, 1L);
            questState.setCond(3);
            string2 = "cliff_q0376_02.htm";
            questState.playSound("ItemSound.quest_middle");
        }
        return string2;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20647) {
            if (questState.getQuestItemsCount(5890) == 0L && questState.getQuestItemsCount(5892) == 0L) {
                questState.rollAndGive(5890, 1, 0.2);
            }
            questState.rollAndGive(5944, 1, 2.6);
        } else if (n == 20648) {
            if (questState.getQuestItemsCount(5890) == 0L && questState.getQuestItemsCount(5892) == 0L) {
                questState.rollAndGive(5890, 1, 0.2);
            }
            questState.rollAndGive(5944, 1, 2.6);
        } else if (n == 20649) {
            if (questState.getQuestItemsCount(5890) == 0L && questState.getQuestItemsCount(5892) == 0L) {
                questState.rollAndGive(5890, 1, 0.2);
            }
            questState.rollAndGive(5944, 1, 2.6);
        } else if (n == 20650) {
            if (questState.getQuestItemsCount(5890) == 0L && questState.getQuestItemsCount(5892) == 0L) {
                questState.rollAndGive(5890, 1, 0.2);
            }
            questState.rollAndGive(5944, 1, 2.6);
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
