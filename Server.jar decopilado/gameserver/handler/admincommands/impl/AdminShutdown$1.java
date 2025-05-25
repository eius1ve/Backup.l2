/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminShutdown;

static class AdminShutdown.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminShutdown$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminShutdown$Commands = new int[AdminShutdown.Commands.values().length];
        try {
            AdminShutdown.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminShutdown$Commands[AdminShutdown.Commands.admin_server_shutdown.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminShutdown.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminShutdown$Commands[AdminShutdown.Commands.admin_server_restart.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminShutdown.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminShutdown$Commands[AdminShutdown.Commands.admin_server_abort.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
