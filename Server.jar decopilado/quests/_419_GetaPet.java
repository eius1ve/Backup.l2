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

public class _419_GetaPet
extends Quest
implements ScriptFile {
    private static final int bcR = 30731;
    private static final int bcS = 30256;
    private static final int bcT = 30091;
    private static final int bcU = 30072;
    private static final int bcV = 20103;
    private static final int bcW = 20106;
    private static final int bcX = 20108;
    private static final int bcY = 20460;
    private static final int bcZ = 20308;
    private static final int bda = 20466;
    private static final int bdb = 20025;
    private static final int bdc = 20105;
    private static final int bdd = 20034;
    private static final int bde = 20474;
    private static final int bdf = 20476;
    private static final int bdg = 20478;
    private static final int bdh = 20403;
    private static final int bdi = 20508;
    private static final int bdj = 22244;
    private static final int bdk = 50;
    private static final int bdl = 3417;
    private static final int bdm = 3418;
    private static final int bdn = 3419;
    private static final int bdo = 3420;
    private static final int bdp = 3421;
    private static final int bdq = 3422;
    private static final int bdr = 3423;
    private static final int bds = 3424;
    private static final int bdt = 3425;
    private static final int bdu = 3426;
    private static final int bdv = 3427;
    private static final int bdw = 2375;
    private static final int bdx = 25;
    private static final int bdy = 3;
    private static final int bdz = 4;
    private static final int bdA = 10;
    private static final int[][] U = new int[][]{{1, 0, 20103, 3418, 3423, 50, 100, 1}, {1, 0, 20106, 3418, 3423, 50, 100, 1}, {1, 0, 20108, 3418, 3423, 50, 100, 1}, {1, 0, 20460, 3419, 3424, 50, 100, 1}, {1, 0, 20308, 3419, 3424, 50, 100, 1}, {1, 0, 20466, 3419, 3424, 50, 100, 1}, {1, 0, 20025, 3420, 3425, 50, 100, 1}, {1, 0, 20105, 3420, 3425, 50, 100, 1}, {1, 0, 20034, 3420, 3425, 50, 100, 1}, {1, 0, 20474, 3421, 3426, 50, 100, 1}, {1, 0, 20476, 3421, 3426, 50, 100, 1}, {1, 0, 20478, 3421, 3426, 50, 100, 1}, {1, 0, 20403, 3422, 3427, 50, 100, 1}, {1, 0, 20508, 3422, 3427, 50, 100, 1}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _419_GetaPet() {
        super(0);
        this.addStartNpc(30731);
        this.addTalkId(new int[]{30731});
        this.addTalkId(new int[]{30256});
        this.addTalkId(new int[]{30091});
        this.addTalkId(new int[]{30072});
        this.addQuestItem(new int[]{3417});
        this.addQuestItem(new int[]{3419});
        this.addQuestItem(new int[]{3420});
        this.addQuestItem(new int[]{3421});
        this.addQuestItem(new int[]{3422});
        this.addQuestItem(new int[]{3423});
        this.addQuestItem(new int[]{3424});
        this.addQuestItem(new int[]{3425});
        this.addQuestItem(new int[]{3426});
        this.addQuestItem(new int[]{3427});
        for (int i = 0; i < U.length; ++i) {
            this.addKillId(new int[]{U[i][2]});
        }
    }

    public long getCount_proof(QuestState questState) {
        long l = 0L;
        switch (questState.getPlayer().getRace()) {
            case human: {
                l = questState.getQuestItemsCount(3423);
                break;
            }
            case elf: {
                l = questState.getQuestItemsCount(3424);
                break;
            }
            case darkelf: {
                l = questState.getQuestItemsCount(3425);
                break;
            }
            case orc: {
                l = questState.getQuestItemsCount(3426);
                break;
            }
            case dwarf: {
                l = questState.getQuestItemsCount(3427);
            }
        }
        return l;
    }

    public String check_questions(QuestState questState) {
        Object object = "";
        int n = questState.getInt("answers");
        int n2 = questState.getInt("question");
        if (n2 > 0) {
            object = "419_q" + String.valueOf(n2) + ".htm";
        } else if (n < 10) {
            String[] stringArray = questState.get("quiz").toString().split(" ");
            int n3 = Rnd.get((int)stringArray.length);
            String string = stringArray[n3];
            questState.set("question", string);
            Object object2 = "";
            if (n3 + 1 == stringArray.length) {
                for (int i = 0; i < stringArray.length - 2; ++i) {
                    object2 = (String)object2 + stringArray[i] + " ";
                }
                object2 = (String)object2 + stringArray[stringArray.length - 2];
            } else {
                for (int i = 0; i < stringArray.length - 1; ++i) {
                    if (i == n3) continue;
                    object2 = (String)object2 + stringArray[i] + " ";
                }
                object2 = (String)object2 + stringArray[stringArray.length - 1];
            }
            questState.set("quiz", (String)object2);
            object = "419_q" + string + ".htm";
        } else {
            questState.giveItems(2375, 1L);
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            object = "Completed.htm";
            questState.exitCurrentQuest(true);
        }
        return object;
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("id");
        if (string.equalsIgnoreCase("details")) {
            string2 = "419_confirm.htm";
        } else if (string.equalsIgnoreCase("agree")) {
            questState.setState(2);
            questState.setCond(1);
            switch (questState.getPlayer().getRace()) {
                case human: {
                    questState.giveItems(3418, 1L);
                    string2 = "419_slay_0.htm";
                    break;
                }
                case elf: {
                    questState.giveItems(3419, 1L);
                    string2 = "419_slay_1.htm";
                    break;
                }
                case darkelf: {
                    questState.giveItems(3420, 1L);
                    string2 = "419_slay_2.htm";
                    break;
                }
                case orc: {
                    questState.giveItems(3421, 1L);
                    string2 = "419_slay_3.htm";
                    break;
                }
                case dwarf: {
                    questState.giveItems(3422, 1L);
                    string2 = "419_slay_4.htm";
                }
            }
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("disagree")) {
            string2 = "419_cancelled.htm";
            questState.exitCurrentQuest(true);
        } else if (n == 1) {
            if (string.equalsIgnoreCase("talk")) {
                string2 = "419_talk.htm";
            } else if (string.equalsIgnoreCase("talk1")) {
                string2 = "419_bella_2.htm";
            } else if (string.equalsIgnoreCase("talk2")) {
                questState.set("progress", String.valueOf(questState.getInt("progress") | 1));
                string2 = "419_bella_3.htm";
            } else if (string.equalsIgnoreCase("talk3")) {
                questState.set("progress", String.valueOf(questState.getInt("progress") | 2));
                string2 = "419_ellie_2.htm";
            } else if (string.equalsIgnoreCase("talk4")) {
                questState.set("progress", String.valueOf(questState.getInt("progress") | 4));
                string2 = "419_metty_2.htm";
            }
        } else if (n == 2) {
            if (string.equalsIgnoreCase("tryme")) {
                string2 = this.check_questions(questState);
            } else if (string.equalsIgnoreCase("wrong")) {
                questState.set("id", "1");
                questState.set("progress", "0");
                questState.unset("quiz");
                questState.unset("answers");
                questState.unset("question");
                questState.giveItems(3417, 1L);
                string2 = "419_failed.htm";
            } else if (string.equalsIgnoreCase("right")) {
                questState.set("answers", String.valueOf(questState.getInt("answers") + 1));
                questState.set("question", "0");
                string2 = this.check_questions(questState);
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getInt("id");
        int n3 = questState.getCond();
        if (n3 == 0) {
            if (n == 30731) {
                if (questState.getPlayer().getLevel() < 15) {
                    string = "419_low_level.htm";
                    questState.exitCurrentQuest(true);
                } else {
                    string = "Start.htm";
                }
            }
        } else if (n3 == 1) {
            if (n == 30731) {
                if (n2 == 0) {
                    long l = this.getCount_proof(questState);
                    if (l == 0L) {
                        string = "419_no_slay.htm";
                    } else if (l < 50L) {
                        string = "419_pending_slay.htm";
                    } else {
                        switch (questState.getPlayer().getRace()) {
                            case human: {
                                questState.takeItems(3418, -1L);
                                questState.takeItems(3423, -1L);
                                break;
                            }
                            case elf: {
                                questState.takeItems(3419, -1L);
                                questState.takeItems(3424, -1L);
                                break;
                            }
                            case darkelf: {
                                questState.takeItems(3420, -1L);
                                questState.takeItems(3425, -1L);
                                break;
                            }
                            case orc: {
                                questState.takeItems(3421, -1L);
                                questState.takeItems(3426, -1L);
                                break;
                            }
                            case dwarf: {
                                questState.takeItems(3422, -1L);
                                questState.takeItems(3427, -1L);
                            }
                        }
                        questState.set("id", "1");
                        questState.giveItems(3417, 1L);
                        string = "Slayed.htm";
                    }
                } else if (n2 == 1) {
                    if (questState.getInt("progress") == 7) {
                        questState.takeItems(3417, -1L);
                        questState.set("quiz", "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
                        questState.set("answers", "0");
                        questState.set("id", "2");
                        string = "Talked.htm";
                    } else {
                        string = "419_pending_talk.htm";
                    }
                } else if (n2 == 2) {
                    string = "Talked.htm";
                }
            } else if (n2 == 1) {
                if (n == 30256) {
                    string = "419_bella_1.htm";
                } else if (n == 30091) {
                    string = "419_ellie_1.htm";
                } else if (n == 30072) {
                    string = "419_metty_1.htm";
                }
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        for (int i = 0; i < U.length; ++i) {
            if (n2 != U[i][0] || n != U[i][2] || U[i][3] != 0 && questState.getQuestItemsCount(U[i][3]) <= 0L) continue;
            if (U[i][5] == 0) {
                questState.rollAndGive(U[i][4], U[i][7], (double)U[i][6]);
                continue;
            }
            if (!questState.rollAndGive(U[i][4], U[i][7], U[i][7], U[i][5], (double)U[i][6]) || U[i][1] == n2 || U[i][1] == 0) continue;
            questState.setCond(Integer.valueOf(U[i][1]).intValue());
            questState.setState(2);
        }
        return null;
    }
}
