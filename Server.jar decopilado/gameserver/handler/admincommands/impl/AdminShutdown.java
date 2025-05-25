/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.math.NumberUtils
 */
package l2.gameserver.handler.admincommands.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import l2.commons.lang.StatsUtils;
import l2.gameserver.Config;
import l2.gameserver.GameServer;
import l2.gameserver.GameTimeController;
import l2.gameserver.Shutdown;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.instancemanager.ServerVariables;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import org.apache.commons.lang3.math.NumberUtils;

public class AdminShutdown
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanRestart) {
            return false;
        }
        try {
            switch (commands) {
                case admin_server_shutdown: {
                    Shutdown.getInstance().schedule(NumberUtils.toInt((String)stringArray[1], (int)-1), 0);
                    break;
                }
                case admin_server_restart: {
                    Shutdown.getInstance().schedule(NumberUtils.toInt((String)stringArray[1], (int)-1), 2);
                    break;
                }
                case admin_server_abort: {
                    Shutdown.getInstance().cancel();
                }
            }
        }
        catch (Exception exception) {
            this.B(player);
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private void B(Player player) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        int n = GameTimeController.getInstance().getGameTime();
        int n2 = n / 60;
        int n3 = n % 60;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, n2);
        calendar.set(12, n3);
        int n4 = ServerVariables.getInt("maxTotalOnline", 0);
        StringBuilder stringBuilder = new StringBuilder("<html><body>");
        stringBuilder.append("<table width=260><tr>");
        stringBuilder.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td width=180><center>Server Management Menu</center></td>");
        stringBuilder.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("</tr></table>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<table>");
        stringBuilder.append("<tr><td>Players Online: " + (GameObjectsStorage.getAllPlayersCount() - GameObjectsStorage.getAllOfflineCount()) + "</td></tr>");
        stringBuilder.append("<tr><td>Offline Traders: " + GameObjectsStorage.getAllOfflineCount() + "</td></tr>");
        stringBuilder.append("<tr><td>Max Session Online: " + GameServer.getInstance().getCurrentMaxOnline() + "</td></tr>");
        stringBuilder.append("<tr><td>Max Total Online: " + Math.max(n4, GameServer.getInstance().getCurrentMaxOnline()) + "</td></tr>");
        stringBuilder.append("<tr><td>Used Memory: " + StatsUtils.getMemUsedMb() + "</td></tr>");
        stringBuilder.append("<tr><td>Server Rates: " + Config.RATE_XP + "x, " + Config.RATE_SP + "x, " + Config.RATE_DROP_ADENA + "x, " + Config.RATE_DROP_ITEMS + "x</td></tr>");
        stringBuilder.append("<tr><td>Game Time: " + simpleDateFormat.format(calendar.getTime()) + "</td></tr>");
        stringBuilder.append("</table><br>");
        stringBuilder.append("<table width=270>");
        stringBuilder.append("<tr><td><center>Seconds till: <edit var=\"shutdown_time\" width=60></center></td></tr>");
        stringBuilder.append("</table><br>");
        stringBuilder.append("<center><table><tr><td>");
        stringBuilder.append("<button value=\"Shutdown\" action=\"bypass -h admin_server_shutdown $shutdown_time\" width=60 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td><td>");
        stringBuilder.append("<button value=\"Restart\" action=\"bypass -h admin_server_restart $shutdown_time\" width=60 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td><td>");
        stringBuilder.append("<button value=\"Abort\" action=\"bypass -h admin_server_abort\" width=60 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td><td>");
        stringBuilder.append("<button value=\"Refresh\" action=\"bypass -h admin_server_shutdown\" width=60 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\">");
        stringBuilder.append("</td></tr></table></center>");
        stringBuilder.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_server_shutdown = new Commands();
        public static final /* enum */ Commands admin_server_restart = new Commands();
        public static final /* enum */ Commands admin_server_abort = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_server_shutdown, admin_server_restart, admin_server_abort};
        }

        static {
            a = Commands.a();
        }
    }
}
