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
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.actor.instances.player.Bonus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountBonusDAO {
    private static final Logger ar = LoggerFactory.getLogger(AccountBonusDAO.class);
    private static final AccountBonusDAO a = new AccountBonusDAO();
    private static final String aU = "SELECT    `expireTime` AS `expireTime`,    `rateXp` AS `rateXp`,    `rateSp` AS `rateSp`,    `rateRaidXp` AS `rateRaidXp`,    `rateRaidSp` AS `rateRaidSp`,    `questRewardRate` AS `questRewardRate`,    `questRewardAdenaRate` AS `questRewardAdenaRate`,    `questDropRate` AS `questDropRate`,    `dropAdena` AS `dropAdena`,    `dropItems` AS `dropItems`,    `dropSealStones` AS `dropSealStones`,    `dropRaidItems` AS `dropRaidItems`,    `dropSpoil` AS `dropSpoil`,    `enchantItemBonus` AS `enchantItemBonus`,    `enchantSkillBonus` AS `enchantSkillBonus`,    `hwidsLimit` AS `hwidsLimit`FROM     `accounts_bonuses`WHERE     `account` = ?";
    private static final String aV = "REPLACE LOW_PRIORITY INTO `accounts_bonuses` (    `account`,    `expireTime`,    `rateXp`,    `rateSp`,    `rateRaidXp`,    `rateRaidSp`,    `questRewardRate`,    `questRewardAdenaRate`,    `questDropRate`,    `dropAdena`,    `dropItems`,    `dropSealStones`,    `dropRaidItems`,    `dropSpoil`,    `enchantItemBonus`,    `enchantSkillBonus`,    `hwidsLimit`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String aW = "DELETE FROM `accounts_bonuses` WHERE `account` = ?";

    public static AccountBonusDAO getInstance() {
        return a;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void load(String string, Bonus bonus) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block4: {
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(aU);
                preparedStatement.setString(1, string);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                bonus.setBonusExpire(resultSet.getLong("expireTime"));
                bonus.setRateXp(resultSet.getFloat("rateXp"));
                bonus.setRateSp(resultSet.getFloat("rateSp"));
                bonus.setRateRaidXp(resultSet.getFloat("rateRaidXp"));
                bonus.setRateRaidSp(resultSet.getFloat("rateRaidSp"));
                bonus.setQuestRewardRate(resultSet.getFloat("questRewardRate"));
                bonus.setQuestRewardAdenaRate(resultSet.getFloat("questRewardAdenaRate"));
                bonus.setQuestDropRate(resultSet.getFloat("questDropRate"));
                bonus.setDropAdena(resultSet.getFloat("dropAdena"));
                bonus.setDropItems(resultSet.getFloat("dropItems"));
                bonus.setDropSealStones(resultSet.getFloat("dropSealStones"));
                bonus.setDropRaidItems(resultSet.getFloat("dropRaidItems"));
                bonus.setDropSpoil(resultSet.getFloat("dropSpoil"));
                bonus.setEnchantItem(resultSet.getFloat("enchantItemBonus"));
                bonus.setEnchantSkill(resultSet.getFloat("enchantSkillBonus"));
                bonus.setHwidsLimit(resultSet.getInt("hwidsLimit"));
            }
            catch (SQLException sQLException) {
                try {
                    ar.error("Can't load account bonus for account \"" + string + "\"", (Throwable)sQLException);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    public Bonus load(String string) {
        Bonus bonus = new Bonus();
        this.load(string, bonus);
        return bonus;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void store(String string, Bonus bonus) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(aV);
            preparedStatement.setString(1, string);
            preparedStatement.setLong(2, bonus.getBonusExpire());
            preparedStatement.setFloat(3, bonus.getRateXp());
            preparedStatement.setFloat(4, bonus.getRateSp());
            preparedStatement.setFloat(5, bonus.getRateRaidXp());
            preparedStatement.setFloat(6, bonus.getRateRaidSp());
            preparedStatement.setFloat(7, bonus.getQuestRewardRate());
            preparedStatement.setFloat(8, bonus.getQuestRewardAdenaRate());
            preparedStatement.setFloat(9, bonus.getQuestDropRate());
            preparedStatement.setFloat(10, bonus.getDropAdena());
            preparedStatement.setFloat(11, bonus.getDropItems());
            preparedStatement.setFloat(12, bonus.getDropSealStones());
            preparedStatement.setFloat(13, bonus.getDropRaidItems());
            preparedStatement.setFloat(14, bonus.getDropSpoil());
            preparedStatement.setFloat(15, bonus.getEnchantItemMul());
            preparedStatement.setFloat(16, bonus.getEnchantSkillMul());
            preparedStatement.setInt(17, bonus.getHwidsLimit());
            preparedStatement.executeUpdate();
        }
        catch (SQLException sQLException) {
            try {
                ar.error("Can't store account bonus for account \"" + string + "\"", (Throwable)sQLException);
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
    public void delete(String string) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(aW);
            preparedStatement.setString(1, string);
            preparedStatement.executeUpdate();
        }
        catch (SQLException sQLException) {
            try {
                ar.error("Can't store account bonus for account \"" + string + "\"", (Throwable)sQLException);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }
}
