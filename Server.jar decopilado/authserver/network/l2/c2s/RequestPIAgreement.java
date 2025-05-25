/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2.c2s;

import l2.authserver.network.l2.L2LoginClient;
import l2.authserver.network.l2.c2s.L2LoginClientPacket;
import l2.authserver.network.l2.s2c.PIAgreementAck;

public class RequestPIAgreement
extends L2LoginClientPacket {
    private int dT;
    private int _status;

    @Override
    public void readImpl() {
        this.dT = this.readD();
        this._status = this.readC();
    }

    @Override
    protected void runImpl() {
        L2LoginClient l2LoginClient = (L2LoginClient)this.getClient();
        l2LoginClient.sendPacket(new PIAgreementAck(this.dT, this._status));
    }
}
