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

public class _326_VanquishRemnants
extends Quest
implements ScriptFile {
    private static final int axo = 30435;
    private static final int axp = 20053;
    private static final int axq = 20058;
    private static final int axr = 20061;
    private static final int axs = 20063;
    private static final int axt = 20066;
    private static final int axu = 20436;
    private static final int axv = 20437;
    private static final int axw = 20438;
    private static final int axx = 20439;
    private static final int axy = 1359;
    private static final int axz = 1360;
    private static final int axA = 1361;
    private static final int axB = 1369;

    public _326_VanquishRemnants() {
        super(0);
        this.addStartNpc(30435);
        this.addKillId(new int[]{20053, 20058, 20061, 20063, 20066, 20436, 20437, 20438, 20439});
        this.addQuestItem(new int[]{1359, 1360, 1361});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30435) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("vanquish_remnants", String.valueOf(1), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "leopold_q0326_03.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                questState.unset("vanquish_remnants");
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
                string2 = "leopold_q0326_07.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "leopold_q0326_08.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("vanquish_remnants");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30435) break;
                if (questState.getPlayer().getLevel() < 21 || questState.getPlayer().getLevel() > 30) {
                    string = "leopold_q0326_01.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "leopold_q0326_02.htm";
                break;
            }
            case 2: {
                if (n2 != 30435 || n != 1) break;
                if (questState.getQuestItemsCount(1359) + questState.getQuestItemsCount(1360) + questState.getQuestItemsCount(1361) == 0L) {
                    string = "leopold_q0326_04.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1359) + questState.getQuestItemsCount(1360) + questState.getQuestItemsCount(1361) < 100L && questState.getQuestItemsCount(1359) + questState.getQuestItemsCount(1360) + questState.getQuestItemsCount(1361) > 0L) {
                    if (questState.getQuestItemsCount(1359) + questState.getQuestItemsCount(1360) + questState.getQuestItemsCount(1361) >= 10L) {
                        questState.giveItems(57, 4320L + 46L * questState.getQuestItemsCount(1359) + 52L * questState.getQuestItemsCount(1360) + 58L * questState.getQuestItemsCount(1361));
                    } else {
                        questState.giveItems(57, 46L * questState.getQuestItemsCount(1359) + 52L * questState.getQuestItemsCount(1360) + 58L * questState.getQuestItemsCount(1361));
                    }
                    this.giveExtraReward(questState.getPlayer());
                    questState.takeItems(1359, -1L);
                    questState.takeItems(1360, -1L);
                    questState.takeItems(1361, -1L);
                    string = "leopold_q0326_05.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1359) + questState.getQuestItemsCount(1360) + questState.getQuestItemsCount(1361) >= 100L && questState.getQuestItemsCount(1369) == 0L) {
                    this.giveExtraReward(questState.getPlayer());
                    questState.giveItems(1369, 1L);
                    questState.giveItems(57, 4320L + 46L * questState.getQuestItemsCount(1359) + 52L * questState.getQuestItemsCount(1360) + 58L * questState.getQuestItemsCount(1361));
                    questState.takeItems(1359, -1L);
                    questState.takeItems(1360, -1L);
                    questState.takeItems(1361, -1L);
                    string = "leopold_q0326_06.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1359) + questState.getQuestItemsCount(1360) + questState.getQuestItemsCount(1361) < 100L || questState.getQuestItemsCount(1369) <= 0L) break;
                this.giveExtraReward(questState.getPlayer());
                questState.giveItems(57, 4320L + 46L * questState.getQuestItemsCount(1359) + 52L * questState.getQuestItemsCount(1360) + 58L * questState.getQuestItemsCount(1361));
                questState.takeItems(1359, -1L);
                questState.takeItems(1360, -1L);
                questState.takeItems(1361, -1L);
                string = "leopold_q0326_09.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("vanquish_remnants");
        int n2 = npcInstance.getNpcId();
        if (n2 == 20053 || n2 == 20058) {
            if (n == 1 && Rnd.get((int)100) < 61) {
                questState.rollAndGive(1359, 1, 100.0);
            }
        } else if (n2 == 20061) {
            if (n == 1 && Rnd.get((int)100) < 57) {
                questState.rollAndGive(1360, 1, 100.0);
            }
        } else if (n2 == 20063) {
            if (n == 1 && Rnd.get((int)100) < 63) {
                questState.rollAndGive(1360, 1, 100.0);
            }
        } else if (n2 == 20066) {
            if (n == 1 && Rnd.get((int)100) < 59) {
                questState.rollAndGive(1361, 1, 100.0);
            }
        } else if (n2 == 20436) {
            if (n == 1 && Rnd.get((int)100) < 55) {
                questState.rollAndGive(1360, 1, 100.0);
            }
        } else if (n2 == 20437) {
            if (n == 1 && Rnd.get((int)100) < 59) {
                questState.rollAndGive(1359, 1, 100.0);
            }
        } else if (n2 == 20438) {
            if (n == 1 && Rnd.get((int)100) < 60) {
                questState.rollAndGive(1361, 1, 100.0);
            }
        } else if (n2 == 20439 && n == 1 && Rnd.get((int)100) < 62) {
            questState.rollAndGive(1360, 1, 100.0);
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
