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

public class _644_GraveRobberAnnihilation
extends Quest
implements ScriptFile {
    private static final int bAU = 32017;
    private static final int bAV = 22003;
    private static final int bAW = 22004;
    private static final int bAX = 22005;
    private static final int bAY = 22006;
    private static final int bAZ = 22008;
    private static final int bBa = 8088;
    private static final int bBb = 1865;
    private static final int bBc = 1867;
    private static final int bBd = 1872;
    private static final int bBe = 1871;
    private static final int bBf = 1870;
    private static final int bBg = 1869;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _644_GraveRobberAnnihilation() {
        super(0);
        this.addStartNpc(32017);
        this.addKillId(new int[]{22003, 22004, 22005, 22006, 22008});
        this.addQuestItem(new int[]{8088});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("sweep_the_snatcher");
        int n2 = questState.getInt("sweep_the_snatcher_c");
        int n3 = npcInstance.getNpcId();
        if (n3 == 32017) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("sweep_the_snatcher", String.valueOf(11), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "karuda_q0644_0103.htm";
            } else if (string.equalsIgnoreCase("reply_3") && n2 == 1 && n >= 11) {
                string2 = "karuda_q0644_0201.htm";
            } else if (n >= 11 && questState.getQuestItemsCount(8088) >= 120L) {
                questState.takeItems(8088, 120L);
                if (string.equalsIgnoreCase("reply_11")) {
                    questState.giveItems(1865, 30L);
                } else if (string.equalsIgnoreCase("reply_12")) {
                    questState.giveItems(1867, 40L);
                } else if (string.equalsIgnoreCase("reply_13")) {
                    questState.giveItems(1872, 40L);
                } else if (string.equalsIgnoreCase("reply_14")) {
                    questState.giveItems(1871, 30L);
                } else if (string.equalsIgnoreCase("reply_15")) {
                    questState.giveItems(1870, 30L);
                } else if (string.equalsIgnoreCase("reply_16")) {
                    questState.giveItems(1869, 30L);
                }
                questState.unset("sweep_the_snatcher");
                questState.unset("sweep_the_snatcher_c");
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
                string2 = "karuda_q0644_0202.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("sweep_the_snatcher");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 32017) break;
                if (questState.getPlayer().getLevel() >= 20) {
                    string = "karuda_q0644_0101.htm";
                    break;
                }
                string = "karuda_q0644_0102.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n2 != 32017 || n < 11 || n > 12) break;
                if (n == 12 && questState.getQuestItemsCount(8088) >= 120L) {
                    questState.set("sweep_the_snatcher_c", String.valueOf(1), true);
                    string = "karuda_q0644_0105.htm";
                    break;
                }
                string = "karuda_q0644_0106.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("sweep_the_snatcher");
        int n2 = npcInstance.getNpcId();
        if (n == 11) {
            int n3;
            if (n2 == 22003) {
                int n4 = Rnd.get((int)1000);
                if (n4 < 714) {
                    if (questState.getQuestItemsCount(8088) + 1L >= 120L) {
                        if (questState.getQuestItemsCount(8088) < 120L) {
                            questState.giveItems(8088, 120L - questState.getQuestItemsCount(8088));
                            questState.playSound("ItemSound.quest_middle");
                        }
                        questState.setCond(2);
                        questState.set("sweep_the_snatcher", String.valueOf(12), true);
                    } else {
                        questState.giveItems(8088, 1L);
                        questState.playSound("ItemSound.quest_itemget");
                    }
                }
            } else if (n2 == 22004) {
                int n5 = Rnd.get((int)1000);
                if (n5 < 841) {
                    if (questState.getQuestItemsCount(8088) + 1L >= 120L) {
                        if (questState.getQuestItemsCount(8088) < 120L) {
                            questState.giveItems(8088, 120L - questState.getQuestItemsCount(8088));
                            questState.playSound("ItemSound.quest_middle");
                        }
                        questState.setCond(2);
                        questState.set("sweep_the_snatcher", String.valueOf(12), true);
                    } else {
                        questState.giveItems(8088, 1L);
                        questState.playSound("ItemSound.quest_itemget");
                    }
                }
            } else if (n2 == 22005) {
                int n6 = Rnd.get((int)1000);
                if (n6 < 746) {
                    if (questState.getQuestItemsCount(8088) + 1L >= 120L) {
                        if (questState.getQuestItemsCount(8088) < 120L) {
                            questState.giveItems(8088, 120L - questState.getQuestItemsCount(8088));
                            questState.playSound("ItemSound.quest_middle");
                        }
                        questState.setCond(2);
                        questState.set("sweep_the_snatcher", String.valueOf(12), true);
                    } else {
                        questState.giveItems(8088, 1L);
                        questState.playSound("ItemSound.quest_itemget");
                    }
                }
            } else if (n2 == 22006) {
                int n7 = Rnd.get((int)1000);
                if (n7 < 778) {
                    if (questState.getQuestItemsCount(8088) + 1L >= 120L) {
                        if (questState.getQuestItemsCount(8088) < 120L) {
                            questState.giveItems(8088, 120L - questState.getQuestItemsCount(8088));
                            questState.playSound("ItemSound.quest_middle");
                        }
                        questState.setCond(2);
                        questState.set("sweep_the_snatcher", String.valueOf(12), true);
                    } else {
                        questState.giveItems(8088, 1L);
                        questState.playSound("ItemSound.quest_itemget");
                    }
                }
            } else if (n2 == 22008 && (n3 = Rnd.get((int)1000)) < 810) {
                if (questState.getQuestItemsCount(8088) + 1L >= 120L) {
                    if (questState.getQuestItemsCount(8088) < 120L) {
                        questState.giveItems(8088, 120L - questState.getQuestItemsCount(8088));
                        questState.playSound("ItemSound.quest_middle");
                    }
                    questState.setCond(2);
                    questState.set("sweep_the_snatcher", String.valueOf(12), true);
                } else {
                    questState.giveItems(8088, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        }
        return null;
    }
}
