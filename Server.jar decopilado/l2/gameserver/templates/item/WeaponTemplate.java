/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item;

import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.FuncTemplate;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.item.ItemType;

public final class WeaponTemplate
extends ItemTemplate {
    private final int Gj;
    private final int Gk;
    private final int Gl;
    private final int Gm;
    private final int Gn;
    private final boolean hk;
    private final int Go;
    private int hG;

    public WeaponTemplate(StatsSet statsSet) {
        super(statsSet);
        this.type = statsSet.getEnum("type", WeaponType.class);
        this.Gj = statsSet.getInteger("soulshots", 0);
        this.Gk = statsSet.getInteger("spiritshots", 0);
        this.hk = statsSet.getBool("is_magic_weapon", false);
        this.Gl = statsSet.getInteger("rnd_dam", 0);
        this.Gm = statsSet.getInteger("atk_reuse", this.type == WeaponType.BOW ? 1500 : 0);
        this.Gn = statsSet.getInteger("mp_consume", 0);
        switch (this.getItemType()) {
            case BOW: {
                this.Go = statsSet.getInteger("attack_range", 500);
                break;
            }
            case POLE: {
                this.Go = statsSet.getInteger("attack_range", 80);
                break;
            }
            default: {
                this.Go = statsSet.getInteger("attack_range", 40);
            }
        }
        if (this.getItemType() == WeaponType.NONE) {
            this._type1 = 1;
            this._type2 = 1;
        } else {
            this._type1 = 0;
            this._type2 = 0;
        }
        if (this.getItemType() == WeaponType.PET) {
            this._type1 = 0;
            this._type2 = this._bodyPart == -100L ? 6 : (this._bodyPart == -104L ? 10 : (this._bodyPart == -101L ? 7 : 8));
            this._bodyPart = 128L;
        }
    }

    @Override
    public WeaponType getItemType() {
        return (WeaponType)this.type;
    }

    @Override
    public long getItemMask() {
        return this.getItemType().mask();
    }

    public int getSoulShotCount() {
        return this.Gj;
    }

    public int getSpiritShotCount() {
        return this.Gk;
    }

    public int getCritical() {
        return this.hG;
    }

    public int getRandomDamage() {
        return this.Gl;
    }

    public int getAttackReuseDelay() {
        return this.Gm;
    }

    public int getMpConsume() {
        return this.Gn;
    }

    public int getAttackRange() {
        return this.Go;
    }

    @Override
    public void attachFunc(FuncTemplate funcTemplate) {
        if (funcTemplate._stat == Stats.CRITICAL_BASE && funcTemplate._order == 8) {
            this.hG = (int)Math.round(funcTemplate._value / 10.0);
        }
        super.attachFunc(funcTemplate);
    }

    @Override
    public boolean isMageItem() {
        return this.hk;
    }

    public static final class WeaponType
    extends Enum<WeaponType>
    implements ItemType {
        public static final /* enum */ WeaponType NONE = new WeaponType(1, "Shield", null);
        public static final /* enum */ WeaponType SWORD = new WeaponType(2, "Sword", Stats.SWORD_WPN_VULNERABILITY);
        public static final /* enum */ WeaponType BLUNT = new WeaponType(3, "Blunt", Stats.BLUNT_WPN_VULNERABILITY);
        public static final /* enum */ WeaponType DAGGER = new WeaponType(4, "Dagger", Stats.DAGGER_WPN_VULNERABILITY);
        public static final /* enum */ WeaponType BOW = new WeaponType(5, "Bow", Stats.BOW_WPN_VULNERABILITY);
        public static final /* enum */ WeaponType POLE = new WeaponType(6, "Pole", Stats.POLE_WPN_VULNERABILITY);
        public static final /* enum */ WeaponType ETC = new WeaponType(7, "Etc", null);
        public static final /* enum */ WeaponType FIST = new WeaponType(8, "Fist", Stats.FIST_WPN_VULNERABILITY);
        public static final /* enum */ WeaponType DUAL = new WeaponType(9, "Dual Sword", Stats.DUAL_WPN_VULNERABILITY);
        public static final /* enum */ WeaponType DUALFIST = new WeaponType(10, "Dual Fist", Stats.FIST_WPN_VULNERABILITY);
        public static final /* enum */ WeaponType BIGSWORD = new WeaponType(11, "Big Sword", Stats.SWORD_WPN_VULNERABILITY);
        public static final /* enum */ WeaponType PET = new WeaponType(12, "Pet", Stats.FIST_WPN_VULNERABILITY);
        public static final /* enum */ WeaponType ROD = new WeaponType(13, "Rod", null);
        public static final /* enum */ WeaponType BIGBLUNT = new WeaponType(14, "Big Blunt", Stats.BLUNT_WPN_VULNERABILITY);
        public static final /* enum */ WeaponType CROSSBOW = new WeaponType(15, "Crossbow", Stats.CROSSBOW_WPN_VULNERABILITY);
        public static final /* enum */ WeaponType RAPIER = new WeaponType(16, "Rapier", Stats.DAGGER_WPN_VULNERABILITY);
        public static final /* enum */ WeaponType ANCIENTSWORD = new WeaponType(17, "Ancient Sword", Stats.SWORD_WPN_VULNERABILITY);
        public static final /* enum */ WeaponType DUALDAGGER = new WeaponType(18, "Dual Dagger", Stats.DAGGER_WPN_VULNERABILITY);
        public static final WeaponType[] VALUES;
        private final long dW;
        private final String gu;
        private final Stats c;
        private static final /* synthetic */ WeaponType[] a;

        public static WeaponType[] values() {
            return (WeaponType[])a.clone();
        }

        public static WeaponType valueOf(String string) {
            return Enum.valueOf(WeaponType.class, string);
        }

        private WeaponType(int n2, String string2, Stats stats) {
            this.dW = 1L << n2;
            this.gu = string2;
            this.c = stats;
        }

        @Override
        public long mask() {
            return this.dW;
        }

        public Stats getDefence() {
            return this.c;
        }

        public String toString() {
            return this.gu;
        }

        private static /* synthetic */ WeaponType[] a() {
            return new WeaponType[]{NONE, SWORD, BLUNT, DAGGER, BOW, POLE, ETC, FIST, DUAL, DUALFIST, BIGSWORD, PET, ROD, BIGBLUNT, CROSSBOW, RAPIER, ANCIENTSWORD, DUALDAGGER};
        }

        static {
            a = WeaponType.a();
            VALUES = WeaponType.values();
        }
    }
}
