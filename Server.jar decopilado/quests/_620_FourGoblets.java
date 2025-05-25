/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Party
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.ChatType
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.Say2
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import java.util.Calendar;
import java.util.HashMap;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.Say2;
import l2.gameserver.scripts.ScriptFile;

public class _620_FourGoblets
extends Quest
implements ScriptFile {
    private static final int bvl = 31453;
    private static final int bvm = 31919;
    private static final int bvn = 31920;
    private static final int bvo = 31452;
    private static final int bvp = 31454;
    private static final int bvq = 31921;
    private static final int bvr = 31922;
    private static final int bvs = 31923;
    private static final int bvt = 31924;
    private static final int bvu = 55;
    private static final int bvv = 25339;
    private static final int bvw = 25342;
    private static final int bvx = 25346;
    private static final int bvy = 25349;
    private static final HashMap<Integer, Integer> c = new HashMap(61);
    private static final HashMap<Integer, Integer> d = new HashMap(10);
    private static final HashMap<Integer, Integer> e = new HashMap(8);

    public _620_FourGoblets() {
        super(0);
        this.addStartNpc(31453);
        this.addStartNpc(31452);
        this.addTalkId(new int[]{31453, 31919, 31452, 31454, 31920});
        this.addTalkId(new int[]{31921, 31922, 31923, 31924});
        this.addKillId(new int[]{25339, 25342, 25346, 25349});
        this.addKillId(c.keySet());
        this.addKillId(d.keySet());
        this.addKillId(e.keySet());
        this.addQuestItem(new int[]{7255, 7256, 7257, 7258, 7259, 7261});
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("four_goblets_ex");
        String string = "noquest";
        Player player = questState.getPlayer();
        int n2 = npcInstance.getNpcId();
        if (n2 == 31453) {
            if (questState.isCreated()) {
                if (player.getLevel() >= 74) {
                    return "printessa_spirit_q0620_01.htm";
                }
                return "printessa_spirit_q0620_12.htm";
            }
            if (questState.isStarted()) {
                if (questState.getQuestItemsCount(7262) == 0L && (questState.getQuestItemsCount(7256) == 0L || questState.getQuestItemsCount(7257) == 0L || questState.getQuestItemsCount(7258) == 0L || questState.getQuestItemsCount(7259) == 0L)) {
                    return "printessa_spirit_q0620_14.htm";
                }
                if (questState.getQuestItemsCount(7262) == 0L && questState.getQuestItemsCount(7256) > 0L && questState.getQuestItemsCount(7257) > 0L && questState.getQuestItemsCount(7258) > 0L && questState.getQuestItemsCount(7259) > 0L) {
                    return "printessa_spirit_q0620_15.htm";
                }
                if (questState.getQuestItemsCount(7262) > 0L) {
                    return "printessa_spirit_q0620_17.htm";
                }
            }
        } else if (n2 == 31919) {
            if (questState.isStarted()) {
                return "el_lord_chamber_ghost_q0620_01.htm";
            }
        } else if (n2 == 31452) {
            if (questState.isCreated()) {
                return "wigoth_ghost_a_q0620_01.htm";
            }
            if (questState.isStarted()) {
                if (questState.getQuestItemsCount(7262) == 0L && (questState.getQuestItemsCount(7256) == 0L || questState.getQuestItemsCount(7257) == 0L || questState.getQuestItemsCount(7258) == 0L || questState.getQuestItemsCount(7259) == 0L) && questState.getQuestItemsCount(7256) + questState.getQuestItemsCount(7257) + questState.getQuestItemsCount(7258) + questState.getQuestItemsCount(7259) < 3L) {
                    return "wigoth_ghost_a_q0620_01.htm";
                }
                if (questState.getQuestItemsCount(7262) == 0L && (questState.getQuestItemsCount(7256) == 0L || questState.getQuestItemsCount(7257) == 0L || questState.getQuestItemsCount(7258) == 0L || questState.getQuestItemsCount(7259) == 0L) && questState.getQuestItemsCount(7256) + questState.getQuestItemsCount(7257) + questState.getQuestItemsCount(7258) + questState.getQuestItemsCount(7259) >= 3L) {
                    return "wigoth_ghost_a_q0620_02.htm";
                }
                if (questState.getQuestItemsCount(7262) == 0L && questState.getQuestItemsCount(7256) >= 1L && questState.getQuestItemsCount(7257) >= 1L && questState.getQuestItemsCount(7258) >= 1L && questState.getQuestItemsCount(7259) >= 1L) {
                    return "wigoth_ghost_a_q0620_04.htm";
                }
                if (questState.getQuestItemsCount(7262) >= 1L) {
                    return "wigoth_ghost_a_q0620_05.htm";
                }
            }
        } else if (n2 == 31454) {
            if (questState.isStarted()) {
                if (n == 2 && questState.getQuestItemsCount(7256) >= 1L && questState.getQuestItemsCount(7257) >= 1L && questState.getQuestItemsCount(7258) >= 1L && questState.getQuestItemsCount(7259) >= 1L) {
                    if (questState.getQuestItemsCount(7255) == 0L && questState.getQuestItemsCount(7254) < 1000L) {
                        questState.set("four_goblets_ex", String.valueOf(3), true);
                        return "wigoth_ghost_b_q0620_01.htm";
                    }
                    if (questState.getQuestItemsCount(7255) >= 1L && questState.getQuestItemsCount(7254) < 1000L) {
                        questState.set("four_goblets_ex", String.valueOf(3), true);
                        return "wigoth_ghost_b_q0620_02.htm";
                    }
                    if (questState.getQuestItemsCount(7255) == 0L && questState.getQuestItemsCount(7254) >= 1000L) {
                        questState.set("four_goblets_ex", String.valueOf(3), true);
                        return "wigoth_ghost_b_q0620_03.htm";
                    }
                    if (questState.getQuestItemsCount(7255) >= 1L && questState.getQuestItemsCount(7254) >= 1000L) {
                        questState.set("four_goblets_ex", String.valueOf(3), true);
                        return "wigoth_ghost_b_q0620_04.htm";
                    }
                }
                if (n == 2 && (questState.getQuestItemsCount(7256) == 0L || questState.getQuestItemsCount(7257) == 0L || questState.getQuestItemsCount(7258) == 0L || questState.getQuestItemsCount(7259) == 0L)) {
                    if (questState.getQuestItemsCount(7255) == 0L && questState.getQuestItemsCount(7254) < 1000L) {
                        questState.set("four_goblets_ex", String.valueOf(3), true);
                        return "wigoth_ghost_b_q0620_05.htm";
                    }
                    if (questState.getQuestItemsCount(7255) >= 1L && questState.getQuestItemsCount(7254) < 1000L) {
                        questState.set("four_goblets_ex", String.valueOf(3), true);
                        return "wigoth_ghost_b_q0620_06.htm";
                    }
                    if (questState.getQuestItemsCount(7255) == 0L && questState.getQuestItemsCount(7254) >= 1000L) {
                        questState.set("four_goblets_ex", String.valueOf(3), true);
                        return "wigoth_ghost_b_q0620_07.htm";
                    }
                    if (questState.getQuestItemsCount(7255) >= 1L && questState.getQuestItemsCount(7254) >= 1000L) {
                        questState.set("four_goblets_ex", String.valueOf(3), true);
                        return "wigoth_ghost_b_q0620_08.htm";
                    }
                }
                if (n == 3) {
                    if (questState.getQuestItemsCount(7255) == 0L && questState.getQuestItemsCount(7254) < 1000L) {
                        return "wigoth_ghost_b_q0620_09.htm";
                    }
                    if (questState.getQuestItemsCount(7255) >= 1L && questState.getQuestItemsCount(7254) < 1000L) {
                        return "wigoth_ghost_b_q0620_10.htm";
                    }
                    if (questState.getQuestItemsCount(7255) == 0L && questState.getQuestItemsCount(7254) >= 1000L) {
                        return "wigoth_ghost_b_q0620_11.htm";
                    }
                    if (questState.getQuestItemsCount(7255) >= 1L && questState.getQuestItemsCount(7254) >= 1000L) {
                        return "wigoth_ghost_b_q0620_12.htm";
                    }
                }
            }
        } else if (n2 == 31921) {
            if (questState.isStarted()) {
                return "conquerors_keeper_q0620_01.htm";
            }
        } else if (n2 == 31922) {
            if (questState.isStarted()) {
                return "lords_keeper_q0620_01.htm";
            }
        } else if (n2 == 31923) {
            if (questState.isStarted()) {
                return "savants_keeper_q0620_01.htm";
            }
        } else if (n2 == 31924 && questState.isStarted()) {
            return "magistrates_keeper_q0620_01.htm";
        }
        return string;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        QuestState questState2;
        Player player = questState.getPlayer();
        String string2 = string;
        int n = questState.getInt("four_goblets_ex");
        int n2 = npcInstance.getNpcId();
        if (n2 == 31453) {
            if (string.equalsIgnoreCase("reply_620")) {
                questState.setCond(1);
                questState.setState(2);
                questState.set("four_goblets", String.valueOf(0), true);
                questState.playSound("ItemSound.quest_accept");
                string2 = "printessa_spirit_q0620_13.htm";
                if (questState.getQuestItemsCount(7262) < 1L) return string2;
                questState.setCond(2);
                return "printessa_spirit_q0620_13.htm";
            }
            if (string.equalsIgnoreCase("reply_5")) {
                return "printessa_spirit_q0620_02.htm";
            }
            if (string.equalsIgnoreCase("reply_6")) {
                return "printessa_spirit_q0620_03.htm";
            }
            if (string.equalsIgnoreCase("reply_7")) {
                return "printessa_spirit_q0620_04.htm";
            }
            if (string.equalsIgnoreCase("reply_8")) {
                return "printessa_spirit_q0620_05.htm";
            }
            if (string.equalsIgnoreCase("reply_9")) {
                return "printessa_spirit_q0620_06.htm";
            }
            if (string.equalsIgnoreCase("reply_10")) {
                return "printessa_spirit_q0620_07.htm";
            }
            if (string.equalsIgnoreCase("reply_11")) {
                return "printessa_spirit_q0620_08.htm";
            }
            if (string.equalsIgnoreCase("reply_12")) {
                return "printessa_spirit_q0620_09.htm";
            }
            if (string.equalsIgnoreCase("reply_13")) {
                return "printessa_spirit_q0620_10.htm";
            }
            if (string.equalsIgnoreCase("reply_14")) {
                return "printessa_spirit_q0620_11.htm";
            }
            if (string.equalsIgnoreCase("reply_4")) {
                if (!questState.isStarted()) return string2;
                if (questState.getQuestItemsCount(7262) != 0L) return string2;
                if (questState.getQuestItemsCount(7256) < 1L) return string2;
                if (questState.getQuestItemsCount(7257) < 1L) return string2;
                if (questState.getQuestItemsCount(7258) < 1L) return string2;
                if (questState.getQuestItemsCount(7259) < 1L) return string2;
                questState.giveItems(7262, 1L);
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
                questState.takeItems(7256, 1L);
                questState.takeItems(7257, 1L);
                questState.takeItems(7258, 1L);
                questState.takeItems(7259, 1L);
                return "printessa_spirit_q0620_16.htm";
            }
            if (string.equalsIgnoreCase("reply_1")) {
                questState.takeItems(7260, -1L);
                questState.takeItems(7261, -1L);
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
                return "printessa_spirit_q0620_18.htm";
            }
            if (string.equalsIgnoreCase("reply_2")) {
                return "printessa_spirit_q0620_19.htm";
            }
            if (!string.equalsIgnoreCase("reply_3")) return string2;
            return "printessa_spirit_q0620_20.htm";
        }
        if (n2 == 31919) {
            if (string.equalsIgnoreCase("reply_1")) {
                if (questState.isStarted() && questState.getQuestItemsCount(7255) >= 1L) {
                    int n3 = 0;
                    while ((long)n3 < questState.getQuestItemsCount(7255)) {
                        if (Rnd.get((int)100) < 50) {
                            int n4 = Rnd.get((int)5);
                            if (n4 == 0) {
                                questState.giveItems(57, 10000L);
                            } else if (n4 == 1) {
                                if (Rnd.get((int)1000) < 848) {
                                    var10_20 = Rnd.get((int)1000);
                                    if (var10_20 < 43) {
                                        questState.giveItems(1884, 42L);
                                    } else if (var10_20 < 66) {
                                        questState.giveItems(1895, 36L);
                                    } else if (var10_20 < 184) {
                                        questState.giveItems(1876, 4L);
                                    } else if (var10_20 < 250) {
                                        questState.giveItems(1881, 6L);
                                    } else if (var10_20 < 287) {
                                        questState.giveItems(5549, 8L);
                                    } else if (var10_20 < 484) {
                                        questState.giveItems(1874, 1L);
                                    } else if (var10_20 < 681) {
                                        questState.giveItems(1889, 1L);
                                    } else if (var10_20 < 799) {
                                        questState.giveItems(1877, 1L);
                                    } else if (var10_20 < 902) {
                                        questState.giveItems(1894, 1L);
                                    } else {
                                        questState.giveItems(4043, 1L);
                                    }
                                }
                                if (Rnd.get((int)1000) < 323) {
                                    var10_20 = Rnd.get((int)1000);
                                    if (var10_20 < 335) {
                                        questState.giveItems(1888, 1L);
                                    } else if (var10_20 < 556) {
                                        questState.giveItems(4040, 1L);
                                    } else if (var10_20 < 725) {
                                        questState.giveItems(1890, 1L);
                                    } else if (var10_20 < 872) {
                                        questState.giveItems(5550, 1L);
                                    } else if (var10_20 < 962) {
                                        questState.giveItems(1893, 1L);
                                    } else if (var10_20 < 986) {
                                        questState.giveItems(4046, 1L);
                                    } else {
                                        questState.giveItems(4048, 1L);
                                    }
                                }
                            } else if (n4 == 2) {
                                if (Rnd.get((int)1000) < 847) {
                                    var10_20 = Rnd.get((int)1000);
                                    if (var10_20 < 148) {
                                        questState.giveItems(1878, 8L);
                                    } else if (var10_20 < 175) {
                                        questState.giveItems(1882, 24L);
                                    } else if (var10_20 < 273) {
                                        questState.giveItems(1879, 4L);
                                    } else if (var10_20 < 322) {
                                        questState.giveItems(1880, 6L);
                                    } else if (var10_20 < 357) {
                                        questState.giveItems(1885, 6L);
                                    } else if (var10_20 < 554) {
                                        questState.giveItems(1875, 1L);
                                    } else if (var10_20 < 685) {
                                        questState.giveItems(1883, 1L);
                                    } else if (var10_20 < 803) {
                                        questState.giveItems(5220, 1L);
                                    } else if (var10_20 < 901) {
                                        questState.giveItems(4039, 1L);
                                    } else {
                                        questState.giveItems(4044, 1L);
                                    }
                                }
                                if (Rnd.get((int)1000) < 251) {
                                    var10_20 = Rnd.get((int)1000);
                                    if (var10_20 < 350) {
                                        questState.giveItems(1887, 1L);
                                    } else if (var10_20 < 587) {
                                        questState.giveItems(4042, 1L);
                                    } else if (var10_20 < 798) {
                                        questState.giveItems(1886, 1L);
                                    } else if (var10_20 < 922) {
                                        questState.giveItems(4041, 1L);
                                    } else if (var10_20 < 966) {
                                        questState.giveItems(1892, 1L);
                                    } else if (var10_20 < 996) {
                                        questState.giveItems(1891, 1L);
                                    } else {
                                        questState.giveItems(4047, 1L);
                                    }
                                }
                            } else if (n4 == 3) {
                                if (Rnd.get((int)1000) < 31) {
                                    var10_20 = Rnd.get((int)1000);
                                    if (var10_20 < 223) {
                                        questState.giveItems(730, 1L);
                                    } else if (var10_20 < 893) {
                                        questState.giveItems(948, 1L);
                                    } else {
                                        questState.giveItems(960, 1L);
                                    }
                                }
                                if (Rnd.get((int)1000) < 5) {
                                    var10_20 = Rnd.get((int)1000);
                                    if (var10_20 < 202) {
                                        questState.giveItems(729, 1L);
                                    } else if (var10_20 < 928) {
                                        questState.giveItems(947, 1L);
                                    } else {
                                        questState.giveItems(959, 1L);
                                    }
                                }
                            } else if (n4 == 4) {
                                if (Rnd.get((int)1000) < 329) {
                                    var10_20 = Rnd.get((int)1000);
                                    if (var10_20 < 88) {
                                        questState.giveItems(6698, 1L);
                                    } else if (var10_20 < 185) {
                                        questState.giveItems(6699, 1L);
                                    } else if (var10_20 < 238) {
                                        questState.giveItems(6700, 1L);
                                    } else if (var10_20 < 262) {
                                        questState.giveItems(6701, 1L);
                                    } else if (var10_20 < 292) {
                                        questState.giveItems(6702, 1L);
                                    } else if (var10_20 < 356) {
                                        questState.giveItems(6703, 1L);
                                    } else if (var10_20 < 420) {
                                        questState.giveItems(6704, 1L);
                                    } else if (var10_20 < 482) {
                                        questState.giveItems(6705, 1L);
                                    } else if (var10_20 < 554) {
                                        questState.giveItems(6706, 1L);
                                    } else if (var10_20 < 576) {
                                        questState.giveItems(6707, 1L);
                                    } else if (var10_20 < 640) {
                                        questState.giveItems(6708, 1L);
                                    } else if (var10_20 < 704) {
                                        questState.giveItems(6709, 1L);
                                    } else if (var10_20 < 777) {
                                        questState.giveItems(6710, 1L);
                                    } else if (var10_20 < 799) {
                                        questState.giveItems(6711, 1L);
                                    } else if (var10_20 < 863) {
                                        questState.giveItems(6712, 1L);
                                    } else if (var10_20 < 927) {
                                        questState.giveItems(6713, 1L);
                                    } else {
                                        questState.giveItems(6714, 1L);
                                    }
                                }
                                if (Rnd.get((int)1000) < 54) {
                                    var10_20 = Rnd.get((int)1000);
                                    if (var10_20 < 100) {
                                        questState.giveItems(6688, 1L);
                                    } else if (var10_20 < 198) {
                                        questState.giveItems(6689, 1L);
                                    } else if (var10_20 < 298) {
                                        questState.giveItems(6690, 1L);
                                    } else if (var10_20 < 398) {
                                        questState.giveItems(6691, 1L);
                                    } else if (var10_20 < 499) {
                                        questState.giveItems(7579, 1L);
                                    } else if (var10_20 < 601) {
                                        questState.giveItems(6693, 1L);
                                    } else if (var10_20 < 703) {
                                        questState.giveItems(6694, 1L);
                                    } else if (var10_20 < 801) {
                                        questState.giveItems(6695, 1L);
                                    } else if (var10_20 < 902) {
                                        questState.giveItems(6696, 1L);
                                    } else {
                                        questState.giveItems(6697, 1L);
                                    }
                                }
                            }
                        }
                        questState.takeItems(7255, 1L);
                        if (questState.getQuestItemsCount(7255) == 0L) {
                            string2 = "el_lord_chamber_ghost_q0620_03.htm";
                        }
                        --n3;
                        ++n3;
                    }
                    return string2;
                }
                if (!questState.isStarted()) return string2;
                if (questState.getQuestItemsCount(7255) != 0L) return string2;
                return "el_lord_chamber_ghost_q0620_06.htm";
            }
            if (string.equalsIgnoreCase("reply_101")) {
                if (!questState.isStarted()) return "el_lord_chamber_ghost002.htm";
                if (questState.getQuestItemsCount(7261) < 1L) {
                    return "el_lord_chamber_ghost002.htm";
                }
                questState.takeItems(7261, 1L);
                player.teleToLocation(178127, -84435, -7216);
                return "";
            }
            if (!string.equalsIgnoreCase("reply_102")) return string2;
            if (!questState.isStarted()) return "el_lord_chamber_ghost002.htm";
            if (questState.getQuestItemsCount(7261) < 1L) {
                return "el_lord_chamber_ghost002.htm";
            }
            questState.takeItems(7261, 1L);
            player.teleToLocation(186699, -75915, -2824);
            return "";
        }
        if (n2 == 31920) {
            if (string.equalsIgnoreCase("reply_101")) {
                if (!questState.isStarted()) return "el_lord_chamber_ghost002.htm";
                if (questState.getQuestItemsCount(7261) < 1L) {
                    return "el_lord_chamber_ghost002.htm";
                }
                questState.takeItems(7261, 1L);
                player.teleToLocation(178127, -84435, -7216);
                return "";
            }
            if (!string.equalsIgnoreCase("reply_102")) return string2;
            if (!questState.isStarted()) return "el_lord_chamber_ghost002.htm";
            if (questState.getQuestItemsCount(7261) < 1L) {
                return "el_lord_chamber_ghost002.htm";
            }
            questState.takeItems(7261, 1L);
            player.teleToLocation(186699, -75915, -2824);
            return "";
        }
        if (n2 == 31452) {
            if (string.equalsIgnoreCase("reply_1")) {
                return "wigoth_ghost_a_q0620_03.htm";
            }
            if (!string.equalsIgnoreCase("reply_2")) return string2;
            questState.takeItems(7260, -1L);
            player.teleToLocation(169584, -91008, -2912);
            return "";
        }
        if (n2 == 31454) {
            if (string.equalsIgnoreCase("reply_1")) {
                if (!questState.isStarted()) return string2;
                if (questState.getQuestItemsCount(7255) < 1L) return string2;
                int n5 = 0;
                while ((long)n5 < questState.getQuestItemsCount(7255)) {
                    if (Rnd.get((int)100) < 50) {
                        int n6 = Rnd.get((int)5);
                        boolean bl = false;
                        if (n6 == 0) {
                            bl = true;
                            questState.giveItems(57, 10000L);
                        } else if (n6 == 1) {
                            if (Rnd.get((int)1000) < 848) {
                                bl = true;
                                var11_26 = Rnd.get((int)1000);
                                if (var11_26 < 43) {
                                    questState.giveItems(1884, 42L);
                                } else if (var11_26 < 66) {
                                    questState.giveItems(1895, 36L);
                                } else if (var11_26 < 184) {
                                    questState.giveItems(1876, 4L);
                                } else if (var11_26 < 250) {
                                    questState.giveItems(1881, 6L);
                                } else if (var11_26 < 287) {
                                    questState.giveItems(5549, 8L);
                                } else if (var11_26 < 484) {
                                    questState.giveItems(1874, 1L);
                                } else if (var11_26 < 681) {
                                    questState.giveItems(1889, 1L);
                                } else if (var11_26 < 799) {
                                    questState.giveItems(1877, 1L);
                                } else if (var11_26 < 902) {
                                    questState.giveItems(1894, 1L);
                                } else {
                                    questState.giveItems(4043, 1L);
                                }
                            }
                            if (Rnd.get((int)1000) < 323) {
                                bl = true;
                                var11_26 = Rnd.get((int)1000);
                                if (var11_26 < 335) {
                                    questState.giveItems(1888, 1L);
                                } else if (var11_26 < 556) {
                                    questState.giveItems(4040, 1L);
                                } else if (var11_26 < 725) {
                                    questState.giveItems(1890, 1L);
                                } else if (var11_26 < 872) {
                                    questState.giveItems(5550, 1L);
                                } else if (var11_26 < 962) {
                                    questState.giveItems(1893, 1L);
                                } else if (var11_26 < 986) {
                                    questState.giveItems(4046, 1L);
                                } else {
                                    questState.giveItems(4048, 1L);
                                }
                            }
                        } else if (n6 == 2) {
                            if (Rnd.get((int)1000) < 847) {
                                bl = true;
                                var11_26 = Rnd.get((int)1000);
                                if (var11_26 < 148) {
                                    questState.giveItems(1878, 8L);
                                } else if (var11_26 < 175) {
                                    questState.giveItems(1882, 24L);
                                } else if (var11_26 < 273) {
                                    questState.giveItems(1879, 4L);
                                } else if (var11_26 < 322) {
                                    questState.giveItems(1880, 6L);
                                } else if (var11_26 < 357) {
                                    questState.giveItems(1885, 6L);
                                } else if (var11_26 < 554) {
                                    questState.giveItems(1875, 1L);
                                } else if (var11_26 < 685) {
                                    questState.giveItems(1883, 1L);
                                } else if (var11_26 < 803) {
                                    questState.giveItems(5220, 1L);
                                } else if (var11_26 < 901) {
                                    questState.giveItems(4039, 1L);
                                } else {
                                    questState.giveItems(4044, 1L);
                                }
                            }
                            if (Rnd.get((int)1000) < 251) {
                                bl = true;
                                var11_26 = Rnd.get((int)1000);
                                if (var11_26 < 350) {
                                    questState.giveItems(1887, 1L);
                                } else if (var11_26 < 587) {
                                    questState.giveItems(4042, 1L);
                                } else if (var11_26 < 798) {
                                    questState.giveItems(1886, 1L);
                                } else if (var11_26 < 922) {
                                    questState.giveItems(4041, 1L);
                                } else if (var11_26 < 966) {
                                    questState.giveItems(1892, 1L);
                                } else if (var11_26 < 996) {
                                    questState.giveItems(1891, 1L);
                                } else {
                                    questState.giveItems(4047, 1L);
                                }
                            }
                        } else if (n6 == 3) {
                            if (Rnd.get((int)1000) < 31) {
                                bl = true;
                                var11_26 = Rnd.get((int)1000);
                                if (var11_26 < 223) {
                                    questState.giveItems(730, 1L);
                                } else if (var11_26 < 893) {
                                    questState.giveItems(948, 1L);
                                } else {
                                    questState.giveItems(960, 1L);
                                }
                            }
                            if (Rnd.get((int)1000) < 5) {
                                bl = true;
                                var11_26 = Rnd.get((int)1000);
                                if (var11_26 < 202) {
                                    questState.giveItems(729, 1L);
                                } else if (var11_26 < 928) {
                                    questState.giveItems(947, 1L);
                                } else {
                                    questState.giveItems(959, 1L);
                                }
                            }
                        } else if (n6 == 4) {
                            if (Rnd.get((int)1000) < 329) {
                                bl = true;
                                var11_26 = Rnd.get((int)1000);
                                if (var11_26 < 88) {
                                    questState.giveItems(6698, 1L);
                                } else if (var11_26 < 185) {
                                    questState.giveItems(6699, 1L);
                                } else if (var11_26 < 238) {
                                    questState.giveItems(6700, 1L);
                                } else if (var11_26 < 262) {
                                    questState.giveItems(6701, 1L);
                                } else if (var11_26 < 292) {
                                    questState.giveItems(6702, 1L);
                                } else if (var11_26 < 356) {
                                    questState.giveItems(6703, 1L);
                                } else if (var11_26 < 420) {
                                    questState.giveItems(6704, 1L);
                                } else if (var11_26 < 482) {
                                    questState.giveItems(6705, 1L);
                                } else if (var11_26 < 554) {
                                    questState.giveItems(6706, 1L);
                                } else if (var11_26 < 576) {
                                    questState.giveItems(6707, 1L);
                                } else if (var11_26 < 640) {
                                    questState.giveItems(6708, 1L);
                                } else if (var11_26 < 704) {
                                    questState.giveItems(6709, 1L);
                                } else if (var11_26 < 777) {
                                    questState.giveItems(6710, 1L);
                                } else if (var11_26 < 799) {
                                    questState.giveItems(6711, 1L);
                                } else if (var11_26 < 863) {
                                    questState.giveItems(6712, 1L);
                                } else if (var11_26 < 927) {
                                    questState.giveItems(6713, 1L);
                                } else {
                                    questState.giveItems(6714, 1L);
                                }
                            }
                            if (Rnd.get((int)1000) < 54) {
                                bl = true;
                                var11_26 = Rnd.get((int)1000);
                                if (var11_26 < 100) {
                                    questState.giveItems(6688, 1L);
                                } else if (var11_26 < 198) {
                                    questState.giveItems(6689, 1L);
                                } else if (var11_26 < 298) {
                                    questState.giveItems(6690, 1L);
                                } else if (var11_26 < 398) {
                                    questState.giveItems(6691, 1L);
                                } else if (var11_26 < 499) {
                                    questState.giveItems(7579, 1L);
                                } else if (var11_26 < 601) {
                                    questState.giveItems(6693, 1L);
                                } else if (var11_26 < 703) {
                                    questState.giveItems(6694, 1L);
                                } else if (var11_26 < 801) {
                                    questState.giveItems(6695, 1L);
                                } else if (var11_26 < 902) {
                                    questState.giveItems(6696, 1L);
                                } else {
                                    questState.giveItems(6697, 1L);
                                }
                            }
                        }
                    }
                    questState.takeItems(7255, 1L);
                    if (questState.getQuestItemsCount(7255) == 0L) {
                        string2 = "wigoth_ghost_b_q0620_13.htm";
                    }
                    --n5;
                    ++n5;
                }
                return string2;
            }
            if (string.equalsIgnoreCase("reply_2")) {
                if (!questState.isStarted()) return string2;
                if (n != 2) {
                    if (n != 3) return string2;
                }
                if (questState.getQuestItemsCount(7254) < 1000L) return string2;
                return "wigoth_ghost_b_q0620_16.htm";
            }
            if (string.equalsIgnoreCase("reply_3")) {
                if (!questState.isStarted()) return string2;
                if (n != 2) {
                    if (n != 3) return string2;
                }
                if (questState.getQuestItemsCount(7254) < 1000L) return string2;
                questState.giveItems(6881, 1L);
                questState.takeItems(7254, 1000L);
                return "wigoth_ghost_b_q0620_17.htm";
            }
            if (string.equalsIgnoreCase("reply_4")) {
                if (!questState.isStarted()) return string2;
                if (n != 2) {
                    if (n != 3) return string2;
                }
                if (questState.getQuestItemsCount(7254) < 1000L) return string2;
                questState.giveItems(6883, 1L);
                questState.takeItems(7254, 1000L);
                return "wigoth_ghost_b_q0620_18.htm";
            }
            if (string.equalsIgnoreCase("reply_5")) {
                if (!questState.isStarted()) return string2;
                if (n != 2) {
                    if (n != 3) return string2;
                }
                if (questState.getQuestItemsCount(7254) < 1000L) return string2;
                questState.giveItems(6885, 1L);
                questState.takeItems(7254, 1000L);
                return "wigoth_ghost_b_q0620_19.htm";
            }
            if (string.equalsIgnoreCase("reply_6")) {
                if (!questState.isStarted()) return string2;
                if (n != 2) {
                    if (n != 3) return string2;
                }
                if (questState.getQuestItemsCount(7254) < 1000L) return string2;
                questState.giveItems(6887, 1L);
                questState.takeItems(7254, 1000L);
                return "wigoth_ghost_b_q0620_20.htm";
            }
            if (string.equalsIgnoreCase("reply_7")) {
                if (!questState.isStarted()) return string2;
                if (n != 2) {
                    if (n != 3) return string2;
                }
                if (questState.getQuestItemsCount(7254) < 1000L) return string2;
                questState.giveItems(7580, 1L);
                questState.takeItems(7254, 1000L);
                return "wigoth_ghost_b_q0620_21.htm";
            }
            if (string.equalsIgnoreCase("reply_8")) {
                if (!questState.isStarted()) return string2;
                if (n != 2) {
                    if (n != 3) return string2;
                }
                if (questState.getQuestItemsCount(7254) < 1000L) return string2;
                questState.giveItems(6891, 1L);
                questState.takeItems(7254, 1000L);
                return "wigoth_ghost_b_q0620_22.htm";
            }
            if (string.equalsIgnoreCase("reply_9")) {
                if (!questState.isStarted()) return string2;
                if (n != 2) {
                    if (n != 3) return string2;
                }
                if (questState.getQuestItemsCount(7254) < 1000L) return string2;
                questState.giveItems(6893, 1L);
                questState.takeItems(7254, 1000L);
                return "wigoth_ghost_b_q0620_23.htm";
            }
            if (string.equalsIgnoreCase("reply_10")) {
                if (!questState.isStarted()) return string2;
                if (n != 2) {
                    if (n != 3) return string2;
                }
                if (questState.getQuestItemsCount(7254) < 1000L) return string2;
                questState.giveItems(6895, 1L);
                questState.takeItems(7254, 1000L);
                return "wigoth_ghost_b_q0620_24.htm";
            }
            if (string.equalsIgnoreCase("reply_11")) {
                if (!questState.isStarted()) return string2;
                if (n != 2) {
                    if (n != 3) return string2;
                }
                if (questState.getQuestItemsCount(7254) < 1000L) return string2;
                questState.giveItems(6897, 1L);
                questState.takeItems(7254, 1000L);
                return "wigoth_ghost_b_q0620_25.htm";
            }
            if (string.equalsIgnoreCase("reply_12")) {
                if (!questState.isStarted()) return string2;
                if (n != 2) {
                    if (n != 3) return string2;
                }
                if (questState.getQuestItemsCount(7254) < 1000L) return string2;
                questState.giveItems(6899, 1L);
                questState.takeItems(7254, 1000L);
                return "wigoth_ghost_b_q0620_26.htm";
            }
            if (!string.equalsIgnoreCase("reply_13")) return string2;
            if (!questState.isStarted()) return string2;
            player.teleToLocation(170000, -88250, -2912);
            return "wigoth_ghost_b_q0620_01a.htm";
        }
        if (n2 == 31921) {
            QuestState questState3;
            if (!string.equalsIgnoreCase("reply_1")) return string2;
            if (!questState.isStarted()) return string2;
            int n7 = Calendar.getInstance().get(12);
            Party party = player.getParty();
            if (n7 >= 0 && n7 < 55) {
                return "conquerors_keeper_q0620_02.htm";
            }
            if (party == null) return "conquerors_keeper_q0620_04.htm";
            if (party.getMemberCount() < Config.FOUR_SEPULCHER_MIN_PARTY_MEMBERS) {
                return "conquerors_keeper_q0620_04.htm";
            }
            if (player != party.getPartyLeader()) {
                return "conquerors_keeper_q0620_03.htm";
            }
            for (Player player2 : party.getPartyMembers()) {
                questState3 = player2.getQuestState(_620_FourGoblets.class);
                if (player2.getInventory().getCountOf(7075) == 0L) {
                    string2 = HtmCache.getInstance().getNotNull("quests/_620_FourGoblets/conquerors_keeper_q0620_05.htm", questState.getPlayer());
                    return string2.replace("<?member1?>", player2.getName());
                }
                if (questState3 == null || !questState3.isStarted() && !questState3.isCompleted()) {
                    string2 = HtmCache.getInstance().getNotNull("quests/_620_FourGoblets/conquerors_keeper_q0620_06.htm", questState.getPlayer());
                    return string2.replace("<?member1?>", player2.getName());
                }
                if (player2.getDistance((GameObject)party.getPartyLeader()) > 500.0) {
                    party.getPartyLeader().sendPacket((IStaticPacket)new Say2(0, ChatType.COMMANDCHANNEL_COMMANDER, npcInstance.getName(), "Your group member " + player2.getName() + " is too far away"));
                    return "";
                }
                if (!player2.isDead()) continue;
                party.getPartyLeader().sendPacket((IStaticPacket)new Say2(0, ChatType.COMMANDCHANNEL_COMMANDER, npcInstance.getName(), "Your group member " + player2.getName() + " is dead"));
                return "";
            }
            if (!npcInstance.av_quest0.compareAndSet(0, 1)) {
                return "conquerors_keeper_q0620_07.htm";
            }
            for (Player player2 : party.getPartyMembers()) {
                questState3 = player2.getQuestState(_620_FourGoblets.class);
                if (questState3.getQuestItemsCount(7262) == 0L) {
                    questState3.giveItems(7261, 1L);
                }
                questState3.takeItems(7075, 1L);
                questState3.set("four_goblets_ex", String.valueOf(1), true);
                questState3.takeItems(7260, -1L);
            }
            npcInstance.teleportParty(party, 181528, -85583, -7216, 1000, 0);
            return "";
        }
        if (n2 == 31922) {
            QuestState questState4;
            if (!string.equalsIgnoreCase("reply_1")) return string2;
            if (!questState.isStarted()) return string2;
            int n8 = Calendar.getInstance().get(12);
            Party party = player.getParty();
            if (n8 >= 0 && n8 < 55) {
                return "lords_keeper_q0620_02.htm";
            }
            if (party == null) return "lords_keeper_q0620_04.htm";
            if (party.getMemberCount() < Config.FOUR_SEPULCHER_MIN_PARTY_MEMBERS) {
                return "lords_keeper_q0620_04.htm";
            }
            if (player != party.getPartyLeader()) {
                return "lords_keeper_q0620_03.htm";
            }
            for (Player player3 : party.getPartyMembers()) {
                questState4 = player3.getQuestState(_620_FourGoblets.class);
                if (player3.getInventory().getCountOf(7075) == 0L) {
                    string2 = HtmCache.getInstance().getNotNull("quests/_620_FourGoblets/lords_keeper_q0620_05.htm", questState.getPlayer());
                    return string2.replace("<?member1?>", player3.getName());
                }
                if (questState4 == null || !questState4.isStarted() && !questState4.isCompleted()) {
                    string2 = HtmCache.getInstance().getNotNull("quests/_620_FourGoblets/lords_keeper_q0620_06.htm", questState.getPlayer());
                    return string2.replace("<?member1?>", player3.getName());
                }
                if (player3.getDistance((GameObject)party.getPartyLeader()) > 500.0) {
                    party.getPartyLeader().sendPacket((IStaticPacket)new Say2(0, ChatType.COMMANDCHANNEL_COMMANDER, npcInstance.getName(), "Your group member " + player3.getName() + " is too far away"));
                    return "";
                }
                if (!player3.isDead()) continue;
                party.getPartyLeader().sendPacket((IStaticPacket)new Say2(0, ChatType.COMMANDCHANNEL_COMMANDER, npcInstance.getName(), "Your group member " + player3.getName() + " is dead"));
                return "";
            }
            if (!npcInstance.av_quest0.compareAndSet(0, 1)) {
                return "lords_keeper_q0620_07.htm";
            }
            for (Player player3 : party.getPartyMembers()) {
                questState4 = player3.getQuestState(_620_FourGoblets.class);
                if (questState4.getQuestItemsCount(7262) == 0L) {
                    questState4.giveItems(7261, 1L);
                }
                questState4.takeItems(7075, 1L);
                questState4.set("four_goblets_ex", String.valueOf(1), true);
                questState4.takeItems(7260, -1L);
            }
            npcInstance.teleportParty(party, 179849, -88990, -7216, 1000, 0);
            return "";
        }
        if (n2 == 31923) {
            QuestState questState5;
            if (!string.equalsIgnoreCase("reply_1")) return string2;
            if (!questState.isStarted()) return string2;
            int n9 = Calendar.getInstance().get(12);
            Party party = player.getParty();
            if (n9 >= 0 && n9 < 55) {
                return "savants_keeper_q0620_02.htm";
            }
            if (party == null) return "savants_keeper_q0620_04.htm";
            if (party.getMemberCount() < Config.FOUR_SEPULCHER_MIN_PARTY_MEMBERS) {
                return "savants_keeper_q0620_04.htm";
            }
            if (player != party.getPartyLeader()) {
                return "savants_keeper_q0620_03.htm";
            }
            for (Player player4 : party.getPartyMembers()) {
                questState5 = player4.getQuestState(_620_FourGoblets.class);
                if (player4.getInventory().getCountOf(7075) == 0L) {
                    string2 = HtmCache.getInstance().getNotNull("quests/_620_FourGoblets/savants_keeper_q0620_05.htm", questState.getPlayer());
                    return string2.replace("<?member1?>", player4.getName());
                }
                if (questState5 == null || !questState5.isStarted() && !questState5.isCompleted()) {
                    string2 = HtmCache.getInstance().getNotNull("quests/_620_FourGoblets/savants_keeper_q0620_06.htm", questState.getPlayer());
                    return string2.replace("<?member1?>", player4.getName());
                }
                if (player4.getDistance((GameObject)party.getPartyLeader()) > 500.0) {
                    party.getPartyLeader().sendPacket((IStaticPacket)new Say2(0, ChatType.COMMANDCHANNEL_COMMANDER, npcInstance.getName(), "Your group member " + player4.getName() + " is too far away"));
                    return "";
                }
                if (!player4.isDead()) continue;
                party.getPartyLeader().sendPacket((IStaticPacket)new Say2(0, ChatType.COMMANDCHANNEL_COMMANDER, npcInstance.getName(), "Your group member " + player4.getName() + " is dead"));
                return "";
            }
            if (!npcInstance.av_quest0.compareAndSet(0, 1)) {
                return "savants_keeper_q0620_07.htm";
            }
            for (Player player4 : party.getPartyMembers()) {
                questState5 = player4.getQuestState(_620_FourGoblets.class);
                if (questState5.getQuestItemsCount(7262) == 0L) {
                    questState5.giveItems(7261, 1L);
                }
                questState5.takeItems(7075, 1L);
                questState5.set("four_goblets_ex", String.valueOf(1), true);
                questState5.takeItems(7260, -1L);
            }
            npcInstance.teleportParty(party, 173216, -86195, -7216, 1000, 0);
            return "";
        }
        if (n2 != 31924) return string2;
        if (!string.equalsIgnoreCase("reply_1")) return string2;
        if (!questState.isStarted()) return string2;
        int n10 = Calendar.getInstance().get(12);
        Party party = player.getParty();
        if (n10 >= 0 && n10 < 55) {
            return "magistrates_keeper_q0620_02.htm";
        }
        if (party == null) return "magistrates_keeper_q0620_04.htm";
        if (party.getMemberCount() < Config.FOUR_SEPULCHER_MIN_PARTY_MEMBERS) {
            return "magistrates_keeper_q0620_04.htm";
        }
        if (player != party.getPartyLeader()) {
            return "magistrates_keeper_q0620_03.htm";
        }
        for (Player player5 : party.getPartyMembers()) {
            questState2 = player5.getQuestState(_620_FourGoblets.class);
            if (player5.getInventory().getCountOf(7075) == 0L) {
                string2 = HtmCache.getInstance().getNotNull("quests/_620_FourGoblets/magistrates_keeper_q0620_05.htm", questState.getPlayer());
                return string2.replace("<?member1?>", player5.getName());
            }
            if (questState2 == null || !questState2.isStarted() && !questState2.isCompleted()) {
                string2 = HtmCache.getInstance().getNotNull("quests/_620_FourGoblets/magistrates_keeper_q0620_06.htm", questState.getPlayer());
                return string2.replace("<?member1?>", player5.getName());
            }
            if (player5.getDistance((GameObject)party.getPartyLeader()) > 500.0) {
                party.getPartyLeader().sendPacket((IStaticPacket)new Say2(0, ChatType.COMMANDCHANNEL_COMMANDER, npcInstance.getName(), "Your group member " + player5.getName() + " is too far away"));
                return "";
            }
            if (!player5.isDead()) continue;
            party.getPartyLeader().sendPacket((IStaticPacket)new Say2(0, ChatType.COMMANDCHANNEL_COMMANDER, npcInstance.getName(), "Your group member " + player5.getName() + " is dead"));
            return "";
        }
        if (!npcInstance.av_quest0.compareAndSet(0, 1)) {
            return "magistrates_keeper_q0620_07.htm";
        }
        for (Player player5 : party.getPartyMembers()) {
            questState2 = player5.getQuestState(_620_FourGoblets.class);
            if (questState2.getQuestItemsCount(7262) == 0L) {
                questState2.giveItems(7261, 1L);
            }
            questState2.takeItems(7075, 1L);
            questState2.set("four_goblets_ex", String.valueOf(1), true);
            questState2.takeItems(7260, -1L);
        }
        npcInstance.teleportParty(party, 175615, -82365, -7216, 1000, 0);
        return "";
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        QuestState questState2;
        Player player = questState.getPlayer();
        int n = npcInstance.getNpcId();
        if (n == 25339 || n == 25346 || n == 25342 || n == 25349) {
            for (QuestState questState3 : this.getPartyMembersWithQuest(player, -1)) {
                if (questState3.getQuestItemsCount(7262) == 0L) {
                    if (n == 25339 && questState3.getQuestItemsCount(7256) == 0L) {
                        questState3.giveItems(7256, 1L);
                    } else if (n == 25342 && questState3.getQuestItemsCount(7257) == 0L) {
                        questState3.giveItems(7257, 1L);
                    } else if (n == 25346 && questState3.getQuestItemsCount(7258) == 0L) {
                        questState3.giveItems(7258, 1L);
                    } else if (n == 25349 && questState3.getQuestItemsCount(7259) == 0L) {
                        questState3.giveItems(7259, 1L);
                    }
                }
                questState3.set("four_goblets_ex", String.valueOf(2), true);
            }
        } else if (c.containsKey(n)) {
            QuestState questState4 = this.getRandomPartyMemberWithQuest(player, -1);
            if (questState4 != null && questState4.rollAndGive(7255, Rnd.chance((int)c.get(n)) ? 2 : 1, 100.0)) {
                questState4.playSound("ItemSound.quest_itemget");
            }
        } else if (d.containsKey(n)) {
            QuestState questState5 = this.getRandomPartyMemberWithQuest(player, -1);
            if (questState5 != null && questState5.rollAndGive(7255, 1, (double)d.get(n).intValue())) {
                questState5.playSound("ItemSound.quest_itemget");
            }
        } else if (e.containsKey(n) && (questState2 = this.getRandomPartyMemberWithQuest(player, -1)) != null && questState2.rollAndGive(7255, Rnd.chance((int)e.get(n)) ? 5 : 4, 100.0)) {
            questState2.playSound("ItemSound.quest_itemget");
        }
        return null;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    static {
        c.put(18120, 51);
        c.put(18122, 10);
        c.put(18121, 44);
        c.put(18123, 51);
        c.put(18125, 10);
        c.put(18124, 44);
        c.put(18126, 51);
        c.put(18128, 10);
        c.put(18127, 44);
        c.put(18129, 51);
        c.put(18131, 10);
        c.put(18130, 44);
        c.put(18132, 54);
        c.put(18133, 42);
        c.put(18134, 7);
        c.put(18135, 42);
        c.put(18136, 42);
        c.put(18138, 41);
        c.put(18139, 39);
        c.put(18137, 6);
        c.put(18140, 41);
        c.put(18166, 8);
        c.put(18167, 7);
        c.put(18168, 10);
        c.put(18169, 6);
        c.put(18171, 11);
        c.put(18170, 7);
        c.put(18172, 6);
        c.put(18173, 17);
        c.put(18175, 10);
        c.put(18174, 45);
        c.put(18176, 17);
        c.put(18178, 10);
        c.put(18177, 45);
        c.put(18179, 17);
        c.put(18181, 10);
        c.put(18180, 45);
        c.put(18182, 17);
        c.put(18184, 10);
        c.put(18183, 45);
        c.put(18195, 8);
        c.put(18185, 46);
        c.put(18186, 47);
        c.put(18187, 42);
        c.put(18188, 7);
        c.put(18189, 42);
        c.put(18190, 42);
        c.put(18192, 41);
        c.put(18193, 39);
        c.put(18191, 6);
        c.put(18194, 41);
        c.put(18220, 47);
        c.put(18221, 51);
        c.put(18222, 43);
        c.put(18223, 7);
        c.put(18224, 44);
        c.put(18225, 43);
        c.put(18227, 82);
        c.put(18228, 36);
        c.put(18226, 6);
        c.put(18229, 41);
        d.put(18141, 90);
        d.put(18142, 90);
        d.put(18143, 90);
        d.put(18144, 90);
        d.put(18149, 75);
        d.put(18148, 85);
        d.put(18146, 78);
        d.put(18147, 73);
        d.put(18145, 76);
        d.put(18230, 58);
        e.put(18212, 50);
        e.put(18213, 50);
        e.put(18214, 50);
        e.put(18215, 50);
        e.put(18216, 50);
        e.put(18217, 50);
        e.put(18218, 50);
        e.put(18219, 50);
    }
}
