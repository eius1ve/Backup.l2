/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.cache.CrestCache;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;

public class RequestSetPledgeCrest
extends L2GameClientPacket {
    private int rY;
    private byte[] q;

    @Override
    protected void readImpl() {
        this.rY = this.readD();
        if (this.rY == 256 && this.rY == this._buf.remaining()) {
            this.q = new byte[this.rY];
            this.readB(this.q);
        }
    }

    @Override
    protected void runImpl() {
        GameClient gameClient = (GameClient)this.getClient();
        Player player = gameClient.getActiveChar();
        if (player == null) {
            return;
        }
        Clan clan = player.getClan();
        if ((player.getClanPrivileges() & 0x80) == 128) {
            if (clan.isPlacedForDisband()) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_ALREADY_REQUESTED_THE_DISSOLUTION_OF_YOUR_CLAN);
                return;
            }
            if (clan.getLevel() < 3) {
                player.sendPacket((IStaticPacket)SystemMsg.A_CLAN_CREST_CAN_ONLY_BE_REGISTERED_WHEN_THE_CLANS_SKILL_LEVEL_IS_3_OR_ABOVE);
                return;
            }
            int n = 0;
            if (this.q != null && CrestCache.isValidCrestData(this.q)) {
                n = CrestCache.getInstance().savePledgeCrest(clan.getClanId(), this.q);
            } else if (clan.hasCrest()) {
                CrestCache.getInstance().removePledgeCrest(clan.getClanId());
            }
            clan.setCrestId(n);
            clan.broadcastClanStatus(false, true, false);
        }
    }
}
