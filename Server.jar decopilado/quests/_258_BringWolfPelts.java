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

public class _258_BringWolfPelts
extends Quest
implements ScriptFile {
    private static final int aty = 30001;
    private static final int atz = 20120;
    private static final int atA = 20442;
    private static final int atB = 702;
    private static final int atC = 390;
    private static final int atD = 29;
    private static final int atE = 22;
    private static final int atF = 1119;
    private static final int atG = 426;

    public _258_BringWolfPelts() {
        super(0);
        this.addStartNpc(30001);
        this.addKillId(new int[]{20120, 20442});
        this.addQuestItem(new int[]{702});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
            questState.setState(2);
            string2 = "lector_q0258_03.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getState();
        switch (n) {
            case 1: {
                if (questState.getPlayer().getLevel() >= 3) {
                    string = "lector_q0258_02.htm";
                    break;
                }
                string = "lector_q0258_01.htm";
                break;
            }
            case 2: {
                if (questState.getQuestItemsCount(702) >= 0L && questState.getQuestItemsCount(702) < 40L && questState.getCond() == 2) {
                    string = "lector_q0258_05.htm";
                }
                if (questState.getQuestItemsCount(702) < 40L) break;
                questState.takeItems(702, 40L);
                int n2 = Rnd.get((int)16);
                if (n2 == 0) {
                    questState.giveItems(390, 1L);
                    questState.playSound("ItemSound.quest_jackpot");
                } else if (n2 < 6) {
                    questState.giveItems(29, 1L);
                } else if (n2 < 9) {
                    questState.giveItems(22, 1L);
                } else if (n2 < 13) {
                    questState.giveItems(1119, 1L);
                } else {
                    questState.giveItems(426, 1L);
                }
                string = "lector_q0258_06.htm";
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if ((n == 20120 || n == 20442) && questState.getQuestItemsCount(702) < 40L) {
            questState.rollAndGive(702, 1, 100.0);
            if (questState.getQuestItemsCount(702) > 39L) {
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            }
        }
        return null;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
