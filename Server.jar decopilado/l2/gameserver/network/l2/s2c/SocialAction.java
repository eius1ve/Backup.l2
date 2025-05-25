/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class SocialAction
extends L2GameServerPacket {
    private int ph;
    private int pK;
    public static final int GREETING = 2;
    public static final int VICTORY = 3;
    public static final int ADVANCE = 4;
    public static final int NO = 5;
    public static final int YES = 6;
    public static final int BOW = 7;
    public static final int UNAWARE = 8;
    public static final int WAITING = 9;
    public static final int LAUGH = 10;
    public static final int APPLAUD = 11;
    public static final int DANCE = 12;
    public static final int SORROW = 13;
    public static final int CHARM = 14;
    public static final int SHYNESS = 15;
    public static final int COUPLE_BOW = 16;
    public static final int COUPLE_HIGH_FIVE = 17;
    public static final int COUPLE_DANCE = 18;
    public static final int LEVEL_UP = 2122;
    public static final int GIVE_HERO = 20016;
    public static final int PROPOSE = 28;
    public static final int PROVOKE = 29;
    public static final int BOASTING = 30;

    public SocialAction(int n, int n2) {
        this.ph = n;
        this.pK = n2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(39);
        this.writeD(this.ph);
        this.writeD(this.pK);
    }
}
