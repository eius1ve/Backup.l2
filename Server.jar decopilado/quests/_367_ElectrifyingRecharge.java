/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.SkillTable
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.SkillTable;

public class _367_ElectrifyingRecharge
extends Quest
implements ScriptFile {
    private static final int aOR = 30673;
    private static final int aOS = 21035;
    private static final int aOT = 5875;
    private static final int aOU = 5876;
    private static final int aOV = 5877;
    private static final int aOW = 5878;
    private static final int aOX = 5879;
    private static final int aOY = 5880;
    private static final int aOZ = 4553;
    private static final int aPa = 4554;
    private static final int aPb = 4555;
    private static final int aPc = 4556;
    private static final int aPd = 4557;
    private static final int aPe = 4558;
    private static final int aPf = 4559;
    private static final int aPg = 4560;
    private static final int aPh = 4561;
    private static final int aPi = 4562;
    private static final int aPj = 4563;
    private static final int aPk = 4564;
    private static final int aPl = 4445;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _367_ElectrifyingRecharge() {
        super(1);
        this.addStartNpc(30673);
        this.addAttackId(new int[]{21035});
        this.addQuestItem(new int[]{5875, 5876, 5877, 5878, 5879, 5880});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30673) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("Buzz_Buzz_Charging_adventure", String.valueOf(1), true);
                questState.giveItems(5875, 1L);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "researcher_lorain_q0367_03.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=367&reply=1")) {
                string2 = "researcher_lorain_q0367_07.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=367&reply=2")) {
                questState.takeItems(5875, -1L);
                questState.takeItems(5876, -1L);
                questState.takeItems(5877, -1L);
                questState.takeItems(5878, -1L);
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
                string2 = "researcher_lorain_q0367_08.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=367&reply=3")) {
                string2 = "researcher_lorain_q0367_09.htm";
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
                if (n != 30673) break;
                if (questState.getPlayer().getLevel() < 37 || questState.getPlayer().getLevel() > 47) {
                    string = "researcher_lorain_q0367_02.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                questState.exitCurrentQuest(true);
                string = "researcher_lorain_q0367_01.htm";
                break;
            }
            case 2: {
                if (n != 30673) break;
                if (questState.getQuestItemsCount(5879) == 0L && questState.getQuestItemsCount(5880) == 0L) {
                    string = "researcher_lorain_q0367_04.htm";
                    break;
                }
                if (questState.getQuestItemsCount(5880) > 0L) {
                    questState.giveItems(5875, 1L);
                    questState.takeItems(5880, -1L);
                    string = "researcher_lorain_q0367_05.htm";
                    break;
                }
                if (questState.getQuestItemsCount(5879) <= 0L) break;
                int n3 = Rnd.get((int)14);
                if (n3 == 0) {
                    questState.giveItems(4553, 1L);
                } else if (n3 == 1) {
                    questState.giveItems(4554, 1L);
                } else if (n3 == 2) {
                    questState.giveItems(4555, 1L);
                } else if (n3 == 3) {
                    questState.giveItems(4556, 1L);
                } else if (n3 == 4) {
                    questState.giveItems(4557, 1L);
                } else if (n3 == 5) {
                    questState.giveItems(4558, 1L);
                } else if (n3 == 6) {
                    questState.giveItems(4559, 1L);
                } else if (n3 == 7) {
                    questState.giveItems(4560, 1L);
                } else if (n3 == 8) {
                    questState.giveItems(4561, 1L);
                } else if (n3 == 9) {
                    questState.giveItems(4562, 1L);
                } else if (n3 == 10) {
                    questState.giveItems(4563, 1L);
                } else if (n3 == 11) {
                    questState.giveItems(4564, 1L);
                } else {
                    questState.giveItems(4445, 1L);
                }
                questState.takeItems(5879, -1L);
                questState.giveItems(5875, 1L);
                this.giveExtraReward(questState.getPlayer());
                string = "researcher_lorain_q0367_06.htm";
            }
        }
        return string;
    }

    public String onAttack(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("Buzz_Buzz_Charging_adventure");
        if (n2 == 1 && n == 21035 && questState.getQuestItemsCount(5879) == 0L) {
            int n3 = Rnd.get((int)37);
            if (n3 == 0) {
                if (questState.getQuestItemsCount(5875) > 0L) {
                    questState.giveItems(5876, 1L);
                    questState.takeItems(5875, -1L);
                    questState.playSound("ItemSound.quest_middle");
                } else if (questState.getQuestItemsCount(5876) > 0L) {
                    questState.giveItems(5877, 1L);
                    questState.takeItems(5876, -1L);
                    questState.playSound("ItemSound.quest_middle");
                } else if (questState.getQuestItemsCount(5877) > 0L) {
                    questState.giveItems(5878, 1L);
                    questState.takeItems(5877, -1L);
                    questState.playSound("ItemSound.quest_middle");
                } else if (questState.getQuestItemsCount(5878) > 0L) {
                    questState.giveItems(5879, 1L);
                    questState.takeItems(5878, -1L);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                }
                npcInstance.doCast(SkillTable.getInstance().getInfo(4072, 4), (Creature)questState.getPlayer(), true);
            } else if (n3 == 1 && questState.getQuestItemsCount(5880) == 0L) {
                questState.giveItems(5880, 1L);
                questState.takeItems(5875, -1L);
                questState.takeItems(5876, -1L);
                questState.takeItems(5877, -1L);
                questState.takeItems(5878, -1L);
                questState.playSound("ItemSound.quest_middle");
            }
        }
        return null;
    }
}
