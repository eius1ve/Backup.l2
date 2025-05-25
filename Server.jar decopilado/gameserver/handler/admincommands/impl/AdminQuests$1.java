/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminQuests;

static class AdminQuests.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminQuests$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminQuests$Commands = new int[AdminQuests.Commands.values().length];
        try {
            AdminQuests.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminQuests$Commands[AdminQuests.Commands.admin_quests.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminQuests.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminQuests$Commands[AdminQuests.Commands.admin_quest.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
