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

public class _324_SweetestVenom
extends Quest
implements ScriptFile {
    private static int awL = 30351;
    private static int awM = 20034;
    private static int awN = 20038;
    private static int awO = 20043;
    private static int awP = 1077;
    private static int awQ = 60;

    public _324_SweetestVenom() {
        super(0);
        this.addStartNpc(awL);
        this.addKillId(new int[]{awM});
        this.addKillId(new int[]{awN});
        this.addKillId(new int[]{awO});
        this.addQuestItem(new int[]{awP});
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        if (npcInstance.getNpcId() != awL) {
            return string;
        }
        int n = questState.getState();
        if (n == 1) {
            if (questState.getPlayer().getLevel() >= 18) {
                string = "astaron_q0324_03.htm";
                questState.setCond(0);
            } else {
                string = "astaron_q0324_02.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (n == 2) {
            long l = questState.getQuestItemsCount(awP);
            if (l >= 10L) {
                string = "astaron_q0324_06.htm";
                questState.takeItems(awP, -1L);
                questState.giveItems(57, 5810L);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
            } else {
                string = "astaron_q0324_05.htm";
            }
        }
        return string;
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        if (string.equalsIgnoreCase("astaron_q0324_04.htm") && questState.getState() == 1) {
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getState() != 2) {
            return null;
        }
        long l = questState.getQuestItemsCount(awP);
        int n = awQ + (npcInstance.getNpcId() - awM) / 4 * 12;
        if (l < 10L && Rnd.chance((int)n)) {
            questState.giveItems(awP, 1L);
            if (l == 9L) {
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            } else {
                questState.playSound("ItemSound.quest_itemget");
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
