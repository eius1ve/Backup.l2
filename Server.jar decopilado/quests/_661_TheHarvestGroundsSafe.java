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

public class _661_TheHarvestGroundsSafe
extends Quest
implements ScriptFile {
    private static final int bCS = 30210;
    private static final int bCT = 21095;
    private static final int bCU = 21096;
    private static final int bCV = 21097;
    private static final int bCW = 8283;
    private static final int bCX = 8284;
    private static final int bCY = 8285;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _661_TheHarvestGroundsSafe() {
        super(2);
        this.addStartNpc(30210);
        this.addKillId(new int[]{21095, 21096, 21097});
        this.addQuestItem(new int[]{8283, 8284, 8285});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("clear_gathering_site_cookie");
        int n2 = npcInstance.getNpcId();
        if (n2 == 30210) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("clear_gathering_site", String.valueOf(11), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "warehouse_keeper_norman_q0661_0103.htm";
            } else if (string.equalsIgnoreCase("reply_1") && n == 1) {
                string2 = "warehouse_keeper_norman_q0661_0201.htm";
            } else if (string.equalsIgnoreCase("reply_3") && n == 1) {
                if (questState.getQuestItemsCount(8283) == 0L && questState.getQuestItemsCount(8284) == 0L && questState.getQuestItemsCount(8285) == 0L) {
                    string2 = "warehouse_keeper_norman_q0661_0202.htm";
                }
                if (questState.getQuestItemsCount(8283) + questState.getQuestItemsCount(8284) + questState.getQuestItemsCount(8285) >= 10L) {
                    questState.giveItems(57, 5773L + 57L * questState.getQuestItemsCount(8283) + 56L * questState.getQuestItemsCount(8284) + 60L * questState.getQuestItemsCount(8285));
                } else {
                    questState.giveItems(57, 57L * questState.getQuestItemsCount(8283) + 56L * questState.getQuestItemsCount(8284) + 60L * questState.getQuestItemsCount(8285));
                }
                string2 = "warehouse_keeper_norman_q0661_0203.htm";
                questState.takeItems(8283, -1L);
                questState.takeItems(8284, -1L);
                questState.takeItems(8285, -1L);
                this.giveExtraReward(questState.getPlayer());
            } else if (string.equalsIgnoreCase("reply_4") && n == 1) {
                string2 = "warehouse_keeper_norman_q0661_0204.htm";
                questState.unset("clear_gathering_site_cookie");
                questState.unset("clear_gathering_site");
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("clear_gathering_site");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30210) break;
                if (questState.getPlayer().getLevel() < 21) {
                    string = "warehouse_keeper_norman_q0661_0102.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "warehouse_keeper_norman_q0661_0101.htm";
                break;
            }
            case 2: {
                if (n2 != 30210 || n != 11) break;
                if (questState.getQuestItemsCount(8283) == 0L && questState.getQuestItemsCount(8284) == 0L && questState.getQuestItemsCount(8285) == 0L) {
                    string = "warehouse_keeper_norman_q0661_0106.htm";
                    break;
                }
                questState.set("clear_gathering_site_cookie", String.valueOf(1), true);
                string = "warehouse_keeper_norman_q0661_0105.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("clear_gathering_site");
        int n2 = npcInstance.getNpcId();
        if (n == 11) {
            if (n2 == 21095) {
                questState.rollAndGive(8283, 1, 50.8);
            } else if (n2 == 21096) {
                questState.rollAndGive(8284, 1, 50.0);
            } else if (n2 == 21097) {
                questState.rollAndGive(8285, 1, 51.6);
            }
        }
        return null;
    }
}
