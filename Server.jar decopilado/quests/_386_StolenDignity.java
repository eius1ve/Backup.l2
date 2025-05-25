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

import java.util.HashMap;
import java.util.Map;
import l2.commons.util.Rnd;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _386_StolenDignity
extends Quest
implements ScriptFile {
    private static final int aVy = 30843;
    private static final int aVz = 6363;
    private static final int aVA = 100;
    private static final Map<Integer, Integer> ci = new HashMap<Integer, Integer>();
    private static final Map<Integer, Bingo> cj = new HashMap<Integer, Bingo>();
    private static final int[][] M = new int[][]{{5529, 10}, {5532, 10}, {5533, 10}, {5534, 10}, {5535, 10}, {5536, 10}, {5537, 10}, {5538, 10}, {5539, 10}, {5541, 10}, {5542, 10}, {5543, 10}, {5544, 10}, {5545, 10}, {5546, 10}, {5547, 10}, {5548, 10}, {8331, 10}, {8341, 10}, {8342, 10}, {8346, 10}, {8349, 10}, {8712, 10}, {8713, 10}, {8714, 10}, {8715, 10}, {8716, 10}, {8717, 10}, {8718, 10}, {8719, 10}, {8720, 10}, {8721, 10}, {8722, 10}};
    private static final int[][] N = new int[][]{{5529, 4}, {5532, 4}, {5533, 4}, {5534, 4}, {5535, 4}, {5536, 4}, {5537, 4}, {5538, 4}, {5539, 4}, {5541, 4}, {5542, 4}, {5543, 4}, {5544, 4}, {5545, 4}, {5546, 4}, {5547, 4}, {5548, 4}, {8331, 4}, {8341, 4}, {8342, 4}, {8346, 4}, {8349, 4}, {8712, 4}, {8713, 4}, {8714, 4}, {8715, 4}, {8716, 4}, {8717, 4}, {8718, 4}, {8719, 4}, {8720, 4}, {8721, 4}, {8722, 4}};

    public _386_StolenDignity() {
        super(1);
        this.addStartNpc(30843);
        ci.put(20670, 14);
        ci.put(20671, 14);
        ci.put(20954, 11);
        ci.put(20956, 13);
        ci.put(20958, 13);
        ci.put(20959, 13);
        ci.put(20960, 11);
        ci.put(20964, 13);
        ci.put(20969, 19);
        ci.put(20967, 18);
        ci.put(20970, 18);
        ci.put(20971, 18);
        ci.put(20974, 28);
        ci.put(20975, 28);
        ci.put(21001, 14);
        ci.put(21003, 18);
        ci.put(21005, 14);
        ci.put(21020, 16);
        ci.put(21021, 15);
        ci.put(21259, 15);
        ci.put(21089, 13);
        ci.put(21108, 19);
        ci.put(21110, 18);
        ci.put(21113, 25);
        ci.put(21114, 23);
        ci.put(21116, 25);
        for (int n : ci.keySet()) {
            this.addKillId(new int[]{n});
        }
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        if (string.equalsIgnoreCase("warehouse_keeper_romp_q0386_05.htm")) {
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("warehouse_keeper_romp_q0386_08.htm")) {
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
        } else {
            if (string.equalsIgnoreCase("game")) {
                if (questState.getQuestItemsCount(6363) < 100L) {
                    return "warehouse_keeper_romp_q0386_11.htm";
                }
                questState.takeItems(6363, 100L);
                int n = questState.getPlayer().getObjectId();
                if (cj.containsKey(n)) {
                    cj.remove(n);
                }
                Bingo bingo = new Bingo(questState);
                cj.put(n, bingo);
                return bingo.getDialog("");
            }
            if (string.contains("choice-")) {
                int n = questState.getPlayer().getObjectId();
                if (!cj.containsKey(n)) {
                    return null;
                }
                Bingo bingo = cj.get(n);
                return bingo.Select(string.replaceFirst("choice-", ""));
            }
        }
        return string;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        if (questState.getState() == 1) {
            if (questState.getPlayer().getLevel() < 58) {
                questState.exitCurrentQuest(true);
                return "warehouse_keeper_romp_q0386_04.htm";
            }
            return "warehouse_keeper_romp_q0386_01.htm";
        }
        return questState.getQuestItemsCount(6363) < 100L ? "warehouse_keeper_romp_q0386_06.htm" : "warehouse_keeper_romp_q0386_07.htm";
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        Integer n = ci.get(npcInstance.getNpcId());
        if (n != null) {
            questState.rollAndGive(6363, 1, (double)n.intValue());
        }
        return null;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public static class Bingo
    extends quests.Bingo {
        protected static final String msg_begin = "I've arranged the numbers 1 through 9 on the grid. Don't peek!<br>Let me have the 100 Infernium Ores. Too many players try to run away without paying when it becomes obvious that they're losing...<br>OK, select six numbers between 1 and 9. Choose the %choicenum% number.";
        protected static final String msg_again = "You've already chosen that number. Make your %choicenum% choice again.";
        protected static final String msg_0lines = "Wow! How unlucky can you get? Your choices are highlighted in red below. As you can see, your choices didn't make a single line! Losing this badly is actually quite rare!<br>You look so sad, I feel bad for you... Wait here... <br>.<br>.<br>.<br>Take this... I hope it will bring you better luck in the future.";
        protected static final String msg_3lines = "Excellent! As you can see, you've formed three lines! Congratulations! As promised, I'll give you some unclaimed merchandise from the warehouse. Wait here...<br>.<br>.<br>.<br>Whew, it's dusty! OK, here you go. Do you like it?";
        protected static final String msg_lose = "Oh, too bad. Your choices didn't form three lines. You should try again... Your choices are highlighted in red.";
        private static final String hm = "<a action=\"bypass -h Quest _386_StolenDignity choice-%n%\">%n%</a>&nbsp;&nbsp;&nbsp;&nbsp;  ";
        private final QuestState c;

        public Bingo(QuestState questState) {
            super(hm);
            this.c = questState;
        }

        @Override
        protected String getFinal() {
            String string = super.getFinal();
            if (this.lines == 3) {
                this.a(M);
            } else if (this.lines == 0) {
                this.a(N);
            }
            cj.remove(this.c.getPlayer().getObjectId());
            return string;
        }

        private void a(int[][] nArray) {
            int[] nArray2 = nArray[Rnd.get((int)nArray.length)];
            this.c.giveItems(nArray2[0], (long)nArray2[1], false);
        }
    }
}
