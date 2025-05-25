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

public class _354_ConquestofAlligatorIsland
extends Quest
implements ScriptFile {
    private static final int aMN = 30895;
    private static final int aMO = 20804;
    private static final int aMP = 20805;
    private static final int aMQ = 20806;
    private static final int aMR = 20807;
    private static final int aMS = 20808;
    private static final int aMT = 20991;
    private static final int aMU = 5863;
    private static final int aMV = 5864;
    private static final int aMW = 5915;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _354_ConquestofAlligatorIsland() {
        super(0);
        this.addStartNpc(30895);
        this.addKillId(new int[]{20804, 20805, 20806, 20807, 20808, 20991});
        this.addQuestItem(new int[]{5863, 5864});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30895) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "warehouse_keeper_kluck_q0354_03.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                if (questState.getQuestItemsCount(5863) >= 100L) {
                    questState.giveItems(57, questState.getQuestItemsCount(5863) * 220L + 10700L);
                    questState.takeItems(5863, -1L);
                    string2 = "warehouse_keeper_kluck_q0354_06b.htm";
                } else if (questState.getQuestItemsCount(5863) > 0L && questState.getQuestItemsCount(5863) < 100L) {
                    questState.giveItems(57, questState.getQuestItemsCount(5863) * 220L + 3100L);
                    questState.takeItems(5863, questState.getQuestItemsCount(5863));
                    string2 = "warehouse_keeper_kluck_q0354_06a.htm";
                } else if (questState.getQuestItemsCount(5863) == 0L) {
                    string2 = "warehouse_keeper_kluck_q0354_06.htm";
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "warehouse_keeper_kluck_q0354_07.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
                string2 = "warehouse_keeper_kluck_q0354_08.htm";
            } else if (string.equalsIgnoreCase("reply_4") && questState.getQuestItemsCount(5864) > 0L && questState.getQuestItemsCount(5864) < 10L) {
                string2 = "warehouse_keeper_kluck_q0354_09.htm";
            } else if (string.equalsIgnoreCase("reply_4") && questState.getQuestItemsCount(5864) >= 10L) {
                questState.giveItems(5915, 1L);
                questState.takeItems(5864, 10L);
                this.giveExtraReward(questState.getPlayer());
                string2 = "warehouse_keeper_kluck_q0354_10.htm";
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
                if (n != 30895) break;
                if (questState.getPlayer().getLevel() < 38) {
                    string = "warehouse_keeper_kluck_q0354_01.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "warehouse_keeper_kluck_q0354_02.htm";
                break;
            }
            case 2: {
                if (n != 30895) break;
                if (questState.getQuestItemsCount(5864) == 0L) {
                    string = "warehouse_keeper_kluck_q0354_04.htm";
                    break;
                }
                if (questState.getQuestItemsCount(5864) < 1L) break;
                string = "warehouse_keeper_kluck_q0354_05.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20804) {
            if (Rnd.get((int)100) < 84) {
                questState.giveItems(5863, 1L);
                questState.playSound("ItemSound.quest_itemget");
            }
            if (Rnd.get((int)10) == 5) {
                questState.giveItems(5864, 1L);
            }
        } else if (n == 20805) {
            if (Rnd.get((int)100) < 91) {
                questState.giveItems(5863, 1L);
                questState.playSound("ItemSound.quest_itemget");
            }
            if (Rnd.get((int)10) == 5) {
                questState.giveItems(5864, 1L);
            }
        } else if (n == 20806) {
            if (Rnd.get((int)100) < 88) {
                questState.giveItems(5863, 1L);
                questState.playSound("ItemSound.quest_itemget");
            }
            if (Rnd.get((int)10) == 5) {
                questState.giveItems(5864, 1L);
            }
        } else if (n == 20807) {
            if (Rnd.get((int)100) < 92) {
                questState.giveItems(5863, 1L);
                questState.playSound("ItemSound.quest_itemget");
            }
            if (Rnd.get((int)10) == 5) {
                questState.giveItems(5864, 1L);
            }
        } else if (n == 20808) {
            if (Rnd.get((int)100) < 14) {
                questState.giveItems(5863, 2L);
                questState.playSound("ItemSound.quest_itemget");
            } else {
                questState.giveItems(5863, 1L);
                questState.playSound("ItemSound.quest_itemget");
            }
            if (Rnd.get((int)10) == 5) {
                questState.giveItems(5864, 1L);
            }
        } else if (n == 20991) {
            if (Rnd.get((int)100) < 69) {
                questState.giveItems(5863, 2L);
                questState.playSound("ItemSound.quest_itemget");
            } else {
                questState.giveItems(5863, 1L);
                questState.playSound("ItemSound.quest_itemget");
            }
            if (Rnd.get((int)10) == 5) {
                questState.giveItems(5864, 1L);
            }
        }
        return null;
    }
}
