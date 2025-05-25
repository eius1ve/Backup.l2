/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminPetition.Commands
extends Enum<AdminPetition.Commands> {
    public static final /* enum */ AdminPetition.Commands admin_view_petitions = new AdminPetition.Commands();
    public static final /* enum */ AdminPetition.Commands admin_view_petition = new AdminPetition.Commands();
    public static final /* enum */ AdminPetition.Commands admin_accept_petition = new AdminPetition.Commands();
    public static final /* enum */ AdminPetition.Commands admin_reject_petition = new AdminPetition.Commands();
    public static final /* enum */ AdminPetition.Commands admin_reset_petitions = new AdminPetition.Commands();
    public static final /* enum */ AdminPetition.Commands admin_force_peti = new AdminPetition.Commands();
    private static final /* synthetic */ AdminPetition.Commands[] a;

    public static AdminPetition.Commands[] values() {
        return (AdminPetition.Commands[])a.clone();
    }

    public static AdminPetition.Commands valueOf(String string) {
        return Enum.valueOf(AdminPetition.Commands.class, string);
    }

    private static /* synthetic */ AdminPetition.Commands[] a() {
        return new AdminPetition.Commands[]{admin_view_petitions, admin_view_petition, admin_accept_petition, admin_reject_petition, admin_reset_petitions, admin_force_peti};
    }

    static {
        a = AdminPetition.Commands.a();
    }
}
