/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ManagePledgePower;

public class RequestPledgePower
extends L2GameClientPacket {
    private int pb;
    private int bC;
    private int pd;

    @Override
    protected void readImpl() {
        this.pb = this.readD();
        this.bC = this.readD();
        if (this.bC == 2) {
            this.pd = this.readD();
        }
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (this.bC == 2) {
            if (this.pb < 1 || this.pb > 9) {
                return;
            }
            if (player.getClan() != null && (player.getClanPrivileges() & 0x10) == 16) {
                if (this.pb == 9) {
                    this.pd = (this.pd & 8) + (this.pd & 0x800) + (this.pd & 0x10000) + (this.pd & 0x1000) + (this.pd & 0x80000);
                }
                player.getClan().setRankPrivs(this.pb, this.pd);
                player.getClan().updatePrivsForRank(this.pb);
            }
        } else if (player.getClan() != null) {
            player.sendPacket((IStaticPacket)new ManagePledgePower(player, this.bC, this.pb));
        } else {
            player.sendActionFailed();
        }
    }
}
