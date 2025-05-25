/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class MagicSkillCanceled
extends L2GameServerPacket {
    private int zs;
    private final int zt;
    private final int zu;

    public MagicSkillCanceled(Creature creature) {
        this.zs = creature.getObjectId();
        this.zt = creature.getX();
        this.zu = creature.getY();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(73);
        this.writeD(this.zs);
    }

    @Override
    public L2GameServerPacket packet(Player player) {
        if (player != null && !player.isInObserverMode()) {
            if (player.buffAnimRange() < 0) {
                return null;
            }
            if (player.buffAnimRange() == 0) {
                return this.zs == player.getObjectId() ? super.packet(player) : null;
            }
            return player.getDistance(this.zt, this.zu) < (double)player.buffAnimRange() ? super.packet(player) : null;
        }
        return super.packet(player);
    }
}
