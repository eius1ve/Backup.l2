/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dao.JdbcEntityState
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.data.xml.holder.ResidenceHolder
 *  l2.gameserver.handler.admincommands.AdminCommandHandler
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.events.impl.SiegeEvent
 *  l2.gameserver.model.entity.events.objects.SiegeClanObject
 *  l2.gameserver.model.entity.residence.Residence
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.tables.ClanTable
 *  l2.gameserver.utils.HtmlUtils
 */
package handler.admincommands;

import handler.admincommands.ScriptAdminCommand;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import l2.commons.dao.JdbcEntityState;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.handler.admincommands.AdminCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.entity.events.objects.SiegeClanObject;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.utils.HtmlUtils;

public class AdminResidence
extends ScriptAdminCommand {
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanEditNPC) {
            return false;
        }
        switch (commands) {
            case admin_residence_list: {
                NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
                npcHtmlMessage.setFile("admin/residence/residence_list.htm");
                StringBuilder stringBuilder = new StringBuilder(200);
                for (Residence residence : ResidenceHolder.getInstance().getResidences()) {
                    if (residence == null) continue;
                    stringBuilder.append("<tr><td>");
                    stringBuilder.append("<a action=\"bypass -h admin_residence ").append(residence.getId()).append("\">").append(HtmlUtils.htmlResidenceName((int)residence.getId())).append("</a>");
                    stringBuilder.append("</td><td>");
                    Clan clan = residence.getOwner();
                    if (clan == null) {
                        stringBuilder.append("NPC");
                    } else {
                        stringBuilder.append(clan.getName());
                    }
                    stringBuilder.append("</td></tr>");
                }
                npcHtmlMessage.replace("%residence_list%", stringBuilder.toString());
                player.sendPacket((IStaticPacket)npcHtmlMessage);
                break;
            }
            case admin_residence: {
                if (stringArray.length != 2) {
                    return false;
                }
                Residence residence = ResidenceHolder.getInstance().getResidence(Integer.parseInt(stringArray[1]));
                if (residence == null) {
                    return false;
                }
                SiegeEvent siegeEvent = residence.getSiegeEvent();
                NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
                npcHtmlMessage.setFile("admin/residence/siege_info.htm");
                npcHtmlMessage.replace("%residence%", HtmlUtils.htmlResidenceName((int)residence.getId()));
                npcHtmlMessage.replace("%id%", String.valueOf(residence.getId()));
                npcHtmlMessage.replace("%owner%", residence.getOwner() == null ? "NPC" : residence.getOwner().getName());
                npcHtmlMessage.replace("%cycle%", String.valueOf(residence.getCycle()));
                npcHtmlMessage.replace("%paid_cycle%", String.valueOf(residence.getPaidCycle()));
                npcHtmlMessage.replace("%reward_count%", String.valueOf(residence.getRewardCount()));
                npcHtmlMessage.replace("%left_time%", String.valueOf(residence.getCycleDelay()));
                StringBuilder stringBuilder = new StringBuilder(100);
                for (Map.Entry entry : siegeEvent.getObjects().entrySet()) {
                    for (Serializable serializable : (List)entry.getValue()) {
                        if (!(serializable instanceof SiegeClanObject)) continue;
                        SiegeClanObject siegeClanObject = (SiegeClanObject)serializable;
                        stringBuilder.append("<tr>").append("<td>").append(siegeClanObject.getClan().getName()).append("</td>").append("<td>").append(siegeClanObject.getClan().getLeaderName()).append("</td>").append("<td>").append(siegeClanObject.getType()).append("</td>").append("</tr>");
                    }
                }
                npcHtmlMessage.replace("%clans%", stringBuilder.toString());
                npcHtmlMessage.replace("%hour%", String.valueOf(residence.getSiegeDate().get(11)));
                npcHtmlMessage.replace("%minute%", String.valueOf(residence.getSiegeDate().get(12)));
                npcHtmlMessage.replace("%day%", String.valueOf(residence.getSiegeDate().get(5)));
                npcHtmlMessage.replace("%month%", String.valueOf(residence.getSiegeDate().get(2) + 1));
                npcHtmlMessage.replace("%year%", String.valueOf(residence.getSiegeDate().get(1)));
                player.sendPacket((IStaticPacket)npcHtmlMessage);
                break;
            }
            case admin_set_owner: {
                if (stringArray.length != 3) {
                    return false;
                }
                Residence residence = ResidenceHolder.getInstance().getResidence(Integer.parseInt(stringArray[1]));
                if (residence == null) {
                    return false;
                }
                Clan clan = null;
                String string2 = stringArray[2];
                if (!string2.equalsIgnoreCase("npc") && (clan = ClanTable.getInstance().getClanByName(string2)) == null) {
                    player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_NAME);
                    AdminCommandHandler.getInstance().useAdminCommandHandler(player, "admin_residence " + residence.getId());
                    return false;
                }
                SiegeEvent siegeEvent = residence.getSiegeEvent();
                siegeEvent.clearActions();
                residence.getLastSiegeDate().setTimeInMillis(clan == null ? 0L : System.currentTimeMillis());
                residence.getOwnDate().setTimeInMillis(clan == null ? 0L : System.currentTimeMillis());
                residence.changeOwner(clan);
                siegeEvent.reCalcNextTime(false);
                break;
            }
            case admin_set_siege_time: {
                Residence residence = ResidenceHolder.getInstance().getResidence(Integer.parseInt(stringArray[1]));
                if (residence == null) {
                    return false;
                }
                Calendar calendar = (Calendar)residence.getSiegeDate().clone();
                block18: for (int i = 2; i < stringArray.length; ++i) {
                    int n;
                    int n2 = Integer.parseInt(stringArray[i]);
                    switch (i) {
                        case 2: {
                            n = 11;
                            break;
                        }
                        case 3: {
                            n = 12;
                            break;
                        }
                        case 4: {
                            n = 5;
                            break;
                        }
                        case 5: {
                            n = 2;
                            --n2;
                            break;
                        }
                        case 6: {
                            n = 1;
                            break;
                        }
                        default: {
                            continue block18;
                        }
                    }
                    calendar.set(n, n2);
                }
                SiegeEvent siegeEvent = residence.getSiegeEvent();
                siegeEvent.clearActions();
                residence.getSiegeDate().setTimeInMillis(calendar.getTimeInMillis());
                siegeEvent.registerActions();
                residence.setJdbcState(JdbcEntityState.UPDATED);
                residence.update();
                AdminCommandHandler.getInstance().useAdminCommandHandler(player, "admin_residence " + residence.getId());
                break;
            }
            case admin_quick_siege_start: {
                Residence residence = ResidenceHolder.getInstance().getResidence(Integer.parseInt(stringArray[1]));
                if (residence == null) {
                    return false;
                }
                Calendar calendar = Calendar.getInstance();
                if (stringArray.length >= 3) {
                    calendar.set(13, -Integer.parseInt(stringArray[2]));
                }
                SiegeEvent siegeEvent = residence.getSiegeEvent();
                siegeEvent.clearActions();
                residence.getSiegeDate().setTimeInMillis(calendar.getTimeInMillis());
                siegeEvent.registerActions();
                residence.setJdbcState(JdbcEntityState.UPDATED);
                residence.update();
                AdminCommandHandler.getInstance().useAdminCommandHandler(player, "admin_residence " + residence.getId());
                break;
            }
            case admin_quick_siege_stop: {
                Residence residence = ResidenceHolder.getInstance().getResidence(Integer.parseInt(stringArray[1]));
                if (residence == null) {
                    return false;
                }
                final SiegeEvent siegeEvent = residence.getSiegeEvent();
                siegeEvent.clearActions();
                ThreadPoolManager.getInstance().execute((Runnable)new RunnableImpl(){

                    public void runImpl() throws Exception {
                        siegeEvent.stopEvent();
                    }
                });
                AdminCommandHandler.getInstance().useAdminCommandHandler(player, "admin_residence " + residence.getId());
            }
        }
        return true;
    }

    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_residence_list = new Commands();
        public static final /* enum */ Commands admin_residence = new Commands();
        public static final /* enum */ Commands admin_set_owner = new Commands();
        public static final /* enum */ Commands admin_set_siege_time = new Commands();
        public static final /* enum */ Commands admin_quick_siege_start = new Commands();
        public static final /* enum */ Commands admin_quick_siege_stop = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_residence_list, admin_residence, admin_set_owner, admin_set_siege_time, admin_quick_siege_start, admin_quick_siege_stop};
        }

        static {
            a = Commands.a();
        }
    }
}
