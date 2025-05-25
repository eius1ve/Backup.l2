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

public class _379_FantasyWine
extends Quest
implements ScriptFile {
    private static final int aUq = 30074;
    private static final int aUr = 20291;
    private static final int aUs = 20292;
    private static final int aUt = 5893;
    private static final int aUu = 5894;
    private static final int[] bK = new int[]{5956, 5957, 5958};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _379_FantasyWine() {
        super(0);
        this.addStartNpc(30074);
        this.addKillId(new int[]{20291});
        this.addKillId(new int[]{20292});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("hitsran_q0379_06.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("reward")) {
            questState.takeItems(5893, -1L);
            questState.takeItems(5894, -1L);
            int n = Rnd.get((int)100);
            if (n < 25) {
                questState.giveItems(bK[0], 1L);
                string2 = "hitsran_q0379_11.htm";
            } else if (n < 50) {
                questState.giveItems(bK[1], 1L);
                string2 = "hitsran_q0379_12.htm";
            } else {
                questState.giveItems(bK[2], 1L);
                string2 = "hitsran_q0379_13.htm";
            }
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
        } else if (string.equalsIgnoreCase("hitsran_q0379_05.htm")) {
            questState.exitCurrentQuest(true);
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
        if (n == 30074) {
            if (n3 == 0) {
                if (questState.getPlayer().getLevel() < 20) {
                    string = "hitsran_q0379_01.htm";
                    questState.exitCurrentQuest(true);
                } else {
                    string = "hitsran_q0379_02.htm";
                }
            } else if (n3 == 1) {
                string = questState.getQuestItemsCount(5893) < 80L && questState.getQuestItemsCount(5894) < 100L ? "hitsran_q0379_07.htm" : (questState.getQuestItemsCount(5893) == 80L && questState.getQuestItemsCount(5894) < 100L ? "hitsran_q0379_08.htm" : (questState.getQuestItemsCount(5893) < 80L && questState.getQuestItemsCount(5894) == 100L ? "hitsran_q0379_09.htm" : "hitsran_q0379_02.htm"));
            } else if (n3 == 2) {
                if (questState.getQuestItemsCount(5893) >= 80L && questState.getQuestItemsCount(5894) >= 100L) {
                    string = "hitsran_q0379_10.htm";
                } else {
                    questState.setCond(1);
                    if (questState.getQuestItemsCount(5893) < 80L && questState.getQuestItemsCount(5894) < 100L) {
                        string = "hitsran_q0379_07.htm";
                    } else if (questState.getQuestItemsCount(5893) >= 80L && questState.getQuestItemsCount(5894) < 100L) {
                        string = "hitsran_q0379_08.htm";
                    } else if (questState.getQuestItemsCount(5893) < 80L && questState.getQuestItemsCount(5894) >= 100L) {
                        string = "hitsran_q0379_09.htm";
                    }
                }
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (questState.getCond() == 1) {
            if (n == 20291 && questState.getQuestItemsCount(5893) < 80L) {
                questState.giveItems(5893, 1L);
            } else if (n == 20292 && questState.getQuestItemsCount(5894) < 100L) {
                questState.giveItems(5894, 1L);
            }
            if (questState.getQuestItemsCount(5893) >= 80L && questState.getQuestItemsCount(5894) >= 100L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(2);
            } else {
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
