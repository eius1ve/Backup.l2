/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.StopMove;
import l2.gameserver.utils.Location;

public class CannotMoveAnymore
extends L2GameClientPacket {
    private Location _loc = new Location();

    @Override
    protected void readImpl() {
        this._loc.x = this.readD();
        this._loc.y = this.readD();
        this._loc.z = this.readD();
        this._loc.h = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.isOlyObserver()) {
            player.sendPacket((IStaticPacket)new StopMove(player.getObjectId(), this._loc));
            return;
        }
        if (!player.isOutOfControl()) {
            player.getAI().notifyEvent(CtrlEvent.EVT_ARRIVED_BLOCKED, this._loc, null);
            player.stopMove();
        }
    }
}
