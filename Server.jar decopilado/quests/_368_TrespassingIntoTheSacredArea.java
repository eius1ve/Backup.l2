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

public class _368_TrespassingIntoTheSacredArea
extends Quest
implements ScriptFile {
    private static final int aPm = 30926;
    private static final int aPn = 20794;
    private static final int aPo = 20795;
    private static final int aPp = 20796;
    private static final int aPq = 20797;
    private static final int aPr = 5881;

    public _368_TrespassingIntoTheSacredArea() {
        super(2);
        this.addStartNpc(30926);
        this.addKillId(new int[]{20794, 20795, 20796, 20797});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30926) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "priestess_restina_q0368_03.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
                string2 = "priestess_restina_q0368_06.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "priestess_restina_q0368_07.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 30926) break;
                if (questState.getPlayer().getLevel() < 36 || questState.getPlayer().getLevel() > 48) {
                    string = "priestess_restina_q0368_02.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "priestess_restina_q0368_01.htm";
                break;
            }
            case 2: {
                if (n != 30926) break;
                if (questState.getQuestItemsCount(5881) < 1L) {
                    string = "priestess_restina_q0368_04.htm";
                    break;
                }
                if (questState.getQuestItemsCount(5881) < 1L) break;
                if (questState.getQuestItemsCount(5881) >= 10L) {
                    questState.giveItems(57, questState.getQuestItemsCount(5881) * 250L + 9450L);
                } else {
                    questState.giveItems(57, questState.getQuestItemsCount(5881) * 250L + 2000L);
                }
                this.giveExtraReward(questState.getPlayer());
                questState.takeItems(5881, -1L);
                string = "priestess_restina_q0368_05.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20794) {
            questState.rollAndGive(5881, 1, 60.0);
        } else if (n == 20795) {
            questState.rollAndGive(5881, 1, 57.0);
        } else if (n == 20796) {
            questState.rollAndGive(5881, 1, 61.0);
        } else if (n == 20797) {
            questState.rollAndGive(5881, 1, 93.0);
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
