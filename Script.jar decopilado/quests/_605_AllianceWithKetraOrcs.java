/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import l2.gameserver.Config;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _605_AllianceWithKetraOrcs
extends Quest
implements ScriptFile {
    private static final int bnI = 7211;
    private static final int bnJ = 7212;
    private static final int bnK = 7213;
    private static final int bnL = 7214;
    private static final int bnM = 7215;
    private static final int bnN = 7216;
    private static final int bnO = 7217;
    private static final int bnP = 7218;
    private static final int bnQ = 7219;
    private static final int bnR = 7220;
    private static final int bnS = 21350;
    private static final int bnT = 21351;
    private static final int bnU = 21353;
    private static final int bnV = 21354;
    private static final int bnW = 21355;
    private static final int bnX = 21357;
    private static final int bnY = 21358;
    private static final int bnZ = 21360;
    private static final int boa = 21361;
    private static final int bob = 21362;
    private static final int boc = 21369;
    private static final int bod = 21370;
    private static final int boe = 21365;
    private static final int bof = 21366;
    private static final int bog = 21368;
    private static final int boh = 21373;
    private static final int boi = 21375;
    private static final int boj = 21374;
    private static final int bok = 21371;
    private static final int bol = 21372;
    private static final int bom = 31371;
    private static final int bon = 25306;
    private static final int boo = 25305;
    private static final int bop = 25302;
    private static final int boq = 21344;
    private static final int bor = 25299;
    private static final int bos = 21346;
    private static final int bot = 21345;
    private static final int bou = 21332;
    private static final int bov = 21336;
    private static final int bow = 21324;
    private static final int box = 21343;
    private static final int boy = 21334;
    private static final int boz = 21339;
    private static final int boA = 21342;
    private static final int boB = 21340;
    private static final int boC = 21328;
    private static final int boD = 21338;
    private static final int boE = 21329;
    private static final int boF = 21327;
    private static final int boG = 21331;
    private static final int boH = 21347;
    private static final int boI = 21325;
    private static final int boJ = 21349;
    private static final int boK = 21348;
    private static final Set<Integer> G = new HashSet<Integer>(Arrays.asList(25302, 25305, 25299, 25306));
    private static final Set<Integer> H = new HashSet<Integer>(Arrays.asList(21344, 21346, 21345, 21332, 21336, 21324, 21343, 21334, 21339, 21342, 21340, 21328, 21338, 21329, 21327, 21331, 21347, 21325, 21349, 21348));

    public _605_AllianceWithKetraOrcs() {
        super(1);
        this.addStartNpc(31371);
        this.addKillId(new int[]{21350});
        this.addKillId(new int[]{21351});
        this.addKillId(new int[]{21353});
        this.addKillId(new int[]{21354});
        this.addKillId(new int[]{21355});
        this.addKillId(new int[]{21357});
        this.addKillId(new int[]{21358});
        this.addKillId(new int[]{21360});
        this.addKillId(new int[]{21361});
        this.addKillId(new int[]{21362});
        this.addKillId(new int[]{21369});
        this.addKillId(new int[]{21370});
        this.addKillId(new int[]{21365});
        this.addKillId(new int[]{21366});
        this.addKillId(new int[]{21368});
        this.addKillId(new int[]{21373});
        this.addKillId(new int[]{21375});
        this.addKillId(new int[]{21374});
        this.addKillId(new int[]{21371});
        this.addKillId(new int[]{21372});
        this.addKillId(new int[]{25306, 25305, 25302, 21344, 25299, 21346, 21345, 21332, 21336, 21324, 21343, 21334, 21339, 21342, 21340, 21328, 21338, 21329, 21327, 21331, 21347, 21325, 21349, 21348});
        this.addQuestItem(new int[]{7216, 7217, 7218});
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            if (questState.getQuestItemsCount(7221) > 0L || questState.getQuestItemsCount(7222) > 0L || questState.getQuestItemsCount(7223) > 0L || questState.getQuestItemsCount(7224) > 0L || questState.getQuestItemsCount(7225) > 0L) {
                return "herald_wakan_q0605_02.htm";
            }
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            if (questState.getQuestItemsCount(7211) < 1L && questState.getQuestItemsCount(7212) < 1L && questState.getQuestItemsCount(7213) < 1L && questState.getQuestItemsCount(7214) < 1L && questState.getQuestItemsCount(7215) < 1L) {
                string2 = "herald_wakan_q0605_04.htm";
                questState.setCond(1);
            } else if (questState.getQuestItemsCount(7211) > 0L) {
                string2 = "herald_wakan_q0605_05.htm";
                questState.setCond(2);
            } else if (questState.getQuestItemsCount(7212) > 0L) {
                string2 = "herald_wakan_q0605_06.htm";
                questState.setCond(3);
            } else if (questState.getQuestItemsCount(7213) > 0L) {
                string2 = "herald_wakan_q0605_07.htm";
                questState.setCond(4);
            } else if (questState.getQuestItemsCount(7214) > 0L) {
                string2 = "herald_wakan_q0605_08.htm";
                questState.setCond(5);
            } else if (questState.getQuestItemsCount(7215) > 0L) {
                string2 = "herald_wakan_q0605_09.htm";
                questState.setCond(6);
            }
        } else if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(7211) < 1L && questState.getQuestItemsCount(7212) < 1L && questState.getQuestItemsCount(7213) < 1L && questState.getQuestItemsCount(7214) < 1L && questState.getQuestItemsCount(7215) < 1L && questState.getQuestItemsCount(7216) >= 100L) {
            string2 = "herald_wakan_q0605_12.htm";
            questState.giveItems(7211, 1L);
            questState.takeItems(7216, -1L);
            questState.setCond(2);
        } else if (string.equalsIgnoreCase("reply_2") && questState.getQuestItemsCount(7211) > 0L && questState.getQuestItemsCount(7216) >= 200L && questState.getQuestItemsCount(7217) >= 100L) {
            string2 = "herald_wakan_q0605_15.htm";
            questState.giveItems(7212, 1L);
            questState.takeItems(7211, -1L);
            questState.takeItems(7216, -1L);
            questState.takeItems(7217, -1L);
            questState.setCond(3);
        } else if (string.equalsIgnoreCase("reply_3") && questState.getQuestItemsCount(7216) >= 300L && questState.getQuestItemsCount(7217) >= 200L && questState.getQuestItemsCount(7218) >= 100L) {
            string2 = "herald_wakan_q0605_18.htm";
            questState.giveItems(7213, 1L);
            questState.takeItems(7212, -1L);
            questState.takeItems(7216, -1L);
            questState.takeItems(7217, -1L);
            questState.takeItems(7218, -1L);
            questState.setCond(4);
        } else if (string.equalsIgnoreCase("reply_4") && questState.getQuestItemsCount(7216) >= 300L && questState.getQuestItemsCount(7217) >= 300L && questState.getQuestItemsCount(7218) >= 200L && questState.getQuestItemsCount(7219) > 0L) {
            string2 = "herald_wakan_q0605_21.htm";
            questState.giveItems(7214, 1L);
            questState.takeItems(7213, -1L);
            questState.takeItems(7216, -1L);
            questState.takeItems(7217, -1L);
            questState.takeItems(7218, -1L);
            questState.takeItems(7219, -1L);
            questState.setCond(5);
        } else if (string.equalsIgnoreCase("reply_5")) {
            string2 = "herald_wakan_q0605_25.htm";
        } else if (string.equalsIgnoreCase("reply_6")) {
            string2 = "herald_wakan_q0605_26.htm";
            questState.takeItems(7215, -1L);
            questState.takeItems(7211, -1L);
            questState.takeItems(7212, -1L);
            questState.takeItems(7213, -1L);
            questState.takeItems(7214, -1L);
            questState.takeItems(7216, -1L);
            questState.takeItems(7217, -1L);
            questState.takeItems(7218, -1L);
            questState.takeItems(7219, -1L);
            questState.takeItems(7220, -1L);
            questState.exitCurrentQuest(true);
            questState.playSound("ItemSound.quest_finish");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getState();
        switch (n) {
            case 1: {
                if (questState.getPlayer().getLevel() >= 74) {
                    string = "herald_wakan_q0605_01.htm";
                    break;
                }
                string = "herald_wakan_q0605_03.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (questState.getQuestItemsCount(7211) < 1L && questState.getQuestItemsCount(7212) < 1L && questState.getQuestItemsCount(7213) < 1L && questState.getQuestItemsCount(7214) < 1L && questState.getQuestItemsCount(7215) < 1L && questState.getQuestItemsCount(7216) < 100L) {
                    string = "herald_wakan_q0605_10.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7211) < 1L && questState.getQuestItemsCount(7212) < 1L && questState.getQuestItemsCount(7213) < 1L && questState.getQuestItemsCount(7214) < 1L && questState.getQuestItemsCount(7215) < 1L && questState.getQuestItemsCount(7216) >= 100L) {
                    string = "herald_wakan_q0605_11.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7211) > 0L && (questState.getQuestItemsCount(7216) < 200L || questState.getQuestItemsCount(7217) < 100L)) {
                    string = "herald_wakan_q0605_13.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7211) > 0L && questState.getQuestItemsCount(7216) >= 200L && questState.getQuestItemsCount(7217) >= 100L) {
                    string = "herald_wakan_q0605_14.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7212) > 0L && (questState.getQuestItemsCount(7216) < 300L || questState.getQuestItemsCount(7217) < 200L || questState.getQuestItemsCount(7218) < 100L)) {
                    string = "herald_wakan_q0605_16.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7212) > 0L && questState.getQuestItemsCount(7216) >= 300L && questState.getQuestItemsCount(7217) >= 200L && questState.getQuestItemsCount(7218) >= 100L) {
                    string = "herald_wakan_q0605_17.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7213) > 0L && (questState.getQuestItemsCount(7216) < 300L || questState.getQuestItemsCount(7217) < 300L || questState.getQuestItemsCount(7218) < 200L || questState.getQuestItemsCount(7219) < 1L)) {
                    string = "herald_wakan_q0605_19.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7213) > 0L && questState.getQuestItemsCount(7216) >= 300L && questState.getQuestItemsCount(7217) >= 300L && questState.getQuestItemsCount(7218) >= 200L && questState.getQuestItemsCount(7219) > 0L) {
                    string = "herald_wakan_q0605_20.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7214) > 0L && (questState.getQuestItemsCount(7216) < 400L || questState.getQuestItemsCount(7217) < 400L || questState.getQuestItemsCount(7218) < 200L || questState.getQuestItemsCount(7220) < 1L)) {
                    string = "herald_wakan_q0605_22.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7214) > 0L && questState.getQuestItemsCount(7216) >= 400L && questState.getQuestItemsCount(7217) >= 400L && questState.getQuestItemsCount(7218) >= 200L && questState.getQuestItemsCount(7220) > 0L) {
                    string = "herald_wakan_q0605_23.htm";
                    questState.giveItems(7215, 1L);
                    questState.takeItems(7214, -1L);
                    questState.takeItems(7216, -1L);
                    questState.takeItems(7217, -1L);
                    questState.takeItems(7218, -1L);
                    questState.takeItems(7220, -1L);
                    questState.setCond(6);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (questState.getQuestItemsCount(7215) <= 0L) break;
                string = "herald_wakan_q0605_24.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if ((questState.getQuestItemsCount(7211) + questState.getQuestItemsCount(7212) + questState.getQuestItemsCount(7213) + questState.getQuestItemsCount(7214) + questState.getQuestItemsCount(7215) == 0L && questState.getQuestItemsCount(7216) < 100L || questState.getQuestItemsCount(7211) >= 1L && questState.getQuestItemsCount(7216) < 200L || questState.getQuestItemsCount(7212) >= 1L && questState.getQuestItemsCount(7216) < 300L || questState.getQuestItemsCount(7213) >= 1L && questState.getQuestItemsCount(7216) < 300L || questState.getQuestItemsCount(7214) >= 1L && questState.getQuestItemsCount(7216) < 400L) && (n == 21351 || n == 21350 || n == 21354 || n == 21353 || n == 21355)) {
            questState.rollAndGive(7216, 1, 50.0);
        } else if ((questState.getQuestItemsCount(7211) >= 1L && questState.getQuestItemsCount(7217) < 100L || questState.getQuestItemsCount(7212) >= 1L && questState.getQuestItemsCount(7217) < 200L || questState.getQuestItemsCount(7213) >= 1L && questState.getQuestItemsCount(7217) < 300L || questState.getQuestItemsCount(7214) >= 1L && questState.getQuestItemsCount(7217) < 400L) && (n == 21357 || n == 21358 || n == 21360 || n == 21361 || n == 21362 || n == 21369 || n == 21370)) {
            questState.rollAndGive(7217, 1, 51.0);
        } else if ((questState.getQuestItemsCount(7212) >= 1L && questState.getQuestItemsCount(7218) < 100L || questState.getQuestItemsCount(7213) >= 1L && questState.getQuestItemsCount(7218) < 200L || questState.getQuestItemsCount(7214) >= 1L && questState.getQuestItemsCount(7218) < 200L) && (n == 21365 || n == 21366 || n == 21368 || n == 21373 || n == 21375 || n == 21374 || n == 21371 || n == 21372 || n == 31371)) {
            questState.rollAndGive(7218, 1, 50.0);
        } else if (Config.ALT_ALLIANCE_KETRA_RAID_KILL_CHECK && _605_AllianceWithKetraOrcs.c(npcInstance) || Config.ALT_ALLIANCE_KETRA_MOBS_KILL_CHECK && _605_AllianceWithKetraOrcs.d(npcInstance)) {
            questState.takeItems(7215, -1L);
            questState.takeItems(7214, -1L);
            questState.takeItems(7213, -1L);
            questState.takeItems(7212, -1L);
            questState.takeItems(7211, -1L);
            questState.takeItems(7216, -1L);
            questState.takeItems(7217, -1L);
            questState.takeItems(7218, -1L);
            questState.exitCurrentQuest(true);
        }
        return null;
    }

    private static boolean c(NpcInstance npcInstance) {
        return G.contains(npcInstance.getNpcId());
    }

    private static boolean d(NpcInstance npcInstance) {
        return H.contains(npcInstance.getNpcId());
    }
}
