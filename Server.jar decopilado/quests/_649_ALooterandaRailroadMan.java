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

public class _649_ALooterandaRailroadMan
extends Quest
implements ScriptFile {
    private static final int bCe = 32052;
    private static final int bCf = 8099;
    private static final int[][] Z = new int[][]{{1, 2, 22017, 0, 8099, 200, 50, 1}, {1, 2, 22018, 0, 8099, 200, 50, 1}, {1, 2, 22019, 0, 8099, 200, 50, 1}, {1, 2, 22021, 0, 8099, 200, 50, 1}, {1, 2, 22022, 0, 8099, 200, 50, 1}, {1, 2, 22023, 0, 8099, 200, 50, 1}, {1, 2, 22024, 0, 8099, 200, 50, 1}, {1, 2, 22026, 0, 8099, 200, 50, 1}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _649_ALooterandaRailroadMan() {
        super(1);
        this.addStartNpc(32052);
        for (int i = 0; i < Z.length; ++i) {
            this.addKillId(new int[]{Z[i][2]});
        }
        this.addQuestItem(new int[]{8099});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            string2 = "railman_obi_q0649_0103.htm";
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("649_3")) {
            if (questState.getQuestItemsCount(8099) >= 200L) {
                string2 = "railman_obi_q0649_0201.htm";
                questState.takeItems(8099, -1L);
                questState.giveItems(57, 21698L, true);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
            } else {
                questState.setCond(1);
                string2 = "railman_obi_q0649_0202.htm";
            }
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
        if (n == 32052) {
            if (n3 == 0) {
                if (questState.getPlayer().getLevel() < 30) {
                    string = "railman_obi_q0649_0102.htm";
                    questState.exitCurrentQuest(true);
                } else {
                    string = "railman_obi_q0649_0101.htm";
                }
            } else if (n3 == 1) {
                string = "railman_obi_q0649_0106.htm";
            } else if (n3 == 2 && questState.getQuestItemsCount(8099) == 200L) {
                string = "railman_obi_q0649_0105.htm";
            } else {
                string = "railman_obi_q0649_0106.htm";
                questState.setCond(1);
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        for (int i = 0; i < Z.length; ++i) {
            if (n2 != Z[i][0] || n != Z[i][2] || Z[i][3] != 0 && questState.getQuestItemsCount(Z[i][3]) <= 0L) continue;
            if (Z[i][5] == 0) {
                questState.rollAndGive(Z[i][4], Z[i][7], (double)Z[i][6]);
                continue;
            }
            if (!questState.rollAndGive(Z[i][4], Z[i][7], Z[i][7], Z[i][5], (double)Z[i][6]) || Z[i][1] == n2 || Z[i][1] == 0) continue;
            questState.setCond(Integer.valueOf(Z[i][1]).intValue());
            questState.setState(2);
        }
        return null;
    }
}
