/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _225_TestOfTheSearcher
extends Quest
implements ScriptFile {
    private static final int aiR = 30690;
    private static final int aiS = 30291;
    private static final int aiT = 30420;
    private static final int aiU = 30628;
    private static final int aiV = 30728;
    private static final int aiW = 30729;
    private static final int aiX = 30730;
    private static final int aiY = 30627;
    private static final int aiZ = 2784;
    private static final int aja = 2785;
    private static final int ajb = 2786;
    private static final int ajc = 2787;
    private static final int ajd = 2788;
    private static final int aje = 2789;
    private static final int ajf = 2808;
    private static final int ajg = 2792;
    private static final int ajh = 2790;
    private static final int aji = 2793;
    private static final int ajj = 2791;
    private static final int ajk = 2794;
    private static final int ajl = 2805;
    private static final int ajm = 2807;
    private static final int ajn = 2795;
    private static final int ajo = 2799;
    private static final int ajp = 2798;
    private static final int ajq = 2796;
    private static final int ajr = 2797;
    private static final int ajs = 2800;
    private static final int ajt = 2803;
    private static final int aju = 2804;
    private static final int ajv = 2806;
    private static final int ajw = 2801;
    private static final int ajx = 2802;
    private static final int ajy = 2809;
    private static final int ajz = 20781;
    private static final int ajA = 27094;
    private static final int ajB = 27093;
    private static final int ajC = 20555;
    private static final int ajD = 20551;
    private static final int ajE = 20144;
    private static final int[][] s = new int[][]{{3, 4, 20781, 0, 2787, 10, 100, 1}, {3, 4, 27094, 0, 2787, 10, 100, 1}, {10, 11, 20555, 0, 2797, 10, 100, 1}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _225_TestOfTheSearcher() {
        super(0);
        this.addStartNpc(30690);
        this.addTalkId(new int[]{30291});
        this.addTalkId(new int[]{30728});
        this.addTalkId(new int[]{30729});
        this.addTalkId(new int[]{30420});
        this.addTalkId(new int[]{30730});
        this.addTalkId(new int[]{30627});
        this.addTalkId(new int[]{30628});
        this.addKillId(new int[]{27093});
        this.addKillId(new int[]{20551});
        this.addKillId(new int[]{20144});
        for (int i = 0; i < s.length; ++i) {
            this.addKillId(new int[]{s[i][2]});
        }
        this.addQuestItem(new int[]{2787, 2797, 2784, 2785, 2786, 2788, 2790, 2789, 2791, 2792, 2793, 2794, 2795, 2796, 2799, 2798, 2800, 2801, 2802, 2803, 2804, 2806, 2805});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("30690-05.htm")) {
            questState.giveItems(2784, 1L);
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("30291-07.htm")) {
            questState.takeItems(2790, -1L);
            questState.takeItems(2791, -1L);
            questState.giveItems(2792, 1L);
            questState.giveItems(2793, 1L);
            questState.giveItems(2794, 1L);
            questState.setCond(8);
            questState.setState(2);
        } else if (string.equalsIgnoreCase("30420-01a.htm")) {
            questState.takeItems(2795, -1L);
            questState.giveItems(2796, 1L);
            questState.setCond(10);
            questState.setState(2);
        } else if (string.equalsIgnoreCase("30730-01d.htm")) {
            questState.takeItems(2799, -1L);
            questState.giveItems(2800, 1L);
            questState.setCond(14);
            questState.setState(2);
        } else if (string.equalsIgnoreCase("30627-01a.htm")) {
            NpcInstance npcInstance2 = GameObjectsStorage.getByNpcId((int)30628);
            if (npcInstance2 == null) {
                if (questState.getQuestItemsCount(2806) == 0L) {
                    questState.giveItems(2806, 1L);
                }
                questState.addSpawn(30628);
                questState.startQuestTimer("Chest", 300000L);
                questState.setCond(17);
                questState.setState(2);
            } else {
                if (!questState.isRunningQuestTimer("Wait1")) {
                    questState.startQuestTimer("Wait1", 300000L);
                }
                string2 = "<html><head><body>Please wait 5 minutes</body></html>";
            }
        } else if (string.equalsIgnoreCase("30628-01a.htm")) {
            questState.takeItems(2806, -1L);
            questState.giveItems(2807, 20L);
            questState.setCond(18);
        } else if (string.equalsIgnoreCase("Wait1") || string.equalsIgnoreCase("Chest")) {
            NpcInstance npcInstance3 = GameObjectsStorage.getByNpcId((int)30628);
            if (npcInstance3 != null) {
                npcInstance3.deleteMe();
            }
            questState.cancelQuestTimer("Wait1");
            questState.cancelQuestTimer("Chest");
            if (questState.getCond() == 17) {
                questState.setCond(16);
            }
            return null;
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getCond();
        if (n == 30690) {
            if (questState.getQuestItemsCount(2809) != 0L) {
                string = "completed";
                questState.exitCurrentQuest(true);
            } else if (n2 == 0) {
                if (questState.getPlayer().getClassId().getId() == 7 || questState.getPlayer().getClassId().getId() == 22 || questState.getPlayer().getClassId().getId() == 35 || questState.getPlayer().getClassId().getId() == 54) {
                    if (questState.getPlayer().getLevel() >= 39) {
                        string = questState.getPlayer().getClassId().getId() == 54 ? "30690-04.htm" : "30690-03.htm";
                    } else {
                        string = "30690-02.htm";
                        questState.exitCurrentQuest(true);
                    }
                } else {
                    string = "30690-01.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n2 == 1) {
                string = "30690-06.htm";
            } else if (n2 > 1 && n2 < 16) {
                string = "30623-17.htm";
            } else if (n2 == 19) {
                string = "30690-08.htm";
                if (!questState.getPlayer().getVarB("prof2.3")) {
                    questState.giveItems(7562, 8L);
                    questState.addExpAndSp(37831L, 18750L);
                    questState.getPlayer().setVar("prof2.3", "1", -1L);
                    this.giveExtraReward(questState.getPlayer());
                }
                questState.takeItems(2808, -1L);
                questState.giveItems(2809, 1L);
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
            }
        } else if (n == 30291) {
            if (n2 == 1) {
                string = "30291-01.htm";
                questState.takeItems(2784, -1L);
                questState.giveItems(2785, 1L);
                questState.setCond(2);
                questState.setState(2);
            } else if (n2 == 2) {
                string = "30291-02.htm";
            } else if (n2 > 2 && n2 < 7) {
                string = "30291-03.htm";
            } else if (n2 == 7) {
                string = "30291-04.htm";
            } else if (n2 == 8) {
                string = "30291-08.htm";
            } else if (n2 == 13 || n2 == 14) {
                string = "30291-09.htm";
            } else if (n2 == 18) {
                questState.takeItems(2794, -1L);
                questState.takeItems(2805, -1L);
                questState.takeItems(2807, -1L);
                questState.giveItems(2808, 1L);
                string = "30291-11.htm";
                questState.setCond(19);
                questState.setState(2);
            } else if (n2 == 19) {
                string = "30291-12.htm";
            }
        } else if (n == 30728) {
            if (n2 == 2) {
                string = "30728-01.htm";
                questState.takeItems(2785, -1L);
                questState.giveItems(2786, 1L);
                questState.setCond(3);
                questState.setState(2);
            } else if (n2 == 3) {
                string = "30728-02.htm";
            } else if (n2 == 4) {
                string = "30728-03.htm";
                questState.takeItems(2787, -1L);
                questState.takeItems(2786, -1L);
                questState.giveItems(2788, 1L);
                questState.setCond(5);
                questState.setState(2);
            } else if (n2 == 5) {
                string = "30728-04.htm";
            } else if (n2 == 6) {
                questState.takeItems(2789, -1L);
                questState.takeItems(2788, -1L);
                questState.giveItems(2790, 1L);
                string = "30728-05.htm";
                questState.setCond(7);
                questState.setState(2);
            } else if (n2 == 7) {
                string = "30728-06.htm";
            } else if (n2 == 8) {
                string = "30728-07.htm";
            }
        } else if (n == 30729) {
            if (n2 == 8) {
                questState.takeItems(2793, -1L);
                questState.giveItems(2795, 1L);
                string = "30729-01.htm";
                questState.setCond(9);
                questState.setState(2);
            } else if (n2 == 9) {
                string = "30729-02.htm";
            } else if (n2 == 12) {
                questState.takeItems(2795, -1L);
                questState.takeItems(2798, -1L);
                questState.giveItems(2799, 1L);
                string = "30729-03.htm";
                questState.setCond(13);
                questState.setState(2);
            } else if (n2 == 13) {
                string = "30729-04.htm";
            } else if (n2 >= 8 && n2 <= 14) {
                string = "30729-05.htm";
            }
        } else if (n == 30420) {
            if (n2 == 9) {
                string = "30420-01.htm";
            } else if (n2 == 10) {
                string = "30420-02.htm";
            } else if (n2 == 11) {
                questState.takeItems(2796, -1L);
                questState.takeItems(2797, -1L);
                questState.giveItems(2798, 1L);
                string = "30420-03.htm";
                questState.setCond(12);
                questState.setState(2);
            } else if (n2 == 12 || n2 == 13) {
                string = "30420-04.htm";
            }
        } else if (n == 30730) {
            if (n2 == 13) {
                string = "30730-01.htm";
            } else if (n2 == 14) {
                string = "30730-02.htm";
            } else if (n2 == 15) {
                questState.takeItems(2803, -1L);
                questState.takeItems(2804, -1L);
                questState.takeItems(2792, -1L);
                questState.takeItems(2800, -1L);
                questState.giveItems(2805, 1L);
                string = "30730-03.htm";
                questState.setCond(16);
                questState.setState(2);
            } else if (n2 == 16) {
                string = "30730-04.htm";
            }
        } else if (n == 30627) {
            if (n2 == 16 || n2 == 17) {
                string = "30627-01.htm";
            }
        } else if (n == 30628) {
            string = n2 == 17 ? "30628-01.htm" : "<html><head><body>You haven't got a Key for this Chest.</body></html>";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        for (int i = 0; i < s.length; ++i) {
            if (n2 != s[i][0] || n != s[i][2] || s[i][3] != 0 && questState.getQuestItemsCount(s[i][3]) <= 0L) continue;
            if (s[i][5] == 0) {
                questState.rollAndGive(s[i][4], s[i][7], (double)s[i][6]);
                continue;
            }
            if (!questState.rollAndGive(s[i][4], s[i][7], s[i][7], s[i][5], (double)s[i][6]) || s[i][1] == n2 || s[i][1] == 0) continue;
            questState.setCond(Integer.valueOf(s[i][1]).intValue());
            questState.setState(2);
        }
        if (n2 == 5 && n == 27093) {
            if (questState.getQuestItemsCount(2791) == 0L) {
                questState.giveItems(2791, 1L);
            }
            if (questState.getQuestItemsCount(2789) == 0L) {
                questState.giveItems(2789, 1L);
            }
            questState.playSound("ItemSound.quest_middle");
            questState.setCond(6);
            questState.setState(2);
        } else if (n2 == 14) {
            if (n == 20551 && questState.getQuestItemsCount(2803) == 0L) {
                questState.giveItems(2801, 1L);
                if (questState.getQuestItemsCount(2801) >= 4L) {
                    questState.takeItems(2801, -1L);
                    questState.giveItems(2803, 1L);
                }
            } else if (n == 20144 && questState.getQuestItemsCount(2804) == 0L) {
                questState.giveItems(2802, 1L);
                if (questState.getQuestItemsCount(2802) >= 4L) {
                    questState.takeItems(2802, -1L);
                    questState.giveItems(2804, 1L);
                }
            }
            if (questState.getQuestItemsCount(2803) != 0L && questState.getQuestItemsCount(2804) != 0L) {
                questState.setCond(15);
                questState.setState(2);
            }
        }
        return null;
    }
}
