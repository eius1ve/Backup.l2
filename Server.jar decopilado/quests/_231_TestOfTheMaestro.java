/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _231_TestOfTheMaestro
extends Quest
implements ScriptFile {
    private static final int amS = 30531;
    private static final int amT = 30533;
    private static final int amU = 30536;
    private static final int amV = 30535;
    private static final int amW = 30532;
    private static final int amX = 30671;
    private static final int amY = 30672;
    private static final int amZ = 30556;
    private static final int ana = 30673;
    private static final int anb = 2864;
    private static final int anc = 2865;
    private static final int and = 2866;
    private static final int ane = 2868;
    private static final int anf = 2869;
    private static final int ang = 2870;
    private static final int anh = 2871;
    private static final int ani = 2872;
    private static final int anj = 2873;
    private static final int ank = 2874;
    private static final int anl = 2875;
    private static final int anm = 2876;
    private static final int ann = 2877;
    private static final int ano = 2878;
    private static final int anp = 2916;
    private static final int anq = 7562;
    private static final int anr = 2867;
    private static final int ans = 27133;
    private static final int ant = 20225;
    private static final int anu = 20229;
    private static final int anv = 20233;
    private static final int anw = 20150;

    public _231_TestOfTheMaestro() {
        super(0);
        this.addStartNpc(30531);
        this.addTalkId(new int[]{30533});
        this.addTalkId(new int[]{30536});
        this.addTalkId(new int[]{30535});
        this.addTalkId(new int[]{30532});
        this.addTalkId(new int[]{30671});
        this.addTalkId(new int[]{30672});
        this.addTalkId(new int[]{30556});
        this.addTalkId(new int[]{30673});
        this.addKillId(new int[]{27133, 20225, 20229, 20233, 20150});
        this.addQuestItem(new int[]{2864, 2865, 2866, 2868, 2869, 2870, 2871, 2872, 2873, 2874, 2875, 2876, 2877, 2878, 2916});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30531) {
            if (string.equalsIgnoreCase("quest_accept") && questState.getPlayer().getClassId().getId() == 56 && questState.getPlayer().getLevel() >= 39) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                questState.set("test_of_the_maestro", String.valueOf(1), true);
                questState.playSound("ItemSound.quest_middle");
                string2 = "first_elder_lockirin_q0231_04.htm";
            }
        } else if (n == 30533) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "elder_balanki_q0231_02.htm";
                questState.set("test_of_the_maestro", String.valueOf(2), true);
            }
        } else if (n == 30671) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "chief_croto_q0231_02.htm";
                questState.giveItems(2869, 1L);
            }
        } else if (n == 30556) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "master_toma_q0231_02.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "master_toma_q0231_03.htm";
            } else if (string.equalsIgnoreCase("reply_3") && questState.getQuestItemsCount(2871) > 0L) {
                string2 = "master_toma_q0231_05.htm";
                questState.giveItems(2916, 1L);
                questState.takeItems(2871, 1L);
                questState.getPlayer().teleToLocation(140352, -194133, -3146);
                NpcInstance npcInstance2 = questState.addSpawn(20150, 140333, -194153, -3138, 200000);
                NpcInstance npcInstance3 = questState.addSpawn(20150, 140395, -194147, -3146, 200000);
                NpcInstance npcInstance4 = questState.addSpawn(20150, 140304, -194082, -3157, 200000);
                if (npcInstance2 != null) {
                    npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                } else if (npcInstance3 != null) {
                    npcInstance3.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                } else if (npcInstance4 != null) {
                    npcInstance4.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)questState.getPlayer(), (Object)20000);
                }
            } else if (string.equalsIgnoreCase("reply_4")) {
                string2 = "master_toma_q0231_04.htm";
            }
        }
        if (n == 30673 && string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(2875) > 0L && questState.getQuestItemsCount(2876) >= 10L && questState.getQuestItemsCount(2877) >= 10L && questState.getQuestItemsCount(2878) >= 10L) {
            string2 = "researcher_lorain_q0231_04.htm";
            questState.giveItems(2874, 1L);
            questState.takeAllItems(new int[]{2876, 2877, 2878, 2875});
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("test_of_the_maestro");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30531) break;
                if (questState.getPlayer().getClassId().getId() == 56 && questState.getPlayer().getLevel() >= 39) {
                    string = "first_elder_lockirin_q0231_03.htm";
                    break;
                }
                if (questState.getPlayer().getClassId().getId() == 56) {
                    string = "first_elder_lockirin_q0231_01.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "first_elder_lockirin_q0231_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n2 == 30531) {
                    if (n >= 1 && (questState.getQuestItemsCount(2864) == 0L || questState.getQuestItemsCount(2865) == 0L || questState.getQuestItemsCount(2866) == 0L)) {
                        string = "first_elder_lockirin_q0231_05.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2864) < 1L || questState.getQuestItemsCount(2865) < 1L || questState.getQuestItemsCount(2866) < 1L) break;
                    if (!questState.getPlayer().getVarB("prof2.3")) {
                        questState.giveItems(7562, 8L);
                        questState.getPlayer().setVar("prof2.3", "1", -1L);
                        questState.addExpAndSp(154499L, 37500L);
                        this.giveExtraReward(questState.getPlayer());
                    }
                    string = "first_elder_lockirin_q0231_06.htm";
                    questState.giveItems(2867, 1L);
                    questState.takeAllItems(new int[]{2864, 2865, 2866});
                    questState.unset("test_of_the_maestro");
                    questState.exitCurrentQuest(false);
                    questState.playSound("ItemSound.quest_finish");
                    break;
                }
                if (n2 == 30533) {
                    if (n == 1 && questState.getQuestItemsCount(2864) == 0L) {
                        string = "elder_balanki_q0231_01.htm";
                        break;
                    }
                    if (n == 2 && questState.getQuestItemsCount(2868) == 0L) {
                        string = "elder_balanki_q0231_03.htm";
                        break;
                    }
                    if (n == 2 && questState.getQuestItemsCount(2868) > 0L) {
                        string = "elder_balanki_q0231_04.htm";
                        questState.giveItems(2864, 1L);
                        questState.takeItems(2868, 1L);
                        questState.set("test_of_the_maestro", String.valueOf(1), true);
                        if (questState.getQuestItemsCount(2866) < 1L || questState.getQuestItemsCount(2865) < 1L) break;
                        questState.setCond(2);
                        questState.playSound("ItemSound.quest_finish");
                        break;
                    }
                    if (questState.getQuestItemsCount(2864) <= 0L) break;
                    string = "elder_balanki_q0231_05.htm";
                    break;
                }
                if (n2 == 30536) {
                    if (n == 1 && questState.getQuestItemsCount(2866) == 0L) {
                        string = "elder_arin_q0231_01.htm";
                        questState.giveItems(2871, 1L);
                        questState.set("test_of_the_maestro", String.valueOf(3), true);
                        break;
                    }
                    if (n == 3 && questState.getQuestItemsCount(2871) > 0L && questState.getQuestItemsCount(2872) == 0L) {
                        string = "elder_arin_q0231_02.htm";
                        break;
                    }
                    if (n == 3 && questState.getQuestItemsCount(2872) >= 5L) {
                        string = "elder_arin_q0231_03.htm";
                        questState.giveItems(2866, 1L);
                        questState.takeItems(2872, questState.getQuestItemsCount(2872));
                        questState.set("test_of_the_maestro", String.valueOf(1), true);
                        if (questState.getQuestItemsCount(2864) < 1L || questState.getQuestItemsCount(2865) < 1L) break;
                        questState.setCond(2);
                        questState.playSound("ItemSound.quest_finish");
                        break;
                    }
                    if (questState.getQuestItemsCount(2866) <= 0L) break;
                    string = "elder_arin_q0231_04.htm";
                    break;
                }
                if (n2 == 30535) {
                    if (n == 1 && questState.getQuestItemsCount(2865) == 0L) {
                        string = "elder_filaur_q0231_01.htm";
                        questState.giveItems(2873, 1L);
                        questState.set("test_of_the_maestro", String.valueOf(4), true);
                        break;
                    }
                    if (n == 4 && questState.getQuestItemsCount(2873) > 0L && questState.getQuestItemsCount(2874) == 0L) {
                        string = "elder_filaur_q0231_02.htm";
                        break;
                    }
                    if (n == 4 && questState.getQuestItemsCount(2873) == 0L && questState.getQuestItemsCount(2874) > 0L) {
                        string = "elder_filaur_q0231_03.htm";
                        questState.giveItems(2865, 1L);
                        questState.takeItems(2874, 1L);
                        questState.set("test_of_the_maestro", String.valueOf(1), true);
                        if (questState.getQuestItemsCount(2864) < 1L || questState.getQuestItemsCount(2866) < 1L) break;
                        questState.setCond(2);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (questState.getQuestItemsCount(2865) <= 0L) break;
                    string = "elder_filaur_q0231_04.htm";
                    break;
                }
                if (n2 == 30671) {
                    if (n == 2 && questState.getQuestItemsCount(2869) == 0L && questState.getQuestItemsCount(2870) == 0L && questState.getQuestItemsCount(2868) == 0L) {
                        string = "chief_croto_q0231_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2869) > 0L && questState.getQuestItemsCount(2870) == 0L) {
                        string = "chief_croto_q0231_03.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(2870) > 0L) {
                        string = "chief_croto_q0231_04.htm";
                        questState.giveItems(2868, 1L);
                        questState.takeItems(2870, 1L);
                        questState.takeItems(2869, questState.getQuestItemsCount(2869));
                        break;
                    }
                    if (questState.getQuestItemsCount(2868) <= 0L) break;
                    string = "chief_croto_q0231_05.htm";
                    break;
                }
                if (n2 == 30672) {
                    if (questState.getQuestItemsCount(2869) <= 0L) break;
                    string = "jailer_dubabah_q0231_01.htm";
                    break;
                }
                if (n2 == 30556) {
                    if (n == 3 && questState.getQuestItemsCount(2871) > 0L) {
                        string = "master_toma_q0231_01.htm";
                        break;
                    }
                    if (n == 3 && questState.getQuestItemsCount(2916) > 0L) {
                        string = "master_toma_q0231_06.htm";
                        questState.giveItems(2872, 5L);
                        questState.takeItems(2916, 1L);
                        break;
                    }
                    if (n != 3 || questState.getQuestItemsCount(2872) != 5L) break;
                    string = "master_toma_q0231_07.htm";
                    break;
                }
                if (n2 != 30673) break;
                if (n == 4 && questState.getQuestItemsCount(2873) > 0L && questState.getQuestItemsCount(2875) == 0L && questState.getQuestItemsCount(2874) == 0L) {
                    string = "researcher_lorain_q0231_01.htm";
                    questState.giveItems(2875, 1L);
                    questState.takeItems(2873, 1L);
                    break;
                }
                if (n == 4 && questState.getQuestItemsCount(2875) > 0L && (questState.getQuestItemsCount(2876) < 10L || questState.getQuestItemsCount(2877) < 10L || questState.getQuestItemsCount(2878) < 10L) && questState.getQuestItemsCount(2874) == 0L) {
                    string = "researcher_lorain_q0231_02.htm";
                    break;
                }
                if (n == 4 && questState.getQuestItemsCount(2875) > 0L && questState.getQuestItemsCount(2876) >= 10L && questState.getQuestItemsCount(2877) >= 10L && questState.getQuestItemsCount(2878) >= 10L && questState.getQuestItemsCount(2874) == 0L) {
                    string = "researcher_lorain_q0231_03.htm";
                    break;
                }
                if (questState.getQuestItemsCount(2874) <= 0L) break;
                string = "researcher_lorain_q0231_05.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("test_of_the_maestro");
        if (n == 27133 && n2 == 2 && questState.getQuestItemsCount(2870) == 0L && questState.getQuestItemsCount(2869) > 0L) {
            questState.giveItems(2870, 1L);
        } else if (n == 20225 && n2 == 4 && questState.getQuestItemsCount(2878) < 10L && questState.getQuestItemsCount(2875) == 1L) {
            questState.rollAndGive(2878, 1, 100.0);
            if (questState.getQuestItemsCount(2878) >= 10L) {
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 20229 && n2 == 4 && questState.getQuestItemsCount(2876) < 10L && questState.getQuestItemsCount(2875) == 1L) {
            questState.rollAndGive(2876, 1, 100.0);
            if (questState.getQuestItemsCount(2876) >= 10L) {
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 20233 && n2 == 4 && questState.getQuestItemsCount(2877) < 10L && questState.getQuestItemsCount(2875) == 1L) {
            questState.rollAndGive(2877, 1, 100.0);
            if (questState.getQuestItemsCount(2877) >= 10L) {
                questState.playSound("ItemSound.quest_middle");
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
