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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.pledge.entry.PledgeApplicantInfo;
import l2.gameserver.model.pledge.entry.PledgeRecruitInfo;
import l2.gameserver.model.pledge.entry.PledgeWaitingInfo;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClanEntryManager {
    protected static final Logger LOG = LoggerFactory.getLogger(ClanEntryManager.class);
    private static final Map<Integer, PledgeWaitingInfo> ah = new ConcurrentHashMap<Integer, PledgeWaitingInfo>();
    private static final Map<Integer, PledgeRecruitInfo> ai = new ConcurrentHashMap<Integer, PledgeRecruitInfo>();
    private static final Map<Integer, Map<Integer, PledgeApplicantInfo>> aj = new ConcurrentHashMap<Integer, Map<Integer, PledgeApplicantInfo>>();
    private static final Map<Integer, ScheduledFuture<?>> ak = new ConcurrentHashMap();
    private static final Map<Integer, ScheduledFuture<?>> al = new ConcurrentHashMap();
    private static final String cf = "REPLACE INTO `clan_entry_applicant` VALUES (?, ?, ?, ?)";
    private static final String cg = "DELETE FROM `clan_entry_applicant` WHERE `charId` = ? AND `clanId` = ?";
    private static final String ch = "DELETE FROM `clan_entry_applicant` WHERE `charId` = ?";
    private static final String ci = "INSERT INTO `clan_entry_waiting_list` VALUES (?, ?, ?, ?, ?)";
    private static final String cj = "DELETE FROM `clan_entry_waiting_list` WHERE `char_id` = ?";
    private static final String ck = "INSERT INTO `clan_entry_recruit` VALUES (?, ?, ?, ?, ?, ?)";
    private static final String cl = "UPDATE `clan_entry_recruit` SET `karma` = ?, `information` = ?, `detailed_information` = ?, `application_type` = ?, `recruit_type` = ? WHERE `clan_id` = ?";
    private static final String cm = "DELETE FROM `clan_entry_recruit` WHERE `clan_id` = ?";
    private static final List<Comparator<PledgeWaitingInfo>> at = Arrays.asList(null, Comparator.comparing(PledgeWaitingInfo::getPlayerName), Comparator.comparingInt(PledgeWaitingInfo::getKarma), Comparator.comparingInt(PledgeWaitingInfo::getPlayerLvl), Comparator.comparingInt(PledgeWaitingInfo::getPlayerClassId));
    private static final List<Comparator<PledgeRecruitInfo>> au = Arrays.asList(null, Comparator.comparing(PledgeRecruitInfo::getClanName), Comparator.comparing(PledgeRecruitInfo::getClanLeaderName), Comparator.comparingInt(PledgeRecruitInfo::getClanLevel), Comparator.comparingInt(PledgeRecruitInfo::getKarma));

    protected ClanEntryManager() {
        this.load();
        CharacterDAO.getInstance().getCharacterDeleteListenerList().add(this::onCharacterDelete);
    }

    private void load() {
        ResultSet resultSet;
        Statement statement;
        Connection connection;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            try {
                statement = connection.createStatement();
                try {
                    resultSet = statement.executeQuery("SELECT * FROM `clan_entry_recruit`");
                    try {
                        while (resultSet.next()) {
                            int n2 = resultSet.getInt("clan_id");
                            ai.put(n2, new PledgeRecruitInfo(n2, resultSet.getInt("karma"), resultSet.getString("information"), resultSet.getString("detailed_information"), resultSet.getInt("application_type"), resultSet.getInt("recruit_type")));
                            if (ClanTable.getInstance().getClan(n2) != null) continue;
                            this.removeFromClanList(n2);
                        }
                        LOG.info(this.getClass().getSimpleName() + ": Loaded " + ai.size() + " clan entry.");
                    }
                    finally {
                        if (resultSet != null) {
                            resultSet.close();
                        }
                    }
                }
                finally {
                    if (statement != null) {
                        statement.close();
                    }
                }
            }
            finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
        catch (Exception exception) {
            LOG.error(this.getClass().getSimpleName() + ": Failed to load: ", (Throwable)exception);
        }
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            try {
                statement = connection.createStatement();
                try {
                    resultSet = statement.executeQuery("SELECT `pwl`.`char_id`, `pwl`.`karma`, `cs`.`class_id`, `cs`.`level`, `c`.`char_name` FROM `clan_entry_waiting_list` AS `pwl` LEFT JOIN `characters` AS `c` ON `c`.`obj_Id` = `pwl`.`char_id` LEFT JOIN `character_subclasses` AS `cs` ON `cs`.`char_obj_id` = `pwl`.`char_id` AND `cs`.`active` = 1");
                    try {
                        while (resultSet.next()) {
                            ah.put(resultSet.getInt("char_id"), new PledgeWaitingInfo(resultSet.getInt("char_id"), resultSet.getInt("level"), resultSet.getInt("karma"), resultSet.getInt("class_id"), resultSet.getString("char_name")));
                        }
                        LOG.info(this.getClass().getSimpleName() + ": Loaded " + ah.size() + " players in waiting list.");
                    }
                    finally {
                        if (resultSet != null) {
                            resultSet.close();
                        }
                    }
                }
                finally {
                    if (statement != null) {
                        statement.close();
                    }
                }
            }
            finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
        catch (Exception exception) {
            LOG.warn(this.getClass().getSimpleName() + ": Failed to load: ", (Throwable)exception);
        }
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            try {
                statement = connection.createStatement();
                try {
                    resultSet = statement.executeQuery("SELECT `pwl`.`charId`, `pwl`.`clanId`, `pwl`.`karma`, `pwl`.`message`, `cs`.`level`, `c`.char_name FROM `clan_entry_applicant` AS `pwl` LEFT JOIN `characters` AS `c` ON `c`.`obj_Id` = `pwl`.`charId` LEFT JOIN `character_subclasses` AS `cs` ON `cs`.`char_obj_id` = `pwl`.`charId` AND `cs`.`active` = 1");
                    try {
                        while (resultSet.next()) {
                            aj.computeIfAbsent(resultSet.getInt("clanId"), n -> new ConcurrentHashMap()).put(resultSet.getInt("charId"), new PledgeApplicantInfo(resultSet.getInt("charId"), resultSet.getString("char_name"), resultSet.getInt("level"), resultSet.getInt("karma"), resultSet.getInt("clanId"), resultSet.getString("message")));
                        }
                        LOG.info(this.getClass().getSimpleName() + ": Loaded " + aj.size() + " player applications.");
                    }
                    finally {
                        if (resultSet != null) {
                            resultSet.close();
                        }
                    }
                }
                finally {
                    if (statement != null) {
                        statement.close();
                    }
                }
            }
            finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
        catch (Exception exception) {
            LOG.warn(this.getClass().getSimpleName() + ": Failed to load: ", (Throwable)exception);
        }
    }

    public Map<Integer, PledgeWaitingInfo> getWaitingList() {
        return ah;
    }

    public Map<Integer, PledgeRecruitInfo> getClanList() {
        return ai;
    }

    public Map<Integer, Map<Integer, PledgeApplicantInfo>> getApplicantList() {
        return aj;
    }

    public Map<Integer, PledgeApplicantInfo> getApplicantListForClan(int n) {
        return aj.getOrDefault(n, Collections.emptyMap());
    }

    public PledgeApplicantInfo getPlayerApplication(int n, int n2) {
        return (PledgeApplicantInfo)aj.getOrDefault(n, Collections.emptyMap()).get(n2);
    }

    public void removePlayerApplication(int n, int n2) {
        Map<Integer, PledgeApplicantInfo> map = aj.get(n);
        try (Connection connection = DatabaseFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(cg);){
            preparedStatement.setInt(1, n2);
            preparedStatement.setInt(2, n);
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            LOG.warn(exception.getMessage(), (Throwable)exception);
        }
        if (map != null) {
            map.remove(n2);
        }
    }

    public void removeAllPlayerApplication(int n) {
        try (Connection connection = DatabaseFactory.getInstance().getConnection();
             PreparedStatement object = connection.prepareStatement(ch);){
            object.setInt(1, n);
            object.executeUpdate();
        }
        catch (Exception exception) {
            LOG.warn(exception.getMessage(), (Throwable)exception);
        }
        for (Map.Entry entry : aj.entrySet()) {
            ((Map)entry.getValue()).remove(n);
        }
    }

    public boolean addPlayerApplicationToClan(int n2, PledgeApplicantInfo pledgeApplicantInfo) {
        if (!al.containsKey(pledgeApplicantInfo.getPlayerId())) {
            aj.computeIfAbsent(n2, n -> new ConcurrentHashMap()).put(pledgeApplicantInfo.getPlayerId(), pledgeApplicantInfo);
            try (Connection connection = DatabaseFactory.getInstance().getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(cf);){
                preparedStatement.setInt(1, pledgeApplicantInfo.getPlayerId());
                preparedStatement.setInt(2, pledgeApplicantInfo.getRequestClanId());
                preparedStatement.setInt(3, pledgeApplicantInfo.getKarma());
                preparedStatement.setString(4, pledgeApplicantInfo.getMessage());
                preparedStatement.executeUpdate();
            }
            catch (Exception exception) {
                LOG.warn(exception.getMessage(), (Throwable)exception);
            }
            return true;
        }
        return false;
    }

    public Integer getClanIdForPlayerApplication(int n) {
        for (Map.Entry<Integer, Map<Integer, PledgeApplicantInfo>> entry : aj.entrySet()) {
            if (!entry.getValue().containsKey(n)) continue;
            return entry.getKey();
        }
        return 0;
    }

    public boolean addToWaitingList(int n, PledgeWaitingInfo pledgeWaitingInfo) {
        if (!al.containsKey(n)) {
            try (Connection connection = DatabaseFactory.getInstance().getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(ci);){
                preparedStatement.setInt(1, pledgeWaitingInfo.getPlayerId());
                preparedStatement.setInt(2, pledgeWaitingInfo.getPlayerLvl());
                preparedStatement.setInt(3, pledgeWaitingInfo.getKarma());
                preparedStatement.setInt(4, pledgeWaitingInfo.getPlayerClassId());
                preparedStatement.setString(5, pledgeWaitingInfo.getPlayerName());
                preparedStatement.executeUpdate();
            }
            catch (Exception exception) {
                LOG.warn(exception.getMessage(), (Throwable)exception);
            }
            ah.put(n, pledgeWaitingInfo);
            return true;
        }
        return false;
    }

    public boolean removeFromWaitingList(int n, boolean bl) {
        if (ah.containsKey(n)) {
            try (Connection connection = DatabaseFactory.getInstance().getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(cj);){
                preparedStatement.setInt(1, n);
                preparedStatement.executeUpdate();
            }
            catch (Exception exception) {
                LOG.warn(exception.getMessage(), (Throwable)exception);
            }
            ah.remove(n);
            if (!bl) {
                this.e(n);
            }
            return true;
        }
        return false;
    }

    public boolean addToClanList(int n, PledgeRecruitInfo pledgeRecruitInfo) {
        if (!ai.containsKey(n) && !ak.containsKey(n)) {
            try (Connection connection = DatabaseFactory.getInstance().getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(ck);){
                preparedStatement.setInt(1, pledgeRecruitInfo.getClanId());
                preparedStatement.setInt(2, pledgeRecruitInfo.getKarma());
                preparedStatement.setString(3, pledgeRecruitInfo.getInformation());
                preparedStatement.setString(4, pledgeRecruitInfo.getDetailedInformation());
                preparedStatement.setInt(5, pledgeRecruitInfo.getApplicationType());
                preparedStatement.setInt(6, pledgeRecruitInfo.getRecruitType());
                preparedStatement.executeUpdate();
            }
            catch (Exception exception) {
                LOG.warn(exception.getMessage(), (Throwable)exception);
            }
            ai.put(n, pledgeRecruitInfo);
            return true;
        }
        return false;
    }

    public boolean updateClanList(int n, PledgeRecruitInfo pledgeRecruitInfo) {
        if (ai.containsKey(n) && !ak.containsKey(n)) {
            try (Connection connection = DatabaseFactory.getInstance().getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(cl);){
                preparedStatement.setInt(1, pledgeRecruitInfo.getKarma());
                preparedStatement.setString(2, pledgeRecruitInfo.getInformation());
                preparedStatement.setString(3, pledgeRecruitInfo.getDetailedInformation());
                preparedStatement.setInt(4, pledgeRecruitInfo.getApplicationType());
                preparedStatement.setInt(5, pledgeRecruitInfo.getRecruitType());
                preparedStatement.setInt(6, pledgeRecruitInfo.getClanId());
                preparedStatement.executeUpdate();
            }
            catch (Exception exception) {
                LOG.warn(exception.getMessage(), (Throwable)exception);
            }
            return ai.replace(n, pledgeRecruitInfo) != null;
        }
        return false;
    }

    public void removeFromClanList(int n) {
        if (ai.containsKey(n)) {
            try (Connection connection = DatabaseFactory.getInstance().getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(cm);){
                preparedStatement.setInt(1, n);
                preparedStatement.executeUpdate();
            }
            catch (Exception exception) {
                LOG.warn(exception.getMessage(), (Throwable)exception);
            }
            ai.remove(n);
            this.f(n);
        }
    }

    public List<PledgeWaitingInfo> getSortedWaitingList(int n, int n2, int n3, int n4, boolean bl) {
        int n5 = Util.constrain(n4, 1, at.size() - 1);
        ArrayList<PledgeWaitingInfo> arrayList = new ArrayList<PledgeWaitingInfo>();
        for (PledgeWaitingInfo pledgeWaitingInfo : ah.values()) {
            if (pledgeWaitingInfo.getPlayerLvl() < n || pledgeWaitingInfo.getPlayerLvl() > n2) continue;
            arrayList.add(pledgeWaitingInfo);
        }
        arrayList.sort(bl ? at.get(n5).reversed() : at.get(n5));
        return arrayList;
    }

    public List<PledgeWaitingInfo> queryWaitingListByName(String string) {
        ArrayList<PledgeWaitingInfo> arrayList = new ArrayList<PledgeWaitingInfo>();
        for (PledgeWaitingInfo pledgeWaitingInfo : ah.values()) {
            if (!pledgeWaitingInfo.getPlayerName().toLowerCase().contains(string)) continue;
            arrayList.add(pledgeWaitingInfo);
        }
        return arrayList;
    }

    public List<PledgeRecruitInfo> getSortedClanListByName(String string, int n) {
        ArrayList<PledgeRecruitInfo> arrayList = new ArrayList<PledgeRecruitInfo>();
        if (n == 1) {
            for (PledgeRecruitInfo pledgeRecruitInfo : ai.values()) {
                if (!pledgeRecruitInfo.getClanName().toLowerCase().contains(string)) continue;
                arrayList.add(pledgeRecruitInfo);
            }
        } else {
            for (PledgeRecruitInfo pledgeRecruitInfo : ai.values()) {
                if (!pledgeRecruitInfo.getClanLeaderName().toLowerCase().contains(string)) continue;
                arrayList.add(pledgeRecruitInfo);
            }
        }
        return arrayList;
    }

    public PledgeRecruitInfo getClanById(int n) {
        return ai.get(n);
    }

    public boolean isClanRegistered(int n) {
        return ai.get(n) != null;
    }

    public boolean isPlayerRegistered(int n) {
        return ah.get(n) != null;
    }

    public List<PledgeRecruitInfo> getUnSortedClanList() {
        return new ArrayList<PledgeRecruitInfo>(ai.values());
    }

    public List<PledgeRecruitInfo> getSortedClanList(int n, int n2, int n3, boolean bl) {
        int n4 = Util.constrain(n3, 1, au.size() - 1);
        ArrayList<PledgeRecruitInfo> arrayList = new ArrayList<PledgeRecruitInfo>(ai.values());
        for (int i = 0; i < arrayList.size(); ++i) {
            PledgeRecruitInfo pledgeRecruitInfo = (PledgeRecruitInfo)arrayList.get(i);
            if (!(n < 0 && n2 >= 0 && n2 != pledgeRecruitInfo.getKarma() || n >= 0 && n2 < 0 && n != (pledgeRecruitInfo.getClan() != null ? pledgeRecruitInfo.getClanLevel() : 0)) && (n < 0 || n2 < 0 || n == (pledgeRecruitInfo.getClan() != null ? pledgeRecruitInfo.getClanLevel() : 0) && n2 == pledgeRecruitInfo.getKarma())) continue;
            arrayList.remove(i--);
        }
        Collections.sort(arrayList, bl ? au.get(n4).reversed() : au.get(n4));
        return arrayList;
    }

    public long getPlayerLockTime(int n) {
        return al.get(n) == null ? 0L : al.get(n).getDelay(TimeUnit.MINUTES);
    }

    public long getClanLockTime(int n) {
        return ak.get(n) == null ? 0L : ak.get(n).getDelay(TimeUnit.MINUTES);
    }

    private void e(int n) {
        if (Config.ENABLE_CLAN_ENTRY_PLAYER_LOCK_TIME > 0) {
            al.put(n, ThreadPoolManager.getInstance().schedule(() -> al.remove(n), TimeUnit.MINUTES.toMillis(Config.ENABLE_CLAN_ENTRY_PLAYER_LOCK_TIME)));
        }
    }

    private void f(int n) {
        if (Config.ENABLE_CLAN_ENTRY_CLAN_LOCK_TIME > 0) {
            ak.put(n, ThreadPoolManager.getInstance().schedule(() -> ak.remove(n), TimeUnit.MINUTES.toMillis(Config.ENABLE_CLAN_ENTRY_CLAN_LOCK_TIME)));
        }
    }

    public static ClanEntryManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void onCharacterDelete(int n) {
        this.removeAllPlayerApplication(n);
        this.removeFromWaitingList(n, true);
    }

    private static class SingletonHolder {
        protected static final ClanEntryManager INSTANCE = new ClanEntryManager();

        private SingletonHolder() {
        }
    }
}
