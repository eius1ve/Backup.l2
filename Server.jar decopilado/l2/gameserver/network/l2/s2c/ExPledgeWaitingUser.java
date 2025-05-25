/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.pledge.entry.PledgeApplicantInfo;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPledgeWaitingUser
extends L2GameServerPacket {
    private final PledgeApplicantInfo a;

    public ExPledgeWaitingUser(PledgeApplicantInfo pledgeApplicantInfo) {
        this.a = pledgeApplicantInfo;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(325);
        this.writeD(this.a.getPlayerId());
        this.writeS(this.a.getMessage());
    }
}
