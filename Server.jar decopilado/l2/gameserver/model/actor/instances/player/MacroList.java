/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.actor.instances.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.Macro;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.MacroUpdateType;
import l2.gameserver.network.l2.s2c.SendMacroList;
import l2.gameserver.utils.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MacroList {
    private static final Logger bM = LoggerFactory.getLogger(MacroList.class);
    private final Player i;
    private final Map<Integer, Macro> aQ = new HashMap<Integer, Macro>();
    private int kj;

    public MacroList(Player player) {
        this.i = player;
        this.kj = 1000;
    }

    public Macro[] getAllMacroses() {
        return this.aQ.values().toArray(new Macro[this.aQ.size()]);
    }

    public Macro getMacro(int n) {
        return this.aQ.get(n - 1);
    }

    public void registerMacro(Macro macro) {
        MacroUpdateType macroUpdateType;
        if (macro.id == 0) {
            macro.id = this.kj++;
            while (this.aQ.get(macro.id) != null) {
                ++this.kj;
                macro.id = macro.id;
            }
            this.aQ.put(macro.id, macro);
            this.a(macro);
            macroUpdateType = MacroUpdateType.ADD;
        } else {
            Macro macro2 = this.aQ.put(macro.id, macro);
            if (macro2 != null) {
                this.b(macro2);
            }
            this.a(macro);
            macroUpdateType = MacroUpdateType.MODIFY;
        }
        this.i.sendPacket((IStaticPacket)new SendMacroList(macroUpdateType, 1, macro));
    }

    public void deleteMacro(int n) {
        Macro macro;
        Macro macro2 = this.aQ.get(n);
        if (macro2 != null) {
            this.b(macro2);
        }
        if ((macro = this.aQ.remove(n)) != null) {
            this.i.sendPacket((IStaticPacket)new SendMacroList(MacroUpdateType.DELETE, 0, macro));
        }
    }

    public void sendAll() {
        Macro[] macroArray = this.getAllMacroses();
        if (macroArray.length == 0) {
            this.i.sendPacket((IStaticPacket)new SendMacroList(MacroUpdateType.LIST, macroArray.length, null));
        } else {
            for (Macro macro : macroArray) {
                this.i.sendPacket((IStaticPacket)new SendMacroList(MacroUpdateType.LIST, macroArray.length, macro));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(Macro macro) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("REPLACE INTO `character_macroses` (`char_obj_id`,`id`,`icon`,`name`,`descr`,`acronym`,`commands`) values(?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, this.i.getObjectId());
            preparedStatement.setInt(2, macro.id);
            preparedStatement.setInt(3, macro.icon);
            preparedStatement.setString(4, macro.name);
            preparedStatement.setString(5, macro.descr);
            preparedStatement.setString(6, macro.acronym);
            StringBuilder stringBuilder = new StringBuilder();
            for (Macro.L2MacroCmd l2MacroCmd : macro.commands) {
                stringBuilder.append(l2MacroCmd.type).append(',');
                stringBuilder.append(l2MacroCmd.d1).append(',');
                stringBuilder.append(l2MacroCmd.d2);
                if (l2MacroCmd.cmd != null && l2MacroCmd.cmd.length() > 0) {
                    stringBuilder.append(',').append(l2MacroCmd.cmd);
                }
                stringBuilder.append(';');
            }
            preparedStatement.setString(7, stringBuilder.toString());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                bM.error("could not store macro: " + macro.toString(), (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void b(Macro macro) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM `character_macroses` WHERE `char_obj_id`=? AND `id`=?");
            preparedStatement.setInt(1, this.i.getObjectId());
            preparedStatement.setInt(2, macro.id);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                bM.error("could not delete macro:", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void restore() {
        this.aQ.clear();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `char_obj_id`, `id`, `icon`, `name`, `descr`, `acronym`, `commands` FROM `character_macroses` WHERE `char_obj_id`=?");
            preparedStatement.setInt(1, this.i.getObjectId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Object object;
                int n = resultSet.getInt("id");
                int n2 = resultSet.getInt("icon");
                String string = Strings.stripSlashes(resultSet.getString("name"));
                String string2 = Strings.stripSlashes(resultSet.getString("descr"));
                String string3 = Strings.stripSlashes(resultSet.getString("acronym"));
                ArrayList<Macro.L2MacroCmd> arrayList = new ArrayList<Macro.L2MacroCmd>();
                StringTokenizer stringTokenizer = new StringTokenizer(resultSet.getString("commands"), ";");
                while (stringTokenizer.hasMoreTokens()) {
                    object = new StringTokenizer(stringTokenizer.nextToken(), ",");
                    int n3 = Integer.parseInt(((StringTokenizer)object).nextToken());
                    int n4 = Integer.parseInt(((StringTokenizer)object).nextToken());
                    int n5 = Integer.parseInt(((StringTokenizer)object).nextToken());
                    String string4 = "";
                    if (((StringTokenizer)object).hasMoreTokens()) {
                        string4 = ((StringTokenizer)object).nextToken();
                    }
                    Macro.L2MacroCmd l2MacroCmd = new Macro.L2MacroCmd(arrayList.size(), n3, n4, n5, string4);
                    arrayList.add(l2MacroCmd);
                }
                object = new Macro(n, n2, string, string2, string3, arrayList.toArray(new Macro.L2MacroCmd[arrayList.size()]));
                this.aQ.put(((Macro)object).id, (Macro)object);
            }
        }
        catch (Exception exception) {
            try {
                bM.error("could not restore shortcuts:", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }
}
