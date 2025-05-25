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

public class _401_PathToWarrior
extends Quest
implements ScriptFile {
    private static final int aVB = 30010;
    private static final int aVC = 30253;
    private static final int aVD = 20035;
    private static final int aVE = 20038;
    private static final int aVF = 20042;
    private static final int aVG = 20043;
    private static final int aVH = 1138;
    private static final int aVI = 1139;
    private static final int aVJ = 1140;
    private static final int aVK = 1141;
    private static final int aVL = 1143;
    private static final int aVM = 1144;
    private static final int aVN = 1145;
    private static final int aVO = 1142;

    public _401_PathToWarrior() {
        super(0);
        this.addStartNpc(30010);
        this.addTalkId(new int[]{30253});
        this.addKillId(new int[]{20035});
        this.addKillId(new int[]{20038});
        this.addKillId(new int[]{20042});
        this.addKillId(new int[]{20043});
        this.addQuestItem(new int[]{1143, 1141, 1138, 1139, 1140, 1144, 1142});
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("401_1")) {
            string2 = questState.getPlayer().getClassId().getId() == 0 ? (questState.getPlayer().getLevel() >= 18 ? (questState.getQuestItemsCount(1145) > 0L ? "ein_q0401_04.htm" : "ein_q0401_05.htm") : "ein_q0401_02.htm") : (questState.getPlayer().getClassId().getId() == 1 ? "ein_q0401_02a.htm" : "ein_q0401_03.htm");
        } else if (string.equalsIgnoreCase("401_2")) {
            string2 = "ein_q0401_10.htm";
        } else if (string.equalsIgnoreCase("401_3")) {
            string2 = "ein_q0401_11.htm";
            questState.takeItems(1143, 1L);
            questState.takeItems(1141, 1L);
            questState.giveItems(1142, 1L);
            questState.setCond(5);
        } else if (string.equalsIgnoreCase("1")) {
            if (questState.getQuestItemsCount(1138) == 0L) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                questState.giveItems(1138, 1L);
                string2 = "ein_q0401_06.htm";
            }
        } else if (string.equalsIgnoreCase("30253_1")) {
            string2 = "trader_simplon_q0401_02.htm";
            questState.takeItems(1138, 1L);
            questState.giveItems(1139, 1L);
            questState.setCond(2);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        int n3 = questState.getCond();
        if (n2 == 1) {
            questState.setState(2);
            questState.setCond(0);
        }
        if (n == 30010 && n3 == 0) {
            string = "ein_q0401_01.htm";
        } else if (n == 30010 && questState.getQuestItemsCount(1138) > 0L) {
            string = "ein_q0401_07.htm";
        } else if (n == 30010 && questState.getQuestItemsCount(1139) == 1L) {
            string = "ein_q0401_08.htm";
        } else if (n == 30253 && questState.getQuestItemsCount(1138) > 0L) {
            string = "trader_simplon_q0401_01.htm";
        } else if (n == 30253 && questState.getQuestItemsCount(1139) > 0L) {
            if (questState.getQuestItemsCount(1140) < 1L) {
                string = "trader_simplon_q0401_03.htm";
            } else if (questState.getQuestItemsCount(1140) < 10L) {
                string = "trader_simplon_q0401_04.htm";
            } else if (questState.getQuestItemsCount(1140) >= 10L) {
                questState.takeItems(1139, -1L);
                questState.takeItems(1140, -1L);
                questState.giveItems(1141, 1L);
                questState.giveItems(1143, 1L);
                questState.setCond(4);
                string = "trader_simplon_q0401_05.htm";
            }
        } else if (n == 30253 && questState.getQuestItemsCount(1143) > 0L) {
            string = "trader_simplon_q0401_06.htm";
        } else if (n == 30010 && questState.getQuestItemsCount(1143) > 0L && questState.getQuestItemsCount(1141) > 0L && questState.getQuestItemsCount(1139) == 0L && questState.getQuestItemsCount(1138) == 0L) {
            string = "ein_q0401_09.htm";
        } else if (n == 30010 && questState.getQuestItemsCount(1142) > 0L && questState.getQuestItemsCount(1139) == 0L && questState.getQuestItemsCount(1138) == 0L) {
            if (questState.getQuestItemsCount(1144) < 20L) {
                string = "ein_q0401_12.htm";
            } else if (questState.getQuestItemsCount(1144) > 19L) {
                questState.takeItems(1144, -1L);
                questState.takeItems(1142, -1L);
                if (questState.getPlayer().getClassId().getLevel() == 1) {
                    questState.giveItems(1145, 1L);
                    if (!questState.getPlayer().getVarB("prof1")) {
                        questState.getPlayer().setVar("prof1", "1", -1L);
                        questState.addExpAndSp(3200L, 1500L);
                        this.giveExtraReward(questState.getPlayer());
                    }
                }
                string = "ein_q0401_13.htm";
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 20035 || n == 20042) {
            if (n2 == 2 && questState.getQuestItemsCount(1140) < 10L) {
                questState.giveItems(1140, 1L);
                if (questState.getQuestItemsCount(1140) == 10L) {
                    questState.playSound("ItemSound.quest_middle");
                    questState.setCond(3);
                } else {
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if ((n == 20043 || n == 20038) && questState.getQuestItemsCount(1144) < 20L && questState.getQuestItemsCount(1142) == 1L && questState.getItemEquipped(5) == 1142) {
            questState.giveItems(1144, 1L);
            if (questState.getQuestItemsCount(1144) == 20L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(6);
            } else {
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
