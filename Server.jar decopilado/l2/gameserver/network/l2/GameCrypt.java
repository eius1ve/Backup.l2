/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2;

import java.nio.ByteBuffer;
import l2.gameserver.network.l2.BlowFishKeygen;

public abstract class GameCrypt<CGCtx, CGKey> {
    private final byte[] m = new byte[16];
    private final byte[] n = new byte[16];
    private boolean i = false;

    public CGKey initKey(CGCtx CGCtx) {
        return (CGKey)BlowFishKeygen.getRandomKey();
    }

    public CGKey setKey(CGKey CGKey) {
        System.arraycopy(CGKey, 0, this.m, 0, 16);
        System.arraycopy(CGKey, 0, this.n, 0, 16);
        return CGKey;
    }

    public boolean decrypt(ByteBuffer[] byteBufferArray) {
        throw new UnsupportedOperationException();
    }

    public boolean decrypt(byte[] byArray, int n, int n2) {
        int n3;
        if (!this.i) {
            return true;
        }
        int n4 = 0;
        for (n3 = 0; n3 < n2; ++n3) {
            int n5 = byArray[n + n3] & 0xFF;
            byArray[n + n3] = (byte)(n5 ^ this.m[n3 & 0xF] ^ n4);
            n4 = n5;
        }
        n3 = this.m[8] & 0xFF;
        n3 |= this.m[9] << 8 & 0xFF00;
        n3 |= this.m[10] << 16 & 0xFF0000;
        n3 |= this.m[11] << 24 & 0xFF000000;
        this.m[8] = (byte)((n3 += n2) & 0xFF);
        this.m[9] = (byte)(n3 >> 8 & 0xFF);
        this.m[10] = (byte)(n3 >> 16 & 0xFF);
        this.m[11] = (byte)(n3 >> 24 & 0xFF);
        return true;
    }

    public boolean encrypt(ByteBuffer[] byteBufferArray) {
        throw new UnsupportedOperationException();
    }

    public void encrypt(byte[] byArray, int n, int n2) {
        int n3;
        if (!this.i) {
            this.i = true;
            return;
        }
        int n4 = 0;
        for (n3 = 0; n3 < n2; ++n3) {
            int n5 = byArray[n + n3] & 0xFF;
            n4 = n5 ^ this.n[n3 & 0xF] ^ n4;
            byArray[n + n3] = (byte)n4;
        }
        n3 = this.n[8] & 0xFF;
        n3 |= this.n[9] << 8 & 0xFF00;
        n3 |= this.n[10] << 16 & 0xFF0000;
        n3 |= this.n[11] << 24 & 0xFF000000;
        this.n[8] = (byte)((n3 += n2) & 0xFF);
        this.n[9] = (byte)(n3 >> 8 & 0xFF);
        this.n[10] = (byte)(n3 >> 16 & 0xFF);
        this.n[11] = (byte)(n3 >> 24 & 0xFF);
    }
}
