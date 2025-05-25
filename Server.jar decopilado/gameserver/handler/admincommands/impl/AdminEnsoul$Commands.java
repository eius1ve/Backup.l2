/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminEnsoul.Commands
extends Enum<AdminEnsoul.Commands> {
    public static final /* enum */ AdminEnsoul.Commands admin_ensoul = new AdminEnsoul.Commands();
    public static final /* enum */ AdminEnsoul.Commands admin_ensoul_extract = new AdminEnsoul.Commands();
    private static final /* synthetic */ AdminEnsoul.Commands[] a;

    public static AdminEnsoul.Commands[] values() {
        return (AdminEnsoul.Commands[])a.clone();
    }

    public static AdminEnsoul.Commands valueOf(String string) {
        return Enum.valueOf(AdminEnsoul.Commands.class, string);
    }

    private static /* synthetic */ AdminEnsoul.Commands[] a() {
        return new AdminEnsoul.Commands[]{admin_ensoul, admin_ensoul_extract};
    }

    static {
        a = AdminEnsoul.Commands.a();
    }
}
