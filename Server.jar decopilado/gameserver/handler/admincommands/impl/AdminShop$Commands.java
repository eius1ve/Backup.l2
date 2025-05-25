/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminShop.Commands
extends Enum<AdminShop.Commands> {
    public static final /* enum */ AdminShop.Commands admin_buy = new AdminShop.Commands();
    public static final /* enum */ AdminShop.Commands admin_gmshop = new AdminShop.Commands();
    public static final /* enum */ AdminShop.Commands admin_tax = new AdminShop.Commands();
    public static final /* enum */ AdminShop.Commands admin_taxclear = new AdminShop.Commands();
    private static final /* synthetic */ AdminShop.Commands[] a;

    public static AdminShop.Commands[] values() {
        return (AdminShop.Commands[])a.clone();
    }

    public static AdminShop.Commands valueOf(String string) {
        return Enum.valueOf(AdminShop.Commands.class, string);
    }

    private static /* synthetic */ AdminShop.Commands[] a() {
        return new AdminShop.Commands[]{admin_buy, admin_gmshop, admin_tax, admin_taxclear};
    }

    static {
        a = AdminShop.Commands.a();
    }
}
