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

public class _364_JovialAccordion
extends Quest
implements ScriptFile {
    private static final int aOx = 30959;
    private static final int aOy = 30957;
    private static final int aOz = 30060;
    private static final int aOA = 30960;
    private static final int aOB = 30961;
    private static final int aOC = 30075;
    private static final int aOD = 4324;
    private static final int aOE = 4323;
    private static final int aOF = 4321;
    private static final int aOG = 4421;
    private static final int aOH = 4322;

    public _364_JovialAccordion() {
        super(0);
        this.addStartNpc(30959);
        this.addTalkId(new int[]{30957, 30060, 30075, 30960, 30961});
        this.addQuestItem(new int[]{4323, 4324, 4321, 4322});
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        int n3 = questState.getInt("jovial_accordion");
        switch (n2) {
            case 1: {
                if (n != 30959) break;
                string = "barbado_q0364_01.htm";
                break;
            }
            case 2: {
                if (n == 30959) {
                    if (n3 >= 1 && n3 < 5) {
                        string = "barbado_q0364_04.htm";
                        break;
                    }
                    if (n3 != 5) break;
                    questState.giveItems(4421, 1L);
                    this.giveExtraReward(questState.getPlayer());
                    questState.playSound("ItemSound.quest_finish");
                    questState.exitCurrentQuest(false);
                    string = "barbado_q0364_05.htm";
                    break;
                }
                if (n == 30957) {
                    if (n3 == 1) {
                        string = "swan_q0364_01.htm";
                        break;
                    }
                    if (!(n3 != 2 && n3 != 3 || questState.getQuestItemsCount(4324) != 1L && questState.getQuestItemsCount(4323) != 1L && questState.getQuestItemsCount(4322) != 1L && questState.getQuestItemsCount(4321) != 1L)) {
                        string = "swan_q0364_03.htm";
                        break;
                    }
                    if (n3 == 4 && questState.getQuestItemsCount(4324) == 0L && questState.getQuestItemsCount(4323) == 0L && questState.getQuestItemsCount(4321) == 0L && questState.getQuestItemsCount(4322) == 0L) {
                        string = "swan_q0364_04.htm";
                        questState.set("jovial_accordion", 5);
                        questState.setCond(3);
                        questState.playSound("ItemSound.quest_middle");
                        questState.giveItems(57, 100L);
                        break;
                    }
                    if (n3 == 3 && questState.getQuestItemsCount(4324) == 0L && questState.getQuestItemsCount(4323) == 0L && questState.getQuestItemsCount(4321) == 0L && questState.getQuestItemsCount(4322) == 0L) {
                        string = "swan_q0364_05.htm";
                        questState.set("jovial_accordion", 5);
                        questState.setCond(3);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (n3 == 2 && questState.getQuestItemsCount(4324) == 0L && questState.getQuestItemsCount(4323) == 0L && questState.getQuestItemsCount(4322) == 0L && questState.getQuestItemsCount(4321) == 0L) {
                        string = "swan_q0364_06.htm";
                        questState.playSound("ItemSound.quest_giveup");
                        questState.exitCurrentQuest(true);
                        break;
                    }
                    if (n3 < 5) break;
                    string = "swan_q0364_07.htm";
                    break;
                }
                if (n == 30060) {
                    if (questState.getQuestItemsCount(4321) >= 1L) {
                        string = "sabrin_q0364_01.htm";
                        questState.takeItems(4321, 1L);
                        if (n3 == 3) {
                            questState.set("jovial_accordion", 4);
                            break;
                        }
                        if (n3 != 2) break;
                        questState.set("jovial_accordion", 3);
                        break;
                    }
                    if (questState.getQuestItemsCount(4321) != 0L) break;
                    string = "sabrin_q0364_02.htm";
                    break;
                }
                if (n == 30075) {
                    if (questState.getQuestItemsCount(4322) >= 1L) {
                        string = "xaber_q0364_01.htm";
                        questState.takeItems(4322, 1L);
                        if (n3 == 3) {
                            questState.set("jovial_accordion", 4);
                            break;
                        }
                        if (n3 != 2) break;
                        questState.set("jovial_accordion", 3);
                        break;
                    }
                    if (questState.getQuestItemsCount(4322) != 0L) break;
                    string = "xaber_q0364_02.htm";
                    break;
                }
                if (n == 30960) {
                    string = "beer_chest_q0364_01.htm";
                    break;
                }
                if (n != 30961) break;
                string = "cloth_chest_q0364_01.htm";
            }
        }
        return string;
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30959) {
            if (string.equalsIgnoreCase("quest_accept")) {
                if (questState.getPlayer().getLevel() >= 15) {
                    questState.playSound("ItemSound.quest_accept");
                    string2 = "barbado_q0364_02.htm";
                    questState.setCond(1);
                    questState.set("jovial_accordion", 1);
                    questState.setState(2);
                } else {
                    string2 = "barbado_q0364_03.htm";
                    questState.exitCurrentQuest(true);
                }
            }
        } else if (n == 30957) {
            if (string.equalsIgnoreCase("reply_1")) {
                questState.giveItems(4323, 1L);
                questState.giveItems(4324, 1L);
                questState.set("jovial_accordion", 2);
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
                string2 = "swan_q0364_02.htm";
            }
        } else if (n == 30960) {
            if (string.equalsIgnoreCase("reply_1")) {
                if (questState.getQuestItemsCount(4324) == 1L) {
                    if (Rnd.get((int)2) == 0) {
                        questState.giveItems(4321, 1L);
                        questState.takeItems(4324, 1L);
                        string2 = "beer_chest_q0364_02.htm";
                    } else {
                        questState.takeItems(4324, 1L);
                        string2 = "beer_chest_q0364_04.htm";
                    }
                } else {
                    string2 = "beer_chest_q0364_03.htm";
                }
            }
        } else if (n == 30961 && string.equalsIgnoreCase("reply_1")) {
            if (questState.getQuestItemsCount(4323) == 1L) {
                if (Rnd.get((int)2) == 0) {
                    questState.giveItems(4322, 1L);
                    questState.takeItems(4323, 1L);
                    string2 = "cloth_chest_q0364_02.htm";
                } else {
                    questState.takeItems(4323, 1L);
                    string2 = "cloth_chest_q0364_04.htm";
                }
            } else {
                string2 = "cloth_chest_q0364_03.htm";
            }
        }
        return string2;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
