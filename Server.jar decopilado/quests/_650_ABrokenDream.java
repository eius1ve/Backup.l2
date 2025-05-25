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
import quests._117_OceanOfDistantStar;

public class _650_ABrokenDream
extends Quest
implements ScriptFile {
    private static final int bCg = 32054;
    private static final int bCh = 22027;
    private static final int bCi = 22028;
    private static final int bCj = 8514;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _650_ABrokenDream() {
        super(0);
        this.addStartNpc(32054);
        this.addKillId(new int[]{22027});
        this.addKillId(new int[]{22028});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            string2 = "ghost_of_railroadman_q0650_0103.htm";
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            questState.setCond(1);
        } else if (string.equalsIgnoreCase("650_4")) {
            string2 = "ghost_of_railroadman_q0650_0205.htm";
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
            questState.unset("cond");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getCond();
        String string = "noquest";
        if (n == 0) {
            QuestState questState2 = questState.getPlayer().getQuestState(_117_OceanOfDistantStar.class);
            if (questState2 != null) {
                if (questState2.isCompleted()) {
                    if (questState.getPlayer().getLevel() < 39) {
                        questState.exitCurrentQuest(true);
                        string = "ghost_of_railroadman_q0650_0102.htm";
                    } else {
                        string = "ghost_of_railroadman_q0650_0101.htm";
                    }
                } else {
                    string = "ghost_of_railroadman_q0650_0104.htm";
                    questState.exitCurrentQuest(true);
                }
            } else {
                string = "ghost_of_railroadman_q0650_0104.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (n == 1) {
            string = "ghost_of_railroadman_q0650_0202.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        questState.rollAndGive(8514, 1, 1, 68.0);
        return null;
    }
}
