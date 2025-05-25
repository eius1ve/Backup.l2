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

public class _622_DeliveryofSpecialLiquor
extends Quest
implements ScriptFile {
    private static int bvz = 31521;
    private static int bvN = 31267;
    private static int bvB = 31543;
    private static int bvC = 31544;
    private static int bvD = 31545;
    private static int bvE = 31546;
    private static int bvF = 31547;
    private static int bvO = 7207;
    private static int bvP = 7198;
    private static int bvJ = 6849;
    private static int bvK = 6847;
    private static int bvL = 6851;
    private static int bvI = 734;
    private static int bvM = 20;

    public _622_DeliveryofSpecialLiquor() {
        super(0);
        this.addStartNpc(bvz);
        this.addTalkId(new int[]{bvN});
        this.addTalkId(new int[]{bvB});
        this.addTalkId(new int[]{bvC});
        this.addTalkId(new int[]{bvD});
        this.addTalkId(new int[]{bvE});
        this.addTalkId(new int[]{bvF});
        this.addQuestItem(new int[]{bvO});
        this.addQuestItem(new int[]{bvP});
    }

    private static void e(QuestState questState, int n) {
        questState.setCond(Integer.valueOf(n).intValue());
        questState.takeItems(bvO, 1L);
        questState.giveItems(bvP, 1L);
        questState.playSound("ItemSound.quest_middle");
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        int n = questState.getState();
        int n2 = questState.getCond();
        long l = questState.getQuestItemsCount(bvO);
        if (string.equalsIgnoreCase("jeremy_q0622_0104.htm") && n == 1) {
            questState.setState(2);
            questState.setCond(1);
            questState.takeItems(bvO, -1L);
            questState.takeItems(bvP, -1L);
            questState.giveItems(bvO, 5L);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("beolin_q0622_0201.htm") && n2 == 1 && l > 0L) {
            _622_DeliveryofSpecialLiquor.e(questState, 2);
        } else if (string.equalsIgnoreCase("kuber_q0622_0301.htm") && n2 == 2 && l > 0L) {
            _622_DeliveryofSpecialLiquor.e(questState, 3);
        } else if (string.equalsIgnoreCase("crocus_q0622_0401.htm") && n2 == 3 && l > 0L) {
            _622_DeliveryofSpecialLiquor.e(questState, 4);
        } else if (string.equalsIgnoreCase("naff_q0622_0501.htm") && n2 == 4 && l > 0L) {
            _622_DeliveryofSpecialLiquor.e(questState, 5);
        } else if (string.equalsIgnoreCase("pulin_q0622_0601.htm") && n2 == 5 && l > 0L) {
            _622_DeliveryofSpecialLiquor.e(questState, 6);
        } else if (string.equalsIgnoreCase("jeremy_q0622_0701.htm") && n2 == 6 && questState.getQuestItemsCount(bvP) >= 5L) {
            questState.setCond(7);
        } else if (string.equalsIgnoreCase("warehouse_keeper_lietta_q0622_0801.htm") && n2 == 7 && questState.getQuestItemsCount(bvP) >= 5L) {
            questState.takeItems(bvO, -1L);
            questState.takeItems(bvP, -1L);
            if (Rnd.chance((int)bvM)) {
                if (Rnd.chance((int)40)) {
                    questState.giveItems(bvJ, 1L);
                } else if (Rnd.chance((int)40)) {
                    questState.giveItems(bvK, 1L);
                } else {
                    questState.giveItems(bvL, 1L);
                }
            } else {
                questState.giveItems(57, 18800L);
                questState.giveItems(bvI, 1L, true);
            }
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
        }
        return string;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        if (questState.getState() == 1) {
            if (n != bvz) {
                return string;
            }
            if (questState.getPlayer().getLevel() >= 68) {
                questState.setCond(0);
                return "jeremy_q0622_0101.htm";
            }
            questState.exitCurrentQuest(true);
            return "jeremy_q0622_0103.htm";
        }
        int n2 = questState.getCond();
        long l = questState.getQuestItemsCount(bvO);
        long l2 = questState.getQuestItemsCount(bvP);
        if (n2 == 1 && n == bvF && l > 0L) {
            string = "beolin_q0622_0101.htm";
        } else if (n2 == 2 && n == bvE && l > 0L) {
            string = "kuber_q0622_0201.htm";
        } else if (n2 == 3 && n == bvD && l > 0L) {
            string = "crocus_q0622_0301.htm";
        } else if (n2 == 4 && n == bvC && l > 0L) {
            string = "naff_q0622_0401.htm";
        } else if (n2 == 5 && n == bvB && l > 0L) {
            string = "pulin_q0622_0501.htm";
        } else if (n2 == 6 && n == bvz && l2 >= 5L) {
            string = "jeremy_q0622_0601.htm";
        } else if (n2 == 7 && n == bvz && l2 >= 5L) {
            string = "jeremy_q0622_0703.htm";
        } else if (n2 == 7 && n == bvN && l2 >= 5L) {
            string = "warehouse_keeper_lietta_q0622_0701.htm";
        } else if (n2 > 0 && n == bvz && l > 0L) {
            string = "jeremy_q0622_0104.htm";
        }
        return string;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
