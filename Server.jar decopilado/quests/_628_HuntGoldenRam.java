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

public class _628_HuntGoldenRam
extends Quest
implements ScriptFile {
    private static final int bwN = 31554;
    private static final int bwO = 21508;
    private static final int bwP = 21509;
    private static final int bwQ = 21510;
    private static final int bwR = 21511;
    private static final int bwS = 21512;
    private static final int bwT = 21513;
    private static final int bwU = 21514;
    private static final int bwV = 21515;
    private static final int bwW = 21516;
    private static final int bwX = 21517;
    private static int Qv = 7246;
    private static int Qw = 7247;
    private static int bwY = 7248;
    private static int bwZ = 7249;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _628_HuntGoldenRam() {
        super(1);
        this.addStartNpc(31554);
        this.addKillId(new int[]{21508, 21509, 21510, 21511, 21512, 21513, 21514, 21515, 21516, 21517});
        this.addQuestItem(new int[]{bwY, bwZ});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            if (questState.getQuestItemsCount(Qv) < 1L && questState.getQuestItemsCount(Qw) < 1L) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "merc_kahmun_q0628_03.htm";
            } else if (questState.getQuestItemsCount(Qv) >= 1L && questState.getQuestItemsCount(Qw) < 1L) {
                questState.setCond(2);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "merc_kahmun_q0628_04.htm";
            } else if (questState.getQuestItemsCount(Qw) >= 1L) {
                questState.setCond(3);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "merc_kahmun_q0628_05.htm";
            }
        } else if (string.equalsIgnoreCase("reply_1")) {
            if (questState.getQuestItemsCount(Qv) < 1L && questState.getQuestItemsCount(Qw) < 1L && questState.getQuestItemsCount(bwY) >= 100L) {
                questState.setCond(2);
                questState.giveItems(Qv, 1L);
                questState.takeItems(bwY, -1L);
                questState.getPlayer().updateRam();
                string2 = "merc_kahmun_q0628_08.htm";
            }
        } else if (string.equalsIgnoreCase("reply_3")) {
            questState.takeItems(Qv, -1L);
            questState.takeItems(Qw, -1L);
            questState.takeItems(bwY, -1L);
            questState.takeItems(bwZ, -1L);
            questState.getPlayer().updateRam();
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
            string2 = "merc_kahmun_q0628_13.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 31554) break;
                if (questState.getPlayer().getLevel() >= 66) {
                    string = "merc_kahmun_q0628_01.htm";
                    break;
                }
                string = "merc_kahmun_q0628_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n != 31554) break;
                if (questState.getQuestItemsCount(Qv) < 1L && questState.getQuestItemsCount(Qw) < 1L && questState.getQuestItemsCount(bwY) < 100L) {
                    string = "merc_kahmun_q0628_06.htm";
                    break;
                }
                if (questState.getQuestItemsCount(Qv) < 1L && questState.getQuestItemsCount(Qw) < 1L && questState.getQuestItemsCount(bwY) >= 100L) {
                    string = "merc_kahmun_q0628_07.htm";
                    break;
                }
                if (questState.getQuestItemsCount(Qv) >= 1L && questState.getQuestItemsCount(Qw) < 1L && (questState.getQuestItemsCount(bwY) < 100L || questState.getQuestItemsCount(bwZ) < 100L)) {
                    string = "merc_kahmun_q0628_09.htm";
                    break;
                }
                if (questState.getQuestItemsCount(Qv) >= 1L && questState.getQuestItemsCount(Qw) < 1L && questState.getQuestItemsCount(bwY) >= 100L && questState.getQuestItemsCount(bwZ) >= 100L) {
                    questState.setCond(3);
                    questState.giveItems(Qw, 1L);
                    questState.takeItems(Qv, -1L);
                    questState.takeItems(bwY, -1L);
                    questState.takeItems(bwZ, -1L);
                    questState.playSound("ItemSound.quest_middle");
                    string = "merc_kahmun_q0628_10.htm";
                    break;
                }
                if (questState.getQuestItemsCount(Qw) < 1L) break;
                string = "merc_kahmun_q0628_11.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n;
        if (questState.getState() != 2) {
            return null;
        }
        int n2 = npcInstance.getNpcId();
        if (n2 == 21508) {
            int n3;
            if (questState.getQuestItemsCount(Qw) < 1L && questState.getQuestItemsCount(bwY) < 100L && (n3 = Rnd.get((int)100)) < 50) {
                if (questState.getQuestItemsCount(Qv) >= 1L) {
                    if (questState.getQuestItemsCount(bwY) >= 99L) {
                        questState.rollAndGive(bwY, 1, 100.0);
                        questState.playSound("ItemSound.quest_middle");
                    } else {
                        questState.rollAndGive(bwY, 1, 100.0);
                        questState.playSound("ItemSound.quest_itemget");
                    }
                } else if (questState.getQuestItemsCount(bwY) >= 99L) {
                    questState.rollAndGive(bwY, 1, 100.0);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.rollAndGive(bwY, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n2 == 21509) {
            int n4;
            if (questState.getQuestItemsCount(Qw) < 1L && questState.getQuestItemsCount(bwY) < 100L && (n4 = Rnd.get((int)100)) < 43) {
                if (questState.getQuestItemsCount(Qv) >= 1L) {
                    if (questState.getQuestItemsCount(bwY) >= 99L) {
                        questState.rollAndGive(bwY, 1, 100.0);
                        questState.playSound("ItemSound.quest_middle");
                    } else {
                        questState.rollAndGive(bwY, 1, 100.0);
                        questState.playSound("ItemSound.quest_itemget");
                    }
                } else if (questState.getQuestItemsCount(bwY) >= 99L) {
                    questState.rollAndGive(bwY, 1, 100.0);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.rollAndGive(bwY, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n2 == 21510) {
            int n5;
            if (questState.getQuestItemsCount(Qw) < 1L && questState.getQuestItemsCount(bwY) < 100L && (n5 = Rnd.get((int)1000)) < 521) {
                if (questState.getQuestItemsCount(Qv) >= 1L) {
                    if (questState.getQuestItemsCount(bwY) >= 99L) {
                        questState.rollAndGive(bwY, 1, 100.0);
                        questState.playSound("ItemSound.quest_middle");
                    } else {
                        questState.rollAndGive(bwY, 1, 100.0);
                        questState.playSound("ItemSound.quest_itemget");
                    }
                } else if (questState.getQuestItemsCount(bwY) >= 99L) {
                    questState.rollAndGive(bwY, 1, 100.0);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.rollAndGive(bwY, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n2 == 21511) {
            int n6;
            if (questState.getQuestItemsCount(Qw) < 1L && questState.getQuestItemsCount(bwY) < 100L && (n6 = Rnd.get((int)1000)) < 575) {
                if (questState.getQuestItemsCount(Qv) >= 1L) {
                    if (questState.getQuestItemsCount(bwY) >= 99L) {
                        questState.rollAndGive(bwY, 1, 100.0);
                        questState.playSound("ItemSound.quest_middle");
                    } else {
                        questState.rollAndGive(bwY, 1, 100.0);
                        questState.playSound("ItemSound.quest_itemget");
                    }
                } else if (questState.getQuestItemsCount(bwY) >= 99L) {
                    questState.rollAndGive(bwY, 1, 100.0);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.rollAndGive(bwY, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n2 == 21512) {
            int n7;
            if (questState.getQuestItemsCount(Qw) < 1L && questState.getQuestItemsCount(bwY) < 100L && (n7 = Rnd.get((int)1000)) < 746) {
                if (questState.getQuestItemsCount(Qv) >= 1L) {
                    if (questState.getQuestItemsCount(bwY) >= 99L) {
                        questState.rollAndGive(bwY, 1, 100.0);
                        questState.playSound("ItemSound.quest_middle");
                    } else {
                        questState.rollAndGive(bwY, 1, 100.0);
                        questState.playSound("ItemSound.quest_itemget");
                    }
                } else if (questState.getQuestItemsCount(bwY) >= 99L) {
                    questState.rollAndGive(bwY, 1, 100.0);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.rollAndGive(bwY, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n2 == 21513) {
            int n8;
            if (questState.getQuestItemsCount(Qw) < 1L && questState.getQuestItemsCount(Qv) >= 1L && questState.getQuestItemsCount(bwZ) < 100L && (n8 = Rnd.get((int)100)) < 50) {
                if (questState.getQuestItemsCount(bwZ) >= 99L) {
                    questState.rollAndGive(bwZ, 1, 100.0);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.rollAndGive(bwZ, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n2 == 21514) {
            int n9;
            if (questState.getQuestItemsCount(Qw) < 1L && questState.getQuestItemsCount(Qv) >= 1L && questState.getQuestItemsCount(bwZ) < 100L && (n9 = Rnd.get((int)100)) < 43) {
                if (questState.getQuestItemsCount(bwZ) >= 99L) {
                    questState.rollAndGive(bwZ, 1, 100.0);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.rollAndGive(bwZ, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n2 == 21515) {
            int n10;
            if (questState.getQuestItemsCount(Qw) < 1L && questState.getQuestItemsCount(Qv) >= 1L && questState.getQuestItemsCount(bwZ) < 100L && (n10 = Rnd.get((int)100)) < 52) {
                if (questState.getQuestItemsCount(bwZ) >= 99L) {
                    questState.rollAndGive(bwZ, 1, 100.0);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.rollAndGive(bwZ, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n2 == 21516) {
            int n11;
            if (questState.getQuestItemsCount(Qw) < 1L && questState.getQuestItemsCount(Qv) >= 1L && questState.getQuestItemsCount(bwZ) < 100L && (n11 = Rnd.get((int)1000)) < 531) {
                if (questState.getQuestItemsCount(bwZ) >= 99L) {
                    questState.rollAndGive(bwZ, 1, 100.0);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.rollAndGive(bwZ, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n2 == 21517 && questState.getQuestItemsCount(Qw) < 1L && questState.getQuestItemsCount(Qv) >= 1L && questState.getQuestItemsCount(bwZ) < 100L && (n = Rnd.get((int)1000)) < 744) {
            if (questState.getQuestItemsCount(bwZ) >= 99L) {
                questState.rollAndGive(bwZ, 1, 100.0);
                questState.playSound("ItemSound.quest_middle");
            } else {
                questState.rollAndGive(bwZ, 1, 100.0);
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
