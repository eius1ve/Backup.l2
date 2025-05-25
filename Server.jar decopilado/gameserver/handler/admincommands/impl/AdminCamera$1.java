/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminCamera;

static class AdminCamera.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminCamera$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminCamera$Commands = new int[AdminCamera.Commands.values().length];
        try {
            AdminCamera.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminCamera$Commands[AdminCamera.Commands.admin_freelook.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminCamera.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminCamera$Commands[AdminCamera.Commands.admin_cinematic.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
