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

public class _298_LizardmensConspiracy
extends Quest
implements ScriptFile {
    private static final int avL = 30333;
    private static final int avM = 30344;
    private static final int avN = 20922;
    private static final int avO = 20923;
    private static final int avP = 20924;
    private static final int avQ = 20926;
    private static final int avR = 20927;
    private static final int avS = 7182;
    private static final int avT = 7183;
    private static final int avU = 7184;
    private static final int[][] A = new int[][]{{20922, 7183}, {20923, 7183}, {20924, 7183}, {20926, 7184}, {20927, 7184}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _298_LizardmensConspiracy() {
        super(0);
        this.addStartNpc(30333);
        this.addTalkId(new int[]{30333});
        this.addTalkId(new int[]{30344});
        for (int[] nArray : A) {
            this.addKillId(new int[]{nArray[0]});
        }
        this.addQuestItem(new int[]{7182, 7183, 7184});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("guard_praga_q0298_0104.htm")) {
            questState.setState(2);
            questState.setCond(1);
            questState.giveItems(7182, 1L);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("magister_rohmer_q0298_0201.htm")) {
            questState.takeItems(7182, -1L);
            questState.setCond(2);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("magister_rohmer_q0298_0301.htm") && questState.getQuestItemsCount(7183) + questState.getQuestItemsCount(7184) > 99L) {
            questState.takeItems(7183, -1L);
            questState.takeItems(7184, -1L);
            questState.addExpAndSp(0L, 42000L);
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
            questState.playSound("ItemSound.quest_finish");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 30333) {
            if (n2 < 1) {
                if (questState.getPlayer().getLevel() < 25) {
                    string = "guard_praga_q0298_0102.htm";
                    questState.exitCurrentQuest(true);
                } else {
                    string = "guard_praga_q0298_0101.htm";
                }
            }
            if (n2 == 1) {
                string = "guard_praga_q0298_0105.htm";
            }
        } else if (n == 30344) {
            if (n2 < 1) {
                string = "magister_rohmer_q0298_0202.htm";
            } else if (n2 == 1) {
                string = "magister_rohmer_q0298_0101.htm";
            } else if (n2 == 2 | questState.getQuestItemsCount(7183) + questState.getQuestItemsCount(7184) < 100L) {
                string = "magister_rohmer_q0298_0204.htm";
                questState.setCond(2);
            } else if (n2 == 3 && questState.getQuestItemsCount(7183) + questState.getQuestItemsCount(7184) > 99L) {
                string = "magister_rohmer_q0298_0203.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = Rnd.get((int)10);
        if (questState.getCond() == 2) {
            for (int[] nArray : A) {
                if (n != nArray[0] || n2 >= 6 || questState.getQuestItemsCount(nArray[1]) >= 50L) continue;
                if (n2 < 2 && nArray[1] == 7183) {
                    questState.giveItems(nArray[1], 2L);
                } else {
                    questState.giveItems(nArray[1], 1L);
                }
                if (questState.getQuestItemsCount(7183) + questState.getQuestItemsCount(7184) > 99L) {
                    questState.setCond(3);
                    questState.playSound("ItemSound.quest_middle");
                    continue;
                }
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
