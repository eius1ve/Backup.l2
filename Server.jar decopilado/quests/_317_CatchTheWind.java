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

public class _317_CatchTheWind
extends Quest
implements ScriptFile {
    private static final int awD = 30361;
    private static final int awE = 20036;
    private static final int awF = 20044;
    private static final int awG = 1078;

    public _317_CatchTheWind() {
        super(0);
        this.addStartNpc(30361);
        this.addKillId(new int[]{20036, 20044});
        this.addQuestItem(new int[]{1078});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "rizraell_q0317_04.htm";
        } else if (string.equalsIgnoreCase("reply_2")) {
            if (questState.getQuestItemsCount(1078) > 0L) {
                if (questState.getQuestItemsCount(1078) >= 10L) {
                    questState.giveItems(57, 2988L + 40L * questState.getQuestItemsCount(1078));
                } else {
                    questState.giveItems(57, 40L * questState.getQuestItemsCount(1078));
                }
            }
            questState.takeItems(1078, -1L);
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
            string2 = "rizraell_q0317_08.htm";
        } else if (string.equalsIgnoreCase("reply_3")) {
            if (questState.getQuestItemsCount(1078) > 0L) {
                if (questState.getQuestItemsCount(1078) >= 10L) {
                    questState.giveItems(57, 2988L + 40L * questState.getQuestItemsCount(1078));
                } else {
                    questState.giveItems(57, 40L * questState.getQuestItemsCount(1078));
                }
            }
            questState.takeItems(1078, -1L);
            string2 = "rizraell_q0317_09.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 30361) break;
                if (questState.getPlayer().getLevel() < 18 || questState.getPlayer().getLevel() > 23) {
                    string = "rizraell_q0317_02.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "rizraell_q0317_03.htm";
                break;
            }
            case 2: {
                if (n != 30361) break;
                if (questState.getQuestItemsCount(1078) == 0L) {
                    string = "rizraell_q0317_05.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1078) == 0L) break;
                string = "rizraell_q0317_07.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = Rnd.get((int)100);
        if (n < 50) {
            questState.rollAndGive(1078, 1, 100.0);
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
