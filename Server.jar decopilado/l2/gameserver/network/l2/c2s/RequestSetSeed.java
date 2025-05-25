/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.instancemanager.CastleManorManager;
import l2.gameserver.model.Manor;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.templates.manor.SeedProduction;

public class RequestSetSeed
extends L2GameClientPacket {
    private int gT;
    private int hp;
    private long[] f;

    @Override
    protected void readImpl() {
        this.hp = this.readD();
        this.gT = this.readD();
        if (this.gT * 20 > this._buf.remaining() || this.gT > Short.MAX_VALUE || this.gT < 1) {
            this.gT = 0;
            return;
        }
        this.f = new long[this.gT * 3];
        int n = 0;
        while (n < this.f.length) {
            int n2 = this.readD();
            long l = this.readQ();
            long l2 = this.readQ();
            if (n2 < 1 || l < 0L || l2 < 0L) {
                this.gT = 0;
                return;
            }
            this.f[n++] = n2;
            this.f[n++] = l;
            this.f[n++] = l2;
        }
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || this.gT == 0) {
            return;
        }
        if (player.getClan() == null) {
            player.sendActionFailed();
            return;
        }
        Castle castle = ResidenceHolder.getInstance().getResidence(Castle.class, this.hp);
        if (castle == null || castle.getOwnerId() != player.getClanId() || (player.getClanPrivileges() & 0x20000) != 131072) {
            player.sendActionFailed();
            return;
        }
        if (castle.isNextPeriodApproved()) {
            player.sendPacket((IStaticPacket)SystemMsg.A_MANOR_CANNOT_BE_SET_UP_BETWEEN_430_AM_AND_8_PM);
            player.sendActionFailed();
            return;
        }
        ArrayList<SeedProduction> arrayList = new ArrayList<SeedProduction>(this.gT);
        List<Manor.SeedData> list = Manor.getInstance().getSeedsForCastle(this.hp);
        block0: for (int i = 0; i < this.gT; ++i) {
            int n = (int)this.f[i * 3 + 0];
            long l = this.f[i * 3 + 1];
            long l2 = this.f[i * 3 + 2];
            if (n <= 0) continue;
            for (Manor.SeedData seedData : list) {
                long l3;
                if (seedData.getId() != n) continue;
                if (l > seedData.getSeedLimit() || l2 < (l3 = Manor.getInstance().getSeedBasicPrice(n)) * 60L / 100L || l2 > l3 * 10L) continue block0;
                SeedProduction seedProduction = CastleManorManager.getInstance().getNewSeedProduction(n, l, l2, l);
                arrayList.add(seedProduction);
                continue block0;
            }
        }
        castle.setSeedProduction(arrayList, 1);
        castle.saveSeedData(1);
    }
}
