/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _219_TestimonyOfFate
extends Quest
implements ScriptFile {
    private static final int adf = 30476;
    private static final int adg = 30614;
    private static final int adh = 30463;
    private static final int adi = 30613;
    private static final int adj = 30114;
    private static final int adk = 30210;
    private static final int adl = 30358;
    private static final int adm = 30419;
    private static final int adn = 31845;
    private static final int ado = 31850;
    private static final int adp = 3173;
    private static final int adq = 3174;
    private static final int adr = 3175;
    private static final int ads = 3176;
    private static final int adt = 3177;
    private static final int adu = 3178;
    private static final int adv = 3179;
    private static final int adw = 3180;
    private static final int adx = 3181;
    private static final int ady = 3182;
    private static final int adz = 3183;
    private static final int adA = 3184;
    private static final int adB = 3185;
    private static final int adC = 3186;
    private static final int adD = 3187;
    private static final int adE = 3189;
    private static final int adF = 3188;
    private static final int adG = 3190;
    private static final int adH = 3191;
    private static final int adI = 3192;
    private static final int adJ = 3193;
    private static final int adK = 3199;
    private static final int adL = 3194;
    private static final int adM = 3195;
    private static final int adN = 3196;
    private static final int adO = 3197;
    private static final int adP = 3200;
    private static final int adQ = 3198;
    private static final int adR = 3201;
    private static final int adS = 1246;
    private static final int adT = 3172;
    private static final int adU = 20144;
    private static final int adV = 20158;
    private static final int adW = 20233;
    private static final int adX = 20202;
    private static final int adY = 20192;
    private static final int adZ = 20193;
    private static final int aea = 20230;
    private static final int aeb = 20157;
    private static final int aec = 20232;
    private static final int aed = 20234;
    private static final int aee = 20554;
    private static final int aef = 20600;
    private static final int aeg = 20270;
    private static final int aeh = 20582;
    private static final int aei = 27079;
    private static final int[][] o = new int[][]{{6, 0, 20158, 3177, 3178, 10, 100, 1}, {6, 0, 20233, 3177, 3179, 10, 100, 1}, {6, 0, 20202, 3177, 3180, 10, 100, 1}, {6, 0, 20192, 3177, 3181, 10, 100, 1}, {6, 0, 20193, 3177, 3181, 10, 100, 1}, {6, 0, 20230, 3177, 3182, 10, 100, 1}, {6, 0, 20157, 3177, 3182, 10, 100, 1}, {6, 0, 20232, 3177, 3182, 10, 100, 1}, {6, 0, 20234, 3177, 3182, 10, 100, 1}, {17, 0, 20554, 3193, 3194, 10, 100, 1}, {17, 0, 20600, 3193, 3195, 10, 100, 1}, {17, 0, 20270, 3193, 3196, 10, 100, 1}, {17, 0, 20582, 3193, 3197, 10, 100, 1}, {17, 0, 27079, 3199, 3200, 10, 100, 1}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _219_TestimonyOfFate() {
        super(0);
        this.addStartNpc(30476);
        this.addTalkId(new int[]{30614});
        this.addTalkId(new int[]{30463});
        this.addTalkId(new int[]{30613});
        this.addTalkId(new int[]{30114});
        this.addTalkId(new int[]{30210});
        this.addTalkId(new int[]{30358});
        this.addTalkId(new int[]{30419});
        this.addTalkId(new int[]{31845});
        this.addTalkId(new int[]{31850});
        for (int i = 0; i < o.length; ++i) {
            this.addKillId(new int[]{o[i][2]});
        }
        this.addKillId(new int[]{20144});
        this.addQuestItem(new int[]{3173, 3174, 3175, 3177, 3183, 3184, 3185, 3186, 3187, 3189, 3188, 3191, 3190, 3192, 3193, 3199, 3198, 3201, 1246, 3178, 3179, 3180, 3181, 3182, 3194, 3195, 3196, 3197, 3200});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("30476-05.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            questState.giveItems(3173, 1L);
        } else if (string.equalsIgnoreCase("30114-04.htm")) {
            questState.takeItems(3185, 1L);
            questState.giveItems(3186, 1L);
            questState.setCond(12);
            questState.setState(2);
        } else if (string.equalsIgnoreCase("30476-12.htm")) {
            if (questState.getPlayer().getLevel() >= 38) {
                questState.takeItems(3187, -1L);
                questState.giveItems(3189, 1L);
                questState.setCond(15);
                questState.setState(2);
            } else {
                string2 = "30476-13.htm";
                questState.takeItems(3187, -1L);
                questState.giveItems(3188, 1L);
                questState.setCond(14);
                questState.setState(2);
            }
        } else if (string.equalsIgnoreCase("30419-02.htm")) {
            questState.takeItems(3191, -1L);
            questState.giveItems(3192, 1L);
            questState.setCond(17);
            questState.setState(2);
        } else if (string.equalsIgnoreCase("31845-02.htm")) {
            questState.giveItems(3193, 1L);
        } else if (string.equalsIgnoreCase("31850-02.htm")) {
            questState.giveItems(3199, 1L);
        } else if (string.equalsIgnoreCase("30419-05.htm")) {
            questState.takeItems(3192, -1L);
            questState.takeItems(3198, -1L);
            questState.takeItems(3201, -1L);
            questState.giveItems(1246, 1L);
            questState.setCond(18);
            questState.setState(2);
        }
        if (string.equalsIgnoreCase("AldersSpirit_Fail")) {
            NpcInstance npcInstance2 = GameObjectsStorage.getByNpcId((int)30613);
            if (npcInstance2 != null) {
                npcInstance2.deleteMe();
            }
            questState.setCond(9);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getCond();
        if (n == 30476) {
            if (questState.getQuestItemsCount(3172) != 0L) {
                string = "completed";
                questState.exitCurrentQuest(true);
            } else if (n2 == 0) {
                if (questState.getPlayer().getRace() == Race.darkelf && questState.getPlayer().getLevel() >= 37) {
                    string = "30476-03.htm";
                } else if (questState.getPlayer().getRace() == Race.darkelf) {
                    string = "30476-02.htm";
                    questState.exitCurrentQuest(true);
                } else {
                    string = "30476-01.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n2 == 2) {
                string = "30476-06.htm";
            } else if (n2 == 9 || n2 == 10) {
                NpcInstance npcInstance2 = GameObjectsStorage.getByNpcId((int)30613);
                if (npcInstance2 == null) {
                    questState.takeItems(3184, -1L);
                    if (questState.getQuestItemsCount(3185) == 0L) {
                        questState.giveItems(3185, 1L);
                    }
                    string = "30476-09.htm";
                    questState.setCond(10);
                    questState.setState(2);
                    questState.addSpawn(30613);
                    questState.startQuestTimer("AldersSpirit_Fail", 300000L);
                } else {
                    string = "<html><head><body>I am borrowed, approach in some minutes</body></html>";
                }
            } else if (n2 == 13) {
                string = "30476-11.htm";
            } else if (n2 == 14) {
                if (questState.getQuestItemsCount(3188) != 0L && questState.getPlayer().getLevel() < 38) {
                    string = "30476-14.htm";
                } else if (questState.getQuestItemsCount(3188) != 0L && questState.getPlayer().getLevel() >= 38) {
                    questState.giveItems(3189, 1L);
                    questState.takeItems(3188, 1L);
                    string = "30476-15.htm";
                    questState.setCond(15);
                    questState.setState(2);
                }
            } else if (n2 == 15) {
                string = "30476-16.htm";
            } else if (n2 == 16 || n2 == 17) {
                string = "30476-17.htm";
            } else if (questState.getQuestItemsCount(3174) > 0L || questState.getQuestItemsCount(3175) > 0L) {
                string = "30476-07.htm";
            } else if (questState.getQuestItemsCount(3176) > 0L || questState.getQuestItemsCount(3177) > 0L) {
                string = "30476-08.htm";
            } else if (questState.getQuestItemsCount(3185) > 0L || questState.getQuestItemsCount(3186) > 0L) {
                string = "30476-10.htm";
            }
        } else if (n == 30614) {
            if (n2 == 1) {
                string = "30614-01.htm";
                questState.takeItems(3173, -1L);
                questState.giveItems(3174, 1L);
                questState.setCond(2);
                questState.setState(2);
            } else if (n2 == 2) {
                string = "30614-02.htm";
            } else if (n2 == 3) {
                questState.takeItems(3175, -1L);
                questState.giveItems(3176, 1L);
                string = "30614-03.htm";
                questState.setCond(5);
                questState.setState(2);
            } else if (n2 == 8) {
                questState.takeItems(3183, -1L);
                questState.giveItems(3184, 1L);
                string = "30614-05.htm";
                questState.setCond(9);
                questState.setState(2);
            } else if (questState.getQuestItemsCount(3176) > 0L || questState.getQuestItemsCount(3177) > 0L) {
                string = "30614-04.htm";
            } else if (questState.getQuestItemsCount(3184) > 0L || questState.getQuestItemsCount(3185) > 0L || questState.getQuestItemsCount(3186) > 0L || questState.getQuestItemsCount(3187) > 0L || questState.getQuestItemsCount(3188) > 0L || questState.getQuestItemsCount(3189) > 0L) {
                string = "30614-06.htm";
            }
        } else if (n == 30463) {
            if (n2 == 5) {
                questState.takeItems(3176, -1L);
                questState.giveItems(3177, 1L);
                string = "30463-01.htm";
                questState.setCond(6);
                questState.setState(2);
            } else if (n2 == 6) {
                string = "30463-02.htm";
            } else if (n2 == 7 && questState.getQuestItemsCount(3178) >= 10L && questState.getQuestItemsCount(3179) >= 10L && questState.getQuestItemsCount(3180) >= 10L && questState.getQuestItemsCount(3181) >= 10L && questState.getQuestItemsCount(3182) >= 10L) {
                questState.takeItems(3178, -1L);
                questState.takeItems(3179, -1L);
                questState.takeItems(3180, -1L);
                questState.takeItems(3181, -1L);
                questState.takeItems(3182, -1L);
                questState.takeItems(3177, -1L);
                questState.giveItems(3183, 1L);
                string = "30463-03.htm";
                questState.setCond(8);
                questState.setState(2);
            } else if (n2 == 7) {
                string = "30463-02.htm";
                questState.setCond(6);
            } else if (n2 == 8) {
                string = "30463-04.htm";
            } else if (questState.getQuestItemsCount(3184) > 0L || questState.getQuestItemsCount(3185) > 0L || questState.getQuestItemsCount(3186) > 0L || questState.getQuestItemsCount(3187) > 0L || questState.getQuestItemsCount(3188) > 0L || questState.getQuestItemsCount(3189) > 0L) {
                string = "30463-05.htm";
            }
        } else if (n == 30613) {
            string = "30613-02.htm";
            questState.setCond(11);
            questState.setState(2);
            questState.cancelQuestTimer("AldersSpirit_Fail");
            NpcInstance npcInstance3 = GameObjectsStorage.getByNpcId((int)30613);
            if (npcInstance3 != null) {
                npcInstance3.deleteMe();
            }
        } else if (n == 30114) {
            if (n2 == 11) {
                string = "30114-01.htm";
            } else if (n2 == 12) {
                string = "30114-05.htm";
            } else if (questState.getQuestItemsCount(3187) > 0L || questState.getQuestItemsCount(3188) > 0L || questState.getQuestItemsCount(3189) > 0L) {
                string = "30114-06.htm";
            }
        } else if (n == 30210) {
            if (n2 == 12) {
                questState.takeItems(3186, -1L);
                questState.giveItems(3187, 1L);
                string = "30210-01.htm";
                questState.setCond(13);
                questState.setState(2);
            } else if (n2 == 13) {
                string = "30210-02.htm";
            }
        } else if (n == 30358) {
            if (n2 == 15) {
                questState.takeItems(3189, -1L);
                questState.giveItems(3191, 1L);
                questState.giveItems(3190, 1L);
                string = "30358-01.htm";
                questState.setCond(16);
                questState.setState(2);
            } else if (n2 == 16) {
                string = "30358-02.htm";
            } else if (n2 == 17) {
                string = "30358-03.htm";
            } else if (n2 == 18) {
                if (!questState.getPlayer().getVarB("prof2.2")) {
                    questState.giveItems(7562, 8L);
                    questState.addExpAndSp(68183L, 1750L);
                    questState.getPlayer().setVar("prof2.2", "1", -1L);
                    this.giveExtraReward(questState.getPlayer());
                }
                questState.takeItems(1246, -1L);
                questState.takeItems(3190, -1L);
                questState.giveItems(3172, 1L);
                string = "30358-04.htm";
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
            }
        } else if (n == 30419) {
            if (n2 == 16) {
                string = "30419-01.htm";
            } else if (n2 == 17) {
                if (questState.getQuestItemsCount(3198) < 1L || questState.getQuestItemsCount(3201) < 1L) {
                    string = "30419-03.htm";
                } else if (questState.getQuestItemsCount(3198) >= 1L && questState.getQuestItemsCount(3201) >= 1L) {
                    string = "30419-04.htm";
                }
            } else if (n2 == 18) {
                string = "30419-06.htm";
            }
        } else if (n == 31845 && n2 == 17) {
            if (questState.getQuestItemsCount(3198) == 0L && questState.getQuestItemsCount(3193) == 0L) {
                string = "31845-01.htm";
            } else if (questState.getQuestItemsCount(3198) == 0L && questState.getQuestItemsCount(3193) > 0L && (questState.getQuestItemsCount(3194) < 10L || questState.getQuestItemsCount(3195) < 10L || questState.getQuestItemsCount(3196) < 10L || questState.getQuestItemsCount(3197) < 10L)) {
                string = "31845-03.htm";
            } else if (questState.getQuestItemsCount(3198) == 0L && questState.getQuestItemsCount(3193) > 0L && questState.getQuestItemsCount(3194) >= 10L && questState.getQuestItemsCount(3195) >= 10L && questState.getQuestItemsCount(3196) >= 10L && questState.getQuestItemsCount(3197) >= 10L) {
                questState.takeItems(3194, -1L);
                questState.takeItems(3195, -1L);
                questState.takeItems(3196, -1L);
                questState.takeItems(3197, -1L);
                questState.takeItems(3193, -1L);
                questState.giveItems(3198, 1L);
                string = "31845-04.htm";
            } else if (questState.getQuestItemsCount(3198) != 0L) {
                string = "31845-05.htm";
            }
        } else if (n == 31850 && n2 == 17) {
            if (questState.getQuestItemsCount(3201) == 0L && questState.getQuestItemsCount(3199) == 0L) {
                string = "31850-01.htm";
            } else if (questState.getQuestItemsCount(3201) == 0L && questState.getQuestItemsCount(3199) > 0L && questState.getQuestItemsCount(3200) == 0L) {
                string = "31850-03.htm";
            } else if (questState.getQuestItemsCount(3201) == 0L && questState.getQuestItemsCount(3199) > 0L && questState.getQuestItemsCount(3200) > 0L) {
                questState.takeItems(3200, -1L);
                questState.takeItems(3199, -1L);
                questState.giveItems(3201, 1L);
                string = "31850-04.htm";
            } else if (questState.getQuestItemsCount(3201) > 0L) {
                string = "31850-05.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        for (int i = 0; i < o.length; ++i) {
            if (n2 != o[i][0] || n != o[i][2] || o[i][3] != 0 && questState.getQuestItemsCount(o[i][3]) <= 0L) continue;
            if (o[i][5] == 0) {
                questState.rollAndGive(o[i][4], o[i][7], (double)o[i][6]);
                continue;
            }
            if (!questState.rollAndGive(o[i][4], o[i][7], o[i][7], o[i][5], (double)o[i][6]) || o[i][1] == n2 || o[i][1] == 0) continue;
            questState.setCond(Integer.valueOf(o[i][1]).intValue());
            questState.setState(2);
        }
        if (n2 == 2 && n == 20144) {
            questState.takeItems(3174, -1L);
            questState.giveItems(3175, 1L);
            questState.playSound("ItemSound.quest_middle");
            questState.setCond(3);
            questState.setState(2);
        } else if (n2 == 6 && questState.getQuestItemsCount(3178) >= 10L && questState.getQuestItemsCount(3179) >= 10L && questState.getQuestItemsCount(3180) >= 10L && questState.getQuestItemsCount(3181) >= 10L && questState.getQuestItemsCount(3182) >= 10L) {
            questState.setCond(7);
            questState.setState(2);
        }
        return null;
    }
}
