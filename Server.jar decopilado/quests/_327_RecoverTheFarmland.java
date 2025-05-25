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

public class _327_RecoverTheFarmland
extends Quest
implements ScriptFile {
    private static final int axC = 30597;
    private static final int axD = 30034;
    private static final int axE = 30313;
    private static final int axF = 30382;
    private static final int axG = 30314;
    private static final int axH = 1846;
    private static final int axI = 1847;
    private static final int axJ = 1848;
    private static final int axK = 1849;
    private static final int axL = 1850;
    private static final int axM = 1851;
    private static final int axN = 1852;
    private static final int axO = 1853;
    private static final int axP = 1854;
    private static final int axQ = 1855;
    private static final int axR = 5012;
    private static final int axS = 20496;
    private static final int axT = 20497;
    private static final int axU = 20498;
    private static final int axV = 20499;
    private static final int axW = 20500;
    private static final int axX = 20501;
    private static final int axY = 20495;
    private static final int axZ = 734;
    private static final int aya = 735;
    private static final int ayb = 736;
    private static final int ayc = 737;
    private static final int ayd = 1061;
    private static final int aye = 1463;
    private static final int ayf = 2510;

    public _327_RecoverTheFarmland() {
        super(0);
        this.addStartNpc(new int[]{30597, 30382});
        this.addTalkId(new int[]{30034, 30313, 30314});
        this.addQuestItem(new int[]{1846, 1847, 1848, 1849, 1850, 1851, 1852, 1853, 1854, 1855});
        this.addKillId(new int[]{20495, 20496, 20497, 20498, 20499, 20500, 20501});
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getState();
        int n2 = npcInstance.getNpcId();
        Player player = questState.getPlayer();
        switch (n) {
            case 1: {
                if (n2 == 30597) {
                    if (player.getLevel() < 25) {
                        string = "piotur_q0327_01.htm";
                        questState.exitCurrentQuest(true);
                        break;
                    }
                    string = "piotur_q0327_02.htm";
                    break;
                }
                if (n2 != 30382) break;
                if (player.getLevel() < 25) {
                    string = "guard_leikan_q0327_01.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "guard_leikan_q0327_02.htm";
                break;
            }
            case 2: {
                if (n2 == 30597) {
                    if (questState.getQuestItemsCount(5012) >= 1L) {
                        string = "piotur_q0327_03a.htm";
                        questState.takeAllItems(5012);
                        questState.setCond(3);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(5012) != 0L) break;
                    if (questState.getQuestItemsCount(1846) + questState.getQuestItemsCount(1847) < 1L) {
                        string = "piotur_q0327_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1846) + questState.getQuestItemsCount(1847) >= 10L) {
                        questState.giveItems(57, 619L + questState.getQuestItemsCount(1846) * 40L + questState.getQuestItemsCount(1847) * 50L);
                        string = "piotur_q0327_05.htm";
                        questState.takeAllItems(new int[]{1846, 1847});
                        questState.setCond(4);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    questState.giveItems(57, questState.getQuestItemsCount(1846) * 40L + questState.getQuestItemsCount(1847) * 50L);
                    string = "piotur_q0327_05.htm";
                    questState.takeAllItems(new int[]{1846, 1847});
                    questState.setCond(4);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (n2 == 30034) {
                    string = "iris_q0327_01.htm";
                    break;
                }
                if (n2 == 30313) {
                    string = "trader_acellopy_q0327_01.htm";
                    break;
                }
                if (n2 == 30314) {
                    string = "trader_nestle_q0327_01.htm";
                    break;
                }
                if (n2 != 30382) break;
                if (player.getLevel() >= 25) {
                    string = "guard_leikan_q0327_02.htm";
                    break;
                }
                if (questState.getQuestItemsCount(5012) >= 1L) {
                    string = "guard_leikan_q0327_04.htm";
                    break;
                }
                if (questState.getQuestItemsCount(5012) == 0L) {
                    string = "guard_leikan_q0327_05.htm";
                    questState.setCond(5);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (questState.getQuestItemsCount(5012) >= 1L) {
                    string = "guard_leikan_q0327_04.htm";
                    break;
                }
                if (questState.getQuestItemsCount(5012) != 0L) break;
                string = "guard_leikan_q0327_05.htm";
                questState.setCond(5);
                questState.playSound("ItemSound.quest_middle");
            }
        }
        return string;
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30597) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "piotur_q0327_03.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                string2 = "piotur_q0327_06.htm";
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "piotur_q0327_07.htm";
            }
        }
        if (n == 30382 && string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(2);
            questState.giveItems(5012, 1L);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "guard_leikan_q0327_03.htm";
        }
        if (n == 30034) {
            if (string.equalsIgnoreCase("reply_1")) {
                long l = questState.getQuestItemsCount(1848);
                if (l < 1L) {
                    string2 = "iris_q0327_02.htm";
                } else {
                    string2 = "iris_q0327_03.htm";
                    questState.takeAllItems(1848);
                    questState.addExpAndSp(l * 307L, 0L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                long l = questState.getQuestItemsCount(1849);
                if (l < 1L) {
                    string2 = "iris_q0327_02.htm";
                } else {
                    string2 = "iris_q0327_04.htm";
                    questState.takeAllItems(1849);
                    questState.addExpAndSp(l * 368L, 0L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (string.equalsIgnoreCase("reply_3")) {
                long l = questState.getQuestItemsCount(1850);
                if (l < 1L) {
                    string2 = "iris_q0327_02.htm";
                } else {
                    string2 = "iris_q0327_05.htm";
                    questState.takeAllItems(1850);
                    questState.addExpAndSp(l * 368L, 0L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (string.equalsIgnoreCase("reply_4")) {
                long l = questState.getQuestItemsCount(1851);
                if (l < 1L) {
                    string2 = "iris_q0327_02.htm";
                } else {
                    string2 = "iris_q0327_06.htm";
                    questState.takeAllItems(1851);
                    questState.addExpAndSp(l * 430L, 0L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (string.equalsIgnoreCase("reply_6")) {
                boolean bl = false;
                if (questState.getQuestItemsCount(1852) > 0L) {
                    long l = questState.getQuestItemsCount(1852);
                    if (l > 0L) {
                        questState.takeAllItems(1852);
                        questState.addExpAndSp(l * 2766L, 0L);
                        questState.playSound("ItemSound.quest_itemget");
                    }
                    bl = true;
                } else if (questState.getQuestItemsCount(1853) > 0L) {
                    long l = questState.getQuestItemsCount(1853);
                    if (l > 0L) {
                        questState.takeAllItems(1853);
                        questState.addExpAndSp(l * 3227L, 0L);
                        questState.playSound("ItemSound.quest_itemget");
                    }
                    bl = true;
                } else if (questState.getQuestItemsCount(1854) > 0L) {
                    long l = questState.getQuestItemsCount(1854);
                    if (l > 0L) {
                        questState.takeAllItems(1854);
                        questState.addExpAndSp(l * 3227L, 0L);
                        questState.playSound("ItemSound.quest_itemget");
                    }
                    bl = true;
                } else if (questState.getQuestItemsCount(1855) > 0L) {
                    long l = questState.getQuestItemsCount(1855);
                    if (l > 0L) {
                        questState.takeAllItems(1855);
                        questState.addExpAndSp(l * 3919L, 0L);
                        questState.playSound("ItemSound.quest_itemget");
                    }
                    bl = true;
                }
                string2 = bl ? "iris_q0327_07.htm" : "iris_q0327_02.htm";
            } else {
                string2 = "iris_q0327_01.htm";
            }
        } else if (n == 30313) {
            if (string.equalsIgnoreCase("reply_1")) {
                if (questState.getQuestItemsCount(1848) < 5L) {
                    string2 = "trader_acellopy_q0327_02.htm";
                } else if (Rnd.get((int)6) < 5) {
                    string2 = "trader_acellopy_q0327_03.htm";
                    questState.takeItems(1848, 5L);
                    questState.giveItems(1852, 1L);
                } else {
                    string2 = "trader_acellopy_q0327_10.htm";
                    questState.takeItems(1848, 5L);
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                if (questState.getQuestItemsCount(1849) < 5L) {
                    string2 = "trader_acellopy_q0327_04.htm";
                } else if (Rnd.get((int)7) < 6) {
                    string2 = "trader_acellopy_q0327_05.htm";
                    questState.takeItems(1849, 5L);
                    questState.giveItems(1853, 1L);
                } else {
                    string2 = "trader_acellopy_q0327_10.htm";
                    questState.takeItems(1849, 5L);
                }
            } else if (string.equalsIgnoreCase("reply_3")) {
                if (questState.getQuestItemsCount(1850) < 5L) {
                    string2 = "trader_acellopy_q0327_06.htm";
                } else if (Rnd.get((int)7) < 6) {
                    string2 = "trader_acellopy_q0327_07.htm";
                    questState.takeItems(1850, 5L);
                    questState.giveItems(1854, 1L);
                } else {
                    string2 = "trader_acellopy_q0327_10.htm";
                    questState.takeItems(1850, 5L);
                }
            } else if (string.equalsIgnoreCase("reply_4")) {
                if (questState.getQuestItemsCount(1851) < 5L) {
                    string2 = "trader_acellopy_q0327_08.htm";
                } else if (Rnd.get((int)8) < 7) {
                    string2 = "trader_acellopy_q0327_09.htm";
                    questState.takeItems(1851, 5L);
                    questState.giveItems(1855, 1L);
                } else {
                    string2 = "trader_acellopy_q0327_10.htm";
                    questState.takeItems(1851, 5L);
                }
            } else {
                string2 = "trader_acellopy_q0327_01.htm";
            }
        } else if (n == 30314) {
            int n2;
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "trader_nestle_q0327_02.htm";
            }
            if (string.equalsIgnoreCase("reply_2")) {
                if (questState.getQuestItemsCount(1852) >= 1L) {
                    questState.giveItems(1463, (long)(Rnd.get((int)41) + 70));
                    questState.takeItems(1852, 1L);
                    string2 = "trader_nestle_q0327_03.htm";
                } else {
                    string2 = "trader_nestle_q0327_07.htm";
                }
            }
            if (string.equalsIgnoreCase("reply_3")) {
                if (questState.getQuestItemsCount(1853) >= 1L) {
                    n2 = Rnd.get((int)100);
                    if (n2 < 40) {
                        questState.giveItems(1061, 1L);
                    } else if (n2 < 84) {
                        questState.giveItems(734, 1L);
                    } else {
                        questState.giveItems(735, 1L);
                    }
                    questState.takeItems(1853, 1L);
                    string2 = "trader_nestle_q0327_04.htm";
                } else {
                    string2 = "trader_nestle_q0327_07.htm";
                }
            }
            if (string.equalsIgnoreCase("reply_4")) {
                if (questState.getQuestItemsCount(1854) >= 1L) {
                    n2 = Rnd.get((int)100);
                    if (n2 < 59) {
                        questState.giveItems(736, 1L);
                    } else {
                        questState.giveItems(737, 1L);
                    }
                    questState.takeItems(1854, 1L);
                    string2 = "trader_nestle_q0327_05.htm";
                } else {
                    string2 = "trader_nestle_q0327_07.htm";
                }
            }
            if (string.equalsIgnoreCase("reply_5")) {
                if (questState.getQuestItemsCount(1855) >= 1L) {
                    questState.giveItems(2510, (long)(Rnd.get((int)41) + 50));
                    questState.takeItems(1855, 1L);
                    string2 = "trader_nestle_q0327_06.htm";
                } else {
                    string2 = "trader_nestle_q0327_07.htm";
                }
            }
            if (string.equalsIgnoreCase("reply_6")) {
                string2 = "trader_nestle_q0327_08.htm";
            }
            if (string.equalsIgnoreCase("reply_7")) {
                string2 = "trader_nestle_q0327_09.htm";
            }
        }
        return string2;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        boolean bl;
        int n = npcInstance.getNpcId();
        boolean bl2 = n == 20496 || n == 20497 || n == 20498 || n == 20499 || n == 20500;
        boolean bl3 = bl = n == 20501 || n == 20495;
        if (bl2 || bl) {
            questState.playSound("ItemSound.quest_middle");
            int n2 = Rnd.get((int)100);
            if (bl2) {
                questState.rollAndGive(1846, 1, 100.0);
            } else {
                questState.rollAndGive(1847, 1, 100.0);
            }
            if (n2 < 21) {
                int n3 = Rnd.get((int)100);
                if (n3 < 25) {
                    questState.rollAndGive(1848, 1, 100.0);
                } else if (n3 < 50) {
                    questState.rollAndGive(1849, 1, 100.0);
                } else if (n3 < 75) {
                    questState.rollAndGive(1850, 1, 100.0);
                } else {
                    questState.rollAndGive(1851, 1, 100.0);
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
