/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.SysMsgContainer;

private static class SysMsgContainer.ClassIdArgument
extends SysMsgContainer.ShortArgument {
    public SysMsgContainer.ClassIdArgument(int n) {
        super((short)n);
    }

    @Override
    SysMsgContainer.Types getType() {
        return SysMsgContainer.Types.CLASS_ID;
    }
}
