/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminPetition;

static class AdminPetition.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminPetition$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminPetition$Commands = new int[AdminPetition.Commands.values().length];
        try {
            AdminPetition.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminPetition$Commands[AdminPetition.Commands.admin_view_petitions.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPetition.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminPetition$Commands[AdminPetition.Commands.admin_view_petition.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPetition.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminPetition$Commands[AdminPetition.Commands.admin_accept_petition.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPetition.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminPetition$Commands[AdminPetition.Commands.admin_reject_petition.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPetition.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminPetition$Commands[AdminPetition.Commands.admin_reset_petitions.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminPetition.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminPetition$Commands[AdminPetition.Commands.admin_force_peti.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
