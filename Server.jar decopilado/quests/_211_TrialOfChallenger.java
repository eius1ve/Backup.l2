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

public class _211_TrialOfChallenger
extends Quest
implements ScriptFile {
    private static final int Xi = 30535;
    private static final int Xj = 30644;
    private static final int Xk = 30645;
    private static final int Xl = 30646;
    private static final int Xm = 30647;
    private static final int Xn = 27110;
    private static final int Xo = 27111;
    private static final int Xp = 27112;
    private static final int Xq = 27113;
    private static final int Xr = 27114;
    private static final int Xs = 2628;
    private static final int Xt = 2631;
    private static final int Xu = 2629;
    private static final int Xv = 2632;
    private static final int Xw = 2918;
    private static final int Xx = 2927;
    private static final int Xy = 1943;
    private static final int Xz = 1946;
    private static final int XA = 1940;
    private static final int XB = 2030;
    private static final int XC = 1904;
    private static final int XD = 1936;
    private static final int XE = 2627;
    private static final int XF = 2630;
    private static final int XG = 72394;
    private static final int XH = 11250;
    public NpcInstance Raldo_Spawn;

    private void b(QuestState questState) {
        if (this.Raldo_Spawn != null) {
            this.Raldo_Spawn.deleteMe();
        }
        this.Raldo_Spawn = this.addSpawn(30646, questState.getPlayer().getLoc(), 100, 300000);
    }

    public _211_TrialOfChallenger() {
        super(0);
        this.addStartNpc(30644);
        this.addTalkId(new int[]{30535});
        this.addTalkId(new int[]{30645});
        this.addTalkId(new int[]{30646});
        this.addTalkId(new int[]{30647});
        this.addKillId(new int[]{27110});
        this.addKillId(new int[]{27111});
        this.addKillId(new int[]{27112});
        this.addKillId(new int[]{27113});
        this.addKillId(new int[]{27114});
        this.addQuestItem(new int[]{2631, 2628, 2629, 2632, 2630});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("1")) {
            string2 = "kash_q0211_05.htm";
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("30644_1")) {
            string2 = "kash_q0211_04.htm";
        } else if (string.equalsIgnoreCase("30645_1")) {
            string2 = "martian_q0211_02.htm";
            questState.takeItems(2628, 1L);
            questState.setCond(4);
        } else if (string.equalsIgnoreCase("30647_1")) {
            if (questState.getQuestItemsCount(2632) > 0L) {
                questState.giveItems(2631, 1L);
                if (Rnd.chance((int)22)) {
                    string2 = "chest_of_shyslassys_q0211_03.htm";
                    questState.takeItems(2632, 1L);
                    questState.playSound("ItemSound.quest_jackpot");
                    int n = Rnd.get((int)100);
                    if (n > 90) {
                        questState.giveItems(2918, 1L);
                        questState.giveItems(2927, 1L);
                        questState.giveItems(1943, 1L);
                        questState.giveItems(1946, 1L);
                        questState.giveItems(1940, 1L);
                    } else if (n > 70) {
                        questState.giveItems(2030, 1L);
                        questState.giveItems(1904, 1L);
                    } else if (n > 40) {
                        questState.giveItems(1936, 1L);
                    } else {
                        questState.giveItems(1940, 1L);
                    }
                } else {
                    string2 = "chest_of_shyslassys_q0211_02.htm";
                    questState.takeItems(2632, -1L);
                    questState.giveItems(57, (long)(Rnd.get((int)1000) + 1));
                }
            } else {
                string2 = "chest_of_shyslassys_q0211_04.htm";
            }
        } else if (string.equalsIgnoreCase("30646_1")) {
            string2 = "raldo_q0211_02.htm";
        } else if (string.equalsIgnoreCase("30646_2")) {
            string2 = "raldo_q0211_03.htm";
        } else if (string.equalsIgnoreCase("30646_3")) {
            string2 = "raldo_q0211_04.htm";
            questState.setCond(8);
            questState.takeItems(2630, 1L);
        } else if (string.equalsIgnoreCase("30646_4")) {
            string2 = "raldo_q0211_06.htm";
            questState.setCond(8);
            questState.takeItems(2630, 1L);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        if (questState.getQuestItemsCount(2627) > 0L) {
            questState.exitCurrentQuest(true);
            return "completed";
        }
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        int n3 = questState.getCond();
        if (n2 == 1) {
            questState.setCond(0);
            if (n == 30644) {
                if (questState.getPlayer().getClassId().ordinal() == 1 || questState.getPlayer().getClassId().ordinal() == 19 || questState.getPlayer().getClassId().ordinal() == 32 || questState.getPlayer().getClassId().ordinal() == 45 || questState.getPlayer().getClassId().ordinal() == 47) {
                    if (questState.getPlayer().getLevel() >= 35) {
                        string = "kash_q0211_03.htm";
                    } else {
                        string = "kash_q0211_01.htm";
                        questState.exitCurrentQuest(true);
                    }
                } else {
                    string = "kash_q0211_02.htm";
                    questState.exitCurrentQuest(true);
                }
            }
        } else if (n == 30644 && n3 == 1) {
            string = "kash_q0211_06.htm";
        } else if (n == 30644 && n3 == 2 && questState.getQuestItemsCount(2631) == 1L) {
            string = "kash_q0211_07.htm";
            questState.takeItems(2631, 1L);
            questState.giveItems(2628, 1L);
            questState.setCond(3);
        } else if (n == 30644 && n3 == 1 && questState.getQuestItemsCount(2628) == 1L) {
            string = "kash_q0211_08.htm";
        } else if (n == 30644 && n3 >= 7) {
            string = "kash_q0211_09.htm";
        } else if (n == 30645 && n3 == 3 && questState.getQuestItemsCount(2628) == 1L) {
            string = "martian_q0211_01.htm";
        } else if (n == 30645 && n3 == 4 && questState.getQuestItemsCount(2629) == 0L) {
            string = "martian_q0211_03.htm";
        } else if (n == 30645 && n3 == 5 && questState.getQuestItemsCount(2629) > 0L) {
            string = "martian_q0211_04.htm";
            questState.takeItems(2629, 1L);
            questState.setCond(6);
        } else if (n == 30645 && n3 == 6) {
            string = "martian_q0211_05.htm";
        } else if (n == 30645 && n3 >= 7) {
            string = "martian_q0211_06.htm";
        } else if (n == 30647 && n3 == 2) {
            string = "chest_of_shyslassys_q0211_01.htm";
        } else if (n == 30646 && n3 == 7 && questState.getQuestItemsCount(2630) > 0L) {
            string = "raldo_q0211_01.htm";
        } else if (n == 30646 && n3 == 8) {
            string = "raldo_q0211_06a.htm";
        } else if (n == 30646 && n3 == 10) {
            string = "raldo_q0211_07.htm";
            questState.takeItems(2632, -1L);
            questState.giveItems(2627, 1L);
            if (!questState.getPlayer().getVarB("prof2.1")) {
                questState.giveItems(7562, 8L);
                questState.addExpAndSp(72394L, 11250L);
                questState.getPlayer().setVar("prof2.1", "1", -1L);
                this.giveExtraReward(questState.getPlayer());
            }
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(false);
        } else if (n == 30535 && n3 == 8) {
            if (questState.getPlayer().getLevel() >= 36) {
                string = "elder_filaur_q0211_01.htm";
                questState.addRadar(176560, -184969, -3729);
                questState.setCond(9);
            } else {
                string = "elder_filaur_q0211_03.htm";
            }
        } else if (n == 30535 && n3 == 9) {
            string = "elder_filaur_q0211_02.htm";
            questState.addRadar(176560, -184969, -3729);
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 27110 && n2 == 1 && questState.getQuestItemsCount(2631) == 0L && questState.getQuestItemsCount(2632) == 0L) {
            questState.giveItems(2632, 1L);
            questState.addSpawn(30647);
            questState.playSound("ItemSound.quest_middle");
            questState.setCond(2);
        } else if (n == 27112 && n2 == 4 && questState.getQuestItemsCount(2629) == 0L) {
            questState.giveItems(2629, 1L);
            questState.setCond(5);
            questState.playSound("ItemSound.quest_middle");
        } else if (n == 27113 && (n2 == 6 || n2 == 7)) {
            if (questState.getQuestItemsCount(2630) == 0L) {
                questState.giveItems(2630, 1L);
            }
            questState.playSound("ItemSound.quest_middle");
            questState.setCond(7);
            this.b(questState);
        } else if (n == 27114 && (n2 == 9 || n2 == 10)) {
            questState.setCond(10);
            questState.playSound("ItemSound.quest_middle");
            this.b(questState);
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
