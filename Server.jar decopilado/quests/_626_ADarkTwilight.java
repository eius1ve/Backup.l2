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

public class _626_ADarkTwilight
extends Quest
implements ScriptFile {
    private static final int bwo = 31517;
    private static int bwp = 7169;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _626_ADarkTwilight() {
        super(1);
        this.addStartNpc(31517);
        int n = 21520;
        while (n <= 21542) {
            this.addKillId(new int[]{n++});
        }
        this.addQuestItem(new int[]{bwp});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("dark_presbyter_q0626_0104.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("dark_presbyter_q0626_0201.htm")) {
            if (questState.getQuestItemsCount(bwp) < 300L) {
                string2 = "dark_presbyter_q0626_0203.htm";
            }
        } else if (string.equalsIgnoreCase("rew_exp")) {
            questState.takeItems(bwp, -1L);
            questState.addExpAndSp(162773L, 12500L);
            string2 = "dark_presbyter_q0626_0202.htm";
            this.giveExtraReward(questState.getPlayer());
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
        } else if (string.equalsIgnoreCase("rew_adena")) {
            questState.takeItems(bwp, -1L);
            questState.giveItems(57, 100000L, true);
            this.giveExtraReward(questState.getPlayer());
            questState.playSound("ItemSound.quest_finish");
            string2 = "dark_presbyter_q0626_0202.htm";
            questState.exitCurrentQuest(true);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        int n2 = npcInstance.getNpcId();
        if (n2 == 31517) {
            if (n == 0) {
                if (questState.getPlayer().getLevel() < 60) {
                    string = "dark_presbyter_q0626_0103.htm";
                    questState.exitCurrentQuest(true);
                } else {
                    string = "dark_presbyter_q0626_0101.htm";
                }
            } else if (n == 1) {
                string = "dark_presbyter_q0626_0106.htm";
            } else if (n == 2) {
                string = "dark_presbyter_q0626_0105.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getCond() == 1 && Rnd.chance((int)70)) {
            questState.giveItems(bwp, 1L);
            if (questState.getQuestItemsCount(bwp) == 300L) {
                questState.setCond(2);
            }
        }
        return null;
    }
}
