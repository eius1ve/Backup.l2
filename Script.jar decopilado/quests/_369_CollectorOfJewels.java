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

public class _369_CollectorOfJewels
extends Quest
implements ScriptFile {
    private static int aPs = 30376;
    private static final int aPt = 20609;
    private static final int aPu = 20612;
    private static final int aPv = 20616;
    private static final int aPw = 20619;
    private static final int aPx = 20747;
    private static final int aPy = 20749;
    private static final int aPz = 5882;
    private static final int aPA = 5883;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _369_CollectorOfJewels() {
        super(0);
        this.addStartNpc(aPs);
        this.addKillId(new int[]{20609, 20612, 20616, 20619, 20747, 20749});
        this.addQuestItem(new int[]{5882, 5883});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == aPs) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("man_collect_element", String.valueOf(1), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "magister_nell_q0369_03.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                questState.setCond(3);
                questState.set("man_collect_element", String.valueOf(3), true);
                string2 = "magister_nell_q0369_07.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                questState.takeItems(5882, -1L);
                questState.takeItems(5883, -1L);
                questState.unset("man_collect_element");
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
                string2 = "magister_nell_q0369_08.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("man_collect_element");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != aPs) break;
                if (questState.getPlayer().getLevel() < 25) {
                    string = "magister_nell_q0369_01.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "magister_nell_q0369_02.htm";
                break;
            }
            case 2: {
                if (n2 != aPs) break;
                if ((questState.getQuestItemsCount(5883) < 50L || questState.getQuestItemsCount(5882) < 50L) && n == 1) {
                    string = "magister_nell_q0369_04.htm";
                    break;
                }
                if (questState.getQuestItemsCount(5883) >= 50L && questState.getQuestItemsCount(5882) >= 50L && n == 1) {
                    questState.giveItems(57, 12500L);
                    questState.takeItems(5882, -1L);
                    questState.takeItems(5883, -1L);
                    questState.set("man_collect_element", String.valueOf(2), true);
                    string = "magister_nell_q0369_05.htm";
                    break;
                }
                if (n == 2) {
                    string = "magister_nell_q0369_09.htm";
                    break;
                }
                if (n == 3 && (questState.getQuestItemsCount(5883) < 200L || questState.getQuestItemsCount(5882) < 200L)) {
                    string = "magister_nell_q0369_10.htm";
                    break;
                }
                if (n != 3 || questState.getQuestItemsCount(5883) < 200L || questState.getQuestItemsCount(5882) < 200L) break;
                questState.giveItems(57, 76000L);
                questState.takeItems(5882, -1L);
                questState.takeItems(5883, -1L);
                questState.unset("man_collect_element");
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
                string = "magister_nell_q0369_11.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("man_collect_element");
        int n2 = npcInstance.getNpcId();
        if (n2 == 20749) {
            if (Rnd.get((int)100) < 2) {
                if (n == 1 && questState.getQuestItemsCount(5882) >= 49L) {
                    questState.giveItems(5882, 1L);
                } else if (n == 3 && questState.getQuestItemsCount(5882) >= 199L) {
                    questState.giveItems(5882, 1L);
                } else {
                    questState.giveItems(5882, 2L);
                }
                if (n == 1 && questState.getQuestItemsCount(5883) >= 50L && questState.getQuestItemsCount(5882) >= 49L) {
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                } else if (n == 3 && questState.getQuestItemsCount(5883) >= 200L && questState.getQuestItemsCount(5882) >= 199L) {
                    questState.setCond(4);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else {
                questState.rollAndGive(5882, 1, 100.0);
                if (n == 1 && questState.getQuestItemsCount(5883) >= 50L && questState.getQuestItemsCount(5882) >= 49L) {
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                } else if (n == 3 && questState.getQuestItemsCount(5883) >= 200L && questState.getQuestItemsCount(5882) >= 199L) {
                    questState.setCond(4);
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n2 == 20609 && Rnd.get((int)100) < 75) {
            questState.rollAndGive(5882, 1, 100.0);
            if (n == 1 && questState.getQuestItemsCount(5883) >= 50L && questState.getQuestItemsCount(5882) >= 49L) {
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            } else if (n == 3 && questState.getQuestItemsCount(5883) >= 200L && questState.getQuestItemsCount(5882) >= 199L) {
                questState.setCond(4);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n2 == 20612 && Rnd.get((int)100) < 91) {
            questState.rollAndGive(5882, 1, 100.0);
            if (n == 1 && questState.getQuestItemsCount(5883) >= 50L && questState.getQuestItemsCount(5882) >= 49L) {
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            } else if (n == 3 && questState.getQuestItemsCount(5883) >= 200L && questState.getQuestItemsCount(5882) >= 199L) {
                questState.setCond(4);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n2 == 20747) {
            if (Rnd.get((int)100) < 2) {
                if (n == 1 && questState.getQuestItemsCount(5883) >= 49L) {
                    questState.giveItems(5883, 1L);
                } else if (n == 3 && questState.getQuestItemsCount(5883) >= 199L) {
                    questState.giveItems(5883, 1L);
                } else {
                    questState.giveItems(5883, 2L);
                }
                if (n == 1 && questState.getQuestItemsCount(5883) >= 49L && questState.getQuestItemsCount(5882) >= 50L) {
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                } else if (n == 3 && questState.getQuestItemsCount(5883) >= 199L && questState.getQuestItemsCount(5882) >= 200L) {
                    questState.setCond(4);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else {
                questState.rollAndGive(5883, 1, 100.0);
                if (n == 1 && questState.getQuestItemsCount(5883) >= 49L && questState.getQuestItemsCount(5882) >= 50L) {
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                } else if (n == 3 && questState.getQuestItemsCount(5883) >= 199L && questState.getQuestItemsCount(5882) >= 200L) {
                    questState.setCond(4);
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        } else if (n2 == 20616 && Rnd.get((int)100) < 80) {
            questState.rollAndGive(5883, 1, 100.0);
            if (n == 1 && questState.getQuestItemsCount(5883) >= 49L && questState.getQuestItemsCount(5882) >= 50L) {
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            } else if (n == 3 && questState.getQuestItemsCount(5883) >= 199L && questState.getQuestItemsCount(5882) >= 200L) {
                questState.setCond(4);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n2 == 20619 && Rnd.get((int)100) < 87) {
            questState.rollAndGive(5883, 1, 100.0);
            if (n == 1 && questState.getQuestItemsCount(5883) >= 49L && questState.getQuestItemsCount(5882) >= 50L) {
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            } else if (n == 3 && questState.getQuestItemsCount(5883) >= 199L && questState.getQuestItemsCount(5882) >= 200L) {
                questState.setCond(4);
                questState.playSound("ItemSound.quest_middle");
            }
        }
        return null;
    }
}
