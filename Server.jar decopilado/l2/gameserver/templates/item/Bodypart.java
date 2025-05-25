/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class Bodypart
extends Enum<Bodypart> {
    public static final /* enum */ Bodypart NONE = new Bodypart(0L);
    public static final /* enum */ Bodypart CHEST = new Bodypart(1024L);
    public static final /* enum */ Bodypart BELT = new Bodypart(0x10000000L);
    public static final /* enum */ Bodypart RIGHT_BRACELET = new Bodypart(0x100000L);
    public static final /* enum */ Bodypart LEFT_BRACELET = new Bodypart(0x200000L);
    public static final /* enum */ Bodypart BROOCH = new Bodypart(0x20000000L);
    public static final /* enum */ Bodypart FULL_ARMOR = new Bodypart(32768L);
    public static final /* enum */ Bodypart HEAD = new Bodypart(64L);
    public static final /* enum */ Bodypart HAIR = new Bodypart(65536L);
    public static final /* enum */ Bodypart FACE = new Bodypart(262144L);
    public static final /* enum */ Bodypart HAIR_ALL = new Bodypart(524288L);
    public static final /* enum */ Bodypart UNDERWEAR = new Bodypart(1L);
    public static final /* enum */ Bodypart BACK = new Bodypart(8192L);
    public static final /* enum */ Bodypart NECKLACE = new Bodypart(8L);
    public static final /* enum */ Bodypart LEGS = new Bodypart(2048L);
    public static final /* enum */ Bodypart FEET = new Bodypart(4096L);
    public static final /* enum */ Bodypart GLOVES = new Bodypart(512L);
    public static final /* enum */ Bodypart RIGHT_HAND = new Bodypart(128L);
    public static final /* enum */ Bodypart LEFT_HAND = new Bodypart(256L);
    public static final /* enum */ Bodypart LEFT_RIGHT_HAND = new Bodypart(16384L);
    public static final /* enum */ Bodypart RIGHT_EAR = new Bodypart(2L);
    public static final /* enum */ Bodypart LEFT_EAR = new Bodypart(4L);
    public static final /* enum */ Bodypart RIGHT_FINGER = new Bodypart(16L);
    public static final /* enum */ Bodypart FORMAL_WEAR = new Bodypart(131072L);
    public static final /* enum */ Bodypart TALISMAN = new Bodypart(0x400000L);
    public static final /* enum */ Bodypart BROOCH_JEWEL = new Bodypart(0x40000000L);
    public static final /* enum */ Bodypart AGATHION_CHARM = new Bodypart(0x3000000000L);
    public static final /* enum */ Bodypart LEFT_FINGER = new Bodypart(32L);
    public static final /* enum */ Bodypart WOLF = new Bodypart(-100L, CHEST);
    public static final /* enum */ Bodypart GREAT_WOLF = new Bodypart(-104L, CHEST);
    public static final /* enum */ Bodypart HATCHLING = new Bodypart(-101L, CHEST);
    public static final /* enum */ Bodypart STRIDER = new Bodypart(-102L, CHEST);
    public static final /* enum */ Bodypart BABY_PET = new Bodypart(-103L, CHEST);
    public static final /* enum */ Bodypart PENDANT = new Bodypart(-105L, NECKLACE);
    private long dK;
    private Bodypart a;
    public static final Bodypart[] VALUES;
    private static final /* synthetic */ Bodypart[] a;

    public static Bodypart[] values() {
        return (Bodypart[])a.clone();
    }

    public static Bodypart valueOf(String string) {
        return Enum.valueOf(Bodypart.class, string);
    }

    private Bodypart(long l) {
        this(l, null);
    }

    private Bodypart(long l, Bodypart bodypart) {
        this.dK = l;
        this.a = bodypart;
    }

    public static Bodypart getBodypartByMask(long l) {
        for (Bodypart bodypart : VALUES) {
            if (bodypart.mask() != l) continue;
            return bodypart;
        }
        return null;
    }

    public long mask() {
        return this.dK;
    }

    public Bodypart getReal() {
        return this.a;
    }

    private static /* synthetic */ Bodypart[] a() {
        return new Bodypart[]{NONE, CHEST, BELT, RIGHT_BRACELET, LEFT_BRACELET, BROOCH, FULL_ARMOR, HEAD, HAIR, FACE, HAIR_ALL, UNDERWEAR, BACK, NECKLACE, LEGS, FEET, GLOVES, RIGHT_HAND, LEFT_HAND, LEFT_RIGHT_HAND, RIGHT_EAR, LEFT_EAR, RIGHT_FINGER, FORMAL_WEAR, TALISMAN, BROOCH_JEWEL, AGATHION_CHARM, LEFT_FINGER, WOLF, GREAT_WOLF, HATCHLING, STRIDER, BABY_PET, PENDANT};
    }

    static {
        a = Bodypart.a();
        VALUES = Bodypart.values();
    }
}
