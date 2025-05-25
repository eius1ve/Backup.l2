/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.handler.usercommands.IUserCommandHandler
 *  l2.gameserver.handler.usercommands.UserCommandHandler
 *  l2.gameserver.scripts.ScriptFile
 */
package handler.usercommands;

import l2.gameserver.handler.usercommands.IUserCommandHandler;
import l2.gameserver.handler.usercommands.UserCommandHandler;
import l2.gameserver.scripts.ScriptFile;

public abstract class ScriptUserCommand
implements IUserCommandHandler,
ScriptFile {
    public void onLoad() {
        UserCommandHandler.getInstance().registerUserCommandHandler((IUserCommandHandler)this);
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
