/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminCreateItem.Commands
extends Enum<AdminCreateItem.Commands> {
    public static final /* enum */ AdminCreateItem.Commands admin_itemcreate = new AdminCreateItem.Commands();
    public static final /* enum */ AdminCreateItem.Commands admin_create_item = new AdminCreateItem.Commands();
    public static final /* enum */ AdminCreateItem.Commands admin_ci = new AdminCreateItem.Commands();
    public static final /* enum */ AdminCreateItem.Commands admin_spreaditem = new AdminCreateItem.Commands();
    public static final /* enum */ AdminCreateItem.Commands admin_create_item_element = new AdminCreateItem.Commands();
    private static final /* synthetic */ AdminCreateItem.Commands[] a;

    public static AdminCreateItem.Commands[] values() {
        return (AdminCreateItem.Commands[])a.clone();
    }

    public static AdminCreateItem.Commands valueOf(String string) {
        return Enum.valueOf(AdminCreateItem.Commands.class, string);
    }

    private static /* synthetic */ AdminCreateItem.Commands[] a() {
        return new AdminCreateItem.Commands[]{admin_itemcreate, admin_create_item, admin_ci, admin_spreaditem, admin_create_item_element};
    }

    static {
        a = AdminCreateItem.Commands.a();
    }
}
