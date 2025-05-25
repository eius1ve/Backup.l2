/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.handler.bbs.CommunityBoardManager
 *  l2.gameserver.handler.bbs.ICommunityBoardHandler
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.Zone$ZoneType
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.CloseBoard
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Location
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services.community.custom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.handler.bbs.CommunityBoardManager;
import l2.gameserver.handler.bbs.ICommunityBoardHandler;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.CloseBoard;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.community.custom.ACbConfigManager;
import services.community.custom.CommunityTools;

public class CommunityTeleport
implements ICommunityBoardHandler,
ScriptFile {
    private static final Logger es = LoggerFactory.getLogger(CommunityTeleport.class);
    public static final Zone.ZoneType[] FORBIDDEN_ZONES = new Zone.ZoneType[]{Zone.ZoneType.RESIDENCE, Zone.ZoneType.ssq_zone, Zone.ZoneType.battle_zone, Zone.ZoneType.SIEGE, Zone.ZoneType.no_restart, Zone.ZoneType.no_summon};

    public void onLoad() {
        if (Config.COMMUNITYBOARD_ENABLED && ACbConfigManager.ALLOW_PVPCB_TELEPORT) {
            es.info("CommunityBoard: CommunityTeleport loaded.");
            CommunityBoardManager.getInstance().registerHandler((ICommunityBoardHandler)this);
        }
    }

    public void onReload() {
        if (Config.COMMUNITYBOARD_ENABLED) {
            CommunityBoardManager.getInstance().removeHandler((ICommunityBoardHandler)this);
        }
    }

    public void onShutdown() {
    }

    public String[] getBypassCommands() {
        return new String[]{"_bbsteleport", "_bbsteleport_delete", "_bbsteleport_save", "_bbsteleport_teleport"};
    }

    public void onBypassCommand(Player player, String string) {
        if (!CommunityTools.checkConditions(player)) {
            String string2 = HtmCache.getInstance().getNotNull("scripts/services/community/pages/locked.htm", player);
            string2 = string2.replace("%name%", player.getName());
            ShowBoard.separateAndSend((String)string2, (Player)player);
            return;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";");
        String string3 = stringTokenizer.nextToken();
        if (string3.equals("_bbsteleport")) {
            this.az(player);
        } else if (string3.equals("_bbsteleport_delete")) {
            int n = Integer.parseInt(stringTokenizer.nextToken());
            this.w(player, n);
            this.az(player);
        } else if (string3.equals("_bbsteleport_save")) {
            String string4 = stringTokenizer.nextToken();
            this.s(player, string4);
            this.az(player);
        } else if (string3.equals("_bbsteleport_teleport")) {
            StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken(), " ");
            int n = Integer.parseInt(stringTokenizer2.nextToken());
            int n2 = Integer.parseInt(stringTokenizer2.nextToken());
            int n3 = Integer.parseInt(stringTokenizer2.nextToken());
            int n4 = Integer.parseInt(stringTokenizer2.nextToken());
            this.a(player, n, n2, n3, n4);
            this.az(player);
        } else {
            ShowBoard.separateAndSend((String)("<html><body><br><br><center>\u0424\u0443\u043d\u043a\u0446\u0438\u044f: " + string + " \u043f\u043e\u043a\u0430 \u043d\u0435 \u0440\u0435\u0430\u043b\u0438\u0437\u043e\u0432\u0430\u043d\u0430</center><br><br></body></html>"), (Player)player);
        }
    }

    private void a(Player player, int n, int n2, int n3, int n4) {
        if (!CommunityTeleport.checkFirstConditions(player) || !CommunityTeleport.checkTeleportConditions(player)) {
            return;
        }
        if (!CommunityTeleport.checkTeleportLocation(player, n, n2, n3)) {
            return;
        }
        if (n4 > 0 && player.getAdena() < (long)n4) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA));
            return;
        }
        if (n4 > 0) {
            player.reduceAdena((long)n4, true);
        }
        if (ACbConfigManager.ALLOW_PVPCB_TELEPORT_AT_TOWN_ZONE_ONLY && !player.isInZonePeace()) {
            player.sendMessage(new CustomMessage("alt.bbs_available_at_town_only", player, new Object[0]));
            return;
        }
        player.teleToLocation(n, n2, n3);
        if (ACbConfigManager.CLOSE_PVPCB_AFTER_TELEPORT) {
            player.sendPacket((IStaticPacket)new CloseBoard());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void az(Player player) {
        Connection connection = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM bbs_comteleport WHERE charId=?;");
            preparedStatement.setLong(1, player.getObjectId());
            ResultSet resultSet = preparedStatement.executeQuery();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<table width=220>");
            while (resultSet.next()) {
                CBteleport cBteleport = new CBteleport();
                cBteleport.TpId = resultSet.getInt("TpId");
                cBteleport.TpName = resultSet.getString("name");
                cBteleport.PlayerId = resultSet.getInt("charId");
                cBteleport.xC = resultSet.getInt("xPos");
                cBteleport.yC = resultSet.getInt("yPos");
                cBteleport.zC = resultSet.getInt("zPos");
                stringBuilder.append("<tr>");
                stringBuilder.append("<td>");
                stringBuilder.append("<button value=\"" + cBteleport.TpName + "\" action=\"bypass _bbsteleport_teleport;" + cBteleport.xC + " " + cBteleport.yC + " " + cBteleport.zC + " " + ACbConfigManager.ALT_CB_TELE_POINT_PRICE + "\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">");
                stringBuilder.append("</td>");
                stringBuilder.append("<td>");
                stringBuilder.append("<button value=\"\u0423\u0434\u0430\u043b\u0438\u0442\u044c\" action=\"bypass _bbsteleport_delete;" + cBteleport.TpId + "\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">");
                stringBuilder.append("</td>");
                stringBuilder.append("</tr>");
            }
            stringBuilder.append("</table>");
            DbUtils.closeQuietly((Statement)preparedStatement, (ResultSet)resultSet);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly((Connection)connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void w(Player player, int n) {
        Connection connection = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM bbs_comteleport WHERE charId=? AND TpId=?;");
            preparedStatement.setInt(1, player.getObjectId());
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
            DbUtils.closeQuietly((Statement)preparedStatement);
        }
        catch (Exception exception) {
            es.error("data error on Delete Teleport: " + exception);
            exception.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly((Connection)connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void s(Player player, String string) {
        if (!CommunityTeleport.checkFirstConditions(player) || !CommunityTeleport.checkTeleportConditions(player)) {
            return;
        }
        if (!CommunityTeleport.checkTeleportLocation(player, player.getX(), player.getY(), player.getZ())) {
            return;
        }
        if (string.equals("") || string.equals(null)) {
            player.sendMessage("\u0412\u044b \u043d\u0435 \u0432\u0432\u0435\u043b\u0438 \u0418\u043c\u044f \u0437\u0430\u043a\u043b\u0430\u0434\u043a\u0438");
            return;
        }
        Connection connection = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM bbs_comteleport WHERE charId=?;");
            preparedStatement.setLong(1, player.getObjectId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (resultSet.getInt(1) <= ACbConfigManager.ALT_CB_TELE_POINT_MAX_COUNT - 1) {
                PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT COUNT(*) FROM bbs_comteleport WHERE charId=? AND name=?;");
                preparedStatement2.setLong(1, player.getObjectId());
                preparedStatement2.setString(2, string);
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                resultSet2.next();
                if (resultSet2.getInt(1) == 0) {
                    PreparedStatement preparedStatement3 = connection.prepareStatement("INSERT INTO bbs_comteleport (charId,xPos,yPos,zPos,name) VALUES(?,?,?,?,?)");
                    preparedStatement3.setInt(1, player.getObjectId());
                    preparedStatement3.setInt(2, player.getX());
                    preparedStatement3.setInt(3, player.getY());
                    preparedStatement3.setInt(4, player.getZ());
                    preparedStatement3.setString(5, string);
                    preparedStatement3.execute();
                    DbUtils.closeQuietly((Statement)preparedStatement3);
                } else {
                    PreparedStatement preparedStatement4 = connection.prepareStatement("UPDATE bbs_comteleport SET xPos=?, yPos=?, zPos=? WHERE charId=? AND name=?;");
                    preparedStatement4.setInt(1, player.getObjectId());
                    preparedStatement4.setInt(2, player.getX());
                    preparedStatement4.setInt(3, player.getY());
                    preparedStatement4.setInt(4, player.getZ());
                    preparedStatement4.setString(5, string);
                    preparedStatement4.execute();
                    DbUtils.closeQuietly((Statement)preparedStatement4);
                }
            } else {
                player.sendMessage("\u0412\u044b \u043d\u0435 \u043c\u043e\u0436\u0435\u0442\u0435 \u0441\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c \u0431\u043e\u043b\u0435\u0435 " + ACbConfigManager.ALT_CB_TELE_POINT_MAX_COUNT + " \u0437\u0430\u043a\u043b\u0430\u0434\u043e\u043a");
                return;
            }
            DbUtils.closeQuietly((Statement)preparedStatement, (ResultSet)resultSet);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly((Connection)connection);
        }
    }

    public static boolean checkFirstConditions(Player player) {
        if (player == null) {
            return false;
        }
        if (player.getActiveWeaponFlagAttachment() != null) {
            player.sendMessage(new CustomMessage("YOU_CANNOT_TELEPORT_WHILE_IN_POSSESSION_OF_A_WARD", player, new Object[0]));
            return false;
        }
        if (player.isOlyParticipant()) {
            player.sendMessage(new CustomMessage("YOU_CANNOT_USE_MY_TELEPORTS_WHILE_PARTICIPATING_IN_AN_OLYMPIAD_MATCH", player, new Object[0]));
            return false;
        }
        if (player.getReflection() != ReflectionManager.DEFAULT) {
            player.sendMessage(new CustomMessage("YOU_CANNOT_USE_MY_TELEPORTS_WHILE_YOU_ARE_DEAD", player, new Object[0]));
            return false;
        }
        if (player.isInDuel()) {
            player.sendMessage(new CustomMessage("YOU_CANNOT_USE_MY_TELEPORTS_DURING_A_DUEL", player, new Object[0]));
            return false;
        }
        if (player.isInCombat() || player.getPvpFlag() != 0) {
            player.sendMessage(new CustomMessage("YOU_CANNOT_USE_MY_TELEPORTS_DURING_A_BATTLE", player, new Object[0]));
            return false;
        }
        if (player.isOnSiegeField() || player.isInZoneBattle()) {
            player.sendMessage(new CustomMessage("YOU_CANNOT_USE_MY_TELEPORTS_WHILE_PARTICIPATING_A_LARGE_SCALE_BATTLE_SUCH_AS_A_CASTLE_SIEGE", player, new Object[0]));
            return false;
        }
        if (player.isFlying()) {
            player.sendMessage(new CustomMessage("YOU_CANNOT_USE_MY_TELEPORTS_WHILE_FLYING", player, new Object[0]));
            return false;
        }
        if (player.isInWater() || player.isInBoat()) {
            player.sendMessage(new CustomMessage("YOU_CANNOT_USE_MY_TELEPORTS_UNDERWATER", player, new Object[0]));
            return false;
        }
        if (!player.hasBonus() && ACbConfigManager.ALT_CB_TELE_PREMIUM_ONLY) {
            player.sendMessage(new CustomMessage("common.TeleportPremiumOnly", player, new Object[0]));
            return false;
        }
        if (ACbConfigManager.ALT_CB_TELE_ITEM_ACCESS > 0 && player.getInventory().getItemByItemId(ACbConfigManager.ALT_CB_TELE_ITEM_ACCESS) == null) {
            String string = HtmCache.getInstance().getNotNull("scripts/services/community/pages/premium_item_only.htm", player);
            string = string.replace("%name%", player.getName());
            string = string.replace("%item%", String.valueOf(ACbConfigManager.ALT_CB_TELE_ITEM_ACCESS));
            ShowBoard.separateAndSend((String)string, (Player)player);
            return false;
        }
        return true;
    }

    public static boolean checkTeleportConditions(Player player) {
        if (player == null) {
            return false;
        }
        if (player.isAlikeDead()) {
            player.sendMessage(new CustomMessage("YOU_CANNOT_USE_MY_TELEPORTS_WHILE_YOU_ARE_DEAD", player, new Object[0]));
            return false;
        }
        if (player.isInStoreMode() || player.isInTrade()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_SUMMON_DURING_A_TRADE_OR_WHILE_USING_A_PRIVATE_STORE);
            return false;
        }
        if (player.isInBoat() || player.isParalyzed() || player.isStunned() || player.isSleeping()) {
            player.sendMessage(new CustomMessage("YOU_CANNOT_USE_MY_TELEPORTS_WHILE_YOU_ARE_IN_A_FLINT_OR_PARALYZED_STATE", player, new Object[0]));
            return false;
        }
        return true;
    }

    public static boolean checkTeleportLocation(Player player, Location location) {
        return CommunityTeleport.checkTeleportLocation(player, location.x, location.y, location.z);
    }

    public static boolean checkTeleportLocation(Player player, int n, int n2, int n3) {
        if (player == null) {
            return false;
        }
        for (Zone.ZoneType zoneType : FORBIDDEN_ZONES) {
            Zone zone = player.getZone(zoneType);
            if (zone == null) continue;
            player.sendMessage(new CustomMessage("YOU_CAN_NOT_TELEPORT_IN_THIS_ZONE", player, new Object[0]));
            return false;
        }
        return true;
    }

    public void onWriteCommand(Player player, String string, String string2, String string3, String string4, String string5, String string6) {
    }

    public class CBteleport {
        public int TpId = 0;
        public String TpName = "";
        public int PlayerId = 0;
        public int xC = 0;
        public int yC = 0;
        public int zC = 0;
    }
}
