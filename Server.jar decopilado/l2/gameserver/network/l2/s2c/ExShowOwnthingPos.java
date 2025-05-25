/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExShowOwnthingPos
extends L2GameServerPacket {
    private List<WardInfo> cA = new ArrayList<WardInfo>(9);

    @Override
    protected void writeImpl() {
        this.writeEx(147);
        this.writeD(this.cA.size());
        for (WardInfo wardInfo : this.cA) {
            this.writeD(wardInfo.xu);
            this.writeD(wardInfo._x);
            this.writeD(wardInfo._y);
            this.writeD(wardInfo.gl);
        }
    }

    private static class WardInfo {
        private int xu;
        private int _x;
        private int _y;
        private int gl;

        public WardInfo(int n, int n2, int n3, int n4) {
            this.xu = n;
            this._x = n2;
            this._y = n3;
            this.gl = n4;
        }
    }
}
