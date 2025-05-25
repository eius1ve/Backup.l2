/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.Containers
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.HashIntObjectMap
 */
package l2.gameserver.templates.item;

import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;
import l2.commons.lang.ArrayUtils;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.CrystalGradeDataHolder;
import l2.gameserver.handler.items.IItemHandler;
import l2.gameserver.instancemanager.CursedWeaponsManager;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.base.Element;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.StatTemplate;
import l2.gameserver.stats.conditions.Condition;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.stats.funcs.FuncTemplate;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.item.ActionType;
import l2.gameserver.templates.item.EtcItemTemplate;
import l2.gameserver.templates.item.ItemFlags;
import l2.gameserver.templates.item.ItemType;
import l2.gameserver.templates.item.WeaponTemplate;
import l2.gameserver.templates.item.support.Grade;
import org.napile.primitive.Containers;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.HashIntObjectMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class ItemTemplate
extends StatTemplate {
    public static final int ITEM_ID_PC_BANG_POINTS = -100;
    public static final int ITEM_ID_CLAN_REPUTATION_SCORE = -200;
    public static final int ITEM_ID_FAME = -300;
    public static final int ITEM_ID_RAID_POINTS = -500;
    public static final int ITEM_ID_ADENA = 57;
    public static final int[] ITEM_ID_CASTLE_CIRCLET = new int[]{0, 6838, 6835, 6839, 6837, 6840, 6834, 6836, 8182, 8183};
    public static final int ITEM_ID_FORMAL_WEAR = 6408;
    public static final int TYPE1_WEAPON_RING_EARRING_NECKLACE = 0;
    public static final int TYPE1_SHIELD_ARMOR = 1;
    public static final int TYPE1_OTHER = 2;
    public static final int TYPE1_ITEM_QUESTITEM_ADENA = 4;
    public static final int TYPE2_WEAPON = 0;
    public static final int TYPE2_SHIELD_ARMOR = 1;
    public static final int TYPE2_ACCESSORY = 2;
    public static final int TYPE2_QUEST = 3;
    public static final int TYPE2_MONEY = 4;
    public static final int TYPE2_OTHER = 5;
    public static final int TYPE2_PET_WOLF = 6;
    public static final int TYPE2_PET_HATCHLING = 7;
    public static final int TYPE2_PET_STRIDER = 8;
    public static final int TYPE2_NODROP = 9;
    public static final int TYPE2_PET_GWOLF = 10;
    public static final int TYPE2_PENDANT = 11;
    public static final int TYPE2_PET_BABY = 12;
    public static final long SLOT_NONE = 0L;
    public static final long SLOT_UNDERWEAR = 1L;
    public static final long SLOT_R_EAR = 2L;
    public static final long SLOT_L_EAR = 4L;
    public static final long SLOT_NECK = 8L;
    public static final long SLOT_R_FINGER = 16L;
    public static final long SLOT_L_FINGER = 32L;
    public static final long SLOT_HEAD = 64L;
    public static final long SLOT_R_HAND = 128L;
    public static final long SLOT_L_HAND = 256L;
    public static final long SLOT_GLOVES = 512L;
    public static final long SLOT_CHEST = 1024L;
    public static final long SLOT_LEGS = 2048L;
    public static final long SLOT_FEET = 4096L;
    public static final long SLOT_BACK = 8192L;
    public static final long SLOT_LR_HAND = 16384L;
    public static final long SLOT_FULL_ARMOR = 32768L;
    public static final long SLOT_HAIR = 65536L;
    public static final long SLOT_FORMAL_WEAR = 131072L;
    public static final long SLOT_DHAIR = 262144L;
    public static final long SLOT_HAIRALL = 524288L;
    public static final long SLOT_R_BRACELET = 0x100000L;
    public static final long SLOT_L_BRACELET = 0x200000L;
    public static final long SLOT_DECO = 0x400000L;
    public static final long SLOT_BELT = 0x10000000L;
    public static final long SLOT_BROOCH = 0x20000000L;
    public static final long SLOT_BROOCH_JEWEL = 0x40000000L;
    public static final long SLOT_AGHATION_CHARM = 0x3000000000L;
    public static final long SLOT_WOLF = -100L;
    public static final long SLOT_HATCHLING = -101L;
    public static final long SLOT_STRIDER = -102L;
    public static final long SLOT_BABYPET = -103L;
    public static final long SLOT_GWOLF = -104L;
    public static final long SLOT_PENDANT = -105L;
    public static final long SLOTS_ARMOR = 180032L;
    public static final long SLOTS_JEWELRY = 62L;
    public static final int ATTRIBUTE_NONE = -2;
    public static final int ATTRIBUTE_FIRE = 0;
    public static final int ATTRIBUTE_WATER = 1;
    public static final int ATTRIBUTE_WIND = 2;
    public static final int ATTRIBUTE_EARTH = 3;
    public static final int ATTRIBUTE_HOLY = 4;
    public static final int ATTRIBUTE_DARK = 5;
    protected final int _itemId;
    private final ItemClass a;
    protected final String _name;
    protected final String _addname;
    protected final String _icon;
    protected final String _icon32;
    protected int _type1;
    protected int _type2;
    private final int Ga;
    protected final Grade _crystalType;
    protected final int _crystalItemId;
    private final int Gb;
    protected long _bodyPart;
    private final long dV;
    private final int Gc;
    private final boolean hf;
    private final boolean hg;
    private final boolean hh;
    private int _flags;
    private final ReuseType a;
    private final int Gd;
    private final int Ge;
    private final int Gf;
    private final int Gg;
    private final int Gh;
    private final int Gi;
    protected Skill[] _skills;
    private Optional<Skill> a;
    private Skill w = null;
    public ItemType type;
    private int[] bj;
    private IntObjectMap<int[]> r;
    private Condition[] _conditions;
    private IItemHandler a;
    private boolean hi;
    private final ActionType a = Optional.empty();
    private boolean hj = false;

    protected ItemTemplate(StatsSet statsSet) {
        this.bj = new int[6];
        this.r = Containers.emptyIntObjectMap();
        this._conditions = Condition.EMPTY_ARRAY;
        this.a = IItemHandler.NULL;
        this._itemId = statsSet.getInteger("item_id");
        this.a = statsSet.getEnum("class", ItemClass.class, ItemClass.OTHER);
        this._name = statsSet.getString("name");
        this._addname = statsSet.getString("add_name", "");
        this._icon = statsSet.getString("icon", "");
        this._icon32 = "<img src=" + this._icon + " width=32 height=32>";
        this.Ga = statsSet.getInteger("weight", 0);
        this.hh = statsSet.getBool("crystallizable", false);
        this.hg = statsSet.getBool("stackable", false);
        this._crystalType = Objects.requireNonNull(CrystalGradeDataHolder.getInstance().getGrade(statsSet.getString("crystal_type", Grade.NONE.getId())), "Undefined grade");
        this._crystalItemId = statsSet.getInteger("crystal_item_id", this._crystalType.getCry());
        this.Gb = statsSet.getInteger("durability", -1);
        this.hf = statsSet.getBool("temporal", false);
        this._bodyPart = statsSet.getLong("bodypart", 0L);
        this.dV = statsSet.getLong("price", 0L);
        this.Gc = statsSet.getInteger("crystal_count", 0);
        this.a = statsSet.getEnum("reuse_type", ReuseType.class, ReuseType.NORMAL);
        this.Gd = statsSet.getInteger("reuse_delay", 0);
        this.Ge = statsSet.getInteger("delay_share_group", -this._itemId);
        this.Gf = statsSet.getInteger("ensoul_slots", 0);
        this.Gg = statsSet.getInteger("ensoul_bm_slots", 0);
        this.Gh = statsSet.getInteger("equip_reuse_delay", -1);
        this.Gi = statsSet.getInteger("enchanted", 0);
        this.a = statsSet.getEnum("default_action", ActionType.class, ActionType.ACTION_NONE);
        for (ItemFlags itemFlags : ItemFlags.VALUES) {
            boolean bl = statsSet.getBool(itemFlags.name().toLowerCase(), itemFlags.getDefaultValue());
            if (this._name.contains("{PvP}")) {
                if (itemFlags == ItemFlags.TRADEABLE && Config.ALT_PVP_ITEMS_TREDABLE) {
                    bl = true;
                }
                if (itemFlags == ItemFlags.ATTRIBUTABLE && Config.ALT_PVP_ITEMS_ATTRIBUTABLE) {
                    bl = true;
                }
                if (itemFlags == ItemFlags.AUGMENTABLE && Config.ALT_PVP_ITEMS_AUGMENTABLE) {
                    bl = true;
                }
            }
            if (!bl) continue;
            this.a(itemFlags);
        }
        this._funcTemplates = FuncTemplate.EMPTY_ARRAY;
        this._skills = Skill.EMPTY_ARRAY;
    }

    public ItemType getItemType() {
        return this.type;
    }

    public String getIcon() {
        return this._icon;
    }

    public String getIcon32() {
        return this._icon32;
    }

    public final int getDurability() {
        return this.Gb;
    }

    public final boolean isTemporal() {
        return this.hf;
    }

    public final int getItemId() {
        return this._itemId;
    }

    public abstract long getItemMask();

    public final int getType2() {
        return this._type2;
    }

    public final int getBaseAttributeValue(Element element) {
        if (element == Element.NONE) {
            return 0;
        }
        return this.bj[element.getId()];
    }

    public void setBaseAtributeElements(int[] nArray) {
        this.bj = nArray;
    }

    public int getBaseEnchantLevel() {
        return this.Gi;
    }

    public final int getType2ForPackets() {
        int n = this._type2;
        switch (this._type2) {
            case 6: 
            case 7: 
            case 8: 
            case 10: 
            case 12: {
                if (this._bodyPart == 1024L) {
                    n = 1;
                    break;
                }
                n = 0;
                break;
            }
            case 11: {
                n = 2;
            }
        }
        return n;
    }

    public final int getWeight() {
        return this.Ga;
    }

    public final boolean isCrystallizable() {
        return this.hh && !this.isStackable() && this.getCrystalType() != Grade.NONE && this.getCrystalCount() > 0;
    }

    public final Grade getCrystalType() {
        return this._crystalType;
    }

    public final Grade getItemGrade() {
        return this.getCrystalType();
    }

    public final int getCrystalCount() {
        return this.Gc;
    }

    public final int getCrystalCount(int n, boolean bl) {
        int n2 = this.Gc;
        if (bl) {
            n2 /= 2;
        }
        if (n > 0 && Config.CRYSTALLIZE_BONUS_AT_ENCHANT) {
            int n3 = this.isWeapon() ? this.getItemGrade().getCryMod() : this.getItemGrade().getCryMod() / 10;
            n2 += n * n3;
            int n4 = n - 3;
            if (n4 > 0) {
                int n5 = this.isWeapon() ? this.getItemGrade().getCryMod() : this.getItemGrade().getCryMod() / 5;
                n2 += n4 * n5;
            }
        }
        return n2;
    }

    public final int getCrystalItemId() {
        return this._crystalItemId;
    }

    public final String getName() {
        return this._name;
    }

    public final String getAdditionalName() {
        return this._addname;
    }

    public final long getBodyPart() {
        return this._bodyPart;
    }

    public final int getType1() {
        return this._type1;
    }

    public final boolean isStackable() {
        return this.hg;
    }

    public final long getReferencePrice() {
        return this.dV;
    }

    public boolean isForHatchling() {
        return this._type2 == 7;
    }

    public boolean isForStrider() {
        return this._type2 == 8;
    }

    public boolean isForWolf() {
        return this._type2 == 6;
    }

    public boolean isForPetBaby() {
        return this._type2 == 12;
    }

    public boolean isForGWolf() {
        return this._type2 == 10;
    }

    public boolean isPendant() {
        return this._type2 == 11;
    }

    public boolean isForPet() {
        return this._type2 == 11 || this._type2 == 7 || this._type2 == 6 || this._type2 == 8 || this._type2 == 10 || this._type2 == 12;
    }

    public void attachSkill(Skill skill) {
        this._skills = ArrayUtils.add(this._skills, skill);
    }

    public Skill[] getAttachedSkills() {
        return this._skills;
    }

    public Skill getFirstSkill() {
        if (this._skills.length > 0) {
            return this._skills[0];
        }
        return null;
    }

    public Optional<Skill> getEnchant4Skill() {
        return this.a;
    }

    public Skill getEnchant6Skill() {
        return this.w;
    }

    public String toString() {
        return this._itemId + " " + this._name;
    }

    public boolean isShadowItem() {
        return this.Gb > 0 && !this.isTemporal();
    }

    public boolean isSealedItem() {
        return this._name.startsWith("Sealed");
    }

    public boolean isAltSeed() {
        return this._name.contains("Alternative");
    }

    public ItemClass getItemClass() {
        return this.a;
    }

    public boolean isSealStone() {
        switch (this._itemId) {
            case 6360: 
            case 6361: 
            case 6362: {
                return true;
            }
        }
        return false;
    }

    public boolean isAdena() {
        return this._itemId == 57;
    }

    public boolean isEquipment() {
        return this._type1 != 4;
    }

    public boolean isKeyMatherial() {
        return this.a == ItemClass.PIECES;
    }

    public boolean isRecipe() {
        return this.a == ItemClass.RECIPIES;
    }

    public boolean isArrow() {
        return this.type == EtcItemTemplate.EtcItemType.ARROW;
    }

    public final boolean isQuiver() {
        return this.type == EtcItemTemplate.EtcItemType.ARROW_QUIVER;
    }

    public boolean isBelt() {
        return this._bodyPart == 0x10000000L;
    }

    public boolean isBracelet() {
        return this._bodyPart == 0x100000L || this._bodyPart == 0x200000L;
    }

    public boolean isBrooche() {
        return this._bodyPart == 0x20000000L;
    }

    public boolean isBroochJewel() {
        return this._bodyPart == 0x40000000L;
    }

    public boolean isAgathionCharm() {
        return this._bodyPart == 0x3000000000L;
    }

    public boolean isUnderwear() {
        return this._bodyPart == 1L;
    }

    public boolean isCloak() {
        return this._bodyPart == 8192L;
    }

    public boolean isTalisman() {
        return this._bodyPart == 0x400000L;
    }

    public boolean isHerb() {
        return this.type == EtcItemTemplate.EtcItemType.HERB;
    }

    public boolean isHeroWeapon() {
        return this._itemId >= 6611 && this._itemId <= 6621;
    }

    public boolean isCursed() {
        return CursedWeaponsManager.getInstance().isCursed(this._itemId);
    }

    public boolean isMercenaryTicket() {
        return this.type == EtcItemTemplate.EtcItemType.MERCENARY_TICKET;
    }

    public boolean isRod() {
        return this.getItemType() == WeaponTemplate.WeaponType.ROD;
    }

    public boolean isWeapon() {
        return this.getType2() == 0;
    }

    public boolean isArmor() {
        return this.getType2() == 1;
    }

    public boolean isAccessory() {
        return this.getType2() == 2;
    }

    public boolean isQuest() {
        return this.getType2() == 3;
    }

    public boolean isMageItem() {
        return false;
    }

    public boolean canBeEnchanted(@Deprecated boolean bl) {
        if (bl && this.getCrystalType() == Grade.NONE) {
            return false;
        }
        if (this.isCursed()) {
            return false;
        }
        if (this.isQuest()) {
            return false;
        }
        return this.isEnchantable();
    }

    public boolean isEquipable() {
        return this.getItemType() == EtcItemTemplate.EtcItemType.BAIT || this.getItemType() == EtcItemTemplate.EtcItemType.ARROW || this.getItemType() == EtcItemTemplate.EtcItemType.ARROW_QUIVER || this.getBodyPart() != 0L && !(this instanceof EtcItemTemplate);
    }

    public void setEnchant4Skill(Skill skill) {
        this.a = Optional.ofNullable(skill);
    }

    public void setEnchant6Skill(Skill skill) {
        this.w = skill;
    }

    public boolean testCondition(Playable playable, ItemInstance itemInstance) {
        return this.testCondition(playable, itemInstance, true);
    }

    public boolean testCondition(Playable playable, ItemInstance itemInstance, boolean bl) {
        if (this.getConditions().length == 0) {
            return true;
        }
        Env env = new Env();
        env.character = playable;
        env.item = itemInstance;
        for (Condition condition : this.getConditions()) {
            if (condition.test(env)) continue;
            if (bl) {
                Player player;
                String string;
                SystemMsg systemMsg = condition.getSystemMsg();
                if (systemMsg != null) {
                    if (systemMsg.size() > 0) {
                        playable.sendPacket((IStaticPacket)new SystemMessage(systemMsg).addItemName(this.getItemId()));
                    } else {
                        playable.sendPacket((IStaticPacket)new SystemMessage(systemMsg));
                    }
                }
                if ((string = condition.getCustomMessage()) != null && playable.isPlayer() && (player = playable.getPlayer()) != null) {
                    player.sendMessage(new CustomMessage(string, player, new Object[0]));
                }
            }
            return false;
        }
        return true;
    }

    public void addCondition(Condition condition) {
        this._conditions = ArrayUtils.add(this._conditions, condition);
    }

    public Condition[] getConditions() {
        return this._conditions;
    }

    public boolean isEnchantable() {
        return this.hasFlag(ItemFlags.ENCHANTABLE);
    }

    public boolean isTradeable() {
        return this.hasFlag(ItemFlags.TRADEABLE);
    }

    public boolean isDestroyable() {
        return this.hasFlag(ItemFlags.DESTROYABLE);
    }

    public boolean isDropable() {
        return this.hasFlag(ItemFlags.DROPABLE);
    }

    public final boolean isSellable() {
        return this.hasFlag(ItemFlags.SELLABLE);
    }

    public final boolean isAugmentable() {
        return this.hasFlag(ItemFlags.AUGMENTABLE);
    }

    public final boolean isAttributable() {
        return this.hasFlag(ItemFlags.ATTRIBUTABLE);
    }

    public final boolean isStoreable() {
        return this.hasFlag(ItemFlags.STOREABLE);
    }

    public final boolean isFreightable() {
        return this.hasFlag(ItemFlags.FREIGHTABLE);
    }

    public boolean hasFlag(ItemFlags itemFlags) {
        return (this._flags & itemFlags.mask()) == itemFlags.mask();
    }

    private void a(ItemFlags itemFlags) {
        this._flags |= itemFlags.mask();
    }

    public IItemHandler getHandler() {
        return this.a;
    }

    public void setHandler(IItemHandler iItemHandler) {
        this.a = iItemHandler;
    }

    public boolean isShotItem() {
        return this.hi;
    }

    public void setIsShotItem(boolean bl) {
        this.hi = bl;
    }

    public int getReuseDelay() {
        return this.Gd;
    }

    public int getReuseGroup() {
        return this.Ge;
    }

    public int getDisplayReuseGroup() {
        return this.Ge < 0 ? -1 : this.Ge;
    }

    public int getEquipReuseDelay() {
        return this.Gh;
    }

    public int getEnsoulNormalSlots() {
        return this.Gf;
    }

    public int getEnsoulBmSlots() {
        return this.Gg;
    }

    public void addEnchantOptions(int n, int[] nArray) {
        if (this.r.isEmpty()) {
            this.r = new HashIntObjectMap();
        }
        this.r.put(n, (Object)nArray);
    }

    public IntObjectMap<int[]> getEnchantOptions() {
        return this.r;
    }

    public ReuseType getReuseType() {
        return this.a;
    }

    public void setStatDisabled(boolean bl) {
        this.hj = bl;
    }

    @Override
    public FuncTemplate[] getAttachedFuncs() {
        if (this.hj) {
            return FuncTemplate.EMPTY_ARRAY;
        }
        return super.getAttachedFuncs();
    }

    @Override
    public Func[] getStatFuncs(Object object) {
        if (this.hj) {
            return Func.EMPTY_FUNC_ARRAY;
        }
        return super.getStatFuncs(object);
    }

    public ActionType getDefaultAction() {
        return this.a;
    }

    public static final class ItemClass
    extends Enum<ItemClass> {
        public static final /* enum */ ItemClass ALL = new ItemClass();
        public static final /* enum */ ItemClass WEAPON = new ItemClass();
        public static final /* enum */ ItemClass ARMOR = new ItemClass();
        public static final /* enum */ ItemClass JEWELRY = new ItemClass();
        public static final /* enum */ ItemClass ACCESSORY = new ItemClass();
        public static final /* enum */ ItemClass CONSUMABLE = new ItemClass();
        public static final /* enum */ ItemClass MATHERIALS = new ItemClass();
        public static final /* enum */ ItemClass PIECES = new ItemClass();
        public static final /* enum */ ItemClass RECIPIES = new ItemClass();
        public static final /* enum */ ItemClass SPELLBOOKS = new ItemClass();
        public static final /* enum */ ItemClass MISC = new ItemClass();
        public static final /* enum */ ItemClass OTHER = new ItemClass();
        private static final /* synthetic */ ItemClass[] a;

        public static ItemClass[] values() {
            return (ItemClass[])a.clone();
        }

        public static ItemClass valueOf(String string) {
            return Enum.valueOf(ItemClass.class, string);
        }

        private static /* synthetic */ ItemClass[] a() {
            return new ItemClass[]{ALL, WEAPON, ARMOR, JEWELRY, ACCESSORY, CONSUMABLE, MATHERIALS, PIECES, RECIPIES, SPELLBOOKS, MISC, OTHER};
        }

        static {
            a = ItemClass.a();
        }
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static abstract class ReuseType
    extends Enum<ReuseType> {
        public static final /* enum */ ReuseType NORMAL = new ReuseType(new SystemMsg[]{SystemMsg.THERE_ARE_S2_SECONDS_REMAINING_IN_S1S_REUSE_TIME, SystemMsg.THERE_ARE_S2_MINUTES_S3_SECONDS_REMAINING_IN_S1S_REUSE_TIME, SystemMsg.THERE_ARE_S2_HOURS_S3_MINUTES_AND_S4_SECONDS_REMAINING_IN_S1S_REUSE_TIME}){

            @Override
            public long next(ItemInstance itemInstance) {
                return System.currentTimeMillis() + (long)itemInstance.getTemplate().getReuseDelay();
            }
        };
        public static final /* enum */ ReuseType EVERY_DAY_AT_6_30 = new ReuseType(new SystemMsg[]{SystemMsg.THERE_ARE_S2_SECONDS_REMAINING_FOR_S1S_REUSE_TIME, SystemMsg.THERE_ARE_S2_MINUTES_S3_SECONDS_REMAINING_FOR_S1S_REUSE_TIME, SystemMsg.THERE_ARE_S2_HOURS_S3_MINUTES_S4_SECONDS_REMAINING_FOR_S1S_REUSE_TIME}){

            @Override
            public long next(ItemInstance itemInstance) {
                Calendar calendar = Calendar.getInstance();
                if (calendar.get(11) > 6 || calendar.get(11) == 6 && calendar.get(12) >= 30) {
                    calendar.add(5, 1);
                }
                calendar.set(11, 6);
                calendar.set(12, 30);
                return calendar.getTimeInMillis();
            }
        };
        private SystemMsg[] b;
        private static final /* synthetic */ ReuseType[] a;

        public static ReuseType[] values() {
            return (ReuseType[])a.clone();
        }

        public static ReuseType valueOf(String string) {
            return Enum.valueOf(ReuseType.class, string);
        }

        private ReuseType(SystemMsg ... systemMsgArray) {
            this.b = systemMsgArray;
        }

        public abstract long next(ItemInstance var1);

        public SystemMsg[] getMessages() {
            return this.b;
        }

        private static /* synthetic */ ReuseType[] a() {
            return new ReuseType[]{NORMAL, EVERY_DAY_AT_6_30};
        }

        static {
            a = ReuseType.a();
        }
    }
}
