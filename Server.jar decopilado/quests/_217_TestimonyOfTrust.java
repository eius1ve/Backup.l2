/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.ClassId
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _217_TestimonyOfTrust
extends Quest
implements ScriptFile {
    private static final int abb = 30191;
    private static final int abc = 30031;
    private static final int abd = 30154;
    private static final int abe = 30358;
    private static final int abf = 30464;
    private static final int abg = 30515;
    private static final int abh = 30531;
    private static final int abi = 30565;
    private static final int abj = 30621;
    private static final int abk = 30657;
    private static final int abl = 20084;
    private static final int abm = 20082;
    private static final int abn = 20087;
    private static final int abo = 20088;
    private static final int abp = 20013;
    private static final int abq = 20019;
    private static final int abr = 20550;
    private static final int abs = 20036;
    private static final int abt = 20044;
    private static final int abu = 20157;
    private static final int abv = 20234;
    private static final int abw = 20232;
    private static final int abx = 20230;
    private static final int aby = 20213;
    private static final int abz = 20553;
    private static final int abA = 27121;
    private static final int abB = 27120;
    private static final int abC = 2734;
    private static final int abD = 2735;
    private static final int abE = 2736;
    private static final int abF = 2737;
    private static final int abG = 2738;
    private static final int abH = 2739;
    private static final int abI = 2740;
    private static final int abJ = 2741;
    private static final int abK = 2742;
    private static final int abL = 2743;
    private static final int abM = 2744;
    private static final int abN = 2745;
    private static final int abO = 2746;
    private static final int abP = 2747;
    private static final int abQ = 2748;
    private static final int abR = 2749;
    private static final int abS = 2750;
    private static final int abT = 2751;
    private static final int abU = 2752;
    private static final int abV = 2753;
    private static final int abW = 2754;
    private static final int abX = 2755;
    private static final int abY = 2756;
    private static final int abZ = 2757;
    private static final int aca = 2758;
    private static final int acb = 2759;
    private static final int acc = 2760;
    private static final int acd = 2761;
    private static final int ace = 7562;

    public _217_TestimonyOfTrust() {
        super(0);
        this.addStartNpc(30191);
        this.addTalkId(new int[]{30031, 30154, 30358, 30464, 30515, 30531, 30565, 30621, 30657});
        this.addKillId(new int[]{20084, 20082, 20087, 20088, 20013, 20019, 20550, 20036, 20044, 27120, 20157, 20234, 20232, 20230, 20213, 20553, 27121});
        this.addQuestItem(new int[]{2740, 2741, 2742, 2743, 2746, 2747, 2745, 2735, 2755, 2752, 2754, 2753, 2736, 2748, 2739, 2738, 2758, 2757, 2756, 2737, 2759, 2761, 2760, 2744, 2749, 2751, 2750});
    }

    private static boolean t(Player player) {
        return player.getClassId() == ClassId.warrior || player.getClassId() == ClassId.knight || player.getClassId() == ClassId.rogue || player.getClassId() == ClassId.wizard || player.getClassId() == ClassId.cleric;
    }

    private static boolean u(Player player) {
        return player.getClassId() == ClassId.fighter || player.getClassId() == ClassId.mage || player.getClassId() == ClassId.elven_fighter || player.getClassId() == ClassId.elven_mage || player.getClassId() == ClassId.dark_fighter || player.getClassId() == ClassId.dark_mage || player.getClassId() == ClassId.orc_fighter || player.getClassId() == ClassId.orc_mage || player.getClassId() == ClassId.dwarven_fighter;
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30191 && string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "hollin_q0217_04.htm";
            questState.set("testimony_of_trust", 1);
            questState.giveItems(2735, 1L);
            questState.giveItems(2736, 1L);
        } else if (n == 30154) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "ozzy_q0217_02.htm";
            } else if (string.equalsIgnoreCase("reply_2") && questState.getQuestItemsCount(2735) > 0L) {
                string2 = "ozzy_q0217_03.htm";
                questState.giveItems(2745, 1L);
                questState.takeItems(2735, 1L);
                questState.setCond(2);
                questState.set("testimony_of_trust", 2);
            }
        } else if (n == 30358) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(2736) > 0L) {
                string2 = "tetrarch_thifiell_q0217_02.htm";
                questState.giveItems(2748, 1L);
                questState.takeItems(2736, 1L);
                questState.set("testimony_of_trust", 5);
                questState.setCond(5);
            }
        } else if (n == 30515 && string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(2757) > 0L) {
            string2 = "seer_manakia_q0217_02.htm";
            questState.takeItems(2757, 1L);
            questState.set("testimony_of_trust", 11);
            questState.setCond(14);
        } else if (n == 30531) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(2737) > 0L) {
                string2 = "first_elder_lockirin_q0217_02.htm";
                questState.giveItems(2759, 1L);
                questState.takeItems(2737, 1L);
                questState.set("testimony_of_trust", 15);
                questState.setCond(18);
            }
        } else if (n == 30565) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(2738) > 0L) {
                string2 = "kakai_the_lord_of_flame_q0217_02.htm";
                questState.giveItems(2757, 1L);
                questState.takeItems(2738, 1L);
                questState.set("testimony_of_trust", 10);
                questState.setCond(13);
            }
        } else if (n == 30621) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(2759) > 0L) {
                string2 = "maestro_nikola_q0217_02.htm";
                questState.giveItems(2760, 1L);
                questState.takeItems(2759, 1L);
                questState.set("testimony_of_trust", 16);
                questState.setCond(19);
            }
        } else if (n == 30657 && string.equalsIgnoreCase("reply_1")) {
            if (questState.getPlayer().getLevel() >= 38 && questState.getQuestItemsCount(2739) > 0L) {
                string2 = "cardinal_seresin_q0217_03.htm";
                questState.giveItems(2738, 1L);
                questState.giveItems(2737, 1L);
                questState.takeItems(2739, 1L);
                questState.set("testimony_of_trust", 9);
                questState.setCond(12);
            } else {
                string2 = "cardinal_seresin_q0217_02.htm";
                questState.setCond(11);
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        Player player = questState.getPlayer();
        int n3 = questState.getInt("testimony_of_trust");
        switch (n2) {
            case 1: {
                if (n != 30191) break;
                if (player.getRace() == Race.human && player.getLevel() >= 37 && _217_TestimonyOfTrust.t(player)) {
                    string = "hollin_q0217_03.htm";
                    break;
                }
                if (player.getRace() == Race.human && player.getLevel() >= 37 && _217_TestimonyOfTrust.u(player)) {
                    string = "hollin_q0217_01a.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (player.getRace() == Race.human && player.getLevel() >= 37) {
                    string = "hollin_q0217_01b.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (player.getRace() == Race.human) {
                    string = "hollin_q0217_01.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "hollin_q0217_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n == 30191) {
                    if (n3 == 7 && questState.getQuestItemsCount(2741) > 0L && questState.getQuestItemsCount(2740) > 0L) {
                        string = "hollin_q0217_05.htm";
                        questState.giveItems(2739, 1L);
                        questState.takeItems(2740, 1L);
                        questState.takeItems(2741, 1L);
                        questState.set("testimony_of_trust", 8);
                        questState.setCond(10);
                        break;
                    }
                    if (n3 == 18 && questState.getQuestItemsCount(2742) > 0L && questState.getQuestItemsCount(2743) > 0L) {
                        string = "hollin_q0217_06.htm";
                        questState.giveItems(2744, 1L);
                        questState.takeItems(2742, 1L);
                        questState.takeItems(2743, 1L);
                        questState.set("testimony_of_trust", 19);
                        questState.setCond(23);
                        break;
                    }
                    if (n3 == 19) {
                        string = "hollin_q0217_07.htm";
                        break;
                    }
                    if (n3 == 1) {
                        string = "hollin_q0217_08.htm";
                        break;
                    }
                    if (n3 != 8) break;
                    string = "hollin_q0217_09.htm";
                    break;
                }
                if (n == 30031) {
                    if (n3 != 19 || questState.getQuestItemsCount(2744) <= 0L) break;
                    questState.addExpAndSp(39571L, 2500L);
                    questState.giveItems(7562, 16L);
                    string = "quilt_q0217_01.htm";
                    questState.takeItems(2744, 1L);
                    questState.giveItems(2734, 1L);
                    questState.playSound("ItemSound.quest_finish");
                    questState.exitCurrentQuest(false);
                    break;
                }
                if (n == 30154) {
                    if (n3 == 1 && questState.getQuestItemsCount(2735) > 0L) {
                        string = "ozzy_q0217_01.htm";
                        break;
                    }
                    if (n3 == 2 && questState.getQuestItemsCount(2745) > 0L) {
                        string = "ozzy_q0217_04.htm";
                        break;
                    }
                    if (n3 == 3 && questState.getQuestItemsCount(2746) > 0L && questState.getQuestItemsCount(2747) > 0L) {
                        string = "ozzy_q0217_05.htm";
                        questState.giveItems(2741, 1L);
                        questState.takeItems(2746, 1L);
                        questState.takeItems(2747, 1L);
                        questState.takeItems(2745, -1L);
                        questState.setCond(4);
                        questState.set("testimony_of_trust", 4);
                        break;
                    }
                    if (n3 != 4) break;
                    string = "ozzy_q0217_06.htm";
                    break;
                }
                if (n == 30358) {
                    if (n3 == 4 && questState.getQuestItemsCount(2736) > 0L) {
                        string = "tetrarch_thifiell_q0217_01.htm";
                        break;
                    }
                    if (n3 == 6 && questState.getQuestItemsCount(2755) > 0L && questState.getQuestItemsCount(2754) + questState.getQuestItemsCount(2753) + questState.getQuestItemsCount(2752) == 3L) {
                        string = "tetrarch_thifiell_q0217_03.htm";
                        questState.giveItems(2740, 1L);
                        questState.takeItems(2755, -1L);
                        questState.takeItems(2752, -1L);
                        questState.takeItems(2754, -1L);
                        questState.takeItems(2753, -1L);
                        questState.setCond(9);
                        questState.set("testimony_of_trust", 7);
                        break;
                    }
                    if (n3 == 7) {
                        string = "tetrarch_thifiell_q0217_04.htm";
                        break;
                    }
                    if (n3 != 5) break;
                    string = "tetrarch_thifiell_q0217_05.htm";
                    break;
                }
                if (n == 30464) {
                    if (n3 == 5 && questState.getQuestItemsCount(2748) > 0L) {
                        string = "magister_clayton_q0217_01.htm";
                        questState.giveItems(2755, 1L);
                        questState.takeItems(2748, 1L);
                        questState.setCond(6);
                        questState.set("testimony_of_trust", 6);
                        break;
                    }
                    if (n3 == 6 && questState.getQuestItemsCount(2755) > 0L && questState.getQuestItemsCount(2754) + questState.getQuestItemsCount(2753) + questState.getQuestItemsCount(2752) < 3L) {
                        string = "magister_clayton_q0217_02.htm";
                        break;
                    }
                    if (n3 != 6 || questState.getQuestItemsCount(2755) <= 0L || questState.getQuestItemsCount(2754) + questState.getQuestItemsCount(2753) + questState.getQuestItemsCount(2752) != 3L) break;
                    string = "magister_clayton_q0217_03.htm";
                    questState.setCond(8);
                    break;
                }
                if (n == 30515) {
                    if (questState.getQuestItemsCount(2757) > 0L) {
                        string = "seer_manakia_q0217_01.htm";
                        break;
                    }
                    if (n3 == 11) {
                        string = "seer_manakia_q0217_03.htm";
                        break;
                    }
                    if (n3 != 12 || questState.getQuestItemsCount(2756) != 10L) break;
                    string = "seer_manakia_q0217_04.htm";
                    questState.giveItems(2758, 1L);
                    questState.takeItems(2756, -1L);
                    questState.set("testimony_of_trust", 13);
                    questState.setCond(16);
                    break;
                }
                if (n == 30531) {
                    if (n3 == 14 && questState.getQuestItemsCount(2737) > 0L) {
                        string = "first_elder_lockirin_q0217_01.htm";
                        break;
                    }
                    if (n3 == 15) {
                        string = "first_elder_lockirin_q0217_03.htm";
                        break;
                    }
                    if (n3 == 17) {
                        string = "first_elder_lockirin_q0217_04.htm";
                        questState.giveItems(2742, 1L);
                        questState.set("testimony_of_trust", 18);
                        questState.setCond(22);
                        break;
                    }
                    if (n3 != 18) break;
                    string = "first_elder_lockirin_q0217_05.htm";
                    break;
                }
                if (n == 30565) {
                    if (n3 == 9 && questState.getQuestItemsCount(2738) > 0L) {
                        string = "kakai_the_lord_of_flame_q0217_01.htm";
                        break;
                    }
                    if (n3 == 10) {
                        string = "kakai_the_lord_of_flame_q0217_03.htm";
                        break;
                    }
                    if (n3 == 13) {
                        string = "kakai_the_lord_of_flame_q0217_04.htm";
                        questState.giveItems(2743, 1L);
                        questState.takeItems(2758, -1L);
                        questState.set("testimony_of_trust", 14);
                        questState.setCond(17);
                        break;
                    }
                    if (n3 != 14) break;
                    string = "kakai_the_lord_of_flame_q0217_05.htm";
                    break;
                }
                if (n == 30621) {
                    if (n3 == 15 && questState.getQuestItemsCount(2759) > 0L) {
                        string = "maestro_nikola_q0217_01.htm";
                        break;
                    }
                    if (n3 == 16 && questState.getQuestItemsCount(2761) < 10L) {
                        string = "maestro_nikola_q0217_03.htm";
                        break;
                    }
                    if (n3 == 16 && questState.getQuestItemsCount(2761) == 10L) {
                        string = "maestro_nikola_q0217_04.htm";
                        questState.takeItems(2761, questState.getQuestItemsCount(2761));
                        questState.takeItems(2760, questState.getQuestItemsCount(2760));
                        questState.set("testimony_of_trust", 17);
                        questState.setCond(21);
                        break;
                    }
                    if (n3 != 17) break;
                    string = "maestro_nikola_q0217_05.htm";
                    break;
                }
                if (n != 30657) break;
                if (n3 == 8 && questState.getQuestItemsCount(2739) > 0L) {
                    string = "cardinal_seresin_q0217_01.htm";
                    break;
                }
                if (n3 == 9) {
                    string = "cardinal_seresin_q0217_04.htm";
                    break;
                }
                if (n3 != 18) break;
                string = "cardinal_seresin_q0217_05.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("testimony_of_trust");
        if (n == 20084 || n == 20082 || n == 20087 || n == 20088) {
            if (n2 == 6 && questState.getQuestItemsCount(2750) < 10L && questState.getQuestItemsCount(2755) > 0L && questState.getQuestItemsCount(2753) == 0L) {
                if (questState.getQuestItemsCount(2750) == 9L) {
                    questState.giveItems(2753, 1L);
                    questState.takeItems(2750, questState.getQuestItemsCount(2750));
                    questState.playSound("ItemSound.quest_middle");
                    if (questState.getQuestItemsCount(2752) >= 1L && questState.getQuestItemsCount(2754) >= 1L) {
                        questState.setCond(7);
                    }
                } else {
                    questState.giveItems(2750, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 20013 || n == 20019) {
            if (n2 == 2 && Rnd.get((int)100) < 33) {
                NpcInstance npcInstance2 = questState.addSpawn(27121, questState.getPlayer().getX(), questState.getPlayer().getY(), questState.getPlayer().getZ());
                if (npcInstance2 != null) {
                    npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                }
                questState.playSound("Itemsound.quest_before_battle");
            }
        } else if (n == 20036 || n == 20044) {
            if (n2 == 2 && Rnd.get((int)100) < 33) {
                NpcInstance npcInstance3 = questState.addSpawn(27120, questState.getPlayer().getX(), questState.getPlayer().getY(), questState.getPlayer().getZ());
                if (npcInstance3 != null) {
                    npcInstance3.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                }
                questState.playSound("Itemsound.quest_before_battle");
            }
        } else if (n == 20550) {
            if (n2 == 6 && questState.getQuestItemsCount(2749) < 10L && questState.getQuestItemsCount(2755) > 0L && questState.getQuestItemsCount(2752) == 0L) {
                if (questState.getQuestItemsCount(2749) == 9L) {
                    questState.giveItems(2752, 1L);
                    questState.takeItems(2749, questState.getQuestItemsCount(2749));
                    questState.playSound("ItemSound.quest_middle");
                    if (questState.getQuestItemsCount(2754) >= 1L && questState.getQuestItemsCount(2753) >= 1L) {
                        questState.setCond(7);
                    }
                } else {
                    questState.giveItems(2749, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 27120) {
            if (n2 == 2 && questState.getQuestItemsCount(2746) == 0L) {
                if (questState.getQuestItemsCount(2747) > 0L) {
                    questState.giveItems(2746, 1L);
                    questState.set("testimony_of_trust", 3);
                    questState.playSound("ItemSound.quest_middle");
                    questState.setCond(3);
                } else {
                    questState.giveItems(2746, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 20157 || n == 20234 || n == 20232 || n == 20230) {
            if (n2 == 6 && questState.getQuestItemsCount(2751) < 10L && questState.getQuestItemsCount(2755) > 0L && questState.getQuestItemsCount(2754) == 0L) {
                if (questState.getQuestItemsCount(2751) == 9L) {
                    questState.giveItems(2754, 1L);
                    questState.takeItems(2751, questState.getQuestItemsCount(2751));
                    questState.playSound("ItemSound.quest_middle");
                    if (questState.getQuestItemsCount(2752) >= 1L && questState.getQuestItemsCount(2753) >= 1L) {
                        questState.setCond(7);
                    }
                } else {
                    questState.giveItems(2751, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 20213) {
            if (n2 == 16 && questState.getQuestItemsCount(2761) < 10L) {
                if (questState.getQuestItemsCount(2761) == 9L) {
                    questState.giveItems(2761, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    questState.setCond(20);
                } else {
                    questState.giveItems(2761, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 20553) {
            if (n2 == 11 && questState.getQuestItemsCount(2756) < 10L && Rnd.get((int)2) == 1) {
                if (questState.getQuestItemsCount(2756) == 9L) {
                    questState.giveItems(2756, 1L);
                    questState.set("testimony_of_trust", 12);
                    questState.playSound("ItemSound.quest_middle");
                    questState.setCond(15);
                } else {
                    questState.giveItems(2756, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 27121 && n2 == 2 && questState.getQuestItemsCount(2747) == 0L) {
            if (questState.getQuestItemsCount(2746) > 0L) {
                questState.giveItems(2747, 1L);
                questState.setCond(3);
                questState.set("testimony_of_trust", 3);
                questState.playSound("ItemSound.quest_middle");
            } else {
                questState.giveItems(2747, 1L);
                questState.playSound("ItemSound.quest_itemget");
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
