/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _271_ProofOfValor
extends Quest
implements ScriptFile {
    private static final int auE = 30577;
    private static final int auF = 1473;
    private static final int auG = 1507;
    private static final int auH = 1506;
    private static final int[][] x = new int[][]{{1, 2, 20475, 0, 1473, 50, 25, 2}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _271_ProofOfValor() {
        super(0);
        this.addStartNpc(30577);
        this.addTalkId(new int[]{30577});
        for (int i = 0; i < x.length; ++i) {
            this.addKillId(new int[]{x[i][2]});
        }
        this.addQuestItem(new int[]{1473});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("praetorian_rukain_q0271_03.htm")) {
            questState.playSound("ItemSound.quest_accept");
            if (questState.getQuestItemsCount(1506) > 0L || questState.getQuestItemsCount(1507) > 0L) {
                string2 = "praetorian_rukain_q0271_07.htm";
            }
            questState.setCond(1);
            questState.setState(2);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getCond();
        if (n == 30577) {
            if (n2 == 0) {
                if (questState.getPlayer().getRace() != Race.orc) {
                    string = "praetorian_rukain_q0271_00.htm";
                    questState.exitCurrentQuest(true);
                } else if (questState.getPlayer().getLevel() < 4) {
                    string = "praetorian_rukain_q0271_01.htm";
                    questState.exitCurrentQuest(true);
                } else if (questState.getQuestItemsCount(1506) > 0L || questState.getQuestItemsCount(1507) > 0L) {
                    string = "praetorian_rukain_q0271_06.htm";
                    questState.exitCurrentQuest(true);
                } else {
                    string = "praetorian_rukain_q0271_02.htm";
                }
            } else if (n2 == 1) {
                string = "praetorian_rukain_q0271_04.htm";
            } else if (n2 == 2 && questState.getQuestItemsCount(1473) >= 50L) {
                questState.takeItems(1473, -1L);
                if (Rnd.chance((int)14)) {
                    questState.takeItems(1507, -1L);
                    questState.giveItems(1507, 1L);
                } else {
                    questState.takeItems(1506, -1L);
                    questState.giveItems(1506, 1L);
                }
                string = "praetorian_rukain_q0271_05.htm";
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
            } else if (n2 == 2 && questState.getQuestItemsCount(1473) < 50L) {
                string = "praetorian_rukain_q0271_04.htm";
                questState.setCond(1);
                questState.setState(2);
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        for (int i = 0; i < x.length; ++i) {
            if (n2 != x[i][0] || n != x[i][2] || x[i][3] != 0 && questState.getQuestItemsCount(x[i][3]) <= 0L) continue;
            if (x[i][5] == 0) {
                questState.rollAndGive(x[i][4], x[i][7], (double)x[i][6]);
                continue;
            }
            if (!questState.rollAndGive(x[i][4], x[i][7], x[i][7], x[i][5], (double)x[i][6]) || x[i][1] == n2 || x[i][1] == 0) continue;
            questState.setCond(Integer.valueOf(x[i][1]).intValue());
            questState.setState(2);
        }
        return null;
    }
}
