/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.math.NumberUtils
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.handler.voicecommands.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.dao.CharacterVariablesDAO;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.network.authcomm.AuthServerCommunication;
import l2.gameserver.network.authcomm.gs2as.IGPwdCng;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.utils.ItemFunctions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;

public class Cfg
implements IVoicedCommandHandler {
    private static final String bY = "{CALL `lip_RepairePlayer`(?)}";
    private static final Pattern a = Pattern.compile("^([\\w\\d_-]{4,18})\\s+([\\w\\d_-]{4,16})$");
    private static final long aP = 3600000L;
    private String[] o = new String[]{"cfg", "menu", "password", "repair"};

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (!Config.ALT_ALLOW_MENU_COMMAND) {
            return false;
        }
        if (string.equalsIgnoreCase(this.o[0]) || string.equalsIgnoreCase(this.o[1])) {
            Object object;
            if (string2 != null && ((String[])(object = string2.split(" "))).length == 2) {
                int n;
                if (((String)object[0]).equalsIgnoreCase("dli")) {
                    if (((String)object[1]).equalsIgnoreCase("on")) {
                        player.setVar("DroplistIcons", "1", -1L);
                    } else if (((String)object[1]).equalsIgnoreCase("of")) {
                        player.unsetVar("DroplistIcons");
                    }
                }
                if (((String)object[0]).equalsIgnoreCase("noe")) {
                    if (((String)object[1]).equalsIgnoreCase("on")) {
                        player.setVar("NoExp", "1", -1L);
                        player.sendMessage(new CustomMessage("usercommandhandlers.ExpOn", player, new Object[0]));
                    } else if (((String)object[1]).equalsIgnoreCase("of")) {
                        player.unsetVar("NoExp");
                        player.sendMessage(new CustomMessage("usercommandhandlers.ExpOff", player, new Object[0]));
                    }
                }
                if (((String)object[0]).equalsIgnoreCase("pvpa")) {
                    if (((String)object[1]).equalsIgnoreCase("on")) {
                        player.setVar("PvPAnnounce", "1", -1L);
                        player.sendMessage(new CustomMessage("usercommandhandlers.PvPAnnounceOn", player, new Object[0]));
                    } else if (((String)object[1]).equalsIgnoreCase("of")) {
                        player.setVar("PvPAnnounce", "0", -1L);
                        player.sendMessage(new CustomMessage("usercommandhandlers.PvPAnnounceOff", player, new Object[0]));
                    }
                }
                if (((String)object[0]).equalsIgnoreCase("notraders")) {
                    if (((String)object[1]).equalsIgnoreCase("on")) {
                        player.setNotShowTraders(true);
                        player.setVar("notraders", "true", -1L);
                    } else if (((String)object[1]).equalsIgnoreCase("of")) {
                        player.setNotShowTraders(false);
                        player.unsetVar("notraders");
                    }
                }
                if (((String)object[0]).equalsIgnoreCase("buffAnimRange")) {
                    n = 15 * NumberUtils.toInt((String)object[1], (int)0);
                    if (n < 0) {
                        n = -1;
                    } else if (n > 1500) {
                        n = 1500;
                    }
                    player.setBuffAnimRange(n);
                    player.setVar("buffAnimRange", String.valueOf(n), -1L);
                }
                if (((String)object[0]).equalsIgnoreCase("noShift")) {
                    if (((String)object[1]).equalsIgnoreCase("on")) {
                        player.setVar("noShift", "1", -1L);
                    } else if (((String)object[1]).startsWith("of")) {
                        player.unsetVar("noShift");
                    }
                }
                if (((String)object[0]).equalsIgnoreCase("shotsAnim")) {
                    if (((String)object[1]).equalsIgnoreCase("on")) {
                        player.unsetVar("noShotsAnim");
                        player.setNoShotsAnim(false);
                    } else if (((String)object[1]).startsWith("of")) {
                        player.setVar("noShotsAnim", "1", -1L);
                        player.setNoShotsAnim(true);
                    }
                }
                if (((String)object[0]).equalsIgnoreCase("hwidlock") && player.getNetConnection() != null && player.getNetConnection().getHwid() != null && !player.getNetConnection().getHwid().isEmpty()) {
                    if (((String)object[1]).equalsIgnoreCase("on")) {
                        player.setHWIDLock(player.getNetConnection().getHwid());
                    } else if (((String)object[1]).startsWith("of")) {
                        player.setHWIDLock(null);
                    }
                }
                if (((String)object[0]).equalsIgnoreCase("iplock") && player.getNetConnection() != null && player.getNetConnection().getIpAddr() != null && !player.getNetConnection().getIpAddr().isEmpty()) {
                    if (((String)object[1]).equalsIgnoreCase("on")) {
                        player.setIPLock(player.getNetConnection().getIpAddr());
                    } else if (((String)object[1]).startsWith("of")) {
                        player.setIPLock(null);
                    }
                }
                if (((String)object[0]).equalsIgnoreCase("lang")) {
                    if (((String)object[1]).equalsIgnoreCase("en")) {
                        player.setVar("lang@", "en", -1L);
                    } else if (((String)object[1]).equalsIgnoreCase("ru")) {
                        player.setVar("lang@", "ru", -1L);
                    }
                }
                if (Config.SERVICES_ENABLE_NO_CARRIER && ((String)object[0]).equalsIgnoreCase("noCarrier")) {
                    n = NumberUtils.toInt((String)object[1], (int)0);
                    if (n <= 0) {
                        n = 0;
                    } else if (n > Config.SERVICES_NO_CARRIER_MAX_TIME) {
                        n = Config.SERVICES_NO_CARRIER_MAX_TIME;
                    } else if (n < Config.SERVICES_NO_CARRIER_MIN_TIME) {
                        n = Config.SERVICES_NO_CARRIER_MIN_TIME;
                    }
                    player.setVar("noCarrier", String.valueOf(n), -1L);
                }
                if (((String)object[0]).equalsIgnoreCase("translit")) {
                    if (((String)object[1]).equalsIgnoreCase("on")) {
                        player.setVar("translit", "tl", -1L);
                    } else if (((String)object[1]).equalsIgnoreCase("la")) {
                        player.setVar("translit", "tc", -1L);
                    } else if (((String)object[1]).equalsIgnoreCase("of")) {
                        player.unsetVar("translit");
                    }
                }
                if (((String)object[0]).equalsIgnoreCase("blockparty")) {
                    if (((String)object[1]).equalsIgnoreCase("on")) {
                        player.setVar("blockparty@", "on", -1L);
                    } else if (((String)object[1]).equalsIgnoreCase("of")) {
                        player.unsetVar("blockparty@");
                    }
                }
                if (((String)object[0]).equalsIgnoreCase("blockpvpevent")) {
                    if (((String)object[1]).equalsIgnoreCase("on")) {
                        player.setVar("blockpvpevent@", "on", -1L);
                    } else if (((String)object[1]).equalsIgnoreCase("of")) {
                        player.unsetVar("blockpvpevent@");
                    }
                }
                if (((String)object[0]).equalsIgnoreCase("blocktrade")) {
                    if (((String)object[1]).equalsIgnoreCase("on")) {
                        player.setVar("blocktrade@", "on", -1L);
                    } else if (((String)object[1]).equalsIgnoreCase("of")) {
                        player.unsetVar("blocktrade@");
                    }
                }
                if (((String)object[0]).equalsIgnoreCase("noaskboss")) {
                    if (((String)object[1]).equalsIgnoreCase("on")) {
                        player.setVar("noaskboss@", "1", -1L);
                    } else if (((String)object[1]).equalsIgnoreCase("of")) {
                        player.unsetVar("noaskboss@");
                    }
                }
                if (Config.AUTO_LOOT_INDIVIDUAL && ((String)object[0]).equalsIgnoreCase("autoloot")) {
                    if (((String)object[1]).equalsIgnoreCase("on")) {
                        if (Config.AUTO_LOOT_ONLY_FOR_PREMIUM && !player.hasBonus()) {
                            player.sendMessage(new CustomMessage("usercommandhandlers.AutolootForPAOnly", player, new Object[0]));
                            return false;
                        }
                        if (Config.AUTO_LOOT_ONLY_WITH_IDS.length > 0 && !ItemFunctions.haveAnyOf(player, Config.AUTO_LOOT_ONLY_WITH_IDS)) {
                            player.sendMessage(new CustomMessage("usercommandhandlers.AutolootWithItemOnly", player, new Object[0]));
                            return false;
                        }
                        player.setAutoLoot(true);
                        player.setAutoLootAdena(true);
                        player.sendMessage(new CustomMessage("usercommandhandlers.AutoLootAll", player, new Object[0]));
                    } else if (((String)object[1]).equalsIgnoreCase("ad")) {
                        player.setAutoLoot(false);
                        if (Config.AUTO_LOOT_ONLY_FOR_PREMIUM && !player.hasBonus()) {
                            player.sendMessage(new CustomMessage("usercommandhandlers.AutolootForPAOnly", player, new Object[0]));
                            return false;
                        }
                        if (Config.AUTO_LOOT_ONLY_WITH_IDS.length > 0 && !ItemFunctions.haveAnyOf(player, Config.AUTO_LOOT_ONLY_WITH_IDS)) {
                            player.sendMessage(new CustomMessage("usercommandhandlers.AutolootWithItemOnly", player, new Object[0]));
                            return false;
                        }
                        player.setAutoLootAdena(true);
                        player.sendMessage(new CustomMessage("usercommandhandlers.AutoLootAdena", player, new Object[0]));
                    } else if (((String)object[1]).equalsIgnoreCase("of")) {
                        player.setAutoLoot(false);
                        player.setAutoLootAdena(false);
                        player.sendMessage(new CustomMessage("usercommandhandlers.AutoLootOff", player, new Object[0]));
                    } else if (((String)object[1]).equalsIgnoreCase("herbon") && Config.AUTO_LOOT_HERBS) {
                        player.setAutoLootHerbs(true);
                        player.sendMessage(new CustomMessage("usercommandhandlers.AutoLootHerbs", player, new Object[0]));
                    } else if (((String)object[1]).equalsIgnoreCase("herbof") && Config.AUTO_LOOT_HERBS) {
                        player.setAutoLootHerbs(false);
                        player.sendMessage(new CustomMessage("usercommandhandlers.AutoLootHerbsOff", player, new Object[0]));
                    }
                }
            }
            object = new NpcHtmlMessage(5);
            ((NpcHtmlMessage)object).setFile("command/cfg.htm");
            ((NpcHtmlMessage)object).replace("%dli%", player.getVarB("DroplistIcons") ? "On" : "Off");
            ((NpcHtmlMessage)object).replace("%noe%", player.getVarB("NoExp") ? "On" : "Off");
            ((NpcHtmlMessage)object).replace("%pvpa%", Config.SERVICES_PK_ANNOUNCE > 0 ? (player.getVarB("PvPAnnounce") ? "On" : "Off") : "N/A");
            ((NpcHtmlMessage)object).replace("%notraders%", player.getVarB("notraders") ? "On" : "Off");
            ((NpcHtmlMessage)object).replace("%noShift%", player.getVarB("noShift") ? "On" : "Off");
            ((NpcHtmlMessage)object).replace("%noCarrier%", Config.SERVICES_ENABLE_NO_CARRIER ? (player.getVarB("noCarrier") ? player.getVar("noCarrier") : "0") : "N/A");
            ((NpcHtmlMessage)object).replace("%noTpRb%", Config.ALT_RAID_BOSS_SPAWN_AND_TELEPORT ? (player.getVarB("noaskboss@") ? "On" : "Off") : "N/A");
            if (player.isAutoLootEnabled()) {
                ((NpcHtmlMessage)object).replace("%autoloot%", "All");
            } else if (player.isAutoLootAdenaEnabled()) {
                ((NpcHtmlMessage)object).replace("%autoloot%", "Adena");
            } else {
                ((NpcHtmlMessage)object).replace("%autoloot%", "Off");
            }
            if (player.isAutoLootHerbsEnabled()) {
                ((NpcHtmlMessage)object).replace("%autoloot_herb%", "On");
            } else {
                ((NpcHtmlMessage)object).replace("%autoloot_herb%", "Off");
            }
            if (player.getLangId() == 0) {
                ((NpcHtmlMessage)object).replace("%lang%", "En");
            } else if (player.getLangId() == 1) {
                ((NpcHtmlMessage)object).replace("%lang%", "Ru");
            } else {
                ((NpcHtmlMessage)object).replace("%lang%", "Unk");
            }
            if (player.getHWIDLock() != null && player.getNetConnection() != null && player.getNetConnection().getHwid() != null && !player.getNetConnection().getHwid().isEmpty()) {
                ((NpcHtmlMessage)object).replace("%hwidlock%", "On");
            } else {
                ((NpcHtmlMessage)object).replace("%hwidlock%", "Off");
            }
            if (player.getIPLock() != null && player.getNetConnection() != null && player.getNetConnection().getIpAddr() != null && !player.getNetConnection().getIpAddr().isEmpty()) {
                ((NpcHtmlMessage)object).replace("%iplock%", "On");
            } else {
                ((NpcHtmlMessage)object).replace("%iplock%", "Off");
            }
            if (player.buffAnimRange() < 0) {
                ((NpcHtmlMessage)object).replace("%buffAnimRange%", "Off");
            } else if (player.buffAnimRange() == 0) {
                if (player.isLangRus()) {
                    ((NpcHtmlMessage)object).replace("%buffAnimRange%", "\u0421\u0432\u043e\u0438");
                } else {
                    ((NpcHtmlMessage)object).replace("%buffAnimRange%", "Self");
                }
            } else {
                ((NpcHtmlMessage)object).replace("%buffAnimRange%", String.valueOf(player.buffAnimRange() / 15));
            }
            if (player.isNoShotsAnim()) {
                ((NpcHtmlMessage)object).replace("%shotsAnim%", "Off");
            } else {
                ((NpcHtmlMessage)object).replace("%shotsAnim%", "On");
            }
            String string3 = player.getVar("blockparty@");
            if (string3 == null) {
                ((NpcHtmlMessage)object).replace("%blockparty%", "Off");
            } else if (string3.equals("on")) {
                ((NpcHtmlMessage)object).replace("%blockparty%", "On");
            }
            String string4 = player.getVar("blocktrade@");
            if (string4 == null) {
                ((NpcHtmlMessage)object).replace("%blocktrade%", "Off");
            } else if (string4.equals("on")) {
                ((NpcHtmlMessage)object).replace("%blocktrade%", "On");
            }
            String string5 = player.getVar("blockpvpevent@");
            if (string5 == null) {
                ((NpcHtmlMessage)object).replace("%blockpvpevent%", "Off");
            } else if (string5.equals("on")) {
                ((NpcHtmlMessage)object).replace("%blockpvpevent%", "On");
            }
            String string6 = player.getVar("translit");
            if (string6 == null) {
                ((NpcHtmlMessage)object).replace("%translit%", "Off");
            } else if (string6.equals("tl")) {
                ((NpcHtmlMessage)object).replace("%translit%", "On");
            } else {
                ((NpcHtmlMessage)object).replace("%translit%", "Lt");
            }
            player.sendPacket((IStaticPacket)object);
            return true;
        }
        if (string.equalsIgnoreCase(this.o[2])) {
            long l;
            String string7 = player.getVar("LastPwdChng");
            boolean bl = true;
            if (string7 != null && !string7.isEmpty() && (l = Long.parseLong(string7) * 1000L) + 3600000L > System.currentTimeMillis()) {
                player.sendMessage(new CustomMessage("usercommandhandlers.CantChangePasswordSoFast", player, new Object[0]));
                bl = false;
            }
            if (bl && !string2.isEmpty()) {
                Matcher matcher = a.matcher(string2);
                if (matcher.find() && matcher.groupCount() == 2) {
                    String string8 = matcher.group(1);
                    String string9 = matcher.group(2);
                    AuthServerCommunication.getInstance().sendPacket(new IGPwdCng(player, string8, string9));
                    return true;
                }
                player.sendMessage(new CustomMessage("usercommandhandlers.PasswordNotMet", player, new Object[0]));
            }
            NpcHtmlMessage npcHtmlMessage2 = new NpcHtmlMessage(5);
            npcHtmlMessage2.setFile("command/passchg.htm");
            player.sendPacket((IStaticPacket)npcHtmlMessage2);
            return true;
        }
        if (!string.equalsIgnoreCase(this.o[3])) return true;
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        npcHtmlMessage.setFile("command/repair.htm");
        if (string2.isEmpty()) {
            CharSequence charSequence;
            ArrayList<Pair> arrayList;
            block150: {
                arrayList = new ArrayList<Pair>();
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;
                try {
                    connection = DatabaseFactory.getInstance().getConnection();
                    preparedStatement = connection.prepareStatement("SELECT `obj_Id`, `char_name` FROM `characters` WHERE `account_name` = ? AND `online` = 0");
                    preparedStatement.setString(1, player.getAccountName());
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        charSequence = resultSet.getString("char_name");
                        int n = resultSet.getInt("obj_Id");
                        arrayList.add(Pair.of((Object)n, (Object)charSequence));
                    }
                }
                catch (Exception exception) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    break block150;
                    catch (Throwable throwable) {
                        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                        throw throwable;
                    }
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
            charSequence = new StringBuilder();
            for (Pair pair : arrayList) {
                String string10 = (String)pair.getRight();
                int n = (Integer)pair.getLeft();
                boolean bl = false;
                String string11 = CharacterVariablesDAO.getInstance().getVar(n, "jailed");
                if (!StringUtils.isBlank((CharSequence)string11)) {
                    int n2 = string11.indexOf(59);
                    boolean bl2 = bl = n2 > 0 && Long.parseLong(string11.substring(0, n2)) - System.currentTimeMillis() > 0L;
                }
                if (!bl) {
                    ((StringBuilder)charSequence).append("<a action=\"bypass -h user_repair " + n + "\">" + string10 + "</a><br1>");
                    continue;
                }
                ((StringBuilder)charSequence).append("<font color=\"AAAAAA\">" + string10 + "</font><br1>");
            }
            npcHtmlMessage.replace("%repair%", ((StringBuilder)charSequence).toString());
        } else {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            CallableStatement callableStatement = null;
            ResultSet resultSet = null;
            try {
                int n;
                block151: {
                    boolean bl;
                    n = Integer.parseInt(string2);
                    String string12 = CharacterVariablesDAO.getInstance().getVar(n, "jailed");
                    if (string12 != null && System.currentTimeMillis() / 1000L < (long)(Integer.parseInt(string12) + 60)) {
                        player.sendMessage(new CustomMessage("usercommandhandlers.CharNotFound", player, new Object[0]));
                        boolean bl3 = true;
                        DbUtils.closeQuietly(connection);
                        return bl3;
                    }
                    connection = DatabaseFactory.getInstance().getConnection();
                    try {
                        preparedStatement = connection.prepareStatement("SELECT * FROM `characters` WHERE `account_name` = ? AND `obj_Id` = ? AND `online` = 0");
                        preparedStatement.setString(1, player.getAccountName());
                        preparedStatement.setInt(2, n);
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) break block151;
                        player.sendMessage(new CustomMessage("usercommandhandlers.CharNotFound", player, new Object[0]));
                        bl = true;
                    }
                    catch (Throwable throwable) {
                        DbUtils.close(preparedStatement, resultSet);
                        throw throwable;
                    }
                    DbUtils.close(preparedStatement, resultSet);
                    DbUtils.closeQuietly(connection);
                    return bl;
                }
                DbUtils.close(preparedStatement, resultSet);
                if (World.getPlayer(n) != null) {
                    player.sendMessage(new CustomMessage("usercommandhandlers.CharacterOnline", player, new Object[0]));
                    boolean bl = true;
                    DbUtils.closeQuietly(connection);
                    return bl;
                }
                try {
                    callableStatement = connection.prepareCall(bY);
                    callableStatement.setInt(1, n);
                    callableStatement.execute();
                }
                finally {
                    DbUtils.close(callableStatement);
                }
                npcHtmlMessage.replace("%repair%", "Character successfully repaired.");
                player.sendMessage(new CustomMessage("usercommandhandlers.CharacterRepaired", player, new Object[0]));
                DbUtils.closeQuietly(connection);
            }
            catch (Exception exception) {
                npcHtmlMessage.replace("%repair%", "Character reparation failed.");
                exception.printStackTrace();
            }
            finally {
                DbUtils.closeQuietly(connection);
            }
        }
        player.sendPacket((IStaticPacket)npcHtmlMessage);
        return true;
    }

    @Override
    public String[] getVoicedCommandList() {
        return this.o;
    }
}
