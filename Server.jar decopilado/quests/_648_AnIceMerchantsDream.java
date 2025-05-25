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
import quests._115_TheOtherSideOfTruth;

public class _648_AnIceMerchantsDream
extends Quest
implements ScriptFile {
    private static final int bBJ = 32020;
    private static final int bBK = 32023;
    private static final int bBL = 8057;
    private static final int bBM = 8077;
    private static final int bBN = 8078;
    private static final int bBO = 22094;
    private static final int bBP = 22092;
    private static final int bBQ = 22080;
    private static final int bBR = 22093;
    private static final int bBS = 22085;
    private static final int bBT = 22086;
    private static final int bBU = 22081;
    private static final int bBV = 22082;
    private static final int bBW = 22097;
    private static final int bBX = 22084;
    private static final int bBY = 22088;
    private static final int bBZ = 22087;
    private static final int bCa = 22089;
    private static final int bCb = 22090;
    private static final int bCc = 22096;
    private static final int bCd = 22098;

    public _648_AnIceMerchantsDream() {
        super(1);
        this.addStartNpc(new int[]{32020, 32023});
        this.addTalkId(new int[]{32023});
        this.addKillId(new int[]{22094, 22092, 22080, 22093, 22085, 22086, 22081, 22082, 22097, 22084, 22088, 22087, 22089, 22090, 22096, 22098});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("ice_merchants_dream");
        int n2 = questState.getInt("ice_merchants_dream_ex");
        int n3 = npcInstance.getNpcId();
        QuestState questState2 = questState.getPlayer().getQuestState(_115_TheOtherSideOfTruth.class);
        if (n3 == 32020) {
            if (string.equalsIgnoreCase("quest_accept")) {
                if (questState.getPlayer().getLevel() >= 53) {
                    questState.playSound("ItemSound.quest_accept");
                    if (questState2 != null && questState2.isCompleted()) {
                        string2 = "repre_q0648_05.htm";
                        questState.setCond(2);
                        questState.setState(2);
                        questState.playSound("ItemSound.quest_accept");
                        questState.set("ice_merchants_dream", 2);
                    } else {
                        string2 = "repre_q0648_04.htm";
                        questState.setCond(1);
                        questState.setState(2);
                        questState.playSound("ItemSound.quest_accept");
                        questState.set("ice_merchants_dream", 1);
                    }
                }
            } else if (string.equalsIgnoreCase("reply_1") && n >= 1) {
                string2 = questState2 != null && questState2.isCompleted() ? "repre_q0648_13.htm" : "repre_q0648_12.htm";
            } else if (string.equalsIgnoreCase("reply_2") && n >= 1) {
                string2 = questState2 != null && questState2.isCompleted() ? "repre_q0648_21.htm" : "repre_q0648_20.htm";
            } else if (string.equalsIgnoreCase("reply_3") && n >= 1) {
                if (questState2 != null && questState2.isCompleted()) {
                    string2 = "repre_q0648_23.htm";
                } else {
                    questState.exitCurrentQuest(true);
                    questState.playSound("ItemSound.quest_finish");
                    string2 = "repre_q0648_22.htm";
                }
            } else if (string.equalsIgnoreCase("reply_4") && n >= 1) {
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
                string2 = "repre_q0648_24.htm";
            } else if (string.equalsIgnoreCase("reply_10") && n >= 1 && (questState2 == null || !questState2.isCompleted())) {
                if (questState.getQuestItemsCount(8077) + questState.getQuestItemsCount(8078) > 0L) {
                    questState.giveItems(57, 300L * questState.getQuestItemsCount(8077) + 1200L * questState.getQuestItemsCount(8078));
                    questState.takeItems(8077, -1L);
                    questState.takeItems(8078, -1L);
                    string2 = "repre_q0648_14.htm";
                } else {
                    string2 = "repre_q0648_16a.htm";
                }
            } else if (string.equalsIgnoreCase("reply_11") && n >= 1 && questState2 != null && questState2.isCompleted()) {
                if (questState.getQuestItemsCount(8077) + questState.getQuestItemsCount(8078) > 0L) {
                    questState.giveItems(57, 300L * questState.getQuestItemsCount(8077) + 1200L * questState.getQuestItemsCount(8078));
                    questState.takeItems(8077, -1L);
                    questState.takeItems(8078, -1L);
                    string2 = "repre_q0648_15.htm";
                } else {
                    string2 = "repre_q0648_16a.htm";
                }
            }
        } else if (n3 == 32023) {
            if (string.equalsIgnoreCase("reply_1") && n >= 1 && questState.getQuestItemsCount(8077) > 0L) {
                if (n2 == 0) {
                    int n4 = Rnd.get((int)4) + 1;
                    int n5 = n4 * 10;
                    questState.set("ice_merchants_dream_ex", n5);
                    string2 = "ice_lathe_q0648_05.htm";
                }
            } else if (string.equalsIgnoreCase("reply_2") && n >= 1 && n2 > 0 && questState.getQuestItemsCount(8077) > 0L) {
                questState.takeItems(8077, 1L);
                int n6 = n2 + 1;
                questState.set("ice_merchants_dream_ex", n6);
                string2 = "ice_lathe_q0648_06.htm";
                questState.playSound("ItemSound2.broken_key");
            } else if (string.equalsIgnoreCase("reply_3") && n >= 1 && n2 > 0 && questState.getQuestItemsCount(8077) > 0L) {
                questState.takeItems(8077, 1L);
                int n7 = n2 + 2;
                questState.set("ice_merchants_dream_ex", n7);
                string2 = "ice_lathe_q0648_07.htm";
                questState.playSound("ItemSound2.broken_key");
            } else if (string.equalsIgnoreCase("reply_4") && n >= 1 && n2 > 0) {
                int n8 = n2 / 10;
                int n9 = n2 - n8 * 10;
                if (n8 == n9) {
                    questState.giveItems(8078, 1L);
                    string2 = "ice_lathe_q0648_08.htm";
                    questState.playSound("ItemSound3.sys_enchant_success");
                } else {
                    string2 = "ice_lathe_q0648_09.htm";
                    questState.playSound("ItemSound3.sys_enchant_failed");
                }
                questState.set("ice_merchants_dream_ex", 0);
            } else if (string.equalsIgnoreCase("reply_5") && n >= 1 && n2 > 0) {
                int n10 = n2 / 10;
                int n11 = n2 - n10 * 10;
                if (n10 == n11 + 2) {
                    questState.giveItems(8078, 1L);
                    string2 = "ice_lathe_q0648_10.htm";
                    questState.playSound("ItemSound3.sys_enchant_success");
                } else {
                    string2 = "ice_lathe_q0648_11.htm";
                    questState.playSound("ItemSound3.sys_enchant_failed");
                }
                questState.set("ice_merchants_dream_ex", 0);
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("ice_merchants_dream");
        int n2 = questState.getInt("ice_merchants_dream_ex");
        int n3 = npcInstance.getNpcId();
        int n4 = questState.getState();
        QuestState questState2 = questState.getPlayer().getQuestState(_115_TheOtherSideOfTruth.class);
        switch (n4) {
            case 1: {
                if (n3 == 32020) {
                    if (questState.getPlayer().getLevel() < 53) {
                        string = "repre_q0648_01.htm";
                        questState.exitCurrentQuest(true);
                    }
                    if (questState.getPlayer().getLevel() < 53) break;
                    if (questState2 != null && questState2.isCompleted()) {
                        string = "repre_q0648_02.htm";
                        break;
                    }
                    string = "repre_q0648_03.htm";
                    break;
                }
                if (n3 != 32023) break;
                string = "ice_lathe_q0648_01.htm";
                break;
            }
            case 2: {
                if (n3 == 32020) {
                    if (questState.getQuestItemsCount(8077) == 0L && questState.getQuestItemsCount(8078) == 0L && n >= 1) {
                        string = questState2 != null && questState2.isCompleted() ? "repre_q0648_09.htm" : "repre_q0648_08.htm";
                        if (n != 1 || questState2 == null || !questState2.isCompleted()) break;
                        questState.setCond(2);
                        questState.set("ice_merchants_dream", 2);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(8077) + questState.getQuestItemsCount(8078) <= 0L || n < 1) break;
                    string = questState2 != null && questState2.isCompleted() ? "repre_q0648_11.htm" : "repre_q0648_10.htm";
                    if (n != 1 || questState2 == null || !questState2.isCompleted()) break;
                    questState.setCond(2);
                    questState.set("ice_merchants_dream", 2);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (n3 != 32023 || n < 1) break;
                if (questState.getQuestItemsCount(8077) == 0L) {
                    string = "ice_lathe_q0648_02.htm";
                    break;
                }
                if (n2 == 0 && questState.getQuestItemsCount(8077) > 0L) {
                    string = "ice_lathe_q0648_03.htm";
                    questState.set("ice_merchants_dream_ex", 0);
                    break;
                }
                if (n2 <= 0) break;
                string = "ice_lathe_q0648_04.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        QuestState questState2 = questState.getPlayer().getQuestState(_115_TheOtherSideOfTruth.class);
        int n = questState.getInt("ice_merchants_dream");
        boolean bl = questState2 != null && questState2.isCompleted() && n >= 2;
        int n2 = npcInstance.getNpcId();
        if (n >= 1) {
            if (n2 == 22092 || n2 == 22081 || n2 == 22082) {
                questState.rollAndGive(8077, 1, 50.0);
            } else {
                if (bl) {
                    questState.rollAndGive(8057, 1, 4.8);
                }
                questState.rollAndGive(8077, 1, 28.5);
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
