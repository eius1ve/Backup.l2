/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.CHashIntObjectMap
 *  org.napile.primitive.maps.impl.CTreeIntObjectMap
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.pledge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExSubPledgeSkillAdd;
import l2.gameserver.tables.SkillTable;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.CHashIntObjectMap;
import org.napile.primitive.maps.impl.CTreeIntObjectMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SubUnit {
    private static final Logger cs = LoggerFactory.getLogger(SubUnit.class);
    private IntObjectMap<Skill> _skills = new CTreeIntObjectMap();
    private IntObjectMap<UnitMember> m = new CHashIntObjectMap();
    private int _type;
    private int pe;
    private UnitMember a;
    private int pf;
    private String _name;
    private Clan a;

    public SubUnit(Clan clan, int n, UnitMember unitMember, String string) {
        this.a = clan;
        this._type = n;
        this._name = string;
        this.setLeader(unitMember, false);
    }

    public SubUnit(Clan clan, int n, int n2, String string) {
        this.a = clan;
        this._type = n;
        this.pe = n2;
        this._name = string;
    }

    public int getType() {
        return this._type;
    }

    public String getName() {
        return this._name;
    }

    public UnitMember getLeader() {
        return this.a;
    }

    public boolean isUnitMember(int n) {
        return this.m.containsKey(n);
    }

    public void addUnitMember(UnitMember unitMember) {
        this.m.put(unitMember.getObjectId(), (Object)unitMember);
    }

    public UnitMember getUnitMember(int n) {
        if (n == 0) {
            return null;
        }
        return (UnitMember)this.m.get(n);
    }

    public UnitMember getUnitMember(String string) {
        for (UnitMember unitMember : this.getUnitMembers()) {
            if (!unitMember.getName().equalsIgnoreCase(string)) continue;
            return unitMember;
        }
        return null;
    }

    public void removeUnitMember(int n) {
        UnitMember unitMember = (UnitMember)this.m.remove(n);
        if (unitMember == null) {
            return;
        }
        if (n == this.getLeaderObjectId()) {
            this.setLeader(null, true);
        }
        if (unitMember.hasSponsor()) {
            this.a.getAnyMember(unitMember.getSponsor()).setApprentice(0);
        }
        SubUnit.a(unitMember);
        unitMember.setPlayerInstance(null, true);
    }

    public void replace(int n, int n2) {
        SubUnit subUnit = this.a.getSubUnit(n2);
        if (subUnit == null) {
            return;
        }
        UnitMember unitMember = (UnitMember)this.m.remove(n);
        if (unitMember == null) {
            return;
        }
        unitMember.setPledgeType(n2);
        subUnit.addUnitMember(unitMember);
        if (unitMember.getPowerGrade() > 5) {
            unitMember.setPowerGrade(this.a.getAffiliationRank(unitMember.getPledgeType()));
        }
    }

    public int getLeaderObjectId() {
        return this.a == null ? 0 : this.a.getObjectId();
    }

    public int size() {
        return this.m.size();
    }

    public Collection<UnitMember> getUnitMembers() {
        return this.m.values();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateDbLeader(UnitMember unitMember) {
        if (this.getType() == 0) {
            this.pf = unitMember != this.a ? unitMember.getObjectId() : 0;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `clan_subpledges` SET `leader_id`=? WHERE `clan_id`=? and `type`=?");
            preparedStatement.setInt(1, unitMember.getObjectId());
            preparedStatement.setInt(2, this.a.getClanId());
            preparedStatement.setInt(3, this._type);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                cs.error("Exception: " + exception, (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public void setLeader(UnitMember unitMember, boolean bl) {
        UnitMember unitMember2 = this.a;
        if (unitMember2 != null) {
            unitMember2.setLeaderOf(-128);
        }
        this.a = unitMember;
        int n = this.pe = unitMember == null ? 0 : unitMember.getObjectId();
        if (unitMember != null) {
            unitMember.setLeaderOf(this._type);
        }
        if (bl) {
            this.updateDbLeader(this.a);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setName(String string, boolean bl) {
        this._name = string;
        if (bl) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("UPDATE `clan_subpledges` SET `name`=? WHERE `clan_id`=? and `type`=?");
                preparedStatement.setString(1, this._name);
                preparedStatement.setInt(2, this.a.getClanId());
                preparedStatement.setInt(3, this._type);
                preparedStatement.execute();
            }
            catch (Exception exception) {
                try {
                    cs.error("Exception: " + exception, (Throwable)exception);
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

    public String getLeaderName() {
        return this.a == null ? "" : this.a.getName();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Skill addSkill(Skill skill, boolean bl) {
        Skill skill2;
        block8: {
            Object object;
            skill2 = null;
            if (skill == null) break block8;
            skill2 = (Skill)this._skills.put(skill.getId(), (Object)skill);
            if (bl) {
                Object object2;
                block7: {
                    object = null;
                    object2 = null;
                    try {
                        object = DatabaseFactory.getInstance().getConnection();
                        if (skill2 != null) {
                            object2 = object.prepareStatement("UPDATE `clan_subpledges_skills` SET `skill_level`=? WHERE `skill_id`=? AND `clan_id`=? AND `type`=?");
                            object2.setInt(1, skill.getLevel());
                            object2.setInt(2, skill2.getId());
                            object2.setInt(3, this.a.getClanId());
                            object2.setInt(4, this._type);
                            object2.execute();
                            break block7;
                        }
                        object2 = object.prepareStatement("INSERT INTO `clan_subpledges_skills` (`clan_id`,`type`,`skill_id`,`skill_level`) VALUES (?,?,?,?)");
                        object2.setInt(1, this.a.getClanId());
                        object2.setInt(2, this._type);
                        object2.setInt(3, skill.getId());
                        object2.setInt(4, skill.getLevel());
                        object2.execute();
                    }
                    catch (Exception exception) {
                        try {
                            cs.warn("Exception: " + exception, (Throwable)exception);
                        }
                        catch (Throwable throwable) {
                            DbUtils.closeQuietly((Connection)object, object2);
                            throw throwable;
                        }
                        DbUtils.closeQuietly((Connection)object, (Statement)object2);
                    }
                }
                DbUtils.closeQuietly((Connection)object, (Statement)object2);
            }
            object = new ExSubPledgeSkillAdd(this._type, skill.getId(), skill.getLevel());
            for (UnitMember unitMember : this.a) {
                Player player;
                if (!unitMember.isOnline() || (player = unitMember.getPlayer()) == null) continue;
                player.sendPacket((IStaticPacket)object);
                if (player.getPledgeType() != this._type) continue;
                this.a(player, skill);
            }
        }
        return skill2;
    }

    public int getNextLeaderObjectId() {
        return this.pf;
    }

    public void addSkillsQuietly(Player player) {
        for (Skill skill : this._skills.values()) {
            this.a(player, skill);
        }
    }

    public void enableSkills(Player player) {
        for (Skill skill : this._skills.values()) {
            if (skill.getMinRank() > player.getPledgeClass()) continue;
            player.removeUnActiveSkill(skill);
        }
    }

    public void disableSkills(Player player) {
        for (Skill skill : this._skills.values()) {
            player.addUnActiveSkill(skill);
        }
    }

    private void a(Player player, Skill skill) {
        if (skill.getMinRank() <= player.getPledgeClass()) {
            player.addSkill(skill, false);
            if (this.a.getReputationScore() < 0 || player.isOlyParticipant()) {
                player.addUnActiveSkill(skill);
            }
        }
    }

    public Collection<Skill> getSkills() {
        return this._skills.values();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void a(UnitMember unitMember) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `characters` SET `clanid`=0, `pledge_type`=?, `pledge_rank`=0, `lvl_joined_academy`=0, `apprentice`=0, `title`='', `leaveclan`=? WHERE `obj_Id`=?");
            preparedStatement.setInt(1, -128);
            preparedStatement.setLong(2, System.currentTimeMillis() / 1000L);
            preparedStatement.setInt(3, unitMember.getObjectId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                cs.warn("Exception: " + exception, (Throwable)exception);
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
    public void restore() {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block6: {
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                Object object;
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT `c`.`char_name` AS `char_name`,`s`.`level` AS `level`,`s`.`class_id` AS `classid`,`c`.`obj_Id` AS `obj_id`,`c`.`title` AS `title`,`c`.`pledge_rank` AS `pledge_rank`,`c`.`apprentice` AS `apprentice`, `c`.`sex` AS `sex` FROM `characters` `c` LEFT JOIN `character_subclasses` `s` ON (`s`.`char_obj_id` = `c`.`obj_Id` AND `s`.`isBase` = '1') WHERE `c`.`clanid`=? AND `c`.`pledge_type`=? ORDER BY `c`.`lastaccess` DESC");
                preparedStatement.setInt(1, this.a.getClanId());
                preparedStatement.setInt(2, this._type);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    object = new UnitMember(this.a, resultSet.getString("char_name"), resultSet.getString("title"), resultSet.getInt("level"), resultSet.getInt("classid"), resultSet.getInt("obj_Id"), this._type, resultSet.getInt("pledge_rank"), resultSet.getInt("apprentice"), resultSet.getInt("sex"), -128);
                    this.addUnitMember((UnitMember)object);
                }
                if (this._type == -1) break block6;
                object = this.a.getSubUnit(0);
                UnitMember unitMember = ((SubUnit)object).getUnitMember(this.pe);
                if (unitMember != null) {
                    this.setLeader(unitMember, false);
                    break block6;
                }
                if (this._type != 0) break block6;
                cs.error("Clan " + this._name + " have no leader!");
            }
            catch (Exception exception) {
                try {
                    cs.warn("Error while restoring clan members for clan: " + this.a.getClanId() + " " + exception, (Throwable)exception);
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void restoreSkills() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `skill_id`,`skill_level` FROM `clan_subpledges_skills` WHERE `clan_id`=? AND `type`=?");
            preparedStatement.setInt(1, this.a.getClanId());
            preparedStatement.setInt(2, this._type);
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
                cs.warn("Exception: " + exception, (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    public int getSkillLevel(int n, int n2) {
        Skill skill = (Skill)this._skills.get(n);
        return skill == null ? n2 : skill.getLevel();
    }

    public int getSkillLevel(int n) {
        return this.getSkillLevel(n, -1);
    }
}
