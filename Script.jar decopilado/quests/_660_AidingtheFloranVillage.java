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

public class _660_AidingtheFloranVillage
extends Quest
implements ScriptFile {
    private static final int bCE = 30608;
    private static final int bCF = 30291;
    private static final int bCG = 20781;
    private static final int bCH = 21102;
    private static final int bCI = 21103;
    private static final int bCJ = 21104;
    private static final int bCK = 21105;
    private static final int bCL = 21106;
    private static final int bCM = 21107;
    private static final int bCN = 8074;
    private static final int bCO = 8075;
    private static final int bCP = 8076;
    private static final int bCQ = 956;
    private static final int bCR = 955;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _660_AidingtheFloranVillage() {
        super(0);
        this.addStartNpc(30608);
        this.addTalkId(new int[]{30291});
        this.addKillId(new int[]{20781, 21102, 21103, 21104, 21105, 21106, 21107});
        this.addQuestItem(new int[]{8074, 8075, 8076});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30608) {
            if (string.equalsIgnoreCase("quest_accept")) {
                if (questState.getPlayer().getLevel() >= 30) {
                    questState.setCond(1);
                    questState.set("support_ploran_town", String.valueOf(1), true);
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                    string2 = "marya_q0660_06.htm";
                } else {
                    string2 = "marya_q0660_06a.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=660&reply=20")) {
                string2 = "marya_q0660_02.htm";
            }
        } else if (n == 30291) {
            if (string.equalsIgnoreCase("menu_select?ask=660&reply=1")) {
                long l;
                long l2;
                long l3 = questState.getQuestItemsCount(8074);
                long l4 = l3 + (l2 = questState.getQuestItemsCount(8075)) + (l = questState.getQuestItemsCount(8076));
                if (l4 > 0L) {
                    questState.giveItems(57, l4 * 100L);
                    questState.takeItems(8074, -1L);
                    questState.takeItems(8075, -1L);
                    questState.takeItems(8076, -1L);
                    string2 = "alankell_q0660_06.htm";
                } else {
                    string2 = "alankell_q0660_08.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=660&reply=2")) {
                string2 = "alankell_q0660_09.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=660&reply=3")) {
                questState.takeItems(8074, -1L);
                questState.takeItems(8075, -1L);
                questState.takeItems(8076, -1L);
                questState.unset("support_ploran_town");
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
                string2 = "alankell_q0660_08a.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=660&reply=10")) {
                long l;
                long l5;
                long l6 = questState.getQuestItemsCount(8074);
                long l7 = l6 + (l5 = questState.getQuestItemsCount(8075)) + (l = questState.getQuestItemsCount(8076));
                if (l7 < 100L) {
                    string2 = "alankell_q0660_11.htm";
                } else {
                    long l8 = 100L;
                    if (l6 < l8) {
                        questState.takeItems(8074, l6);
                        l8 -= l6;
                    } else {
                        questState.takeItems(8074, l8);
                        l8 = 0L;
                    }
                    if (l5 < l8) {
                        questState.takeItems(8075, l5);
                        l8 -= l5;
                    } else {
                        questState.takeItems(8075, l8);
                        l8 = 0L;
                    }
                    if (l < l8) {
                        questState.takeItems(8076, l);
                        l8 -= l;
                    } else {
                        questState.takeItems(8076, l8);
                        l8 = 0L;
                    }
                    if (Rnd.get((int)99) > 50) {
                        questState.giveItems(956, 1L);
                        questState.giveItems(57, 13000L);
                        string2 = "alankell_q0660_12.htm";
                        this.giveExtraReward(questState.getPlayer());
                    } else {
                        questState.giveItems(57, 1000L);
                        string2 = "alankell_q0660_13.htm";
                        this.giveExtraReward(questState.getPlayer());
                    }
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=660&reply=14")) {
                long l;
                long l9;
                long l10 = questState.getQuestItemsCount(8074);
                long l11 = l10 + (l9 = questState.getQuestItemsCount(8075)) + (l = questState.getQuestItemsCount(8076));
                if (l11 < 200L) {
                    string2 = "alankell_q0660_15.htm";
                } else {
                    long l12 = 200L;
                    if (l10 < l12) {
                        questState.takeItems(8074, l10);
                        l12 -= l10;
                    } else {
                        questState.takeItems(8074, l12);
                        l12 = 0L;
                    }
                    if (l9 < l12) {
                        questState.takeItems(8075, l9);
                        l12 -= l9;
                    } else {
                        questState.takeItems(8075, l12);
                        l12 = 0L;
                    }
                    if (l < l12) {
                        questState.takeItems(8076, l);
                        l12 -= l;
                    } else {
                        questState.takeItems(8076, l12);
                        l12 = 0L;
                    }
                    if (Rnd.get((int)100) >= 50) {
                        if (Rnd.get((int)2) == 0) {
                            questState.giveItems(956, 1L);
                            questState.giveItems(57, 20000L);
                        } else {
                            questState.giveItems(955, 1L);
                        }
                        this.giveExtraReward(questState.getPlayer());
                        string2 = "alankell_q0660_16.htm";
                    } else {
                        questState.giveItems(57, 2000L);
                        this.giveExtraReward(questState.getPlayer());
                        string2 = "alankell_q0660_17.htm";
                    }
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=660&reply=18")) {
                long l;
                long l13;
                long l14 = questState.getQuestItemsCount(8074);
                long l15 = l14 + (l13 = questState.getQuestItemsCount(8075)) + (l = questState.getQuestItemsCount(8076));
                if (l15 < 500L) {
                    string2 = "alankell_q0660_19.htm";
                } else {
                    long l16 = 500L;
                    if (l14 < l16) {
                        questState.takeItems(8074, l14);
                        l16 -= l14;
                    } else {
                        questState.takeItems(8074, l16);
                        l16 = 0L;
                    }
                    if (l13 < l16) {
                        questState.takeItems(8075, l13);
                        l16 -= l13;
                    } else {
                        questState.takeItems(8075, l16);
                        l16 = 0L;
                    }
                    if (l < l16) {
                        questState.takeItems(8076, l);
                        l16 -= l;
                    } else {
                        questState.takeItems(8076, l16);
                        l16 = 0L;
                    }
                    if (Rnd.get((int)100) >= 50) {
                        questState.giveItems(955, 1L);
                        questState.giveItems(57, 45000L);
                        this.giveExtraReward(questState.getPlayer());
                        string2 = "alankell_q0660_20.htm";
                    } else {
                        this.giveExtraReward(questState.getPlayer());
                        questState.giveItems(57, 5000L);
                        string2 = "alankell_q0660_21.htm";
                    }
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=660&reply=20")) {
                long l;
                long l17;
                long l18 = questState.getQuestItemsCount(8074);
                long l19 = l18 + (l17 = questState.getQuestItemsCount(8075)) + (l = questState.getQuestItemsCount(8076));
                if (l19 <= 0L) {
                    string2 = "alankell_q0660_23.htm";
                } else {
                    long l20 = l19 * 100L;
                    questState.giveItems(57, l20);
                    string2 = "alankell_q0660_22.htm";
                    this.giveExtraReward(questState.getPlayer());
                }
                questState.takeItems(8074, -1L);
                questState.takeItems(8075, -1L);
                questState.takeItems(8076, -1L);
                questState.unset("support_ploran_town");
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("support_ploran_town");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30608) break;
                if (questState.getPlayer().getLevel() < 30) {
                    string = "marya_q0660_04.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "marya_q0660_01.htm";
                break;
            }
            case 2: {
                if (n2 == 30608) {
                    if (n != 1) break;
                    string = "marya_q0660_05.htm";
                    break;
                }
                if (n2 != 30291) break;
                if (n == 1) {
                    questState.setCond(2);
                    questState.set("support_ploran_town", String.valueOf(2), true);
                    questState.playSound("ItemSound.quest_middle");
                    string = "alankell_q0660_04.htm";
                    break;
                }
                if (n != 2) break;
                string = "alankell_q0660_05.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("support_ploran_town");
        int n2 = npcInstance.getNpcId();
        if (n2 == 20781 || n2 == 21104) {
            if (n == 2) {
                questState.rollAndGive(8076, 1, 65.0);
            }
        } else if (n2 == 21102) {
            if (n == 2) {
                questState.rollAndGive(8074, 1, 50.0);
            }
        } else if (n2 == 21103) {
            if (n == 2) {
                questState.rollAndGive(8075, 1, 52.0);
            }
        } else if (n2 == 21105) {
            if (n == 2) {
                questState.rollAndGive(8076, 1, 75.0);
            }
        } else if (n2 == 21106) {
            if (n == 2) {
                questState.rollAndGive(8074, 1, 63.0);
            }
        } else if (n2 == 21107 && n == 2) {
            if (Rnd.get((int)100) < 33) {
                questState.rollAndGive(8076, 2, 100.0);
            } else {
                questState.rollAndGive(8076, 1, 100.0);
            }
        }
        return null;
    }
}
