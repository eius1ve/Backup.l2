/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _006_StepIntoTheFuture
extends Quest
implements ScriptFile {
    private static final int JD = 30006;
    private static final int JE = 30033;
    private static final int JF = 30311;
    private static final int JG = 7571;
    private static final int JH = 7126;
    private static final int JI = 7570;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _006_StepIntoTheFuture() {
        super(0);
        this.addStartNpc(30006);
        this.addTalkId(new int[]{30033});
        this.addTalkId(new int[]{30311});
        this.addQuestItem(new int[]{7571});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("rapunzel_q0006_0104.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("baul_q0006_0201.htm")) {
            questState.giveItems(7571, 1L, false);
            questState.setCond(2);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("sir_collin_windawood_q0006_0301.htm")) {
            questState.takeItems(7571, -1L);
            questState.setCond(3);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("rapunzel_q0006_0401.htm")) {
            questState.giveItems(7126, 1L, false);
            questState.giveItems(7570, 1L, false);
            questState.unset("cond");
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(false);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 30006) {
            if (n2 == 0) {
                if (questState.getPlayer().getRace() == Race.human && questState.getPlayer().getLevel() >= 3) {
                    string = "rapunzel_q0006_0101.htm";
                } else {
                    string = "rapunzel_q0006_0102.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n2 == 1) {
                string = "rapunzel_q0006_0105.htm";
            } else if (n2 == 3) {
                string = "rapunzel_q0006_0301.htm";
            }
        } else if (n == 30033) {
            if (n2 == 1) {
                string = "baul_q0006_0101.htm";
            } else if (n2 == 2 && questState.getQuestItemsCount(7571) > 0L) {
                string = "baul_q0006_0202.htm";
            }
        } else if (n == 30311) {
            if (n2 == 2 && questState.getQuestItemsCount(7571) > 0L) {
                string = "sir_collin_windawood_q0006_0201.htm";
            } else if (n2 == 2 && questState.getQuestItemsCount(7571) == 0L) {
                string = "sir_collin_windawood_q0006_0302.htm";
            } else if (n2 == 3) {
                string = "sir_collin_windawood_q0006_0303.htm";
            }
        }
        return string;
    }
}
