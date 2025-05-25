/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.dao.SiegeClanDAO
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.events.impl.ClanHallMiniGameEvent
 *  l2.gameserver.model.entity.events.objects.CMGSiegeClanObject
 *  l2.gameserver.model.entity.events.objects.SiegeClanObject
 *  l2.gameserver.model.entity.residence.ClanHall
 *  l2.gameserver.model.entity.residence.Residence
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.TimeUtils
 */
package npc.model.residences.clanhall;

import java.io.Serializable;
import java.util.Calendar;
import l2.gameserver.dao.SiegeClanDAO;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.impl.ClanHallMiniGameEvent;
import l2.gameserver.model.entity.events.objects.CMGSiegeClanObject;
import l2.gameserver.model.entity.events.objects.SiegeClanObject;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.TimeUtils;

public class RainbowMessengerInstance
extends NpcInstance {
    public static final int ITEM_ID = 8034;

    public RainbowMessengerInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!RainbowMessengerInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        ClanHall clanHall = this.getClanHall();
        ClanHallMiniGameEvent clanHallMiniGameEvent = (ClanHallMiniGameEvent)clanHall.getSiegeEvent();
        if (string.equalsIgnoreCase("register")) {
            if (clanHallMiniGameEvent.isRegistrationOver()) {
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti014.htm", new Object[0]);
                return;
            }
            Clan clan = player.getClan();
            if (clan == null || clan.getLevel() < 3 || clan.getAllSize() <= 5) {
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti011.htm", new Object[0]);
                return;
            }
            if (player.getClan().isPlacedForDisband()) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_ALREADY_REQUESTED_THE_DISSOLUTION_OF_YOUR_CLAN);
                return;
            }
            if (clan.getLeaderId() != player.getObjectId()) {
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti010.htm", new Object[0]);
                return;
            }
            if (clan.getHasHideout() > 0) {
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti012.htm", new Object[0]);
                return;
            }
            if (clanHallMiniGameEvent.getSiegeClan("attackers", clan) != null) {
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti013.htm", new Object[0]);
                return;
            }
            long l = player.getInventory().getCountOf(8034);
            if (l == 0L) {
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti008.htm", new Object[0]);
            } else {
                if (!player.consumeItem(8034, l)) {
                    return;
                }
                CMGSiegeClanObject cMGSiegeClanObject = new CMGSiegeClanObject("attackers", clan, l);
                clanHallMiniGameEvent.addObject("attackers", (Serializable)cMGSiegeClanObject);
                SiegeClanDAO.getInstance().insert((Residence)clanHall, (SiegeClanObject)cMGSiegeClanObject);
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti009.htm", new Object[0]);
            }
        } else if (string.equalsIgnoreCase("cancel")) {
            if (clanHallMiniGameEvent.isRegistrationOver()) {
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti017.htm", new Object[0]);
                return;
            }
            Clan clan = player.getClan();
            if (clan == null || clan.getLevel() < 3) {
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti011.htm", new Object[0]);
                return;
            }
            if (clan.getLeaderId() != player.getObjectId()) {
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti010.htm", new Object[0]);
                return;
            }
            SiegeClanObject siegeClanObject = clanHallMiniGameEvent.getSiegeClan("attackers", clan);
            if (siegeClanObject == null) {
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti016.htm", new Object[0]);
            } else {
                clanHallMiniGameEvent.removeObject("attackers", (Serializable)siegeClanObject);
                SiegeClanDAO.getInstance().delete((Residence)clanHall, siegeClanObject);
                ItemFunctions.addItem((Playable)player, (int)8034, (long)(siegeClanObject.getParam() / 2L), (boolean)true);
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti005.htm", new Object[0]);
            }
        } else if (string.equalsIgnoreCase("refund")) {
            if (clanHallMiniGameEvent.isRegistrationOver()) {
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti010.htm", new Object[0]);
                return;
            }
            Clan clan = player.getClan();
            if (clan == null || clan.getLevel() < 3) {
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti011.htm", new Object[0]);
                return;
            }
            if (clan.getLeaderId() != player.getObjectId()) {
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti010.htm", new Object[0]);
                return;
            }
            SiegeClanObject siegeClanObject = clanHallMiniGameEvent.getSiegeClan("refund", clan);
            if (siegeClanObject == null) {
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti020.htm", new Object[0]);
            } else {
                clanHallMiniGameEvent.removeObject("refund", (Serializable)siegeClanObject);
                SiegeClanDAO.getInstance().delete((Residence)clanHall, siegeClanObject);
                ItemFunctions.addItem((Playable)player, (int)8034, (long)siegeClanObject.getParam(), (boolean)true);
                this.showChatWindow(player, "residence2/clanhall/messenger_yetti019.htm", new Object[0]);
            }
        } else {
            super.onBypassFeedback(player, string);
        }
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        ClanHall clanHall = this.getClanHall();
        Clan clan = clanHall.getOwner();
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
        if (clan != null) {
            npcHtmlMessage.setFile("residence2/clanhall/messenger_yetti001.htm");
            npcHtmlMessage.replace("%owner_name%", clan.getName());
        } else {
            npcHtmlMessage.setFile("residence2/clanhall/messenger_yetti001a.htm");
        }
        npcHtmlMessage.replace("%siege_date%", TimeUtils.toSimpleFormat((Calendar)clanHall.getSiegeDate()));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }
}
