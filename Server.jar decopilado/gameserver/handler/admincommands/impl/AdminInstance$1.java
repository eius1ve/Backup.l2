/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminInstance;

static class AdminInstance.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminInstance$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminInstance$Commands = new int[AdminInstance.Commands.values().length];
        try {
            AdminInstance.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminInstance$Commands[AdminInstance.Commands.admin_instance.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminInstance.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminInstance$Commands[AdminInstance.Commands.admin_instance_id.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminInstance.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminInstance$Commands[AdminInstance.Commands.admin_collapse.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminInstance.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminInstance$Commands[AdminInstance.Commands.admin_reset_reuse.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminInstance.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminInstance$Commands[AdminInstance.Commands.admin_reset_reuse_all.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminInstance.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminInstance$Commands[AdminInstance.Commands.admin_set_reuse.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
