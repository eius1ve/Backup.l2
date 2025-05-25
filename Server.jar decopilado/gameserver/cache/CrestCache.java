/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntIntHashMap
 *  gnu.trove.TIntObjectHashMap
 *  org.apache.commons.lang3.builder.HashCodeBuilder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.cache;

import gnu.trove.TIntIntHashMap;
import gnu.trove.TIntObjectHashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CrestCache {
    public static final int ALLY_CREST_SIZE = 192;
    public static final int CREST_SIZE = 256;
    public static final int LARGE_CREST_SIZE = 2176;
    private static final Logger aq = LoggerFactory.getLogger(CrestCache.class);
    private static final CrestCache a = new CrestCache();
    private final TIntIntHashMap a;
    private final TIntIntHashMap b;
    private final TIntIntHashMap c;
    private final TIntObjectHashMap<byte[]> c;
    private final TIntObjectHashMap<byte[]> d;
    private final TIntObjectHashMap<byte[]> e;
    private final ReentrantReadWriteLock a = new TIntIntHashMap();
    private final Lock i;
    private final Lock j;

    public static final CrestCache getInstance() {
        return a;
    }

    private CrestCache() {
        this.b = new TIntIntHashMap();
        this.c = new TIntIntHashMap();
        this.c = new TIntObjectHashMap();
        this.d = new TIntObjectHashMap();
        this.e = new TIntObjectHashMap();
        this.a = new ReentrantReadWriteLock();
        this.i = this.a.readLock();
        this.j = this.a.writeLock();
        this.load();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void load() {
        int n = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int n2;
            byte[] byArray;
            int n3;
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `clan_id`, `crest` FROM `clan_data` WHERE `crest` IS NOT NULL");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ++n;
                n3 = resultSet.getInt("clan_id");
                byArray = resultSet.getBytes("crest");
                n2 = CrestCache.a(n3, byArray);
                this.a.put(n3, n2);
                this.c.put(n2, (Object)byArray);
            }
            DbUtils.close(preparedStatement, resultSet);
            preparedStatement = connection.prepareStatement("SELECT `clan_id`, `largecrest` FROM `clan_data` WHERE `largecrest` IS NOT NULL");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ++n;
                n3 = resultSet.getInt("clan_id");
                byArray = resultSet.getBytes("largecrest");
                n2 = CrestCache.a(n3, byArray);
                this.b.put(n3, n2);
                this.d.put(n2, (Object)byArray);
            }
            DbUtils.close(preparedStatement, resultSet);
            preparedStatement = connection.prepareStatement("SELECT `ally_id`, `crest` FROM `ally_data` WHERE `crest` IS NOT NULL");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ++n;
                n3 = resultSet.getInt("ally_id");
                byArray = resultSet.getBytes("crest");
                n2 = CrestCache.a(n3, byArray);
                this.c.put(n3, n2);
                this.e.put(n2, (Object)byArray);
            }
        }
        catch (Exception exception) {
            try {
                aq.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        aq.info("CrestCache: Loaded " + n + " crests");
    }

    private static int a(int n, byte[] byArray) {
        return Math.abs(new HashCodeBuilder(15, 87).append(n).append(byArray).toHashCode());
    }

    public byte[] getPledgeCrest(int n) {
        byte[] byArray = null;
        this.i.lock();
        try {
            byArray = (byte[])this.c.get(n);
        }
        finally {
            this.i.unlock();
        }
        return byArray;
    }

    public byte[] getPledgeCrestLarge(int n) {
        byte[] byArray = null;
        this.i.lock();
        try {
            byArray = (byte[])this.d.get(n);
        }
        finally {
            this.i.unlock();
        }
        return byArray;
    }

    public byte[] getAllyCrest(int n) {
        byte[] byArray = null;
        this.i.lock();
        try {
            byArray = (byte[])this.e.get(n);
        }
        finally {
            this.i.unlock();
        }
        return byArray;
    }

    public int getPledgeCrestId(int n) {
        int n2 = 0;
        this.i.lock();
        try {
            n2 = this.a.get(n);
        }
        finally {
            this.i.unlock();
        }
        return n2;
    }

    public int getPledgeCrestLargeId(int n) {
        int n2 = 0;
        this.i.lock();
        try {
            n2 = this.b.get(n);
        }
        finally {
            this.i.unlock();
        }
        return n2;
    }

    public int getAllyCrestId(int n) {
        int n2 = 0;
        this.i.lock();
        try {
            n2 = this.c.get(n);
        }
        finally {
            this.i.unlock();
        }
        return n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removePledgeCrest(int n) {
        this.j.lock();
        try {
            this.c.remove(this.a.remove(n));
        }
        finally {
            this.j.unlock();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `clan_data` SET `crest`=? WHERE `clan_id`=?");
            preparedStatement.setNull(1, -3);
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                aq.error("", (Throwable)exception);
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
    public void removePledgeCrestLarge(int n) {
        this.j.lock();
        try {
            this.d.remove(this.b.remove(n));
        }
        finally {
            this.j.unlock();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `clan_data` SET `largecrest`=? WHERE `clan_id`=?");
            preparedStatement.setNull(1, -3);
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                aq.error("", (Throwable)exception);
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
    public void removeAllyCrest(int n) {
        this.j.lock();
        try {
            this.e.remove(this.c.remove(n));
        }
        finally {
            this.j.unlock();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `ally_data` SET `crest`=? WHERE `ally_id`=?");
            preparedStatement.setNull(1, -3);
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                aq.error("", (Throwable)exception);
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
    public int savePledgeCrest(int n, byte[] byArray) {
        int n2 = CrestCache.a(n, byArray);
        this.j.lock();
        try {
            this.a.put(n, n2);
            this.c.put(n2, (Object)byArray);
        }
        finally {
            this.j.unlock();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `clan_data` SET `crest`=? WHERE `clan_id`=?");
            preparedStatement.setBytes(1, byArray);
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                aq.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        return n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int savePledgeCrestLarge(int n, byte[] byArray) {
        int n2 = CrestCache.a(n, byArray);
        this.j.lock();
        try {
            this.b.put(n, n2);
            this.d.put(n2, (Object)byArray);
        }
        finally {
            this.j.unlock();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `clan_data` SET `largecrest`=? WHERE `clan_id`=?");
            preparedStatement.setBytes(1, byArray);
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                aq.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        return n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int saveAllyCrest(int n, byte[] byArray) {
        int n2 = CrestCache.a(n, byArray);
        this.j.lock();
        try {
            this.c.put(n, n2);
            this.e.put(n2, (Object)byArray);
        }
        finally {
            this.j.unlock();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `ally_data` SET `crest`=? WHERE `ally_id`=?");
            preparedStatement.setBytes(1, byArray);
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                aq.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        return n2;
    }

    public static boolean isValidCrestData(byte[] byArray) {
        switch (byArray.length) {
            case 192: 
            case 256: 
            case 2176: {
                break;
            }
            default: {
                return false;
            }
        }
        if (byArray[0] != 68 || byArray[1] != 68 || byArray[2] != 83 || byArray[3] != 32 || byArray[84] != 68 || byArray[85] != 88 || byArray[86] != 84 || byArray[87] != 49) {
            return false;
        }
        switch (byArray.length) {
            case 192: {
                if (byArray[12] == 16 && byArray[16] == 8) break;
                return false;
            }
            case 256: {
                if (byArray[12] == 16 && byArray[16] == 16) break;
                return false;
            }
            case 2176: {
                if (byArray[12] == 64 && byArray[16] == 64) break;
                return false;
            }
        }
        return true;
    }
}
