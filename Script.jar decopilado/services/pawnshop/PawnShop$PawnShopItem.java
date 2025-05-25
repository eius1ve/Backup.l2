/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.gameserver.Config
 *  l2.gameserver.GameServer
 *  l2.gameserver.dao.CharacterDAO
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.World
 *  l2.gameserver.templates.item.ItemTemplate
 */
package services.pawnshop;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.GameServer;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.World;
import l2.gameserver.templates.item.ItemTemplate;
import services.pawnshop.PawnShop;

private static class PawnShop.PawnShopItem {
    private final int bGt;
    private final int bGu;
    private final int bGv;
    private final int bGw;
    private final int bGx;
    private final int bGy;
    private final int bGz;
    private final int bGA;
    private final int bGB;
    private final long eB;
    private final AtomicBoolean m;
    private String _ownerName;
    private Skill A;
    private String is;

    private PawnShop.PawnShopItem(long l, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        this.eB = l;
        this.bGt = n;
        this.bGu = n2;
        this.bGv = n3;
        this.bGw = n4;
        this.bGx = n5;
        this.bGy = n6;
        this.bGz = n9;
        this.bGA = n7;
        this.bGB = n8;
        this.A = PawnShop.b(n7, n8);
        this.m = new AtomicBoolean(false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<PawnShop.PawnShopItem> loadItems() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        LinkedList<PawnShop.PawnShopItem> linkedList = new LinkedList<PawnShop.PawnShopItem>();
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            GameServer.getInstance().getDbmsStructureSynchronizer(connection).synchronize(new String[]{"pawnshop"});
            statement = connection.createStatement();
            resultSet = statement.executeQuery("{CALL `lip_ex_PawnShopLoadItems`()}");
            while (resultSet.next()) {
                PawnShop.PawnShopItem pawnShopItem = new PawnShop.PawnShopItem(resultSet.getLong("createdAt"), resultSet.getInt("id"), resultSet.getInt("ownerId"), resultSet.getInt("itemType"), resultSet.getInt("amount"), resultSet.getInt("enchantLevel"), resultSet.getInt("currency"), resultSet.getInt("varOpt1"), resultSet.getInt("varOpt2"), resultSet.getInt("price"));
                linkedList.add(0, pawnShopItem);
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)statement, (ResultSet)resultSet);
        }
        catch (SQLException sQLException) {
            et.error("PawnShop: Can't load items", (Throwable)sQLException);
        }
        finally {
            DbUtils.closeQuietly((Connection)connection, statement, resultSet);
        }
        return linkedList;
    }

    public AtomicBoolean getDeleted() {
        return this.m;
    }

    public String getOwnerName() {
        if (this._ownerName == null) {
            Player player = World.getPlayer((int)this.getOwnerId());
            if (player != null) {
                this._ownerName = player.getName();
            } else {
                this._ownerName = CharacterDAO.getInstance().getNameByObjectId(this.getOwnerId());
                if (this._ownerName == null) {
                    this._ownerName = "";
                }
            }
        }
        return this._ownerName;
    }

    public long getCreatedAt() {
        return this.eB;
    }

    public boolean isActive() {
        return TimeUnit.SECONDS.toDays(Math.max(0L, TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - this.getCreatedAt())) <= (long)Config.PAWNSHOP_ITEM_TERM;
    }

    public int getId() {
        return this.bGt;
    }

    public int getOwnerId() {
        return this.bGu;
    }

    public int getItemTypeId() {
        return this.bGv;
    }

    public int getAmount() {
        return this.bGw;
    }

    public int getEnchantLevel() {
        return this.bGx;
    }

    public int getCurrencyItemId() {
        return this.bGy;
    }

    public int getPrice() {
        return this.bGz;
    }

    public int getVarOpt1() {
        return this.bGA;
    }

    public int getVarOpt2() {
        return this.bGB;
    }

    public Skill getVariationSkill() {
        return this.A;
    }

    public boolean isAugmented() {
        return this.getVarOpt1() > 0 || this.getVarOpt2() > 0;
    }

    public ItemTemplate getItemTemplate() {
        return ItemHolder.getInstance().getTemplate(this.getItemTypeId());
    }

    public String getNameForQuery() {
        if (this.is == null) {
            ItemTemplate itemTemplate = this.getItemTemplate();
            this.is = itemTemplate != null ? PawnShop.j(itemTemplate.getName()) : "";
        }
        return this.is;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean store() {
        boolean bl;
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            callableStatement = connection.prepareCall("{CALL `lip_ex_PawnShopStoreItem`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            callableStatement.setInt(1, (int)this.eB);
            callableStatement.setInt(2, this.bGt);
            callableStatement.setInt(3, this.bGu);
            callableStatement.setInt(4, this.bGv);
            callableStatement.setInt(5, this.bGw);
            callableStatement.setInt(6, this.bGx);
            callableStatement.setInt(7, this.bGy);
            callableStatement.setInt(8, this.bGz);
            callableStatement.setInt(9, this.bGA);
            callableStatement.setInt(10, this.bGB);
            callableStatement.execute();
            bl = true;
        }
        catch (SQLException sQLException) {
            try {
                et.error("", (Throwable)sQLException);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly((Connection)connection, callableStatement);
                throw throwable;
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)callableStatement);
            return false;
        }
        DbUtils.closeQuietly((Connection)connection, (Statement)callableStatement);
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void delete() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            callableStatement = connection.prepareCall("{CALL `lip_ex_PawnShopDeleteItem`(?)}");
            callableStatement.setInt(1, this.bGt);
            callableStatement.execute();
            this.m.set(true);
        }
        catch (SQLException sQLException) {
            try {
                et.error("", (Throwable)sQLException);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly((Connection)connection, callableStatement);
                throw throwable;
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)callableStatement);
        }
        DbUtils.closeQuietly((Connection)connection, (Statement)callableStatement);
    }
}
