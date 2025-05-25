/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.skills.AbnormalEffect;

public class NpcInfoAbnormalVisualEffect
extends L2GameServerPacket {
    private final int zJ;
    private final AbnormalEffect[] b;

    public NpcInfoAbnormalVisualEffect(Creature creature) {
        this.zJ = creature.getObjectId();
        this.b = creature.getAbnormalEffects();
    }

    @Override
    protected void writeImpl() {
        this.writeC(75);
        this.writeD(this.zJ);
        this.writeD(0);
        this.writeH(this.b.length);
        for (AbnormalEffect abnormalEffect : this.b) {
            this.writeH(abnormalEffect.getClientId());
        }
    }
}
