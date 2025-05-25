/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _228_TestOfMagus
extends Quest
implements ScriptFile {
    private static final int akW = 30629;
    private static final int akX = 30391;
    private static final int akY = 30612;
    private static final int akZ = 30411;
    private static final int ala = 30412;
    private static final int alb = 30413;
    private static final int alc = 30409;
    private static final int ald = 2841;
    private static final int ale = 2842;
    private static final int alf = 2843;
    private static final int alg = 2844;
    private static final int alh = 2845;
    private static final int ali = 2846;
    private static final int alj = 2847;
    private static final int alk = 2856;
    private static final int all = 2857;
    private static final int alm = 2858;
    private static final int aln = 2859;
    private static final int alo = 2862;
    private static final int alp = 2848;
    private static final int alq = 2860;
    private static final int alr = 2849;
    private static final int als = 2861;
    private static final int alt = 2850;
    private static final int alu = 2851;
    private static final int alv = 2852;
    private static final int alw = 2863;
    private static final int alx = 2853;
    private static final int aly = 2854;
    private static final int alz = 2855;
    private static final int alA = 2840;
    private static final int alB = 27095;
    private static final int alC = 27096;
    private static final int alD = 27097;
    private static final int alE = 20145;
    private static final int alF = 20176;
    private static final int alG = 20553;
    private static final int alH = 20564;
    private static final int alI = 20565;
    private static final int alJ = 20566;
    private static final int alK = 27098;
    private static final int alL = 20230;
    private static final int alM = 20231;
    private static final int alN = 20157;
    private static final int alO = 20232;
    private static final int alP = 20234;
    private static final int[][] u = new int[][]{{3, 0, 27095, 2843, 2844, 10, 100, 1}, {3, 0, 27096, 2843, 2845, 10, 100, 1}, {3, 0, 27097, 2843, 2846, 10, 100, 1}, {5, 0, 20145, 2861, 2850, 20, 50, 2}, {5, 0, 20176, 2861, 2851, 10, 50, 2}, {5, 0, 20553, 2861, 2852, 10, 50, 2}, {5, 0, 20564, 2863, 2853, 10, 100, 2}, {5, 0, 20565, 2863, 2854, 10, 100, 2}, {5, 0, 20566, 2863, 2855, 10, 100, 2}, {5, 0, 27098, 2860, 2849, 5, 50, 1}, {5, 0, 20230, 2862, 2848, 20, 30, 2}, {5, 0, 20231, 2862, 2848, 20, 30, 2}, {5, 0, 20157, 2862, 2848, 20, 30, 2}, {5, 0, 20232, 2862, 2848, 20, 40, 2}, {5, 0, 20234, 2862, 2848, 20, 50, 2}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _228_TestOfMagus() {
        super(0);
        this.addStartNpc(30629);
        this.addTalkId(new int[]{30391});
        this.addTalkId(new int[]{30612});
        this.addTalkId(new int[]{30412});
        this.addTalkId(new int[]{30409});
        this.addTalkId(new int[]{30413});
        this.addTalkId(new int[]{30411});
        for (int i = 0; i < u.length; ++i) {
            this.addKillId(new int[]{u[i][2]});
        }
        this.addQuestItem(new int[]{2841, 2842, 2843, 2858, 2861, 2863, 2859, 2862, 2857, 2860, 2856, 2847, 2844, 2845, 2846, 2850, 2851, 2852, 2853, 2854, 2855, 2849, 2848});
    }

    public void checkBooks(QuestState questState) {
        if (questState.getQuestItemsCount(2856) != 0L && questState.getQuestItemsCount(2857) != 0L && questState.getQuestItemsCount(2858) != 0L && questState.getQuestItemsCount(2859) != 0L) {
            questState.setCond(6);
            questState.setState(2);
        }
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("30629-04.htm")) {
            questState.giveItems(2841, 1L);
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("30391-02.htm")) {
            questState.takeItems(2841, -1L);
            questState.giveItems(2842, 1L);
            questState.setCond(2);
            questState.setState(2);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("30612-02.htm")) {
            questState.takeItems(2842, -1L);
            questState.giveItems(2843, 1L);
            questState.setCond(3);
            questState.setState(2);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("30629-10.htm")) {
            questState.takeItems(2843, -1L);
            questState.takeItems(2844, -1L);
            questState.takeItems(2845, -1L);
            questState.takeItems(2846, -1L);
            questState.giveItems(2847, 1L);
            questState.setCond(5);
            questState.setState(2);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("30412-02.htm")) {
            questState.giveItems(2861, 1L);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("30409-03.htm")) {
            questState.giveItems(2863, 1L);
            questState.playSound("ItemSound.quest_middle");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getCond();
        if (n == 30629) {
            if (questState.getQuestItemsCount(2840) != 0L) {
                string = "completed";
                questState.exitCurrentQuest(true);
            } else if (n2 == 0) {
                if (questState.getPlayer().getClassId().getId() == 11 || questState.getPlayer().getClassId().getId() == 26 || questState.getPlayer().getClassId().getId() == 39) {
                    if (questState.getPlayer().getLevel() >= 39) {
                        string = "30629-03.htm";
                    } else {
                        string = "30629-02.htm";
                        questState.exitCurrentQuest(true);
                    }
                } else {
                    string = "30629-01.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n2 == 1) {
                string = "30629-05.htm";
            } else if (n2 == 2) {
                string = "30629-06.htm";
            } else if (n2 == 3) {
                string = "30629-07.htm";
            } else if (n2 == 4) {
                string = "30629-08.htm";
            } else if (n2 == 5) {
                string = "30629-11.htm";
            } else if (n2 == 6) {
                questState.takeItems(2847, -1L);
                questState.takeItems(2856, -1L);
                questState.takeItems(2857, -1L);
                questState.takeItems(2858, -1L);
                questState.takeItems(2859, -1L);
                questState.giveItems(2840, 1L);
                string = "30629-12.htm";
                if (!questState.getPlayer().getVarB("prof2.3")) {
                    questState.giveItems(7562, 8L);
                    questState.addExpAndSp(164032L, 17500L);
                    questState.getPlayer().setVar("prof2.3", "1", -1L);
                    this.giveExtraReward(questState.getPlayer());
                }
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
            }
        } else if (n == 30391) {
            if (n2 == 1) {
                string = "30391-01.htm";
            } else if (n2 == 2) {
                string = "30391-03.htm";
            } else if (n2 == 3 || n2 == 4) {
                string = "30391-04.htm";
            } else if (n2 >= 5) {
                string = "30391-05.htm";
            }
        } else if (n == 30612) {
            if (n2 == 2) {
                string = "30612-01.htm";
            } else if (n2 == 3) {
                string = "30612-03.htm";
            } else if (n2 == 4) {
                string = "30612-04.htm";
            } else if (n2 >= 5) {
                string = "30612-05.htm";
            }
        } else if (n == 30411 && n2 == 5) {
            if (questState.getQuestItemsCount(2857) == 0L) {
                if (questState.getQuestItemsCount(2860) == 0L) {
                    string = "30411-01.htm";
                    questState.giveItems(2860, 1L);
                    questState.playSound("ItemSound.quest_middle");
                } else if (questState.getQuestItemsCount(2849) < 5L) {
                    string = "30411-02.htm";
                } else {
                    questState.takeItems(2860, -1L);
                    questState.takeItems(2849, -1L);
                    questState.giveItems(2857, 1L);
                    string = "30411-03.htm";
                    this.checkBooks(questState);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else {
                string = "30411-04.htm";
            }
        } else if (n == 30412 && n2 == 5) {
            if (questState.getQuestItemsCount(2858) == 0L) {
                if (questState.getQuestItemsCount(2861) == 0L) {
                    string = "30412-01.htm";
                } else if (questState.getQuestItemsCount(2850) < 20L || questState.getQuestItemsCount(2851) < 10L || questState.getQuestItemsCount(2852) < 10L) {
                    string = "30412-03.htm";
                } else {
                    questState.takeItems(2861, -1L);
                    questState.takeItems(2850, -1L);
                    questState.takeItems(2851, -1L);
                    questState.takeItems(2852, -1L);
                    questState.giveItems(2858, 1L);
                    string = "30412-04.htm";
                    this.checkBooks(questState);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else {
                string = "30412-05.htm";
            }
        } else if (n == 30409 && n2 == 5) {
            if (questState.getQuestItemsCount(2859) == 0L) {
                if (questState.getQuestItemsCount(2863) == 0L) {
                    string = "30409-01.htm";
                } else if (questState.getQuestItemsCount(2853) < 10L || questState.getQuestItemsCount(2854) < 10L || questState.getQuestItemsCount(2855) < 10L) {
                    string = "30409-04.htm";
                } else {
                    questState.takeItems(2863, -1L);
                    questState.takeItems(20564, -1L);
                    questState.takeItems(2854, -1L);
                    questState.takeItems(2855, -1L);
                    questState.giveItems(2859, 1L);
                    string = "30409-05.htm";
                    this.checkBooks(questState);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else {
                string = "30409-06.htm";
            }
        } else if (n == 30413 && n2 == 5) {
            if (questState.getQuestItemsCount(2856) == 0L) {
                if (questState.getQuestItemsCount(2862) == 0L) {
                    string = "30413-01.htm";
                    questState.giveItems(2862, 1L);
                    questState.playSound("ItemSound.quest_middle");
                } else if (questState.getQuestItemsCount(2848) < 20L) {
                    string = "30413-02.htm";
                } else {
                    questState.takeItems(2862, -1L);
                    questState.takeItems(2848, -1L);
                    questState.giveItems(2856, 1L);
                    string = "30413-03.htm";
                    this.checkBooks(questState);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else {
                string = "30413-04.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        for (int i = 0; i < u.length; ++i) {
            if (n2 != u[i][0] || n != u[i][2] || u[i][3] != 0 && questState.getQuestItemsCount(u[i][3]) <= 0L) continue;
            if (u[i][5] == 0) {
                questState.rollAndGive(u[i][4], u[i][7], (double)u[i][6]);
                continue;
            }
            if (!questState.rollAndGive(u[i][4], u[i][7], u[i][7], u[i][5], (double)u[i][6]) || u[i][1] == n2 || u[i][1] == 0) continue;
            questState.setCond(Integer.valueOf(u[i][1]).intValue());
            questState.setState(2);
        }
        if (n2 == 3 && questState.getQuestItemsCount(2844) != 0L && questState.getQuestItemsCount(2845) != 0L && questState.getQuestItemsCount(2846) != 0L) {
            questState.setCond(4);
            questState.setState(2);
        }
        return null;
    }
}
