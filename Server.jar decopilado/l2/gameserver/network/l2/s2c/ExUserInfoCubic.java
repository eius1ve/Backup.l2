/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.skills.effects.EffectCubic;

public class ExUserInfoCubic
extends L2GameServerPacket {
    private int objectId;
    private EffectCubic[] a;
    private int xR;

    public ExUserInfoCubic(Player player) {
        this.objectId = player.getObjectId();
        this.a = player.getCubics().toArray(new EffectCubic[player.getCubics().size()]);
        this.xR = player.getAgathionId();
    }

    @Override
    protected void writeImpl() {
        this.writeEx(343);
        this.writeD(this.objectId);
        this.writeH(this.a.length);
        for (EffectCubic effectCubic : this.a) {
            this.writeH(effectCubic.getId());
        }
        this.writeD(this.xR);
    }
}
