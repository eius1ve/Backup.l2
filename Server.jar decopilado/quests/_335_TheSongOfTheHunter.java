/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;

public class _335_TheSongOfTheHunter
extends Quest
implements ScriptFile {
    private static final int aBA = 30744;
    private static final int aBB = 30745;
    private static final int aBC = 30746;
    private static final int aBD = 20660;
    private static final int aBE = 20269;
    private static final int aBF = 20271;
    private static final int aBG = 27140;
    private static final int aBH = 27141;
    private static final int aBI = 27142;
    private static final int aBJ = 20552;
    private static final int aBK = 20664;
    private static final int aBL = 20557;
    private static final int aBM = 20567;
    private static final int aBN = 20565;
    private static final int aBO = 20667;
    private static final int aBP = 20589;
    private static final int aBQ = 20594;
    private static final int aBR = 20555;
    private static final int aBS = 20556;
    private static final int aBT = 20554;
    private static final int aBU = 27160;
    private static final int aBV = 20659;
    private static final int aBW = 27149;
    private static final int aBX = 20550;
    private static final int aBY = 20642;
    private static final int aBZ = 20641;
    private static final int aCa = 20643;
    private static final int aCb = 27162;
    private static final int aCc = 20662;
    private static final int aCd = 20661;
    private static final int aCe = 20676;
    private static final int aCf = 20600;
    private static final int aCg = 27164;
    private static final int aCh = 20603;
    private static final int aCi = 27157;
    private static final int aCj = 20578;
    private static final int aCk = 20582;
    private static final int aCl = 20581;
    private static final int aCm = 20579;
    private static final int aCn = 27156;
    private static final int aCo = 20590;
    private static final int aCp = 20563;
    private static final int aCq = 20639;
    private static final int aCr = 20592;
    private static final int aCs = 20598;
    private static final int aCt = 20558;
    private static final int aCu = 20562;
    private static final int aCv = 20631;
    private static final int aCw = 20634;
    private static final int aCx = 20633;
    private static final int aCy = 20632;
    private static final int aCz = 27161;
    private static final int aCA = 20675;
    private static final int aCB = 27144;
    private static final int aCC = 27148;
    private static final int aCD = 27145;
    private static final int aCE = 27147;
    private static final int aCF = 27146;
    private static final int aCG = 20601;
    private static final int aCH = 20602;
    private static final int aCI = 20571;
    private static final int aCJ = 20588;
    private static final int aCK = 20586;
    private static final int aCL = 27159;
    private static final int aCM = 27158;
    private static final int aCN = 20591;
    private static final int aCO = 20597;
    private static final int aCP = 20560;
    private static final int aCQ = 20561;
    private static final int aCR = 20593;
    private static final int aCS = 20599;
    private static final int aCT = 27163;
    private static final int aCU = 20686;
    private static final int aCV = 20682;
    private static final int aCW = 20683;
    private static final int aCX = 20684;
    private static final int aCY = 20553;
    private static final int aCZ = 27143;
    private static final int aDa = 3692;
    private static final int aDb = 3693;
    private static final int aDc = 3694;
    private static final int aDd = 3695;
    private static final int aDe = 3696;
    private static final int aDf = 3697;
    private static final int aDg = 3698;
    private static final int aDh = 3699;
    private static final int aDi = 3700;
    private static final int aDj = 3701;
    private static final int aDk = 3702;
    private static final int aDl = 3703;
    private static final int aDm = 3704;
    private static final int aDn = 3705;
    private static final int aDo = 3706;
    private static final int aDp = 3707;
    private static final int aDq = 3708;
    private static final int aDr = 3471;
    private static final int aDs = 3709;
    private static final int aDt = 3710;
    private static final int aDu = 3711;
    private static final int aDv = 3712;
    private static final int aDw = 3713;
    private static final int aDx = 3714;
    private static final int aDy = 3715;
    private static final int aDz = 3716;
    private static final int aDA = 3717;
    private static final int aDB = 3718;
    private static final int aDC = 3719;
    private static final int aDD = 3720;
    private static final int aDE = 3721;
    private static final int aDF = 3722;
    private static final int aDG = 3723;
    private static final int aDH = 3724;
    private static final int aDI = 3725;
    private static final int aDJ = 3726;
    private static final int aDK = 3727;
    private static final int aDL = 3728;
    private static final int aDM = 3729;
    private static final int aDN = 3730;
    private static final int aDO = 3731;
    private static final int aDP = 3732;
    private static final int aDQ = 3733;
    private static final int aDR = 3734;
    private static final int aDS = 3735;
    private static final int aDT = 3736;
    private static final int aDU = 3737;
    private static final int aDV = 3738;
    private static final int aDW = 3739;
    private static final int aDX = 3740;
    private static final int aDY = 3741;
    private static final int aDZ = 3742;
    private static final int aEa = 3743;
    private static final int aEb = 3744;
    private static final int aEc = 3745;
    private static final int aEd = 3746;
    private static final int aEe = 3747;
    private static final int aEf = 3748;
    private static final int aEg = 3749;
    private static final int aEh = 3750;
    private static final int aEi = 3751;
    private static final int aEj = 3752;
    private static final int aEk = 3753;
    private static final int aEl = 3754;
    private static final int aEm = 3755;
    private static final int aEn = 3756;
    private static final int aEo = 3757;
    private static final int aEp = 3758;
    private static final int aEq = 3759;
    private static final int aEr = 3760;
    private static final int aEs = 3761;
    private static final int aEt = 3762;
    private static final int aEu = 3763;
    private static final int aEv = 3764;
    private static final int aEw = 3765;
    private static final int aEx = 3766;
    private static final int aEy = 3767;
    private static final int aEz = 3768;
    private static final int aEA = 3769;
    private static final int aEB = 3770;
    private static final int aEC = 3771;
    private static final int aED = 3772;
    private static final int aEE = 3773;
    private static final int aEF = 3774;
    private static final int aEG = 3775;
    private static final int aEH = 3776;
    private static final int aEI = 3777;
    private static final int aEJ = 3778;
    private static final int aEK = 3779;
    private static final int aEL = 3780;
    private static final int aEM = 3781;
    private static final int aEN = 3782;
    private static final int aEO = 3783;
    private static final int aEP = 3784;
    private static final int aEQ = 3785;
    private static final int aER = 3786;
    private static final int aES = 3787;
    private static final int aET = 3788;
    private static final int aEU = 3789;
    private static final int aEV = 3790;
    private static final int aEW = 3791;
    private static final int aEX = 3792;
    private static final int aEY = 3793;
    private static final int aEZ = 3794;
    private static final int aFa = 3795;
    private static final int aFb = 3796;
    private static final int aFc = 3797;
    private static final int aFd = 3798;
    private static final int aFe = 3799;
    private static final int aFf = 3800;
    private static final int aFg = 3801;
    private static final int aFh = 3802;
    private static final int aFi = 3803;
    private static final int aFj = 3804;
    private static final int aFk = 3805;
    private static final int aFl = 3806;
    private static final int aFm = 3807;
    private static final int aFn = 3808;
    private static final int aFo = 3809;
    private static final int aFp = 3810;

    public _335_TheSongOfTheHunter() {
        super(0);
        this.addStartNpc(30744);
        this.addTalkId(new int[]{30746, 30745});
        this.addKillId(new int[]{20660, 20269, 20271, 27140, 27141, 27142, 20552, 20664, 20557, 20567, 20565, 20667, 20589, 20594, 20555, 20556, 20554, 27160, 20659, 27149, 20550, 20642, 20641, 20643, 27162, 20662, 20661, 20676, 20600, 27164, 20603, 27157, 20578, 20582, 20581, 20579, 27156, 20590, 20563, 20639, 20592, 20598, 20558, 20562, 20631, 20634, 20633, 20632, 27161, 20675, 27144, 27148, 27145, 27147, 27146, 20601, 20602, 20571, 20588, 20586, 27159, 27158, 20591, 20597, 20560, 20561, 20593, 20599, 27163, 20686, 20682, 20683, 20684, 20553, 27143});
        this.addQuestItem(new int[]{3692, 3693, 3694, 3695, 3696, 3697, 3698, 3699, 3700, 3701, 3702, 3703, 3704, 3705, 3706, 3707, 3708, 3471, 3709, 3710, 3711, 3712, 3713, 3714, 3715, 3716, 3717, 3718, 3719, 3720, 3721, 3722, 3723, 3724, 3725, 3726, 3727, 3728, 3729, 3730, 3731, 3732, 3733, 3734, 3735, 3736, 3737, 3738, 3739, 3740, 3741, 3742, 3743, 3744, 3745, 3746, 3747, 3748, 3749, 3750, 3751, 3752, 3753, 3754, 3755, 3756, 3757, 3758, 3759, 3760, 3761, 3762, 3763, 3764, 3765, 3766, 3767, 3768, 3769, 3770, 3771, 3772, 3773, 3774, 3775, 3776, 3777, 3778, 3779, 3780, 3781, 3782, 3783, 3784, 3785, 3786, 3787, 3788, 3789, 3790, 3791, 3792, 3793, 3794, 3795, 3796, 3797, 3798, 3799, 3800, 3801, 3802, 3803, 3804, 3805, 3806, 3807, 3808, 3809, 3810});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("the_song_of_the_hunter");
        if (n == 30744) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                if (questState.getQuestItemsCount(3695) == 0L) {
                    questState.giveItems(3695, 1L);
                }
                questState.set("the_song_of_the_hunter", 0);
                string2 = "grey_q0335_03.htm";
            }
            if (string.equalsIgnoreCase("reply_1")) {
                if (questState.getQuestItemsCount(3727) + questState.getQuestItemsCount(3728) + questState.getQuestItemsCount(3729) + questState.getQuestItemsCount(3730) + questState.getQuestItemsCount(3731) + questState.getQuestItemsCount(3732) + questState.getQuestItemsCount(3733) + questState.getQuestItemsCount(3734) + questState.getQuestItemsCount(3735) + questState.getQuestItemsCount(3736) + questState.getQuestItemsCount(3737) + questState.getQuestItemsCount(3738) + questState.getQuestItemsCount(3739) + questState.getQuestItemsCount(3740) + questState.getQuestItemsCount(3741) + questState.getQuestItemsCount(3742) + questState.getQuestItemsCount(3743) + questState.getQuestItemsCount(3744) + questState.getQuestItemsCount(3745) + questState.getQuestItemsCount(3746) + questState.getQuestItemsCount(3747) == 0L) {
                    string2 = "grey_q0335_09.htm";
                    questState.giveItems(3696, 1L);
                } else {
                    string2 = "grey_q0335_09a.htm";
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "grey_q0335_15.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                if (questState.getQuestItemsCount(3694) < 20L) {
                    string2 = "grey_q0335_16.htm";
                } else {
                    string2 = "grey_q0335_17.htm";
                    questState.giveItems(57, 20000L);
                }
                questState.playSound("ItemSound.quest_finish");
                questState.takeAllItems(new int[]{3692, 3693, 3694, 3695, 3696, 3697, 3698, 3699, 3700, 3701, 3702, 3703, 3704, 3705, 3706, 3707, 3708, 3471, 3709, 3710, 3711, 3712, 3713, 3714, 3715, 3716, 3717, 3718, 3719, 3720, 3721, 3722, 3723, 3724, 3725, 3726, 3727, 3728, 3729, 3730, 3731, 3732, 3733, 3734, 3735, 3736, 3737, 3738, 3739, 3740, 3741, 3742, 3743, 3744, 3745, 3746, 3747, 3748, 3749, 3750, 3751, 3752, 3753, 3754, 3755, 3756, 3757, 3758, 3759, 3760, 3761, 3762, 3763, 3764, 3765, 3766, 3767, 3768, 3769, 3770, 3771, 3772, 3773, 3774, 3775, 3776, 3777, 3778, 3779, 3780, 3781, 3782, 3783, 3784, 3785, 3786, 3787, 3788, 3789, 3790, 3791, 3792, 3793, 3794, 3795, 3796, 3797, 3798, 3799, 3800, 3801, 3802, 3803, 3804, 3805, 3806, 3807, 3808, 3809, 3810});
                questState.exitCurrentQuest(true);
            } else if (string.equalsIgnoreCase("reply_4")) {
                string2 = "grey_q0335_18.htm";
            }
        } else if (n == 30745) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = questState.getQuestItemsCount(3696) == 0L ? "tor_q0335_02.htm" : "tor_q0335_03.htm";
            } else {
                if (string.equalsIgnoreCase("reply_2")) {
                    if (n2 == 0) {
                        int n3;
                        int n4;
                        int n5;
                        int n6;
                        int n7;
                        questState.set("hunt_request_0", 0);
                        questState.set("hunt_request_1", 0);
                        questState.set("hunt_request_2", 0);
                        questState.set("hunt_request_3", 0);
                        questState.set("hunt_request_4", 0);
                        do {
                            if (questState.getQuestItemsCount(3694) == 0L) {
                                n7 = Rnd.get((int)12);
                                n6 = Rnd.get((int)12);
                                n5 = Rnd.get((int)12);
                                n4 = Rnd.get((int)12);
                                n3 = Rnd.get((int)12);
                                questState.set("the_song_of_the_hunter", 1);
                                continue;
                            }
                            if (questState.getQuestItemsCount(3694) < 4L) {
                                if (Rnd.get((int)100) < 20) {
                                    n7 = Rnd.get((int)6) + 12;
                                    n6 = Rnd.get((int)12);
                                    n5 = Rnd.get((int)6);
                                    n4 = Rnd.get((int)6) + 6;
                                    n3 = Rnd.get((int)12);
                                    questState.set("the_song_of_the_hunter", 1);
                                    continue;
                                }
                                n7 = Rnd.get((int)12);
                                n6 = Rnd.get((int)12);
                                n5 = Rnd.get((int)12);
                                n4 = Rnd.get((int)12);
                                n3 = Rnd.get((int)12);
                                questState.set("the_song_of_the_hunter", 1);
                                continue;
                            }
                            if (Rnd.get((int)100) < 20) {
                                n7 = Rnd.get((int)6) + 12;
                                n6 = Rnd.get((int)20) == 0 ? Rnd.get((int)2) + 18 : Rnd.get((int)12);
                                n5 = Rnd.get((int)6);
                                n4 = Rnd.get((int)6) + 6;
                                n3 = Rnd.get((int)12);
                                questState.set("the_song_of_the_hunter", 1);
                                continue;
                            }
                            n7 = Rnd.get((int)12);
                            n6 = Rnd.get((int)20) == 0 ? Rnd.get((int)2) + 18 : Rnd.get((int)12);
                            n5 = Rnd.get((int)6);
                            n4 = Rnd.get((int)6) + 6;
                            n3 = Rnd.get((int)12);
                            questState.set("the_song_of_the_hunter", 1);
                        } while (n7 == n6 || n6 == n5 || n5 == n4 || n4 == n3 || n7 == n3 || n7 == n5 || n7 == n4 || n6 == n4 || n6 == n3 || n5 == n3);
                        questState.set("hunt_request_0", 33520 + n7);
                        questState.set("hunt_request_1", 33520 + n6);
                        questState.set("hunt_request_2", 33520 + n5);
                        questState.set("hunt_request_3", 33520 + n4);
                        questState.set("hunt_request_4", 33520 + n3);
                        string2 = HtmCache.getInstance().getNotNull("quests/_335_TheSongOfTheHunter/tor_q0335_16.htm", questState.getPlayer());
                        string2 = string2.replace("<?reply1?>", new CustomMessage(questState.get("hunt_request_0"), questState.getPlayer(), new Object[0]).toString());
                        string2 = string2.replace("<?reply2?>", new CustomMessage(questState.get("hunt_request_1"), questState.getPlayer(), new Object[0]).toString());
                        string2 = string2.replace("<?reply3?>", new CustomMessage(questState.get("hunt_request_2"), questState.getPlayer(), new Object[0]).toString());
                        string2 = string2.replace("<?reply4?>", new CustomMessage(questState.get("hunt_request_3"), questState.getPlayer(), new Object[0]).toString());
                        string2 = string2.replace("<?reply5?>", new CustomMessage(questState.get("hunt_request_4"), questState.getPlayer(), new Object[0]).toString());
                        return string2;
                    }
                    string2 = HtmCache.getInstance().getNotNull("quests/_335_TheSongOfTheHunter/tor_q0335_16.htm", questState.getPlayer());
                    string2 = string2.replace("<?reply1?>", new CustomMessage(questState.get("hunt_request_4"), questState.getPlayer(), new Object[0]).toString());
                    string2 = string2.replace("<?reply2?>", new CustomMessage(questState.get("hunt_request_3"), questState.getPlayer(), new Object[0]).toString());
                    string2 = string2.replace("<?reply3?>", new CustomMessage(questState.get("hunt_request_2"), questState.getPlayer(), new Object[0]).toString());
                    string2 = string2.replace("<?reply4?>", new CustomMessage(questState.get("hunt_request_1"), questState.getPlayer(), new Object[0]).toString());
                    string2 = string2.replace("<?reply5?>", new CustomMessage(questState.get("hunt_request_0"), questState.getPlayer(), new Object[0]).toString());
                    return string2;
                }
                if (string.equalsIgnoreCase("reply_3")) {
                    string2 = "tor_q0335_04.htm";
                } else {
                    if (string.equalsIgnoreCase("reply_4")) {
                        if (n2 == 0) {
                            int n8;
                            int n9;
                            int n10;
                            int n11;
                            int n12;
                            questState.set("hunt_request_0", 0);
                            questState.set("hunt_request_1", 0);
                            questState.set("hunt_request_2", 0);
                            questState.set("hunt_request_3", 0);
                            questState.set("hunt_request_4", 0);
                            do {
                                if (questState.getQuestItemsCount(3694) == 0L) {
                                    n12 = Rnd.get((int)10);
                                    n11 = Rnd.get((int)10);
                                    n10 = Rnd.get((int)5);
                                    n9 = Rnd.get((int)5) + 5;
                                    n8 = Rnd.get((int)10);
                                    questState.set("the_song_of_the_hunter", 1);
                                    continue;
                                }
                                if (questState.getQuestItemsCount(3694) < 4L) {
                                    if (Rnd.get((int)100) < 20) {
                                        n12 = Rnd.get((int)6) + 10;
                                        n11 = Rnd.get((int)10);
                                        n10 = Rnd.get((int)5);
                                        n9 = Rnd.get((int)5) + 5;
                                        n8 = Rnd.get((int)10);
                                        questState.set("the_song_of_the_hunter", 1);
                                        continue;
                                    }
                                    n12 = Rnd.get((int)10);
                                    n11 = Rnd.get((int)10);
                                    n10 = Rnd.get((int)5);
                                    n9 = Rnd.get((int)5) + 5;
                                    n8 = Rnd.get((int)10);
                                    questState.set("the_song_of_the_hunter", 1);
                                    continue;
                                }
                                if (Rnd.get((int)100) < 20) {
                                    n12 = Rnd.get((int)6) + 10;
                                    n11 = Rnd.get((int)20) == 0 ? Rnd.get((int)3) + 16 : Rnd.get((int)10);
                                    n10 = Rnd.get((int)5);
                                    n9 = Rnd.get((int)5) + 5;
                                    n8 = Rnd.get((int)10);
                                    questState.set("the_song_of_the_hunter", 1);
                                    continue;
                                }
                                n12 = Rnd.get((int)10);
                                n11 = Rnd.get((int)20) == 0 ? Rnd.get((int)3) + 16 : Rnd.get((int)10);
                                n10 = Rnd.get((int)5);
                                n9 = Rnd.get((int)5) + 5;
                                n8 = Rnd.get((int)10);
                                questState.set("the_song_of_the_hunter", 1);
                            } while (n12 == n11 || n11 == n10 || n10 == n9 || n9 == n8 || n12 == n8 || n12 == n10 || n12 == n9 || n11 == n9 || n11 == n8 || n10 == n8);
                            questState.set("hunt_request_0", 33520 + (n12 + 20));
                            questState.set("hunt_request_1", 33520 + (n11 + 20));
                            questState.set("hunt_request_2", 33520 + (n10 + 20));
                            questState.set("hunt_request_3", 33520 + (n9 + 20));
                            questState.set("hunt_request_4", 33520 + (n8 + 20));
                            string2 = HtmCache.getInstance().getNotNull("quests/_335_TheSongOfTheHunter/tor_q0335_16.htm", questState.getPlayer());
                            string2 = string2.replace("<?reply1?>", new CustomMessage(questState.get("hunt_request_0"), questState.getPlayer(), new Object[0]).toString());
                            string2 = string2.replace("<?reply2?>", new CustomMessage(questState.get("hunt_request_1"), questState.getPlayer(), new Object[0]).toString());
                            string2 = string2.replace("<?reply3?>", new CustomMessage(questState.get("hunt_request_2"), questState.getPlayer(), new Object[0]).toString());
                            string2 = string2.replace("<?reply4?>", new CustomMessage(questState.get("hunt_request_3"), questState.getPlayer(), new Object[0]).toString());
                            string2 = string2.replace("<?reply5?>", new CustomMessage(questState.get("hunt_request_4"), questState.getPlayer(), new Object[0]).toString());
                            return string2;
                        }
                        string2 = HtmCache.getInstance().getNotNull("quests/_335_TheSongOfTheHunter/tor_q0335_16.htm", questState.getPlayer());
                        string2 = string2.replace("<?reply1?>", new CustomMessage(questState.get("hunt_request_4"), questState.getPlayer(), new Object[0]).toString());
                        string2 = string2.replace("<?reply2?>", new CustomMessage(questState.get("hunt_request_3"), questState.getPlayer(), new Object[0]).toString());
                        string2 = string2.replace("<?reply3?>", new CustomMessage(questState.get("hunt_request_2"), questState.getPlayer(), new Object[0]).toString());
                        string2 = string2.replace("<?reply4?>", new CustomMessage(questState.get("hunt_request_1"), questState.getPlayer(), new Object[0]).toString());
                        string2 = string2.replace("<?reply5?>", new CustomMessage(questState.get("hunt_request_0"), questState.getPlayer(), new Object[0]).toString());
                        return string2;
                    }
                    if (string.equalsIgnoreCase("reply_48")) {
                        string2 = "tor_q0335_05a.htm";
                    } else if (string.equalsIgnoreCase("reply_49")) {
                        string2 = "tor_q0335_05b.htm";
                        if (questState.getQuestItemsCount(3694) > 0L) {
                            questState.takeItems(3694, 1L);
                        }
                        questState.takeAllItems(new int[]{3727, 3728, 3729, 3730, 3731, 3732, 3733, 3734, 3735, 3736, 3737, 3738, 3739, 3740, 3741, 3742, 3743, 3744, 3745, 3746, 3747, 3748, 3749, 3750, 3751, 3752, 3753, 3754, 3755, 3756, 3757, 3758, 3759, 3760, 3761, 3762, 3763, 3764, 3765, 3766, 3767, 3768, 3769, 3770, 3771, 3772, 3773, 3774, 3775, 3776, 3777, 3778, 3779, 3780, 3781, 3782, 3783, 3784, 3785, 3786, 3787, 3788, 3789, 3790, 3791, 3792, 3793, 3794, 3795, 3796, 3797, 3798, 3799, 3800, 3801, 3802, 3803, 3804, 3805, 3806, 3807, 3808, 3809, 3810});
                    } else if (string.equalsIgnoreCase("reply_5")) {
                        string2 = "tor_q0335_10a.htm";
                        questState.giveItems(3727, 1L);
                    } else if (string.equalsIgnoreCase("reply_6")) {
                        string2 = "tor_q0335_10b.htm";
                        questState.giveItems(3728, 1L);
                    } else if (string.equalsIgnoreCase("reply_7")) {
                        string2 = "tor_q0335_10c.htm";
                        questState.giveItems(3729, 1L);
                    } else if (string.equalsIgnoreCase("reply_8")) {
                        string2 = "tor_q0335_10d.htm";
                        questState.giveItems(3730, 1L);
                    } else if (string.equalsIgnoreCase("reply_9")) {
                        string2 = "tor_q0335_10e.htm";
                        questState.giveItems(3731, 1L);
                    } else if (string.equalsIgnoreCase("reply_10")) {
                        string2 = "tor_q0335_10f.htm";
                        questState.giveItems(3732, 1L);
                    } else if (string.equalsIgnoreCase("reply_11")) {
                        string2 = "tor_q0335_10g.htm";
                        questState.giveItems(3733, 1L);
                    } else if (string.equalsIgnoreCase("reply_12")) {
                        string2 = "tor_q0335_10h.htm";
                        questState.giveItems(3734, 1L);
                    } else if (string.equalsIgnoreCase("reply_13")) {
                        string2 = "tor_q0335_10i.htm";
                        questState.giveItems(3735, 1L);
                    } else if (string.equalsIgnoreCase("reply_14")) {
                        string2 = "tor_q0335_10j.htm";
                        questState.giveItems(3736, 1L);
                    } else if (string.equalsIgnoreCase("reply_15")) {
                        string2 = "tor_q0335_10k.htm";
                        questState.giveItems(3737, 1L);
                    } else if (string.equalsIgnoreCase("reply_16")) {
                        string2 = "tor_q0335_10l.htm";
                        questState.giveItems(3738, 1L);
                    } else if (string.equalsIgnoreCase("reply_17")) {
                        string2 = "tor_q0335_11a.htm";
                        questState.giveItems(3739, 1L);
                    } else if (string.equalsIgnoreCase("reply_18")) {
                        string2 = "tor_q0335_11b.htm";
                        questState.giveItems(3740, 1L);
                    } else if (string.equalsIgnoreCase("reply_19")) {
                        string2 = "tor_q0335_11c.htm";
                        questState.giveItems(3741, 1L);
                    } else if (string.equalsIgnoreCase("reply_20")) {
                        string2 = "tor_q0335_11d.htm";
                        questState.giveItems(3742, 1L);
                    } else if (string.equalsIgnoreCase("reply_21")) {
                        string2 = "tor_q0335_11e.htm";
                        questState.giveItems(3743, 1L);
                    } else if (string.equalsIgnoreCase("reply_22")) {
                        string2 = "tor_q0335_11f.htm";
                        questState.giveItems(3744, 1L);
                    } else if (string.equalsIgnoreCase("reply_23")) {
                        string2 = "tor_q0335_12a.htm";
                        questState.giveItems(3745, 1L);
                    } else if (string.equalsIgnoreCase("reply_24")) {
                        string2 = "tor_q0335_12b.htm";
                        questState.giveItems(3746, 1L);
                    } else if (string.equalsIgnoreCase("reply_25")) {
                        string2 = "tor_q0335_12c.htm";
                        questState.giveItems(3747, 1L);
                    } else if (string.equalsIgnoreCase("reply_26")) {
                        string2 = "tor_q0335_13a.htm";
                        questState.giveItems(3748, 1L);
                    } else if (string.equalsIgnoreCase("reply_27")) {
                        string2 = "tor_q0335_13b.htm";
                        questState.giveItems(3749, 1L);
                    } else if (string.equalsIgnoreCase("reply_28")) {
                        string2 = "tor_q0335_13c.htm";
                        questState.giveItems(3750, 1L);
                    } else if (string.equalsIgnoreCase("reply_29")) {
                        string2 = "tor_q0335_13d.htm";
                        questState.giveItems(3751, 1L);
                    } else if (string.equalsIgnoreCase("reply_30")) {
                        string2 = "tor_q0335_13e.htm";
                        questState.giveItems(3752, 1L);
                    } else if (string.equalsIgnoreCase("reply_31")) {
                        string2 = "tor_q0335_13f.htm";
                        questState.giveItems(3753, 1L);
                    } else if (string.equalsIgnoreCase("reply_32")) {
                        string2 = "tor_q0335_13g.htm";
                        questState.giveItems(3754, 1L);
                    } else if (string.equalsIgnoreCase("reply_33")) {
                        string2 = "tor_q0335_13h.htm";
                        questState.giveItems(3755, 1L);
                    } else if (string.equalsIgnoreCase("reply_34")) {
                        string2 = "tor_q0335_13i.htm";
                        questState.giveItems(3756, 1L);
                    } else if (string.equalsIgnoreCase("reply_35")) {
                        string2 = "tor_q0335_13j.htm";
                        questState.giveItems(3757, 1L);
                    } else if (string.equalsIgnoreCase("reply_36")) {
                        string2 = "tor_q0335_13k.htm";
                        questState.giveItems(3758, 1L);
                    } else if (string.equalsIgnoreCase("reply_37")) {
                        string2 = "tor_q0335_13l.htm";
                        questState.giveItems(3759, 1L);
                    } else if (string.equalsIgnoreCase("reply_38")) {
                        string2 = "tor_q0335_14a.htm";
                        questState.giveItems(3760, 1L);
                    } else if (string.equalsIgnoreCase("reply_39")) {
                        string2 = "tor_q0335_14b.htm";
                        questState.giveItems(3761, 1L);
                    } else if (string.equalsIgnoreCase("reply_40")) {
                        string2 = "tor_q0335_14c.htm";
                        questState.giveItems(3762, 1L);
                    } else if (string.equalsIgnoreCase("reply_41")) {
                        string2 = "tor_q0335_14d.htm";
                        questState.giveItems(3763, 1L);
                    } else if (string.equalsIgnoreCase("reply_42")) {
                        string2 = "tor_q0335_14e.htm";
                        questState.giveItems(3764, 1L);
                    } else if (string.equalsIgnoreCase("reply_43")) {
                        string2 = "tor_q0335_14f.htm";
                        questState.giveItems(3765, 1L);
                    } else if (string.equalsIgnoreCase("reply_44")) {
                        string2 = "tor_q0335_15a.htm";
                        questState.giveItems(3766, 1L);
                    } else if (string.equalsIgnoreCase("reply_45")) {
                        string2 = "tor_q0335_15b.htm";
                        questState.giveItems(3767, 1L);
                    } else if (string.equalsIgnoreCase("reply_46")) {
                        string2 = "tor_q0335_15c.htm";
                        questState.giveItems(3768, 1L);
                    }
                }
            }
        } else if (n == 30746) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "cybellin_q0335_03.htm";
                if (questState.getQuestItemsCount(3471) == 0L) {
                    questState.giveItems(3471, 1L);
                }
                if (questState.getQuestItemsCount(3697) == 0L) {
                    questState.giveItems(3697, 1L);
                }
                questState.giveItems(3698, 1L);
                if (questState.getQuestItemsCount(3708) == 1L) {
                    questState.takeItems(3708, -1L);
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "cybellin_q0335_06.htm";
                if (questState.getQuestItemsCount(3699) > 0L) {
                    questState.giveItems(57, 3400L);
                } else if (questState.getQuestItemsCount(3700) > 0L) {
                    questState.giveItems(57, 6800L);
                } else if (questState.getQuestItemsCount(3701) > 0L) {
                    questState.giveItems(57, 13600L);
                } else if (questState.getQuestItemsCount(3702) > 0L) {
                    questState.giveItems(57, 27200L);
                } else if (questState.getQuestItemsCount(3703) > 0L) {
                    questState.giveItems(57, 54400L);
                } else if (questState.getQuestItemsCount(3704) > 0L) {
                    questState.giveItems(57, 108800L);
                } else if (questState.getQuestItemsCount(3705) > 0L) {
                    questState.giveItems(57, 217600L);
                } else if (questState.getQuestItemsCount(3706) > 0L) {
                    questState.giveItems(57, 435200L);
                } else if (questState.getQuestItemsCount(3707) > 0L) {
                    questState.giveItems(57, 870400L);
                }
                questState.takeAllItems(new int[]{3699, 3700, 3701, 3702, 3703, 3704, 3705, 3706, 3707});
            } else if (string.equalsIgnoreCase("reply_3")) {
                string2 = "cybellin_q0335_07.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                string2 = "cybellin_q0335_10.htm";
                questState.takeAllItems(new int[]{3698, 3471, 3697});
            }
        }
        return string2;
    }

    private int a(QuestState questState, int n, int n2) {
        return questState.getQuestItemsCount(n) >= (long)n2 ? 1 : 0;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 30744) break;
                if (questState.getPlayer().getLevel() >= 35) {
                    string = "grey_q0335_02.htm";
                    break;
                }
                string = "grey_q0335_01.htm";
                break;
            }
            case 2: {
                if (n == 30744) {
                    if (questState.getQuestItemsCount(3695) > 0L && this.a(questState, 3709, 40) + this.a(questState, 3710, 20) + this.a(questState, 3711, 1) + this.a(questState, 3712, 1) + this.a(questState, 3713, 1) + this.a(questState, 3714, 1) + this.a(questState, 3715, 20) + this.a(questState, 3716, 30) < 3) {
                        string = "grey_q0335_05.htm";
                    } else if (questState.getQuestItemsCount(3695) > 0L && this.a(questState, 3709, 40) + this.a(questState, 3710, 20) + this.a(questState, 3711, 1) + this.a(questState, 3712, 1) + this.a(questState, 3713, 1) + this.a(questState, 3714, 1) + this.a(questState, 3715, 20) + this.a(questState, 3716, 30) >= 3) {
                        string = "grey_q0335_06.htm";
                        questState.setCond(2);
                        questState.playSound("ItemSound.quest_middle");
                        questState.giveItems(3692, 1L);
                        questState.takeAllItems(new int[]{3709, 3710, 3711, 3712, 3713, 3714, 3715, 3716, 3695});
                    } else if (questState.getPlayer().getLevel() < 45 && questState.getQuestItemsCount(3692) > 0L) {
                        string = "grey_q0335_07.htm";
                    } else if (questState.getPlayer().getLevel() >= 45 && questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3696) == 0L) {
                        string = "grey_q0335_08.htm";
                    } else if (questState.getQuestItemsCount(3696) > 0L && this.a(questState, 3717, 20) + this.a(questState, 3718, 20) + this.a(questState, 3719, 30) + this.a(questState, 3720, 20) + this.a(questState, 3721, 20) + (this.a(questState, 3722, 1) + this.a(questState, 3723, 1) + this.a(questState, 3724, 1) + this.a(questState, 3725, 1) + this.a(questState, 3726, 5)) < 3) {
                        string = "grey_q0335_11.htm";
                    } else if (questState.getQuestItemsCount(3696) > 0L && this.a(questState, 3717, 20) + this.a(questState, 3718, 20) + this.a(questState, 3719, 30) + this.a(questState, 3720, 20) + this.a(questState, 3721, 20) + (this.a(questState, 3722, 1) + this.a(questState, 3723, 1) + this.a(questState, 3724, 1) + this.a(questState, 3725, 1) + this.a(questState, 3726, 5)) >= 3) {
                        string = "grey_q0335_12.htm";
                        questState.setCond(3);
                        questState.playSound("ItemSound.quest_middle");
                        questState.giveItems(3693, 1L);
                        questState.takeItems(3718, questState.getQuestItemsCount(3718));
                        questState.takeItems(3717, questState.getQuestItemsCount(3717));
                        questState.takeItems(3719, questState.getQuestItemsCount(3719));
                        questState.takeItems(3720, questState.getQuestItemsCount(3720));
                        questState.takeItems(3721, questState.getQuestItemsCount(3721));
                        questState.takeItems(3722, questState.getQuestItemsCount(3722));
                        questState.takeItems(3723, questState.getQuestItemsCount(3723));
                        questState.takeItems(3724, questState.getQuestItemsCount(3724));
                        questState.takeItems(3725, questState.getQuestItemsCount(3725));
                        questState.takeItems(3726, questState.getQuestItemsCount(3726));
                        questState.takeItems(3696, questState.getQuestItemsCount(3696));
                        questState.takeItems(3692, questState.getQuestItemsCount(3692));
                        questState.set("the_song_of_the_hunter", 0);
                        questState.set("hunt_request_0", 0);
                        questState.set("hunt_request_1", 0);
                        questState.set("hunt_request_2", 0);
                        questState.set("hunt_request_3", 0);
                        questState.set("hunt_request_4", 0);
                    } else if (questState.getQuestItemsCount(3693) > 0L) {
                        string = "grey_q0335_14.htm";
                    }
                }
                if (n == 30745) {
                    if (questState.getQuestItemsCount(3693) == 0L && questState.getQuestItemsCount(3692) == 0L) {
                        string = "tor_q0335_01a.htm";
                    } else if (questState.getPlayer().getLevel() < 45 && questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3727) + questState.getQuestItemsCount(3728) + questState.getQuestItemsCount(3729) + questState.getQuestItemsCount(3730) + questState.getQuestItemsCount(3731) + questState.getQuestItemsCount(3732) + questState.getQuestItemsCount(3733) + questState.getQuestItemsCount(3734) + questState.getQuestItemsCount(3735) + questState.getQuestItemsCount(3736) + questState.getQuestItemsCount(3737) + questState.getQuestItemsCount(3738) + questState.getQuestItemsCount(3739) + questState.getQuestItemsCount(3740) + questState.getQuestItemsCount(3741) + questState.getQuestItemsCount(3742) + questState.getQuestItemsCount(3743) + questState.getQuestItemsCount(3744) + questState.getQuestItemsCount(3745) + questState.getQuestItemsCount(3746) + questState.getQuestItemsCount(3747) + questState.getQuestItemsCount(3748) + questState.getQuestItemsCount(3749) + questState.getQuestItemsCount(3750) + questState.getQuestItemsCount(3751) + questState.getQuestItemsCount(3752) + questState.getQuestItemsCount(3753) + questState.getQuestItemsCount(3754) + questState.getQuestItemsCount(3755) + questState.getQuestItemsCount(3756) + questState.getQuestItemsCount(3757) + questState.getQuestItemsCount(3758) + questState.getQuestItemsCount(3759) + questState.getQuestItemsCount(3760) + questState.getQuestItemsCount(3761) + questState.getQuestItemsCount(3762) + questState.getQuestItemsCount(3763) + questState.getQuestItemsCount(3764) + questState.getQuestItemsCount(3765) + questState.getQuestItemsCount(3766) + questState.getQuestItemsCount(3767) + questState.getQuestItemsCount(3768) == 0L) {
                        string = "tor_q0335_01b.htm";
                    } else if (questState.getPlayer().getLevel() >= 45 && questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3727) + questState.getQuestItemsCount(3728) + questState.getQuestItemsCount(3729) + questState.getQuestItemsCount(3730) + questState.getQuestItemsCount(3731) + questState.getQuestItemsCount(3732) + questState.getQuestItemsCount(3733) + questState.getQuestItemsCount(3734) + questState.getQuestItemsCount(3735) + questState.getQuestItemsCount(3736) + questState.getQuestItemsCount(3737) + questState.getQuestItemsCount(3738) + questState.getQuestItemsCount(3739) + questState.getQuestItemsCount(3740) + questState.getQuestItemsCount(3741) + questState.getQuestItemsCount(3742) + questState.getQuestItemsCount(3743) + questState.getQuestItemsCount(3744) + questState.getQuestItemsCount(3745) + questState.getQuestItemsCount(3746) + questState.getQuestItemsCount(3747) + questState.getQuestItemsCount(3748) + questState.getQuestItemsCount(3749) + questState.getQuestItemsCount(3750) + questState.getQuestItemsCount(3751) + questState.getQuestItemsCount(3752) + questState.getQuestItemsCount(3753) + questState.getQuestItemsCount(3754) + questState.getQuestItemsCount(3755) + questState.getQuestItemsCount(3756) + questState.getQuestItemsCount(3757) + questState.getQuestItemsCount(3758) + questState.getQuestItemsCount(3759) + questState.getQuestItemsCount(3760) + questState.getQuestItemsCount(3761) + questState.getQuestItemsCount(3762) + questState.getQuestItemsCount(3763) + questState.getQuestItemsCount(3764) + questState.getQuestItemsCount(3765) + questState.getQuestItemsCount(3766) + questState.getQuestItemsCount(3767) + questState.getQuestItemsCount(3768) == 0L && questState.getQuestItemsCount(3696) == 0L) {
                        string = "tor_q0335_03a.htm";
                    } else if (questState.getPlayer().getLevel() >= 45 && questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3727) + questState.getQuestItemsCount(3728) + questState.getQuestItemsCount(3729) + questState.getQuestItemsCount(3730) + questState.getQuestItemsCount(3731) + questState.getQuestItemsCount(3732) + questState.getQuestItemsCount(3733) + questState.getQuestItemsCount(3734) + questState.getQuestItemsCount(3735) + questState.getQuestItemsCount(3736) + questState.getQuestItemsCount(3737) + questState.getQuestItemsCount(3738) + questState.getQuestItemsCount(3739) + questState.getQuestItemsCount(3740) + questState.getQuestItemsCount(3741) + questState.getQuestItemsCount(3742) + questState.getQuestItemsCount(3743) + questState.getQuestItemsCount(3744) + questState.getQuestItemsCount(3745) + questState.getQuestItemsCount(3746) + questState.getQuestItemsCount(3747) + questState.getQuestItemsCount(3748) + questState.getQuestItemsCount(3749) + questState.getQuestItemsCount(3750) + questState.getQuestItemsCount(3751) + questState.getQuestItemsCount(3752) + questState.getQuestItemsCount(3753) + questState.getQuestItemsCount(3754) + questState.getQuestItemsCount(3755) + questState.getQuestItemsCount(3756) + questState.getQuestItemsCount(3757) + questState.getQuestItemsCount(3758) + questState.getQuestItemsCount(3759) + questState.getQuestItemsCount(3760) + questState.getQuestItemsCount(3761) + questState.getQuestItemsCount(3762) + questState.getQuestItemsCount(3763) + questState.getQuestItemsCount(3764) + questState.getQuestItemsCount(3765) + questState.getQuestItemsCount(3766) + questState.getQuestItemsCount(3767) + questState.getQuestItemsCount(3768) == 0L && questState.getQuestItemsCount(3696) == 1L) {
                        string = "tor_q0335_03.htm";
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3727) + questState.getQuestItemsCount(3728) + questState.getQuestItemsCount(3729) + questState.getQuestItemsCount(3730) + questState.getQuestItemsCount(3731) + questState.getQuestItemsCount(3732) + questState.getQuestItemsCount(3733) + questState.getQuestItemsCount(3734) + questState.getQuestItemsCount(3735) + questState.getQuestItemsCount(3736) + questState.getQuestItemsCount(3737) + questState.getQuestItemsCount(3738) + questState.getQuestItemsCount(3739) + questState.getQuestItemsCount(3740) + questState.getQuestItemsCount(3741) + questState.getQuestItemsCount(3742) + questState.getQuestItemsCount(3743) + questState.getQuestItemsCount(3744) + questState.getQuestItemsCount(3745) + questState.getQuestItemsCount(3746) + questState.getQuestItemsCount(3747) + questState.getQuestItemsCount(3748) + questState.getQuestItemsCount(3749) + questState.getQuestItemsCount(3750) + questState.getQuestItemsCount(3751) + questState.getQuestItemsCount(3752) + questState.getQuestItemsCount(3753) + questState.getQuestItemsCount(3754) + questState.getQuestItemsCount(3755) + questState.getQuestItemsCount(3756) + questState.getQuestItemsCount(3757) + questState.getQuestItemsCount(3758) + questState.getQuestItemsCount(3759) + questState.getQuestItemsCount(3760) + questState.getQuestItemsCount(3761) + questState.getQuestItemsCount(3762) + questState.getQuestItemsCount(3763) + questState.getQuestItemsCount(3764) + questState.getQuestItemsCount(3765) + questState.getQuestItemsCount(3766) + questState.getQuestItemsCount(3767) + questState.getQuestItemsCount(3768) == 0L) {
                        string = "tor_q0335_03b.htm";
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3727) > 0L) {
                        if (questState.getQuestItemsCount(3769) >= 40L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 2090L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3727, questState.getQuestItemsCount(3727));
                            questState.takeItems(3769, questState.getQuestItemsCount(3769));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3728) > 0L) {
                        if (questState.getQuestItemsCount(3770) >= 50L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 6340L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3728, questState.getQuestItemsCount(3728));
                            questState.takeItems(3770, questState.getQuestItemsCount(3770));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3729) > 0L) {
                        if (questState.getQuestItemsCount(3771) >= 50L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 9480L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3729, questState.getQuestItemsCount(3729));
                            questState.takeItems(3771, questState.getQuestItemsCount(3771));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3730) > 0L) {
                        if (questState.getQuestItemsCount(3772) >= 30L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 9110L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3730, questState.getQuestItemsCount(3730));
                            questState.takeItems(3772, 30L);
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3731) > 0L) {
                        if (questState.getQuestItemsCount(3773) >= 40L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 8690L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3731, questState.getQuestItemsCount(3731));
                            questState.takeItems(3773, questState.getQuestItemsCount(3773));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3732) > 0L) {
                        if (questState.getQuestItemsCount(3774) >= 100L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 9480L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3732, questState.getQuestItemsCount(3732));
                            questState.takeItems(3774, questState.getQuestItemsCount(3774));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3733) > 0L) {
                        if (questState.getQuestItemsCount(3775) >= 50L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 11280L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3733, questState.getQuestItemsCount(3733));
                            questState.takeItems(3775, questState.getQuestItemsCount(3775));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3734) > 0L) {
                        if (questState.getQuestItemsCount(3776) >= 30L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 9640L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3734, questState.getQuestItemsCount(3734));
                            questState.takeItems(3776, questState.getQuestItemsCount(3776));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3735) > 0L) {
                        if (questState.getQuestItemsCount(3777) >= 100L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 9180L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3735, questState.getQuestItemsCount(3735));
                            questState.takeItems(3777, questState.getQuestItemsCount(3777));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3736) > 0L) {
                        if (questState.getQuestItemsCount(3778) >= 50L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 5160L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3736, questState.getQuestItemsCount(3736));
                            questState.takeItems(3778, questState.getQuestItemsCount(3778));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3737) > 0L) {
                        if (questState.getQuestItemsCount(3779) >= 30L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 3140L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3737, questState.getQuestItemsCount(3737));
                            questState.takeItems(3779, questState.getQuestItemsCount(3779));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3738) > 0L) {
                        if (questState.getQuestItemsCount(3780) >= 40L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 3160L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3738, questState.getQuestItemsCount(3738));
                            questState.takeItems(3780, questState.getQuestItemsCount(3780));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3739) > 0L) {
                        if (questState.getQuestItemsCount(3781) >= 1L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 6370L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3739, questState.getQuestItemsCount(3739));
                            questState.takeItems(3781, questState.getQuestItemsCount(3781));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3740) > 0L) {
                        if (questState.getQuestItemsCount(3782) >= 50L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 19080L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3740, questState.getQuestItemsCount(3740));
                            questState.takeItems(3782, questState.getQuestItemsCount(3782));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3741) > 0L) {
                        if (questState.getQuestItemsCount(3783) >= 50L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 17730L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3741, questState.getQuestItemsCount(3741));
                            questState.takeItems(3783, questState.getQuestItemsCount(3783));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3742) > 0L) {
                        if (questState.getQuestItemsCount(3784) >= 1L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 5790L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3742, questState.getQuestItemsCount(3742));
                            questState.takeItems(3784, questState.getQuestItemsCount(3784));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3743) > 0L) {
                        if (questState.getQuestItemsCount(3785) >= 1L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 8560L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3743, questState.getQuestItemsCount(3743));
                            questState.takeItems(3785, questState.getQuestItemsCount(3785));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3744) > 0L) {
                        if (questState.getQuestItemsCount(3786) >= 30L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 8320L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3744, questState.getQuestItemsCount(3744));
                            questState.takeItems(3786, questState.getQuestItemsCount(3786));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3745) > 0L) {
                        if (questState.getQuestItemsCount(3787) >= 30L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 30310L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3745, questState.getQuestItemsCount(3745));
                            questState.takeItems(3787, questState.getQuestItemsCount(3787));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3746) > 0L) {
                        if (questState.getQuestItemsCount(3788) >= 1L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 27540L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3746, questState.getQuestItemsCount(3746));
                            questState.takeItems(3788, questState.getQuestItemsCount(3788));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3692) > 0L && questState.getQuestItemsCount(3747) > 0L) {
                        if (questState.getQuestItemsCount(3789) >= 1L) {
                            string = "tor_q0335_06a.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 20560L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3747, questState.getQuestItemsCount(3747));
                            questState.takeItems(3789, questState.getQuestItemsCount(3789));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3748) > 0L) {
                        if (questState.getQuestItemsCount(3790) >= 40L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 6850L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3748, questState.getQuestItemsCount(3748));
                            questState.takeItems(3790, questState.getQuestItemsCount(3790));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3749) > 0L) {
                        if (questState.getQuestItemsCount(3791) >= 40L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 7250L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3749, questState.getQuestItemsCount(3749));
                            questState.takeItems(3791, questState.getQuestItemsCount(3791));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3750) > 0L) {
                        if (questState.getQuestItemsCount(3792) >= 50L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 7160L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3750, questState.getQuestItemsCount(3750));
                            questState.takeItems(3792, questState.getQuestItemsCount(3792));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3751) > 0L) {
                        if (questState.getQuestItemsCount(3793) >= 40L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 6580L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3751, questState.getQuestItemsCount(3751));
                            questState.takeItems(3793, questState.getQuestItemsCount(3793));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3752) > 0L) {
                        if (questState.getQuestItemsCount(3794) >= 20L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 10100L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3752, questState.getQuestItemsCount(3752));
                            questState.takeItems(3794, questState.getQuestItemsCount(3794));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3753) > 0L) {
                        if (questState.getQuestItemsCount(3795) >= 30L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 13000L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3753, questState.getQuestItemsCount(3753));
                            questState.takeItems(3795, questState.getQuestItemsCount(3795));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3754) > 0L) {
                        if (questState.getQuestItemsCount(3796) >= 40L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 7660L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3754, questState.getQuestItemsCount(3754));
                            questState.takeItems(3796, questState.getQuestItemsCount(3796));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3755) > 0L) {
                        if (questState.getQuestItemsCount(3797) >= 40L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 7660L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3755, questState.getQuestItemsCount(3755));
                            questState.takeItems(3797, questState.getQuestItemsCount(3797));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3756) > 0L) {
                        if (questState.getQuestItemsCount(3798) >= 40L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 11260L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3756, questState.getQuestItemsCount(3756));
                            questState.takeItems(3798, questState.getQuestItemsCount(3798));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3757) > 0L) {
                        if (questState.getQuestItemsCount(3799) >= 40L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 7660L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3757, questState.getQuestItemsCount(3757));
                            questState.takeItems(3799, questState.getQuestItemsCount(3799));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3758) > 0L) {
                        if (questState.getQuestItemsCount(3800) >= 30L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 8810L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3758, questState.getQuestItemsCount(3758));
                            questState.takeItems(3800, questState.getQuestItemsCount(3800));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3759) > 0L) {
                        if (questState.getQuestItemsCount(3801) >= 30L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 7350L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3759, questState.getQuestItemsCount(3759));
                            questState.takeItems(3801, questState.getQuestItemsCount(3801));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3760) > 0L) {
                        if (questState.getQuestItemsCount(3802) >= 1L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 8760L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3760, questState.getQuestItemsCount(3760));
                            questState.takeItems(3802, questState.getQuestItemsCount(3802));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3761) > 0L) {
                        if (questState.getQuestItemsCount(3803) >= 1L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 9380L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3761, questState.getQuestItemsCount(3761));
                            questState.takeItems(3803, questState.getQuestItemsCount(3803));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3762) > 0L) {
                        if (questState.getQuestItemsCount(3804) >= 40L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 17820L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3762, questState.getQuestItemsCount(3762));
                            questState.takeItems(3804, questState.getQuestItemsCount(3804));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3763) > 0L) {
                        if (questState.getQuestItemsCount(3805) >= 20L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 17540L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3763, questState.getQuestItemsCount(3763));
                            questState.takeItems(3805, questState.getQuestItemsCount(3805));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3764) > 0L) {
                        if (questState.getQuestItemsCount(3806) >= 20L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 14160L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3764, questState.getQuestItemsCount(3764));
                            questState.takeItems(3806, questState.getQuestItemsCount(3806));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3765) > 0L) {
                        if (questState.getQuestItemsCount(3807) >= 1L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 15960L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3765, questState.getQuestItemsCount(3765));
                            questState.takeItems(3807, questState.getQuestItemsCount(3807));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3766) > 0L) {
                        if (questState.getQuestItemsCount(3808) >= 20L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 39100L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3766, questState.getQuestItemsCount(3766));
                            questState.takeItems(3808, questState.getQuestItemsCount(3808));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3767) > 0L) {
                        if (questState.getQuestItemsCount(3809) >= 1L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 39550L);
                            questState.set("the_song_of_the_hunter", 0);
                            questState.playSound("ItemSound.quest_middle");
                            questState.takeItems(3767, questState.getQuestItemsCount(3767));
                            questState.takeItems(3809, questState.getQuestItemsCount(3809));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    } else if (questState.getQuestItemsCount(3693) > 0L && questState.getQuestItemsCount(3768) > 0L) {
                        if (questState.getQuestItemsCount(3810) >= 10L) {
                            string = "tor_q0335_06b.htm";
                            questState.giveItems(3694, 1L);
                            questState.giveItems(57, 41200L);
                            questState.playSound("ItemSound.quest_middle");
                            questState.set("the_song_of_the_hunter", 0);
                            questState.takeItems(3768, questState.getQuestItemsCount(3768));
                            questState.takeItems(3810, questState.getQuestItemsCount(3810));
                        } else {
                            string = "tor_q0335_05.htm";
                        }
                    }
                }
                if (n != 30746) break;
                if (questState.getQuestItemsCount(3693) == 0L && questState.getQuestItemsCount(3692) == 0L) {
                    string = "cybellin_q0335_01.htm";
                    break;
                }
                if ((questState.getQuestItemsCount(3693) > 0L || questState.getQuestItemsCount(3692) > 0L) && questState.getQuestItemsCount(3697) == 0L) {
                    string = "cybellin_q0335_02.htm";
                    break;
                }
                if ((questState.getQuestItemsCount(3693) > 0L || questState.getQuestItemsCount(3692) > 0L) && questState.getQuestItemsCount(3697) > 0L && questState.getQuestItemsCount(3698) > 0L) {
                    string = "cybellin_q0335_04.htm";
                    break;
                }
                if (!(questState.getQuestItemsCount(3693) <= 0L && questState.getQuestItemsCount(3692) <= 0L || questState.getQuestItemsCount(3697) <= 0L || questState.getQuestItemsCount(3699) <= 0L && questState.getQuestItemsCount(3700) <= 0L && questState.getQuestItemsCount(3701) <= 0L && questState.getQuestItemsCount(3702) <= 0L && questState.getQuestItemsCount(3703) <= 0L && questState.getQuestItemsCount(3704) <= 0L && questState.getQuestItemsCount(3705) <= 0L && questState.getQuestItemsCount(3706) <= 0L)) {
                    string = "cybellin_q0335_05.htm";
                    break;
                }
                if (questState.getQuestItemsCount(3707) > 0L) {
                    string = "cybellin_q0335_05a.htm";
                    questState.giveItems(57, 870400L);
                    questState.takeItems(3707, -1L);
                    break;
                }
                if ((questState.getQuestItemsCount(3693) > 0L || questState.getQuestItemsCount(3692) > 0L) && questState.getQuestItemsCount(3697) > 0L && questState.getQuestItemsCount(3698) == 0L && questState.getQuestItemsCount(3699) == 0L && questState.getQuestItemsCount(3700) == 0L && questState.getQuestItemsCount(3701) == 0L && questState.getQuestItemsCount(3702) == 0L && questState.getQuestItemsCount(3703) == 0L && questState.getQuestItemsCount(3704) == 0L && questState.getQuestItemsCount(3705) == 0L && questState.getQuestItemsCount(3706) == 0L && questState.getQuestItemsCount(3707) == 0L && questState.getQuestItemsCount(3708) == 0L) {
                    string = "cybellin_q0335_08.htm";
                    break;
                }
                if (questState.getQuestItemsCount(3693) <= 0L && questState.getQuestItemsCount(3692) <= 0L || questState.getQuestItemsCount(3697) <= 0L || questState.getQuestItemsCount(3708) <= 0L) break;
                string = "cybellin_q0335_09.htm";
                questState.takeItems(3708, -1L);
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20660 && questState.getQuestItemsCount(3696) > 0L && questState.getQuestItemsCount(3721) < 20L && Rnd.get((int)100) < 60) {
            questState.rollAndGive(3721, 1, 100.0);
            if (questState.getQuestItemsCount(3721) >= 20L) {
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 20269 && questState.getQuestItemsCount(3741) > 0L && questState.getQuestItemsCount(3783) < 50L && Rnd.get((int)100) < 93) {
            questState.rollAndGive(3783, 1, 100.0);
            if (questState.getQuestItemsCount(3783) >= 50L) {
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 20271) {
            if (questState.getQuestItemsCount(3695) > 0L && questState.getQuestItemsCount(3711) + questState.getQuestItemsCount(3712) + questState.getQuestItemsCount(3713) < 3L) {
                if (Rnd.get((int)10) < 2) {
                    NpcInstance npcInstance2;
                    if (questState.getQuestItemsCount(3711) == 0L) {
                        NpcInstance npcInstance3 = questState.addSpawn(27140, 300000);
                        if (npcInstance3 != null) {
                            npcInstance3.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                        }
                    } else if (questState.getQuestItemsCount(3712) == 0L) {
                        NpcInstance npcInstance4 = questState.addSpawn(27141, 300000);
                        if (npcInstance4 != null) {
                            npcInstance4.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                        }
                    } else if (questState.getQuestItemsCount(3713) == 0L && (npcInstance2 = questState.addSpawn(27142, 300000)) != null) {
                        npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                    }
                }
            } else if (questState.getQuestItemsCount(3741) > 0L && questState.getQuestItemsCount(3783) < 50L) {
                questState.rollAndGive(3783, 1, 100.0);
                if (questState.getQuestItemsCount(3783) >= 50L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 27140 && questState.getQuestItemsCount(3695) > 0L && questState.getQuestItemsCount(3711) < 1L) {
            questState.giveItems(3711, 1L);
            questState.playSound("ItemSound.quest_itemget");
        } else if (n == 27141 && questState.getQuestItemsCount(3695) > 0L && questState.getQuestItemsCount(3712) < 1L) {
            questState.giveItems(3712, 1L);
            questState.playSound("ItemSound.quest_itemget");
        } else if (n == 27142 && questState.getQuestItemsCount(3695) > 0L && questState.getQuestItemsCount(3713) < 1L) {
            questState.giveItems(3713, 1L);
            questState.playSound("ItemSound.quest_itemget");
        } else if (n == 20552 && questState.getQuestItemsCount(3733) > 0L && questState.getQuestItemsCount(3775) < 50L) {
            questState.rollAndGive(3775, 1, 100.0);
            if (questState.getQuestItemsCount(3775) >= 50L) {
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 20664) {
            if (questState.getQuestItemsCount(3763) > 0L && questState.getQuestItemsCount(3805) < 20L && Rnd.get((int)100) < 87) {
                questState.rollAndGive(3805, 1, 100.0);
                if (questState.getQuestItemsCount(3805) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20557) {
            if (questState.getQuestItemsCount(3731) > 0L && questState.getQuestItemsCount(3773) < 40L && Rnd.get((int)100) < 90) {
                questState.rollAndGive(3773, 1, 100.0);
                if (questState.getQuestItemsCount(3773) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20567) {
            if (questState.getQuestItemsCount(3740) > 0L && questState.getQuestItemsCount(3782) < 50L && Rnd.get((int)100) < 60) {
                questState.rollAndGive(3782, 1, 100.0);
                if (questState.getQuestItemsCount(3782) >= 50L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20565) {
            if (questState.getQuestItemsCount(3729) > 0L && questState.getQuestItemsCount(3771) < 50L) {
                questState.rollAndGive(3771, 1, 100.0);
                if (questState.getQuestItemsCount(3771) >= 50L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getQuestItemsCount(3695) > 0L && questState.getQuestItemsCount(3715) < 20L && Rnd.get((int)100) < 62) {
                questState.rollAndGive(3715, 1, 100.0);
                if (questState.getQuestItemsCount(3715) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20667) {
            if (questState.getQuestItemsCount(3753) > 0L && questState.getQuestItemsCount(3795) < 30L && Rnd.get((int)100) < 100) {
                questState.rollAndGive(3795, 1, 100.0);
                if (questState.getQuestItemsCount(3795) >= 30L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20589) {
            if (questState.getQuestItemsCount(3754) > 0L && questState.getQuestItemsCount(3796) < 40L) {
                NpcInstance npcInstance5;
                if (Rnd.get((int)100) < 59) {
                    questState.rollAndGive(3796, 1, 100.0);
                    if (questState.getQuestItemsCount(3796) >= 40L) {
                        questState.playSound("ItemSound.quest_middle");
                    }
                }
                if (Rnd.get((int)20) < 2 && (npcInstance5 = questState.addSpawn(27149, 300000)) != null) {
                    npcInstance5.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance5, (String)"NpcString.33512", (Object[])new Object[0]);
                }
            }
        } else if (n == 20594) {
            if (questState.getQuestItemsCount(3757) > 0L && questState.getQuestItemsCount(3799) < 40L) {
                NpcInstance npcInstance6;
                if (Rnd.get((int)100) < 74) {
                    questState.rollAndGive(3799, 1, 100.0);
                    if (questState.getQuestItemsCount(3799) >= 40L) {
                        questState.playSound("ItemSound.quest_middle");
                    }
                }
                if (Rnd.get((int)20) < 2 && (npcInstance6 = questState.addSpawn(27149, 300000)) != null) {
                    npcInstance6.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance6, (String)"NpcString.33512", (Object[])new Object[0]);
                }
            }
        } else if (n == 20555 && questState.getQuestItemsCount(3695) > 0L && questState.getQuestItemsCount(3716) < 30L && Rnd.get((int)100) < 84) {
            questState.rollAndGive(3716, 1, 100.0);
            if (questState.getQuestItemsCount(3716) >= 30L) {
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 20556 && questState.getQuestItemsCount(3730) > 0L && questState.getQuestItemsCount(3772) < 30L && Rnd.get((int)100) < 60) {
            questState.rollAndGive(3772, 1, 100.0);
            if (questState.getQuestItemsCount(3772) >= 30L) {
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 20554) {
            NpcInstance npcInstance7;
            if (questState.getQuestItemsCount(3735) > 0L && questState.getQuestItemsCount(3777) < 100L) {
                questState.rollAndGive(3777, 2, 100.0);
                if (questState.getQuestItemsCount(3777) >= 100L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            } else if (questState.getQuestItemsCount(3746) > 0L && questState.getQuestItemsCount(3788) < 1L && Rnd.get((int)10) < 2 && (npcInstance7 = questState.addSpawn(27160, 300000)) != null) {
                npcInstance7.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
            }
        } else if (n == 27160 && questState.getQuestItemsCount(3746) > 0L && questState.getQuestItemsCount(3788) < 1L) {
            questState.rollAndGive(3788, 1, 100.0);
            if (Rnd.get((int)2) == 0) {
                for (int i = 1; i <= 2; ++i) {
                    NpcInstance npcInstance8 = questState.addSpawn(27150, 300000);
                    if (npcInstance8 == null) continue;
                    npcInstance8.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance8, (String)"NpcString.33511", (Object[])new Object[0]);
                }
            }
        } else if (n == 20659 && questState.getQuestItemsCount(3766) > 0L && questState.getQuestItemsCount(3808) < 20L && Rnd.get((int)100) < 83) {
            questState.rollAndGive(3808, 1, 100.0);
            if (questState.getQuestItemsCount(3808) >= 20L) {
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 27149) {
            if (questState.getQuestItemsCount(3754) > 0L && questState.getQuestItemsCount(3796) < 40L) {
                questState.rollAndGive(3796, 5, 100.0);
                if (questState.getQuestItemsCount(3796) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getQuestItemsCount(3755) > 0L && questState.getQuestItemsCount(3797) < 40L) {
                questState.rollAndGive(3797, 5, 100.0);
                if (questState.getQuestItemsCount(3797) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getQuestItemsCount(3757) > 0L && questState.getQuestItemsCount(3799) < 40L) {
                questState.rollAndGive(3799, 5, 100.0);
                if (questState.getQuestItemsCount(3799) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getQuestItemsCount(3762) > 0L && questState.getQuestItemsCount(3804) < 40L) {
                questState.rollAndGive(3804, 5, 100.0);
                if (questState.getQuestItemsCount(3804) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getQuestItemsCount(3752) > 0L && questState.getQuestItemsCount(3794) < 20L) {
                questState.rollAndGive(3794, 3, 100.0);
                if (questState.getQuestItemsCount(3794) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.33513", (Object[])new Object[0]);
        } else if (n == 20550) {
            if (questState.getQuestItemsCount(3695) > 0L && questState.getQuestItemsCount(3709) < 40L && Rnd.get((int)100) < 90) {
                questState.rollAndGive(3709, 1, 100.0);
                if (questState.getQuestItemsCount(3709) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getQuestItemsCount(3732) > 0L && questState.getQuestItemsCount(3774) < 100L) {
                if (Rnd.get((int)100) < 60) {
                    questState.rollAndGive(3774, 2, 100.0);
                    if (questState.getQuestItemsCount(3774) >= 100L) {
                        questState.playSound("ItemSound.quest_middle");
                    }
                } else {
                    questState.rollAndGive(3774, 1, 100.0);
                    if (questState.getQuestItemsCount(3774) >= 100L) {
                        questState.playSound("ItemSound.quest_middle");
                    }
                }
            }
        } else if (n == 20642) {
            if (questState.getQuestItemsCount(3751) > 0L && questState.getQuestItemsCount(3793) < 40L && Rnd.get((int)100) < 98) {
                questState.rollAndGive(3793, 1, 100.0);
                if (questState.getQuestItemsCount(3793) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getPlayer().getActiveWeaponInstance() != null && questState.getPlayer().getActiveWeaponInstance().getItemId() == 3471 && (questState.getQuestItemsCount(3692) > 0L || questState.getQuestItemsCount(3693) > 0L)) {
                if (Rnd.get((int)100) < 60) {
                    if (questState.getQuestItemsCount(3697) > 0L) {
                        if (questState.getQuestItemsCount(3698) > 0L) {
                            questState.giveItems(3699, 1L);
                            questState.takeItems(3698, questState.getQuestItemsCount(3698));
                        } else if (questState.getQuestItemsCount(3699) > 0L) {
                            questState.giveItems(3700, 1L);
                            questState.takeItems(3699, questState.getQuestItemsCount(3699));
                        } else if (questState.getQuestItemsCount(3700) > 0L) {
                            questState.giveItems(3701, 1L);
                            questState.takeItems(3700, questState.getQuestItemsCount(3700));
                        } else if (questState.getQuestItemsCount(3701) > 0L) {
                            questState.giveItems(3702, 1L);
                            questState.takeItems(3701, questState.getQuestItemsCount(3701));
                        } else if (questState.getQuestItemsCount(3702) > 0L) {
                            questState.giveItems(3703, 1L);
                            questState.takeItems(3702, questState.getQuestItemsCount(3702));
                        } else if (questState.getQuestItemsCount(3703) > 0L) {
                            questState.giveItems(3704, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3703, questState.getQuestItemsCount(3703));
                        } else if (questState.getQuestItemsCount(3704) > 0L) {
                            questState.giveItems(3705, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3704, questState.getQuestItemsCount(3704));
                        } else if (questState.getQuestItemsCount(3705) > 0L) {
                            questState.giveItems(3706, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3705, questState.getQuestItemsCount(3705));
                        } else if (questState.getQuestItemsCount(3706) > 0L) {
                            questState.giveItems(3707, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3706, questState.getQuestItemsCount(3706));
                        }
                    }
                } else if (questState.getQuestItemsCount(3697) > 0L && (questState.getQuestItemsCount(3698) >= 1L || questState.getQuestItemsCount(3699) >= 1L || questState.getQuestItemsCount(3700) >= 1L || questState.getQuestItemsCount(3701) >= 1L || questState.getQuestItemsCount(3702) >= 1L || questState.getQuestItemsCount(3703) >= 1L || questState.getQuestItemsCount(3704) >= 1L || questState.getQuestItemsCount(3705) >= 1L || questState.getQuestItemsCount(3706) >= 1L)) {
                    questState.takeItems(3698, questState.getQuestItemsCount(3698));
                    questState.takeItems(3699, questState.getQuestItemsCount(3699));
                    questState.takeItems(3700, questState.getQuestItemsCount(3700));
                    questState.takeItems(3701, questState.getQuestItemsCount(3701));
                    questState.takeItems(3702, questState.getQuestItemsCount(3702));
                    questState.takeItems(3703, questState.getQuestItemsCount(3703));
                    questState.takeItems(3704, questState.getQuestItemsCount(3704));
                    questState.takeItems(3705, questState.getQuestItemsCount(3705));
                    questState.takeItems(3706, questState.getQuestItemsCount(3706));
                    questState.giveItems(3708, 1L);
                }
            }
        } else if (n == 20641) {
            if (questState.getQuestItemsCount(3751) > 0L && questState.getQuestItemsCount(3793) < 40L && Rnd.get((int)100) < 98) {
                questState.rollAndGive(3793, 1, 100.0);
                if (questState.getQuestItemsCount(3793) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getPlayer().getActiveWeaponInstance() != null && questState.getPlayer().getActiveWeaponInstance().getItemId() == 3471 && (questState.getQuestItemsCount(3692) > 0L || questState.getQuestItemsCount(3693) > 0L)) {
                if (Rnd.get((int)100) < 60) {
                    if (questState.getQuestItemsCount(3697) > 0L) {
                        if (questState.getQuestItemsCount(3698) > 0L) {
                            questState.giveItems(3699, 1L);
                            questState.takeItems(3698, questState.getQuestItemsCount(3698));
                        } else if (questState.getQuestItemsCount(3699) > 0L) {
                            questState.giveItems(3700, 1L);
                            questState.takeItems(3699, questState.getQuestItemsCount(3699));
                        } else if (questState.getQuestItemsCount(3700) > 0L) {
                            questState.giveItems(3701, 1L);
                            questState.takeItems(3700, questState.getQuestItemsCount(3700));
                        } else if (questState.getQuestItemsCount(3701) > 0L) {
                            questState.giveItems(3702, 1L);
                            questState.takeItems(3701, questState.getQuestItemsCount(3701));
                        } else if (questState.getQuestItemsCount(3702) > 0L) {
                            questState.giveItems(3703, 1L);
                            questState.takeItems(3702, questState.getQuestItemsCount(3702));
                        } else if (questState.getQuestItemsCount(3703) > 0L) {
                            questState.giveItems(3704, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3703, questState.getQuestItemsCount(3703));
                        } else if (questState.getQuestItemsCount(3704) > 0L) {
                            questState.giveItems(3705, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3704, questState.getQuestItemsCount(3704));
                        } else if (questState.getQuestItemsCount(3705) > 0L) {
                            questState.giveItems(3706, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3705, questState.getQuestItemsCount(3705));
                        } else if (questState.getQuestItemsCount(3706) > 0L) {
                            questState.giveItems(3707, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3706, questState.getQuestItemsCount(3706));
                        }
                    }
                } else if (questState.getQuestItemsCount(3697) > 0L && (questState.getQuestItemsCount(3698) >= 1L || questState.getQuestItemsCount(3699) >= 1L || questState.getQuestItemsCount(3700) >= 1L || questState.getQuestItemsCount(3701) >= 1L || questState.getQuestItemsCount(3702) >= 1L || questState.getQuestItemsCount(3703) >= 1L || questState.getQuestItemsCount(3704) >= 1L || questState.getQuestItemsCount(3705) >= 1L || questState.getQuestItemsCount(3706) >= 1L)) {
                    questState.takeItems(3698, questState.getQuestItemsCount(3698));
                    questState.takeItems(3699, questState.getQuestItemsCount(3699));
                    questState.takeItems(3700, questState.getQuestItemsCount(3700));
                    questState.takeItems(3701, questState.getQuestItemsCount(3701));
                    questState.takeItems(3702, questState.getQuestItemsCount(3702));
                    questState.takeItems(3703, questState.getQuestItemsCount(3703));
                    questState.takeItems(3704, questState.getQuestItemsCount(3704));
                    questState.takeItems(3705, questState.getQuestItemsCount(3705));
                    questState.takeItems(3706, questState.getQuestItemsCount(3706));
                    questState.giveItems(3708, 1L);
                }
            }
        } else if (n == 20643) {
            if (questState.getQuestItemsCount(3751) > 0L && questState.getQuestItemsCount(3793) < 40L && Rnd.get((int)100) < 100) {
                questState.rollAndGive(3793, 1, 100.0);
                if (questState.getQuestItemsCount(3793) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getQuestItemsCount(3751) > 0L && questState.getQuestItemsCount(3793) < 40L) {
                if (Rnd.get((int)100) < 60) {
                    if (questState.getQuestItemsCount(3697) > 0L) {
                        if (questState.getQuestItemsCount(3698) > 0L) {
                            questState.giveItems(3699, 1L);
                            questState.takeItems(3698, questState.getQuestItemsCount(3698));
                        } else if (questState.getQuestItemsCount(3699) > 0L) {
                            questState.giveItems(3700, 1L);
                            questState.takeItems(3699, questState.getQuestItemsCount(3699));
                        } else if (questState.getQuestItemsCount(3700) > 0L) {
                            questState.giveItems(3701, 1L);
                            questState.takeItems(3700, questState.getQuestItemsCount(3700));
                        } else if (questState.getQuestItemsCount(3701) > 0L) {
                            questState.giveItems(3702, 1L);
                            questState.takeItems(3701, questState.getQuestItemsCount(3701));
                        } else if (questState.getQuestItemsCount(3702) > 0L) {
                            questState.giveItems(3703, 1L);
                            questState.takeItems(3702, questState.getQuestItemsCount(3702));
                        } else if (questState.getQuestItemsCount(3703) > 0L) {
                            questState.giveItems(3704, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3703, questState.getQuestItemsCount(3703));
                        } else if (questState.getQuestItemsCount(3704) > 0L) {
                            questState.giveItems(3705, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3704, questState.getQuestItemsCount(3704));
                        } else if (questState.getQuestItemsCount(3705) > 0L) {
                            questState.giveItems(3706, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3705, questState.getQuestItemsCount(3705));
                        } else if (questState.getQuestItemsCount(3706) > 0L) {
                            questState.giveItems(3707, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3706, questState.getQuestItemsCount(3706));
                        }
                    }
                } else if (questState.getQuestItemsCount(3697) > 0L && (questState.getQuestItemsCount(3698) >= 1L || questState.getQuestItemsCount(3699) >= 1L || questState.getQuestItemsCount(3700) >= 1L || questState.getQuestItemsCount(3701) >= 1L || questState.getQuestItemsCount(3702) >= 1L || questState.getQuestItemsCount(3703) >= 1L || questState.getQuestItemsCount(3704) >= 1L || questState.getQuestItemsCount(3705) >= 1L || questState.getQuestItemsCount(3706) >= 1L)) {
                    questState.takeItems(3698, questState.getQuestItemsCount(3698));
                    questState.takeItems(3699, questState.getQuestItemsCount(3699));
                    questState.takeItems(3700, questState.getQuestItemsCount(3700));
                    questState.takeItems(3701, questState.getQuestItemsCount(3701));
                    questState.takeItems(3702, questState.getQuestItemsCount(3702));
                    questState.takeItems(3703, questState.getQuestItemsCount(3703));
                    questState.takeItems(3704, questState.getQuestItemsCount(3704));
                    questState.takeItems(3705, questState.getQuestItemsCount(3705));
                    questState.takeItems(3706, questState.getQuestItemsCount(3706));
                    questState.giveItems(3708, 1L);
                }
            }
        } else if (n == 27162) {
            if (questState.getQuestItemsCount(3767) > 0L && questState.getQuestItemsCount(3809) < 1L) {
                questState.giveItems(3809, 1L);
                if (Rnd.get((int)2) == 0) {
                    for (int i = 1; i <= 2; ++i) {
                        NpcInstance npcInstance9 = questState.addSpawn(27150, 300000);
                        if (npcInstance9 == null) continue;
                        npcInstance9.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance9, (String)"NpcString.33511", (Object[])new Object[0]);
                    }
                }
            }
        } else if (n == 20662) {
            NpcInstance npcInstance10;
            if (questState.getQuestItemsCount(3752) > 0L && questState.getQuestItemsCount(3794) < 20L) {
                if (Rnd.get((int)100) < 62) {
                    questState.rollAndGive(3794, 1, 100.0);
                    if (questState.getQuestItemsCount(3794) >= 20L) {
                        questState.playSound("ItemSound.quest_middle");
                    }
                }
                if (Rnd.get((int)20) < 2 && (npcInstance10 = questState.addSpawn(27149, 300000)) != null) {
                    npcInstance10.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance10, (String)"NpcString.33512", (Object[])new Object[0]);
                }
            }
            if (questState.getQuestItemsCount(3767) > 0L && questState.getQuestItemsCount(3809) < 1L && Rnd.get((int)10) < 2 && (npcInstance10 = questState.addSpawn(27162, 300000)) != null) {
                npcInstance10.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                Functions.npcSayCustomMessage((NpcInstance)npcInstance10, (String)"NpcString.33512", (Object[])new Object[0]);
            }
        } else if (n == 20661) {
            if (questState.getQuestItemsCount(3752) > 0L && questState.getQuestItemsCount(3794) < 20L) {
                NpcInstance npcInstance11;
                if (Rnd.get((int)100) < 60) {
                    questState.rollAndGive(3794, 1, 100.0);
                    if (questState.getQuestItemsCount(3794) >= 20L) {
                        questState.playSound("ItemSound.quest_middle");
                    }
                }
                if (Rnd.get((int)20) < 2 && (npcInstance11 = questState.addSpawn(27149, 300000)) != null) {
                    npcInstance11.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance11, (String)"NpcString.33512", (Object[])new Object[0]);
                }
            }
        } else if (n == 20676) {
            if (questState.getQuestItemsCount(3768) > 0L && questState.getQuestItemsCount(3810) < 10L && Rnd.get((int)100) < 74) {
                questState.rollAndGive(3810, 1, 100.0);
                if (questState.getQuestItemsCount(3810) >= 10L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20600) {
            NpcInstance npcInstance12;
            if (questState.getQuestItemsCount(3737) > 0L && questState.getQuestItemsCount(3779) < 30L && Rnd.get((int)100) < 60) {
                questState.rollAndGive(3779, 1, 100.0);
                if (questState.getQuestItemsCount(3779) >= 30L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getQuestItemsCount(3747) > 0L && questState.getQuestItemsCount(3789) < 1L && Rnd.get((int)10) < 2 && (npcInstance12 = questState.addSpawn(27164, 300000)) != null) {
                npcInstance12.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                Functions.npcSayCustomMessage((NpcInstance)npcInstance12, (String)"NpcString.33511", (Object[])new Object[0]);
            }
        } else if (n == 27164) {
            if (questState.getQuestItemsCount(3747) > 0L && questState.getQuestItemsCount(3789) < 1L) {
                questState.giveItems(3789, 1L);
                if (Rnd.get((int)2) == 0) {
                    for (int i = 1; i <= 2; ++i) {
                        NpcInstance npcInstance13 = questState.addSpawn(27150, 300000);
                        if (npcInstance13 == null) continue;
                        npcInstance13.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance13, (String)"NpcString.33511", (Object[])new Object[0]);
                    }
                }
            }
        } else if (n == 20603) {
            if (questState.getQuestItemsCount(3744) > 0L && questState.getQuestItemsCount(3786) < 30L && Rnd.get((int)100) < 60) {
                questState.rollAndGive(3786, 1, 100.0);
                if (questState.getQuestItemsCount(3786) >= 30L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 27157 && questState.getQuestItemsCount(3739) > 0L && questState.getQuestItemsCount(3781) < 1L) {
            questState.giveItems(3781, 1L);
        } else if (n == 20578) {
            if (questState.getQuestItemsCount(3727) > 0L && questState.getQuestItemsCount(3769) < 40L && Rnd.get((int)10) < 9) {
                questState.rollAndGive(3769, 1, 100.0);
                if (questState.getQuestItemsCount(3769) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getPlayer().getActiveWeaponInstance() != null && questState.getPlayer().getActiveWeaponInstance().getItemId() == 3471 && (questState.getQuestItemsCount(3692) > 0L || questState.getQuestItemsCount(3693) > 0L)) {
                if (Rnd.get((int)100) < 60) {
                    if (questState.getQuestItemsCount(3697) > 0L) {
                        if (questState.getQuestItemsCount(3698) > 0L) {
                            questState.giveItems(3699, 1L);
                            questState.takeItems(3698, questState.getQuestItemsCount(3698));
                        } else if (questState.getQuestItemsCount(3699) > 0L) {
                            questState.giveItems(3700, 1L);
                            questState.takeItems(3699, questState.getQuestItemsCount(3699));
                        } else if (questState.getQuestItemsCount(3700) > 0L) {
                            questState.giveItems(3701, 1L);
                            questState.takeItems(3700, questState.getQuestItemsCount(3700));
                        } else if (questState.getQuestItemsCount(3701) > 0L) {
                            questState.giveItems(3702, 1L);
                            questState.takeItems(3701, questState.getQuestItemsCount(3701));
                        } else if (questState.getQuestItemsCount(3702) > 0L) {
                            questState.giveItems(3703, 1L);
                            questState.takeItems(3702, questState.getQuestItemsCount(3702));
                        } else if (questState.getQuestItemsCount(3703) > 0L) {
                            questState.giveItems(3704, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3703, questState.getQuestItemsCount(3703));
                        } else if (questState.getQuestItemsCount(3704) > 0L) {
                            questState.giveItems(3705, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3704, questState.getQuestItemsCount(3704));
                        } else if (questState.getQuestItemsCount(3705) > 0L) {
                            questState.giveItems(3706, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3705, questState.getQuestItemsCount(3705));
                        } else if (questState.getQuestItemsCount(3706) > 0L) {
                            questState.giveItems(3707, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3706, questState.getQuestItemsCount(3706));
                        }
                    }
                } else if (questState.getQuestItemsCount(3697) > 0L && (questState.getQuestItemsCount(3698) >= 1L || questState.getQuestItemsCount(3699) >= 1L || questState.getQuestItemsCount(3700) >= 1L || questState.getQuestItemsCount(3701) >= 1L || questState.getQuestItemsCount(3702) >= 1L || questState.getQuestItemsCount(3703) >= 1L || questState.getQuestItemsCount(3704) >= 1L || questState.getQuestItemsCount(3705) >= 1L || questState.getQuestItemsCount(3706) >= 1L)) {
                    questState.takeItems(3698, questState.getQuestItemsCount(3698));
                    questState.takeItems(3699, questState.getQuestItemsCount(3699));
                    questState.takeItems(3700, questState.getQuestItemsCount(3700));
                    questState.takeItems(3701, questState.getQuestItemsCount(3701));
                    questState.takeItems(3702, questState.getQuestItemsCount(3702));
                    questState.takeItems(3703, questState.getQuestItemsCount(3703));
                    questState.takeItems(3704, questState.getQuestItemsCount(3704));
                    questState.takeItems(3705, questState.getQuestItemsCount(3705));
                    questState.takeItems(3706, questState.getQuestItemsCount(3706));
                    questState.giveItems(3708, 1L);
                }
            }
        } else if (n == 20582) {
            NpcInstance npcInstance14;
            if (questState.getQuestItemsCount(3739) > 0L && questState.getQuestItemsCount(3781) < 1L && Rnd.get((int)10) < 2 && (npcInstance14 = questState.addSpawn(27157, 300000)) != null) {
                npcInstance14.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
            }
            if (questState.getPlayer().getActiveWeaponInstance() != null && questState.getPlayer().getActiveWeaponInstance().getItemId() == 3471 && (questState.getQuestItemsCount(3692) > 0L || questState.getQuestItemsCount(3693) > 0L)) {
                if (Rnd.get((int)100) < 60) {
                    if (questState.getQuestItemsCount(3697) > 0L) {
                        if (questState.getQuestItemsCount(3698) > 0L) {
                            questState.giveItems(3699, 1L);
                            questState.takeItems(3698, questState.getQuestItemsCount(3698));
                        } else if (questState.getQuestItemsCount(3699) > 0L) {
                            questState.giveItems(3700, 1L);
                            questState.takeItems(3699, questState.getQuestItemsCount(3699));
                        } else if (questState.getQuestItemsCount(3700) > 0L) {
                            questState.giveItems(3701, 1L);
                            questState.takeItems(3700, questState.getQuestItemsCount(3700));
                        } else if (questState.getQuestItemsCount(3701) > 0L) {
                            questState.giveItems(3702, 1L);
                            questState.takeItems(3701, questState.getQuestItemsCount(3701));
                        } else if (questState.getQuestItemsCount(3702) > 0L) {
                            questState.giveItems(3703, 1L);
                            questState.takeItems(3702, questState.getQuestItemsCount(3702));
                        } else if (questState.getQuestItemsCount(3703) > 0L) {
                            questState.giveItems(3704, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3703, questState.getQuestItemsCount(3703));
                        } else if (questState.getQuestItemsCount(3704) > 0L) {
                            questState.giveItems(3705, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3704, questState.getQuestItemsCount(3704));
                        } else if (questState.getQuestItemsCount(3705) > 0L) {
                            questState.giveItems(3706, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3705, questState.getQuestItemsCount(3705));
                        } else if (questState.getQuestItemsCount(3706) > 0L) {
                            questState.giveItems(3707, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3706, questState.getQuestItemsCount(3706));
                        }
                    }
                } else if (questState.getQuestItemsCount(3697) > 0L && (questState.getQuestItemsCount(3698) >= 1L || questState.getQuestItemsCount(3699) >= 1L || questState.getQuestItemsCount(3700) >= 1L || questState.getQuestItemsCount(3701) >= 1L || questState.getQuestItemsCount(3702) >= 1L || questState.getQuestItemsCount(3703) >= 1L || questState.getQuestItemsCount(3704) >= 1L || questState.getQuestItemsCount(3705) >= 1L || questState.getQuestItemsCount(3706) >= 1L)) {
                    questState.takeItems(3698, questState.getQuestItemsCount(3698));
                    questState.takeItems(3699, questState.getQuestItemsCount(3699));
                    questState.takeItems(3700, questState.getQuestItemsCount(3700));
                    questState.takeItems(3701, questState.getQuestItemsCount(3701));
                    questState.takeItems(3702, questState.getQuestItemsCount(3702));
                    questState.takeItems(3703, questState.getQuestItemsCount(3703));
                    questState.takeItems(3704, questState.getQuestItemsCount(3704));
                    questState.takeItems(3705, questState.getQuestItemsCount(3705));
                    questState.takeItems(3706, questState.getQuestItemsCount(3706));
                    questState.giveItems(3708, 1L);
                }
            }
        } else if (n == 20581) {
            NpcInstance npcInstance15;
            if (questState.getQuestItemsCount(3742) > 0L && questState.getQuestItemsCount(3784) < 1L && Rnd.get((int)10) < 2 && (npcInstance15 = questState.addSpawn(27156, 300000)) != null) {
                npcInstance15.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
            }
            if (questState.getPlayer().getActiveWeaponInstance() != null && questState.getPlayer().getActiveWeaponInstance().getItemId() == 3471 && (questState.getQuestItemsCount(3692) > 0L || questState.getQuestItemsCount(3693) > 0L)) {
                if (Rnd.get((int)100) < 60) {
                    if (questState.getQuestItemsCount(3697) > 0L) {
                        if (questState.getQuestItemsCount(3698) > 0L) {
                            questState.giveItems(3699, 1L);
                            questState.takeItems(3698, questState.getQuestItemsCount(3698));
                        } else if (questState.getQuestItemsCount(3699) > 0L) {
                            questState.giveItems(3700, 1L);
                            questState.takeItems(3699, questState.getQuestItemsCount(3699));
                        } else if (questState.getQuestItemsCount(3700) > 0L) {
                            questState.giveItems(3701, 1L);
                            questState.takeItems(3700, questState.getQuestItemsCount(3700));
                        } else if (questState.getQuestItemsCount(3701) > 0L) {
                            questState.giveItems(3702, 1L);
                            questState.takeItems(3701, questState.getQuestItemsCount(3701));
                        } else if (questState.getQuestItemsCount(3702) > 0L) {
                            questState.giveItems(3703, 1L);
                            questState.takeItems(3702, questState.getQuestItemsCount(3702));
                        } else if (questState.getQuestItemsCount(3703) > 0L) {
                            questState.giveItems(3704, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3703, questState.getQuestItemsCount(3703));
                        } else if (questState.getQuestItemsCount(3704) > 0L) {
                            questState.giveItems(3705, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3704, questState.getQuestItemsCount(3704));
                        } else if (questState.getQuestItemsCount(3705) > 0L) {
                            questState.giveItems(3706, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3705, questState.getQuestItemsCount(3705));
                        } else if (questState.getQuestItemsCount(3706) > 0L) {
                            questState.giveItems(3707, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3706, questState.getQuestItemsCount(3706));
                        }
                    }
                } else if (questState.getQuestItemsCount(3697) > 0L && (questState.getQuestItemsCount(3698) >= 1L || questState.getQuestItemsCount(3699) >= 1L || questState.getQuestItemsCount(3700) >= 1L || questState.getQuestItemsCount(3701) >= 1L || questState.getQuestItemsCount(3702) >= 1L || questState.getQuestItemsCount(3703) >= 1L || questState.getQuestItemsCount(3704) >= 1L || questState.getQuestItemsCount(3705) >= 1L || questState.getQuestItemsCount(3706) >= 1L)) {
                    questState.takeItems(3698, questState.getQuestItemsCount(3698));
                    questState.takeItems(3699, questState.getQuestItemsCount(3699));
                    questState.takeItems(3700, questState.getQuestItemsCount(3700));
                    questState.takeItems(3701, questState.getQuestItemsCount(3701));
                    questState.takeItems(3702, questState.getQuestItemsCount(3702));
                    questState.takeItems(3703, questState.getQuestItemsCount(3703));
                    questState.takeItems(3704, questState.getQuestItemsCount(3704));
                    questState.takeItems(3705, questState.getQuestItemsCount(3705));
                    questState.takeItems(3706, questState.getQuestItemsCount(3706));
                    questState.giveItems(3708, 1L);
                }
            }
        } else if (n == 20579) {
            if (questState.getQuestItemsCount(3727) > 0L && questState.getQuestItemsCount(3769) < 40L && Rnd.get((int)100) < 93) {
                questState.rollAndGive(3769, 1, 100.0);
                if (questState.getQuestItemsCount(3769) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getPlayer().getActiveWeaponInstance() != null && questState.getPlayer().getActiveWeaponInstance().getItemId() == 3471 && (questState.getQuestItemsCount(3692) > 0L || questState.getQuestItemsCount(3693) > 0L)) {
                if (Rnd.get((int)100) < 60) {
                    if (questState.getQuestItemsCount(3697) > 0L) {
                        if (questState.getQuestItemsCount(3698) > 0L) {
                            questState.giveItems(3699, 1L);
                            questState.takeItems(3698, questState.getQuestItemsCount(3698));
                        } else if (questState.getQuestItemsCount(3699) > 0L) {
                            questState.giveItems(3700, 1L);
                            questState.takeItems(3699, questState.getQuestItemsCount(3699));
                        } else if (questState.getQuestItemsCount(3700) > 0L) {
                            questState.giveItems(3701, 1L);
                            questState.takeItems(3700, questState.getQuestItemsCount(3700));
                        } else if (questState.getQuestItemsCount(3701) > 0L) {
                            questState.giveItems(3702, 1L);
                            questState.takeItems(3701, questState.getQuestItemsCount(3701));
                        } else if (questState.getQuestItemsCount(3702) > 0L) {
                            questState.giveItems(3703, 1L);
                            questState.takeItems(3702, questState.getQuestItemsCount(3702));
                        } else if (questState.getQuestItemsCount(3703) > 0L) {
                            questState.giveItems(3704, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3703, questState.getQuestItemsCount(3703));
                        } else if (questState.getQuestItemsCount(3704) > 0L) {
                            questState.giveItems(3705, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3704, questState.getQuestItemsCount(3704));
                        } else if (questState.getQuestItemsCount(3705) > 0L) {
                            questState.giveItems(3706, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3705, questState.getQuestItemsCount(3705));
                        } else if (questState.getQuestItemsCount(3706) > 0L) {
                            questState.giveItems(3707, 1L);
                            questState.playSound("ItemSound.quest_jackpot");
                            questState.takeItems(3706, questState.getQuestItemsCount(3706));
                        }
                    }
                } else if (questState.getQuestItemsCount(3697) > 0L && (questState.getQuestItemsCount(3698) >= 1L || questState.getQuestItemsCount(3699) >= 1L || questState.getQuestItemsCount(3700) >= 1L || questState.getQuestItemsCount(3701) >= 1L || questState.getQuestItemsCount(3702) >= 1L || questState.getQuestItemsCount(3703) >= 1L || questState.getQuestItemsCount(3704) >= 1L || questState.getQuestItemsCount(3705) >= 1L || questState.getQuestItemsCount(3706) >= 1L)) {
                    questState.takeItems(3698, questState.getQuestItemsCount(3698));
                    questState.takeItems(3699, questState.getQuestItemsCount(3699));
                    questState.takeItems(3700, questState.getQuestItemsCount(3700));
                    questState.takeItems(3701, questState.getQuestItemsCount(3701));
                    questState.takeItems(3702, questState.getQuestItemsCount(3702));
                    questState.takeItems(3703, questState.getQuestItemsCount(3703));
                    questState.takeItems(3704, questState.getQuestItemsCount(3704));
                    questState.takeItems(3705, questState.getQuestItemsCount(3705));
                    questState.takeItems(3706, questState.getQuestItemsCount(3706));
                    questState.giveItems(3708, 1L);
                }
            }
        } else if (n == 27156 && questState.getQuestItemsCount(3742) > 0L && questState.getQuestItemsCount(3784) < 1L) {
            questState.giveItems(3784, 1L);
        } else if (n == 20590) {
            if (questState.getQuestItemsCount(3755) > 0L && questState.getQuestItemsCount(3797) < 40L) {
                NpcInstance npcInstance16;
                if (Rnd.get((int)100) < 61) {
                    questState.rollAndGive(3797, 1, 100.0);
                    if (questState.getQuestItemsCount(3797) >= 40L) {
                        questState.playSound("ItemSound.quest_middle");
                    }
                }
                if (Rnd.get((int)20) < 2 && (npcInstance16 = questState.addSpawn(27149, 300000)) != null) {
                    npcInstance16.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance16, (String)"NpcString.33512", (Object[])new Object[0]);
                }
            }
        } else if (n == 20563) {
            if (questState.getQuestItemsCount(3695) > 0L && questState.getQuestItemsCount(3715) < 20L && Rnd.get((int)100) < 60) {
                questState.rollAndGive(3715, 1, 100.0);
                if (questState.getQuestItemsCount(3715) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20639) {
            if (questState.getQuestItemsCount(3762) > 0L && questState.getQuestItemsCount(3804) < 40L) {
                NpcInstance npcInstance17;
                if (Rnd.get((int)100) < 96) {
                    questState.rollAndGive(3804, 1, 100.0);
                    if (questState.getQuestItemsCount(3804) >= 40L) {
                        questState.playSound("ItemSound.quest_middle");
                    }
                }
                if (Rnd.get((int)20) < 2 && (npcInstance17 = questState.addSpawn(27149, 300000)) != null) {
                    npcInstance17.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance17, (String)"NpcString.33512", (Object[])new Object[0]);
                }
            }
        } else if (n == 20592) {
            if (questState.getQuestItemsCount(3756) > 0L && questState.getQuestItemsCount(3798) < 40L && Rnd.get((int)100) < 90) {
                questState.rollAndGive(3798, 1, 100.0);
                if (questState.getQuestItemsCount(3798) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20598) {
            if (questState.getQuestItemsCount(3756) > 0L && questState.getQuestItemsCount(3798) < 40L) {
                questState.rollAndGive(3798, 1, 100.0);
                if (questState.getQuestItemsCount(3798) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20558) {
            if (questState.getQuestItemsCount(3748) > 0L && questState.getQuestItemsCount(3790) < 40L && Rnd.get((int)100) < 77) {
                questState.rollAndGive(3790, 1, 100.0);
                if (questState.getQuestItemsCount(3790) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20562) {
            if (questState.getQuestItemsCount(3745) > 0L && questState.getQuestItemsCount(3787) < 30L && Rnd.get((int)100) < 60) {
                questState.rollAndGive(3787, 1, 100.0);
                if (questState.getQuestItemsCount(3787) >= 30L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20631) {
            if (questState.getQuestItemsCount(3736) > 0L && questState.getQuestItemsCount(3778) < 50L) {
                questState.rollAndGive(3778, 1, 100.0);
                if (questState.getQuestItemsCount(3778) >= 50L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20634) {
            NpcInstance npcInstance18;
            if (questState.getQuestItemsCount(3750) > 0L && questState.getQuestItemsCount(3792) < 50L) {
                if (Rnd.get((int)100) < 99) {
                    questState.rollAndGive(3792, 1, 100.0);
                    if (questState.getQuestItemsCount(3792) >= 50L) {
                        questState.playSound("ItemSound.quest_middle");
                    }
                }
            } else if (questState.getQuestItemsCount(3761) > 0L && questState.getQuestItemsCount(3803) < 1L && Rnd.get((int)10) < 2 && (npcInstance18 = questState.addSpawn(27161, 300000)) != null) {
                npcInstance18.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
            }
        } else if (n == 20633) {
            if (questState.getQuestItemsCount(3750) > 0L && questState.getQuestItemsCount(3792) < 50L && Rnd.get((int)100) < 63) {
                questState.rollAndGive(3792, 1, 100.0);
                if (questState.getQuestItemsCount(3792) >= 50L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20632) {
            if (questState.getQuestItemsCount(3736) > 0L && questState.getQuestItemsCount(3778) < 50L && Rnd.get((int)100) < 93) {
                questState.rollAndGive(3778, 1, 100.0);
                if (questState.getQuestItemsCount(3778) >= 50L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 27161 && questState.getQuestItemsCount(3761) > 0L && questState.getQuestItemsCount(3803) < 1L) {
            questState.giveItems(3803, 1L);
        } else if (n == 20675) {
            if (questState.getQuestItemsCount(3696) > 0L && questState.getQuestItemsCount(3720) < 20L && Rnd.get((int)100) < 60) {
                questState.rollAndGive(3720, 1, 100.0);
                if (questState.getQuestItemsCount(3720) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 27144 && questState.getQuestItemsCount(3696) > 0L && questState.getQuestItemsCount(3722) < 1L) {
            questState.giveItems(3722, 1L);
        } else if (n == 27148 && questState.getQuestItemsCount(3696) > 0L && questState.getQuestItemsCount(3726) < 1L) {
            questState.giveItems(3726, 1L);
        } else if (n == 27145 && questState.getQuestItemsCount(3696) > 0L && questState.getQuestItemsCount(3723) < 1L) {
            questState.giveItems(3723, 1L);
        } else if (n == 27147 && questState.getQuestItemsCount(3696) > 0L && questState.getQuestItemsCount(3725) < 1L) {
            questState.giveItems(3725, 1L);
            questState.playSound("ItemSound.quest_itemget");
        } else if (n == 27146 && questState.getQuestItemsCount(3696) > 0L && questState.getQuestItemsCount(3724) < 1L) {
            questState.giveItems(3724, 1L);
            questState.playSound("ItemSound.quest_itemget");
        } else if (n == 20601) {
            if (questState.getQuestItemsCount(3738) > 0L && questState.getQuestItemsCount(3780) < 40L && Rnd.get((int)100) < 72) {
                questState.rollAndGive(3780, 1, 100.0);
                if (questState.getQuestItemsCount(3780) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20602) {
            if (questState.getQuestItemsCount(3738) > 0L && questState.getQuestItemsCount(3780) < 40L && Rnd.get((int)100) < 90) {
                questState.rollAndGive(3780, 1, 100.0);
                if (questState.getQuestItemsCount(3780) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20571) {
            if (questState.getQuestItemsCount(3696) > 0L && questState.getQuestItemsCount(3722) + questState.getQuestItemsCount(3723) + questState.getQuestItemsCount(3724) + questState.getQuestItemsCount(3725) + questState.getQuestItemsCount(3726) < 5L && Rnd.get((int)10) < 2) {
                NpcInstance npcInstance19;
                if (questState.getQuestItemsCount(3722) == 0L) {
                    NpcInstance npcInstance20 = questState.addSpawn(27144, 300000);
                    if (npcInstance20 != null) {
                        npcInstance20.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                    }
                } else if (questState.getQuestItemsCount(3723) == 0L) {
                    NpcInstance npcInstance21 = questState.addSpawn(27145, 300000);
                    if (npcInstance21 != null) {
                        npcInstance21.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                    }
                } else if (questState.getQuestItemsCount(3724) == 0L) {
                    NpcInstance npcInstance22 = questState.addSpawn(27146, 300000);
                    if (npcInstance22 != null) {
                        npcInstance22.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                    }
                } else if (questState.getQuestItemsCount(3725) == 0L) {
                    NpcInstance npcInstance23 = questState.addSpawn(27147, 300000);
                    if (npcInstance23 != null) {
                        npcInstance23.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                    }
                } else if (questState.getQuestItemsCount(3726) == 0L && (npcInstance19 = questState.addSpawn(27148, 300000)) != null) {
                    npcInstance19.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                }
            }
            if (questState.getQuestItemsCount(3759) > 0L && questState.getQuestItemsCount(3801) < 30L && Rnd.get((int)100) < 73) {
                questState.rollAndGive(3801, 1, 100.0);
                if (questState.getQuestItemsCount(3801) >= 30L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20588) {
            NpcInstance npcInstance24;
            if (questState.getQuestItemsCount(3728) > 0L && questState.getQuestItemsCount(3770) < 50L) {
                questState.rollAndGive(3770, 1, 100.0);
                if (questState.getQuestItemsCount(3770) >= 50L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getQuestItemsCount(3760) > 0L && questState.getQuestItemsCount(3802) < 1L && Rnd.get((int)10) == 0 && (npcInstance24 = questState.addSpawn(27159, 300000)) != null) {
                npcInstance24.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
            }
        } else if (n == 20586) {
            NpcInstance npcInstance25;
            if (questState.getQuestItemsCount(3728) > 0L && questState.getQuestItemsCount(3770) < 50L && Rnd.get((int)100) < 95) {
                questState.rollAndGive(3770, 1, 100.0);
                if (questState.getQuestItemsCount(3770) >= 50L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getQuestItemsCount(3696) > 0L && questState.getQuestItemsCount(3717) < 20L && Rnd.get((int)100) < 60) {
                questState.rollAndGive(3717, 1, 100.0);
                if (questState.getQuestItemsCount(3717) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getQuestItemsCount(3743) > 0L && questState.getQuestItemsCount(3785) < 1L && Rnd.get((int)10) < 2 && (npcInstance25 = questState.addSpawn(27158, 300000)) != null) {
                npcInstance25.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
            }
        } else if (n == 27159 && questState.getQuestItemsCount(3760) > 0L && questState.getQuestItemsCount(3802) < 1L) {
            questState.giveItems(3802, 1L);
            questState.playSound("ItemSound.quest_itemget");
        } else if (n == 27158 && questState.getQuestItemsCount(3743) > 0L && questState.getQuestItemsCount(3785) < 1L) {
            questState.giveItems(3785, 1L);
            questState.playSound("ItemSound.quest_itemget");
        } else if (n == 20591) {
            if (questState.getQuestItemsCount(3696) > 0L && questState.getQuestItemsCount(3719) < 30L && Rnd.get((int)100) < 85) {
                questState.rollAndGive(3719, 1, 100.0);
                if (questState.getQuestItemsCount(3719) >= 30L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20597) {
            if (questState.getQuestItemsCount(3696) > 0L && questState.getQuestItemsCount(3719) < 30L && Rnd.get((int)100) < 85) {
                questState.rollAndGive(3719, 1, 100.0);
                if (questState.getQuestItemsCount(3719) >= 30L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20560) {
            if (questState.getQuestItemsCount(3696) > 0L && questState.getQuestItemsCount(3718) < 20L && Rnd.get((int)100) < 60) {
                questState.rollAndGive(3718, 1, 100.0);
                if (questState.getQuestItemsCount(3718) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getQuestItemsCount(3749) > 0L && questState.getQuestItemsCount(3791) < 40L && Rnd.get((int)100) < 76) {
                questState.rollAndGive(3791, 1, 100.0);
                if (questState.getQuestItemsCount(3791) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20561) {
            if (questState.getQuestItemsCount(3696) > 0L && questState.getQuestItemsCount(3718) < 20L && Rnd.get((int)100) < 60) {
                questState.rollAndGive(3718, 1, 100.0);
                if (questState.getQuestItemsCount(3718) >= 20L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
            if (questState.getQuestItemsCount(3749) > 0L && questState.getQuestItemsCount(3791) < 40L && Rnd.get((int)100) < 85) {
                questState.rollAndGive(3791, 1, 100.0);
                if (questState.getQuestItemsCount(3791) >= 40L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 20593 && questState.getQuestItemsCount(3764) > 0L && questState.getQuestItemsCount(3806) < 20L && Rnd.get((int)100) < 78) {
            questState.rollAndGive(3806, 1, 100.0);
            if (questState.getQuestItemsCount(3806) >= 20L) {
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 20599 && questState.getQuestItemsCount(3764) > 0L && questState.getQuestItemsCount(3806) < 20L && Rnd.get((int)100) < 96) {
            questState.rollAndGive(3806, 1, 100.0);
            if (questState.getQuestItemsCount(3806) >= 20L) {
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 27163 && questState.getQuestItemsCount(3765) > 0L && questState.getQuestItemsCount(3807) < 1L) {
            questState.giveItems(3807, 1L);
        } else if (n == 20686 && questState.getQuestItemsCount(3765) > 0L && questState.getQuestItemsCount(3807) < 1L && Rnd.get((int)10) < 2) {
            NpcInstance npcInstance26 = questState.addSpawn(27163, 300000);
            if (npcInstance26 != null) {
                npcInstance26.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
            }
        } else if (n == 20682 && questState.getQuestItemsCount(3758) > 0L && questState.getQuestItemsCount(3800) < 30L && Rnd.get((int)100) < 80) {
            questState.rollAndGive(3800, 1, 100.0);
            if (questState.getQuestItemsCount(3800) >= 30L) {
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 20683 && questState.getQuestItemsCount(3758) > 0L && questState.getQuestItemsCount(3800) < 30L && Rnd.get((int)100) < 95) {
            questState.rollAndGive(3800, 1, 100.0);
            if (questState.getQuestItemsCount(3800) >= 30L) {
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 20684 && questState.getQuestItemsCount(3758) > 0L && questState.getQuestItemsCount(3800) < 30L) {
            questState.rollAndGive(3800, 1, 100.0);
            if (questState.getQuestItemsCount(3800) >= 30L) {
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 20553) {
            if (questState.getQuestItemsCount(3695) > 0L && questState.getQuestItemsCount(3714) < 1L && Rnd.get((int)10) < 2) {
                NpcInstance npcInstance27 = questState.addSpawn(27143, 300000);
                if (npcInstance27 != null) {
                    npcInstance27.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                }
            } else if (questState.getQuestItemsCount(3734) > 0L && questState.getQuestItemsCount(3776) < 30L && Rnd.get((int)100) < 63) {
                questState.rollAndGive(3776, 1, 100.0);
                if (questState.getQuestItemsCount(3776) >= 30L) {
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n == 27143 && questState.getQuestItemsCount(3695) > 0L && questState.getQuestItemsCount(3714) < 1L) {
            questState.giveItems(3714, 1L);
            if (questState.getQuestItemsCount(3714) >= 1L) {
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
