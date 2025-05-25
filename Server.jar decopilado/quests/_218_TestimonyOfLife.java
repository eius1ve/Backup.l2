/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.ClassId
 *  l2.gameserver.model.base.Race
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
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.ScriptFile;

public class _218_TestimonyOfLife
extends Quest
implements ScriptFile {
    private static final int acf = 30460;
    private static final int acg = 30154;
    private static final int ach = 30300;
    private static final int aci = 30371;
    private static final int acj = 30375;
    private static final int ack = 30419;
    private static final int acl = 30655;
    private static final int acm = 20145;
    private static final int acn = 20176;
    private static final int aco = 20233;
    private static final int acp = 27077;
    private static final int acq = 20550;
    private static final int acr = 20581;
    private static final int acs = 20582;
    private static final int act = 20082;
    private static final int acu = 20084;
    private static final int acv = 20086;
    private static final int acw = 20087;
    private static final int acx = 20088;
    private static final int acy = 3140;
    private static final int acz = 3141;
    private static final int acA = 3142;
    private static final int acB = 3143;
    private static final int acC = 3144;
    private static final int acD = 3145;
    private static final int acE = 3146;
    private static final int acF = 3147;
    private static final int acG = 3148;
    private static final int acH = 3149;
    private static final int acI = 3150;
    private static final int acJ = 3151;
    private static final int acK = 3152;
    private static final int acL = 3153;
    private static final int acM = 3154;
    private static final int acN = 3155;
    private static final int acO = 3156;
    private static final int acP = 3157;
    private static final int acQ = 3158;
    private static final int acR = 3159;
    private static final int acS = 3160;
    private static final int acT = 3161;
    private static final int acU = 3162;
    private static final int acV = 3163;
    private static final int acW = 3164;
    private static final int acX = 3165;
    private static final int acY = 3166;
    private static final int acZ = 3167;
    private static final int ada = 3168;
    private static final int adb = 3169;
    private static final int adc = 3170;
    private static final int add = 3171;
    private static final int ade = 3026;

    public _218_TestimonyOfLife() {
        super(0);
        this.addStartNpc(30460);
        this.addTalkId(new int[]{30154, 30300, 30371, 30375, 30419, 30655});
        this.addKillId(new int[]{20145, 20176, 20233, 27077, 20550, 20581, 20582, 20082, 20084, 20086, 20087, 20088});
        this.addQuestItem(new int[]{3141, 3142, 3143, 3144, 3145, 3146, 3147, 3148, 3149, 3150, 3151, 3152, 3153, 3154, 3155, 3156, 3157, 3158, 3159, 3160, 3161, 3162, 3163, 3164, 3165, 3166, 3167, 3168, 3169, 3170, 3171, 3026});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        Player player = questState.getPlayer();
        if (n == 30460) {
            if (string.equalsIgnoreCase("quest_accept") && questState.getCond() == 0 && player.getRace() == Race.elf && player.getLevel() >= 37 && (player.getClassId() == ClassId.elven_scout || player.getClassId() == ClassId.elven_knight || player.getClassId() == ClassId.elven_wizard || player.getClassId() == ClassId.oracle)) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                if (questState.getQuestItemsCount(3141) == 0L) {
                    questState.giveItems(3141, 1L);
                }
                questState.playSound("ItemSound.quest_middle");
                string2 = "master_cardien_q0218_04.htm";
            }
        } else if (n == 30154) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "ozzy_q0218_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "ozzy_q0218_03.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                string2 = "ozzy_q0218_04.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                string2 = "ozzy_q0218_05.htm";
            } else if (string.equalsIgnoreCase("reply_5")) {
                string2 = "ozzy_q0218_06.htm";
            } else if (string.equalsIgnoreCase("reply_6") && questState.getQuestItemsCount(3141) >= 1L) {
                string2 = "ozzy_q0218_07.htm";
                questState.takeItems(3141, 1L);
                questState.giveItems(3144, 1L);
                questState.giveItems(3143, 1L);
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 30300) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "blacksmith_pushkin_q0218_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "blacksmith_pushkin_q0218_03.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                string2 = "blacksmith_pushkin_q0218_04.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                string2 = "blacksmith_pushkin_q0218_05.htm";
            } else if (string.equalsIgnoreCase("reply_5")) {
                if (questState.getQuestItemsCount(3145) >= 1L) {
                    string2 = "blacksmith_pushkin_q0218_06.htm";
                    questState.takeItems(3145, 1L);
                    questState.giveItems(3149, 1L);
                    questState.setCond(4);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else if (string.equalsIgnoreCase("reply_6")) {
                string2 = "blacksmith_pushkin_q0218_09.htm";
            } else if (string.equalsIgnoreCase("reply_7") && questState.getQuestItemsCount(3149) >= 1L) {
                string2 = "blacksmith_pushkin_q0218_10.htm";
                questState.setCond(6);
                questState.playSound("ItemSound.quest_middle");
                questState.takeAllItems(new int[]{3161, 3162, 3163});
                questState.takeItems(3149, 1L);
                questState.giveItems(3150, 1L);
            }
        } else if (n == 30371) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "thalya_q0218_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                if (questState.getQuestItemsCount(3143) >= 1L) {
                    string2 = "thalya_q0218_03.htm";
                    questState.takeItems(3143, 1L);
                    questState.giveItems(3145, 1L);
                    questState.setCond(3);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else if (string.equalsIgnoreCase("reply_3") && questState.getQuestItemsCount(3155) >= 1L) {
                string2 = "thalya_q0218_11.htm";
                questState.takeItems(3155, 1L);
                questState.giveItems(3147, 1L);
                questState.setCond(14);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 30375) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(3152) >= 1L) {
                string2 = "priest_adonius_q0218_02.htm";
                questState.takeItems(3152, 1L);
                questState.giveItems(3153, 1L);
                questState.setCond(9);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 30419) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "arkenia_q0218_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "arkenia_q0218_03.htm";
            } else if (string.equalsIgnoreCase("reply_3") && questState.getQuestItemsCount(3146) >= 1L) {
                string2 = "arkenia_q0218_04.htm";
                questState.takeItems(3146, 1L);
                questState.giveItems(3151, 1L);
                questState.giveItems(3152, 1L);
                questState.setCond(8);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 30655 && string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(3147) >= 1L) {
            string2 = "isael_silvershadow_q0218_02.htm";
            questState.takeItems(3147, 1L);
            questState.giveItems(3156, 1L);
            questState.setCond(15);
            questState.playSound("ItemSound.quest_middle");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        Player player = questState.getPlayer();
        if (player == null) {
            return null;
        }
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 30460) break;
                if (player.getRace() == Race.elf && player.getLevel() >= 37 && (player.getClassId() == ClassId.elven_scout || player.getClassId() == ClassId.elven_knight || player.getClassId() == ClassId.elven_wizard || player.getClassId() == ClassId.oracle)) {
                    string = "master_cardien_q0218_03.htm";
                    break;
                }
                if (player.getRace() == Race.elf && player.getLevel() >= 37) {
                    string = "master_cardien_q0218_01a.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (player.getRace() == Race.elf) {
                    string = "master_cardien_q0218_02.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "master_cardien_q0218_01.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n == 30460) {
                    if (questState.getQuestItemsCount(3141) == 1L) {
                        string = "master_cardien_q0218_05.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) == 1L) {
                        string = "master_cardien_q0218_06.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3142) != 1L) break;
                    questState.addExpAndSp(104591L, 11250L);
                    this.giveExtraReward(questState.getPlayer());
                    questState.takeItems(3142, 1L);
                    questState.giveItems(3140, 1L);
                    string = "master_cardien_q0218_07.htm";
                    questState.exitCurrentQuest(false);
                    questState.playSound("ItemSound.quest_finish");
                    player.sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                    if (!questState.getPlayer().getVarB("prof2.2")) {
                        questState.giveItems(7562, 16L);
                    }
                    questState.getPlayer().setVar("prof2.2", "1", -1L);
                    break;
                }
                if (n == 30154) {
                    if (questState.getQuestItemsCount(3141) == 1L) {
                        string = "ozzy_q0218_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) == 1L && questState.getQuestItemsCount(3160) == 0L) {
                        string = "ozzy_q0218_08.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3160) >= 1L) {
                        string = "ozzy_q0218_09.htm";
                        questState.takeAllItems(3160);
                        questState.takeItems(3144, 1L);
                        questState.giveItems(3142, 1L);
                        questState.setCond(21);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(3142) != 1L) break;
                    string = "ozzy_q0218_10.htm";
                    break;
                }
                if (n == 30300) {
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3145) >= 1L) {
                        string = "blacksmith_pushkin_q0218_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3149) >= 1L) {
                        if (questState.getQuestItemsCount(3161) >= 10L && questState.getQuestItemsCount(3162) >= 20L && questState.getQuestItemsCount(3163) >= 20L) {
                            string = "blacksmith_pushkin_q0218_08.htm";
                            break;
                        }
                        string = "blacksmith_pushkin_q0218_07.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3150) >= 1L) {
                        string = "blacksmith_pushkin_q0218_11.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3145) != 0L || questState.getQuestItemsCount(3149) != 0L || questState.getQuestItemsCount(3150) != 0L || questState.getQuestItemsCount(3144) != 1L) break;
                    string = "blacksmith_pushkin_q0218_12.htm";
                    break;
                }
                if (n == 30371) {
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3143) >= 1L) {
                        string = "thalya_q0218_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3145) >= 1L) {
                        string = "thalya_q0218_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3149) >= 1L) {
                        string = "thalya_q0218_05.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3150) >= 1L) {
                        string = "thalya_q0218_06.htm";
                        questState.takeItems(3150, 1L);
                        questState.giveItems(3146, 1L);
                        questState.setCond(7);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3146) >= 1L) {
                        string = "thalya_q0218_07.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3151) >= 1L) {
                        string = "thalya_q0218_08.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3155) >= 1L) {
                        string = "thalya_q0218_09.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3148) >= 1L) {
                        if (player.getLevel() < 38) {
                            string = "thalya_q0218_12.htm";
                            break;
                        }
                        string = "thalya_q0218_13.htm";
                        questState.takeItems(3148, 1L);
                        questState.giveItems(3147, 1L);
                        questState.setCond(14);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3147) >= 1L) {
                        string = "thalya_q0218_14.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3156) >= 1L) {
                        string = "thalya_q0218_15.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3026) >= 1L && questState.getQuestItemsCount(3157) >= 1L) {
                        string = "thalya_q0218_16.htm";
                        questState.takeItems(3157, 1L);
                        questState.giveItems(3158, 1L);
                        questState.setCond(18);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3026) >= 1L && questState.getQuestItemsCount(3158) >= 1L) {
                        string = "thalya_q0218_17.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3159) >= 1L) {
                        string = "thalya_q0218_18.htm";
                        questState.takeItems(3159, 1L);
                        questState.giveItems(3160, 1L);
                        questState.setCond(20);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(3142) < 1L && (questState.getQuestItemsCount(3160) < 1L || questState.getQuestItemsCount(3144) != 1L)) break;
                    string = "thalya_q0218_19.htm";
                    break;
                }
                if (n == 30375) {
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3152) >= 1L) {
                        string = "priest_adonius_q0218_01.htm";
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3153) >= 1L) {
                        if (questState.getQuestItemsCount(3164) >= 20L && questState.getQuestItemsCount(3165) >= 20L) {
                            string = "priest_adonius_q0218_04.htm";
                            questState.takeItems(3164, questState.getQuestItemsCount(3164));
                            questState.takeItems(3165, questState.getQuestItemsCount(3165));
                            questState.takeItems(3153, 1L);
                            questState.giveItems(3154, 1L);
                            questState.setCond(11);
                            questState.playSound("ItemSound.quest_middle");
                        } else {
                            string = "priest_adonius_q0218_03.htm";
                        }
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3154) >= 1L) {
                        string = "priest_adonius_q0218_05.htm";
                    }
                    if (questState.getQuestItemsCount(3152) != 0L || questState.getQuestItemsCount(3153) != 0L || questState.getQuestItemsCount(3154) != 0L || questState.getQuestItemsCount(3144) != 1L) break;
                    string = "priest_adonius_q0218_06.htm";
                    break;
                }
                if (n == 30419) {
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3146) >= 1L) {
                        string = "arkenia_q0218_01.htm";
                    }
                    if (questState.getQuestItemsCount(3152) >= 1L || questState.getQuestItemsCount(3153) >= 1L && questState.getQuestItemsCount(3144) == 1L) {
                        string = "arkenia_q0218_05.htm";
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3154) >= 1L) {
                        string = "arkenia_q0218_06.htm";
                        questState.takeItems(3151, questState.getQuestItemsCount(3151));
                        questState.takeItems(3154, 1L);
                        questState.giveItems(3155, 1L);
                        questState.setCond(12);
                        questState.playSound("ItemSound.quest_middle");
                    }
                    if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3155) >= 1L) {
                        string = "arkenia_q0218_07.htm";
                    }
                    if (questState.getQuestItemsCount(3146) != 0L || questState.getQuestItemsCount(3151) != 0L || questState.getQuestItemsCount(3154) != 0L || questState.getQuestItemsCount(3155) != 0L || questState.getQuestItemsCount(3144) != 1L) break;
                    string = "arkenia_q0218_08.htm";
                    break;
                }
                if (n != 30655) break;
                if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3147) >= 1L) {
                    string = "isael_silvershadow_q0218_01.htm";
                }
                if (questState.getQuestItemsCount(3144) >= 1L && questState.getQuestItemsCount(3156) >= 1L) {
                    if (questState.getQuestItemsCount(3166) >= 1L && questState.getQuestItemsCount(3167) >= 1L && questState.getQuestItemsCount(3168) >= 1L && questState.getQuestItemsCount(3169) >= 1L && questState.getQuestItemsCount(3170) >= 1L && questState.getQuestItemsCount(3171) >= 1L) {
                        string = "isael_silvershadow_q0218_04.htm";
                        questState.takeItems(3166, -1L);
                        questState.takeItems(3167, -1L);
                        questState.takeItems(3168, -1L);
                        questState.takeItems(3169, -1L);
                        questState.takeItems(3170, -1L);
                        questState.takeItems(3171, -1L);
                        questState.takeItems(3156, 1L);
                        questState.giveItems(3157, 1L);
                        questState.giveItems(3026, 1L);
                        questState.setCond(17);
                        questState.playSound("ItemSound.quest_middle");
                    } else {
                        string = "isael_silvershadow_q0218_03.htm";
                    }
                }
                if (questState.getQuestItemsCount(3026) >= 1L && questState.getQuestItemsCount(3157) >= 1L) {
                    string = "isael_silvershadow_q0218_05.htm";
                }
                if (questState.getQuestItemsCount(3158) < 1L && questState.getQuestItemsCount(3160) < 1L && (questState.getQuestItemsCount(3142) < 1L || questState.getQuestItemsCount(3144) != 1L)) break;
                string = "isael_silvershadow_q0218_06.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20581 && questState.getQuestItemsCount(3156) == 1L && Rnd.get((int)100) < 50) {
            if (questState.getQuestItemsCount(3166) == 0L) {
                questState.giveItems(3166, 1L);
                questState.playSound("ItemSound.quest_middle");
            } else if (questState.getQuestItemsCount(3167) == 0L) {
                questState.giveItems(3167, 1L);
                questState.playSound("ItemSound.quest_middle");
            } else if (questState.getQuestItemsCount(3168) == 0L) {
                questState.giveItems(3168, 1L);
                questState.playSound("ItemSound.quest_middle");
            } else if (questState.getQuestItemsCount(3169) == 0L) {
                questState.giveItems(3169, 1L);
                questState.playSound("ItemSound.quest_middle");
            } else if (questState.getQuestItemsCount(3170) == 0L) {
                questState.giveItems(3170, 1L);
                questState.playSound("ItemSound.quest_middle");
            } else if (questState.getQuestItemsCount(3171) == 0L) {
                questState.giveItems(3171, 1L);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 20582 && questState.getQuestItemsCount(3156) == 1L && Rnd.get((int)100) < 50) {
            if (questState.getQuestItemsCount(3166) == 0L) {
                questState.giveItems(3166, 1L);
                questState.playSound("ItemSound.quest_middle");
            } else if (questState.getQuestItemsCount(3167) == 0L) {
                questState.giveItems(3167, 1L);
                questState.playSound("ItemSound.quest_middle");
            } else if (questState.getQuestItemsCount(3168) == 0L) {
                questState.giveItems(3168, 1L);
                questState.playSound("ItemSound.quest_middle");
            } else if (questState.getQuestItemsCount(3169) == 0L) {
                questState.giveItems(3169, 1L);
                questState.playSound("ItemSound.quest_middle");
            } else if (questState.getQuestItemsCount(3170) == 0L) {
                questState.giveItems(3170, 1L);
                questState.playSound("ItemSound.quest_middle");
            } else if (questState.getQuestItemsCount(3171) == 0L) {
                questState.giveItems(3171, 1L);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 27077 && questState.getQuestItemsCount(3144) == 1L && questState.getQuestItemsCount(3026) == 1L && questState.getQuestItemsCount(3158) == 1L && questState.getQuestItemsCount(3159) == 0L) {
            if (questState.getPlayer().getActiveWeaponInstance() != null && questState.getPlayer().getActiveWeaponInstance().getItemId() == 3026) {
                questState.giveItems(3159, 1L);
                questState.playSound("ItemSound.quest_itemget");
                questState.takeItems(3158, questState.getQuestItemsCount(3158));
                questState.takeItems(3026, questState.getQuestItemsCount(3026));
                questState.setCond(19);
            }
        } else if (n == 20145 && questState.getQuestItemsCount(3144) == 1L && questState.getQuestItemsCount(3153) == 1L && questState.getQuestItemsCount(3165) < 20L) {
            if (Rnd.get((int)100) < 50) {
                questState.rollAndGive(3165, 1, 100.0);
                if (questState.getQuestItemsCount(3165) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                } else if (questState.getQuestItemsCount(3164) >= 20L && questState.getQuestItemsCount(3165) >= 20L) {
                    questState.setCond(10);
                }
            }
        } else if (n == 20233 && questState.getQuestItemsCount(3144) == 1L && questState.getQuestItemsCount(3153) == 1L && questState.getQuestItemsCount(3164) < 20L) {
            if (Rnd.get((int)100) < 50) {
                questState.rollAndGive(3164, 1, 100.0);
                if (questState.getQuestItemsCount(3164) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                } else if (questState.getQuestItemsCount(3165) >= 20L && questState.getQuestItemsCount(3164) >= 20L) {
                    questState.setCond(10);
                }
            }
        } else if (n == 20176 && questState.getQuestItemsCount(3144) == 1L && questState.getQuestItemsCount(3149) == 1L && questState.getQuestItemsCount(3163) < 20L) {
            if (Rnd.get((int)100) < 50) {
                questState.rollAndGive(3163, 1, 100.0);
                if (questState.getQuestItemsCount(3163) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                } else if (questState.getQuestItemsCount(3161) >= 10L && questState.getQuestItemsCount(3162) >= 20L && questState.getQuestItemsCount(3163) >= 20L) {
                    questState.setCond(5);
                }
            }
        } else if (n == 20550 && questState.getQuestItemsCount(3144) == 1L && questState.getQuestItemsCount(3149) == 1L && questState.getQuestItemsCount(3161) < 10L) {
            if (Rnd.get((int)100) < 50) {
                questState.rollAndGive(3161, 1, 100.0);
                if (questState.getQuestItemsCount(3161) >= 10L) {
                    questState.playSound("ItemSound.quest_middle");
                } else if (questState.getQuestItemsCount(3163) >= 20L && questState.getQuestItemsCount(3162) >= 20L && questState.getQuestItemsCount(3161) >= 10L) {
                    questState.setCond(5);
                }
            }
        } else if (n == 20082 && questState.getQuestItemsCount(3144) == 1L && questState.getQuestItemsCount(3149) == 1L && questState.getQuestItemsCount(3162) < 20L) {
            if (Rnd.get((int)100) < 80) {
                questState.rollAndGive(3162, 1, 100.0);
                if (questState.getQuestItemsCount(3162) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                } else if (questState.getQuestItemsCount(3161) >= 10L && questState.getQuestItemsCount(3163) >= 20L && questState.getQuestItemsCount(3162) >= 20L) {
                    questState.setCond(5);
                }
            }
        } else if (n == 20084 && questState.getQuestItemsCount(3144) == 1L && questState.getQuestItemsCount(3149) == 1L && questState.getQuestItemsCount(3162) < 20L) {
            if (Rnd.get((int)100) < 80) {
                questState.rollAndGive(3162, 1, 100.0);
                if (questState.getQuestItemsCount(3162) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                } else if (questState.getQuestItemsCount(3161) >= 10L && questState.getQuestItemsCount(3163) >= 20L && questState.getQuestItemsCount(3162) >= 20L) {
                    questState.setCond(5);
                }
            }
        } else if (n == 20086 && questState.getQuestItemsCount(3144) == 1L && questState.getQuestItemsCount(3149) == 1L && questState.getQuestItemsCount(3162) < 20L) {
            if (Rnd.get((int)100) < 80) {
                questState.rollAndGive(3162, 1, 100.0);
                if (questState.getQuestItemsCount(3162) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                } else if (questState.getQuestItemsCount(3161) >= 10L && questState.getQuestItemsCount(3163) >= 20L && questState.getQuestItemsCount(3162) >= 20L) {
                    questState.setCond(5);
                }
            }
        } else if (n == 20087 && questState.getQuestItemsCount(3144) == 1L && questState.getQuestItemsCount(3149) == 1L && questState.getQuestItemsCount(3162) < 20L) {
            if (Rnd.get((int)100) < 80) {
                questState.rollAndGive(3162, 1, 100.0);
                if (questState.getQuestItemsCount(3162) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                } else if (questState.getQuestItemsCount(3161) >= 10L && questState.getQuestItemsCount(3163) >= 20L && questState.getQuestItemsCount(3162) >= 20L) {
                    questState.setCond(5);
                }
            }
        } else if (n == 20088 && questState.getQuestItemsCount(3144) == 1L && questState.getQuestItemsCount(3149) == 1L && questState.getQuestItemsCount(3162) < 20L && Rnd.get((int)100) < 80) {
            questState.rollAndGive(3162, 1, 100.0);
            if (questState.getQuestItemsCount(3162) >= 20L) {
                questState.playSound("ItemSound.quest_middle");
            } else if (questState.getQuestItemsCount(3161) >= 10L && questState.getQuestItemsCount(3163) >= 20L && questState.getQuestItemsCount(3162) >= 20L) {
                questState.setCond(5);
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
