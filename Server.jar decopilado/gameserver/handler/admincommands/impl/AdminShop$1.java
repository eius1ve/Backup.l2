/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminShop;

static class AdminShop.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminShop$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminShop$Commands = new int[AdminShop.Commands.values().length];
        try {
            AdminShop.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminShop$Commands[AdminShop.Commands.admin_buy.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminShop.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminShop$Commands[AdminShop.Commands.admin_gmshop.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminShop.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminShop$Commands[AdminShop.Commands.admin_tax.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminShop.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminShop$Commands[AdminShop.Commands.admin_taxclear.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
