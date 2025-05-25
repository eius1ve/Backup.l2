/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dao.JdbcEntityState
 *  l2.gameserver.Config
 *  l2.gameserver.dao.SiegeClanDAO
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.data.xml.holder.ResidenceHolder
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.events.impl.ClanHallAuctionEvent
 *  l2.gameserver.model.entity.events.objects.AuctionSiegeClanObject
 *  l2.gameserver.model.entity.events.objects.SiegeClanObject
 *  l2.gameserver.model.entity.residence.ClanHall
 *  l2.gameserver.model.entity.residence.Residence
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.model.pledge.Privilege
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.HtmlUtils
 *  l2.gameserver.utils.MapRegionUtils
 *  org.apache.commons.lang3.ArrayUtils
 */
package npc.model.residences.clanhall;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import l2.commons.dao.JdbcEntityState;
import l2.gameserver.Config;
import l2.gameserver.dao.SiegeClanDAO;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.impl.ClanHallAuctionEvent;
import l2.gameserver.model.entity.events.objects.AuctionSiegeClanObject;
import l2.gameserver.model.entity.events.objects.SiegeClanObject;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.Privilege;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.HtmlUtils;
import l2.gameserver.utils.MapRegionUtils;
import org.apache.commons.lang3.ArrayUtils;

public class AuctioneerInstance
extends NpcInstance {
    private static final SimpleDateFormat h = new SimpleDateFormat("dd/MM/yy");
    private static final NumberFormat f = NumberFormat.getIntegerInstance(Locale.KOREA);
    private static final long ep = 604800000L;
    private static final int HS = 7;
    private static final String hd = "\t<tr>\n\t\t<td width=50>\n\t\t\t<font color=\"aaaaff\">&^%id%;</font>\n\t\t</td>\n\t\t<td width=100>\n\t\t\t<a action=\"bypass -h npc_%objectId%_info %id%\"><font color=\"ffffaa\">&%%id%;[%size%]</font></a>\n\t\t</td>\n\t\t<td width=50>%date%</td>\n\t\t<td width=70 align=right>\n\t\t\t<font color=\"aaffff\">%min_bid%</font>\n\t\t</td>\n\t</tr>";
    private static final int HT = 10;
    private static final String he = "\t<tr>\n\t\t<td width=100><font color=\"aaaaff\">&%%id%;</font></td>\n\t\t<td width=100><font color=\"ffffaa\">%clan_name%</font></td>\n\t\t<td width=70>%date%</td>\n\t</tr>";
    private static final int[] bp = new int[]{1, 3, 7};

    public AuctioneerInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!AuctioneerInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(string.replace("\r\n", "<br1>"));
        String string2 = stringTokenizer.nextToken();
        if (string2.equalsIgnoreCase("map")) {
            this.showChatWindow(player, this.i(), new Object[0]);
        } else if (string2.equalsIgnoreCase("list_all")) {
            int n = Integer.parseInt(stringTokenizer.nextToken());
            ArrayList<ClanHallAuctionEvent> arrayList = new ArrayList<ClanHallAuctionEvent>();
            for (ClanHall clanHall : ResidenceHolder.getInstance().getResidenceList(ClanHall.class)) {
                if (clanHall.getSiegeEvent().getClass() != ClanHallAuctionEvent.class || !clanHall.getSiegeEvent().isInProgress() || !Config.CH_DISPLAY_IDS.contains(clanHall.getId())) continue;
                arrayList.add((ClanHallAuctionEvent)clanHall.getSiegeEvent());
            }
            if (arrayList.isEmpty()) {
                player.sendPacket((IStaticPacket)SystemMsg.THERE_ARE_NO_CLAN_HALLS_UP_FOR_AUCTION);
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            int n2 = 7 * n;
            int n3 = n2 + 7;
            if (n2 > arrayList.size()) {
                n2 = 0;
                n3 = n2 + 7;
            }
            if (n3 > arrayList.size()) {
                n3 = arrayList.size();
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("residence2/clanhall/auction_list_clanhalls.htm");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = n2; i < n3; ++i) {
                ClanHallAuctionEvent clanHallAuctionEvent = (ClanHallAuctionEvent)arrayList.get(i);
                List list = clanHallAuctionEvent.getObjects("attackers");
                Calendar calendar = clanHallAuctionEvent.getEndSiegeDate();
                String string3 = hd.replace("%id%", String.valueOf(clanHallAuctionEvent.getId())).replace("%min_bid%", String.valueOf(((ClanHall)clanHallAuctionEvent.getResidence()).getAuctionMinBid())).replace("%size%", String.valueOf(list.size())).replace("%date%", h.format(calendar.getTimeInMillis()));
                stringBuilder.append(string3);
            }
            npcHtmlMessage.replace("%list%", stringBuilder.toString());
            if (arrayList.size() > n3) {
                npcHtmlMessage.replace("%next_button%", "<td>" + StringHolder.getInstance().getNotNull(player, "html_util_button_next") + "</td>");
                npcHtmlMessage.replace("%next_bypass%", "-h npc_%objectId%_list_all " + (n + 1));
            } else {
                npcHtmlMessage.replace("%next_button%", "");
            }
            if (n != 0) {
                npcHtmlMessage.replace("%prev_button%", "<td>" + StringHolder.getInstance().getNotNull(player, "html_util_button_perv") + "</td>");
                npcHtmlMessage.replace("%prev_bypass%", "-h npc_%objectId%_list_all " + (n - 1));
            } else {
                npcHtmlMessage.replace("%prev_button%", "");
            }
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("info")) {
            List list2;
            String string4 = null;
            Object object = null;
            SiegeClanObject siegeClanObject = null;
            if (stringTokenizer.hasMoreTokens()) {
                int n = Integer.parseInt(stringTokenizer.nextToken());
                object = (ClanHall)ResidenceHolder.getInstance().getResidence(n);
                string4 = "residence2/clanhall/auction_clanhall_info_main.htm";
            } else {
                ClanHall clanHall = player.getClan() == null ? null : (object = player.getClan().getHasHideout() > 0 ? (ClanHall)ResidenceHolder.getInstance().getResidence(player.getClan().getHasHideout()) : null);
                if (object != null && object.getSiegeEvent().getClass() == ClanHallAuctionEvent.class) {
                    string4 = object.getSiegeEvent().isInProgress() ? "residence2/clanhall/auction_clanhall_info_owner_sell.htm" : "residence2/clanhall/auction_clanhall_info_owner.htm";
                } else {
                    for (List list2 : ResidenceHolder.getInstance().getResidenceList(ClanHall.class)) {
                        if (list2.getSiegeEvent().getClass() != ClanHallAuctionEvent.class || (siegeClanObject = list2.getSiegeEvent().getSiegeClan("attackers", player.getClan())) == null) continue;
                        object = list2;
                        break;
                    }
                    if (siegeClanObject == null) {
                        player.sendPacket((IStaticPacket)SystemMsg.THERE_ARE_NO_OFFERINGS_I_OWN_OR_I_MADE_A_BID_FOR);
                        this.showChatWindow(player, 0, new Object[0]);
                        return;
                    }
                    string4 = "residence2/clanhall/auction_clanhall_info_bidded.htm";
                }
            }
            ClanHallAuctionEvent clanHallAuctionEvent = (ClanHallAuctionEvent)object.getSiegeEvent();
            list2 = clanHallAuctionEvent.getObjects("attackers");
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile(string4);
            npcHtmlMessage.replace("%id%", String.valueOf(object.getId()));
            npcHtmlMessage.replace("%bigger_size%", String.valueOf(list2.size()));
            npcHtmlMessage.replace("%grade%", String.valueOf(object.getGrade()));
            npcHtmlMessage.replace("%rental_fee%", String.valueOf(object.getRentalFee()));
            npcHtmlMessage.replace("%cha_currency%", HtmlUtils.htmlItemName((int)Config.CH_BID_CURRENCY_ITEM_ID));
            Clan clan = object.getOwner();
            npcHtmlMessage.replace("%owner%", clan == null ? "" : clan.getName());
            npcHtmlMessage.replace("%owner_leader%", clan == null ? "" : clan.getLeaderName());
            npcHtmlMessage.replace("%description%", object.getAuctionDescription());
            npcHtmlMessage.replace("%min_bid%", String.valueOf(object.getAuctionMinBid()));
            Calendar calendar = clanHallAuctionEvent.getEndSiegeDate();
            npcHtmlMessage.replace("%date%", h.format(calendar.getTimeInMillis()));
            npcHtmlMessage.replace("%hour%", String.valueOf(calendar.get(11)));
            int n = (int)((calendar.getTimeInMillis() - System.currentTimeMillis()) / 60000L);
            npcHtmlMessage.replace("%remaining_hour%", String.valueOf(n / 60));
            npcHtmlMessage.replace("%remaining_minutes%", String.valueOf(n % 60));
            if (siegeClanObject != null) {
                npcHtmlMessage.replace("%my_bid%", String.valueOf(siegeClanObject.getParam()));
            }
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("bidder_list")) {
            int n = Integer.parseInt(stringTokenizer.nextToken());
            int n4 = Integer.parseInt(stringTokenizer.nextToken());
            ClanHall clanHall = (ClanHall)ResidenceHolder.getInstance().getResidence(n);
            ClanHallAuctionEvent clanHallAuctionEvent = (ClanHallAuctionEvent)clanHall.getSiegeEvent();
            List list = clanHallAuctionEvent.getObjects("attackers");
            if (!clanHallAuctionEvent.isInProgress()) {
                return;
            }
            int n5 = 10 * n4;
            int n6 = n5 + 10;
            if (n5 > list.size()) {
                n5 = 0;
                n6 = n5 + 10;
            }
            if (n6 > list.size()) {
                n6 = list.size();
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("residence2/clanhall/auction_bidder_list.htm");
            npcHtmlMessage.replace("%id%", String.valueOf(n));
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = n5; i < n6; ++i) {
                AuctionSiegeClanObject auctionSiegeClanObject = (AuctionSiegeClanObject)list.get(i);
                String string5 = he.replace("%id%", String.valueOf(n)).replace("%clan_name%", auctionSiegeClanObject.getClan().getName()).replace("%date%", h.format(auctionSiegeClanObject.getDate()));
                stringBuilder.append(string5);
            }
            npcHtmlMessage.replace("%list%", stringBuilder.toString());
            if (list.size() > n6) {
                npcHtmlMessage.replace("%next_button%", "<td>" + StringHolder.getInstance().getNotNull(player, "html_util_button_next") + "</td>");
                npcHtmlMessage.replace("%next_bypass%", "-h npc_%objectId%_bidder_list " + n + " " + (n4 + 1));
            } else {
                npcHtmlMessage.replace("%next_button%", "");
            }
            if (n4 != 0) {
                npcHtmlMessage.replace("%prev_button%", "<td>" + StringHolder.getInstance().getNotNull(player, "html_util_button_prev") + "</td>");
                npcHtmlMessage.replace("%prev_bypass%", "-h npc_%objectId%_bidder_list " + n + " " + (n4 - 1));
            } else {
                npcHtmlMessage.replace("%prev_button%", "");
            }
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("bid_start")) {
            if (!this.s(player)) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            int n = Integer.parseInt(stringTokenizer.nextToken());
            ClanHall clanHall = (ClanHall)ResidenceHolder.getInstance().getResidence(n);
            ClanHallAuctionEvent clanHallAuctionEvent = (ClanHallAuctionEvent)clanHall.getSiegeEvent();
            if (!clanHallAuctionEvent.isInProgress()) {
                return;
            }
            long l = clanHall.getAuctionMinBid();
            AuctionSiegeClanObject auctionSiegeClanObject = (AuctionSiegeClanObject)clanHallAuctionEvent.getSiegeClan("attackers", player.getClan());
            if (auctionSiegeClanObject != null) {
                l = auctionSiegeClanObject.getParam();
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("residence2/clanhall/auction_bid_start.htm");
            npcHtmlMessage.replace("%id%", String.valueOf(n));
            npcHtmlMessage.replace("%min_bid%", String.valueOf(l));
            npcHtmlMessage.replace("%clan_currency_amount%", String.valueOf(player.getClan().getWarehouse().getCountOf(Config.CH_BID_CURRENCY_ITEM_ID)));
            npcHtmlMessage.replace("%cha_currency%", HtmlUtils.htmlItemName((int)Config.CH_BID_CURRENCY_ITEM_ID));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("bid_next")) {
            ClanHall clanHall;
            ClanHallAuctionEvent clanHallAuctionEvent;
            if (!this.s(player)) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            int n = Integer.parseInt(stringTokenizer.nextToken());
            long l = 0L;
            if (stringTokenizer.hasMoreTokens()) {
                try {
                    l = f.parse(stringTokenizer.nextToken()).longValue();
                }
                catch (ParseException parseException) {
                    // empty catch block
                }
            }
            if (!(clanHallAuctionEvent = (ClanHallAuctionEvent)(clanHall = (ClanHall)ResidenceHolder.getInstance().getResidence(n)).getSiegeEvent()).isInProgress()) {
                return;
            }
            if (!this.a(player, clanHallAuctionEvent, l)) {
                return;
            }
            long l2 = clanHall.getAuctionMinBid();
            AuctionSiegeClanObject auctionSiegeClanObject = (AuctionSiegeClanObject)clanHallAuctionEvent.getSiegeClan("attackers", player.getClan());
            if (auctionSiegeClanObject != null) {
                l2 = auctionSiegeClanObject.getParam();
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("residence2/clanhall/auction_bid_confirm.htm");
            npcHtmlMessage.replace("%id%", String.valueOf(n));
            npcHtmlMessage.replace("%bid%", String.valueOf(l));
            npcHtmlMessage.replace("%cha_currency%", HtmlUtils.htmlItemName((int)Config.CH_BID_CURRENCY_ITEM_ID));
            npcHtmlMessage.replace("%min_bid%", String.valueOf(l2));
            Calendar calendar = clanHallAuctionEvent.getEndSiegeDate();
            npcHtmlMessage.replace("%date%", h.format(calendar.getTimeInMillis()));
            npcHtmlMessage.replace("%hour%", String.valueOf(calendar.get(11)));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("bid_confirm")) {
            if (!this.s(player)) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            int n = Integer.parseInt(stringTokenizer.nextToken());
            long l = Long.parseLong(stringTokenizer.nextToken());
            ClanHall clanHall = (ClanHall)ResidenceHolder.getInstance().getResidence(n);
            ClanHallAuctionEvent clanHallAuctionEvent = (ClanHallAuctionEvent)clanHall.getSiegeEvent();
            if (!clanHallAuctionEvent.isInProgress()) {
                return;
            }
            for (ClanHall clanHall2 : ResidenceHolder.getInstance().getResidenceList(ClanHall.class)) {
                if (clanHall == clanHall2 || clanHall2.getSiegeEvent().getClass() != ClanHallAuctionEvent.class || !clanHall2.getSiegeEvent().isInProgress() || clanHall2.getSiegeEvent().getSiegeClan("attackers", player.getClan()) == null) continue;
                player.sendPacket((IStaticPacket)SystemMsg.SINCE_YOU_HAVE_ALREADY_SUBMITTED_A_BID_YOU_ARE_NOT_ALLOWED_TO_PARTICIPATE_IN_ANOTHER_AUCTION_AT_THIS_TIME);
                this.onBypassFeedback(player, "bid_start " + n);
                return;
            }
            if (!this.a(player, clanHallAuctionEvent, l)) {
                return;
            }
            long l3 = l;
            AuctionSiegeClanObject auctionSiegeClanObject = (AuctionSiegeClanObject)clanHallAuctionEvent.getSiegeClan("attackers", player.getClan());
            if (auctionSiegeClanObject != null) {
                l3 -= auctionSiegeClanObject.getParam();
                if (l <= auctionSiegeClanObject.getParam()) {
                    player.sendPacket((IStaticPacket)SystemMsg.THE_BID_AMOUNT_MUST_BE_HIGHER_THAN_THE_PREVIOUS_BID);
                    this.onBypassFeedback(player, "bid_start " + clanHallAuctionEvent.getId());
                    return;
                }
            }
            player.getClan().getWarehouse().destroyItemByItemId(Config.CH_BID_CURRENCY_ITEM_ID, l3);
            if (auctionSiegeClanObject != null) {
                auctionSiegeClanObject.setParam(l);
                SiegeClanDAO.getInstance().update((Residence)clanHall, (SiegeClanObject)auctionSiegeClanObject);
            } else {
                auctionSiegeClanObject = new AuctionSiegeClanObject("attackers", player.getClan(), l);
                clanHallAuctionEvent.addObject("attackers", (Serializable)auctionSiegeClanObject);
                SiegeClanDAO.getInstance().insert((Residence)clanHall, (SiegeClanObject)auctionSiegeClanObject);
            }
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_BID_HAS_BEEN_SUCCESSFULLY_PLACED);
            this.onBypassFeedback(player, "info");
        } else if (string2.equalsIgnoreCase("cancel_bid")) {
            if (!this.s(player)) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            int n = Integer.parseInt(stringTokenizer.nextToken());
            ClanHall clanHall = (ClanHall)ResidenceHolder.getInstance().getResidence(n);
            ClanHallAuctionEvent clanHallAuctionEvent = (ClanHallAuctionEvent)clanHall.getSiegeEvent();
            if (!clanHallAuctionEvent.isInProgress()) {
                return;
            }
            AuctionSiegeClanObject auctionSiegeClanObject = (AuctionSiegeClanObject)clanHallAuctionEvent.getSiegeClan("attackers", player.getClan());
            if (auctionSiegeClanObject == null) {
                return;
            }
            long l = auctionSiegeClanObject.getParam() - (long)((double)auctionSiegeClanObject.getParam() * 0.1);
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("residence2/clanhall/auction_bid_cancel.htm");
            npcHtmlMessage.replace("%id%", String.valueOf(n));
            npcHtmlMessage.replace("%cha_currency%", HtmlUtils.htmlItemName((int)Config.CH_BID_CURRENCY_ITEM_ID));
            npcHtmlMessage.replace("%bid%", String.valueOf(auctionSiegeClanObject.getParam()));
            npcHtmlMessage.replace("%return%", String.valueOf(l));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("cancel_bid_confirm")) {
            if (!this.s(player)) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            int n = Integer.parseInt(stringTokenizer.nextToken());
            ClanHall clanHall = (ClanHall)ResidenceHolder.getInstance().getResidence(n);
            ClanHallAuctionEvent clanHallAuctionEvent = (ClanHallAuctionEvent)clanHall.getSiegeEvent();
            if (!clanHallAuctionEvent.isInProgress()) {
                return;
            }
            AuctionSiegeClanObject auctionSiegeClanObject = (AuctionSiegeClanObject)clanHallAuctionEvent.getSiegeClan("attackers", player.getClan());
            if (auctionSiegeClanObject == null) {
                return;
            }
            long l = auctionSiegeClanObject.getParam() - (long)((double)auctionSiegeClanObject.getParam() * 0.1);
            player.getClan().getWarehouse().addItem(Config.CH_BID_CURRENCY_ITEM_ID, l);
            clanHallAuctionEvent.removeObject("attackers", (Serializable)auctionSiegeClanObject);
            SiegeClanDAO.getInstance().delete((Residence)clanHall, (SiegeClanObject)auctionSiegeClanObject);
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_CANCELED_YOUR_BID);
            this.showChatWindow(player, 0, new Object[0]);
        } else if (string2.equalsIgnoreCase("register_start")) {
            if (!this.s(player)) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            ClanHall clanHall = (ClanHall)ResidenceHolder.getInstance().getResidence(player.getClan().getHasHideout());
            if (clanHall.getSiegeEvent().getClass() != ClanHallAuctionEvent.class || clanHall.getSiegeEvent().isInProgress()) {
                return;
            }
            if (clanHall.getLastSiegeDate().getTimeInMillis() + 604800000L > System.currentTimeMillis()) {
                player.sendPacket((IStaticPacket)SystemMsg.IT_HAS_NOT_YET_BEEN_SEVEN_DAYS_SINCE_CANCELING_AN_AUCTION);
                this.onBypassFeedback(player, "info");
                return;
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("residence2/clanhall/auction_clanhall_register_start.htm");
            npcHtmlMessage.replace("%id%", String.valueOf(player.getClan().getHasHideout()));
            npcHtmlMessage.replace("%currency_amount%", String.valueOf(player.getClan().getWarehouse().getCountOf(Config.CH_BID_CURRENCY_ITEM_ID)));
            npcHtmlMessage.replace("%cha_currency%", HtmlUtils.htmlItemName((int)Config.CH_BID_CURRENCY_ITEM_ID));
            npcHtmlMessage.replace("%deposit%", String.valueOf(clanHall.getDeposit()));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("register_next")) {
            if (!this.s(player)) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            ClanHall clanHall = (ClanHall)ResidenceHolder.getInstance().getResidence(player.getClan().getHasHideout());
            if (clanHall.getSiegeEvent().getClass() != ClanHallAuctionEvent.class || clanHall.getSiegeEvent().isInProgress()) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            if (player.getClan().getWarehouse().getCountOf(Config.CH_BID_CURRENCY_ITEM_ID) < clanHall.getDeposit()) {
                player.sendPacket((IStaticPacket)SystemMsg.THERE_IS_NOT_ENOUGH_ADENA_IN_THE_CLAN_HALL_WAREHOUSE);
                this.onBypassFeedback(player, "register_start");
                return;
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("residence2/clanhall/auction_clanhall_register_next.htm");
            npcHtmlMessage.replace("%min_bid%", String.valueOf(clanHall.getBaseMinBid()));
            npcHtmlMessage.replace("%last_bid%", String.valueOf(clanHall.getBaseMinBid()));
            npcHtmlMessage.replace("%cha_currency%", HtmlUtils.htmlItemName((int)Config.CH_BID_CURRENCY_ITEM_ID));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("register_next2")) {
            if (!this.s(player)) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            ClanHall clanHall = (ClanHall)ResidenceHolder.getInstance().getResidence(player.getClan().getHasHideout());
            if (clanHall.getSiegeEvent().getClass() != ClanHallAuctionEvent.class || clanHall.getSiegeEvent().isInProgress()) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            int n = Integer.parseInt(stringTokenizer.nextToken());
            if (ArrayUtils.indexOf((int[])bp, (int)n) == -1) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            long l = -1L;
            Object object = "";
            if (stringTokenizer.hasMoreTokens()) {
                try {
                    l = Long.parseLong(stringTokenizer.nextToken());
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (stringTokenizer.hasMoreTokens()) {
                object = stringTokenizer.nextToken();
                while (stringTokenizer.hasMoreTokens()) {
                    object = (String)object + " " + stringTokenizer.nextToken();
                }
            }
            object = HtmlUtils.sanitizeHtml((String)object);
            if (l <= -1L) {
                this.onBypassFeedback(player, "register_next");
                return;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(11, n);
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("residence2/clanhall/auction_clanhall_register_confirm.htm");
            npcHtmlMessage.replace("%description%", (String)object);
            npcHtmlMessage.replace("%day%", String.valueOf(n));
            npcHtmlMessage.replace("%bid%", String.valueOf(l));
            npcHtmlMessage.replace("%cha_currency%", HtmlUtils.htmlItemName((int)Config.CH_BID_CURRENCY_ITEM_ID));
            npcHtmlMessage.replace("%base_bid%", String.valueOf(clanHall.getBaseMinBid()));
            npcHtmlMessage.replace("%hour%", String.valueOf(calendar.get(11)));
            npcHtmlMessage.replace("%date%", h.format(calendar.getTimeInMillis()));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("register_confirm")) {
            if (!this.s(player)) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            ClanHall clanHall = (ClanHall)ResidenceHolder.getInstance().getResidence(player.getClan().getHasHideout());
            if (clanHall.getSiegeEvent().getClass() != ClanHallAuctionEvent.class || clanHall.getSiegeEvent().isInProgress()) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            if (clanHall.getLastSiegeDate().getTimeInMillis() + 604800000L > System.currentTimeMillis()) {
                player.sendPacket((IStaticPacket)SystemMsg.IT_HAS_NOT_YET_BEEN_SEVEN_DAYS_SINCE_CANCELING_AN_AUCTION);
                this.onBypassFeedback(player, "info");
                return;
            }
            int n = Integer.parseInt(stringTokenizer.nextToken());
            if (ArrayUtils.indexOf((int[])bp, (int)n) == -1) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            long l = Long.parseLong(stringTokenizer.nextToken());
            Object object = "";
            if (stringTokenizer.hasMoreTokens()) {
                object = stringTokenizer.nextToken();
                while (stringTokenizer.hasMoreTokens()) {
                    object = (String)object + " " + stringTokenizer.nextToken();
                }
            }
            object = HtmlUtils.sanitizeHtml((String)object);
            if (l <= -1L) {
                this.onBypassFeedback(player, "register_next");
                return;
            }
            clanHall.setAuctionMinBid(l);
            clanHall.setAuctionDescription((String)object);
            clanHall.setAuctionLength(n);
            clanHall.getSiegeDate().setTimeInMillis(System.currentTimeMillis());
            clanHall.setJdbcState(JdbcEntityState.UPDATED);
            clanHall.update();
            clanHall.getSiegeEvent().reCalcNextTime(false);
            this.onBypassFeedback(player, "info");
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_REGISTERED_FOR_A_CLAN_HALL_AUCTION);
        } else if (string2.equals("cancel_start")) {
            if (!this.s(player)) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            ClanHall clanHall = (ClanHall)ResidenceHolder.getInstance().getResidence(player.getClan().getHasHideout());
            if (clanHall.getSiegeEvent().getClass() != ClanHallAuctionEvent.class || !clanHall.getSiegeEvent().isInProgress()) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("residence2/clanhall/auction_clanhall_cancel_confirm.htm");
            npcHtmlMessage.replace("%deposit%", String.valueOf(clanHall.getDeposit()));
            npcHtmlMessage.replace("%cha_currency%", HtmlUtils.htmlItemName((int)Config.CH_BID_CURRENCY_ITEM_ID));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equals("cancel_confirm")) {
            if (!this.s(player)) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            ClanHall clanHall = (ClanHall)ResidenceHolder.getInstance().getResidence(player.getClan().getHasHideout());
            if (clanHall.getSiegeEvent().getClass() != ClanHallAuctionEvent.class || !clanHall.getSiegeEvent().isInProgress()) {
                this.showChatWindow(player, 0, new Object[0]);
                return;
            }
            clanHall.getSiegeEvent().setInProgress(false);
            clanHall.getSiegeDate().setTimeInMillis(0L);
            clanHall.getLastSiegeDate().setTimeInMillis(System.currentTimeMillis());
            clanHall.setAuctionDescription("");
            clanHall.setAuctionLength(0);
            clanHall.setAuctionMinBid(0L);
            clanHall.setJdbcState(JdbcEntityState.UPDATED);
            clanHall.update();
            ClanHallAuctionEvent clanHallAuctionEvent = (ClanHallAuctionEvent)clanHall.getSiegeEvent();
            List list = clanHallAuctionEvent.removeObjects("attackers");
            SiegeClanDAO.getInstance().delete((Residence)clanHall);
            for (AuctionSiegeClanObject auctionSiegeClanObject : list) {
                long l = auctionSiegeClanObject.getParam() - (long)((double)auctionSiegeClanObject.getParam() * 0.1);
                auctionSiegeClanObject.getClan().getWarehouse().addItem(Config.CH_BID_CURRENCY_ITEM_ID, l);
            }
            clanHall.getSiegeEvent().reCalcNextTime(false);
            this.onBypassFeedback(player, "info");
        } else {
            super.onBypassFeedback(player, string);
        }
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        this.showChatWindow(player, "residence2/clanhall/auction_dealer001.htm", new Object[0]);
    }

    private boolean s(Player player) {
        if (player.getClan() == null || player.getClan().getLevel() < Config.CLAN_MIN_LEVEL_FOR_BID) {
            player.sendPacket((IStaticPacket)SystemMsg.ONLY_A_CLAN_LEADER_WHOSE_CLAN_IS_OF_LEVEL_2_OR_HIGHER_IS_ALLOWED_TO_PARTICIPATE_IN_A_CLAN_HALL_AUCTION);
            return false;
        }
        if (player.getClan().isPlacedForDisband()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_ALREADY_REQUESTED_THE_DISSOLUTION_OF_YOUR_CLAN);
            return false;
        }
        if (!player.hasPrivilege(Privilege.CH_AUCTION)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
            return false;
        }
        return true;
    }

    private boolean a(Player player, ClanHallAuctionEvent clanHallAuctionEvent, long l) {
        long l2;
        long l3 = l;
        AuctionSiegeClanObject auctionSiegeClanObject = (AuctionSiegeClanObject)clanHallAuctionEvent.getSiegeClan("attackers", player.getClan());
        if (auctionSiegeClanObject != null) {
            l3 -= auctionSiegeClanObject.getParam();
        }
        if (l3 > player.getClan().getWarehouse().getCountOf(Config.CH_BID_CURRENCY_ITEM_ID)) {
            player.sendPacket((IStaticPacket)SystemMsg.THERE_IS_NOT_ENOUGH_ADENA_IN_THE_CLAN_HALL_WAREHOUSE);
            this.onBypassFeedback(player, "bid_start " + clanHallAuctionEvent.getId());
            return false;
        }
        long l4 = l2 = auctionSiegeClanObject == null ? ((ClanHall)clanHallAuctionEvent.getResidence()).getAuctionMinBid() : auctionSiegeClanObject.getParam();
        if (l < l2) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_BID_PRICE_MUST_BE_HIGHER_THAN_THE_MINIMUM_PRICE_CURRENTLY_BEING_BID);
            this.onBypassFeedback(player, "bid_start " + clanHallAuctionEvent.getId());
            return false;
        }
        return true;
    }

    private String i() {
        int n = MapRegionUtils.regionX((GameObject)this);
        int n2 = MapRegionUtils.regionY((GameObject)this);
        return String.format("residence2/clanhall/map_agit_%d_%d.htm", n, n2);
    }
}
