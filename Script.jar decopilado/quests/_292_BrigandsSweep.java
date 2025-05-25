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

public class _292_BrigandsSweep
extends Quest
implements ScriptFile {
    private static int afE = 30532;
    private static int afG = 30533;
    private static int avh = 20322;
    private static int avi = 20323;
    private static int avj = 20324;
    private static int avk = 20327;
    private static int avl = 20528;
    private static int avm = 1483;
    private static int avn = 1484;
    private static int avo = 1485;
    private static int avp = 1486;
    private static int avq = 1487;
    private static int avr = 10;
    private static final int[][] z = new int[][]{{1, 0, avh, 0, avm, 0, 40, 1}, {1, 0, avi, 0, avm, 0, 40, 1}, {1, 0, avk, 0, avm, 0, 40, 1}, {1, 0, avj, 0, avn, 0, 40, 1}, {1, 0, avl, 0, avo, 0, 40, 1}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _292_BrigandsSweep() {
        super(0);
        this.addStartNpc(afE);
        this.addTalkId(new int[]{afG});
        for (int i = 0; i < z.length; ++i) {
            this.addKillId(new int[]{z[i][2]});
        }
        this.addQuestItem(new int[]{avp});
        this.addQuestItem(new int[]{avq});
        this.addQuestItem(new int[]{avm});
        this.addQuestItem(new int[]{avn});
        this.addQuestItem(new int[]{avo});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        if (string.equalsIgnoreCase("elder_spiron_q0292_03.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("elder_spiron_q0292_06.htm")) {
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
        }
        return string;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getCond();
        if (n == afE) {
            if (n2 == 0) {
                if (questState.getPlayer().getRace() != Race.dwarf) {
                    string = "elder_spiron_q0292_00.htm";
                    questState.exitCurrentQuest(true);
                } else if (questState.getPlayer().getLevel() < 5) {
                    string = "elder_spiron_q0292_01.htm";
                    questState.exitCurrentQuest(true);
                } else {
                    string = "elder_spiron_q0292_02.htm";
                }
            } else if (n2 == 1) {
                long l = questState.getQuestItemsCount(avm) * 12L + questState.getQuestItemsCount(avn) * 36L + questState.getQuestItemsCount(avo) * 33L + questState.getQuestItemsCount(avq) * 100L;
                if (l == 0L) {
                    return "elder_spiron_q0292_04.htm";
                }
                string = questState.getQuestItemsCount(avq) != 0L ? "elder_spiron_q0292_10.htm" : (questState.getQuestItemsCount(avp) == 0L ? "elder_spiron_q0292_05.htm" : (questState.getQuestItemsCount(avp) == 1L ? "elder_spiron_q0292_08.htm" : "elder_spiron_q0292_09.htm"));
                questState.takeItems(avm, -1L);
                questState.takeItems(avn, -1L);
                questState.takeItems(avo, -1L);
                questState.takeItems(avq, -1L);
                questState.giveItems(57, l);
                this.giveExtraReward(questState.getPlayer());
            }
        } else if (n == afG && n2 == 1) {
            if (questState.getQuestItemsCount(avq) == 0L) {
                string = "balanki_q0292_01.htm";
            } else {
                questState.takeItems(avq, -1L);
                questState.giveItems(57, 120L);
                string = "balanki_q0292_02.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        for (int i = 0; i < z.length; ++i) {
            if (n2 != z[i][0] || n != z[i][2] || z[i][3] != 0 && questState.getQuestItemsCount(z[i][3]) <= 0L) continue;
            if (z[i][5] == 0) {
                questState.rollAndGive(z[i][4], z[i][7], (double)z[i][6]);
                continue;
            }
            if (!questState.rollAndGive(z[i][4], z[i][7], z[i][7], z[i][5], (double)z[i][6]) || z[i][1] == n2 || z[i][1] == 0) continue;
            questState.setCond(Integer.valueOf(z[i][1]).intValue());
            questState.setState(2);
        }
        if (questState.getQuestItemsCount(avq) == 0L && Rnd.chance((int)avr)) {
            if (questState.getQuestItemsCount(avp) < 3L) {
                questState.giveItems(avp, 1L);
                questState.playSound("ItemSound.quest_itemget");
            } else {
                questState.takeItems(avp, -1L);
                questState.giveItems(avq, 1L);
                questState.playSound("ItemSound.quest_middle");
            }
        }
        return null;
    }
}
