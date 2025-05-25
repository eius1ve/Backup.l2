/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.handler.bypass.BypassHandler
 *  l2.gameserver.handler.bypass.IBypassHandler
 *  l2.gameserver.scripts.ScriptFile
 */
package handler.bypass;

import l2.gameserver.handler.bypass.BypassHandler;
import l2.gameserver.handler.bypass.IBypassHandler;
import l2.gameserver.scripts.ScriptFile;

public abstract class ScriptBypassHandler
implements IBypassHandler,
ScriptFile {
    public void onLoad() {
        BypassHandler.getInstance().registerBypass((IBypassHandler)this);
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
