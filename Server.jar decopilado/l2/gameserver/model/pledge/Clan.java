/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.CTreeIntObjectMap
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.pledge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import l2.commons.collections.JoinedIterator;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.cache.CrestCache;
import l2.gameserver.data.xml.holder.EventHolder;
import l2.gameserver.data.xml.holder.OneDayRewardHolder;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.data.xml.holder.SkillAcquireHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.database.mysql;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.SkillLearn;
import l2.gameserver.model.base.AcquireType;
import l2.gameserver.model.entity.oneDayReward.requirement.JoinClanRequirement;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.entity.residence.ResidenceType;
import l2.gameserver.model.items.ClanWarehouse;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.RankPrivs;
import l2.gameserver.model.pledge.SubUnit;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExPledgeCount;
import l2.gameserver.network.l2.s2c.JoinPledge;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PledgeReceiveSubPledgeCreated;
import l2.gameserver.network.l2.s2c.PledgeShowInfoUpdate;
import l2.gameserver.network.l2.s2c.PledgeShowMemberListAdd;
import l2.gameserver.network.l2.s2c.PledgeShowMemberListAll;
import l2.gameserver.network.l2.s2c.PledgeShowMemberListDeleteAll;
import l2.gameserver.network.l2.s2c.PledgeSkillList;
import l2.gameserver.network.l2.s2c.PledgeSkillListAdd;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Log;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.CTreeIntObjectMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Clan
implements Iterable<UnitMember> {
    private static final Logger cr = LoggerFactory.getLogger(Clan.class);
    private final int oR;
    private int oP;
    private int _level;
    private int oS;
    private int oT;
    private int oU;
    private int oV;
    private long cF;
    private long cG;
    private long cH;
    private long cI;
    private long cJ;
    private final ClanWarehouse a;
    private int oW = -1;
    private String dK = null;
    private List<Clan> bJ = new CopyOnWriteArrayList<Clan>();
    private List<Clan> bK = new CopyOnWriteArrayList<Clan>();
    protected IntObjectMap<Skill> _skills = new CTreeIntObjectMap();
    protected IntObjectMap<RankPrivs> _privs = new CTreeIntObjectMap();
    protected IntObjectMap<SubUnit> _subUnits = new CTreeIntObjectMap();
    private int oX = 0;
    private int oY;
    public static final int CP_NOTHING = 0;
    public static final int CP_CL_INVITE_CLAN = 2;
    public static final int CP_CL_MANAGE_TITLES = 4;
    public static final int CP_CL_WAREHOUSE_SEARCH = 8;
    public static final int CP_CL_MANAGE_RANKS = 16;
    public static final int CP_CL_CLAN_WAR = 32;
    public static final int CP_CL_DISMISS = 64;
    public static final int CP_CL_EDIT_CREST = 128;
    public static final int CP_CL_APPRENTICE = 256;
    public static final int CP_CL_TROOPS_FAME = 512;
    public static final int CP_CH_ENTRY_EXIT = 2048;
    public static final int CP_CH_USE_FUNCTIONS = 4096;
    public static final int CP_CH_AUCTION = 8192;
    public static final int CP_CH_DISMISS = 16384;
    public static final int CP_CH_SET_FUNCTIONS = 32768;
    public static final int CP_CS_ENTRY_EXIT = 65536;
    public static final int CP_CS_MANOR_ADMIN = 131072;
    public static final int CP_CS_MANAGE_SIEGE = 262144;
    public static final int CP_CS_USE_FUNCTIONS = 524288;
    public static final int CP_CS_DISMISS = 0x100000;
    public static final int CP_CS_TAXES = 0x200000;
    public static final int CP_CS_MERCENARIES = 0x400000;
    public static final int CP_CS_SET_FUNCTIONS = 0x7FFFFE;
    public static final int CP_ALL = 0xFFFFFE;
    public static final int RANK_FIRST = 1;
    public static final int RANK_LAST = 9;
    public static final int SUBUNIT_NONE = -128;
    public static final int SUBUNIT_ACADEMY = -1;
    public static final int SUBUNIT_MAIN_CLAN = 0;
    public static final int SUBUNIT_ROYAL1 = 100;
    public static final int SUBUNIT_ROYAL2 = 200;
    public static final int SUBUNIT_KNIGHT1 = 1001;
    public static final int SUBUNIT_KNIGHT2 = 1002;
    public static final int SUBUNIT_KNIGHT3 = 2001;
    public static final int SUBUNIT_KNIGHT4 = 2002;
    private static final ClanReputationComparator a = new ClanReputationComparator();
    private static final int oZ = 100;

    public Clan(int n) {
        this.oR = n;
        this.InitializePrivs();
        this.a = new ClanWarehouse(this);
        this.a.restore();
    }

    public int getClanId() {
        return this.oR;
    }

    public int getLeaderId() {
        return this.getLeaderId(0);
    }

    public UnitMember getLeader() {
        return this.getLeader(0);
    }

    public String getLeaderName() {
        return this.getLeaderName(0);
    }

    public String getName() {
        return this.getUnitName(0);
    }

    public UnitMember getAnyMember(int n) {
        for (SubUnit subUnit : this.getAllSubUnits()) {
            UnitMember unitMember = subUnit.getUnitMember(n);
            if (unitMember == null) continue;
            return unitMember;
        }
        return null;
    }

    public UnitMember getAnyMember(String string) {
        for (SubUnit subUnit : this.getAllSubUnits()) {
            UnitMember unitMember = subUnit.getUnitMember(string);
            if (unitMember == null) continue;
            return unitMember;
        }
        return null;
    }

    public int getAllSize() {
        int n = 0;
        for (SubUnit subUnit : this.getAllSubUnits()) {
            n += subUnit.size();
        }
        return n;
    }

    public String getUnitName(int n) {
        if (n == -128 || !this._subUnits.containsKey(n)) {
            return "";
        }
        return this.getSubUnit(n).getName();
    }

    public String getLeaderName(int n) {
        if (n == -128 || !this._subUnits.containsKey(n)) {
            return "";
        }
        return this.getSubUnit(n).getLeaderName();
    }

    public int getLeaderId(int n) {
        if (n == -128 || !this._subUnits.containsKey(n)) {
            return 0;
        }
        return this.getSubUnit(n).getLeaderObjectId();
    }

    public UnitMember getLeader(int n) {
        if (n == -128 || !this._subUnits.containsKey(n)) {
            return null;
        }
        return this.getSubUnit(n).getLeader();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void flush() {
        for (UnitMember unitMember : this) {
            this.removeClanMember(unitMember.getObjectId());
        }
        this.a.writeLock();
        try {
            for (ItemInstance itemInstance : this.a.getItems()) {
                this.a.destroyItem(itemInstance);
            }
        }
        finally {
            this.a.writeUnlock();
        }
        if (this.oS != 0) {
            ResidenceHolder.getInstance().getResidence(Castle.class, this.oS).changeOwner(null);
        }
    }

    public void removeClanMember(int n) {
        if (n == this.getLeaderId(0)) {
            return;
        }
        for (SubUnit subUnit : this.getAllSubUnits()) {
            if (!subUnit.isUnitMember(n)) continue;
            this.removeClanMember(subUnit.getType(), n);
            break;
        }
    }

    public void removeClanMember(int n, int n2) {
        SubUnit subUnit = this.getSubUnit(n);
        if (subUnit == null) {
            return;
        }
        subUnit.removeUnitMember(n2);
    }

    public List<UnitMember> getAllMembers() {
        Collection<SubUnit> collection = this.getAllSubUnits();
        int n = 0;
        for (SubUnit object : collection) {
            n += object.size();
        }
        ArrayList arrayList = new ArrayList(n);
        for (SubUnit subUnit : collection) {
            arrayList.addAll(subUnit.getUnitMembers());
        }
        return arrayList;
    }

    public List<Player> getOnlineMembers(int n) {
        ArrayList<Player> arrayList = new ArrayList<Player>(this.getAllSize() - 1);
        for (UnitMember unitMember : this) {
            if (unitMember == null || !unitMember.isOnline() || unitMember.getObjectId() == n) continue;
            arrayList.add(unitMember.getPlayer());
        }
        return arrayList;
    }

    public int getAllyId() {
        return this.oP;
    }

    public int getLevel() {
        return this._level;
    }

    public int getCastle() {
        return this.oS;
    }

    public int getHasHideout() {
        return this.oT;
    }

    public int getResidenceId(ResidenceType residenceType) {
        switch (residenceType) {
            case Castle: {
                return this.oS;
            }
            case ClanHall: {
                return this.oT;
            }
        }
        return 0;
    }

    public void setAllyId(int n) {
        this.oP = n;
    }

    public void setHasCastle(int n) {
        this.oS = n;
    }

    public void setHasHideout(int n) {
        this.oT = n;
    }

    public void setLevel(int n) {
        this._level = n;
    }

    public boolean isAnyMember(int n) {
        for (SubUnit subUnit : this.getAllSubUnits()) {
            if (!subUnit.isUnitMember(n)) continue;
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateClanInDB() {
        if (this.getLeaderId() == 0) {
            cr.warn("updateClanInDB with empty LeaderId");
            Thread.dumpStack();
            return;
        }
        if (this.getClanId() == 0) {
            cr.warn("updateClanInDB with empty ClanId");
            Thread.dumpStack();
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `clan_data` SET `ally_id`=?,`reputation_score`=?,`expelled_member`=?,`leaved_ally`=?,`dissolved_ally`=?,`clan_level`=?,`warehouse`=?,`disband_end`=?,`disband_penalty`=?,`custom_points`=? WHERE `clan_id`=?");
            preparedStatement.setInt(1, this.getAllyId());
            preparedStatement.setInt(2, this.getReputationScore());
            preparedStatement.setLong(3, this.getExpelledMemberTime() / 1000L);
            preparedStatement.setLong(4, this.getLeavedAllyTime() / 1000L);
            preparedStatement.setLong(5, this.getDissolvedAllyTime() / 1000L);
            preparedStatement.setInt(6, this._level);
            preparedStatement.setInt(7, this.getWhBonus());
            preparedStatement.setInt(8, (int)(this.getDisbandEndTime() / 1000L));
            preparedStatement.setInt(9, (int)(this.getDisbandPenaltyTime() / 1000L));
            preparedStatement.setInt(10, this.getCustomPoints());
            preparedStatement.setInt(11, this.getClanId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                cr.warn("error while updating clan '" + this.getClanId() + "' data in db");
                cr.error("", (Throwable)exception);
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
    public void store() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO `clan_data` (`clan_id`,`clan_level`,`hasCastle`,`hasHideout`,`ally_id`,`expelled_member`,`leaved_ally`,`dissolved_ally`) values (?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, this.oR);
            preparedStatement.setInt(2, this._level);
            preparedStatement.setInt(3, this.oS);
            preparedStatement.setInt(4, this.oT);
            preparedStatement.setInt(5, this.oP);
            preparedStatement.setLong(6, this.getExpelledMemberTime() / 1000L);
            preparedStatement.setLong(7, this.getLeavedAllyTime() / 1000L);
            preparedStatement.setLong(8, this.getDissolvedAllyTime() / 1000L);
            preparedStatement.execute();
            DbUtils.close(preparedStatement);
            SubUnit subUnit = (SubUnit)this._subUnits.get(0);
            preparedStatement = connection.prepareStatement("INSERT INTO `clan_subpledges` (`clan_id`, `type`, `leader_id`, `name`) VALUES (?,?,?,?)");
            preparedStatement.setInt(1, this.oR);
            preparedStatement.setInt(2, subUnit.getType());
            preparedStatement.setInt(3, subUnit.getLeaderObjectId());
            preparedStatement.setString(4, subUnit.getName());
            preparedStatement.execute();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("UPDATE `characters` SET `clanid`=?,`pledge_type`=? WHERE `obj_Id`=?");
            preparedStatement.setInt(1, this.getClanId());
            preparedStatement.setInt(2, subUnit.getType());
            preparedStatement.setInt(3, this.getLeaderId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                cr.warn("Exception: " + exception, (Throwable)exception);
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
    public static Clan restore(int n) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        Clan clan;
        block8: {
            Clan clan2;
            if (n == 0) {
                return null;
            }
            clan = null;
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT `clan_level`,`hasCastle`,`hasHideout`,`ally_id`,`reputation_score`,`expelled_member`,`leaved_ally`,`dissolved_ally`,`warehouse`,`disband_end`,`disband_penalty`,`custom_points` FROM `clan_data` where `clan_id`=?");
                preparedStatement.setInt(1, n);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    clan = new Clan(n);
                    clan.setLevel(resultSet.getInt("clan_level"));
                    clan.setHasCastle(resultSet.getInt("hasCastle"));
                    clan.setHasHideout(resultSet.getInt("hasHideout"));
                    clan.setAllyId(resultSet.getInt("ally_id"));
                    clan.oX = resultSet.getInt("reputation_score");
                    clan.setExpelledMemberTime(resultSet.getLong("expelled_member") * 1000L);
                    clan.setLeavedAllyTime(resultSet.getLong("leaved_ally") * 1000L);
                    clan.setDissolvedAllyTime(resultSet.getLong("dissolved_ally") * 1000L);
                    clan.setWhBonus(resultSet.getInt("warehouse"));
                    clan.setDisbandEndTime(resultSet.getLong("disband_end") * 1000L);
                    clan.setDisbandPenaltyTime(resultSet.getLong("disband_penalty") * 1000L);
                    clan.oY = resultSet.getInt("custom_points");
                    break block8;
                }
                cr.warn("Clan " + n + " doesnt exists!");
                clan2 = null;
            }
            catch (Exception exception) {
                try {
                    cr.error("Error while restoring clan!", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            return clan2;
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        if (clan == null) {
            cr.warn("Clan " + n + " does't exist");
            return null;
        }
        clan.restoreSkills();
        clan.bM();
        for (SubUnit subUnit : clan.getAllSubUnits()) {
            subUnit.restore();
            subUnit.restoreSkills();
        }
        clan.bN();
        clan.setCrestId(CrestCache.getInstance().getPledgeCrestId(n));
        clan.setCrestLargeId(CrestCache.getInstance().getPledgeCrestLargeId(n));
        return clan;
    }

    public void broadcastToOnlineMembers(IStaticPacket ... iStaticPacketArray) {
        for (UnitMember unitMember : this) {
            if (!unitMember.isOnline()) continue;
            unitMember.getPlayer().sendPacket(iStaticPacketArray);
        }
    }

    public void broadcastToOnlineMembers(L2GameServerPacket ... l2GameServerPacketArray) {
        for (UnitMember unitMember : this) {
            if (!unitMember.isOnline()) continue;
            unitMember.getPlayer().sendPacket(l2GameServerPacketArray);
        }
    }

    public void broadcastToOtherOnlineMembers(L2GameServerPacket l2GameServerPacket, Player player) {
        for (UnitMember unitMember : this) {
            if (!unitMember.isOnline() || unitMember.getPlayer() == player) continue;
            unitMember.getPlayer().sendPacket((IStaticPacket)l2GameServerPacket);
        }
    }

    public void broadcastToOtherOnlineMembers(IStaticPacket iStaticPacket, Player player) {
        for (UnitMember unitMember : this) {
            if (!unitMember.isOnline() || unitMember.getPlayer() == player) continue;
            unitMember.getPlayer().sendPacket(iStaticPacket);
        }
    }

    public String toString() {
        return this.getName();
    }

    public void setCrestId(int n) {
        this.oU = n;
    }

    public int getCrestId() {
        return this.oU;
    }

    public boolean hasCrest() {
        return this.oU > 0;
    }

    public int getCrestLargeId() {
        return this.oV;
    }

    public void setCrestLargeId(int n) {
        this.oV = n;
    }

    public boolean hasCrestLarge() {
        return this.oV > 0;
    }

    public long getAdenaCount() {
        return this.a.getCountOfAdena();
    }

    public ClanWarehouse getWarehouse() {
        return this.a;
    }

    public int isAtWar() {
        if (this.bJ != null && !this.bJ.isEmpty()) {
            return 1;
        }
        return 0;
    }

    public int isAtWarOrUnderAttack() {
        if (!this.bJ.isEmpty() || !this.bK.isEmpty()) {
            return 1;
        }
        return 0;
    }

    public boolean isAtWarWith(int n) {
        Clan clan = ClanTable.getInstance().getClan(n);
        return this.bJ != null && !this.bJ.isEmpty() && this.bJ.contains(clan);
    }

    public boolean isUnderAttackFrom(int n) {
        Clan clan = ClanTable.getInstance().getClan(n);
        return this.bK.contains(clan);
    }

    public void setEnemyClan(Clan clan) {
        this.bJ.add(clan);
    }

    public void deleteEnemyClan(Clan clan) {
        this.bJ.remove(clan);
    }

    public void setAttackerClan(Clan clan) {
        this.bK.add(clan);
    }

    public void deleteAttackerClan(Clan clan) {
        this.bK.remove(clan);
    }

    public List<Clan> getEnemyClans() {
        return this.bJ;
    }

    public int getWarsCount() {
        return this.bJ.size();
    }

    public List<Clan> getAttackerClans() {
        return Collections.unmodifiableList(this.bK);
    }

    public void broadcastClanStatus(boolean bl, boolean bl2, boolean bl3) {
        List<L2GameServerPacket> list = bl ? this.listAll() : null;
        PledgeShowInfoUpdate pledgeShowInfoUpdate = new PledgeShowInfoUpdate(this);
        for (UnitMember unitMember : this) {
            if (!unitMember.isOnline()) continue;
            if (bl) {
                unitMember.getPlayer().sendPacket((IStaticPacket)PledgeShowMemberListDeleteAll.STATIC);
                unitMember.getPlayer().sendPacket(list);
            }
            unitMember.getPlayer().sendPacket((IStaticPacket)pledgeShowInfoUpdate);
            if (bl2) {
                unitMember.getPlayer().broadcastCharInfo();
            }
            if (!bl3) continue;
            unitMember.getPlayer().broadcastRelation();
        }
    }

    public Alliance getAlliance() {
        return this.oP == 0 ? null : ClanTable.getInstance().getAlliance(this.oP);
    }

    public void setExpelledMemberTime(long l) {
        this.cF = l;
    }

    public long getExpelledMemberTime() {
        return this.cF;
    }

    public void setExpelledMember() {
        this.cF = System.currentTimeMillis();
        this.updateClanInDB();
    }

    public void setLeavedAllyTime(long l) {
        this.cG = l;
    }

    public long getLeavedAllyTime() {
        return this.cG;
    }

    public void setLeavedAlly() {
        this.cG = System.currentTimeMillis();
        this.updateClanInDB();
    }

    public void setDissolvedAllyTime(long l) {
        this.cH = l;
    }

    public long getDissolvedAllyTime() {
        return this.cH;
    }

    public void setDissolvedAlly() {
        this.cH = System.currentTimeMillis();
        this.updateClanInDB();
    }

    public boolean canInvite() {
        return System.currentTimeMillis() - this.cF >= Config.EXPELLED_MEMBER_PENALTY;
    }

    public boolean canJoinAlly() {
        return System.currentTimeMillis() - this.cG >= Config.LEAVED_ALLY_PENALTY;
    }

    public boolean canCreateAlly() {
        return System.currentTimeMillis() - this.cH >= Config.DISSOLVED_ALLY_PENALTY;
    }

    public boolean canDisband() {
        return System.currentTimeMillis() > this.cI;
    }

    public int getRank() {
        Clan[] clanArray = ClanTable.getInstance().getClans();
        Arrays.sort(clanArray, a);
        int n = 1;
        for (int i = 0; i < clanArray.length; ++i) {
            if (i == 100) {
                return 0;
            }
            Clan clan = clanArray[i];
            if (clan != this) continue;
            return n + i;
        }
        return 0;
    }

    public int getReputationScore() {
        return this.oX;
    }

    private void q(int n) {
        if (this.oX >= 0 && n < 0) {
            this.broadcastToOnlineMembers(SystemMsg.SINCE_THE_CLAN_REPUTATION_SCORE_HAS_DROPPED_TO_0_OR_LOWER_YOUR_CLAN_SKILLS_WILL_BE_DEACTIVATED);
            for (UnitMember unitMember : this) {
                if (!unitMember.isOnline() || unitMember.getPlayer() == null) continue;
                this.disableSkills(unitMember.getPlayer());
            }
        } else if (this.oX < 0 && n >= 0) {
            this.broadcastToOnlineMembers(SystemMsg.CLAN_SKILLS_WILL_NOW_BE_ACTIVATED_SINCE_THE_CLANS_REPUTATION_SCORE_IS_0_OR_HIGHER);
            for (UnitMember unitMember : this) {
                if (!unitMember.isOnline() || unitMember.getPlayer() == null) continue;
                this.enableSkills(unitMember.getPlayer());
            }
        }
        if (this.oX != n) {
            this.oX = n;
            this.broadcastToOnlineMembers(new PledgeShowInfoUpdate(this));
        }
        this.updateClanInDB();
    }

    public int incReputation(int n, boolean bl, String string) {
        if (n > 0 && this._level < Config.MIN_CLAN_LEVEL_FOR_REPUTATION) {
            return 0;
        }
        if (bl && Math.abs(n) <= Config.RATE_CLAN_REP_SCORE_MAX_AFFECTED) {
            n = (int)Math.round((double)n * Config.RATE_CLAN_REP_SCORE);
        }
        this.q(this.oX + n);
        Log.add(this.getName() + "|" + n + "|" + this.oX + "|" + string, "clan_reputation");
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void restoreSkills() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `skill_id`,`skill_level` FROM `clan_skills` WHERE `clan_id`=?");
            preparedStatement.setInt(1, this.getClanId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int n = resultSet.getInt("skill_id");
                int n2 = resultSet.getInt("skill_level");
                Skill skill = SkillTable.getInstance().getInfo(n, n2);
                this._skills.put(skill.getId(), (Object)skill);
            }
        }
        catch (Exception exception) {
            try {
                cr.warn("Could not restore clan skills: " + exception);
                cr.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    public Collection<Skill> getSkills() {
        return this._skills.values();
    }

    public final Skill[] getAllSkills() {
        if (this.oX < 0) {
            return Skill.EMPTY_ARRAY;
        }
        return this._skills.values().toArray(new Skill[this._skills.values().size()]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Skill addSkill(Skill skill, boolean bl) {
        Skill skill2;
        block8: {
            Object object;
            Object object2;
            skill2 = null;
            if (skill == null) break block8;
            skill2 = (Skill)this._skills.put(skill.getId(), (Object)skill);
            if (bl) {
                block7: {
                    object2 = null;
                    object = null;
                    try {
                        object2 = DatabaseFactory.getInstance().getConnection();
                        if (skill2 != null) {
                            object = object2.prepareStatement("UPDATE `clan_skills` SET `skill_level`=? WHERE `skill_id`=? AND `clan_id`=?");
                            object.setInt(1, skill.getLevel());
                            object.setInt(2, skill2.getId());
                            object.setInt(3, this.getClanId());
                            object.execute();
                            break block7;
                        }
                        object = object2.prepareStatement("INSERT INTO `clan_skills` (`clan_id`,`skill_id`,`skill_level`) VALUES (?,?,?)");
                        object.setInt(1, this.getClanId());
                        object.setInt(2, skill.getId());
                        object.setInt(3, skill.getLevel());
                        object.execute();
                    }
                    catch (Exception exception) {
                        try {
                            cr.warn("Error could not store char skills: " + exception);
                            cr.error("", (Throwable)exception);
                        }
                        catch (Throwable throwable) {
                            DbUtils.closeQuietly((Connection)object2, object);
                            throw throwable;
                        }
                        DbUtils.closeQuietly((Connection)object2, (Statement)object);
                    }
                }
                DbUtils.closeQuietly((Connection)object2, (Statement)object);
            }
            object2 = new PledgeSkillListAdd(skill.getId(), skill.getLevel());
            object = new PledgeSkillList(this);
            for (UnitMember unitMember : this) {
                Player player;
                if (!unitMember.isOnline() || (player = unitMember.getPlayer()) == null) continue;
                this.a(player, skill);
                player.sendPacket(new IStaticPacket[]{object2, object});
                player.sendSkillList();
            }
        }
        return skill2;
    }

    public void addSkillsQuietly(Player player) {
        for (Skill skill : this._skills.values()) {
            this.a(player, skill);
        }
        SubUnit subUnit = this.getSubUnit(player.getPledgeType());
        if (subUnit != null) {
            subUnit.addSkillsQuietly(player);
        }
    }

    public void enableSkills(Player player) {
        if (player.isOlyParticipant()) {
            return;
        }
        for (Skill skill : this._skills.values()) {
            if (skill.getMinPledgeClass() > player.getPledgeClass()) continue;
            player.removeUnActiveSkill(skill);
        }
        SubUnit subUnit = this.getSubUnit(player.getPledgeType());
        if (subUnit != null) {
            subUnit.enableSkills(player);
        }
    }

    public void disableSkills(Player player) {
        for (Skill skill : this._skills.values()) {
            player.addUnActiveSkill(skill);
        }
        SubUnit subUnit = this.getSubUnit(player.getPledgeType());
        if (subUnit != null) {
            subUnit.disableSkills(player);
        }
    }

    private void a(Player player, Skill skill) {
        if (skill.getMinPledgeClass() <= player.getPledgeClass()) {
            player.addSkill(skill, false);
            if (this.oX < 0 || player.isOlyParticipant()) {
                player.addUnActiveSkill(skill);
            }
        }
    }

    public void removeSkill(int n) {
        this._skills.remove(n);
        PledgeSkillListAdd pledgeSkillListAdd = new PledgeSkillListAdd(n, 0);
        for (UnitMember unitMember : this) {
            Player player = unitMember.getPlayer();
            if (player == null || !player.isOnline()) continue;
            player.removeSkillById(n);
            player.sendPacket((IStaticPacket)pledgeSkillListAdd);
            player.sendSkillList();
        }
    }

    public void broadcastSkillListToOnlineMembers() {
        for (UnitMember unitMember : this) {
            Player player = unitMember.getPlayer();
            if (player == null || !player.isOnline()) continue;
            player.sendPacket((IStaticPacket)new PledgeSkillList(this));
            player.sendSkillList();
        }
    }

    public static boolean isAcademy(int n) {
        return n == -1;
    }

    public static boolean isRoyalGuard(int n) {
        return n == 100 || n == 200;
    }

    public static boolean isOrderOfKnights(int n) {
        return n == 1001 || n == 1002 || n == 2001 || n == 2002;
    }

    public int getAffiliationRank(int n) {
        if (Clan.isAcademy(n)) {
            return 9;
        }
        if (Clan.isOrderOfKnights(n)) {
            return 8;
        }
        if (Clan.isRoyalGuard(n)) {
            return 7;
        }
        return 6;
    }

    public final SubUnit getSubUnit(int n) {
        return (SubUnit)this._subUnits.get(n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void addSubUnit(SubUnit subUnit, boolean bl) {
        this._subUnits.put(subUnit.getType(), (Object)subUnit);
        if (bl) {
            this.broadcastToOnlineMembers(new PledgeReceiveSubPledgeCreated(subUnit));
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("INSERT INTO `clan_subpledges` (clan_id,type,leader_id,name) VALUES (?,?,?,?)");
                preparedStatement.setInt(1, this.getClanId());
                preparedStatement.setInt(2, subUnit.getType());
                preparedStatement.setInt(3, subUnit.getLeaderObjectId());
                preparedStatement.setString(4, subUnit.getName());
                preparedStatement.execute();
            }
            catch (Exception exception) {
                try {
                    cr.warn("Could not store clan Sub pledges: " + exception);
                    cr.error("", (Throwable)exception);
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

    public int createSubPledge(Player player, int n, UnitMember unitMember, String string) {
        int n2 = n;
        if ((n = this.getAvailablePledgeTypes(n)) == -128) {
            if (n2 == -1) {
                player.sendPacket((IStaticPacket)SystemMsg.YOUR_CLAN_HAS_ALREADY_ESTABLISHED_A_CLAN_ACADEMY);
            } else {
                player.sendMessage(new CustomMessage("Clan.CantCreateSubUnit", player, new Object[0]));
            }
            return -128;
        }
        switch (n) {
            case -1: {
                break;
            }
            case 100: 
            case 200: {
                if (this.getReputationScore() < Config.ROYAL_SUBUNIT_CRP_PRICE) {
                    player.sendPacket((IStaticPacket)SystemMsg.THE_CLAN_REPUTATION_SCORE_IS_TOO_LOW);
                    return -128;
                }
                this.incReputation(-Config.ROYAL_SUBUNIT_CRP_PRICE, false, "SubunitCreate");
                break;
            }
            case 1001: 
            case 1002: 
            case 2001: 
            case 2002: {
                if (this.getReputationScore() < Config.KNIGHT_SUBUNIT_CRP_PRICE) {
                    player.sendPacket((IStaticPacket)SystemMsg.THE_CLAN_REPUTATION_SCORE_IS_TOO_LOW);
                    return -128;
                }
                this.incReputation(-Config.KNIGHT_SUBUNIT_CRP_PRICE, false, "SubunitCreate");
            }
        }
        this.addSubUnit(new SubUnit(this, n, unitMember, string), true);
        return n;
    }

    public int getAvailablePledgeTypes(int n) {
        if (n == 0) {
            return -128;
        }
        if (this._subUnits.get(n) != null) {
            switch (n) {
                case -1: {
                    return -128;
                }
                case 100: {
                    n = this.getAvailablePledgeTypes(200);
                    break;
                }
                case 200: {
                    return -128;
                }
                case 1001: {
                    n = this.getAvailablePledgeTypes(1002);
                    break;
                }
                case 1002: {
                    n = this.getAvailablePledgeTypes(2001);
                    break;
                }
                case 2001: {
                    n = this.getAvailablePledgeTypes(2002);
                    break;
                }
                case 2002: {
                    return -128;
                }
            }
        }
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void bM() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM `clan_subpledges` WHERE `clan_id`=?");
            preparedStatement.setInt(1, this.getClanId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int n = resultSet.getInt("type");
                int n2 = resultSet.getInt("leader_id");
                String string = resultSet.getString("name");
                SubUnit subUnit = new SubUnit(this, n, n2, string);
                this.addSubUnit(subUnit, false);
            }
        }
        catch (Exception exception) {
            try {
                cr.warn("Could not restore clan SubPledges: " + exception, (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    public int getSubPledgeLimit(int n) {
        int n2 = switch (this._level) {
            case 0 -> Config.LIMIT_CLAN_LEVEL0;
            case 1 -> Config.LIMIT_CLAN_LEVEL1;
            case 2 -> Config.LIMIT_CLAN_LEVEL2;
            case 3 -> Config.LIMIT_CLAN_LEVEL3;
            default -> Config.LIMIT_CLAN_LEVEL_4_AND_HIGH;
        };
        switch (n) {
            case -1: {
                n2 = Config.LIMIT_CLAN_ACADEMY;
                break;
            }
            case 100: 
            case 200: {
                n2 = Config.LIMIT_CLAN_HIGH_UNITS;
                break;
            }
            case 1001: 
            case 1002: 
            case 2001: 
            case 2002: {
                n2 = Config.LIMIT_CLAN_LOW_UNITS;
            }
        }
        return n2;
    }

    public int getUnitMembersSize(int n) {
        if (n == -128 || !this._subUnits.containsKey(n)) {
            return 0;
        }
        return this.getSubUnit(n).size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void bN() {
        if (this._privs == null) {
            this.InitializePrivs();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `privilleges`,`rank` FROM `clan_privs` WHERE `clan_id`=?");
            preparedStatement.setInt(1, this.getClanId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int n = resultSet.getInt("rank");
                int n2 = resultSet.getInt("privilleges");
                RankPrivs rankPrivs = (RankPrivs)this._privs.get(n);
                if (rankPrivs != null) {
                    rankPrivs.setPrivs(n2);
                    continue;
                }
                cr.warn("Invalid rank value (" + n + "), please check clan_privs table");
            }
        }
        catch (Exception exception) {
            try {
                cr.warn("Could not restore clan privs by rank: " + exception);
                cr.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    public void InitializePrivs() {
        for (int i = 1; i <= 9; ++i) {
            this._privs.put(i, (Object)new RankPrivs(i, 0, 0));
        }
    }

    public void updatePrivsForRank(int n) {
        for (UnitMember unitMember : this) {
            if (!unitMember.isOnline() || unitMember.getPlayer() == null || unitMember.getPlayer().getPowerGrade() != n || unitMember.getPlayer().isClanLeader()) continue;
            unitMember.getPlayer().sendUserInfo(false);
        }
    }

    public RankPrivs getRankPrivs(int n) {
        if (n < 1 || n > 9) {
            cr.warn("Requested invalid rank value: " + n);
            Thread.dumpStack();
            return null;
        }
        if (this._privs.get(n) == null) {
            cr.warn("Request of rank before init: " + n);
            Thread.dumpStack();
            this.setRankPrivs(n, 0);
        }
        return (RankPrivs)this._privs.get(n);
    }

    public int countMembersByRank(int n) {
        int n2 = 0;
        for (UnitMember unitMember : this) {
            if (unitMember.getPowerGrade() != n) continue;
            ++n2;
        }
        return n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setRankPrivs(int n, int n2) {
        if (n < 1 || n > 9) {
            cr.warn("Requested set of invalid rank value: " + n);
            Thread.dumpStack();
            return;
        }
        if (this._privs.get(n) != null) {
            ((RankPrivs)this._privs.get(n)).setPrivs(n2);
        } else {
            this._privs.put(n, (Object)new RankPrivs(n, this.countMembersByRank(n), n2));
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("REPLACE INTO `clan_privs`(`clan_id`,`rank`,`privilleges`) VALUES (?,?,?)");
            preparedStatement.setInt(1, this.getClanId());
            preparedStatement.setInt(2, n);
            preparedStatement.setInt(3, n2);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                cr.warn("Could not store clan privs for rank: " + exception);
                cr.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public final RankPrivs[] getAllRankPrivs() {
        if (this._privs == null) {
            return new RankPrivs[0];
        }
        return this._privs.values().toArray(new RankPrivs[this._privs.values().size()]);
    }

    public int getWhBonus() {
        return this.oW;
    }

    public void setWhBonus(int n) {
        if (this.oW != -1) {
            mysql.set("UPDATE `clan_data` SET `warehouse`=? WHERE `clan_id`=?", n, this.getClanId());
        }
        this.oW = n;
    }

    public final Collection<SubUnit> getAllSubUnits() {
        return this._subUnits.values();
    }

    public List<L2GameServerPacket> listAll() {
        ArrayList<L2GameServerPacket> arrayList = new ArrayList<L2GameServerPacket>(this._subUnits.size());
        for (SubUnit subUnit : this.getAllSubUnits()) {
            arrayList.add(new PledgeShowMemberListAll(this, subUnit));
        }
        return arrayList;
    }

    public String getNotice() {
        return this.dK;
    }

    public void setNotice(String string) {
        this.dK = string;
    }

    public int getSkillLevel(int n, int n2) {
        Skill skill = (Skill)this._skills.get(n);
        return skill == null ? n2 : skill.getLevel();
    }

    public int getSkillLevel(int n) {
        return this.getSkillLevel(n, -1);
    }

    @Override
    public Iterator<UnitMember> iterator() {
        ArrayList<Iterator<Iterator<UnitMember>>> arrayList = new ArrayList<Iterator<Iterator<UnitMember>>>(this._subUnits.size());
        for (SubUnit subUnit : this._subUnits.values()) {
            arrayList.add(subUnit.getUnitMembers().iterator());
        }
        return new JoinedIterator<UnitMember>(arrayList);
    }

    public boolean isPlacedForDisband() {
        return this.cI != 0L;
    }

    public void placeForDisband() {
        this.cI = System.currentTimeMillis() + Config.CLAN_DISBAND_TIME;
        this.updateClanInDB();
    }

    public void unPlaceDisband() {
        this.cI = 0L;
        this.cJ = System.currentTimeMillis() + Config.CLAN_DISBAND_PENALTY;
        this.updateClanInDB();
    }

    public long getDisbandEndTime() {
        return this.cI;
    }

    public void setDisbandEndTime(long l) {
        this.cI = l;
    }

    public long getDisbandPenaltyTime() {
        return this.cJ;
    }

    public void setDisbandPenaltyTime(long l) {
        this.cJ = l;
    }

    public int getCustomPoints() {
        return this.oY;
    }

    public void setCustomPoints(int n) {
        this.oY = n;
        this.updateClanInDB();
    }

    public static void addClanLeaderSkills(Player player) {
        for (SkillLearn skillLearn : SkillAcquireHolder.getInstance().getAvailableSkills(player, AcquireType.CLAN_LEADER)) {
            Skill skill = SkillTable.getInstance().getInfo(skillLearn.getId(), skillLearn.getLevel());
            if (skill == null || (skill.getId() == 326 || skill.getId() == 327) && !player.isNoble() || player.getSkillLevel(skill.getId()) >= skill.getLevel()) continue;
            player.addSkill(skill, false);
        }
    }

    public static void removeClanLeaderSkills(Player player) {
        for (SkillLearn skillLearn : SkillAcquireHolder.getInstance().getAllSkillLearn(player, player.getClassId(), AcquireType.CLAN_LEADER)) {
            Skill skill = SkillTable.getInstance().getInfo(skillLearn.getId(), skillLearn.getLevel());
            if (skill == null) continue;
            player.removeSkill(skill, false);
        }
    }

    public boolean addToClan(Player player, int n) {
        player.sendPacket((IStaticPacket)new JoinPledge(this.getClanId()));
        SubUnit subUnit = this.getSubUnit(n);
        if (subUnit == null) {
            return false;
        }
        UnitMember unitMember = new UnitMember(this, player.getName(), player.getTitle(), player.getLevel(), player.getClassId().getId(), player.getObjectId(), n, player.getPowerGrade(), player.getApprentice(), player.getSex(), -128);
        subUnit.addUnitMember(unitMember);
        player.setPledgeType(n);
        player.setClan(this);
        unitMember.setPlayerInstance(player, false);
        if (n == -1) {
            player.setLvlJoinedAcademy(player.getLevel());
        }
        unitMember.setPowerGrade(this.getAffiliationRank(player.getPledgeType()));
        this.broadcastToOtherOnlineMembers(new PledgeShowMemberListAdd(unitMember), player);
        this.broadcastToOnlineMembers(new L2GameServerPacket[]{new SystemMessage(SystemMsg.S1_HAS_JOINED_THE_CLAN).addString(player.getName()), new PledgeShowInfoUpdate(this), new ExPledgeCount(this)});
        player.sendPacket((IStaticPacket)SystemMsg.ENTERED_THE_CLAN);
        player.sendPacket(player.getClan().listAll());
        player.setLeaveClanTime(0L);
        player.updatePledgeClass();
        this.addSkillsQuietly(player);
        player.sendPacket((IStaticPacket)new PledgeSkillList(this));
        player.sendSkillList();
        OneDayRewardHolder.getInstance().fireRequirements(player, null, JoinClanRequirement.class);
        EventHolder.getInstance().findEvent(player);
        player.broadcastUserInfo(true, new UserInfoType[0]);
        player.sendPacket((IStaticPacket)new JoinPledge(this.getClanId()));
        player.broadcastCharInfo();
        player.store(false);
        return true;
    }

    private static class ClanReputationComparator
    implements Comparator<Clan> {
        private ClanReputationComparator() {
        }

        @Override
        public int compare(Clan clan, Clan clan2) {
            if (clan == null || clan2 == null) {
                return 0;
            }
            return clan2.getReputationScore() - clan.getReputationScore();
        }
    }
}
