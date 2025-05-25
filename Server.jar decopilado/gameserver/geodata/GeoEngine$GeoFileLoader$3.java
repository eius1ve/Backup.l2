/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.geodata;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import l2.gameserver.geodata.GeoEngine;

final class GeoEngine.GeoFileLoader.3
extends GeoEngine.GeoFileLoader {
    @Override
    public boolean matches(File file) {
        return file.getName().endsWith(".dat");
    }

    @Override
    public ByteBuffer read(File file, int n, int n2) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            FileChannel fileChannel = randomAccessFile.getChannel();
            int n3 = (int)fileChannel.size();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, n3);
            mappedByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            byte by = mappedByteBuffer.get();
            byte by2 = mappedByteBuffer.get();
            if (n3 < 393234 || by != n || by2 != n2) {
                fileChannel.close();
                randomAccessFile.close();
                throw new RuntimeException("Invalid geodata : " + file.getName() + "!");
            }
            mappedByteBuffer.rewind();
            return mappedByteBuffer;
        }
        catch (IOException iOException) {
            bc.error("", (Throwable)iOException);
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean load(byte[][][] byArray, ByteBuffer byteBuffer, int n) {
        int n2 = 18;
        int n3 = 0;
        byte[][][] byArray2 = byArray;
        synchronized (byArray) {
            Object object = byArray[n];
            if (object == null) {
                byte[][] byArrayArray = new byte[65536][];
                object = byArrayArray;
                byArray[n] = byArrayArray;
            }
            // ** MonitorExit[var7_6] (shouldn't be in output)
            byteBuffer.rewind();
            block10: for (n3 = 0; n3 < 65536; ++n3) {
                short s = byteBuffer.getShort(n2);
                n2 += 2;
                switch (s) {
                    case 0: {
                        byte[] byArray3 = new byte[]{0, byteBuffer.get(n2), byteBuffer.get(n2 + 1)};
                        n2 += 4;
                        object[n3] = byArray3;
                        continue block10;
                    }
                    case 64: {
                        byte[] byArray3 = new byte[129];
                        byArray3[0] = 1;
                        byteBuffer.position(n2);
                        byteBuffer.get(byArray3, 1, 128);
                        n2 += 128;
                        object[n3] = byArray3;
                        continue block10;
                    }
                    default: {
                        byte[] byArray3;
                        ByteBuffer byteBuffer2 = tempBuff;
                        synchronized (byteBuffer2) {
                            tempBuff.clear();
                            tempBuff.put((byte)2);
                            for (int i = 0; i < 64; ++i) {
                                byte by = (byte)byteBuffer.getShort(n2);
                                if (by <= 0 || by > 127) {
                                    throw new RuntimeException("Invalid layer count for MultilayerBlock");
                                }
                                MAX_LAYERS = Math.max(MAX_LAYERS, by);
                                n2 += 2;
                                tempBuff.put(by);
                                for (int j = 0; j < by << 1; ++j) {
                                    tempBuff.put(byteBuffer.get(n2++));
                                }
                            }
                            tempBuff.flip();
                            byArray3 = Arrays.copyOf(tempBuff.array(), tempBuff.remaining());
                            tempBuff.rewind();
                        }
                        object[n3] = byArray3;
                    }
                }
            }
            byteBuffer.position(0);
            return true;
        }
    }
}
