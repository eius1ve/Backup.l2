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
import quests._037_PleaseMakeMeFormalWear;

public class _033_MakeAPairOfDressShoes
extends Quest
implements ScriptFile {
    private static final int LY = 30838;
    private static final int LZ = 30164;
    private static final int Ma = 31520;
    private static final int Mb = 7164;
    private static final int Mc = 1882;
    private static final int Md = 1868;
    private static final int Me = 7113;

    public _033_MakeAPairOfDressShoes() {
        super(0);
        this.addStartNpc(30838);
        this.addTalkId(new int[]{30164, 31520});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("make_the_dress_shoes_cookie");
        int n2 = npcInstance.getNpcId();
        if (n2 == 30838) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("make_the_dress_shoes", String.valueOf(11), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "trader_woodley_q0033_0104.htm";
            } else if (string.equalsIgnoreCase("reply_1") && n == 2) {
                questState.setCond(3);
                questState.set("make_the_dress_shoes", String.valueOf(31), true);
                questState.playSound("ItemSound.quest_middle");
                string2 = "trader_woodley_q0033_0301.htm";
            } else if (string.equalsIgnoreCase("reply_1") && n == 3) {
                if (questState.getQuestItemsCount(1882) >= 200L && questState.getQuestItemsCount(1868) >= 600L && questState.getQuestItemsCount(57) >= 200000L) {
                    questState.setCond(4);
                    questState.set("make_the_dress_shoes", String.valueOf(41), true);
                    questState.takeItems(1882, 200L);
                    questState.takeItems(1868, 600L);
                    questState.takeItems(57, 200000L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "trader_woodley_q0033_0401.htm";
                } else {
                    string2 = "trader_woodley_q0033_0402.htm";
                }
            } else if (string.equalsIgnoreCase("reply_3") && n == 5) {
                questState.giveItems(7113, 1L);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(false);
                string2 = "trader_woodley_q0033_0601.htm";
            }
        } else if (n2 == 30164) {
            if (string.equalsIgnoreCase("reply_1") && n == 4) {
                if (questState.getQuestItemsCount(57) >= 300000L) {
                    questState.setCond(5);
                    questState.set("make_the_dress_shoes", String.valueOf(51), true);
                    questState.takeItems(57, 300000L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "iz_q0033_0501.htm";
                } else {
                    string2 = "iz_q0033_0502.htm";
                }
            }
        } else if (n2 == 31520 && string.equalsIgnoreCase("reply_1") && n == 1) {
            questState.setCond(2);
            questState.set("make_the_dress_shoes", String.valueOf(21), true);
            questState.playSound("ItemSound.quest_middle");
            string2 = "leikar_q0033_0201.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("make_the_dress_shoes");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30838) break;
                if (questState.getPlayer().getLevel() < 60) {
                    string = "trader_woodley_q0033_0103.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (questState.getPlayer().getQuestState(_037_PleaseMakeMeFormalWear.class) != null && questState.getPlayer().getQuestState(_037_PleaseMakeMeFormalWear.class).isStarted() && questState.getQuestItemsCount(7164) >= 1L) {
                    string = "trader_woodley_q0033_0101.htm";
                    break;
                }
                string = "trader_woodley_q0033_0102.htm";
                break;
            }
            case 2: {
                if (n2 == 30838) {
                    if (n == 11) {
                        string = "trader_woodley_q0033_0105.htm";
                        break;
                    }
                    if (n == 21) {
                        questState.set("make_the_dress_shoes_cookie", String.valueOf(2), true);
                        string = "trader_woodley_q0033_0201.htm";
                        break;
                    }
                    if (n == 31) {
                        if (questState.getQuestItemsCount(1882) >= 200L && questState.getQuestItemsCount(1868) >= 600L && questState.getQuestItemsCount(57) >= 500000L) {
                            questState.set("make_the_dress_shoes_cookie", String.valueOf(3), true);
                            string = "trader_woodley_q0033_0302.htm";
                            break;
                        }
                        string = "trader_woodley_q0033_0303.htm";
                        break;
                    }
                    if (n == 41) {
                        string = "trader_woodley_q0033_0403.htm";
                        break;
                    }
                    if (n != 51) break;
                    questState.set("make_the_dress_shoes_cookie", String.valueOf(5), true);
                    string = "trader_woodley_q0033_0501.htm";
                    break;
                }
                if (n2 == 30164) {
                    if (n == 41) {
                        questState.set("make_the_dress_shoes_cookie", String.valueOf(4), true);
                        string = "iz_q0033_0401.htm";
                        break;
                    }
                    if (n != 51) break;
                    string = "iz_q0033_0503.htm";
                    break;
                }
                if (n2 != 31520) break;
                if (n == 11) {
                    questState.set("make_the_dress_shoes_cookie", String.valueOf(1), true);
                    string = "leikar_q0033_0101.htm";
                    break;
                }
                if (n != 21) break;
                string = "leikar_q0033_0202.htm";
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
