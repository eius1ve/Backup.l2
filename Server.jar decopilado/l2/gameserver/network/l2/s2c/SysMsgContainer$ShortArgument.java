/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.SysMsgContainer;

private static class SysMsgContainer.ShortArgument
extends SysMsgContainer.IArgument {
    private final short a;

    public SysMsgContainer.ShortArgument(short s) {
        this.a = s;
    }

    @Override
    void writeData(SysMsgContainer<?> sysMsgContainer) {
        ((SysMsgContainer)sysMsgContainer).writeH(this.a);
    }

    @Override
    SysMsgContainer.Types getType() {
        return SysMsgContainer.Types.NUMBER;
    }
}
