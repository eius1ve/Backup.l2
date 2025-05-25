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

public class _320_BonesTellFuture
extends Quest
implements ScriptFile {
    private static final int awK = 809;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _320_BonesTellFuture() {
        super(0);
        this.addStartNpc(30359);
        this.addTalkId(new int[]{30359});
        this.addKillId(new int[]{20517});
        this.addKillId(new int[]{20518});
        this.addQuestItem(new int[]{809});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("tetrarch_kaitar_q0320_04.htm")) {
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
            if (questState.getPlayer().getRace() != Race.darkelf) {
                string = "tetrarch_kaitar_q0320_00.htm";
                questState.exitCurrentQuest(true);
            } else if (questState.getPlayer().getLevel() >= 10) {
                string = "tetrarch_kaitar_q0320_03.htm";
            } else {
                string = "tetrarch_kaitar_q0320_02.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (questState.getQuestItemsCount(809) < 10L) {
            string = "tetrarch_kaitar_q0320_05.htm";
        } else {
            string = "tetrarch_kaitar_q0320_06.htm";
            questState.giveItems(57, 8470L, true);
            questState.takeItems(809, -1L);
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
            questState.unset("cond");
            questState.playSound("ItemSound.quest_finish");
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        questState.rollAndGive(809, 1, 1, 10, 10.0);
        if (questState.getQuestItemsCount(809) >= 10L) {
            questState.setCond(2);
        }
        questState.setState(2);
        return null;
    }
}
