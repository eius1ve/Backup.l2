/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import java.util.StringTokenizer;
import l2.gameserver.Config;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.instances.VillageMasterInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.SubUnit;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.PledgeShowInfoUpdate;
import l2.gameserver.network.l2.s2c.PledgeStatusChanged;
import l2.gameserver.network.l2.s2c.Say2;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.Util;

public class AdminPledge
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (player.getPlayerAccess() == null || !player.getPlayerAccess().CanEditPledge || player.getTarget() == null || !player.getTarget().isPlayer()) {
            return false;
        }
        Player player2 = (Player)player.getTarget();
        if (string.startsWith("admin_pledge")) {
            StringTokenizer stringTokenizer = new StringTokenizer(string);
            stringTokenizer.nextToken();
            String string2 = stringTokenizer.nextToken();
            if (string2.equals("create")) {
                try {
                    if (player2 == null) {
                        player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                        return false;
                    }
                    if (player2.getPlayer().getLevel() < 10) {
                        player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_MEET_THE_CRITERIA_IN_ORDER_TO_CREATE_A_CLAN);
                        return false;
                    }
                    String string3 = stringTokenizer.nextToken();
                    if (string3.length() > 16) {
                        player.sendPacket((IStaticPacket)SystemMsg.CLAN_NAMES_LENGTH_IS_INCORRECT);
                        return false;
                    }
                    if (!Util.isMatchingRegexp(string3, Config.CLAN_NAME_TEMPLATE)) {
                        player.sendPacket((IStaticPacket)SystemMsg.CLAN_NAME_IS_INVALID);
                        return false;
                    }
                    Clan clan = ClanTable.getInstance().createClan(player2, string3);
                    if (clan != null) {
                        player2.sendPacket(clan.listAll());
                        player2.sendPacket(new PledgeShowInfoUpdate(clan), SystemMsg.YOUR_CLAN_HAS_BEEN_CREATED);
                        player2.updatePledgeClass();
                        player2.sendUserInfo(true);
                        player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/pledgemanage.htm"));
                        return true;
                    }
                    player.sendPacket((IStaticPacket)SystemMsg.THIS_NAME_ALREADY_EXISTS);
                    return false;
                }
                catch (Exception exception) {}
            } else if (string2.equals("setlevel")) {
                if (player2.getClan() == null) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    return false;
                }
                try {
                    int n = Integer.parseInt(stringTokenizer.nextToken());
                    Clan clan = player2.getClan();
                    player.sendMessage("You set level " + n + " for clan " + clan.getName());
                    clan.setLevel(n);
                    clan.updateClanInDB();
                    if (n == 5) {
                        player2.sendPacket((IStaticPacket)SystemMsg.NOW_THAT_YOUR_CLAN_LEVEL_IS_ABOVE_LEVEL_5_IT_CAN_ACCUMULATE_CLAN_REPUTATION_POINTS);
                    }
                    PledgeShowInfoUpdate pledgeShowInfoUpdate = new PledgeShowInfoUpdate(clan);
                    PledgeStatusChanged pledgeStatusChanged = new PledgeStatusChanged(clan);
                    for (Player player3 : clan.getOnlineMembers(0)) {
                        player3.updatePledgeClass();
                        player3.sendPacket(SystemMsg.YOUR_CLANS_LEVEL_HAS_INCREASED, pledgeShowInfoUpdate, pledgeStatusChanged);
                        player3.broadcastUserInfo(true, new UserInfoType[0]);
                    }
                    player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/pledgemanage.htm"));
                    return true;
                }
                catch (Exception exception) {}
            } else if (string2.equals("resetcreate")) {
                if (player2.getClan() == null) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    return false;
                }
                player2.getClan().setExpelledMemberTime(0L);
                player.sendMessage("The penalty for creating a clan has been lifted for " + player2.getName());
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/pledgemanage.htm"));
            } else if (string2.equals("resetwait")) {
                player2.setLeaveClanTime(0L);
                player.sendMessage("The penalty for leaving a clan has been lifted for " + player2.getName());
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/pledgemanage.htm"));
            } else if (string2.equals("addrep")) {
                try {
                    int n = Integer.parseInt(stringTokenizer.nextToken());
                    if (player2.getClan() == null || player2.getClan().getLevel() < 5) {
                        player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                        return false;
                    }
                    player2.getClan().incReputation(n, false, "admin_manual");
                    player.sendMessage("Added " + n + " clan points to clan " + player2.getClan().getName() + ".");
                    player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/pledgemanage.htm"));
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage("Please specify a number of clan points to add.");
                }
            } else if (string2.equals("setleader")) {
                Clan clan = player2.getClan();
                if (clan == null) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    player.sendMessage("The target is not a clan member.");
                    return false;
                }
                String string4 = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : player2.getName();
                SubUnit subUnit = clan.getSubUnit(0);
                if (subUnit == null) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    player.sendMessage("The main clan of the clan was not found.");
                    return false;
                }
                UnitMember unitMember = subUnit.getUnitMember(string4);
                if (unitMember == null) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    player.sendMessage("The specified player was not found in the Main Clan section.");
                    return false;
                }
                try {
                    VillageMasterInstance.setNowLeader(clan, subUnit, unitMember);
                    player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/pledgemanage.htm"));
                    player.sendMessage("New leader " + string4 + " has been successfully appointed for clan " + player2.getClan().getName());
                    unitMember.getClan().broadcastToOnlineMembers(new Say2(0, ChatType.CLAN, "GM", "New Clan Leader " + string4 + " has been successfully appointed!"));
                }
                catch (Exception exception) {
                    player.sendMessage("An error occurred while installing the new leader");
                }
            } else if (string2.equals("setclanname")) {
                if (player2.getClan() == null) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    return false;
                }
                String string5 = null;
                if (!stringTokenizer.hasMoreTokens()) {
                    player.sendMessage("Enter new clan name");
                    return false;
                }
                string5 = stringTokenizer.nextToken();
                if (ClanTable.getInstance().getClanByName(string5) != null) {
                    player.sendMessage("Clan Name already taken");
                    return false;
                }
                if (!Util.isMatchingRegexp(string5, Config.CLAN_NAME_TEMPLATE)) {
                    player.sendMessage("Invalid clan name. You can't change clan name");
                    return false;
                }
                if (player2.getEvent(SiegeEvent.class) != null) {
                    player.sendMessage("\u0421lan is currently under siege. You can't change clan name now");
                    return false;
                }
                String string6 = string5;
                SubUnit subUnit = player2.getClan().getSubUnit(0);
                String string7 = subUnit.getName();
                subUnit.setName(string6, true);
                player2.getClan().broadcastClanStatus(true, true, false);
                player.sendMessage("Clan Name changed. New name is " + string5);
                Log.add("Change clan name - " + string7 + "on new name " + string6, "admin change", player);
            }
        }
        return false;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_pledge = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_pledge};
        }

        static {
            a = Commands.a();
        }
    }
}
