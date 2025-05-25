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

public class _277_GatekeepersOffering
extends Quest
implements ScriptFile {
    private static final int avf = 1572;
    private static final int avg = 1658;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _277_GatekeepersOffering() {
        super(0);
        this.addStartNpc(30576);
        this.addKillId(new int[]{20333});
        this.addQuestItem(new int[]{1572});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("1")) {
            if (questState.getPlayer().getLevel() >= 15) {
                string2 = "gatekeeper_tamil_q0277_03.htm";
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
            } else {
                string2 = "gatekeeper_tamil_q0277_01.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 30576 && n2 == 0) {
            string = "gatekeeper_tamil_q0277_02.htm";
        } else if (n == 30576 && n2 == 1 && questState.getQuestItemsCount(1572) < 20L) {
            string = "gatekeeper_tamil_q0277_04.htm";
        } else if (n == 30576 && n2 == 2 && questState.getQuestItemsCount(1572) < 20L) {
            string = "gatekeeper_tamil_q0277_04.htm";
        } else if (n == 30576 && n2 == 2 && questState.getQuestItemsCount(1572) >= 20L) {
            string = "gatekeeper_tamil_q0277_05.htm";
            questState.takeItems(1572, -1L);
            questState.giveItems(1658, 2L);
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        questState.rollAndGive(1572, 1, 1, 20, 33.0);
        if (questState.getQuestItemsCount(1572) >= 20L) {
            questState.setCond(2);
        }
        return null;
    }
}
