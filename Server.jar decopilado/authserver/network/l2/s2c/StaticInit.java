/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2.s2c;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import l2.authserver.crypt.NewCrypt;
import l2.authserver.network.l2.BaseLoginClient;
import l2.authserver.utils.BuffUtils;
import l2.commons.net.nio.impl.SendablePacket;

public class StaticInit
extends SendablePacket<BaseLoginClient> {
    public static final byte[] STATIC_BLOWFISH_KEY = new byte[]{107, 96, -53, 91, -126, -50, -112, -79, -52, 43, 108, 85, 108, 108, 108, 108};
    private static final NewCrypt b = new NewCrypt(STATIC_BLOWFISH_KEY);
    private final byte[] g;

    public StaticInit(byte[] byArray) {
        this.g = byArray;
    }

    public StaticInit(byte[] byArray, byte[] byArray2, int n) {
        this(StaticInit.a(n, StaticInit.a(n, byArray, byArray2)));
    }

    private static byte[] a(int n, byte[] byArray, byte[] byArray2) {
        return BuffUtils.withBytes(byteBuffer -> {
            byteBuffer.put((byte)0);
            byteBuffer.putInt(n);
            byteBuffer.putInt(50721);
            byteBuffer.put(byArray, 0, byArray.length);
            byteBuffer.putInt(1732584193);
            byteBuffer.putInt(-271733879);
            byteBuffer.putInt(-1732584194);
            byteBuffer.putInt(271733878);
            byteBuffer.put(byArray2, 0, byArray2.length);
            byteBuffer.putInt(0);
            byteBuffer.putInt(0);
            byteBuffer.putChar('\u0000');
            return byteBuffer.array();
        }, ByteOrder.LITTLE_ENDIAN, 8 * (1 + (9 + byArray.length + 16 + byArray2.length + 4 + 4 + 4 + 2) / 8));
    }

    private static byte[] a(int n, byte[] byArray) {
        try {
            int n2 = 4 + byArray.length;
            n2 += 8 - n2 % 8;
            byte[] byArray2 = new byte[n2];
            System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
            ByteBuffer byteBuffer = ByteBuffer.wrap(byArray2).order(ByteOrder.LITTLE_ENDIAN);
            NewCrypt.encXORPass(byteBuffer, 0, n2, n);
            b.crypt(byteBuffer, 0, n2);
            return byArray2;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    protected boolean write() {
        this.getByteBuffer().put(this.g);
        return true;
    }
}
