/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.instancemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import l2.commons.dbutils.DbUtils;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.Couple;
import l2.gameserver.network.l2.components.CustomMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoupleManager {
    private static final Logger bo = LoggerFactory.getLogger(CoupleManager.class);
    private static CoupleManager a;
    private List<Couple> av;
    private List<Couple> aw;

    public static CoupleManager getInstance() {
        if (a == null) {
            new CoupleManager();
        }
        return a;
    }

    public CoupleManager() {
        a = this;
        bo.info("Initializing CoupleManager");
        a.load();
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new StoreTask(), 600000L, 600000L);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void load() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM `couples` ORDER BY `id`");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Couple couple = new Couple(resultSet.getInt("id"));
                couple.setPlayer1Id(resultSet.getInt("player1Id"));
                couple.setPlayer2Id(resultSet.getInt("player2Id"));
                couple.setMaried(resultSet.getBoolean("maried"));
                couple.setAffiancedDate(resultSet.getLong("affiancedDate"));
                couple.setWeddingDate(resultSet.getLong("weddingDate"));
                this.getCouples().add(couple);
            }
            bo.info("Loaded: " + this.getCouples().size() + " couples(s)");
        }
        catch (Exception exception) {
            try {
                bo.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    public final Couple getCouple(int n) {
        for (Couple couple : this.getCouples()) {
            if (couple == null || couple.getId() != n) continue;
            return couple;
        }
        return null;
    }

    public void engage(Player player) {
        int n = player.getObjectId();
        for (Couple couple : this.getCouples()) {
            if (couple == null || couple.getPlayer1Id() != n && couple.getPlayer2Id() != n) continue;
            if (couple.getMaried()) {
                player.setMaried(true);
            }
            player.setCoupleId(couple.getId());
            if (couple.getPlayer1Id() == n) {
                player.setPartnerId(couple.getPlayer2Id());
                continue;
            }
            player.setPartnerId(couple.getPlayer1Id());
        }
    }

    public void notifyPartner(Player player) {
        Player player2;
        if (player.getPartnerId() != 0 && (player2 = GameObjectsStorage.getPlayer(player.getPartnerId())) != null) {
            player2.sendMessage(new CustomMessage("l2p.gameserver.instancemanager.CoupleManager.PartnerEntered", player2, new Object[0]));
        }
    }

    public void createCouple(Player player, Player player2) {
        if (player != null && player2 != null && player.getPartnerId() == 0 && player2.getPartnerId() == 0) {
            this.getCouples().add(new Couple(player, player2));
        }
    }

    public final List<Couple> getCouples() {
        if (this.av == null) {
            this.av = new CopyOnWriteArrayList<Couple>();
        }
        return this.av;
    }

    public List<Couple> getDeletedCouples() {
        if (this.aw == null) {
            this.aw = new CopyOnWriteArrayList<Couple>();
        }
        return this.aw;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void store() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            if (this.aw != null && !this.aw.isEmpty()) {
                preparedStatement = connection.prepareStatement("DELETE FROM `couples` WHERE `id` = ?");
                for (Couple couple : this.aw) {
                    preparedStatement.setInt(1, couple.getId());
                    preparedStatement.execute();
                }
                this.aw.clear();
            }
            if (this.av != null && !this.av.isEmpty()) {
                for (Couple couple : this.av) {
                    if (couple == null || !couple.isChanged()) continue;
                    couple.store(connection);
                    couple.setChanged(false);
                }
            }
        }
        catch (Exception exception) {
            bo.error("", (Throwable)exception);
        }
        finally {
            DbUtils.closeQuietly(connection, preparedStatement);
        }
    }

    private class StoreTask
    extends RunnableImpl {
        private StoreTask() {
        }

        @Override
        public void runImpl() throws Exception {
            CoupleManager.this.store();
        }
    }
}
