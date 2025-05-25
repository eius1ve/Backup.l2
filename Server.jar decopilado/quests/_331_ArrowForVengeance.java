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

public class _331_ArrowForVengeance
extends Quest
implements ScriptFile {
    private static final int azd = 1452;
    private static final int aze = 1453;
    private static final int azf = 1454;
    private static final int azg = 20145;
    private static final int azh = 20158;
    private static final int azi = 20176;
    private static final int azj = 30125;

    public _331_ArrowForVengeance() {
        super(0);
        this.addStartNpc(30125);
        this.addKillId(new int[]{20145, 20158, 20176});
        this.addQuestItem(new int[]{1452, 1453, 1454});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "beltkem_q0331_03.htm";
        } else if (string.equalsIgnoreCase("reply_1")) {
            string2 = "beltkem_q0331_06.htm";
            questState.exitCurrentQuest(true);
            questState.playSound("ItemSound.quest_finish");
        } else if (string.equalsIgnoreCase("reply_2")) {
            string2 = "beltkem_q0331_07.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 30125) break;
                if (questState.getPlayer().getLevel() >= 32) {
                    string = "beltkem_q0331_02.htm";
                    break;
                }
                string = "beltkem_q0331_01.htm";
                break;
            }
            case 2: {
                if (n != 30125) break;
                if (questState.getQuestItemsCount(1452) + questState.getQuestItemsCount(1453) + questState.getQuestItemsCount(1454) > 0L) {
                    if (questState.getQuestItemsCount(1452) + questState.getQuestItemsCount(1453) + questState.getQuestItemsCount(1454) >= 10L) {
                        questState.giveItems(57, 3100L + 78L * questState.getQuestItemsCount(1452) + 88L * questState.getQuestItemsCount(1453) + 92L * questState.getQuestItemsCount(1454));
                    } else {
                        questState.giveItems(57, 78L * questState.getQuestItemsCount(1452) + 88L * questState.getQuestItemsCount(1453) + 92L * questState.getQuestItemsCount(1454));
                    }
                    this.giveExtraReward(questState.getPlayer());
                    questState.takeItems(1452, -1L);
                    questState.takeItems(1453, -1L);
                    questState.takeItems(1454, -1L);
                    string = "beltkem_q0331_05.htm";
                    break;
                }
                string = "beltkem_q0331_04.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20145 && Rnd.get((int)100) < 59) {
            questState.rollAndGive(1452, 1, 100.0);
        } else if (n == 20158 && Rnd.get((int)100) < 61) {
            questState.rollAndGive(1453, 1, 100.0);
        } else if (n == 20176 && Rnd.get((int)100) < 60) {
            questState.rollAndGive(1454, 1, 100.0);
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
