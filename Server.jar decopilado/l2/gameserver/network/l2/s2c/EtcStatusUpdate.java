/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class EtcStatusUpdate
extends L2GameServerPacket {
    private static final int tV = 1;
    private static final int tW = 2;
    private static final int tX = 4;
    private int tY;
    private int tZ;
    private int ua;
    private int ub;
    private int uc;
    private int ud;
    private int ue;

    public EtcStatusUpdate(Player player) {
        this.tY = player.getIncreasedForce();
        this.tZ = player.getWeightPenalty();
        this.ua = player.getArmorsExpertisePenalty();
        this.ub = player.getWeaponsExpertisePenalty();
        this.uc = player.getDeathPenalty() == null ? 0 : player.getDeathPenalty().getLevel();
        this.ud = player.getConsumedSouls();
        boolean bl = player.getMessageRefusal() || player.getNoChannel() > 0L || player.isBlockAll();
        boolean bl2 = player.isCharmOfCourage();
        boolean bl3 = player.isInDangerArea();
        if (bl) {
            this.ue |= 1;
        }
        if (bl3) {
            this.ue |= 2;
        }
        if (bl2) {
            this.ue |= 4;
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(249);
        this.writeC(this.tY);
        this.writeD(this.tZ);
        this.writeC(this.ub);
        this.writeC(this.ua);
        this.writeC(this.uc);
        this.writeC(this.ud);
        this.writeC(this.ue);
    }
}
