/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminGmChat;

static class AdminGmChat.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminGmChat$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminGmChat$Commands = new int[AdminGmChat.Commands.values().length];
        try {
            AdminGmChat.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminGmChat$Commands[AdminGmChat.Commands.admin_gmchat.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminGmChat.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminGmChat$Commands[AdminGmChat.Commands.admin_snoop.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
