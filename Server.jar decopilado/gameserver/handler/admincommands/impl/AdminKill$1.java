/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminKill;

static class AdminKill.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminKill$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminKill$Commands = new int[AdminKill.Commands.values().length];
        try {
            AdminKill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminKill$Commands[AdminKill.Commands.admin_kill.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminKill.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminKill$Commands[AdminKill.Commands.admin_damage.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
