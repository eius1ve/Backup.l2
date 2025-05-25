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

public class _624_TheFinestIngredientsPart1
extends Quest
implements ScriptFile {
    private static final int bvR = 31521;
    private static final int bvS = 21321;
    private static final int bvT = 21319;
    private static final int bvU = 21317;
    private static final int bvV = 21314;
    private static final int bvW = 7204;
    private static final int bvX = 7202;
    private static final int bvY = 7203;
    private static final int bvZ = 7080;
    private static final int bwa = 7205;

    public _624_TheFinestIngredientsPart1() {
        super(1);
        this.addStartNpc(31521);
        this.addKillId(new int[]{21321, 21319, 21317, 21314});
        this.addQuestItem(new int[]{7202, 7203, 7204});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setState(2);
            questState.set("finest_ingredients_part1", String.valueOf(11), true);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
            string2 = "jeremy_q0624_0104.htm";
        } else if (string.equalsIgnoreCase("reply_3")) {
            if (questState.getQuestItemsCount(7202) >= 50L && questState.getQuestItemsCount(7203) >= 50L && questState.getQuestItemsCount(7204) >= 50L) {
                questState.takeItems(7202, -1L);
                questState.takeItems(7203, -1L);
                questState.takeItems(7204, -1L);
                questState.giveItems(7205, 1L);
                questState.giveItems(7080, 1L);
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                string2 = "jeremy_q0624_0201.htm";
            } else {
                string2 = "jeremy_q0624_0202.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getState();
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getInt("finest_ingredients_part1");
        switch (n) {
            case 1: {
                if (n2 != 31521) break;
                if (questState.getPlayer().getLevel() >= 73) {
                    string = "jeremy_q0624_0101.htm";
                    break;
                }
                string = "jeremy_q0624_0103.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n2 != 31521 || n3 < 11 || n3 > 12) break;
                string = n3 == 12 && questState.getQuestItemsCount(7203) >= 50L && questState.getQuestItemsCount(7202) >= 50L && questState.getQuestItemsCount(7204) >= 50L ? "jeremy_q0624_0105.htm" : "jeremy_q0624_0106.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 21321) {
            if (questState.getQuestItemsCount(7204) < 50L) {
                questState.rollAndGive(7204, 1, 100.0);
            }
            if (questState.getQuestItemsCount(7204) >= 50L && questState.getQuestItemsCount(7203) >= 50L && questState.getQuestItemsCount(7202) >= 50L) {
                questState.setCond(2);
                questState.set("finest_ingredients_part1", String.valueOf(12), true);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 21319) {
            if (questState.getQuestItemsCount(7202) < 50L) {
                questState.rollAndGive(7202, 1, 100.0);
            }
            if (questState.getQuestItemsCount(7204) >= 50L && questState.getQuestItemsCount(7203) >= 50L && questState.getQuestItemsCount(7202) >= 50L) {
                questState.setCond(2);
                questState.set("finest_ingredients_part1", String.valueOf(12), true);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 21317) {
            if (questState.getQuestItemsCount(7204) < 50L) {
                questState.rollAndGive(7204, 1, 100.0);
            }
            if (questState.getQuestItemsCount(7204) >= 50L && questState.getQuestItemsCount(7203) >= 50L && questState.getQuestItemsCount(7202) >= 50L) {
                questState.setCond(2);
                questState.set("finest_ingredients_part1", String.valueOf(12), true);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 21314) {
            if (questState.getQuestItemsCount(7203) < 50L) {
                questState.rollAndGive(7203, 1, 100.0);
            }
            if (questState.getQuestItemsCount(7204) >= 50L && questState.getQuestItemsCount(7203) >= 50L && questState.getQuestItemsCount(7202) >= 50L) {
                questState.setCond(2);
                questState.set("finest_ingredients_part1", String.valueOf(12), true);
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
