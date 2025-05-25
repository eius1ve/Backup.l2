/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminDoorControl;

static class AdminDoorControl.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminDoorControl$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminDoorControl$Commands = new int[AdminDoorControl.Commands.values().length];
        try {
            AdminDoorControl.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminDoorControl$Commands[AdminDoorControl.Commands.admin_open.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminDoorControl.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminDoorControl$Commands[AdminDoorControl.Commands.admin_close.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
