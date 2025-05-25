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

public class _155_FindSirWindawood
extends Quest
implements ScriptFile {
    private static final int TQ = 30042;
    private static final int TR = 30311;
    private static final int TS = 1019;
    private static final int TT = 734;

    public _155_FindSirWindawood() {
        super(0);
        this.addStartNpc(30042);
        this.addTalkId(new int[]{30311});
        this.addQuestItem(new int[]{1019});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setState(2);
            questState.setCond(1);
            questState.giveItems(1019, 1L);
            string2 = "abel_q0305_04.htm";
            questState.playSound("ItemSound.quest_accept");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 30042) break;
                if (questState.getPlayer().getLevel() >= 3) {
                    string = "abel_q0305_03.htm";
                    break;
                }
                string = "abel_q0305_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n == 30042) {
                    if (questState.getQuestItemsCount(1019) != 1L) break;
                    string = "abel_q0305_05.htm";
                    break;
                }
                if (n != 30311 || questState.getQuestItemsCount(1019) != 1L) break;
                questState.takeItems(1019, -1L);
                questState.giveItems(734, 1L);
                questState.exitCurrentQuest(false);
                this.giveExtraReward(questState.getPlayer());
                questState.playSound("ItemSound.quest_finish");
                string = "sir_collin_windawood_q0305_01.htm";
            }
        }
        return string;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
