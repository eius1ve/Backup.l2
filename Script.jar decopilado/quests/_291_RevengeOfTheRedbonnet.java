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

public class _291_RevengeOfTheRedbonnet
extends Quest
implements ScriptFile {
    int MaryseRedbonnet = 30553;
    int BlackWolfPelt = 1482;
    int ScrollOfEscape = 736;
    int GrandmasPearl = 1502;
    int GrandmasMirror = 1503;
    int GrandmasNecklace = 1504;
    int GrandmasHairpin = 1505;
    int BlackWolf = 20317;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _291_RevengeOfTheRedbonnet() {
        super(0);
        this.addStartNpc(this.MaryseRedbonnet);
        this.addTalkId(new int[]{this.MaryseRedbonnet});
        this.addKillId(new int[]{this.BlackWolf});
        this.addQuestItem(new int[]{this.BlackWolfPelt});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("marife_redbonnet_q0291_03.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        if (n == 0) {
            if (questState.getPlayer().getLevel() < 4) {
                string = "marife_redbonnet_q0291_01.htm";
                questState.exitCurrentQuest(true);
            } else {
                string = "marife_redbonnet_q0291_02.htm";
            }
        } else if (n == 1) {
            string = "marife_redbonnet_q0291_04.htm";
        } else if (n == 2 && questState.getQuestItemsCount(this.BlackWolfPelt) < 40L) {
            string = "marife_redbonnet_q0291_04.htm";
            questState.setCond(1);
        } else if (n == 2 && questState.getQuestItemsCount(this.BlackWolfPelt) >= 40L) {
            int n2 = Rnd.get((int)100);
            questState.takeItems(this.BlackWolfPelt, -1L);
            if (n2 < 3) {
                questState.giveItems(this.GrandmasPearl, 1L);
            } else if (n2 < 21) {
                questState.giveItems(this.GrandmasMirror, 1L);
            } else if (n2 < 46) {
                questState.giveItems(this.GrandmasNecklace, 1L);
            } else {
                questState.giveItems(this.ScrollOfEscape, 1L);
                questState.giveItems(this.GrandmasHairpin, 1L);
            }
            string = "marife_redbonnet_q0291_05.htm";
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getCond() == 1 && questState.getQuestItemsCount(this.BlackWolfPelt) < 40L) {
            questState.giveItems(this.BlackWolfPelt, 1L);
            if (questState.getQuestItemsCount(this.BlackWolfPelt) < 40L) {
                questState.playSound("ItemSound.quest_itemget");
            } else {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(2);
                questState.setState(2);
            }
        }
        return null;
    }
}
