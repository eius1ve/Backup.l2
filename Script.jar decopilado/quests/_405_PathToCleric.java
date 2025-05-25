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

public class _405_PathToCleric
extends Quest
implements ScriptFile {
    private static final int aXu = 30017;
    private static final int aXv = 30022;
    private static final int aXw = 30030;
    private static final int aXx = 30253;
    private static final int aXy = 30333;
    private static final int aXz = 30408;
    private static final int aXA = 20026;
    private static final int aXB = 20029;
    private static final int aXC = 1191;
    private static final int aXD = 1192;
    private static final int aXE = 1193;
    private static final int aXF = 1194;
    private static final int aXG = 1195;
    private static final int aXH = 1196;
    private static final int aXI = 1197;
    private static final int aXJ = 1198;
    private static final int aXK = 1199;
    private static final int aXL = 1200;
    private static final int aXM = 1201;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _405_PathToCleric() {
        super(0);
        this.addStartNpc(30022);
        this.addTalkId(new int[]{30017});
        this.addTalkId(new int[]{30030});
        this.addTalkId(new int[]{30253});
        this.addTalkId(new int[]{30333});
        this.addTalkId(new int[]{30408});
        this.addKillId(new int[]{20026});
        this.addKillId(new int[]{20029});
        this.addQuestItem(new int[]{1200, 1192, 1196, 1194, 1195, 1191, 1199, 1198, 1197, 1193});
    }

    public void checkBooks(QuestState questState) {
        if (questState.getQuestItemsCount(1196) + questState.getQuestItemsCount(1194) + questState.getQuestItemsCount(1195) >= 5L) {
            questState.setCond(2);
        }
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("1")) {
            if (questState.getPlayer().getLevel() >= 18 && questState.getPlayer().getClassId().getId() == 10 && questState.getQuestItemsCount(1201) < 1L) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                questState.giveItems(1191, 1L);
                string2 = "gigon_q0405_05.htm";
            } else if (questState.getPlayer().getClassId().getId() != 10) {
                string2 = questState.getPlayer().getClassId().getId() == 15 ? "gigon_q0405_02a.htm" : "gigon_q0405_02.htm";
            } else if (questState.getPlayer().getLevel() < 18 && questState.getPlayer().getClassId().getId() == 10) {
                string2 = "gigon_q0405_03.htm";
            } else if (questState.getPlayer().getLevel() >= 18 && questState.getPlayer().getClassId().getId() == 10 && questState.getQuestItemsCount(1201) > 0L) {
                string2 = "gigon_q0405_04.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 30022) {
            if (questState.getQuestItemsCount(1201) > 0L) {
                string = "gigon_q0405_04.htm";
                questState.exitCurrentQuest(true);
            }
            if (n2 < 1 && questState.getQuestItemsCount(1201) < 1L) {
                string = "gigon_q0405_01.htm";
            } else if (n2 == 1 | n2 == 2 && questState.getQuestItemsCount(1191) > 0L) {
                if (questState.getQuestItemsCount(1194) > 0L && questState.getQuestItemsCount(1195) > 2L && questState.getQuestItemsCount(1196) > 0L) {
                    string = "gigon_q0405_08.htm";
                    questState.takeItems(1196, -1L);
                    questState.takeItems(1194, -1L);
                    questState.takeItems(1195, -1L);
                    questState.takeItems(1191, -1L);
                    questState.giveItems(1192, 1L);
                    questState.setCond(3);
                } else {
                    string = "gigon_q0405_06.htm";
                }
            } else if (n2 < 6 && questState.getQuestItemsCount(1192) > 0L) {
                string = "gigon_q0405_07.htm";
            } else if (n2 == 6 && questState.getQuestItemsCount(1192) > 0L && questState.getQuestItemsCount(1200) > 0L) {
                string = "gigon_q0405_09.htm";
                questState.takeItems(1200, -1L);
                questState.takeItems(1192, -1L);
                if (!questState.getPlayer().getVarB("q405")) {
                    questState.getPlayer().setVar("q405", "1", -1L);
                }
                questState.exitCurrentQuest(true);
                if (questState.getPlayer().getClassId().getLevel() == 1) {
                    questState.giveItems(1201, 1L);
                    if (!questState.getPlayer().getVarB("prof1")) {
                        questState.getPlayer().setVar("prof1", "1", -1L);
                        questState.addExpAndSp(3200L, 5610L);
                        this.giveExtraReward(questState.getPlayer());
                    }
                }
                questState.playSound("ItemSound.quest_finish");
            }
        } else if (n == 30253 && n2 == 1 && questState.getQuestItemsCount(1191) > 0L) {
            if (questState.getQuestItemsCount(1195) < 1L) {
                string = "trader_simplon_q0405_01.htm";
                questState.giveItems(1195, 3L);
                this.checkBooks(questState);
            } else if (questState.getQuestItemsCount(1195) > 2L) {
                string = "trader_simplon_q0405_02.htm";
            }
        } else if (n == 30030 && n2 == 1 && questState.getQuestItemsCount(1191) > 0L) {
            if (questState.getQuestItemsCount(1194) < 1L) {
                string = "vivi_q0405_01.htm";
                questState.giveItems(1194, 1L);
                this.checkBooks(questState);
            } else if (questState.getQuestItemsCount(1194) > 0L) {
                string = "vivi_q0405_02.htm";
            }
        } else if (n == 30333 && n2 == 1 && questState.getQuestItemsCount(1191) > 0L) {
            if (questState.getQuestItemsCount(1196) < 1L && questState.getQuestItemsCount(1199) < 1L) {
                string = "guard_praga_q0405_01.htm";
                questState.giveItems(1199, 1L);
            } else if (questState.getQuestItemsCount(1196) < 1L && questState.getQuestItemsCount(1199) > 0L && questState.getQuestItemsCount(1198) < 1L) {
                string = "guard_praga_q0405_02.htm";
            } else if (questState.getQuestItemsCount(1196) < 1L && questState.getQuestItemsCount(1199) > 0L && questState.getQuestItemsCount(1198) > 0L) {
                string = "guard_praga_q0405_03.htm";
                questState.takeItems(1199, -1L);
                questState.takeItems(1198, -1L);
                questState.giveItems(1196, 1L);
                this.checkBooks(questState);
            } else if (questState.getQuestItemsCount(1196) > 0L) {
                string = "guard_praga_q0405_04.htm";
            }
        } else if (n == 30408) {
            if (questState.getQuestItemsCount(1192) < 1L) {
                string = "lemoniell_q0405_02.htm";
            } else if (n2 == 3 && questState.getQuestItemsCount(1192) == 1L && questState.getQuestItemsCount(1193) < 1L && questState.getQuestItemsCount(1200) < 1L && questState.getQuestItemsCount(1197) < 1L) {
                string = "lemoniell_q0405_01.htm";
                questState.giveItems(1193, 1L);
                questState.setCond(4);
            } else if (n2 == 4 && questState.getQuestItemsCount(1192) == 1L && questState.getQuestItemsCount(1193) > 0L && questState.getQuestItemsCount(1200) < 1L && questState.getQuestItemsCount(1197) < 1L) {
                string = "lemoniell_q0405_03.htm";
            } else if (questState.getQuestItemsCount(1192) == 1L && questState.getQuestItemsCount(1193) < 1L && questState.getQuestItemsCount(1200) < 1L && questState.getQuestItemsCount(1197) > 0L) {
                string = "lemoniell_q0405_04.htm";
                questState.takeItems(1197, -1L);
                questState.giveItems(1200, 1L);
                questState.setCond(6);
            } else if (questState.getQuestItemsCount(1192) == 1L && questState.getQuestItemsCount(1193) < 1L && questState.getQuestItemsCount(1200) > 0L && questState.getQuestItemsCount(1197) < 1L) {
                string = "lemoniell_q0405_05.htm";
            }
        } else if (n == 30017 && questState.getQuestItemsCount(1192) > 0L) {
            if (n2 == 4 && questState.getQuestItemsCount(1193) > 0L && questState.getQuestItemsCount(1197) < 1L) {
                string = "gallin_q0405_01.htm";
                questState.takeItems(1193, -1L);
                questState.giveItems(1197, 1L);
                questState.setCond(5);
            } else if (n2 == 5 && questState.getQuestItemsCount(1193) < 1L && questState.getQuestItemsCount(1197) > 0L) {
                string = "gallin_q0405_02.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20026 | n == 20029 && questState.getCond() == 1 && questState.getQuestItemsCount(1198) < 1L) {
            questState.giveItems(1198, 1L);
            questState.playSound("ItemSound.quest_middle");
        }
        return null;
    }
}
