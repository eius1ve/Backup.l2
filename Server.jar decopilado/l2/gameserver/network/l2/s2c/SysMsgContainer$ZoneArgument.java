/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.SysMsgContainer;

private static class SysMsgContainer.ZoneArgument
extends SysMsgContainer.IArgument {
    private final int Cm;
    private final int Cn;
    private final int Co;

    public SysMsgContainer.ZoneArgument(int n, int n2, int n3) {
        this.Cm = n;
        this.Cn = n2;
        this.Co = n3;
    }

    @Override
    void writeData(SysMsgContainer<?> sysMsgContainer) {
        ((SysMsgContainer)sysMsgContainer).writeD(this.Cm);
        ((SysMsgContainer)sysMsgContainer).writeD(this.Cn);
        ((SysMsgContainer)sysMsgContainer).writeD(this.Co);
    }

    @Override
    SysMsgContainer.Types getType() {
        return SysMsgContainer.Types.ZONE_NAME;
    }
}
