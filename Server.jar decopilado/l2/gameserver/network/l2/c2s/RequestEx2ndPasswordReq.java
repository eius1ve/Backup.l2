/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.SecondPasswordAuth;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.Ex2ndPasswordAck;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class RequestEx2ndPasswordReq
extends L2GameClientPacket {
    private boolean dT;
    private String ee;
    private String ef;

    @Override
    protected void readImpl() {
        this.dT = this.readC() == 2;
        this.ee = this.readS(8);
        if (this.dT) {
            this.ef = this.readS(8);
        }
    }

    @Override
    protected void runImpl() {
        GameClient gameClient = (GameClient)this.getClient();
        SecondPasswordAuth secondPasswordAuth = gameClient.getSecondPasswordAuth();
        if (secondPasswordAuth == null) {
            gameClient.sendPacket((L2GameServerPacket)new Ex2ndPasswordAck(Ex2ndPasswordAck.Ex2ndPasswordAckResult.ERROR));
            return;
        }
        if (this.dT) {
            if (secondPasswordAuth.changePassword(this.ee, this.ef)) {
                gameClient.sendPacket((L2GameServerPacket)new Ex2ndPasswordAck(Ex2ndPasswordAck.Ex2ndPasswordAckResult.SUCCESS_CREATE));
                return;
            }
            if (secondPasswordAuth.isBlocked()) {
                gameClient.sendPacket((L2GameServerPacket)new Ex2ndPasswordAck(Ex2ndPasswordAck.Ex2ndPasswordAckResult.BLOCK_HOMEPAGE));
            } else {
                gameClient.sendPacket((L2GameServerPacket)new Ex2ndPasswordAck(Ex2ndPasswordAck.Ex2ndPasswordAckResult.FAIL_VERIFY, secondPasswordAuth.getTrysCount()));
            }
        } else if (!secondPasswordAuth.isSecondPasswordSet()) {
            secondPasswordAuth.changePassword(null, this.ee);
            gameClient.sendPacket((L2GameServerPacket)new Ex2ndPasswordAck(Ex2ndPasswordAck.Ex2ndPasswordAckResult.SUCCESS_CREATE));
        } else {
            if (secondPasswordAuth.isValidSecondPassword(this.ee)) {
                gameClient.sendPacket((L2GameServerPacket)new Ex2ndPasswordAck(Ex2ndPasswordAck.Ex2ndPasswordAckResult.SUCCESS_VERIFY));
                gameClient.setSecondPasswordAuthed(true);
                return;
            }
            if (secondPasswordAuth.isBlocked()) {
                gameClient.sendPacket((L2GameServerPacket)new Ex2ndPasswordAck(Ex2ndPasswordAck.Ex2ndPasswordAckResult.BLOCK_HOMEPAGE));
            } else {
                gameClient.sendPacket((L2GameServerPacket)new Ex2ndPasswordAck(Ex2ndPasswordAck.Ex2ndPasswordAckResult.FAIL_VERIFY, secondPasswordAuth.getTrysCount()));
            }
        }
    }
}
