/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.SysMsgContainer;

private static class SysMsgContainer.IntegerArgument
extends SysMsgContainer.IArgument {
    private final int Cd;

    public SysMsgContainer.IntegerArgument(int n) {
        this.Cd = n;
    }

    @Override
    public void writeData(SysMsgContainer<?> sysMsgContainer) {
        ((SysMsgContainer)sysMsgContainer).writeD(this.Cd);
    }

    @Override
    SysMsgContainer.Types getType() {
        return SysMsgContainer.Types.NUMBER;
    }
}
