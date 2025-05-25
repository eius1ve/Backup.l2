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

public class _328_SenseForBusiness
extends Quest
implements ScriptFile {
    private final int ayg = 30436;
    private final int ayh = 1347;
    private final int ayi = 1366;
    private final int ayj = 1348;

    public _328_SenseForBusiness() {
        super(0);
        this.addStartNpc(30436);
        this.addKillId(new int[]{20055, 20059, 20067, 20068, 20070, 20072});
        this.addQuestItem(new int[]{1347, 1366, 1348});
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "trader_salient_q0328_03.htm";
        } else if (string.equalsIgnoreCase("reply_1")) {
            string2 = "trader_salient_q0328_06.htm";
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
        } else if (string.equalsIgnoreCase("reply_2")) {
            string2 = "trader_salient_q0328_07.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 30436) break;
                if (questState.getPlayer().getLevel() >= 21) {
                    string = "trader_salient_q0328_02.htm";
                    break;
                }
                string = "trader_salient_q0328_01.htm";
                break;
            }
            case 2: {
                if (n != 30436) break;
                if (questState.getQuestItemsCount(1347) + questState.getQuestItemsCount(1366) + questState.getQuestItemsCount(1348) > 0L) {
                    if (questState.getQuestItemsCount(1347) + questState.getQuestItemsCount(1366) + questState.getQuestItemsCount(1348) >= 10L) {
                        questState.giveItems(57, 618L + 25L * questState.getQuestItemsCount(1347) + 1000L * questState.getQuestItemsCount(1366) + 60L * questState.getQuestItemsCount(1348));
                    } else {
                        questState.giveItems(57, 25L * questState.getQuestItemsCount(1347) + 1000L * questState.getQuestItemsCount(1366) + 60L * questState.getQuestItemsCount(1348));
                    }
                    this.giveExtraReward(questState.getPlayer());
                    questState.takeItems(1347, -1L);
                    questState.takeItems(1366, -1L);
                    questState.takeItems(1348, -1L);
                    string = "trader_salient_q0328_05.htm";
                    break;
                }
                string = "trader_salient_q0328_04.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = Rnd.get((int)100);
        if (n == 20055) {
            if (n2 < 47) {
                questState.rollAndGive(1347, 1, 100.0);
            } else if (n2 < 49) {
                questState.rollAndGive(1366, 1, 100.0);
            }
        } else if (n == 20059) {
            if (n2 < 51) {
                questState.rollAndGive(1347, 1, 100.0);
            } else if (n2 < 53) {
                questState.rollAndGive(1366, 1, 100.0);
            }
        } else if (n == 20067) {
            if (n2 < 67) {
                questState.rollAndGive(1347, 1, 100.0);
            } else if (n2 < 69) {
                questState.rollAndGive(1366, 1, 100.0);
            }
        } else if (n == 20068) {
            if (n2 < 75) {
                questState.rollAndGive(1347, 1, 100.0);
            } else if (n2 < 77) {
                questState.rollAndGive(1366, 1, 100.0);
            }
        } else if (n == 20070) {
            if (n2 < 50) {
                questState.rollAndGive(1348, 1, 100.0);
            }
        } else if (n == 20072 && n2 < 51) {
            questState.rollAndGive(1348, 1, 100.0);
        }
        return null;
    }
}
