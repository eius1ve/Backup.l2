/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Collections;
import java.util.List;
import l2.gameserver.model.entity.events.objects.BoatPoint;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExAirShipTeleportList
extends L2GameServerPacket {
    private int lx;
    private List<BoatPoint> ca = Collections.emptyList();

    @Override
    protected void writeImpl() {
        this.writeEx(154);
        this.writeD(this.lx);
        this.writeD(this.ca.size());
        for (int i = 0; i < this.ca.size(); ++i) {
            BoatPoint boatPoint = this.ca.get(i);
            this.writeD(i - 1);
            this.writeD(boatPoint.getFuel());
            this.writeD(boatPoint.x);
            this.writeD(boatPoint.y);
            this.writeD(boatPoint.z);
        }
    }
}
