/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.components.ChatType;

static class Say2.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$network$l2$components$ChatType;

    static {
        $SwitchMap$l2$gameserver$network$l2$components$ChatType = new int[ChatType.values().length];
        try {
            Say2.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.SYSTEM_MESSAGE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.TELL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.CLAN.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Say2.1.$SwitchMap$l2$gameserver$network$l2$components$ChatType[ChatType.ALLIANCE.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
