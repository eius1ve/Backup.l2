/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c.mask;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.mask.IUpdateTypeComponent;

public abstract class AbstractMaskPacket<T extends IUpdateTypeComponent>
extends L2GameServerPacket {
    protected static final byte[] DEFAULT_FLAG_ARRAY = new byte[]{-128, 64, 32, 16, 8, 4, 2, 1};

    protected abstract byte[] getMasks();

    protected void onNewMaskAdded(T t) {
    }

    @SafeVarargs
    public final void addComponentType(T ... TArray) {
        for (T t : TArray) {
            if (this.containsMask(t)) continue;
            this.addMask(t.getMask());
            this.onNewMaskAdded(t);
        }
    }

    protected void addMask(int n) {
        byte[] byArray = this.getMasks();
        int n2 = n >> 3;
        byArray[n2] = (byte)(byArray[n2] | DEFAULT_FLAG_ARRAY[n & 7]);
    }

    public boolean containsMask(T t) {
        return this.containsMask(t.getMask());
    }

    public boolean containsMask(int n) {
        return (this.getMasks()[n >> 3] & DEFAULT_FLAG_ARRAY[n & 7]) != 0;
    }

    public boolean containsMask(int n, T t) {
        return (n & t.getMask()) == t.getMask();
    }
}
