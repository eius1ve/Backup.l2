/*
 * Decompiled with CFR 0.152.
 */
package handler.admincommands;

static final class AdminTeleportBookmark.Commands
extends Enum<AdminTeleportBookmark.Commands> {
    public static final /* enum */ AdminTeleportBookmark.Commands admin_bk = new AdminTeleportBookmark.Commands();
    public static final /* enum */ AdminTeleportBookmark.Commands admin_bkgo = new AdminTeleportBookmark.Commands();
    public static final /* enum */ AdminTeleportBookmark.Commands admin_bkdel = new AdminTeleportBookmark.Commands();
    public static final /* enum */ AdminTeleportBookmark.Commands admin_bkpage = new AdminTeleportBookmark.Commands();
    private static final /* synthetic */ AdminTeleportBookmark.Commands[] a;

    public static AdminTeleportBookmark.Commands[] values() {
        return (AdminTeleportBookmark.Commands[])a.clone();
    }

    public static AdminTeleportBookmark.Commands valueOf(String string) {
        return Enum.valueOf(AdminTeleportBookmark.Commands.class, string);
    }

    private static /* synthetic */ AdminTeleportBookmark.Commands[] a() {
        return new AdminTeleportBookmark.Commands[]{admin_bk, admin_bkgo, admin_bkdel, admin_bkpage};
    }

    static {
        a = AdminTeleportBookmark.Commands.a();
    }
}
