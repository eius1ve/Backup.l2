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

public class _341_HuntingForWildBeasts
extends Quest
implements ScriptFile {
    private static int aGe = 30078;
    private static int aIg = 20021;
    private static int aIh = 20203;
    private static int aIi = 20310;
    private static int aIj = 20335;
    private static int aIk = 4259;
    private static int aIl = 40;

    public _341_HuntingForWildBeasts() {
        super(0);
        this.addStartNpc(aGe);
        this.addKillId(new int[]{aIg});
        this.addKillId(new int[]{aIh});
        this.addKillId(new int[]{aIi});
        this.addKillId(new int[]{aIj});
        this.addQuestItem(new int[]{aIk});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept") && questState.getState() == 1) {
            string2 = "pano_q0341_04.htm";
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        if (npcInstance.getNpcId() != aGe) {
            return string;
        }
        int n = questState.getState();
        if (n == 1) {
            if (questState.getPlayer().getLevel() >= 20) {
                string = "pano_q0341_01.htm";
                questState.setCond(0);
            } else {
                string = "pano_q0341_02.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (n == 2) {
            if (questState.getQuestItemsCount(aIk) >= 20L) {
                string = "pano_q0341_05.htm";
                questState.takeItems(aIk, -1L);
                questState.giveItems(57, 3710L);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
            } else {
                string = "pano_q0341_06.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getState() != 2) {
            return null;
        }
        long l = questState.getQuestItemsCount(aIk);
        if (l < 20L && Rnd.chance((int)aIl)) {
            questState.giveItems(aIk, 1L);
            if (l == 19L) {
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            } else {
                questState.playSound("ItemSound.quest_itemget");
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
