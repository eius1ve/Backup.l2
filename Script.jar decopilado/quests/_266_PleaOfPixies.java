/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _266_PleaOfPixies
extends Quest
implements ScriptFile {
    private static final int aut = 1334;
    private static final int auu = 1337;
    private static final int auv = 1338;
    private static final int auw = 1339;
    private static final int auxx = 1336;
    private static final int auy = 2176;
    private static final int auz = 3032;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _266_PleaOfPixies() {
        super(0);
        this.addStartNpc(31852);
        this.addKillId(new int[]{20525, 20530, 20534, 20537});
        this.addQuestItem(new int[]{1334});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("pixy_murika_q0266_03.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        if (questState.getCond() == 0) {
            if (questState.getPlayer().getRace() != Race.elf) {
                string = "pixy_murika_q0266_00.htm";
                questState.exitCurrentQuest(true);
            } else if (questState.getPlayer().getLevel() < 3) {
                string = "pixy_murika_q0266_01.htm";
                questState.exitCurrentQuest(true);
            } else {
                string = "pixy_murika_q0266_02.htm";
            }
        } else if (questState.getQuestItemsCount(1334) < 100L) {
            string = "pixy_murika_q0266_04.htm";
        } else {
            questState.takeItems(1334, -1L);
            int n = Rnd.get((int)100);
            if (n < 2) {
                questState.giveItems(1337, 1L);
                questState.giveItems(3032, 1L);
                questState.playSound("ItemSound.quest_jackpot");
            } else if (n < 20) {
                questState.giveItems(1338, 1L);
                questState.giveItems(2176, 1L);
            } else if (n < 45) {
                questState.giveItems(1339, 1L);
            } else {
                questState.giveItems(1336, 1L);
            }
            string = "pixy_murika_q0266_05.htm";
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
            questState.playSound("ItemSound.quest_finish");
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getCond() == 1) {
            questState.rollAndGive(1334, 1, 1, 100, (double)(60 + npcInstance.getLevel() * 5));
        }
        return null;
    }
}
