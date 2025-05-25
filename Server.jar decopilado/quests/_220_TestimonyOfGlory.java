/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.ClassId
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.RadarControl
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.RadarControl;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;

public class _220_TestimonyOfGlory
extends Quest
implements ScriptFile {
    private static final int aej = 30514;
    private static final int aek = 30642;
    private static final int ael = 30515;
    private static final int aem = 30501;
    private static final int aen = 30615;
    private static final int aeo = 30616;
    private static final int aep = 30617;
    private static final int aeq = 30618;
    private static final int aer = 30619;
    private static final int aes = 30571;
    private static final int aet = 30565;
    private static final int aeu = 3204;
    private static final int aev = 3205;
    private static final int aew = 3206;
    private static final int aex = 3207;
    private static final int aey = 3208;
    private static final int aez = 3209;
    private static final int aeA = 3210;
    private static final int aeB = 3211;
    private static final int aeC = 3212;
    private static final int aeD = 3213;
    private static final int aeE = 3214;
    private static final int aeF = 3215;
    private static final int aeG = 3216;
    private static final int aeH = 3217;
    private static final int aeI = 3218;
    private static final int aeJ = 3219;
    private static final int aeK = 3220;
    private static final int aeL = 3221;
    private static final int aeM = 3222;
    private static final int aeN = 3223;
    private static final int aeO = 3224;
    private static final int aeP = 3225;
    private static final int aeQ = 3226;
    private static final int aeR = 3227;
    private static final int aeS = 3228;
    private static final int aeT = 3229;
    private static final int aeU = 3230;
    private static final int aeV = 3231;
    private static final int aeW = 3232;
    private static final int aeX = 3233;
    private static final int aeY = 3234;
    private static final int aeZ = 3235;
    private static final int afa = 3236;
    private static final int afb = 3237;
    private static final int afc = 3203;
    private static final int afd = 20192;
    private static final int afe = 20193;
    private static final int aff = 20550;
    private static final int afg = 20563;
    private static final int afh = 20234;
    private static final int afi = 27080;
    private static final int afj = 27081;
    private static final int afk = 27082;
    private static final int afl = 27083;
    private static final int afm = 20583;
    private static final int afn = 20584;
    private static final int afo = 20585;
    private static final int afp = 20586;
    private static final int afq = 20587;
    private static final int afr = 20588;
    private static final int afs = 20601;
    private static final int aft = 20602;
    private static final int afu = 20778;
    private static final int afv = 20779;
    private static final int afw = 27086;

    public _220_TestimonyOfGlory() {
        super(0);
        this.addStartNpc(30514);
        this.addTalkId(new int[]{30642, 30515, 30501, 30615, 30616, 30617, 30618, 30619, 30571, 30565});
        this.addKillId(new int[]{20192, 20193, 20550, 20563, 20234, 27080, 27081, 27082, 27083, 20583, 20584, 20585, 20586, 20587, 20588, 20601, 20602, 20778, 20779, 27086});
        this.addQuestItem(new int[]{3204, 3205, 3206, 3207, 3208, 3209, 3210, 3211, 3212, 3213, 3214, 3215, 3216, 3217, 3218, 3219, 3220, 3221, 3222, 3223, 3224, 3225, 3226, 3227, 3228, 3229, 3230, 3231, 3232, 3233, 3234, 3235, 3236, 3237});
        this.addAttackId(new int[]{20778});
    }

    private static boolean v(Player player) {
        return player.getClassId() == ClassId.orc_shaman || player.getClassId() == ClassId.orc_raider || player.getClassId() == ClassId.orc_monk;
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        Player player = questState.getPlayer();
        if (n == 30514) {
            if (string.equalsIgnoreCase("quest_accept")) {
                if (player.getRace() == Race.orc && player.getLevel() >= 37 && _220_TestimonyOfGlory.v(questState.getPlayer())) {
                    questState.setCond(1);
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                    questState.giveItems(3204, 1L);
                    string2 = "prefect_vokiyan_q0220_05.htm";
                }
            } else if (string.equalsIgnoreCase("reply_1")) {
                string2 = "prefect_vokiyan_q0220_04.htm";
            }
        } else if (n == 30642) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "gandi_chief_chianta_q0220_02.htm";
            } else if (string.equalsIgnoreCase("reply_2") && questState.getQuestItemsCount(3208) > 0L) {
                string2 = "gandi_chief_chianta_q0220_03.htm";
                questState.giveItems(3210, 1L);
                questState.takeItems(3208, 1L);
                questState.setCond(4);
                questState.playSound("ItemSound.quest_middle");
            } else if (string.equalsIgnoreCase("reply_3") && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3211) > 0L && questState.getQuestItemsCount(3213) > 0L && questState.getQuestItemsCount(3214) > 0L && questState.getQuestItemsCount(3215) > 0L && questState.getQuestItemsCount(3212) > 0L) {
                questState.setCond(6);
                questState.playSound("ItemSound.quest_middle");
                string2 = "gandi_chief_chianta_q0220_07.htm";
                questState.giveItems(3217, 1L);
                questState.takeItems(3211, 1L);
                questState.takeItems(3212, 1L);
                questState.takeItems(3213, 1L);
                questState.takeItems(3214, 1L);
                questState.takeItems(3215, 1L);
                questState.takeItems(3210, 1L);
                questState.takeItems(3228, -1L);
                questState.takeItems(3229, -1L);
                questState.takeItems(3230, -1L);
            }
        } else if (n == 30515) {
            if (string.equalsIgnoreCase("reply_1")) {
                if (questState.getQuestItemsCount(3211) == 0L && questState.getQuestItemsCount(3228) > 0L) {
                    string2 = "seer_manakia_q0220_04.htm";
                    questState.getPlayer().addRadarWithMap(80100, 119991, -2264);
                } else if (questState.getQuestItemsCount(3211) > 0L) {
                    string2 = "seer_manakia_q0220_02.htm";
                } else if (questState.getQuestItemsCount(3211) == 0L && questState.getQuestItemsCount(3228) == 0L) {
                    string2 = "seer_manakia_q0220_03.htm";
                    questState.giveItems(3228, 1L);
                    questState.getPlayer().addRadarWithMap(80100, 119991, -2264);
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                if (questState.getQuestItemsCount(3212) > 0L) {
                    string2 = "seer_manakia_q0220_05.htm";
                } else if (questState.getQuestItemsCount(3212) == 0L && questState.getQuestItemsCount(3229) == 0L) {
                    string2 = "seer_manakia_q0220_06.htm";
                    questState.giveItems(3229, 1L);
                    questState.getPlayer().addRadarWithMap(12805, 189249, -3616);
                } else if (questState.getQuestItemsCount(3212) == 0L && questState.getQuestItemsCount(3229) > 0L) {
                    string2 = "seer_manakia_q0220_07.htm";
                    questState.getPlayer().addRadarWithMap(12805, 189249, -3616);
                }
            }
        } else if (n == 30501) {
            if (string.equalsIgnoreCase("reply_1")) {
                if (questState.getQuestItemsCount(3213) > 0L) {
                    string2 = "prefect_kasman_q0220_02.htm";
                } else if (questState.getQuestItemsCount(3213) == 0L && questState.getQuestItemsCount(3230) == 0L) {
                    string2 = "prefect_kasman_q0220_03.htm";
                    questState.giveItems(3230, 1L);
                    questState.getPlayer().addRadarWithMap(-2150, 124443, -3724);
                } else if (questState.getQuestItemsCount(3213) == 0L && (questState.getQuestItemsCount(3230) > 0L || questState.getQuestItemsCount(3233) > 0L)) {
                    string2 = "prefect_kasman_q0220_04.htm";
                    questState.getPlayer().addRadarWithMap(-2150, 124443, -3724);
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                if (questState.getQuestItemsCount(3214) > 0L) {
                    string2 = "prefect_kasman_q0220_05.htm";
                } else if (questState.getQuestItemsCount(3214) == 0L && questState.getQuestItemsCount(3231) == 0L) {
                    string2 = "prefect_kasman_q0220_06.htm";
                    questState.giveItems(3231, 1L);
                    questState.getPlayer().addRadarWithMap(-94294, 110818, -3563);
                } else if (questState.getQuestItemsCount(3214) == 0L && questState.getQuestItemsCount(3231) > 0L) {
                    string2 = "prefect_kasman_q0220_07.htm";
                    questState.getPlayer().addRadarWithMap(-94294, 110818, -3563);
                }
            } else if (string.equalsIgnoreCase("reply_3")) {
                if (questState.getQuestItemsCount(3215) > 0L) {
                    string2 = "prefect_kasman_q0220_08.htm";
                } else if (questState.getQuestItemsCount(3215) == 0L && questState.getQuestItemsCount(3232) == 0L) {
                    string2 = "prefect_kasman_q0220_09.htm";
                    questState.giveItems(3232, 1L);
                    questState.getPlayer().addRadarWithMap(-55217, 200628, -3724);
                } else if (questState.getQuestItemsCount(3215) == 0L && questState.getQuestItemsCount(3232) > 0L) {
                    string2 = "prefect_kasman_q0220_10.htm";
                    questState.getPlayer().addRadarWithMap(-55217, 200628, -3724);
                }
            }
        } else if (n == 30615) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "breka_chief_voltar_q0220_03.htm";
            } else if (string.equalsIgnoreCase("reply_2") && questState.getQuestItemsCount(3228) > 0L) {
                string2 = "breka_chief_voltar_q0220_04.htm";
                questState.addSpawn(27080, 1200000);
                questState.addSpawn(27081, 1200000);
                questState.giveItems(3223, 1L);
                questState.takeItems(3228, 1L);
            }
        } else if (n == 30616) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "enku_chief_kepra_q0220_03.htm";
            } else if (string.equalsIgnoreCase("reply_2") && questState.getQuestItemsCount(3229) > 0L) {
                string2 = "enku_chief_kepra_q0220_04.htm";
                questState.addSpawn(27082, 1200000);
                questState.addSpawn(27082, 1200000);
                questState.addSpawn(27082, 1200000);
                questState.addSpawn(27082, 1200000);
                questState.giveItems(3225, 1L);
                questState.takeItems(3229, 1L);
            }
        } else if (n == 30617) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(3231) > 0L) {
                string2 = "turek_chief_burai_q0220_03.htm";
                questState.addSpawn(27083, 1200000);
                questState.addSpawn(27083, 1200000);
                questState.giveItems(3227, 1L);
                questState.takeItems(3231, 1L);
            }
        } else if (n == 30619) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(3230) > 0L) {
                string2 = "vuku_chief_driko_q0220_03.htm";
                questState.giveItems(3233, 1L);
                questState.takeItems(3230, 1L);
            }
        } else if (n == 30618) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(3232) > 0L) {
                string2 = "lennunt_chief_harak_q0220_03.htm";
                questState.giveItems(3215, 1L);
                questState.takeItems(3232, 1L);
                if (questState.getQuestItemsCount(3214) >= 1L && questState.getQuestItemsCount(3212) >= 1L && questState.getQuestItemsCount(3211) >= 1L && questState.getQuestItemsCount(3213) >= 1L) {
                    questState.setCond(5);
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 30571) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "seer_tanapi_q0220_02.htm";
            } else if (string.equalsIgnoreCase("reply_2") && questState.getQuestItemsCount(3220) > 0L) {
                string2 = "seer_tanapi_q0220_03.htm";
                questState.giveItems(3235, 1L);
                questState.takeItems(3220, 1L);
                questState.setCond(9);
                questState.playSound("ItemSound.quest_middle");
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        Player player = questState.getPlayer();
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 30514) break;
                if (player.getRace() == Race.orc && player.getLevel() >= 37 && _220_TestimonyOfGlory.v(questState.getPlayer())) {
                    string = "prefect_vokiyan_q0220_03.htm";
                    break;
                }
                if (player.getRace() == Race.orc && player.getLevel() >= 37) {
                    string = "prefect_vokiyan_q0220_01a.htm";
                    break;
                }
                if (player.getRace() == Race.orc) {
                    string = "prefect_vokiyan_q0220_02.htm";
                    break;
                }
                string = "prefect_vokiyan_q0220_01.htm";
                break;
            }
            case 2: {
                NpcInstance npcInstance2;
                NpcInstance npcInstance3;
                if (n == 30514) {
                    if (questState.getQuestItemsCount(3204) > 0L && (questState.getQuestItemsCount(3205) < 10L || questState.getQuestItemsCount(3205) < 10L || questState.getQuestItemsCount(3206) < 10L || questState.getQuestItemsCount(3207) < 10L)) {
                        string = "prefect_vokiyan_q0220_06.htm";
                    } else if (questState.getQuestItemsCount(3204) > 0L && questState.getQuestItemsCount(3205) >= 10L && questState.getQuestItemsCount(3206) >= 10L && questState.getQuestItemsCount(3207) >= 10L) {
                        string = "prefect_vokiyan_q0220_08.htm";
                        questState.setCond(3);
                        questState.playSound("ItemSound.quest_middle");
                        questState.giveItems(3208, 1L);
                        questState.giveItems(3209, 1L);
                        questState.takeItems(3204, 1L);
                        questState.takeItems(3205, questState.getQuestItemsCount(3205));
                        questState.takeItems(3206, questState.getQuestItemsCount(3206));
                        questState.takeItems(3207, questState.getQuestItemsCount(3207));
                    } else if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3208) > 0L) {
                        string = "prefect_vokiyan_q0220_09.htm";
                    } else if (questState.getQuestItemsCount(3209) == 0L && (questState.getQuestItemsCount(3208) > 0L || questState.getQuestItemsCount(3220) > 0L)) {
                        string = "prefect_vokiyan_q0220_10.htm";
                    }
                } else if (n == 30642) {
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3208) > 0L) {
                        string = "gandi_chief_chianta_q0220_01.htm";
                    } else if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3211) + questState.getQuestItemsCount(3213) + questState.getQuestItemsCount(3214) + questState.getQuestItemsCount(3215) + questState.getQuestItemsCount(3212) < 5L) {
                        string = "gandi_chief_chianta_q0220_04.htm";
                    } else if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3211) > 0L && questState.getQuestItemsCount(3213) > 0L && questState.getQuestItemsCount(3214) > 0L && questState.getQuestItemsCount(3215) > 0L && questState.getQuestItemsCount(3212) > 0L) {
                        string = "gandi_chief_chianta_q0220_05.htm";
                    } else if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3216) > 0L) {
                        string = "gandi_chief_chianta_q0220_09.htm";
                        questState.giveItems(3217, 1L);
                        questState.takeItems(3216, 1L);
                    } else if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3217) > 0L && (questState.getQuestItemsCount(3218) < 20L || questState.getQuestItemsCount(3219) < 20L)) {
                        string = "gandi_chief_chianta_q0220_10.htm";
                    } else if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3217) > 0L && questState.getQuestItemsCount(3218) >= 20L && questState.getQuestItemsCount(3219) >= 20L) {
                        questState.giveItems(3220, 1L);
                        questState.takeItems(3209, 1L);
                        questState.takeItems(3217, 1L);
                        questState.takeItems(3218, questState.getQuestItemsCount(3218));
                        questState.takeItems(3219, questState.getQuestItemsCount(3219));
                        string = "gandi_chief_chianta_q0220_11.htm";
                        questState.setCond(8);
                        questState.playSound("ItemSound.quest_middle");
                    } else if (questState.getQuestItemsCount(3220) > 0L) {
                        string = "gandi_chief_chianta_q0220_12.htm";
                    } else if (questState.getQuestItemsCount(3235) > 0L || questState.getQuestItemsCount(3237) > 0L) {
                        string = "gandi_chief_chianta_q0220_13.htm";
                    }
                } else if (n == 30515) {
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L) {
                        string = "seer_manakia_q0220_01.htm";
                    } else if (questState.getQuestItemsCount(3216) > 0L || questState.getQuestItemsCount(3217) > 0L || questState.getQuestItemsCount(3220) > 0L) {
                        string = "seer_manakia_q0220_08.htm";
                    }
                } else if (n == 30501) {
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L) {
                        string = "prefect_kasman_q0220_01.htm";
                    } else if (questState.getQuestItemsCount(3216) > 0L || questState.getQuestItemsCount(3217) > 0L || questState.getQuestItemsCount(3220) > 0L) {
                        string = "prefect_kasman_q0220_11.htm";
                    }
                } else if (n == 30615) {
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3211) == 0L && questState.getQuestItemsCount(3228) == 0L && questState.getQuestItemsCount(3223) == 0L && questState.getQuestItemsCount(3221) == 0L && questState.getQuestItemsCount(3222) == 0L) {
                        string = "breka_chief_voltar_q0220_01.htm";
                    } else if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3228) > 0L) {
                        string = "breka_chief_voltar_q0220_02.htm";
                        player.sendPacket((IStaticPacket)new RadarControl(2, 2, 80100, 119991, -2264));
                    } else if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3223) > 0L && questState.getQuestItemsCount(3221) + questState.getQuestItemsCount(3222) < 2L && questState.getQuestItemsCount(3211) == 0L) {
                        string = "breka_chief_voltar_q0220_05.htm";
                        npcInstance3 = GameObjectsStorage.getByNpcId((int)27080);
                        npcInstance2 = GameObjectsStorage.getByNpcId((int)27081);
                        if (npcInstance3 == null) {
                            NpcInstance npcInstance4 = questState.addSpawn(27080, questState.getPlayer().getX(), questState.getPlayer().getY(), questState.getPlayer().getZ(), questState.getPlayer().getHeading(), 150, 1200000);
                            npcInstance4.getAggroList().addDamageHate((Creature)questState.getPlayer(), 0, 2000);
                        } else if (npcInstance2 == null) {
                            NpcInstance npcInstance5 = questState.addSpawn(27081, questState.getPlayer().getX(), questState.getPlayer().getY(), questState.getPlayer().getZ(), questState.getPlayer().getHeading(), 150, 1200000);
                            npcInstance5.getAggroList().addDamageHate((Creature)questState.getPlayer(), 0, 2000);
                        }
                    } else if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3221) > 0L && questState.getQuestItemsCount(3222) > 0L) {
                        string = "breka_chief_voltar_q0220_06.htm";
                        questState.giveItems(3211, 1L);
                        questState.takeItems(3221, 1L);
                        questState.takeItems(3222, 1L);
                        if (questState.getQuestItemsCount(3214) >= 1L && questState.getQuestItemsCount(3212) >= 1L && questState.getQuestItemsCount(3215) >= 1L && questState.getQuestItemsCount(3213) >= 1L) {
                            questState.setCond(5);
                            questState.playSound("ItemSound.quest_middle");
                        }
                    } else if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3211) > 0L) {
                        string = "breka_chief_voltar_q0220_07.htm";
                    } else if (questState.getQuestItemsCount(3216) > 0L || questState.getQuestItemsCount(3217) > 0L || questState.getQuestItemsCount(3220) > 0L) {
                        string = "breka_chief_voltar_q0220_08.htm";
                    }
                }
                if (n == 30616) {
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3212) == 0L && questState.getQuestItemsCount(3229) == 0L && questState.getQuestItemsCount(3225) == 0L && questState.getQuestItemsCount(3224) < 4L) {
                        string = "enku_chief_kepra_q0220_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3229) > 0L) {
                        string = "enku_chief_kepra_q0220_02.htm";
                        player.sendPacket((IStaticPacket)new RadarControl(2, 2, 12805, 189249, -3616));
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3225) > 0L && questState.getQuestItemsCount(3224) < 4L) {
                        string = "enku_chief_kepra_q0220_05.htm";
                        if (GameObjectsStorage.getCountByNpcId((int)27082) >= 5) break;
                        npcInstance3 = questState.addSpawn(27082, questState.getPlayer().getX(), questState.getPlayer().getY(), 1200000);
                        npcInstance2 = questState.addSpawn(27082, questState.getPlayer().getX(), questState.getPlayer().getY(), 1200000);
                        npcInstance3.getAggroList().addDamageHate((Creature)questState.getPlayer(), 0, 2000);
                        npcInstance2.getAggroList().addDamageHate((Creature)questState.getPlayer(), 0, 2000);
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3224) >= 4L) {
                        string = "enku_chief_kepra_q0220_06.htm";
                        questState.giveItems(3212, 1L);
                        questState.takeItems(3224, questState.getQuestItemsCount(3224));
                        if (questState.getQuestItemsCount(3214) < 1L || questState.getQuestItemsCount(3211) < 1L || questState.getQuestItemsCount(3215) < 1L || questState.getQuestItemsCount(3213) < 1L) break;
                        questState.setCond(5);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3212) > 0L) {
                        string = "enku_chief_kepra_q0220_07.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3216) <= 0L && questState.getQuestItemsCount(3217) <= 0L && questState.getQuestItemsCount(3220) <= 0L) break;
                    string = "enku_chief_kepra_q0220_08.htm";
                    break;
                }
                if (n == 30617) {
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3214) == 0L && questState.getQuestItemsCount(3231) == 0L && questState.getQuestItemsCount(3227) == 0L && questState.getQuestItemsCount(3226) == 0L) {
                        string = "turek_chief_burai_q0220_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3231) > 0L) {
                        string = "turek_chief_burai_q0220_02.htm";
                        player.sendPacket((IStaticPacket)new RadarControl(2, 2, -94294, 110818, -3563));
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3227) > 0L) {
                        string = "turek_chief_burai_q0220_04.htm";
                        if (GameObjectsStorage.getCountByNpcId((int)27083) >= 3) break;
                        questState.addSpawn(27083, questState.getPlayer().getX(), questState.getPlayer().getY(), questState.getPlayer().getZ(), questState.getPlayer().getHeading(), 150, 1200000);
                        questState.addSpawn(27083, questState.getPlayer().getX(), questState.getPlayer().getY(), questState.getPlayer().getZ(), questState.getPlayer().getHeading(), 150, 1200000);
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3226) >= 2L) {
                        string = "turek_chief_burai_q0220_05.htm";
                        questState.giveItems(3214, 1L);
                        questState.takeItems(3226, questState.getQuestItemsCount(3226));
                        if (questState.getQuestItemsCount(3212) < 1L || questState.getQuestItemsCount(3211) < 1L || questState.getQuestItemsCount(3215) < 1L || questState.getQuestItemsCount(3213) < 1L) break;
                        questState.setCond(5);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3214) > 0L) {
                        string = "turek_chief_burai_q0220_06.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) <= 0L || questState.getQuestItemsCount(3216) <= 0L && questState.getQuestItemsCount(3217) <= 0L && questState.getQuestItemsCount(3220) <= 0L) break;
                    string = "turek_chief_burai_q0220_07.htm";
                    break;
                }
                if (n == 30618) {
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3215) == 0L && questState.getQuestItemsCount(3232) == 0L) {
                        string = "lennunt_chief_harak_q0220_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3215) == 0L && questState.getQuestItemsCount(3232) > 0L) {
                        string = "lennunt_chief_harak_q0220_02.htm";
                        player.sendPacket((IStaticPacket)new RadarControl(2, 2, -55217, 200628, -3724));
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3215) > 0L) {
                        string = "lennunt_chief_harak_q0220_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3216) <= 0L && questState.getQuestItemsCount(3217) <= 0L && questState.getQuestItemsCount(3220) <= 0L) break;
                    string = "lennunt_chief_harak_q0220_05.htm";
                    break;
                }
                if (n == 30619) {
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3213) == 0L && questState.getQuestItemsCount(3230) == 0L && questState.getQuestItemsCount(3233) == 0L) {
                        string = "vuku_chief_driko_q0220_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3213) == 0L && questState.getQuestItemsCount(3230) == 1L) {
                        string = "vuku_chief_driko_q0220_02.htm";
                        player.sendPacket((IStaticPacket)new RadarControl(2, 2, -2150, 124443, -3724));
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3213) == 0L && questState.getQuestItemsCount(3233) > 0L && questState.getQuestItemsCount(3234) < 30L) {
                        string = "vuku_chief_driko_q0220_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3213) == 0L && questState.getQuestItemsCount(3233) > 0L && questState.getQuestItemsCount(3234) >= 30L) {
                        string = "vuku_chief_driko_q0220_05.htm";
                        questState.giveItems(3213, 1L);
                        questState.takeItems(3234, questState.getQuestItemsCount(3234));
                        questState.takeItems(3233, questState.getQuestItemsCount(3233));
                        if (questState.getQuestItemsCount(3214) < 1L || questState.getQuestItemsCount(3212) < 1L || questState.getQuestItemsCount(3211) < 1L || questState.getQuestItemsCount(3215) < 1L) break;
                        questState.setCond(5);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3213) > 0L) {
                        string = "vuku_chief_driko_q0220_06.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3209) <= 0L || questState.getQuestItemsCount(3216) <= 0L && questState.getQuestItemsCount(3217) <= 0L && questState.getQuestItemsCount(3220) <= 0L) break;
                    string = "vuku_chief_driko_q0220_07.htm";
                    break;
                }
                if (n == 30571) {
                    if (questState.getQuestItemsCount(3220) > 0L) {
                        string = "seer_tanapi_q0220_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3235) > 0L && questState.getQuestItemsCount(3236) == 0L) {
                        string = "seer_tanapi_q0220_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3235) > 0L && questState.getQuestItemsCount(3236) == 1L) {
                        string = "seer_tanapi_q0220_05.htm";
                        questState.giveItems(3237, 1L);
                        questState.takeItems(3236, 1L);
                        questState.takeItems(3235, 1L);
                        questState.setCond(11);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(3237) <= 0L) break;
                    string = "seer_tanapi_q0220_06.htm";
                    break;
                }
                if (n != 30565) break;
                if (questState.getQuestItemsCount(3237) == 0L && (questState.getQuestItemsCount(3220) > 0L || questState.getQuestItemsCount(3235) > 0L)) {
                    string = "kakai_the_lord_of_flame_q0220_01.htm";
                    break;
                }
                if (questState.getQuestItemsCount(3237) <= 0L) break;
                if (!questState.getPlayer().getVarB("prof2.2")) {
                    questState.giveItems(7562, 8L);
                    questState.addExpAndSp(91457L, 2500L);
                    questState.getPlayer().setVar("prof2.2", "1", -1L);
                    this.giveExtraReward(questState.getPlayer());
                }
                string = "kakai_the_lord_of_flame_q0220_02.htm";
                questState.giveItems(3203, 1L);
                questState.takeItems(3237, 1L);
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
                player.sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20192 || n == 20193) {
            if (questState.getQuestItemsCount(3204) > 0L && questState.getQuestItemsCount(3206) < 10L && Rnd.get((int)20) < 20) {
                if (questState.getQuestItemsCount(3206) >= 9L) {
                    questState.giveItems(3206, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    if (questState.getQuestItemsCount(3205) >= 10L && questState.getQuestItemsCount(3207) >= 10L) {
                        questState.setCond(2);
                    }
                } else {
                    questState.giveItems(3206, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 20550) {
            if (questState.getQuestItemsCount(3204) > 0L && questState.getQuestItemsCount(3207) < 10L && Rnd.get((int)20) < 20) {
                if (questState.getQuestItemsCount(3207) >= 9L) {
                    questState.giveItems(3207, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    if (questState.getQuestItemsCount(3205) >= 10L && questState.getQuestItemsCount(3206) >= 10L) {
                        questState.setCond(2);
                    }
                } else {
                    questState.giveItems(3207, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 20563) {
            if (questState.getQuestItemsCount(3204) > 0L && questState.getQuestItemsCount(3205) < 10L && Rnd.get((int)20) < 20) {
                if (questState.getQuestItemsCount(3205) >= 9L) {
                    questState.giveItems(3205, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    if (questState.getQuestItemsCount(3206) >= 10L && questState.getQuestItemsCount(3207) >= 10L) {
                        questState.setCond(2);
                    }
                } else {
                    questState.giveItems(3205, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 20234) {
            if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3213) == 0L && questState.getQuestItemsCount(3233) > 0L && questState.getQuestItemsCount(3234) < 30L && Rnd.get((int)20) < 20) {
                if (questState.getQuestItemsCount(3234) >= 29L) {
                    questState.giveItems(3234, 1L);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.giveItems(3234, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 27080) {
            if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3223) > 0L && questState.getQuestItemsCount(3221) == 0L) {
                if (questState.getQuestItemsCount(3222) > 0L) {
                    questState.giveItems(3221, 1L);
                    questState.takeItems(3223, 1L);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.giveItems(3221, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 27081) {
            if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3223) > 0L && questState.getQuestItemsCount(3222) == 0L) {
                if (questState.getQuestItemsCount(3221) > 0L) {
                    questState.giveItems(3222, 1L);
                    questState.takeItems(3223, 1L);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.giveItems(3222, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 27082) {
            if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3225) > 0L && questState.getQuestItemsCount(3224) < 4L) {
                if (questState.getQuestItemsCount(3224) == 3L) {
                    questState.giveItems(3224, 1L);
                    questState.takeItems(3225, 1L);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.giveItems(3224, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 27083) {
            if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3210) > 0L && questState.getQuestItemsCount(3227) > 0L && questState.getQuestItemsCount(3226) < 2L) {
                questState.giveItems(3226, 1L, false);
                questState.playSound("ItemSound.quest_itemget");
                if (questState.getQuestItemsCount(3226) > 1L) {
                    questState.takeItems(3227, 1L);
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20583 || n == 20584 || n == 20585 || n == 20586 || n == 20587) {
            if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3217) > 0L && questState.getQuestItemsCount(3219) < 20L && Rnd.get((int)10) < 10) {
                if (questState.getQuestItemsCount(3219) >= 19L) {
                    questState.giveItems(3219, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    if (questState.getQuestItemsCount(3218) >= 20L) {
                        questState.setCond(7);
                    }
                } else {
                    questState.giveItems(3219, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 20588) {
            if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3217) > 0L && questState.getQuestItemsCount(3219) < 20L) {
                if (questState.getQuestItemsCount(3219) >= 19L) {
                    questState.giveItems(3219, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    if (questState.getQuestItemsCount(3218) >= 20L) {
                        questState.setCond(7);
                    }
                } else {
                    questState.giveItems(3219, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 20601 || n == 20602) {
            if (questState.getQuestItemsCount(3209) > 0L && questState.getQuestItemsCount(3217) > 0L && questState.getQuestItemsCount(3218) < 20L && Rnd.get((int)10) < 10) {
                if (questState.getQuestItemsCount(3218) >= 19L) {
                    questState.giveItems(3218, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    if (questState.getQuestItemsCount(3219) >= 20L) {
                        questState.setCond(7);
                    }
                } else {
                    questState.giveItems(3218, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 20778) {
            if (questState.getQuestItemsCount(3235) > 0L && questState.getQuestItemsCount(3236) == 0L) {
                NpcInstance npcInstance2 = questState.addSpawn(27086, 1200000);
                Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"22052", (Object[])new Object[0]);
            }
        } else if (n == 20779) {
            if (questState.getQuestItemsCount(3235) > 0L && questState.getQuestItemsCount(3236) == 0L) {
                NpcInstance npcInstance3 = questState.addSpawn(27086, 1200000);
                Functions.npcSayCustomMessage((NpcInstance)npcInstance3, (String)"22054", (Object[])new Object[0]);
            }
        } else if (n == 27086 && questState.getQuestItemsCount(3235) > 0L && questState.getQuestItemsCount(3236) == 0L) {
            questState.giveItems(3236, 1L);
            questState.setCond(10);
            questState.playSound("ItemSound.quest_middle");
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"22056", (Object[])new Object[0]);
        }
        return null;
    }

    public String onAttack(NpcInstance npcInstance, QuestState questState) {
        if (questState.getQuestItemsCount(3236) == 0L && questState.getInt("attacked") == 0) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"22051", (Object[])new Object[0]);
            questState.set("attacked", "1");
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
