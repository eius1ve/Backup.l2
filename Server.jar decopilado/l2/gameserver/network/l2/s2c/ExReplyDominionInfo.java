/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Collections;
import java.util.List;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExReplyDominionInfo
extends L2GameServerPacket {
    private List<TerritoryInfo> cr = Collections.emptyList();

    @Override
    protected void writeImpl() {
        this.writeEx(146);
        this.writeD(this.cr.size());
        for (TerritoryInfo territoryInfo : this.cr) {
            this.writeD(territoryInfo.id);
            this.writeS(territoryInfo.terr);
            this.writeS(territoryInfo.clan);
            this.writeD(territoryInfo.flags.length);
            for (int n : territoryInfo.flags) {
                this.writeD(n);
            }
            this.writeD(territoryInfo.startTime);
        }
    }

    private class TerritoryInfo {
        public int id;
        public String terr;
        public String clan;
        public int[] flags;
        public int startTime;

        public TerritoryInfo(int n, String string, String string2, int[] nArray, int n2) {
            this.id = n;
            this.terr = string;
            this.clan = string2;
            this.flags = nArray;
            this.startTime = n2;
        }
    }
}
