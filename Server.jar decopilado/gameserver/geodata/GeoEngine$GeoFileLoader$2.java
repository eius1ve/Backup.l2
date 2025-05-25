/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.geodata;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import l2.gameserver.geodata.GeoEngine;

final class GeoEngine.GeoFileLoader.2
extends GeoEngine.GeoFileLoader {
    @Override
    public boolean matches(File file) {
        return file.getName().endsWith(".l2g");
    }

    @Override
    public ByteBuffer read(File file, int n, int n2) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            FileChannel fileChannel = randomAccessFile.getChannel();
            int n3 = 0;
            int n4 = Math.max(4, (int)fileChannel.size() - 4);
            ByteBuffer byteBuffer = ByteBuffer.allocate(n4).order(ByteOrder.LITTLE_ENDIAN);
            byteBuffer.limit(4);
            fileChannel.read(byteBuffer);
            byteBuffer.flip();
            n3 = 0x814141AB ^ byteBuffer.getInt();
            byteBuffer.clear();
            fileChannel.read(byteBuffer);
            byteBuffer.rewind();
            byte by = (byte)(n3 >> 24 & 0xFF ^ n3 >> 16 & 0xFF ^ n3 >> 8 & 0xFF ^ n3 >> 0 & 0xFF);
            while (byteBuffer.hasRemaining()) {
                byteBuffer.put(byteBuffer.position(), (byte)(byteBuffer.get() ^ by));
                by = byteBuffer.get(byteBuffer.position() - 1);
                n3 -= by;
            }
            byteBuffer.rewind();
            if (n3 != 0) {
                throw new RuntimeException("Test failed!");
            }
            if (n4 < 196608 || n3 != 0) {
                throw new RuntimeException("Invalid geodata : " + file.getName() + "!");
            }
            return byteBuffer;
        }
        catch (IOException iOException) {
            bc.error("", (Throwable)iOException);
            return null;
        }
    }
}
