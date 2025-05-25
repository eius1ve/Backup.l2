/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RequestExCubeGameChangeTeam
extends L2GameClientPacket {
    private static final Logger cQ = LoggerFactory.getLogger(RequestExCubeGameChangeTeam.class);
    int _team;
    int _arena;

    @Override
    protected void readImpl() {
        this._arena = this.readD() + 1;
        this._team = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
    }
}
