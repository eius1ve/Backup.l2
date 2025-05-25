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

public class _402_PathToKnight
extends Quest
implements ScriptFile {
    private static final int aVP = 30417;
    private static final int aVQ = 30031;
    private static final int aVR = 30037;
    private static final int aVS = 30039;
    private static final int aVT = 30289;
    private static final int aVU = 30311;
    private static final int aVV = 30332;
    private static final int aVW = 30379;
    private static final int aVX = 30653;
    private static final int aVY = 20775;
    private static final int aVZ = 27024;
    private static final int aWa = 20038;
    private static final int aWb = 20043;
    private static final int aWc = 20050;
    private static final int aWd = 20030;
    private static final int aWe = 20027;
    private static final int aWf = 20024;
    private static final int aWg = 20103;
    private static final int aWh = 20106;
    private static final int aWi = 20108;
    private static final int aWj = 20404;
    private static final int aWk = 1161;
    private static final int aWl = 1162;
    private static final int aWm = 1163;
    private static final int aWn = 1164;
    private static final int aWo = 1165;
    private static final int aWp = 1166;
    private static final int aWq = 1167;
    private static final int aWr = 1168;
    private static final int aWs = 1169;
    private static final int aWt = 1170;
    private static final int aWu = 1171;
    private static final int aWv = 1172;
    private static final int aWw = 1173;
    private static final int aWx = 1174;
    private static final int aWy = 1175;
    private static final int aWz = 1176;
    private static final int aWA = 1177;
    private static final int aWB = 1178;
    private static final int aWC = 1179;
    private static final int aWD = 1271;

    public _402_PathToKnight() {
        super(0);
        this.addStartNpc(30417);
        this.addTalkId(new int[]{30031, 30037, 30039, 30289, 30311, 30332, 30379, 30653});
        this.addKillId(new int[]{20775, 27024, 20038, 20043, 20050, 20030, 20027, 20024, 20103, 20106, 20108, 20404});
        this.addQuestItem(new int[]{1271, 1169, 1171, 1173, 1175, 1177, 1179});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        int n2 = questState.getPlayer().getClassId().getId();
        int n3 = questState.getPlayer().getVarInt("prof1");
        int n4 = 0;
        int n5 = 4;
        if (n == 30417) {
            if (string.equalsIgnoreCase("quest_accept")) {
                if (questState.getQuestItemsCount(1271) == 0L) {
                    questState.giveItems(1271, 1L);
                    questState.setCond(1);
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                    string2 = "sir_karrel_vasper_q0402_08.htm";
                }
            } else if (string.equalsIgnoreCase("reply_1")) {
                string2 = n2 == n4 ? (questState.getQuestItemsCount(1161) > 0L ? "sir_karrel_vasper_q0402_04.htm" : "sir_karrel_vasper_q0402_05.htm") : (n2 == n5 ? "sir_karrel_vasper_q0402_02a.htm" : "sir_karrel_vasper_q0402_03.htm");
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "sir_karrel_vasper_q0402_07.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                string2 = "sir_karrel_vasper_q0402_15.htm";
            } else if (string.equalsIgnoreCase("reply_4") && questState.getQuestItemsCount(1271) > 0L && questState.getQuestItemsCount(1162) + questState.getQuestItemsCount(1163) + questState.getQuestItemsCount(1164) + questState.getQuestItemsCount(1165) + questState.getQuestItemsCount(1166) + questState.getQuestItemsCount(1167) == 3L) {
                if (n3 == 0) {
                    questState.getPlayer().setVar("prof1", "1", -1L);
                    this.giveExtraReward(questState.getPlayer());
                    questState.addExpAndSp(3200L, 0L);
                    if (questState.getQuestItemsCount(1166) >= 1L && questState.getQuestItemsCount(1167) >= 1L) {
                        questState.addExpAndSp(0L, 7260L);
                    } else if (questState.getQuestItemsCount(1166) >= 1L || questState.getQuestItemsCount(1167) >= 1L) {
                        questState.addExpAndSp(0L, 4760L);
                    } else {
                        questState.addExpAndSp(0L, 2260L);
                    }
                }
                questState.takeItems(1162, -1L);
                questState.takeItems(1163, -1L);
                questState.takeItems(1164, -1L);
                questState.takeItems(1165, -1L);
                questState.takeItems(1166, -1L);
                questState.takeItems(1167, -1L);
                questState.takeItems(1168, -1L);
                questState.takeItems(1172, -1L);
                questState.takeItems(1176, -1L);
                questState.takeItems(1170, -1L);
                questState.takeItems(1174, -1L);
                questState.takeItems(1178, -1L);
                questState.takeItems(1169, -1L);
                questState.takeItems(1171, -1L);
                questState.takeItems(1173, -1L);
                questState.takeItems(1175, -1L);
                questState.takeItems(1177, -1L);
                questState.takeItems(1179, -1L);
                questState.takeItems(1271, -1L);
                questState.giveItems(1161, 1L);
                string2 = "sir_karrel_vasper_q0402_13.htm";
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
            } else if (string.equalsIgnoreCase("reply_5") && questState.getQuestItemsCount(1271) > 0L && questState.getQuestItemsCount(1162) + questState.getQuestItemsCount(1163) + questState.getQuestItemsCount(1164) + questState.getQuestItemsCount(1165) + questState.getQuestItemsCount(1166) + questState.getQuestItemsCount(1167) > 3L && questState.getQuestItemsCount(1162) + questState.getQuestItemsCount(1163) + questState.getQuestItemsCount(1164) + questState.getQuestItemsCount(1165) + questState.getQuestItemsCount(1166) + questState.getQuestItemsCount(1167) < 6L) {
                if (n3 == 0) {
                    questState.getPlayer().setVar("prof1", "1", -1L);
                    this.giveExtraReward(questState.getPlayer());
                    questState.addExpAndSp(3200L, 0L);
                    if (questState.getQuestItemsCount(1166) >= 1L && questState.getQuestItemsCount(1167) >= 1L) {
                        questState.addExpAndSp(0L, 7260L);
                    } else if (questState.getQuestItemsCount(1166) >= 1L || questState.getQuestItemsCount(1167) >= 1L) {
                        questState.addExpAndSp(0L, 4760L);
                    } else {
                        questState.addExpAndSp(0L, 2260L);
                    }
                }
                questState.takeItems(1162, -1L);
                questState.takeItems(1163, -1L);
                questState.takeItems(1164, -1L);
                questState.takeItems(1165, -1L);
                questState.takeItems(1166, -1L);
                questState.takeItems(1167, -1L);
                questState.takeItems(1168, -1L);
                questState.takeItems(1172, -1L);
                questState.takeItems(1176, -1L);
                questState.takeItems(1170, -1L);
                questState.takeItems(1174, -1L);
                questState.takeItems(1178, -1L);
                questState.takeItems(1169, -1L);
                questState.takeItems(1171, -1L);
                questState.takeItems(1173, -1L);
                questState.takeItems(1175, -1L);
                questState.takeItems(1177, -1L);
                questState.takeItems(1179, -1L);
                questState.takeItems(1271, -1L);
                questState.giveItems(1161, 1L);
                string2 = "sir_karrel_vasper_q0402_14.htm";
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
            }
        } else if (n == 30031) {
            if (string.equalsIgnoreCase("reply_1")) {
                questState.giveItems(1178, 1L);
                string2 = "quilt_q0402_02.htm";
            }
        } else if (n == 30037) {
            if (string.equalsIgnoreCase("reply_1")) {
                questState.giveItems(1174, 1L);
                string2 = "levian_q0402_02.htm";
            }
        } else if (n == 30039) {
            if (string.equalsIgnoreCase("reply_1")) {
                questState.giveItems(1176, 1L);
                string2 = "gilbert_q0402_02.htm";
            }
        } else if (n == 30289) {
            if (string.equalsIgnoreCase("reply_1")) {
                questState.giveItems(1170, 1L);
                string2 = "bishop_raimund_q0402_03.htm";
            }
        } else if (n == 30332) {
            if (string.equalsIgnoreCase("reply_1")) {
                questState.giveItems(1168, 1L);
                string2 = "captain_bathia_q0402_02.htm";
            }
        } else if (n == 30379 && string.equalsIgnoreCase("reply_1")) {
            questState.giveItems(1172, 1L);
            string2 = "captain_bezique_q0402_02.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        Player player = questState.getPlayer();
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        int n3 = questState.getPlayer().getVarInt("prof1");
        switch (n2) {
            case 1: {
                if (n != 30417) break;
                if (player.getLevel() < 18) {
                    string = "sir_karrel_vasper_q0402_02.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (player.getClassId().getId() != 0) {
                    string = "sir_karrel_vasper_q0402_03.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "sir_karrel_vasper_q0402_01.htm";
                break;
            }
            case 2: {
                if (n == 30417) {
                    if (questState.getQuestItemsCount(1271) > 0L && questState.getQuestItemsCount(1162) + questState.getQuestItemsCount(1163) + questState.getQuestItemsCount(1164) + questState.getQuestItemsCount(1165) + questState.getQuestItemsCount(1166) + questState.getQuestItemsCount(1167) < 3L) {
                        string = "sir_karrel_vasper_q0402_09.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1271) > 0L && questState.getQuestItemsCount(1162) + questState.getQuestItemsCount(1163) + questState.getQuestItemsCount(1164) + questState.getQuestItemsCount(1165) + questState.getQuestItemsCount(1166) + questState.getQuestItemsCount(1167) == 3L) {
                        string = "sir_karrel_vasper_q0402_10.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1271) > 0L && questState.getQuestItemsCount(1162) + questState.getQuestItemsCount(1163) + questState.getQuestItemsCount(1164) + questState.getQuestItemsCount(1165) + questState.getQuestItemsCount(1166) + questState.getQuestItemsCount(1167) > 3L && questState.getQuestItemsCount(1162) + questState.getQuestItemsCount(1163) + questState.getQuestItemsCount(1164) + questState.getQuestItemsCount(1165) + questState.getQuestItemsCount(1166) + questState.getQuestItemsCount(1167) < 6L) {
                        string = "sir_karrel_vasper_q0402_11.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1271) <= 0L || questState.getQuestItemsCount(1162) + questState.getQuestItemsCount(1163) + questState.getQuestItemsCount(1164) + questState.getQuestItemsCount(1165) + questState.getQuestItemsCount(1166) + questState.getQuestItemsCount(1167) != 6L) break;
                    if (n3 == 0) {
                        questState.getPlayer().setVar("prof1", "1", -1L);
                        this.giveExtraReward(questState.getPlayer());
                        questState.addExpAndSp(3200L, 0L);
                        if (questState.getQuestItemsCount(1166) >= 1L && questState.getQuestItemsCount(1167) >= 1L) {
                            questState.addExpAndSp(0L, 7260L);
                        } else if (questState.getQuestItemsCount(1166) >= 1L || questState.getQuestItemsCount(1167) >= 1L) {
                            questState.addExpAndSp(0L, 4760L);
                        } else {
                            questState.addExpAndSp(0L, 2260L);
                        }
                    }
                    questState.takeItems(1162, -1L);
                    questState.takeItems(1163, -1L);
                    questState.takeItems(1164, -1L);
                    questState.takeItems(1165, -1L);
                    questState.takeItems(1166, -1L);
                    questState.takeItems(1167, -1L);
                    questState.takeItems(1168, -1L);
                    questState.takeItems(1172, -1L);
                    questState.takeItems(1176, -1L);
                    questState.takeItems(1170, -1L);
                    questState.takeItems(1174, -1L);
                    questState.takeItems(1178, -1L);
                    questState.takeItems(1169, -1L);
                    questState.takeItems(1171, -1L);
                    questState.takeItems(1173, -1L);
                    questState.takeItems(1175, -1L);
                    questState.takeItems(1177, -1L);
                    questState.takeItems(1179, -1L);
                    questState.takeItems(1271, -1L);
                    questState.giveItems(1161, 1L);
                    string = "sir_karrel_vasper_q0402_12.htm";
                    questState.playSound("ItemSound.quest_finish");
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (n == 30031) {
                    if (questState.getQuestItemsCount(1178) == 0L && questState.getQuestItemsCount(1271) > 0L && questState.getQuestItemsCount(1167) == 0L) {
                        string = "quilt_q0402_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1178) > 0L) {
                        if (questState.getQuestItemsCount(1179) < 10L) {
                            string = "quilt_q0402_03.htm";
                            break;
                        }
                        string = "quilt_q0402_04.htm";
                        questState.takeItems(1179, -1L);
                        questState.takeItems(1178, 1L);
                        questState.giveItems(1167, 1L);
                        break;
                    }
                    if (questState.getQuestItemsCount(1167) <= 0L) break;
                    string = "quilt_q0402_05.htm";
                    break;
                }
                if (n == 30037) {
                    if (questState.getQuestItemsCount(1174) == 0L && questState.getQuestItemsCount(1271) > 0L && questState.getQuestItemsCount(1165) == 0L) {
                        string = "levian_q0402_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1174) > 0L) {
                        if (questState.getQuestItemsCount(1175) < 20L) {
                            string = "levian_q0402_03.htm";
                            break;
                        }
                        string = "levian_q0402_04.htm";
                        questState.takeItems(1175, -1L);
                        questState.takeItems(1174, 1L);
                        questState.giveItems(1165, 1L);
                        break;
                    }
                    if (questState.getQuestItemsCount(1165) <= 0L) break;
                    string = "levian_q0402_05.htm";
                    break;
                }
                if (n == 30039) {
                    if (questState.getQuestItemsCount(1176) == 0L && questState.getQuestItemsCount(1271) > 0L && questState.getQuestItemsCount(1166) == 0L) {
                        string = "gilbert_q0402_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1176) > 0L) {
                        if (questState.getQuestItemsCount(1177) < 20L) {
                            string = "gilbert_q0402_03.htm";
                            break;
                        }
                        string = "gilbert_q0402_04.htm";
                        questState.takeItems(1177, -1L);
                        questState.takeItems(1176, 1L);
                        questState.giveItems(1166, 1L);
                        break;
                    }
                    if (questState.getQuestItemsCount(1166) <= 0L) break;
                    string = "gilbert_q0402_05.htm";
                    break;
                }
                if (n == 30289) {
                    if (questState.getQuestItemsCount(1170) == 0L && questState.getQuestItemsCount(1163) == 0L && questState.getQuestItemsCount(1271) > 0L) {
                        string = "bishop_raimund_q0402_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1170) > 0L) {
                        if (questState.getQuestItemsCount(1171) < 12L) {
                            string = "bishop_raimund_q0402_04.htm";
                            break;
                        }
                        string = "bishop_raimund_q0402_05.htm";
                        questState.takeItems(1171, -1L);
                        questState.takeItems(1170, 1L);
                        questState.giveItems(1163, 1L);
                        break;
                    }
                    if (questState.getQuestItemsCount(1163) <= 0L) break;
                    string = "bishop_raimund_q0402_06.htm";
                    break;
                }
                if (n == 30311) {
                    if (questState.getQuestItemsCount(1271) <= 0L) break;
                    string = "sir_collin_windawood_q0402_01.htm";
                    break;
                }
                if (n == 30332) {
                    if (questState.getQuestItemsCount(1168) == 0L && questState.getQuestItemsCount(1271) > 0L && questState.getQuestItemsCount(1162) == 0L) {
                        string = "captain_bathia_q0402_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1168) > 0L) {
                        if (questState.getQuestItemsCount(1169) < 10L) {
                            string = "captain_bathia_q0402_03.htm";
                            break;
                        }
                        string = "captain_bathia_q0402_04.htm";
                        questState.takeItems(1169, -1L);
                        questState.takeItems(1168, 1L);
                        questState.giveItems(1162, 1L);
                        break;
                    }
                    if (questState.getQuestItemsCount(1162) <= 0L) break;
                    string = "captain_bathia_q0402_05.htm";
                    break;
                }
                if (n == 30379) {
                    if (questState.getQuestItemsCount(1271) > 0L && questState.getQuestItemsCount(1164) == 0L && questState.getQuestItemsCount(1172) == 0L) {
                        string = "captain_bezique_q0402_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1172) > 0L) {
                        if (questState.getQuestItemsCount(1173) < 20L) {
                            string = "captain_bezique_q0402_03.htm";
                            break;
                        }
                        string = "captain_bezique_q0402_04.htm";
                        questState.takeItems(1173, -1L);
                        questState.takeItems(1172, 1L);
                        questState.giveItems(1164, 1L);
                        break;
                    }
                    if (questState.getQuestItemsCount(1164) <= 0L) break;
                    string = "captain_bezique_q0402_05.htm";
                    break;
                }
                if (n != 30653 || questState.getQuestItemsCount(1271) <= 0L) break;
                string = "sir_aron_tanford_q0402_01.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20775) {
            if (questState.getQuestItemsCount(1168) > 0L && questState.getQuestItemsCount(1169) < 10L) {
                questState.rollAndGive(1169, 1, 100.0);
                if (questState.getQuestItemsCount(1169) >= 10L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 27024) {
            if (questState.getQuestItemsCount(1170) > 0L && questState.getQuestItemsCount(1171) < 12L && Rnd.get((int)10) < 5) {
                questState.rollAndGive(1171, 1, 100.0);
                if (questState.getQuestItemsCount(1171) >= 12L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20038) {
            if (questState.getQuestItemsCount(1172) > 0L && questState.getQuestItemsCount(1173) < 20L) {
                questState.rollAndGive(1173, 1, 100.0);
                if (questState.getQuestItemsCount(1173) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20043) {
            if (questState.getQuestItemsCount(1172) > 0L && questState.getQuestItemsCount(1173) < 20L) {
                questState.rollAndGive(1173, 1, 100.0);
                if (questState.getQuestItemsCount(1173) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20050) {
            if (questState.getQuestItemsCount(1172) > 0L && questState.getQuestItemsCount(1173) < 20L) {
                questState.rollAndGive(1173, 1, 100.0);
                if (questState.getQuestItemsCount(1173) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20030) {
            if (questState.getQuestItemsCount(1174) > 0L && questState.getQuestItemsCount(1175) < 20L && Rnd.get((int)10) < 5) {
                questState.rollAndGive(1175, 1, 100.0);
                if (questState.getQuestItemsCount(1175) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20027) {
            if (questState.getQuestItemsCount(1174) > 0L && questState.getQuestItemsCount(1175) < 20L && Rnd.get((int)10) < 5) {
                questState.rollAndGive(1175, 1, 100.0);
                if (questState.getQuestItemsCount(1175) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20024) {
            if (questState.getQuestItemsCount(1174) > 0L && questState.getQuestItemsCount(1175) < 20L && Rnd.get((int)10) < 5) {
                questState.rollAndGive(1175, 1, 100.0);
                if (questState.getQuestItemsCount(1175) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20103) {
            if (questState.getQuestItemsCount(1176) > 0L && Rnd.get((int)10) < 4 && questState.getQuestItemsCount(1177) < 20L) {
                questState.rollAndGive(1177, 1, 100.0);
                if (questState.getQuestItemsCount(1177) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20106) {
            if (questState.getQuestItemsCount(1176) > 0L && Rnd.get((int)10) < 4 && questState.getQuestItemsCount(1177) < 20L) {
                questState.rollAndGive(1177, 1, 100.0);
                if (questState.getQuestItemsCount(1177) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20108) {
            if (questState.getQuestItemsCount(1176) > 0L && Rnd.get((int)10) < 4 && questState.getQuestItemsCount(1177) < 20L) {
                questState.rollAndGive(1177, 1, 100.0);
                if (questState.getQuestItemsCount(1177) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20404 && questState.getQuestItemsCount(1178) > 0L && questState.getQuestItemsCount(1179) < 10L && Rnd.get((int)10) < 4) {
            questState.rollAndGive(1179, 1, 100.0);
            if (questState.getQuestItemsCount(1179) >= 10L) {
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
