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

public class _639_GuardiansOfTheHolyGrail
extends Quest
implements ScriptFile {
    private static final int byM = 31350;
    private static final int byN = 32008;
    private static final int byO = 32028;
    private static final int byP = 8069;
    private static final int byQ = 8070;
    private static final int byR = 8071;
    private static final int byS = 8056;
    private static final int byT = 960;
    private static final int byU = 959;
    private static final int byV = 22123;
    private static final int byW = 22122;
    private static final int byX = 22128;
    private static final int byY = 22135;
    private static final int byZ = 22132;
    private static final int bza = 22131;
    private static final int bzb = 22129;
    private static final int bzc = 22133;
    private static final int bzd = 22134;
    private static final int bze = 22127;
    private static final int bzf = 22126;
    private static final int bzg = 22124;
    private static final int bzh = 22125;
    private static final int bzi = 22130;

    public _639_GuardiansOfTheHolyGrail() {
        super(1);
        this.addStartNpc(31350);
        this.addTalkId(new int[]{32008, 32028});
        this.addQuestItem(new int[]{8069});
        this.addKillId(new int[]{22123, 22122, 22128, 22135, 22132, 22131, 22129, 22133, 22134, 22127, 22126, 22124, 22125, 22130});
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("falsepriest_dominic_q0639_04.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equals("falsepriest_dominic_q0639_09.htm")) {
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
        } else if (string.equals("falsepriest_dominic_q0639_08.htm")) {
            questState.giveItems(57, questState.takeAllItems(8069) * 1625L, true);
        } else if (string.equals("falsepriest_gremory_q0639_05.htm")) {
            questState.setCond(2);
            questState.playSound("ItemSound.quest_middle");
            questState.giveItems(8070, 1L, false);
        } else if (string.equals("holy_grail_q0639_02.htm")) {
            questState.setCond(3);
            questState.playSound("ItemSound.quest_middle");
            questState.takeItems(8070, -1L);
            questState.giveItems(8071, 1L);
        } else if (string.equals("falsepriest_gremory_q0639_09.htm")) {
            questState.setCond(4);
            questState.playSound("ItemSound.quest_middle");
            questState.takeItems(8071, -1L);
        } else if (string.equals("falsepriest_gremory_q0639_11.htm") && questState.getQuestItemsCount(8069) >= 4000L) {
            questState.takeItems(8069, 4000L);
            questState.giveItems(959, 1L, true);
            this.giveExtraReward(questState.getPlayer());
        } else if (string.equals("falsepriest_gremory_q0639_13.htm") && questState.getQuestItemsCount(8069) >= 400L) {
            questState.takeItems(8069, 400L);
            questState.giveItems(960, 1L, true);
            this.giveExtraReward(questState.getPlayer());
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        int n3 = questState.getCond();
        if (n == 31350) {
            if (n2 == 1) {
                string = questState.getPlayer().getLevel() >= 73 ? "falsepriest_dominic_q0639_01.htm" : "falsepriest_dominic_q0639_02.htm";
                questState.exitCurrentQuest(true);
            } else {
                string = questState.getQuestItemsCount(8069) >= 1L ? "falsepriest_dominic_q0639_05.htm" : "falsepriest_dominic_q0639_06.htm";
            }
        } else if (n == 32008) {
            if (n3 == 1) {
                string = "falsepriest_gremory_q0639_01.htm";
            } else if (n3 == 2) {
                string = "falsepriest_gremory_q0639_06.htm";
            } else if (n3 == 3) {
                string = "falsepriest_gremory_q0639_08.htm";
            } else if (n3 == 4 && questState.getQuestItemsCount(8069) < 400L) {
                string = "falsepriest_gremory_q0639_09.htm";
            } else if (n3 == 4 && questState.getQuestItemsCount(8069) >= 4000L) {
                string = "falsepriest_gremory_q0639_10.htm";
            } else if (n3 == 4 && questState.getQuestItemsCount(8069) >= 400L && questState.getQuestItemsCount(8069) < 4000L) {
                string = "falsepriest_gremory_q0639_14.htm";
            }
        } else if (n == 32028 && n3 == 2) {
            string = "holy_grail_q0639_01.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 22123) {
            questState.rollAndGive(8069, 1, 75.0);
        } else if (n == 22122) {
            questState.rollAndGive(8069, 1, 76.0);
        } else if (n == 22128) {
            questState.rollAndGive(8069, 1, 17.0);
        } else if (n == 22130) {
            questState.rollAndGive(8056, 1, 17.0);
            questState.rollAndGive(8069, 1, 85.0);
        } else if (n == 22135) {
            questState.rollAndGive(8056, 1, 30.0);
            questState.rollAndGive(8069, 1, 93.0);
        } else if (n == 22132) {
            questState.rollAndGive(8056, 1, 3.4);
            questState.rollAndGive(8069, 1, 58.0);
        } else if (n == 22131) {
            questState.rollAndGive(8056, 1, 3.4);
            questState.rollAndGive(8069, 1, 92.0);
        } else if (n == 22129) {
            questState.rollAndGive(8056, 1, 3.4);
            questState.rollAndGive(8069, 1, 59.0);
        } else if (n == 22133) {
            questState.rollAndGive(8056, 1, 3.4);
            questState.rollAndGive(8069, 1, 23.0);
        } else if (n == 22134) {
            questState.rollAndGive(8056, 1, 3.4);
            questState.rollAndGive(8069, 1, 58.0);
        } else if (n == 22127 || n == 22125) {
            questState.rollAndGive(8069, 1, 58.0);
        } else if (n == 22126 || n == 22124) {
            questState.rollAndGive(8069, 1, 59.0);
        }
        return null;
    }
}
