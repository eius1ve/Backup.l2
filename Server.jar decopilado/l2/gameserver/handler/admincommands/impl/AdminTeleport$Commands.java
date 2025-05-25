/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

private static final class AdminTeleport.Commands
extends Enum<AdminTeleport.Commands> {
    public static final /* enum */ AdminTeleport.Commands admin_show_moves = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_show_moves_other = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_show_teleport = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_teleport_to_character = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_teleportto = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_teleport_to = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_move_to = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_moveto = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_teleport = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_teleport_character = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_recall = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_walk = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_recall_npc = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_gonorth = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_gosouth = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_goeast = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_gowest = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_goup = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_godown = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_tele = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_teleto = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_tele_to = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_instant_move = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_tonpc = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_to_npc = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_toobject = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_setref = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_getref = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_recall_party = new AdminTeleport.Commands();
    public static final /* enum */ AdminTeleport.Commands admin_sendhome = new AdminTeleport.Commands();
    private static final /* synthetic */ AdminTeleport.Commands[] a;

    public static AdminTeleport.Commands[] values() {
        return (AdminTeleport.Commands[])a.clone();
    }

    public static AdminTeleport.Commands valueOf(String string) {
        return Enum.valueOf(AdminTeleport.Commands.class, string);
    }

    private static /* synthetic */ AdminTeleport.Commands[] a() {
        return new AdminTeleport.Commands[]{admin_show_moves, admin_show_moves_other, admin_show_teleport, admin_teleport_to_character, admin_teleportto, admin_teleport_to, admin_move_to, admin_moveto, admin_teleport, admin_teleport_character, admin_recall, admin_walk, admin_recall_npc, admin_gonorth, admin_gosouth, admin_goeast, admin_gowest, admin_goup, admin_godown, admin_tele, admin_teleto, admin_tele_to, admin_instant_move, admin_tonpc, admin_to_npc, admin_toobject, admin_setref, admin_getref, admin_recall_party, admin_sendhome};
    }

    static {
        a = AdminTeleport.Commands.a();
    }
}
