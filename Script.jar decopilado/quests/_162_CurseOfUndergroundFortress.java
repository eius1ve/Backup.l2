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

public class _162_CurseOfUndergroundFortress
extends Quest
implements ScriptFile {
    private static final int Ul = 30147;
    private static final int Um = 20033;
    private static final int Un = 20345;
    private static final int Uo = 20371;
    private static final int Up = 20463;
    private static final int Uq = 20464;
    private static final int Ur = 20504;
    private static final int Us = 1158;
    private static final int Ut = 1159;
    private static final int Uu = 625;

    public _162_CurseOfUndergroundFortress() {
        super(0);
        this.addStartNpc(30147);
        this.addKillId(new int[]{20033, 20345, 20371, 20463, 20464, 20504});
        this.addQuestItem(new int[]{1159, 1158});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "uno_q0314_04.htm";
        } else if (string.equalsIgnoreCase("reply_1")) {
            string2 = "uno_q0314_03.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getState();
        Player player = questState.getPlayer();
        switch (n) {
            case 1: {
                if (player.getRace() == Race.darkelf) {
                    string = "uno_q0314_00.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (player.getLevel() >= 12) {
                    string = "uno_q0314_02.htm";
                    break;
                }
                string = "uno_q0314_01.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (questState.getQuestItemsCount(1159) + questState.getQuestItemsCount(1158) < 13L) {
                    string = "uno_q0314_05.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1159) + questState.getQuestItemsCount(1158) < 13L) break;
                string = "uno_q0314_06.htm";
                questState.giveItems(625, 1L);
                questState.giveItems(57, 24000L);
                questState.takeItems(1159, -1L);
                questState.takeItems(1158, -1L);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(false);
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20033 && Rnd.get((int)100) < 25 && questState.getQuestItemsCount(1159) < 3L) {
            questState.rollAndGive(1159, 1, 100.0);
            if (questState.getQuestItemsCount(1159) >= 2L) {
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(1158) >= 10L) {
                    questState.setCond(2);
                }
            }
        } else if (n == 20345 && Rnd.get((int)100) < 26 && questState.getQuestItemsCount(1159) < 3L) {
            questState.rollAndGive(1159, 1, 100.0);
            if (questState.getQuestItemsCount(1159) >= 2L) {
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(1158) >= 10L) {
                    questState.setCond(2);
                }
            }
        } else if (n == 20371 && Rnd.get((int)100) < 23 && questState.getQuestItemsCount(1159) < 3L) {
            questState.rollAndGive(1159, 1, 100.0);
            if (questState.getQuestItemsCount(1159) >= 2L) {
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(1158) >= 10L) {
                    questState.setCond(2);
                }
            }
        } else if (n == 20463 && Rnd.get((int)4) == 1) {
            questState.rollAndGive(1158, 1, 100.0);
            if (questState.getQuestItemsCount(1158) >= 10L) {
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(1159) >= 3L) {
                    questState.setCond(2);
                }
            }
        } else if (n == 20464 && Rnd.get((int)100) < 23) {
            questState.rollAndGive(1158, 1, 100.0);
            if (questState.getQuestItemsCount(1158) >= 10L) {
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(1159) >= 10L) {
                    questState.setCond(2);
                }
            }
        } else if (n == 20504 && Rnd.get((int)100) < 26) {
            questState.rollAndGive(1158, 1, 100.0);
            if (questState.getQuestItemsCount(1158) >= 10L) {
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(1159) >= 3L) {
                    questState.setCond(2);
                }
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
