/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.SkillLearn;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExAcquireSkillInfo
extends L2GameServerPacket {
    private final SkillLearn b;

    public ExAcquireSkillInfo(SkillLearn skillLearn) {
        this.b = skillLearn;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(252);
        this.writeD(this.b.getId());
        this.writeD(this.b.getLevel());
        this.writeQ(this.b.getCost());
        this.writeH(this.b.getMinLevel());
        this.writeH(0);
        if (this.b.getItemId() > 0) {
            this.writeD(1);
            this.writeD(this.b.getItemId());
            this.writeQ(this.b.getItemCount());
        } else {
            this.writeD(0);
        }
        this.writeD(0);
    }
}
