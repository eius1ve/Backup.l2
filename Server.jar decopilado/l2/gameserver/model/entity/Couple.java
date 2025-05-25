/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.instancemanager.CoupleManager;
import l2.gameserver.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Couple {
    private static final Logger bP = LoggerFactory.getLogger(Couple.class);
    private int _id = 0;
    private int lb = 0;
    private int lc = 0;
    private boolean cg = false;
    private long bW;
    private long bX;
    private boolean cV;

    public Couple(int n) {
        this._id = n;
    }

    public Couple(Player player, Player player2) {
        long l;
        this._id = IdFactory.getInstance().getNextId();
        this.lb = player.getObjectId();
        this.lc = player2.getObjectId();
        this.bW = l = System.currentTimeMillis();
        this.bX = l;
        player.setCoupleId(this._id);
        player.setPartnerId(this.lc);
        player2.setCoupleId(this._id);
        player2.setPartnerId(this.lb);
    }

    public void marry() {
        this.bX = System.currentTimeMillis();
        this.cg = true;
        this.setChanged(true);
    }

    public void divorce() {
        CoupleManager.getInstance().getCouples().remove(this);
        CoupleManager.getInstance().getDeletedCouples().add(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void store(Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("REPLACE INTO `couples` (`id`, `player1Id`, `player2Id`, `maried`, `affiancedDate`, `weddingDate`) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, this._id);
            preparedStatement.setInt(2, this.lb);
            preparedStatement.setInt(3, this.lc);
            preparedStatement.setBoolean(4, this.cg);
            preparedStatement.setLong(5, this.bW);
            preparedStatement.setLong(6, this.bX);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            bP.error("", (Throwable)exception);
        }
        finally {
            DbUtils.closeQuietly(preparedStatement);
        }
    }

    public final int getId() {
        return this._id;
    }

    public final int getPlayer1Id() {
        return this.lb;
    }

    public final int getPlayer2Id() {
        return this.lc;
    }

    public final boolean getMaried() {
        return this.cg;
    }

    public final long getAffiancedDate() {
        return this.bW;
    }

    public final long getWeddingDate() {
        return this.bX;
    }

    public void setPlayer1Id(int n) {
        this.lb = n;
    }

    public void setPlayer2Id(int n) {
        this.lc = n;
    }

    public void setMaried(boolean bl) {
        this.cg = bl;
    }

    public void setAffiancedDate(long l) {
        this.bW = l;
    }

    public void setWeddingDate(long l) {
        this.bX = l;
    }

    public boolean isChanged() {
        return this.cV;
    }

    public void setChanged(boolean bl) {
        this.cV = bl;
    }
}
