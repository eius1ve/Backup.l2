/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminLevel;

static class AdminLevel.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminLevel$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminLevel$Commands = new int[AdminLevel.Commands.values().length];
        try {
            AdminLevel.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminLevel$Commands[AdminLevel.Commands.admin_add_level.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminLevel.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminLevel$Commands[AdminLevel.Commands.admin_addLevel.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminLevel.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminLevel$Commands[AdminLevel.Commands.admin_set_level.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminLevel.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminLevel$Commands[AdminLevel.Commands.admin_setLevel.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
