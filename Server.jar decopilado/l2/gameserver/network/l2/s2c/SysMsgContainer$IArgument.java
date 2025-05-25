/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.SysMsgContainer;

public static abstract class SysMsgContainer.IArgument {
    void write(SysMsgContainer<?> sysMsgContainer) {
        ((SysMsgContainer)sysMsgContainer).writeC(this.getType().ordinal());
        this.writeData(sysMsgContainer);
    }

    abstract SysMsgContainer.Types getType();

    abstract void writeData(SysMsgContainer<?> var1);
}
