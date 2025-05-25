/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;

public class _409_PathToOracle
extends Quest
implements ScriptFile {
    private static final int aYN = 30293;
    private static final int aYO = 30424;
    private static final int aYP = 30428;
    private static final int aYQ = 27032;
    private static final int aYR = 27033;
    private static final int aYS = 27034;
    private static final int aYT = 27035;
    private static final int aYU = 1231;
    private static final int aYV = 1232;
    private static final int aYW = 1233;
    private static final int aYX = 1234;
    private static final int aYY = 1235;
    private static final int aYZ = 1236;
    private static final int aZa = 1275;

    public _409_PathToOracle() {
        super(0);
        this.addStartNpc(30293);
        this.addTalkId(new int[]{30424, 30428});
        this.addKillId(new int[]{27032, 27033, 27034, 27035});
        this.addAttackId(new int[]{27032, 27033, 27034, 27035});
        this.addQuestItem(new int[]{1231, 1232, 1233, 1234, 1236, 1275});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("path_to_oracle");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getPlayer().getClassId().getId();
        int n4 = 25;
        int n5 = 29;
        if (n2 == 30293) {
            if (string.equalsIgnoreCase("quest_accept")) {
                if (questState.getPlayer().getLevel() < 18) {
                    string2 = "father_manuell_q0409_03.htm";
                    questState.exitCurrentQuest(true);
                } else if (n3 != n4) {
                    if (n3 == n5) {
                        string2 = "father_manuell_q0409_02a.htm";
                        questState.exitCurrentQuest(true);
                    } else {
                        string2 = "father_manuell_q0409_02.htm";
                        questState.exitCurrentQuest(true);
                    }
                } else if (questState.getQuestItemsCount(1235) > 0L && n3 == n4) {
                    string2 = "father_manuell_q0409_04.htm";
                } else {
                    questState.setCond(1);
                    questState.set("path_to_oracle", String.valueOf(1), true);
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                    questState.giveItems(1231, 1L);
                    string2 = "father_manuell_q0409_05.htm";
                }
            }
        } else if (n2 == 30424) {
            if (string.equalsIgnoreCase("reply_1")) {
                if (n == 1) {
                    string2 = "allana_q0409_07.htm";
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "allana_q0409_08.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                string2 = "allana_q0409_09.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                questState.addSpawn(27032);
                questState.addSpawn(27033);
                questState.addSpawn(27034);
                questState.set("path_to_oracle", String.valueOf(2), true);
                return null;
            }
        } else if (n2 == 30428) {
            if (string.equalsIgnoreCase("reply_1")) {
                if (n == 2) {
                    string2 = "perrin_q0409_02.htm";
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                if (n == 2) {
                    string2 = "perrin_q0409_03.htm";
                }
            } else if (string.equalsIgnoreCase("reply_3") && n == 2) {
                questState.addSpawn(27035);
                questState.set("path_to_oracle", String.valueOf(3), true);
                return null;
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        Player player = questState.getPlayer();
        int n = questState.getInt("path_to_oracle");
        int n2 = questState.getPlayer().getLevel();
        int n3 = npcInstance.getNpcId();
        int n4 = questState.getState();
        switch (n4) {
            case 1: {
                if (n3 != 30293) break;
                if (questState.getQuestItemsCount(1235) == 0L) {
                    string = "father_manuell_q0409_01.htm";
                    break;
                }
                string = "father_manuell_q0409_04.htm";
                break;
            }
            case 2: {
                if (n3 == 30293) {
                    if (questState.getQuestItemsCount(1231) <= 0L) break;
                    if (questState.getQuestItemsCount(1232) == 0L && questState.getQuestItemsCount(1233) == 0L && questState.getQuestItemsCount(1234) == 0L && questState.getQuestItemsCount(1236) == 0L) {
                        if (n == 2) {
                            questState.setCond(8);
                            questState.set("path_to_oracle", String.valueOf(1), true);
                            string = "father_manuell_q0409_09.htm";
                            break;
                        }
                        questState.set("path_to_oracle", String.valueOf(1), true);
                        string = "father_manuell_q0409_06.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1232) == 1L && questState.getQuestItemsCount(1233) == 1L && questState.getQuestItemsCount(1234) == 1L && questState.getQuestItemsCount(1236) == 0L) {
                        questState.takeItems(1232, 1L);
                        questState.takeItems(1233, 1L);
                        questState.takeItems(1234, 1L);
                        questState.takeItems(1231, 1L);
                        if (questState.getPlayer().getClassId().getLevel() == 1) {
                            questState.giveItems(1235, 1L);
                            if (!questState.getPlayer().getVarB("prof1")) {
                                questState.getPlayer().setVar("prof1", "1", -1L);
                                questState.addExpAndSp(3200L, 1890L);
                                this.giveExtraReward(questState.getPlayer());
                            }
                        }
                        string = "father_manuell_q0409_08.htm";
                        player.sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                        questState.unset("path_to_oracle");
                        questState.playSound("ItemSound.quest_finish");
                        questState.exitCurrentQuest(false);
                        break;
                    }
                    string = "father_manuell_q0409_07.htm";
                    break;
                }
                if (n3 == 30424) {
                    if (questState.getQuestItemsCount(1231) <= 0L) break;
                    if (questState.getQuestItemsCount(1232) == 0L && questState.getQuestItemsCount(1233) == 0L && questState.getQuestItemsCount(1234) == 0L && questState.getQuestItemsCount(1236) == 0L) {
                        if (n == 2) {
                            string = "allana_q0409_05.htm";
                            break;
                        }
                        if (n != 1) break;
                        questState.setCond(2);
                        questState.playSound("ItemSound.quest_middle");
                        string = "allana_q0409_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1232) == 0L && questState.getQuestItemsCount(1233) == 0L && questState.getQuestItemsCount(1234) == 1L && questState.getQuestItemsCount(1236) == 0L) {
                        questState.setCond(4);
                        questState.set("path_to_oracle", String.valueOf(2), true);
                        questState.giveItems(1236, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "allana_q0409_02.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1232) == 0L && questState.getQuestItemsCount(1233) == 0L && questState.getQuestItemsCount(1234) == 1L && questState.getQuestItemsCount(1236) == 1L) {
                        if (n == 3 && questState.getQuestItemsCount(1275) == 0L) {
                            questState.setCond(4);
                            questState.set("path_to_oracle", String.valueOf(2), true);
                            questState.playSound("ItemSound.quest_middle");
                            string = "allana_q0409_06.htm";
                            break;
                        }
                        string = "allana_q0409_03.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1232) == 1L && questState.getQuestItemsCount(1233) == 0L && questState.getQuestItemsCount(1234) == 1L && questState.getQuestItemsCount(1236) == 1L) {
                        questState.setCond(9);
                        questState.takeItems(1236, 1L);
                        questState.giveItems(1233, 1L);
                        questState.playSound("ItemSound.quest_middle");
                        string = "allana_q0409_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1232) != 1L || questState.getQuestItemsCount(1234) != 1L || questState.getQuestItemsCount(1236) != 0L || questState.getQuestItemsCount(1233) <= 0L) break;
                    questState.setCond(7);
                    questState.playSound("ItemSound.quest_middle");
                    string = "allana_q0409_05.htm";
                    break;
                }
                if (n3 != 30428 || questState.getQuestItemsCount(1231) <= 0L || questState.getQuestItemsCount(1234) <= 0L || questState.getQuestItemsCount(1236) <= 0L) break;
                if (questState.getQuestItemsCount(1275) == 1L) {
                    questState.setCond(6);
                    questState.playSound("ItemSound.quest_middle");
                    questState.takeItems(1275, 1L);
                    questState.giveItems(1232, 1L);
                    string = "perrin_q0409_04.htm";
                    break;
                }
                string = questState.getQuestItemsCount(1232) > 0L ? "perrin_q0409_05.htm" : (n == 3 ? "perrin_q0409_06.htm" : "perrin_q0409_01.htm");
            }
        }
        return string;
    }

    public String onAttack(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("q409_lizardman_warrior_cry");
        int n3 = questState.getInt("q409_lizardman_scout_cry");
        int n4 = questState.getInt("q409_lizardman_cry");
        int n5 = questState.getInt("tamato_cry");
        if (n == 27032 && n2 == 0) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.THE_SACRED_FLAME_IS_OURS", (Object[])new Object[0]);
            questState.set("q409_lizardman_warrior_cry", 1);
        } else if (n == 27033 && n3 == 0) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.THE_SACRED_FLAME_IS_OURS", (Object[])new Object[0]);
            questState.set("q409_lizardman_scout_cry", 1);
        } else if (n == 27034 && n4 == 0) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.THE_SACRED_FLAME_IS_OURS", (Object[])new Object[0]);
            questState.set("q409_lizardman_cry", 1);
        } else if (n == 27035 && n5 == 0) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.AS_YOU_WISH_MASTER", (Object[])new Object[0]);
            questState.set("tamato_cry", 1);
        }
        return null;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 27032 || n == 27033 || n == 27034) {
            if (questState.getQuestItemsCount(1234) == 0L) {
                if (n == 27032) {
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.ARRGHH_WE_SHALL_NEVER_SURRENDER", (Object[])new Object[0]);
                }
                questState.giveItems(1234, 1L);
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(3);
            }
        } else if (n == 27035 && questState.getQuestItemsCount(1275) == 0L) {
            questState.giveItems(1275, 1L);
            questState.playSound("ItemSound.quest_middle");
            questState.setCond(5);
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
