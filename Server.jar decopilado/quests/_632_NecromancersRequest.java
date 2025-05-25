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

public class _632_NecromancersRequest
extends Quest
implements ScriptFile {
    private static final int bxv = 31522;
    private static final int bxw = 7542;
    private static final int bxx = 7543;
    private static final int bxy = 120000;
    private static final int[] bQ = new int[]{21568, 21573, 21582, 21585, 21586, 21587, 21588, 21589, 21590, 21591, 21592, 21593, 21594, 21595};
    private static final int[] bR = new int[]{21547, 21548, 21549, 21550, 21551, 21552, 21555, 21556, 21562, 21571, 21576, 21577, 21579};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _632_NecromancersRequest() {
        super(1);
        this.addStartNpc(31522);
        this.addKillId(bQ);
        this.addKillId(bR);
        this.addQuestItem(new int[]{7542, 7543});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("632_4")) {
            questState.playSound("ItemSound.quest_finish");
            string2 = "shadow_hardin_q0632_0204.htm";
            questState.exitCurrentQuest(true);
        } else if (string.equals("632_1")) {
            string2 = "shadow_hardin_q0632_0104.htm";
        } else if (string.equals("632_3")) {
            if (questState.getCond() == 2 && questState.getQuestItemsCount(7542) > 199L) {
                questState.takeItems(7542, 200L);
                questState.giveItems(57, 120000L, true);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.setCond(1);
                string2 = "shadow_hardin_q0632_0202.htm";
            }
        } else if (string.equals("quest_accept")) {
            if (questState.getPlayer().getLevel() > 62) {
                string2 = "shadow_hardin_q0632_0104.htm";
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
            } else {
                string2 = "shadow_hardin_q0632_0103.htm";
                questState.exitCurrentQuest(true);
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n2 == 0 && n == 31522) {
            string = "shadow_hardin_q0632_0101.htm";
        }
        if (n2 == 1) {
            string = "shadow_hardin_q0632_0202.htm";
        }
        if (n2 == 2 && questState.getQuestItemsCount(7542) > 199L) {
            string = "shadow_hardin_q0632_0105.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        for (int n : bQ) {
            if (n != npcInstance.getNpcId()) continue;
            if (questState.getCond() < 2 && Rnd.chance((int)50)) {
                questState.rollAndGive(7542, 1, 100.0);
                if (questState.getQuestItemsCount(7542) > 199L) {
                    questState.setCond(2);
                }
            }
            return null;
        }
        questState.rollAndGive(7543, 1, 33.0);
        return null;
    }
}
