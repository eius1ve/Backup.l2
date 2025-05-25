/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminEnchant.Commands
extends Enum<AdminEnchant.Commands> {
    public static final /* enum */ AdminEnchant.Commands admin_seteh = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_setec = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_seteg = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_setel = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_seteb = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_setew = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_setes = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_setle = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_setre = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_setlf = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_setrf = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_seten = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_setun = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_setba = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_setha = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_setdha = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_setlbr = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_setrbr = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_setbelt = new AdminEnchant.Commands();
    public static final /* enum */ AdminEnchant.Commands admin_enchant = new AdminEnchant.Commands();
    private static final /* synthetic */ AdminEnchant.Commands[] a;

    public static AdminEnchant.Commands[] values() {
        return (AdminEnchant.Commands[])a.clone();
    }

    public static AdminEnchant.Commands valueOf(String string) {
        return Enum.valueOf(AdminEnchant.Commands.class, string);
    }

    private static /* synthetic */ AdminEnchant.Commands[] a() {
        return new AdminEnchant.Commands[]{admin_seteh, admin_setec, admin_seteg, admin_setel, admin_seteb, admin_setew, admin_setes, admin_setle, admin_setre, admin_setlf, admin_setrf, admin_seten, admin_setun, admin_setba, admin_setha, admin_setdha, admin_setlbr, admin_setrbr, admin_setbelt, admin_enchant};
    }

    static {
        a = AdminEnchant.Commands.a();
    }
}
