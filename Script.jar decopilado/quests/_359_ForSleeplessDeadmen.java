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

public class _359_ForSleeplessDeadmen
extends Quest
implements ScriptFile {
    private static final int aNG = 10;
    private static final int aNH = 60;
    private static final int aNI = 5869;
    private static final int aNJ = 6341;
    private static final int aNK = 6342;
    private static final int aNL = 6343;
    private static final int aNM = 6344;
    private static final int aNN = 6345;
    private static final int aNO = 6346;
    private static final int aNP = 5494;
    private static final int aNQ = 5495;
    private static final int aNR = 30857;
    private static final int aNS = 21006;
    private static final int aNT = 21007;
    private static final int aNU = 21008;
    private static final int aNV = 21009;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _359_ForSleeplessDeadmen() {
        super(0);
        this.addStartNpc(30857);
        this.addKillId(new int[]{21006});
        this.addKillId(new int[]{21007});
        this.addKillId(new int[]{21008});
        this.addKillId(new int[]{21009});
        this.addQuestItem(new int[]{5869});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getCond();
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
            string2 = "highpriest_orven_q0359_06.htm";
        } else if (string.equalsIgnoreCase("reply_1")) {
            string2 = "highpriest_orven_q0359_05.htm";
        } else if (string.equalsIgnoreCase("reply_2") && n == 3) {
            questState.setCond(1);
            int n2 = Rnd.get((int)100);
            int n3 = n2 <= 16 ? 6343 : (n2 <= 33 ? 6341 : (n2 <= 50 ? 6345 : (n2 <= 58 ? 6344 : (n2 <= 67 ? 6342 : (n2 <= 76 ? 6346 : (n2 <= 84 ? 5494 : 5495))))));
            questState.giveItems(n3, 4L, true);
            this.giveExtraReward(questState.getPlayer());
            string2 = "highpriest_orven_q0359_10.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getState();
        int n2 = questState.getCond();
        if (n == 1) {
            if (questState.getPlayer().getLevel() < 60) {
                questState.exitCurrentQuest(true);
                string = "highpriest_orven_q0359_01.htm";
            } else {
                string = "highpriest_orven_q0359_02.htm";
            }
        } else if (n == 2) {
            if (n2 == 1 && questState.getQuestItemsCount(5869) < 60L) {
                string = "highpriest_orven_q0359_07.htm";
            } else if (n2 == 2 && questState.getQuestItemsCount(5869) < 60L) {
                string = "highpriest_orven_q0359_07.htm";
            } else if (n2 == 2 && questState.getQuestItemsCount(5869) >= 60L) {
                questState.takeItems(5869, 60L);
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(3);
                string = "highpriest_orven_q0359_08.htm";
            } else if (n2 == 3) {
                string = "highpriest_orven_q0359_09.htm";
            }
        } else {
            string = "highpriest_orven_q0359_05.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getQuestItemsCount(5869) < 60L) {
            questState.rollAndGive(5869, 1, 36.0);
            if (questState.getQuestItemsCount(5869) >= 60L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(2);
            }
        }
        return null;
    }
}
