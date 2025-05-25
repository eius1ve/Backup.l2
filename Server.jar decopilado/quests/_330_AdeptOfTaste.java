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

public class _330_AdeptOfTaste
extends Quest
implements ScriptFile {
    private static final int ayp = 30062;
    private static final int ayq = 30067;
    private static final int ayr = 30069;
    private static final int ays = 30073;
    private static final int ayt = 30078;
    private static final int ayu = 30461;
    private static final int ayv = 30469;
    private static final int ayw = 20147;
    private static final int ayx = 20154;
    private static final int ayy = 20155;
    private static final int ayz = 20156;
    private static final int ayA = 20204;
    private static final int ayB = 20223;
    private static final int ayC = 20226;
    private static final int ayD = 20228;
    private static final int ayE = 20229;
    private static final int ayF = 20265;
    private static final int ayG = 20266;
    private static final int ayH = 1420;
    private static final int ayI = 1421;
    private static final int ayJ = 1422;
    private static final int ayK = 1423;
    private static final int ayL = 1424;
    private static final int ayM = 1425;
    private static final int ayN = 1426;
    private static final int ayO = 1427;
    private static final int ayP = 1428;
    private static final int ayQ = 1429;
    private static final int ayR = 1430;
    private static final int ayS = 1431;
    private static final int ayT = 1432;
    private static final int ayU = 1433;
    private static final int ayV = 1434;
    private static final int ayW = 1435;
    private static final int ayX = 1436;
    private static final int ayY = 1437;
    private static final int ayZ = 1438;
    private static final int aza = 1439;
    private static final int azb = 1440;
    private static final int azc = 1441;
    private static final int[] by = new int[]{1442, 1443, 1444, 1445, 1446};
    private static final int[] bz = new int[]{1447, 1448, 1449, 1450, 1451};
    private static final int[] bA = new int[]{1424, 1429, 1433, 1437, 1441};
    private static final int[] bB = new int[]{1425, 1430, 1438};
    private static final int[] bC = new int[]{0, 0, 1455, 1456, 1457};
    private static final int[] bD = new int[]{10000, 14870, 6490, 12220, 16540};

    public _330_AdeptOfTaste() {
        super(0);
        this.addStartNpc(30469);
        this.addTalkId(new int[]{30062});
        this.addTalkId(new int[]{30067});
        this.addTalkId(new int[]{30069});
        this.addTalkId(new int[]{30073});
        this.addTalkId(new int[]{30078});
        this.addTalkId(new int[]{30461});
        this.addKillId(new int[]{20147});
        this.addKillId(new int[]{20154});
        this.addKillId(new int[]{20155});
        this.addKillId(new int[]{20156});
        this.addKillId(new int[]{20204});
        this.addKillId(new int[]{20223});
        this.addKillId(new int[]{20226});
        this.addKillId(new int[]{20228});
        this.addKillId(new int[]{20229});
        this.addKillId(new int[]{20265});
        this.addKillId(new int[]{20266});
        this.addQuestItem(new int[]{1420});
        this.addQuestItem(new int[]{1421});
        this.addQuestItem(new int[]{1422});
        this.addQuestItem(new int[]{1423});
        this.addQuestItem(new int[]{1426});
        this.addQuestItem(new int[]{1427});
        this.addQuestItem(new int[]{1428});
        this.addQuestItem(new int[]{1431});
        this.addQuestItem(new int[]{1432});
        this.addQuestItem(new int[]{1434});
        this.addQuestItem(new int[]{1435});
        this.addQuestItem(new int[]{1436});
        this.addQuestItem(new int[]{1439});
        this.addQuestItem(new int[]{1440});
        this.addQuestItem(bA);
        this.addQuestItem(bB);
        this.addQuestItem(by);
        this.addQuestItem(bz);
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        int n = questState.getState();
        if (string.equalsIgnoreCase("30469_03.htm") && n == 1) {
            if (questState.getQuestItemsCount(1420) == 0L) {
                questState.giveItems(1420, 1L);
            }
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("30062_05.htm") && n == 2) {
            if (questState.getQuestItemsCount(1423) + questState.getQuestItemsCount(1422) < 40L) {
                return null;
            }
            _330_AdeptOfTaste.a(questState, 1424);
        } else if (string.equalsIgnoreCase("30067_05.htm") && n == 2) {
            if (questState.getQuestItemsCount(1436) + questState.getQuestItemsCount(1435) < 20L) {
                return null;
            }
            _330_AdeptOfTaste.b(questState, 1437);
        } else if (string.equalsIgnoreCase("30073_05.htm") && n == 2) {
            if (questState.getQuestItemsCount(1427) < 20L) {
                return null;
            }
            _330_AdeptOfTaste.c(questState, 1429);
        }
        return string;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        boolean bl;
        int n = questState.getState();
        int n2 = npcInstance.getNpcId();
        if (n == 1) {
            if (n2 != 30469) {
                return "noquest";
            }
            if (questState.getPlayer().getLevel() < 24) {
                questState.exitCurrentQuest(true);
                return "30469_01.htm";
            }
            questState.setCond(0);
            return "30469_02.htm";
        }
        if (n != 2) {
            return "noquest";
        }
        long l = questState.getQuestItemsCount(bA);
        long l2 = questState.getQuestItemsCount(bB);
        long l3 = l + l2;
        boolean bl2 = bl = questState.getQuestItemsCount(1420) > 0L;
        if (n2 == 30469) {
            if (bl) {
                if (l3 < 5L) {
                    return "30469_04.htm";
                }
                questState.takeAllItems(1420);
                questState.takeAllItems(bA);
                questState.takeAllItems(bB);
                if (l2 > 3L) {
                    l2 = 3L;
                }
                questState.playSound((l2 += (long)Rnd.get((int)0, (int)1)) == 4L ? "ItemSound.quest_jackpot" : "ItemSound.quest_middle");
                questState.giveItems(by[(int)l2], 1L);
                return "30469_05t" + ++l2 + ".htm";
            }
            if (l3 == 0L) {
                long l4 = questState.getQuestItemsCount(by);
                long l5 = questState.getQuestItemsCount(bz);
                if (l4 > 0L && l5 == 0L) {
                    return "30469_06.htm";
                }
                if (l4 == 0L && l5 > 0L) {
                    for (int i = bz.length; i > 0; --i) {
                        if (questState.getQuestItemsCount(bz[i - 1]) <= 0L) continue;
                        questState.takeAllItems(bz);
                        if (bD[i - 1] > 0) {
                            questState.giveItems(57, (long)bD[i - 1]);
                        }
                        if (bC[i - 1] > 0) {
                            questState.giveItems(bC[i - 1], 1L);
                        }
                        questState.playSound("ItemSound.quest_finish");
                        this.giveExtraReward(questState.getPlayer());
                        questState.exitCurrentQuest(true);
                        return "30469_06t" + i + ".htm";
                    }
                }
            }
        }
        if (n2 == 30461) {
            if (bl) {
                return "30461_01.htm";
            }
            if (l3 == 0L) {
                if (questState.getQuestItemsCount(bz) > 0L) {
                    return "30461_04.htm";
                }
                for (int i = by.length; i > 0; --i) {
                    if (questState.getQuestItemsCount(by[i - 1]) <= 0L) continue;
                    questState.takeAllItems(by);
                    questState.playSound("ItemSound.quest_middle");
                    questState.giveItems(bz[i - 1], 1L);
                    return "30461_02t" + i + ".htm";
                }
            }
        }
        if (!bl || l3 >= 5L) {
            return "noquest";
        }
        if (n2 == 30062) {
            boolean bl3;
            boolean bl4 = bl3 = questState.getQuestItemsCount(1424) > 0L || questState.getQuestItemsCount(1425) > 0L;
            if (questState.getQuestItemsCount(1421) > 0L) {
                if (!bl3) {
                    long l6 = questState.getQuestItemsCount(1423);
                    if (l6 >= 40L) {
                        _330_AdeptOfTaste.a(questState, 1425);
                        return "30062_06.htm";
                    }
                    return (l6 += questState.getQuestItemsCount(1422)) < 40L ? "30062_02.htm" : "30062_03.htm";
                }
            } else {
                if (bl3) {
                    return "30062_07.htm";
                }
                questState.giveItems(1421, 1L);
                return "30062_01.htm";
            }
        }
        if (n2 == 30067) {
            boolean bl5;
            boolean bl6 = bl5 = questState.getQuestItemsCount(1437) > 0L || questState.getQuestItemsCount(1438) > 0L;
            if (questState.getQuestItemsCount(1434) > 0L) {
                if (!bl5) {
                    long l7 = questState.getQuestItemsCount(1436);
                    if (l7 >= 20L) {
                        _330_AdeptOfTaste.b(questState, 1438);
                        return "30067_06.htm";
                    }
                    return (l7 += questState.getQuestItemsCount(1435)) < 20L ? "30067_02.htm" : "30067_03.htm";
                }
            } else if (bl5) {
                return "30067_07.htm";
            }
            questState.giveItems(1434, 1L);
            return "30067_01.htm";
        }
        if (n2 == 30069) {
            boolean bl7;
            boolean bl8 = bl7 = questState.getQuestItemsCount(1441) > 0L;
            if (questState.getQuestItemsCount(1439) > 0L) {
                if (!bl7) {
                    if (questState.getQuestItemsCount(1440) < 30L) {
                        return "30069_02.htm";
                    }
                    questState.takeItems(1439, -1L);
                    questState.takeItems(1440, -1L);
                    questState.playSound("ItemSound.quest_middle");
                    questState.giveItems(1441, 1L);
                    return "30069_03.htm";
                }
            } else {
                if (bl7) {
                    return "30069_04.htm";
                }
                questState.giveItems(1439, 1L);
                return "30069_01.htm";
            }
        }
        if (n2 == 30073) {
            boolean bl9;
            boolean bl10 = bl9 = questState.getQuestItemsCount(1429) > 0L || questState.getQuestItemsCount(1430) > 0L;
            if (questState.getQuestItemsCount(1426) > 0L) {
                if (!bl9) {
                    if (questState.getQuestItemsCount(1427) < 20L) {
                        return "30073_02.htm";
                    }
                    if (questState.getQuestItemsCount(1428) < 10L) {
                        return "30073_03.htm";
                    }
                    _330_AdeptOfTaste.c(questState, 1430);
                    return "30073_06.htm";
                }
            } else {
                if (bl9) {
                    return "30073_07.htm";
                }
                questState.giveItems(1426, 1L);
                return "30073_01.htm";
            }
        }
        if (n2 == 30078) {
            boolean bl11;
            boolean bl12 = bl11 = questState.getQuestItemsCount(1433) > 0L;
            if (questState.getQuestItemsCount(1431) > 0L) {
                if (!bl11) {
                    if (questState.getQuestItemsCount(1432) < 30L) {
                        return "30078_02.htm";
                    }
                    questState.takeItems(1431, -1L);
                    questState.takeItems(1432, -1L);
                    questState.playSound("ItemSound.quest_middle");
                    questState.giveItems(1433, 1L);
                    return "30078_03.htm";
                }
            } else {
                if (bl11) {
                    return "30078_04.htm";
                }
                questState.giveItems(1431, 1L);
                return "30078_01.htm";
            }
        }
        return "noquest";
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        boolean bl;
        if (questState.getState() != 2) {
            return null;
        }
        int n = npcInstance.getNpcId();
        long l = questState.getQuestItemsCount(bA);
        long l2 = questState.getQuestItemsCount(bB);
        long l3 = l + l2;
        boolean bl2 = bl = questState.getQuestItemsCount(1420) > 0L;
        if (!bl || l3 >= 5L) {
            return null;
        }
        if (n == 20147 && questState.getQuestItemsCount(1431) > 0L) {
            questState.rollAndGive(1432, 1, 1, 30, 100.0);
        } else if (n == 20154 && questState.getQuestItemsCount(1421) > 0L) {
            _330_AdeptOfTaste.a(questState, 70, 77);
        } else if (n == 20155 && questState.getQuestItemsCount(1421) > 0L) {
            _330_AdeptOfTaste.a(questState, 77, 85);
        } else if (n == 20156 && questState.getQuestItemsCount(1421) > 0L) {
            _330_AdeptOfTaste.a(questState, 87, 96);
        } else if (n == 20223 && questState.getQuestItemsCount(1421) > 0L) {
            _330_AdeptOfTaste.a(questState, 70, 77);
        } else if (n == 20204 && questState.getQuestItemsCount(1426) > 0L) {
            _330_AdeptOfTaste.b(questState, 80, 95);
        } else if (n == 20229 && questState.getQuestItemsCount(1426) > 0L) {
            _330_AdeptOfTaste.b(questState, 92, 100);
        } else if (n == 20226 && questState.getQuestItemsCount(1434) > 0L) {
            _330_AdeptOfTaste.c(questState, 87, 96);
        } else if (n == 20228 && questState.getQuestItemsCount(1434) > 0L) {
            _330_AdeptOfTaste.c(questState, 90, 100);
        } else if (n == 20265 && questState.getQuestItemsCount(1439) > 0L) {
            questState.rollAndGive(1440, 1, 3, 30, 97.0);
        } else if (n == 20266 && questState.getQuestItemsCount(1439) > 0L) {
            questState.rollAndGive(1440, 1, 2, 30, 100.0);
        }
        return null;
    }

    private static void a(QuestState questState, int n, int n2) {
        int n3 = Rnd.get((int)100);
        if (n3 < n) {
            questState.rollAndGive(1422, 1, 1, 40, 100.0);
        } else if (n3 < n2) {
            questState.rollAndGive(1423, 1, 1, 40, 100.0);
        }
    }

    private static void b(QuestState questState, int n, int n2) {
        int n3 = Rnd.get((int)100);
        if (n3 < n) {
            questState.rollAndGive(1427, 1, 1, 20, 100.0);
        } else if (n3 < n2) {
            questState.rollAndGive(1428, 1, 1, 10, 100.0);
        }
    }

    private static void c(QuestState questState, int n, int n2) {
        int n3 = Rnd.get((int)100);
        if (n3 < n) {
            questState.rollAndGive(1435, 1, 1, 20, 100.0);
        } else if (n3 < n2) {
            questState.rollAndGive(1436, 1, 1, 20, 100.0);
        }
    }

    private static void a(QuestState questState, int n) {
        questState.takeItems(1421, -1L);
        questState.takeItems(1423, -1L);
        questState.takeItems(1422, -1L);
        questState.playSound("ItemSound.quest_middle");
        questState.giveItems(n, 1L);
    }

    private static void b(QuestState questState, int n) {
        questState.takeItems(1434, -1L);
        questState.takeItems(1436, -1L);
        questState.takeItems(1435, -1L);
        questState.playSound("ItemSound.quest_middle");
        questState.giveItems(n, 1L);
    }

    private static void c(QuestState questState, int n) {
        questState.takeItems(1426, -1L);
        questState.takeItems(1427, -1L);
        questState.takeItems(1428, -1L);
        questState.playSound("ItemSound.quest_middle");
        questState.giveItems(n, 1L);
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
