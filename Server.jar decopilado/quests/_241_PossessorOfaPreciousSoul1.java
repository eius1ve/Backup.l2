/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.ScriptFile;

public class _241_PossessorOfaPreciousSoul1
extends Quest
implements ScriptFile {
    private static final int arn = 31740;
    private static final int aro = 30753;
    private static final int arp = 31336;
    private static final int arq = 31743;
    private static final int arr = 30692;
    private static final int ars = 31042;
    private static final int art = 31744;
    private static final int aru = 31739;
    private static final int arv = 31742;
    private static final int arw = 30754;
    private static final int arx = 31272;
    private static final int ary = 20244;
    private static final int arz = 20245;
    private static final int arA = 20283;
    private static final int arB = 20284;
    private static final int arC = 21508;
    private static final int arD = 21509;
    private static final int arE = 21510;
    private static final int arF = 21511;
    private static final int arG = 27113;
    private static final int arH = 7587;
    private static final int arI = 7597;
    private static final int arJ = 7589;
    private static final int arK = 7588;
    private static final int arL = 7598;
    private static final int arM = 7599;
    private static final int arN = 6029;
    private static final int arO = 6033;
    private static final int arP = 7677;

    public _241_PossessorOfaPreciousSoul1() {
        super(0);
        this.addStartNpc(31739);
        this.addTalkId(new int[]{30753, 30754, 30692, 31042, 31742, 31744, 31336, 31743, 31740, 31272});
        this.addKillId(new int[]{20244, 20245, 20283, 20284, 21508, 21509, 21510, 21511, 27113});
        this.addQuestItem(new int[]{7587, 7597, 7589, 7588, 7598, 7599});
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        Player player = questState.getPlayer();
        int n = questState.getInt("noble_soul_noblesse_1_cookie");
        int n2 = npcInstance.getNpcId();
        if (n2 == 31739) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("noble_soul_noblesse_1", String.valueOf(11), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "talien_q0241_0104.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 3) {
                if (questState.getQuestItemsCount(7587) >= 1L) {
                    questState.setCond(5);
                    questState.set("noble_soul_noblesse_1", String.valueOf(41), true);
                    questState.takeItems(7587, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "talien_q0241_0401.htm";
                } else {
                    string2 = "talien_q0241_0402.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 6) {
                if (questState.getQuestItemsCount(7589) >= 1L) {
                    questState.setCond(9);
                    questState.set("noble_soul_noblesse_1", String.valueOf(71), true);
                    questState.takeItems(7589, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "talien_q0241_0701.htm";
                } else {
                    string2 = "talien_q0241_0702.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 8) {
                if (questState.getQuestItemsCount(7588) >= 1L) {
                    questState.setCond(11);
                    questState.set("noble_soul_noblesse_1", String.valueOf(91), true);
                    questState.takeItems(7588, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "talien_q0241_0901.htm";
                } else {
                    string2 = "talien_q0241_0902.htm";
                }
            }
        } else if (n2 == 30753) {
            if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 1) {
                questState.setCond(2);
                questState.set("noble_soul_noblesse_1", String.valueOf(21), true);
                questState.playSound("ItemSound.quest_middle");
                string2 = "gabrielle_q0241_0201.htm";
            }
        } else if (n2 == 30754) {
            if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 2) {
                questState.setCond(3);
                questState.set("noble_soul_noblesse_1", String.valueOf(31), true);
                questState.playSound("ItemSound.quest_middle");
                string2 = "watcher_antaras_gilmore_q0241_0301.htm";
            }
        } else if (n2 == 30692) {
            if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 7) {
                questState.setCond(10);
                questState.set("noble_soul_noblesse_1", String.valueOf(81), true);
                questState.giveItems(7588, 1L);
                questState.playSound("ItemSound.quest_middle");
                string2 = "master_stedmiel_q0241_0801.htm";
            }
        } else if (n2 == 31042) {
            if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 4) {
                questState.setCond(6);
                questState.set("noble_soul_noblesse_1", String.valueOf(51), true);
                questState.playSound("ItemSound.quest_middle");
                string2 = "muzyk_q0241_0501.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 5) {
                if (questState.getQuestItemsCount(7597) >= 10L) {
                    questState.setCond(8);
                    questState.set("noble_soul_noblesse_1", String.valueOf(61), true);
                    questState.takeItems(7597, 10L);
                    questState.giveItems(7589, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "muzyk_q0241_0601.htm";
                } else {
                    string2 = "muzyk_q0241_0602.htm";
                }
            }
        } else if (n2 == 31742) {
            if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 9) {
                questState.setCond(12);
                questState.set("noble_soul_noblesse_1", String.valueOf(101), true);
                questState.playSound("ItemSound.quest_middle");
                string2 = "virgil_q0241_1001.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 14) {
                questState.setCond(18);
                questState.set("noble_soul_noblesse_1", String.valueOf(151), true);
                questState.playSound("ItemSound.quest_middle");
                string2 = "virgil_q0241_1501.htm";
            }
        } else if (n2 == 31744) {
            if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 10) {
                questState.setCond(13);
                questState.set("noble_soul_noblesse_1", String.valueOf(111), true);
                questState.playSound("ItemSound.quest_middle");
                string2 = "ogmar_q0241_1101.htm";
            }
        } else if (n2 == 31336) {
            if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 11) {
                questState.setCond(14);
                questState.set("noble_soul_noblesse_1", String.valueOf(121), true);
                questState.playSound("ItemSound.quest_middle");
                string2 = "highseer_rahorakti_q0241_1201.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 12) {
                if (questState.getQuestItemsCount(7598) >= 5L) {
                    questState.setCond(16);
                    questState.set("noble_soul_noblesse_1", String.valueOf(131), true);
                    questState.takeItems(7598, 5L);
                    questState.giveItems(7599, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "highseer_rahorakti_q0241_1301.htm";
                } else {
                    string2 = "highseer_rahorakti_q0241_1302.htm";
                }
            }
        } else if (n2 == 31743) {
            if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 13) {
                if (questState.getQuestItemsCount(7599) >= 1L) {
                    questState.setCond(17);
                    questState.set("noble_soul_noblesse_1", String.valueOf(141), true);
                    questState.takeItems(7599, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "kasandra_q0241_1401.htm";
                } else {
                    string2 = "kasandra_q0241_1402.htm";
                }
            }
        } else if (n2 == 31272) {
            if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 16) {
                questState.setCond(20);
                questState.set("noble_soul_noblesse_1", String.valueOf(171), true);
                string2 = "head_blacksmith_noel_q0241_1701.htm";
                questState.playSound("ItemSound.quest_middle");
            } else if (string.equalsIgnoreCase("menu_select?ask=241&reply=1") && n == 17) {
                if (questState.getQuestItemsCount(6029) >= 5L && questState.getQuestItemsCount(6033) >= 1L) {
                    questState.takeItems(6029, 5L);
                    questState.takeItems(6033, 1L);
                    questState.setCond(21);
                    questState.set("noble_soul_noblesse_1", String.valueOf(181), true);
                    string2 = "head_blacksmith_noel_q0241_1801.htm";
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    string2 = "head_blacksmith_noel_q0241_1802.htm";
                }
            }
        } else if (n2 == 31740) {
            if (string.equalsIgnoreCase("menu_select?ask=241&reply=3") && n == 15) {
                questState.setCond(19);
                questState.set("noble_soul_noblesse_1", String.valueOf(161), true);
                string2 = "caradine_q0241_1601.htm";
                questState.playSound("ItemSound.quest_middle");
            } else if (string.equalsIgnoreCase("menu_select?ask=241&reply=3") && n == 18) {
                questState.giveItems(7677, 1L);
                player.sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(false);
                string2 = "caradine_q0241_1901.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        if (!questState.getPlayer().isSubClassActive()) {
            return "quest_not_subclass001.htm";
        }
        String string = "no-quest";
        int n = questState.getInt("noble_soul_noblesse_1");
        boolean bl = questState.getPlayer().isSubClassActive();
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 31739) break;
                if (questState.getPlayer().getLevel() < 50) {
                    string = "talien_q0241_0103.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (bl) {
                    string = "talien_q0241_0101.htm";
                    break;
                }
                string = "talien_q0241_0102.htm";
                break;
            }
            case 2: {
                if (n2 == 31739) {
                    if (n == 11) {
                        string = "talien_q0241_0105.htm";
                        break;
                    }
                    if (n == 32) {
                        if (questState.getQuestItemsCount(7587) >= 1L) {
                            questState.set("noble_soul_noblesse_1_cookie", String.valueOf(3), true);
                            string = "talien_q0241_0301.htm";
                            break;
                        }
                        string = "talien_q0241_0302.htm";
                        break;
                    }
                    if (n == 41) {
                        string = "talien_q0241_0403.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(7589) >= 1L && n == 61) {
                        questState.set("noble_soul_noblesse_1_cookie", String.valueOf(6), true);
                        string = "talien_q0241_0601.htm";
                        break;
                    }
                    if (n == 71) {
                        string = "talien_q0241_0703.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(7588) >= 1L && n == 81) {
                        questState.set("noble_soul_noblesse_1_cookie", String.valueOf(8), true);
                        string = "talien_q0241_0801.htm";
                        break;
                    }
                    if (n != 91) break;
                    string = "talien_q0241_0903.htm";
                    break;
                }
                if (n2 == 30753) {
                    if (n == 11) {
                        questState.set("noble_soul_noblesse_1_cookie", String.valueOf(1), true);
                        string = "gabrielle_q0241_0101.htm";
                        break;
                    }
                    if (n != 21) break;
                    string = "gabrielle_q0241_0202.htm";
                    break;
                }
                if (n2 == 30754) {
                    if (n == 21) {
                        questState.set("noble_soul_noblesse_1_cookie", String.valueOf(2), true);
                        string = "watcher_antaras_gilmore_q0241_0201.htm";
                        break;
                    }
                    if (n != 31) break;
                    string = "watcher_antaras_gilmore_q0241_0302.htm";
                    break;
                }
                if (n2 == 30692) {
                    if (n == 71) {
                        questState.set("noble_soul_noblesse_1_cookie", String.valueOf(7), true);
                        string = "master_stedmiel_q0241_0701.htm";
                        break;
                    }
                    if (n != 81) break;
                    string = "master_stedmiel_q0241_0802.htm";
                    break;
                }
                if (n2 == 31042) {
                    if (n == 41) {
                        questState.set("noble_soul_noblesse_1_cookie", String.valueOf(4), true);
                        string = "muzyk_q0241_0401.htm";
                        break;
                    }
                    if (n <= 52 && n >= 51) {
                        if (n == 52 && questState.getQuestItemsCount(7597) >= 10L) {
                            questState.set("noble_soul_noblesse_1_cookie", String.valueOf(5), true);
                            string = "muzyk_q0241_0502.htm";
                            break;
                        }
                        string = "muzyk_q0241_0503.htm";
                        break;
                    }
                    if (n != 61) break;
                    string = "muzyk_q0241_0603.htm";
                    break;
                }
                if (n2 == 31742) {
                    if (n == 91) {
                        questState.set("noble_soul_noblesse_1_cookie", String.valueOf(9), true);
                        string = "virgil_q0241_0901.htm";
                        break;
                    }
                    if (n == 101) {
                        string = "virgil_q0241_1002.htm";
                        break;
                    }
                    if (n == 141) {
                        questState.set("noble_soul_noblesse_1_cookie", String.valueOf(14), true);
                        string = "virgil_q0241_1401.htm";
                        break;
                    }
                    if (n != 151) break;
                    string = "virgil_q0241_1502.htm";
                    break;
                }
                if (n2 == 31744) {
                    if (n == 101) {
                        questState.set("noble_soul_noblesse_1_cookie", String.valueOf(10), true);
                        string = "ogmar_q0241_1001.htm";
                        break;
                    }
                    if (n != 111) break;
                    string = "ogmar_q0241_1102.htm";
                    break;
                }
                if (n2 == 31336) {
                    if (n == 111) {
                        questState.set("noble_soul_noblesse_1_cookie", String.valueOf(11), true);
                        string = "highseer_rahorakti_q0241_1101.htm";
                        break;
                    }
                    if (n <= 122 && n >= 121) {
                        if (n == 122 && questState.getQuestItemsCount(7598) >= 5L) {
                            questState.set("noble_soul_noblesse_1_cookie", String.valueOf(12), true);
                            string = "highseer_rahorakti_q0241_1202.htm";
                            break;
                        }
                        string = "highseer_rahorakti_q0241_1203.htm";
                        break;
                    }
                    if (n != 131) break;
                    string = "highseer_rahorakti_q0241_1303.htm";
                    break;
                }
                if (n2 == 31743) {
                    if (questState.getQuestItemsCount(7599) >= 1L && n == 131) {
                        questState.set("noble_soul_noblesse_1_cookie", String.valueOf(13), true);
                        string = "kasandra_q0241_1301.htm";
                        break;
                    }
                    if (n != 141) break;
                    string = "kasandra_q0241_1403.htm";
                    break;
                }
                if (n2 == 31272) {
                    if (n == 161) {
                        questState.set("noble_soul_noblesse_1_cookie", String.valueOf(16), true);
                        string = "head_blacksmith_noel_q0241_1601.htm";
                        break;
                    }
                    if (n == 171) {
                        if (questState.getQuestItemsCount(6029) >= 5L && questState.getQuestItemsCount(6033) >= 1L) {
                            questState.set("noble_soul_noblesse_1_cookie", String.valueOf(17), true);
                            string = "head_blacksmith_noel_q0241_1702.htm";
                            break;
                        }
                        string = "head_blacksmith_noel_q0241_1703.htm";
                        break;
                    }
                    if (n != 181) break;
                    string = "head_blacksmith_noel_q0241_1803.htm";
                    break;
                }
                if (n2 != 31740) break;
                if (n == 151) {
                    questState.set("noble_soul_noblesse_1_cookie", String.valueOf(15), true);
                    string = "caradine_q0241_1501.htm";
                }
                if (n == 161) {
                    string = "caradine_q0241_1602.htm";
                    break;
                }
                if (n != 181) break;
                questState.set("noble_soul_noblesse_1_cookie", String.valueOf(18), true);
                string = "caradine_q0241_1801.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n;
        if (!questState.getPlayer().isSubClassActive()) {
            return null;
        }
        int n2 = questState.getInt("noble_soul_noblesse_1");
        int n3 = npcInstance.getNpcId();
        if (n3 == 20244 || n3 == 20283) {
            if (n2 == 51) {
                questState.rollAndGive(7597, 1, 45.0);
                if (questState.getQuestItemsCount(7597) >= 10L) {
                    questState.setCond(7);
                    questState.set("noble_soul_noblesse_1", String.valueOf(52), true);
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n3 == 20245 || n3 == 20284) {
            if (n2 == 51) {
                questState.rollAndGive(7597, 1, 50.0);
                if (questState.getQuestItemsCount(7597) >= 10L) {
                    questState.setCond(7);
                    questState.set("noble_soul_noblesse_1", String.valueOf(52), true);
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n3 == 21508 || n3 == 21509 || n3 == 21510 || n3 == 21511) {
            if (n2 == 121) {
                questState.rollAndGive(7598, 1, 30.0);
                if (questState.getQuestItemsCount(7598) >= 5L) {
                    questState.setCond(15);
                    questState.set("noble_soul_noblesse_1", String.valueOf(122), true);
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n3 == 27113 && n2 == 31 && (n = Rnd.get((int)1000)) < 1000) {
            if (questState.getQuestItemsCount(7587) + 1L >= 1L) {
                if (questState.getQuestItemsCount(7587) < 1L) {
                    questState.setCond(4);
                    questState.set("noble_soul_noblesse_1", String.valueOf(32), true);
                    questState.giveItems(7587, 1L - questState.getQuestItemsCount(7587));
                    questState.playSound("ItemSound.quest_middle");
                }
            } else {
                questState.giveItems(7587, 1L);
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
