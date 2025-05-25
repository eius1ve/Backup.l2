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

public class _366_SilverHairedShaman
extends Quest
implements ScriptFile {
    private static final int aOM = 30111;
    private static final int aON = 20986;
    private static final int aOO = 20987;
    private static final int aOP = 20988;
    private static final int aOQ = 5874;

    public _366_SilverHairedShaman() {
        super(2);
        this.addStartNpc(30111);
        this.addKillId(new int[]{20986, 20987, 20988});
        this.addQuestItem(new int[]{5874});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.set("silver_haired_shaman", String.valueOf(1), true);
            string2 = "dieter_q0366_03.htm";
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("reply_1")) {
            questState.unset("silver_haired_shaman");
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
            string2 = "dieter_q0366_06.htm";
        } else if (string.equalsIgnoreCase("reply_2")) {
            string2 = "dieter_q0366_07.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        int n3 = questState.getInt("silver_haired_shaman");
        switch (n2) {
            case 1: {
                if (n != 30111) break;
                if (questState.getPlayer().getLevel() >= 48) {
                    string = "dieter_q0366_01.htm";
                    break;
                }
                if (questState.getPlayer().getLevel() >= 48) break;
                string = "dieter_q0366_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n != 30111) break;
                if (n3 == 1 && questState.getQuestItemsCount(5874) < 1L) {
                    string = "dieter_q0366_04.htm";
                    break;
                }
                if (n3 != 1 || questState.getQuestItemsCount(5874) < 1L) break;
                questState.giveItems(57, questState.getQuestItemsCount(5874) * 500L + 29000L);
                questState.takeItems(5874, -1L);
                this.giveExtraReward(questState.getPlayer());
                string = "dieter_q0366_05.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getCond();
        if (n == 1 && Rnd.chance((int)80)) {
            questState.rollAndGive(5874, 1, 100.0);
            questState.playSound("ItemSound.quest_middle");
        }
        return null;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
