/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminServer;
import l2.gameserver.model.base.Race;

static class AdminServer.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$base$Race;
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminServer$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminServer$Commands = new int[AdminServer.Commands.values().length];
        try {
            AdminServer.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminServer$Commands[AdminServer.Commands.admin_server.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminServer.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminServer$Commands[AdminServer.Commands.admin_server_info.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminServer.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminServer$Commands[AdminServer.Commands.admin_check_actor.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminServer.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminServer$Commands[AdminServer.Commands.admin_setvar.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminServer.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminServer$Commands[AdminServer.Commands.admin_set_ai_interval.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminServer.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminServer$Commands[AdminServer.Commands.admin_spawn2.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$l2$gameserver$model$base$Race = new int[Race.values().length];
        try {
            AdminServer.1.$SwitchMap$l2$gameserver$model$base$Race[Race.human.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminServer.1.$SwitchMap$l2$gameserver$model$base$Race[Race.darkelf.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminServer.1.$SwitchMap$l2$gameserver$model$base$Race[Race.dwarf.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminServer.1.$SwitchMap$l2$gameserver$model$base$Race[Race.elf.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminServer.1.$SwitchMap$l2$gameserver$model$base$Race[Race.orc.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
