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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import l2.commons.dao.JdbcDAO;
import l2.commons.dao.JdbcEntityState;
import l2.commons.dao.JdbcEntityStats;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.dao.ItemsDAO;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.mail.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MailDAO
implements JdbcDAO<Integer, Mail> {
    private static final Logger aK = LoggerFactory.getLogger(MailDAO.class);
    private static final String bv = "SELECT sender_id, sender_name, receiver_id, receiver_name, expire_time, topic, body, price, type, unread FROM mail WHERE message_id = ?";
    private static final String bw = "INSERT INTO mail(sender_id, sender_name, receiver_id, receiver_name, expire_time, topic, body, price, type, unread) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String bx = "UPDATE mail SET sender_id = ?, sender_name = ?, receiver_id = ?, receiver_name = ?, expire_time = ?, topic = ?, body = ?, price = ?, type = ?, unread = ? WHERE message_id = ?";
    private static final String by = "DELETE FROM mail WHERE message_id = ?";
    private static final String bz = "SELECT message_id FROM mail WHERE expire_time <= ?";
    private static final String bA = "SELECT message_id FROM character_mail WHERE char_id = ? AND is_sender = ?";
    private static final String bB = "INSERT INTO character_mail(char_id, message_id, is_sender) VALUES (?,?,?)";
    private static final String bC = "DELETE FROM character_mail WHERE char_id = ? AND message_id = ? AND is_sender = ?";
    private static final String bD = "SELECT item_id FROM mail_attachments WHERE message_id = ?";
    private static final String bE = "REPLACE INTO mail_attachments(message_id, item_id) VALUES (?,?)";
    private static final String bF = "DELETE FROM mail_attachments WHERE message_id = ?";
    private static final MailDAO a = new MailDAO();
    private final AtomicLong m = new AtomicLong();
    private final AtomicLong n = new AtomicLong();
    private final AtomicLong o = new AtomicLong();
    private final AtomicLong p = new AtomicLong();
    private final Map<Integer, Mail> A = Collections.synchronizedMap(new LinkedHashMap<Integer, Mail>(Config.MAIL_CACHE_SIZE, 0.75f, true){

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Mail> entry) {
            return this.size() > Config.MAIL_CACHE_SIZE;
        }
    });
    private final JdbcEntityStats a = new JdbcEntityStats(){

        @Override
        public long getLoadCount() {
            return MailDAO.this.m.get();
        }

        @Override
        public long getInsertCount() {
            return MailDAO.this.n.get();
        }

        @Override
        public long getUpdateCount() {
            return MailDAO.this.o.get();
        }

        @Override
        public long getDeleteCount() {
            return MailDAO.this.p.get();
        }
    };

    private MailDAO() {
    }

    public static MailDAO getInstance() {
        return a;
    }

    public Map<Integer, Mail> getCache() {
        return this.A;
    }

    @Override
    public JdbcEntityStats getStats() {
        return this.a;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(Mail mail) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(bw, 1);
            preparedStatement.setInt(1, mail.getSenderId());
            preparedStatement.setString(2, mail.getSenderName());
            preparedStatement.setInt(3, mail.getReceiverId());
            preparedStatement.setString(4, mail.getReceiverName());
            preparedStatement.setInt(5, mail.getExpireTime());
            preparedStatement.setString(6, mail.getTopic());
            preparedStatement.setString(7, mail.getBody());
            preparedStatement.setLong(8, mail.getPrice());
            preparedStatement.setInt(9, mail.getType().ordinal());
            preparedStatement.setBoolean(10, mail.isUnread());
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            mail.setMessageId(resultSet.getInt(1));
            if (!mail.getAttachments().isEmpty()) {
                DbUtils.close(preparedStatement);
                preparedStatement = connection.prepareStatement(bE);
                for (ItemInstance itemInstance : mail.getAttachments()) {
                    preparedStatement.setInt(1, mail.getMessageId());
                    preparedStatement.setInt(2, itemInstance.getObjectId());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
            DbUtils.close(preparedStatement);
            if (mail.getType() == Mail.SenderType.NORMAL) {
                preparedStatement = connection.prepareStatement(bB);
                preparedStatement.setInt(1, mail.getSenderId());
                preparedStatement.setInt(2, mail.getMessageId());
                preparedStatement.setBoolean(3, true);
                preparedStatement.execute();
            }
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement(bB);
            preparedStatement.setInt(1, mail.getReceiverId());
            preparedStatement.setInt(2, mail.getMessageId());
            preparedStatement.setBoolean(3, false);
            preparedStatement.execute();
        }
        catch (Throwable throwable) {
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            throw throwable;
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        this.n.incrementAndGet();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Mail a(int n) throws SQLException {
        Mail mail = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(bv);
            preparedStatement.setInt(1, n);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mail = new Mail();
                mail.setMessageId(n);
                mail.setSenderId(resultSet.getInt(1));
                mail.setSenderName(resultSet.getString(2));
                mail.setReceiverId(resultSet.getInt(3));
                mail.setReceiverName(resultSet.getString(4));
                mail.setExpireTime(resultSet.getInt(5));
                mail.setTopic(resultSet.getString(6));
                mail.setBody(resultSet.getString(7));
                mail.setPrice(resultSet.getLong(8));
                mail.setType(Mail.SenderType.VALUES[resultSet.getInt(9)]);
                mail.setUnread(resultSet.getBoolean(10));
                DbUtils.close(preparedStatement, resultSet);
                preparedStatement = connection.prepareStatement(bD);
                preparedStatement.setInt(1, n);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int n2 = resultSet.getInt(1);
                    ItemInstance itemInstance = ItemsDAO.getInstance().load(n2);
                    if (itemInstance == null) continue;
                    mail.addAttachment(itemInstance);
                }
            }
        }
        catch (Throwable throwable) {
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            throw throwable;
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        this.m.incrementAndGet();
        return mail;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void b(Mail mail) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(bx);
            preparedStatement.setInt(1, mail.getSenderId());
            preparedStatement.setString(2, mail.getSenderName());
            preparedStatement.setInt(3, mail.getReceiverId());
            preparedStatement.setString(4, mail.getReceiverName());
            preparedStatement.setInt(5, mail.getExpireTime());
            preparedStatement.setString(6, mail.getTopic());
            preparedStatement.setString(7, mail.getBody());
            preparedStatement.setLong(8, mail.getPrice());
            preparedStatement.setInt(9, mail.getType().ordinal());
            preparedStatement.setBoolean(10, mail.isUnread());
            preparedStatement.setInt(11, mail.getMessageId());
            preparedStatement.execute();
            if (mail.getAttachments().isEmpty()) {
                DbUtils.close(preparedStatement);
                preparedStatement = connection.prepareStatement(bF);
                preparedStatement.setInt(1, mail.getMessageId());
                preparedStatement.execute();
            }
        }
        catch (Throwable throwable) {
            DbUtils.closeQuietly(connection, preparedStatement);
            throw throwable;
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        this.o.incrementAndGet();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void c(Mail mail) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(by);
            preparedStatement.setInt(1, mail.getMessageId());
            preparedStatement.execute();
        }
        catch (Throwable throwable) {
            DbUtils.closeQuietly(connection, preparedStatement);
            throw throwable;
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        this.p.incrementAndGet();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<Mail> a(int n, boolean bl) {
        List<Integer> list = Collections.emptyList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(bA);
            preparedStatement.setInt(1, n);
            preparedStatement.setBoolean(2, bl);
            resultSet = preparedStatement.executeQuery();
            list = new ArrayList<Integer>();
            while (resultSet.next()) {
                list.add(resultSet.getInt(1));
            }
        }
        catch (SQLException sQLException) {
            try {
                aK.error("Error while restore mail of owner : " + n, (Throwable)sQLException);
                list.clear();
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return this.load((Collection<Integer>)list);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean a(int n, int n2, boolean bl) {
        boolean bl2;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(bC);
            preparedStatement.setInt(1, n);
            preparedStatement.setInt(2, n2);
            preparedStatement.setBoolean(3, bl);
            bl2 = preparedStatement.execute();
        }
        catch (SQLException sQLException) {
            boolean bl3;
            try {
                aK.error("Error while deleting mail of owner : " + n, (Throwable)sQLException);
                bl3 = false;
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
            return bl3;
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        return bl2;
    }

    public List<Mail> getReceivedMailByOwnerId(int n) {
        return this.a(n, false);
    }

    public List<Mail> getSentMailByOwnerId(int n) {
        return this.a(n, true);
    }

    public Mail getReceivedMailByMailId(int n, int n2) {
        List<Mail> list = this.a(n, false);
        for (Mail mail : list) {
            if (mail.getMessageId() != n2) continue;
            return mail;
        }
        return null;
    }

    public Mail getSentMailByMailId(int n, int n2) {
        List<Mail> list = this.a(n, true);
        for (Mail mail : list) {
            if (mail.getMessageId() != n2) continue;
            return mail;
        }
        return null;
    }

    public boolean deleteReceivedMailByMailId(int n, int n2) {
        return this.a(n, n2, false);
    }

    public boolean deleteSentMailByMailId(int n, int n2) {
        return this.a(n, n2, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<Mail> getExpiredMail(int n) {
        List<Integer> list = Collections.emptyList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(bz);
            preparedStatement.setInt(1, n);
            resultSet = preparedStatement.executeQuery();
            list = new ArrayList<Integer>();
            while (resultSet.next()) {
                list.add(resultSet.getInt(1));
            }
        }
        catch (SQLException sQLException) {
            try {
                aK.error("Error while restore expired mail!", (Throwable)sQLException);
                list.clear();
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return this.load((Collection<Integer>)list);
    }

    @Override
    public Mail load(Integer n) {
        Mail mail = this.A.get(n);
        if (mail != null) {
            return mail;
        }
        try {
            mail = this.a(n);
            if (mail != null) {
                mail.setJdbcState(JdbcEntityState.STORED);
                this.A.put(mail.getMessageId(), mail);
            }
        }
        catch (SQLException sQLException) {
            aK.error("Error while restoring mail : " + n, (Throwable)sQLException);
            return null;
        }
        return mail;
    }

    @Override
    public List<Mail> load(Collection<Integer> collection) {
        if (collection.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<Mail> arrayList = new ArrayList<Mail>(collection.size());
        for (Integer n : collection) {
            Mail mail = this.load(n);
            if (mail == null) continue;
            arrayList.add(mail);
        }
        return arrayList;
    }

    @Override
    public void save(Mail mail) {
        if (!mail.getJdbcState().isSavable()) {
            return;
        }
        try {
            this.a(mail);
            mail.setJdbcState(JdbcEntityState.STORED);
        }
        catch (SQLException sQLException) {
            aK.error("Error while saving mail!", (Throwable)sQLException);
            return;
        }
        this.A.put(mail.getMessageId(), mail);
    }

    @Override
    public void update(Mail mail) {
        if (!mail.getJdbcState().isUpdatable()) {
            return;
        }
        try {
            this.b(mail);
            mail.setJdbcState(JdbcEntityState.STORED);
        }
        catch (SQLException sQLException) {
            aK.error("Error while updating mail : " + mail.getMessageId(), (Throwable)sQLException);
            return;
        }
        this.A.putIfAbsent(mail.getMessageId(), mail);
    }

    @Override
    public void saveOrUpdate(Mail mail) {
        if (mail.getJdbcState().isSavable()) {
            this.save(mail);
        } else if (mail.getJdbcState().isUpdatable()) {
            this.update(mail);
        }
    }

    @Override
    public void delete(Mail mail) {
        if (!mail.getJdbcState().isDeletable()) {
            return;
        }
        try {
            this.c(mail);
            mail.setJdbcState(JdbcEntityState.DELETED);
        }
        catch (SQLException sQLException) {
            aK.error("Error while deleting mail : " + mail.getMessageId(), (Throwable)sQLException);
            return;
        }
        this.A.remove(mail.getMessageId());
    }
}
