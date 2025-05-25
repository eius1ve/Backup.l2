/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.PledgeReceivePowerInfo;

public class RequestPledgeMemberPowerInfo
extends L2GameClientPacket {
    private int rL;
    private String ek;

    @Override
    protected void readImpl() {
        this.rL = this.readD();
        this.ek = this.readS(16);
    }

    @Override
    protected void runImpl() {
        UnitMember unitMember;
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Clan clan = player.getClan();
        if (clan != null && (unitMember = clan.getAnyMember(this.ek)) != null) {
            player.sendPacket((IStaticPacket)new PledgeReceivePowerInfo(unitMember));
        }
    }
}
