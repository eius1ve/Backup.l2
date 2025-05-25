/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item;

import l2.gameserver.Config;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.item.Bodypart;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.item.ItemType;
import l2.gameserver.templates.item.WeaponTemplate;

public final class ArmorTemplate
extends ItemTemplate {
    public static final double EMPTY_RING = 5.0;
    public static final double EMPTY_EARRING = 9.0;
    public static final double EMPTY_NECKLACE = 13.0;
    public static final double EMPTY_HELMET = 12.0;
    public static final double EMPTY_BODY_FIGHTER = 31.0;
    public static final double EMPTY_LEGS_FIGHTER = 18.0;
    public static final double EMPTY_BODY_MYSTIC = 15.0;
    public static final double EMPTY_LEGS_MYSTIC = 8.0;
    public static final double EMPTY_GLOVES = 8.0;
    public static final double EMPTY_BOOTS = 7.0;

    public ArmorTemplate(StatsSet statsSet) {
        super(statsSet);
        this.type = statsSet.getEnum("type", ArmorType.class);
        if (this._bodyPart == 8L || (this._bodyPart & 4L) != 0L || (this._bodyPart & 0x20L) != 0L) {
            this._type1 = 0;
            this._type2 = 2;
        } else if (this._bodyPart == 65536L || this._bodyPart == 262144L || this._bodyPart == 524288L) {
            this._type1 = 2;
            this._type2 = Config.ALT_HAIR_TO_ACC_SLOT ? 2 : 5;
        } else {
            this._type1 = 1;
            this._type2 = 1;
        }
        if (this.getItemType() == ArmorType.PET) {
            this._type1 = 1;
            switch (Bodypart.getBodypartByMask(this._bodyPart)) {
                case WOLF: {
                    this._type2 = 6;
                    this._bodyPart = 1024L;
                    break;
                }
                case GREAT_WOLF: {
                    this._type2 = 10;
                    this._bodyPart = 1024L;
                    break;
                }
                case HATCHLING: {
                    this._type2 = 7;
                    this._bodyPart = 1024L;
                    break;
                }
                case PENDANT: {
                    this._type2 = 11;
                    this._bodyPart = 8L;
                    break;
                }
                case BABY_PET: {
                    this._type2 = 12;
                    this._bodyPart = 1024L;
                    break;
                }
                default: {
                    this._type2 = 8;
                    this._bodyPart = 1024L;
                }
            }
        }
    }

    @Override
    public ArmorType getItemType() {
        return (ArmorType)this.type;
    }

    @Override
    public final long getItemMask() {
        return this.getItemType().mask();
    }

    public static final class ArmorType
    extends Enum<ArmorType>
    implements ItemType {
        public static final /* enum */ ArmorType NONE = new ArmorType(1, "None");
        public static final /* enum */ ArmorType LIGHT = new ArmorType(2, "Light");
        public static final /* enum */ ArmorType HEAVY = new ArmorType(3, "Heavy");
        public static final /* enum */ ArmorType MAGIC = new ArmorType(4, "Magic");
        public static final /* enum */ ArmorType PET = new ArmorType(5, "Pet");
        public static final /* enum */ ArmorType SIGIL = new ArmorType(6, "Sigil");
        public static final ArmorType[] VALUES;
        private final long dT;
        private final String gs;
        private static final /* synthetic */ ArmorType[] a;

        public static ArmorType[] values() {
            return (ArmorType[])a.clone();
        }

        public static ArmorType valueOf(String string) {
            return Enum.valueOf(ArmorType.class, string);
        }

        private ArmorType(int n2, String string2) {
            this.dT = 1L << n2 + WeaponTemplate.WeaponType.VALUES.length;
            this.gs = string2;
        }

        @Override
        public long mask() {
            return this.dT;
        }

        public String toString() {
            return this.gs;
        }

        private static /* synthetic */ ArmorType[] a() {
            return new ArmorType[]{NONE, LIGHT, HEAVY, MAGIC, PET, SIGIL};
        }

        static {
            a = ArmorType.a();
            VALUES = ArmorType.values();
        }
    }
}
