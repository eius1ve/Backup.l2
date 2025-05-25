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

public class _631_DeliciousTopChoiceMeat
extends Quest
implements ScriptFile {
    private static final int bxn = 31537;
    private static final int[] bP = new int[]{21460, 21461, 21462, 21463, 21464, 21465, 21466, 21467, 21468, 21469, 21479, 21480, 21481, 21482, 21483, 21484, 21485, 21486, 21487, 21488, 21498, 21499, 21500, 21501, 21502, 21503, 21504, 21505, 21506, 21507};
    private static final int bxo = 7546;
    private static final int bxp = 4039;
    private static final int bxq = 4040;
    private static final int bxr = 4041;
    private static final int bxs = 4042;
    private static final int bxt = 4043;
    private static final int bxu = 4044;
    private static final int[][] X = new int[][]{{1, 4039, 15}, {2, 4043, 15}, {3, 4044, 15}, {4, 4040, 10}, {5, 4042, 10}, {6, 4041, 5}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _631_DeliciousTopChoiceMeat() {
        super(0);
        this.addStartNpc(31537);
        this.addTalkId(new int[]{31537});
        for (int n : bP) {
            this.addKillId(new int[]{n});
        }
        this.addQuestItem(new int[]{7546});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("31537-03.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("31537-05.htm") && questState.getQuestItemsCount(7546) >= 120L) {
            questState.setCond(3);
        }
        for (int[] nArray : X) {
            if (!string.equalsIgnoreCase(String.valueOf(nArray[0]))) continue;
            if (questState.getCond() == 3 && questState.getQuestItemsCount(7546) >= 120L) {
                string2 = "31537-06.htm";
                questState.takeItems(7546, -1L);
                questState.giveItems(nArray[1], (long)(nArray[2] * (int)questState.getRateQuestsReward()));
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
                continue;
            }
            string2 = "31537-07.htm";
            questState.setCond(1);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        if (n < 1) {
            if (questState.getPlayer().getLevel() < 65) {
                string = "31537-02.htm";
                questState.exitCurrentQuest(true);
            } else {
                string = "31537-01.htm";
            }
        } else if (n == 1) {
            string = "31537-01a.htm";
        } else if (n == 2) {
            if (questState.getQuestItemsCount(7546) < 120L) {
                string = "31537-01a.htm";
                questState.setCond(1);
            } else {
                string = "31537-04.htm";
            }
        } else if (n == 3) {
            string = "31537-05.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (questState.getCond() == 1) {
            for (int n2 : bP) {
                if (!Rnd.chance((int)80) || n != n2) continue;
                questState.rollAndGive(7546, 1, 100.0);
                if (questState.getQuestItemsCount(7546) < 120L) {
                    questState.playSound("ItemSound.quest_itemget");
                    continue;
                }
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(2);
            }
        }
        return null;
    }
}
