/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminReload;

static class AdminReload.2 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands = new int[AdminReload.Commands.values().length];
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_config.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_multisell.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_gmaccess.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_htm.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_qr.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_qs.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_qs_help.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_skills.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_items.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_npc.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_spawn.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_fish.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_translit.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_shops.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_static.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_chatfilters.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_auto_announce.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_pets.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_locale.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_nobles.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_promo.ordinal()] = 22;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_prime_shop.ordinal()] = 23;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminReload.2.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminReload$Commands[AdminReload.Commands.admin_reload_instances.ordinal()] = 24;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
