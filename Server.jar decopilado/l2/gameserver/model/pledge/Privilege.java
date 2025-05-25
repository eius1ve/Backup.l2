/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.pledge;

public final class Privilege
extends Enum<Privilege> {
    public static final /* enum */ Privilege FREE = new Privilege();
    public static final /* enum */ Privilege CL_JOIN_CLAN = new Privilege();
    public static final /* enum */ Privilege CL_GIVE_TITLE = new Privilege();
    public static final /* enum */ Privilege CL_VIEW_WAREHOUSE = new Privilege();
    public static final /* enum */ Privilege CL_MANAGE_RANKS = new Privilege();
    public static final /* enum */ Privilege CL_PLEDGE_WAR = new Privilege();
    public static final /* enum */ Privilege CL_DISMISS = new Privilege();
    public static final /* enum */ Privilege CL_REGISTER_CREST = new Privilege();
    public static final /* enum */ Privilege CL_APPRENTICE = new Privilege();
    public static final /* enum */ Privilege CL_TROOPS_FAME = new Privilege();
    public static final /* enum */ Privilege CH_ENTER_EXIT = new Privilege();
    public static final /* enum */ Privilege CH_USE_FUNCTIONS = new Privilege();
    public static final /* enum */ Privilege CH_AUCTION = new Privilege();
    public static final /* enum */ Privilege CH_DISMISS = new Privilege();
    public static final /* enum */ Privilege CH_SET_FUNCTIONS = new Privilege();
    public static final /* enum */ Privilege CS_FS_ENTER_EXIT = new Privilege();
    public static final /* enum */ Privilege CS_FS_MANOR_ADMIN = new Privilege();
    public static final /* enum */ Privilege CS_FS_SIEGE_WAR = new Privilege();
    public static final /* enum */ Privilege CS_FS_USE_FUNCTIONS = new Privilege();
    public static final /* enum */ Privilege CS_FS_DISMISS = new Privilege();
    public static final /* enum */ Privilege CS_FS_MANAGER_TAXES = new Privilege();
    public static final /* enum */ Privilege CS_FS_MERCENARIES = new Privilege();
    public static final /* enum */ Privilege CS_FS_SET_FUNCTIONS = new Privilege();
    public static final int ALL = 0x7FFFFE;
    public static final int NONE = 0;
    private final int pa = this.ordinal() == 0 ? 0 : 1 << this.ordinal();
    private static final /* synthetic */ Privilege[] a;

    public static Privilege[] values() {
        return (Privilege[])a.clone();
    }

    public static Privilege valueOf(String string) {
        return Enum.valueOf(Privilege.class, string);
    }

    public int mask() {
        return this.pa;
    }

    private static /* synthetic */ Privilege[] a() {
        return new Privilege[]{FREE, CL_JOIN_CLAN, CL_GIVE_TITLE, CL_VIEW_WAREHOUSE, CL_MANAGE_RANKS, CL_PLEDGE_WAR, CL_DISMISS, CL_REGISTER_CREST, CL_APPRENTICE, CL_TROOPS_FAME, CH_ENTER_EXIT, CH_USE_FUNCTIONS, CH_AUCTION, CH_DISMISS, CH_SET_FUNCTIONS, CS_FS_ENTER_EXIT, CS_FS_MANOR_ADMIN, CS_FS_SIEGE_WAR, CS_FS_USE_FUNCTIONS, CS_FS_DISMISS, CS_FS_MANAGER_TAXES, CS_FS_MERCENARIES, CS_FS_SET_FUNCTIONS};
    }

    static {
        a = Privilege.a();
    }
}
