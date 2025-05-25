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

public class _154_SacrificeToSea
extends Quest
implements ScriptFile {
    private static final int TG = 1032;
    private static final int TH = 1033;
    private static final int TI = 1034;
    private static final int TJ = 113;
    private static final int TK = 30312;
    private static final int TL = 30051;
    private static final int TM = 30055;
    private static final int TN = 20481;
    private static final int TO = 20544;
    private static final int TP = 20545;

    public _154_SacrificeToSea() {
        super(0);
        this.addStartNpc(30312);
        this.addTalkId(new int[]{30051, 30055});
        this.addKillId(new int[]{20481, 20544, 20545});
        this.addQuestItem(new int[]{1032, 1033, 1034});
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.set("sacrifice_to_sea", String.valueOf(1), true);
            string2 = "rockswell_q0304_04.htm";
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        int n3 = questState.getInt("sacrifice_to_sea");
        switch (n2) {
            case 1: {
                if (n != 30312) break;
                if (questState.getPlayer().getLevel() >= 2) {
                    string = "rockswell_q0304_03.htm";
                    break;
                }
                string = "rockswell_q0304_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n == 30312) {
                    if (n3 == 1 && questState.getQuestItemsCount(1033) == 0L && questState.getQuestItemsCount(1034) == 0L && questState.getQuestItemsCount(1032) < 10L) {
                        string = "rockswell_q0304_05.htm";
                    } else if (n3 == 1 && questState.getQuestItemsCount(1032) >= 10L) {
                        string = "rockswell_q0304_08.htm";
                    } else if (n3 == 1 && questState.getQuestItemsCount(1033) >= 1L) {
                        string = "rockswell_q0304_06.htm";
                    } else if (n3 == 1 && questState.getQuestItemsCount(1034) >= 1L) {
                        string = "rockswell_q0304_07.htm";
                        questState.takeItems(1034, -1L);
                        questState.takeItems(1032, -1L);
                        questState.giveItems(113, 1L);
                        questState.unset("sacrifice_to_sea");
                        questState.playSound("ItemSound.quest_finish");
                        this.giveExtraReward(questState.getPlayer());
                        questState.exitCurrentQuest(false);
                    }
                }
                if (n == 30051) {
                    if (n3 == 1 && questState.getQuestItemsCount(1032) < 10L && questState.getQuestItemsCount(1032) > 0L) {
                        string = "cristel_q0304_01.htm";
                        break;
                    }
                    if (n3 == 1 && questState.getQuestItemsCount(1032) >= 10L && questState.getQuestItemsCount(1033) == 0L && questState.getQuestItemsCount(1034) == 0L && questState.getQuestItemsCount(1034) < 10L) {
                        string = "cristel_q0304_02.htm";
                        questState.giveItems(1033, 1L);
                        questState.takeItems(1032, -1L);
                        questState.setCond(3);
                        questState.playSound("ItemSound.quest_middle");
                        break;
                    }
                    if (n3 == 1 && questState.getQuestItemsCount(1033) >= 1L) {
                        string = "cristel_q0304_03.htm";
                        break;
                    }
                    if (n3 != 1 || questState.getQuestItemsCount(1034) != 1L) break;
                    string = "cristel_q0304_04.htm";
                    break;
                }
                if (n != 30055) break;
                if (n3 == 1 && questState.getQuestItemsCount(1033) >= 1L) {
                    string = "rollfnan_q0304_01.htm";
                    questState.giveItems(1034, 1L);
                    questState.takeItems(1033, -1L);
                    questState.setCond(4);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (n3 == 1 && questState.getQuestItemsCount(1034) >= 1L) {
                    string = "rollfnan_q0304_02.htm";
                    break;
                }
                if (n3 != 1 || questState.getQuestItemsCount(1033) != 0L || questState.getQuestItemsCount(1034) != 0L) break;
                string = "rollfnan_q0304_03.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getQuestItemsCount(1032) < 10L && questState.getQuestItemsCount(1033) == 0L && questState.getQuestItemsCount(1034) == 0L && Rnd.get((int)10) < 4) {
            questState.rollAndGive(1032, 1, 100.0);
            if (questState.getQuestItemsCount(1032) >= 10L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(2);
            }
        }
        return null;
    }
}
