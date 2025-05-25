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

public class _413_PathToShillienOracle
extends Quest
implements ScriptFile {
    private static final int aZW = 30330;
    private static final int aZX = 30375;
    private static final int aZY = 30377;
    private static final int aZZ = 20457;
    private static final int baa = 20458;
    private static final int bab = 20514;
    private static final int bac = 20515;
    private static final int bad = 20776;
    private static final int bae = 1262;
    private static final int baf = 1263;
    private static final int bag = 1264;
    private static final int bah = 1265;
    private static final int bai = 1266;
    private static final int baj = 1267;
    private static final int bak = 1268;
    private static final int bal = 1269;
    private static final int bam = 1270;
    private static final int[] bM = new int[]{20457, 20458, 20514, 20515};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _413_PathToShillienOracle() {
        super(0);
        this.addStartNpc(30330);
        this.addTalkId(new int[]{30375});
        this.addTalkId(new int[]{30377});
        this.addKillId(new int[]{20776});
        for (int n : bM) {
            this.addKillId(new int[]{n});
        }
        this.addQuestItem(new int[]{1268});
        this.addQuestItem(new int[]{1262});
        this.addQuestItem(new int[]{1269});
        this.addQuestItem(new int[]{1267});
        this.addQuestItem(new int[]{1265});
        this.addQuestItem(new int[]{1266});
        this.addQuestItem(new int[]{1263});
        this.addQuestItem(new int[]{1264});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("1")) {
            string2 = "master_sidra_q0413_06.htm";
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            questState.giveItems(1262, 1L);
        } else if (string.equalsIgnoreCase("413_1")) {
            if (questState.getPlayer().getLevel() >= 18 && questState.getPlayer().getClassId().getId() == 38 && questState.getQuestItemsCount(1270) < 1L) {
                string2 = "master_sidra_q0413_05.htm";
            } else if (questState.getPlayer().getClassId().getId() != 38) {
                string2 = questState.getPlayer().getClassId().getId() == 42 ? "master_sidra_q0413_02a.htm" : "master_sidra_q0413_03.htm";
            } else if (questState.getPlayer().getLevel() < 18 && questState.getPlayer().getClassId().getId() == 38) {
                string2 = "master_sidra_q0413_02.htm";
            } else if (questState.getPlayer().getLevel() >= 18 && questState.getPlayer().getClassId().getId() == 38 && questState.getQuestItemsCount(1270) > 0L) {
                string2 = "master_sidra_q0413_04.htm";
            }
        } else if (string.equalsIgnoreCase("30377_1")) {
            string2 = "magister_talbot_q0413_02.htm";
            questState.takeItems(1262, -1L);
            questState.giveItems(1263, 5L);
            questState.playSound("ItemSound.quest_itemget");
            questState.setCond(2);
        } else if (string.equalsIgnoreCase("30375_1")) {
            string2 = "priest_adonius_q0413_02.htm";
        } else if (string.equalsIgnoreCase("30375_2")) {
            string2 = "priest_adonius_q0413_03.htm";
        } else if (string.equalsIgnoreCase("30375_3")) {
            string2 = "priest_adonius_q0413_04.htm";
            questState.takeItems(1266, -1L);
            questState.giveItems(1267, 1L);
            questState.playSound("ItemSound.quest_itemget");
            questState.setCond(5);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 30330) {
            if (n2 < 1) {
                string = "master_sidra_q0413_01.htm";
            } else if (n2 == 1) {
                string = "master_sidra_q0413_07.htm";
            } else if (n2 == 2 | n2 == 3) {
                string = "master_sidra_q0413_08.htm";
            } else if (n2 > 3 && n2 < 7) {
                string = "master_sidra_q0413_09.htm";
            } else if (n2 == 7 && questState.getQuestItemsCount(1269) > 0L && questState.getQuestItemsCount(1265) > 0L) {
                string = "master_sidra_q0413_10.htm";
                questState.exitCurrentQuest(true);
                if (questState.getPlayer().getClassId().getLevel() == 1) {
                    questState.giveItems(1270, 1L);
                    if (!questState.getPlayer().getVarB("prof1")) {
                        questState.getPlayer().setVar("prof1", "1", -1L);
                        questState.addExpAndSp(3200L, 3120L);
                        this.giveExtraReward(questState.getPlayer());
                    }
                }
                questState.playSound("ItemSound.quest_finish");
            }
        } else if (n == 30377) {
            if (n2 == 1 && questState.getQuestItemsCount(1262) > 0L) {
                string = "magister_talbot_q0413_01.htm";
            } else if (n2 == 2) {
                if (questState.getQuestItemsCount(1264) < 1L) {
                    string = "magister_talbot_q0413_03.htm";
                } else if (questState.getQuestItemsCount(1264) > 0L) {
                    string = "magister_talbot_q0413_04.htm";
                }
            } else if (n2 == 3 && questState.getQuestItemsCount(1264) > 4L) {
                string = "magister_talbot_q0413_05.htm";
                questState.takeItems(1264, -1L);
                questState.giveItems(1265, 1L);
                questState.giveItems(1266, 1L);
                questState.playSound("ItemSound.quest_itemget");
                questState.setCond(4);
            } else if (n2 > 3 && n2 < 7) {
                string = "magister_talbot_q0413_06.htm";
            } else if (n2 == 7) {
                string = "magister_talbot_q0413_07.htm";
            }
        } else if (n == 30375) {
            if (n2 == 4 && questState.getQuestItemsCount(1266) > 0L) {
                string = "priest_adonius_q0413_01.htm";
            } else if (n2 == 5 && questState.getQuestItemsCount(1268) < 1L) {
                string = "priest_adonius_q0413_05.htm";
            } else if (n2 == 5 && questState.getQuestItemsCount(1268) < 10L) {
                string = "priest_adonius_q0413_06.htm";
            } else if (n2 == 6 && questState.getQuestItemsCount(1268) > 9L) {
                string = "priest_adonius_q0413_07.htm";
                questState.takeItems(1268, -1L);
                questState.takeItems(1267, -1L);
                questState.giveItems(1269, 1L);
                questState.playSound("ItemSound.quest_itemget");
                questState.setCond(7);
            } else if (n2 == 7 && questState.getQuestItemsCount(1269) > 0L) {
                string = "priest_adonius_q0413_08.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 20776 && n2 == 2 && questState.getQuestItemsCount(1263) > 0L) {
            questState.giveItems(1264, 1L);
            questState.takeItems(1263, 1L);
            if (questState.getQuestItemsCount(1263) < 1L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(3);
            } else {
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        for (int n3 : bM) {
            if (n != n3 || n2 != 5 || questState.getQuestItemsCount(1268) >= 10L) continue;
            questState.giveItems(1268, 1L);
            if (questState.getQuestItemsCount(1268) > 9L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(6);
                continue;
            }
            questState.playSound("ItemSound.quest_itemget");
        }
        return null;
    }
}
