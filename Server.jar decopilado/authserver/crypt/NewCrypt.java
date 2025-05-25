/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.crypt;

import java.io.IOException;
import java.nio.ByteBuffer;
import l2.authserver.crypt.BlowfishEngine;

public class NewCrypt {
    private final BlowfishEngine a = new BlowfishEngine();
    private final BlowfishEngine b;

    public NewCrypt(byte[] byArray) {
        this.a.init(true, byArray);
        this.b = new BlowfishEngine();
        this.b.init(false, byArray);
    }

    public NewCrypt(String string) {
        this(string.getBytes());
    }

    public static boolean verifyChecksum(byte[] byArray) {
        return NewCrypt.verifyChecksum(byArray, 0, byArray.length);
    }

    public static boolean verifyChecksum(byte[] byArray, int n, int n2) {
        int n3;
        if ((n2 & 3) != 0 || n2 <= 4) {
            return false;
        }
        long l = 0L;
        int n4 = n2 - 4;
        long l2 = -1L;
        for (n3 = n; n3 < n4; n3 += 4) {
            l2 = byArray[n3] & 0xFF;
            l2 |= (long)(byArray[n3 + 1] << 8 & 0xFF00);
            l2 |= (long)(byArray[n3 + 2] << 16 & 0xFF0000);
            l ^= (l2 |= (long)(byArray[n3 + 3] << 24 & 0xFF000000));
        }
        l2 = byArray[n3] & 0xFF;
        l2 |= (long)(byArray[n3 + 1] << 8 & 0xFF00);
        l2 |= (long)(byArray[n3 + 2] << 16 & 0xFF0000);
        return (l2 |= (long)(byArray[n3 + 3] << 24 & 0xFF000000)) == l;
    }

    public static boolean verifyChecksum(ByteBuffer byteBuffer, int n, int n2) {
        int n3;
        if ((n2 & 3) != 0 || n2 <= 4) {
            return false;
        }
        int n4 = 0;
        int n5 = n2 - 4;
        int n6 = -1;
        for (n3 = n; n3 < n5; n3 += 4) {
            n6 = byteBuffer.getInt(n3) & 0xFFFFFFFF;
            n4 ^= n6;
        }
        n6 = byteBuffer.getInt(n3) & 0xFFFFFFFF;
        return n6 == n4;
    }

    public static void appendChecksum(byte[] byArray) {
        NewCrypt.appendChecksum(byArray, 0, byArray.length);
    }

    public static void appendChecksum(byte[] byArray, int n, int n2) {
        long l;
        int n3;
        long l2 = 0L;
        int n4 = n2 - 4;
        for (n3 = n; n3 < n4; n3 += 4) {
            l = byArray[n3] & 0xFF;
            l |= (long)(byArray[n3 + 1] << 8 & 0xFF00);
            l |= (long)(byArray[n3 + 2] << 16 & 0xFF0000);
            l2 ^= (l |= (long)(byArray[n3 + 3] << 24 & 0xFF000000));
        }
        l = byArray[n3] & 0xFF;
        l |= (long)(byArray[n3 + 1] << 8 & 0xFF00);
        l |= (long)(byArray[n3 + 2] << 16 & 0xFF0000);
        l |= (long)(byArray[n3 + 3] << 24 & 0xFF000000);
        byArray[n3] = (byte)(l2 & 0xFFL);
        byArray[n3 + 1] = (byte)(l2 >> 8 & 0xFFL);
        byArray[n3 + 2] = (byte)(l2 >> 16 & 0xFFL);
        byArray[n3 + 3] = (byte)(l2 >> 24 & 0xFFL);
    }

    public static void appendChecksum(ByteBuffer byteBuffer, int n, int n2) {
        int n3;
        int n4 = 0;
        int n5 = n2 - 4;
        for (n3 = n; n3 < n5; n3 += 4) {
            int n6 = 0xFFFFFFFF & byteBuffer.getInt(n3);
            n4 ^= n6;
        }
        byteBuffer.putInt(n3, 0xFFFFFFFF & n4);
    }

    public static void encXORPass(ByteBuffer byteBuffer, int n, int n2, int n3) {
        int n4;
        int n5 = n2 - 8;
        int n6 = n3;
        for (n4 = 4 + n; n4 < n5; n4 += 4) {
            int n7 = byteBuffer.getInt(n4);
            n6 += n7;
            byteBuffer.putInt(n4, n7 ^= n6);
        }
        byteBuffer.putInt(n4, n6);
    }

    public void decrypt(ByteBuffer byteBuffer, int n, int n2) throws IOException {
        int n3 = n2 / 8;
        for (int i = 0; i < n3; ++i) {
            this.b.processBlock(byteBuffer, n + i * 8, byteBuffer, n + i * 8);
        }
    }

    public void crypt(ByteBuffer byteBuffer, int n, int n2) throws IOException {
        int n3 = n2 / 8;
        for (int i = 0; i < n3; ++i) {
            this.a.processBlock(byteBuffer, n + i * 8, byteBuffer, n + i * 8);
        }
        byteBuffer.position(n + n3 * 8);
    }
}
