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

public class _362_BardsMandolin
extends Quest
implements ScriptFile {
    private static final int aOf = 30957;
    private static final int aOg = 30956;
    private static final int aOh = 30958;
    private static final int aOi = 30837;
    private static final int aOj = 4316;
    private static final int aOk = 4317;
    private static final int aOl = 4410;

    public _362_BardsMandolin() {
        super(0);
        this.addStartNpc(30957);
        this.addTalkId(new int[]{30956, 30958, 30837});
        this.addQuestItem(new int[]{4316, 4317});
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        int n3 = questState.getInt("bards_mandolin");
        switch (n2) {
            case 1: {
                if (n != 30957) break;
                if (questState.getPlayer().getLevel() >= 15) {
                    string = "swan_q0362_01.htm";
                    break;
                }
                string = "swan_q0362_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n == 30957) {
                    if (n3 >= 1 && n3 < 3) {
                        string = "swan_q0362_04.htm";
                        break;
                    }
                    if (n3 == 3 || n3 == 4) {
                        if (n3 == 3) {
                            questState.setCond(4);
                            questState.giveItems(4317, 1L);
                            questState.set("bards_mandolin", 4);
                            string = "swan_q0362_05.htm";
                            break;
                        }
                        string = "swan_q0362_05.htm";
                        break;
                    }
                    if (n3 != 5) break;
                    string = "swan_q0362_06.htm";
                    break;
                }
                if (n == 30956) {
                    if (n3 == 4 && questState.getQuestItemsCount(4317) > 0L && questState.getQuestItemsCount(4316) > 0L) {
                        questState.setCond(5);
                        questState.takeItems(4317, 1L);
                        questState.takeItems(4316, 1L);
                        questState.set("bards_mandolin", 5);
                        string = "nanarin_q0362_01.htm";
                    }
                    if (n3 < 5) break;
                    string = "nanarin_q0362_02.htm";
                    break;
                }
                if (n == 30958) {
                    if (n3 == 2) {
                        questState.setCond(3);
                        questState.giveItems(4316, 1L);
                        questState.set("bards_mandolin", 3);
                        string = "galion_q0362_01.htm";
                    }
                    if (n3 < 3) break;
                    string = "galion_q0362_02.htm";
                    break;
                }
                if (n != 30837) break;
                if (n3 == 1) {
                    questState.setCond(2);
                    questState.set("bards_mandolin", 2);
                    string = "woodrow_q0362_01.htm";
                }
                if (n3 == 2) {
                    string = "woodrow_q0362_02.htm";
                }
                if (n3 < 3) break;
                string = "woodrow_q0362_03.htm";
            }
        }
        return string;
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("bards_mandolin");
        if (n == 30957) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("bards_mandolin", 1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "swan_q0362_03.htm";
            }
            if (string.equalsIgnoreCase("reply_3") && n2 == 5) {
                questState.giveItems(57, 10000L);
                questState.giveItems(4410, 1L);
                string2 = "swan_q0362_07.htm";
                this.giveExtraReward(questState.getPlayer());
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(false);
            }
            if (string.equalsIgnoreCase("reply_4") && n2 == 5) {
                questState.giveItems(57, 10000L);
                questState.giveItems(4410, 1L);
                string2 = "swan_q0362_08.htm";
                this.giveExtraReward(questState.getPlayer());
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(false);
            }
        }
        return string2;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
