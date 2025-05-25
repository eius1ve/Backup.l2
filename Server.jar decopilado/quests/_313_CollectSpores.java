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

public class _313_CollectSpores
extends Quest
implements ScriptFile {
    private static final int awu = 30150;
    private static final int awv = 20509;
    private static final int aww = 1118;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _313_CollectSpores() {
        super(0);
        this.addStartNpc(30150);
        this.addTalkId(new int[]{30150});
        this.addKillId(new int[]{20509});
        this.addQuestItem(new int[]{1118});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        if (string.equalsIgnoreCase("green_q0313_05.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        }
        return string;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        if (n == 0) {
            if (questState.getPlayer().getLevel() >= 8) {
                string = "green_q0313_03.htm";
            } else {
                string = "green_q0313_02.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (n == 1) {
            string = "green_q0313_06.htm";
        } else if (n == 2) {
            if (questState.getQuestItemsCount(1118) < 10L) {
                questState.setCond(1);
                string = "green_q0313_06.htm";
            } else {
                questState.takeItems(1118, -1L);
                questState.giveItems(57, 3500L, true);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                string = "green_q0313_07.htm";
                questState.exitCurrentQuest(true);
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n2 == 1 && n == 20509 && Rnd.chance((int)70)) {
            questState.giveItems(1118, 1L);
            if (questState.getQuestItemsCount(1118) < 10L) {
                questState.playSound("ItemSound.quest_itemget");
            } else {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(2);
                questState.setState(2);
            }
        }
        return null;
    }
}
