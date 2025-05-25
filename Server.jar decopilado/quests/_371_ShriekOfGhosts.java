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

public class _371_ShriekOfGhosts
extends Quest
implements ScriptFile {
    private static int aPM = 30867;
    private static int aMY = 30929;
    private static int aPN = 20818;
    private static int aPO = 20820;
    private static int aPP = 20824;
    private static int aPQ = 6002;
    private static int aPR = 6003;
    private static int aPS = 6004;
    private static int aPT = 6005;
    private static int aPU = 6006;
    private static int aPV = 5903;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _371_ShriekOfGhosts() {
        super(1);
        this.addStartNpc(aPM);
        this.addTalkId(new int[]{aMY});
        this.addKillId(new int[]{aPN, aPO, aPP});
        this.addQuestItem(new int[]{aPV});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == aPM) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("spirits_cry_secrets", String.valueOf(1), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "seer_reva_q0371_03.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                if (questState.getQuestItemsCount(aPV) < 1L) {
                    string2 = "seer_reva_q0371_06.htm";
                } else if (questState.getQuestItemsCount(aPV) >= 1L && questState.getQuestItemsCount(aPV) < 100L) {
                    questState.giveItems(57, questState.getQuestItemsCount(aPV) * 1000L + 15000L);
                    questState.takeItems(aPV, -1L);
                    this.giveExtraReward(questState.getPlayer());
                    string2 = "seer_reva_q0371_07.htm";
                } else if (questState.getQuestItemsCount(aPV) >= 100L) {
                    questState.giveItems(57, questState.getQuestItemsCount(aPV) * 1000L + 37700L);
                    questState.takeItems(aPV, -1L);
                    this.giveExtraReward(questState.getPlayer());
                    string2 = "seer_reva_q0371_08.htm";
                }
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "seer_reva_q0371_09.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                if (questState.getQuestItemsCount(aPV) > 0L) {
                    questState.giveItems(57, questState.getQuestItemsCount(aPV) * 1000L);
                }
                questState.takeItems(aPV, -1L);
                questState.unset("spirits_cry_secrets");
                questState.exitCurrentQuest(true);
                string2 = "seer_reva_q0371_10.htm";
            }
        } else if (n == aMY && string.equalsIgnoreCase("reply_1")) {
            if (questState.getQuestItemsCount(aPQ) < 1L) {
                string2 = "patrin_q0371_02.htm";
            } else if (questState.getQuestItemsCount(aPQ) >= 1L) {
                int n2 = Rnd.get((int)100);
                if (n2 < 2) {
                    questState.giveItems(aPR, 1L);
                    questState.takeItems(aPQ, 1L);
                    string2 = "patrin_q0371_03.htm";
                } else if (n2 < 32) {
                    questState.giveItems(aPS, 1L);
                    questState.takeItems(aPQ, 1L);
                    string2 = "patrin_q0371_04.htm";
                } else if (n2 < 62) {
                    questState.giveItems(aPT, 1L);
                    questState.takeItems(aPQ, 1L);
                    string2 = "patrin_q0371_05.htm";
                } else if (n2 < 77) {
                    questState.giveItems(aPU, 1L);
                    questState.takeItems(aPQ, 1L);
                    string2 = "patrin_q0371_06.htm";
                } else {
                    questState.giveItems(aPQ, 1L);
                    string2 = "patrin_q0371_07.htm";
                }
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("spirits_cry_secrets");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != aPM) break;
                if (questState.getPlayer().getLevel() < 59) {
                    string = "seer_reva_q0371_01.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (questState.getPlayer().getLevel() < 59) break;
                string = "seer_reva_q0371_02.htm";
                break;
            }
            case 2: {
                if (n2 == aPM) {
                    if (n == 1 && questState.getQuestItemsCount(aPQ) < 1L) {
                        string = "seer_reva_q0371_04.htm";
                        break;
                    }
                    if (n != 1 || questState.getQuestItemsCount(aPQ) < 1L) break;
                    string = "seer_reva_q0371_05.htm";
                    break;
                }
                if (n2 != aMY || n != 1) break;
                string = "patrin_q0371_01.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("spirits_cry_secrets");
        int n2 = npcInstance.getNpcId();
        if (n == 1) {
            if (n2 == aPN) {
                int n3 = Rnd.get((int)1000);
                if (n3 < 350) {
                    questState.giveItems(aPV, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                } else if (n3 < 400) {
                    questState.giveItems(aPQ, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (n2 == aPO) {
                int n4 = Rnd.get((int)1000);
                if (n4 < 583) {
                    questState.giveItems(aPV, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                } else if (n4 < 673) {
                    questState.giveItems(aPQ, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (n2 == aPP) {
                int n5 = Rnd.get((int)1000);
                if (n5 < 458) {
                    questState.giveItems(aPV, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                } else if (n5 < 538) {
                    questState.giveItems(aPQ, 1L);
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        }
        return null;
    }
}
