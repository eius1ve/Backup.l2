/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _221_TestimonyOfProsperity
extends Quest
implements ScriptFile {
    private static final int afx = 30104;
    private static final int afy = 30466;
    private static final int afz = 30620;
    private static final int afA = 30597;
    private static final int afB = 30005;
    private static final int afC = 30368;
    private static final int afD = 30531;
    private static final int afE = 30532;
    private static final int afF = 30517;
    private static final int afG = 30533;
    private static final int afH = 30519;
    private static final int afI = 30553;
    private static final int Keef = 30534;
    private static final int afJ = 30555;
    private static final int afK = 30535;
    private static final int afL = 30554;
    private static final int Arin = 30536;
    private static final int afM = 30556;
    private static final int afN = 30621;
    private static final int afO = 30622;
    private static final int afP = 3239;
    private static final int afQ = 3264;
    private static final int afR = 3265;
    private static final int afS = 3266;
    private static final int afT = 3267;
    private static final int afU = 3243;
    private static final int afV = 3242;
    private static final int afW = 3428;
    private static final int afX = 3244;
    private static final int afY = 3246;
    private static final int afZ = 3247;
    private static final int aga = 3252;
    private static final int agb = 3258;
    private static final int agc = 3248;
    private static final int agd = 3253;
    private static final int age = 3255;
    private static final int agf = 3254;
    private static final int agg = 3259;
    private static final int agh = 3249;
    private static final int agi = 3263;
    private static final int agj = 3260;
    private static final int agk = 3250;
    private static final int agl = 3257;
    private static final int agm = 3261;
    private static final int agn = 3251;
    private static final int ago = 3256;
    private static final int agp = 3262;
    private static final int agq = 3241;
    private static final int agr = 3268;
    private static final int ags = 3269;
    private static final int agt = 3240;
    private static final int agu = 3270;
    private static final int agv = 3271;
    private static final int agw = 3272;
    private static final int agx = 3023;
    private static final int agy = 3245;
    private static final int agz = 3273;
    private static final int agA = 3274;
    private static final int agB = 3275;
    private static final int agC = 3030;
    private static final int agD = 3238;
    private static final int agE = 1867;
    private static final int agF = 20154;
    private static final int agG = 20155;
    private static final int agH = 20156;
    private static final int agI = 20223;
    private static final int agJ = 20228;
    private static final int agK = 20157;
    private static final int agL = 20230;
    private static final int agM = 20232;
    private static final int agN = 20234;
    private static final int agO = 20231;
    private static final int agP = 20233;
    private static final int[][] p = new int[][]{{1, 0, 20154, 3264, 3265, 20, 60, 1}, {1, 0, 20155, 3264, 3265, 20, 80, 1}, {1, 0, 20156, 3264, 3265, 20, 100, 1}, {1, 0, 20223, 3264, 3265, 20, 30, 1}, {1, 0, 20228, 3264, 3266, 10, 100, 1}, {7, 0, 20157, 0, 3273, 20, 100, 1}, {7, 0, 20230, 0, 3273, 20, 100, 1}, {7, 0, 20232, 0, 3273, 20, 100, 1}, {7, 0, 20234, 0, 3273, 20, 100, 1}, {7, 0, 20231, 0, 3274, 10, 100, 1}, {7, 0, 20233, 0, 3275, 10, 100, 1}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _221_TestimonyOfProsperity() {
        super(0);
        this.addStartNpc(30104);
        this.addTalkId(new int[]{30466});
        this.addTalkId(new int[]{30620});
        this.addTalkId(new int[]{30597});
        this.addTalkId(new int[]{30005});
        this.addTalkId(new int[]{30368});
        this.addTalkId(new int[]{30517});
        this.addTalkId(new int[]{30519});
        this.addTalkId(new int[]{30531});
        this.addTalkId(new int[]{30532});
        this.addTalkId(new int[]{30533});
        this.addTalkId(new int[]{30534});
        this.addTalkId(new int[]{30535});
        this.addTalkId(new int[]{30536});
        this.addTalkId(new int[]{30553});
        this.addTalkId(new int[]{30554});
        this.addTalkId(new int[]{30555});
        this.addTalkId(new int[]{30556});
        this.addTalkId(new int[]{30621});
        this.addTalkId(new int[]{30622});
        for (int i = 0; i < p.length; ++i) {
            this.addKillId(new int[]{p[i][2]});
        }
        this.addQuestItem(new int[]{3239, 3264, 3267, 3242, 3243, 3428, 3244, 3246, 3247, 3248, 3249, 3250, 3251, 3258, 3259, 3260, 3261, 3262, 3241, 3252, 3254, 3253, 3263, 3257, 3256, 3255, 3268, 3240, 3245, 3271, 3270, 3272, 3265, 3266, 3273, 3274, 3275});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("30104-04.htm")) {
            questState.playSound("ItemSound.quest_accept");
            questState.giveItems(3239, 1L);
            questState.setCond(1);
            questState.setState(2);
        } else if (string.equalsIgnoreCase("30466-03.htm")) {
            questState.giveItems(3264, 1L);
        } else if (string.equalsIgnoreCase("30620-03.htm")) {
            questState.takeItems(3267, -1L);
            questState.giveItems(3243, 1L);
            string2 = "30620-03.htm";
            if (questState.getQuestItemsCount(3241) > 0L && questState.getQuestItemsCount(3242) > 0L && questState.getQuestItemsCount(3243) > 0L && questState.getQuestItemsCount(3244) > 0L) {
                questState.setCond(2);
                questState.setState(2);
            }
        } else if (string.equalsIgnoreCase("30597-02.htm")) {
            questState.giveItems(3242, 1L);
            if (questState.getQuestItemsCount(3241) > 0L && questState.getQuestItemsCount(3242) > 0L && questState.getQuestItemsCount(3243) > 0L && questState.getQuestItemsCount(3244) > 0L) {
                questState.setCond(2);
                questState.setState(2);
            }
        } else if (string.equalsIgnoreCase("30005-04.htm")) {
            questState.giveItems(3428, 1L);
        } else if (string.equalsIgnoreCase("30368-03.htm")) {
            questState.takeItems(3428, -1L);
            questState.giveItems(3244, 1L);
            if (questState.getQuestItemsCount(3241) > 0L && questState.getQuestItemsCount(3242) > 0L && questState.getQuestItemsCount(3243) > 0L && questState.getQuestItemsCount(3244) > 0L) {
                questState.setCond(2);
                questState.setState(2);
            }
        } else if (string.equalsIgnoreCase("30531-03.htm")) {
            questState.giveItems(3246, 1L);
            questState.giveItems(3247, 1L);
            questState.giveItems(3248, 1L);
            questState.giveItems(3249, 1L);
            questState.giveItems(3250, 1L);
            questState.giveItems(3251, 1L);
        } else if (string.equalsIgnoreCase("30555-02.htm")) {
            questState.giveItems(3263, 1L);
        } else if (string.equalsIgnoreCase("30534-03a.htm") && questState.getQuestItemsCount(57) >= 5000L) {
            string2 = "30534-03b.htm";
            questState.takeItems(57, 5000L);
            questState.takeItems(3263, -1L);
            questState.giveItems(3260, 1L);
        } else if (string.equalsIgnoreCase("30104-07.htm")) {
            questState.takeItems(3239, -1L);
            questState.takeItems(3241, -1L);
            questState.takeItems(3242, -1L);
            questState.takeItems(3243, -1L);
            questState.takeItems(3244, -1L);
            if (questState.getPlayer().getLevel() < 38) {
                questState.giveItems(3268, 1L);
                questState.setCond(3);
                questState.setState(2);
            } else {
                questState.giveItems(3269, 1L);
                questState.giveItems(3240, 1L);
                string2 = "30104-08.htm";
                questState.setCond(4);
                questState.setState(2);
            }
        } else if (string.equalsIgnoreCase("30621-04.htm")) {
            questState.giveItems(3270, 1L);
            questState.setCond(5);
            questState.setState(2);
        } else if (string.equalsIgnoreCase("30622-02.htm")) {
            questState.takeItems(3270, -1L);
            questState.giveItems(3271, 1L);
            questState.setCond(6);
            questState.setState(2);
        } else if (string.equalsIgnoreCase("30622-04.htm")) {
            questState.takeItems(3272, -1L);
            questState.takeItems(3030, 1L);
            questState.giveItems(3245, 1L);
            questState.setCond(9);
            questState.setState(2);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getCond();
        if (n == 30104) {
            if (questState.getQuestItemsCount(3238) != 0L) {
                string = "completed";
                questState.exitCurrentQuest(true);
            } else if (n2 == 0) {
                if (questState.getPlayer().getRace() == Race.dwarf) {
                    if (questState.getPlayer().getLevel() >= 37) {
                        string = "30104-03.htm";
                    } else {
                        string = "30104-02.htm";
                        questState.exitCurrentQuest(true);
                    }
                } else {
                    string = "30104-01.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n2 == 1) {
                string = "30104-05.htm";
            } else if (n2 == 2) {
                if (questState.getQuestItemsCount(3241) > 0L && questState.getQuestItemsCount(3242) > 0L && questState.getQuestItemsCount(3243) > 0L && questState.getQuestItemsCount(3244) > 0L) {
                    string = "30104-06.htm";
                }
            } else if (n2 == 3 && questState.getQuestItemsCount(3268) > 0L) {
                if (questState.getPlayer().getLevel() < 38) {
                    string = "30104-09.htm";
                } else {
                    string = "30104-10.htm";
                    questState.takeItems(3268, -1L);
                    questState.giveItems(3240, 1L);
                    questState.giveItems(3269, 1L);
                    questState.setCond(4);
                    questState.setState(2);
                }
            } else if (n2 == 4 && questState.getQuestItemsCount(3240) > 0L && questState.getQuestItemsCount(3269) > 0L && questState.getQuestItemsCount(3245) == 0L) {
                string = "30104-11.htm";
            } else if (n2 >= 5 && n2 <= 7) {
                string = "30104-12.htm";
            } else if (n2 == 9) {
                if (!questState.getPlayer().getVarB("prof2.2")) {
                    questState.giveItems(7562, 8L);
                    questState.addExpAndSp(12969L, 1000L);
                    questState.getPlayer().setVar("prof2.2", "1", -1L);
                    this.giveExtraReward(questState.getPlayer());
                }
                questState.takeItems(3240, -1L);
                questState.takeItems(3245, -1L);
                questState.giveItems(3238, 1L);
                string = "30104-13.htm";
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
            }
        } else if (n == 30531) {
            if (questState.getQuestItemsCount(3246) == 0L) {
                string = "30531-01.htm";
            } else if (questState.getQuestItemsCount(3246) > 0L) {
                if (questState.getQuestItemsCount(3258) > 0L && questState.getQuestItemsCount(3259) > 0L && questState.getQuestItemsCount(3260) > 0L && questState.getQuestItemsCount(3261) > 0L && questState.getQuestItemsCount(3262) > 0L) {
                    string = "30531-05.htm";
                    questState.takeItems(3246, -1L);
                    questState.takeItems(3258, -1L);
                    questState.takeItems(3259, -1L);
                    questState.takeItems(3260, -1L);
                    questState.takeItems(3261, -1L);
                    questState.takeItems(3262, -1L);
                    questState.giveItems(3241, 1L);
                    if (questState.getQuestItemsCount(3241) > 0L && questState.getQuestItemsCount(3242) > 0L && questState.getQuestItemsCount(3243) > 0L && questState.getQuestItemsCount(3244) > 0L) {
                        questState.setCond(2);
                    }
                } else {
                    string = "30531-04.htm";
                }
            } else if (n2 >= 1 && questState.getQuestItemsCount(3239) > 0L && questState.getQuestItemsCount(3241) > 0L && questState.getQuestItemsCount(3246) == 0L) {
                string = "30531-06.htm";
            } else if (n2 >= 1 && questState.getQuestItemsCount(3240) > 0L) {
                string = "30531-07.htm";
            }
        } else if (n == 30532 && n2 == 1 && questState.getQuestItemsCount(3246) > 0L) {
            if (questState.getQuestItemsCount(3247) > 0L) {
                string = "30532-01.htm";
                questState.takeItems(3247, -1L);
            } else if (questState.getQuestItemsCount(3258) == 0L && questState.getQuestItemsCount(3252) == 0L) {
                string = "30532-02.htm";
            } else if (questState.getQuestItemsCount(3252) > 0L) {
                questState.takeItems(3252, -1L);
                questState.giveItems(3258, 1L);
                string = "30532-03.htm";
            } else if (questState.getQuestItemsCount(3258) > 0L) {
                string = "30532-04.htm";
            }
        } else if (n == 30517 && n2 == 1 && questState.getQuestItemsCount(3246) > 0L && questState.getQuestItemsCount(3247) == 0L && questState.getQuestItemsCount(3258) == 0L) {
            if (questState.getQuestItemsCount(3252) == 0L) {
                questState.giveItems(3252, 1L);
                string = "30517-01.htm";
            } else {
                string = "30517-02.htm";
            }
        } else if (n == 30533 && n2 == 1 && questState.getQuestItemsCount(3246) > 0L) {
            if (questState.getQuestItemsCount(3248) > 0L) {
                string = "30533-01.htm";
                questState.takeItems(3248, -1L);
            } else if (questState.getQuestItemsCount(3259) == 0L && (questState.getQuestItemsCount(3253) == 0L || questState.getQuestItemsCount(3254) == 0L)) {
                string = "30533-02.htm";
            } else if (questState.getQuestItemsCount(3253) != 0L && questState.getQuestItemsCount(3254) != 0L) {
                string = "30533-03.htm";
                questState.takeItems(3254, -1L);
                questState.takeItems(3253, -1L);
                questState.giveItems(3259, 1L);
            } else if (questState.getQuestItemsCount(3259) > 0L) {
                string = "30533-04.htm";
            }
        } else if (n == 30519 && n2 == 1 && questState.getQuestItemsCount(3246) > 0L && questState.getQuestItemsCount(3248) == 0L && questState.getQuestItemsCount(3259) == 0L) {
            if (questState.getQuestItemsCount(3253) == 0L) {
                string = "30519-01.htm";
                questState.giveItems(3253, 1L);
            } else {
                string = "30519-02.htm";
            }
        } else if (n == 30553 && n2 == 1 && questState.getQuestItemsCount(3246) > 0L && questState.getQuestItemsCount(3248) == 0L && questState.getQuestItemsCount(3259) == 0L) {
            if (questState.getQuestItemsCount(3255) == 0L && questState.getQuestItemsCount(3254) == 0L) {
                string = "30553-01.htm";
                questState.giveItems(3255, 1L);
            } else if (questState.getQuestItemsCount(3255) > 0L && questState.getQuestItemsCount(3254) == 0L) {
                if (questState.getQuestItemsCount(1867) < 100L) {
                    string = "30553-02.htm";
                } else {
                    string = "30553-03.htm";
                    questState.takeItems(1867, 100L);
                    questState.takeItems(3255, -1L);
                    questState.giveItems(3254, 1L);
                }
            } else if (questState.getQuestItemsCount(3254) > 0L) {
                string = "30553-04.htm";
            }
        } else if (n == 30534 && n2 == 1 && questState.getQuestItemsCount(3246) > 0L) {
            if (questState.getQuestItemsCount(3249) > 0L) {
                string = "30534-01.htm";
                questState.takeItems(3249, -1L);
            } else if (questState.getQuestItemsCount(3260) == 0L && questState.getQuestItemsCount(3263) == 0L) {
                string = "30534-02.htm";
            } else if (questState.getQuestItemsCount(3260) == 0L && questState.getQuestItemsCount(3263) > 0L) {
                string = "30534-03.htm";
            } else if (questState.getQuestItemsCount(3260) > 0L) {
                string = "30534-04.htm";
            }
        } else if (n == 30555 && n2 == 1 && questState.getQuestItemsCount(3246) > 0L && questState.getQuestItemsCount(3249) == 0L && questState.getQuestItemsCount(3260) == 0L) {
            if (questState.getQuestItemsCount(3263) == 0L) {
                string = "30555-01.htm";
            } else if (questState.getQuestItemsCount(3263) > 0L) {
                string = "30555-03.htm";
            }
        } else if (n == 30535 && n2 == 1 && questState.getQuestItemsCount(3246) > 0L) {
            if (questState.getQuestItemsCount(3250) > 0L) {
                string = "30535-01.htm";
                questState.takeItems(3250, -1L);
            } else if (questState.getQuestItemsCount(3261) == 0L && questState.getQuestItemsCount(3257) == 0L) {
                string = "30535-02.htm";
            } else if (questState.getQuestItemsCount(3257) > 0L && questState.getQuestItemsCount(3261) == 0L) {
                string = "30535-03.htm";
                questState.takeItems(3257, -1L);
                questState.giveItems(3261, 1L);
            } else if (questState.getQuestItemsCount(3246) > 0L && questState.getQuestItemsCount(3261) > 0L && questState.getQuestItemsCount(3257) == 0L && questState.getQuestItemsCount(3250) == 0L) {
                string = "30535-04.htm";
            }
        } else if (n == 30554 && n2 == 1 && questState.getQuestItemsCount(3246) > 0L && questState.getQuestItemsCount(3250) == 0L && questState.getQuestItemsCount(3261) == 0L) {
            if (questState.getQuestItemsCount(3257) == 0L) {
                string = "30554-01.htm";
                questState.giveItems(3257, 1L);
            } else {
                string = "30554-02.htm";
            }
        } else if (n == 30536 && n2 == 1 && questState.getQuestItemsCount(3246) > 0L) {
            if (questState.getQuestItemsCount(3251) > 0L) {
                string = "30536-01.htm";
                questState.takeItems(3251, -1L);
            } else if (questState.getQuestItemsCount(3262) == 0L && questState.getQuestItemsCount(3256) == 0L) {
                string = "30536-02.htm";
            } else if (questState.getQuestItemsCount(3262) == 0L && questState.getQuestItemsCount(3256) > 0L) {
                string = "30536-03.htm";
                questState.takeItems(3256, -1L);
                questState.giveItems(3262, 1L);
            } else {
                string = "30536-04.htm";
            }
        } else if (n == 30556 && n2 == 1 && questState.getQuestItemsCount(3246) > 0L && questState.getQuestItemsCount(3251) == 0L && questState.getQuestItemsCount(3262) == 0L) {
            if (questState.getQuestItemsCount(3256) == 0L) {
                string = "30556-01.htm";
                questState.giveItems(3256, 1L);
            } else {
                string = "30556-02.htm";
            }
        } else if (n == 30597) {
            if (n2 == 1) {
                string = questState.getQuestItemsCount(3242) == 0L ? "30597-01.htm" : "30597-03.htm";
            } else if (questState.getQuestItemsCount(3240) > 0L) {
                string = "30597-04.htm";
            }
        } else if (n == 30005) {
            if (n2 == 1) {
                if (questState.getQuestItemsCount(3244) == 0L && questState.getQuestItemsCount(3428) == 0L) {
                    string = "30005-01.htm";
                } else if (questState.getQuestItemsCount(3244) == 0L && questState.getQuestItemsCount(3428) > 0L) {
                    string = "30005-05.htm";
                } else if (questState.getQuestItemsCount(3244) > 0L) {
                    string = "30005-06.htm";
                }
            } else if (questState.getQuestItemsCount(3240) > 0L) {
                string = "30005-07.htm";
            }
        } else if (n == 30368) {
            if (n2 == 1) {
                if (questState.getQuestItemsCount(3428) > 0L && questState.getQuestItemsCount(3244) == 0L) {
                    string = "30368-01.htm";
                } else if (questState.getQuestItemsCount(3244) > 0L && questState.getQuestItemsCount(3428) == 0L) {
                    string = "30368-04.htm";
                }
            } else if (questState.getQuestItemsCount(3240) > 0L) {
                string = "30368-05.htm";
            }
        } else if (n == 30466) {
            if (n2 == 1) {
                if (questState.getQuestItemsCount(3264) == 0L && questState.getQuestItemsCount(3243) == 0L && questState.getQuestItemsCount(3267) == 0L) {
                    string = "30466-01.htm";
                } else if (questState.getQuestItemsCount(3265) < 20L || questState.getQuestItemsCount(3266) < 10L) {
                    string = "30466-04.htm";
                } else if (questState.getQuestItemsCount(3265) >= 20L || questState.getQuestItemsCount(3266) >= 10L) {
                    questState.takeItems(3264, -1L);
                    questState.takeItems(3265, -1L);
                    questState.takeItems(3266, -1L);
                    questState.giveItems(3267, 1L);
                    string = "30466-05.htm";
                } else if (questState.getQuestItemsCount(3267) > 0L && questState.getQuestItemsCount(3243) == 0L) {
                    string = "30466-06.htm";
                } else if (questState.getQuestItemsCount(3243) > 0L) {
                    string = "30466-07.htm";
                }
            } else if (questState.getQuestItemsCount(3240) > 0L) {
                string = "30466-08.htm";
            }
        } else if (n == 30620) {
            if (n2 == 1) {
                string = questState.getQuestItemsCount(3267) != 0L ? "30620-01.htm" : "30620-04.htm";
            } else if (questState.getQuestItemsCount(3240) > 0L) {
                string = "30620-05.htm";
            }
        } else if (n == 30621) {
            if (n2 == 4) {
                string = "30621-01.htm";
            } else if (n2 == 5) {
                string = "30621-05.htm";
            } else if (n2 == 6) {
                questState.takeItems(3271, -1L);
                questState.giveItems(3272, 1L);
                questState.giveItems(3023, 1L);
                string = "30621-06.htm";
                questState.setCond(7);
                questState.setState(2);
            } else if (n2 == 7) {
                string = "30621-07.htm";
            } else if (n2 == 8 && questState.getQuestItemsCount(3030) > 0L) {
                string = "30621-08.htm";
            } else if (n2 == 9) {
                string = "30621-09.htm";
            }
        } else if (n == 30622) {
            if (n2 == 5) {
                string = "30622-01.htm";
            } else if (n2 == 8 && questState.getQuestItemsCount(3030) > 0L) {
                string = "30622-03.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        for (int i = 0; i < p.length; ++i) {
            if (n2 != p[i][0] || n != p[i][2] || p[i][3] != 0 && questState.getQuestItemsCount(p[i][3]) <= 0L) continue;
            if (p[i][5] == 0) {
                questState.rollAndGive(p[i][4], p[i][7], (double)p[i][6]);
                continue;
            }
            if (!questState.rollAndGive(p[i][4], p[i][7], p[i][7], p[i][5], (double)p[i][6]) || p[i][1] == n2 || p[i][1] == 0) continue;
            questState.setCond(Integer.valueOf(p[i][1]).intValue());
            questState.setState(2);
        }
        if (n2 == 7 && questState.getQuestItemsCount(3273) >= 20L && questState.getQuestItemsCount(3274) >= 10L && questState.getQuestItemsCount(3275) >= 10L) {
            questState.setCond(8);
            questState.setState(2);
        }
        return null;
    }
}
