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

public class _360_PlunderTheirSupplies
extends Quest
implements ScriptFile {
    private static final int aNW = 30873;
    private static final int aNX = 20666;
    private static final int aNY = 20669;
    private static final int aNZ = 5872;
    private static final int aOa = 5871;
    private static final int aOb = 5870;
    private static final int aOc = 50;
    private static final int aOd = 65;
    private static final int aOe = 5;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _360_PlunderTheirSupplies() {
        super(0);
        this.addStartNpc(30873);
        this.addKillId(new int[]{20666});
        this.addKillId(new int[]{20669});
        this.addQuestItem(new int[]{5872});
        this.addQuestItem(new int[]{5871});
        this.addQuestItem(new int[]{5870});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("guard_coleman_q0360_04.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("guard_coleman_q0360_10.htm")) {
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getState();
        long l = questState.getQuestItemsCount(5870);
        long l2 = questState.getQuestItemsCount(5872);
        if (n != 2) {
            string = questState.getPlayer().getLevel() >= 52 ? "guard_coleman_q0360_02.htm" : "guard_coleman_q0360_01.htm";
        } else if (l > 0L || l2 > 0L) {
            long l3 = 6000L + l2 * 100L + l * 6000L;
            questState.takeItems(5872, -1L);
            questState.takeItems(5870, -1L);
            questState.giveItems(57, l3);
            this.giveExtraReward(questState.getPlayer());
            string = "guard_coleman_q0360_08.htm";
        } else {
            string = "guard_coleman_q0360_05.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20666 && Rnd.chance((int)50) || n == 20669 && Rnd.chance((int)65)) {
            questState.giveItems(5872, 1L);
            questState.playSound("ItemSound.quest_itemget");
        }
        if (Rnd.chance((int)5)) {
            if (questState.getQuestItemsCount(5871) < 4L) {
                questState.giveItems(5871, 1L);
            } else {
                questState.takeItems(5871, -1L);
                questState.giveItems(5870, 1L);
            }
            questState.playSound("ItemSound.quest_itemget");
        }
        return null;
    }
}
