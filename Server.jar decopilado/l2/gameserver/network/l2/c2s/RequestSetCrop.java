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
import l2.gameserver.templates.manor.CropProcure;

public class RequestSetCrop
extends L2GameClientPacket {
    private int gT;
    private int hp;
    private RequestSetCropEntry[] a = RequestSetCropEntry.EMPTY_ARRAY;

    @Override
    protected void readImpl() {
        this.hp = this.readD();
        this.gT = this.readD();
        if (this.gT * 21 > this._buf.remaining() || this.gT > Short.MAX_VALUE || this.gT < 1) {
            this.gT = 0;
            return;
        }
        this.a = new RequestSetCropEntry[this.gT];
        for (int i = 0; i < this.a.length; ++i) {
            int n = this.readD();
            long l = this.readQ();
            long l2 = this.readQ();
            int n2 = this.readC();
            if (n < 1 || l < 0L || l2 < 0L || n2 < 0 || n2 > 2) {
                this.gT = 0;
                return;
            }
            this.a[i] = new RequestSetCropEntry(n, l, l2, n2);
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
        ArrayList<CropProcure> arrayList = new ArrayList<CropProcure>(this.gT);
        List<Manor.SeedData> list = Manor.getInstance().getCropsForCastle(this.hp);
        block0: for (int i = 0; i < this.gT; ++i) {
            RequestSetCropEntry requestSetCropEntry = this.a[i];
            if (requestSetCropEntry == null || requestSetCropEntry.getId() <= 0) continue;
            for (Manor.SeedData seedData : list) {
                if (seedData.getCrop() != requestSetCropEntry.getId()) continue;
                if (requestSetCropEntry.getSales() > seedData.getCropLimit()) continue block0;
                long l = Manor.getInstance().getCropBasicPrice(requestSetCropEntry.getId());
                if (requestSetCropEntry.getPrice() != 0L && (requestSetCropEntry.getPrice() < l * 60L / 100L || requestSetCropEntry.getPrice() > l * 10L)) continue block0;
                CropProcure cropProcure = CastleManorManager.getInstance().getNewCropProcure(requestSetCropEntry.getId(), requestSetCropEntry.getSales(), requestSetCropEntry.getType(), requestSetCropEntry.getPrice(), requestSetCropEntry.getSales());
                arrayList.add(cropProcure);
                continue block0;
            }
        }
        castle.setCropProcure(arrayList, 1);
        castle.saveCropData(1);
    }

    private static class RequestSetCropEntry {
        public static final RequestSetCropEntry[] EMPTY_ARRAY = new RequestSetCropEntry[0];
        private final int rZ;
        private final long cU;
        private final long cV;
        private final int sa;

        private RequestSetCropEntry(int n, long l, long l2, int n2) {
            this.rZ = n;
            this.cU = l;
            this.cV = l2;
            this.sa = n2;
        }

        public int getId() {
            return this.rZ;
        }

        public long getSales() {
            return this.cU;
        }

        public long getPrice() {
            return this.cV;
        }

        public int getType() {
            return this.sa;
        }
    }
}
