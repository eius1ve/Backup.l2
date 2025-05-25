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

public class _117_OceanOfDistantStar
extends Quest
implements ScriptFile {
    private static final int Rr = 32053;
    private static final int Rs = 32055;
    private static final int Rt = 32052;
    private static final int Ru = 32054;
    private static final int Rv = 32076;
    private static final int Rw = 8495;
    private static final int Rx = 8488;
    private static final int Ry = 22023;
    private static final int Rz = 22024;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _117_OceanOfDistantStar() {
        super(0);
        this.addStartNpc(32053);
        this.addTalkId(new int[]{32055});
        this.addTalkId(new int[]{32052});
        this.addTalkId(new int[]{32076});
        this.addTalkId(new int[]{32054});
        this.addKillId(new int[]{22023});
        this.addKillId(new int[]{22024});
        this.addQuestItem(new int[]{8495, 8488});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("railman_abu_q0117_0104.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("ghost_of_railroadman2_q0117_0201.htm")) {
            questState.setCond(2);
        } else if (string.equalsIgnoreCase("railman_obi_q0117_0301.htm")) {
            questState.setCond(3);
        } else if (string.equalsIgnoreCase("railman_abu_q0117_0401.htm")) {
            questState.setCond(4);
        } else if (string.equalsIgnoreCase("q_box_of_railroad_q0117_0501.htm")) {
            questState.setCond(5);
            questState.giveItems(8488, 1L);
        } else if (string.equalsIgnoreCase("railman_abu_q0117_0601.htm")) {
            questState.setCond(6);
        } else if (string.equalsIgnoreCase("railman_obi_q0117_0701.htm")) {
            questState.setCond(7);
        } else if (string.equalsIgnoreCase("railman_obi_q0117_0801.htm")) {
            questState.takeItems(8495, -1L);
            questState.setCond(9);
        } else if (string.equalsIgnoreCase("ghost_of_railroadman2_q0117_0901.htm")) {
            questState.takeItems(8488, -1L);
            questState.setCond(10);
        } else if (string.equalsIgnoreCase("ghost_of_railroadman_q0117_1002.htm")) {
            questState.addExpAndSp(63591L, 0L);
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(false);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        int n3 = 0;
        if (n2 != 1) {
            n3 = questState.getCond();
        }
        if (n == 32053) {
            if (n3 == 0) {
                if (questState.getPlayer().getLevel() >= 39) {
                    string = "railman_abu_q0117_0101.htm";
                } else {
                    string = "railman_abu_q0117_0103.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n3 == 3) {
                string = "railman_abu_q0117_0301.htm";
            } else if (n3 == 5 && questState.getQuestItemsCount(8488) > 0L) {
                string = "railman_abu_q0117_0501.htm";
            } else if (n3 == 6 && questState.getQuestItemsCount(8488) > 0L) {
                string = "railman_abu_q0117_0601.htm";
            }
        } else if (n == 32055) {
            if (n3 == 1) {
                string = "ghost_of_railroadman2_q0117_0101.htm";
            } else if (n3 == 9 && questState.getQuestItemsCount(8488) > 0L) {
                string = "ghost_of_railroadman2_q0117_0801.htm";
            }
        } else if (n == 32052) {
            if (n3 == 2) {
                string = "railman_obi_q0117_0201.htm";
            } else if (n3 == 6 && questState.getQuestItemsCount(8488) > 0L) {
                string = "railman_obi_q0117_0601.htm";
            } else if (n3 == 7 && questState.getQuestItemsCount(8488) > 0L) {
                string = "railman_obi_q0117_0701.htm";
            } else if (n3 == 8 && questState.getQuestItemsCount(8495) > 0L) {
                string = "railman_obi_q0117_0704.htm";
            }
        } else if (n == 32076 && n3 == 4) {
            string = "q_box_of_railroad_q0117_0401.htm";
        } else if (n == 32054 && n3 == 10) {
            string = "ghost_of_railroadman_q0117_0901.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getCond() == 7 && Rnd.chance((int)30)) {
            if (questState.getQuestItemsCount(8495) < 1L) {
                questState.giveItems(8495, 1L);
                questState.playSound("ItemSound.quest_itemget");
            }
            questState.setCond(8);
            questState.setState(2);
        }
        return null;
    }
}
