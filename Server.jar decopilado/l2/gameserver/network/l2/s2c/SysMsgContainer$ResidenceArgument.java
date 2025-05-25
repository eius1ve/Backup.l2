/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.SysMsgContainer;

private static class SysMsgContainer.ResidenceArgument
extends SysMsgContainer.IntegerArgument {
    public SysMsgContainer.ResidenceArgument(int n) {
        super(n);
    }

    @Override
    SysMsgContainer.Types getType() {
        return SysMsgContainer.Types.RESIDENCE_NAME;
    }
}
