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

public class _036_MakeASewingKit
extends Quest
implements ScriptFile {
    private static final int My = 30847;
    private static final int Mz = 20566;
    private static final int MA = 7163;
    private static final int MB = 7164;
    private static final int MC = 1891;
    private static final int MD = 1893;
    private static final int ME = 7078;

    public _036_MakeASewingKit() {
        super(2);
        this.addStartNpc(30847);
        this.addKillId(new int[]{20566});
        this.addQuestItem(new int[]{7163});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("make_a_workbox_cookie");
        int n2 = npcInstance.getNpcId();
        if (n2 == 30847) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("make_a_workbox", String.valueOf(11), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "head_blacksmith_ferris_q0036_0104.htm";
            } else if (string.equalsIgnoreCase("reply_1") && n == 1) {
                if (questState.getQuestItemsCount(7163) >= 5L) {
                    questState.setCond(3);
                    questState.set("make_a_workbox", String.valueOf(21), true);
                    questState.takeItems(7163, 5L);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "head_blacksmith_ferris_q0036_0201.htm";
                } else {
                    string2 = "head_blacksmith_ferris_q0036_0202.htm";
                }
            } else if (string.equalsIgnoreCase("reply_3") && n == 2) {
                if (questState.getQuestItemsCount(1891) >= 10L && questState.getQuestItemsCount(1893) >= 10L) {
                    questState.takeItems(1891, 10L);
                    questState.takeItems(1893, 10L);
                    questState.giveItems(7078, 1L);
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(false);
                    string2 = "head_blacksmith_ferris_q0036_0301.htm";
                } else {
                    string2 = "head_blacksmith_ferris_q0036_0302.htm";
                }
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("make_a_workbox");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30847) break;
                if (questState.getPlayer().getLevel() < 60) {
                    string = "head_blacksmith_ferris_q0036_0103.htm";
                    break;
                }
                if (questState.getPlayer().getQuestState(_037_PleaseMakeMeFormalWear.class) != null && questState.getPlayer().getQuestState(_037_PleaseMakeMeFormalWear.class).isStarted() && questState.getQuestItemsCount(7164) >= 1L) {
                    string = "head_blacksmith_ferris_q0036_0101.htm";
                    break;
                }
                string = "head_blacksmith_ferris_q0036_0102.htm";
                break;
            }
            case 2: {
                if (n2 != 30847) break;
                if (n >= 11 && n <= 12) {
                    if (n == 12 && questState.getQuestItemsCount(7163) >= 5L) {
                        questState.set("make_a_workbox_cookie", String.valueOf(1), true);
                        string = "head_blacksmith_ferris_q0036_0105.htm";
                        break;
                    }
                    string = "head_blacksmith_ferris_q0036_0106.htm";
                    break;
                }
                if (n != 21) break;
                if (questState.getQuestItemsCount(1891) >= 10L && questState.getQuestItemsCount(1893) >= 10L) {
                    questState.set("make_a_workbox_cookie", String.valueOf(2), true);
                    string = "head_blacksmith_ferris_q0036_0203.htm";
                    break;
                }
                string = "head_blacksmith_ferris_q0036_0204.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n;
        int n2 = questState.getInt("make_a_workbox");
        int n3 = npcInstance.getNpcId();
        if (n3 == 20566 && n2 == 11 && (n = Rnd.get((int)1000)) < 500) {
            if (questState.getQuestItemsCount(7163) + 1L >= 5L) {
                if (questState.getQuestItemsCount(7163) < 5L) {
                    questState.giveItems(7163, 5L - questState.getQuestItemsCount(7163));
                    questState.playSound("ItemSound.quest_middle");
                }
                questState.setCond(2);
                questState.set("make_a_workbox", String.valueOf(12), true);
            } else {
                questState.giveItems(7163, 1L);
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
