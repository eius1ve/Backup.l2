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

public class _659_IdRatherBeCollectingFairyBreath
extends Quest
implements ScriptFile {
    private static final int bCC = 30634;
    private static final int[] bT = new int[]{20078, 21026, 21025, 21024, 21023};
    private static final int bCD = 8286;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _659_IdRatherBeCollectingFairyBreath() {
        super(0);
        this.addStartNpc(30634);
        this.addTalkId(new int[]{30634});
        this.addTalkId(new int[]{30634});
        this.addTalkId(new int[]{30634});
        for (int n : bT) {
            this.addKillId(new int[]{n});
        }
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("high_summoner_galatea_q0659_0103.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("high_summoner_galatea_q0659_0203.htm")) {
            long l = questState.getQuestItemsCount(8286);
            if (l > 0L) {
                long l2 = 0L;
                l2 = l < 10L ? l * 50L : l * 50L + 5365L;
                questState.takeItems(8286, -1L);
                questState.giveItems(57, l2);
                this.giveExtraReward(questState.getPlayer());
            }
        } else if (string.equalsIgnoreCase("high_summoner_galatea_q0659_0204.htm")) {
            questState.exitCurrentQuest(true);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getState();
        int n3 = 0;
        if (n2 != 1) {
            n3 = questState.getCond();
        }
        if (n == 30634) {
            if (questState.getPlayer().getLevel() < 26) {
                string = "high_summoner_galatea_q0659_0102.htm";
                questState.exitCurrentQuest(true);
            } else if (n3 == 0) {
                string = "high_summoner_galatea_q0659_0101.htm";
            } else if (n3 == 1) {
                string = questState.getQuestItemsCount(8286) == 0L ? "high_summoner_galatea_q0659_0105.htm" : "high_summoner_galatea_q0659_0105.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n2 == 1) {
            for (int n3 : bT) {
                if (n != n3 || !Rnd.chance((int)30)) continue;
                questState.giveItems(8286, 1L);
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
