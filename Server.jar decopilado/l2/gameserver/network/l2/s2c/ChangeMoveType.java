/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.model.base.EnvType;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ChangeMoveType
extends L2GameServerPacket {
    private final int sR;
    private final boolean ee;
    private final EnvType a;

    public ChangeMoveType(Creature creature) {
        this.sR = creature.getObjectId();
        this.ee = creature.isRunning();
        this.a = creature.isFlying() ? EnvType.AIR : (creature.isInWater() ? EnvType.WATER : EnvType.GROUND);
    }

    @Override
    protected final void writeImpl() {
        this.writeC(40);
        this.writeD(this.sR);
        this.writeD(this.ee ? 1 : 0);
        this.writeC(this.a.ordinal());
    }
}
