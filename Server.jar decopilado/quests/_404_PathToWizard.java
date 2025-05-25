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

public class _404_PathToWizard
extends Quest
implements ScriptFile {
    private static final int aWY = 30391;
    private static final int aWZ = 30409;
    private static final int aXa = 30410;
    private static final int aXb = 30411;
    private static final int aXc = 30412;
    private static final int aXd = 30413;
    private static final int aXe = 20021;
    private static final int aXf = 20359;
    private static final int aXg = 27030;
    private static final int aXh = 1280;
    private static final int aXi = 1281;
    private static final int aXj = 1282;
    private static final int aXk = 1283;
    private static final int aXl = 1284;
    private static final int aXm = 1285;
    private static final int aXn = 1286;
    private static final int aXo = 1287;
    private static final int aXp = 1288;
    private static final int aXq = 1289;
    private static final int aXr = 1290;
    private static final int aXs = 1291;
    private static final int aXt = 1292;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _404_PathToWizard() {
        super(0);
        this.addStartNpc(30391);
        this.addTalkId(new int[]{30409});
        this.addTalkId(new int[]{30410});
        this.addTalkId(new int[]{30411});
        this.addTalkId(new int[]{30412});
        this.addTalkId(new int[]{30413});
        this.addKillId(new int[]{20021});
        this.addKillId(new int[]{20359});
        this.addKillId(new int[]{27030});
        this.addQuestItem(new int[]{1281, 1280, 1284, 1283, 1287, 1286, 1290, 1289, 1282, 1285, 1288, 1291});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("1")) {
            if (questState.getPlayer().getClassId().getId() == 10) {
                if (questState.getPlayer().getLevel() >= 18) {
                    if (questState.getQuestItemsCount(1292) > 0L) {
                        string2 = "parina_q0404_03.htm";
                    } else {
                        string2 = "parina_q0404_08.htm";
                        questState.setCond(1);
                        questState.setState(2);
                        questState.playSound("ItemSound.quest_accept");
                    }
                } else {
                    string2 = "parina_q0404_02.htm";
                }
            } else {
                string2 = questState.getPlayer().getClassId().getId() == 11 ? "parina_q0404_02a.htm" : "parina_q0404_01.htm";
            }
        } else if (string.equalsIgnoreCase("30410_1") && questState.getQuestItemsCount(1284) < 1L) {
            string2 = "lizardman_of_wasteland_q0404_03.htm";
            questState.giveItems(1284, 1L);
            questState.setCond(6);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 30391) {
            if (n2 == 0) {
                string = "parina_q0404_04.htm";
            } else if (n2 > 0 && questState.getQuestItemsCount(1282) < 1L | questState.getQuestItemsCount(1285) < 1L | questState.getQuestItemsCount(1288) < 1L | questState.getQuestItemsCount(1291) < 1L) {
                string = "parina_q0404_05.htm";
            } else if (n2 > 0 && questState.getQuestItemsCount(1282) > 0L && questState.getQuestItemsCount(1285) > 0L && questState.getQuestItemsCount(1288) > 0L && questState.getQuestItemsCount(1291) > 0L) {
                string = "parina_q0404_06.htm";
                questState.takeItems(1282, questState.getQuestItemsCount(1282));
                questState.takeItems(1285, questState.getQuestItemsCount(1285));
                questState.takeItems(1288, questState.getQuestItemsCount(1288));
                questState.takeItems(1291, questState.getQuestItemsCount(1291));
                if (questState.getPlayer().getClassId().getLevel() == 1) {
                    if (questState.getQuestItemsCount(1292) < 1L) {
                        questState.giveItems(1292, 1L);
                    }
                    if (!questState.getPlayer().getVarB("prof1")) {
                        questState.getPlayer().setVar("prof1", "1", -1L);
                        questState.addExpAndSp(3200L, 2020L);
                        this.giveExtraReward(questState.getPlayer());
                    }
                }
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
            }
        } else if (n == 30411) {
            if (n2 > 0 && questState.getQuestItemsCount(1280) < 1L && questState.getQuestItemsCount(1282) < 1L) {
                questState.giveItems(1280, 1L);
                string = "flame_salamander_q0404_01.htm";
                questState.setCond(2);
            } else if (n2 > 0 && questState.getQuestItemsCount(1280) > 0L && questState.getQuestItemsCount(1281) < 1L) {
                string = "flame_salamander_q0404_02.htm";
            } else if (n2 == 3 && questState.getQuestItemsCount(1280) > 0L && questState.getQuestItemsCount(1281) > 0L) {
                questState.takeItems(1281, -1L);
                questState.takeItems(1280, -1L);
                if (questState.getQuestItemsCount(1282) < 1L) {
                    questState.giveItems(1282, 1L);
                }
                string = "flame_salamander_q0404_03.htm";
                questState.setCond(4);
            } else if (n2 > 0 && questState.getQuestItemsCount(1282) > 0L) {
                string = "flame_salamander_q0404_04.htm";
            }
        } else if (n == 30412) {
            if (n2 == 4 && questState.getQuestItemsCount(1282) > 0L && questState.getQuestItemsCount(1283) < 1L && questState.getQuestItemsCount(1285) < 1L) {
                questState.giveItems(1283, 1L);
                string = "wind_sylph_q0404_01.htm";
                questState.setCond(5);
            } else if (n2 > 0 && questState.getQuestItemsCount(1283) > 0L && questState.getQuestItemsCount(1284) < 1L) {
                string = "wind_sylph_q0404_02.htm";
            } else if (n2 > 0 && questState.getQuestItemsCount(1283) > 0L && questState.getQuestItemsCount(1284) > 0L) {
                questState.takeItems(1284, questState.getQuestItemsCount(1284));
                questState.takeItems(1283, questState.getQuestItemsCount(1283));
                if (questState.getQuestItemsCount(1285) < 1L) {
                    questState.giveItems(1285, 1L);
                }
                string = "wind_sylph_q0404_03.htm";
                questState.setCond(7);
            } else if (n2 > 0 && questState.getQuestItemsCount(1285) > 0L) {
                string = "wind_sylph_q0404_04.htm";
            }
        } else if (n == 30410) {
            if (n2 > 0 && questState.getQuestItemsCount(1283) > 0L && questState.getQuestItemsCount(1284) < 1L) {
                string = "lizardman_of_wasteland_q0404_01.htm";
            } else if (n2 > 0 && questState.getQuestItemsCount(1283) > 0L && questState.getQuestItemsCount(1284) > 0L) {
                string = "lizardman_of_wasteland_q0404_04.htm";
            }
        } else if (n == 30413) {
            if (n2 == 7 && questState.getQuestItemsCount(1285) > 0L && questState.getQuestItemsCount(1286) < 1L && questState.getQuestItemsCount(1288) < 1L) {
                questState.giveItems(1286, 1L);
                string = "water_undine_q0404_01.htm";
                questState.setCond(8);
            } else if (n2 > 0 && questState.getQuestItemsCount(1286) > 0L && questState.getQuestItemsCount(1287) < 2L) {
                string = "water_undine_q0404_02.htm";
            } else if (n2 == 9 && questState.getQuestItemsCount(1286) > 0L && questState.getQuestItemsCount(1287) > 1L) {
                questState.takeItems(1287, -1L);
                questState.takeItems(1286, -1L);
                if (questState.getQuestItemsCount(1288) < 1L) {
                    questState.giveItems(1288, 1L);
                }
                string = "water_undine_q0404_03.htm";
                questState.setCond(10);
            } else if (n2 > 0 && questState.getQuestItemsCount(1288) > 0L) {
                string = "water_undine_q0404_04.htm";
            }
        } else if (n == 30409) {
            if (n2 > 0 && questState.getQuestItemsCount(1288) > 0L && questState.getQuestItemsCount(1289) < 1L && questState.getQuestItemsCount(1291) < 1L) {
                questState.giveItems(1289, 1L);
                string = "earth_snake_q0404_01.htm";
                questState.setCond(11);
            } else if (n2 > 0 && questState.getQuestItemsCount(1289) > 0L && questState.getQuestItemsCount(1290) < 1L) {
                string = "earth_snake_q0404_02.htm";
            } else if (n2 == 12 && questState.getQuestItemsCount(1289) > 0L && questState.getQuestItemsCount(1290) > 0L) {
                questState.takeItems(1290, questState.getQuestItemsCount(1290));
                questState.takeItems(1289, questState.getQuestItemsCount(1289));
                if (questState.getQuestItemsCount(1291) < 1L) {
                    questState.giveItems(1291, 1L);
                }
                string = "earth_snake_q0404_04.htm";
                questState.setCond(13);
            } else if (n2 > 0 && questState.getQuestItemsCount(1291) > 0L) {
                string = "earth_snake_q0404_04.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 20359) {
            if (n2 == 2) {
                questState.giveItems(1281, 1L);
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(3);
            }
        } else if (n == 27030) {
            if (n2 == 8 && questState.getQuestItemsCount(1287) < 2L) {
                questState.giveItems(1287, 1L);
                if (questState.getQuestItemsCount(1287) == 2L) {
                    questState.playSound("ItemSound.quest_middle");
                    questState.setCond(9);
                } else {
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 20021 && n2 == 11) {
            questState.giveItems(1290, 1L);
            questState.playSound("ItemSound.quest_middle");
            questState.setCond(12);
        }
        return null;
    }
}
