/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.xml.holder.ResidenceHolder
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.residence.ClanHall
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.TimeUtils
 */
package quests;

import java.util.Calendar;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.TimeUtils;

public class _504_CompetitionForTheBanditStronghold
extends Quest
implements ScriptFile {
    private static final int bmi = 35437;
    private static final int bmj = 20570;
    private static final int bmk = 20571;
    private static final int bml = 20572;
    private static final int bmm = 20573;
    private static final int bmn = 20574;
    private static final int bmo = 4332;
    private static final int bmp = 5009;
    private static final int bmq = 4333;

    public _504_CompetitionForTheBanditStronghold() {
        super(2);
        this.addStartNpc(35437);
        this.addTalkId(new int[]{35437});
        this.addKillId(new int[]{20570});
        this.addKillId(new int[]{20571});
        this.addKillId(new int[]{20572});
        this.addKillId(new int[]{20573});
        this.addKillId(new int[]{20574});
        this.addQuestItem(new int[]{4333, 4332, 5009});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("azit_messenger_q0504_02.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.giveItems(4333, 1L);
            questState.playSound("ItemSound.quest_accept");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        Player player = questState.getPlayer();
        Clan clan = player.getClan();
        ClanHall clanHall = (ClanHall)ResidenceHolder.getInstance().getResidence(35);
        if (clanHall.getSiegeEvent().isRegistrationOver()) {
            string = null;
            this.showHtmlFile(player, "azit_messenger_q0504_03.htm", false, new Object[]{"%siege_time%", TimeUtils.toSimpleFormat((Calendar)clanHall.getSiegeDate())});
        } else if (clan == null || player.getObjectId() != clan.getLeaderId()) {
            string = "azit_messenger_q0504_05.htm";
        } else if (player.getObjectId() == clan.getLeaderId() && clan.getLevel() < 4) {
            string = "azit_messenger_q0504_04.htm";
        } else if (clanHall.getSiegeEvent().getSiegeClan("attackers", player.getClan()) != null) {
            string = "azit_messenger_q0504_06.htm";
        } else if (clan.getHasHideout() > 0) {
            string = "azit_messenger_q0504_10.htm";
        } else if (n == 0) {
            string = "azit_messenger_q0504_01.htm";
        } else if (questState.getQuestItemsCount(4333) == 1L && questState.getQuestItemsCount(4332) < 30L) {
            string = "azit_messenger_q0504_07.htm";
        } else if (questState.getQuestItemsCount(5009) >= 1L) {
            string = "azit_messenger_q0504_07a.htm";
        } else if (questState.getQuestItemsCount(4333) == 1L && questState.getQuestItemsCount(4332) == 30L) {
            questState.takeItems(4332, -1L);
            questState.takeItems(4333, -1L);
            questState.giveItems(5009, 1L);
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.setCond(-1);
            string = "azit_messenger_q0504_08.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getQuestItemsCount(4332) < 30L) {
            questState.giveItems(4332, 1L);
            questState.playSound("ItemSound.quest_itemget");
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
