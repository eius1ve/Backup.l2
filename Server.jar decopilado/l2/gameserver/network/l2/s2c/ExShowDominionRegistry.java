/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Collections;
import java.util.List;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExShowDominionRegistry
extends L2GameServerPacket {
    private int qQ;
    private String fq;
    private String fr;
    private String fs;
    private int xp;
    private int xq;
    private int xr;
    private int cP;
    private boolean eR;
    private boolean eS;
    private List<TerritoryFlagsInfo> cz = Collections.emptyList();

    @Override
    protected void writeImpl() {
        this.writeEx(144);
        this.writeD(this.qQ);
        this.writeS(this.fq);
        this.writeS(this.fr);
        this.writeS(this.fs);
        this.writeD(this.xp);
        this.writeD(this.xq);
        this.writeD(this.xr);
        this.writeD(this.cP);
        this.writeD(this.eS);
        this.writeD(this.eR);
        this.writeD(1);
        this.writeD(this.cz.size());
        for (TerritoryFlagsInfo territoryFlagsInfo : this.cz) {
            this.writeD(territoryFlagsInfo.id);
            this.writeD(territoryFlagsInfo.flags.length);
            for (int n : territoryFlagsInfo.flags) {
                this.writeD(n);
            }
        }
    }

    private class TerritoryFlagsInfo {
        public int id;
        public int[] flags;

        public TerritoryFlagsInfo(int n, int[] nArray) {
            this.id = n;
            this.flags = nArray;
        }
    }
}
