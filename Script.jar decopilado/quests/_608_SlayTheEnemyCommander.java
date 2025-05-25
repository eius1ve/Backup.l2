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

public class _608_SlayTheEnemyCommander
extends Quest
implements ScriptFile {
    private static final int bpm = 31370;
    private static final int bpn = 25312;
    private static final int bpo = 7236;
    private static final int bpp = 7220;
    private static final int bpq = 7211;
    private static final int bpr = 7212;
    private static final int bps = 7213;
    private static final int bpt = 7214;
    private static final int bpu = 7215;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _608_SlayTheEnemyCommander() {
        super(1);
        this.addStartNpc(31370);
        this.addKillId(new int[]{25312});
        this.addQuestItem(new int[]{7236});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            string2 = "elder_kadun_zu_ketra_q0608_0104.htm";
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("608_3")) {
            if (questState.getQuestItemsCount(7236) >= 1L) {
                string2 = "elder_kadun_zu_ketra_q0608_0201.htm";
                questState.takeItems(7236, -1L);
                questState.giveItems(7220, 1L);
                questState.addExpAndSp(0L, 10000L);
                questState.unset("cond");
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
            } else {
                string2 = "elder_kadun_zu_ketra_q0608_0106.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        if (n == 0) {
            if (questState.getPlayer().getLevel() >= 75) {
                if (questState.getQuestItemsCount(7214) == 1L || questState.getQuestItemsCount(7215) == 1L) {
                    string = "elder_kadun_zu_ketra_q0608_0101.htm";
                } else {
                    string = "elder_kadun_zu_ketra_q0608_0102.htm";
                    questState.exitCurrentQuest(true);
                }
            } else {
                string = "elder_kadun_zu_ketra_q0608_0103.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (n == 1 && questState.getQuestItemsCount(7236) == 0L) {
            string = "elder_kadun_zu_ketra_q0608_0106.htm";
        } else if (n == 2 && questState.getQuestItemsCount(7236) >= 1L) {
            string = "elder_kadun_zu_ketra_q0608_0105.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getCond() == 1) {
            questState.giveItems(7236, 1L);
            questState.setCond(2);
            questState.playSound("ItemSound.quest_itemget");
        }
        return null;
    }
}
