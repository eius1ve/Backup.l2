/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.HashIntObjectMap
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.entity.residence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import l2.commons.dao.JdbcEntityState;
import l2.commons.dbutils.DbUtils;
import l2.commons.math.SafeMath;
import l2.gameserver.dao.CastleDAO;
import l2.gameserver.dao.CastleHiredGuardDAO;
import l2.gameserver.dao.ClanDataDAO;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Manor;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.SevenSigns;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.entity.residence.ResidenceType;
import l2.gameserver.model.items.ItemContainer;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.NpcString;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.item.support.MerchantGuard;
import l2.gameserver.templates.manor.CropProcure;
import l2.gameserver.templates.manor.SeedProduction;
import l2.gameserver.utils.GameStats;
import l2.gameserver.utils.Log;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.HashIntObjectMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Castle
extends Residence {
    private static final Logger cc = LoggerFactory.getLogger(Castle.class);
    private static final String dw = "DELETE FROM `castle_manor_production` WHERE `castle_id`=?;";
    private static final String dx = "DELETE FROM `castle_manor_production` WHERE `castle_id`=? AND `period`=?;";
    private static final String dy = "DELETE FROM `castle_manor_procure` WHERE `castle_id`=?;";
    private static final String dz = "DELETE FROM `castle_manor_procure` WHERE `castle_id`=? AND `period`=?;";
    private static final String dA = "UPDATE `castle_manor_procure` SET `can_buy`=? WHERE `crop_id`=? AND `castle_id`=? AND `period`=?";
    private static final String dB = "UPDATE `castle_manor_production` SET `can_produce`=? WHERE `seed_id`=? AND `castle_id`=? AND `period`=?";
    private final IntObjectMap<MerchantGuard> l = new HashIntObjectMap();
    private List<CropProcure> bF;
    private List<SeedProduction> bt;
    private List<CropProcure> bG;
    private List<SeedProduction> bH;
    private boolean dr;
    private int ma;
    private double F;
    private long cl;
    private long cm;
    private long cn;
    private final NpcString b;
    private Set<ItemInstance> x = new CopyOnWriteArraySet<ItemInstance>();

    public Castle(StatsSet statsSet) {
        super(statsSet);
        this.b = NpcString.valueOf(1001000 + this._id);
    }

    @Override
    public ResidenceType getType() {
        return ResidenceType.Castle;
    }

    @Override
    public void changeOwner(Clan clan) {
        Object object;
        if (clan != null && clan.getCastle() != 0 && (object = ResidenceHolder.getInstance().getResidence(Castle.class, clan.getCastle())) != null) {
            ((Castle)object).changeOwner(null);
        }
        object = null;
        if (this.getOwnerId() > 0 && (clan == null || clan.getClanId() != this.getOwnerId())) {
            this.removeSkills();
            this.setTaxPercent(null, 0);
            this.cancelCycleTask();
            object = this.getOwner();
            if (object != null) {
                Object object2;
                long l = this.getTreasury();
                if (l > 0L && (object2 = ((Clan)object).getWarehouse()) != null) {
                    ((ItemContainer)object2).addItem(57, l);
                    this.addToTreasuryNoTax(-l, false, false);
                    Log.add(this.getName() + "|" + -l + "|Castle:changeOwner", "treasury");
                }
                for (Player player : ((Clan)object).getOnlineMembers(0)) {
                    if (player == null || player.getInventory() == null) continue;
                    player.getInventory().validateItems();
                }
                ((Clan)object).setHasCastle(0);
            }
        }
        if (clan != null) {
            clan.setHasCastle(this.getId());
        }
        this.a(clan);
        this.rewardSkills();
        this.update();
    }

    @Override
    protected void loadData() {
        this.ma = 0;
        this.F = 0.0;
        this.cl = 0L;
        this.bF = new ArrayList<CropProcure>();
        this.bt = new ArrayList<SeedProduction>();
        this.bG = new ArrayList<CropProcure>();
        this.bH = new ArrayList<SeedProduction>();
        this.dr = false;
        this._owner = ClanDataDAO.getInstance().getOwner(this);
        CastleDAO.getInstance().select(this);
        CastleHiredGuardDAO.getInstance().load(this);
    }

    public void setTaxPercent(int n) {
        this.ma = Math.min(Math.max(0, n), 100);
        this.F = (double)this.ma / 100.0;
    }

    public void setTreasury(long l) {
        this.cl = l;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(Clan clan) {
        PreparedStatement preparedStatement;
        Connection connection;
        block4: {
            this._owner = clan;
            connection = null;
            preparedStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("UPDATE `clan_data` SET `hasCastle`=0 WHERE `hasCastle`=? LIMIT 1");
                preparedStatement.setInt(1, this.getId());
                preparedStatement.execute();
                DbUtils.close(preparedStatement);
                if (clan == null) break block4;
                preparedStatement = connection.prepareStatement("UPDATE `clan_data` SET `hasCastle`=? WHERE `clan_id`=? LIMIT 1");
                preparedStatement.setInt(1, this.getId());
                preparedStatement.setInt(2, this.getOwnerId());
                preparedStatement.execute();
                clan.broadcastClanStatus(true, false, false);
            }
            catch (Exception exception) {
                try {
                    cc.error("", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public int getTaxPercent() {
        if (this.ma > 5 && SevenSigns.getInstance().getSealOwner(3) == 1) {
            this.ma = 5;
        }
        return this.ma;
    }

    public int getTaxPercent0() {
        return this.ma;
    }

    public long getCollectedShops() {
        return this.cm;
    }

    public long getCollectedSeed() {
        return this.cn;
    }

    public void setCollectedShops(long l) {
        this.cm = l;
    }

    public void setCollectedSeed(long l) {
        this.cn = l;
    }

    public void addToTreasury(long l, boolean bl, boolean bl2) {
        Castle castle;
        if (this.getOwnerId() <= 0) {
            return;
        }
        if (l == 0L) {
            return;
        }
        if (l > 1L && this._id != 5 && this._id != 8 && (castle = ResidenceHolder.getInstance().getResidence(Castle.class, this._id >= 7 ? 8 : 5)) != null) {
            long l2 = (long)((double)l * castle.getTaxRate());
            if (castle.getOwnerId() > 0) {
                castle.addToTreasury(l2, bl, bl2);
                if (this._id == 5) {
                    Log.add("Aden|" + l2 + "|Castle:adenTax", "treasury");
                } else if (this._id == 8) {
                    Log.add("Rune|" + l2 + "|Castle:runeTax", "treasury");
                }
            }
            l -= l2;
        }
        this.addToTreasuryNoTax(l, bl, bl2);
    }

    public void addToTreasuryNoTax(long l, boolean bl, boolean bl2) {
        if (this.getOwnerId() <= 0) {
            return;
        }
        if (l == 0L) {
            return;
        }
        GameStats.addAdena(l);
        this.cl = SafeMath.addAndLimit(this.cl, l);
        if (bl) {
            this.cm += l;
        }
        if (bl2) {
            this.cn += l;
        }
        this.setJdbcState(JdbcEntityState.UPDATED);
        this.update();
    }

    public int getCropRewardType(int n) {
        int n2 = 0;
        for (CropProcure cropProcure : this.bF) {
            if (cropProcure.getId() != n) continue;
            n2 = cropProcure.getReward();
        }
        return n2;
    }

    public void setTaxPercent(Player player, int n) {
        this.setTaxPercent(n);
        this.setJdbcState(JdbcEntityState.UPDATED);
        this.update();
        if (player != null) {
            player.sendMessage(new CustomMessage("l2p.gameserver.model.entity.Castle.OutOfControl.CastleTaxChangetTo", player, new Object[0]).addString(this.getName()).addNumber(n));
        }
    }

    public double getTaxRate() {
        if (this.F > 0.05 && SevenSigns.getInstance().getSealOwner(3) == 1) {
            this.F = 0.05;
        }
        return this.F;
    }

    public long getTreasury() {
        return this.cl;
    }

    public List<SeedProduction> getSeedProduction(int n) {
        return n == 0 ? this.bt : this.bH;
    }

    public List<CropProcure> getCropProcure(int n) {
        return n == 0 ? this.bF : this.bG;
    }

    public void setSeedProduction(List<SeedProduction> list, int n) {
        if (n == 0) {
            this.bt = list;
        } else {
            this.bH = list;
        }
    }

    public void setCropProcure(List<CropProcure> list, int n) {
        if (n == 0) {
            this.bF = list;
        } else {
            this.bG = list;
        }
    }

    public synchronized SeedProduction getSeed(int n, int n2) {
        for (SeedProduction seedProduction : this.getSeedProduction(n2)) {
            if (seedProduction.getId() != n) continue;
            return seedProduction;
        }
        return null;
    }

    public synchronized CropProcure getCrop(int n, int n2) {
        for (CropProcure cropProcure : this.getCropProcure(n2)) {
            if (cropProcure.getId() != n) continue;
            return cropProcure;
        }
        return null;
    }

    public long getManorCost(int n) {
        List<SeedProduction> list;
        List<CropProcure> list2;
        if (n == 0) {
            list2 = this.bF;
            list = this.bt;
        } else {
            list2 = this.bG;
            list = this.bH;
        }
        long l = 0L;
        if (list != null) {
            for (SeedProduction object : list) {
                l += Manor.getInstance().getSeedBuyPrice(object.getId()) * object.getStartProduce();
            }
        }
        if (list2 != null) {
            for (CropProcure cropProcure : list2) {
                l += cropProcure.getPrice() * cropProcure.getStartAmount();
            }
        }
        return l;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveSeedData() {
        PreparedStatement preparedStatement;
        Connection connection;
        block10: {
            connection = null;
            preparedStatement = null;
            try {
                String[] stringArray;
                Object object;
                int n;
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(dw);
                preparedStatement.setInt(1, this.getId());
                preparedStatement.execute();
                DbUtils.close(preparedStatement);
                if (this.bt != null) {
                    n = 0;
                    object = "INSERT INTO `castle_manor_production` VALUES ";
                    stringArray = new String[this.bt.size()];
                    for (SeedProduction seedProduction : this.bt) {
                        stringArray[n] = "(" + this.getId() + "," + seedProduction.getId() + "," + seedProduction.getCanProduce() + "," + seedProduction.getStartProduce() + "," + seedProduction.getPrice() + ",0)";
                        ++n;
                    }
                    if (stringArray.length > 0) {
                        object = (String)object + stringArray[0];
                        for (int i = 1; i < stringArray.length; ++i) {
                            object = (String)object + "," + stringArray[i];
                        }
                        preparedStatement = connection.prepareStatement((String)object);
                        preparedStatement.execute();
                        DbUtils.close(preparedStatement);
                    }
                }
                if (this.bH == null) break block10;
                n = 0;
                object = "INSERT INTO `castle_manor_production` VALUES ";
                stringArray = new String[this.bH.size()];
                for (SeedProduction seedProduction : this.bH) {
                    stringArray[n] = "(" + this.getId() + "," + seedProduction.getId() + "," + seedProduction.getCanProduce() + "," + seedProduction.getStartProduce() + "," + seedProduction.getPrice() + ",1)";
                    ++n;
                }
                if (stringArray.length <= 0) break block10;
                object = (String)object + stringArray[0];
                for (int i = 1; i < stringArray.length; ++i) {
                    object = (String)object + "," + stringArray[i];
                }
                preparedStatement = connection.prepareStatement((String)object);
                preparedStatement.execute();
                DbUtils.close(preparedStatement);
            }
            catch (Exception exception) {
                try {
                    cc.error("Error adding seed production data for castle " + this.getName() + "!", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveSeedData(int n) {
        PreparedStatement preparedStatement;
        Connection connection;
        block6: {
            connection = null;
            preparedStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(dx);
                preparedStatement.setInt(1, this.getId());
                preparedStatement.setInt(2, n);
                preparedStatement.execute();
                DbUtils.close(preparedStatement);
                List<SeedProduction> list = null;
                list = this.getSeedProduction(n);
                if (list == null) break block6;
                int n2 = 0;
                Object object = "INSERT INTO `castle_manor_production` VALUES ";
                String[] stringArray = new String[list.size()];
                for (SeedProduction seedProduction : list) {
                    stringArray[n2] = "(" + this.getId() + "," + seedProduction.getId() + "," + seedProduction.getCanProduce() + "," + seedProduction.getStartProduce() + "," + seedProduction.getPrice() + "," + n + ")";
                    ++n2;
                }
                if (stringArray.length <= 0) break block6;
                object = (String)object + stringArray[0];
                for (int i = 1; i < stringArray.length; ++i) {
                    object = (String)object + "," + stringArray[i];
                }
                preparedStatement = connection.prepareStatement((String)object);
                preparedStatement.execute();
                DbUtils.close(preparedStatement);
            }
            catch (Exception exception) {
                try {
                    cc.error("Error adding seed production data for castle " + this.getName() + "!", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveCropData() {
        PreparedStatement preparedStatement;
        Connection connection;
        block10: {
            connection = null;
            preparedStatement = null;
            try {
                String[] stringArray;
                Object object;
                int n;
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(dy);
                preparedStatement.setInt(1, this.getId());
                preparedStatement.execute();
                DbUtils.close(preparedStatement);
                if (this.bF != null) {
                    n = 0;
                    object = "INSERT INTO `castle_manor_procure` VALUES ";
                    stringArray = new String[this.bF.size()];
                    for (CropProcure cropProcure : this.bF) {
                        stringArray[n] = "(" + this.getId() + "," + cropProcure.getId() + "," + cropProcure.getAmount() + "," + cropProcure.getStartAmount() + "," + cropProcure.getPrice() + "," + cropProcure.getReward() + ",0)";
                        ++n;
                    }
                    if (stringArray.length > 0) {
                        object = (String)object + stringArray[0];
                        for (int i = 1; i < stringArray.length; ++i) {
                            object = (String)object + "," + stringArray[i];
                        }
                        preparedStatement = connection.prepareStatement((String)object);
                        preparedStatement.execute();
                        DbUtils.close(preparedStatement);
                    }
                }
                if (this.bG == null) break block10;
                n = 0;
                object = "INSERT INTO `castle_manor_procure` VALUES ";
                stringArray = new String[this.bG.size()];
                for (CropProcure cropProcure : this.bG) {
                    stringArray[n] = "(" + this.getId() + "," + cropProcure.getId() + "," + cropProcure.getAmount() + "," + cropProcure.getStartAmount() + "," + cropProcure.getPrice() + "," + cropProcure.getReward() + ",1)";
                    ++n;
                }
                if (stringArray.length <= 0) break block10;
                object = (String)object + stringArray[0];
                for (int i = 1; i < stringArray.length; ++i) {
                    object = (String)object + "," + stringArray[i];
                }
                preparedStatement = connection.prepareStatement((String)object);
                preparedStatement.execute();
                DbUtils.close(preparedStatement);
            }
            catch (Exception exception) {
                try {
                    cc.error("Error adding crop data for castle " + this.getName() + "!", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveCropData(int n) {
        PreparedStatement preparedStatement;
        Connection connection;
        block6: {
            connection = null;
            preparedStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement(dz);
                preparedStatement.setInt(1, this.getId());
                preparedStatement.setInt(2, n);
                preparedStatement.execute();
                DbUtils.close(preparedStatement);
                List<CropProcure> list = null;
                list = this.getCropProcure(n);
                if (list == null) break block6;
                int n2 = 0;
                Object object = "INSERT INTO `castle_manor_procure` VALUES ";
                String[] stringArray = new String[list.size()];
                for (CropProcure cropProcure : list) {
                    stringArray[n2] = "(" + this.getId() + "," + cropProcure.getId() + "," + cropProcure.getAmount() + "," + cropProcure.getStartAmount() + "," + cropProcure.getPrice() + "," + cropProcure.getReward() + "," + n + ")";
                    ++n2;
                }
                if (stringArray.length <= 0) break block6;
                object = (String)object + stringArray[0];
                for (int i = 1; i < stringArray.length; ++i) {
                    object = (String)object + "," + stringArray[i];
                }
                preparedStatement = connection.prepareStatement((String)object);
                preparedStatement.execute();
                DbUtils.close(preparedStatement);
            }
            catch (Exception exception) {
                try {
                    cc.error("Error adding crop data for castle " + this.getName() + "!", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateCrop(int n, long l, int n2) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(dA);
            preparedStatement.setLong(1, l);
            preparedStatement.setInt(2, n);
            preparedStatement.setInt(3, this.getId());
            preparedStatement.setInt(4, n2);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                cc.error("Error adding crop data for castle " + this.getName() + "!", (Throwable)exception);
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
    public void updateSeed(int n, long l, int n2) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(dB);
            preparedStatement.setLong(1, l);
            preparedStatement.setInt(2, n);
            preparedStatement.setInt(3, this.getId());
            preparedStatement.setInt(4, n2);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                cc.error("Error adding seed production data for castle " + this.getName() + "!", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public boolean isNextPeriodApproved() {
        return this.dr;
    }

    public void setNextPeriodApproved(boolean bl) {
        this.dr = bl;
    }

    @Override
    public void update() {
        CastleDAO.getInstance().update(this);
    }

    public NpcString getNpcStringName() {
        return this.b;
    }

    public void addMerchantGuard(MerchantGuard merchantGuard) {
        this.l.put(merchantGuard.getItemId(), (Object)merchantGuard);
    }

    public MerchantGuard getMerchantGuard(int n) {
        return (MerchantGuard)this.l.get(n);
    }

    public IntObjectMap<MerchantGuard> getMerchantGuards() {
        return this.l;
    }

    public Set<ItemInstance> getSpawnMerchantTickets() {
        return this.x;
    }

    @Override
    public void startCycleTask() {
    }
}
