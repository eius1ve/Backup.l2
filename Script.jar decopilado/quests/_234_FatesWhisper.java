/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;

public class _234_FatesWhisper
extends Quest
implements ScriptFile {
    private static final int apv = 31002;
    private static final int apw = 30182;
    private static final int apx = 30847;
    private static final int apy = 30178;
    private static final int apz = 30833;
    private static final int apA = 31027;
    private static final int apB = 31028;
    private static final int apC = 31029;
    private static final int apD = 31030;
    private static final int apE = 29020;
    private static final int apF = 4665;
    private static final int apG = 4666;
    private static final int apH = 4667;
    private static final int apI = 4668;
    private static final int apJ = 4669;
    private static final int apK = 4670;
    private static final int apL = 4671;
    private static final int apM = 4672;
    private static final int apN = 4673;
    private static final int apO = 5011;
    private static final int apP = 1460;
    private static final int apQ = 79;
    private static final int apR = 4717;
    private static final int apS = 4718;
    private static final int apT = 4719;
    private static final int apU = 4828;
    private static final int apV = 4829;
    private static final int apW = 4830;
    private static final int apX = 287;
    private static final int apY = 4858;
    private static final int apZ = 4859;
    private static final int aqa = 4860;
    private static final int aqb = 97;
    private static final int aqc = 4753;
    private static final int aqd = 4754;
    private static final int aqe = 4755;
    private static final int aqf = 175;
    private static final int aqg = 4900;
    private static final int aqh = 4901;
    private static final int aqi = 4902;
    private static final int aqj = 210;
    private static final int aqk = 4780;
    private static final int aql = 4781;
    private static final int aqm = 4782;
    private static final int aqn = 234;
    private static final int aqo = 6359;
    private static final int aqp = 4804;
    private static final int aqq = 4805;
    private static final int aqr = 4806;
    private static final int aqs = 268;
    private static final int aqt = 4750;
    private static final int aqu = 4751;
    private static final int aqv = 4752;
    private static final int aqw = 171;
    private static final int aqx = 2626;
    private static final int aqy = 7883;
    private static final int aqz = 8105;
    private static final int aqA = 8106;
    private static final int aqB = 8107;
    private static final int aqC = 7889;
    private static final int aqD = 8117;
    private static final int aqE = 8118;
    private static final int aqF = 8119;
    private static final int aqG = 7901;
    private static final int aqH = 8132;
    private static final int aqI = 8133;
    private static final int aqJ = 8134;
    private static final int aqK = 7893;
    private static final int aqL = 8144;
    private static final int aqM = 8145;
    private static final int aqN = 8146;
    private static final int aqO = 80;
    private static final int aqP = 7884;
    private static final int aqQ = 288;
    private static final int aqR = 98;
    private static final int aqS = 150;
    private static final int aqT = 212;
    private static final int aqU = 7894;
    private static final int aqV = 235;
    private static final int aqW = 269;
    private static final int aqX = 2504;
    private static final int aqY = 7899;
    private static final int aqZ = 5233;

    public _234_FatesWhisper() {
        super(1);
        this.addStartNpc(31002);
        this.addTalkId(new int[]{30182, 30847, 30178, 30833, 31027, 31028, 31029, 31030});
        this.addAttackId(new int[]{29020});
        this.addQuestItem(new int[]{4666, 4669, 4667, 4668, 4672, 4670, 4671, 4665, 4673});
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    /*
     * Opcode count of 18516 triggered aggressive code reduction.  Override with --aggressivesizethreshold.
     */
    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2;
        block1205: {
            int n;
            block1243: {
                int n2;
                block1242: {
                    block1239: {
                        block1241: {
                            block1240: {
                                block1203: {
                                    block1238: {
                                        block1237: {
                                            block1236: {
                                                block1235: {
                                                    block1234: {
                                                        block1233: {
                                                            block1232: {
                                                                block1231: {
                                                                    block1230: {
                                                                        block1229: {
                                                                            block1228: {
                                                                                block1227: {
                                                                                    block1226: {
                                                                                        block1225: {
                                                                                            block1224: {
                                                                                                block1223: {
                                                                                                    block1222: {
                                                                                                        block1221: {
                                                                                                            block1220: {
                                                                                                                block1219: {
                                                                                                                    block1218: {
                                                                                                                        block1217: {
                                                                                                                            block1216: {
                                                                                                                                block1215: {
                                                                                                                                    block1214: {
                                                                                                                                        block1213: {
                                                                                                                                            block1212: {
                                                                                                                                                block1211: {
                                                                                                                                                    block1210: {
                                                                                                                                                        block1209: {
                                                                                                                                                            block1208: {
                                                                                                                                                                block1207: {
                                                                                                                                                                    block1206: {
                                                                                                                                                                        block1204: {
                                                                                                                                                                            string2 = string;
                                                                                                                                                                            n = questState.getInt("whispers_of_destiny");
                                                                                                                                                                            n2 = npcInstance.getNpcId();
                                                                                                                                                                            if (n2 != 31002) break block1203;
                                                                                                                                                                            if (!string.equalsIgnoreCase("quest_accept")) break block1204;
                                                                                                                                                                            questState.setCond(1);
                                                                                                                                                                            questState.set("whispers_of_destiny", String.valueOf(1), true);
                                                                                                                                                                            questState.setState(2);
                                                                                                                                                                            questState.playSound("ItemSound.quest_accept");
                                                                                                                                                                            string2 = "maestro_leorin_q0234_06.htm";
                                                                                                                                                                            break block1205;
                                                                                                                                                                        }
                                                                                                                                                                        if (!string.equalsIgnoreCase("menu_select?ask=234&reply=1")) break block1206;
                                                                                                                                                                        string2 = "maestro_leorin_q0234_02.htm";
                                                                                                                                                                        break block1205;
                                                                                                                                                                    }
                                                                                                                                                                    if (!string.equalsIgnoreCase("menu_select?ask=234&reply=2")) break block1207;
                                                                                                                                                                    string2 = "maestro_leorin_q0234_03.htm";
                                                                                                                                                                    break block1205;
                                                                                                                                                                }
                                                                                                                                                                if (!string.equalsIgnoreCase("menu_select?ask=234&reply=3")) break block1208;
                                                                                                                                                                string2 = "maestro_leorin_q0234_04.htm";
                                                                                                                                                                break block1205;
                                                                                                                                                            }
                                                                                                                                                            if (!string.equalsIgnoreCase("menu_select?ask=234&reply=4")) break block1209;
                                                                                                                                                            string2 = "maestro_leorin_q0234_05.htm";
                                                                                                                                                            break block1205;
                                                                                                                                                        }
                                                                                                                                                        if (!string.equalsIgnoreCase("menu_select?ask=234&reply=5")) break block1210;
                                                                                                                                                        if (n != 1 || questState.getQuestItemsCount(4666) != 1L) break block1205;
                                                                                                                                                        questState.setCond(2);
                                                                                                                                                        questState.set("whispers_of_destiny", String.valueOf(2), true);
                                                                                                                                                        questState.takeItems(4666, 1L);
                                                                                                                                                        questState.playSound("ItemSound.quest_middle");
                                                                                                                                                        string2 = "maestro_leorin_q0234_11.htm";
                                                                                                                                                        break block1205;
                                                                                                                                                    }
                                                                                                                                                    if (!string.equalsIgnoreCase("menu_select?ask=234&reply=6")) break block1211;
                                                                                                                                                    if (n != 2 || questState.getQuestItemsCount(4667) < 1L || questState.getQuestItemsCount(4668) < 1L || questState.getQuestItemsCount(4669) < 1L) break block1205;
                                                                                                                                                    questState.setCond(3);
                                                                                                                                                    questState.set("whispers_of_destiny", String.valueOf(4), true);
                                                                                                                                                    questState.takeItems(4667, -1L);
                                                                                                                                                    questState.takeItems(4668, -1L);
                                                                                                                                                    questState.takeItems(4669, -1L);
                                                                                                                                                    questState.playSound("ItemSound.quest_middle");
                                                                                                                                                    string2 = "maestro_leorin_q0234_14.htm";
                                                                                                                                                    break block1205;
                                                                                                                                                }
                                                                                                                                                if (!string.equalsIgnoreCase("menu_select?ask=234&reply=7")) break block1212;
                                                                                                                                                if (n != 4 || questState.getQuestItemsCount(4672) <= 0L) break block1205;
                                                                                                                                                questState.setCond(4);
                                                                                                                                                questState.set("whispers_of_destiny", String.valueOf(5), true);
                                                                                                                                                questState.takeItems(4672, 1L);
                                                                                                                                                questState.playSound("ItemSound.quest_middle");
                                                                                                                                                string2 = "maestro_leorin_q0234_17.htm";
                                                                                                                                                break block1205;
                                                                                                                                            }
                                                                                                                                            if (!string.equalsIgnoreCase("menu_select?ask=234&reply=8")) break block1213;
                                                                                                                                            if (n != 5 || questState.getQuestItemsCount(4670) <= 0L) break block1205;
                                                                                                                                            questState.setCond(5);
                                                                                                                                            questState.set("whispers_of_destiny", String.valueOf(6), true);
                                                                                                                                            questState.takeItems(4670, 1L);
                                                                                                                                            questState.playSound("ItemSound.quest_middle");
                                                                                                                                            string2 = "maestro_leorin_q0234_20.htm";
                                                                                                                                            break block1205;
                                                                                                                                        }
                                                                                                                                        if (!string.equalsIgnoreCase("menu_select?ask=234&reply=9")) break block1214;
                                                                                                                                        if (n != 9 || questState.getQuestItemsCount(4671) <= 0L) break block1205;
                                                                                                                                        questState.setCond(9);
                                                                                                                                        questState.set("whispers_of_destiny", String.valueOf(10), true);
                                                                                                                                        questState.takeItems(4671, 1L);
                                                                                                                                        questState.playSound("ItemSound.quest_middle");
                                                                                                                                        string2 = "maestro_leorin_q0234_23.htm";
                                                                                                                                        break block1205;
                                                                                                                                    }
                                                                                                                                    if (!string.equalsIgnoreCase("menu_select?ask=234&reply=10")) break block1215;
                                                                                                                                    if (n != 10) break block1205;
                                                                                                                                    if (questState.getQuestItemsCount(1460) >= 984L) {
                                                                                                                                        questState.setCond(10);
                                                                                                                                        questState.set("whispers_of_destiny", String.valueOf(11), true);
                                                                                                                                        questState.takeItems(1460, 984L);
                                                                                                                                        questState.playSound("ItemSound.quest_middle");
                                                                                                                                        string2 = "maestro_leorin_q0234_26.htm";
                                                                                                                                    } else {
                                                                                                                                        string2 = "maestro_leorin_q0234_34.htm";
                                                                                                                                    }
                                                                                                                                    break block1205;
                                                                                                                                }
                                                                                                                                if (!string.equalsIgnoreCase("menu_select?ask=234&reply=11")) break block1216;
                                                                                                                                if (n != 10) break block1205;
                                                                                                                                if (questState.getQuestItemsCount(1460) >= 984L) {
                                                                                                                                    questState.setCond(10);
                                                                                                                                    questState.set("whispers_of_destiny", String.valueOf(19), true);
                                                                                                                                    questState.takeItems(1460, 984L);
                                                                                                                                    questState.playSound("ItemSound.quest_middle");
                                                                                                                                    string2 = "maestro_leorin_q0234_26a.htm";
                                                                                                                                } else {
                                                                                                                                    string2 = "maestro_leorin_q0234_34.htm";
                                                                                                                                }
                                                                                                                                break block1205;
                                                                                                                            }
                                                                                                                            if (!string.equalsIgnoreCase("menu_select?ask=234&reply=12")) break block1217;
                                                                                                                            if (n != 10) break block1205;
                                                                                                                            if (questState.getQuestItemsCount(1460) >= 984L) {
                                                                                                                                questState.setCond(10);
                                                                                                                                questState.set("whispers_of_destiny", String.valueOf(12), true);
                                                                                                                                questState.takeItems(1460, 984L);
                                                                                                                                questState.playSound("ItemSound.quest_middle");
                                                                                                                                string2 = "maestro_leorin_q0234_27.htm";
                                                                                                                            } else {
                                                                                                                                string2 = "maestro_leorin_q0234_34.htm";
                                                                                                                            }
                                                                                                                            break block1205;
                                                                                                                        }
                                                                                                                        if (!string.equalsIgnoreCase("menu_select?ask=234&reply=13")) break block1218;
                                                                                                                        if (n != 10) break block1205;
                                                                                                                        if (questState.getQuestItemsCount(1460) >= 984L) {
                                                                                                                            questState.setCond(10);
                                                                                                                            questState.set("whispers_of_destiny", String.valueOf(13), true);
                                                                                                                            questState.takeItems(1460, 984L);
                                                                                                                            questState.playSound("ItemSound.quest_middle");
                                                                                                                            string2 = "maestro_leorin_q0234_28.htm";
                                                                                                                        } else {
                                                                                                                            string2 = "maestro_leorin_q0234_34.htm";
                                                                                                                        }
                                                                                                                        break block1205;
                                                                                                                    }
                                                                                                                    if (!string.equalsIgnoreCase("menu_select?ask=234&reply=14")) break block1219;
                                                                                                                    if (n != 10) break block1205;
                                                                                                                    if (questState.getQuestItemsCount(1460) >= 984L) {
                                                                                                                        questState.setCond(10);
                                                                                                                        questState.set("whispers_of_destiny", String.valueOf(14), true);
                                                                                                                        questState.takeItems(1460, 984L);
                                                                                                                        questState.playSound("ItemSound.quest_middle");
                                                                                                                        string2 = "maestro_leorin_q0234_29.htm";
                                                                                                                    } else {
                                                                                                                        string2 = "maestro_leorin_q0234_34.htm";
                                                                                                                    }
                                                                                                                    break block1205;
                                                                                                                }
                                                                                                                if (!string.equalsIgnoreCase("menu_select?ask=234&reply=15")) break block1220;
                                                                                                                if (n != 10) break block1205;
                                                                                                                if (questState.getQuestItemsCount(1460) >= 984L) {
                                                                                                                    questState.setCond(10);
                                                                                                                    questState.set("whispers_of_destiny", String.valueOf(15), true);
                                                                                                                    questState.takeItems(1460, 984L);
                                                                                                                    questState.playSound("ItemSound.quest_middle");
                                                                                                                    string2 = "maestro_leorin_q0234_30.htm";
                                                                                                                } else {
                                                                                                                    string2 = "maestro_leorin_q0234_34.htm";
                                                                                                                }
                                                                                                                break block1205;
                                                                                                            }
                                                                                                            if (!string.equalsIgnoreCase("menu_select?ask=234&reply=16")) break block1221;
                                                                                                            if (n != 10) break block1205;
                                                                                                            if (questState.getQuestItemsCount(1460) >= 984L) {
                                                                                                                questState.setCond(10);
                                                                                                                questState.set("whispers_of_destiny", String.valueOf(16), true);
                                                                                                                questState.takeItems(1460, 984L);
                                                                                                                questState.playSound("ItemSound.quest_middle");
                                                                                                                string2 = "maestro_leorin_q0234_31.htm";
                                                                                                            } else {
                                                                                                                string2 = "maestro_leorin_q0234_34.htm";
                                                                                                            }
                                                                                                            break block1205;
                                                                                                        }
                                                                                                        if (!string.equalsIgnoreCase("menu_select?ask=234&reply=17")) break block1222;
                                                                                                        if (n != 10) break block1205;
                                                                                                        if (questState.getQuestItemsCount(1460) >= 984L) {
                                                                                                            questState.setCond(10);
                                                                                                            questState.set("whispers_of_destiny", String.valueOf(17), true);
                                                                                                            questState.takeItems(1460, 984L);
                                                                                                            questState.playSound("ItemSound.quest_middle");
                                                                                                            string2 = "maestro_leorin_q0234_32.htm";
                                                                                                        } else {
                                                                                                            string2 = "maestro_leorin_q0234_34.htm";
                                                                                                        }
                                                                                                        break block1205;
                                                                                                    }
                                                                                                    if (!string.equalsIgnoreCase("menu_select?ask=234&reply=18")) break block1223;
                                                                                                    if (n != 10) break block1205;
                                                                                                    if (questState.getQuestItemsCount(1460) >= 984L) {
                                                                                                        questState.setCond(10);
                                                                                                        questState.set("whispers_of_destiny", String.valueOf(18), true);
                                                                                                        questState.takeItems(1460, 984L);
                                                                                                        questState.playSound("ItemSound.quest_middle");
                                                                                                        string2 = "maestro_leorin_q0234_33.htm";
                                                                                                    } else {
                                                                                                        string2 = "maestro_leorin_q0234_34.htm";
                                                                                                    }
                                                                                                    break block1205;
                                                                                                }
                                                                                                if (!string.equalsIgnoreCase("menu_select?ask=234&reply=41")) break block1224;
                                                                                                if (n != 10) break block1205;
                                                                                                if (questState.getQuestItemsCount(1460) >= 984L) {
                                                                                                    questState.setCond(10);
                                                                                                    questState.set("whispers_of_destiny", String.valueOf(41), true);
                                                                                                    questState.takeItems(1460, 984L);
                                                                                                    questState.playSound("ItemSound.quest_middle");
                                                                                                    string2 = "maestro_leorin_q0234_33a.htm";
                                                                                                } else {
                                                                                                    string2 = "maestro_leorin_q0234_34.htm";
                                                                                                }
                                                                                                break block1205;
                                                                                            }
                                                                                            if (!string.equalsIgnoreCase("menu_select?ask=234&reply=42")) break block1225;
                                                                                            if (n != 10) break block1205;
                                                                                            if (questState.getQuestItemsCount(1460) >= 984L) {
                                                                                                questState.setCond(10);
                                                                                                questState.set("whispers_of_destiny", String.valueOf(42), true);
                                                                                                questState.takeItems(1460, 984L);
                                                                                                questState.playSound("ItemSound.quest_middle");
                                                                                                string2 = "maestro_leorin_q0234_33b.htm";
                                                                                            } else {
                                                                                                string2 = "maestro_leorin_q0234_34.htm";
                                                                                            }
                                                                                            break block1205;
                                                                                        }
                                                                                        if (!string.equalsIgnoreCase("menu_select?ask=234&reply=43")) break block1226;
                                                                                        if (n != 10) break block1205;
                                                                                        if (questState.getQuestItemsCount(1460) >= 984L) {
                                                                                            questState.setCond(10);
                                                                                            questState.set("whispers_of_destiny", String.valueOf(43), true);
                                                                                            questState.takeItems(1460, 984L);
                                                                                            questState.playSound("ItemSound.quest_middle");
                                                                                            string2 = "maestro_leorin_q0234_33c.htm";
                                                                                        } else {
                                                                                            string2 = "maestro_leorin_q0234_34.htm";
                                                                                        }
                                                                                        break block1205;
                                                                                    }
                                                                                    if (!string.equalsIgnoreCase("menu_select?ask=234&reply=44")) break block1227;
                                                                                    if (n != 10) break block1205;
                                                                                    if (questState.getQuestItemsCount(1460) >= 984L) {
                                                                                        questState.setCond(10);
                                                                                        questState.set("whispers_of_destiny", String.valueOf(44), true);
                                                                                        questState.takeItems(1460, 984L);
                                                                                        questState.playSound("ItemSound.quest_middle");
                                                                                        string2 = "maestro_leorin_q0234_33d.htm";
                                                                                    } else {
                                                                                        string2 = "maestro_leorin_q0234_34.htm";
                                                                                    }
                                                                                    break block1205;
                                                                                }
                                                                                if (!string.equalsIgnoreCase("menu_select?ask=234&reply=21")) break block1228;
                                                                                if (n == 11 && (questState.getQuestItemsCount(79) > 0L || questState.getQuestItemsCount(4717) > 0L || questState.getQuestItemsCount(4718) > 0L || questState.getQuestItemsCount(4719) > 0L)) {
                                                                                    questState.giveItems(80, 1L);
                                                                                    questState.giveItems(5011, 1L);
                                                                                    if (questState.getQuestItemsCount(79) > 0L) {
                                                                                        questState.takeItems(79, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4717) > 0L) {
                                                                                        questState.takeItems(4717, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4718) > 0L) {
                                                                                        questState.takeItems(4718, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4719) > 0L) {
                                                                                        questState.takeItems(4719, 1L);
                                                                                    }
                                                                                    this.giveExtraReward(questState.getPlayer());
                                                                                    questState.exitCurrentQuest(false);
                                                                                    questState.playSound("ItemSound.quest_finish");
                                                                                    string2 = "maestro_leorin_q0234_44.htm";
                                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                                }
                                                                                if (n == 12 && (questState.getQuestItemsCount(4828) > 0L || questState.getQuestItemsCount(4829) > 0L || questState.getQuestItemsCount(4830) > 0L || questState.getQuestItemsCount(287) > 0L)) {
                                                                                    questState.giveItems(80, 1L);
                                                                                    questState.giveItems(5011, 1L);
                                                                                    if (questState.getQuestItemsCount(287) > 0L) {
                                                                                        questState.takeItems(287, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4828) > 0L) {
                                                                                        questState.takeItems(4828, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4829) > 0L) {
                                                                                        questState.takeItems(4829, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4830) > 0L) {
                                                                                        questState.takeItems(4830, 1L);
                                                                                    }
                                                                                    this.giveExtraReward(questState.getPlayer());
                                                                                    questState.exitCurrentQuest(false);
                                                                                    questState.playSound("ItemSound.quest_finish");
                                                                                    string2 = "maestro_leorin_q0234_44.htm";
                                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                                }
                                                                                if (n == 13 && (questState.getQuestItemsCount(4858) > 0L || questState.getQuestItemsCount(4859) > 0L || questState.getQuestItemsCount(4860) > 0L || questState.getQuestItemsCount(97) > 0L)) {
                                                                                    questState.giveItems(80, 1L);
                                                                                    questState.giveItems(5011, 1L);
                                                                                    if (questState.getQuestItemsCount(97) > 0L) {
                                                                                        questState.takeItems(97, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4858) > 0L) {
                                                                                        questState.takeItems(4858, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4859) > 0L) {
                                                                                        questState.takeItems(4859, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4860) > 0L) {
                                                                                        questState.takeItems(4860, 1L);
                                                                                    }
                                                                                    this.giveExtraReward(questState.getPlayer());
                                                                                    questState.exitCurrentQuest(false);
                                                                                    questState.playSound("ItemSound.quest_finish");
                                                                                    string2 = "maestro_leorin_q0234_44.htm";
                                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                                }
                                                                                if (n == 14 && (questState.getQuestItemsCount(4753) > 0L || questState.getQuestItemsCount(4754) > 0L || questState.getQuestItemsCount(4755) > 0L || questState.getQuestItemsCount(175) > 0L)) {
                                                                                    questState.giveItems(80, 1L);
                                                                                    questState.giveItems(5011, 1L);
                                                                                    if (questState.getQuestItemsCount(175) > 0L) {
                                                                                        questState.takeItems(175, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4753) > 0L) {
                                                                                        questState.takeItems(4753, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4754) > 0L) {
                                                                                        questState.takeItems(4754, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4755) > 0L) {
                                                                                        questState.takeItems(4755, 1L);
                                                                                    }
                                                                                    this.giveExtraReward(questState.getPlayer());
                                                                                    questState.exitCurrentQuest(false);
                                                                                    questState.playSound("ItemSound.quest_finish");
                                                                                    string2 = "maestro_leorin_q0234_44.htm";
                                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                                }
                                                                                if (n == 15 && (questState.getQuestItemsCount(4900) > 0L || questState.getQuestItemsCount(4901) > 0L || questState.getQuestItemsCount(4902) > 0L || questState.getQuestItemsCount(210) > 0L)) {
                                                                                    questState.giveItems(80, 1L);
                                                                                    questState.giveItems(5011, 1L);
                                                                                    if (questState.getQuestItemsCount(210) > 0L) {
                                                                                        questState.takeItems(210, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4900) > 0L) {
                                                                                        questState.takeItems(4900, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4901) > 0L) {
                                                                                        questState.takeItems(4901, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4902) > 0L) {
                                                                                        questState.takeItems(4902, 1L);
                                                                                    }
                                                                                    this.giveExtraReward(questState.getPlayer());
                                                                                    questState.exitCurrentQuest(false);
                                                                                    questState.playSound("ItemSound.quest_finish");
                                                                                    string2 = "maestro_leorin_q0234_44.htm";
                                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                                }
                                                                                if (n == 16 && (questState.getQuestItemsCount(4780) > 0L || questState.getQuestItemsCount(4781) > 0L || questState.getQuestItemsCount(4782) > 0L || questState.getQuestItemsCount(234) > 0L || questState.getQuestItemsCount(6359) > 0L)) {
                                                                                    questState.giveItems(80, 1L);
                                                                                    questState.giveItems(5011, 1L);
                                                                                    if (questState.getQuestItemsCount(234) > 0L) {
                                                                                        questState.takeItems(234, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4780) > 0L) {
                                                                                        questState.takeItems(4780, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4781) > 0L) {
                                                                                        questState.takeItems(4781, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4782) > 0L) {
                                                                                        questState.takeItems(4782, 1L);
                                                                                    } else if (questState.getQuestItemsCount(6359) > 0L) {
                                                                                        questState.takeItems(6359, 1L);
                                                                                    }
                                                                                    this.giveExtraReward(questState.getPlayer());
                                                                                    questState.exitCurrentQuest(false);
                                                                                    questState.playSound("ItemSound.quest_finish");
                                                                                    string2 = "maestro_leorin_q0234_44.htm";
                                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                                }
                                                                                if (n == 17 && (questState.getQuestItemsCount(4804) > 0L || questState.getQuestItemsCount(4805) > 0L || questState.getQuestItemsCount(4806) > 0L || questState.getQuestItemsCount(268) > 0L)) {
                                                                                    questState.giveItems(80, 1L);
                                                                                    questState.giveItems(5011, 1L);
                                                                                    if (questState.getQuestItemsCount(268) > 0L) {
                                                                                        questState.takeItems(268, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4804) > 0L) {
                                                                                        questState.takeItems(4804, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4805) > 0L) {
                                                                                        questState.takeItems(4805, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4806) > 0L) {
                                                                                        questState.takeItems(4806, 1L);
                                                                                    }
                                                                                    this.giveExtraReward(questState.getPlayer());
                                                                                    questState.exitCurrentQuest(false);
                                                                                    questState.playSound("ItemSound.quest_finish");
                                                                                    string2 = "maestro_leorin_q0234_44.htm";
                                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                                }
                                                                                if (n == 18 && (questState.getQuestItemsCount(4750) > 0L || questState.getQuestItemsCount(4751) > 0L || questState.getQuestItemsCount(4752) > 0L || questState.getQuestItemsCount(171) > 0L)) {
                                                                                    questState.giveItems(80, 1L);
                                                                                    questState.giveItems(5011, 1L);
                                                                                    if (questState.getQuestItemsCount(171) > 0L) {
                                                                                        questState.takeItems(171, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4750) > 0L) {
                                                                                        questState.takeItems(4750, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4751) > 0L) {
                                                                                        questState.takeItems(4751, 1L);
                                                                                    } else if (questState.getQuestItemsCount(4752) > 0L) {
                                                                                        questState.takeItems(4752, 1L);
                                                                                    }
                                                                                    this.giveExtraReward(questState.getPlayer());
                                                                                    questState.exitCurrentQuest(false);
                                                                                    questState.playSound("ItemSound.quest_finish");
                                                                                    string2 = "maestro_leorin_q0234_44.htm";
                                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                                }
                                                                                if (n == 19 && questState.getQuestItemsCount(2626) > 0L) {
                                                                                    questState.giveItems(80, 1L);
                                                                                    questState.giveItems(5011, 1L);
                                                                                    questState.takeItems(2626, 1L);
                                                                                    this.giveExtraReward(questState.getPlayer());
                                                                                    questState.exitCurrentQuest(false);
                                                                                    questState.playSound("ItemSound.quest_finish");
                                                                                    string2 = "maestro_leorin_q0234_44.htm";
                                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                                }
                                                                                if (n == 41 && (questState.getQuestItemsCount(7883) > 0L || questState.getQuestItemsCount(8105) > 0L || questState.getQuestItemsCount(8106) > 0L || questState.getQuestItemsCount(8107) > 0L)) {
                                                                                    questState.giveItems(80, 1L);
                                                                                    questState.giveItems(5011, 1L);
                                                                                    if (questState.getQuestItemsCount(7883) > 0L) {
                                                                                        questState.takeItems(7883, 1L);
                                                                                    } else if (questState.getQuestItemsCount(8105) > 0L) {
                                                                                        questState.takeItems(8105, 1L);
                                                                                    } else if (questState.getQuestItemsCount(8106) > 0L) {
                                                                                        questState.takeItems(8106, 1L);
                                                                                    } else if (questState.getQuestItemsCount(8107) > 0L) {
                                                                                        questState.takeItems(8107, 1L);
                                                                                    }
                                                                                    this.giveExtraReward(questState.getPlayer());
                                                                                    questState.exitCurrentQuest(false);
                                                                                    questState.playSound("ItemSound.quest_finish");
                                                                                    string2 = "maestro_leorin_q0234_44.htm";
                                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                                }
                                                                                if (n == 42 && (questState.getQuestItemsCount(7889) > 0L || questState.getQuestItemsCount(8117) > 0L || questState.getQuestItemsCount(8118) > 0L || questState.getQuestItemsCount(8119) > 0L)) {
                                                                                    questState.giveItems(80, 1L);
                                                                                    questState.giveItems(5011, 1L);
                                                                                    if (questState.getQuestItemsCount(7889) > 0L) {
                                                                                        questState.takeItems(7889, 1L);
                                                                                    } else if (questState.getQuestItemsCount(8117) > 0L) {
                                                                                        questState.takeItems(8117, 1L);
                                                                                    } else if (questState.getQuestItemsCount(8118) > 0L) {
                                                                                        questState.takeItems(8118, 1L);
                                                                                    } else if (questState.getQuestItemsCount(8119) > 0L) {
                                                                                        questState.takeItems(8119, 1L);
                                                                                    }
                                                                                    this.giveExtraReward(questState.getPlayer());
                                                                                    questState.exitCurrentQuest(false);
                                                                                    questState.playSound("ItemSound.quest_finish");
                                                                                    string2 = "maestro_leorin_q0234_44.htm";
                                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                                }
                                                                                if (n == 43 && (questState.getQuestItemsCount(7901) > 0L || questState.getQuestItemsCount(8132) > 0L || questState.getQuestItemsCount(8133) > 0L || questState.getQuestItemsCount(8134) > 0L)) {
                                                                                    questState.giveItems(80, 1L);
                                                                                    questState.giveItems(5011, 1L);
                                                                                    if (questState.getQuestItemsCount(7901) > 0L) {
                                                                                        questState.takeItems(7901, 1L);
                                                                                    } else if (questState.getQuestItemsCount(8132) > 0L) {
                                                                                        questState.takeItems(8132, 1L);
                                                                                    } else if (questState.getQuestItemsCount(8133) > 0L) {
                                                                                        questState.takeItems(8133, 1L);
                                                                                    } else if (questState.getQuestItemsCount(8134) > 0L) {
                                                                                        questState.takeItems(8134, 1L);
                                                                                    }
                                                                                    this.giveExtraReward(questState.getPlayer());
                                                                                    questState.exitCurrentQuest(false);
                                                                                    questState.playSound("ItemSound.quest_finish");
                                                                                    string2 = "maestro_leorin_q0234_44.htm";
                                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                                }
                                                                                if (n != 44 || questState.getQuestItemsCount(7893) <= 0L && questState.getQuestItemsCount(8144) <= 0L && questState.getQuestItemsCount(8145) <= 0L && questState.getQuestItemsCount(8146) <= 0L) break block1205;
                                                                                questState.giveItems(80, 1L);
                                                                                questState.giveItems(5011, 1L);
                                                                                if (questState.getQuestItemsCount(7893) > 0L) {
                                                                                    questState.takeItems(7893, 1L);
                                                                                } else if (questState.getQuestItemsCount(8144) > 0L) {
                                                                                    questState.takeItems(8144, 1L);
                                                                                } else if (questState.getQuestItemsCount(8145) > 0L) {
                                                                                    questState.takeItems(8145, 1L);
                                                                                } else if (questState.getQuestItemsCount(8146) > 0L) {
                                                                                    questState.takeItems(8146, 1L);
                                                                                }
                                                                                this.giveExtraReward(questState.getPlayer());
                                                                                questState.exitCurrentQuest(false);
                                                                                questState.playSound("ItemSound.quest_finish");
                                                                                string2 = "maestro_leorin_q0234_44.htm";
                                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                                break block1205;
                                                                            }
                                                                            if (!string.equalsIgnoreCase("menu_select?ask=234&reply=22")) break block1229;
                                                                            if (n == 11 && (questState.getQuestItemsCount(79) > 0L || questState.getQuestItemsCount(4717) > 0L || questState.getQuestItemsCount(4718) > 0L || questState.getQuestItemsCount(4719) > 0L)) {
                                                                                questState.giveItems(288, 1L);
                                                                                questState.giveItems(5011, 1L);
                                                                                if (questState.getQuestItemsCount(79) > 0L) {
                                                                                    questState.takeItems(79, 1L);
                                                                                } else if (questState.getQuestItemsCount(4717) > 0L) {
                                                                                    questState.takeItems(4717, 1L);
                                                                                } else if (questState.getQuestItemsCount(4718) > 0L) {
                                                                                    questState.takeItems(4718, 1L);
                                                                                } else if (questState.getQuestItemsCount(4719) > 0L) {
                                                                                    questState.takeItems(4719, 1L);
                                                                                }
                                                                                this.giveExtraReward(questState.getPlayer());
                                                                                questState.exitCurrentQuest(false);
                                                                                questState.playSound("ItemSound.quest_finish");
                                                                                string2 = "maestro_leorin_q0234_45.htm";
                                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                            }
                                                                            if (n == 12 && (questState.getQuestItemsCount(4828) > 0L || questState.getQuestItemsCount(4829) > 0L || questState.getQuestItemsCount(4830) > 0L || questState.getQuestItemsCount(287) > 0L)) {
                                                                                questState.giveItems(288, 1L);
                                                                                questState.giveItems(5011, 1L);
                                                                                if (questState.getQuestItemsCount(287) > 0L) {
                                                                                    questState.takeItems(287, 1L);
                                                                                } else if (questState.getQuestItemsCount(4828) > 0L) {
                                                                                    questState.takeItems(4828, 1L);
                                                                                } else if (questState.getQuestItemsCount(4829) > 0L) {
                                                                                    questState.takeItems(4829, 1L);
                                                                                } else if (questState.getQuestItemsCount(4830) > 0L) {
                                                                                    questState.takeItems(4830, 1L);
                                                                                }
                                                                                this.giveExtraReward(questState.getPlayer());
                                                                                questState.exitCurrentQuest(false);
                                                                                questState.playSound("ItemSound.quest_finish");
                                                                                string2 = "maestro_leorin_q0234_45.htm";
                                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                            }
                                                                            if (n == 13 && (questState.getQuestItemsCount(4858) > 0L || questState.getQuestItemsCount(4859) > 0L || questState.getQuestItemsCount(4860) > 0L || questState.getQuestItemsCount(97) > 0L)) {
                                                                                questState.giveItems(288, 1L);
                                                                                questState.giveItems(5011, 1L);
                                                                                if (questState.getQuestItemsCount(97) > 0L) {
                                                                                    questState.takeItems(97, 1L);
                                                                                } else if (questState.getQuestItemsCount(4858) > 0L) {
                                                                                    questState.takeItems(4858, 1L);
                                                                                } else if (questState.getQuestItemsCount(4859) > 0L) {
                                                                                    questState.takeItems(4859, 1L);
                                                                                } else if (questState.getQuestItemsCount(4860) > 0L) {
                                                                                    questState.takeItems(4860, 1L);
                                                                                }
                                                                                this.giveExtraReward(questState.getPlayer());
                                                                                questState.exitCurrentQuest(false);
                                                                                questState.playSound("ItemSound.quest_finish");
                                                                                string2 = "maestro_leorin_q0234_45.htm";
                                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                            }
                                                                            if (n == 14 && (questState.getQuestItemsCount(4753) > 0L || questState.getQuestItemsCount(4754) > 0L || questState.getQuestItemsCount(4755) > 0L || questState.getQuestItemsCount(175) > 0L)) {
                                                                                questState.giveItems(288, 1L);
                                                                                questState.giveItems(5011, 1L);
                                                                                if (questState.getQuestItemsCount(175) > 0L) {
                                                                                    questState.takeItems(175, 1L);
                                                                                } else if (questState.getQuestItemsCount(4753) > 0L) {
                                                                                    questState.takeItems(4753, 1L);
                                                                                } else if (questState.getQuestItemsCount(4754) > 0L) {
                                                                                    questState.takeItems(4754, 1L);
                                                                                } else if (questState.getQuestItemsCount(4755) > 0L) {
                                                                                    questState.takeItems(4755, 1L);
                                                                                }
                                                                                this.giveExtraReward(questState.getPlayer());
                                                                                questState.exitCurrentQuest(false);
                                                                                questState.playSound("ItemSound.quest_finish");
                                                                                string2 = "maestro_leorin_q0234_45.htm";
                                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                            }
                                                                            if (n == 15 && (questState.getQuestItemsCount(4900) > 0L || questState.getQuestItemsCount(4901) > 0L || questState.getQuestItemsCount(4902) > 0L || questState.getQuestItemsCount(210) > 0L)) {
                                                                                questState.giveItems(288, 1L);
                                                                                questState.giveItems(5011, 1L);
                                                                                if (questState.getQuestItemsCount(210) > 0L) {
                                                                                    questState.takeItems(210, 1L);
                                                                                } else if (questState.getQuestItemsCount(4900) > 0L) {
                                                                                    questState.takeItems(4900, 1L);
                                                                                } else if (questState.getQuestItemsCount(4901) > 0L) {
                                                                                    questState.takeItems(4901, 1L);
                                                                                } else if (questState.getQuestItemsCount(4902) > 0L) {
                                                                                    questState.takeItems(4902, 1L);
                                                                                }
                                                                                this.giveExtraReward(questState.getPlayer());
                                                                                questState.exitCurrentQuest(false);
                                                                                questState.playSound("ItemSound.quest_finish");
                                                                                string2 = "maestro_leorin_q0234_45.htm";
                                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                            }
                                                                            if (n == 16 && (questState.getQuestItemsCount(4780) > 0L || questState.getQuestItemsCount(4781) > 0L || questState.getQuestItemsCount(4782) > 0L || questState.getQuestItemsCount(234) > 0L || questState.getQuestItemsCount(6359) > 0L)) {
                                                                                questState.giveItems(288, 1L);
                                                                                questState.giveItems(5011, 1L);
                                                                                if (questState.getQuestItemsCount(234) > 0L) {
                                                                                    questState.takeItems(234, 1L);
                                                                                } else if (questState.getQuestItemsCount(4780) > 0L) {
                                                                                    questState.takeItems(4780, 1L);
                                                                                } else if (questState.getQuestItemsCount(4781) > 0L) {
                                                                                    questState.takeItems(4781, 1L);
                                                                                } else if (questState.getQuestItemsCount(4782) > 0L) {
                                                                                    questState.takeItems(4782, 1L);
                                                                                } else if (questState.getQuestItemsCount(6359) > 0L) {
                                                                                    questState.takeItems(6359, 1L);
                                                                                }
                                                                                this.giveExtraReward(questState.getPlayer());
                                                                                questState.exitCurrentQuest(false);
                                                                                questState.playSound("ItemSound.quest_finish");
                                                                                string2 = "maestro_leorin_q0234_45.htm";
                                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                            }
                                                                            if (n == 17 && (questState.getQuestItemsCount(4804) > 0L || questState.getQuestItemsCount(4805) > 0L || questState.getQuestItemsCount(4806) > 0L || questState.getQuestItemsCount(268) > 0L)) {
                                                                                questState.giveItems(288, 1L);
                                                                                questState.giveItems(5011, 1L);
                                                                                if (questState.getQuestItemsCount(268) > 0L) {
                                                                                    questState.takeItems(268, 1L);
                                                                                } else if (questState.getQuestItemsCount(4804) > 0L) {
                                                                                    questState.takeItems(4804, 1L);
                                                                                } else if (questState.getQuestItemsCount(4805) > 0L) {
                                                                                    questState.takeItems(4805, 1L);
                                                                                } else if (questState.getQuestItemsCount(4806) > 0L) {
                                                                                    questState.takeItems(4806, 1L);
                                                                                }
                                                                                this.giveExtraReward(questState.getPlayer());
                                                                                questState.exitCurrentQuest(false);
                                                                                questState.playSound("ItemSound.quest_finish");
                                                                                string2 = "maestro_leorin_q0234_45.htm";
                                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                            }
                                                                            if (n == 18 && (questState.getQuestItemsCount(4750) > 0L || questState.getQuestItemsCount(4751) > 0L || questState.getQuestItemsCount(4752) > 0L || questState.getQuestItemsCount(171) > 0L)) {
                                                                                questState.giveItems(288, 1L);
                                                                                questState.giveItems(5011, 1L);
                                                                                if (questState.getQuestItemsCount(171) > 0L) {
                                                                                    questState.takeItems(171, 1L);
                                                                                } else if (questState.getQuestItemsCount(4750) > 0L) {
                                                                                    questState.takeItems(4750, 1L);
                                                                                } else if (questState.getQuestItemsCount(4751) > 0L) {
                                                                                    questState.takeItems(4751, 1L);
                                                                                } else if (questState.getQuestItemsCount(4752) > 0L) {
                                                                                    questState.takeItems(4752, 1L);
                                                                                }
                                                                                this.giveExtraReward(questState.getPlayer());
                                                                                questState.exitCurrentQuest(false);
                                                                                questState.playSound("ItemSound.quest_finish");
                                                                                string2 = "maestro_leorin_q0234_45.htm";
                                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                            }
                                                                            if (n == 19 && questState.getQuestItemsCount(2626) > 0L) {
                                                                                questState.giveItems(288, 1L);
                                                                                questState.giveItems(5011, 1L);
                                                                                questState.takeItems(2626, 1L);
                                                                                this.giveExtraReward(questState.getPlayer());
                                                                                questState.exitCurrentQuest(false);
                                                                                questState.playSound("ItemSound.quest_finish");
                                                                                string2 = "maestro_leorin_q0234_45.htm";
                                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                            }
                                                                            if (n == 41 && (questState.getQuestItemsCount(7883) > 0L || questState.getQuestItemsCount(8105) > 0L || questState.getQuestItemsCount(8106) > 0L || questState.getQuestItemsCount(8107) > 0L)) {
                                                                                questState.giveItems(288, 1L);
                                                                                questState.giveItems(5011, 1L);
                                                                                if (questState.getQuestItemsCount(7883) > 0L) {
                                                                                    questState.takeItems(7883, 1L);
                                                                                } else if (questState.getQuestItemsCount(8105) > 0L) {
                                                                                    questState.takeItems(8105, 1L);
                                                                                } else if (questState.getQuestItemsCount(8106) > 0L) {
                                                                                    questState.takeItems(8106, 1L);
                                                                                } else if (questState.getQuestItemsCount(8107) > 0L) {
                                                                                    questState.takeItems(8107, 1L);
                                                                                }
                                                                                this.giveExtraReward(questState.getPlayer());
                                                                                questState.exitCurrentQuest(false);
                                                                                questState.playSound("ItemSound.quest_finish");
                                                                                string2 = "maestro_leorin_q0234_45.htm";
                                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                            }
                                                                            if (n == 42 && (questState.getQuestItemsCount(7889) > 0L || questState.getQuestItemsCount(8117) > 0L || questState.getQuestItemsCount(8118) > 0L || questState.getQuestItemsCount(8119) > 0L)) {
                                                                                questState.giveItems(288, 1L);
                                                                                questState.giveItems(5011, 1L);
                                                                                if (questState.getQuestItemsCount(7889) > 0L) {
                                                                                    questState.takeItems(7889, 1L);
                                                                                } else if (questState.getQuestItemsCount(8117) > 0L) {
                                                                                    questState.takeItems(8117, 1L);
                                                                                } else if (questState.getQuestItemsCount(8118) > 0L) {
                                                                                    questState.takeItems(8118, 1L);
                                                                                } else if (questState.getQuestItemsCount(8119) > 0L) {
                                                                                    questState.takeItems(8119, 1L);
                                                                                }
                                                                                this.giveExtraReward(questState.getPlayer());
                                                                                questState.exitCurrentQuest(false);
                                                                                questState.playSound("ItemSound.quest_finish");
                                                                                string2 = "maestro_leorin_q0234_45.htm";
                                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                            }
                                                                            if (n == 43 && (questState.getQuestItemsCount(7901) > 0L || questState.getQuestItemsCount(8132) > 0L || questState.getQuestItemsCount(8133) > 0L || questState.getQuestItemsCount(8134) > 0L)) {
                                                                                questState.giveItems(288, 1L);
                                                                                questState.giveItems(5011, 1L);
                                                                                if (questState.getQuestItemsCount(7901) > 0L) {
                                                                                    questState.takeItems(7901, 1L);
                                                                                } else if (questState.getQuestItemsCount(8132) > 0L) {
                                                                                    questState.takeItems(8132, 1L);
                                                                                } else if (questState.getQuestItemsCount(8133) > 0L) {
                                                                                    questState.takeItems(8133, 1L);
                                                                                } else if (questState.getQuestItemsCount(8134) > 0L) {
                                                                                    questState.takeItems(8134, 1L);
                                                                                }
                                                                                this.giveExtraReward(questState.getPlayer());
                                                                                questState.exitCurrentQuest(false);
                                                                                questState.playSound("ItemSound.quest_finish");
                                                                                string2 = "maestro_leorin_q0234_45.htm";
                                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                            }
                                                                            if (n != 44 || questState.getQuestItemsCount(7893) <= 0L && questState.getQuestItemsCount(8144) <= 0L && questState.getQuestItemsCount(8145) <= 0L && questState.getQuestItemsCount(8146) <= 0L) break block1205;
                                                                            questState.giveItems(288, 1L);
                                                                            questState.giveItems(5011, 1L);
                                                                            if (questState.getQuestItemsCount(7893) > 0L) {
                                                                                questState.takeItems(7893, 1L);
                                                                            } else if (questState.getQuestItemsCount(8144) > 0L) {
                                                                                questState.takeItems(8144, 1L);
                                                                            } else if (questState.getQuestItemsCount(8145) > 0L) {
                                                                                questState.takeItems(8145, 1L);
                                                                            } else if (questState.getQuestItemsCount(8146) > 0L) {
                                                                                questState.takeItems(8146, 1L);
                                                                            }
                                                                            this.giveExtraReward(questState.getPlayer());
                                                                            questState.exitCurrentQuest(false);
                                                                            questState.playSound("ItemSound.quest_finish");
                                                                            string2 = "maestro_leorin_q0234_45.htm";
                                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                            break block1205;
                                                                        }
                                                                        if (!string.equalsIgnoreCase("menu_select?ask=234&reply=23")) break block1230;
                                                                        if (n == 11 && (questState.getQuestItemsCount(79) > 0L || questState.getQuestItemsCount(4717) > 0L || questState.getQuestItemsCount(4718) > 0L || questState.getQuestItemsCount(4719) > 0L)) {
                                                                            questState.giveItems(98, 1L);
                                                                            questState.giveItems(5011, 1L);
                                                                            if (questState.getQuestItemsCount(79) > 0L) {
                                                                                questState.takeItems(79, 1L);
                                                                            } else if (questState.getQuestItemsCount(4717) > 0L) {
                                                                                questState.takeItems(4717, 1L);
                                                                            } else if (questState.getQuestItemsCount(4718) > 0L) {
                                                                                questState.takeItems(4718, 1L);
                                                                            } else if (questState.getQuestItemsCount(4719) > 0L) {
                                                                                questState.takeItems(4719, 1L);
                                                                            }
                                                                            this.giveExtraReward(questState.getPlayer());
                                                                            questState.exitCurrentQuest(false);
                                                                            questState.playSound("ItemSound.quest_finish");
                                                                            string2 = "maestro_leorin_q0234_46.htm";
                                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                        }
                                                                        if (n == 12 && (questState.getQuestItemsCount(4828) > 0L || questState.getQuestItemsCount(4829) > 0L || questState.getQuestItemsCount(4830) > 0L || questState.getQuestItemsCount(287) > 0L)) {
                                                                            questState.giveItems(98, 1L);
                                                                            questState.giveItems(5011, 1L);
                                                                            if (questState.getQuestItemsCount(287) > 0L) {
                                                                                questState.takeItems(287, 1L);
                                                                            } else if (questState.getQuestItemsCount(4828) > 0L) {
                                                                                questState.takeItems(4828, 1L);
                                                                            } else if (questState.getQuestItemsCount(4829) > 0L) {
                                                                                questState.takeItems(4829, 1L);
                                                                            } else if (questState.getQuestItemsCount(4830) > 0L) {
                                                                                questState.takeItems(4830, 1L);
                                                                            }
                                                                            this.giveExtraReward(questState.getPlayer());
                                                                            questState.exitCurrentQuest(false);
                                                                            questState.playSound("ItemSound.quest_finish");
                                                                            string2 = "maestro_leorin_q0234_46.htm";
                                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                        }
                                                                        if (n == 13 && (questState.getQuestItemsCount(4858) > 0L || questState.getQuestItemsCount(4859) > 0L || questState.getQuestItemsCount(4860) > 0L || questState.getQuestItemsCount(97) > 0L)) {
                                                                            questState.giveItems(98, 1L);
                                                                            questState.giveItems(5011, 1L);
                                                                            if (questState.getQuestItemsCount(97) > 0L) {
                                                                                questState.takeItems(97, 1L);
                                                                            } else if (questState.getQuestItemsCount(4858) > 0L) {
                                                                                questState.takeItems(4858, 1L);
                                                                            } else if (questState.getQuestItemsCount(4859) > 0L) {
                                                                                questState.takeItems(4859, 1L);
                                                                            } else if (questState.getQuestItemsCount(4860) > 0L) {
                                                                                questState.takeItems(4860, 1L);
                                                                            }
                                                                            this.giveExtraReward(questState.getPlayer());
                                                                            questState.exitCurrentQuest(false);
                                                                            questState.playSound("ItemSound.quest_finish");
                                                                            string2 = "maestro_leorin_q0234_46.htm";
                                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                        }
                                                                        if (n == 14 && (questState.getQuestItemsCount(4753) > 0L || questState.getQuestItemsCount(4754) > 0L || questState.getQuestItemsCount(4755) > 0L || questState.getQuestItemsCount(175) > 0L)) {
                                                                            questState.giveItems(98, 1L);
                                                                            questState.giveItems(5011, 1L);
                                                                            if (questState.getQuestItemsCount(175) > 0L) {
                                                                                questState.takeItems(175, 1L);
                                                                            } else if (questState.getQuestItemsCount(4753) > 0L) {
                                                                                questState.takeItems(4753, 1L);
                                                                            } else if (questState.getQuestItemsCount(4754) > 0L) {
                                                                                questState.takeItems(4754, 1L);
                                                                            } else if (questState.getQuestItemsCount(4755) > 0L) {
                                                                                questState.takeItems(4755, 1L);
                                                                            }
                                                                            this.giveExtraReward(questState.getPlayer());
                                                                            questState.exitCurrentQuest(false);
                                                                            questState.playSound("ItemSound.quest_finish");
                                                                            string2 = "maestro_leorin_q0234_46.htm";
                                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                        }
                                                                        if (n == 15 && (questState.getQuestItemsCount(4900) > 0L || questState.getQuestItemsCount(4901) > 0L || questState.getQuestItemsCount(4902) > 0L || questState.getQuestItemsCount(210) > 0L)) {
                                                                            questState.giveItems(98, 1L);
                                                                            questState.giveItems(5011, 1L);
                                                                            if (questState.getQuestItemsCount(210) > 0L) {
                                                                                questState.takeItems(210, 1L);
                                                                            } else if (questState.getQuestItemsCount(4900) > 0L) {
                                                                                questState.takeItems(4900, 1L);
                                                                            } else if (questState.getQuestItemsCount(4901) > 0L) {
                                                                                questState.takeItems(4901, 1L);
                                                                            } else if (questState.getQuestItemsCount(4902) > 0L) {
                                                                                questState.takeItems(4902, 1L);
                                                                            }
                                                                            this.giveExtraReward(questState.getPlayer());
                                                                            questState.exitCurrentQuest(false);
                                                                            questState.playSound("ItemSound.quest_finish");
                                                                            string2 = "maestro_leorin_q0234_46.htm";
                                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                        }
                                                                        if (n == 16 && (questState.getQuestItemsCount(4780) > 0L || questState.getQuestItemsCount(4781) > 0L || questState.getQuestItemsCount(4782) > 0L || questState.getQuestItemsCount(234) > 0L || questState.getQuestItemsCount(6359) > 0L)) {
                                                                            questState.giveItems(98, 1L);
                                                                            questState.giveItems(5011, 1L);
                                                                            if (questState.getQuestItemsCount(234) > 0L) {
                                                                                questState.takeItems(234, 1L);
                                                                            } else if (questState.getQuestItemsCount(4780) > 0L) {
                                                                                questState.takeItems(4780, 1L);
                                                                            } else if (questState.getQuestItemsCount(4781) > 0L) {
                                                                                questState.takeItems(4781, 1L);
                                                                            } else if (questState.getQuestItemsCount(4782) > 0L) {
                                                                                questState.takeItems(4782, 1L);
                                                                            } else if (questState.getQuestItemsCount(6359) > 0L) {
                                                                                questState.takeItems(6359, 1L);
                                                                            }
                                                                            this.giveExtraReward(questState.getPlayer());
                                                                            questState.exitCurrentQuest(false);
                                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                            questState.playSound("ItemSound.quest_finish");
                                                                            string2 = "maestro_leorin_q0234_46.htm";
                                                                        }
                                                                        if (n == 17 && (questState.getQuestItemsCount(4804) > 0L || questState.getQuestItemsCount(4805) > 0L || questState.getQuestItemsCount(4806) > 0L || questState.getQuestItemsCount(268) > 0L)) {
                                                                            questState.giveItems(98, 1L);
                                                                            questState.giveItems(5011, 1L);
                                                                            if (questState.getQuestItemsCount(268) > 0L) {
                                                                                questState.takeItems(268, 1L);
                                                                            } else if (questState.getQuestItemsCount(4804) > 0L) {
                                                                                questState.takeItems(4804, 1L);
                                                                            } else if (questState.getQuestItemsCount(4805) > 0L) {
                                                                                questState.takeItems(4805, 1L);
                                                                            } else if (questState.getQuestItemsCount(4806) > 0L) {
                                                                                questState.takeItems(4806, 1L);
                                                                            }
                                                                            this.giveExtraReward(questState.getPlayer());
                                                                            questState.exitCurrentQuest(false);
                                                                            questState.playSound("ItemSound.quest_finish");
                                                                            string2 = "maestro_leorin_q0234_46.htm";
                                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                        }
                                                                        if (n == 18 && (questState.getQuestItemsCount(4750) > 0L || questState.getQuestItemsCount(4751) > 0L || questState.getQuestItemsCount(4752) > 0L || questState.getQuestItemsCount(171) > 0L)) {
                                                                            questState.giveItems(98, 1L);
                                                                            questState.giveItems(5011, 1L);
                                                                            if (questState.getQuestItemsCount(171) > 0L) {
                                                                                questState.takeItems(171, 1L);
                                                                            } else if (questState.getQuestItemsCount(4750) > 0L) {
                                                                                questState.takeItems(4750, 1L);
                                                                            } else if (questState.getQuestItemsCount(4751) > 0L) {
                                                                                questState.takeItems(4751, 1L);
                                                                            } else if (questState.getQuestItemsCount(4752) > 0L) {
                                                                                questState.takeItems(4752, 1L);
                                                                            }
                                                                            this.giveExtraReward(questState.getPlayer());
                                                                            questState.exitCurrentQuest(false);
                                                                            questState.playSound("ItemSound.quest_finish");
                                                                            string2 = "maestro_leorin_q0234_46.htm";
                                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                        }
                                                                        if (n == 19 && questState.getQuestItemsCount(2626) > 0L) {
                                                                            questState.giveItems(98, 1L);
                                                                            questState.giveItems(5011, 1L);
                                                                            questState.takeItems(2626, 1L);
                                                                            this.giveExtraReward(questState.getPlayer());
                                                                            questState.exitCurrentQuest(false);
                                                                            questState.playSound("ItemSound.quest_finish");
                                                                            string2 = "maestro_leorin_q0234_46.htm";
                                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                        }
                                                                        if (n == 41 && (questState.getQuestItemsCount(7883) > 0L || questState.getQuestItemsCount(8105) > 0L || questState.getQuestItemsCount(8106) > 0L || questState.getQuestItemsCount(8107) > 0L)) {
                                                                            questState.giveItems(98, 1L);
                                                                            questState.giveItems(5011, 1L);
                                                                            if (questState.getQuestItemsCount(7883) > 0L) {
                                                                                questState.takeItems(7883, 1L);
                                                                            } else if (questState.getQuestItemsCount(8105) > 0L) {
                                                                                questState.takeItems(8105, 1L);
                                                                            } else if (questState.getQuestItemsCount(8106) > 0L) {
                                                                                questState.takeItems(8106, 1L);
                                                                            } else if (questState.getQuestItemsCount(8107) > 0L) {
                                                                                questState.takeItems(8107, 1L);
                                                                            }
                                                                            this.giveExtraReward(questState.getPlayer());
                                                                            questState.exitCurrentQuest(false);
                                                                            questState.playSound("ItemSound.quest_finish");
                                                                            string2 = "maestro_leorin_q0234_46.htm";
                                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                        }
                                                                        if (n == 42 && (questState.getQuestItemsCount(7889) > 0L || questState.getQuestItemsCount(8117) > 0L || questState.getQuestItemsCount(8118) > 0L || questState.getQuestItemsCount(8119) > 0L)) {
                                                                            questState.giveItems(98, 1L);
                                                                            questState.giveItems(5011, 1L);
                                                                            if (questState.getQuestItemsCount(7889) > 0L) {
                                                                                questState.takeItems(7889, 1L);
                                                                            } else if (questState.getQuestItemsCount(8117) > 0L) {
                                                                                questState.takeItems(8117, 1L);
                                                                            } else if (questState.getQuestItemsCount(8118) > 0L) {
                                                                                questState.takeItems(8118, 1L);
                                                                            } else if (questState.getQuestItemsCount(8119) > 0L) {
                                                                                questState.takeItems(8119, 1L);
                                                                            }
                                                                            this.giveExtraReward(questState.getPlayer());
                                                                            questState.exitCurrentQuest(false);
                                                                            questState.playSound("ItemSound.quest_finish");
                                                                            string2 = "maestro_leorin_q0234_46.htm";
                                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                        }
                                                                        if (n == 43 && (questState.getQuestItemsCount(7901) > 0L || questState.getQuestItemsCount(8132) > 0L || questState.getQuestItemsCount(8133) > 0L || questState.getQuestItemsCount(8134) > 0L)) {
                                                                            questState.giveItems(98, 1L);
                                                                            questState.giveItems(5011, 1L);
                                                                            if (questState.getQuestItemsCount(7901) > 0L) {
                                                                                questState.takeItems(7901, 1L);
                                                                            } else if (questState.getQuestItemsCount(8132) > 0L) {
                                                                                questState.takeItems(8132, 1L);
                                                                            } else if (questState.getQuestItemsCount(8133) > 0L) {
                                                                                questState.takeItems(8133, 1L);
                                                                            } else if (questState.getQuestItemsCount(8134) > 0L) {
                                                                                questState.takeItems(8134, 1L);
                                                                            }
                                                                            this.giveExtraReward(questState.getPlayer());
                                                                            questState.exitCurrentQuest(false);
                                                                            questState.playSound("ItemSound.quest_finish");
                                                                            string2 = "maestro_leorin_q0234_46.htm";
                                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                        }
                                                                        if (n != 44 || questState.getQuestItemsCount(7893) <= 0L && questState.getQuestItemsCount(8144) <= 0L && questState.getQuestItemsCount(8145) <= 0L && questState.getQuestItemsCount(8146) <= 0L) break block1205;
                                                                        questState.giveItems(98, 1L);
                                                                        questState.giveItems(5011, 1L);
                                                                        if (questState.getQuestItemsCount(7893) > 0L) {
                                                                            questState.takeItems(7893, 1L);
                                                                        } else if (questState.getQuestItemsCount(8144) > 0L) {
                                                                            questState.takeItems(8144, 1L);
                                                                        } else if (questState.getQuestItemsCount(8145) > 0L) {
                                                                            questState.takeItems(8145, 1L);
                                                                        } else if (questState.getQuestItemsCount(8146) > 0L) {
                                                                            questState.takeItems(8146, 1L);
                                                                        }
                                                                        this.giveExtraReward(questState.getPlayer());
                                                                        questState.exitCurrentQuest(false);
                                                                        questState.playSound("ItemSound.quest_finish");
                                                                        string2 = "maestro_leorin_q0234_46.htm";
                                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                        break block1205;
                                                                    }
                                                                    if (!string.equalsIgnoreCase("menu_select?ask=234&reply=24")) break block1231;
                                                                    if (n == 11 && (questState.getQuestItemsCount(79) > 0L || questState.getQuestItemsCount(4717) > 0L || questState.getQuestItemsCount(4718) > 0L || questState.getQuestItemsCount(4719) > 0L)) {
                                                                        questState.giveItems(150, 1L);
                                                                        questState.giveItems(5011, 1L);
                                                                        if (questState.getQuestItemsCount(79) > 0L) {
                                                                            questState.takeItems(79, 1L);
                                                                        } else if (questState.getQuestItemsCount(4717) > 0L) {
                                                                            questState.takeItems(4717, 1L);
                                                                        } else if (questState.getQuestItemsCount(4718) > 0L) {
                                                                            questState.takeItems(4718, 1L);
                                                                        } else if (questState.getQuestItemsCount(4719) > 0L) {
                                                                            questState.takeItems(4719, 1L);
                                                                        }
                                                                        this.giveExtraReward(questState.getPlayer());
                                                                        questState.exitCurrentQuest(false);
                                                                        questState.playSound("ItemSound.quest_finish");
                                                                        string2 = "maestro_leorin_q0234_47.htm";
                                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                    }
                                                                    if (n == 12 && (questState.getQuestItemsCount(4828) > 0L || questState.getQuestItemsCount(4829) > 0L || questState.getQuestItemsCount(4830) > 0L || questState.getQuestItemsCount(287) > 0L)) {
                                                                        questState.giveItems(150, 1L);
                                                                        questState.giveItems(5011, 1L);
                                                                        if (questState.getQuestItemsCount(287) > 0L) {
                                                                            questState.takeItems(287, 1L);
                                                                        } else if (questState.getQuestItemsCount(4828) > 0L) {
                                                                            questState.takeItems(4828, 1L);
                                                                        } else if (questState.getQuestItemsCount(4829) > 0L) {
                                                                            questState.takeItems(4829, 1L);
                                                                        } else if (questState.getQuestItemsCount(4830) > 0L) {
                                                                            questState.takeItems(4830, 1L);
                                                                        }
                                                                        this.giveExtraReward(questState.getPlayer());
                                                                        questState.exitCurrentQuest(false);
                                                                        questState.playSound("ItemSound.quest_finish");
                                                                        string2 = "maestro_leorin_q0234_47.htm";
                                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                    }
                                                                    if (n == 13 && (questState.getQuestItemsCount(4858) > 0L || questState.getQuestItemsCount(4859) > 0L || questState.getQuestItemsCount(4860) > 0L || questState.getQuestItemsCount(97) > 0L)) {
                                                                        questState.giveItems(150, 1L);
                                                                        questState.giveItems(5011, 1L);
                                                                        if (questState.getQuestItemsCount(97) > 0L) {
                                                                            questState.takeItems(97, 1L);
                                                                        } else if (questState.getQuestItemsCount(4858) > 0L) {
                                                                            questState.takeItems(4858, 1L);
                                                                        } else if (questState.getQuestItemsCount(4859) > 0L) {
                                                                            questState.takeItems(4859, 1L);
                                                                        } else if (questState.getQuestItemsCount(4860) > 0L) {
                                                                            questState.takeItems(4860, 1L);
                                                                        }
                                                                        this.giveExtraReward(questState.getPlayer());
                                                                        questState.exitCurrentQuest(false);
                                                                        questState.playSound("ItemSound.quest_finish");
                                                                        string2 = "maestro_leorin_q0234_47.htm";
                                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                    }
                                                                    if (n == 14 && (questState.getQuestItemsCount(4753) > 0L || questState.getQuestItemsCount(4754) > 0L || questState.getQuestItemsCount(4755) > 0L || questState.getQuestItemsCount(175) > 0L)) {
                                                                        questState.giveItems(150, 1L);
                                                                        questState.giveItems(5011, 1L);
                                                                        if (questState.getQuestItemsCount(175) > 0L) {
                                                                            questState.takeItems(175, 1L);
                                                                        } else if (questState.getQuestItemsCount(4753) > 0L) {
                                                                            questState.takeItems(4753, 1L);
                                                                        } else if (questState.getQuestItemsCount(4754) > 0L) {
                                                                            questState.takeItems(4754, 1L);
                                                                        } else if (questState.getQuestItemsCount(4755) > 0L) {
                                                                            questState.takeItems(4755, 1L);
                                                                        }
                                                                        this.giveExtraReward(questState.getPlayer());
                                                                        questState.exitCurrentQuest(false);
                                                                        questState.playSound("ItemSound.quest_finish");
                                                                        string2 = "maestro_leorin_q0234_47.htm";
                                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                    }
                                                                    if (n == 15 && (questState.getQuestItemsCount(4900) > 0L || questState.getQuestItemsCount(4901) > 0L || questState.getQuestItemsCount(4902) > 0L || questState.getQuestItemsCount(210) > 0L)) {
                                                                        questState.giveItems(150, 1L);
                                                                        questState.giveItems(5011, 1L);
                                                                        if (questState.getQuestItemsCount(210) > 0L) {
                                                                            questState.takeItems(210, 1L);
                                                                        } else if (questState.getQuestItemsCount(4900) > 0L) {
                                                                            questState.takeItems(4900, 1L);
                                                                        } else if (questState.getQuestItemsCount(4901) > 0L) {
                                                                            questState.takeItems(4901, 1L);
                                                                        } else if (questState.getQuestItemsCount(4902) > 0L) {
                                                                            questState.takeItems(4902, 1L);
                                                                        }
                                                                        this.giveExtraReward(questState.getPlayer());
                                                                        questState.exitCurrentQuest(false);
                                                                        questState.playSound("ItemSound.quest_finish");
                                                                        string2 = "maestro_leorin_q0234_47.htm";
                                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                    }
                                                                    if (n == 16 && (questState.getQuestItemsCount(4780) > 0L || questState.getQuestItemsCount(4781) > 0L || questState.getQuestItemsCount(4782) > 0L || questState.getQuestItemsCount(234) > 0L || questState.getQuestItemsCount(6359) > 0L)) {
                                                                        questState.giveItems(150, 1L);
                                                                        questState.giveItems(5011, 1L);
                                                                        if (questState.getQuestItemsCount(234) > 0L) {
                                                                            questState.takeItems(234, 1L);
                                                                        } else if (questState.getQuestItemsCount(4780) > 0L) {
                                                                            questState.takeItems(4780, 1L);
                                                                        } else if (questState.getQuestItemsCount(4781) > 0L) {
                                                                            questState.takeItems(4781, 1L);
                                                                        } else if (questState.getQuestItemsCount(4782) > 0L) {
                                                                            questState.takeItems(4782, 1L);
                                                                        } else if (questState.getQuestItemsCount(6359) > 0L) {
                                                                            questState.takeItems(6359, 1L);
                                                                        }
                                                                        this.giveExtraReward(questState.getPlayer());
                                                                        questState.exitCurrentQuest(false);
                                                                        questState.playSound("ItemSound.quest_finish");
                                                                        string2 = "maestro_leorin_q0234_47.htm";
                                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                    }
                                                                    if (n == 17 && (questState.getQuestItemsCount(4804) > 0L || questState.getQuestItemsCount(4805) > 0L || questState.getQuestItemsCount(4806) > 0L || questState.getQuestItemsCount(268) > 0L)) {
                                                                        questState.giveItems(150, 1L);
                                                                        questState.giveItems(5011, 1L);
                                                                        if (questState.getQuestItemsCount(268) > 0L) {
                                                                            questState.takeItems(268, 1L);
                                                                        } else if (questState.getQuestItemsCount(4804) > 0L) {
                                                                            questState.takeItems(4804, 1L);
                                                                        } else if (questState.getQuestItemsCount(4805) > 0L) {
                                                                            questState.takeItems(4805, 1L);
                                                                        } else if (questState.getQuestItemsCount(4806) > 0L) {
                                                                            questState.takeItems(4806, 1L);
                                                                        }
                                                                        this.giveExtraReward(questState.getPlayer());
                                                                        questState.exitCurrentQuest(false);
                                                                        questState.playSound("ItemSound.quest_finish");
                                                                        string2 = "maestro_leorin_q0234_47.htm";
                                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                    }
                                                                    if (n == 18 && (questState.getQuestItemsCount(4750) > 0L || questState.getQuestItemsCount(4751) > 0L || questState.getQuestItemsCount(4752) > 0L || questState.getQuestItemsCount(171) > 0L)) {
                                                                        questState.giveItems(150, 1L);
                                                                        questState.giveItems(5011, 1L);
                                                                        if (questState.getQuestItemsCount(171) > 0L) {
                                                                            questState.takeItems(171, 1L);
                                                                        } else if (questState.getQuestItemsCount(4750) > 0L) {
                                                                            questState.takeItems(4750, 1L);
                                                                        } else if (questState.getQuestItemsCount(4751) > 0L) {
                                                                            questState.takeItems(4751, 1L);
                                                                        } else if (questState.getQuestItemsCount(4752) > 0L) {
                                                                            questState.takeItems(4752, 1L);
                                                                        }
                                                                        this.giveExtraReward(questState.getPlayer());
                                                                        questState.exitCurrentQuest(false);
                                                                        questState.playSound("ItemSound.quest_finish");
                                                                        string2 = "maestro_leorin_q0234_47.htm";
                                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                    }
                                                                    if (n == 19 && questState.getQuestItemsCount(2626) > 0L) {
                                                                        questState.giveItems(150, 1L);
                                                                        questState.giveItems(5011, 1L);
                                                                        questState.takeItems(2626, 1L);
                                                                        this.giveExtraReward(questState.getPlayer());
                                                                        questState.exitCurrentQuest(false);
                                                                        questState.playSound("ItemSound.quest_finish");
                                                                        string2 = "maestro_leorin_q0234_47.htm";
                                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                    }
                                                                    if (n == 41 && (questState.getQuestItemsCount(7883) > 0L || questState.getQuestItemsCount(8105) > 0L || questState.getQuestItemsCount(8106) > 0L || questState.getQuestItemsCount(8107) > 0L)) {
                                                                        questState.giveItems(150, 1L);
                                                                        questState.giveItems(5011, 1L);
                                                                        if (questState.getQuestItemsCount(7883) > 0L) {
                                                                            questState.takeItems(7883, 1L);
                                                                        } else if (questState.getQuestItemsCount(8105) > 0L) {
                                                                            questState.takeItems(8105, 1L);
                                                                        } else if (questState.getQuestItemsCount(8106) > 0L) {
                                                                            questState.takeItems(8106, 1L);
                                                                        } else if (questState.getQuestItemsCount(8107) > 0L) {
                                                                            questState.takeItems(8107, 1L);
                                                                        }
                                                                        this.giveExtraReward(questState.getPlayer());
                                                                        questState.exitCurrentQuest(false);
                                                                        questState.playSound("ItemSound.quest_finish");
                                                                        string2 = "maestro_leorin_q0234_47.htm";
                                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                    }
                                                                    if (n == 42 && (questState.getQuestItemsCount(7889) > 0L || questState.getQuestItemsCount(8117) > 0L || questState.getQuestItemsCount(8118) > 0L || questState.getQuestItemsCount(8119) > 0L)) {
                                                                        questState.giveItems(150, 1L);
                                                                        questState.giveItems(5011, 1L);
                                                                        if (questState.getQuestItemsCount(7889) > 0L) {
                                                                            questState.takeItems(7889, 1L);
                                                                        } else if (questState.getQuestItemsCount(8117) > 0L) {
                                                                            questState.takeItems(8117, 1L);
                                                                        } else if (questState.getQuestItemsCount(8118) > 0L) {
                                                                            questState.takeItems(8118, 1L);
                                                                        } else if (questState.getQuestItemsCount(8119) > 0L) {
                                                                            questState.takeItems(8119, 1L);
                                                                        }
                                                                        this.giveExtraReward(questState.getPlayer());
                                                                        questState.exitCurrentQuest(false);
                                                                        questState.playSound("ItemSound.quest_finish");
                                                                        string2 = "maestro_leorin_q0234_47.htm";
                                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                    }
                                                                    if (n == 43 && (questState.getQuestItemsCount(7901) > 0L || questState.getQuestItemsCount(8132) > 0L || questState.getQuestItemsCount(8133) > 0L || questState.getQuestItemsCount(8134) > 0L)) {
                                                                        questState.giveItems(150, 1L);
                                                                        questState.giveItems(5011, 1L);
                                                                        if (questState.getQuestItemsCount(7901) > 0L) {
                                                                            questState.takeItems(7901, 1L);
                                                                        } else if (questState.getQuestItemsCount(8132) > 0L) {
                                                                            questState.takeItems(8132, 1L);
                                                                        } else if (questState.getQuestItemsCount(8133) > 0L) {
                                                                            questState.takeItems(8133, 1L);
                                                                        } else if (questState.getQuestItemsCount(8134) > 0L) {
                                                                            questState.takeItems(8134, 1L);
                                                                        }
                                                                        this.giveExtraReward(questState.getPlayer());
                                                                        questState.exitCurrentQuest(false);
                                                                        questState.playSound("ItemSound.quest_finish");
                                                                        string2 = "maestro_leorin_q0234_47.htm";
                                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                    }
                                                                    if (n != 44 || questState.getQuestItemsCount(7893) <= 0L && questState.getQuestItemsCount(8144) <= 0L && questState.getQuestItemsCount(8145) <= 0L && questState.getQuestItemsCount(8146) <= 0L) break block1205;
                                                                    questState.giveItems(150, 1L);
                                                                    questState.giveItems(5011, 1L);
                                                                    if (questState.getQuestItemsCount(7893) > 0L) {
                                                                        questState.takeItems(7893, 1L);
                                                                    } else if (questState.getQuestItemsCount(8144) > 0L) {
                                                                        questState.takeItems(8144, 1L);
                                                                    } else if (questState.getQuestItemsCount(8145) > 0L) {
                                                                        questState.takeItems(8145, 1L);
                                                                    } else if (questState.getQuestItemsCount(8146) > 0L) {
                                                                        questState.takeItems(8146, 1L);
                                                                    }
                                                                    this.giveExtraReward(questState.getPlayer());
                                                                    questState.exitCurrentQuest(false);
                                                                    questState.playSound("ItemSound.quest_finish");
                                                                    string2 = "maestro_leorin_q0234_47.htm";
                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                    break block1205;
                                                                }
                                                                if (!string.equalsIgnoreCase("menu_select?ask=234&reply=25")) break block1232;
                                                                if (n == 11 && (questState.getQuestItemsCount(79) > 0L || questState.getQuestItemsCount(4717) > 0L || questState.getQuestItemsCount(4718) > 0L || questState.getQuestItemsCount(4719) > 0L)) {
                                                                    questState.giveItems(212, 1L);
                                                                    questState.giveItems(5011, 1L);
                                                                    if (questState.getQuestItemsCount(79) > 0L) {
                                                                        questState.takeItems(79, 1L);
                                                                    } else if (questState.getQuestItemsCount(4717) > 0L) {
                                                                        questState.takeItems(4717, 1L);
                                                                    } else if (questState.getQuestItemsCount(4718) > 0L) {
                                                                        questState.takeItems(4718, 1L);
                                                                    } else if (questState.getQuestItemsCount(4719) > 0L) {
                                                                        questState.takeItems(4719, 1L);
                                                                    }
                                                                    this.giveExtraReward(questState.getPlayer());
                                                                    questState.exitCurrentQuest(false);
                                                                    questState.playSound("ItemSound.quest_finish");
                                                                    string2 = "maestro_leorin_q0234_48.htm";
                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                }
                                                                if (n == 12 && (questState.getQuestItemsCount(4828) > 0L || questState.getQuestItemsCount(4829) > 0L || questState.getQuestItemsCount(4830) > 0L || questState.getQuestItemsCount(287) > 0L)) {
                                                                    questState.giveItems(212, 1L);
                                                                    questState.giveItems(5011, 1L);
                                                                    if (questState.getQuestItemsCount(287) > 0L) {
                                                                        questState.takeItems(287, 1L);
                                                                    } else if (questState.getQuestItemsCount(4828) > 0L) {
                                                                        questState.takeItems(4828, 1L);
                                                                    } else if (questState.getQuestItemsCount(4829) > 0L) {
                                                                        questState.takeItems(4829, 1L);
                                                                    } else if (questState.getQuestItemsCount(4830) > 0L) {
                                                                        questState.takeItems(4830, 1L);
                                                                    }
                                                                    this.giveExtraReward(questState.getPlayer());
                                                                    questState.exitCurrentQuest(false);
                                                                    questState.playSound("ItemSound.quest_finish");
                                                                    string2 = "maestro_leorin_q0234_48.htm";
                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                }
                                                                if (n == 13 && (questState.getQuestItemsCount(4858) > 0L || questState.getQuestItemsCount(4859) > 0L || questState.getQuestItemsCount(4860) > 0L || questState.getQuestItemsCount(97) > 0L)) {
                                                                    questState.giveItems(212, 1L);
                                                                    questState.giveItems(5011, 1L);
                                                                    if (questState.getQuestItemsCount(97) > 0L) {
                                                                        questState.takeItems(97, 1L);
                                                                    } else if (questState.getQuestItemsCount(4858) > 0L) {
                                                                        questState.takeItems(4858, 1L);
                                                                    } else if (questState.getQuestItemsCount(4859) > 0L) {
                                                                        questState.takeItems(4859, 1L);
                                                                    } else if (questState.getQuestItemsCount(4860) > 0L) {
                                                                        questState.takeItems(4860, 1L);
                                                                    }
                                                                    this.giveExtraReward(questState.getPlayer());
                                                                    questState.exitCurrentQuest(false);
                                                                    questState.playSound("ItemSound.quest_finish");
                                                                    string2 = "maestro_leorin_q0234_48.htm";
                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                }
                                                                if (n == 14 && (questState.getQuestItemsCount(4753) > 0L || questState.getQuestItemsCount(4754) > 0L || questState.getQuestItemsCount(4755) > 0L || questState.getQuestItemsCount(175) > 0L)) {
                                                                    questState.giveItems(212, 1L);
                                                                    questState.giveItems(5011, 1L);
                                                                    if (questState.getQuestItemsCount(175) > 0L) {
                                                                        questState.takeItems(175, 1L);
                                                                    } else if (questState.getQuestItemsCount(4753) > 0L) {
                                                                        questState.takeItems(4753, 1L);
                                                                    } else if (questState.getQuestItemsCount(4754) > 0L) {
                                                                        questState.takeItems(4754, 1L);
                                                                    } else if (questState.getQuestItemsCount(4755) > 0L) {
                                                                        questState.takeItems(4755, 1L);
                                                                    }
                                                                    this.giveExtraReward(questState.getPlayer());
                                                                    questState.exitCurrentQuest(false);
                                                                    questState.playSound("ItemSound.quest_finish");
                                                                    string2 = "maestro_leorin_q0234_48.htm";
                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                }
                                                                if (n == 15 && (questState.getQuestItemsCount(4900) > 0L || questState.getQuestItemsCount(4901) > 0L || questState.getQuestItemsCount(4902) > 0L || questState.getQuestItemsCount(210) > 0L)) {
                                                                    questState.giveItems(212, 1L);
                                                                    questState.giveItems(5011, 1L);
                                                                    if (questState.getQuestItemsCount(210) > 0L) {
                                                                        questState.takeItems(210, 1L);
                                                                    } else if (questState.getQuestItemsCount(4900) > 0L) {
                                                                        questState.takeItems(4900, 1L);
                                                                    } else if (questState.getQuestItemsCount(4901) > 0L) {
                                                                        questState.takeItems(4901, 1L);
                                                                    } else if (questState.getQuestItemsCount(4902) > 0L) {
                                                                        questState.takeItems(4902, 1L);
                                                                    }
                                                                    this.giveExtraReward(questState.getPlayer());
                                                                    questState.exitCurrentQuest(false);
                                                                    questState.playSound("ItemSound.quest_finish");
                                                                    string2 = "maestro_leorin_q0234_48.htm";
                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                }
                                                                if (n == 16 && (questState.getQuestItemsCount(4780) > 0L || questState.getQuestItemsCount(4781) > 0L || questState.getQuestItemsCount(4782) > 0L || questState.getQuestItemsCount(234) > 0L || questState.getQuestItemsCount(6359) > 0L)) {
                                                                    questState.giveItems(212, 1L);
                                                                    questState.giveItems(5011, 1L);
                                                                    if (questState.getQuestItemsCount(234) > 0L) {
                                                                        questState.takeItems(234, 1L);
                                                                    } else if (questState.getQuestItemsCount(4780) > 0L) {
                                                                        questState.takeItems(4780, 1L);
                                                                    } else if (questState.getQuestItemsCount(4781) > 0L) {
                                                                        questState.takeItems(4781, 1L);
                                                                    } else if (questState.getQuestItemsCount(4782) > 0L) {
                                                                        questState.takeItems(4782, 1L);
                                                                    } else if (questState.getQuestItemsCount(6359) > 0L) {
                                                                        questState.takeItems(6359, 1L);
                                                                    }
                                                                    this.giveExtraReward(questState.getPlayer());
                                                                    questState.exitCurrentQuest(false);
                                                                    questState.playSound("ItemSound.quest_finish");
                                                                    string2 = "maestro_leorin_q0234_48.htm";
                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                }
                                                                if (n == 17 && (questState.getQuestItemsCount(4804) > 0L || questState.getQuestItemsCount(4805) > 0L || questState.getQuestItemsCount(4806) > 0L || questState.getQuestItemsCount(268) > 0L)) {
                                                                    questState.giveItems(212, 1L);
                                                                    questState.giveItems(5011, 1L);
                                                                    if (questState.getQuestItemsCount(268) > 0L) {
                                                                        questState.takeItems(268, 1L);
                                                                    } else if (questState.getQuestItemsCount(4804) > 0L) {
                                                                        questState.takeItems(4804, 1L);
                                                                    } else if (questState.getQuestItemsCount(4805) > 0L) {
                                                                        questState.takeItems(4805, 1L);
                                                                    } else if (questState.getQuestItemsCount(4806) > 0L) {
                                                                        questState.takeItems(4806, 1L);
                                                                    }
                                                                    this.giveExtraReward(questState.getPlayer());
                                                                    questState.exitCurrentQuest(false);
                                                                    questState.playSound("ItemSound.quest_finish");
                                                                    string2 = "maestro_leorin_q0234_48.htm";
                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                }
                                                                if (n == 18 && (questState.getQuestItemsCount(4750) > 0L || questState.getQuestItemsCount(4751) > 0L || questState.getQuestItemsCount(4752) > 0L || questState.getQuestItemsCount(171) > 0L)) {
                                                                    questState.giveItems(212, 1L);
                                                                    questState.giveItems(5011, 1L);
                                                                    if (questState.getQuestItemsCount(171) > 0L) {
                                                                        questState.takeItems(171, 1L);
                                                                    } else if (questState.getQuestItemsCount(4750) > 0L) {
                                                                        questState.takeItems(4750, 1L);
                                                                    } else if (questState.getQuestItemsCount(4751) > 0L) {
                                                                        questState.takeItems(4751, 1L);
                                                                    } else if (questState.getQuestItemsCount(4752) > 0L) {
                                                                        questState.takeItems(4752, 1L);
                                                                    }
                                                                    this.giveExtraReward(questState.getPlayer());
                                                                    questState.exitCurrentQuest(false);
                                                                    questState.playSound("ItemSound.quest_finish");
                                                                    string2 = "maestro_leorin_q0234_48.htm";
                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                }
                                                                if (n == 19 && questState.getQuestItemsCount(2626) > 0L) {
                                                                    questState.giveItems(212, 1L);
                                                                    questState.giveItems(5011, 1L);
                                                                    questState.takeItems(2626, 1L);
                                                                    this.giveExtraReward(questState.getPlayer());
                                                                    questState.exitCurrentQuest(false);
                                                                    questState.playSound("ItemSound.quest_finish");
                                                                    string2 = "maestro_leorin_q0234_48.htm";
                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                }
                                                                if (n == 41 && (questState.getQuestItemsCount(7883) > 0L || questState.getQuestItemsCount(8105) > 0L || questState.getQuestItemsCount(8106) > 0L || questState.getQuestItemsCount(8107) > 0L)) {
                                                                    questState.giveItems(212, 1L);
                                                                    questState.giveItems(5011, 1L);
                                                                    if (questState.getQuestItemsCount(7883) > 0L) {
                                                                        questState.takeItems(7883, 1L);
                                                                    } else if (questState.getQuestItemsCount(8105) > 0L) {
                                                                        questState.takeItems(8105, 1L);
                                                                    } else if (questState.getQuestItemsCount(8106) > 0L) {
                                                                        questState.takeItems(8106, 1L);
                                                                    } else if (questState.getQuestItemsCount(8107) > 0L) {
                                                                        questState.takeItems(8107, 1L);
                                                                    }
                                                                    this.giveExtraReward(questState.getPlayer());
                                                                    questState.exitCurrentQuest(false);
                                                                    questState.playSound("ItemSound.quest_finish");
                                                                    string2 = "maestro_leorin_q0234_48.htm";
                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                }
                                                                if (n == 42 && (questState.getQuestItemsCount(7889) > 0L || questState.getQuestItemsCount(8117) > 0L || questState.getQuestItemsCount(8118) > 0L || questState.getQuestItemsCount(8119) > 0L)) {
                                                                    questState.giveItems(212, 1L);
                                                                    questState.giveItems(5011, 1L);
                                                                    if (questState.getQuestItemsCount(7889) > 0L) {
                                                                        questState.takeItems(7889, 1L);
                                                                    } else if (questState.getQuestItemsCount(8117) > 0L) {
                                                                        questState.takeItems(8117, 1L);
                                                                    } else if (questState.getQuestItemsCount(8118) > 0L) {
                                                                        questState.takeItems(8118, 1L);
                                                                    } else if (questState.getQuestItemsCount(8119) > 0L) {
                                                                        questState.takeItems(8119, 1L);
                                                                    }
                                                                    this.giveExtraReward(questState.getPlayer());
                                                                    questState.exitCurrentQuest(false);
                                                                    questState.playSound("ItemSound.quest_finish");
                                                                    string2 = "maestro_leorin_q0234_48.htm";
                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                }
                                                                if (n == 43 && (questState.getQuestItemsCount(7901) > 0L || questState.getQuestItemsCount(8132) > 0L || questState.getQuestItemsCount(8133) > 0L || questState.getQuestItemsCount(8134) > 0L)) {
                                                                    questState.giveItems(212, 1L);
                                                                    questState.giveItems(5011, 1L);
                                                                    if (questState.getQuestItemsCount(7901) > 0L) {
                                                                        questState.takeItems(7901, 1L);
                                                                    } else if (questState.getQuestItemsCount(8132) > 0L) {
                                                                        questState.takeItems(8132, 1L);
                                                                    } else if (questState.getQuestItemsCount(8133) > 0L) {
                                                                        questState.takeItems(8133, 1L);
                                                                    } else if (questState.getQuestItemsCount(8134) > 0L) {
                                                                        questState.takeItems(8134, 1L);
                                                                    }
                                                                    this.giveExtraReward(questState.getPlayer());
                                                                    questState.exitCurrentQuest(false);
                                                                    questState.playSound("ItemSound.quest_finish");
                                                                    string2 = "maestro_leorin_q0234_48.htm";
                                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                }
                                                                if (n != 44 || questState.getQuestItemsCount(7893) <= 0L && questState.getQuestItemsCount(8144) <= 0L && questState.getQuestItemsCount(8145) <= 0L && questState.getQuestItemsCount(8146) <= 0L) break block1205;
                                                                questState.giveItems(212, 1L);
                                                                questState.giveItems(5011, 1L);
                                                                if (questState.getQuestItemsCount(7893) > 0L) {
                                                                    questState.takeItems(7893, 1L);
                                                                } else if (questState.getQuestItemsCount(8144) > 0L) {
                                                                    questState.takeItems(8144, 1L);
                                                                } else if (questState.getQuestItemsCount(8145) > 0L) {
                                                                    questState.takeItems(8145, 1L);
                                                                } else if (questState.getQuestItemsCount(8146) > 0L) {
                                                                    questState.takeItems(8146, 1L);
                                                                }
                                                                this.giveExtraReward(questState.getPlayer());
                                                                questState.exitCurrentQuest(false);
                                                                questState.playSound("ItemSound.quest_finish");
                                                                string2 = "maestro_leorin_q0234_48.htm";
                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                                break block1205;
                                                            }
                                                            if (!string.equalsIgnoreCase("menu_select?ask=234&reply=26")) break block1233;
                                                            if (n == 11 && (questState.getQuestItemsCount(79) > 0L || questState.getQuestItemsCount(4717) > 0L || questState.getQuestItemsCount(4718) > 0L || questState.getQuestItemsCount(4719) > 0L)) {
                                                                questState.giveItems(235, 1L);
                                                                questState.giveItems(5011, 1L);
                                                                if (questState.getQuestItemsCount(79) > 0L) {
                                                                    questState.takeItems(79, 1L);
                                                                } else if (questState.getQuestItemsCount(4717) > 0L) {
                                                                    questState.takeItems(4717, 1L);
                                                                } else if (questState.getQuestItemsCount(4718) > 0L) {
                                                                    questState.takeItems(4718, 1L);
                                                                } else if (questState.getQuestItemsCount(4719) > 0L) {
                                                                    questState.takeItems(4719, 1L);
                                                                }
                                                                this.giveExtraReward(questState.getPlayer());
                                                                questState.exitCurrentQuest(false);
                                                                questState.playSound("ItemSound.quest_finish");
                                                                string2 = "maestro_leorin_q0234_49.htm";
                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                            }
                                                            if (n == 12 && (questState.getQuestItemsCount(4828) > 0L || questState.getQuestItemsCount(4829) > 0L || questState.getQuestItemsCount(4830) > 0L || questState.getQuestItemsCount(287) > 0L)) {
                                                                questState.giveItems(235, 1L);
                                                                questState.giveItems(5011, 1L);
                                                                if (questState.getQuestItemsCount(287) > 0L) {
                                                                    questState.takeItems(287, 1L);
                                                                } else if (questState.getQuestItemsCount(4828) > 0L) {
                                                                    questState.takeItems(4828, 1L);
                                                                } else if (questState.getQuestItemsCount(4829) > 0L) {
                                                                    questState.takeItems(4829, 1L);
                                                                } else if (questState.getQuestItemsCount(4830) > 0L) {
                                                                    questState.takeItems(4830, 1L);
                                                                }
                                                                this.giveExtraReward(questState.getPlayer());
                                                                questState.exitCurrentQuest(false);
                                                                questState.playSound("ItemSound.quest_finish");
                                                                string2 = "maestro_leorin_q0234_49.htm";
                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                            }
                                                            if (n == 13 && (questState.getQuestItemsCount(4858) > 0L || questState.getQuestItemsCount(4859) > 0L || questState.getQuestItemsCount(4860) > 0L || questState.getQuestItemsCount(97) > 0L)) {
                                                                questState.giveItems(235, 1L);
                                                                questState.giveItems(5011, 1L);
                                                                if (questState.getQuestItemsCount(97) > 0L) {
                                                                    questState.takeItems(97, 1L);
                                                                } else if (questState.getQuestItemsCount(4858) > 0L) {
                                                                    questState.takeItems(4858, 1L);
                                                                } else if (questState.getQuestItemsCount(4859) > 0L) {
                                                                    questState.takeItems(4859, 1L);
                                                                } else if (questState.getQuestItemsCount(4860) > 0L) {
                                                                    questState.takeItems(4860, 1L);
                                                                }
                                                                this.giveExtraReward(questState.getPlayer());
                                                                questState.exitCurrentQuest(false);
                                                                questState.playSound("ItemSound.quest_finish");
                                                                string2 = "maestro_leorin_q0234_49.htm";
                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                            }
                                                            if (n == 14 && (questState.getQuestItemsCount(4753) > 0L || questState.getQuestItemsCount(4754) > 0L || questState.getQuestItemsCount(4755) > 0L || questState.getQuestItemsCount(175) > 0L)) {
                                                                questState.giveItems(235, 1L);
                                                                questState.giveItems(5011, 1L);
                                                                if (questState.getQuestItemsCount(175) > 0L) {
                                                                    questState.takeItems(175, 1L);
                                                                } else if (questState.getQuestItemsCount(4753) > 0L) {
                                                                    questState.takeItems(4753, 1L);
                                                                } else if (questState.getQuestItemsCount(4754) > 0L) {
                                                                    questState.takeItems(4754, 1L);
                                                                } else if (questState.getQuestItemsCount(4755) > 0L) {
                                                                    questState.takeItems(4755, 1L);
                                                                }
                                                                this.giveExtraReward(questState.getPlayer());
                                                                questState.exitCurrentQuest(false);
                                                                questState.playSound("ItemSound.quest_finish");
                                                                string2 = "maestro_leorin_q0234_49.htm";
                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                            }
                                                            if (n == 15 && (questState.getQuestItemsCount(4900) > 0L || questState.getQuestItemsCount(4901) > 0L || questState.getQuestItemsCount(4902) > 0L || questState.getQuestItemsCount(210) > 0L)) {
                                                                questState.giveItems(235, 1L);
                                                                questState.giveItems(5011, 1L);
                                                                if (questState.getQuestItemsCount(210) > 0L) {
                                                                    questState.takeItems(210, 1L);
                                                                } else if (questState.getQuestItemsCount(4900) > 0L) {
                                                                    questState.takeItems(4900, 1L);
                                                                } else if (questState.getQuestItemsCount(4901) > 0L) {
                                                                    questState.takeItems(4901, 1L);
                                                                } else if (questState.getQuestItemsCount(4902) > 0L) {
                                                                    questState.takeItems(4902, 1L);
                                                                }
                                                                this.giveExtraReward(questState.getPlayer());
                                                                questState.exitCurrentQuest(false);
                                                                questState.playSound("ItemSound.quest_finish");
                                                                string2 = "maestro_leorin_q0234_49.htm";
                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                            }
                                                            if (n == 16 && (questState.getQuestItemsCount(4780) > 0L || questState.getQuestItemsCount(4781) > 0L || questState.getQuestItemsCount(4782) > 0L || questState.getQuestItemsCount(234) > 0L || questState.getQuestItemsCount(6359) > 0L)) {
                                                                questState.giveItems(235, 1L);
                                                                questState.giveItems(5011, 1L);
                                                                if (questState.getQuestItemsCount(234) > 0L) {
                                                                    questState.takeItems(234, 1L);
                                                                } else if (questState.getQuestItemsCount(4780) > 0L) {
                                                                    questState.takeItems(4780, 1L);
                                                                } else if (questState.getQuestItemsCount(4781) > 0L) {
                                                                    questState.takeItems(4781, 1L);
                                                                } else if (questState.getQuestItemsCount(4782) > 0L) {
                                                                    questState.takeItems(4782, 1L);
                                                                } else if (questState.getQuestItemsCount(6359) > 0L) {
                                                                    questState.takeItems(6359, 1L);
                                                                }
                                                                this.giveExtraReward(questState.getPlayer());
                                                                questState.exitCurrentQuest(false);
                                                                questState.playSound("ItemSound.quest_finish");
                                                                string2 = "maestro_leorin_q0234_49.htm";
                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                            }
                                                            if (n == 17 && (questState.getQuestItemsCount(4804) > 0L || questState.getQuestItemsCount(4805) > 0L || questState.getQuestItemsCount(4806) > 0L || questState.getQuestItemsCount(268) > 0L)) {
                                                                questState.giveItems(235, 1L);
                                                                questState.giveItems(5011, 1L);
                                                                if (questState.getQuestItemsCount(268) > 0L) {
                                                                    questState.takeItems(268, 1L);
                                                                } else if (questState.getQuestItemsCount(4804) > 0L) {
                                                                    questState.takeItems(4804, 1L);
                                                                } else if (questState.getQuestItemsCount(4805) > 0L) {
                                                                    questState.takeItems(4805, 1L);
                                                                } else if (questState.getQuestItemsCount(4806) > 0L) {
                                                                    questState.takeItems(4806, 1L);
                                                                }
                                                                this.giveExtraReward(questState.getPlayer());
                                                                questState.exitCurrentQuest(false);
                                                                questState.playSound("ItemSound.quest_finish");
                                                                string2 = "maestro_leorin_q0234_49.htm";
                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                            }
                                                            if (n == 18 && (questState.getQuestItemsCount(4750) > 0L || questState.getQuestItemsCount(4751) > 0L || questState.getQuestItemsCount(4752) > 0L || questState.getQuestItemsCount(171) > 0L)) {
                                                                questState.giveItems(235, 1L);
                                                                questState.giveItems(5011, 1L);
                                                                if (questState.getQuestItemsCount(171) > 0L) {
                                                                    questState.takeItems(171, 1L);
                                                                } else if (questState.getQuestItemsCount(4750) > 0L) {
                                                                    questState.takeItems(4750, 1L);
                                                                } else if (questState.getQuestItemsCount(4751) > 0L) {
                                                                    questState.takeItems(4751, 1L);
                                                                } else if (questState.getQuestItemsCount(4752) > 0L) {
                                                                    questState.takeItems(4752, 1L);
                                                                }
                                                                this.giveExtraReward(questState.getPlayer());
                                                                questState.exitCurrentQuest(false);
                                                                questState.playSound("ItemSound.quest_finish");
                                                                string2 = "maestro_leorin_q0234_49.htm";
                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                            }
                                                            if (n == 19 && questState.getQuestItemsCount(2626) > 0L) {
                                                                questState.giveItems(235, 1L);
                                                                questState.giveItems(5011, 1L);
                                                                questState.takeItems(2626, 1L);
                                                                this.giveExtraReward(questState.getPlayer());
                                                                questState.exitCurrentQuest(false);
                                                                questState.playSound("ItemSound.quest_finish");
                                                                string2 = "maestro_leorin_q0234_49.htm";
                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                            }
                                                            if (n == 41 && (questState.getQuestItemsCount(7883) > 0L || questState.getQuestItemsCount(8105) > 0L || questState.getQuestItemsCount(8106) > 0L || questState.getQuestItemsCount(8107) > 0L)) {
                                                                questState.giveItems(235, 1L);
                                                                questState.giveItems(5011, 1L);
                                                                if (questState.getQuestItemsCount(7883) > 0L) {
                                                                    questState.takeItems(7883, 1L);
                                                                } else if (questState.getQuestItemsCount(8105) > 0L) {
                                                                    questState.takeItems(8105, 1L);
                                                                } else if (questState.getQuestItemsCount(8106) > 0L) {
                                                                    questState.takeItems(8106, 1L);
                                                                } else if (questState.getQuestItemsCount(8107) > 0L) {
                                                                    questState.takeItems(8107, 1L);
                                                                }
                                                                this.giveExtraReward(questState.getPlayer());
                                                                questState.exitCurrentQuest(false);
                                                                questState.playSound("ItemSound.quest_finish");
                                                                string2 = "maestro_leorin_q0234_49.htm";
                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                            }
                                                            if (n == 42 && (questState.getQuestItemsCount(7889) > 0L || questState.getQuestItemsCount(8117) > 0L || questState.getQuestItemsCount(8118) > 0L || questState.getQuestItemsCount(8119) > 0L)) {
                                                                questState.giveItems(235, 1L);
                                                                questState.giveItems(5011, 1L);
                                                                if (questState.getQuestItemsCount(7889) > 0L) {
                                                                    questState.takeItems(7889, 1L);
                                                                } else if (questState.getQuestItemsCount(8117) > 0L) {
                                                                    questState.takeItems(8117, 1L);
                                                                } else if (questState.getQuestItemsCount(8118) > 0L) {
                                                                    questState.takeItems(8118, 1L);
                                                                } else if (questState.getQuestItemsCount(8119) > 0L) {
                                                                    questState.takeItems(8119, 1L);
                                                                }
                                                                this.giveExtraReward(questState.getPlayer());
                                                                questState.exitCurrentQuest(false);
                                                                questState.playSound("ItemSound.quest_finish");
                                                                string2 = "maestro_leorin_q0234_49.htm";
                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                            }
                                                            if (n == 43 && (questState.getQuestItemsCount(7901) > 0L || questState.getQuestItemsCount(8132) > 0L || questState.getQuestItemsCount(8133) > 0L || questState.getQuestItemsCount(8134) > 0L)) {
                                                                questState.giveItems(235, 1L);
                                                                questState.giveItems(5011, 1L);
                                                                if (questState.getQuestItemsCount(7901) > 0L) {
                                                                    questState.takeItems(7901, 1L);
                                                                } else if (questState.getQuestItemsCount(8132) > 0L) {
                                                                    questState.takeItems(8132, 1L);
                                                                } else if (questState.getQuestItemsCount(8133) > 0L) {
                                                                    questState.takeItems(8133, 1L);
                                                                } else if (questState.getQuestItemsCount(8134) > 0L) {
                                                                    questState.takeItems(8134, 1L);
                                                                }
                                                                this.giveExtraReward(questState.getPlayer());
                                                                questState.exitCurrentQuest(false);
                                                                questState.playSound("ItemSound.quest_finish");
                                                                string2 = "maestro_leorin_q0234_49.htm";
                                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                            }
                                                            if (n != 44 || questState.getQuestItemsCount(7893) <= 0L && questState.getQuestItemsCount(8144) <= 0L && questState.getQuestItemsCount(8145) <= 0L && questState.getQuestItemsCount(8146) <= 0L) break block1205;
                                                            questState.giveItems(235, 1L);
                                                            questState.giveItems(5011, 1L);
                                                            if (questState.getQuestItemsCount(7893) > 0L) {
                                                                questState.takeItems(7893, 1L);
                                                            } else if (questState.getQuestItemsCount(8144) > 0L) {
                                                                questState.takeItems(8144, 1L);
                                                            } else if (questState.getQuestItemsCount(8145) > 0L) {
                                                                questState.takeItems(8145, 1L);
                                                            } else if (questState.getQuestItemsCount(8146) > 0L) {
                                                                questState.takeItems(8146, 1L);
                                                            }
                                                            this.giveExtraReward(questState.getPlayer());
                                                            questState.exitCurrentQuest(false);
                                                            questState.playSound("ItemSound.quest_finish");
                                                            string2 = "maestro_leorin_q0234_49.htm";
                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                            break block1205;
                                                        }
                                                        if (!string.equalsIgnoreCase("menu_select?ask=234&reply=27")) break block1234;
                                                        if (n == 11 && (questState.getQuestItemsCount(79) > 0L || questState.getQuestItemsCount(4717) > 0L || questState.getQuestItemsCount(4718) > 0L || questState.getQuestItemsCount(4719) > 0L)) {
                                                            questState.giveItems(269, 1L);
                                                            questState.giveItems(5011, 1L);
                                                            if (questState.getQuestItemsCount(79) > 0L) {
                                                                questState.takeItems(79, 1L);
                                                            } else if (questState.getQuestItemsCount(4717) > 0L) {
                                                                questState.takeItems(4717, 1L);
                                                            } else if (questState.getQuestItemsCount(4718) > 0L) {
                                                                questState.takeItems(4718, 1L);
                                                            } else if (questState.getQuestItemsCount(4719) > 0L) {
                                                                questState.takeItems(4719, 1L);
                                                            }
                                                            this.giveExtraReward(questState.getPlayer());
                                                            questState.exitCurrentQuest(false);
                                                            questState.playSound("ItemSound.quest_finish");
                                                            string2 = "maestro_leorin_q0234_50.htm";
                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                        }
                                                        if (n == 12 && (questState.getQuestItemsCount(4828) > 0L || questState.getQuestItemsCount(4829) > 0L || questState.getQuestItemsCount(4830) > 0L || questState.getQuestItemsCount(287) > 0L)) {
                                                            questState.giveItems(269, 1L);
                                                            questState.giveItems(5011, 1L);
                                                            if (questState.getQuestItemsCount(287) > 0L) {
                                                                questState.takeItems(287, 1L);
                                                            } else if (questState.getQuestItemsCount(4828) > 0L) {
                                                                questState.takeItems(4828, 1L);
                                                            } else if (questState.getQuestItemsCount(4829) > 0L) {
                                                                questState.takeItems(4829, 1L);
                                                            } else if (questState.getQuestItemsCount(4830) > 0L) {
                                                                questState.takeItems(4830, 1L);
                                                            }
                                                            this.giveExtraReward(questState.getPlayer());
                                                            questState.exitCurrentQuest(false);
                                                            questState.playSound("ItemSound.quest_finish");
                                                            string2 = "maestro_leorin_q0234_50.htm";
                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                        }
                                                        if (n == 13 && (questState.getQuestItemsCount(4858) > 0L || questState.getQuestItemsCount(4859) > 0L || questState.getQuestItemsCount(4860) > 0L || questState.getQuestItemsCount(97) > 0L)) {
                                                            questState.giveItems(269, 1L);
                                                            questState.giveItems(5011, 1L);
                                                            if (questState.getQuestItemsCount(97) > 0L) {
                                                                questState.takeItems(97, 1L);
                                                            } else if (questState.getQuestItemsCount(4858) > 0L) {
                                                                questState.takeItems(4858, 1L);
                                                            } else if (questState.getQuestItemsCount(4859) > 0L) {
                                                                questState.takeItems(4859, 1L);
                                                            } else if (questState.getQuestItemsCount(4860) > 0L) {
                                                                questState.takeItems(4860, 1L);
                                                            }
                                                            this.giveExtraReward(questState.getPlayer());
                                                            questState.exitCurrentQuest(false);
                                                            questState.playSound("ItemSound.quest_finish");
                                                            string2 = "maestro_leorin_q0234_50.htm";
                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                        }
                                                        if (n == 14 && (questState.getQuestItemsCount(4753) > 0L || questState.getQuestItemsCount(4754) > 0L || questState.getQuestItemsCount(4755) > 0L || questState.getQuestItemsCount(175) > 0L)) {
                                                            questState.giveItems(269, 1L);
                                                            questState.giveItems(5011, 1L);
                                                            if (questState.getQuestItemsCount(175) > 0L) {
                                                                questState.takeItems(175, 1L);
                                                            } else if (questState.getQuestItemsCount(4753) > 0L) {
                                                                questState.takeItems(4753, 1L);
                                                            } else if (questState.getQuestItemsCount(4754) > 0L) {
                                                                questState.takeItems(4754, 1L);
                                                            } else if (questState.getQuestItemsCount(4755) > 0L) {
                                                                questState.takeItems(4755, 1L);
                                                            }
                                                            this.giveExtraReward(questState.getPlayer());
                                                            questState.exitCurrentQuest(false);
                                                            questState.playSound("ItemSound.quest_finish");
                                                            string2 = "maestro_leorin_q0234_50.htm";
                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                        }
                                                        if (n == 15 && (questState.getQuestItemsCount(4900) > 0L || questState.getQuestItemsCount(4901) > 0L || questState.getQuestItemsCount(4902) > 0L || questState.getQuestItemsCount(210) > 0L)) {
                                                            questState.giveItems(269, 1L);
                                                            questState.giveItems(5011, 1L);
                                                            if (questState.getQuestItemsCount(210) > 0L) {
                                                                questState.takeItems(210, 1L);
                                                            } else if (questState.getQuestItemsCount(4900) > 0L) {
                                                                questState.takeItems(4900, 1L);
                                                            } else if (questState.getQuestItemsCount(4901) > 0L) {
                                                                questState.takeItems(4901, 1L);
                                                            } else if (questState.getQuestItemsCount(4902) > 0L) {
                                                                questState.takeItems(4902, 1L);
                                                            }
                                                            this.giveExtraReward(questState.getPlayer());
                                                            questState.exitCurrentQuest(false);
                                                            questState.playSound("ItemSound.quest_finish");
                                                            string2 = "maestro_leorin_q0234_50.htm";
                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                        }
                                                        if (n == 16 && (questState.getQuestItemsCount(4780) > 0L || questState.getQuestItemsCount(4781) > 0L || questState.getQuestItemsCount(4782) > 0L || questState.getQuestItemsCount(234) > 0L || questState.getQuestItemsCount(6359) > 0L)) {
                                                            questState.giveItems(269, 1L);
                                                            questState.giveItems(5011, 1L);
                                                            if (questState.getQuestItemsCount(234) > 0L) {
                                                                questState.takeItems(234, 1L);
                                                            } else if (questState.getQuestItemsCount(4780) > 0L) {
                                                                questState.takeItems(4780, 1L);
                                                            } else if (questState.getQuestItemsCount(4781) > 0L) {
                                                                questState.takeItems(4781, 1L);
                                                            } else if (questState.getQuestItemsCount(4782) > 0L) {
                                                                questState.takeItems(4782, 1L);
                                                            } else if (questState.getQuestItemsCount(6359) > 0L) {
                                                                questState.takeItems(6359, 1L);
                                                            }
                                                            this.giveExtraReward(questState.getPlayer());
                                                            questState.exitCurrentQuest(false);
                                                            questState.playSound("ItemSound.quest_finish");
                                                            string2 = "maestro_leorin_q0234_50.htm";
                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                        }
                                                        if (n == 17 && (questState.getQuestItemsCount(4804) > 0L || questState.getQuestItemsCount(4805) > 0L || questState.getQuestItemsCount(4806) > 0L || questState.getQuestItemsCount(268) > 0L)) {
                                                            questState.giveItems(269, 1L);
                                                            questState.giveItems(5011, 1L);
                                                            if (questState.getQuestItemsCount(268) > 0L) {
                                                                questState.takeItems(268, 1L);
                                                            } else if (questState.getQuestItemsCount(4804) > 0L) {
                                                                questState.takeItems(4804, 1L);
                                                            } else if (questState.getQuestItemsCount(4805) > 0L) {
                                                                questState.takeItems(4805, 1L);
                                                            } else if (questState.getQuestItemsCount(4806) > 0L) {
                                                                questState.takeItems(4806, 1L);
                                                            }
                                                            this.giveExtraReward(questState.getPlayer());
                                                            questState.exitCurrentQuest(false);
                                                            questState.playSound("ItemSound.quest_finish");
                                                            string2 = "maestro_leorin_q0234_50.htm";
                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                        }
                                                        if (n == 18 && (questState.getQuestItemsCount(4750) > 0L || questState.getQuestItemsCount(4751) > 0L || questState.getQuestItemsCount(4752) > 0L || questState.getQuestItemsCount(171) > 0L)) {
                                                            questState.giveItems(269, 1L);
                                                            questState.giveItems(5011, 1L);
                                                            if (questState.getQuestItemsCount(171) > 0L) {
                                                                questState.takeItems(171, 1L);
                                                            } else if (questState.getQuestItemsCount(4750) > 0L) {
                                                                questState.takeItems(4750, 1L);
                                                            } else if (questState.getQuestItemsCount(4751) > 0L) {
                                                                questState.takeItems(4751, 1L);
                                                            } else if (questState.getQuestItemsCount(4752) > 0L) {
                                                                questState.takeItems(4752, 1L);
                                                            }
                                                            this.giveExtraReward(questState.getPlayer());
                                                            questState.exitCurrentQuest(false);
                                                            questState.playSound("ItemSound.quest_finish");
                                                            string2 = "maestro_leorin_q0234_50.htm";
                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                        }
                                                        if (n == 19 && questState.getQuestItemsCount(2626) > 0L) {
                                                            questState.giveItems(269, 1L);
                                                            questState.giveItems(5011, 1L);
                                                            questState.takeItems(2626, 1L);
                                                            this.giveExtraReward(questState.getPlayer());
                                                            questState.exitCurrentQuest(false);
                                                            questState.playSound("ItemSound.quest_finish");
                                                            string2 = "maestro_leorin_q0234_50.htm";
                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                        }
                                                        if (n == 41 && (questState.getQuestItemsCount(7883) > 0L || questState.getQuestItemsCount(8105) > 0L || questState.getQuestItemsCount(8106) > 0L || questState.getQuestItemsCount(8107) > 0L)) {
                                                            questState.giveItems(269, 1L);
                                                            questState.giveItems(5011, 1L);
                                                            if (questState.getQuestItemsCount(7883) > 0L) {
                                                                questState.takeItems(7883, 1L);
                                                            } else if (questState.getQuestItemsCount(8105) > 0L) {
                                                                questState.takeItems(8105, 1L);
                                                            } else if (questState.getQuestItemsCount(8106) > 0L) {
                                                                questState.takeItems(8106, 1L);
                                                            } else if (questState.getQuestItemsCount(8107) > 0L) {
                                                                questState.takeItems(8107, 1L);
                                                            }
                                                            this.giveExtraReward(questState.getPlayer());
                                                            questState.exitCurrentQuest(false);
                                                            questState.playSound("ItemSound.quest_finish");
                                                            string2 = "maestro_leorin_q0234_50.htm";
                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                        }
                                                        if (n == 42 && (questState.getQuestItemsCount(7889) > 0L || questState.getQuestItemsCount(8117) > 0L || questState.getQuestItemsCount(8118) > 0L || questState.getQuestItemsCount(8119) > 0L)) {
                                                            questState.giveItems(269, 1L);
                                                            questState.giveItems(5011, 1L);
                                                            if (questState.getQuestItemsCount(7889) > 0L) {
                                                                questState.takeItems(7889, 1L);
                                                            } else if (questState.getQuestItemsCount(8117) > 0L) {
                                                                questState.takeItems(8117, 1L);
                                                            } else if (questState.getQuestItemsCount(8118) > 0L) {
                                                                questState.takeItems(8118, 1L);
                                                            } else if (questState.getQuestItemsCount(8119) > 0L) {
                                                                questState.takeItems(8119, 1L);
                                                            }
                                                            this.giveExtraReward(questState.getPlayer());
                                                            questState.exitCurrentQuest(false);
                                                            questState.playSound("ItemSound.quest_finish");
                                                            string2 = "maestro_leorin_q0234_50.htm";
                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                        }
                                                        if (n == 43 && (questState.getQuestItemsCount(7901) > 0L || questState.getQuestItemsCount(8132) > 0L || questState.getQuestItemsCount(8133) > 0L || questState.getQuestItemsCount(8134) > 0L)) {
                                                            questState.giveItems(269, 1L);
                                                            questState.giveItems(5011, 1L);
                                                            if (questState.getQuestItemsCount(7901) > 0L) {
                                                                questState.takeItems(7901, 1L);
                                                            } else if (questState.getQuestItemsCount(8132) > 0L) {
                                                                questState.takeItems(8132, 1L);
                                                            } else if (questState.getQuestItemsCount(8133) > 0L) {
                                                                questState.takeItems(8133, 1L);
                                                            } else if (questState.getQuestItemsCount(8134) > 0L) {
                                                                questState.takeItems(8134, 1L);
                                                            }
                                                            this.giveExtraReward(questState.getPlayer());
                                                            questState.exitCurrentQuest(false);
                                                            questState.playSound("ItemSound.quest_finish");
                                                            string2 = "maestro_leorin_q0234_50.htm";
                                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                        }
                                                        if (n != 44 || questState.getQuestItemsCount(7893) <= 0L && questState.getQuestItemsCount(8144) <= 0L && questState.getQuestItemsCount(8145) <= 0L && questState.getQuestItemsCount(8146) <= 0L) break block1205;
                                                        questState.giveItems(269, 1L);
                                                        questState.giveItems(5011, 1L);
                                                        if (questState.getQuestItemsCount(7893) > 0L) {
                                                            questState.takeItems(7893, 1L);
                                                        } else if (questState.getQuestItemsCount(8144) > 0L) {
                                                            questState.takeItems(8144, 1L);
                                                        } else if (questState.getQuestItemsCount(8145) > 0L) {
                                                            questState.takeItems(8145, 1L);
                                                        } else if (questState.getQuestItemsCount(8146) > 0L) {
                                                            questState.takeItems(8146, 1L);
                                                        }
                                                        this.giveExtraReward(questState.getPlayer());
                                                        questState.exitCurrentQuest(false);
                                                        questState.playSound("ItemSound.quest_finish");
                                                        string2 = "maestro_leorin_q0234_50.htm";
                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                        break block1205;
                                                    }
                                                    if (!string.equalsIgnoreCase("menu_select?ask=234&reply=28")) break block1235;
                                                    if (n == 11 && (questState.getQuestItemsCount(79) > 0L || questState.getQuestItemsCount(4717) > 0L || questState.getQuestItemsCount(4718) > 0L || questState.getQuestItemsCount(4719) > 0L)) {
                                                        questState.giveItems(2504, 1L);
                                                        questState.giveItems(5011, 1L);
                                                        if (questState.getQuestItemsCount(79) > 0L) {
                                                            questState.takeItems(79, 1L);
                                                        } else if (questState.getQuestItemsCount(4717) > 0L) {
                                                            questState.takeItems(4717, 1L);
                                                        } else if (questState.getQuestItemsCount(4718) > 0L) {
                                                            questState.takeItems(4718, 1L);
                                                        } else if (questState.getQuestItemsCount(4719) > 0L) {
                                                            questState.takeItems(4719, 1L);
                                                        }
                                                        this.giveExtraReward(questState.getPlayer());
                                                        questState.exitCurrentQuest(false);
                                                        questState.playSound("ItemSound.quest_finish");
                                                        string2 = "maestro_leorin_q0234_51.htm";
                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                    }
                                                    if (n == 12 && (questState.getQuestItemsCount(4828) > 0L || questState.getQuestItemsCount(4829) > 0L || questState.getQuestItemsCount(4830) > 0L || questState.getQuestItemsCount(287) > 0L)) {
                                                        questState.giveItems(2504, 1L);
                                                        questState.giveItems(5011, 1L);
                                                        if (questState.getQuestItemsCount(287) > 0L) {
                                                            questState.takeItems(287, 1L);
                                                        } else if (questState.getQuestItemsCount(4828) > 0L) {
                                                            questState.takeItems(4828, 1L);
                                                        } else if (questState.getQuestItemsCount(4829) > 0L) {
                                                            questState.takeItems(4829, 1L);
                                                        } else if (questState.getQuestItemsCount(4830) > 0L) {
                                                            questState.takeItems(4830, 1L);
                                                        }
                                                        this.giveExtraReward(questState.getPlayer());
                                                        questState.exitCurrentQuest(false);
                                                        questState.playSound("ItemSound.quest_finish");
                                                        string2 = "maestro_leorin_q0234_51.htm";
                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                    }
                                                    if (n == 13 && (questState.getQuestItemsCount(4858) > 0L || questState.getQuestItemsCount(4859) > 0L || questState.getQuestItemsCount(4860) > 0L || questState.getQuestItemsCount(97) > 0L)) {
                                                        questState.giveItems(2504, 1L);
                                                        questState.giveItems(5011, 1L);
                                                        if (questState.getQuestItemsCount(97) > 0L) {
                                                            questState.takeItems(97, 1L);
                                                        } else if (questState.getQuestItemsCount(4858) > 0L) {
                                                            questState.takeItems(4858, 1L);
                                                        } else if (questState.getQuestItemsCount(4859) > 0L) {
                                                            questState.takeItems(4859, 1L);
                                                        } else if (questState.getQuestItemsCount(4860) > 0L) {
                                                            questState.takeItems(4860, 1L);
                                                        }
                                                        this.giveExtraReward(questState.getPlayer());
                                                        questState.exitCurrentQuest(false);
                                                        questState.playSound("ItemSound.quest_finish");
                                                        string2 = "maestro_leorin_q0234_51.htm";
                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                    }
                                                    if (n == 14 && (questState.getQuestItemsCount(4753) > 0L || questState.getQuestItemsCount(4754) > 0L || questState.getQuestItemsCount(4755) > 0L || questState.getQuestItemsCount(175) > 0L)) {
                                                        questState.giveItems(2504, 1L);
                                                        questState.giveItems(5011, 1L);
                                                        if (questState.getQuestItemsCount(175) > 0L) {
                                                            questState.takeItems(175, 1L);
                                                        } else if (questState.getQuestItemsCount(4753) > 0L) {
                                                            questState.takeItems(4753, 1L);
                                                        } else if (questState.getQuestItemsCount(4754) > 0L) {
                                                            questState.takeItems(4754, 1L);
                                                        } else if (questState.getQuestItemsCount(4755) > 0L) {
                                                            questState.takeItems(4755, 1L);
                                                        }
                                                        this.giveExtraReward(questState.getPlayer());
                                                        questState.exitCurrentQuest(false);
                                                        questState.playSound("ItemSound.quest_finish");
                                                        string2 = "maestro_leorin_q0234_51.htm";
                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                    }
                                                    if (n == 15 && (questState.getQuestItemsCount(4900) > 0L || questState.getQuestItemsCount(4901) > 0L || questState.getQuestItemsCount(4902) > 0L || questState.getQuestItemsCount(210) > 0L)) {
                                                        questState.giveItems(2504, 1L);
                                                        questState.giveItems(5011, 1L);
                                                        if (questState.getQuestItemsCount(210) > 0L) {
                                                            questState.takeItems(210, 1L);
                                                        } else if (questState.getQuestItemsCount(4900) > 0L) {
                                                            questState.takeItems(4900, 1L);
                                                        } else if (questState.getQuestItemsCount(4901) > 0L) {
                                                            questState.takeItems(4901, 1L);
                                                        } else if (questState.getQuestItemsCount(4902) > 0L) {
                                                            questState.takeItems(4902, 1L);
                                                        }
                                                        this.giveExtraReward(questState.getPlayer());
                                                        questState.exitCurrentQuest(false);
                                                        questState.playSound("ItemSound.quest_finish");
                                                        string2 = "maestro_leorin_q0234_51.htm";
                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                    }
                                                    if (n == 16 && (questState.getQuestItemsCount(4780) > 0L || questState.getQuestItemsCount(4781) > 0L || questState.getQuestItemsCount(4782) > 0L || questState.getQuestItemsCount(234) > 0L || questState.getQuestItemsCount(6359) > 0L)) {
                                                        questState.giveItems(2504, 1L);
                                                        questState.giveItems(5011, 1L);
                                                        if (questState.getQuestItemsCount(234) > 0L) {
                                                            questState.takeItems(234, 1L);
                                                        } else if (questState.getQuestItemsCount(4780) > 0L) {
                                                            questState.takeItems(4780, 1L);
                                                        } else if (questState.getQuestItemsCount(4781) > 0L) {
                                                            questState.takeItems(4781, 1L);
                                                        } else if (questState.getQuestItemsCount(4782) > 0L) {
                                                            questState.takeItems(4782, 1L);
                                                        } else if (questState.getQuestItemsCount(6359) > 0L) {
                                                            questState.takeItems(6359, 1L);
                                                        }
                                                        this.giveExtraReward(questState.getPlayer());
                                                        questState.exitCurrentQuest(false);
                                                        questState.playSound("ItemSound.quest_finish");
                                                        string2 = "maestro_leorin_q0234_51.htm";
                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                    }
                                                    if (n == 17 && (questState.getQuestItemsCount(4804) > 0L || questState.getQuestItemsCount(4805) > 0L || questState.getQuestItemsCount(4806) > 0L || questState.getQuestItemsCount(268) > 0L)) {
                                                        questState.giveItems(2504, 1L);
                                                        questState.giveItems(5011, 1L);
                                                        if (questState.getQuestItemsCount(268) > 0L) {
                                                            questState.takeItems(268, 1L);
                                                        } else if (questState.getQuestItemsCount(4804) > 0L) {
                                                            questState.takeItems(4804, 1L);
                                                        } else if (questState.getQuestItemsCount(4805) > 0L) {
                                                            questState.takeItems(4805, 1L);
                                                        } else if (questState.getQuestItemsCount(4806) > 0L) {
                                                            questState.takeItems(4806, 1L);
                                                        }
                                                        this.giveExtraReward(questState.getPlayer());
                                                        questState.exitCurrentQuest(false);
                                                        questState.playSound("ItemSound.quest_finish");
                                                        string2 = "maestro_leorin_q0234_51.htm";
                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                    }
                                                    if (n == 18 && (questState.getQuestItemsCount(4750) > 0L || questState.getQuestItemsCount(4751) > 0L || questState.getQuestItemsCount(4752) > 0L || questState.getQuestItemsCount(171) > 0L)) {
                                                        questState.giveItems(2504, 1L);
                                                        questState.giveItems(5011, 1L);
                                                        if (questState.getQuestItemsCount(171) > 0L) {
                                                            questState.takeItems(171, 1L);
                                                        } else if (questState.getQuestItemsCount(4750) > 0L) {
                                                            questState.takeItems(4750, 1L);
                                                        } else if (questState.getQuestItemsCount(4751) > 0L) {
                                                            questState.takeItems(4751, 1L);
                                                        } else if (questState.getQuestItemsCount(4752) > 0L) {
                                                            questState.takeItems(4752, 1L);
                                                        }
                                                        this.giveExtraReward(questState.getPlayer());
                                                        questState.exitCurrentQuest(false);
                                                        questState.playSound("ItemSound.quest_finish");
                                                        string2 = "maestro_leorin_q0234_51.htm";
                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                    }
                                                    if (n == 19 && questState.getQuestItemsCount(2626) > 0L) {
                                                        questState.giveItems(2504, 1L);
                                                        questState.giveItems(5011, 1L);
                                                        questState.takeItems(2626, 1L);
                                                        this.giveExtraReward(questState.getPlayer());
                                                        questState.exitCurrentQuest(false);
                                                        questState.playSound("ItemSound.quest_finish");
                                                        string2 = "maestro_leorin_q0234_51.htm";
                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                    }
                                                    if (n == 41 && (questState.getQuestItemsCount(7883) > 0L || questState.getQuestItemsCount(8105) > 0L || questState.getQuestItemsCount(8106) > 0L || questState.getQuestItemsCount(8107) > 0L)) {
                                                        questState.giveItems(2504, 1L);
                                                        questState.giveItems(5011, 1L);
                                                        if (questState.getQuestItemsCount(7883) > 0L) {
                                                            questState.takeItems(7883, 1L);
                                                        } else if (questState.getQuestItemsCount(8105) > 0L) {
                                                            questState.takeItems(8105, 1L);
                                                        } else if (questState.getQuestItemsCount(8106) > 0L) {
                                                            questState.takeItems(8106, 1L);
                                                        } else if (questState.getQuestItemsCount(8107) > 0L) {
                                                            questState.takeItems(8107, 1L);
                                                        }
                                                        this.giveExtraReward(questState.getPlayer());
                                                        questState.exitCurrentQuest(false);
                                                        questState.playSound("ItemSound.quest_finish");
                                                        string2 = "maestro_leorin_q0234_51.htm";
                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                    }
                                                    if (n == 42 && (questState.getQuestItemsCount(7889) > 0L || questState.getQuestItemsCount(8117) > 0L || questState.getQuestItemsCount(8118) > 0L || questState.getQuestItemsCount(8119) > 0L)) {
                                                        questState.giveItems(2504, 1L);
                                                        questState.giveItems(5011, 1L);
                                                        if (questState.getQuestItemsCount(7889) > 0L) {
                                                            questState.takeItems(7889, 1L);
                                                        } else if (questState.getQuestItemsCount(8117) > 0L) {
                                                            questState.takeItems(8117, 1L);
                                                        } else if (questState.getQuestItemsCount(8118) > 0L) {
                                                            questState.takeItems(8118, 1L);
                                                        } else if (questState.getQuestItemsCount(8119) > 0L) {
                                                            questState.takeItems(8119, 1L);
                                                        }
                                                        this.giveExtraReward(questState.getPlayer());
                                                        questState.exitCurrentQuest(false);
                                                        questState.playSound("ItemSound.quest_finish");
                                                        string2 = "maestro_leorin_q0234_51.htm";
                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                    }
                                                    if (n == 43 && (questState.getQuestItemsCount(7901) > 0L || questState.getQuestItemsCount(8132) > 0L || questState.getQuestItemsCount(8133) > 0L || questState.getQuestItemsCount(8134) > 0L)) {
                                                        questState.giveItems(2504, 1L);
                                                        questState.giveItems(5011, 1L);
                                                        if (questState.getQuestItemsCount(7901) > 0L) {
                                                            questState.takeItems(7901, 1L);
                                                        } else if (questState.getQuestItemsCount(8132) > 0L) {
                                                            questState.takeItems(8132, 1L);
                                                        } else if (questState.getQuestItemsCount(8133) > 0L) {
                                                            questState.takeItems(8133, 1L);
                                                        } else if (questState.getQuestItemsCount(8134) > 0L) {
                                                            questState.takeItems(8134, 1L);
                                                        }
                                                        this.giveExtraReward(questState.getPlayer());
                                                        questState.exitCurrentQuest(false);
                                                        questState.playSound("ItemSound.quest_finish");
                                                        string2 = "maestro_leorin_q0234_51.htm";
                                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                    }
                                                    if (n != 44 || questState.getQuestItemsCount(7893) <= 0L && questState.getQuestItemsCount(8144) <= 0L && questState.getQuestItemsCount(8145) <= 0L && questState.getQuestItemsCount(8146) <= 0L) break block1205;
                                                    questState.giveItems(2504, 1L);
                                                    questState.giveItems(5011, 1L);
                                                    if (questState.getQuestItemsCount(7893) > 0L) {
                                                        questState.takeItems(7893, 1L);
                                                    } else if (questState.getQuestItemsCount(8144) > 0L) {
                                                        questState.takeItems(8144, 1L);
                                                    } else if (questState.getQuestItemsCount(8145) > 0L) {
                                                        questState.takeItems(8145, 1L);
                                                    } else if (questState.getQuestItemsCount(8146) > 0L) {
                                                        questState.takeItems(8146, 1L);
                                                    }
                                                    this.giveExtraReward(questState.getPlayer());
                                                    questState.exitCurrentQuest(false);
                                                    questState.playSound("ItemSound.quest_finish");
                                                    string2 = "maestro_leorin_q0234_51.htm";
                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                    break block1205;
                                                }
                                                if (!string.equalsIgnoreCase("menu_select?ask=234&reply=29")) break block1236;
                                                if (n == 11 && (questState.getQuestItemsCount(79) > 0L || questState.getQuestItemsCount(4717) > 0L || questState.getQuestItemsCount(4718) > 0L || questState.getQuestItemsCount(4719) > 0L)) {
                                                    questState.giveItems(5233, 1L);
                                                    questState.giveItems(5011, 1L);
                                                    if (questState.getQuestItemsCount(79) > 0L) {
                                                        questState.takeItems(79, 1L);
                                                    } else if (questState.getQuestItemsCount(4717) > 0L) {
                                                        questState.takeItems(4717, 1L);
                                                    } else if (questState.getQuestItemsCount(4718) > 0L) {
                                                        questState.takeItems(4718, 1L);
                                                    } else if (questState.getQuestItemsCount(4719) > 0L) {
                                                        questState.takeItems(4719, 1L);
                                                    }
                                                    this.giveExtraReward(questState.getPlayer());
                                                    questState.exitCurrentQuest(false);
                                                    questState.playSound("ItemSound.quest_finish");
                                                    string2 = "maestro_leorin_q0234_52.htm";
                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                }
                                                if (n == 12 && (questState.getQuestItemsCount(4828) > 0L || questState.getQuestItemsCount(4829) > 0L || questState.getQuestItemsCount(4830) > 0L || questState.getQuestItemsCount(287) > 0L)) {
                                                    questState.giveItems(5233, 1L);
                                                    questState.giveItems(5011, 1L);
                                                    if (questState.getQuestItemsCount(287) > 0L) {
                                                        questState.takeItems(287, 1L);
                                                    } else if (questState.getQuestItemsCount(4828) > 0L) {
                                                        questState.takeItems(4828, 1L);
                                                    } else if (questState.getQuestItemsCount(4829) > 0L) {
                                                        questState.takeItems(4829, 1L);
                                                    } else if (questState.getQuestItemsCount(4830) > 0L) {
                                                        questState.takeItems(4830, 1L);
                                                    }
                                                    this.giveExtraReward(questState.getPlayer());
                                                    questState.exitCurrentQuest(false);
                                                    questState.playSound("ItemSound.quest_finish");
                                                    string2 = "maestro_leorin_q0234_52.htm";
                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                }
                                                if (n == 13 && (questState.getQuestItemsCount(4858) > 0L || questState.getQuestItemsCount(4859) > 0L || questState.getQuestItemsCount(4860) > 0L || questState.getQuestItemsCount(97) > 0L)) {
                                                    questState.giveItems(5233, 1L);
                                                    questState.giveItems(5011, 1L);
                                                    if (questState.getQuestItemsCount(97) > 0L) {
                                                        questState.takeItems(97, 1L);
                                                    } else if (questState.getQuestItemsCount(4858) > 0L) {
                                                        questState.takeItems(4858, 1L);
                                                    } else if (questState.getQuestItemsCount(4859) > 0L) {
                                                        questState.takeItems(4859, 1L);
                                                    } else if (questState.getQuestItemsCount(4860) > 0L) {
                                                        questState.takeItems(4860, 1L);
                                                    }
                                                    this.giveExtraReward(questState.getPlayer());
                                                    questState.exitCurrentQuest(false);
                                                    questState.playSound("ItemSound.quest_finish");
                                                    string2 = "maestro_leorin_q0234_52.htm";
                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                }
                                                if (n == 14 && (questState.getQuestItemsCount(4753) > 0L || questState.getQuestItemsCount(4754) > 0L || questState.getQuestItemsCount(4755) > 0L || questState.getQuestItemsCount(175) > 0L)) {
                                                    questState.giveItems(5233, 1L);
                                                    questState.giveItems(5011, 1L);
                                                    if (questState.getQuestItemsCount(175) > 0L) {
                                                        questState.takeItems(175, 1L);
                                                    } else if (questState.getQuestItemsCount(4753) > 0L) {
                                                        questState.takeItems(4753, 1L);
                                                    } else if (questState.getQuestItemsCount(4754) > 0L) {
                                                        questState.takeItems(4754, 1L);
                                                    } else if (questState.getQuestItemsCount(4755) > 0L) {
                                                        questState.takeItems(4755, 1L);
                                                    }
                                                    this.giveExtraReward(questState.getPlayer());
                                                    questState.exitCurrentQuest(false);
                                                    questState.playSound("ItemSound.quest_finish");
                                                    string2 = "maestro_leorin_q0234_52.htm";
                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                }
                                                if (n == 15 && (questState.getQuestItemsCount(4900) > 0L || questState.getQuestItemsCount(4901) > 0L || questState.getQuestItemsCount(4902) > 0L || questState.getQuestItemsCount(210) > 0L)) {
                                                    questState.giveItems(5233, 1L);
                                                    questState.giveItems(5011, 1L);
                                                    if (questState.getQuestItemsCount(210) > 0L) {
                                                        questState.takeItems(210, 1L);
                                                    } else if (questState.getQuestItemsCount(4900) > 0L) {
                                                        questState.takeItems(4900, 1L);
                                                    } else if (questState.getQuestItemsCount(4901) > 0L) {
                                                        questState.takeItems(4901, 1L);
                                                    } else if (questState.getQuestItemsCount(4902) > 0L) {
                                                        questState.takeItems(4902, 1L);
                                                    }
                                                    this.giveExtraReward(questState.getPlayer());
                                                    questState.exitCurrentQuest(false);
                                                    questState.playSound("ItemSound.quest_finish");
                                                    string2 = "maestro_leorin_q0234_52.htm";
                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                }
                                                if (n == 16 && (questState.getQuestItemsCount(4780) > 0L || questState.getQuestItemsCount(4781) > 0L || questState.getQuestItemsCount(4782) > 0L || questState.getQuestItemsCount(234) > 0L || questState.getQuestItemsCount(6359) > 0L)) {
                                                    questState.giveItems(5233, 1L);
                                                    questState.giveItems(5011, 1L);
                                                    if (questState.getQuestItemsCount(234) > 0L) {
                                                        questState.takeItems(234, 1L);
                                                    } else if (questState.getQuestItemsCount(4780) > 0L) {
                                                        questState.takeItems(4780, 1L);
                                                    } else if (questState.getQuestItemsCount(4781) > 0L) {
                                                        questState.takeItems(4781, 1L);
                                                    } else if (questState.getQuestItemsCount(4782) > 0L) {
                                                        questState.takeItems(4782, 1L);
                                                    } else if (questState.getQuestItemsCount(6359) > 0L) {
                                                        questState.takeItems(6359, 1L);
                                                    }
                                                    this.giveExtraReward(questState.getPlayer());
                                                    questState.exitCurrentQuest(false);
                                                    questState.playSound("ItemSound.quest_finish");
                                                    string2 = "maestro_leorin_q0234_52.htm";
                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                }
                                                if (n == 17 && (questState.getQuestItemsCount(4804) > 0L || questState.getQuestItemsCount(4805) > 0L || questState.getQuestItemsCount(4806) > 0L || questState.getQuestItemsCount(268) > 0L)) {
                                                    questState.giveItems(5233, 1L);
                                                    questState.giveItems(5011, 1L);
                                                    if (questState.getQuestItemsCount(268) > 0L) {
                                                        questState.takeItems(268, 1L);
                                                    } else if (questState.getQuestItemsCount(4804) > 0L) {
                                                        questState.takeItems(4804, 1L);
                                                    } else if (questState.getQuestItemsCount(4805) > 0L) {
                                                        questState.takeItems(4805, 1L);
                                                    } else if (questState.getQuestItemsCount(4806) > 0L) {
                                                        questState.takeItems(4806, 1L);
                                                    }
                                                    this.giveExtraReward(questState.getPlayer());
                                                    questState.exitCurrentQuest(false);
                                                    questState.playSound("ItemSound.quest_finish");
                                                    string2 = "maestro_leorin_q0234_52.htm";
                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                }
                                                if (n == 18 && (questState.getQuestItemsCount(4750) > 0L || questState.getQuestItemsCount(4751) > 0L || questState.getQuestItemsCount(4752) > 0L || questState.getQuestItemsCount(171) > 0L)) {
                                                    questState.giveItems(5233, 1L);
                                                    questState.giveItems(5011, 1L);
                                                    if (questState.getQuestItemsCount(171) > 0L) {
                                                        questState.takeItems(171, 1L);
                                                    } else if (questState.getQuestItemsCount(4750) > 0L) {
                                                        questState.takeItems(4750, 1L);
                                                    } else if (questState.getQuestItemsCount(4751) > 0L) {
                                                        questState.takeItems(4751, 1L);
                                                    } else if (questState.getQuestItemsCount(4752) > 0L) {
                                                        questState.takeItems(4752, 1L);
                                                    }
                                                    this.giveExtraReward(questState.getPlayer());
                                                    questState.exitCurrentQuest(false);
                                                    questState.playSound("ItemSound.quest_finish");
                                                    string2 = "maestro_leorin_q0234_52.htm";
                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                }
                                                if (n == 19 && questState.getQuestItemsCount(2626) > 0L) {
                                                    questState.giveItems(5233, 1L);
                                                    questState.giveItems(5011, 1L);
                                                    questState.takeItems(2626, 1L);
                                                    this.giveExtraReward(questState.getPlayer());
                                                    questState.exitCurrentQuest(false);
                                                    questState.playSound("ItemSound.quest_finish");
                                                    string2 = "maestro_leorin_q0234_52.htm";
                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                }
                                                if (n == 41 && (questState.getQuestItemsCount(7883) > 0L || questState.getQuestItemsCount(8105) > 0L || questState.getQuestItemsCount(8106) > 0L || questState.getQuestItemsCount(8107) > 0L)) {
                                                    questState.giveItems(5233, 1L);
                                                    questState.giveItems(5011, 1L);
                                                    if (questState.getQuestItemsCount(7883) > 0L) {
                                                        questState.takeItems(7883, 1L);
                                                    } else if (questState.getQuestItemsCount(8105) > 0L) {
                                                        questState.takeItems(8105, 1L);
                                                    } else if (questState.getQuestItemsCount(8106) > 0L) {
                                                        questState.takeItems(8106, 1L);
                                                    } else if (questState.getQuestItemsCount(8107) > 0L) {
                                                        questState.takeItems(8107, 1L);
                                                    }
                                                    this.giveExtraReward(questState.getPlayer());
                                                    questState.exitCurrentQuest(false);
                                                    questState.playSound("ItemSound.quest_finish");
                                                    string2 = "maestro_leorin_q0234_52.htm";
                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                }
                                                if (n == 42 && (questState.getQuestItemsCount(7889) > 0L || questState.getQuestItemsCount(8117) > 0L || questState.getQuestItemsCount(8118) > 0L || questState.getQuestItemsCount(8119) > 0L)) {
                                                    questState.giveItems(5233, 1L);
                                                    questState.giveItems(5011, 1L);
                                                    if (questState.getQuestItemsCount(7889) > 0L) {
                                                        questState.takeItems(7889, 1L);
                                                    } else if (questState.getQuestItemsCount(8117) > 0L) {
                                                        questState.takeItems(8117, 1L);
                                                    } else if (questState.getQuestItemsCount(8118) > 0L) {
                                                        questState.takeItems(8118, 1L);
                                                    } else if (questState.getQuestItemsCount(8119) > 0L) {
                                                        questState.takeItems(8119, 1L);
                                                    }
                                                    this.giveExtraReward(questState.getPlayer());
                                                    questState.exitCurrentQuest(false);
                                                    questState.playSound("ItemSound.quest_finish");
                                                    string2 = "maestro_leorin_q0234_52.htm";
                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                }
                                                if (n == 43 && (questState.getQuestItemsCount(7901) > 0L || questState.getQuestItemsCount(8132) > 0L || questState.getQuestItemsCount(8133) > 0L || questState.getQuestItemsCount(8134) > 0L)) {
                                                    questState.giveItems(5233, 1L);
                                                    questState.giveItems(5011, 1L);
                                                    if (questState.getQuestItemsCount(7901) > 0L) {
                                                        questState.takeItems(7901, 1L);
                                                    } else if (questState.getQuestItemsCount(8132) > 0L) {
                                                        questState.takeItems(8132, 1L);
                                                    } else if (questState.getQuestItemsCount(8133) > 0L) {
                                                        questState.takeItems(8133, 1L);
                                                    } else if (questState.getQuestItemsCount(8134) > 0L) {
                                                        questState.takeItems(8134, 1L);
                                                    }
                                                    this.giveExtraReward(questState.getPlayer());
                                                    questState.exitCurrentQuest(false);
                                                    questState.playSound("ItemSound.quest_finish");
                                                    string2 = "maestro_leorin_q0234_52.htm";
                                                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                }
                                                if (n != 44 || questState.getQuestItemsCount(7893) <= 0L && questState.getQuestItemsCount(8144) <= 0L && questState.getQuestItemsCount(8145) <= 0L && questState.getQuestItemsCount(8146) <= 0L) break block1205;
                                                questState.giveItems(5233, 1L);
                                                questState.giveItems(5011, 1L);
                                                if (questState.getQuestItemsCount(7893) > 0L) {
                                                    questState.takeItems(7893, 1L);
                                                } else if (questState.getQuestItemsCount(8144) > 0L) {
                                                    questState.takeItems(8144, 1L);
                                                } else if (questState.getQuestItemsCount(8145) > 0L) {
                                                    questState.takeItems(8145, 1L);
                                                } else if (questState.getQuestItemsCount(8146) > 0L) {
                                                    questState.takeItems(8146, 1L);
                                                }
                                                this.giveExtraReward(questState.getPlayer());
                                                questState.exitCurrentQuest(false);
                                                questState.playSound("ItemSound.quest_finish");
                                                string2 = "maestro_leorin_q0234_52.htm";
                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                                break block1205;
                                            }
                                            if (!string.equalsIgnoreCase("menu_select?ask=234&reply=30")) break block1237;
                                            if (n == 11 && (questState.getQuestItemsCount(79) > 0L || questState.getQuestItemsCount(4717) > 0L || questState.getQuestItemsCount(4718) > 0L || questState.getQuestItemsCount(4719) > 0L)) {
                                                questState.giveItems(7884, 1L);
                                                questState.giveItems(5011, 1L);
                                                if (questState.getQuestItemsCount(79) > 0L) {
                                                    questState.takeItems(79, 1L);
                                                } else if (questState.getQuestItemsCount(4717) > 0L) {
                                                    questState.takeItems(4717, 1L);
                                                } else if (questState.getQuestItemsCount(4718) > 0L) {
                                                    questState.takeItems(4718, 1L);
                                                } else if (questState.getQuestItemsCount(4719) > 0L) {
                                                    questState.takeItems(4719, 1L);
                                                }
                                                this.giveExtraReward(questState.getPlayer());
                                                questState.exitCurrentQuest(false);
                                                questState.playSound("ItemSound.quest_finish");
                                                string2 = "maestro_leorin_q0234_53.htm";
                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                            }
                                            if (n == 12 && (questState.getQuestItemsCount(4828) > 0L || questState.getQuestItemsCount(4829) > 0L || questState.getQuestItemsCount(4830) > 0L || questState.getQuestItemsCount(287) > 0L)) {
                                                questState.giveItems(7884, 1L);
                                                questState.giveItems(5011, 1L);
                                                if (questState.getQuestItemsCount(287) > 0L) {
                                                    questState.takeItems(287, 1L);
                                                } else if (questState.getQuestItemsCount(4828) > 0L) {
                                                    questState.takeItems(4828, 1L);
                                                } else if (questState.getQuestItemsCount(4829) > 0L) {
                                                    questState.takeItems(4829, 1L);
                                                } else if (questState.getQuestItemsCount(4830) > 0L) {
                                                    questState.takeItems(4830, 1L);
                                                }
                                                this.giveExtraReward(questState.getPlayer());
                                                questState.exitCurrentQuest(false);
                                                questState.playSound("ItemSound.quest_finish");
                                                string2 = "maestro_leorin_q0234_53.htm";
                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                            }
                                            if (n == 13 && (questState.getQuestItemsCount(4858) > 0L || questState.getQuestItemsCount(4859) > 0L || questState.getQuestItemsCount(4860) > 0L || questState.getQuestItemsCount(97) > 0L)) {
                                                questState.giveItems(7884, 1L);
                                                questState.giveItems(5011, 1L);
                                                if (questState.getQuestItemsCount(97) > 0L) {
                                                    questState.takeItems(97, 1L);
                                                } else if (questState.getQuestItemsCount(4858) > 0L) {
                                                    questState.takeItems(4858, 1L);
                                                } else if (questState.getQuestItemsCount(4859) > 0L) {
                                                    questState.takeItems(4859, 1L);
                                                } else if (questState.getQuestItemsCount(4860) > 0L) {
                                                    questState.takeItems(4860, 1L);
                                                }
                                                this.giveExtraReward(questState.getPlayer());
                                                questState.exitCurrentQuest(false);
                                                questState.playSound("ItemSound.quest_finish");
                                                string2 = "maestro_leorin_q0234_53.htm";
                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                            }
                                            if (n == 14 && (questState.getQuestItemsCount(4753) > 0L || questState.getQuestItemsCount(4754) > 0L || questState.getQuestItemsCount(4755) > 0L || questState.getQuestItemsCount(175) > 0L)) {
                                                questState.giveItems(7884, 1L);
                                                questState.giveItems(5011, 1L);
                                                if (questState.getQuestItemsCount(175) > 0L) {
                                                    questState.takeItems(175, 1L);
                                                } else if (questState.getQuestItemsCount(4753) > 0L) {
                                                    questState.takeItems(4753, 1L);
                                                } else if (questState.getQuestItemsCount(4754) > 0L) {
                                                    questState.takeItems(4754, 1L);
                                                } else if (questState.getQuestItemsCount(4755) > 0L) {
                                                    questState.takeItems(4755, 1L);
                                                }
                                                this.giveExtraReward(questState.getPlayer());
                                                questState.exitCurrentQuest(false);
                                                questState.playSound("ItemSound.quest_finish");
                                                string2 = "maestro_leorin_q0234_53.htm";
                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                            }
                                            if (n == 15 && (questState.getQuestItemsCount(4900) > 0L || questState.getQuestItemsCount(4901) > 0L || questState.getQuestItemsCount(4902) > 0L || questState.getQuestItemsCount(210) > 0L)) {
                                                questState.giveItems(7884, 1L);
                                                questState.giveItems(5011, 1L);
                                                if (questState.getQuestItemsCount(210) > 0L) {
                                                    questState.takeItems(210, 1L);
                                                } else if (questState.getQuestItemsCount(4900) > 0L) {
                                                    questState.takeItems(4900, 1L);
                                                } else if (questState.getQuestItemsCount(4901) > 0L) {
                                                    questState.takeItems(4901, 1L);
                                                } else if (questState.getQuestItemsCount(4902) > 0L) {
                                                    questState.takeItems(4902, 1L);
                                                }
                                                this.giveExtraReward(questState.getPlayer());
                                                questState.exitCurrentQuest(false);
                                                questState.playSound("ItemSound.quest_finish");
                                                string2 = "maestro_leorin_q0234_53.htm";
                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                            }
                                            if (n == 16 && (questState.getQuestItemsCount(4780) > 0L || questState.getQuestItemsCount(4781) > 0L || questState.getQuestItemsCount(4782) > 0L || questState.getQuestItemsCount(234) > 0L || questState.getQuestItemsCount(6359) > 0L)) {
                                                questState.giveItems(7884, 1L);
                                                questState.giveItems(5011, 1L);
                                                if (questState.getQuestItemsCount(234) > 0L) {
                                                    questState.takeItems(234, 1L);
                                                } else if (questState.getQuestItemsCount(4780) > 0L) {
                                                    questState.takeItems(4780, 1L);
                                                } else if (questState.getQuestItemsCount(4781) > 0L) {
                                                    questState.takeItems(4781, 1L);
                                                } else if (questState.getQuestItemsCount(4782) > 0L) {
                                                    questState.takeItems(4782, 1L);
                                                } else if (questState.getQuestItemsCount(6359) > 0L) {
                                                    questState.takeItems(6359, 1L);
                                                }
                                                this.giveExtraReward(questState.getPlayer());
                                                questState.exitCurrentQuest(false);
                                                questState.playSound("ItemSound.quest_finish");
                                                string2 = "maestro_leorin_q0234_53.htm";
                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                            }
                                            if (n == 17 && (questState.getQuestItemsCount(4804) > 0L || questState.getQuestItemsCount(4805) > 0L || questState.getQuestItemsCount(4806) > 0L || questState.getQuestItemsCount(268) > 0L)) {
                                                questState.giveItems(7884, 1L);
                                                questState.giveItems(5011, 1L);
                                                if (questState.getQuestItemsCount(268) > 0L) {
                                                    questState.takeItems(268, 1L);
                                                } else if (questState.getQuestItemsCount(4804) > 0L) {
                                                    questState.takeItems(4804, 1L);
                                                } else if (questState.getQuestItemsCount(4805) > 0L) {
                                                    questState.takeItems(4805, 1L);
                                                } else if (questState.getQuestItemsCount(4806) > 0L) {
                                                    questState.takeItems(4806, 1L);
                                                }
                                                this.giveExtraReward(questState.getPlayer());
                                                questState.exitCurrentQuest(false);
                                                questState.playSound("ItemSound.quest_finish");
                                                string2 = "maestro_leorin_q0234_53.htm";
                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                            }
                                            if (n == 18 && (questState.getQuestItemsCount(4750) > 0L || questState.getQuestItemsCount(4751) > 0L || questState.getQuestItemsCount(4752) > 0L || questState.getQuestItemsCount(171) > 0L)) {
                                                questState.giveItems(7884, 1L);
                                                questState.giveItems(5011, 1L);
                                                if (questState.getQuestItemsCount(171) > 0L) {
                                                    questState.takeItems(171, 1L);
                                                } else if (questState.getQuestItemsCount(4750) > 0L) {
                                                    questState.takeItems(4750, 1L);
                                                } else if (questState.getQuestItemsCount(4751) > 0L) {
                                                    questState.takeItems(4751, 1L);
                                                } else if (questState.getQuestItemsCount(4752) > 0L) {
                                                    questState.takeItems(4752, 1L);
                                                }
                                                this.giveExtraReward(questState.getPlayer());
                                                questState.exitCurrentQuest(false);
                                                questState.playSound("ItemSound.quest_finish");
                                                string2 = "maestro_leorin_q0234_53.htm";
                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                            }
                                            if (n == 19 && questState.getQuestItemsCount(2626) > 0L) {
                                                questState.giveItems(7884, 1L);
                                                questState.giveItems(5011, 1L);
                                                questState.takeItems(2626, 1L);
                                                this.giveExtraReward(questState.getPlayer());
                                                questState.exitCurrentQuest(false);
                                                questState.playSound("ItemSound.quest_finish");
                                                string2 = "maestro_leorin_q0234_53.htm";
                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                            }
                                            if (n == 41 && (questState.getQuestItemsCount(7883) > 0L || questState.getQuestItemsCount(8105) > 0L || questState.getQuestItemsCount(8106) > 0L || questState.getQuestItemsCount(8107) > 0L)) {
                                                questState.giveItems(7884, 1L);
                                                questState.giveItems(5011, 1L);
                                                if (questState.getQuestItemsCount(7883) > 0L) {
                                                    questState.takeItems(7883, 1L);
                                                } else if (questState.getQuestItemsCount(8105) > 0L) {
                                                    questState.takeItems(8105, 1L);
                                                } else if (questState.getQuestItemsCount(8106) > 0L) {
                                                    questState.takeItems(8106, 1L);
                                                } else if (questState.getQuestItemsCount(8107) > 0L) {
                                                    questState.takeItems(8107, 1L);
                                                }
                                                this.giveExtraReward(questState.getPlayer());
                                                questState.exitCurrentQuest(false);
                                                questState.playSound("ItemSound.quest_finish");
                                                string2 = "maestro_leorin_q0234_53.htm";
                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                            }
                                            if (n == 42 && (questState.getQuestItemsCount(7889) > 0L || questState.getQuestItemsCount(8117) > 0L || questState.getQuestItemsCount(8118) > 0L || questState.getQuestItemsCount(8119) > 0L)) {
                                                questState.giveItems(7884, 1L);
                                                questState.giveItems(5011, 1L);
                                                if (questState.getQuestItemsCount(7889) > 0L) {
                                                    questState.takeItems(7889, 1L);
                                                } else if (questState.getQuestItemsCount(8117) > 0L) {
                                                    questState.takeItems(8117, 1L);
                                                } else if (questState.getQuestItemsCount(8118) > 0L) {
                                                    questState.takeItems(8118, 1L);
                                                } else if (questState.getQuestItemsCount(8119) > 0L) {
                                                    questState.takeItems(8119, 1L);
                                                }
                                                this.giveExtraReward(questState.getPlayer());
                                                questState.exitCurrentQuest(false);
                                                questState.playSound("ItemSound.quest_finish");
                                                string2 = "maestro_leorin_q0234_53.htm";
                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                            }
                                            if (n == 43 && (questState.getQuestItemsCount(7901) > 0L || questState.getQuestItemsCount(8132) > 0L || questState.getQuestItemsCount(8133) > 0L || questState.getQuestItemsCount(8134) > 0L)) {
                                                questState.giveItems(7884, 1L);
                                                questState.giveItems(5011, 1L);
                                                if (questState.getQuestItemsCount(7901) > 0L) {
                                                    questState.takeItems(7901, 1L);
                                                } else if (questState.getQuestItemsCount(8132) > 0L) {
                                                    questState.takeItems(8132, 1L);
                                                } else if (questState.getQuestItemsCount(8133) > 0L) {
                                                    questState.takeItems(8133, 1L);
                                                } else if (questState.getQuestItemsCount(8134) > 0L) {
                                                    questState.takeItems(8134, 1L);
                                                }
                                                this.giveExtraReward(questState.getPlayer());
                                                questState.exitCurrentQuest(false);
                                                questState.playSound("ItemSound.quest_finish");
                                                string2 = "maestro_leorin_q0234_53.htm";
                                                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                            }
                                            if (n != 44 || questState.getQuestItemsCount(7893) <= 0L && questState.getQuestItemsCount(8144) <= 0L && questState.getQuestItemsCount(8145) <= 0L && questState.getQuestItemsCount(8146) <= 0L) break block1205;
                                            questState.giveItems(7884, 1L);
                                            questState.giveItems(5011, 1L);
                                            if (questState.getQuestItemsCount(7893) > 0L) {
                                                questState.takeItems(7893, 1L);
                                            } else if (questState.getQuestItemsCount(8144) > 0L) {
                                                questState.takeItems(8144, 1L);
                                            } else if (questState.getQuestItemsCount(8145) > 0L) {
                                                questState.takeItems(8145, 1L);
                                            } else if (questState.getQuestItemsCount(8146) > 0L) {
                                                questState.takeItems(8146, 1L);
                                            }
                                            this.giveExtraReward(questState.getPlayer());
                                            questState.exitCurrentQuest(false);
                                            questState.playSound("ItemSound.quest_finish");
                                            string2 = "maestro_leorin_q0234_53.htm";
                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                            break block1205;
                                        }
                                        if (!string.equalsIgnoreCase("menu_select?ask=234&reply=31")) break block1238;
                                        if (n == 11 && (questState.getQuestItemsCount(79) > 0L || questState.getQuestItemsCount(4717) > 0L || questState.getQuestItemsCount(4718) > 0L || questState.getQuestItemsCount(4719) > 0L)) {
                                            questState.giveItems(7894, 1L);
                                            questState.giveItems(5011, 1L);
                                            if (questState.getQuestItemsCount(79) > 0L) {
                                                questState.takeItems(79, 1L);
                                            } else if (questState.getQuestItemsCount(4717) > 0L) {
                                                questState.takeItems(4717, 1L);
                                            } else if (questState.getQuestItemsCount(4718) > 0L) {
                                                questState.takeItems(4718, 1L);
                                            } else if (questState.getQuestItemsCount(4719) > 0L) {
                                                questState.takeItems(4719, 1L);
                                            }
                                            this.giveExtraReward(questState.getPlayer());
                                            questState.exitCurrentQuest(false);
                                            questState.playSound("ItemSound.quest_finish");
                                            string2 = "maestro_leorin_q0234_54.htm";
                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                        }
                                        if (n == 12 && (questState.getQuestItemsCount(4828) > 0L || questState.getQuestItemsCount(4829) > 0L || questState.getQuestItemsCount(4830) > 0L || questState.getQuestItemsCount(287) > 0L)) {
                                            questState.giveItems(7894, 1L);
                                            questState.giveItems(5011, 1L);
                                            if (questState.getQuestItemsCount(287) > 0L) {
                                                questState.takeItems(287, 1L);
                                            } else if (questState.getQuestItemsCount(4828) > 0L) {
                                                questState.takeItems(4828, 1L);
                                            } else if (questState.getQuestItemsCount(4829) > 0L) {
                                                questState.takeItems(4829, 1L);
                                            } else if (questState.getQuestItemsCount(4830) > 0L) {
                                                questState.takeItems(4830, 1L);
                                            }
                                            this.giveExtraReward(questState.getPlayer());
                                            questState.exitCurrentQuest(false);
                                            questState.playSound("ItemSound.quest_finish");
                                            string2 = "maestro_leorin_q0234_54.htm";
                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                        }
                                        if (n == 13 && (questState.getQuestItemsCount(4858) > 0L || questState.getQuestItemsCount(4859) > 0L || questState.getQuestItemsCount(4860) > 0L || questState.getQuestItemsCount(97) > 0L)) {
                                            questState.giveItems(7894, 1L);
                                            questState.giveItems(5011, 1L);
                                            if (questState.getQuestItemsCount(97) > 0L) {
                                                questState.takeItems(97, 1L);
                                            } else if (questState.getQuestItemsCount(4858) > 0L) {
                                                questState.takeItems(4858, 1L);
                                            } else if (questState.getQuestItemsCount(4859) > 0L) {
                                                questState.takeItems(4859, 1L);
                                            } else if (questState.getQuestItemsCount(4860) > 0L) {
                                                questState.takeItems(4860, 1L);
                                            }
                                            this.giveExtraReward(questState.getPlayer());
                                            questState.exitCurrentQuest(false);
                                            questState.playSound("ItemSound.quest_finish");
                                            string2 = "maestro_leorin_q0234_54.htm";
                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                        }
                                        if (n == 14 && (questState.getQuestItemsCount(4753) > 0L || questState.getQuestItemsCount(4754) > 0L || questState.getQuestItemsCount(4755) > 0L || questState.getQuestItemsCount(175) > 0L)) {
                                            questState.giveItems(7894, 1L);
                                            questState.giveItems(5011, 1L);
                                            if (questState.getQuestItemsCount(175) > 0L) {
                                                questState.takeItems(175, 1L);
                                            } else if (questState.getQuestItemsCount(4753) > 0L) {
                                                questState.takeItems(4753, 1L);
                                            } else if (questState.getQuestItemsCount(4754) > 0L) {
                                                questState.takeItems(4754, 1L);
                                            } else if (questState.getQuestItemsCount(4755) > 0L) {
                                                questState.takeItems(4755, 1L);
                                            }
                                            this.giveExtraReward(questState.getPlayer());
                                            questState.exitCurrentQuest(false);
                                            questState.playSound("ItemSound.quest_finish");
                                            string2 = "maestro_leorin_q0234_54.htm";
                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                        }
                                        if (n == 15 && (questState.getQuestItemsCount(4900) > 0L || questState.getQuestItemsCount(4901) > 0L || questState.getQuestItemsCount(4902) > 0L || questState.getQuestItemsCount(210) > 0L)) {
                                            questState.giveItems(7894, 1L);
                                            questState.giveItems(5011, 1L);
                                            if (questState.getQuestItemsCount(210) > 0L) {
                                                questState.takeItems(210, 1L);
                                            } else if (questState.getQuestItemsCount(4900) > 0L) {
                                                questState.takeItems(4900, 1L);
                                            } else if (questState.getQuestItemsCount(4901) > 0L) {
                                                questState.takeItems(4901, 1L);
                                            } else if (questState.getQuestItemsCount(4902) > 0L) {
                                                questState.takeItems(4902, 1L);
                                            }
                                            this.giveExtraReward(questState.getPlayer());
                                            questState.exitCurrentQuest(false);
                                            questState.playSound("ItemSound.quest_finish");
                                            string2 = "maestro_leorin_q0234_54.htm";
                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                        }
                                        if (n == 16 && (questState.getQuestItemsCount(4780) > 0L || questState.getQuestItemsCount(4781) > 0L || questState.getQuestItemsCount(4782) > 0L || questState.getQuestItemsCount(234) > 0L || questState.getQuestItemsCount(6359) > 0L)) {
                                            questState.giveItems(7894, 1L);
                                            questState.giveItems(5011, 1L);
                                            if (questState.getQuestItemsCount(234) > 0L) {
                                                questState.takeItems(234, 1L);
                                            } else if (questState.getQuestItemsCount(4780) > 0L) {
                                                questState.takeItems(4780, 1L);
                                            } else if (questState.getQuestItemsCount(4781) > 0L) {
                                                questState.takeItems(4781, 1L);
                                            } else if (questState.getQuestItemsCount(4782) > 0L) {
                                                questState.takeItems(4782, 1L);
                                            } else if (questState.getQuestItemsCount(6359) > 0L) {
                                                questState.takeItems(6359, 1L);
                                            }
                                            this.giveExtraReward(questState.getPlayer());
                                            questState.exitCurrentQuest(false);
                                            questState.playSound("ItemSound.quest_finish");
                                            string2 = "maestro_leorin_q0234_54.htm";
                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                        }
                                        if (n == 17 && (questState.getQuestItemsCount(4804) > 0L || questState.getQuestItemsCount(4805) > 0L || questState.getQuestItemsCount(4806) > 0L || questState.getQuestItemsCount(268) > 0L)) {
                                            questState.giveItems(7894, 1L);
                                            questState.giveItems(5011, 1L);
                                            if (questState.getQuestItemsCount(268) > 0L) {
                                                questState.takeItems(268, 1L);
                                            } else if (questState.getQuestItemsCount(4804) > 0L) {
                                                questState.takeItems(4804, 1L);
                                            } else if (questState.getQuestItemsCount(4805) > 0L) {
                                                questState.takeItems(4805, 1L);
                                            } else if (questState.getQuestItemsCount(4806) > 0L) {
                                                questState.takeItems(4806, 1L);
                                            }
                                            this.giveExtraReward(questState.getPlayer());
                                            questState.exitCurrentQuest(false);
                                            questState.playSound("ItemSound.quest_finish");
                                            string2 = "maestro_leorin_q0234_54.htm";
                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                        }
                                        if (n == 18 && (questState.getQuestItemsCount(4750) > 0L || questState.getQuestItemsCount(4751) > 0L || questState.getQuestItemsCount(4752) > 0L || questState.getQuestItemsCount(171) > 0L)) {
                                            questState.giveItems(7894, 1L);
                                            questState.giveItems(5011, 1L);
                                            if (questState.getQuestItemsCount(171) > 0L) {
                                                questState.takeItems(171, 1L);
                                            } else if (questState.getQuestItemsCount(4750) > 0L) {
                                                questState.takeItems(4750, 1L);
                                            } else if (questState.getQuestItemsCount(4751) > 0L) {
                                                questState.takeItems(4751, 1L);
                                            } else if (questState.getQuestItemsCount(4752) > 0L) {
                                                questState.takeItems(4752, 1L);
                                            }
                                            this.giveExtraReward(questState.getPlayer());
                                            questState.exitCurrentQuest(false);
                                            questState.playSound("ItemSound.quest_finish");
                                            string2 = "maestro_leorin_q0234_54.htm";
                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                        }
                                        if (n == 19 && questState.getQuestItemsCount(2626) > 0L) {
                                            questState.giveItems(7894, 1L);
                                            questState.giveItems(5011, 1L);
                                            questState.takeItems(2626, 1L);
                                            this.giveExtraReward(questState.getPlayer());
                                            questState.exitCurrentQuest(false);
                                            questState.playSound("ItemSound.quest_finish");
                                            string2 = "maestro_leorin_q0234_54.htm";
                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                        }
                                        if (n == 41 && (questState.getQuestItemsCount(7883) > 0L || questState.getQuestItemsCount(8105) > 0L || questState.getQuestItemsCount(8106) > 0L || questState.getQuestItemsCount(8107) > 0L)) {
                                            questState.giveItems(7894, 1L);
                                            questState.giveItems(5011, 1L);
                                            if (questState.getQuestItemsCount(7883) > 0L) {
                                                questState.takeItems(7883, 1L);
                                            } else if (questState.getQuestItemsCount(8105) > 0L) {
                                                questState.takeItems(8105, 1L);
                                            } else if (questState.getQuestItemsCount(8106) > 0L) {
                                                questState.takeItems(8106, 1L);
                                            } else if (questState.getQuestItemsCount(8107) > 0L) {
                                                questState.takeItems(8107, 1L);
                                            }
                                            this.giveExtraReward(questState.getPlayer());
                                            questState.exitCurrentQuest(false);
                                            questState.playSound("ItemSound.quest_finish");
                                            string2 = "maestro_leorin_q0234_54.htm";
                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                        }
                                        if (n == 42 && (questState.getQuestItemsCount(7889) > 0L || questState.getQuestItemsCount(8117) > 0L || questState.getQuestItemsCount(8118) > 0L || questState.getQuestItemsCount(8119) > 0L)) {
                                            questState.giveItems(7894, 1L);
                                            questState.giveItems(5011, 1L);
                                            if (questState.getQuestItemsCount(7889) > 0L) {
                                                questState.takeItems(7889, 1L);
                                            } else if (questState.getQuestItemsCount(8117) > 0L) {
                                                questState.takeItems(8117, 1L);
                                            } else if (questState.getQuestItemsCount(8118) > 0L) {
                                                questState.takeItems(8118, 1L);
                                            } else if (questState.getQuestItemsCount(8119) > 0L) {
                                                questState.takeItems(8119, 1L);
                                            }
                                            this.giveExtraReward(questState.getPlayer());
                                            questState.exitCurrentQuest(false);
                                            questState.playSound("ItemSound.quest_finish");
                                            string2 = "maestro_leorin_q0234_54.htm";
                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                        }
                                        if (n == 43 && (questState.getQuestItemsCount(7901) > 0L || questState.getQuestItemsCount(8132) > 0L || questState.getQuestItemsCount(8133) > 0L || questState.getQuestItemsCount(8134) > 0L)) {
                                            questState.giveItems(7894, 1L);
                                            questState.giveItems(5011, 1L);
                                            if (questState.getQuestItemsCount(7901) > 0L) {
                                                questState.takeItems(7901, 1L);
                                            } else if (questState.getQuestItemsCount(8132) > 0L) {
                                                questState.takeItems(8132, 1L);
                                            } else if (questState.getQuestItemsCount(8133) > 0L) {
                                                questState.takeItems(8133, 1L);
                                            } else if (questState.getQuestItemsCount(8134) > 0L) {
                                                questState.takeItems(8134, 1L);
                                            }
                                            this.giveExtraReward(questState.getPlayer());
                                            questState.exitCurrentQuest(false);
                                            questState.playSound("ItemSound.quest_finish");
                                            string2 = "maestro_leorin_q0234_54.htm";
                                            questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                        }
                                        if (n != 44 || questState.getQuestItemsCount(7893) <= 0L && questState.getQuestItemsCount(8144) <= 0L && questState.getQuestItemsCount(8145) <= 0L && questState.getQuestItemsCount(8146) <= 0L) break block1205;
                                        questState.giveItems(7894, 1L);
                                        questState.giveItems(5011, 1L);
                                        if (questState.getQuestItemsCount(7893) > 0L) {
                                            questState.takeItems(7893, 1L);
                                        } else if (questState.getQuestItemsCount(8144) > 0L) {
                                            questState.takeItems(8144, 1L);
                                        } else if (questState.getQuestItemsCount(8145) > 0L) {
                                            questState.takeItems(8145, 1L);
                                        } else if (questState.getQuestItemsCount(8146) > 0L) {
                                            questState.takeItems(8146, 1L);
                                        }
                                        this.giveExtraReward(questState.getPlayer());
                                        questState.exitCurrentQuest(false);
                                        questState.playSound("ItemSound.quest_finish");
                                        string2 = "maestro_leorin_q0234_54.htm";
                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                        break block1205;
                                    }
                                    if (!string.equalsIgnoreCase("menu_select?ask=234&reply=32")) break block1205;
                                    if (n == 11 && (questState.getQuestItemsCount(79) > 0L || questState.getQuestItemsCount(4717) > 0L || questState.getQuestItemsCount(4718) > 0L || questState.getQuestItemsCount(4719) > 0L)) {
                                        questState.giveItems(7899, 1L);
                                        questState.giveItems(5011, 1L);
                                        if (questState.getQuestItemsCount(79) > 0L) {
                                            questState.takeItems(79, 1L);
                                        } else if (questState.getQuestItemsCount(4717) > 0L) {
                                            questState.takeItems(4717, 1L);
                                        } else if (questState.getQuestItemsCount(4718) > 0L) {
                                            questState.takeItems(4718, 1L);
                                        } else if (questState.getQuestItemsCount(4719) > 0L) {
                                            questState.takeItems(4719, 1L);
                                        }
                                        this.giveExtraReward(questState.getPlayer());
                                        questState.exitCurrentQuest(false);
                                        questState.playSound("ItemSound.quest_finish");
                                        string2 = "maestro_leorin_q0234_55.htm";
                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                    }
                                    if (n == 12 && (questState.getQuestItemsCount(4828) > 0L || questState.getQuestItemsCount(4829) > 0L || questState.getQuestItemsCount(4830) > 0L || questState.getQuestItemsCount(287) > 0L)) {
                                        questState.giveItems(7899, 1L);
                                        questState.giveItems(5011, 1L);
                                        if (questState.getQuestItemsCount(287) > 0L) {
                                            questState.takeItems(287, 1L);
                                        } else if (questState.getQuestItemsCount(4828) > 0L) {
                                            questState.takeItems(4828, 1L);
                                        } else if (questState.getQuestItemsCount(4829) > 0L) {
                                            questState.takeItems(4829, 1L);
                                        } else if (questState.getQuestItemsCount(4830) > 0L) {
                                            questState.takeItems(4830, 1L);
                                        }
                                        this.giveExtraReward(questState.getPlayer());
                                        questState.exitCurrentQuest(false);
                                        questState.playSound("ItemSound.quest_finish");
                                        string2 = "maestro_leorin_q0234_55.htm";
                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                    }
                                    if (n == 13 && (questState.getQuestItemsCount(4858) > 0L || questState.getQuestItemsCount(4859) > 0L || questState.getQuestItemsCount(4860) > 0L || questState.getQuestItemsCount(97) > 0L)) {
                                        questState.giveItems(7899, 1L);
                                        questState.giveItems(5011, 1L);
                                        if (questState.getQuestItemsCount(97) > 0L) {
                                            questState.takeItems(97, 1L);
                                        } else if (questState.getQuestItemsCount(4858) > 0L) {
                                            questState.takeItems(4858, 1L);
                                        } else if (questState.getQuestItemsCount(4859) > 0L) {
                                            questState.takeItems(4859, 1L);
                                        } else if (questState.getQuestItemsCount(4860) > 0L) {
                                            questState.takeItems(4860, 1L);
                                        }
                                        this.giveExtraReward(questState.getPlayer());
                                        questState.exitCurrentQuest(false);
                                        questState.playSound("ItemSound.quest_finish");
                                        string2 = "maestro_leorin_q0234_55.htm";
                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                    }
                                    if (n == 14 && (questState.getQuestItemsCount(4753) > 0L || questState.getQuestItemsCount(4754) > 0L || questState.getQuestItemsCount(4755) > 0L || questState.getQuestItemsCount(175) > 0L)) {
                                        questState.giveItems(7899, 1L);
                                        questState.giveItems(5011, 1L);
                                        if (questState.getQuestItemsCount(175) > 0L) {
                                            questState.takeItems(175, 1L);
                                        } else if (questState.getQuestItemsCount(4753) > 0L) {
                                            questState.takeItems(4753, 1L);
                                        } else if (questState.getQuestItemsCount(4754) > 0L) {
                                            questState.takeItems(4754, 1L);
                                        } else if (questState.getQuestItemsCount(4755) > 0L) {
                                            questState.takeItems(4755, 1L);
                                        }
                                        this.giveExtraReward(questState.getPlayer());
                                        questState.exitCurrentQuest(false);
                                        questState.playSound("ItemSound.quest_finish");
                                        string2 = "maestro_leorin_q0234_55.htm";
                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                    }
                                    if (n == 15 && (questState.getQuestItemsCount(4900) > 0L || questState.getQuestItemsCount(4901) > 0L || questState.getQuestItemsCount(4902) > 0L || questState.getQuestItemsCount(210) > 0L)) {
                                        questState.giveItems(7899, 1L);
                                        questState.giveItems(5011, 1L);
                                        if (questState.getQuestItemsCount(210) > 0L) {
                                            questState.takeItems(210, 1L);
                                        } else if (questState.getQuestItemsCount(4900) > 0L) {
                                            questState.takeItems(4900, 1L);
                                        } else if (questState.getQuestItemsCount(4901) > 0L) {
                                            questState.takeItems(4901, 1L);
                                        } else if (questState.getQuestItemsCount(4902) > 0L) {
                                            questState.takeItems(4902, 1L);
                                        }
                                        this.giveExtraReward(questState.getPlayer());
                                        questState.exitCurrentQuest(false);
                                        questState.playSound("ItemSound.quest_finish");
                                        string2 = "maestro_leorin_q0234_55.htm";
                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                    }
                                    if (n == 16 && (questState.getQuestItemsCount(4780) > 0L || questState.getQuestItemsCount(4781) > 0L || questState.getQuestItemsCount(4782) > 0L || questState.getQuestItemsCount(234) > 0L || questState.getQuestItemsCount(6359) > 0L)) {
                                        questState.giveItems(7899, 1L);
                                        questState.giveItems(5011, 1L);
                                        if (questState.getQuestItemsCount(234) > 0L) {
                                            questState.takeItems(234, 1L);
                                        } else if (questState.getQuestItemsCount(4780) > 0L) {
                                            questState.takeItems(4780, 1L);
                                        } else if (questState.getQuestItemsCount(4781) > 0L) {
                                            questState.takeItems(4781, 1L);
                                        } else if (questState.getQuestItemsCount(4782) > 0L) {
                                            questState.takeItems(4782, 1L);
                                        } else if (questState.getQuestItemsCount(6359) > 0L) {
                                            questState.takeItems(6359, 1L);
                                        }
                                        this.giveExtraReward(questState.getPlayer());
                                        questState.exitCurrentQuest(false);
                                        questState.playSound("ItemSound.quest_finish");
                                        string2 = "maestro_leorin_q0234_55.htm";
                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                    }
                                    if (n == 17 && (questState.getQuestItemsCount(4804) > 0L || questState.getQuestItemsCount(4805) > 0L || questState.getQuestItemsCount(4806) > 0L || questState.getQuestItemsCount(268) > 0L)) {
                                        questState.giveItems(7899, 1L);
                                        questState.giveItems(5011, 1L);
                                        if (questState.getQuestItemsCount(268) > 0L) {
                                            questState.takeItems(268, 1L);
                                        } else if (questState.getQuestItemsCount(4804) > 0L) {
                                            questState.takeItems(4804, 1L);
                                        } else if (questState.getQuestItemsCount(4805) > 0L) {
                                            questState.takeItems(4805, 1L);
                                        } else if (questState.getQuestItemsCount(4806) > 0L) {
                                            questState.takeItems(4806, 1L);
                                        }
                                        this.giveExtraReward(questState.getPlayer());
                                        questState.exitCurrentQuest(false);
                                        questState.playSound("ItemSound.quest_finish");
                                        string2 = "maestro_leorin_q0234_55.htm";
                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                    }
                                    if (n == 18 && (questState.getQuestItemsCount(4750) > 0L || questState.getQuestItemsCount(4751) > 0L || questState.getQuestItemsCount(4752) > 0L || questState.getQuestItemsCount(171) > 0L)) {
                                        questState.giveItems(7899, 1L);
                                        questState.giveItems(5011, 1L);
                                        if (questState.getQuestItemsCount(171) > 0L) {
                                            questState.takeItems(171, 1L);
                                        } else if (questState.getQuestItemsCount(4750) > 0L) {
                                            questState.takeItems(4750, 1L);
                                        } else if (questState.getQuestItemsCount(4751) > 0L) {
                                            questState.takeItems(4751, 1L);
                                        } else if (questState.getQuestItemsCount(4752) > 0L) {
                                            questState.takeItems(4752, 1L);
                                        }
                                        this.giveExtraReward(questState.getPlayer());
                                        questState.exitCurrentQuest(false);
                                        questState.playSound("ItemSound.quest_finish");
                                        string2 = "maestro_leorin_q0234_55.htm";
                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                    }
                                    if (n == 19 && questState.getQuestItemsCount(2626) > 0L) {
                                        questState.giveItems(7899, 1L);
                                        questState.giveItems(5011, 1L);
                                        questState.takeItems(2626, 1L);
                                        this.giveExtraReward(questState.getPlayer());
                                        questState.exitCurrentQuest(false);
                                        questState.playSound("ItemSound.quest_finish");
                                        string2 = "maestro_leorin_q0234_55.htm";
                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                    }
                                    if (n == 41 && (questState.getQuestItemsCount(7883) > 0L || questState.getQuestItemsCount(8105) > 0L || questState.getQuestItemsCount(8106) > 0L || questState.getQuestItemsCount(8107) > 0L)) {
                                        questState.giveItems(7899, 1L);
                                        questState.giveItems(5011, 1L);
                                        if (questState.getQuestItemsCount(7883) > 0L) {
                                            questState.takeItems(7883, 1L);
                                        } else if (questState.getQuestItemsCount(8105) > 0L) {
                                            questState.takeItems(8105, 1L);
                                        } else if (questState.getQuestItemsCount(8106) > 0L) {
                                            questState.takeItems(8106, 1L);
                                        } else if (questState.getQuestItemsCount(8107) > 0L) {
                                            questState.takeItems(8107, 1L);
                                        }
                                        this.giveExtraReward(questState.getPlayer());
                                        questState.exitCurrentQuest(false);
                                        questState.playSound("ItemSound.quest_finish");
                                        string2 = "maestro_leorin_q0234_55.htm";
                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                    }
                                    if (n == 42 && (questState.getQuestItemsCount(7889) > 0L || questState.getQuestItemsCount(8117) > 0L || questState.getQuestItemsCount(8118) > 0L || questState.getQuestItemsCount(8119) > 0L)) {
                                        questState.giveItems(7899, 1L);
                                        questState.giveItems(5011, 1L);
                                        if (questState.getQuestItemsCount(7889) > 0L) {
                                            questState.takeItems(7889, 1L);
                                        } else if (questState.getQuestItemsCount(8117) > 0L) {
                                            questState.takeItems(8117, 1L);
                                        } else if (questState.getQuestItemsCount(8118) > 0L) {
                                            questState.takeItems(8118, 1L);
                                        } else if (questState.getQuestItemsCount(8119) > 0L) {
                                            questState.takeItems(8119, 1L);
                                        }
                                        this.giveExtraReward(questState.getPlayer());
                                        questState.exitCurrentQuest(false);
                                        questState.playSound("ItemSound.quest_finish");
                                        string2 = "maestro_leorin_q0234_55.htm";
                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                    }
                                    if (n == 43 && (questState.getQuestItemsCount(7901) > 0L || questState.getQuestItemsCount(8132) > 0L || questState.getQuestItemsCount(8133) > 0L || questState.getQuestItemsCount(8134) > 0L)) {
                                        questState.giveItems(7899, 1L);
                                        questState.giveItems(5011, 1L);
                                        if (questState.getQuestItemsCount(7901) > 0L) {
                                            questState.takeItems(7901, 1L);
                                        } else if (questState.getQuestItemsCount(8132) > 0L) {
                                            questState.takeItems(8132, 1L);
                                        } else if (questState.getQuestItemsCount(8133) > 0L) {
                                            questState.takeItems(8133, 1L);
                                        } else if (questState.getQuestItemsCount(8134) > 0L) {
                                            questState.takeItems(8134, 1L);
                                        }
                                        this.giveExtraReward(questState.getPlayer());
                                        questState.exitCurrentQuest(false);
                                        questState.playSound("ItemSound.quest_finish");
                                        string2 = "maestro_leorin_q0234_55.htm";
                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                    }
                                    if (n == 44 && (questState.getQuestItemsCount(7893) > 0L || questState.getQuestItemsCount(8144) > 0L || questState.getQuestItemsCount(8145) > 0L || questState.getQuestItemsCount(8146) > 0L)) {
                                        questState.giveItems(7899, 1L);
                                        questState.giveItems(5011, 1L);
                                        if (questState.getQuestItemsCount(7893) > 0L) {
                                            questState.takeItems(7893, 1L);
                                        } else if (questState.getQuestItemsCount(8144) > 0L) {
                                            questState.takeItems(8144, 1L);
                                        } else if (questState.getQuestItemsCount(8145) > 0L) {
                                            questState.takeItems(8145, 1L);
                                        } else if (questState.getQuestItemsCount(8146) > 0L) {
                                            questState.takeItems(8146, 1L);
                                        }
                                        this.giveExtraReward(questState.getPlayer());
                                        questState.exitCurrentQuest(false);
                                        questState.playSound("ItemSound.quest_finish");
                                        string2 = "maestro_leorin_q0234_55.htm";
                                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                                    }
                                    break block1205;
                                }
                                if (n2 != 30182) break block1239;
                                if (!string.equalsIgnoreCase("menu_select?ask=234&reply=1")) break block1240;
                                string2 = "cliff_q0234_02.htm";
                                break block1205;
                            }
                            if (!string.equalsIgnoreCase("menu_select?ask=234&reply=2")) break block1241;
                            string2 = "cliff_q0234_03.htm";
                            break block1205;
                        }
                        if (!string.equalsIgnoreCase("menu_select?ask=234&reply=3") || n != 4 || questState.getQuestItemsCount(4672) != 0L) break block1205;
                        questState.giveItems(4672, 1L);
                        string2 = "cliff_q0234_04.htm";
                        break block1205;
                    }
                    if (n2 != 30178) break block1242;
                    if (!string.equalsIgnoreCase("menu_select?ask=234&reply=1")) break block1205;
                    questState.setCond(6);
                    questState.set("whispers_of_destiny", String.valueOf(7), true);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "zenkin_q0234_02.htm";
                    break block1205;
                }
                if (n2 != 30833) break block1205;
                if (!string.equalsIgnoreCase("menu_select?ask=234&reply=1")) break block1243;
                if (n != 7) break block1205;
                string2 = "master_kaspar_q0234_02.htm";
                break block1205;
            }
            if (!string.equalsIgnoreCase("menu_select?ask=234&reply=2") || n != 7) break block1205;
            questState.setCond(7);
            questState.set("whispers_of_destiny", String.valueOf(8), true);
            questState.giveItems(4665, 1L);
            questState.playSound("ItemSound.quest_middle");
            string2 = "master_kaspar_q0234_03a.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("whispers_of_destiny");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 31002) break;
                if (questState.getPlayer().getLevel() >= 75) {
                    string = "maestro_leorin_q0234_01.htm";
                    break;
                }
                questState.exitCurrentQuest(true);
                string = "maestro_leorin_q0234_01a.htm";
                break;
            }
            case 2: {
                if (n2 == 31002) {
                    if (n == 1 && questState.getQuestItemsCount(4666) == 0L) {
                        string = "maestro_leorin_q0234_09.htm";
                        break;
                    }
                    if (n == 1 && questState.getQuestItemsCount(4666) == 1L) {
                        string = "maestro_leorin_q0234_10.htm";
                        break;
                    }
                    if (n == 2 && (questState.getQuestItemsCount(4667) < 1L || questState.getQuestItemsCount(4668) < 1L || questState.getQuestItemsCount(4669) < 1L)) {
                        string = "maestro_leorin_q0234_12.htm";
                        break;
                    }
                    if (n == 2 && questState.getQuestItemsCount(4667) >= 1L && questState.getQuestItemsCount(4668) >= 1L && questState.getQuestItemsCount(4669) >= 1L) {
                        string = "maestro_leorin_q0234_13.htm";
                        break;
                    }
                    if (n == 4 && questState.getQuestItemsCount(4672) == 0L) {
                        string = "maestro_leorin_q0234_15.htm";
                        break;
                    }
                    if (n == 4 && questState.getQuestItemsCount(4672) == 1L) {
                        string = "maestro_leorin_q0234_16.htm";
                        break;
                    }
                    if (n == 5 && questState.getQuestItemsCount(4670) == 0L) {
                        string = "maestro_leorin_q0234_18.htm";
                        break;
                    }
                    if (n == 5 && questState.getQuestItemsCount(4670) == 1L) {
                        string = "maestro_leorin_q0234_19.htm";
                        break;
                    }
                    if (n < 9 && n >= 6) {
                        string = "maestro_leorin_q0234_21.htm";
                        break;
                    }
                    if (n == 9 && questState.getQuestItemsCount(4671) == 1L) {
                        string = "maestro_leorin_q0234_22.htm";
                        break;
                    }
                    if (n == 10 && questState.getQuestItemsCount(1460) < 984L) {
                        string = "maestro_leorin_q0234_24.htm";
                        break;
                    }
                    if (n == 10 && questState.getQuestItemsCount(1460) >= 984L) {
                        string = "maestro_leorin_q0234_25.htm";
                        break;
                    }
                    if (n == 11 && (questState.getQuestItemsCount(79) > 0L || questState.getQuestItemsCount(4717) > 0L || questState.getQuestItemsCount(4718) > 0L || questState.getQuestItemsCount(4719) > 0L)) {
                        string = "maestro_leorin_q0234_35.htm";
                        break;
                    }
                    if (n == 11 && questState.getQuestItemsCount(79) == 0L && questState.getQuestItemsCount(4717) == 0L && questState.getQuestItemsCount(4718) == 0L && questState.getQuestItemsCount(4719) == 0L) {
                        string = "maestro_leorin_q0234_35a.htm";
                        break;
                    }
                    if (n == 12 && (questState.getQuestItemsCount(4828) > 0L || questState.getQuestItemsCount(4829) > 0L || questState.getQuestItemsCount(4830) > 0L || questState.getQuestItemsCount(287) > 0L)) {
                        string = "maestro_leorin_q0234_36.htm";
                        break;
                    }
                    if (n == 12 && questState.getQuestItemsCount(4828) == 0L && questState.getQuestItemsCount(4829) == 0L && questState.getQuestItemsCount(4830) == 0L && questState.getQuestItemsCount(287) == 0L) {
                        string = "maestro_leorin_q0234_36a.htm";
                        break;
                    }
                    if (n == 13 && (questState.getQuestItemsCount(4858) > 0L || questState.getQuestItemsCount(4859) > 0L || questState.getQuestItemsCount(4860) > 0L || questState.getQuestItemsCount(97) > 0L)) {
                        string = "maestro_leorin_q0234_37.htm";
                        break;
                    }
                    if (n == 13 && questState.getQuestItemsCount(4858) == 0L && questState.getQuestItemsCount(4859) == 0L && questState.getQuestItemsCount(4860) == 0L && questState.getQuestItemsCount(97) == 0L) {
                        string = "maestro_leorin_q0234_37a.htm";
                        break;
                    }
                    if (n == 14 && (questState.getQuestItemsCount(4753) > 0L || questState.getQuestItemsCount(4754) > 0L || questState.getQuestItemsCount(4755) > 0L || questState.getQuestItemsCount(175) > 0L)) {
                        string = "maestro_leorin_q0234_38.htm";
                        break;
                    }
                    if (n == 14 && questState.getQuestItemsCount(175) == 0L && questState.getQuestItemsCount(4753) == 0L && questState.getQuestItemsCount(4754) == 0L && questState.getQuestItemsCount(4755) == 0L) {
                        string = "maestro_leorin_q0234_38a.htm";
                        break;
                    }
                    if (n == 15 && (questState.getQuestItemsCount(4900) > 0L || questState.getQuestItemsCount(4901) > 0L || questState.getQuestItemsCount(4902) > 0L || questState.getQuestItemsCount(210) > 0L)) {
                        string = "maestro_leorin_q0234_39.htm";
                        break;
                    }
                    if (n == 15 && questState.getQuestItemsCount(210) == 0L && questState.getQuestItemsCount(4900) == 0L && questState.getQuestItemsCount(4901) == 0L && questState.getQuestItemsCount(4902) == 0L) {
                        string = "maestro_leorin_q0234_39a.htm";
                        break;
                    }
                    if (n == 16 && (questState.getQuestItemsCount(4780) > 0L || questState.getQuestItemsCount(4781) > 0L || questState.getQuestItemsCount(4782) > 0L || questState.getQuestItemsCount(234) > 0L || questState.getQuestItemsCount(6359) > 0L)) {
                        string = "maestro_leorin_q0234_40.htm";
                        break;
                    }
                    if (n == 16 && questState.getQuestItemsCount(234) == 0L && questState.getQuestItemsCount(4780) == 0L && questState.getQuestItemsCount(4781) == 0L && questState.getQuestItemsCount(4782) == 0L && questState.getQuestItemsCount(6359) == 0L) {
                        string = "maestro_leorin_q0234_40a.htm";
                        break;
                    }
                    if (n == 17 && (questState.getQuestItemsCount(4804) > 0L || questState.getQuestItemsCount(4805) > 0L || questState.getQuestItemsCount(4806) > 0L || questState.getQuestItemsCount(268) > 0L)) {
                        string = "maestro_leorin_q0234_41.htm";
                        break;
                    }
                    if (n == 17 && questState.getQuestItemsCount(268) == 0L && questState.getQuestItemsCount(4804) == 0L && questState.getQuestItemsCount(4805) == 0L && questState.getQuestItemsCount(4806) == 0L) {
                        string = "maestro_leorin_q0234_41a.htm";
                        break;
                    }
                    if (n == 18 && (questState.getQuestItemsCount(4750) > 0L || questState.getQuestItemsCount(4751) > 0L || questState.getQuestItemsCount(4752) > 0L || questState.getQuestItemsCount(171) > 0L)) {
                        string = "maestro_leorin_q0234_42.htm";
                        break;
                    }
                    if (n == 18 && questState.getQuestItemsCount(171) == 0L && questState.getQuestItemsCount(4750) == 0L && questState.getQuestItemsCount(4751) == 0L && questState.getQuestItemsCount(4752) == 0L) {
                        string = "maestro_leorin_q0234_42a.htm";
                        break;
                    }
                    if (n == 19 && questState.getQuestItemsCount(2626) > 0L) {
                        string = "maestro_leorin_q0234_43.htm";
                        break;
                    }
                    if (n == 19 && questState.getQuestItemsCount(2626) == 0L) {
                        string = "maestro_leorin_q0234_43a.htm";
                        break;
                    }
                    if (n == 41 && (questState.getQuestItemsCount(7883) > 0L || questState.getQuestItemsCount(8105) > 0L || questState.getQuestItemsCount(8106) > 0L || questState.getQuestItemsCount(8107) > 0L)) {
                        string = "maestro_leorin_q0234_43b.htm";
                        break;
                    }
                    if (n == 41 && questState.getQuestItemsCount(7883) == 0L && questState.getQuestItemsCount(8105) == 0L && questState.getQuestItemsCount(8106) == 0L && questState.getQuestItemsCount(8107) == 0L) {
                        string = "maestro_leorin_q0234_43c.htm";
                        break;
                    }
                    if (n == 42 && (questState.getQuestItemsCount(7889) > 0L || questState.getQuestItemsCount(8117) > 0L || questState.getQuestItemsCount(8118) > 0L || questState.getQuestItemsCount(8119) > 0L)) {
                        string = "maestro_leorin_q0234_43d.htm";
                        break;
                    }
                    if (n == 42 && questState.getQuestItemsCount(7889) == 0L && questState.getQuestItemsCount(8117) == 0L && questState.getQuestItemsCount(8118) == 0L && questState.getQuestItemsCount(8119) == 0L) {
                        string = "maestro_leorin_q0234_43e.htm";
                        break;
                    }
                    if (n == 43 && (questState.getQuestItemsCount(7901) > 0L || questState.getQuestItemsCount(8132) > 0L || questState.getQuestItemsCount(8133) > 0L || questState.getQuestItemsCount(8134) > 0L)) {
                        string = "maestro_leorin_q0234_43f.htm";
                        break;
                    }
                    if (n == 43 && questState.getQuestItemsCount(7901) == 0L && questState.getQuestItemsCount(8132) == 0L && questState.getQuestItemsCount(8133) == 0L && questState.getQuestItemsCount(8134) == 0L) {
                        string = "maestro_leorin_q0234_43g.htm";
                        break;
                    }
                    if (n == 44 && (questState.getQuestItemsCount(7893) > 0L || questState.getQuestItemsCount(8144) > 0L || questState.getQuestItemsCount(8145) > 0L || questState.getQuestItemsCount(8146) > 0L)) {
                        string = "maestro_leorin_q0234_43h.htm";
                        break;
                    }
                    if (n != 44 || questState.getQuestItemsCount(7893) != 0L || questState.getQuestItemsCount(8144) != 0L || questState.getQuestItemsCount(8145) != 0L || questState.getQuestItemsCount(8146) != 0L) break;
                    string = "maestro_leorin_q0234_43i.htm";
                    break;
                }
                if (n2 == 30182) {
                    if (n == 4 && questState.getQuestItemsCount(4672) == 0L) {
                        string = "cliff_q0234_01.htm";
                        break;
                    }
                    if (n == 4 && questState.getQuestItemsCount(4672) == 1L) {
                        string = "cliff_q0234_05.htm";
                        break;
                    }
                    if (n < 5) break;
                    string = "cliff_q0234_06.htm";
                    break;
                }
                if (n2 == 30847) {
                    if (n == 5 && questState.getQuestItemsCount(4670) == 0L) {
                        questState.giveItems(4670, 1L);
                        string = "head_blacksmith_ferris_q0234_01.htm";
                        break;
                    }
                    if (n == 5 && questState.getQuestItemsCount(4670) == 1L) {
                        string = "head_blacksmith_ferris_q0234_02.htm";
                        break;
                    }
                    if (n < 6) break;
                    string = "head_blacksmith_ferris_q0234_03.htm";
                    break;
                }
                if (n2 == 30178) {
                    if (n == 6) {
                        string = "zenkin_q0234_01.htm";
                        break;
                    }
                    if (n == 7) {
                        string = "zenkin_q0234_03.htm";
                        break;
                    }
                    if (n < 8) break;
                    string = "zenkin_q0234_04.htm";
                    break;
                }
                if (n2 == 30833) {
                    if (n == 7) {
                        string = "master_kaspar_q0234_01.htm";
                        break;
                    }
                    if (n == 8 && questState.getQuestItemsCount(4673) == 0L) {
                        string = "master_kaspar_q0234_03.htm";
                        break;
                    }
                    if (n == 8 && questState.getQuestItemsCount(4673) == 1L) {
                        questState.setCond(8);
                        questState.set("whispers_of_destiny", String.valueOf(9), true);
                        questState.giveItems(4671, 1L);
                        questState.takeItems(4673, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "master_kaspar_q0234_04.htm";
                        break;
                    }
                    if (n < 9) break;
                    string = "master_kaspar_q0234_05.htm";
                    break;
                }
                if (n2 == 31027) {
                    if (n == 1 && questState.getQuestItemsCount(4666) == 0L) {
                        questState.giveItems(4666, 1L);
                        questState.playSound("ItemSound.quest_itemget");
                        string = "coffer_of_the_dead_q0234_01.htm";
                        break;
                    }
                    if (n <= 1 && questState.getQuestItemsCount(4666) != 1L) break;
                    string = "coffer_of_the_dead_q0234_02.htm";
                    break;
                }
                if (n2 == 31028) {
                    if (n == 2 && questState.getQuestItemsCount(4667) == 0L) {
                        questState.giveItems(4667, 1L);
                        questState.playSound("ItemSound.quest_itemget");
                        string = "chest_of_kernon_q0234_01.htm";
                        break;
                    }
                    if (n == 2 && questState.getQuestItemsCount(4667) != 1L) break;
                    string = "chest_of_kernon_q0234_02.htm";
                    break;
                }
                if (n2 == 31029) {
                    if (n == 2 && questState.getQuestItemsCount(4668) == 0L) {
                        questState.giveItems(4668, 1L);
                        questState.playSound("ItemSound.quest_itemget");
                        string = "chest_of_golkonda_q0234_01.htm";
                        break;
                    }
                    if (n == 2 && questState.getQuestItemsCount(4668) != 1L) break;
                    string = "chest_of_golkonda_q0234_02.htm";
                    break;
                }
                if (n2 != 31030) break;
                if (n == 2 && questState.getQuestItemsCount(4669) == 0L) {
                    questState.giveItems(4669, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                    string = "chest_of_hallate_q0234_01.htm";
                    break;
                }
                if (n == 2 && questState.getQuestItemsCount(4669) != 1L) break;
                string = "chest_of_hallate_q0234_02.htm";
            }
        }
        return string;
    }

    public String onAttack(NpcInstance npcInstance, QuestState questState) {
        if (npcInstance.getNpcId() == 29020 && questState.getQuestItemsCount(4665) >= 1L && questState.getQuestItemsCount(4673) == 0L && questState.getItemEquipped(5) == 4665) {
            questState.takeItems(4665, 1L);
            questState.giveItems(4673, 1L);
            questState.playSound("ItemSound.quest_itemget");
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"WHO_DARES_TO_AND_STEAL_MY_NOBLE_BLOOD", (Object[])new Object[0]);
        }
        return null;
    }
}
