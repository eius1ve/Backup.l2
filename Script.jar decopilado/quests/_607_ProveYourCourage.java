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

public class _607_ProveYourCourage
extends Quest
implements ScriptFile {
    private static final int bpd = 31370;
    private static final int bpe = 25309;
    private static final int bpf = 7235;
    private static final int bpg = 7219;
    private static final int bph = 7211;
    private static final int bpi = 7212;
    private static final int bpj = 7213;
    private static final int bpk = 7214;
    private static final int bpl = 7215;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _607_ProveYourCourage() {
        super(1);
        this.addStartNpc(31370);
        this.addKillId(new int[]{25309});
        this.addQuestItem(new int[]{7235});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("quest_accept")) {
            string2 = "elder_kadun_zu_ketra_q0607_0104.htm";
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equals("607_3")) {
            if (questState.getQuestItemsCount(7235) >= 1L) {
                string2 = "elder_kadun_zu_ketra_q0607_0201.htm";
                questState.takeItems(7235, -1L);
                questState.giveItems(7219, 1L);
                questState.addExpAndSp(0L, 10000L);
                questState.unset("cond");
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
            } else {
                string2 = "elder_kadun_zu_ketra_q0607_0106.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        if (n == 0) {
            if (questState.getPlayer().getLevel() >= 75) {
                if (questState.getQuestItemsCount(7213) == 1L || questState.getQuestItemsCount(7214) == 1L || questState.getQuestItemsCount(7215) == 1L) {
                    string = "elder_kadun_zu_ketra_q0607_0101.htm";
                } else {
                    string = "elder_kadun_zu_ketra_q0607_0102.htm";
                    questState.exitCurrentQuest(true);
                }
            } else {
                string = "elder_kadun_zu_ketra_q0607_0103.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (n == 1 && questState.getQuestItemsCount(7235) == 0L) {
            string = "elder_kadun_zu_ketra_q0607_0106.htm";
        } else if (n == 2 && questState.getQuestItemsCount(7235) >= 1L) {
            string = "elder_kadun_zu_ketra_q0607_0105.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 25309 && questState.getCond() == 1) {
            questState.giveItems(7235, 1L);
            questState.setCond(2);
            questState.playSound("ItemSound.quest_itemget");
        }
        return null;
    }
}
