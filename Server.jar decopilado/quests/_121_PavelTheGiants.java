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

public class _121_PavelTheGiants
extends Quest
implements ScriptFile {
    private static int Sj = 31961;
    private static int Sk = 32041;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _121_PavelTheGiants() {
        super(0);
        this.addStartNpc(Sj);
        this.addTalkId(new int[]{Sj, Sk});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        if (string.equals("collecter_yumi_q0121_0201.htm")) {
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.addExpAndSp(10000L, 0L);
            questState.exitCurrentQuest(false);
        }
        return string;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        int n3 = questState.getCond();
        if (n2 == 1 && n == Sj) {
            if (questState.getPlayer().getLevel() >= 46) {
                string = "head_blacksmith_newyear_q0121_0101.htm";
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
            } else {
                string = "head_blacksmith_newyear_q0121_0103.htm";
                questState.exitCurrentQuest(false);
            }
        } else if (n2 == 2) {
            string = n == Sk && n3 == 1 ? "collecter_yumi_q0121_0101.htm" : "head_blacksmith_newyear_q0121_0105.htm";
        }
        return string;
    }
}
