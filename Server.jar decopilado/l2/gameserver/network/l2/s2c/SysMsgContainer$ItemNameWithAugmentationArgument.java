/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.SysMsgContainer;

private static class SysMsgContainer.ItemNameWithAugmentationArgument
extends SysMsgContainer.IArgument {
    private final int Ce;
    private final int Cf;
    private final int Cg;

    public SysMsgContainer.ItemNameWithAugmentationArgument(int n, int n2, int n3) {
        this.Ce = n;
        this.Cf = n2;
        this.Cg = n3;
    }

    @Override
    SysMsgContainer.Types getType() {
        return SysMsgContainer.Types.ITEM_NAME_WITH_AUGMENTATION;
    }

    void writeData(SysMsgContainer sysMsgContainer) {
        sysMsgContainer.writeD(this.Ce);
        sysMsgContainer.writeH(this.Cf);
        sysMsgContainer.writeH(this.Cg);
    }
}
