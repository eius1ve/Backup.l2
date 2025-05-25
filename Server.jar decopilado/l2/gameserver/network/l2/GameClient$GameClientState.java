/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2;

public static final class GameClient.GameClientState
extends Enum<GameClient.GameClientState> {
    public static final /* enum */ GameClient.GameClientState CONNECTED = new GameClient.GameClientState();
    public static final /* enum */ GameClient.GameClientState AUTHED = new GameClient.GameClientState();
    public static final /* enum */ GameClient.GameClientState IN_GAME = new GameClient.GameClientState();
    public static final /* enum */ GameClient.GameClientState DISCONNECTED = new GameClient.GameClientState();
    private static final /* synthetic */ GameClient.GameClientState[] a;

    public static GameClient.GameClientState[] values() {
        return (GameClient.GameClientState[])a.clone();
    }

    public static GameClient.GameClientState valueOf(String string) {
        return Enum.valueOf(GameClient.GameClientState.class, string);
    }

    private static /* synthetic */ GameClient.GameClientState[] a() {
        return new GameClient.GameClientState[]{CONNECTED, AUTHED, IN_GAME, DISCONNECTED};
    }

    static {
        a = GameClient.GameClientState.a();
    }
}
