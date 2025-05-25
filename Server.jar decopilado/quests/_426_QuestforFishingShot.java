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

public class _426_QuestforFishingShot
extends Quest
implements ScriptFile {
    private static final int bfq = 31989;
    private static final int bfr = 31576;
    private static final int bfs = 31567;
    private static final int bft = 31569;
    private static final int bfu = 31697;
    private static final int bfv = 32007;
    private static final int bfw = 31578;
    private static final int bfx = 31571;
    private static final int bfy = 31579;
    private static final int bfz = 31562;
    private static final int bfA = 31570;
    private static final int bfB = 31577;
    private static final int bfC = 31575;
    private static final int bfD = 31564;
    private static final int bfE = 31573;
    private static final int bfF = 31572;
    private static final int bfG = 31565;
    private static final int bfH = 31568;
    private static final int bfI = 31563;
    private static final int bfJ = 31696;
    private static final int bfK = 31566;
    private static final int bfL = 31574;
    private static final int bfM = 20074;
    private static final int bfN = 20077;
    private static final int bfO = 20079;
    private static final int bfP = 20080;
    private static final int bfQ = 20081;
    private static final int bfR = 20084;
    private static final int bfS = 20087;
    private static final int bfT = 20088;
    private static final int bfU = 20819;
    private static final int bfV = 20812;
    private static final int bfW = 20456;
    private static final int bfX = 21024;
    private static final int bfY = 20386;
    private static final int bfZ = 22046;
    private static final int bga = 20772;
    private static final int bgb = 21058;
    private static final int bgc = 21060;
    private static final int bgd = 20983;
    private static final int bge = 20985;
    private static final int bgf = 20043;
    private static final int bgg = 20317;
    private static final int bgh = 20814;
    private static final int bgi = 20794;
    private static final int bgj = 20796;
    private static final int bgk = 20389;
    private static final int bgl = 20267;
    private static final int bgm = 20268;
    private static final int bgn = 20270;
    private static final int bgo = 20269;
    private static final int bgp = 20271;
    private static final int bgq = 22022;
    private static final int bgr = 21539;
    private static final int bgs = 21540;
    private static final int bgt = 21536;
    private static final int bgu = 21520;
    private static final int bgv = 21544;
    private static final int bgw = 21530;
    private static final int bgx = 21523;
    private static final int bgy = 21533;
    private static final int bgz = 21534;
    private static final int bgA = 21526;
    private static final int bgB = 21527;
    private static final int bgC = 21528;
    private static final int bgD = 21269;
    private static final int bgE = 21271;
    private static final int bgF = 20349;
    private static final int bgG = 21273;
    private static final int bgH = 20350;
    private static final int bgI = 21270;
    private static final int bgJ = 21272;
    private static final int bgK = 20552;
    private static final int bgL = 20135;
    private static final int bgM = 20804;
    private static final int bgN = 20806;
    private static final int bgO = 21106;
    private static final int bgP = 20790;
    private static final int bgQ = 22048;
    private static final int bgR = 20346;
    private static final int bgS = 20105;
    private static final int bgT = 20345;
    private static final int bgU = 21105;
    private static final int bgV = 21107;
    private static final int bgW = 21104;
    private static final int bgX = 20781;
    private static final int bgY = 21387;
    private static final int bgZ = 21655;
    private static final int bha = 21390;
    private static final int bhb = 21656;
    private static final int bhc = 21382;
    private static final int bhd = 21384;
    private static final int bhe = 21654;
    private static final int bhf = 20557;
    private static final int bhg = 20783;
    private static final int bhh = 21638;
    private static final int bhi = 20013;
    private static final int bhj = 20463;
    private static final int bhk = 20536;
    private static final int bhl = 20538;
    private static final int bhm = 20544;
    private static final int bhn = 21395;
    private static final int bho = 20539;
    private static final int bhp = 20537;
    private static final int bhq = 20978;
    private static final int bhr = 20979;
    private static final int bhs = 20800;
    private static final int bht = 20667;
    private static final int bhu = 20792;
    private static final int bhv = 20433;
    private static final int bhw = 20841;
    private static final int bhx = 21617;
    private static final int bhy = 21618;
    private static final int bhz = 21518;
    private static final int bhA = 20651;
    private static final int bhB = 20811;
    private static final int bhC = 20809;
    private static final int bhD = 20555;
    private static final int bhE = 21025;
    private static final int bhF = 20324;
    private static final int bhG = 20528;
    private static final int bhH = 20554;
    private static final int bhI = 20083;
    private static final int bhJ = 22002;
    private static final int bhK = 20368;
    private static final int bhL = 20659;
    private static final int bhM = 20525;
    private static final int bhN = 20333;
    private static final int bhO = 20859;
    private static final int bhP = 21068;
    private static final int bhQ = 20550;
    private static final int bhR = 20994;
    private static final int bhS = 20646;
    private static final int bhT = 20824;
    private static final int bhU = 21061;
    private static final int bhV = 20825;
    private static final int bhW = 20822;
    private static final int bhX = 20816;
    private static final int bhY = 20641;
    private static final int bhZ = 20644;
    private static final int bia = 20643;
    private static final int bib = 20663;
    private static final int bic = 20661;
    private static final int bid = 20308;
    private static final int bie = 20815;
    private static final int bif = 20241;
    private static final int big = 20286;
    private static final int bih = 20403;
    private static final int bii = 20005;
    private static final int bij = 20471;
    private static final int bik = 20470;
    private static final int bil = 21117;
    private static final int bim = 20476;
    private static final int bin = 20475;
    private static final int bio = 20650;
    private static final int bip = 20487;
    private static final int biq = 20030;
    private static final int bir = 20356;
    private static final int bis = 21100;
    private static final int bit = 21101;
    private static final int biu = 20357;
    private static final int biv = 20652;
    private static final int biw = 20070;
    private static final int bix = 20658;
    private static final int biy = 20657;
    private static final int biz = 20656;
    private static final int biA = 20655;
    private static final int biB = 20786;
    private static final int biC = 21644;
    private static final int biD = 20849;
    private static final int biE = 20044;
    private static final int biF = 21393;
    private static final int biG = 21657;
    private static final int biH = 21078;
    private static final int biI = 20363;
    private static final int biJ = 20834;
    private static final int biK = 20157;
    private static final int biL = 20234;
    private static final int biM = 20232;
    private static final int biN = 20230;
    private static final int biO = 22093;
    private static final int biP = 22081;
    private static final int biQ = 22097;
    private static final int biR = 22057;
    private static final int biS = 22051;
    private static final int biT = 20639;
    private static final int biU = 20636;
    private static final int biV = 20638;
    private static final int biW = 20225;
    private static final int biX = 20227;
    private static final int biY = 21516;
    private static final int biZ = 21517;
    private static final int bja = 21514;
    private static final int bjb = 20943;
    private static final int bjc = 20944;
    private static final int bjd = 20089;
    private static final int bje = 21125;
    private static final int bjf = 20808;
    private static final int bjg = 21103;
    private static final int bjh = 20575;
    private static final int bji = 20576;
    private static final int bjj = 20162;
    private static final int bjk = 21034;
    private static final int bjl = 21641;
    private static final int bjm = 20066;
    private static final int bjn = 20058;
    private static final int bjo = 20063;
    private static final int bjp = 20547;
    private static final int bjq = 21261;
    private static final int bjr = 21262;
    private static final int bjs = 21263;
    private static final int bjt = 21264;
    private static final int bju = 20131;
    private static final int bjv = 20648;
    private static final int bjw = 20836;
    private static final int bjx = 20845;
    private static final int bjy = 21629;
    private static final int bjz = 21630;
    private static final int bjA = 20511;
    private static final int bjB = 21102;
    private static final int bjC = 20829;
    private static final int bjD = 20828;
    private static final int bjE = 21072;
    private static final int bjF = 21066;
    private static final int bjG = 20050;
    private static final int bjH = 20106;
    private static final int bjI = 21081;
    private static final int bjJ = 21542;
    private static final int bjK = 21543;
    private static final int bjL = 20085;
    private static final int bjM = 20312;
    private static final int bjN = 20788;
    private static final int bjO = 20551;
    private static final int bjP = 20559;
    private static final int bjQ = 21378;
    private static final int bjR = 21652;
    private static final int bjS = 21376;
    private static final int bjT = 21071;
    private static final int bjU = 20810;
    private static final int bjV = 20404;
    private static final int bjW = 21026;
    private static final int bjX = 22020;
    private static final int bjY = 20100;
    private static final int bjZ = 21075;
    private static final int bka = 21023;
    private static final int bkb = 21508;
    private static final int bkc = 21511;
    private static final int bkd = 21512;
    private static final int bke = 21510;
    private static final int bkf = 20562;
    private static final int bkg = 20046;
    private static final int bkh = 20016;
    private static final int bki = 20047;
    private static final int bkj = 20630;
    private static final int bkk = 20634;
    private static final int bkl = 20665;
    private static final int bkm = 20632;
    private static final int bkn = 20837;
    private static final int bko = 20936;
    private static final int bkp = 20941;
    private static final int bkq = 20937;
    private static final int bkr = 20940;
    private static final int bks = 20939;
    private static final int bkt = 22089;
    private static final int bku = 20573;
    private static final int bkv = 20784;
    private static final int bkw = 21639;
    private static final int bkx = 21380;
    private static final int bky = 20802;
    private static final int bkz = 21318;
    private static final int bkA = 21322;
    private static final int bkB = 21314;
    private static final int bkC = 21316;
    private static final int bkD = 21320;
    private static final int bkE = 22008;
    private static final int bkF = 20991;
    private static final int bkG = 20560;
    private static final int bkH = 20341;
    private static final int bkI = 20115;
    private static final int bkJ = 20839;
    private static final int bkK = 21611;
    private static final int bkL = 21612;
    private static final int bkM = 20448;
    private static final int bkN = 20847;
    private static final int bkO = 21635;
    private static final int bkP = 21636;
    private static final int bkQ = 20017;
    private static final int bkR = 20798;
    private static final int bkS = 20132;
    private static final int bkT = 20078;
    private static final int bkU = 20553;
    private static final int bkV = 20120;
    private static final int bkW = 22025;
    private static final int bkX = 20176;
    private static final int bkY = 20833;
    private static final int bkZ = 21605;
    private static final int bla = 21606;
    private static final int blb = 21623;
    private static final int blc = 21623;
    private static final int bld = 21624;
    private static final int ble = 7586;

    public _426_QuestforFishingShot() {
        super(1);
        this.addStartNpc(new int[]{31989, 31576, 31567, 31569, 31697, 32007, 31578, 31571, 31579, 31562, 31570, 31577, 31575, 31564, 31573, 31572, 31565, 31568, 31563, 31696, 31566, 31574});
        this.addKillId(new int[]{20074, 20077, 20079, 20080, 20081, 20084, 20087, 20088, 20819, 20812, 20456, 21024, 20386, 22046, 20772, 21058, 21060, 20983, 20985, 20043, 20317, 20814, 20794, 20796, 20389, 20267, 20268, 20270, 20269, 20271, 22022, 21539, 21540, 21536, 21520, 21544, 21530, 21523, 21533, 21534, 21526, 21527, 21528, 21269, 21271, 20349, 21273, 20350, 21270, 21272, 20552, 20135, 20804, 20806, 21106, 20790, 22048, 20346, 20105, 20345, 21105, 21107, 21104, 20781, 21387, 21655, 21390, 21656, 21382, 21384, 21654, 20557, 20783, 21638, 20013, 20463, 20536, 20538, 20544, 21395, 20539, 20537, 20978, 20979, 20800, 20667, 20792, 20433, 20841, 21617, 21618, 21518, 20651, 20811, 20809, 20555, 21025, 20324, 20528, 20554, 20083, 22002, 20368, 20659, 20525, 20333, 20859, 21068, 20550, 20994, 20646, 20824, 21061, 20825, 20822, 20816, 20641, 20644, 20643, 20663, 20661, 20308, 20815, 20241, 20286, 20403, 20005, 20471, 20470, 21117, 20476, 20475, 20650, 20487, 20030, 20356, 21100, 21101, 20357, 20652, 20070, 20658, 20657, 20656, 20655, 20786, 21644, 20849, 20044, 21393, 21657, 21078, 20363, 20834, 20157, 20234, 20232, 20230, 22093, 22081, 22097, 22057, 22051, 20639, 20636, 20638, 20225, 20227, 21516, 21517, 21514, 20943, 20944, 20089, 21125, 20808, 21103, 20575, 20576, 20162, 21034, 21641, 20066, 20058, 20063, 20547, 21261, 21262, 21263, 21264, 20131, 20648, 20836, 20845, 21629, 21630, 20511, 21102, 20829, 20828, 21072, 21066, 20050, 20106, 21081, 21542, 21543, 20085, 20312, 20788, 20551, 20559, 21378, 21652, 21376, 21071, 20810, 20404, 21026, 22020, 20100, 21075, 21023, 21508, 21511, 21512, 21510, 20562, 20046, 20016, 20047, 20630, 20634, 20665, 20632, 20837, 20936, 20941, 20937, 20940, 20939, 22089, 20573, 20784, 21639, 21380, 20802, 21318, 21322, 21314, 21316, 21320, 22008, 20991, 20560, 20341, 20115, 20839, 21611, 21612, 20448, 20847, 21635, 21636, 20017, 20798, 20132, 20078, 20553, 20120, 22025, 20176, 20833, 21605, 21606, 21623, 21623, 21624});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            questState.setCond(1);
            string2 = "fisher_berix_q0426_03.htm";
        } else if (string.equalsIgnoreCase("reply_1")) {
            string2 = "fisher_berix_q0426_06.htm";
        } else if (string.equalsIgnoreCase("reply_2")) {
            string2 = "fisher_berix_q0426_07.htm";
        } else if (string.equalsIgnoreCase("reply_3")) {
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
            string2 = "fisher_berix_q0426_08.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        int n2 = questState.getState();
        if (n2 == 1) {
            string = "fisher_berix_q0426_01.htm";
        } else if (n == 1 && questState.getQuestItemsCount(7586) == 0L) {
            string = "fisher_berix_q0426_04.htm";
        } else if (n == 1 && questState.getQuestItemsCount(7586) >= 1L) {
            string = "fisher_berix_q0426_05.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (Rnd.chance((int)25)) {
            questState.rollAndGive(7586, 1, 100.0);
            questState.playSound("ItemSound.quest_itemget");
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
