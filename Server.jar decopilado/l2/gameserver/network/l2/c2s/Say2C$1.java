/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.network.l2.components.ChatType;

static class Say2C.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$network$l2$components$ChatType;

    static {
        $SwitchMap$l2$gameserver$network$l2$components$ChatType = new int[ChatType.values().length];
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.TELL.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.SHOUT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.TRADE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.ALL.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.CLAN.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.ALLIANCE.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.PARTY.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.PARTY_ROOM.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.COMMANDCHANNEL_ALL.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.COMMANDCHANNEL_COMMANDER.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.HERO_VOICE.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.PETITION_PLAYER.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.PETITION_GM.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.BATTLEFIELD.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.MPCC_ROOM.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2C.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.WORLD.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
