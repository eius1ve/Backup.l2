/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2;

import l2.gameserver.network.l2.CGModule;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.GameCrypt;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public static class CGModule.CGDefaultModule
extends CGModule<CGModule.CGContext, byte[]> {
    @Override
    public String getName() {
        return "None";
    }

    @Override
    public CGModule init(String string) {
        this.info("Initializing default CGModule.");
        return this;
    }

    @Override
    public void done() {
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public CGModule.CGContext initClient(GameClient gameClient) {
        return new CGModule.CGContext(gameClient);
    }

    @Override
    public void doneClient(CGModule.CGContext cGContext) {
        cGContext.setGameClient(null);
    }

    @Override
    public GameCrypt<CGModule.CGContext, byte[]> initCrypt(CGModule.CGContext cGContext) {
        return new GameCrypt<CGModule.CGContext, byte[]>(){};
    }

    @Override
    public L2GameClientPacket handlePacket(CGModule.CGContext cGContext, int n, Integer n2) {
        return null;
    }

    @Override
    public String getHWIDText(CGModule.CGContext cGContext) {
        return null;
    }

    @Override
    public void addHWIDBan(CGModule.CGContext cGContext, String string, String string2, String string3, String string4) {
    }
}
