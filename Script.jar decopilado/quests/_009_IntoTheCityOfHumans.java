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

public class _009_IntoTheCityOfHumans
extends Quest
implements ScriptFile {
    private static final int JJ = 30583;
    private static final int JK = 30571;
    private static final int JL = 30576;
    private static final int JM = 7126;
    private static final int JN = 7570;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _009_IntoTheCityOfHumans() {
        super(0);
        this.addStartNpc(30583);
        this.addTalkId(new int[]{30583});
        this.addTalkId(new int[]{30571});
        this.addTalkId(new int[]{30576});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("centurion_petukai_q0009_0104.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("seer_tanapi_q0009_0201.htm")) {
            questState.setCond(2);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("gatekeeper_tamil_q0009_0301.htm")) {
            questState.giveItems(7126, 1L);
            questState.giveItems(7570, 1L);
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
        if (n == 30583) {
            if (n2 == 0) {
                if (questState.getPlayer().getRace() == Race.orc && questState.getPlayer().getLevel() >= 3) {
                    string = "centurion_petukai_q0009_0101.htm";
                } else {
                    string = "centurion_petukai_q0009_0102.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n2 == 1) {
                string = "centurion_petukai_q0009_0105.htm";
            }
        } else if (n == 30571) {
            if (n2 == 1) {
                string = "seer_tanapi_q0009_0101.htm";
            } else if (n2 == 2) {
                string = "seer_tanapi_q0009_0202.htm";
            }
        } else if (n == 30576 && n2 == 2) {
            string = "gatekeeper_tamil_q0009_0201.htm";
        }
        return string;
    }
}
