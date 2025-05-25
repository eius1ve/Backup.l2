/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminEffects.Commands
extends Enum<AdminEffects.Commands> {
    public static final /* enum */ AdminEffects.Commands admin_invis = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_vis = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_offline_vis = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_offline_invis = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_earthquake = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_redsky = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_jump_target = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_block = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_para = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_bloc_party = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_para_party = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_unbloc_party = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_unpara_party = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_unblock = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_unpara = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_changename = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_gmspeed = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_invul = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_setinvul = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_getinvul = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_social = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_abnormal = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_transform = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_showmovie = new AdminEffects.Commands();
    public static final /* enum */ AdminEffects.Commands admin_atmosphere = new AdminEffects.Commands();
    private static final /* synthetic */ AdminEffects.Commands[] a;

    public static AdminEffects.Commands[] values() {
        return (AdminEffects.Commands[])a.clone();
    }

    public static AdminEffects.Commands valueOf(String string) {
        return Enum.valueOf(AdminEffects.Commands.class, string);
    }

    private static /* synthetic */ AdminEffects.Commands[] a() {
        return new AdminEffects.Commands[]{admin_invis, admin_vis, admin_offline_vis, admin_offline_invis, admin_earthquake, admin_redsky, admin_jump_target, admin_block, admin_para, admin_bloc_party, admin_para_party, admin_unbloc_party, admin_unpara_party, admin_unblock, admin_unpara, admin_changename, admin_gmspeed, admin_invul, admin_setinvul, admin_getinvul, admin_social, admin_abnormal, admin_transform, admin_showmovie, admin_atmosphere};
    }

    static {
        a = AdminEffects.Commands.a();
    }
}
