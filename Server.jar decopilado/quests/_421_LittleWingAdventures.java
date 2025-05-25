/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Summon
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.SkillTable
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Summon;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.SkillTable;

public class _421_LittleWingAdventures
extends Quest
implements ScriptFile {
    private static final int beF = 30610;
    private static final int beG = 30747;
    private static final int beH = 27185;
    private static final int beI = 27186;
    private static final int beJ = 27187;
    private static final int beK = 27188;
    private static final int beL = 4325;
    private static final int beM = 3500;
    private static final int beN = 3501;
    private static final int beO = 3502;
    private static final int beP = 4422;
    private static final int beQ = 4423;
    private static final int beR = 4424;
    int c1_flag = 0;

    public _421_LittleWingAdventures() {
        super(0);
        this.addStartNpc(30610);
        this.addTalkId(new int[]{30747});
        this.addAttackId(new int[]{27185, 27186, 27187, 27188});
        this.addQuestItem(new int[]{4325});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        Summon summon = questState.getPlayer().getPet();
        int n = questState.getInt("adventure_of_the_little_wings_ex");
        int n2 = npcInstance.getNpcId();
        if (n2 == 30610) {
            if (string.equalsIgnoreCase("quest_accept")) {
                if (questState.getQuestItemsCount(3500) + questState.getQuestItemsCount(3501) + questState.getQuestItemsCount(3502) == 1L) {
                    ItemInstance itemInstance;
                    if (questState.getQuestItemsCount(3500) == 1L) {
                        ItemInstance itemInstance2 = questState.getPlayer().getInventory().getItemByItemId(3500);
                        if (itemInstance2 != null) {
                            if (itemInstance2.getEnchantLevel() < 55) {
                                string2 = "sage_cronos_q0421_06.htm";
                            } else {
                                questState.setCond(1);
                                questState.set("adventure_of_the_little_wings", String.valueOf(100), true);
                                questState.set("adventure_of_the_little_wings_ex", String.valueOf(itemInstance2.getObjectId()), true);
                                questState.setState(2);
                                questState.playSound("ItemSound.quest_accept");
                                string2 = "sage_cronos_q0421_05.htm";
                            }
                        }
                    } else if (questState.getQuestItemsCount(3501) == 1L) {
                        ItemInstance itemInstance3 = questState.getPlayer().getInventory().getItemByItemId(3501);
                        if (itemInstance3 != null) {
                            if (itemInstance3.getEnchantLevel() < 55) {
                                string2 = "sage_cronos_q0421_06.htm";
                            } else {
                                questState.setCond(1);
                                questState.set("adventure_of_the_little_wings", String.valueOf(100), true);
                                questState.set("adventure_of_the_little_wings_ex", String.valueOf(itemInstance3.getObjectId()), true);
                                questState.setState(2);
                                questState.playSound("ItemSound.quest_accept");
                                string2 = "sage_cronos_q0421_05.htm";
                            }
                        }
                    } else if (questState.getQuestItemsCount(3502) == 1L && (itemInstance = questState.getPlayer().getInventory().getItemByItemId(3502)) != null) {
                        if (itemInstance.getEnchantLevel() < 55) {
                            string2 = "sage_cronos_q0421_06.htm";
                        } else {
                            questState.setCond(1);
                            questState.set("adventure_of_the_little_wings", String.valueOf(100), true);
                            questState.set("adventure_of_the_little_wings_ex", String.valueOf(itemInstance.getObjectId()), true);
                            questState.setState(2);
                            questState.playSound("ItemSound.quest_accept");
                            string2 = "sage_cronos_q0421_05.htm";
                        }
                    }
                } else {
                    string2 = "sage_cronos_q0421_06.htm";
                }
            }
        } else if (n2 == 30747) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = summon != null ? (questState.getPlayer().getInventory().getCountOf(3500) == 1L && n == questState.getPlayer().getInventory().getItemByItemId(3500).getObjectId() || questState.getPlayer().getInventory().getCountOf(3501) == 1L && n == questState.getPlayer().getInventory().getItemByItemId(3501).getObjectId() || questState.getPlayer().getInventory().getCountOf(3502) == 1L && n == questState.getPlayer().getInventory().getItemByItemId(3502).getObjectId() ? "fairy_mymyu_q0421_04.htm" : "fairy_mymyu_q0421_03.htm") : "fairy_mymyu_q0421_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                if (summon != null) {
                    if (questState.getPlayer().getInventory().getCountOf(3500) == 1L && n == questState.getPlayer().getInventory().getItemByItemId(3500).getObjectId() || questState.getPlayer().getInventory().getCountOf(3501) == 1L && n == questState.getPlayer().getInventory().getItemByItemId(3501).getObjectId() || questState.getPlayer().getInventory().getCountOf(3502) == 1L && n == questState.getPlayer().getInventory().getItemByItemId(3502).getObjectId()) {
                        questState.setCond(2);
                        questState.set("adventure_of_the_little_wings", String.valueOf(0), true);
                        questState.giveItems(4325, 4L);
                        questState.playSound("ItemSound.quest_middle");
                        string2 = "fairy_mymyu_q0421_05.htm";
                    } else {
                        string2 = "fairy_mymyu_q0421_06.htm";
                    }
                } else {
                    string2 = "fairy_mymyu_q0421_06.htm";
                }
            } else if (string.equalsIgnoreCase("reply_3")) {
                string2 = "fairy_mymyu_q0421_07.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                string2 = "fairy_mymyu_q0421_08.htm";
            } else if (string.equalsIgnoreCase("reply_5")) {
                string2 = "fairy_mymyu_q0421_09.htm";
            } else if (string.equalsIgnoreCase("reply_6")) {
                string2 = "fairy_mymyu_q0421_10.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        Summon summon = questState.getPlayer().getPet();
        int n = questState.getInt("adventure_of_the_little_wings");
        int n2 = questState.getInt("adventure_of_the_little_wings_ex");
        int n3 = npcInstance.getNpcId();
        int n4 = questState.getState();
        switch (n4) {
            case 1: {
                ItemInstance itemInstance;
                if (n3 != 30610) break;
                if (questState.getPlayer().getLevel() < 45) {
                    string = "sage_cronos_q0421_01.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (questState.getQuestItemsCount(3500) + questState.getQuestItemsCount(3501) + questState.getQuestItemsCount(3502) >= 2L) {
                    string = "sage_cronos_q0421_02.htm";
                    break;
                }
                if (questState.getQuestItemsCount(3500) + questState.getQuestItemsCount(3501) + questState.getQuestItemsCount(3502) == 1L && questState.getQuestItemsCount(3500) == 1L && (itemInstance = questState.getPlayer().getInventory().getItemByItemId(3500)) != null) {
                    string = itemInstance.getEnchantLevel() < 55 ? "sage_cronos_q0421_03.htm" : "sage_cronos_q0421_04.htm";
                }
                if (questState.getQuestItemsCount(3501) == 1L && (itemInstance = questState.getPlayer().getInventory().getItemByItemId(3501)) != null) {
                    string = itemInstance.getEnchantLevel() < 55 ? "sage_cronos_q0421_03.htm" : "sage_cronos_q0421_04.htm";
                }
                if (questState.getQuestItemsCount(3502) != 1L || (itemInstance = questState.getPlayer().getInventory().getItemByItemId(3502)) == null) break;
                if (itemInstance.getEnchantLevel() < 55) {
                    string = "sage_cronos_q0421_03.htm";
                    break;
                }
                string = "sage_cronos_q0421_04.htm";
                break;
            }
            case 2: {
                if (n3 == 30610) {
                    if (n != 100 && n != 200 && (n < 0 || n > 16)) break;
                    string = "sage_cronos_q0421_07.htm";
                    break;
                }
                if (n3 != 30747) break;
                if (n == 100) {
                    questState.set("adventure_of_the_little_wings", String.valueOf(200), true);
                    string = "fairy_mymyu_q0421_01.htm";
                    break;
                }
                if (n == 200) {
                    if (summon != null) {
                        if (questState.getPlayer().getInventory().getCountOf(3500) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3500).getObjectId() || questState.getPlayer().getInventory().getCountOf(3501) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3501).getObjectId() || questState.getPlayer().getInventory().getCountOf(3502) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3502).getObjectId()) {
                            string = "fairy_mymyu_q0421_04.htm";
                            break;
                        }
                        string = "fairy_mymyu_q0421_03.htm";
                        break;
                    }
                    string = "fairy_mymyu_q0421_02.htm";
                    break;
                }
                if (n == 0) {
                    string = "fairy_mymyu_q0421_07.htm";
                    break;
                }
                if (n > 0 && n < 15 && questState.getQuestItemsCount(4325) >= 1L) {
                    string = "fairy_mymyu_q0421_11.htm";
                    break;
                }
                if (n == 15 && questState.getQuestItemsCount(4325) == 0L) {
                    if (summon != null) {
                        if (questState.getPlayer().getInventory().getCountOf(3500) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3500).getObjectId() || questState.getPlayer().getInventory().getCountOf(3501) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3501).getObjectId() || questState.getPlayer().getInventory().getCountOf(3502) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3502).getObjectId()) {
                            questState.set("adventure_of_the_little_wings", String.valueOf(16), true);
                            string = "fairy_mymyu_q0421_13.htm";
                            break;
                        }
                        string = "fairy_mymyu_q0421_14.htm";
                        break;
                    }
                    string = "fairy_mymyu_q0421_12.htm";
                    break;
                }
                if (n != 16 || questState.getQuestItemsCount(4325) != 0L) break;
                if (summon == null) {
                    string = "fairy_mymyu_q0421_15.htm";
                    break;
                }
                if (questState.getQuestItemsCount(3500) + questState.getQuestItemsCount(3501) + questState.getQuestItemsCount(3502) == 1L) {
                    ItemInstance itemInstance;
                    if (questState.getQuestItemsCount(3500) == 1L) {
                        ItemInstance itemInstance2 = questState.getPlayer().getInventory().getItemByItemId(3500);
                        if (itemInstance2 == null) break;
                        if (n2 == itemInstance2.getObjectId()) {
                            if (questState.getPlayer().getInventory().getCountOf(3500) != 1L || n2 != itemInstance2.getObjectId()) break;
                            summon.unSummon();
                            questState.getPlayer().getInventory().destroyItemByObjectId(n2, 1L);
                            questState.giveItems(4422, 1L);
                            questState.unset("adventure_of_the_little_wings");
                            questState.unset("adventure_of_the_little_wings_ex");
                            questState.playSound("ItemSound.quest_finish");
                            questState.exitCurrentQuest(true);
                            string = "fairy_mymyu_q0421_16.htm";
                            break;
                        }
                        npcInstance.doCast(SkillTable.getInstance().getInfo(4167, 1), (Creature)questState.getPlayer(), true);
                        string = "fairy_mymyu_q0421_18.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3501) == 1L) {
                        ItemInstance itemInstance3 = questState.getPlayer().getInventory().getItemByItemId(3501);
                        if (itemInstance3 == null) break;
                        if (n2 == itemInstance3.getObjectId()) {
                            if (questState.getPlayer().getInventory().getCountOf(3501) != 1L || n2 != itemInstance3.getObjectId()) break;
                            summon.unSummon();
                            questState.getPlayer().getInventory().destroyItemByObjectId(n2, 1L);
                            questState.giveItems(4423, 1L);
                            questState.unset("adventure_of_the_little_wings");
                            questState.unset("adventure_of_the_little_wings_ex");
                            questState.playSound("ItemSound.quest_finish");
                            questState.exitCurrentQuest(true);
                            string = "fairy_mymyu_q0421_16.htm";
                            break;
                        }
                        npcInstance.doCast(SkillTable.getInstance().getInfo(4167, 1), (Creature)questState.getPlayer(), true);
                        string = "fairy_mymyu_q0421_18.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(3502) != 1L || (itemInstance = questState.getPlayer().getInventory().getItemByItemId(3502)) == null) break;
                    if (n2 == itemInstance.getObjectId()) {
                        if (questState.getPlayer().getInventory().getCountOf(3502) != 1L || n2 != itemInstance.getObjectId()) break;
                        summon.unSummon();
                        questState.getPlayer().getInventory().destroyItemByObjectId(n2, 1L);
                        questState.giveItems(4424, 1L);
                        questState.unset("adventure_of_the_little_wings");
                        questState.unset("adventure_of_the_little_wings_ex");
                        questState.playSound("ItemSound.quest_finish");
                        questState.exitCurrentQuest(true);
                        string = "fairy_mymyu_q0421_16.htm";
                        break;
                    }
                    npcInstance.doCast(SkillTable.getInstance().getInfo(4167, 1), (Creature)questState.getPlayer(), true);
                    string = "fairy_mymyu_q0421_18.htm";
                    break;
                }
                if (questState.getQuestItemsCount(3500) + questState.getQuestItemsCount(3501) + questState.getQuestItemsCount(3502) < 2L) break;
                string = "fairy_mymyu_q0421_17.htm";
            }
        }
        return string;
    }

    public String onAttack(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("adventure_of_the_little_wings");
        int n2 = questState.getInt("adventure_of_the_little_wings_ex");
        int n3 = npcInstance.getNpcId();
        if (n3 == 27185) {
            if (n <= 16) {
                if (Rnd.get((int)100) <= 29) {
                    npcInstance.doCast(SkillTable.getInstance().getInfo(4243, 1), (Creature)questState.getPlayer().getPet(), true);
                }
                if (n % 2 == 0) {
                    if ((questState.getPlayer().getInventory().getCountOf(3500) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3500).getObjectId() || questState.getPlayer().getInventory().getCountOf(3501) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3501).getObjectId() || questState.getPlayer().getInventory().getCountOf(3502) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3502).getObjectId()) && Rnd.get((int)100) <= 2 && questState.getQuestItemsCount(4325) >= 1L) {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.GIVE_ME_A_FAIRY_LEAF", (Object[])new Object[0]);
                        questState.takeItems(4325, 1L);
                        questState.set("adventure_of_the_little_wings", String.valueOf(n + 1), true);
                        questState.playSound("ItemSound.quest_middle");
                        if (n >= 15 && questState.getQuestItemsCount(4325) <= 1L) {
                            questState.setCond(3);
                        }
                    }
                } else {
                    int n4 = Rnd.get((int)3);
                    if (n4 == 0) {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.WHY_DO_YOU_BOTHER_ME_AGAIN", (Object[])new Object[0]);
                    } else if (n4 == 1) {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.HEY_YOUVE_ALREADY_DRUNK_THE_ESSENCE_OF_WIND", (Object[])new Object[0]);
                    } else {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.LEAVE_NOW_BEFORE_YOU_INCUR_THE_WRATH_OF_THE_GUARDIAN_GHOST", (Object[])new Object[0]);
                    }
                }
            }
        } else if (n3 == 27186) {
            if (n <= 16) {
                if (Rnd.get((int)100) <= 29) {
                    npcInstance.doCast(SkillTable.getInstance().getInfo(4243, 1), (Creature)questState.getPlayer().getPet(), true);
                }
                if (n % 4 < 2) {
                    if ((questState.getPlayer().getInventory().getCountOf(3500) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3500).getObjectId() || questState.getPlayer().getInventory().getCountOf(3501) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3501).getObjectId() || questState.getPlayer().getInventory().getCountOf(3502) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3502).getObjectId()) && Rnd.get((int)100) <= 1 && questState.getQuestItemsCount(4325) >= 1L) {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.GIVE_ME_A_FAIRY_LEAF", (Object[])new Object[0]);
                        questState.takeItems(4325, 1L);
                        questState.set("adventure_of_the_little_wings", String.valueOf(n + 2), true);
                        questState.playSound("ItemSound.quest_middle");
                        if (n >= 15 && questState.getQuestItemsCount(4325) <= 1L) {
                            questState.setCond(3);
                        }
                    }
                } else {
                    int n5 = Rnd.get((int)3);
                    if (n5 == 0) {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.WHY_DO_YOU_BOTHER_ME_AGAIN", (Object[])new Object[0]);
                    } else if (n5 == 1) {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.HEY_YOUVE_ALREADY_DRUNK_THE_ESSENCE_OF_A_STAR", (Object[])new Object[0]);
                    } else {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.LEAVE_NOW_BEFORE_YOU_INCUR_THE_WRATH_OF_THE_GUARDIAN_GHOST", (Object[])new Object[0]);
                    }
                }
            }
        } else if (n3 == 27187) {
            if (n <= 16) {
                if (Rnd.get((int)100) <= 29) {
                    npcInstance.doCast(SkillTable.getInstance().getInfo(4243, 1), (Creature)questState.getPlayer().getPet(), true);
                }
                if (n % 8 < 4) {
                    if ((questState.getPlayer().getInventory().getCountOf(3500) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3500).getObjectId() || questState.getPlayer().getInventory().getCountOf(3501) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3501).getObjectId() || questState.getPlayer().getInventory().getCountOf(3502) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3502).getObjectId()) && Rnd.get((int)100) <= 1 && questState.getQuestItemsCount(4325) >= 1L) {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.GIVE_ME_A_FAIRY_LEAF", (Object[])new Object[0]);
                        questState.takeItems(4325, 1L);
                        questState.set("adventure_of_the_little_wings", String.valueOf(n + 4), true);
                        questState.playSound("ItemSound.quest_middle");
                        if (n >= 15 && questState.getQuestItemsCount(4325) <= 1L) {
                            questState.setCond(3);
                        }
                    }
                } else {
                    int n6 = Rnd.get((int)3);
                    if (n6 == 0) {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.WHY_DO_YOU_BOTHER_ME_AGAIN", (Object[])new Object[0]);
                    } else if (n6 == 1) {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.HEY_YOUVE_ALREADY_DRUNK_THE_ESSENCE_OF_DUSK", (Object[])new Object[0]);
                    } else {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.LEAVE_NOW_BEFORE_YOU_INCUR_THE_WRATH_OF_THE_GUARDIAN_GHOST", (Object[])new Object[0]);
                    }
                }
            }
        } else if (n3 == 27188 && n <= 16) {
            if (Rnd.get((int)100) <= 29) {
                npcInstance.doCast(SkillTable.getInstance().getInfo(4243, 1), (Creature)questState.getPlayer().getPet(), true);
            }
            if (n % 16 < 8) {
                if (questState.getPlayer().getInventory().getCountOf(3500) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3500).getObjectId() || questState.getPlayer().getInventory().getCountOf(3501) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3501).getObjectId() || questState.getPlayer().getInventory().getCountOf(3502) == 1L && n2 == questState.getPlayer().getInventory().getItemByItemId(3502).getObjectId()) {
                    ++this.c1_flag;
                    if (this.c1_flag < 270) {
                        if (Rnd.get((int)100) <= 1) {
                            npcInstance.doCast(SkillTable.getInstance().getInfo(1201, 33), (Creature)questState.getPlayer(), true);
                        }
                    } else if (Rnd.get((int)100) <= 1 && questState.getQuestItemsCount(4325) >= 1L) {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.GIVE_ME_A_FAIRY_LEAF", (Object[])new Object[0]);
                        questState.takeItems(4325, 1L);
                        questState.set("adventure_of_the_little_wings", String.valueOf(n + 8), true);
                        questState.playSound("ItemSound.quest_middle");
                        this.c1_flag = 0;
                        if (n >= 15 && questState.getQuestItemsCount(4325) <= 1L) {
                            questState.setCond(3);
                        }
                    }
                }
            } else {
                int n7 = Rnd.get((int)3);
                if (n7 == 0) {
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.WHY_DO_YOU_BOTHER_ME_AGAIN", (Object[])new Object[0]);
                } else if (n7 == 1) {
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.HEY_YOUVE_ALREADY_DRUNK_THE_ESSENCE_OF_THE_ABYSS", (Object[])new Object[0]);
                } else {
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.LEAVE_NOW_BEFORE_YOU_INCUR_THE_WRATH_OF_THE_GUARDIAN_GHOST", (Object[])new Object[0]);
                }
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
