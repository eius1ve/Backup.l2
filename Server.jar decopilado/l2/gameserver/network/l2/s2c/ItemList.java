/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.List;
import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.LockType;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class ItemList
extends AbstractItemListPacket {
    private final boolean eY;
    private final List<ItemInfo> cL;
    private final boolean eZ;
    private LockType a;
    private int[] aN;

    public ItemList(boolean bl, List<ItemInfo> list, boolean bl2, LockType lockType, int ... nArray) {
        this.eY = bl;
        this.cL = list;
        this.eZ = bl2;
        this.a = lockType;
        this.aN = nArray;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(17);
        this.writeC(this.eY ? 1 : 2);
        if (this.eY) {
            this.writeD(this.eZ);
            this.writeD(this.cL.size());
        } else {
            this.writeD(this.cL.size());
            this.writeD(this.cL.size());
            for (ItemInfo itemInfo : this.cL) {
                this.writeItemInfo(itemInfo);
            }
        }
        this.writeH(this.aN.length);
        if (this.aN.length > 0) {
            this.writeC(this.a.ordinal());
            for (Object object : (Object)this.aN) {
                this.writeD((int)object);
            }
        }
    }
}
