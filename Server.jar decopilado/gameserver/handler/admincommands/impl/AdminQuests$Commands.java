/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminQuests.Commands
extends Enum<AdminQuests.Commands> {
    public static final /* enum */ AdminQuests.Commands admin_quests = new AdminQuests.Commands();
    public static final /* enum */ AdminQuests.Commands admin_quest = new AdminQuests.Commands();
    private static final /* synthetic */ AdminQuests.Commands[] a;

    public static AdminQuests.Commands[] values() {
        return (AdminQuests.Commands[])a.clone();
    }

    public static AdminQuests.Commands valueOf(String string) {
        return Enum.valueOf(AdminQuests.Commands.class, string);
    }

    private static /* synthetic */ AdminQuests.Commands[] a() {
        return new AdminQuests.Commands[]{admin_quests, admin_quest};
    }

    static {
        a = AdminQuests.Commands.a();
    }
}
