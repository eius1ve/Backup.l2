/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 */
package l2.gameserver.handler.usercommands;

import gnu.trove.TIntObjectHashMap;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.handler.usercommands.IUserCommandHandler;
import l2.gameserver.handler.usercommands.impl.ClanPenalty;
import l2.gameserver.handler.usercommands.impl.ClanWarsList;
import l2.gameserver.handler.usercommands.impl.CommandChannel;
import l2.gameserver.handler.usercommands.impl.Escape;
import l2.gameserver.handler.usercommands.impl.InstanceZone;
import l2.gameserver.handler.usercommands.impl.LocCommand;
import l2.gameserver.handler.usercommands.impl.OlympiadStat;
import l2.gameserver.handler.usercommands.impl.PartyInfo;
import l2.gameserver.handler.usercommands.impl.Time;

public class UserCommandHandler
extends AbstractHolder {
    private static final UserCommandHandler a = new UserCommandHandler();
    private TIntObjectHashMap<IUserCommandHandler> s = new TIntObjectHashMap();

    public static UserCommandHandler getInstance() {
        return a;
    }

    private UserCommandHandler() {
        this.registerUserCommandHandler(new ClanWarsList());
        this.registerUserCommandHandler(new ClanPenalty());
        this.registerUserCommandHandler(new CommandChannel());
        this.registerUserCommandHandler(new Escape());
        this.registerUserCommandHandler(new LocCommand());
        this.registerUserCommandHandler(new OlympiadStat());
        this.registerUserCommandHandler(new PartyInfo());
        this.registerUserCommandHandler(new InstanceZone());
        this.registerUserCommandHandler(new Time());
    }

    public void registerUserCommandHandler(IUserCommandHandler iUserCommandHandler) {
        int[] nArray;
        for (int n : nArray = iUserCommandHandler.getUserCommandList()) {
            this.s.put(n, (Object)iUserCommandHandler);
        }
    }

    public IUserCommandHandler getUserCommandHandler(int n) {
        return (IUserCommandHandler)this.s.get(n);
    }

    @Override
    public int size() {
        return this.s.size();
    }

    @Override
    public void clear() {
        this.s.clear();
    }
}
