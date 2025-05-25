/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Collections;
import java.util.List;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExShowFortressInfo
extends L2GameServerPacket {
    private List<FortressInfo> cx = Collections.emptyList();

    @Override
    protected final void writeImpl() {
        this.writeEx(21);
        this.writeD(this.cx.size());
        for (FortressInfo fortressInfo : this.cx) {
            this.writeD(fortressInfo._id);
            this.writeS(fortressInfo._owner);
            this.writeD(fortressInfo._status);
            this.writeD(fortressInfo._siege);
        }
    }

    static class FortressInfo {
        public int _id;
        public int _siege;
        public String _owner;
        public boolean _status;

        public FortressInfo(String string, int n, boolean bl, int n2) {
            this._owner = string;
            this._id = n;
            this._status = bl;
            this._siege = n2;
        }
    }
}
