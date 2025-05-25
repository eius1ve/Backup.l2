/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.mask.IUpdateTypeComponent;

public final class UserInfoType
extends Enum<UserInfoType>
implements IUpdateTypeComponent {
    public static final /* enum */ UserInfoType RELATION = new UserInfoType(0, 4);
    public static final /* enum */ UserInfoType BASIC_INFO = new UserInfoType(1, 16);
    public static final /* enum */ UserInfoType BASE_STATS = new UserInfoType(2, 18);
    public static final /* enum */ UserInfoType MAX_HPCPMP = new UserInfoType(3, 14);
    public static final /* enum */ UserInfoType CURRENT_HPMPCP_EXP_SP = new UserInfoType(4, 38);
    public static final /* enum */ UserInfoType ENCHANTLEVEL = new UserInfoType(5, 4);
    public static final /* enum */ UserInfoType APPAREANCE = new UserInfoType(6, 15);
    public static final /* enum */ UserInfoType STATUS = new UserInfoType(7, 6);
    public static final /* enum */ UserInfoType STATS = new UserInfoType(8, 56);
    public static final /* enum */ UserInfoType ELEMENTALS = new UserInfoType(9, 14);
    public static final /* enum */ UserInfoType POSITION = new UserInfoType(10, 18);
    public static final /* enum */ UserInfoType SPEED = new UserInfoType(11, 18);
    public static final /* enum */ UserInfoType MULTIPLIER = new UserInfoType(12, 18);
    public static final /* enum */ UserInfoType COL_RADIUS_HEIGHT = new UserInfoType(13, 18);
    public static final /* enum */ UserInfoType ATK_ELEMENTAL = new UserInfoType(14, 5);
    public static final /* enum */ UserInfoType CLAN = new UserInfoType(15, 32);
    public static final /* enum */ UserInfoType SOCIAL = new UserInfoType(16, 22);
    public static final /* enum */ UserInfoType VITA_FAME = new UserInfoType(17, 15);
    public static final /* enum */ UserInfoType SLOTS = new UserInfoType(18, 9);
    public static final /* enum */ UserInfoType MOVEMENTS = new UserInfoType(19, 4);
    public static final /* enum */ UserInfoType COLOR = new UserInfoType(20, 10);
    public static final /* enum */ UserInfoType INVENTORY_LIMIT = new UserInfoType(21, 9);
    public static final /* enum */ UserInfoType TRUE_HERO = new UserInfoType(22, 9);
    public static final /* enum */ UserInfoType ATT_SPIRITS = new UserInfoType(23, 26);
    public static final UserInfoType[] VALUES;
    private final int CH;
    private final int CI;
    private static final /* synthetic */ UserInfoType[] a;

    public static UserInfoType[] values() {
        return (UserInfoType[])a.clone();
    }

    public static UserInfoType valueOf(String string) {
        return Enum.valueOf(UserInfoType.class, string);
    }

    private UserInfoType(int n2, int n3) {
        this.CH = n2;
        this.CI = n3;
    }

    @Override
    public int getMask() {
        return this.CH;
    }

    public int getBlockLength() {
        return this.CI;
    }

    private static /* synthetic */ UserInfoType[] a() {
        return new UserInfoType[]{RELATION, BASIC_INFO, BASE_STATS, MAX_HPCPMP, CURRENT_HPMPCP_EXP_SP, ENCHANTLEVEL, APPAREANCE, STATUS, STATS, ELEMENTALS, POSITION, SPEED, MULTIPLIER, COL_RADIUS_HEIGHT, ATK_ELEMENTAL, CLAN, SOCIAL, VITA_FAME, SLOTS, MOVEMENTS, COLOR, INVENTORY_LIMIT, TRUE_HERO, ATT_SPIRITS};
    }

    static {
        a = UserInfoType.a();
        VALUES = UserInfoType.values();
    }
}
