/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExChangeNicknameNColor
extends L2GameServerPacket {
    private int _itemId;

    public ExChangeNicknameNColor(int n) {
        this._itemId = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(132);
        this.writeD(this._itemId);
    }
}
