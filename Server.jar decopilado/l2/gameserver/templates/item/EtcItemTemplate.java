/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item;

import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.item.ArmorTemplate;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.item.ItemType;
import l2.gameserver.templates.item.WeaponTemplate;

public final class EtcItemTemplate
extends ItemTemplate {
    public EtcItemTemplate(StatsSet statsSet) {
        super(statsSet);
        this.type = statsSet.getEnum("type", EtcItemType.class);
        this._type1 = 4;
        switch (this.getItemType()) {
            case QUEST: 
            case RUNE_QUEST: {
                this._type2 = 3;
                break;
            }
            case MONEY: {
                this._type2 = 4;
                break;
            }
            default: {
                this._type2 = 5;
            }
        }
    }

    @Override
    public EtcItemType getItemType() {
        return (EtcItemType)this.type;
    }

    @Override
    public long getItemMask() {
        return this.getItemType().mask();
    }

    @Override
    public final boolean isShadowItem() {
        return false;
    }

    @Override
    public final boolean canBeEnchanted(boolean bl) {
        return false;
    }

    public static final class EtcItemType
    extends Enum<EtcItemType>
    implements ItemType {
        public static final /* enum */ EtcItemType ARROW = new EtcItemType(1, "Arrow");
        public static final /* enum */ EtcItemType MATERIAL = new EtcItemType(2, "Material");
        public static final /* enum */ EtcItemType PET_COLLAR = new EtcItemType(3, "PetCollar");
        public static final /* enum */ EtcItemType POTION = new EtcItemType(4, "Potion");
        public static final /* enum */ EtcItemType RECIPE = new EtcItemType(5, "Recipe");
        public static final /* enum */ EtcItemType SCROLL = new EtcItemType(6, "Scroll");
        public static final /* enum */ EtcItemType QUEST = new EtcItemType(7, "Quest");
        public static final /* enum */ EtcItemType MONEY = new EtcItemType(8, "Money");
        public static final /* enum */ EtcItemType OTHER = new EtcItemType(9, "Other");
        public static final /* enum */ EtcItemType SPELLBOOK = new EtcItemType(10, "Spellbook");
        public static final /* enum */ EtcItemType SEED = new EtcItemType(11, "Seed");
        public static final /* enum */ EtcItemType BAIT = new EtcItemType(12, "Bait");
        public static final /* enum */ EtcItemType SHOT = new EtcItemType(13, "Shot");
        public static final /* enum */ EtcItemType BOLT = new EtcItemType(14, "Bolt");
        public static final /* enum */ EtcItemType RUNE = new EtcItemType(15, "Rune");
        public static final /* enum */ EtcItemType HERB = new EtcItemType(16, "Herb");
        public static final /* enum */ EtcItemType MERCENARY_TICKET = new EtcItemType(17, "Mercenary Ticket");
        public static final /* enum */ EtcItemType ARROW_QUIVER = new EtcItemType(18, "ArrowQuiver");
        public static final /* enum */ EtcItemType SPIRITSHOT = new EtcItemType(19, "SpiritShot");
        public static final /* enum */ EtcItemType BLESSED_SPIRITSHOT = new EtcItemType(20, "BlessedSpiritShot");
        public static final /* enum */ EtcItemType RUNE_QUEST = new EtcItemType(21, "RuneQuest");
        private final long dU;
        private final String gt;
        private static final /* synthetic */ EtcItemType[] a;

        public static EtcItemType[] values() {
            return (EtcItemType[])a.clone();
        }

        public static EtcItemType valueOf(String string) {
            return Enum.valueOf(EtcItemType.class, string);
        }

        private EtcItemType(int n2, String string2) {
            this.dU = 1L << n2 + WeaponTemplate.WeaponType.VALUES.length + ArmorTemplate.ArmorType.VALUES.length;
            this.gt = string2;
        }

        @Override
        public long mask() {
            return this.dU;
        }

        public String toString() {
            return this.gt;
        }

        private static /* synthetic */ EtcItemType[] a() {
            return new EtcItemType[]{ARROW, MATERIAL, PET_COLLAR, POTION, RECIPE, SCROLL, QUEST, MONEY, OTHER, SPELLBOOK, SEED, BAIT, SHOT, BOLT, RUNE, HERB, MERCENARY_TICKET, ARROW_QUIVER, SPIRITSHOT, BLESSED_SPIRITSHOT, RUNE_QUEST};
        }

        static {
            a = EtcItemType.a();
        }
    }
}
