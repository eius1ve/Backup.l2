/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.instancemanager;

import gnu.trove.TIntObjectHashMap;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ScheduledFuture;
import javax.xml.parsers.DocumentBuilderFactory;
import l2.commons.dbutils.DbUtils;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.CursedWeapon;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.InventoryUpdate;
import l2.gameserver.network.l2.s2c.ShortCutInit;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CursedWeaponsManager {
    private static final Logger bp = LoggerFactory.getLogger(CursedWeaponsManager.class);
    private static final CursedWeaponsManager a = new CursedWeaponsManager();
    private CursedWeapon[] a;
    private TIntObjectHashMap<CursedWeapon> t = new TIntObjectHashMap();
    private ScheduledFuture<?> O;
    private static final int fN = 60000;

    public static final CursedWeaponsManager getInstance() {
        return a;
    }

    public CursedWeaponsManager() {
        this.a = new CursedWeapon[0];
        if (!Config.ALLOW_CURSED_WEAPONS) {
            return;
        }
        this.load();
        this.restore();
        this.ay();
        this.cancelTask();
        this.O = ThreadPoolManager.getInstance().scheduleAtFixedRate(new RemoveTask(), 60000L, 60000L);
        bp.info("CursedWeaponsManager: Loaded " + this.a.length + " cursed weapon(s).");
    }

    @Deprecated
    public final void reload() {
    }

    private void load() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setIgnoringComments(true);
            File file = new File(Config.DATAPACK_ROOT, "data/cursed_weapons.xml");
            if (!file.exists()) {
                return;
            }
            Document document = documentBuilderFactory.newDocumentBuilder().parse(file);
            for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (!"list".equalsIgnoreCase(node.getNodeName())) continue;
                for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                    if (!"item".equalsIgnoreCase(node2.getNodeName())) continue;
                    NamedNodeMap namedNodeMap = node2.getAttributes();
                    int n = Integer.parseInt(namedNodeMap.getNamedItem("id").getNodeValue());
                    int n2 = Integer.parseInt(namedNodeMap.getNamedItem("skillId").getNodeValue());
                    String string = "Unknown cursed weapon";
                    if (namedNodeMap.getNamedItem("name") != null) {
                        string = namedNodeMap.getNamedItem("name").getNodeValue();
                    } else if (ItemHolder.getInstance().getTemplate(n) != null) {
                        string = ItemHolder.getInstance().getTemplate(n).getName();
                    }
                    if (n == 0) continue;
                    CursedWeapon cursedWeapon = new CursedWeapon(n, n2, string);
                    for (Node node3 = node2.getFirstChild(); node3 != null; node3 = node3.getNextSibling()) {
                        if ("dropRate".equalsIgnoreCase(node3.getNodeName())) {
                            cursedWeapon.setDropRate(Integer.parseInt(node3.getAttributes().getNamedItem("val").getNodeValue()));
                            continue;
                        }
                        if ("duration".equalsIgnoreCase(node3.getNodeName())) {
                            namedNodeMap = node3.getAttributes();
                            cursedWeapon.setDurationMin(Integer.parseInt(namedNodeMap.getNamedItem("min").getNodeValue()));
                            cursedWeapon.setDurationMax(Integer.parseInt(namedNodeMap.getNamedItem("max").getNodeValue()));
                            continue;
                        }
                        if ("durationLost".equalsIgnoreCase(node3.getNodeName())) {
                            cursedWeapon.setDurationLost(Integer.parseInt(node3.getAttributes().getNamedItem("val").getNodeValue()));
                            continue;
                        }
                        if ("disapearChance".equalsIgnoreCase(node3.getNodeName())) {
                            cursedWeapon.setDisapearChance(Integer.parseInt(node3.getAttributes().getNamedItem("val").getNodeValue()));
                            continue;
                        }
                        if ("stageKills".equalsIgnoreCase(node3.getNodeName())) {
                            cursedWeapon.setStageKills(Integer.parseInt(node3.getAttributes().getNamedItem("val").getNodeValue()));
                            continue;
                        }
                        if ("transformationId".equalsIgnoreCase(node3.getNodeName())) {
                            cursedWeapon.setTransformationId(Integer.parseInt(node3.getAttributes().getNamedItem("val").getNodeValue()));
                            continue;
                        }
                        if ("transformationTemplateId".equalsIgnoreCase(node3.getNodeName())) {
                            cursedWeapon.setTransformationTemplateId(Integer.parseInt(node3.getAttributes().getNamedItem("val").getNodeValue()));
                            continue;
                        }
                        if (!"transformationName".equalsIgnoreCase(node3.getNodeName())) continue;
                        cursedWeapon.setTransformationName(node3.getAttributes().getNamedItem("val").getNodeValue());
                    }
                    this.t.put(n, (Object)cursedWeapon);
                }
            }
            this.a = (CursedWeapon[])this.t.getValues((Object[])new CursedWeapon[this.t.size()]);
        }
        catch (Exception exception) {
            bp.error("CursedWeaponsManager: Error parsing cursed_weapons file. " + exception);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void restore() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM `cursed_weapons`");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int n = resultSet.getInt("item_id");
                CursedWeapon cursedWeapon = (CursedWeapon)this.t.get(n);
                if (cursedWeapon != null) {
                    cursedWeapon.setPlayerId(resultSet.getInt("player_id"));
                    cursedWeapon.setPlayerKarma(resultSet.getInt("player_karma"));
                    cursedWeapon.setPlayerPkKills(resultSet.getInt("player_pkkills"));
                    cursedWeapon.setNbKills(resultSet.getInt("nb_kills"));
                    cursedWeapon.setLoc(new Location(resultSet.getInt("x"), resultSet.getInt("y"), resultSet.getInt("z")));
                    cursedWeapon.setEndTime(resultSet.getLong("end_time") * 1000L);
                    cursedWeapon.giveSkillAndUpdateStats();
                    if (cursedWeapon.reActivate()) continue;
                    this.endOfLife(cursedWeapon);
                    continue;
                }
                this.removeFromDb(n);
                bp.warn("CursedWeaponsManager: Unknown cursed weapon " + n + ", deleted");
            }
        }
        catch (Exception exception) {
            try {
                bp.warn("CursedWeaponsManager: Could not restore cursed_weapons data: " + exception);
                bp.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void ay() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM `character_skills` WHERE `skill_id`=?");
            preparedStatement2 = connection.prepareStatement("SELECT `owner_id` FROM `items` WHERE `item_type`=?");
            for (CursedWeapon cursedWeapon : this.a) {
                int n = cursedWeapon.getItemId();
                int n2 = cursedWeapon.getSkillId();
                boolean bl = false;
                preparedStatement.setInt(1, n2);
                preparedStatement.executeUpdate();
                preparedStatement2.setInt(1, n);
                resultSet = preparedStatement2.executeQuery();
                while (resultSet.next()) {
                    int n3 = resultSet.getInt("owner_id");
                    if (!bl) {
                        if (n3 != cursedWeapon.getPlayerId() || cursedWeapon.getPlayerId() == 0) {
                            this.a(n3, n, cursedWeapon);
                            bp.info("CursedWeaponsManager[254]: Player " + n3 + " owns the cursed weapon " + n + " but he shouldn't.");
                            continue;
                        }
                        bl = true;
                        continue;
                    }
                    this.a(n3, n, cursedWeapon);
                    bp.info("CursedWeaponsManager[262]: Player " + n3 + " owns the cursed weapon " + n + " but he shouldn't.");
                }
                if (bl || cursedWeapon.getPlayerId() == 0) continue;
                this.removeFromDb(cursedWeapon.getItemId());
                bp.info("CursedWeaponsManager: Unownered weapon, removing from table...");
            }
        }
        catch (Exception exception) {
            try {
                bp.warn("CursedWeaponsManager: Could not check cursed_weapons data: " + exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(preparedStatement);
                DbUtils.closeQuietly(connection, preparedStatement2, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(preparedStatement);
            DbUtils.closeQuietly(connection, preparedStatement2, resultSet);
            return;
        }
        DbUtils.closeQuietly(preparedStatement);
        DbUtils.closeQuietly(connection, preparedStatement2, resultSet);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(int n, int n2, CursedWeapon cursedWeapon) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM `items` WHERE `owner_id`=? AND `item_type`=?");
            preparedStatement.setInt(1, n);
            preparedStatement.setInt(2, n2);
            preparedStatement.executeUpdate();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("UPDATE `characters` SET `karma`=?, `pkkills`=? WHERE `obj_id`=?");
            preparedStatement.setInt(1, cursedWeapon.getPlayerKarma());
            preparedStatement.setInt(2, cursedWeapon.getPlayerPkKills());
            preparedStatement.setInt(3, n);
            if (preparedStatement.executeUpdate() != 1) {
                bp.warn("Error while updating karma & pkkills for userId " + cursedWeapon.getPlayerId());
            }
            this.removeFromDb(n2);
        }
        catch (SQLException sQLException) {
            try {
                bp.error("", (Throwable)sQLException);
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
    public void removeFromDb(int n) {
        PreparedStatement preparedStatement;
        Connection connection;
        block4: {
            connection = null;
            preparedStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("DELETE FROM `cursed_weapons` WHERE `item_id` = ?");
                preparedStatement.setInt(1, n);
                preparedStatement.executeUpdate();
                if (this.getCursedWeapon(n) == null) break block4;
                this.getCursedWeapon(n).initWeapon();
            }
            catch (SQLException sQLException) {
                try {
                    bp.error("CursedWeaponsManager: Failed to remove data: " + sQLException);
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

    private void cancelTask() {
        if (this.O != null) {
            this.O.cancel(false);
            this.O = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void endOfLife(CursedWeapon cursedWeapon) {
        block11: {
            block12: {
                PreparedStatement preparedStatement;
                Connection connection;
                if (!cursedWeapon.isActivated()) break block12;
                Player player = cursedWeapon.getOnlineOwner();
                if (player != null) {
                    bp.info("CursedWeaponsManager: " + cursedWeapon.getName() + " being removed online from " + player + ".");
                    player.abortAttack(true, true);
                    player.setKarma(cursedWeapon.getPlayerKarma(), true);
                    player.setPkKills(cursedWeapon.getPlayerPkKills());
                    player.setCursedWeaponEquippedId(0);
                    player.setTransformation(0);
                    player.setTransformationName(null);
                    player.removeSkill(SkillTable.getInstance().getInfo(cursedWeapon.getSkillId(), player.getSkillLevel(cursedWeapon.getSkillId())), false);
                    player.sendSkillList();
                    player.sendPacket((IStaticPacket)new ShortCutInit(player));
                    ItemInstance itemInstance = player.getInventory().getPaperdollItem(5);
                    if (cursedWeapon.getItem() == itemInstance) {
                        player.getInventory().setPaperdollItem(7, null);
                        player.getInventory().setPaperdollItem(5, null);
                        player.getInventory().unEquipItem(cursedWeapon.getItem());
                        player.getInventory().destroyItemByItemId(cursedWeapon.getItemId(), 1L);
                        player.sendPacket((IStaticPacket)new InventoryUpdate().addRemovedItem(itemInstance));
                    }
                    player.broadcastCharInfo();
                } else {
                    bp.info("CursedWeaponsManager: " + cursedWeapon.getName() + " being removed offline.");
                    connection = null;
                    preparedStatement = null;
                    connection = DatabaseFactory.getInstance().getConnection();
                    preparedStatement = connection.prepareStatement("DELETE FROM `items` WHERE `owner_id`=? AND `item_type`=?");
                    preparedStatement.setInt(1, cursedWeapon.getPlayerId());
                    preparedStatement.setInt(2, cursedWeapon.getItemId());
                    preparedStatement.executeUpdate();
                    DbUtils.close(preparedStatement);
                    preparedStatement = connection.prepareStatement("DELETE FROM `character_skills` WHERE `char_obj_id`=? AND `skill_id`=?");
                    preparedStatement.setInt(1, cursedWeapon.getPlayerId());
                    preparedStatement.setInt(2, cursedWeapon.getSkillId());
                    preparedStatement.executeUpdate();
                    DbUtils.close(preparedStatement);
                    preparedStatement = connection.prepareStatement("UPDATE `characters` SET `karma`=?, `pkkills`=? WHERE `obj_Id`=?");
                    preparedStatement.setInt(1, cursedWeapon.getPlayerKarma());
                    preparedStatement.setInt(2, cursedWeapon.getPlayerPkKills());
                    preparedStatement.setInt(3, cursedWeapon.getPlayerId());
                    preparedStatement.executeUpdate();
                    DbUtils.closeQuietly(connection, preparedStatement);
                }
                break block11;
                catch (SQLException sQLException) {
                    try {
                        bp.warn("CursedWeaponsManager: Could not delete : " + sQLException);
                    }
                    catch (Throwable throwable) {
                        DbUtils.closeQuietly(connection, preparedStatement);
                        throw throwable;
                    }
                    DbUtils.closeQuietly(connection, preparedStatement);
                    break block11;
                }
            }
            if (cursedWeapon.getPlayer() != null && cursedWeapon.getPlayer().getInventory().getItemByItemId(cursedWeapon.getItemId()) != null) {
                Player player = cursedWeapon.getPlayer();
                if (!cursedWeapon.getPlayer().getInventory().destroyItemByItemId(cursedWeapon.getItemId(), 1L)) {
                    bp.info("CursedWeaponsManager[453]: Error! Cursed weapon not found!!!");
                }
                player.sendChanges();
                player.broadcastUserInfo(true, new UserInfoType[0]);
            } else if (cursedWeapon.getItem() != null) {
                cursedWeapon.getItem().deleteMe();
                cursedWeapon.getItem().delete();
                bp.info("CursedWeaponsManager: " + cursedWeapon.getName() + " item has been removed from World.");
            }
        }
        cursedWeapon.initWeapon();
        this.removeFromDb(cursedWeapon.getItemId());
        this.announce((SystemMessage)new SystemMessage(SystemMsg.ID1818_S1_HAS_DISAPPEARED).addString(cursedWeapon.getName()));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveData(CursedWeapon cursedWeapon) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        CursedWeapon cursedWeapon2 = cursedWeapon;
        synchronized (cursedWeapon2) {
            block7: {
                try {
                    connection = DatabaseFactory.getInstance().getConnection();
                    preparedStatement = connection.prepareStatement("DELETE FROM `cursed_weapons` WHERE `item_id` = ?");
                    preparedStatement.setInt(1, cursedWeapon.getItemId());
                    preparedStatement.executeUpdate();
                    if (!cursedWeapon.isActive()) break block7;
                    DbUtils.close(preparedStatement);
                    preparedStatement = connection.prepareStatement("REPLACE INTO `cursed_weapons` (`item_id`, `player_id`, `player_karma`, `player_pkkills`, `nb_kills`, `x`, `y`, `z`, `end_time`) VALUES (?,?,?,?,?,?,?,?,?)");
                    preparedStatement.setInt(1, cursedWeapon.getItemId());
                    preparedStatement.setInt(2, cursedWeapon.getPlayerId());
                    preparedStatement.setInt(3, cursedWeapon.getPlayerKarma());
                    preparedStatement.setInt(4, cursedWeapon.getPlayerPkKills());
                    preparedStatement.setInt(5, cursedWeapon.getNbKills());
                    preparedStatement.setInt(6, cursedWeapon.getLoc().x);
                    preparedStatement.setInt(7, cursedWeapon.getLoc().y);
                    preparedStatement.setInt(8, cursedWeapon.getLoc().z);
                    preparedStatement.setLong(9, cursedWeapon.getEndTime() / 1000L);
                    preparedStatement.executeUpdate();
                }
                catch (SQLException sQLException) {
                    try {
                        bp.error("CursedWeapon: Failed to save data: " + sQLException);
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
    }

    public void saveData() {
        for (CursedWeapon cursedWeapon : this.a) {
            this.saveData(cursedWeapon);
        }
    }

    public void checkPlayer(Player player, ItemInstance itemInstance) {
        if (player == null || itemInstance == null || player.isOlyParticipant()) {
            return;
        }
        CursedWeapon cursedWeapon = (CursedWeapon)this.t.get(itemInstance.getItemId());
        if (cursedWeapon == null) {
            return;
        }
        if (player.getObjectId() == cursedWeapon.getPlayerId() || cursedWeapon.getPlayerId() == 0 || cursedWeapon.isDropped()) {
            this.activate(player, itemInstance);
            this.showUsageTime(player, cursedWeapon);
        } else {
            bp.warn("CursedWeaponsManager: " + player + " tried to obtain " + itemInstance + " in wrong way");
            player.getInventory().destroyItem(itemInstance, itemInstance.getCount());
        }
    }

    public void activate(Player player, ItemInstance itemInstance) {
        if (player == null || player.isOlyParticipant()) {
            return;
        }
        CursedWeapon cursedWeapon = (CursedWeapon)this.t.get(itemInstance.getItemId());
        if (cursedWeapon == null) {
            return;
        }
        if (player.isCursedWeaponEquipped()) {
            if (player.getCursedWeaponEquippedId() != itemInstance.getItemId()) {
                CursedWeapon cursedWeapon2 = (CursedWeapon)this.t.get(player.getCursedWeaponEquippedId());
                cursedWeapon2.setNbKills(cursedWeapon2.getStageKills() - 1);
                cursedWeapon2.increaseKills();
            }
            this.endOfLife(cursedWeapon);
            player.getInventory().destroyItem(itemInstance, 1L);
        } else if (cursedWeapon.getTimeLeft() > 0L) {
            cursedWeapon.activate(player, itemInstance);
            this.saveData(cursedWeapon);
            this.announce((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.THE_OWNER_OF_S2_HAS_APPEARED_IN_THE_S1_REGION).addZoneName(player.getLoc())).addItemName(cursedWeapon.getItemId()));
        } else {
            this.endOfLife(cursedWeapon);
            player.getInventory().destroyItem(itemInstance, 1L);
        }
    }

    public void doLogout(Player player) {
        for (CursedWeapon cursedWeapon : this.a) {
            if (player.getInventory().getItemByItemId(cursedWeapon.getItemId()) == null) continue;
            cursedWeapon.setPlayer(null);
            cursedWeapon.setItem(null);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void dropAttackable(NpcInstance npcInstance, Player player) {
        if (player.isOlyParticipant() || player.isCursedWeaponEquipped() || this.a.length == 0 || player.getReflection() != ReflectionManager.DEFAULT) {
            return;
        }
        CursedWeapon[] cursedWeaponArray = this.a;
        synchronized (this.a) {
            Object[] objectArray = new CursedWeapon[]{};
            for (CursedWeapon cursedWeapon : this.a) {
                if (cursedWeapon.isActive()) continue;
                objectArray = (CursedWeapon[])ArrayUtils.add((Object[])objectArray, (Object)cursedWeapon);
            }
            if (objectArray.length > 0) {
                CursedWeapon cursedWeapon = objectArray[Rnd.get(objectArray.length)];
                if (Rnd.get(100000000) <= cursedWeapon.getDropRate()) {
                    cursedWeapon.create(npcInstance, player);
                }
            }
            // ** MonitorExit[var3_3] (shouldn't be in output)
            return;
        }
    }

    public void dropPlayer(Player player) {
        CursedWeapon cursedWeapon = (CursedWeapon)this.t.get(player.getCursedWeaponEquippedId());
        if (cursedWeapon == null) {
            return;
        }
        if (cursedWeapon.dropIt(null, null, player)) {
            this.saveData(cursedWeapon);
            this.announce((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.S2_WAS_DROPPED_IN_THE_S1_REGION).addZoneName(player.getLoc())).addItemName(cursedWeapon.getItemId()));
        } else {
            this.endOfLife(cursedWeapon);
        }
    }

    public void increaseKills(int n) {
        CursedWeapon cursedWeapon = (CursedWeapon)this.t.get(n);
        if (cursedWeapon != null) {
            cursedWeapon.increaseKills();
            this.saveData(cursedWeapon);
        }
    }

    public int getLevel(int n) {
        CursedWeapon cursedWeapon = (CursedWeapon)this.t.get(n);
        return cursedWeapon != null ? cursedWeapon.getLevel() : 0;
    }

    public void announce(SystemMessage systemMessage) {
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            player.sendPacket((IStaticPacket)systemMessage);
        }
    }

    public void showUsageTime(Player player, int n) {
        CursedWeapon cursedWeapon = (CursedWeapon)this.t.get(n);
        if (cursedWeapon != null) {
            this.showUsageTime(player, cursedWeapon);
        }
    }

    public void showUsageTime(Player player, CursedWeapon cursedWeapon) {
        SystemMessage systemMessage = new SystemMessage(SystemMsg.S1_HAS_S2_MINUTES_OF_USAGE_TIME_REMAINING);
        systemMessage.addItemName(cursedWeapon.getItemId());
        systemMessage.addNumber(cursedWeapon.getTimeLeft() / 60000L);
        player.sendPacket((IStaticPacket)systemMessage);
    }

    public boolean isCursed(int n) {
        return this.t.containsKey(n);
    }

    public CursedWeapon[] getCursedWeapons() {
        return this.a;
    }

    public int[] getCursedWeaponsIds() {
        return this.t.keys();
    }

    public CursedWeapon getCursedWeapon(int n) {
        return (CursedWeapon)this.t.get(n);
    }

    private class RemoveTask
    extends RunnableImpl {
        private RemoveTask() {
        }

        @Override
        public void runImpl() throws Exception {
            for (CursedWeapon cursedWeapon : CursedWeaponsManager.this.a) {
                if (!cursedWeapon.isActive() || cursedWeapon.getTimeLeft() > 0L) continue;
                CursedWeaponsManager.this.endOfLife(cursedWeapon);
            }
        }
    }
}
