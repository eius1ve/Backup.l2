/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2;

import l2.commons.util.Rnd;

public class BlowFishKeygen {
    private static final int pz = 20;
    private static final byte[][] c = new byte[20][16];

    private BlowFishKeygen() {
    }

    public static byte[] getRandomKey() {
        return c[Rnd.get(20)];
    }

    static {
        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < c[i].length; ++j) {
                BlowFishKeygen.c[i][j] = (byte)Rnd.get(255);
            }
            BlowFishKeygen.c[i][8] = -56;
            BlowFishKeygen.c[i][9] = 39;
            BlowFishKeygen.c[i][10] = -109;
            BlowFishKeygen.c[i][11] = 1;
            BlowFishKeygen.c[i][12] = -95;
            BlowFishKeygen.c[i][13] = 108;
            BlowFishKeygen.c[i][14] = 49;
            BlowFishKeygen.c[i][15] = -105;
        }
    }
}
