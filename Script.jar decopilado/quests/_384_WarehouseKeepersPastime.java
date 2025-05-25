/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import java.util.HashMap;
import java.util.Map;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _384_WarehouseKeepersPastime
extends Quest
implements ScriptFile {
    private static final int aVv = 30182;
    private static final int aVw = 30685;
    private static final int aVx = 5964;
    private static final Map<Integer, Integer> cg = new HashMap<Integer, Integer>();
    private static final Map<Integer, Bingo> ch = new HashMap<Integer, Bingo>();
    private static final int[][] I = new int[][]{{16, 1888, 1}, {32, 1887, 1}, {50, 1894, 1}, {80, 952, 1}, {89, 1890, 1}, {98, 1893, 1}, {100, 951, 1}};
    private static final int[][] J = new int[][]{{50, 883, 1}, {80, 951, 1}, {98, 852, 1}, {100, 401, 1}};
    private static final int[][] K = new int[][]{{50, 4041, 1}, {80, 952, 1}, {98, 1892, 1}, {100, 917, 1}};
    private static final int[][] L = new int[][]{{50, 951, 1}, {80, 500, 1}, {98, 2437, 2}, {100, 135, 1}};

    public _384_WarehouseKeepersPastime() {
        super(0);
        this.addStartNpc(30182);
        this.addTalkId(new int[]{30685});
        cg.put(20948, 18);
        cg.put(20945, 12);
        cg.put(20946, 15);
        cg.put(20947, 16);
        cg.put(20635, 15);
        cg.put(20773, 61);
        cg.put(20774, 60);
        cg.put(20760, 24);
        cg.put(20758, 24);
        cg.put(20759, 23);
        cg.put(20242, 22);
        cg.put(20281, 22);
        cg.put(20556, 14);
        cg.put(20668, 21);
        cg.put(20241, 22);
        cg.put(20286, 22);
        cg.put(20950, 20);
        cg.put(20949, 19);
        cg.put(20942, 9);
        cg.put(20943, 12);
        cg.put(20944, 11);
        cg.put(20559, 14);
        cg.put(20243, 21);
        cg.put(20282, 21);
        cg.put(20677, 34);
        cg.put(20605, 15);
        for (int n : cg.keySet()) {
            this.addKillId(new int[]{n});
        }
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        int n = questState.getState();
        long l = questState.getQuestItemsCount(5964);
        if (string.equalsIgnoreCase("30182-05.htm") && n == 1) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if ((string.equalsIgnoreCase("30182-08.htm") || string.equalsIgnoreCase("30685-08.htm")) && n == 2) {
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(false);
        } else {
            if (string.contains("-game") && n == 2) {
                int n2;
                boolean bl = string.contains("-big");
                int n3 = n2 = bl ? 100 : 10;
                if (l < (long)n2) {
                    return string.replaceFirst("-big", "").replaceFirst("game", "09.htm");
                }
                questState.takeItems(5964, (long)n2);
                int n4 = questState.getPlayer().getObjectId();
                if (ch.containsKey(n4)) {
                    ch.remove(n4);
                }
                Bingo bingo = new Bingo(bl, questState);
                ch.put(n4, bingo);
                return bingo.getDialog("");
            }
            if (string.contains("choice-") && n == 2) {
                int n5 = questState.getPlayer().getObjectId();
                if (!ch.containsKey(n5)) {
                    return null;
                }
                Bingo bingo = ch.get(n5);
                return bingo.Select(string.replaceFirst("choice-", ""));
            }
        }
        return string;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getState();
        int n2 = npcInstance.getNpcId();
        if (n == 1) {
            if (n2 != 30182) {
                return "noquest";
            }
            if (questState.getPlayer().getLevel() < 40) {
                questState.exitCurrentQuest(true);
                return "30182-04.htm";
            }
            questState.setCond(0);
            return "30182-01.htm";
        }
        if (n != 2) {
            return "noquest";
        }
        long l = questState.getQuestItemsCount(5964);
        if (l >= 100L) {
            return String.valueOf(n2) + "-06.htm";
        }
        if (l >= 10L) {
            return String.valueOf(n2) + "-06a.htm";
        }
        return String.valueOf(n2) + "-06b.htm";
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getState() != 2) {
            return null;
        }
        Integer n = cg.get(npcInstance.getNpcId());
        if (n != null && Rnd.chance((double)((double)n.intValue() * Config.RATE_QUESTS_REWARD))) {
            questState.giveItems(5964, 1L);
            questState.playSound(questState.getQuestItemsCount(5964) == 10L ? "ItemSound.quest_middle" : "ItemSound.quest_itemget");
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
        protected static final String msg_begin = "I've arranged 9 numbers on the panel. Don't peek! Ha ha ha!<br>Now give me your 10 medals. Some players run away when they realize that they don't stand a good chance of winning. Therefore, I prefer to hold the medals before the game starts. If you quit during game play, you'll forfeit your bet. Is that satisfactory?<br>Now, select your %choicenum% number.";
        protected static final String msg_0lines = "You are spectacularly unlucky! The red-colored numbers on the panel below are the ones you chose. As you can see, they didn't create even a single line. Did you know that it is harder not to create a single line than creating all 3 lines?<br>Usually, I don't give a reward when you don't create a single line, but since I'm feeling sorry for you, I'll be generous this time. Wait here.<br>.<br>.<br>.<br><br><br>Here, take this. I hope it will bring you better luck in the future.";
        protected static final String msg_3lines = "You've created 3 lines! The red colored numbers on the bingo panel below are the numbers you chose. Congratulations! As I promised, I'll give you an unclaimed item from my warehouse. Wait here.<br>.<br>.<br>.<br><br><br>Puff puff... it's very dusty. Here it is. Do you like it?";
        private static final String hl = "<a action=\"bypass -h Quest _384_WarehouseKeepersPastime choice-%n%\">%n%</a>&nbsp;&nbsp;&nbsp;&nbsp;  ";
        private final boolean hw;
        private final QuestState b;

        public Bingo(boolean bl, QuestState questState) {
            super(hl);
            this.hw = bl;
            this.b = questState;
        }

        @Override
        protected String getFinal() {
            String string = super.getFinal();
            if (this.lines == 3) {
                this.a(this.hw ? J : I);
            } else if (this.lines == 0) {
                this.a(this.hw ? L : K);
            }
            ch.remove(this.b.getPlayer().getObjectId());
            return string;
        }

        private void a(int[][] nArray) {
            int n = Rnd.get((int)100);
            for (int[] nArray2 : nArray) {
                if (n >= nArray2[0]) continue;
                this.b.giveItems(nArray2[1], (long)nArray2[2], true);
                return;
            }
        }
    }
}
