/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.SysMsgContainer;

private static class SysMsgContainer.LongArgument
extends SysMsgContainer.IArgument {
    private final long dA;

    public SysMsgContainer.LongArgument(long l) {
        this.dA = l;
    }

    @Override
    void writeData(SysMsgContainer<?> sysMsgContainer) {
        ((SysMsgContainer)sysMsgContainer).writeQ(this.dA);
    }

    @Override
    SysMsgContainer.Types getType() {
        return SysMsgContainer.Types.LONG;
    }
}
