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

public class RequestSetPledgeCrestLarge
extends L2GameClientPacket {
    private int rY;
    private byte[] q;

    @Override
    protected void readImpl() {
        this.rY = this.readD();
        if (this.rY == 2176 && this.rY == this._buf.remaining()) {
            this.q = new byte[this.rY];
            this.readB(this.q);
        }
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            return;
        }
        if ((player.getClanPrivileges() & 0x80) == 128) {
            if (clan.isPlacedForDisband()) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_ALREADY_REQUESTED_THE_DISSOLUTION_OF_YOUR_CLAN);
                return;
            }
            if (clan.getCastle() == 0 && clan.getHasHideout() == 0) {
                player.sendPacket((IStaticPacket)SystemMsg.THE_CLAN_CREST_WAS_SUCCESSFULLY_REGISTERED__REMEMBER_ONLY_A_CLAN_THAT_OWNS_A_CLAN_HALL_OR_CASTLE_CAN_DISPLAY_A_CREST);
                return;
            }
            int n = 0;
            if (this.q != null && CrestCache.isValidCrestData(this.q)) {
                n = CrestCache.getInstance().savePledgeCrestLarge(clan.getClanId(), this.q);
                player.sendPacket((IStaticPacket)SystemMsg.THE_CLAN_CREST_WAS_SUCCESSFULLY_REGISTERED__REMEMBER_ONLY_A_CLAN_THAT_OWNS_A_CLAN_HALL_OR_CASTLE_CAN_DISPLAY_A_CREST);
            } else if (clan.hasCrestLarge()) {
                CrestCache.getInstance().removePledgeCrestLarge(clan.getClanId());
            }
            clan.setCrestLargeId(n);
            clan.broadcastClanStatus(false, true, false);
        }
    }
}
