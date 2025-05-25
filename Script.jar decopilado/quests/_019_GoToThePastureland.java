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

public class _019_GoToThePastureland
extends Quest
implements ScriptFile {
    int VLADIMIR = 31302;
    int TUNATUN = 31537;
    int BEAST_MEAT = 7547;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _019_GoToThePastureland() {
        super(0);
        this.addStartNpc(this.VLADIMIR);
        this.addTalkId(new int[]{this.VLADIMIR});
        this.addTalkId(new int[]{this.TUNATUN});
        this.addQuestItem(new int[]{this.BEAST_MEAT});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("trader_vladimir_q0019_0104.htm")) {
            questState.giveItems(this.BEAST_MEAT, 1L);
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        }
        if (string.equals("beast_herder_tunatun_q0019_0201.htm")) {
            questState.takeItems(this.BEAST_MEAT, -1L);
            questState.giveItems(57, 30000L);
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
        if (n == this.VLADIMIR) {
            if (n2 == 0) {
                if (questState.getPlayer().getLevel() >= 63) {
                    string = "trader_vladimir_q0019_0101.htm";
                } else {
                    string = "trader_vladimir_q0019_0103.htm";
                    questState.exitCurrentQuest(true);
                }
            } else {
                string = "trader_vladimir_q0019_0105.htm";
            }
        } else if (n == this.TUNATUN) {
            if (questState.getQuestItemsCount(this.BEAST_MEAT) >= 1L) {
                string = "beast_herder_tunatun_q0019_0101.htm";
            } else {
                string = "beast_herder_tunatun_q0019_0202.htm";
                questState.exitCurrentQuest(true);
            }
        }
        return string;
    }
}
