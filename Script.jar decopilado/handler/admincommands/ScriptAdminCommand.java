/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.handler.admincommands.AdminCommandHandler
 *  l2.gameserver.handler.admincommands.IAdminCommandHandler
 *  l2.gameserver.scripts.ScriptFile
 */
package handler.admincommands;

import l2.gameserver.handler.admincommands.AdminCommandHandler;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.scripts.ScriptFile;

public abstract class ScriptAdminCommand
implements IAdminCommandHandler,
ScriptFile {
    public void onLoad() {
        AdminCommandHandler.getInstance().registerAdminCommandHandler((IAdminCommandHandler)this);
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
