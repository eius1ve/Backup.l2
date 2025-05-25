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

public class _408_PathToElvenwizard
extends Quest
implements ScriptFile {
    private static final int aYs = 30157;
    private static final int aYt = 30371;
    private static final int aYu = 30414;
    private static final int aYv = 30423;
    private static final int aYw = 20019;
    private static final int aYx = 20466;
    private static final int aYy = 20047;
    private static final int aYz = 1218;
    private static final int aYA = 1219;
    private static final int aYB = 1220;
    private static final int aYC = 1221;
    private static final int aYD = 1222;
    private static final int aYE = 1223;
    private static final int aYF = 1224;
    private static final int aYG = 1225;
    private static final int aYH = 1226;
    private static final int aYI = 1229;
    private static final int aYJ = 1230;
    private static final int aYK = 1272;
    private static final int aYL = 1273;
    private static final int aYM = 1274;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _408_PathToElvenwizard() {
        super(0);
        this.addStartNpc(30414);
        this.addTalkId(new int[]{30157});
        this.addTalkId(new int[]{30371});
        this.addTalkId(new int[]{30423});
        this.addKillId(new int[]{20019});
        this.addKillId(new int[]{20466});
        this.addKillId(new int[]{20047});
        this.addQuestItem(new int[]{1218, 1229, 1224, 1222, 1272, 1220, 1273, 1221, 1274, 1226, 1223, 1219, 1225});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("1")) {
            if (questState.getPlayer().getClassId().getId() != 25) {
                string2 = questState.getPlayer().getClassId().getId() == 26 ? "rogellia_q0408_02a.htm" : "rogellia_q0408_03.htm";
            } else if (questState.getPlayer().getLevel() < 18) {
                string2 = "rogellia_q0408_04.htm";
            } else if (questState.getQuestItemsCount(1230) > 0L) {
                string2 = "rogellia_q0408_05.htm";
            } else {
                questState.setState(2);
                questState.setCond(1);
                questState.playSound("ItemSound.quest_accept");
                questState.giveItems(1229, 1L);
                string2 = "rogellia_q0408_06.htm";
            }
        } else if (string.equalsIgnoreCase("408_1")) {
            if (questState.getQuestItemsCount(1220) > 0L) {
                string2 = "rogellia_q0408_10.htm";
            } else if (questState.getQuestItemsCount(1220) < 1L && questState.getQuestItemsCount(1229) > 0L) {
                questState.giveItems(1218, 1L);
                string2 = "rogellia_q0408_07.htm";
            }
        } else if (string.equalsIgnoreCase("408_4")) {
            if (questState.getQuestItemsCount(1218) > 0L) {
                questState.takeItems(1218, -1L);
                questState.giveItems(1272, 1L);
                string2 = "grain_q0408_02.htm";
            }
        } else if (string.equalsIgnoreCase("408_2")) {
            if (questState.getQuestItemsCount(1221) > 0L) {
                string2 = "rogellia_q0408_13.htm";
            } else if (questState.getQuestItemsCount(1221) < 1L && questState.getQuestItemsCount(1229) > 0L) {
                questState.giveItems(1222, 1L);
                string2 = "rogellia_q0408_14.htm";
            }
        } else if (string.equalsIgnoreCase("408_5")) {
            if (questState.getQuestItemsCount(1222) > 0L) {
                questState.takeItems(1222, -1L);
                questState.giveItems(1273, 1L);
                string2 = "thalya_q0408_02.htm";
            }
        } else if (string.equalsIgnoreCase("408_3")) {
            if (questState.getQuestItemsCount(1226) > 0L) {
                string2 = "rogellia_q0408_17.htm";
            } else if (questState.getQuestItemsCount(1226) < 1L && questState.getQuestItemsCount(1229) > 0L) {
                questState.giveItems(1224, 1L);
                string2 = "rogellia_q0408_18.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 30414) {
            if (n2 < 1) {
                string = "rogellia_q0408_01.htm";
            } else if (questState.getQuestItemsCount(1272) > 0L) {
                if (questState.getQuestItemsCount(1219) < 5L) {
                    string = "rogellia_q0408_09.htm";
                } else if (questState.getQuestItemsCount(1219) > 4L) {
                    string = "rogellia_q0408_25.htm";
                } else if (questState.getQuestItemsCount(1223) > 4L) {
                    string = "rogellia_q0408_26.htm";
                }
            } else if (questState.getQuestItemsCount(1222) > 0L) {
                string = "rogellia_q0408_15.htm";
            } else if (questState.getQuestItemsCount(1224) > 0L) {
                string = "rogellia_q0408_19.htm";
            } else if (questState.getQuestItemsCount(1273) > 0L && questState.getQuestItemsCount(1223) < 5L) {
                string = "rogellia_q0408_16.htm";
            } else if (questState.getQuestItemsCount(1274) > 0L) {
                string = questState.getQuestItemsCount(1225) < 2L ? "rogellia_q0408_20.htm" : "rogellia_q0408_27.htm";
            } else if (questState.getQuestItemsCount(1218) > 0L) {
                string = "rogellia_q0408_08.htm";
            } else if (questState.getQuestItemsCount(1218) < 1L && questState.getQuestItemsCount(1222) < 1L && questState.getQuestItemsCount(1224) < 1L && questState.getQuestItemsCount(1272) < 1L && questState.getQuestItemsCount(1273) < 1L && questState.getQuestItemsCount(1274) < 1L && questState.getQuestItemsCount(1229) > 0L) {
                if (questState.getQuestItemsCount(1220) < 1L | questState.getQuestItemsCount(1226) < 1L | questState.getQuestItemsCount(1221) < 1L) {
                    string = "rogellia_q0408_11.htm";
                } else if (questState.getQuestItemsCount(1220) > 0L && questState.getQuestItemsCount(1226) > 0L && questState.getQuestItemsCount(1221) > 0L) {
                    questState.takeItems(1220, -1L);
                    questState.takeItems(1221, questState.getQuestItemsCount(1221));
                    questState.takeItems(1226, questState.getQuestItemsCount(1226));
                    questState.takeItems(1229, questState.getQuestItemsCount(1229));
                    string = "rogellia_q0408_24.htm";
                    if (questState.getPlayer().getClassId().getLevel() == 1) {
                        questState.giveItems(1230, 1L);
                        if (!questState.getPlayer().getVarB("prof1")) {
                            questState.getPlayer().setVar("prof1", "1", -1L);
                            questState.addExpAndSp(3200L, 1890L);
                            this.giveExtraReward(questState.getPlayer());
                        }
                    }
                    questState.playSound("ItemSound.quest_finish");
                    questState.exitCurrentQuest(true);
                }
            }
        } else if (n == 30157 && n2 > 0) {
            if (questState.getQuestItemsCount(1218) > 0L) {
                string = "grain_q0408_01.htm";
            } else if (questState.getQuestItemsCount(1272) > 0L) {
                if (questState.getQuestItemsCount(1219) < 5L) {
                    string = "grain_q0408_03.htm";
                } else {
                    questState.takeItems(1219, -1L);
                    questState.takeItems(1272, -1L);
                    questState.giveItems(1220, 1L);
                    string = "grain_q0408_04.htm";
                }
            }
        } else if (n == 30371 && n2 > 0) {
            if (questState.getQuestItemsCount(1222) > 0L) {
                string = "thalya_q0408_01.htm";
            } else if (questState.getQuestItemsCount(1273) > 0L) {
                if (questState.getQuestItemsCount(1223) < 5L) {
                    string = "thalya_q0408_03.htm";
                } else {
                    questState.takeItems(1223, -1L);
                    questState.takeItems(1273, -1L);
                    questState.giveItems(1221, 1L);
                    string = "thalya_q0408_04.htm";
                }
            }
        } else if (n == 30423 && n2 > 0) {
            if (questState.getQuestItemsCount(1224) > 0L) {
                questState.takeItems(1224, -1L);
                questState.giveItems(1274, 1L);
                string = "northwindel_q0408_01.htm";
            } else if (questState.getQuestItemsCount(1274) > 0L) {
                if (questState.getQuestItemsCount(1225) < 2L) {
                    string = "northwindel_q0408_02.htm";
                } else {
                    questState.takeItems(1225, -1L);
                    questState.takeItems(1274, -1L);
                    questState.giveItems(1226, 1L);
                    string = "northwindel_q0408_03.htm";
                }
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 20466) {
            if (n2 > 0 && questState.getQuestItemsCount(1272) > 0L && questState.getQuestItemsCount(1219) < 5L && Rnd.chance((int)70)) {
                questState.giveItems(1219, 1L);
                if (questState.getQuestItemsCount(1219) < 5L) {
                    questState.playSound("ItemSound.quest_itemget");
                } else {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20019) {
            if (n2 > 0 && questState.getQuestItemsCount(1273) > 0L && questState.getQuestItemsCount(1223) < 5L && Rnd.chance((int)40)) {
                questState.giveItems(1223, 1L);
                if (questState.getQuestItemsCount(1223) < 5L) {
                    questState.playSound("ItemSound.quest_itemget");
                } else {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20047 && n2 > 0 && questState.getQuestItemsCount(1274) > 0L && questState.getQuestItemsCount(1225) < 2L && Rnd.chance((int)40)) {
            questState.giveItems(1225, 1L);
            if (questState.getQuestItemsCount(1225) < 2L) {
                questState.playSound("ItemSound.quest_itemget");
            } else {
                questState.playSound("ItemSound.quest_middle");
            }
        }
        return null;
    }
}
