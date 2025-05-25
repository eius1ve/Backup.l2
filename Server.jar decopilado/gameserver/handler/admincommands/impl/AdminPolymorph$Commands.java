/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminPolymorph.Commands
extends Enum<AdminPolymorph.Commands> {
    public static final /* enum */ AdminPolymorph.Commands admin_polyself = new AdminPolymorph.Commands();
    public static final /* enum */ AdminPolymorph.Commands admin_polymorph = new AdminPolymorph.Commands();
    public static final /* enum */ AdminPolymorph.Commands admin_poly = new AdminPolymorph.Commands();
    public static final /* enum */ AdminPolymorph.Commands admin_unpolyself = new AdminPolymorph.Commands();
    public static final /* enum */ AdminPolymorph.Commands admin_unpolymorph = new AdminPolymorph.Commands();
    public static final /* enum */ AdminPolymorph.Commands admin_unpoly = new AdminPolymorph.Commands();
    private static final /* synthetic */ AdminPolymorph.Commands[] a;

    public static AdminPolymorph.Commands[] values() {
        return (AdminPolymorph.Commands[])a.clone();
    }

    public static AdminPolymorph.Commands valueOf(String string) {
        return Enum.valueOf(AdminPolymorph.Commands.class, string);
    }

    private static /* synthetic */ AdminPolymorph.Commands[] a() {
        return new AdminPolymorph.Commands[]{admin_polyself, admin_polymorph, admin_poly, admin_unpolyself, admin_unpolymorph, admin_unpoly};
    }

    static {
        a = AdminPolymorph.Commands.a();
    }
}
