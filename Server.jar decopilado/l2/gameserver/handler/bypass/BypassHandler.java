/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.bypass;

import l2.gameserver.handler.bypass.IBypassHandler;

public class BypassHandler {
    private static final BypassHandler a = new BypassHandler();

    public static BypassHandler getInstance() {
        return a;
    }

    public void registerBypass(IBypassHandler iBypassHandler) {
    }
}
