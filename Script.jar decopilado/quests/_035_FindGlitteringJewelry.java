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
import quests._037_PleaseMakeMeFormalWear;

public class _035_FindGlitteringJewelry
extends Quest
implements ScriptFile {
    private static final int Mp = 30091;
    private static final int Mq = 30879;
    private static final int Mr = 20135;
    private static final int Ms = 7162;
    private static final int Mt = 7164;
    private static final int Mu = 1893;
    private static final int Mv = 1873;
    private static final int Mw = 4044;
    private static final int Mx = 7077;

    public _035_FindGlitteringJewelry() {
        super(2);
        this.addStartNpc(30091);
        this.addTalkId(new int[]{30879});
        this.addKillId(new int[]{20135});
        this.addQuestItem(new int[]{7162});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("find_jewel_cookie");
        int n2 = npcInstance.getNpcId();
        if (n2 == 30091) {
            if (string.equalsIgnoreCase("quest_accept") && questState.getQuestItemsCount(7164) >= 1L) {
                questState.setCond(1);
                questState.set("find_jewel", String.valueOf(11), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "elliany_q0035_0104.htm";
            } else if (string.equalsIgnoreCase("reply_1") && n == 2) {
                if (questState.getQuestItemsCount(7162) >= 10L) {
                    questState.setCond(4);
                    questState.set("find_jewel", String.valueOf(31), true);
                    questState.takeItems(7162, 10L);
                    string2 = "elliany_q0035_0301.htm";
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    string2 = "elliany_q0035_0302.htm";
                }
            } else if (string.equalsIgnoreCase("reply_3") && n == 3) {
                if (questState.getQuestItemsCount(1893) >= 5L && questState.getQuestItemsCount(1873) >= 500L && questState.getQuestItemsCount(4044) >= 150L) {
                    questState.takeItems(1893, 5L);
                    questState.takeItems(1873, 500L);
                    questState.takeItems(4044, 150L);
                    questState.giveItems(7077, 1L);
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(false);
                    questState.playSound("ItemSound.quest_finish");
                    string2 = "elliany_q0035_0401.htm";
                } else {
                    string2 = "elliany_q0035_0402.htm";
                }
            }
        } else if (n2 == 30879 && string.equalsIgnoreCase("reply_1") && n == 1) {
            questState.setCond(2);
            questState.set("find_jewel", String.valueOf(21), true);
            questState.playSound("ItemSound.quest_middle");
            string2 = "wharf_manager_felton_q0035_0201.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("find_jewel");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30091) break;
                if (questState.getPlayer().getLevel() < 60) {
                    string = "elliany_q0035_0103.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (questState.getPlayer().getQuestState(_037_PleaseMakeMeFormalWear.class) != null && questState.getPlayer().getQuestState(_037_PleaseMakeMeFormalWear.class).isStarted() && questState.getQuestItemsCount(7164) >= 1L) {
                    string = "elliany_q0035_0101.htm";
                    break;
                }
                string = "elliany_q0035_0102.htm";
                break;
            }
            case 2: {
                if (n2 == 30091) {
                    if (n == 11) {
                        string = "elliany_q0035_0105.htm";
                        break;
                    }
                    if (n == 22) {
                        if (questState.getQuestItemsCount(7162) >= 10L) {
                            questState.set("find_jewel_cookie", String.valueOf(2), true);
                            string = "elliany_q0035_0201.htm";
                            break;
                        }
                        string = "elliany_q0035_0202.htm";
                        break;
                    }
                    if (n != 31) break;
                    if (questState.getQuestItemsCount(1893) >= 5L && questState.getQuestItemsCount(1873) >= 500L && questState.getQuestItemsCount(4044) >= 150L) {
                        questState.set("find_jewel_cookie", String.valueOf(3), true);
                        string = "elliany_q0035_0303.htm";
                        break;
                    }
                    string = "elliany_q0035_0304.htm";
                    break;
                }
                if (n2 != 30879) break;
                if (n == 11) {
                    questState.set("find_jewel_cookie", String.valueOf(1), true);
                    string = "wharf_manager_felton_q0035_0101.htm";
                    break;
                }
                if (n != 21) break;
                string = "wharf_manager_felton_q0035_0202.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n;
        int n2 = questState.getInt("find_jewel");
        int n3 = npcInstance.getNpcId();
        if (n3 == 20135 && n2 == 21 && (n = Rnd.get((int)1000)) < 500) {
            if (questState.getQuestItemsCount(7162) + 1L >= 10L) {
                if (questState.getQuestItemsCount(7162) < 10L) {
                    questState.giveItems(7162, 10L - questState.getQuestItemsCount(7162));
                    questState.playSound("ItemSound.quest_middle");
                }
                questState.setCond(3);
                questState.set("find_jewel", String.valueOf(22), true);
            } else {
                questState.giveItems(7162, 1L);
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
