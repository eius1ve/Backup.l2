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

public class _611_AllianceWithVarkaSilenos
extends Quest
implements ScriptFile {
    private static final int bqj = 7221;
    private static final int bqk = 7222;
    private static final int bql = 7223;
    private static final int bqm = 7224;
    private static final int bqn = 7225;
    private static final int bqo = 7226;
    private static final int bqp = 7227;
    private static final int bqq = 7228;
    private static final int bqr = 7229;
    private static final int bqs = 7230;
    private static final int bqt = 21327;
    private static final int bqu = 21324;
    private static final int bqv = 21328;
    private static final int bqw = 21325;
    private static final int bqx = 21329;
    private static final int bqy = 21338;
    private static final int bqz = 21331;
    private static final int bqA = 21332;
    private static final int bqB = 21335;
    private static final int bqC = 21334;
    private static final int bqD = 21343;
    private static final int bqE = 21344;
    private static final int bqF = 21336;
    private static final int bqG = 21340;
    private static final int bqH = 21339;
    private static final int bqI = 21342;
    private static final int bqJ = 21347;
    private static final int bqK = 21348;
    private static final int bqL = 21349;
    private static final int bqM = 21345;
    private static final int bqN = 21346;
    private static final int bqO = 31378;
    private static final int bqP = 21375;
    private static final int bqQ = 21374;
    private static final int bqR = 25315;
    private static final int bqS = 25312;
    private static final int bqT = 21370;
    private static final int bqU = 25309;
    private static final int bqV = 21372;
    private static final int bqW = 21371;
    private static final int bqX = 21365;
    private static final int bqY = 21351;
    private static final int bqZ = 21369;
    private static final int bra = 21350;
    private static final int brb = 21354;
    private static final int brc = 21361;
    private static final int brd = 21360;
    private static final int bre = 21366;
    private static final int brf = 21368;
    private static final int brg = 21357;
    private static final int brh = 21353;
    private static final int bri = 21364;
    private static final int brj = 21362;
    private static final int brk = 21355;
    private static final int brl = 21358;
    private static final int brm = 21373;
    private static final int brn = 25316;
    private static final Set<Integer> I = new HashSet<Integer>(Arrays.asList(25315, 25309, 25312, 25316));
    private static final Set<Integer> J = new HashSet<Integer>(Arrays.asList(21375, 21374, 21370, 21372, 21371, 21365, 21351, 21369, 21350, 21354, 21361, 21360, 21366, 21368, 21357, 21353, 21364, 21362, 21355, 21358, 21373));

    public _611_AllianceWithVarkaSilenos() {
        super(1);
        this.addStartNpc(31378);
        this.addKillId(new int[]{21327});
        this.addKillId(new int[]{21324});
        this.addKillId(new int[]{21328});
        this.addKillId(new int[]{21325});
        this.addKillId(new int[]{21329});
        this.addKillId(new int[]{21338});
        this.addKillId(new int[]{21331});
        this.addKillId(new int[]{21332});
        this.addKillId(new int[]{21335});
        this.addKillId(new int[]{21334});
        this.addKillId(new int[]{21343});
        this.addKillId(new int[]{21344});
        this.addKillId(new int[]{21336});
        this.addKillId(new int[]{21340});
        this.addKillId(new int[]{21339});
        this.addKillId(new int[]{21342});
        this.addKillId(new int[]{21347});
        this.addKillId(new int[]{21349});
        this.addKillId(new int[]{21348});
        this.addKillId(new int[]{21345});
        this.addKillId(new int[]{21346});
        this.addKillId(new int[]{21375, 21374, 25315, 25312, 21370, 25309, 21372, 21371, 21365, 21351, 21369, 21350, 21354, 21361, 21360, 21366, 21368, 21357, 21353, 21364, 21362, 21355, 21358, 21373, 25316});
        this.addQuestItem(new int[]{7226});
        this.addQuestItem(new int[]{7227});
        this.addQuestItem(new int[]{7228});
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
            if (questState.getQuestItemsCount(7211) > 0L || questState.getQuestItemsCount(7212) > 0L || questState.getQuestItemsCount(7213) > 0L || questState.getQuestItemsCount(7214) > 0L || questState.getQuestItemsCount(7215) > 0L) {
                return "herald_naran_q0611_02.htm";
            }
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            if (questState.getQuestItemsCount(7221) < 1L && questState.getQuestItemsCount(7222) < 1L && questState.getQuestItemsCount(7223) < 1L && questState.getQuestItemsCount(7224) < 1L && questState.getQuestItemsCount(7225) < 1L) {
                string2 = "herald_naran_q0611_04.htm";
                questState.setCond(1);
            } else if (questState.getQuestItemsCount(7221) > 0L) {
                string2 = "herald_naran_q0611_05.htm";
                questState.setCond(2);
            } else if (questState.getQuestItemsCount(7222) > 0L) {
                string2 = "herald_naran_q0611_06.htm";
                questState.setCond(3);
            } else if (questState.getQuestItemsCount(7223) > 0L) {
                string2 = "herald_naran_q0611_07.htm";
                questState.setCond(4);
            } else if (questState.getQuestItemsCount(7224) > 0L) {
                string2 = "herald_naran_q0611_08.htm";
                questState.setCond(5);
            } else if (questState.getQuestItemsCount(7225) > 0L) {
                string2 = "herald_naran_q0611_09.htm";
                questState.setCond(6);
            }
        } else if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(7221) < 1L && questState.getQuestItemsCount(7222) < 1L && questState.getQuestItemsCount(7223) < 1L && questState.getQuestItemsCount(7224) < 1L && questState.getQuestItemsCount(7225) < 1L && questState.getQuestItemsCount(7226) >= 100L) {
            string2 = "herald_naran_q0611_12.htm";
            questState.giveItems(7221, 1L);
            questState.takeItems(7226, -1L);
            questState.setCond(2);
        } else if (string.equalsIgnoreCase("reply_2") && questState.getQuestItemsCount(7221) > 0L && questState.getQuestItemsCount(7226) >= 200L && questState.getQuestItemsCount(7227) >= 100L) {
            string2 = "herald_naran_q0611_15.htm";
            questState.giveItems(7222, 1L);
            questState.takeItems(7221, -1L);
            questState.takeItems(7226, -1L);
            questState.takeItems(7227, -1L);
            questState.setCond(3);
        } else if (string.equalsIgnoreCase("reply_3") && questState.getQuestItemsCount(7226) >= 300L && questState.getQuestItemsCount(7227) >= 200L && questState.getQuestItemsCount(7228) >= 100L) {
            string2 = "herald_naran_q0611_18.htm";
            questState.giveItems(7223, 1L);
            questState.takeItems(7222, -1L);
            questState.takeItems(7226, -1L);
            questState.takeItems(7227, -1L);
            questState.takeItems(7228, -1L);
            questState.setCond(4);
        } else if (string.equalsIgnoreCase("reply_4") && questState.getQuestItemsCount(7226) >= 300L && questState.getQuestItemsCount(7227) >= 300L && questState.getQuestItemsCount(7228) >= 200L && questState.getQuestItemsCount(7229) > 0L) {
            string2 = "herald_naran_q0611_21.htm";
            questState.giveItems(7224, 1L);
            questState.takeItems(7222, -1L);
            questState.takeItems(7223, -1L);
            questState.takeItems(7226, -1L);
            questState.takeItems(7227, -1L);
            questState.takeItems(7228, -1L);
            questState.takeItems(7229, -1L);
            questState.setCond(5);
        } else if (string.equalsIgnoreCase("reply_5")) {
            string2 = "herald_naran_q0611_25.htm";
        } else if (string.equalsIgnoreCase("reply_6")) {
            string2 = "herald_naran_q0611_26.htm";
            questState.takeItems(7221, -1L);
            questState.takeItems(7222, -1L);
            questState.takeItems(7223, -1L);
            questState.takeItems(7224, -1L);
            questState.takeItems(7225, -1L);
            questState.takeItems(7226, -1L);
            questState.takeItems(7227, -1L);
            questState.takeItems(7228, -1L);
            questState.takeItems(7229, -1L);
            questState.takeItems(7230, -1L);
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
                    string = "herald_naran_q0611_01.htm";
                    break;
                }
                string = "herald_naran_q0611_03.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (questState.getQuestItemsCount(7221) < 1L && questState.getQuestItemsCount(7222) < 1L && questState.getQuestItemsCount(7223) < 1L && questState.getQuestItemsCount(7224) < 1L && questState.getQuestItemsCount(7225) < 1L && questState.getQuestItemsCount(7226) < 100L) {
                    string = "herald_naran_q0611_10.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7221) < 1L && questState.getQuestItemsCount(7222) < 1L && questState.getQuestItemsCount(7223) < 1L && questState.getQuestItemsCount(7224) < 1L && questState.getQuestItemsCount(7225) < 1L && questState.getQuestItemsCount(7226) >= 100L) {
                    string = "herald_naran_q0611_11.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7221) > 0L && (questState.getQuestItemsCount(7226) < 200L || questState.getQuestItemsCount(7227) < 100L)) {
                    string = "herald_naran_q0611_13.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7221) > 0L && questState.getQuestItemsCount(7226) >= 200L && questState.getQuestItemsCount(7227) >= 100L) {
                    string = "herald_naran_q0611_14.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7222) > 0L && (questState.getQuestItemsCount(7226) < 300L || questState.getQuestItemsCount(7227) < 200L || questState.getQuestItemsCount(7228) < 100L)) {
                    string = "herald_naran_q0611_16.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7222) > 0L && questState.getQuestItemsCount(7226) >= 300L && questState.getQuestItemsCount(7227) >= 200L && questState.getQuestItemsCount(7228) >= 100L) {
                    string = "herald_naran_q0611_17.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7223) > 0L && (questState.getQuestItemsCount(7226) < 300L || questState.getQuestItemsCount(7227) < 300L || questState.getQuestItemsCount(7228) < 200L || questState.getQuestItemsCount(7229) < 1L)) {
                    string = "herald_naran_q0611_19.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7223) > 0L && questState.getQuestItemsCount(7226) >= 300L && questState.getQuestItemsCount(7227) >= 300L && questState.getQuestItemsCount(7228) >= 200L && questState.getQuestItemsCount(7229) > 0L) {
                    string = "herald_naran_q0611_20.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7224) > 0L && (questState.getQuestItemsCount(7226) < 400L || questState.getQuestItemsCount(7227) < 400L || questState.getQuestItemsCount(7228) < 200L || questState.getQuestItemsCount(7230) < 1L)) {
                    string = "herald_naran_q0611_22.htm";
                    break;
                }
                if (questState.getQuestItemsCount(7224) > 0L && questState.getQuestItemsCount(7226) >= 400L && questState.getQuestItemsCount(7227) >= 400L && questState.getQuestItemsCount(7228) >= 200L && questState.getQuestItemsCount(7230) > 0L) {
                    string = "herald_naran_q0611_23.htm";
                    questState.giveItems(7225, 1L);
                    questState.takeItems(7224, -1L);
                    questState.takeItems(7226, -1L);
                    questState.takeItems(7227, -1L);
                    questState.takeItems(7228, -1L);
                    questState.takeItems(7230, -1L);
                    questState.setCond(6);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (questState.getQuestItemsCount(7225) <= 0L) break;
                string = "herald_naran_q0611_24.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if ((questState.getQuestItemsCount(7221) + questState.getQuestItemsCount(7222) + questState.getQuestItemsCount(7223) + questState.getQuestItemsCount(7224) + questState.getQuestItemsCount(7225) == 0L && questState.getQuestItemsCount(7226) < 100L || questState.getQuestItemsCount(7221) >= 1L && questState.getQuestItemsCount(7226) < 200L || questState.getQuestItemsCount(7222) >= 1L && questState.getQuestItemsCount(7226) < 300L || questState.getQuestItemsCount(7223) >= 1L && questState.getQuestItemsCount(7226) < 300L || questState.getQuestItemsCount(7224) >= 1L && questState.getQuestItemsCount(7226) < 400L) && (n == 21327 || n == 21324 || n == 21328 || n == 21325 || n == 21329)) {
            questState.rollAndGive(7226, 1, 50.9);
        } else if ((questState.getQuestItemsCount(7221) >= 1L && questState.getQuestItemsCount(7227) < 100L || questState.getQuestItemsCount(7222) >= 1L && questState.getQuestItemsCount(7227) < 200L || questState.getQuestItemsCount(7223) >= 1L && questState.getQuestItemsCount(7227) < 300L || questState.getQuestItemsCount(7224) >= 1L && questState.getQuestItemsCount(7227) < 400L) && (n == 21338 || n == 21331 || n == 21332 || n == 21335 || n == 21334 || n == 21343 || n == 21344 || n == 21336)) {
            questState.rollAndGive(7227, 1, 52.7);
        } else if ((questState.getQuestItemsCount(7222) >= 1L && questState.getQuestItemsCount(7228) < 100L || questState.getQuestItemsCount(7223) >= 1L && questState.getQuestItemsCount(7228) < 200L || questState.getQuestItemsCount(7224) >= 1L && questState.getQuestItemsCount(7228) < 200L) && (n == 21340 || n == 21339 || n == 21342 || n == 21347 || n == 21349 || n == 21348 || n == 21345 || n == 21346)) {
            questState.rollAndGive(7228, 1, 52.7);
        } else if (Config.ALT_ALLIANCE_VARKA_RAID_KILL_CHECK && _611_AllianceWithVarkaSilenos.e(npcInstance) || Config.ALT_ALLIANCE_VARKA_MOBS_KILL_CHECK && _611_AllianceWithVarkaSilenos.f(npcInstance)) {
            questState.takeItems(7225, -1L);
            questState.takeItems(7224, -1L);
            questState.takeItems(7223, -1L);
            questState.takeItems(7222, -1L);
            questState.takeItems(7221, -1L);
            questState.takeItems(7226, -1L);
            questState.takeItems(7227, -1L);
            questState.takeItems(7228, -1L);
            questState.exitCurrentQuest(true);
        }
        return null;
    }

    private static boolean e(NpcInstance npcInstance) {
        return I.contains(npcInstance.getNpcId());
    }

    private static boolean f(NpcInstance npcInstance) {
        return J.contains(npcInstance.getNpcId());
    }
}
