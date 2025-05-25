/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.instances;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.Future;
import l2.commons.dbutils.DbUtils;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.PetDataHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.PetData;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Summon;
import l2.gameserver.model.base.BaseStats;
import l2.gameserver.model.instances.PetBabyInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PetInventory;
import l2.gameserver.model.items.attachment.FlagItemAttachment;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExPetInfo;
import l2.gameserver.network.l2.s2c.InventoryUpdate;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.stats.Stats;
import l2.gameserver.templates.item.WeaponTemplate;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PetInstance
extends Summon {
    private static final Logger cl = LoggerFactory.getLogger(PetInstance.class);
    private static final int nj = 5169;
    private final int nk;
    private int nl;
    protected PetData _data;
    protected int _maxLvl;
    private Future<?> M;
    protected PetInventory _inventory;
    private int _level;
    private boolean dD;
    private int nm;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final PetInstance restore(ItemInstance itemInstance, NpcTemplate npcTemplate, Player player) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        PetInstance petInstance;
        block5: {
            petInstance = null;
            connection = null;
            preparedStatement = null;
            resultSet = null;
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `objId`, `name`, `level`, `curHp`, `curMp`, `exp`, `sp`, `fed` FROM `pets` WHERE `item_obj_id`=?");
            preparedStatement.setInt(1, itemInstance.getObjectId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) break block5;
            PetData petData = PetDataHolder.getInstance().getInfo(npcTemplate.getNpcId());
            petInstance = petData.isBabyPet() || petData.isImprovedBabyPet() ? new PetBabyInstance(IdFactory.getInstance().getNextId(), npcTemplate, player, itemInstance) : new PetInstance(IdFactory.getInstance().getNextId(), npcTemplate, player, itemInstance);
            PetBabyInstance petBabyInstance = petInstance;
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            return petBabyInstance;
        }
        try {
            int n = Math.max(1, Math.min(resultSet.getInt("level"), PetDataHolder.getInstance().getMaxLevel(npcTemplate.getNpcId())));
            PetData petData = PetDataHolder.getInstance().getInfo(npcTemplate.getNpcId(), n);
            petInstance = petData.isBabyPet() || petData.isImprovedBabyPet() ? new PetBabyInstance(resultSet.getInt("objId"), npcTemplate, player, itemInstance, n, resultSet.getLong("exp")) : new PetInstance(resultSet.getInt("objId"), npcTemplate, player, itemInstance, n, resultSet.getLong("exp"));
            petInstance.setRespawned(true);
            String string = resultSet.getString("name");
            petInstance.setName(string == null || string.isEmpty() ? npcTemplate.name : string);
            petInstance.setCurrentHpMp(resultSet.getDouble("curHp"), resultSet.getInt("curMp"), true);
            petInstance.setCurrentCp(petInstance.getMaxCp());
            petInstance.setSp(resultSet.getInt("sp"));
            petInstance.setCurrentFed(resultSet.getInt("fed"));
        }
        catch (Exception exception) {
            PetInstance petInstance2;
            try {
                cl.error("Could not restore Pet data from item: " + itemInstance + "!", (Throwable)exception);
                petInstance2 = null;
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            return petInstance2;
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return petInstance;
    }

    public PetInstance(int n, NpcTemplate npcTemplate, Player player, ItemInstance itemInstance) {
        this(n, npcTemplate, player, itemInstance, 0, 0L);
    }

    public PetInstance(int n, NpcTemplate npcTemplate, Player player, ItemInstance itemInstance, int n2, long l) {
        super(n, npcTemplate, player);
        this.nk = itemInstance.getObjectId();
        this._exp = l;
        if (itemInstance.getEnchantLevel() <= 0) {
            this._level = npcTemplate.npcId == 12564 ? (player.getLevel() >= 80 ? player.getLevel() - 1 : player.getLevel()) : npcTemplate.level;
            this._data = PetDataHolder.getInstance().getInfo(this.getNpcId(), this._level);
            this._exp = this._data.getExp();
        } else {
            this._level = itemInstance.getEnchantLevel();
            this._data = PetDataHolder.getInstance().getInfo(this.getNpcId(), this._level);
        }
        this._maxLvl = PetDataHolder.getInstance().getMaxLevel(this.getNpcId()) - 1;
        int n3 = this._data.getMinLevel();
        if (this._level < n3) {
            this._level = n3;
        }
        if (this._exp < this.getExpForThisLevel()) {
            this._exp = this.getExpForThisLevel();
        }
        while (this._exp >= this.getExpForNextLevel() && this._level < this.getMaxLevel()) {
            this._data = PetDataHolder.getInstance().getInfo(this.getNpcId(), ++this._level);
        }
        while (this._exp < this.getExpForThisLevel() && this._level > n3) {
            this._data = PetDataHolder.getInstance().getInfo(this.getNpcId(), --this._level);
        }
        this._inventory = new PetInventory(this);
    }

    @Override
    protected void onSpawn() {
        super.onSpawn();
        this.startFeed(false);
    }

    @Override
    protected void onDespawn() {
        super.onSpawn();
        this.bK();
    }

    public boolean tryFeedItem(ItemInstance itemInstance) {
        boolean bl;
        if (itemInstance == null) {
            return false;
        }
        boolean bl2 = bl = this._data.isStrider() && itemInstance.getItemId() == 5169;
        if (this.getFoodId() != itemInstance.getItemId() && !bl) {
            return false;
        }
        int n = Math.min(this.getMaxFed(), this.getCurrentFed() + Math.max(this.getMaxFed() * this.getAddFed() * (bl ? 2 : 1) / 100, 1));
        if (this.getCurrentFed() != n && this.getInventory().destroyItem(itemInstance, 1L)) {
            this.getPlayer().sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_PET_WAS_HUNGRY_SO_IT_ATE_S1).addItemName(itemInstance.getItemId()));
            this.setCurrentFed(n);
            this.sendStatusUpdate();
        }
        return true;
    }

    public boolean tryFeed() {
        ItemInstance itemInstance = this.getInventory().getItemByItemId(this.getFoodId());
        if (itemInstance == null && this._data.isStrider()) {
            itemInstance = this.getInventory().getItemByItemId(5169);
        }
        return this.tryFeedItem(itemInstance);
    }

    @Override
    public void addExpAndSp(long l, long l2) {
        Player player = this.getPlayer();
        this._exp += l;
        this._sp = (int)((long)this._sp + l2);
        this._exp = Math.min(this._exp, PetDataHolder.getInstance().getInfoMaxLevel(this.getNpcId()).getExp());
        if (l > 0L || l2 > 0L) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_PET_GAINED_S1_EXPERIENCE_POINTS).addNumber(l));
        }
        int n = this._level;
        while (this._exp >= this.getExpForNextLevel() && this._level < this.getMaxLevel()) {
            this._data = PetDataHolder.getInstance().getInfo(this.getTemplate().npcId, ++this._level);
        }
        while (this._exp < this.getExpForThisLevel() && this._level > this.getMinLevel()) {
            this._data = PetDataHolder.getInstance().getInfo(this.getTemplate().npcId, --this._level);
        }
        if (n < this._level) {
            player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2PetInstance.PetLevelUp", player, new Object[0]).addNumber(this._level));
            this.broadcastPacket(new SocialAction(this.getObjectId(), 2122));
            this.setCurrentHpMp(this.getMaxHp(), this.getMaxMp());
        }
        if (n != this._level) {
            this.updateControlItem();
        }
        if (Math.abs(l) > 0L || Math.abs(l2) > 0L) {
            this.sendStatusUpdate();
        }
    }

    @Override
    public boolean consumeItem(int n, long l) {
        return this.getPlayer().getInventory().destroyItemByItemId(n, l);
    }

    private void bI() {
        if (this.isInZoneBattle()) {
            return;
        }
        int n = this.getLevel();
        double d = -0.07 * (double)n + 6.5;
        this.nm = (int)Math.round((double)(this.getExpForNextLevel() - this.getExpForThisLevel()) * d / 100.0);
        this.addExpAndSp(-this.nm, 0L);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void bJ() {
        Player player = this.getPlayer();
        if (this.getControlItemObjId() == 0) {
            return;
        }
        if (!player.getInventory().destroyItemByObjectId(this.getControlItemObjId(), 1L)) {
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM `pets` WHERE `item_obj_id`=?");
            preparedStatement.setInt(1, this.getControlItemObjId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                cl.warn("could not delete pet:" + exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    @Override
    protected void onDeath(Creature creature) {
        super.onDeath(creature);
        Player player = this.getPlayer();
        player.sendPacket((IStaticPacket)SystemMsg.THE_PET_HAS_BEEN_KILLED);
        this.startDecay(86400000L);
        this.bK();
        this.bI();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void doPickupItem(GameObject gameObject) {
        Player player = this.getPlayer();
        this.stopMove();
        if (!gameObject.isItem()) {
            return;
        }
        ItemInstance itemInstance = (ItemInstance)gameObject;
        if (itemInstance.isCursed()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_FAILED_TO_PICK_UP_S1).addItemName(itemInstance.getItemId()));
            return;
        }
        ItemInstance itemInstance2 = itemInstance;
        synchronized (itemInstance2) {
            FlagItemAttachment flagItemAttachment;
            if (!itemInstance.isVisible()) {
                return;
            }
            if (itemInstance.isHerb()) {
                Skill[] skillArray = itemInstance.getTemplate().getAttachedSkills();
                if (skillArray.length > 0) {
                    for (Skill skill : skillArray) {
                        this.altUseSkill(skill, this);
                    }
                }
                itemInstance.deleteMe();
                return;
            }
            if (!this.getInventory().validateWeight(itemInstance)) {
                this.sendPacket((IStaticPacket)SystemMsg.YOUR_PET_CANNOT_CARRY_ANY_MORE_ITEMS_);
                return;
            }
            if (!this.getInventory().validateCapacity(itemInstance)) {
                this.sendPacket((IStaticPacket)SystemMsg.YOUR_PET_CANNOT_CARRY_ANY_MORE_ITEMS);
                return;
            }
            if (!itemInstance.getTemplate().getHandler().pickupItem(this, itemInstance)) {
                return;
            }
            FlagItemAttachment flagItemAttachment2 = flagItemAttachment = itemInstance.getAttachment() instanceof FlagItemAttachment ? (FlagItemAttachment)itemInstance.getAttachment() : null;
            if (flagItemAttachment != null) {
                return;
            }
            itemInstance.pickupMe();
        }
        if (player.getParty() == null || player.getParty().getLootDistribution() == 0) {
            Log.LogItem(player, Log.ItemLog.PetPickup, itemInstance);
            this.getInventory().addItem(itemInstance);
            this.sendChanges();
            this.broadcastPickUpMsg(itemInstance);
            itemInstance.pickupMe();
        } else {
            player.getParty().distributeItem(player, itemInstance, null);
        }
        this.broadcastPickUpMsg(itemInstance);
    }

    public void doRevive(double d) {
        this.restoreExp(d);
        this.doRevive();
    }

    @Override
    public void doRevive() {
        this.stopDecay();
        super.doRevive();
        this.startFeed(false);
        this.setRunning();
    }

    @Override
    public int getAccuracy() {
        return (int)this.calcStat(Stats.ACCURACY_COMBAT, this._data.getAccuracy(), null, null);
    }

    @Override
    public ItemInstance getActiveWeaponInstance() {
        return null;
    }

    @Override
    public WeaponTemplate getActiveWeaponItem() {
        return null;
    }

    public ItemInstance getControlItem() {
        Player player = this.getPlayer();
        if (player == null) {
            return null;
        }
        int n = this.getControlItemObjId();
        if (n == 0) {
            return null;
        }
        return player.getInventory().getItemByObjectId(n);
    }

    @Override
    public int getControlItemObjId() {
        return this.nk;
    }

    @Override
    public int getCriticalHit(Creature creature, Skill skill) {
        return (int)this.calcStat(Stats.CRITICAL_BASE, this._data.getCritical(), creature, skill);
    }

    @Override
    public int getCurrentFed() {
        return this.nl;
    }

    @Override
    public int getEvasionRate(Creature creature) {
        return (int)this.calcStat(Stats.EVASION_RATE, this._data.getEvasion(), creature, null);
    }

    @Override
    public long getExpForNextLevel() {
        return PetDataHolder.getInstance().getInfo(this.getNpcId(), this._level + 1).getExp();
    }

    @Override
    public long getExpForThisLevel() {
        return this._data.getExp();
    }

    public int getFoodId() {
        return this._data.getFoodId();
    }

    public int getAddFed() {
        return this._data.getAddFed();
    }

    @Override
    public PetInventory getInventory() {
        return this._inventory;
    }

    @Override
    public long getWearedMask() {
        return this._inventory.getWearedMask();
    }

    @Override
    public final int getLevel() {
        return this._level;
    }

    public void setLevel(int n) {
        this._level = n;
    }

    @Override
    public double getLevelMod() {
        return (89.0 + (double)this.getLevel()) / 100.0;
    }

    public int getMinLevel() {
        return this._data.getMinLevel();
    }

    public int getMaxLevel() {
        return this._maxLvl;
    }

    @Override
    public int getMaxFed() {
        return this._data.getFeedMax();
    }

    @Override
    public int getMaxLoad() {
        return (int)this.calcStat(Stats.MAX_LOAD, this._data.getMaxLoad(), null, null);
    }

    @Override
    public int getInventoryLimit() {
        return Config.ALT_PET_INVENTORY_LIMIT;
    }

    @Override
    public int getMaxHp() {
        return (int)this.calcStat(Stats.MAX_HP, this._data.getHP(), null, null);
    }

    @Override
    public int getMaxMp() {
        return (int)this.calcStat(Stats.MAX_MP, this._data.getMP(), null, null);
    }

    @Override
    public int getPAtk(Creature creature) {
        double d = BaseStats.STR.calcBonus(this) * this.getLevelMod();
        return (int)this.calcStat(Stats.POWER_ATTACK, (double)this._data.getPAtk() / d, creature, null);
    }

    @Override
    public int getPDef(Creature creature) {
        double d = this.getLevelMod();
        return (int)this.calcStat(Stats.POWER_DEFENCE, (double)this._data.getPDef() / d, creature, null);
    }

    @Override
    public int getMAtk(Creature creature, Skill skill) {
        double d = BaseStats.INT.calcBonus(this);
        double d2 = this.getLevelMod();
        double d3 = d2 * d2 * d * d;
        return (int)this.calcStat(Stats.MAGIC_ATTACK, (double)this._data.getMAtk() / d3, creature, skill);
    }

    @Override
    public int getMDef(Creature creature, Skill skill) {
        double d = BaseStats.MEN.calcBonus(this) * this.getLevelMod();
        return (int)this.calcStat(Stats.MAGIC_DEFENCE, (double)this._data.getMDef() / d, creature, skill);
    }

    @Override
    public int getPAtkSpd() {
        return (int)this.calcStat(Stats.POWER_ATTACK_SPEED, this.calcStat(Stats.ATK_BASE, this._data.getAtkSpeed(), null, null), null, null);
    }

    @Override
    public int getMAtkSpd() {
        return (int)this.calcStat(Stats.MAGIC_ATTACK_SPEED, this._data.getCastSpeed(), null, null);
    }

    @Override
    public int getRunSpeed() {
        return this.getSpeed(this._data.getSpeed());
    }

    @Override
    public int getSoulshotConsumeCount() {
        return this._data.getSoulshots();
    }

    @Override
    public int getSpiritshotConsumeCount() {
        return this._data.getSpiritshots();
    }

    @Override
    public ItemInstance getSecondaryWeaponInstance() {
        return null;
    }

    @Override
    public WeaponTemplate getSecondaryWeaponItem() {
        return null;
    }

    public int getSkillLevel(int n) {
        if (this._skills == null || this._skills.get(n) == null) {
            return -1;
        }
        int n2 = this.getLevel();
        return n2 > 70 ? 7 + (n2 - 70) / 5 : n2 / 10;
    }

    @Override
    public int getSummonType() {
        return 2;
    }

    @Override
    public NpcTemplate getTemplate() {
        return (NpcTemplate)this._template;
    }

    @Override
    public boolean isMountable() {
        return this._data.isMountable();
    }

    public boolean isRespawned() {
        return this.dD;
    }

    public void restoreExp(double d) {
        if (this.nm != 0) {
            this.addExpAndSp((long)((double)this.nm * d / 100.0), 0L);
            this.nm = 0;
        }
    }

    public void setCurrentFed(int n) {
        this.nl = Math.min(this.getMaxFed(), Math.max(0, n));
    }

    public void setRespawned(boolean bl) {
        this.dD = bl;
    }

    @Override
    public void setSp(int n) {
        this._sp = n;
    }

    public void startFeed(boolean bl) {
        boolean bl2 = this.M == null;
        this.bK();
        if (!this.isDead()) {
            int n = Math.max(bl2 ? 15000 : 1000, 60000 / (bl ? this._data.getFeedBattle() : this._data.getFeedNormal()));
            this.M = ThreadPoolManager.getInstance().schedule(new FeedTask(), n);
        }
    }

    private void bK() {
        if (this.M != null) {
            this.M.cancel(false);
            this.M = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void store() {
        if (this.getControlItemObjId() == 0 || this._exp == 0L) {
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            String string = !this.isRespawned() ? "INSERT INTO `pets` (`name`,`level`,`curHp`,`curMp`,`exp`,`sp`,`fed`,`objId`,`item_obj_id`) VALUES (?,?,?,?,?,?,?,?,?)" : "UPDATE `pets` SET `name`=?,`level`=?,`curHp`=?,`curMp`=?,`exp`=?,`sp`=?,`fed`=?,`objId`=? WHERE `item_obj_id` = ?";
            preparedStatement = connection.prepareStatement(string);
            preparedStatement.setString(1, this.getName().equalsIgnoreCase(this.getTemplate().name) ? "" : this.getName());
            preparedStatement.setInt(2, this._level);
            preparedStatement.setDouble(3, this.getCurrentHp());
            preparedStatement.setDouble(4, this.getCurrentMp());
            preparedStatement.setLong(5, this._exp);
            preparedStatement.setLong(6, this._sp);
            preparedStatement.setInt(7, this.nl);
            preparedStatement.setInt(8, this.getObjectId());
            preparedStatement.setInt(9, this.nk);
            preparedStatement.executeUpdate();
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        catch (Exception exception) {
            cl.error("Could not store pet data!", (Throwable)exception);
        }
        finally {
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        this.dD = true;
    }

    @Override
    protected void onDecay() {
        this.getInventory().store();
        this.bJ();
        super.onDecay();
    }

    @Override
    public void unSummon() {
        this.bK();
        this.getInventory().store();
        this.store();
        super.unSummon();
    }

    public void updateControlItem() {
        ItemInstance itemInstance = this.getControlItem();
        if (itemInstance == null) {
            return;
        }
        itemInstance.setEnchantLevel(this._level);
        itemInstance.setDamaged(this.isDefaultName() ? 0 : 1);
        Player player = this.getPlayer();
        player.sendPacket((IStaticPacket)new InventoryUpdate().addModifiedItem(itemInstance));
    }

    @Override
    public double getExpPenalty() {
        return this._data.getExpPenalty();
    }

    @Override
    protected L2GameServerPacket createInfoPacketForOthers(Player player, boolean bl) {
        return new ExPetInfo(this, player).update(bl);
    }

    @Override
    public void displayGiveDamageMessage(Creature creature, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        Player player = this.getPlayer();
        if (player != null) {
            if (bl) {
                player.sendPacket((IStaticPacket)SystemMsg.SUMMONED_MONSTERS_CRITICAL_HIT);
            }
            if (bl2) {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1S_ATTACK_WENT_ASTRAY).addName(this));
            } else {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_PET_HIT_FOR_S1_DAMAGE).addNumber(n));
            }
        }
    }

    @Override
    public void displayReceiveDamageMessage(Creature creature, int n) {
        Player player = this.getPlayer();
        if (creature != this && !this.isDead() && player != null) {
            SystemMessage systemMessage = new SystemMessage(SystemMsg.YOUR_PET_RECEIVED_S2_DAMAGE_BY_C1);
            systemMessage.addName(creature);
            systemMessage.addNumber((long)n);
            player.sendPacket((IStaticPacket)systemMessage);
        }
    }

    @Override
    public boolean isPet() {
        return true;
    }

    public boolean isDefaultName() {
        return StringUtils.isEmpty((CharSequence)this._name) || this.getName().equalsIgnoreCase(this.getTemplate().name);
    }

    @Override
    public int getEffectIdentifier() {
        return 0;
    }

    class FeedTask
    extends RunnableImpl {
        FeedTask() {
        }

        @Override
        public void runImpl() throws Exception {
            Player player = PetInstance.this.getPlayer();
            while ((double)PetInstance.this.getCurrentFed() <= 0.55 * (double)PetInstance.this.getMaxFed() && PetInstance.this.tryFeed()) {
            }
            if ((double)PetInstance.this.getCurrentFed() <= 0.1 * (double)PetInstance.this.getMaxFed()) {
                player.sendMessage(new CustomMessage("l2p.gameserver.model.instances.L2PetInstance.UnSummonHungryPet", player, new Object[0]));
                PetInstance.this.unSummon();
                return;
            }
            PetInstance.this.setCurrentFed(PetInstance.this.getCurrentFed() - 5);
            PetInstance.this.sendStatusUpdate();
            PetInstance.this.startFeed(PetInstance.this.isInCombat());
        }
    }
}
