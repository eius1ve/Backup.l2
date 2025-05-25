/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _272_WrathOfAncestors
extends Quest
implements ScriptFile {
    private static final int auI = 30572;
    private static final int auJ = 1474;
    private static final int auK = 20319;
    private static final int auL = 20320;
    private static final int[][] y = new int[][]{{1, 2, 20319, 0, 1474, 50, 100, 1}, {1, 2, 20320, 0, 1474, 50, 100, 1}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _272_WrathOfAncestors() {
        super(0);
        this.addStartNpc(30572);
        for (int i = 0; i < y.length; ++i) {
            this.addKillId(new int[]{y[i][2]});
        }
        this.addQuestItem(new int[]{1474});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("1")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "seer_livina_q0272_03.htm";
        }
        return string2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getCond();
        if (n != 30572) return string;
        if (n2 == 0) {
            if (questState.getPlayer().getRace() != Race.orc) {
                string = "seer_livina_q0272_00.htm";
                questState.exitCurrentQuest(true);
                return string;
            } else {
                if (questState.getPlayer().getLevel() >= 5) return "seer_livina_q0272_02.htm";
                string = "seer_livina_q0272_01.htm";
                questState.exitCurrentQuest(true);
            }
            return string;
        } else {
            if (n2 == 1) {
                return "seer_livina_q0272_04.htm";
            }
            if (n2 != 2) return string;
            questState.takeItems(1474, -1L);
            questState.giveItems(57, 1500L);
            string = "seer_livina_q0272_05.htm";
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        for (int i = 0; i < y.length; ++i) {
            if (n2 != y[i][0] || n != y[i][2] || y[i][3] != 0 && questState.getQuestItemsCount(y[i][3]) <= 0L) continue;
            if (y[i][5] == 0) {
                questState.rollAndGive(y[i][4], y[i][7], (double)y[i][6]);
                continue;
            }
            if (!questState.rollAndGive(y[i][4], y[i][7], y[i][7], y[i][5], (double)y[i][6]) || y[i][1] == n2 || y[i][1] == 0) continue;
            questState.setCond(Integer.valueOf(y[i][1]).intValue());
            questState.setState(2);
        }
        return null;
    }
}
