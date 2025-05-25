/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.Containers
 *  org.napile.primitive.sets.IntSet
 *  org.napile.primitive.sets.impl.HashIntSet
 */
package l2.gameserver.model.items;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import l2.commons.collections.LazyArrayList;
import l2.commons.util.concurrent.atomic.AtomicEnumBitFlag;
import l2.gameserver.Config;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.dao.ItemsDAO;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.PetDataHolder;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.instancemanager.CursedWeaponsManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.Element;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemAttributes;
import l2.gameserver.model.items.ItemStateFlags;
import l2.gameserver.model.items.attachment.ItemAttachment;
import l2.gameserver.model.items.listeners.ItemEnchantOptionsListener;
import l2.gameserver.network.l2.s2c.DropItem;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SpawnItem;
import l2.gameserver.scripts.Events;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.stats.funcs.FuncTemplate;
import l2.gameserver.taskmanager.ItemsAutoDestroy;
import l2.gameserver.taskmanager.LazyPrecisionTaskManager;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.item.ItemType;
import l2.gameserver.templates.item.support.Grade;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;
import org.napile.primitive.Containers;
import org.napile.primitive.sets.IntSet;
import org.napile.primitive.sets.impl.HashIntSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ItemInstance
extends GameObject {
    public static final int[] EMPTY_ENCHANT_OPTIONS = new int[3];
    private static final long cw = 3162753878915133228L;
    private static final ItemsDAO b = ItemsDAO.getInstance();
    public static final int CHARGED_NONE = 0;
    public static final int CHARGED_SOULSHOT = 1;
    public static final int CHARGED_SPIRITSHOT = 1;
    public static final int CHARGED_BLESSED_SPIRITSHOT = 2;
    public static final int FLAG_NO_DROP = 1;
    public static final int FLAG_NO_TRADE = 2;
    public static final int FLAG_NO_TRANSFER = 4;
    public static final int FLAG_NO_CRYSTALLIZE = 8;
    public static final int FLAG_NO_ENCHANT = 16;
    public static final int FLAG_NO_DESTROY = 32;
    public static final int FLAG_NO_FREIGHT = 64;
    private ItemAttributes b;
    private int[] aM;
    private int oo;
    private int op;
    private long cx;
    private ItemLocation a;
    private int kk;
    private Integer b = new ItemAttributes();
    private int oq = -1;
    private int or = -9999;
    private int os;
    private int ot;
    private int ou;
    private int ov;
    private int ow;
    private int ox;
    private int oy;
    private int oz;
    private int oA;
    private AtomicEnumBitFlag<ItemStateFlags> a;
    private ItemTemplate d;
    private boolean dF;
    private long cy;
    private IntSet d;
    private long cz;
    private int oB = 0;
    private int oC = 0;
    private boolean dG = false;
    private ItemAttachment a;
    private ScheduledFuture<?> ad;

    public ItemInstance(int n) {
        super(n);
        this.aM = EMPTY_ENCHANT_OPTIONS;
        this.a = new AtomicEnumBitFlag();
        this.d = Containers.EMPTY_INT_SET;
    }

    public ItemInstance(int n, int n2) {
        super(n);
        this.aM = EMPTY_ENCHANT_OPTIONS;
        this.a = new AtomicEnumBitFlag();
        this.d = Containers.EMPTY_INT_SET;
        this.setItemId(n2);
        this.setDuration(this.getTemplate().getDurability());
        this.setPeriodBegin(this.getTemplate().isTemporal() ? (int)(System.currentTimeMillis() / 1000L) + this.getTemplate().getDurability() * 60 : -9999);
        this.setLocData(-1);
        this.setEnchantLevel(this.getTemplate().getBaseEnchantLevel());
    }

    public int getOwnerId() {
        return this.oo;
    }

    public void setOwnerId(int n) {
        if (this.oo == n) {
            return;
        }
        this.oo = n;
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
    }

    public int getItemId() {
        return this.op;
    }

    public void setVisibleItemId(int n) {
        this.oA = n;
    }

    public int getVisibleItemId() {
        if (this.oA > 0) {
            return this.oA;
        }
        return this.getItemId();
    }

    public void setItemId(int n) {
        this.op = n;
        this.d = ItemHolder.getInstance().getTemplate(n);
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
    }

    public long getCount() {
        return this.cx;
    }

    public AtomicEnumBitFlag<ItemStateFlags> getItemStateFlag() {
        return this.a;
    }

    public void setCount(long l) {
        if (l < 0L) {
            l = 0L;
        }
        if (this.isStackable() && l > Config.ITEMS_MAX_AMMOUNT) {
            this.cx = Config.ITEMS_MAX_AMMOUNT;
            this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
            return;
        }
        if (!this.isStackable() && l > 1L) {
            this.cx = 1L;
            this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
            return;
        }
        if (this.cx == l) {
            return;
        }
        this.cx = l;
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
    }

    public int getEnchantLevel() {
        return this.b != null ? this.b : 0;
    }

    public void setEnchantLevel(int n) {
        Integer n2 = this.b;
        this.b = Math.max(this.getTemplate().getBaseEnchantLevel(), n);
        if (n2 != this.b && this.getTemplate().getEnchantOptions().size() > 0) {
            int[] nArray;
            Player player = GameObjectsStorage.getPlayer(this.getOwnerId());
            if (this.isEquipped() && player != null) {
                ItemEnchantOptionsListener.getInstance().onUnequip(this.getEquipSlot(), this, player);
            }
            int[] nArray2 = this.aM = (nArray = (int[])this.getTemplate().getEnchantOptions().get(this.b.intValue())) == null ? EMPTY_ENCHANT_OPTIONS : nArray;
            if (this.isEquipped() && player != null) {
                ItemEnchantOptionsListener.getInstance().onEquip(this.getEquipSlot(), this, player);
            }
        }
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
    }

    public void setLocName(String string) {
        this.setLocation(ItemLocation.valueOf(string));
    }

    public String getLocName() {
        return this.a.name();
    }

    public void setLocation(ItemLocation itemLocation) {
        if (this.a == itemLocation) {
            return;
        }
        this.a = itemLocation;
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
    }

    public ItemLocation getLocation() {
        return this.a;
    }

    public void setLocData(int n) {
        if (this.kk == n) {
            return;
        }
        this.kk = n;
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
    }

    public int getLocData() {
        return this.kk;
    }

    public int getBlessed() {
        return this.ox;
    }

    public void setBlessed(int n) {
        this.ox = n;
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
    }

    public int getDamaged() {
        return this.oy;
    }

    public void setDamaged(int n) {
        this.oy = n;
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
    }

    public int getCustomFlags() {
        return this.oz;
    }

    public void setCustomFlags(int n) {
        if (this.oz == n) {
            return;
        }
        this.oz = n;
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
    }

    public ItemAttributes getAttributes() {
        return this.b;
    }

    public void setAttributes(ItemAttributes itemAttributes) {
        this.b = itemAttributes;
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
    }

    public int getDuration() {
        if (!this.isShadowItem()) {
            return -1;
        }
        return this.oq;
    }

    public void setDuration(int n) {
        this.oq = n;
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
    }

    public int getPeriod() {
        if (!this.isTemporalItem()) {
            return -9999;
        }
        return this.or - (int)(System.currentTimeMillis() / 1000L);
    }

    public int getPeriodBegin() {
        if (!this.isTemporalItem()) {
            return -9999;
        }
        return this.or;
    }

    public void setPeriodBegin(int n) {
        this.or = n;
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
    }

    public void startTimer(Runnable runnable) {
        this.ad = LazyPrecisionTaskManager.getInstance().scheduleAtFixedRate(runnable, 0L, 60000L);
    }

    public void stopTimer() {
        if (this.ad != null) {
            this.ad.cancel(false);
            this.ad = null;
        }
    }

    public boolean isEquipable() {
        return this.d.isEquipable();
    }

    public boolean isEquipped() {
        return this.dF;
    }

    public void setEquipped(boolean bl) {
        this.dF = bl;
    }

    public long getBodyPart() {
        return this.d.getBodyPart();
    }

    public int getEquipSlot() {
        return this.getLocData();
    }

    public ItemTemplate getTemplate() {
        return this.d;
    }

    public void setDropTime(long l) {
        this.cy = l;
    }

    public long getLastDropTime() {
        return this.cy;
    }

    public long getDropTimeOwner() {
        return this.cz;
    }

    public ItemType getItemType() {
        return this.d.getItemType();
    }

    public boolean isArmor() {
        return this.d.isArmor();
    }

    public boolean isAccessory() {
        return this.d.isAccessory();
    }

    public boolean isWeapon() {
        return this.d.isWeapon();
    }

    public long getReferencePrice() {
        return this.d.getReferencePrice();
    }

    public boolean isStackable() {
        return this.d.isStackable();
    }

    @Override
    public void onAction(Player player, boolean bl) {
        if (Events.onAction(player, this, bl)) {
            return;
        }
        if (player.isCursedWeaponEquipped() && CursedWeaponsManager.getInstance().isCursed(this.getItemId())) {
            return;
        }
        player.getAI().setIntention(CtrlIntention.AI_INTENTION_PICK_UP, this, null);
    }

    @Override
    public int getActingRange() {
        return 16;
    }

    public boolean isAugmented() {
        return this.getVariationStat1() != 0 || this.getVariationStat2() != 0;
    }

    public int getVariationStat1() {
        return this.os;
    }

    public int getVariationStat2() {
        return this.ot;
    }

    public void setVariationStat1(int n) {
        this.os = n;
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
    }

    public void setVariationStat2(int n) {
        this.ot = n;
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
    }

    public int getEnsoulSlotN1() {
        return this.ou;
    }

    public ItemInstance setEnsoulSlotN1(int n) {
        this.ou = n;
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
        return this;
    }

    public int getEnsoulSlotN2() {
        return this.ov;
    }

    public ItemInstance setEnsoulSlotN2(int n) {
        this.ov = n;
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
        return this;
    }

    public int getEnsoulSlotBm() {
        return this.ow;
    }

    public ItemInstance setEnsoulSlotBm(int n) {
        this.ow = n;
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
        return this;
    }

    public boolean isEnsouled() {
        return this.getEnsoulSlotN1() != 0 || this.getEnsoulSlotN2() != 0 || this.getEnsoulSlotBm() != 0;
    }

    public int getChargedSoulshot() {
        return this.oB;
    }

    public int getChargedSpiritshot() {
        return this.oC;
    }

    public boolean getChargedFishshot() {
        return this.dG;
    }

    public void setChargedSoulshot(int n) {
        this.oB = n;
    }

    public void setChargedSpiritshot(int n) {
        this.oC = n;
    }

    public void setChargedFishshot(boolean bl) {
        this.dG = bl;
    }

    public Func[] getStatFuncs() {
        Func[] funcArray = Func.EMPTY_FUNC_ARRAY;
        LazyArrayList<Func> lazyArrayList = LazyArrayList.newInstance();
        if (this.d.getAttachedFuncs().length > 0) {
            for (FuncTemplate funcTemplate : this.d.getAttachedFuncs()) {
                Func func = funcTemplate.getFunc(this);
                if (func == null) continue;
                lazyArrayList.add(func);
            }
        }
        for (Element element : Element.VALUES) {
            if (this.isWeapon()) {
                lazyArrayList.add(new FuncAttack(element, 64, this));
            }
            if (!this.isArmor()) continue;
            lazyArrayList.add(new FuncDefence(element, 64, this));
        }
        if (!lazyArrayList.isEmpty()) {
            funcArray = lazyArrayList.toArray(new Func[lazyArrayList.size()]);
        }
        LazyArrayList.recycle(lazyArrayList);
        return funcArray;
    }

    public boolean isHeroWeapon() {
        return this.d.isHeroWeapon();
    }

    public boolean canBeDestroyed(Player player) {
        if ((this.getCustomFlags() & 0x20) == 32) {
            return false;
        }
        if (player.isMounted() && PetDataHolder.getInstance().getByControlItemId(this.getItemId()) != null) {
            return false;
        }
        if (player.getPetControlItem() == this) {
            return false;
        }
        if (player.getEnchantScroll() == this) {
            return false;
        }
        if (this.isCursed()) {
            return false;
        }
        return this.d.isDestroyable();
    }

    public boolean canBeDropped(Player player, boolean bl) {
        if (player.getPlayerAccess().CanDropAnyItems) {
            return true;
        }
        if ((this.getCustomFlags() & 1) == 1) {
            return false;
        }
        if (this.isShadowItem()) {
            return false;
        }
        if (this.isTemporalItem()) {
            return false;
        }
        if (!(!this.isAugmented() && !this.isEnsouled() || bl && Config.DROP_ITEMS_AUGMENTED || Config.ALT_ALLOW_DROP_AUGMENTED)) {
            return false;
        }
        if (!ItemFunctions.checkIfCanDiscard(player, this)) {
            return false;
        }
        if (this.getVisibleItemId() != this.getItemId() && !Config.ALT_ALLOW_DROP_APPAREANCED) {
            return false;
        }
        return this.d.isDropable();
    }

    public boolean canBeTraded(Player player) {
        if (this.isEquipped()) {
            return false;
        }
        if (player.getPlayerAccess().CanTradeAnyItem) {
            return true;
        }
        if ((this.getCustomFlags() & 2) == 2) {
            return false;
        }
        if (this.isShadowItem()) {
            return false;
        }
        if (this.isTemporalItem()) {
            return false;
        }
        if ((this.isAugmented() || this.isEnsouled()) && !Config.ALT_ALLOW_TRADE_AUGMENTED) {
            return false;
        }
        if (!ItemFunctions.checkIfCanDiscard(player, this)) {
            return false;
        }
        if (this.getVisibleItemId() != this.getItemId() && !Config.ALT_ALLOW_TRADE_APPAREANCED) {
            return false;
        }
        return this.d.isTradeable();
    }

    public boolean canBeSold(Player player) {
        if ((this.getCustomFlags() & 0x20) == 32) {
            return false;
        }
        if (this.getItemId() == 57) {
            return false;
        }
        if (this.d.getReferencePrice() == 0L) {
            return false;
        }
        if (this.isShadowItem()) {
            return false;
        }
        if (this.isTemporalItem()) {
            return false;
        }
        if ((this.isAugmented() || this.isEnsouled()) && !Config.ALT_ALLOW_TRADE_AUGMENTED) {
            return false;
        }
        if (this.getVisibleItemId() != this.getItemId() && !Config.ALT_ALLOW_TRADE_APPAREANCED) {
            return false;
        }
        if (this.isEquipped()) {
            return false;
        }
        if (!ItemFunctions.checkIfCanDiscard(player, this)) {
            return false;
        }
        return this.d.isSellable();
    }

    public boolean canBeStored(Player player, boolean bl) {
        if ((this.getCustomFlags() & 4) == 4) {
            return false;
        }
        if (!this.getTemplate().isStoreable()) {
            return false;
        }
        if (!bl && (this.isShadowItem() || this.isTemporalItem())) {
            return false;
        }
        if (!bl && (this.isAugmented() || this.isEnsouled()) && !Config.ALT_ALLOW_TRADE_AUGMENTED) {
            return false;
        }
        if (this.getVisibleItemId() != this.getItemId() && !Config.ALT_ALLOW_TRADE_APPAREANCED) {
            return false;
        }
        if (this.isEquipped()) {
            return false;
        }
        if (!ItemFunctions.checkIfCanDiscard(player, this)) {
            return false;
        }
        return bl || this.d.isTradeable();
    }

    public boolean canBeCrystallized(Player player) {
        if ((this.getCustomFlags() & 8) == 8) {
            return false;
        }
        if (this.isShadowItem()) {
            return false;
        }
        if (this.isTemporalItem()) {
            return false;
        }
        if (!ItemFunctions.checkIfCanDiscard(player, this)) {
            return false;
        }
        return this.d.isCrystallizable();
    }

    public boolean canBeEnchanted(boolean bl) {
        if ((this.getCustomFlags() & 0x10) == 16) {
            return false;
        }
        return this.d.canBeEnchanted(bl);
    }

    public boolean canBeExchanged(Player player) {
        if ((this.getCustomFlags() & 0x20) == 32) {
            return false;
        }
        if (this.isShadowItem()) {
            return false;
        }
        if (this.isTemporalItem()) {
            return false;
        }
        if (!ItemFunctions.checkIfCanDiscard(player, this)) {
            return false;
        }
        return this.d.isDestroyable();
    }

    public boolean canBeFreighted(Player player) {
        if ((this.getCustomFlags() & 0x40) == 64) {
            return false;
        }
        if (this.isShadowItem()) {
            return false;
        }
        if (this.isTemporalItem()) {
            return false;
        }
        if (this.isAugmented()) {
            return false;
        }
        if (this.isEnsouled()) {
            return false;
        }
        if (this.getTemplate().isQuest()) {
            return false;
        }
        if (this.isEquipped()) {
            return false;
        }
        if (!ItemFunctions.checkIfCanDiscard(player, this)) {
            return false;
        }
        return this.d.isFreightable();
    }

    public boolean isShadowItem() {
        return this.d.isShadowItem();
    }

    public boolean isTemporalItem() {
        return this.d.isTemporal();
    }

    public boolean isAltSeed() {
        return this.d.isAltSeed();
    }

    public boolean isCursed() {
        return this.d.isCursed();
    }

    public void dropToTheGround(Player player, NpcInstance npcInstance) {
        Creature creature = npcInstance;
        if (creature == null) {
            creature = player;
        }
        Location location = Location.findAroundPosition(creature, 128);
        if (player != null) {
            this.d = new HashIntSet(1, 2.0f);
            for (Player player2 : player.getPlayerGroup()) {
                this.d.add(player2.getObjectId());
            }
            this.cz = System.currentTimeMillis();
            this.cz = npcInstance != null ? (Config.IGNORE_ITEM_PICKUP_DELAY_MONSTER_IDS.contains(npcInstance.getNpcId()) ? (this.cz += Config.IGNORE_ITEM_PICKUP_DELAY) : (npcInstance.isRaid() ? (this.cz += Config.NONOWNER_ITEM_PICKUP_DELAY_RAID) : (this.cz += Config.NONOWNER_ITEM_PICKUP_DELAY))) : (this.cz += Config.NONOWNER_ITEM_PICKUP_DELAY);
        }
        this.dropMe(creature, location);
        if (this.isHerb()) {
            ItemsAutoDestroy.getInstance().addHerb(this);
        } else if (Config.AUTODESTROY_ITEM_AFTER > 0 && !this.isCursed()) {
            ItemsAutoDestroy.getInstance().addItem(this);
        }
    }

    public void dropToTheGround(Creature creature, Location location) {
        if (GeoEngine.canMoveToCoord(creature.getX(), creature.getY(), creature.getZ(), location.x, location.y, location.z, creature.getGeoIndex())) {
            this.dropMe(creature, location);
        } else {
            this.dropMe(creature, creature.getLoc());
        }
        if (this.isHerb()) {
            ItemsAutoDestroy.getInstance().addHerb(this);
        } else if (Config.AUTODESTROY_ITEM_AFTER > 0 && !this.isCursed()) {
            ItemsAutoDestroy.getInstance().addItem(this);
        }
    }

    public void dropToTheGround(Playable playable, Location location) {
        this.setLocation(ItemLocation.VOID);
        this.save();
        if (GeoEngine.canMoveToCoord(playable.getX(), playable.getY(), playable.getZ(), location.x, location.y, location.z, playable.getGeoIndex())) {
            this.dropMe(playable, location);
        } else {
            this.dropMe(playable, playable.getLoc());
        }
        if (this.isHerb()) {
            ItemsAutoDestroy.getInstance().addHerb(this);
        } else if (Config.AUTODESTROY_ITEM_AFTER > 0 && !this.isCursed()) {
            ItemsAutoDestroy.getInstance().addItem(this);
        }
    }

    public void dropMe(Creature creature, Location location) {
        if (creature != null) {
            this.setReflection(creature.getReflection());
        }
        this.spawnMe0(location, creature);
    }

    public final void pickupMe() {
        this.decayMe();
        this.setReflection(ReflectionManager.DEFAULT);
    }

    public ItemTemplate.ItemClass getItemClass() {
        return this.d.getItemClass();
    }

    private int getDefence(Element element) {
        return this.isArmor() ? this.getAttributeElementValue(element, true) : 0;
    }

    public int getDefenceFire() {
        return this.getDefence(Element.FIRE);
    }

    public int getDefenceWater() {
        return this.getDefence(Element.WATER);
    }

    public int getDefenceWind() {
        return this.getDefence(Element.WIND);
    }

    public int getDefenceEarth() {
        return this.getDefence(Element.EARTH);
    }

    public int getDefenceHoly() {
        return this.getDefence(Element.HOLY);
    }

    public int getDefenceUnholy() {
        return this.getDefence(Element.UNHOLY);
    }

    public int getAttributeElementValue(Element element, boolean bl) {
        return this.b.getValue(element) + (bl ? this.d.getBaseAttributeValue(element) : 0);
    }

    public Element getAttributeElement() {
        return this.b.getElement();
    }

    public int getAttributeElementValue() {
        return this.b.getValue();
    }

    public Element getAttackElement() {
        Element element;
        Element element2 = element = this.isWeapon() ? this.getAttributeElement() : Element.NONE;
        if (element == Element.NONE) {
            for (Element element3 : Element.VALUES) {
                if (this.d.getBaseAttributeValue(element3) <= 0) continue;
                return element3;
            }
        }
        return element;
    }

    public int getAttackElementValue() {
        return this.isWeapon() ? this.getAttributeElementValue(this.getAttackElement(), true) : 0;
    }

    public void setAttributeElement(Element element, int n) {
        this.b.setValue(element, n);
        this.getItemStateFlag().set(ItemStateFlags.STATE_CHANGED, true);
    }

    public boolean isHerb() {
        return this.getTemplate().isHerb();
    }

    public Grade getCrystalType() {
        return this.d.getCrystalType();
    }

    public int getCrystalItemId() {
        return this.d.getCrystalItemId();
    }

    @Override
    public String getName() {
        return this.getTemplate().getName();
    }

    public void save() {
        b.store(this);
    }

    public void delete() {
        b.delete(this);
    }

    @Override
    public List<L2GameServerPacket> addPacketList(Player player, Creature creature) {
        L2GameServerPacket l2GameServerPacket = null;
        l2GameServerPacket = creature != null ? new DropItem(this, creature.getObjectId()) : new SpawnItem(this);
        return Collections.singletonList(l2GameServerPacket);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getTemplate().getItemId());
        stringBuilder.append(" ");
        if (this.getEnchantLevel() > 0) {
            stringBuilder.append("+");
            stringBuilder.append(this.getEnchantLevel());
            stringBuilder.append(" ");
        }
        stringBuilder.append(this.getTemplate().getName());
        if (!this.getTemplate().getAdditionalName().isEmpty()) {
            stringBuilder.append(" ");
            stringBuilder.append("\\").append(this.getTemplate().getAdditionalName()).append("\\");
        }
        stringBuilder.append(" ");
        stringBuilder.append("(");
        stringBuilder.append(this.getCount());
        stringBuilder.append(")");
        stringBuilder.append("[");
        stringBuilder.append(this.getObjectId());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public boolean isItem() {
        return true;
    }

    public ItemAttachment getAttachment() {
        return this.a;
    }

    public void setAttachment(ItemAttachment itemAttachment) {
        ItemAttachment itemAttachment2 = this.a;
        this.a = itemAttachment;
        if (this.a != null) {
            this.a.setItem(this);
        }
        if (itemAttachment2 != null) {
            itemAttachment2.setItem(null);
        }
    }

    public int[] getEnchantOptions() {
        return this.aM;
    }

    public IntSet getDropPlayers() {
        return this.d;
    }

    public static final class ItemLocation
    extends Enum<ItemLocation> {
        public static final /* enum */ ItemLocation VOID = new ItemLocation();
        public static final /* enum */ ItemLocation INVENTORY = new ItemLocation();
        public static final /* enum */ ItemLocation PAPERDOLL = new ItemLocation();
        public static final /* enum */ ItemLocation PET_INVENTORY = new ItemLocation();
        public static final /* enum */ ItemLocation PET_PAPERDOLL = new ItemLocation();
        public static final /* enum */ ItemLocation WAREHOUSE = new ItemLocation();
        public static final /* enum */ ItemLocation CLANWH = new ItemLocation();
        public static final /* enum */ ItemLocation FREIGHT = new ItemLocation();
        @Deprecated
        public static final /* enum */ ItemLocation LEASE = new ItemLocation();
        public static final /* enum */ ItemLocation MAIL = new ItemLocation();
        private static final /* synthetic */ ItemLocation[] a;

        public static ItemLocation[] values() {
            return (ItemLocation[])a.clone();
        }

        public static ItemLocation valueOf(String string) {
            return Enum.valueOf(ItemLocation.class, string);
        }

        private static /* synthetic */ ItemLocation[] a() {
            return new ItemLocation[]{VOID, INVENTORY, PAPERDOLL, PET_INVENTORY, PET_PAPERDOLL, WAREHOUSE, CLANWH, FREIGHT, LEASE, MAIL};
        }

        static {
            a = ItemLocation.a();
        }
    }

    public class FuncAttack
    extends Func {
        private final Element a;

        public FuncAttack(Element element, int n, Object object) {
            super(element.getAttack(), n, object);
            this.a = element;
        }

        @Override
        public void calc(Env env) {
            env.value += (double)ItemInstance.this.getAttributeElementValue(this.a, true);
        }
    }

    public class FuncDefence
    extends Func {
        private final Element b;

        public FuncDefence(Element element, int n, Object object) {
            super(element.getDefence(), n, object);
            this.b = element;
        }

        @Override
        public void calc(Env env) {
            env.value += (double)ItemInstance.this.getAttributeElementValue(this.b, true);
        }
    }
}
