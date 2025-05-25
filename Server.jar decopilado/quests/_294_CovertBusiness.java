/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _294_CovertBusiness
extends Quest
implements ScriptFile {
    public static int BatFang = 1491;
    public static int RingOfRaccoon = 1508;
    public static int BarbedBat = 20370;
    public static int BladeBat = 20480;
    public static int Keef = 30534;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _294_CovertBusiness() {
        super(0);
        this.addStartNpc(Keef);
        this.addTalkId(new int[]{Keef});
        this.addKillId(new int[]{BarbedBat});
        this.addKillId(new int[]{BladeBat});
        this.addQuestItem(new int[]{BatFang});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("elder_keef_q0294_03.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getState();
        if (n == 1) {
            if (questState.getPlayer().getRace() != Race.dwarf) {
                string = "elder_keef_q0294_00.htm";
                questState.exitCurrentQuest(true);
            } else {
                if (questState.getPlayer().getLevel() >= 10) {
                    string = "elder_keef_q0294_02.htm";
                    return string;
                }
                string = "elder_keef_q0294_01.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (questState.getQuestItemsCount(BatFang) < 100L) {
            string = "elder_keef_q0294_04.htm";
        } else {
            if (questState.getQuestItemsCount(RingOfRaccoon) < 1L) {
                questState.giveItems(RingOfRaccoon, 1L);
                string = "elder_keef_q0294_05.htm";
            } else {
                questState.giveItems(57, 2400L);
                string = "elder_keef_q0294_06.htm";
            }
            questState.addExpAndSp(0L, 600L);
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getCond() == 1) {
            questState.rollAndGive(BatFang, 1, 2, 100, 100.0);
        }
        return null;
    }
}
