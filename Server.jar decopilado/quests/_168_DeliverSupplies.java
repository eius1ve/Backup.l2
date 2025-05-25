/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.Player;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _168_DeliverSupplies
extends Quest
implements ScriptFile {
    private static final int UZ = 30349;
    private static final int Va = 30355;
    private static final int Vb = 30357;
    private static final int Vc = 30360;
    private static final int Vd = 1153;
    private static final int Ve = 1154;
    private static final int Vf = 1155;
    private static final int Vg = 1156;
    private static final int Vh = 1157;

    public _168_DeliverSupplies() {
        super(0);
        this.addStartNpc(30349);
        this.addTalkId(new int[]{30355, 30357, 30360});
        this.addQuestItem(new int[]{1153, 1154, 1155, 1156, 1157});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "sentry_jenine_q0325_03.htm";
            questState.giveItems(1153, 1L);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        Player player = questState.getPlayer();
        switch (n2) {
            case 1: {
                if (n != 30349) break;
                if (player.getRace() != Race.darkelf) {
                    string = "sentry_jenine_q0325_00.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (player.getLevel() >= 3) {
                    string = "sentry_jenine_q0325_02.htm";
                    break;
                }
                string = "sentry_jenine_q0325_01.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n == 30349) {
                    if (questState.getQuestItemsCount(1153) >= 1L) {
                        string = "sentry_jenine_q0325_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1154) == 1L && questState.getQuestItemsCount(1155) == 1L && questState.getQuestItemsCount(1156) == 1L) {
                        string = "sentry_jenine_q0325_05.htm";
                        questState.takeItems(1154, 1L);
                        questState.setCond(3);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(1154) == 0L && (questState.getQuestItemsCount(1155) == 1L || questState.getQuestItemsCount(1156) == 1L)) {
                        string = "sentry_jenine_q0325_07.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1157) != 2L) break;
                    string = "sentry_jenine_q0325_06.htm";
                    questState.takeItems(1157, 2L);
                    questState.giveItems(57, 820L);
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(false);
                    break;
                }
                if (n == 30355) {
                    if (questState.getQuestItemsCount(1155) == 1L && questState.getQuestItemsCount(1154) == 0L) {
                        string = "sentry_roseline_q0325_01.htm";
                        questState.takeItems(1155, 1L);
                        questState.giveItems(1157, 1L);
                        if (questState.getQuestItemsCount(1156) != 0L) break;
                        questState.setCond(4);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(1155) != 0L || questState.getQuestItemsCount(1157) < 1L) break;
                    string = "sentry_roseline_q0325_02.htm";
                    break;
                }
                if (n == 30357) {
                    if (questState.getQuestItemsCount(1156) == 1L && questState.getQuestItemsCount(1154) == 0L) {
                        string = "sentry_krpion_q0325_01.htm";
                        questState.takeItems(1156, 1L);
                        questState.giveItems(1157, 1L);
                        if (questState.getQuestItemsCount(1155) != 0L) break;
                        questState.setCond(4);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(1156) != 0L || questState.getQuestItemsCount(1157) < 1L) break;
                    string = "sentry_krpion_q0325_02.htm";
                    break;
                }
                if (n != 30360) break;
                if (questState.getQuestItemsCount(1153) == 1L) {
                    string = "master_harant_q0325_01.htm";
                    questState.takeItems(1153, 1L);
                    questState.giveItems(1154, 1L);
                    questState.giveItems(1155, 1L);
                    questState.giveItems(1156, 1L);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (questState.getQuestItemsCount(1154) + questState.getQuestItemsCount(1155) + questState.getQuestItemsCount(1156) <= 0L) break;
                string = "master_harant_q0325_02.htm";
            }
        }
        return string;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
