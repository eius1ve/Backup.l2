/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminDisconnect;

static class AdminDisconnect.2 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminDisconnect$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminDisconnect$Commands = new int[AdminDisconnect.Commands.values().length];
        try {
            AdminDisconnect.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminDisconnect$Commands[AdminDisconnect.Commands.admin_disconnect.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminDisconnect.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminDisconnect$Commands[AdminDisconnect.Commands.admin_kick.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
