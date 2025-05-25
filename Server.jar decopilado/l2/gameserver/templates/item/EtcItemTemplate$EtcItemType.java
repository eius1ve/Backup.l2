/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item;

import l2.gameserver.templates.item.ArmorTemplate;
import l2.gameserver.templates.item.ItemType;
import l2.gameserver.templates.item.WeaponTemplate;

public static final class EtcItemTemplate.EtcItemType
extends Enum<EtcItemTemplate.EtcItemType>
implements ItemType {
    public static final /* enum */ EtcItemTemplate.EtcItemType ARROW = new EtcItemTemplate.EtcItemType(1, "Arrow");
    public static final /* enum */ EtcItemTemplate.EtcItemType MATERIAL = new EtcItemTemplate.EtcItemType(2, "Material");
    public static final /* enum */ EtcItemTemplate.EtcItemType PET_COLLAR = new EtcItemTemplate.EtcItemType(3, "PetCollar");
    public static final /* enum */ EtcItemTemplate.EtcItemType POTION = new EtcItemTemplate.EtcItemType(4, "Potion");
    public static final /* enum */ EtcItemTemplate.EtcItemType RECIPE = new EtcItemTemplate.EtcItemType(5, "Recipe");
    public static final /* enum */ EtcItemTemplate.EtcItemType SCROLL = new EtcItemTemplate.EtcItemType(6, "Scroll");
    public static final /* enum */ EtcItemTemplate.EtcItemType QUEST = new EtcItemTemplate.EtcItemType(7, "Quest");
    public static final /* enum */ EtcItemTemplate.EtcItemType MONEY = new EtcItemTemplate.EtcItemType(8, "Money");
    public static final /* enum */ EtcItemTemplate.EtcItemType OTHER = new EtcItemTemplate.EtcItemType(9, "Other");
    public static final /* enum */ EtcItemTemplate.EtcItemType SPELLBOOK = new EtcItemTemplate.EtcItemType(10, "Spellbook");
    public static final /* enum */ EtcItemTemplate.EtcItemType SEED = new EtcItemTemplate.EtcItemType(11, "Seed");
    public static final /* enum */ EtcItemTemplate.EtcItemType BAIT = new EtcItemTemplate.EtcItemType(12, "Bait");
    public static final /* enum */ EtcItemTemplate.EtcItemType SHOT = new EtcItemTemplate.EtcItemType(13, "Shot");
    public static final /* enum */ EtcItemTemplate.EtcItemType BOLT = new EtcItemTemplate.EtcItemType(14, "Bolt");
    public static final /* enum */ EtcItemTemplate.EtcItemType RUNE = new EtcItemTemplate.EtcItemType(15, "Rune");
    public static final /* enum */ EtcItemTemplate.EtcItemType HERB = new EtcItemTemplate.EtcItemType(16, "Herb");
    public static final /* enum */ EtcItemTemplate.EtcItemType MERCENARY_TICKET = new EtcItemTemplate.EtcItemType(17, "Mercenary Ticket");
    public static final /* enum */ EtcItemTemplate.EtcItemType ARROW_QUIVER = new EtcItemTemplate.EtcItemType(18, "ArrowQuiver");
    public static final /* enum */ EtcItemTemplate.EtcItemType SPIRITSHOT = new EtcItemTemplate.EtcItemType(19, "SpiritShot");
    public static final /* enum */ EtcItemTemplate.EtcItemType BLESSED_SPIRITSHOT = new EtcItemTemplate.EtcItemType(20, "BlessedSpiritShot");
    public static final /* enum */ EtcItemTemplate.EtcItemType RUNE_QUEST = new EtcItemTemplate.EtcItemType(21, "RuneQuest");
    private final long dU;
    private final String gt;
    private static final /* synthetic */ EtcItemTemplate.EtcItemType[] a;

    public static EtcItemTemplate.EtcItemType[] values() {
        return (EtcItemTemplate.EtcItemType[])a.clone();
    }

    public static EtcItemTemplate.EtcItemType valueOf(String string) {
        return Enum.valueOf(EtcItemTemplate.EtcItemType.class, string);
    }

    private EtcItemTemplate.EtcItemType(int n2, String string2) {
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

    private static /* synthetic */ EtcItemTemplate.EtcItemType[] a() {
        return new EtcItemTemplate.EtcItemType[]{ARROW, MATERIAL, PET_COLLAR, POTION, RECIPE, SCROLL, QUEST, MONEY, OTHER, SPELLBOOK, SEED, BAIT, SHOT, BOLT, RUNE, HERB, MERCENARY_TICKET, ARROW_QUIVER, SPIRITSHOT, BLESSED_SPIRITSHOT, RUNE_QUEST};
    }

    static {
        a = EtcItemTemplate.EtcItemType.a();
    }
}
