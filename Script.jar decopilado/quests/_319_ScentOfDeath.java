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

public class _319_ScentOfDeath
extends Quest
implements ScriptFile {
    private static final int awH = 30138;
    private static final int awI = 1060;
    private static final int awJ = 1045;
    private static final int[][] B = new int[][]{{1, 2, 20015, 0, 1045, 5, 20, 1}, {1, 2, 20020, 0, 1045, 5, 25, 1}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _319_ScentOfDeath() {
        super(0);
        this.addStartNpc(30138);
        this.addTalkId(new int[]{30138});
        for (int i = 0; i < B.length; ++i) {
            this.addKillId(new int[]{B[i][2]});
        }
        this.addQuestItem(new int[]{1045});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("mina_q0319_04.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getState();
        int n3 = 0;
        if (n2 != 1) {
            n3 = questState.getCond();
        }
        if (n == 30138) {
            if (n3 == 0) {
                if (questState.getPlayer().getLevel() < 11) {
                    string = "mina_q0319_02.htm";
                    questState.exitCurrentQuest(true);
                } else {
                    string = "mina_q0319_03.htm";
                }
            } else if (n3 == 1) {
                string = "mina_q0319_05.htm";
            } else if (n3 == 2 && questState.getQuestItemsCount(1045) >= 5L) {
                string = "mina_q0319_06.htm";
                questState.takeItems(1045, -1L);
                questState.giveItems(57, 3350L);
                questState.giveItems(1060, 1L);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
            } else {
                string = "mina_q0319_05.htm";
                questState.setCond(1);
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        for (int i = 0; i < B.length; ++i) {
            if (n2 != B[i][0] || n != B[i][2] || B[i][3] != 0 && questState.getQuestItemsCount(B[i][3]) <= 0L) continue;
            if (B[i][5] == 0) {
                questState.rollAndGive(B[i][4], B[i][7], (double)B[i][6]);
                continue;
            }
            if (!questState.rollAndGive(B[i][4], B[i][7], B[i][7], B[i][5], (double)B[i][6]) || B[i][1] == n2 || B[i][1] == 0) continue;
            questState.setCond(Integer.valueOf(B[i][1]).intValue());
            questState.setState(2);
        }
        return null;
    }
}
