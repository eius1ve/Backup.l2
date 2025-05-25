/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class MonRaceInfo
extends L2GameServerPacket {
    private int zB;
    private int zD;
    private NpcInstance[] c;
    private int[][] j;

    public MonRaceInfo(int n, int n2, NpcInstance[] npcInstanceArray, int[][] nArray) {
        this.zB = n;
        this.zD = n2;
        this.c = npcInstanceArray;
        this.j = nArray;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(227);
        this.writeD(this.zB);
        this.writeD(this.zD);
        this.writeD(8);
        for (int i = 0; i < 8; ++i) {
            this.writeD(this.c[i].getObjectId());
            this.writeD(this.c[i].getTemplate().npcId + 1000000);
            this.writeD(14107);
            this.writeD(181875 + 58 * (7 - i));
            this.writeD(-3566);
            this.writeD(12080);
            this.writeD(181875 + 58 * (7 - i));
            this.writeD(-3566);
            this.writeF(this.c[i].getColHeight());
            this.writeF(this.c[i].getColRadius());
            this.writeD(this.j[i][0]);
            for (int j = 0; j < 20; ++j) {
                this.writeC(this.zB == 0 ? this.j[i][j] : 0);
            }
        }
    }
}
