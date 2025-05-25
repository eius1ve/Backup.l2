/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.GameObject;
import l2.gameserver.network.l2.s2c.SysMsgContainer;

private static class SysMsgContainer.VisibleDamageArgument
extends SysMsgContainer.IArgument {
    private final int Cj;
    private final int Ck;
    private final int Cl;

    public SysMsgContainer.VisibleDamageArgument(GameObject gameObject, GameObject gameObject2, int n) {
        this.Cj = gameObject.getObjectId();
        this.Ck = gameObject2.getObjectId();
        this.Cl = n;
    }

    @Override
    void writeData(SysMsgContainer<?> sysMsgContainer) {
        ((SysMsgContainer)sysMsgContainer).writeD(this.Ck);
        ((SysMsgContainer)sysMsgContainer).writeD(this.Cj);
        ((SysMsgContainer)sysMsgContainer).writeD(this.Cl);
    }

    @Override
    SysMsgContainer.Types getType() {
        return SysMsgContainer.Types.TYPE_VISIBLE_DAMAGE;
    }
}
