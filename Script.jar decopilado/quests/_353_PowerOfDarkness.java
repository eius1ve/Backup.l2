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

public class _353_PowerOfDarkness
extends Quest
implements ScriptFile {
    private static int aMF = 31044;
    private static int aMG = 20283;
    private static int aMH = 20284;
    private static int aMI = 20244;
    private static int aMJ = 20245;
    private static int aMK = 5862;
    private static int aML = 57;
    private static int aMM = 50;

    public _353_PowerOfDarkness() {
        super(0);
        this.addStartNpc(aMF);
        this.addKillId(new int[]{aMG});
        this.addKillId(new int[]{aMH});
        this.addKillId(new int[]{aMI});
        this.addKillId(new int[]{aMJ});
        this.addQuestItem(new int[]{aMK});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        int n = questState.getState();
        if (string.equalsIgnoreCase("31044-04.htm") && n == 1) {
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("31044-08.htm") && n == 2) {
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
        }
        return string;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        if (npcInstance.getNpcId() != aMF) {
            return string;
        }
        if (questState.getState() == 1) {
            if (questState.getPlayer().getLevel() >= 55) {
                string = "31044-02.htm";
                questState.setCond(0);
            } else {
                string = "31044-01.htm";
                questState.exitCurrentQuest(true);
            }
        } else {
            long l = questState.getQuestItemsCount(aMK);
            if (l > 0L) {
                string = "31044-06.htm";
                questState.takeItems(aMK, -1L);
                questState.giveItems(aML, 2500L + 230L * l);
                this.giveExtraReward(questState.getPlayer());
                questState.playSound("ItemSound.quest_middle");
            } else {
                string = "31044-05.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getState() != 2) {
            return null;
        }
        if (Rnd.chance((int)aMM)) {
            questState.giveItems(aMK, 1L);
            questState.playSound("ItemSound.quest_itemget");
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
