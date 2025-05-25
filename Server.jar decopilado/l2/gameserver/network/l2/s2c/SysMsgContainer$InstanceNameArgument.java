/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.SysMsgContainer;

public static class SysMsgContainer.InstanceNameArgument
extends SysMsgContainer.IntegerArgument {
    public SysMsgContainer.InstanceNameArgument(int n) {
        super(n);
    }

    @Override
    SysMsgContainer.Types getType() {
        return SysMsgContainer.Types.INSTANCE_NAME;
    }
}
