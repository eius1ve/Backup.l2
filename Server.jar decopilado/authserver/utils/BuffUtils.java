/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.function.Function;

public class BuffUtils {
    public static <R> R withBytes(Function<ByteBuffer, R> function, byte[] ... byArray) {
        return BuffUtils.withBytes(function, ByteOrder.nativeOrder(), byArray);
    }

    public static <R> R withBytes(Function<ByteBuffer, R> function, ByteOrder byteOrder, byte[] ... byArray) {
        int n = 0;
        for (byte[] byArray2 : byArray) {
            n += byArray2.length;
        }
        byte[] byArray3 = new byte[n];
        int n2 = 0;
        for (byte[] byArray4 : byArray) {
            System.arraycopy(byArray4, 0, byArray3, n2, byArray4.length);
            n2 += byArray4.length;
        }
        return function.apply(ByteBuffer.wrap(byArray3).order(byteOrder));
    }

    public static <R> R withBytes(Function<ByteBuffer, R> function, ByteOrder byteOrder, int n) {
        return function.apply(ByteBuffer.wrap(new byte[n]).order(byteOrder));
    }
}
