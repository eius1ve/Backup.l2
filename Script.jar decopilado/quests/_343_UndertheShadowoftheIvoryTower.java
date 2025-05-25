/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.ClassId
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _343_UndertheShadowoftheIvoryTower
extends Quest
implements ScriptFile {
    private static final int aIm = 30834;
    private static final int aIn = 30835;
    private static final int aIo = 30934;
    private static final int aIp = 30935;
    private static final int aIq = 20563;
    private static final int aIr = 20564;
    private static final int aIs = 20565;
    private static final int aIt = 20566;
    private static final int aIu = 4364;
    private static final int aIv = 4365;
    private static final String hj = "@param1";
    private static final String hk = "@param2";

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _343_UndertheShadowoftheIvoryTower() {
        super(0);
        this.addStartNpc(30834);
        this.addTalkId(new int[]{30835, 30934, 30935});
        this.addKillId(new int[]{20563, 20564, 20565, 20566});
        this.addQuestItem(new int[]{4364, 4365});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("shadow_of_the_ivory_tower");
        int n3 = questState.getInt("shadow_of_the_ivory_tower_ex");
        if (n == 30834) {
            if (string.equalsIgnoreCase("quest_accept") && _343_UndertheShadowoftheIvoryTower.w(questState.getPlayer()) && questState.getPlayer().getLevel() >= 40) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                questState.set("shadow_of_the_ivory_tower", String.valueOf(1), true);
                questState.set("ex_shadow_of_the_ivory_tower", String.valueOf(1), true);
                string2 = "magic_trader_cema_q0343_05.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                if (_343_UndertheShadowoftheIvoryTower.w(questState.getPlayer()) && questState.getPlayer().getLevel() >= 40) {
                    string2 = "magic_trader_cema_q0343_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                if (questState.getQuestItemsCount(4364) >= 1L) {
                    questState.giveItems(57, questState.getQuestItemsCount(4364) * 120L);
                    questState.takeItems(4364, -1L);
                    string2 = "magic_trader_cema_q0343_08.htm";
                } else {
                    string2 = "magic_trader_cema_q0343_08a.htm";
                }
            } else if (string.equalsIgnoreCase("reply_3")) {
                string2 = "magic_trader_cema_q0343_09.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                string2 = "magic_trader_cema_q0343_10.htm";
            } else if (string.equalsIgnoreCase("reply_5")) {
                string2 = "magic_trader_cema_q0343_11.htm";
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
            } else if (string.equalsIgnoreCase("reply_6")) {
                if (questState.getQuestItemsCount(4364) >= 10L) {
                    questState.giveItems(734, 1L);
                    questState.takeItems(4364, 10L);
                    string2 = "magic_trader_cema_q0343_12.htm";
                } else {
                    string2 = "magic_trader_cema_q0343_19.htm";
                }
            } else if (string.equalsIgnoreCase("reply_7")) {
                if (questState.getQuestItemsCount(4364) >= 15L) {
                    questState.giveItems(737, 1L);
                    questState.takeItems(4364, 15L);
                    string2 = "magic_trader_cema_q0343_13.htm";
                } else {
                    string2 = "magic_trader_cema_q0343_19.htm";
                }
            } else if (string.equalsIgnoreCase("reply_8")) {
                if (questState.getQuestItemsCount(4364) >= 20L) {
                    questState.giveItems(735, 1L);
                    questState.takeItems(4364, 20L);
                    string2 = "magic_trader_cema_q0343_14.htm";
                } else {
                    string2 = "magic_trader_cema_q0343_19.htm";
                }
            } else if (string.equalsIgnoreCase("reply_9")) {
                if (questState.getQuestItemsCount(4364) >= 20L) {
                    questState.giveItems(2508, 10L);
                    questState.takeItems(4364, 20L);
                    string2 = "magic_trader_cema_q0343_15.htm";
                } else {
                    string2 = "magic_trader_cema_q0343_19.htm";
                }
            } else if (string.equalsIgnoreCase("reply_10")) {
                if (questState.getQuestItemsCount(4364) >= 100L) {
                    questState.giveItems(956, 1L);
                    questState.takeItems(4364, 100L);
                    string2 = "magic_trader_cema_q0343_16.htm";
                } else {
                    string2 = "magic_trader_cema_q0343_19.htm";
                }
            } else if (string.equalsIgnoreCase("reply_11")) {
                if (questState.getQuestItemsCount(4364) >= 400L) {
                    questState.giveItems(952, 1L);
                    questState.takeItems(4364, 400L);
                    string2 = "magic_trader_cema_q0343_17.htm";
                } else {
                    string2 = "magic_trader_cema_q0343_19.htm";
                }
            } else if (string.equalsIgnoreCase("reply_12")) {
                if (questState.getQuestItemsCount(4364) >= 200L) {
                    questState.giveItems(4365, 1L);
                    questState.takeItems(4364, 200L);
                    string2 = "magic_trader_cema_q0343_18.htm";
                } else {
                    string2 = "magic_trader_cema_q0343_19.htm";
                }
            }
        } else if (n == 30835) {
            if (string.equalsIgnoreCase("reply_1")) {
                if (questState.getQuestItemsCount(4365) < 1L) {
                    string2 = "lich_king_icarus_q0343_02.htm";
                } else if (questState.getQuestItemsCount(4365) >= 1L) {
                    int n4 = Rnd.get((int)1000);
                    if (n4 <= 119) {
                        questState.giveItems(955, 1L);
                    } else if (n4 <= 169) {
                        questState.giveItems(951, 1L);
                    } else if (n4 <= 329) {
                        questState.giveItems(2511, (long)(Rnd.get((int)200) + 401));
                    } else if (n4 <= 559) {
                        questState.giveItems(2510, (long)(Rnd.get((int)200) + 401));
                    } else if (n4 <= 561) {
                        questState.giveItems(316, 1L);
                    } else if (n4 <= 578) {
                        questState.giveItems(630, 1L);
                    } else if (n4 <= 579) {
                        questState.giveItems(188, 1L);
                    } else if (n4 <= 581) {
                        questState.giveItems(885, 1L);
                    } else if (n4 <= 582) {
                        questState.giveItems(103, 1L);
                    } else if (n4 <= 584) {
                        questState.giveItems(917, 1L);
                    } else {
                        questState.giveItems(736, 1L);
                    }
                    questState.takeItems(4365, 1L);
                    string2 = "lich_king_icarus_q0343_03.htm";
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "lich_king_icarus_q0343_04.htm";
            }
        } else if (n == 30934) {
            if (_343_UndertheShadowoftheIvoryTower.w(questState.getPlayer())) {
                if (string.equalsIgnoreCase("reply_1")) {
                    if (n2 == 1) {
                        if (n3 >= 25) {
                            string2 = "collector_masha_q0343_05.htm";
                        } else if (n3 >= 1 && n3 < 25 && questState.getQuestItemsCount(4364) < 10L) {
                            string2 = "collector_masha_q0343_06.htm";
                        } else if (n3 >= 1 && n3 < 25 && questState.getQuestItemsCount(4364) >= 10L) {
                            string2 = "collector_masha_q0343_07.htm";
                            questState.takeItems(4364, 10L);
                            questState.set("shadow_of_the_ivory_tower", String.valueOf(2), true);
                        }
                    }
                } else if (string.equalsIgnoreCase("reply_2")) {
                    if (n2 == 2) {
                        int n5 = Rnd.get((int)100);
                        int n6 = Rnd.get((int)3);
                        if (n5 < 20 && n6 == 0) {
                            questState.set("shadow_of_the_ivory_tower_ex", n3 + 4);
                            questState.set(hj, 0);
                            string2 = "collector_masha_q0343_08a.htm";
                        } else if (n5 < 20 && n6 == 1) {
                            questState.set("shadow_of_the_ivory_tower_ex", n3 + 4);
                            questState.set(hj, 1);
                            string2 = "collector_masha_q0343_08b.htm";
                        } else if (n5 < 20 && n6 == 2) {
                            questState.set("shadow_of_the_ivory_tower_ex", n3 + 4);
                            questState.set(hj, 2);
                            string2 = "collector_masha_q0343_08c.htm";
                        } else if (n5 >= 20 && n5 < 50 && n6 == 0) {
                            questState.set("shadow_of_the_ivory_tower_ex", n3 + 4);
                            if (Rnd.get((int)2) == 0) {
                                questState.set(hj, 0);
                            } else {
                                questState.set(hj, 1);
                            }
                            string2 = "collector_masha_q0343_09a.htm";
                        } else if (n5 >= 20 && n5 < 50 && n6 == 1) {
                            questState.set("shadow_of_the_ivory_tower_ex", n3 + 4);
                            if (Rnd.get((int)2) == 0) {
                                questState.set(hj, 1);
                            } else {
                                questState.set(hj, 2);
                            }
                            string2 = "collector_masha_q0343_09b.htm";
                        } else if (n5 >= 20 && n5 < 50 && n6 == 2) {
                            questState.set("shadow_of_the_ivory_tower_ex", n3 + 4);
                            if (Rnd.get((int)2) == 0) {
                                questState.set(hj, 2);
                            } else {
                                questState.set(hj, 0);
                            }
                            string2 = "collector_masha_q0343_09c.htm";
                        } else {
                            questState.set(hj, Rnd.get((int)3));
                            string2 = "collector_masha_q0343_10.htm";
                        }
                    }
                } else if (string.equalsIgnoreCase("reply_3")) {
                    if (n2 == 2) {
                        if (questState.getInt(hj) == 0) {
                            questState.giveItems(4364, 10L);
                            string2 = "collector_masha_q0343_11a.htm";
                            questState.set(hj, 4);
                        } else if (questState.getInt(hj) == 1) {
                            string2 = "collector_masha_q0343_11b.htm";
                        } else if (questState.getInt(hj) == 2) {
                            questState.giveItems(4364, 20L);
                            string2 = "collector_masha_q0343_11c.htm";
                            questState.set(hj, 4);
                        }
                        questState.set("shadow_of_the_ivory_tower", String.valueOf(1), true);
                    }
                } else if (string.equalsIgnoreCase("reply_4")) {
                    if (n2 == 2) {
                        if (questState.getInt(hj) == 0) {
                            questState.giveItems(4364, 20L);
                            string2 = "collector_masha_q0343_12a.htm";
                            questState.set(hj, 4);
                        } else if (questState.getInt(hj) == 1) {
                            questState.giveItems(4364, 10L);
                            string2 = "collector_masha_q0343_12b.htm";
                            questState.set(hj, 4);
                        } else if (questState.getInt(hj) == 2) {
                            string2 = "collector_masha_q0343_12c.htm";
                        }
                        questState.set("shadow_of_the_ivory_tower", String.valueOf(1), true);
                    }
                } else if (string.equalsIgnoreCase("reply_5") && n2 == 2) {
                    if (questState.getInt(hj) == 0) {
                        string2 = "collector_masha_q0343_13a.htm";
                    } else if (questState.getInt(hj) == 1) {
                        questState.giveItems(4364, 20L);
                        string2 = "collector_masha_q0343_13b.htm";
                        questState.set(hj, 4);
                    } else if (questState.getInt(hj) == 2) {
                        questState.giveItems(4364, 10L);
                        string2 = "collector_masha_q0343_13c.htm";
                        questState.set(hj, 4);
                    }
                    questState.set("shadow_of_the_ivory_tower", String.valueOf(1), true);
                }
            }
        } else if (n == 30935 && _343_UndertheShadowoftheIvoryTower.w(questState.getPlayer())) {
            if (string.equalsIgnoreCase("reply_1")) {
                if (questState.getQuestItemsCount(4364) < 10L) {
                    string2 = "collector_trumpin_q0343_03.htm";
                } else if (questState.getQuestItemsCount(4364) >= 10L) {
                    questState.set(hk, Rnd.get((int)2));
                    string2 = "collector_trumpin_q0343_04.htm";
                }
            }
            if (string.equalsIgnoreCase("reply_2")) {
                if (questState.getInt(hk) == 0 && questState.getInt(hj) == 0 && questState.getQuestItemsCount(4364) >= 10L) {
                    questState.set(hj, 1);
                    questState.set(hk, 2);
                    string2 = "collector_trumpin_q0343_05.htm";
                } else if (questState.getInt(hk) == 0 && questState.getInt(hj) == 1 && questState.getQuestItemsCount(4364) >= 10L) {
                    questState.set(hj, 2);
                    questState.set(hk, 2);
                    string2 = "collector_trumpin_q0343_05a.htm";
                } else if (questState.getInt(hk) == 0 && questState.getInt(hj) == 2 && questState.getQuestItemsCount(4364) >= 10L) {
                    questState.set(hj, 3);
                    questState.set(hk, 2);
                    string2 = "collector_trumpin_q0343_05b.htm";
                } else if (questState.getInt(hk) == 0 && questState.getInt(hj) == 3 && questState.getQuestItemsCount(4364) >= 10L) {
                    questState.set(hj, 4);
                    questState.set(hk, 2);
                    string2 = "collector_trumpin_q0343_05c.htm";
                } else if (questState.getInt(hk) == 0 && questState.getInt(hj) == 4 && questState.getQuestItemsCount(4364) >= 10L) {
                    questState.giveItems(4364, 310L);
                    string2 = "collector_trumpin_q0343_05d.htm";
                    questState.set(hj, 0);
                    questState.set(hk, 2);
                } else if (questState.getInt(hk) == 1 && questState.getQuestItemsCount(4364) >= 10L) {
                    questState.takeItems(4364, 10L);
                    string2 = "collector_trumpin_q0343_06.htm";
                    questState.set(hj, 0);
                    questState.set(hk, 2);
                } else if (questState.getQuestItemsCount(4364) < 10L) {
                    string2 = "collector_trumpin_q0343_03.htm";
                }
            }
            if (string.equalsIgnoreCase("reply_3")) {
                if (questState.getInt(hk) == 0 && questState.getQuestItemsCount(4364) >= 10L) {
                    questState.takeItems(4364, 10L);
                    string2 = "collector_trumpin_q0343_07.htm";
                    questState.set(hj, 0);
                    questState.set(hk, 2);
                } else if (questState.getInt(hk) == 1 && questState.getInt(hj) == 0 && questState.getQuestItemsCount(4364) >= 10L) {
                    questState.set(hj, 1);
                    questState.set(hk, 2);
                    string2 = "collector_trumpin_q0343_08.htm";
                } else if (questState.getInt(hk) == 1 && questState.getInt(hj) == 1 && questState.getQuestItemsCount(4364) >= 10L) {
                    questState.set(hj, 2);
                    questState.set(hk, 2);
                    string2 = "collector_trumpin_q0343_08a.htm";
                } else if (questState.getInt(hk) == 1 && questState.getInt(hj) == 2 && questState.getQuestItemsCount(4364) >= 10L) {
                    questState.set(hj, 3);
                    questState.set(hk, 2);
                    string2 = "collector_trumpin_q0343_08b.htm";
                } else if (questState.getInt(hk) == 1 && questState.getInt(hj) == 3 && questState.getQuestItemsCount(4364) >= 10L) {
                    questState.set(hj, 4);
                    questState.set(hk, 2);
                    string2 = "collector_trumpin_q0343_08c.htm";
                } else if (questState.getInt(hk) == 1 && questState.getInt(hj) == 4 && questState.getQuestItemsCount(4364) >= 10L) {
                    questState.giveItems(4364, 310L);
                    string2 = "collector_trumpin_q0343_08d.htm";
                    questState.set(hj, 0);
                    questState.set(hk, 2);
                } else if (questState.getQuestItemsCount(4364) < 10L) {
                    string2 = "collector_trumpin_q0343_03.htm";
                }
            }
            if (string.equalsIgnoreCase("reply_4")) {
                if (questState.getInt(hj) == 1) {
                    questState.giveItems(4364, 10L);
                    string2 = "collector_trumpin_q0343_09.htm";
                    questState.set(hj, 0);
                    questState.set(hk, 2);
                } else if (questState.getInt(hj) == 2) {
                    questState.giveItems(4364, 30L);
                    string2 = "collector_trumpin_q0343_09a.htm";
                    questState.set(hj, 0);
                    questState.set(hk, 2);
                } else if (questState.getInt(hj) == 3) {
                    questState.giveItems(4364, 70L);
                    string2 = "collector_trumpin_q0343_09b.htm";
                    questState.set(hj, 0);
                    questState.set(hk, 2);
                } else if (questState.getInt(hj) == 4) {
                    questState.giveItems(4364, 150L);
                    string2 = "collector_trumpin_q0343_09c.htm";
                    questState.set(hj, 0);
                    questState.set(hk, 2);
                }
            }
            if (string.equalsIgnoreCase("reply_5")) {
                questState.set(hj, 0);
                questState.set(hk, 2);
                string2 = "collector_trumpin_q0343_10.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("shadow_of_the_ivory_tower_ex");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30834) break;
                if (!_343_UndertheShadowoftheIvoryTower.w(questState.getPlayer())) {
                    string = "magic_trader_cema_q0343_01.htm";
                    break;
                }
                if (_343_UndertheShadowoftheIvoryTower.w(questState.getPlayer()) && questState.getPlayer().getLevel() < 40) {
                    string = "magic_trader_cema_q0343_02.htm";
                    break;
                }
                if (!_343_UndertheShadowoftheIvoryTower.w(questState.getPlayer()) || questState.getPlayer().getLevel() < 40) break;
                string = "magic_trader_cema_q0343_03.htm";
                break;
            }
            case 2: {
                if (n2 == 30834) {
                    if (questState.getQuestItemsCount(4364) < 1L && _343_UndertheShadowoftheIvoryTower.w(questState.getPlayer())) {
                        string = "magic_trader_cema_q0343_06.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(4364) < 1L || !_343_UndertheShadowoftheIvoryTower.w(questState.getPlayer())) break;
                    string = "magic_trader_cema_q0343_07.htm";
                    break;
                }
                if (n2 == 30835) {
                    if (!_343_UndertheShadowoftheIvoryTower.w(questState.getPlayer())) break;
                    string = "lich_king_icarus_q0343_01.htm";
                    break;
                }
                if (n2 == 30934) {
                    if (n == 0 && _343_UndertheShadowoftheIvoryTower.w(questState.getPlayer())) {
                        questState.set("shadow_of_the_ivory_tower_ex", 1);
                        string = "collector_masha_q0343_03.htm";
                        break;
                    }
                    if (n < 1 || !_343_UndertheShadowoftheIvoryTower.w(questState.getPlayer())) break;
                    string = "collector_masha_q0343_04.htm";
                    questState.set("shadow_of_the_ivory_tower", 1);
                    break;
                }
                if (n2 != 30935 || !_343_UndertheShadowoftheIvoryTower.w(questState.getPlayer())) break;
                questState.set(hj, 0);
                string = "collector_trumpin_q0343_01.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("shadow_of_the_ivory_tower_ex");
        if (n == 20563 || n == 20564 || n == 20565) {
            if (_343_UndertheShadowoftheIvoryTower.w(questState.getPlayer())) {
                if (Rnd.get((int)100) < 63) {
                    questState.rollAndGive(4364, 1, 100.0);
                }
                if (n2 > 1 && Rnd.get((int)100) <= 12) {
                    questState.set("shadow_of_the_ivory_tower_ex", n2 - 1);
                }
            }
        } else if (n == 20566 && _343_UndertheShadowoftheIvoryTower.w(questState.getPlayer())) {
            if (Rnd.get((int)100) < 68) {
                questState.rollAndGive(4364, 1, 100.0);
            }
            if (n2 > 1 && Rnd.get((int)100) <= 13) {
                questState.set("shadow_of_the_ivory_tower_ex", n2 - 1);
            }
        }
        return null;
    }

    private static boolean w(Player player) {
        return player.getClassId() == ClassId.mage || player.getClassId() == ClassId.wizard || player.getClassId() == ClassId.sorcerer || player.getClassId() == ClassId.necromancer || player.getClassId() == ClassId.warlock || player.getClassId() == ClassId.elven_mage || player.getClassId() == ClassId.elven_wizard || player.getClassId() == ClassId.spellsinger || player.getClassId() == ClassId.elemental_summoner || player.getClassId() == ClassId.dark_mage || player.getClassId() == ClassId.dark_wizard || player.getClassId() == ClassId.spellhowler || player.getClassId() == ClassId.phantom_summoner || player.getClassId() == ClassId.archmage || player.getClassId() == ClassId.soultaker || player.getClassId() == ClassId.arcana_lord || player.getClassId() == ClassId.mystic_muse || player.getClassId() == ClassId.elemental_master || player.getClassId() == ClassId.storm_screamer || player.getClassId() == ClassId.spectral_master || player.getClassId() == ClassId.orc_mage || player.getClassId() == ClassId.orc_shaman || player.getClassId() == ClassId.overlord || player.getClassId() == ClassId.warcryer || player.getClassId() == ClassId.dominator || player.getClassId() == ClassId.doomcryer;
    }
}
