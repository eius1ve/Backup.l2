/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.collections.CollectionUtils
 *  l2.gameserver.dao.SiegeClanDAO
 *  l2.gameserver.dao.SiegePlayerDAO
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.events.impl.ClanHallTeamBattleEvent
 *  l2.gameserver.model.entity.events.objects.CTBSiegeClanObject
 *  l2.gameserver.model.entity.events.objects.SiegeClanObject
 *  l2.gameserver.model.entity.residence.ClanHall
 *  l2.gameserver.model.entity.residence.Residence
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.NpcString
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.TimeUtils
 */
package npc.model.residences.clanhall;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import l2.commons.collections.CollectionUtils;
import l2.gameserver.dao.SiegeClanDAO;
import l2.gameserver.dao.SiegePlayerDAO;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.impl.ClanHallTeamBattleEvent;
import l2.gameserver.model.entity.events.objects.CTBSiegeClanObject;
import l2.gameserver.model.entity.events.objects.SiegeClanObject;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.NpcString;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.TimeUtils;
import quests._655_AGrandPlanForTamingWildBeasts;

public class FarmMessengerInstance
extends NpcInstance {
    public FarmMessengerInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        ClanHall clanHall = this.getClanHall();
        ClanHallTeamBattleEvent clanHallTeamBattleEvent = (ClanHallTeamBattleEvent)clanHall.getSiegeEvent();
        Clan clan = player.getClan();
        if (string.equalsIgnoreCase("registrationMenu")) {
            if (!this.b(player, true)) {
                return;
            }
            this.showChatWindow(player, "residence2/clanhall/farm_kel_mahum_messenger_1.htm", new Object[0]);
        } else if (string.equalsIgnoreCase("registerAsClan")) {
            if (!this.b(player, false)) {
                return;
            }
            List list = clanHallTeamBattleEvent.getObjects("attackers");
            CTBSiegeClanObject cTBSiegeClanObject = (CTBSiegeClanObject)clanHallTeamBattleEvent.getSiegeClan("attackers", clan);
            if (cTBSiegeClanObject != null) {
                this.p(player, list.indexOf(cTBSiegeClanObject));
                return;
            }
            QuestState questState = player.getQuestState(_655_AGrandPlanForTamingWildBeasts.class);
            if (questState == null || questState.getQuestItemsCount(8293) != 1L) {
                this.showChatWindow(player, "residence2/clanhall/farm_kel_mahum_messenger_27.htm", new Object[0]);
                return;
            }
            questState.exitCurrentQuest(true);
            this.al(player);
        } else if (string.equalsIgnoreCase("registerAsMember")) {
            CTBSiegeClanObject cTBSiegeClanObject = (CTBSiegeClanObject)clanHallTeamBattleEvent.getSiegeClan("attackers", player.getClan());
            if (cTBSiegeClanObject == null) {
                this.showChatWindow(player, "residence2/clanhall/farm_kel_mahum_messenger_7.htm", new Object[0]);
                return;
            }
            if (cTBSiegeClanObject.getClan().getLeaderId() == player.getObjectId()) {
                this.showChatWindow(player, "residence2/clanhall/farm_kel_mahum_messenger_5.htm", new Object[0]);
                return;
            }
            if (cTBSiegeClanObject.getPlayers().contains(player.getObjectId())) {
                this.showChatWindow(player, "residence2/clanhall/farm_kel_mahum_messenger_9.htm", new Object[0]);
            } else {
                if (cTBSiegeClanObject.getPlayers().size() >= 18) {
                    this.showChatWindow(player, "residence2/clanhall/farm_kel_mahum_messenger_8.htm", new Object[0]);
                    return;
                }
                cTBSiegeClanObject.getPlayers().add(player.getObjectId());
                SiegePlayerDAO.getInstance().insert((Residence)clanHall, clan.getClanId(), player.getObjectId());
                this.showChatWindow(player, "residence2/clanhall/farm_kel_mahum_messenger_9.htm", new Object[0]);
            }
        } else if (string.startsWith("formAlliance")) {
            CTBSiegeClanObject cTBSiegeClanObject = (CTBSiegeClanObject)clanHallTeamBattleEvent.getSiegeClan("attackers", player.getClan());
            if (cTBSiegeClanObject == null) {
                this.showChatWindow(player, "residence2/clanhall/farm_kel_mahum_messenger_7.htm", new Object[0]);
                return;
            }
            if (cTBSiegeClanObject.getClan().getLeaderId() != player.getObjectId()) {
                this.showChatWindow(player, "residence2/clanhall/agit_oel_mahum_messeger_10.htm", new Object[0]);
                return;
            }
            if (cTBSiegeClanObject.getParam() > 0L) {
                return;
            }
            StringTokenizer stringTokenizer = new StringTokenizer(string);
            stringTokenizer.nextToken();
            int n = Integer.parseInt(stringTokenizer.nextToken());
            cTBSiegeClanObject.setParam(n);
            SiegeClanDAO.getInstance().update((Residence)clanHall, (SiegeClanObject)cTBSiegeClanObject);
            this.showChatWindow(player, "residence2/clanhall/agit_oel_mahum_messeger_9.htm", new Object[0]);
        } else if (string.equalsIgnoreCase("setNpc")) {
            CTBSiegeClanObject cTBSiegeClanObject = (CTBSiegeClanObject)clanHallTeamBattleEvent.getSiegeClan("attackers", player.getClan());
            if (cTBSiegeClanObject == null) {
                this.showChatWindow(player, "residence2/clanhall/farm_kel_mahum_messenger_7.htm", new Object[0]);
                return;
            }
            if (cTBSiegeClanObject.getClan().getLeaderId() != player.getObjectId()) {
                this.showChatWindow(player, "residence2/clanhall/agit_oel_mahum_messeger_10.htm", new Object[0]);
                return;
            }
            this.showChatWindow(player, this.a((SiegeClanObject)cTBSiegeClanObject), new Object[0]);
        } else if (string.equalsIgnoreCase("viewNpc")) {
            CTBSiegeClanObject cTBSiegeClanObject = (CTBSiegeClanObject)clanHallTeamBattleEvent.getSiegeClan("attackers", player.getClan());
            if (cTBSiegeClanObject == null) {
                this.showChatWindow(player, "residence2/clanhall/farm_kel_mahum_messenger_7.htm", new Object[0]);
                return;
            }
            String string2 = cTBSiegeClanObject.getParam() == 0L ? "residence2/clanhall/agit_oel_mahum_messeger_10.htm" : this.a((SiegeClanObject)cTBSiegeClanObject);
            this.showChatWindow(player, string2, new Object[0]);
        } else if (string.equalsIgnoreCase("listClans")) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("residence2/clanhall/farm_messenger003.htm");
            List list = clanHallTeamBattleEvent.getObjects("attackers");
            for (int i = 0; i < 5; ++i) {
                CTBSiegeClanObject cTBSiegeClanObject = (CTBSiegeClanObject)CollectionUtils.safeGet((List)list, (int)i);
                if (cTBSiegeClanObject != null) {
                    npcHtmlMessage.replace("%clan_" + i + "%", cTBSiegeClanObject.getClan().getName());
                } else {
                    npcHtmlMessage.replaceNpcString("%clan_" + i + "%", NpcString.__UNREGISTERED__, new Object[0]);
                }
                npcHtmlMessage.replace("%clan_count_" + i + "%", cTBSiegeClanObject == null ? "" : String.valueOf(cTBSiegeClanObject.getPlayers().size()));
            }
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else {
            super.onBypassFeedback(player, string);
        }
    }

    private void al(Player player) {
        Clan clan = player.getClan();
        ClanHall clanHall = this.getClanHall();
        ClanHallTeamBattleEvent clanHallTeamBattleEvent = (ClanHallTeamBattleEvent)clanHall.getSiegeEvent();
        CTBSiegeClanObject cTBSiegeClanObject = new CTBSiegeClanObject("attackers", clan, 0L);
        cTBSiegeClanObject.getPlayers().add(player.getObjectId());
        clanHallTeamBattleEvent.addObject("attackers", (Serializable)cTBSiegeClanObject);
        SiegeClanDAO.getInstance().insert((Residence)clanHall, (SiegeClanObject)cTBSiegeClanObject);
        SiegePlayerDAO.getInstance().insert((Residence)clanHall, clan.getClanId(), player.getObjectId());
        List list = clanHallTeamBattleEvent.getObjects("attackers");
        this.p(player, list.indexOf(cTBSiegeClanObject));
    }

    private void p(Player player, int n) {
        String string = null;
        switch (n) {
            case 0: {
                string = "residence2/clanhall/farm_kel_mahum_messenger_4a.htm";
                break;
            }
            case 1: {
                string = "residence2/clanhall/farm_kel_mahum_messenger_4b.htm";
                break;
            }
            case 2: {
                string = "residence2/clanhall/farm_kel_mahum_messenger_4c.htm";
                break;
            }
            case 3: {
                string = "residence2/clanhall/farm_kel_mahum_messenger_4d.htm";
                break;
            }
            case 4: {
                string = "residence2/clanhall/farm_kel_mahum_messenger_4e.htm";
                break;
            }
            default: {
                return;
            }
        }
        this.showChatWindow(player, string, new Object[0]);
    }

    private String a(SiegeClanObject siegeClanObject) {
        String string = null;
        switch ((int)siegeClanObject.getParam()) {
            case 0: {
                string = "residence2/clanhall/farm_kel_mahum_messenger_6.htm";
                break;
            }
            case 35618: {
                string = "residence2/clanhall/farm_kel_mahum_messenger_17.htm";
                break;
            }
            case 35619: {
                string = "residence2/clanhall/farm_kel_mahum_messenger_18.htm";
                break;
            }
            case 35620: {
                string = "residence2/clanhall/farm_kel_mahum_messenger_19.htm";
                break;
            }
            case 35621: {
                string = "residence2/clanhall/farm_kel_mahum_messenger_20.htm";
                break;
            }
            case 35622: {
                string = "residence2/clanhall/farm_kel_mahum_messenger_23.htm";
            }
        }
        return string;
    }

    private boolean b(Player player, boolean bl) {
        Clan clan = player.getClan();
        ClanHall clanHall = this.getClanHall();
        ClanHallTeamBattleEvent clanHallTeamBattleEvent = (ClanHallTeamBattleEvent)clanHall.getSiegeEvent();
        List list = clanHallTeamBattleEvent.getObjects("attackers");
        SiegeClanObject siegeClanObject = clanHallTeamBattleEvent.getSiegeClan("attackers", clan);
        if (clanHallTeamBattleEvent.isRegistrationOver()) {
            this.showChatWindow(player, "quests/_655_AGrandPlanForTamingWildBeasts/farm_messenger_q0655_11.htm", new Object[]{"%siege_time%", TimeUtils.toSimpleFormat((Calendar)clanHall.getSiegeDate())});
            return false;
        }
        if (bl && siegeClanObject != null) {
            return true;
        }
        if (clan == null || player.getObjectId() != clan.getLeaderId()) {
            this.showChatWindow(player, "quests/_655_AGrandPlanForTamingWildBeasts/farm_messenger_q0655_03.htm", new Object[0]);
            return false;
        }
        if (player.getClan().isPlacedForDisband()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_ALREADY_REQUESTED_THE_DISSOLUTION_OF_YOUR_CLAN);
            return false;
        }
        if (player.getObjectId() == clan.getLeaderId() && clan.getLevel() < 4) {
            this.showChatWindow(player, "quests/_655_AGrandPlanForTamingWildBeasts/farm_messenger_q0655_05.htm", new Object[0]);
            return false;
        }
        if (clan.getHasHideout() == clanHall.getId()) {
            this.showChatWindow(player, "residence2/clanhall/farm_kel_mahum_messenger_22.htm", new Object[0]);
            return false;
        }
        if (clan.getHasHideout() > 0) {
            this.showChatWindow(player, "quests/_655_AGrandPlanForTamingWildBeasts/farm_messenger_q0655_04.htm", new Object[0]);
            return false;
        }
        if (list.size() >= 5) {
            this.showChatWindow(player, "residence2/clanhall/farm_kel_mahum_messenger_21.htm", new Object[0]);
            return false;
        }
        return true;
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        Clan clan = this.getClanHall().getOwner();
        if (clan != null) {
            this.showChatWindow(player, "residence2/clanhall/farm_messenger001.htm", new Object[]{"%owner_name%", clan.getName()});
        } else {
            this.showChatWindow(player, "residence2/clanhall/farm_messenger002.htm", new Object[0]);
        }
    }
}
