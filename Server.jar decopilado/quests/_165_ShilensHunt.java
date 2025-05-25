/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _165_ShilensHunt
extends Quest
implements ScriptFile {
    private static final int UF = 1160;
    private static final int UG = 1060;
    private static final int UH = 30348;
    private static final int UI = 20456;
    private static final int UJ = 20529;
    private static final int UK = 20532;
    private static final int UL = 20536;

    public _165_ShilensHunt() {
        super(0);
        this.addStartNpc(30348);
        this.addKillId(new int[]{20456, 20529, 20532, 20536});
        this.addQuestItem(new int[]{1160});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "sentry_nelsya_q0321_03.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getState();
        Player player = questState.getPlayer();
        switch (n) {
            case 1: {
                if (player.getRace() != Race.darkelf) {
                    string = "sentry_nelsya_q0321_00.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (player.getLevel() >= 3) {
                    string = "sentry_nelsya_q0321_02.htm";
                    break;
                }
                string = "sentry_nelsya_q0321_01.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (questState.getQuestItemsCount(1160) < 13L) {
                    string = "sentry_nelsya_q0321_04.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1160) < 13L) break;
                string = "sentry_nelsya_q0321_05.htm";
                questState.takeItems(1160, -1L);
                questState.giveItems(1060, 5L);
                questState.exitCurrentQuest(false);
                this.giveExtraReward(questState.getPlayer());
                questState.playSound("ItemSound.quest_finish");
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20456 && Rnd.get((int)3) < 3 && questState.getCond() == 1) {
            questState.rollAndGive(1160, 1, 100.0);
            if (questState.getQuestItemsCount(1160) >= 13L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(2);
            }
        } else if (n == 20529 && Rnd.get((int)3) < 1 && questState.getCond() == 1) {
            questState.rollAndGive(1160, 1, 100.0);
            if (questState.getQuestItemsCount(1160) >= 13L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(2);
            }
        } else if (n == 20532 && Rnd.get((int)3) < 1 && questState.getCond() == 1) {
            questState.rollAndGive(1160, 1, 100.0);
            if (questState.getQuestItemsCount(1160) >= 13L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(2);
            }
        } else if (n == 20536 && Rnd.get((int)3) < 2 && questState.getCond() == 1) {
            questState.rollAndGive(1160, 1, 100.0);
            if (questState.getQuestItemsCount(1160) >= 13L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(2);
            }
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
