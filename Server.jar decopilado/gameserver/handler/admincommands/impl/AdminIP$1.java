/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminIP;

static class AdminIP.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminIP$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminIP$Commands = new int[AdminIP.Commands.values().length];
        try {
            AdminIP.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminIP$Commands[AdminIP.Commands.admin_charip.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminIP.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminIP$Commands[AdminIP.Commands.admin_charhwid.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
