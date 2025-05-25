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

public class _347_GoGetTheCalculator
extends Quest
implements ScriptFile {
    private static final int aJs = 30526;
    private static final int aJt = 30527;
    private static final int aJu = 30532;
    private static final int aJv = 30533;
    private static final int aJw = 20540;
    private static final int aJx = 4286;
    private static final int aJy = 4285;
    private static final int aJz = 4393;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _347_GoGetTheCalculator() {
        super(0);
        this.addStartNpc(30526);
        this.addTalkId(new int[]{30527, 30532, 30533});
        this.addKillId(new int[]{20540});
        this.addQuestItem(new int[]{4286});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("get_calculator");
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.set("get_calculator", String.valueOf(100), true);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "blacksmith_bronp_q0347_08.htm";
        } else if (string.equalsIgnoreCase("reply_7") && n == 600 && questState.getQuestItemsCount(4285) >= 1L) {
            questState.unset("get_calculator");
            questState.takeItems(4285, -1L);
            questState.giveItems(4393, 1L);
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
            string2 = "blacksmith_bronp_q0347_10.htm";
        } else if (string.equalsIgnoreCase("reply_8") && n == 600 && questState.getQuestItemsCount(4285) >= 1L) {
            questState.unset("get_calculator");
            questState.takeItems(4285, -1L);
            questState.giveItems(57, 1000L, true);
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
            string2 = "blacksmith_bronp_q0347_11.htm";
        } else if (string.equalsIgnoreCase("reply_3")) {
            questState.set("get_calculator", String.valueOf(200 + n), true);
            if (n == 100) {
                questState.setCond(3);
                questState.playSound("ItemSound.quest_middle");
            } else if (n == 200) {
                questState.setCond(4);
                questState.playSound("ItemSound.quest_middle");
            }
            string2 = "elder_spiron_q0347_02.htm";
        } else if (string.equalsIgnoreCase("reply_2")) {
            if (questState.getQuestItemsCount(57) >= 100L) {
                questState.set("get_calculator", String.valueOf(100 + n), true);
                questState.takeItems(57, 100L);
                if (n == 100) {
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                } else if (n == 300) {
                    questState.setCond(4);
                    questState.playSound("ItemSound.quest_middle");
                }
                string2 = "elder_balanki_q0347_02.htm";
            } else {
                string2 = "elder_balanki_q0347_03.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("get_calculator");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30526) break;
                if (questState.getPlayer().getLevel() >= 12) {
                    string = "blacksmith_bronp_q0347_01.htm";
                    break;
                }
                string = "blacksmith_bronp_q0347_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n2 == 30526) {
                    if (questState.getQuestItemsCount(4285) >= 1L) {
                        string = "blacksmith_bronp_q0347_09.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(4285) == 0L && n == 600) {
                        string = "blacksmith_bronp_q0347_12.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(4285) == 0L && n == 100) {
                        string = "blacksmith_bronp_q0347_13.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(4285) == 0L && (n == 200 || n == 300)) {
                        string = "blacksmith_bronp_q0347_14.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(4285) != 0L || n != 400 && n != 500 && n != 600) break;
                    string = "blacksmith_bronp_q0347_15.htm";
                    break;
                }
                if (n2 == 30527) {
                    if (n == 100 || n == 200 || n == 300) {
                        string = "blacksmith_silvery_q0347_01.htm";
                        break;
                    }
                    if (n == 400) {
                        questState.setCond(5);
                        questState.set("get_calculator", String.valueOf(500), true);
                        questState.playSound("ItemSound.quest_middle");
                        string = "blacksmith_silvery_q0347_02.htm";
                        break;
                    }
                    if (n == 500 && questState.getQuestItemsCount(4286) >= 10L) {
                        questState.setCond(6);
                        questState.set("get_calculator", String.valueOf(600), true);
                        questState.giveItems(4285, 1L);
                        questState.takeItems(4286, -1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "blacksmith_silvery_q0347_03.htm";
                        break;
                    }
                    if (n == 500 && questState.getQuestItemsCount(4286) < 10L) {
                        string = "blacksmith_silvery_q0347_04.htm";
                        break;
                    }
                    if (n != 600 && n != 600) break;
                    string = "blacksmith_silvery_q0347_05.htm";
                    break;
                }
                if (n2 == 30532) {
                    if (n == 100 || n == 200) {
                        string = "elder_spiron_q0347_01.htm";
                        break;
                    }
                    if (n != 300 && n != 400 && n != 500 && n != 600) break;
                    string = "elder_spiron_q0347_05.htm";
                    break;
                }
                if (n2 != 30533) break;
                if (n == 100 || n == 300) {
                    string = "elder_balanki_q0347_01.htm";
                    break;
                }
                if (n != 200 && n != 400 && n != 500 && n != 600) break;
                string = "elder_balanki_q0347_04.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("get_calculator");
        if (n == 20540 && n2 == 500 && questState.getQuestItemsCount(4286) < 10L && Rnd.get((int)10) <= 4) {
            questState.giveItems(4286, 1L);
            if (questState.getQuestItemsCount(4286) >= 10L) {
                questState.playSound("ItemSound.quest_middle");
            } else {
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
