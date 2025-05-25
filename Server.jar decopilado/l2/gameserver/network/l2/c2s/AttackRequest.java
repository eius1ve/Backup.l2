/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ActionFail;

public class AttackRequest
extends L2GameClientPacket {
    private int fW;
    private int pN;
    private int pO;
    private int pP;
    private int pQ;

    @Override
    protected void readImpl() {
        this.fW = this.readD();
        this.pN = this.readD();
        this.pO = this.readD();
        this.pP = this.readD();
        this.pQ = this.readC();
    }

    @Override
    protected void runImpl() {
        GameClient gameClient = (GameClient)this.getClient();
        Player player = gameClient.getActiveChar();
        if (player == null) {
            return;
        }
        player.setActive();
        if (player.isOutOfControl()) {
            player.sendActionFailed();
            return;
        }
        if (!player.getPlayerAccess().CanAttack) {
            player.sendActionFailed();
            return;
        }
        GameObject gameObject = player.getVisibleObject(this.fW);
        if (gameObject == null) {
            player.sendActionFailed();
            return;
        }
        if (player.getAggressionTarget() != null && player.getAggressionTarget() != gameObject && !player.getAggressionTarget().isDead()) {
            player.sendActionFailed();
            return;
        }
        if (gameObject.isPlayer() && (player.isInBoat() || gameObject.isInBoat())) {
            player.sendPacket(SystemMsg.THIS_IS_NOT_ALLOWED_WHILE_USING_A_FERRY, ActionFail.STATIC);
            return;
        }
        if (gameObject.isPlayable()) {
            if (player.isInZonePeace()) {
                player.sendPacket(SystemMsg.YOU_MAY_NOT_ATTACK_IN_A_PEACEFUL_ZONE, ActionFail.STATIC);
                return;
            }
            if (((Playable)gameObject).isInZonePeace()) {
                player.sendPacket(SystemMsg.YOU_MAY_NOT_ATTACK_THIS_TARGET_IN_A_PEACEFUL_ZONE, ActionFail.STATIC);
                return;
            }
        }
        if (player.getTarget() != gameObject) {
            gameObject.onAction(player, this.pQ == 1);
            return;
        }
        if (gameObject.getObjectId() != player.getObjectId() && !player.isInStoreMode() && !player.isProcessingRequest()) {
            gameObject.onForcedAttack(player, this.pQ == 1);
        }
    }
}
