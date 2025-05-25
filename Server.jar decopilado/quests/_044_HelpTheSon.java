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

public class _044_HelpTheSon
extends Quest
implements ScriptFile {
    private static final int Nz = 30827;
    private static final int NA = 30505;
    private static final int NB = 168;
    private static final int NC = 7552;
    private static final int ND = 7553;
    private static final int NE = 7585;
    private static final int NF = 20921;
    private static final int NG = 20920;
    private static final int NH = 20919;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _044_HelpTheSon() {
        super(0);
        this.addStartNpc(30827);
        this.addTalkId(new int[]{30505});
        this.addKillId(new int[]{20921});
        this.addKillId(new int[]{20920});
        this.addKillId(new int[]{20919});
        this.addQuestItem(new int[]{7552});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("1")) {
            string2 = "pet_manager_lundy_q0044_0104.htm";
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equals("3") && questState.getQuestItemsCount(168) > 0L) {
            string2 = "pet_manager_lundy_q0044_0201.htm";
            questState.takeItems(168, 1L);
            questState.setCond(2);
        } else if (string.equals("4") && questState.getQuestItemsCount(7552) >= 30L) {
            string2 = "pet_manager_lundy_q0044_0301.htm";
            questState.takeItems(7552, -1L);
            questState.giveItems(7553, 1L);
            questState.setCond(4);
        } else if (string.equals("5") && questState.getQuestItemsCount(7553) > 0L) {
            string2 = "high_prefect_drikus_q0044_0401.htm";
            questState.takeItems(7553, 1L);
            questState.setCond(5);
        } else if (string.equals("7")) {
            string2 = "pet_manager_lundy_q0044_0501.htm";
            questState.giveItems(7585, 1L);
            this.giveExtraReward(questState.getPlayer());
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(false);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getState();
        if (n2 == 1) {
            if (questState.getPlayer().getLevel() >= 24) {
                string = "pet_manager_lundy_q0044_0101.htm";
            } else {
                questState.exitCurrentQuest(true);
                string = "pet_manager_lundy_q0044_0103.htm";
            }
        } else if (n2 == 2) {
            int n3 = questState.getCond();
            if (n == 30827) {
                if (n3 == 1) {
                    string = questState.getQuestItemsCount(168) == 0L ? "pet_manager_lundy_q0044_0106.htm" : "pet_manager_lundy_q0044_0105.htm";
                } else if (n3 == 2) {
                    string = "pet_manager_lundy_q0044_0204.htm";
                } else if (n3 == 3) {
                    string = "pet_manager_lundy_q0044_0203.htm";
                } else if (n3 == 4) {
                    string = "pet_manager_lundy_q0044_0303.htm";
                } else if (n3 == 5) {
                    string = "pet_manager_lundy_q0044_0401.htm";
                }
            } else if (n == 30505) {
                if (n3 == 4 && questState.getQuestItemsCount(7553) > 0L) {
                    string = "high_prefect_drikus_q0044_0301.htm";
                } else if (n3 == 5) {
                    string = "high_prefect_drikus_q0044_0403.htm";
                }
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getCond();
        if (n == 2 && questState.getQuestItemsCount(7552) < 30L) {
            questState.giveItems(7552, 1L);
            if (questState.getQuestItemsCount(7552) >= 30L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(3);
                questState.playSound("ItemSound.quest_itemget");
            } else {
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
