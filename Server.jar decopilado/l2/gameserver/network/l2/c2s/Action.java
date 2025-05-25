/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ActionFail;

public class Action
extends L2GameClientPacket {
    private int fW;
    private int pK;

    @Override
    protected void readImpl() {
        this.fW = this.readD();
        this.readD();
        this.readD();
        this.readD();
        this.pK = this.readC();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Action.onAction(player, this.fW, this.pK == 1);
    }

    public static void onAction(Player player, int n, boolean bl) {
        if (player.isOutOfControl()) {
            player.sendActionFailed();
            return;
        }
        if (player.isInStoreMode()) {
            player.sendActionFailed();
            return;
        }
        GameObject gameObject = player.getVisibleObject(n);
        if (gameObject == null) {
            player.sendActionFailed();
            return;
        }
        player.setActive();
        if (player.getAggressionTarget() != null && player.getAggressionTarget() != gameObject) {
            player.sendActionFailed();
            return;
        }
        if (player.isLockedTarget()) {
            player.sendActionFailed();
            return;
        }
        if (player.isFrozen()) {
            player.sendPacket(SystemMsg.YOU_CANNOT_MOVE_WHILE_FROZEN, ActionFail.STATIC);
            return;
        }
        gameObject.onAction(player, bl);
    }
}
