/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminMonsterRace.Commands
extends Enum<AdminMonsterRace.Commands> {
    public static final /* enum */ AdminMonsterRace.Commands admin_mons = new AdminMonsterRace.Commands();
    private static final /* synthetic */ AdminMonsterRace.Commands[] a;

    public static AdminMonsterRace.Commands[] values() {
        return (AdminMonsterRace.Commands[])a.clone();
    }

    public static AdminMonsterRace.Commands valueOf(String string) {
        return Enum.valueOf(AdminMonsterRace.Commands.class, string);
    }

    private static /* synthetic */ AdminMonsterRace.Commands[] a() {
        return new AdminMonsterRace.Commands[]{admin_mons};
    }

    static {
        a = AdminMonsterRace.Commands.a();
    }
}
