/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.ScriptFile;

public class _510_AClansReputation
extends Quest
implements ScriptFile {
    private static final int bmP = 31331;
    private static final int bmQ = 8767;
    private static final int bmR = 50;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _510_AClansReputation() {
        super(2);
        this.addStartNpc(31331);
        int n = 22215;
        while (n <= 22217) {
            this.addKillId(new int[]{n++});
        }
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        int n = questState.getCond();
        String string2 = string;
        if (string.equals("31331-3.htm")) {
            if (n == 0) {
                questState.setCond(1);
                questState.setState(2);
            }
        } else if (string.equals("31331-6.htm")) {
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        Player player = questState.getPlayer();
        Clan clan = player.getClan();
        if (player.getClan() == null || !player.isClanLeader()) {
            questState.exitCurrentQuest(true);
            string = "31331-0.htm";
        } else if (player.getClan().getLevel() < 5) {
            questState.exitCurrentQuest(true);
            string = "31331-0.htm";
        } else {
            int n = questState.getCond();
            int n2 = questState.getState();
            if (n2 == 1 && n == 0) {
                string = "31331-1.htm";
            } else if (n2 == 2 && n == 1) {
                long l = questState.getQuestItemsCount(8767);
                if (l == 0L) {
                    string = "31331-4.htm";
                } else if (l >= 1L) {
                    string = "31331-7.htm";
                    questState.takeItems(8767, -1L);
                    int n3 = 50 * (int)l * (int)Config.RATE_CLAN_REP_SCORE;
                    this.giveExtraReward(questState.getPlayer());
                    int n4 = clan.incReputation(n3, true, "_510_AClansReputation");
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_SUCCESSFULLY_COMPLETED_A_CLAN_QUEST_S1_POINTS_HAVE_BEEN_ADDED_TO_YOUR_CLANS_REPUTATION_SCORE).addNumber(n4));
                }
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n;
        if (!questState.getPlayer().isClanLeader()) {
            questState.exitCurrentQuest(true);
        } else if (questState.getState() == 2 && (n = npcInstance.getNpcId()) >= 22215 && n <= 22218) {
            questState.giveItems(8767, 1L);
            questState.playSound("ItemSound.quest_itemget");
        }
        return null;
    }
}
