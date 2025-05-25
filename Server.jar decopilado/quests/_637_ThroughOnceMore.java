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

public class _637_ThroughOnceMore
extends Quest
implements ScriptFile {
    private static final int bxI = 32010;
    private static final int bxJ = 21565;
    private static final int bxK = 21566;
    private static final int bxL = 21567;
    private static final int bxM = 8066;
    private static final int bxN = 8064;
    private static final int bxO = 8065;
    private static final int bxP = 8067;
    private static final int bxQ = 8273;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _637_ThroughOnceMore() {
        super(0);
        this.addStartNpc(32010);
        this.addKillId(new int[]{21565, 21566, 21567});
        this.addQuestItem(new int[]{8066});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 32010) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("beyond_the_door_agai", String.valueOf(1), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "falsepriest_flauron_q0637_11.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                string2 = "falsepriest_flauron_q0637_06.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "falsepriest_flauron_q0637_07.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                string2 = "falsepriest_flauron_q0637_08.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                string2 = "falsepriest_flauron_q0637_09.htm";
            } else if (string.equalsIgnoreCase("reply_5")) {
                string2 = "falsepriest_flauron_q0637_10.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("beyond_the_door_agai");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 32010) break;
                if (questState.getPlayer().getLevel() >= 73 && questState.getQuestItemsCount(8065) >= 1L && questState.getQuestItemsCount(8067) == 0L) {
                    string = "falsepriest_flauron_q0637_01.htm";
                    break;
                }
                string = "falsepriest_flauron_q0637_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n2 != 32010) break;
                if (n == 1 && questState.getQuestItemsCount(8064) >= 1L && questState.getQuestItemsCount(8065) == 0L && questState.getQuestItemsCount(8067) == 0L) {
                    string = "falsepriest_flauron_q0637_03.htm";
                    break;
                }
                if (n == 1 && questState.getQuestItemsCount(8064) == 0L && questState.getQuestItemsCount(8065) == 0L && questState.getQuestItemsCount(8067) == 0L) {
                    string = "falsepriest_flauron_q0637_04.htm";
                    break;
                }
                if (n == 1 && questState.getQuestItemsCount(8067) >= 1L) {
                    string = "falsepriest_flauron_q0637_05.htm";
                    break;
                }
                if (n == 1 && questState.getQuestItemsCount(8066) < 10L) {
                    string = "falsepriest_flauron_q0637_12.htm";
                    break;
                }
                if (n != 1 || questState.getQuestItemsCount(8066) < 10L) break;
                questState.giveItems(8067, 1L);
                questState.giveItems(8273, 10L);
                questState.takeItems(8064, -1L);
                questState.takeItems(8065, -1L);
                questState.takeItems(8066, -1L);
                questState.unset("beyond_the_door_agai");
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
                string = "falsepriest_flauron_q0637_13.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("beyond_the_door_agai");
        int n2 = npcInstance.getNpcId();
        if (n == 1) {
            if (n2 == 21565) {
                questState.rollAndGive(8066, 1, 84.0);
                long l = questState.getQuestItemsCount(8066);
                if (l >= 10L) {
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else if (n2 == 21566) {
                questState.rollAndGive(8066, 1, 92.0);
                long l = questState.getQuestItemsCount(8066);
                if (l >= 10L) {
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                }
            } else if (n2 == 21567) {
                questState.rollAndGive(8066, 1, 10.0);
                long l = questState.getQuestItemsCount(8066);
                if (l >= 10L) {
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                }
            }
        }
        return null;
    }
}
