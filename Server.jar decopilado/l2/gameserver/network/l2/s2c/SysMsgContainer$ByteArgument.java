/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.SysMsgContainer;

private static class SysMsgContainer.ByteArgument
extends SysMsgContainer.IArgument {
    private final int Cc;

    public SysMsgContainer.ByteArgument(int n) {
        this.Cc = n;
    }

    @Override
    void writeData(SysMsgContainer<?> sysMsgContainer) {
        ((SysMsgContainer)sysMsgContainer).writeC(this.Cc);
    }

    @Override
    SysMsgContainer.Types getType() {
        return SysMsgContainer.Types.BYTE;
    }
}
