/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.impl.AdminEffects;

static class AdminEffects.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands;

    static {
        $SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands = new int[AdminEffects.Commands.values().length];
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_invis.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_vis.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_gmspeed.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_invul.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_offline_vis.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_offline_invis.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_earthquake.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_redsky.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_para.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_block.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_unpara.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_unblock.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_bloc_party.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_para_party.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_unbloc_party.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_unpara_party.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_changename.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_setinvul.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_getinvul.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_social.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_abnormal.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_transform.ordinal()] = 22;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_showmovie.ordinal()] = 23;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_atmosphere.ordinal()] = 24;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminEffects.1.$SwitchMap$l2$gameserver$handler$admincommands$impl$AdminEffects$Commands[AdminEffects.Commands.admin_jump_target.ordinal()] = 25;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
