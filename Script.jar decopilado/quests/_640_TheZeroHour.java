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
import quests._109_InSearchOfTheNest;

public class _640_TheZeroHour
extends Quest
implements ScriptFile {
    private static final int bzj = 22108;
    private static final int bzk = 22113;
    private static final int bzl = 22114;
    private static final int bzm = 22109;
    private static final int bzn = 22110;
    private static final int bzo = 22118;
    private static final int bzp = 22119;
    private static final int bzq = 22105;
    private static final int bzr = 22116;
    private static final int bzs = 22107;
    private static final int bzt = 22117;
    private static final int bzu = 22111;
    private static final int bzv = 22121;
    private static final int bzw = 22115;
    private static final int bzx = 22106;
    private static final int bzy = 4042;
    private static final int bzz = 4043;
    private static final int bzA = 4044;
    private static final int bzB = 1887;
    private static final int bzC = 1888;
    private static final int bzD = 1889;
    private static final int bzE = 5550;
    private static final int bzF = 1890;
    private static final int bzG = 1893;
    private static final int bzH = 31554;
    private static final int bzI = 8085;

    public _640_TheZeroHour() {
        super(1);
        this.addStartNpc(31554);
        this.addKillId(new int[]{22108, 22113, 22114, 22109, 22110, 22118, 22119, 22105, 22116, 22107, 22117, 22111, 22121, 22115, 22106});
        this.addQuestItem(new int[]{8085});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        int n = questState.getCond();
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            if (questState.getPlayer().getLevel() >= 66) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "merc_kahmun_q0640_0103.htm";
            }
        } else if (string.equalsIgnoreCase("reply_1") && n == 1) {
            string2 = "merc_kahmun_q0640_0201.htm";
        } else if (string.equalsIgnoreCase("reply_3") && n == 1) {
            string2 = "merc_kahmun_q0640_0202.htm";
        } else if (string.equalsIgnoreCase("reply_4") && n == 1) {
            string2 = "merc_kahmun_q0640_0205.htm";
            questState.exitCurrentQuest(true);
            questState.playSound("ItemSound.quest_finish");
        } else if (string.equalsIgnoreCase("reply_11") && n == 1) {
            if (questState.getQuestItemsCount(8085) >= 12L) {
                questState.takeItems(8085, 12L);
                questState.giveItems(4042, 1L);
                string2 = "merc_kahmun_q0640_0203.htm";
            } else {
                string2 = "merc_kahmun_q0640_0204.htm";
            }
        } else if (string.equalsIgnoreCase("reply_12") && n == 1) {
            if (questState.getQuestItemsCount(8085) >= 6L) {
                questState.takeItems(8085, 6L);
                questState.giveItems(4043, 1L);
                string2 = "merc_kahmun_q0640_0203.htm";
            } else {
                string2 = "merc_kahmun_q0640_0204.htm";
            }
        } else if (string.equalsIgnoreCase("reply_13") && n == 1) {
            if (questState.getQuestItemsCount(8085) >= 6L) {
                questState.takeItems(8085, 6L);
                questState.giveItems(4044, 1L);
                string2 = "merc_kahmun_q0640_0203.htm";
            } else {
                string2 = "merc_kahmun_q0640_0204.htm";
            }
        } else if (string.equalsIgnoreCase("reply_14") && n == 1) {
            if (questState.getQuestItemsCount(8085) >= 81L) {
                questState.takeItems(8085, 81L);
                questState.giveItems(1887, 10L);
                string2 = "merc_kahmun_q0640_0203.htm";
            } else {
                string2 = "merc_kahmun_q0640_0204.htm";
            }
        } else if (string.equalsIgnoreCase("reply_15") && n == 1) {
            if (questState.getQuestItemsCount(8085) >= 33L) {
                questState.takeItems(8085, 33L);
                questState.giveItems(1888, 5L);
                string2 = "merc_kahmun_q0640_0203.htm";
            } else {
                string2 = "merc_kahmun_q0640_0204.htm";
            }
        } else if (string.equalsIgnoreCase("reply_16") && n == 1) {
            if (questState.getQuestItemsCount(8085) >= 30L) {
                questState.takeItems(8085, 30L);
                questState.giveItems(1889, 10L);
                string2 = "merc_kahmun_q0640_0203.htm";
            } else {
                string2 = "merc_kahmun_q0640_0204.htm";
            }
        } else if (string.equalsIgnoreCase("reply_17") && n == 1) {
            if (questState.getQuestItemsCount(8085) >= 150L) {
                questState.takeItems(8085, 150L);
                questState.giveItems(5550, 10L);
                string2 = "merc_kahmun_q0640_0203.htm";
            } else {
                string2 = "merc_kahmun_q0640_0204.htm";
            }
        } else if (string.equalsIgnoreCase("reply_18") && n == 1) {
            if (questState.getQuestItemsCount(8085) >= 131L) {
                questState.takeItems(8085, 131L);
                questState.giveItems(1890, 10L);
                string2 = "merc_kahmun_q0640_0203.htm";
            } else {
                string2 = "merc_kahmun_q0640_0204.htm";
            }
        } else if (string.equalsIgnoreCase("reply_19") && n == 1) {
            if (questState.getQuestItemsCount(8085) >= 123L) {
                questState.takeItems(8085, 123L);
                questState.giveItems(1893, 5L);
                string2 = "merc_kahmun_q0640_0203.htm";
            } else {
                string2 = "merc_kahmun_q0640_0204.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        QuestState questState2 = questState.getPlayer().getQuestState(_109_InSearchOfTheNest.class);
        if (questState.getPlayer().getLevel() >= 66 && n == 0) {
            string = questState2 != null && questState2.isCompleted() ? "merc_kahmun_q0640_0101.htm" : "merc_kahmun_q0640_0104.htm";
        } else if (questState.getPlayer().getLevel() < 66 && n == 0) {
            string = "merc_kahmun_q0640_0102.htm";
        } else if (n == 1) {
            string = questState.getQuestItemsCount(8085) == 0L ? "merc_kahmun_q0640_0106.htm" : "merc_kahmun_q0640_0105.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = Rnd.get((int)1000);
        if (n == 22108) {
            if (n2 < 77) {
                questState.rollAndGive(8085, 2, 100.0);
            } else {
                questState.rollAndGive(8085, 1, 100.0);
            }
        } else if (n == 22113) {
            if (n2 < 235) {
                questState.rollAndGive(8085, 2, 100.0);
            } else {
                questState.rollAndGive(8085, 1, 100.0);
            }
        } else if (n == 22114) {
            questState.rollAndGive(8085, 1, 82.9);
        } else if (n == 22109) {
            if (n2 < 63) {
                questState.rollAndGive(8085, 2, 100.0);
            } else {
                questState.rollAndGive(8085, 1, 100.0);
            }
        } else if (n == 22110) {
            questState.rollAndGive(8085, 1, 80.6);
        } else if (n == 22118) {
            questState.rollAndGive(8085, 1, 98.2);
        } else if (n == 22119 || n == 22105) {
            questState.rollAndGive(8085, 1, 72.7);
        } else if (n == 22116) {
            questState.rollAndGive(8085, 1, 70.2);
        } else if (n == 22107) {
            questState.rollAndGive(8085, 1, 77.3);
        } else if (n == 22117) {
            questState.rollAndGive(8085, 1, 72.3);
        } else if (n == 22111) {
            questState.rollAndGive(8085, 1, 77.6);
        } else if (n == 22121) {
            questState.rollAndGive(8085, 1, 70.4);
        } else if (n == 22115) {
            questState.rollAndGive(8085, 1, 68.2);
        } else if (n == 22106) {
            questState.rollAndGive(8085, 1, 75.0);
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
