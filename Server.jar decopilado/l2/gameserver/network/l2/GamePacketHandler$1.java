/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2;

import l2.gameserver.network.l2.GameClient;

static class GamePacketHandler.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$network$l2$GameClient$GameClientState;

    static {
        $SwitchMap$l2$gameserver$network$l2$GameClient$GameClientState = new int[GameClient.GameClientState.values().length];
        try {
            GamePacketHandler.1.$SwitchMap$l2$gameserver$network$l2$GameClient$GameClientState[GameClient.GameClientState.CONNECTED.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            GamePacketHandler.1.$SwitchMap$l2$gameserver$network$l2$GameClient$GameClientState[GameClient.GameClientState.AUTHED.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            GamePacketHandler.1.$SwitchMap$l2$gameserver$network$l2$GameClient$GameClientState[GameClient.GameClientState.IN_GAME.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
