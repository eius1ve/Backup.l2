/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  org.apache.commons.lang3.tuple.Pair
 *  org.apache.commons.lang3.tuple.Triple
 */
package events.Attendance;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class ExVipAttendanceItemListPacket
extends L2GameServerPacket {
    private List<Pair<Integer, Triple<Long, Integer, Integer>>> E = new ArrayList<Pair<Integer, Triple<Long, Integer, Integer>>>();
    private final int bE;
    private final int bF;
    private final int type;
    private final boolean S;
    private final int bG;

    public ExVipAttendanceItemListPacket(int n, int n2, int n3, boolean bl, int n4) {
        this.bE = n;
        this.bF = n2;
        this.type = n3;
        this.S = bl;
        this.bG = n4;
    }

    public ExVipAttendanceItemListPacket addItem(int n, long l, int n2, int n3) {
        this.E.add((Pair<Integer, Triple<Long, Integer, Integer>>)Pair.of((Object)n, (Object)Triple.of((Object)l, (Object)n2, (Object)n3)));
        return this;
    }

    protected void writeImpl() {
        this.writeEx(382);
        this.writeC(this.bE);
        this.writeC(this.bF);
        this.writeD(0);
        this.writeD(0);
        this.writeC(this.type);
        this.writeC(this.S);
        this.writeC(0);
        this.writeC(this.E.size());
        for (Pair<Integer, Triple<Long, Integer, Integer>> pair : this.E) {
            this.writeD((Integer)pair.getLeft());
            this.writeQ((Long)((Triple)pair.getRight()).getLeft());
            this.writeC((Integer)((Triple)pair.getRight()).getMiddle());
            this.writeC((Integer)((Triple)pair.getRight()).getRight());
        }
        this.writeC(0);
        this.writeD(this.bG);
    }
}
