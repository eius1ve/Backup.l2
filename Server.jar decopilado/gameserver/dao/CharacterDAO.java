/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import l2.commons.dbutils.DbUtils;
import l2.commons.listener.ListenerList;
import l2.gameserver.GameServer;
import l2.gameserver.dao.CharacterVariablesDAO;
import l2.gameserver.dao.ItemsDAO;
import l2.gameserver.data.xml.holder.ClassLevelGainHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.instancemanager.RaidBossSpawnManager;
import l2.gameserver.listener.game.OnCharacterDeleteListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.tables.SkillTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CharacterDAO {
    private static final Logger ay = LoggerFactory.getLogger(CharacterDAO.class);
    private static final CharacterDAO a = new CharacterDAO();
    private static final String bc = "INSERT INTO `characters` (    `account_name`,  `obj_Id`, `char_name`, `face`, `hairStyle`, `hairColor`, `sex`, `karma`,    `pvpkills`, `pkkills`, `clanid`, `createtime`, `deletetime`, `title`, `accesslevel`,  `online`,    `leaveclan`, `deleteclan`, `nochannel`, `pledge_type`, `pledge_rank`, `lvl_joined_academy`, `apprentice`,`base_class_id` ) VALUES (     ?, ?, ?, ?, ?, ?, ?, ?,     ?, ?, ?, ?, ?, ?, ?, ?,     ?, ?, ?, ?, ?, ?, ?, ? )";
    private static final String bd = "INSERT INTO `character_subclasses` (    `char_obj_id`, `class_id`, `exp`, `sp`, `curHp`, `curMp`, `curCp`,     `maxHp`, `maxMp`, `maxCp`, `level`, `active`, `isBase`, `death_penalty`) VALUES (    ?, ?, ?, ?, ?, ?, ?,     ?, ?, ?, ?, ?, ?, ? )";
    private CharacterDeleteListenerList a = new CharacterDeleteListenerList();

    public static CharacterDAO getInstance() {
        return a;
    }

    public CharacterDeleteListenerList getCharacterDeleteListenerList() {
        return this.a;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void deleteCharacterDataByObjId(int n) {
        if (n < 0) {
            return;
        }
        RaidBossSpawnManager.getInstance().deletePoints(n);
        Collection<Integer> collection = ItemsDAO.getInstance().loadItemObjectIdsByOwner(n);
        for (Integer object2 : collection) {
            int n2 = object2;
            boolean bl = false;
            if (bl) continue;
            ItemsDAO.getInstance().delete(n2);
        }
        Object object3 = null;
        Object var4_5 = null;
        try {
            object3 = DatabaseFactory.getInstance().getConnection();
            PreparedStatement preparedStatement = object3.prepareStatement("DELETE FROM `characters` WHERE `obj_Id`=?");
            preparedStatement.setInt(1, n);
            preparedStatement.executeUpdate();
            DbUtils.closeQuietly(preparedStatement);
            PreparedStatement preparedStatement2 = object3.prepareStatement("DELETE FROM `character_subclasses` WHERE `char_obj_id`=?");
            preparedStatement2.setInt(1, n);
            preparedStatement2.executeUpdate();
            DbUtils.closeQuietly(preparedStatement2);
            PreparedStatement preparedStatement3 = object3.prepareStatement("DELETE FROM `character_blocklist` WHERE `obj_Id`=?");
            preparedStatement3.setInt(1, n);
            preparedStatement3.executeUpdate();
            DbUtils.closeQuietly(preparedStatement3);
            PreparedStatement preparedStatement4 = object3.prepareStatement("DELETE FROM `character_effects_save` WHERE `object_id`=?");
            preparedStatement4.setInt(1, n);
            preparedStatement4.executeUpdate();
            DbUtils.closeQuietly(preparedStatement4);
            PreparedStatement preparedStatement5 = object3.prepareStatement("DELETE FROM `character_friends` WHERE `char_id`=? OR `friend_id`=?");
            preparedStatement5.setInt(1, n);
            preparedStatement5.setInt(2, n);
            preparedStatement5.executeUpdate();
            DbUtils.closeQuietly(preparedStatement5);
            PreparedStatement preparedStatement6 = object3.prepareStatement("DELETE FROM `character_group_reuse` WHERE `object_id`=?");
            preparedStatement6.setInt(1, n);
            preparedStatement6.executeUpdate();
            DbUtils.closeQuietly(preparedStatement6);
            PreparedStatement preparedStatement7 = object3.prepareStatement("DELETE FROM `character_hennas` WHERE `char_obj_id`=?");
            preparedStatement7.setInt(1, n);
            preparedStatement7.executeUpdate();
            DbUtils.closeQuietly(preparedStatement7);
            PreparedStatement preparedStatement8 = object3.prepareStatement("DELETE FROM `character_macroses` WHERE `char_obj_id`=?");
            preparedStatement8.setInt(1, n);
            preparedStatement8.executeUpdate();
            DbUtils.closeQuietly(preparedStatement8);
            PreparedStatement preparedStatement9 = object3.prepareStatement("DELETE FROM `character_post_friends` WHERE `object_id`=? OR `post_friend`=?");
            preparedStatement9.setInt(1, n);
            preparedStatement9.setInt(2, n);
            preparedStatement9.executeUpdate();
            DbUtils.closeQuietly(preparedStatement9);
            PreparedStatement preparedStatement10 = object3.prepareStatement("DELETE FROM `character_quests` WHERE `char_id`=?");
            preparedStatement10.setInt(1, n);
            preparedStatement10.executeUpdate();
            DbUtils.closeQuietly(preparedStatement10);
            PreparedStatement preparedStatement11 = object3.prepareStatement("DELETE FROM `character_recipebook` WHERE `char_id`=?");
            preparedStatement11.setInt(1, n);
            preparedStatement11.executeUpdate();
            DbUtils.closeQuietly(preparedStatement11);
            PreparedStatement preparedStatement12 = object3.prepareStatement("DELETE FROM `character_recommends` WHERE `objId`=? OR `targetId`=?");
            preparedStatement12.setInt(1, n);
            preparedStatement12.setInt(2, n);
            preparedStatement12.executeUpdate();
            DbUtils.closeQuietly(preparedStatement12);
            PreparedStatement preparedStatement13 = object3.prepareStatement("DELETE FROM `character_shortcuts` WHERE `object_id`=?");
            preparedStatement13.setInt(1, n);
            preparedStatement13.executeUpdate();
            DbUtils.closeQuietly(preparedStatement13);
            PreparedStatement preparedStatement14 = object3.prepareStatement("DELETE FROM `character_skills` WHERE `char_obj_id`=?");
            preparedStatement14.setInt(1, n);
            preparedStatement14.executeUpdate();
            DbUtils.closeQuietly(preparedStatement14);
            PreparedStatement preparedStatement15 = object3.prepareStatement("DELETE FROM `character_skills_save` WHERE `char_obj_id`=?");
            preparedStatement15.setInt(1, n);
            preparedStatement15.executeUpdate();
            DbUtils.closeQuietly(preparedStatement15);
            PreparedStatement preparedStatement16 = object3.prepareStatement("DELETE FROM `character_onedayreward` WHERE `player_object_id`=?");
            preparedStatement16.setInt(1, n);
            preparedStatement16.executeUpdate();
            DbUtils.closeQuietly(preparedStatement16);
            PreparedStatement preparedStatement17 = object3.prepareStatement("DELETE FROM `character_tp_bookmarks` WHERE `object_id`=?");
            preparedStatement17.setInt(1, n);
            preparedStatement17.executeUpdate();
            DbUtils.closeQuietly(preparedStatement17);
            NoblesController.getInstance().deleteNoble((Connection)object3, n);
            CharacterVariablesDAO.getInstance().deleteVars0((Connection)object3, n);
            this.getCharacterDeleteListenerList().onCharacterDelete(n);
        }
        catch (SQLException sQLException) {
            ay.error("Can't delete character", (Throwable)sQLException);
        }
        finally {
            DbUtils.closeQuietly((Connection)object3);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean insert(Player player) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(bc);
            preparedStatement.setString(1, player.getAccountName());
            preparedStatement.setInt(2, player.getObjectId());
            preparedStatement.setString(3, player.getName());
            preparedStatement.setInt(4, player.getFace());
            preparedStatement.setInt(5, player.getHairStyle());
            preparedStatement.setInt(6, player.getHairColor());
            preparedStatement.setInt(7, player.getSex());
            preparedStatement.setInt(8, player.getKarma());
            preparedStatement.setInt(9, player.getPvpKills());
            preparedStatement.setInt(10, player.getPkKills());
            preparedStatement.setInt(11, player.getClanId());
            preparedStatement.setLong(12, player.getCreateTime() / 1000L);
            preparedStatement.setInt(13, player.getDeleteTimer());
            preparedStatement.setString(14, player.getTitle());
            preparedStatement.setInt(15, player.getAccessLevel());
            preparedStatement.setInt(16, player.isOnline() ? 1 : 0);
            preparedStatement.setLong(17, player.getLeaveClanTime() / 1000L);
            preparedStatement.setLong(18, player.getDeleteClanTime() / 1000L);
            preparedStatement.setLong(19, player.getNoChannel() > 0L ? player.getNoChannel() / 1000L : player.getNoChannel());
            preparedStatement.setInt(20, player.getPledgeType());
            preparedStatement.setInt(21, player.getPowerGrade());
            preparedStatement.setInt(22, player.getLvlJoinedAcademy());
            preparedStatement.setInt(23, player.getApprentice());
            preparedStatement.setInt(24, player.getBaseClassId());
            preparedStatement.executeUpdate();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement(bd);
            preparedStatement.setInt(1, player.getObjectId());
            preparedStatement.setInt(2, player.getTemplate().classId.getId());
            preparedStatement.setInt(3, 0);
            preparedStatement.setInt(4, 0);
            preparedStatement.setDouble(5, ClassLevelGainHolder.getInstance().getHp(player.getClassId(), player.getLevel()));
            preparedStatement.setDouble(6, ClassLevelGainHolder.getInstance().getMp(player.getClassId(), player.getLevel()));
            preparedStatement.setDouble(7, ClassLevelGainHolder.getInstance().getCp(player.getClassId(), player.getLevel()));
            preparedStatement.setDouble(8, ClassLevelGainHolder.getInstance().getHp(player.getClassId(), player.getLevel()));
            preparedStatement.setDouble(9, ClassLevelGainHolder.getInstance().getMp(player.getClassId(), player.getLevel()));
            preparedStatement.setDouble(10, ClassLevelGainHolder.getInstance().getCp(player.getClassId(), player.getLevel()));
            preparedStatement.setInt(11, 1);
            preparedStatement.setInt(12, 1);
            preparedStatement.setInt(13, 1);
            preparedStatement.setInt(14, 0);
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            boolean bl;
            try {
                ay.error("Can't store character", (Throwable)exception);
                bl = false;
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
            return bl;
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getObjectIdByName(String string) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        int n;
        block4: {
            n = 0;
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT `obj_Id` FROM `characters` WHERE `char_name`=?");
                preparedStatement.setString(1, string);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                n = resultSet.getInt(1);
            }
            catch (Exception exception) {
                try {
                    ay.error("Can't get character object id by name" + exception, (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getNameByObjectId(int n) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        String string;
        block4: {
            string = "";
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT `char_name` FROM `characters` WHERE `obj_Id`=?");
                preparedStatement.setInt(1, n);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                string = resultSet.getString(1);
            }
            catch (Exception exception) {
                try {
                    ay.error("Can't get char name by id" + exception, (Throwable)exception);
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int accountCharNumber(String string) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        int n;
        block4: {
            n = 0;
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT COUNT(`char_name`) FROM `characters` WHERE `account_name`=?");
                preparedStatement.setString(1, string);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                n = resultSet.getInt(1);
            }
            catch (Exception exception) {
                try {
                    ay.error("Can't get amount of the account characters", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateBase(int n, int n2) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE characters SET base_class_id = ? WHERE obj_Id = ?");
            preparedStatement.setInt(1, n2);
            preparedStatement.setInt(2, n);
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            try {
                ay.error("CharacterDAO.updateBase(int,int):", (Throwable)exception);
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
    public List<Skill> getCharacterSkills(int n, int n2) {
        LinkedList<Skill> linkedList = new LinkedList<Skill>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `skill_id`,`skill_level` FROM `character_skills` WHERE `char_obj_id`=? AND `class_index`=?");
            preparedStatement.setInt(1, n);
            preparedStatement.setInt(2, n2);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int n3 = resultSet.getInt("skill_id");
                int n4 = resultSet.getInt("skill_level");
                Skill skill = SkillTable.getInstance().getInfo(n3, n4);
                if (skill == null) continue;
                linkedList.add(skill);
            }
        }
        catch (Exception exception) {
            try {
                ay.warn("Could not restore skills for player objId: " + n);
                ay.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<Integer, String> listCharactersByAccountName(String string) {
        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<Integer, String>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `obj_Id`, `char_name` FROM `characters` WHERE `account_name`= ?");
            preparedStatement.setString(1, string);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer n = resultSet.getInt("obj_Id");
                String string2 = resultSet.getString("char_name");
                linkedHashMap.put(n, string2);
            }
        }
        catch (SQLException sQLException) {
            try {
                ay.error("Can't list account characters", (Throwable)sQLException);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(resultSet, preparedStatement, connection);
                throw throwable;
            }
            DbUtils.closeQuietly(resultSet, preparedStatement, connection);
        }
        DbUtils.closeQuietly(resultSet, preparedStatement, connection);
        return linkedHashMap;
    }

    public class CharacterDeleteListenerList
    extends ListenerList<GameServer> {
        public void onCharacterDelete(int n) {
            this.forEachListener(OnCharacterDeleteListener.class, onCharacterDeleteListener -> {
                try {
                    onCharacterDeleteListener.onCharacterDelete(n);
                }
                catch (Exception exception) {
                    ay.warn("Character delete listener", (Throwable)exception);
                }
            });
        }
    }
}
