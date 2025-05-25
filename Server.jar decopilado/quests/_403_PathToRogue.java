/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;

public class _403_PathToRogue
extends Quest
implements ScriptFile {
    private static final int aWE = 30379;
    private static final int aWF = 30425;
    private static final int aWG = 20035;
    private static final int aWH = 20042;
    private static final int aWI = 20045;
    private static final int aWJ = 20051;
    private static final int aWK = 20054;
    private static final int aWL = 20060;
    private static final int aWM = 27038;
    private static final int aWN = 1180;
    private static final int aWO = 1183;
    private static final int aWP = 1184;
    private static final int aWQ = 1185;
    private static final int aWR = 1186;
    private static final int aWS = 1187;
    private static final int aWT = 1188;
    private static final int aWU = 1189;
    private static final int aWV = 1190;
    private static final int aWW = 1181;
    private static final int aWX = 1182;
    private static final int[][] O = new int[][]{{20035, 2}, {20042, 3}, {20045, 2}, {20051, 2}, {20054, 8}, {20060, 8}};
    private static final int[] bL = new int[]{1186, 1187, 1188, 1189};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _403_PathToRogue() {
        super(0);
        this.addStartNpc(30379);
        this.addTalkId(new int[]{30425});
        this.addKillId(new int[]{27038});
        this.addAttackId(new int[]{27038});
        for (int[] nArray : O) {
            this.addKillId(new int[]{nArray[0]});
            this.addAttackId(new int[]{nArray[0]});
        }
        this.addQuestItem(bL);
        this.addQuestItem(new int[]{1181, 1182, 1185, 1184, 1180, 1183});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("30379_2")) {
            string2 = questState.getPlayer().getClassId().getId() == 0 ? (questState.getPlayer().getLevel() >= 18 ? (questState.getQuestItemsCount(1190) > 0L ? "captain_bezique_q0403_04.htm" : "captain_bezique_q0403_05.htm") : "captain_bezique_q0403_03.htm") : (questState.getPlayer().getClassId().getId() == 7 ? "captain_bezique_q0403_02a.htm" : "captain_bezique_q0403_02.htm");
        } else if (string.equalsIgnoreCase("1")) {
            questState.setCond(1);
            questState.setState(2);
            questState.giveItems(1180, 1L);
            string2 = "captain_bezique_q0403_06.htm";
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("30425_1")) {
            questState.takeItems(1180, 1L);
            if (questState.getQuestItemsCount(1181) < 1L) {
                questState.giveItems(1181, 1L);
            }
            if (questState.getQuestItemsCount(1182) < 1L) {
                questState.giveItems(1182, 1L);
            }
            questState.setCond(2);
            string2 = "neti_q0403_05.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 30379) {
            if (n2 == 6 && questState.getQuestItemsCount(1184) < 1L && questState.getQuestItemsCount(1186) + questState.getQuestItemsCount(1187) + questState.getQuestItemsCount(1188) + questState.getQuestItemsCount(1189) == 4L) {
                string = "captain_bezique_q0403_09.htm";
                questState.takeItems(1181, 1L);
                questState.takeItems(1182, 1L);
                questState.takeItems(1185, 1L);
                for (int n3 : bL) {
                    questState.takeItems(n3, -1L);
                }
                if (questState.getPlayer().getClassId().getLevel() == 1) {
                    questState.giveItems(1190, 1L);
                    if (!questState.getPlayer().getVarB("prof1")) {
                        questState.getPlayer().setVar("prof1", "1", -1L);
                        questState.addExpAndSp(3200L, 1130L);
                        this.giveExtraReward(questState.getPlayer());
                    }
                }
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
            } else if (n2 == 1 && questState.getQuestItemsCount(1184) < 1L && questState.getQuestItemsCount(1180) > 0L) {
                string = "captain_bezique_q0403_07.htm";
            } else if (n2 == 4 && questState.getQuestItemsCount(1184) > 0L) {
                string = "captain_bezique_q0403_08.htm";
                questState.takeItems(1184, 1L);
                questState.giveItems(1185, 1L);
                questState.setCond(5);
            } else {
                string = n2 > 1 && questState.getQuestItemsCount(1181) > 0L && questState.getQuestItemsCount(1182) > 0L && questState.getQuestItemsCount(1185) < 1L ? "captain_bezique_q0403_10.htm" : (n2 == 5 && questState.getQuestItemsCount(1185) > 0L ? "captain_bezique_q0403_11.htm" : "captain_bezique_q0403_01.htm");
            }
        } else if (n == 30425) {
            if (n2 == 1 && questState.getQuestItemsCount(1180) > 0L) {
                string = "neti_q0403_01.htm";
            } else if (n2 == 2 | n2 == 3 && questState.getQuestItemsCount(1183) < 10L) {
                string = "neti_q0403_06.htm";
                questState.setCond(2);
            } else if (n2 == 3 && questState.getQuestItemsCount(1183) > 9L) {
                string = "neti_q0403_07.htm";
                questState.takeItems(1183, -1L);
                questState.giveItems(1184, 1L);
                questState.setCond(4);
            } else if (n2 == 4 && questState.getQuestItemsCount(1184) > 0L) {
                string = "neti_q0403_08.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        int n3 = questState.getInt("netis_cond");
        if (n3 == 1 && questState.getItemEquipped(5) == 1181 || questState.getItemEquipped(5) == 1182) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"40307", (Object[])new Object[0]);
            switch (n2) {
                case 2: {
                    for (int[] nArray : O) {
                        if (n != nArray[0] || !Rnd.chance((int)(10 * nArray[1])) || questState.getQuestItemsCount(1183) >= 10L) continue;
                        questState.giveItems(1183, 1L);
                        if (questState.getQuestItemsCount(1183) == 10L) {
                            questState.playSound("ItemSound.quest_middle");
                            questState.setCond(3);
                            continue;
                        }
                        questState.playSound("ItemSound.quest_itemget");
                    }
                    break;
                }
                case 5: {
                    int n4;
                    if (n != 27038 || questState.getQuestItemsCount(1185) <= 0L || questState.getQuestItemsCount(bL[n4 = Rnd.get((int)4)]) != 0L) break;
                    questState.giveItems(bL[n4], 1L);
                    if (questState.getQuestItemsCount(1186) + questState.getQuestItemsCount(1187) + questState.getQuestItemsCount(1188) + questState.getQuestItemsCount(1189) < 4L) {
                        questState.playSound("ItemSound.quest_itemget");
                        break;
                    }
                    questState.playSound("ItemSound.quest_middle");
                    questState.setCond(6);
                }
            }
        }
        return null;
    }

    public String onAttack(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("netis_cond");
        if (questState.getItemEquipped(5) != 1181 && questState.getItemEquipped(5) != 1182) {
            questState.set("netis_cond", "0");
        } else if (n == 0) {
            questState.set("netis_cond", "1");
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"40306", (Object[])new Object[0]);
        }
        return null;
    }
}
