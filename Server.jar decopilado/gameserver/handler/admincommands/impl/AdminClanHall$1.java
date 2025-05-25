/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminClanHall;

static class AdminClanHall.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminClanHall$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminClanHall$Commands = new int[AdminClanHall.Commands.values().length];
        try {
            AdminClanHall.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminClanHall$Commands[AdminClanHall.Commands.admin_clanhall.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminClanHall.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminClanHall$Commands[AdminClanHall.Commands.admin_clanhallset.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminClanHall.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminClanHall$Commands[AdminClanHall.Commands.admin_clanhalldel.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminClanHall.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminClanHall$Commands[AdminClanHall.Commands.admin_clanhallteleportself.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
