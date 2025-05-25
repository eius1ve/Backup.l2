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

public class _042_HelpTheUncle
extends Quest
implements ScriptFile {
    private static final int Nh = 30828;
    private static final int Ni = 30735;
    private static final int Nj = 291;
    private static final int Nk = 7548;
    private static final int Nl = 7549;
    private static final int Nm = 7583;
    private static final int Nn = 20068;
    private static final int No = 20266;
    private static final int Np = 30;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _042_HelpTheUncle() {
        super(0);
        this.addStartNpc(30828);
        this.addTalkId(new int[]{30828});
        this.addTalkId(new int[]{30735});
        this.addKillId(new int[]{20068});
        this.addKillId(new int[]{20266});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("1")) {
            string2 = "pet_manager_waters_q0042_0104.htm";
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equals("3") && questState.getQuestItemsCount(291) > 0L) {
            string2 = "pet_manager_waters_q0042_0201.htm";
            questState.takeItems(291, 1L);
            questState.setCond(2);
        } else if (string.equals("4") && questState.getQuestItemsCount(7548) >= 30L) {
            string2 = "pet_manager_waters_q0042_0301.htm";
            questState.takeItems(7548, 30L);
            questState.giveItems(7549, 1L);
            questState.setCond(4);
        } else if (string.equals("5") && questState.getQuestItemsCount(7549) > 0L) {
            string2 = "sophia_q0042_0401.htm";
            questState.takeItems(7549, 1L);
            questState.setCond(5);
        } else if (string.equals("7")) {
            string2 = "pet_manager_waters_q0042_0501.htm";
            questState.giveItems(7583, 1L);
            this.giveExtraReward(questState.getPlayer());
            questState.playSound("ItemSound.quest_finish");
            questState.unset("cond");
            questState.exitCurrentQuest(false);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        int n3 = questState.getCond();
        if (n2 == 1) {
            if (questState.getPlayer().getLevel() >= 25) {
                string = "pet_manager_waters_q0042_0101.htm";
            } else {
                string = "pet_manager_waters_q0042_0103.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (n2 == 2) {
            if (n == 30828) {
                if (n3 == 1) {
                    string = questState.getQuestItemsCount(291) == 0L ? "pet_manager_waters_q0042_0106.htm" : "pet_manager_waters_q0042_0105.htm";
                } else if (n3 == 2) {
                    string = "pet_manager_waters_q0042_0204.htm";
                } else if (n3 == 3) {
                    string = "pet_manager_waters_q0042_0203.htm";
                } else if (n3 == 4) {
                    string = "pet_manager_waters_q0042_0303.htm";
                } else if (n3 == 5) {
                    string = "pet_manager_waters_q0042_0401.htm";
                }
            } else if (n == 30735) {
                if (n3 == 4 && questState.getQuestItemsCount(7549) > 0L) {
                    string = "sophia_q0042_0301.htm";
                } else if (n3 == 5) {
                    string = "sophia_q0042_0402.htm";
                }
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getCond();
        if (n == 2) {
            long l = questState.getQuestItemsCount(7548);
            if (l < 29L) {
                questState.giveItems(7548, 1L);
                questState.playSound("ItemSound.quest_itemget");
            } else if (l == 29L) {
                questState.giveItems(7548, 1L);
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(3);
            }
        }
        return null;
    }
}
