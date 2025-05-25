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

public class _264_KeenClaws
extends Quest
implements ScriptFile {
    private static final int auf = 30136;
    private static final int aug = 1367;
    private static final int auh = 36;
    private static final int aui = 43;
    private static final int auj = 462;
    private static final int auk = 1061;
    private static final int aul = 48;
    private static final int aum = 35;
    private static final int aun = 20003;
    private static final int auo = 20456;
    private static final int[][] w = new int[][]{{1, 2, 20003, 0, 1367, 50, 50, 2}, {1, 2, 20456, 0, 1367, 50, 50, 2}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _264_KeenClaws() {
        super(0);
        this.addStartNpc(30136);
        this.addKillId(new int[]{20003});
        this.addKillId(new int[]{20456});
        this.addQuestItem(new int[]{1367});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("paint_q0264_03.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        }
        return string2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n != 30136) return string;
        if (n2 == 0) {
            if (questState.getPlayer().getLevel() >= 3) {
                return "paint_q0264_02.htm";
            }
            questState.exitCurrentQuest(true);
            return "paint_q0264_01.htm";
        }
        if (n2 == 1) {
            return "paint_q0264_04.htm";
        }
        if (n2 != 2) return string;
        questState.takeItems(1367, -1L);
        int n3 = Rnd.get((int)17);
        if (n3 == 0) {
            questState.giveItems(43, 1L);
            questState.playSound("ItemSound.quest_jackpot");
        } else if (n3 < 2) {
            questState.giveItems(57, 1000L);
        } else if (n3 < 5) {
            questState.giveItems(36, 1L);
        } else if (n3 < 8) {
            questState.giveItems(462, 1L);
            questState.giveItems(57, 50L);
        } else if (n3 < 11) {
            questState.giveItems(1061, 1L);
        } else if (n3 < 14) {
            questState.giveItems(48, 1L);
        } else {
            questState.giveItems(35, 1L);
        }
        string = "paint_q0264_05.htm";
        questState.playSound("ItemSound.quest_finish");
        this.giveExtraReward(questState.getPlayer());
        questState.exitCurrentQuest(true);
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        for (int i = 0; i < w.length; ++i) {
            if (n2 != w[i][0] || n != w[i][2] || w[i][3] != 0 && questState.getQuestItemsCount(w[i][3]) <= 0L) continue;
            if (w[i][5] == 0) {
                questState.rollAndGive(w[i][4], w[i][7], (double)w[i][6]);
                continue;
            }
            if (!questState.rollAndGive(w[i][4], w[i][7], w[i][7], w[i][5], (double)w[i][6]) || w[i][1] == n2 || w[i][1] == 0) continue;
            questState.setCond(Integer.valueOf(w[i][1]).intValue());
            questState.setState(2);
        }
        return null;
    }
}
