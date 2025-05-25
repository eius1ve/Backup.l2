/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _646_SignsOfRevolt
extends Quest
implements ScriptFile {
    private static int QK = 32016;
    private static int bBy = 22029;
    private static int bBz = 22044;
    private static int bBA = 22047;
    private static int bBB = 22049;
    private static int bBC = 1880;
    private static int bBD = 1881;
    private static int bBE = 1882;
    private static int bBF = 8087;
    private static int bBG = 75;

    public _646_SignsOfRevolt() {
        super(0);
        this.addStartNpc(QK);
        int n = bBy;
        while (n <= bBz) {
            this.addKillId(new int[]{n++});
        }
        this.addKillId(new int[]{bBA});
        this.addKillId(new int[]{bBB});
        this.addQuestItem(new int[]{bBF});
    }

    private static String a(QuestState questState, int n, int n2) {
        if (questState.getQuestItemsCount(bBF) < 180L) {
            return null;
        }
        questState.takeItems(bBF, -1L);
        questState.giveItems(n, (long)n2, true);
        questState.getQuest().giveExtraReward(questState.getPlayer());
        questState.playSound("ItemSound.quest_finish");
        questState.exitCurrentQuest(true);
        return "torant_q0646_0202.htm";
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        int n = questState.getState();
        if (string.equalsIgnoreCase("torant_q0646_0103.htm") && n == 1) {
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
        } else {
            if (string.equalsIgnoreCase("reward_adena") && n == 2) {
                return _646_SignsOfRevolt.a(questState, 57, 21600);
            }
            if (string.equalsIgnoreCase("reward_cbp") && n == 2) {
                return _646_SignsOfRevolt.a(questState, bBD, 12);
            }
            if (string.equalsIgnoreCase("reward_steel") && n == 2) {
                return _646_SignsOfRevolt.a(questState, bBC, 9);
            }
            if (string.equalsIgnoreCase("reward_leather") && n == 2) {
                return _646_SignsOfRevolt.a(questState, bBE, 20);
            }
        }
        return string;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        if (npcInstance.getNpcId() != QK) {
            return string;
        }
        int n = questState.getState();
        if (n == 1) {
            if (questState.getPlayer().getLevel() < 40) {
                string = "torant_q0646_0102.htm";
                questState.exitCurrentQuest(true);
            } else {
                string = "torant_q0646_0101.htm";
                questState.setCond(0);
            }
        } else if (n == 2) {
            string = questState.getQuestItemsCount(bBF) >= 180L ? "torant_q0646_0105.htm" : "torant_q0646_0106.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        Player player = questState.getRandomPartyMember(2, Config.ALT_PARTY_DISTRIBUTION_RANGE);
        if (player == null) {
            return null;
        }
        QuestState questState2 = player.getQuestState(questState.getQuest().getName());
        long l = questState2.getQuestItemsCount(bBF);
        if (l < 180L && Rnd.chance((int)bBG)) {
            questState2.giveItems(bBF, 1L);
            if (l == 179L) {
                questState2.playSound("ItemSound.quest_middle");
                questState2.setCond(2);
            } else {
                questState2.playSound("ItemSound.quest_itemget");
            }
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
