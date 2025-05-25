/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.base.Element;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExBaseAttributeCancelResult
extends L2GameServerPacket {
    private boolean eq;
    private int fW;
    private Element _element;

    public ExBaseAttributeCancelResult(boolean bl, ItemInstance itemInstance, Element element) {
        this.eq = bl;
        this.fW = itemInstance.getObjectId();
        this._element = element;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(117);
        this.writeD(this.eq);
        this.writeD(this.fW);
        this.writeD(this._element.getId());
    }
}
