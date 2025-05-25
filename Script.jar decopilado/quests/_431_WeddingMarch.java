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

public class _431_WeddingMarch
extends Quest
implements ScriptFile {
    private static int blf = 31042;
    private static int blg = 7540;
    private static int blh = 7062;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _431_WeddingMarch() {
        super(0);
        this.addStartNpc(blf);
        this.addKillId(new int[]{20786});
        this.addKillId(new int[]{20787});
        this.addQuestItem(new int[]{blg});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            string2 = "muzyk_q0431_0104.htm";
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("431_3")) {
            if (questState.getQuestItemsCount(blg) == 50L) {
                string2 = "muzyk_q0431_0201.htm";
                questState.takeItems(blg, -1L);
                questState.giveItems(blh, 25L);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
            } else {
                string2 = "muzyk_q0431_0202.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        if (n2 == blf) {
            if (n3 != 2) {
                if (questState.getPlayer().getLevel() < 38) {
                    string = "muzyk_q0431_0103.htm";
                    questState.exitCurrentQuest(true);
                } else {
                    string = "muzyk_q0431_0101.htm";
                }
            } else if (n == 1) {
                string = "muzyk_q0431_0106.htm";
            } else if (n == 2 && questState.getQuestItemsCount(blg) == 50L) {
                string = "muzyk_q0431_0105.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getState() != 2) {
            return null;
        }
        int n = npcInstance.getNpcId();
        if ((n == 20786 || n == 20787) && questState.getCond() == 1 && questState.getQuestItemsCount(blg) < 50L) {
            questState.giveItems(blg, 1L);
            if (questState.getQuestItemsCount(blg) == 50L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(2);
            } else {
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
