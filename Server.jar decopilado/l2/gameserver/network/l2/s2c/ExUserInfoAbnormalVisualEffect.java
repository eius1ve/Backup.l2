/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.skills.AbnormalEffect;

public class ExUserInfoAbnormalVisualEffect
extends L2GameServerPacket {
    private final int xP;
    private final int xQ;
    private final AbnormalEffect[] a;

    public ExUserInfoAbnormalVisualEffect(Player player) {
        this.xP = player.getObjectId();
        this.xQ = player.getTransformation();
        this.a = player.getAbnormalEffects();
    }

    @Override
    protected void writeImpl() {
        this.writeEx(344);
        this.writeD(this.xP);
        this.writeD(this.xQ);
        this.writeD(this.a.length);
        for (AbnormalEffect abnormalEffect : this.a) {
            this.writeH(abnormalEffect.getClientId());
        }
    }
}
