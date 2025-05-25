/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminTeleport;

static class AdminTeleport.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands = new int[AdminTeleport.Commands.values().length];
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_show_moves.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_show_moves_other.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_show_teleport.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_teleport_to_character.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_teleport_to.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_teleportto.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_move_to.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_moveto.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_teleport.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_walk.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_gonorth.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_gosouth.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_goeast.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_gowest.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_goup.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_godown.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_tele.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_teleto.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_tele_to.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_instant_move.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_tonpc.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_to_npc.ordinal()] = 22;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_toobject.ordinal()] = 23;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_teleport_character.ordinal()] = 24;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_recall.ordinal()] = 25;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_sendhome.ordinal()] = 26;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_setref.ordinal()] = 27;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_getref.ordinal()] = 28;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_recall_party.ordinal()] = 29;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminTeleport.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminTeleport$Commands[AdminTeleport.Commands.admin_recall_npc.ordinal()] = 30;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
