/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.SysMsgContainer;

private static class SysMsgContainer.SysStringArgument
extends SysMsgContainer.IntegerArgument {
    public SysMsgContainer.SysStringArgument(int n) {
        super(n);
    }

    @Override
    SysMsgContainer.Types getType() {
        return SysMsgContainer.Types.SYSTEM_STRING;
    }
}
