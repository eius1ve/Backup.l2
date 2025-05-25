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

public class _432_BirthdayPartySong
extends Quest
implements ScriptFile {
    private static int bli = 31043;
    private static int blj = 21103;
    private static int blk = 7541;
    private static int bll = 7061;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _432_BirthdayPartySong() {
        super(0);
        this.addStartNpc(bli);
        this.addKillId(new int[]{blj});
        this.addQuestItem(new int[]{blk});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("muzyko_q0432_0104.htm")) {
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("muzyko_q0432_0201.htm")) {
            if (questState.getQuestItemsCount(blk) == 50L) {
                questState.takeItems(blk, -1L);
                questState.giveItems(bll, 25L);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
            } else {
                string2 = "muzyko_q0432_0202.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        int n2 = npcInstance.getNpcId();
        if (n2 == bli) {
            if (n == 0) {
                if (questState.getPlayer().getLevel() >= 31) {
                    string = "muzyko_q0432_0101.htm";
                } else {
                    string = "muzyko_q0432_0103.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n == 1) {
                string = "muzyko_q0432_0106.htm";
            } else if (n == 2 && questState.getQuestItemsCount(blk) == 50L) {
                string = "muzyko_q0432_0105.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getState() != 2) {
            return null;
        }
        int n = npcInstance.getNpcId();
        if (n == blj && questState.getCond() == 1 && questState.getQuestItemsCount(blk) < 50L) {
            questState.giveItems(blk, 1L);
            if (questState.getQuestItemsCount(blk) == 50L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(2);
            } else {
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
