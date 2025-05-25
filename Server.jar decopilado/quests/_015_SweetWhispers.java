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

public class _015_SweetWhispers
extends Quest
implements ScriptFile {
    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _015_SweetWhispers() {
        super(0);
        this.addStartNpc(31302);
        this.addTalkId(new int[]{31517});
        this.addTalkId(new int[]{31518});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("trader_vladimir_q0015_0104.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("dark_necromancer_q0015_0201.htm")) {
            questState.setCond(2);
        } else if (string.equalsIgnoreCase("dark_presbyter_q0015_0301.htm")) {
            questState.addExpAndSp(88000L, 0L);
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
        if (n == 31302) {
            if (n2 == 0) {
                if (questState.getPlayer().getLevel() >= 60) {
                    string = "trader_vladimir_q0015_0101.htm";
                } else {
                    string = "trader_vladimir_q0015_0103.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n2 >= 1) {
                string = "trader_vladimir_q0015_0105.htm";
            }
        } else if (n == 31518) {
            if (n2 == 1) {
                string = "dark_necromancer_q0015_0101.htm";
            } else if (n2 == 2) {
                string = "dark_necromancer_q0015_0202.htm";
            }
        } else if (n == 31517 && n2 == 2) {
            string = "dark_presbyter_q0015_0201.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        return null;
    }
}
