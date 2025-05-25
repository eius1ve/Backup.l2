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

public class _262_TradewiththeIvoryTower
extends Quest
implements ScriptFile {
    private static final int atU = 30137;
    private static final int atV = 20007;
    private static final int atW = 20400;
    private static final int atX = 707;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _262_TradewiththeIvoryTower() {
        super(0);
        this.addStartNpc(30137);
        this.addKillId(new int[]{20400, 20007});
        this.addQuestItem(new int[]{707});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("vollodos_q0262_03.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        if (n == 0) {
            if (questState.getPlayer().getLevel() >= 8) {
                string = "vollodos_q0262_02.htm";
                return string;
            }
            string = "vollodos_q0262_01.htm";
            questState.exitCurrentQuest(true);
        } else if (n == 1 && questState.getQuestItemsCount(707) < 10L) {
            string = "vollodos_q0262_04.htm";
        } else if (n == 2 && questState.getQuestItemsCount(707) >= 10L) {
            questState.giveItems(57, 3000L);
            questState.takeItems(707, -1L);
            questState.setCond(0);
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            string = "vollodos_q0262_05.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = Rnd.get((int)10);
        if (questState.getCond() == 1 && questState.getQuestItemsCount(707) < 10L && (n == 20007 && n2 < 3 || n == 20400 && n2 < 4)) {
            questState.giveItems(707, 1L);
            if (questState.getQuestItemsCount(707) == 10L) {
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            } else {
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
