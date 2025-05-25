/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _417_PathToScavenger
extends Quest
implements ScriptFile {
    private static final int bbX = 30524;
    private static final int bbY = 30519;
    private static final int bbZ = 30556;
    private static final int bca = 30316;
    private static final int bcb = 30557;
    private static final int bcc = 31958;
    private static final int bcd = 30538;
    private static final int bce = 30517;
    private static final int bcf = 30525;
    private static final int bcg = 20403;
    private static final int bch = 27058;
    private static final int bci = 20508;
    private static final int bcj = 20777;
    private static final int bck = 1642;
    private static final int bcl = 1643;
    private static final int bcm = 1644;
    private static final int bcn = 1645;
    private static final int bco = 1646;
    private static final int bcp = 1647;
    private static final int bcq = 1648;
    private static final int bcr = 1649;
    private static final int bcs = 1650;
    private static final int bct = 1651;
    private static final int bcu = 1652;
    private static final int bcv = 1653;
    private static final int bcw = 1654;
    private static final int bcx = 1655;
    private static final int bcy = 1656;
    private static final int bcz = 1657;
    private static final int bcA = 8543;

    public _417_PathToScavenger() {
        super(0);
        this.addStartNpc(30524);
        this.addTalkId(new int[]{30519, 30556, 30316, 30557, 31958, 30538, 30517, 30525});
        this.addKillId(new int[]{20403, 20508, 20777, 27058});
        this.addQuestItem(new int[]{1643, 1644, 1645, 1646, 1647, 1648, 1649, 1650, 1651, 1652, 1653, 1654, 1655, 1656, 1657});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("path_to_scavenger_ex");
        int n2 = questState.getInt("path_to_scavenger");
        int n3 = npcInstance.getNpcId();
        int n4 = questState.getPlayer().getVarInt("prof1");
        int n5 = questState.getPlayer().getClassId().getId();
        int n6 = 53;
        int n7 = 54;
        if (n3 == 30524) {
            if (string.equalsIgnoreCase("quest_accept")) {
                if (questState.getPlayer().getLevel() < 18) {
                    string2 = "collector_pipi_q0417_02.htm";
                    questState.exitCurrentQuest(true);
                } else if (n5 != n6) {
                    if (n5 == n7) {
                        string2 = "collector_pipi_q0417_02a.htm";
                        questState.exitCurrentQuest(true);
                    } else {
                        string2 = "collector_pipi_q0417_08.htm";
                        questState.exitCurrentQuest(true);
                    }
                } else if (questState.getQuestItemsCount(1642) == 1L && n5 == n6) {
                    string2 = "collector_pipi_q0417_04.htm";
                } else {
                    questState.setCond(1);
                    questState.set("path_to_scavenger_ex", String.valueOf(0), true);
                    questState.giveItems(1643, 1L);
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                    string2 = "collector_pipi_q0417_05.htm";
                }
            }
        } else if (n3 == 30519) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(1643) > 0L) {
                switch (Rnd.get((int)3)) {
                    case 0: {
                        string2 = "trader_mion_q0417_02.htm";
                        questState.takeItems(1643, 1L);
                        questState.giveItems(1649, 1L);
                        break;
                    }
                    case 1: {
                        string2 = "trader_mion_q0417_03.htm";
                        questState.takeItems(1643, 1L);
                        questState.giveItems(1648, 1L);
                        break;
                    }
                    case 2: {
                        string2 = "trader_mion_q0417_04.htm";
                        questState.takeItems(1643, 1L);
                        questState.giveItems(1647, 1L);
                    }
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "trader_mion_q0417_06.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                questState.set("path_to_scavenger_ex", String.valueOf(n + 1), true);
                string2 = "trader_mion_q0417_07.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                switch (Rnd.get((int)2)) {
                    case 0: {
                        string2 = "trader_mion_q0417_06.htm";
                        break;
                    }
                    case 1: {
                        string2 = "trader_mion_q0417_11.htm";
                    }
                }
            } else if (string.equalsIgnoreCase("reply_5")) {
                if (n % 10 < 2) {
                    string2 = "trader_mion_q0417_07.htm";
                    questState.set("path_to_scavenger_ex", String.valueOf(n + 1), true);
                } else if (n % 10 == 2 && n2 == 0) {
                    string2 = "trader_mion_q0417_07.htm";
                } else if (n % 10 == 2 && n2 == 1) {
                    string2 = "trader_mion_q0417_09.htm";
                    questState.set("path_to_scavenger_ex", String.valueOf(n + 1), true);
                } else if (n % 10 >= 3 && n2 == 1) {
                    questState.setCond(4);
                    questState.giveItems(1646, 1L);
                    questState.takeItems(1648, -1L);
                    questState.takeItems(1649, -1L);
                    questState.takeItems(1647, -1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "trader_mion_q0417_10.htm";
                }
            } else if (string.equalsIgnoreCase("reply_6")) {
                switch (Rnd.get((int)3)) {
                    case 0: {
                        string2 = "trader_mion_q0417_02.htm";
                        questState.takeItems(1652, -1L);
                        questState.takeItems(1651, -1L);
                        questState.takeItems(1650, -1L);
                        questState.giveItems(1649, 1L);
                        break;
                    }
                    case 1: {
                        string2 = "trader_mion_q0417_03.htm";
                        questState.takeItems(1652, -1L);
                        questState.takeItems(1651, -1L);
                        questState.takeItems(1650, -1L);
                        questState.giveItems(1648, 1L);
                        break;
                    }
                    case 2: {
                        string2 = "trader_mion_q0417_04.htm";
                        questState.takeItems(1652, -1L);
                        questState.takeItems(1651, -1L);
                        questState.takeItems(1650, -1L);
                        questState.giveItems(1647, 1L);
                    }
                }
            }
        } else if (n3 == 30556) {
            if (string.equalsIgnoreCase("reply_10") && questState.getQuestItemsCount(1654) == 1L && questState.getQuestItemsCount(1656) >= 20L) {
                questState.setCond(9);
                questState.giveItems(1657, 1L);
                questState.takeItems(1654, -1L);
                questState.takeItems(1656, -1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "master_toma_q0417_05b.htm";
            } else if (string.equalsIgnoreCase("reply_11") && questState.getQuestItemsCount(1654) == 1L && questState.getQuestItemsCount(1656) >= 20L) {
                questState.setCond(12);
                questState.set("path_to_scavenger", String.valueOf(2), true);
                questState.giveItems(8543, 1L);
                questState.takeItems(1654, -1L);
                questState.takeItems(1656, -1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "master_toma_q0417_06b.htm";
            }
        } else if (n3 == 30316) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(1657) > 0L) {
                questState.setCond(10);
                questState.takeItems(1657, 1L);
                questState.giveItems(1644, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "raut_q0417_02.htm";
            } else if (string.equalsIgnoreCase("reply_2") && questState.getQuestItemsCount(1657) > 0L) {
                questState.setCond(10);
                questState.takeItems(1657, 1L);
                questState.giveItems(1644, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "raut_q0417_03.htm";
            }
        } else if (n3 == 30557) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "torai_q0417_02.htm";
            } else if (string.equalsIgnoreCase("reply_2") && questState.getQuestItemsCount(1644) > 0L) {
                questState.setCond(11);
                questState.takeItems(1644, 1L);
                questState.giveItems(1645, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "torai_q0417_03.htm";
            }
        } else if (n3 == 31958 && string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(8543) > 0L && n2 == 2) {
            string2 = "warehouse_chief_yaseni_q0417_02.htm";
            questState.giveItems(1642, 1L);
            if (n4 == 0) {
                questState.getPlayer().setVar("prof1", "1", -1L);
                questState.addExpAndSp(3200L, 7080L);
                this.giveExtraReward(questState.getPlayer());
            }
            questState.takeItems(8543, -1L);
            questState.unset("path_to_scavenger");
            questState.unset("path_to_scavenger_ex");
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("path_to_scavenger");
        int n2 = questState.getInt("path_to_scavenger_ex");
        int n3 = questState.getPlayer().getVarInt("prof1");
        int n4 = npcInstance.getNpcId();
        int n5 = questState.getState();
        switch (n5) {
            case 1: {
                if (n4 != 30524) break;
                string = "collector_pipi_q0417_01.htm";
                break;
            }
            case 2: {
                if (n4 == 30524) {
                    if (questState.getQuestItemsCount(1643) == 1L) {
                        string = "collector_pipi_q0417_06.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1643) != 0L) break;
                    string = "collector_pipi_q0417_07.htm";
                    break;
                }
                if (n4 == 30519) {
                    if (questState.getQuestItemsCount(1643) == 1L) {
                        questState.setCond(2);
                        questState.playSound("ItemSound.quest_middle");
                        string = "trader_mion_q0417_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1648) + questState.getQuestItemsCount(1647) + questState.getQuestItemsCount(1649) == 1L && n2 % 10 == 0) {
                        string = "trader_mion_q0417_05.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1648) + questState.getQuestItemsCount(1647) + questState.getQuestItemsCount(1649) == 1L && n2 % 10 > 0) {
                        string = "trader_mion_q0417_08.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1651) + questState.getQuestItemsCount(1650) + questState.getQuestItemsCount(1652) == 1L && n2 < 50) {
                        string = "trader_mion_q0417_12.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1651) + questState.getQuestItemsCount(1650) + questState.getQuestItemsCount(1652) == 1L && n2 >= 50) {
                        questState.setCond(4);
                        questState.giveItems(1646, 1L);
                        questState.takeItems(1651, -1L);
                        questState.takeItems(1652, -1L);
                        questState.takeItems(1650, -1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "trader_mion_q0417_15.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1646) > 0L) {
                        string = "trader_mion_q0417_13.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1653) <= 0L && questState.getQuestItemsCount(1654) <= 0L && questState.getQuestItemsCount(1657) <= 0L && questState.getQuestItemsCount(1644) <= 0L && questState.getQuestItemsCount(1645) <= 0L) break;
                    string = "trader_mion_q0417_14.htm";
                    break;
                }
                if (n4 == 30556) {
                    if (questState.getQuestItemsCount(1646) == 1L) {
                        questState.setCond(5);
                        questState.takeItems(1646, 1L);
                        questState.giveItems(1653, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "master_toma_q0417_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1653) == 1L && questState.getQuestItemsCount(1655) < 5L) {
                        string = "master_toma_q0417_02.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1653) == 1L && questState.getQuestItemsCount(1655) >= 5L) {
                        questState.setCond(7);
                        questState.takeItems(1655, -1L);
                        questState.takeItems(1653, 1L);
                        questState.giveItems(1654, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "master_toma_q0417_03.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1654) == 1L && questState.getQuestItemsCount(1656) < 20L) {
                        string = "master_toma_q0417_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1654) == 1L && questState.getQuestItemsCount(1656) >= 20L) {
                        string = "master_toma_q0417_05a.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1657) > 0L && questState.getQuestItemsCount(8543) == 0L) {
                        string = "master_toma_q0417_06a.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(8543) > 0L && questState.getQuestItemsCount(1657) == 0L && n == 2) {
                        string = "master_toma_q0417_06c.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1644) <= 0L && questState.getQuestItemsCount(1645) <= 0L) break;
                    string = "master_toma_q0417_07.htm";
                    break;
                }
                if (n4 == 30316) {
                    if (questState.getQuestItemsCount(1657) == 1L) {
                        string = "raut_q0417_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1644) == 1L) {
                        string = "raut_q0417_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1645) != 1L) break;
                    string = "raut_q0417_05.htm";
                    questState.takeItems(1645, 1L);
                    questState.giveItems(1642, 1L);
                    if (n3 == 0) {
                        questState.getPlayer().setVar("prof1", "1", -1L);
                        questState.addExpAndSp(3200L, 7080L);
                        this.giveExtraReward(questState.getPlayer());
                    }
                    questState.unset("path_to_scavenger");
                    questState.unset("path_to_scavenger_ex");
                    questState.playSound("ItemSound.quest_finish");
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (n4 == 30557) {
                    if (questState.getQuestItemsCount(1644) != 1L) break;
                    string = "torai_q0417_01.htm";
                    break;
                }
                if (n4 == 31958) {
                    if (questState.getQuestItemsCount(8543) <= 0L || questState.getQuestItemsCount(1657) != 0L || n != 2) break;
                    string = "warehouse_chief_yaseni_q0417_01.htm";
                    break;
                }
                if (n4 == 30538) {
                    if (questState.getQuestItemsCount(1649) == 1L && n2 < 20) {
                        questState.set("path_to_scavenger_ex", String.valueOf(n2 + 10), true);
                        questState.takeItems(1649, 1L);
                        questState.giveItems(1652, 1L);
                        string = "zimenf_priest_of_earth_q0417_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1649) == 1L && n2 >= 20) {
                        questState.setCond(3);
                        questState.set("path_to_scavenger", String.valueOf(1), true);
                        questState.set("path_to_scavenger_ex", String.valueOf(n2 + 10), true);
                        questState.takeItems(1649, 1L);
                        questState.giveItems(1652, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "zimenf_priest_of_earth_q0417_02.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1652) != 1L) break;
                    string = "zimenf_priest_of_earth_q0417_03.htm";
                    break;
                }
                if (n4 == 30517) {
                    if (questState.getQuestItemsCount(1648) == 1L && n2 < 20) {
                        questState.set("path_to_scavenger_ex", String.valueOf(n2 + 10), true);
                        questState.takeItems(1648, 1L);
                        questState.giveItems(1651, 1L);
                        string = "trader_chali_q0417_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1648) == 1L && n2 >= 20) {
                        questState.setCond(3);
                        questState.set("path_to_scavenger", String.valueOf(1), true);
                        questState.set("path_to_scavenger_ex", String.valueOf(n2 + 10), true);
                        questState.takeItems(1648, 1L);
                        questState.giveItems(1651, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "trader_chali_q0417_02.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1651) != 1L) break;
                    string = "trader_chali_q0417_03.htm";
                    break;
                }
                if (n4 != 30525) break;
                if (questState.getQuestItemsCount(1647) == 1L && n2 < 20) {
                    questState.set("path_to_scavenger_ex", String.valueOf(n2 + 10), true);
                    questState.takeItems(1647, 1L);
                    questState.giveItems(1650, 1L);
                    string = "head_blacksmith_bronk_q0417_01.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1647) == 1L && n2 >= 20) {
                    questState.setCond(3);
                    questState.set("path_to_scavenger", String.valueOf(1), true);
                    questState.set("path_to_scavenger_ex", String.valueOf(n2 + 10), true);
                    questState.takeItems(1647, 1L);
                    questState.giveItems(1650, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string = "head_blacksmith_bronk_q0417_02.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1650) != 1L) break;
                string = "head_blacksmith_bronk_q0417_03.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        MonsterInstance monsterInstance = (MonsterInstance)npcInstance;
        if (n == 20777) {
            if (questState.getQuestItemsCount(1653) == 1L && questState.getQuestItemsCount(1655) < 5L && Rnd.get((int)100) <= 20) {
                questState.addSpawn(27058);
            }
        } else if (n == 27058) {
            if (questState.getQuestItemsCount(1653) == 1L && questState.getQuestItemsCount(1655) < 5L && monsterInstance.isSpoiled()) {
                questState.rollAndGive(1655, 1, 100.0);
                if (questState.getQuestItemsCount(1655) >= 5L) {
                    questState.setCond(6);
                    questState.giveItems(1655, 1L);
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if ((n == 20403 || n == 20508) && questState.getQuestItemsCount(1654) == 1L && questState.getQuestItemsCount(1656) < 20L && monsterInstance.isSpoiled() && Rnd.get((int)2) < 2) {
            questState.rollAndGive(1656, 1, 100.0);
            if (questState.getQuestItemsCount(1656) >= 20L) {
                questState.setCond(8);
                questState.giveItems(1656, 1L);
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
