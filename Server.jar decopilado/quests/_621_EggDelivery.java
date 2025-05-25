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

public class _621_EggDelivery
extends Quest
implements ScriptFile {
    private static int bvz = 31521;
    private static int bvA = 31584;
    private static int bvB = 31543;
    private static int bvC = 31544;
    private static int bvD = 31545;
    private static int bvE = 31546;
    private static int bvF = 31547;
    private static final int bvG = 7206;
    private static final int bvH = 7196;
    private static final int bvI = 734;
    private static final int bvJ = 6849;
    private static final int bvK = 6847;
    private static final int bvL = 6851;
    private static int bvM = 20;

    public _621_EggDelivery() {
        super(0);
        this.addStartNpc(bvz);
        this.addTalkId(new int[]{bvA});
        this.addTalkId(new int[]{bvB});
        this.addTalkId(new int[]{bvC});
        this.addTalkId(new int[]{bvD});
        this.addTalkId(new int[]{bvE});
        this.addTalkId(new int[]{bvF});
        this.addQuestItem(new int[]{7206});
        this.addQuestItem(new int[]{7196});
    }

    private static void d(QuestState questState, int n) {
        questState.setCond(Integer.valueOf(n).intValue());
        questState.takeItems(7206, 1L);
        questState.giveItems(7196, 1L);
        questState.playSound("ItemSound.quest_middle");
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        int n = questState.getState();
        int n2 = questState.getCond();
        long l = questState.getQuestItemsCount(7206);
        if (string.equalsIgnoreCase("jeremy_q0621_0104.htm") && n == 1) {
            questState.takeItems(7206, -1L);
            questState.takeItems(7196, -1L);
            questState.setState(2);
            questState.setCond(1);
            questState.giveItems(7206, 5L);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("pulin_q0621_0201.htm") && n2 == 1 && l > 0L) {
            _621_EggDelivery.d(questState, 2);
        } else if (string.equalsIgnoreCase("naff_q0621_0301.htm") && n2 == 2 && l > 0L) {
            _621_EggDelivery.d(questState, 3);
        } else if (string.equalsIgnoreCase("crocus_q0621_0401.htm") && n2 == 3 && l > 0L) {
            _621_EggDelivery.d(questState, 4);
        } else if (string.equalsIgnoreCase("kuber_q0621_0501.htm") && n2 == 4 && l > 0L) {
            _621_EggDelivery.d(questState, 5);
        } else if (string.equalsIgnoreCase("beolin_q0621_0601.htm") && n2 == 5 && l > 0L) {
            _621_EggDelivery.d(questState, 6);
        } else if (string.equalsIgnoreCase("jeremy_q0621_0701.htm") && n2 == 6 && questState.getQuestItemsCount(7196) >= 5L) {
            questState.setCond(7);
        } else if (string.equalsIgnoreCase("brewer_valentine_q0621_0801.htm") && n2 == 7 && questState.getQuestItemsCount(7196) >= 5L) {
            questState.takeItems(7206, -1L);
            questState.takeItems(7196, -1L);
            if (Rnd.chance((int)bvM)) {
                if (Rnd.chance((int)40)) {
                    questState.giveItems(6849, 1L, true);
                } else if (Rnd.chance((int)40)) {
                    questState.giveItems(6847, 1L, true);
                } else {
                    questState.giveItems(6851, 1L, true);
                }
            } else {
                questState.giveItems(57, 18800L);
                questState.giveItems(734, 1L, true);
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
                return "jeremy_q0621_0101.htm";
            }
            questState.exitCurrentQuest(true);
            return "jeremy_q0621_0103.htm";
        }
        int n2 = questState.getCond();
        long l = questState.getQuestItemsCount(7206);
        long l2 = questState.getQuestItemsCount(7196);
        if (n2 == 1 && n == bvB && l > 0L) {
            string = "pulin_q0621_0101.htm";
        }
        if (n2 == 2 && n == bvC && l > 0L) {
            string = "naff_q0621_0201.htm";
        }
        if (n2 == 3 && n == bvD && l > 0L) {
            string = "crocus_q0621_0301.htm";
        }
        if (n2 == 4 && n == bvE && l > 0L) {
            string = "kuber_q0621_0401.htm";
        }
        if (n2 == 5 && n == bvF && l > 0L) {
            string = "beolin_q0621_0501.htm";
        }
        if (n2 == 6 && n == bvz && l2 >= 5L) {
            string = "jeremy_q0621_0601.htm";
        }
        if (n2 == 7 && n == bvz && l2 >= 5L) {
            string = "jeremy_q0621_0703.htm";
        }
        if (n2 == 7 && n == bvA && l2 >= 5L) {
            string = "brewer_valentine_q0621_0701.htm";
        } else if (n2 > 0 && n == bvz && l > 0L) {
            string = "jeremy_q0621_0104.htm";
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
