/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.pledge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.RankPrivs;
import l2.gameserver.model.pledge.SubUnit;
import l2.gameserver.network.l2.s2c.NickNameChanged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class UnitMember {
    private static final Logger ct = LoggerFactory.getLogger(UnitMember.class);
    private Player _player;
    private Clan a;
    private String _name;
    private String _title;
    private int fW;
    private int _level;
    private int ga;
    private int gg;
    private int if;
    private int ig;
    private int ii;
    private int pg = -128;

    public UnitMember(Clan clan, String string, String string2, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this.a = clan;
        this.fW = n3;
        this._name = string;
        this._title = string2;
        this._level = n;
        this.ga = n2;
        this.if = n4;
        this.ig = n5;
        this.ii = n6;
        this.gg = n7;
        this.pg = n8;
        if (n5 != 0) {
            RankPrivs rankPrivs = clan.getRankPrivs(n5);
            rankPrivs.setParty(clan.countMembersByRank(n5));
        }
    }

    public UnitMember(Player player) {
        this.fW = player.getObjectId();
        this._player = player;
    }

    public void setPlayerInstance(Player player, boolean bl) {
        Player player2 = this._player = bl ? null : player;
        if (player == null) {
            return;
        }
        this.a = player.getClan();
        this._name = player.getName();
        this._title = player.getTitle();
        this._level = player.getLevel();
        this.ga = player.getClassId().getId();
        this.if = player.getPledgeType();
        this.ig = player.getPowerGrade();
        this.ii = player.getApprentice();
        this.gg = player.getSex();
    }

    public Player getPlayer() {
        return this._player;
    }

    public boolean isOnline() {
        Player player = this.getPlayer();
        return player != null && !player.isInOfflineMode();
    }

    public Clan getClan() {
        Player player = this.getPlayer();
        return player == null ? this.a : player.getClan();
    }

    public int getClassId() {
        Player player = this.getPlayer();
        return player == null ? this.ga : player.getClassId().getId();
    }

    public int getSex() {
        Player player = this.getPlayer();
        return player == null ? this.gg : player.getSex();
    }

    public int getLevel() {
        Player player = this.getPlayer();
        return player == null ? this._level : player.getLevel();
    }

    public String getName() {
        Player player = this.getPlayer();
        return player == null ? this._name : player.getName();
    }

    public int getObjectId() {
        return this.fW;
    }

    public String getTitle() {
        Player player = this.getPlayer();
        return player == null ? this._title : player.getTitle();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setTitle(String string) {
        Player player = this.getPlayer();
        this._title = string;
        if (player != null) {
            player.setTitle(string);
            player.broadcastPacket(new NickNameChanged(player));
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `characters` SET `title`=? WHERE `obj_Id`=?");
            preparedStatement.setString(1, string);
            preparedStatement.setInt(2, this.getObjectId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                ct.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
            return;
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        return;
    }

    public SubUnit getSubUnit() {
        return this.a.getSubUnit(this.if);
    }

    public int getPledgeType() {
        Player player = this.getPlayer();
        return player == null ? this.if : player.getPledgeType();
    }

    public void setPledgeType(int n) {
        Player player = this.getPlayer();
        this.if = n;
        if (player != null) {
            player.setPledgeType(n);
        } else {
            this.bO();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void bO() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `characters` SET `pledge_type`=? WHERE `obj_Id`=?");
            preparedStatement.setInt(1, this.if);
            preparedStatement.setInt(2, this.getObjectId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                ct.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public int getPowerGrade() {
        Player player = this.getPlayer();
        return player == null ? this.ig : player.getPowerGrade();
    }

    public void setPowerGrade(int n) {
        Player player = this.getPlayer();
        int n2 = this.getPowerGrade();
        this.ig = n;
        if (player != null) {
            player.setPowerGrade(n);
        } else {
            this.bP();
        }
        this.f(n2, n);
    }

    private void f(int n, int n2) {
        RankPrivs rankPrivs;
        if (n != 0) {
            rankPrivs = this.getClan().getRankPrivs(n);
            rankPrivs.setParty(this.getClan().countMembersByRank(n));
        }
        if (n2 != 0) {
            rankPrivs = this.getClan().getRankPrivs(n2);
            rankPrivs.setParty(this.getClan().countMembersByRank(n2));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void bP() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `characters` SET `pledge_rank`=? WHERE `obj_Id`=?");
            preparedStatement.setInt(1, this.ig);
            preparedStatement.setInt(2, this.getObjectId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                ct.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    private int getApprentice() {
        Player player = this.getPlayer();
        return player == null ? this.ii : player.getApprentice();
    }

    public void setApprentice(int n) {
        Player player = this.getPlayer();
        this.ii = n;
        if (player != null) {
            player.setApprentice(n);
        } else {
            this.bQ();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void bQ() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `characters` SET `apprentice`=? WHERE `obj_Id`=?");
            preparedStatement.setInt(1, this.ii);
            preparedStatement.setInt(2, this.getObjectId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                ct.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public String getApprenticeName() {
        if (this.getApprentice() != 0 && this.getClan().getAnyMember(this.getApprentice()) != null) {
            return this.getClan().getAnyMember(this.getApprentice()).getName();
        }
        return "";
    }

    public boolean hasApprentice() {
        return this.getApprentice() != 0;
    }

    public int getSponsor() {
        if (this.getPledgeType() != -1) {
            return 0;
        }
        int n = this.getObjectId();
        for (UnitMember unitMember : this.getClan()) {
            if (unitMember.getApprentice() != n) continue;
            return unitMember.getObjectId();
        }
        return 0;
    }

    private String g() {
        int n = this.getSponsor();
        if (n == 0) {
            return "";
        }
        if (this.getClan().getAnyMember(n) != null) {
            return this.getClan().getAnyMember(n).getName();
        }
        return "";
    }

    public boolean hasSponsor() {
        return this.getSponsor() != 0;
    }

    public String getRelatedName() {
        if (this.getPledgeType() == -1) {
            return this.g();
        }
        return this.getApprenticeName();
    }

    public boolean isClanLeader() {
        Player player = this.getPlayer();
        return player == null ? this.pg == 0 : player.isClanLeader();
    }

    public int isSubLeader() {
        for (SubUnit subUnit : this.getClan().getAllSubUnits()) {
            if (subUnit.getLeaderObjectId() != this.getObjectId()) continue;
            return subUnit.getType();
        }
        return 0;
    }

    public void setLeaderOf(int n) {
        this.pg = n;
    }

    public int getLeaderOf() {
        return this.pg;
    }
}
