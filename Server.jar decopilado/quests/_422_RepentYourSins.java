/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Summon
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.Summon;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.ScriptFile;

public class _422_RepentYourSins
extends Quest
implements ScriptFile {
    private static final int beS = 30981;
    private static final int beT = 30668;
    private static final int beU = 30597;
    private static final int beV = 30612;
    private static final int beW = 30718;
    private static final int beX = 30300;
    private static final int beY = 20039;
    private static final int beZ = 20494;
    private static final int bfa = 20193;
    private static final int bfb = 20561;
    private static final int bfc = 4326;
    private static final int bfd = 4327;
    private static final int bfe = 4328;
    private static final int bff = 4329;
    private static final int bfg = 4331;
    private static final int bfh = 4330;
    private static final int bfi = 4426;
    private static final int bfj = 4425;
    private static final int bfk = 1873;
    private static final int bfl = 1877;
    private static final int bfm = 1892;
    private static final int bfn = 1879;
    private static final int bfo = 1880;
    private static final int bfp = 12564;

    public _422_RepentYourSins() {
        super(0);
        this.addStartNpc(30981);
        this.addTalkId(new int[]{30668, 30597, 30612, 30718, 30300});
        this.addKillId(new int[]{20039, 20494, 20193, 20561});
        this.addQuestItem(new int[]{4326, 4327, 4328, 4329, 4330, 4331, 4425});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        Summon summon = questState.getPlayer().getPet();
        int n = npcInstance.getNpcId();
        int n2 = questState.getPlayer().getLevel() >= 80 ? questState.getPlayer().getLevel() - 1 : questState.getPlayer().getLevel();
        int n3 = questState.getPlayer().getPkKills();
        int n4 = questState.getInt("brother_chained_to_you");
        int n5 = questState.getInt("brother_chained_to_you_ex");
        if (n == 30981) {
            if (string.equalsIgnoreCase("quest_accept")) {
                if (n2 > 80) {
                    string2 = "black_judge_q0422_02a.htm";
                } else if (n2 > 20 && n2 < 31) {
                    questState.setCond(3);
                    questState.set("brother_chained_to_you", String.valueOf(2), true);
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                    string2 = "black_judge_q0422_04.htm";
                } else if (n2 < 21) {
                    questState.setCond(1);
                    questState.setCond(2);
                    questState.set("brother_chained_to_you", String.valueOf(1), true);
                    questState.set("brother_chained_to_you_ex", String.valueOf(0), true);
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                    string2 = "black_judge_q0422_03.htm";
                } else if (n2 > 30 && n2 < 41) {
                    questState.setCond(4);
                    questState.set("brother_chained_to_you", String.valueOf(3), true);
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                    string2 = "black_judge_q0422_05.htm";
                } else {
                    questState.setCond(5);
                    questState.set("brother_chained_to_you", String.valueOf(4), true);
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                    string2 = "black_judge_q0422_06.htm";
                }
            } else if (string.equalsIgnoreCase("reply_1")) {
                if (n4 >= 9 && n4 <= 12 && (questState.getQuestItemsCount(4426) >= 1L || questState.getQuestItemsCount(4330) >= 1L)) {
                    if (questState.getQuestItemsCount(4330) > 0L) {
                        questState.takeItems(4330, 1L);
                    }
                    if (questState.getQuestItemsCount(4426) > 0L) {
                        questState.takeItems(4426, 1L);
                    }
                    questState.setCond(16);
                    questState.giveItems(4425, 1L);
                    questState.set("brother_chained_to_you_ex", String.valueOf(n2), true);
                    string2 = "black_judge_q0422_11.htm";
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                if (n4 >= 9 && n4 <= 12) {
                    string2 = "black_judge_q0422_14.htm";
                }
            } else if (string.equalsIgnoreCase("reply_3")) {
                ItemInstance itemInstance = questState.getPlayer().getInventory().getItemByItemId(4425);
                if (n4 >= 9 && n4 <= 12 && itemInstance.getEnchantLevel() > n5) {
                    if (summon != null && summon.getNpcId() == 12564) {
                        string2 = "black_judge_q0422_15t.htm";
                    } else {
                        int n6;
                        int n7 = n2 > n5 ? itemInstance.getEnchantLevel() - n5 - (n2 - n5) : itemInstance.getEnchantLevel() - n5;
                        if (n7 < 0) {
                            n7 = 0;
                        }
                        if (n3 <= (n6 = Rnd.get((int)(10 * n7)) + 1)) {
                            if (questState.getQuestItemsCount(4426) < 1L) {
                                questState.giveItems(4426, 1L);
                            }
                            questState.getPlayer().setPkKills(0);
                            questState.getPlayer().sendUserInfo(true, new UserInfoType[]{UserInfoType.SOCIAL});
                            questState.playSound("ItemSound.quest_finish");
                            questState.exitCurrentQuest(true);
                            string2 = "black_judge_q0422_15.htm";
                        } else {
                            questState.takeItems(4425, 1L);
                            questState.giveItems(4426, 1L);
                            questState.set("brother_chained_to_you_ex", String.valueOf(0), true);
                            questState.getPlayer().setPkKills(questState.getPlayer().getPkKills() - n6);
                            questState.getPlayer().sendUserInfo(true, new UserInfoType[]{UserInfoType.SOCIAL});
                            string2 = "black_judge_q0422_16.htm";
                        }
                    }
                }
            } else if (string.equalsIgnoreCase("reply_4")) {
                if (n4 >= 9 && n4 <= 12) {
                    string2 = "black_judge_q0422_17.htm";
                }
            } else if (string.equalsIgnoreCase("reply_5") && n4 >= 9 && n4 <= 12) {
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
                string2 = "black_judge_q0422_18.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("brother_chained_to_you");
        int n3 = questState.getInt("brother_chained_to_you_ex");
        int n4 = questState.getState();
        int n5 = questState.getPlayer().getPkKills();
        switch (n4) {
            case 1: {
                if (n != 30981) break;
                if (questState.getPlayer().getLevel() > 80) {
                    string = "black_judge_q0422_02a.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (n5 == 0) {
                    string = "black_judge_q0422_01.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "black_judge_q0422_02.htm";
                break;
            }
            case 2: {
                if (n == 30981) {
                    if (n2 == 1000) {
                        questState.takeItems(4425, 1L);
                        break;
                    }
                    if (n2 < 9) {
                        string = "black_judge_q0422_07.htm";
                        break;
                    }
                    if (n2 >= 9 && n2 <= 12 && questState.getQuestItemsCount(4331) == 0L && questState.getQuestItemsCount(4426) == 0L && questState.getQuestItemsCount(4330) == 0L && questState.getQuestItemsCount(4425) == 0L) {
                        questState.setCond(14);
                        questState.giveItems(4331, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "black_judge_q0422_08.htm";
                        break;
                    }
                    if (n2 >= 9 && n2 <= 12 && questState.getQuestItemsCount(4331) >= 1L && questState.getQuestItemsCount(4426) == 0L && questState.getQuestItemsCount(4330) == 0L && questState.getQuestItemsCount(4425) == 0L) {
                        string = "black_judge_q0422_09.htm";
                        break;
                    }
                    if (n2 >= 9 && n2 <= 12 && questState.getQuestItemsCount(4331) == 0L && questState.getQuestItemsCount(4426) == 0L && questState.getQuestItemsCount(4330) >= 1L && questState.getQuestItemsCount(4425) == 0L) {
                        string = "black_judge_q0422_10.htm";
                        break;
                    }
                    if (n2 >= 9 && n2 <= 12 && questState.getQuestItemsCount(4425) >= 1L) {
                        ItemInstance itemInstance = questState.getPlayer().getInventory().getItemByItemId(4425);
                        if (itemInstance == null) break;
                        if (itemInstance.getEnchantLevel() < n3 + 1) {
                            string = "black_judge_q0422_12.htm";
                            break;
                        }
                        string = "black_judge_q0422_13.htm";
                        break;
                    }
                    if (n2 < 9 || n2 > 12 || questState.getQuestItemsCount(4426) < 1L || questState.getQuestItemsCount(4425) != 0L) break;
                    string = "black_judge_q0422_16t.htm";
                    break;
                }
                if (n == 30668) {
                    if (n2 == 1) {
                        questState.setCond(6);
                        questState.set("brother_chained_to_you", String.valueOf(5), true);
                        questState.playSound("ItemSound.quest_middle");
                        string = "katari_q0422_01.htm";
                        break;
                    }
                    if (n2 == 5 && questState.getQuestItemsCount(4326) < 10L) {
                        string = "katari_q0422_02.htm";
                        break;
                    }
                    if (n2 == 5 && questState.getQuestItemsCount(4326) >= 10L) {
                        questState.setCond(10);
                        questState.set("brother_chained_to_you", String.valueOf(9), true);
                        questState.takeItems(4326, -1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "katari_q0422_03.htm";
                        break;
                    }
                    if (n2 != 9) break;
                    string = "katari_q0422_04.htm";
                    break;
                }
                if (n == 30597) {
                    if (n2 == 2) {
                        questState.setCond(7);
                        questState.set("brother_chained_to_you", String.valueOf(6), true);
                        questState.playSound("ItemSound.quest_middle");
                        string = "piotur_q0422_01.htm";
                        break;
                    }
                    if (n2 == 6 && questState.getQuestItemsCount(4327) < 10L) {
                        string = "piotur_q0422_02.htm";
                        break;
                    }
                    if (n2 == 6 && questState.getQuestItemsCount(4327) >= 10L) {
                        questState.setCond(11);
                        questState.set("brother_chained_to_you", String.valueOf(10), true);
                        questState.takeItems(4327, -1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "piotur_q0422_03.htm";
                        break;
                    }
                    if (n2 != 10) break;
                    string = "piotur_q0422_04.htm";
                    break;
                }
                if (n == 30612) {
                    if (n2 == 3) {
                        questState.setCond(8);
                        questState.set("brother_chained_to_you", String.valueOf(7), true);
                        questState.playSound("ItemSound.quest_middle");
                        string = "sage_kasian_q0422_01.htm";
                        break;
                    }
                    if (n2 == 7 && questState.getQuestItemsCount(4327) < 1L) {
                        string = "sage_kasian_q0422_02.htm";
                        break;
                    }
                    if (n2 == 7 && questState.getQuestItemsCount(4327) >= 1L) {
                        questState.setCond(12);
                        questState.set("brother_chained_to_you", String.valueOf(11), true);
                        questState.takeItems(4328, -1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "sage_kasian_q0422_03.htm";
                        break;
                    }
                    if (n2 != 11) break;
                    string = "sage_kasian_q0422_04.htm";
                    break;
                }
                if (n == 30718) {
                    if (n2 == 4) {
                        questState.setCond(9);
                        questState.set("brother_chained_to_you", String.valueOf(8), true);
                        questState.playSound("ItemSound.quest_middle");
                        string = "magister_joan_q0422_01.htm";
                        break;
                    }
                    if (n2 == 8 && questState.getQuestItemsCount(4329) < 3L) {
                        string = "magister_joan_q0422_02.htm";
                        break;
                    }
                    if (n2 == 8 && questState.getQuestItemsCount(4329) >= 3L) {
                        questState.setCond(13);
                        questState.set("brother_chained_to_you", String.valueOf(12), true);
                        questState.takeItems(4329, -1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "magister_joan_q0422_03.htm";
                        break;
                    }
                    if (n2 != 12) break;
                    string = "magister_joan_q0422_04.htm";
                    break;
                }
                if (n != 30300) break;
                if (n2 >= 9 && n2 <= 12 && questState.getQuestItemsCount(4331) >= 1L && questState.getQuestItemsCount(1892) > 0L && questState.getQuestItemsCount(1880) >= 5L && questState.getQuestItemsCount(1877) >= 2L && questState.getQuestItemsCount(1873) >= 10L && questState.getQuestItemsCount(1879) >= 10L && questState.getQuestItemsCount(4330) == 0L && questState.getQuestItemsCount(4425) == 0L && questState.getQuestItemsCount(4426) == 0L) {
                    questState.setCond(15);
                    questState.takeItems(4331, 1L);
                    questState.takeItems(1892, 1L);
                    questState.takeItems(1880, 5L);
                    questState.takeItems(1877, 2L);
                    questState.takeItems(1873, 10L);
                    questState.takeItems(1879, 10L);
                    questState.giveItems(4330, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    string = "blacksmith_pushkin_q0422_01.htm";
                    break;
                }
                if (n2 >= 9 && n2 <= 12 && questState.getQuestItemsCount(4331) >= 1L && (questState.getQuestItemsCount(1892) == 0L || questState.getQuestItemsCount(1880) < 5L || questState.getQuestItemsCount(1877) < 2L || questState.getQuestItemsCount(1873) < 10L || questState.getQuestItemsCount(1879) < 10L) && questState.getQuestItemsCount(4330) == 0L && questState.getQuestItemsCount(4425) == 0L && questState.getQuestItemsCount(4426) == 0L) {
                    string = "blacksmith_pushkin_q0422_02.htm";
                    break;
                }
                if (n2 < 9 || n2 > 12 || questState.getQuestItemsCount(4330) != 1L && questState.getQuestItemsCount(4425) != 1L && questState.getQuestItemsCount(4426) != 1L) break;
                string = "blacksmith_pushkin_q0422_03.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("brother_chained_to_you");
        if (n == 20039) {
            if (n2 == 5 && questState.getQuestItemsCount(4326) < 10L) {
                if (questState.getQuestItemsCount(4326) == 9L) {
                    questState.giveItems(4326, 1L);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.giveItems(4326, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 20494) {
            if (n2 == 6 && questState.getQuestItemsCount(4327) < 10L) {
                if (questState.getQuestItemsCount(4327) == 9L) {
                    questState.giveItems(4327, 1L);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.giveItems(4327, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == 20193) {
            if (n2 == 7 && questState.getQuestItemsCount(4328) < 1L) {
                questState.giveItems(4328, 1L);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 20561 && n2 == 8 && questState.getQuestItemsCount(4329) < 3L) {
            if (questState.getQuestItemsCount(4329) == 2L) {
                questState.giveItems(4329, 1L);
                questState.playSound("ItemSound.quest_middle");
            } else {
                questState.giveItems(4329, 1L);
                questState.playSound("ItemSound.quest_itemget");
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
