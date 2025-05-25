/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.handler.admincommands.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.database.mysql;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.utils.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminChangeAccessLevel
implements IAdminCommandHandler {
    private static final Logger be = LoggerFactory.getLogger(AdminChangeAccessLevel.class);

    private static void r(Player player) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        Object object = "Moderators managment panel.<br>";
        File file = new File("config/GMAccess.d/");
        if (!file.exists() || !file.isDirectory()) {
            object = (String)object + "Error: Can't open permissions folder.";
            npcHtmlMessage.setHtml((String)object);
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            return;
        }
        object = (String)object + "<p align=right>";
        object = (String)object + "<button width=100 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\" action=\"bypass -h admin_moders_add\" value=\"Add modrator\">";
        object = (String)object + "</p><br>";
        object = (String)object + "<center><font color=LEVEL>Moderators:</font></center>";
        object = (String)object + "<table width=285>";
        for (File file2 : file.listFiles()) {
            String string;
            String string2 = file2.getName();
            if (file2.isDirectory() || !string2.startsWith("m") || !string2.endsWith(".xml") || !(string = string2.substring(1, string2.lastIndexOf(".xml"))).matches("-?\\d+")) continue;
            int n = Integer.parseInt(string);
            Object object2 = AdminChangeAccessLevel.d(n);
            boolean bl = false;
            if (object2 == null || ((String)object2).isEmpty()) {
                object2 = "" + n;
            } else {
                bl = GameObjectsStorage.getPlayer((String)object2) != null;
            }
            object = (String)object + "<tr>";
            object = (String)object + "<td width=140>" + (String)object2;
            object = (String)object + (bl ? " <font color=\"33CC66\">(on)</font>" : " <font color=\"cc334d\">(offline)</font>");
            object = (String)object + "</td>";
            object = (String)object + "<td width=45><button width=60 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\" action=\"bypass -h admin_moders_del " + n + "\" value=\"Remove\"></td>";
            object = (String)object + "</tr>";
        }
        object = (String)object + "</table>";
        npcHtmlMessage.setHtml((String)object);
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String d(int n) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        String string;
        block4: {
            string = null;
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT `char_name` FROM `characters` WHERE `obj_Id`=\"" + n + "\" LIMIT 1");
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                string = resultSet.getString(1);
            }
            catch (Exception exception) {
                try {
                    be.warn("SQL Error: " + exception);
                    be.error("", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return string;
    }

    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanGmEdit) {
            return false;
        }
        switch (commands) {
            case admin_changelvl: {
                if (stringArray.length == 2) {
                    int n = Integer.parseInt(stringArray[1]);
                    if (!player.getTarget().isPlayer()) break;
                    ((Player)player.getTarget()).setAccessLevel(n);
                    break;
                }
                if (stringArray.length != 3) break;
                int n = Integer.parseInt(stringArray[2]);
                Player player2 = GameObjectsStorage.getPlayer(stringArray[1]);
                if (player2 == null) break;
                player2.setAccessLevel(n);
                break;
            }
            case admin_moders: {
                AdminChangeAccessLevel.r(player);
                break;
            }
            case admin_moders_add: {
                Object object;
                if (player.getTarget() == null || !player.getTarget().isPlayer()) {
                    player.sendMessage("Incorrect target. Please select a player.");
                    AdminChangeAccessLevel.r(player);
                    return false;
                }
                Player player3 = player.getTarget().getPlayer();
                if (Config.gmlist.containsKey(player3.getObjectId())) {
                    player.sendMessage("Error: Moderator " + player3.getName() + " already in server access list.");
                    AdminChangeAccessLevel.r(player);
                    return false;
                }
                String string2 = "m" + player3.getObjectId() + ".xml";
                if (!Files.copyFile("config/GMAccess.d/moderator.xml", "config/GMAccess.d/" + string2)) {
                    player.sendMessage("Error: Failed to copy access-file.");
                    AdminChangeAccessLevel.r(player);
                    return false;
                }
                Object object2 = "";
                try {
                    String string3;
                    object = new BufferedReader(new FileReader("config/GMAccess.d/" + string2));
                    while ((string3 = ((BufferedReader)object).readLine()) != null) {
                        object2 = (String)object2 + string3 + "\n";
                    }
                    ((BufferedReader)object).close();
                    object2 = ((String)object2).replaceFirst("-1", "" + player3.getObjectId());
                    Files.writeFile("config/GMAccess.d/" + string2, (String)object2);
                }
                catch (Exception exception) {
                    player.sendMessage("Error: Failed to modify object ID in access-file.");
                    File file = new File("config/GMAccess.d/" + string2);
                    if (file.exists()) {
                        file.delete();
                    }
                    AdminChangeAccessLevel.r(player);
                    return false;
                }
                object = new File("config/GMAccess.d/" + string2);
                if (!((File)object).exists()) {
                    player.sendMessage("Error: Failed to read access-file for " + player3.getName());
                    AdminChangeAccessLevel.r(player);
                    return false;
                }
                Config.loadGMAccess((File)object);
                player3.setPlayerAccess(Config.gmlist.get(player3.getObjectId()));
                player.sendMessage("Moderator " + player3.getName() + " added.");
                AdminChangeAccessLevel.r(player);
                break;
            }
            case admin_moders_del: {
                String string4;
                File file;
                if (stringArray.length < 2) {
                    player.sendMessage("Please specify moderator object ID to delete moderator.");
                    AdminChangeAccessLevel.r(player);
                    return false;
                }
                int n = Integer.parseInt(stringArray[1]);
                if (!Config.gmlist.containsKey(n)) {
                    player.sendMessage("Error: Moderator with object ID " + n + " not found in server access lits.");
                    AdminChangeAccessLevel.r(player);
                    return false;
                }
                Config.gmlist.remove(n);
                Player player4 = GameObjectsStorage.getPlayer(n);
                if (player4 != null) {
                    player4.setPlayerAccess(null);
                }
                if (!((file = new File("config/GMAccess.d/" + (string4 = "m" + n + ".xml"))).exists() && file.isFile() && file.delete())) {
                    player.sendMessage("Error: Can't delete access-file: " + string4);
                    AdminChangeAccessLevel.r(player);
                    return false;
                }
                if (player4 != null) {
                    player.sendMessage("Moderator " + player4.getName() + " deleted.");
                } else {
                    player.sendMessage("Moderator with object ID " + n + " deleted.");
                }
                AdminChangeAccessLevel.r(player);
                break;
            }
            case admin_penalty: {
                if (stringArray.length < 2) {
                    player.sendMessage("USAGE: //penalty charName [count] [reason]");
                    return false;
                }
                int n = 1;
                if (stringArray.length > 2) {
                    n = Integer.parseInt(stringArray[2]);
                }
                String string5 = "not indicated";
                if (stringArray.length > 3) {
                    string5 = stringArray[3];
                }
                int n2 = 0;
                Player player5 = GameObjectsStorage.getPlayer(stringArray[1]);
                if (player5 != null && player5.getPlayerAccess().CanBanChat) {
                    n2 = player5.getObjectId();
                    int n3 = 0;
                    String string6 = player5.getVar("penaltyChatCount");
                    if (string6 != null) {
                        n3 = Integer.parseInt(string6);
                    }
                    player5.setVar("penaltyChatCount", "" + (n3 + n), -1L);
                } else {
                    n2 = mysql.simple_get_int("obj_Id", "characters", "`char_name`='" + stringArray[1] + "'");
                    if (n2 > 0) {
                        Integer n4 = (Integer)mysql.get("SELECT `value` FROM character_variables WHERE `obj_id` = " + n2 + " AND `name` = 'penaltyChatCount'");
                        mysql.set("REPLACE INTO character_variables (obj_id, type, name, value, expire_time) VALUES (" + n2 + ",'user-var','penaltyChatCount','" + (n4 + n) + "',-1)");
                    }
                }
                if (n2 <= 0 || !Config.BANCHAT_ANNOUNCE_FOR_ALL_WORLD) break;
                Announcements.getInstance().announceByCustomMessage("common.nochannel.reason", new String[]{player.getName(), stringArray[1], Integer.toString(n), string5});
            }
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_changelvl = new Commands();
        public static final /* enum */ Commands admin_moders = new Commands();
        public static final /* enum */ Commands admin_moders_add = new Commands();
        public static final /* enum */ Commands admin_moders_del = new Commands();
        public static final /* enum */ Commands admin_penalty = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_changelvl, admin_moders, admin_moders_add, admin_moders_del, admin_penalty};
        }

        static {
            a = Commands.a();
        }
    }
}
