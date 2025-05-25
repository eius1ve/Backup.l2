/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.ClassId
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.ScriptFile;

public class _226_TestOfHealer
extends Quest
implements ScriptFile {
    private static final int ajF = 30473;
    private static final int ajG = 30428;
    private static final int ajH = 30659;
    private static final int ajI = 30424;
    private static final int ajJ = 30658;
    private static final int ajK = 30660;
    private static final int ajL = 30327;
    private static final int ajM = 30674;
    private static final int ajN = 30662;
    private static final int ajO = 30663;
    private static final int ajP = 30664;
    private static final int ajQ = 30661;
    private static final int ajR = 30665;
    private static final int ajS = 27122;
    private static final int ajT = 27123;
    private static final int ajU = 27124;
    private static final int ajV = 27125;
    private static final int ajW = 27126;
    private static final int ajX = 27127;
    private static final int ajY = 27134;
    private static final int ajZ = 2810;
    private static final int aka = 2811;
    private static final int akb = 2812;
    private static final int akc = 2813;
    private static final int akd = 2814;
    private static final int ake = 2815;
    private static final int akf = 2816;
    private static final int akg = 2817;
    private static final int akh = 2818;
    private static final int aki = 2819;
    private static final int akj = 2820;
    private static final int akk = 7562;
    private static final String[] aV = new String[]{"orphan_girl_q0226_01.htm", "orphan_girl_q0226_02.htm", "orphan_girl_q0226_03.htm", "orphan_girl_q0226_04.htm", "orphan_girl_q0226_05.htm"};

    public _226_TestOfHealer() {
        super(0);
        this.addStartNpc(30473);
        this.addTalkId(new int[]{30428, 30659, 30424, 30658, 30660, 30327, 30674, 30662, 30663, 30664, 30661, 30665});
        this.addKillId(new int[]{27123, 27124, 27125, 27127, 27134});
        this.addQuestItem(new int[]{2810, 2811, 2812, 2813, 2814, 2815, 2816, 2817, 2818, 2819});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        Player player = questState.getPlayer();
        int n2 = questState.getInt("test_of_healer");
        if (n == 30473) {
            if (string.equalsIgnoreCase("quest_accept")) {
                if (_226_TestOfHealer.w(player) && player.getLevel() >= 39) {
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                    questState.setCond(1);
                    questState.playSound("ItemSound.quest_middle");
                    questState.set("test_of_healer", 1);
                    questState.giveItems(2810, 1L);
                    string2 = "priest_bandellos_q0226_04.htm";
                }
            } else if (string.equalsIgnoreCase("reply_1") && n2 == 10 && questState.getQuestItemsCount(2813) > 0L) {
                string2 = "priest_bandellos_q0226_08.htm";
            } else if (string.equalsIgnoreCase("reply_2") && questState.getQuestItemsCount(2813) > 0L && n2 == 10) {
                string2 = "priest_bandellos_q0226_09.htm";
                if (!questState.getPlayer().getVarB("prof2.3")) {
                    questState.giveItems(7562, 8L);
                    questState.addExpAndSp(134839L, 50000L);
                    questState.getPlayer().setVar("prof2.3", "1", -1L);
                    this.giveExtraReward(questState.getPlayer());
                }
                questState.giveItems(2820, 1L);
                questState.takeItems(2813, 1L);
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(false);
                questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
            }
        } else if (n == 30428) {
            if (string.equalsIgnoreCase("reply_1") && n2 == 1 && questState.getQuestItemsCount(2810) > 0L) {
                NpcInstance npcInstance2;
                string2 = "perrin_q0226_02.htm";
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
                NpcInstance npcInstance3 = GameObjectsStorage.getByNpcId((int)27134);
                if (npcInstance3 == null && (npcInstance2 = questState.addSpawn(27134, 300000)) != null) {
                    npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                }
            }
        } else if (n == 30658) {
            if (string.equalsIgnoreCase("reply_1") && n2 == 4 && questState.getQuestItemsCount(2812) == 0L && questState.getQuestItemsCount(2814) == 0L && questState.getQuestItemsCount(2813) == 0L) {
                if (questState.getQuestItemsCount(57) >= 100000L) {
                    string2 = "father_gupu_q0226_02.htm";
                    questState.takeItems(57, 100000L);
                    questState.giveItems(2812, 1L);
                    questState.setCond(7);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    string2 = "father_gupu_q0226_05.htm";
                }
            } else if (string.equalsIgnoreCase("reply_2") && n2 == 4 && questState.getQuestItemsCount(2812) == 0L && questState.getQuestItemsCount(2814) == 0L && questState.getQuestItemsCount(2813) == 0L) {
                string2 = "father_gupu_q0226_03.htm";
                questState.set("test_of_healer", 5);
            }
        } else if (n == 30660) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(2812) > 0L) {
                string2 = "windy_shaoring_q0226_02.htm";
            } else if (string.equalsIgnoreCase("reply_2") && questState.getQuestItemsCount(2812) > 0L) {
                string2 = "windy_shaoring_q0226_03.htm";
                questState.takeItems(2812, 1L);
                questState.giveItems(2814, 1L);
                questState.setCond(8);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 30674) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(2815) > 0L && n2 == 6) {
                string2 = "daurin_hammercrush_q0226_02.htm";
                questState.takeItems(2815, 1L);
                NpcInstance npcInstance4 = GameObjectsStorage.getByNpcId((int)27122);
                NpcInstance npcInstance5 = GameObjectsStorage.getByNpcId((int)27123);
                if (npcInstance4 == null && npcInstance5 == null) {
                    NpcInstance npcInstance6;
                    NpcInstance npcInstance7;
                    NpcInstance npcInstance8 = questState.addSpawn(27122, 300000);
                    if (npcInstance8 != null) {
                        npcInstance8.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                    }
                    if ((npcInstance7 = questState.addSpawn(27122, 300000)) != null) {
                        npcInstance7.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                    }
                    if ((npcInstance6 = questState.addSpawn(27123, 300000)) != null) {
                        npcInstance6.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                    }
                }
                questState.playSound("Itemsound.quest_before_battle");
                questState.setCond(11);
            }
        } else if (n == 30665 && string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(2817) + questState.getQuestItemsCount(2818) + questState.getQuestItemsCount(2819) + questState.getQuestItemsCount(2816) == 4L) {
            string2 = "saint_kristina_q0226_02.htm";
            questState.takeItems(2816, questState.getQuestItemsCount(2816));
            questState.takeItems(2817, questState.getQuestItemsCount(2817));
            questState.takeItems(2818, questState.getQuestItemsCount(2818));
            questState.takeItems(2819, questState.getQuestItemsCount(2819));
            questState.giveItems(2811, 1L);
            questState.set("test_of_healer", 9);
            questState.setCond(22);
            questState.playSound("ItemSound.quest_middle");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        int n3 = questState.getInt("test_of_healer");
        Player player = questState.getPlayer();
        switch (n2) {
            case 1: {
                if (n != 30473) break;
                if (_226_TestOfHealer.w(player) && player.getLevel() >= 39) {
                    string = "priest_bandellos_q0226_03.htm";
                    break;
                }
                if (_226_TestOfHealer.w(player)) {
                    string = "priest_bandellos_q0226_01.htm";
                    break;
                }
                string = "priest_bandellos_q0226_02.htm";
                break;
            }
            case 2: {
                if (n == 30473) {
                    if (n3 < 10 && n3 >= 1) {
                        string = "priest_bandellos_q0226_05.htm";
                        break;
                    }
                    if (n3 == 10 && questState.getQuestItemsCount(2813) == 0L) {
                        string = "priest_bandellos_q0226_06.htm";
                        if (!questState.getPlayer().getVarB("prof2.3")) {
                            questState.giveItems(7562, 8L);
                            questState.addExpAndSp(118304L, 26250L);
                            questState.getPlayer().setVar("prof2.3", "1", -1L);
                            this.giveExtraReward(questState.getPlayer());
                        }
                        questState.giveItems(2820, 1L);
                        questState.takeItems(2813, 1L);
                        questState.playSound("ItemSound.quest_finish");
                        questState.exitCurrentQuest(false);
                        questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                        break;
                    }
                    if (n3 != 10 || questState.getQuestItemsCount(2813) <= 0L) break;
                    string = "priest_bandellos_q0226_07.htm";
                    break;
                }
                if (n == 30428) {
                    if (n3 == 1 && questState.getQuestItemsCount(2810) > 0L) {
                        string = "perrin_q0226_01.htm";
                        break;
                    }
                    if (n3 == 2) {
                        string = "perrin_q0226_03.htm";
                        questState.set("test_of_healer", 3);
                        questState.takeItems(2810, -1L);
                        questState.setCond(4);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (n3 != 3) break;
                    string = "perrin_q0226_04.htm";
                    break;
                }
                if (n == 30659) {
                    string = aV[Rnd.get((int)aV.length)];
                    break;
                }
                if (n == 30424) {
                    if (n3 == 3) {
                        string = "allana_q0226_01.htm";
                        questState.set("test_of_healer", 4);
                        questState.setCond(5);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (n3 != 4) break;
                    string = "allana_q0226_02.htm";
                    questState.set("test_of_healer", 4);
                    break;
                }
                if (n == 30658) {
                    if (n3 == 4 && questState.getQuestItemsCount(2812) == 0L && questState.getQuestItemsCount(2814) == 0L && questState.getQuestItemsCount(2813) == 0L) {
                        string = "father_gupu_q0226_01.htm";
                        questState.setCond(6);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(2812) > 0L) {
                        string = "father_gupu_q0226_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2814) > 0L) {
                        string = "father_gupu_q0226_06.htm";
                        questState.giveItems(2813, 1L);
                        questState.takeItems(2814, 1L);
                        questState.set("test_of_healer", 5);
                        break;
                    }
                    if (n3 != 5) break;
                    string = "father_gupu_q0226_07.htm";
                    questState.setCond(9);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (n == 30660) {
                    if (questState.getQuestItemsCount(2812) > 0L) {
                        string = "windy_shaoring_q0226_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2814) <= 0L) break;
                    string = "windy_shaoring_q0226_04.htm";
                    break;
                }
                if (n == 30327) {
                    if (n3 == 5) {
                        string = "master_sorius_q0226_01.htm";
                        questState.giveItems(2815, 1L);
                        questState.set("test_of_healer", 6);
                        questState.setCond(10);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (n3 >= 6 && n3 < 9) {
                        string = "master_sorius_q0226_02.htm";
                        break;
                    }
                    if (n3 == 9 && questState.getQuestItemsCount(2811) > 0L) {
                        string = "master_sorius_q0226_03.htm";
                        questState.takeItems(2811, 1L);
                        questState.set("test_of_healer", 10);
                        questState.setCond(23);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (n3 < 10) break;
                    string = "master_sorius_q0226_04.htm";
                    break;
                }
                if (n == 30674) {
                    if (n3 == 6 && questState.getQuestItemsCount(2815) > 0L) {
                        string = "daurin_hammercrush_q0226_01.htm";
                        break;
                    }
                    if (n3 == 6 && questState.getQuestItemsCount(2816) == 0L && questState.getQuestItemsCount(2815) == 0L) {
                        NpcInstance npcInstance2;
                        NpcInstance npcInstance3;
                        string = "daurin_hammercrush_q0226_02a.htm";
                        NpcInstance npcInstance4 = GameObjectsStorage.getByNpcId((int)27122);
                        NpcInstance npcInstance5 = GameObjectsStorage.getByNpcId((int)27123);
                        if (npcInstance4 != null || npcInstance5 != null) break;
                        NpcInstance npcInstance6 = questState.addSpawn(27122, 300000);
                        if (npcInstance6 != null) {
                            npcInstance6.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                        }
                        if ((npcInstance3 = questState.addSpawn(27122, 300000)) != null) {
                            npcInstance3.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                        }
                        if ((npcInstance2 = questState.addSpawn(27123, 300000)) == null) break;
                        npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                        break;
                    }
                    if (n3 == 6 && questState.getQuestItemsCount(2816) > 0L) {
                        string = "daurin_hammercrush_q0226_03.htm";
                        questState.set("test_of_healer", 8);
                        questState.setCond(13);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (n3 < 8) break;
                    string = "daurin_hammercrush_q0226_04.htm";
                    break;
                }
                if (n == 30662) {
                    if (n3 == 8 && questState.getQuestItemsCount(2816) > 0L && questState.getQuestItemsCount(2817) == 0L) {
                        string = "piper_longbow_q0226_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2817) > 0L && (questState.getQuestItemsCount(2818) == 0L || questState.getQuestItemsCount(2819) == 0L)) {
                        string = "piper_longbow_q0226_02.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2817) <= 0L || questState.getQuestItemsCount(2818) <= 0L || questState.getQuestItemsCount(2819) <= 0L) break;
                    string = "piper_longbow_q0226_03.htm";
                    questState.setCond(21);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (n == 30663) {
                    if (n3 == 8 && questState.getQuestItemsCount(2816) > 0L && questState.getQuestItemsCount(2817) == 0L) {
                        string = "slein_shining_blade_q0226_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2817) > 0L && (questState.getQuestItemsCount(2818) == 0L || questState.getQuestItemsCount(2819) == 0L)) {
                        string = "slein_shining_blade_q0226_02.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2817) <= 0L || questState.getQuestItemsCount(2818) <= 0L || questState.getQuestItemsCount(2819) <= 0L) break;
                    string = "slein_shining_blade_q0226_03.htm";
                    questState.setCond(21);
                    break;
                }
                if (n == 30664) {
                    if (n3 == 8 && questState.getQuestItemsCount(2816) > 0L && questState.getQuestItemsCount(2819) == 0L) {
                        string = "kein_flying_knife_q0226_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2817) > 0L && (questState.getQuestItemsCount(2818) == 0L || questState.getQuestItemsCount(2819) == 0L)) {
                        string = "kein_flying_knife_q0226_02.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2817) <= 0L || questState.getQuestItemsCount(2818) <= 0L || questState.getQuestItemsCount(2819) <= 0L) break;
                    string = "kein_flying_knife_q0226_03.htm";
                    questState.setCond(21);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (n == 30661) {
                    NpcInstance npcInstance7 = GameObjectsStorage.getByNpcId((int)30661);
                    if (n3 == 8 && questState.getQuestItemsCount(2816) >= 1L && questState.getQuestItemsCount(2817) == 0L) {
                        NpcInstance npcInstance8;
                        string = "mystery_darkelf_q0226_01.htm";
                        questState.setCond(14);
                        if (npcInstance7 != null) {
                            npcInstance7.doDie((Creature)questState.getPlayer());
                        }
                        if ((npcInstance8 = GameObjectsStorage.getByNpcId((int)27124)) == null) {
                            for (int i = 1; i <= 3; ++i) {
                                NpcInstance npcInstance9 = questState.addSpawn(27124, 300000);
                                if (npcInstance9 == null) continue;
                                npcInstance9.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                            }
                        }
                        questState.playSound("Itemsound.quest_before_battle");
                        break;
                    }
                    if (n3 == 8 && questState.getQuestItemsCount(2816) >= 1L && questState.getQuestItemsCount(2817) >= 1L && questState.getQuestItemsCount(2818) == 0L) {
                        NpcInstance npcInstance10;
                        string = "mystery_darkelf_q0226_02.htm";
                        questState.setCond(16);
                        if (npcInstance7 != null) {
                            npcInstance7.doDie((Creature)questState.getPlayer());
                        }
                        if ((npcInstance10 = GameObjectsStorage.getByNpcId((int)27125)) == null) {
                            for (int i = 1; i <= 3; ++i) {
                                NpcInstance npcInstance11 = questState.addSpawn(27125, 300000);
                                if (npcInstance11 == null) continue;
                                npcInstance11.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                            }
                        }
                        questState.playSound("Itemsound.quest_before_battle");
                        break;
                    }
                    if (n3 == 8 && questState.getQuestItemsCount(2816) >= 1L && questState.getQuestItemsCount(2817) >= 1L && questState.getQuestItemsCount(2818) >= 1L && questState.getQuestItemsCount(2819) == 0L) {
                        string = "mystery_darkelf_q0226_03.htm";
                        questState.setCond(18);
                        if (npcInstance7 != null) {
                            npcInstance7.doDie((Creature)questState.getPlayer());
                        }
                        NpcInstance npcInstance12 = GameObjectsStorage.getByNpcId((int)27126);
                        NpcInstance npcInstance13 = GameObjectsStorage.getByNpcId((int)27127);
                        if (npcInstance12 == null && npcInstance13 == null) {
                            for (int i = 1; i <= 2; ++i) {
                                NpcInstance npcInstance14 = questState.addSpawn(27126, 300000);
                                if (npcInstance14 == null) continue;
                                npcInstance14.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                            }
                            NpcInstance npcInstance15 = questState.addSpawn(27127, 300000);
                            if (npcInstance15 != null) {
                                npcInstance15.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                            }
                        }
                        questState.playSound("Itemsound.quest_before_battle");
                        break;
                    }
                    if (n3 != 8 || questState.getQuestItemsCount(2817) + questState.getQuestItemsCount(2818) + questState.getQuestItemsCount(2819) + questState.getQuestItemsCount(2816) != 4L) break;
                    string = "mystery_darkelf_q0226_04.htm";
                    questState.setCond(20);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (n != 30665) break;
                if (questState.getQuestItemsCount(2817) + questState.getQuestItemsCount(2818) + questState.getQuestItemsCount(2819) + questState.getQuestItemsCount(2816) == 4L) {
                    string = "saint_kristina_q0226_01.htm";
                    break;
                }
                if (n3 < 9 && questState.getQuestItemsCount(2817) + questState.getQuestItemsCount(2818) + questState.getQuestItemsCount(2819) + questState.getQuestItemsCount(2816) < 4L) {
                    string = "saint_kristina_q0226_03.htm";
                    break;
                }
                if (n3 < 9) break;
                string = "saint_kristina_q0226_04.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("test_of_healer");
        if (n == 27134) {
            if (n2 == 1) {
                questState.set("test_of_healer", 2);
                questState.setCond(3);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 27123 && n2 == 6 && questState.getQuestItemsCount(2816) == 0L) {
            questState.giveItems(2816, 1L);
            questState.playSound("ItemSound.quest_middle");
            questState.setCond(12);
        } else if (n == 27124) {
            if (n2 == 8 && questState.getQuestItemsCount(2816) > 0L && questState.getQuestItemsCount(2817) == 0L) {
                questState.giveItems(2817, 1L);
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(15);
            }
        } else if (n == 27125) {
            if (n2 == 8 && questState.getQuestItemsCount(2816) > 0L && questState.getQuestItemsCount(2818) == 0L) {
                questState.giveItems(2818, 1L);
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(17);
            }
        } else if (n == 27127 && n2 == 8 && questState.getQuestItemsCount(2816) > 0L && questState.getQuestItemsCount(2819) == 0L) {
            questState.giveItems(2819, 1L);
            questState.playSound("ItemSound.quest_middle");
            questState.setCond(19);
        }
        return null;
    }

    private static boolean w(Player player) {
        return player.getClassId() == ClassId.cleric || player.getClassId() == ClassId.oracle || player.getClassId() == ClassId.paladin || player.getClassId() == ClassId.elven_knight || player.getClassId() == ClassId.knight;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
