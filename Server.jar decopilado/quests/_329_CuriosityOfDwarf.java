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

public class _329_CuriosityOfDwarf
extends Quest
implements ScriptFile {
    private static final int ayk = 1346;
    private static final int ayl = 1365;
    private static final int aym = 30437;
    private static final int ayn = 20083;
    private static final int ayo = 20085;

    public _329_CuriosityOfDwarf() {
        super(0);
        this.addStartNpc(30437);
        this.addKillId(new int[]{20083, 20085});
        this.addQuestItem(new int[]{1365, 1346});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "trader_rolento_q0329_03.htm";
        } else if (string.equalsIgnoreCase("reply_1")) {
            string2 = "trader_rolento_q0329_06.htm";
            questState.exitCurrentQuest(true);
            questState.playSound("ItemSound.quest_finish");
        } else if (string.equalsIgnoreCase("reply_2")) {
            string2 = "trader_rolento_q0329_07.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 30437) break;
                if (questState.getPlayer().getLevel() >= 33) {
                    string = "trader_rolento_q0329_02.htm";
                    break;
                }
                string = "trader_rolento_q0329_01.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n != 30437) break;
                if (questState.getQuestItemsCount(1365) + questState.getQuestItemsCount(1346) > 0L) {
                    if (questState.getQuestItemsCount(1365) + questState.getQuestItemsCount(1346) >= 10L) {
                        questState.giveItems(57, 1183L + 50L * questState.getQuestItemsCount(1365) + 1000L * questState.getQuestItemsCount(1346));
                    } else {
                        questState.giveItems(57, 50L * questState.getQuestItemsCount(1365) + 1000L * questState.getQuestItemsCount(1346));
                    }
                    this.giveExtraReward(questState.getPlayer());
                    questState.takeItems(1365, -1L);
                    questState.takeItems(1346, -1L);
                    string = "trader_rolento_q0329_05.htm";
                    break;
                }
                string = "trader_rolento_q0329_04.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = Rnd.get((int)100);
        if (n == 20085) {
            if (n2 < 5) {
                questState.rollAndGive(1346, 1, 100.0);
            } else if (n2 < 58) {
                questState.rollAndGive(1365, 1, 100.0);
            }
        } else if (n == 20083) {
            if (n2 < 6) {
                questState.rollAndGive(1346, 1, 100.0);
            } else if (n2 < 56) {
                questState.rollAndGive(1365, 1, 100.0);
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
