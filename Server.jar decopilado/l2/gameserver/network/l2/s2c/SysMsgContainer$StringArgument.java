/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.SysMsgContainer;

private static class SysMsgContainer.StringArgument
extends SysMsgContainer.IArgument {
    private final String fO;

    public SysMsgContainer.StringArgument(String string) {
        this.fO = string == null ? "null" : string;
    }

    @Override
    void writeData(SysMsgContainer<?> sysMsgContainer) {
        ((SysMsgContainer)sysMsgContainer).writeS(this.fO);
    }

    @Override
    SysMsgContainer.Types getType() {
        return SysMsgContainer.Types.TEXT;
    }
}
