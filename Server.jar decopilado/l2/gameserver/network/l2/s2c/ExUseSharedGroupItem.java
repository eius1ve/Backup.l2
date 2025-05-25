/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.skills.TimeStamp;

public class ExUseSharedGroupItem
extends L2GameServerPacket {
    private int _itemId;
    private int xM;
    private int xN;
    private int xO;

    public ExUseSharedGroupItem(int n, TimeStamp timeStamp) {
        this.xM = n;
        this._itemId = timeStamp.getId();
        this.xN = (int)(timeStamp.getReuseCurrent() / 1000L);
        this.xO = (int)(timeStamp.getReuseBasic() / 1000L);
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(75);
        this.writeD(this._itemId);
        this.writeD(this.xM);
        this.writeD(this.xN);
        this.writeD(this.xO);
    }
}
