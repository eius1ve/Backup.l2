/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.SysMsgContainer;

private static class SysMsgContainer.SkillArgument
extends SysMsgContainer.IArgument {
    private final int Ch;
    private final int Ci;

    public SysMsgContainer.SkillArgument(int n, int n2) {
        this.Ch = n;
        this.Ci = n2;
    }

    @Override
    void writeData(SysMsgContainer<?> sysMsgContainer) {
        ((SysMsgContainer)sysMsgContainer).writeD(this.Ch);
        ((SysMsgContainer)sysMsgContainer).writeH(this.Ci);
    }

    @Override
    SysMsgContainer.Types getType() {
        return SysMsgContainer.Types.SKILL_NAME;
    }
}
