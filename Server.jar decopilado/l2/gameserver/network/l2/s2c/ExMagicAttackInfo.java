/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExMagicAttackInfo
extends L2GameServerPacket {
    public static final int CRITICAL = 1;
    public static final int CRITICAL_HEAL = 2;
    public static final int OVERHIT = 3;
    public static final int EVADED = 4;
    public static final int BLOCKED = 5;
    public static final int RESISTED = 6;
    public static final int IMMUNE = 7;
    public static final int IMMUNE2 = 8;
    private final int vD;
    private final int vE;
    private final int vF;

    public ExMagicAttackInfo(int n, int n2, int n3) {
        this.vD = n;
        this.vE = n2;
        this.vF = n3;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(251);
        this.writeD(this.vD);
        this.writeD(this.vE);
        this.writeD(this.vF);
    }
}
