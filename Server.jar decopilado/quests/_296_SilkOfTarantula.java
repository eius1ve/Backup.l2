/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _296_SilkOfTarantula
extends Quest
implements ScriptFile {
    private static final int avA = 30519;
    private static final int avB = 30548;
    private static final int avC = 20394;
    private static final int avD = 20403;
    private static final int avE = 20508;
    private static final int avF = 1493;
    private static final int avG = 1494;
    private static final int avH = 1508;
    private static final int avI = 1509;

    public _296_SilkOfTarantula() {
        super(0);
        this.addStartNpc(30519);
        this.addTalkId(new int[]{30548});
        this.addKillId(new int[]{20394, 20403, 20508});
        this.addQuestItem(new int[]{1493, 1494});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30519) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "trader_mion_q0296_03.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                questState.takeItems(1494, -1L);
                string2 = "trader_mion_q0296_06.htm";
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "trader_mion_q0296_07.htm";
            }
        } else if (n == 30548 && string.equalsIgnoreCase("reply_3")) {
            if (questState.getQuestItemsCount(1494) >= 1L) {
                string2 = "defender_nathan_q0296_03.htm";
                questState.giveItems(1493, (long)(15 + Rnd.get((int)9)) * questState.getQuestItemsCount(1494));
                questState.takeItems(1494, -1L);
            } else {
                string2 = "defender_nathan_q0296_02.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        Player player = questState.getPlayer();
        if (player == null) {
            return null;
        }
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 30519) break;
                if (player.getLevel() < 15) {
                    string = "trader_mion_q0296_01.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (questState.getQuestItemsCount(1508) > 0L || questState.getQuestItemsCount(1509) > 0L) {
                    string = "trader_mion_q0296_02.htm";
                    break;
                }
                string = "trader_mion_q0296_08.htm";
                break;
            }
            case 2: {
                if (n == 30519) {
                    if (questState.getQuestItemsCount(1493) < 1L) {
                        string = "trader_mion_q0296_04.htm";
                    } else if (questState.getQuestItemsCount(1493) >= 1L) {
                        if (questState.getQuestItemsCount(1493) >= 10L) {
                            questState.giveItems(57, questState.getQuestItemsCount(1493) * 30L + 2000L);
                        } else {
                            questState.giveItems(57, questState.getQuestItemsCount(1493) * 30L);
                        }
                        string = "trader_mion_q0296_05.htm";
                        questState.takeItems(1493, -1L);
                    }
                }
                if (n != 30548) break;
                string = "defender_nathan_q0296_01.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20394 || n == 20403 || n == 20508) {
            int n2 = Rnd.get((int)100);
            if (n2 > 95) {
                questState.rollAndGive(1494, 1, 100.0);
            } else if (n2 > 45) {
                questState.rollAndGive(1493, 1, 100.0);
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
