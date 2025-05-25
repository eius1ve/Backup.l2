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

public class _052_WilliesSpecialBait
extends Quest
implements ScriptFile {
    private static final int Ox = 31574;
    private static final int[] bq = new int[]{20573, 20574};
    private static final int Oy = 7623;
    private static final int Oz = 7612;
    private static final Integer i = 1315;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _052_WilliesSpecialBait() {
        super(0);
        this.addStartNpc(31574);
        this.addKillId(bq);
        this.addQuestItem(new int[]{7623});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("fisher_willeri_q0052_0104.htm")) {
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equals("fisher_willeri_q0052_0201.htm")) {
            if (questState.getQuestItemsCount(7623) < 100L) {
                string2 = "fisher_willeri_q0052_0202.htm";
            } else {
                questState.unset("cond");
                questState.takeItems(7623, -1L);
                questState.giveItems(7612, 4L);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(false);
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getCond();
        int n3 = questState.getState();
        if (n == 31574) {
            if (n3 == 1) {
                if (questState.getPlayer().getLevel() < 48) {
                    string = "fisher_willeri_q0052_0103.htm";
                    questState.exitCurrentQuest(true);
                } else if (questState.getPlayer().getSkillLevel(i) >= 16) {
                    string = "fisher_willeri_q0052_0101.htm";
                } else {
                    string = "fisher_willeri_q0052_0102.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n2 == 1 || n2 == 2) {
                if (questState.getQuestItemsCount(7623) < 100L) {
                    string = "fisher_willeri_q0052_0106.htm";
                    questState.setCond(1);
                } else {
                    string = "fisher_willeri_q0052_0105.htm";
                }
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if ((n == bq[0] || n == bq[1] && questState.getCond() == 1) && questState.getQuestItemsCount(7623) < 100L && Rnd.chance((int)30)) {
            questState.giveItems(7623, 1L);
            if (questState.getQuestItemsCount(7623) == 100L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(2);
            } else {
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
