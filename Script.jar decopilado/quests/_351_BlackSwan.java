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

public class _351_BlackSwan
extends Quest
implements ScriptFile {
    private static final int aMc = 30916;
    private static final int aMd = 30969;
    private static final int aMe = 30897;
    private static final int aMf = 20784;
    private static final int aMg = 20785;
    private static final int aMh = 21639;
    private static final int aMi = 21640;
    private static final int aMj = 4296;
    private static final int aMk = 4297;
    private static final int aMl = 4298;
    private static final int aMm = 4407;
    private static final int aMn = 1867;
    private static final int aMo = 1872;
    private static final int aMp = 1870;
    private static final int aMq = 1871;
    private static final int aMr = 1882;
    private static final int aMs = 1879;
    private static final int aMt = 1881;
    private static final int aMu = 1874;
    private static final int aMv = 1875;
    private static final int aMw = 1894;
    private static final int aMx = 1888;
    private static final int aMy = 1887;
    private static final int aMz = 5220;

    public _351_BlackSwan() {
        super(0);
        this.addStartNpc(30916);
        this.addTalkId(new int[]{30969, 30897});
        this.addKillId(new int[]{20784, 20785, 21639, 21640});
        this.addQuestItem(new int[]{4296, 4297, 4298, 4407});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30916) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("black_swan", String.valueOf(1), true);
                questState.giveItems(4296, 1L);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "captain_gosta_q0351_04.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                string2 = "captain_gosta_q0351_03.htm";
            }
        } else if (n == 30969) {
            if (string.equalsIgnoreCase("reply_1")) {
                if (questState.getQuestItemsCount(4297) == 0L) {
                    string2 = "iason_haine_q0351_02.htm";
                } else if (questState.getQuestItemsCount(4297) >= 10L) {
                    questState.giveItems(57, 3880L + 20L * questState.getQuestItemsCount(4297));
                } else {
                    questState.giveItems(57, 20L * questState.getQuestItemsCount(4297));
                }
                questState.takeItems(4297, -1L);
                string2 = "iason_haine_q0351_03.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                if (questState.getQuestItemsCount(4298) == 0L) {
                    string2 = "iason_haine_q0351_04.htm";
                } else {
                    questState.setCond(2);
                    questState.giveItems(4407, questState.getQuestItemsCount(4298));
                    questState.giveItems(57, 3880L);
                    questState.takeItems(4298, -1L);
                    string2 = "iason_haine_q0351_05.htm";
                }
            } else if (string.equalsIgnoreCase("reply_3")) {
                string2 = "iason_haine_q0351_06.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                string2 = questState.getQuestItemsCount(4298) == 0L && questState.getQuestItemsCount(4297) == 0L ? "iason_haine_q0351_07.htm" : "iason_haine_q0351_08.htm";
            } else if (string.equalsIgnoreCase("reply_5")) {
                if (questState.getQuestItemsCount(4296) > 0L) {
                    questState.takeItems(4296, -1L);
                }
                questState.unset("black_swan");
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
                string2 = "iason_haine_q0351_09.htm";
            }
        } else if (n == 30897) {
            if (string.equalsIgnoreCase("reply_1")) {
                if (questState.getQuestItemsCount(4407) > 0L) {
                    questState.giveItems(57, 700L);
                    questState.takeItems(4407, 1L);
                    string2 = "head_blacksmith_roman_q0351_03.htm";
                } else {
                    string2 = "head_blacksmith_roman_q0351_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                if (questState.getQuestItemsCount(4407) >= 3L) {
                    questState.giveItems(1867, 20L);
                    questState.takeItems(4407, 3L);
                    string2 = "head_blacksmith_roman_q0351_03.htm";
                } else {
                    string2 = "head_blacksmith_roman_q0351_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_3")) {
                if (questState.getQuestItemsCount(4407) >= 3L) {
                    questState.giveItems(1872, 20L);
                    questState.takeItems(4407, 3L);
                    string2 = "head_blacksmith_roman_q0351_03.htm";
                } else {
                    string2 = "head_blacksmith_roman_q0351_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_4")) {
                if (questState.getQuestItemsCount(4407) >= 2L) {
                    questState.giveItems(1870, 10L);
                    questState.takeItems(4407, 2L);
                    string2 = "head_blacksmith_roman_q0351_03.htm";
                } else {
                    string2 = "head_blacksmith_roman_q0351_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_5")) {
                if (questState.getQuestItemsCount(4407) >= 2L) {
                    questState.giveItems(1871, 10L);
                    questState.takeItems(4407, 2L);
                    string2 = "head_blacksmith_roman_q0351_03.htm";
                } else {
                    string2 = "head_blacksmith_roman_q0351_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_6")) {
                if (questState.getQuestItemsCount(4407) >= 9L) {
                    questState.giveItems(1882, 10L);
                    questState.takeItems(4407, 9L);
                    string2 = "head_blacksmith_roman_q0351_03.htm";
                } else {
                    string2 = "head_blacksmith_roman_q0351_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_7")) {
                if (questState.getQuestItemsCount(4407) >= 5L) {
                    questState.giveItems(1879, 6L);
                    questState.takeItems(4407, 5L);
                    string2 = "head_blacksmith_roman_q0351_03.htm";
                } else {
                    string2 = "head_blacksmith_roman_q0351_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_8")) {
                if (questState.getQuestItemsCount(4407) >= 3L) {
                    questState.giveItems(1881, 2L);
                    questState.takeItems(4407, 3L);
                    string2 = "head_blacksmith_roman_q0351_03.htm";
                } else {
                    string2 = "head_blacksmith_roman_q0351_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_9")) {
                if (questState.getQuestItemsCount(4407) >= 3L) {
                    questState.giveItems(1874, 1L);
                    questState.takeItems(4407, 3L);
                    string2 = "head_blacksmith_roman_q0351_03.htm";
                } else {
                    string2 = "head_blacksmith_roman_q0351_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_10")) {
                if (questState.getQuestItemsCount(4407) >= 3L) {
                    questState.giveItems(1875, 1L);
                    questState.takeItems(4407, 3L);
                    string2 = "head_blacksmith_roman_q0351_03.htm";
                } else {
                    string2 = "head_blacksmith_roman_q0351_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_11")) {
                if (questState.getQuestItemsCount(4407) >= 6L) {
                    questState.giveItems(1894, 1L);
                    questState.giveItems(57, 210L);
                    questState.takeItems(4407, 6L);
                    string2 = "head_blacksmith_roman_q0351_03.htm";
                } else {
                    string2 = "head_blacksmith_roman_q0351_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_12")) {
                if (questState.getQuestItemsCount(4407) >= 7L) {
                    questState.giveItems(1888, 1L);
                    questState.giveItems(57, 280L);
                    questState.takeItems(4407, 7L);
                    string2 = "head_blacksmith_roman_q0351_03.htm";
                } else {
                    string2 = "head_blacksmith_roman_q0351_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_13")) {
                if (questState.getQuestItemsCount(4407) >= 9L) {
                    questState.giveItems(1887, 1L);
                    questState.giveItems(57, 630L);
                    questState.takeItems(4407, 9L);
                    string2 = "head_blacksmith_roman_q0351_03.htm";
                } else {
                    string2 = "head_blacksmith_roman_q0351_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_15")) {
                if (questState.getQuestItemsCount(4407) >= 5L) {
                    questState.giveItems(5220, 1L);
                    questState.takeItems(4407, 5L);
                    string2 = "head_blacksmith_roman_q0351_03.htm";
                } else {
                    string2 = "head_blacksmith_roman_q0351_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_14")) {
                string2 = "head_blacksmith_roman_q0351_05.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("black_swan");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30916) break;
                if (questState.getPlayer().getLevel() < 32 || questState.getPlayer().getLevel() > 36) {
                    string = "captain_gosta_q0351_01.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "captain_gosta_q0351_02.htm";
                break;
            }
            case 2: {
                if (n2 == 30916) {
                    if (n < 0) break;
                    string = "captain_gosta_q0351_05.htm";
                    break;
                }
                if (n2 == 30969) {
                    if (n != 1) break;
                    string = "iason_haine_q0351_01.htm";
                    break;
                }
                if (n2 != 30897) break;
                if (questState.getQuestItemsCount(4407) >= 1L) {
                    string = "head_blacksmith_roman_q0351_01.htm";
                    break;
                }
                if (questState.getQuestItemsCount(4407) != 0L) break;
                string = "head_blacksmith_roman_q0351_02.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (questState.getQuestItemsCount(4296) > 0L) {
            if (n == 20784 || n == 21639) {
                int n2 = Rnd.get((int)100);
                if (n2 < 10) {
                    questState.giveItems(4297, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                    if (Rnd.get((int)20) == 0) {
                        questState.giveItems(4298, 1L);
                    }
                } else if (n2 < 15) {
                    questState.giveItems(4297, 2L);
                    questState.playSound("ItemSound.quest_itemget");
                    if (Rnd.get((int)20) == 0) {
                        questState.giveItems(4298, 1L);
                    }
                } else if (Rnd.get((int)100) < 4) {
                    questState.giveItems(4298, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (n == 20785 || n == 21640) {
                int n3 = Rnd.get((int)20);
                if (n3 < 10) {
                    questState.giveItems(4297, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                    if (Rnd.get((int)20) == 0) {
                        questState.giveItems(4298, 1L);
                    }
                } else if (n3 < 15) {
                    questState.giveItems(4297, 2L);
                    questState.playSound("ItemSound.quest_itemget");
                    if (Rnd.get((int)20) == 0) {
                        questState.giveItems(4298, 1L);
                    }
                } else if (Rnd.get((int)100) < 3) {
                    questState.giveItems(4298, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
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
